/*******************************************************************************
 * Copyright (c) 2015-2018 Oak Ridge National Laboratory.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.csstudio.display.builder.representation.javafx.widgets;

import static org.csstudio.display.builder.representation.EmbeddedDisplayRepresentationUtil.checkCompletion;
import static org.csstudio.display.builder.representation.EmbeddedDisplayRepresentationUtil.loadDisplayModel;
import static org.csstudio.display.builder.representation.ToolkitRepresentation.logger;

import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;

import org.csstudio.display.builder.model.DirtyFlag;
import org.csstudio.display.builder.model.DisplayModel;
import org.csstudio.display.builder.model.UntypedWidgetPropertyListener;
import org.csstudio.display.builder.model.WidgetProperty;
import org.csstudio.display.builder.model.util.ModelThreadPool;
import org.csstudio.display.builder.model.widgets.EmbeddedDisplayWidget;
import org.csstudio.display.builder.model.widgets.EmbeddedDisplayWidget.Resize;
import org.csstudio.display.builder.representation.EmbeddedDisplayRepresentationUtil.DisplayAndGroup;
import org.csstudio.display.builder.representation.javafx.JFXUtil;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.transform.Scale;

/** Creates JavaFX item for model widget
 *
 *  <p>Different from widget representations in general,
 *  this one implements the loading of the embedded model,
 *  an operation that could be considered a runtime aspect.
 *  This was done to allow viewing the embedded content
 *  in the editor.
 *  The embedded model will be started by the EmbeddedDisplayRuntime.
 *
 *  @author Kay Kasemir
 */
@SuppressWarnings("nls")
public class EmbeddedDisplayRepresentation extends RegionBaseRepresentation<ScrollPane, EmbeddedDisplayWidget>
{
    private static final Background TRANSPARENT_BACKGROUND = new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY));
    private static final Border EDIT_BORDER = new Border(new BorderStroke(new Color(0.45, 0.44, 0.43, 0.7), BorderStrokeStyle.DOTTED, CornerRadii.EMPTY, BorderWidths.DEFAULT));

    private final DirtyFlag dirty_sizes = new DirtyFlag();
    private final DirtyFlag dirty_background = new DirtyFlag();
    private final UntypedWidgetPropertyListener backgroundChangedListener = this::backgroundChanged;
    private final UntypedWidgetPropertyListener fileChangedListener = this::fileChanged;
    private final UntypedWidgetPropertyListener sizesChangedListener = this::sizesChanged;

    private volatile double zoom_factor = 1.0;

    /** Inner pane that holds child widgets
     *
     *  <p>Set to null when representation is disposed,
     *  which is used as indicator to pending display updates.
     */
    private volatile Pane inner;
    private volatile Background inner_background = Background.EMPTY;
    private volatile Border inner_border = Border.EMPTY;

    private Scale zoom;
    private ScrollPane scroll;

    /** The display file (and optional group inside that display) to load */
    private final AtomicReference<DisplayAndGroup> pending_display_and_group = new AtomicReference<>();

    /** Track active model in a thread-safe way
     *  to assert that each one is represented and removed
     */
    private final AtomicReference<DisplayModel> active_content_model = new AtomicReference<>();

    /** Flag to avoid recursion when this code changes the widget size */
    private volatile boolean resizing = false;

    @Override
    protected boolean isFilteringEditModeClicks()
    {
        return true;
    }

    @Override
    public ScrollPane createJFXNode() throws Exception
    {
        // inner.setScaleX() and setScaleY() zoom from the center
        // and not the top-left edge, requiring adjustments to
        // inner.setTranslateX() and ..Y() to compensate.
        // Using a separate Scale transformation does not have that problem.
        // See http://stackoverflow.com/questions/10707880/javafx-scale-and-translate-operation-results-in-anomaly
        inner = new Pane();
        inner.getTransforms().add(zoom = new Scale());

        scroll = new ScrollPane(inner);
        //  Removing 1px border around the ScrollPane's content. See https://stackoverflow.com/a/29376445
        scroll.getStyleClass().addAll("embedded_display", "edge-to-edge");
        // Panning tends to 'jerk' the content when clicked
        // scroll.setPannable(true);
        return scroll;
    }

    @Override
    protected Parent getChildParent(final Parent parent)
    {
        return inner;
    }

    @Override
    protected void registerListeners()
    {
        super.registerListeners();
        model_widget.propWidth().addUntypedPropertyListener(sizesChangedListener);
        model_widget.propHeight().addUntypedPropertyListener(sizesChangedListener);
        model_widget.propResize().addUntypedPropertyListener(sizesChangedListener);

        model_widget.propFile().addUntypedPropertyListener(fileChangedListener);
        model_widget.propGroupName().addUntypedPropertyListener(fileChangedListener);
        model_widget.propMacros().addUntypedPropertyListener(fileChangedListener);

        model_widget.propTransparent().addUntypedPropertyListener(backgroundChangedListener);
        fileChanged(null, null, null);
    }

    @Override
    protected void unregisterListeners()
    {
        model_widget.propWidth().removePropertyListener(sizesChangedListener);
        model_widget.propHeight().removePropertyListener(sizesChangedListener);
        model_widget.propResize().removePropertyListener(sizesChangedListener);
        model_widget.propFile().removePropertyListener(fileChangedListener);
        model_widget.propGroupName().removePropertyListener(fileChangedListener);
        model_widget.propMacros().removePropertyListener(fileChangedListener);
        model_widget.propTransparent().removePropertyListener(backgroundChangedListener);
        super.unregisterListeners();
    }

    private void sizesChanged(final WidgetProperty<?> property, final Object old_value, final Object new_value)
    {
        if (resizing)
            return;

        final int widget_width = model_widget.propWidth().getValue();
        final int widget_height = model_widget.propHeight().getValue();
        inner.setMinWidth(widget_width);
        inner.setMinHeight(widget_height);

        final Resize resize = model_widget.propResize().getValue();
        final DisplayModel content_model = active_content_model.get();
        if (content_model != null)
        {
            final int content_width = content_model.propWidth().getValue();
            final int content_height = content_model.propHeight().getValue();
            if (resize == Resize.ResizeContent)
            {
                final double zoom_x = content_width  > 0 ? (double) widget_width  / content_width : 1.0;
                final double zoom_y = content_height > 0 ? (double) widget_height / content_height : 1.0;
                zoom_factor = Math.min(zoom_x, zoom_y);
            }
            else if (resize == Resize.SizeToContent)
            {
                zoom_factor = 1.0;
                resizing = true;
                if (content_width > 0)
                    model_widget.propWidth().setValue(content_width);
                if (content_height > 0)
                    model_widget.propHeight().setValue(content_height);
                resizing = false;
            }
        }

        dirty_sizes.mark();
        toolkit.scheduleUpdate(this);
    }

    private void fileChanged(final WidgetProperty<?> property, final Object old_value, final Object new_value)
    {
        final DisplayAndGroup file_and_group =
            new DisplayAndGroup(model_widget.propFile().getValue(), model_widget.propGroupName().getValue());

        // System.out.println("Requested: " + file_and_group);
        final DisplayAndGroup skipped = pending_display_and_group.getAndSet(file_and_group);
        if (skipped != null)
            logger.log(Level.FINE, "Skipped: {0}", skipped);

        // Load embedded display in background thread
        ModelThreadPool.getExecutor().execute(this::updatePendingDisplay);
    }

    /** Update to the next pending display
     *
     *  <p>Synchronized to serialize the background threads.
     *
     *  <p>Example: Displays A, B, C are requested in quick succession.
     *
     *  <p>pending_display_and_group=A is submitted to executor thread A.
     *
     *  <p>While handling A, pending_display_and_group=B is submitted to executor thread B.
     *  Thread B will be blocked in synchronized method.
     *
     *  <p>Then pending_display_and_group=C is submitted to executor thread C.
     *  As thread A finishes, thread B finds pending_display_and_group==C.
     *  As thread C finally continues, it finds pending_display_and_group empty.
     *  --> Showing A, then C, skipping B.
     */
    private synchronized void updatePendingDisplay()
    {
        final DisplayAndGroup handle = pending_display_and_group.getAndSet(null);
        if (handle == null)
        {
            // System.out.println("Nothing to handle");
            return;
        }
        if (inner == null)
        {
            // System.out.println("Aborted: " + handle);
            return;
        }

        try
        {   // Load new model (potentially slow)
            final DisplayModel new_model = loadDisplayModel(model_widget, handle);

            // Stop (old) runtime
            // EmbeddedWidgetRuntime tracks this property to start/stop the embedded model's runtime
            model_widget.runtimePropEmbeddedModel().setValue(null);

            // Atomically update the 'active' model
            final DisplayModel old_model = active_content_model.getAndSet(new_model);
            new_model.propBackgroundColor().addUntypedPropertyListener(backgroundChangedListener);

            if (old_model != null)
            {   // Dispose old model
                final Future<Object> completion = toolkit.submit(() ->
                {
                    toolkit.disposeRepresentation(old_model);
                    return null;
                });
                checkCompletion(model_widget, completion, "timeout disposing old representation");
            }
            // Represent new model on UI thread
            final Future<Object> completion = toolkit.submit(() ->
            {
                representContent(new_model);
                return null;
            });
            checkCompletion(model_widget, completion, "timeout representing new content");

            // Allow EmbeddedWidgetRuntime to start the new runtime
            model_widget.runtimePropEmbeddedModel().setValue(new_model);
        }
        catch (Exception ex)
        {
            logger.log(Level.WARNING, "Failed to handle embedded display " + handle, ex);
        }
    }

    /** @param content_model Model to represent */
    private void representContent(final DisplayModel content_model)
    {
        try
        {
            sizesChanged(null, null, null);
            toolkit.representModel(inner, content_model);
            backgroundChanged(null, null, null);
        }
        catch (final Exception ex)
        {
            logger.log(Level.WARNING, "Failed to represent embedded display", ex);
        }
    }

    private void backgroundChanged(final WidgetProperty<?> property, final Object old_value, final Object new_value)
    {
        final DisplayModel content_model = active_content_model.get();
        if (model_widget.propTransparent().getValue())
        {
            inner_background = TRANSPARENT_BACKGROUND;

            // Reinstall the frame in edit mode. Scroll is unusable, so make it with inner
            if (toolkit.isEditMode())
                inner_border = EDIT_BORDER;
        }
        else
        {
            if (content_model == null)
                inner_background = Background.EMPTY;
            else
                inner_background = new Background(new BackgroundFill(JFXUtil.convert(content_model.propBackgroundColor().getValue()), CornerRadii.EMPTY, Insets.EMPTY));

            // TODO Haven't found perfect way to set the 'background' color
            // of the embedded content.
            // Setting the 'inner' background will sometimes leave a gray section in the right and or bottom edge
            // of the embedded content if the container is (much) larger than the content
            // The scroll pane background can only be set via style,
            // and then shines through on the outside of the scrollbars
            // scroll.setStyle("-fx-control-inner-background: " + JFXUtil.webRGB(content_model.propBackgroundColor().getValue()) +
            //                  "; -fx-background: " + JFXUtil.webRGB(content_model.propBackgroundColor().getValue()));
            inner_border = Border.EMPTY;
        }

        dirty_background.mark();
        toolkit.scheduleUpdate(this);
    }

    @Override
    public void updateChanges()
    {
        // Late update after disposal?
        if (inner == null)
            return;
        super.updateChanges();
        if (dirty_sizes.checkAndClear())
        {
            final Integer width = model_widget.propWidth().getValue();
            final Integer height = model_widget.propHeight().getValue();
            scroll.setPrefSize(width, height);

            final Resize resize = model_widget.propResize().getValue();
            if (resize == Resize.None)
            {
                zoom.setX(1.0);
                zoom.setY(1.0);
                scroll.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
                scroll.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
            }
            else if (resize == Resize.ResizeContent)
            {
                zoom.setX(zoom_factor);
                zoom.setY(zoom_factor);
                scroll.setHbarPolicy(ScrollBarPolicy.NEVER);
                scroll.setVbarPolicy(ScrollBarPolicy.NEVER);
            }
            else // SizeToContent
            {
                zoom.setX(1.0);
                zoom.setY(1.0);
                scroll.setHbarPolicy(ScrollBarPolicy.NEVER);
                scroll.setVbarPolicy(ScrollBarPolicy.NEVER);
            }
        }
        if (dirty_background.checkAndClear())
        {
            inner.setBackground(inner_background);
            inner.setBorder(inner_border);
        }
    }

    @Override
    public void dispose()
    {
        // When the file name is changed, updatePendingDisplay()
        // will atomically update the active_content_model,
        // represent the new model, and then set runtimePropEmbeddedModel.
        //
        // Fetching the embedded model from active_content_model
        // could dispose a representation that hasn't been represented, yet.
        // Fetching the embedded model from runtimePropEmbeddedModel
        // could fail to dispose what's just now being represented.
        //
        // --> Very unlikely to happen because runtime has been stopped,
        //     so nothing is changing the file name right now.
        final DisplayModel em = active_content_model.getAndSet(null);
        model_widget.runtimePropEmbeddedModel().setValue(null);

        if (inner != null  &&  em != null)
            toolkit.disposeRepresentation(em);
        inner = null;

        super.dispose();
    }
}
