/*******************************************************************************
 * Copyright (c) 2015-2018 Oak Ridge National Laboratory.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.phoebus.ui.javafx;

import static org.phoebus.ui.javafx.JFXUtil.logger;
import static org.phoebus.ui.javafx.UpdateThrottle.TIMER;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.stream.Collectors;

import org.phoebus.ui.Messages;

import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.control.Button;
import javafx.scene.control.Cell;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Callback;
import javafx.util.converter.DefaultStringConverter;

/** Table of strings
 *
 *  <p>Table that shows String data based on a list
 *  of headers and a String matrix (List of Lists).
 *
 *  <p>Data can be changed at runtime, columns will
 *  then be re-created.

 *  <p>User can edit the cells.
 *  While inefficient, the table creates a deep copy
 *  of the data submitted to it for display, so changes
 *  in the table will not affect the original data.
 *
 *  <p>Toolbar and key shortcuts can be used to add/remove
 *  rows or columns:
 *  <ul>
 *  <li>t - Show/hide toolbar
 *  </ul>
 *
 *  <p>Class is implemented as {@link BorderPane}, but should
 *  be treated as a {@link Region}.
 *
 *  @author Kay Kasemir
 */
@SuppressWarnings("nls")
public class StringTable extends BorderPane
{
    /** Value used for the last row
     *
     *  <p>This exact value is placed in the last row.
     *  It's not considered part of the data,
     *  but a marker that allows user to start a new row
     *  by entering values.
     *
     *  <p>Table data is compared as exact identity (== MAGIC_LAST_ROW).
     */
    private static final List<StringProperty> MAGIC_LAST_ROW = Arrays.asList(new SimpleStringProperty(Messages.MagicLastRow));

    /** Value used to temporarily detach the 'data' from table */
    private static final ObservableList<List<StringProperty>> NO_DATA = FXCollections.observableArrayList();

    /** Data shown in the table, includes MAGIC_LAST_ROW */
    private final ObservableList<List<StringProperty>> data = FXCollections.observableArrayList();

    /** Optional cell coloring, does not include MAGIC_LAST_ROW */
    private volatile List<List<Color>> cell_colors = null;

    /** Cell value factory aware of MAGIC_LAST_ROW which only has one column */
    private static final Callback<CellDataFeatures<List<StringProperty>, String>, ObservableValue<String>> VALUE_FACTORY = param ->
    {
        final TableView<List<StringProperty>> table = param.getTableView();
        final int col_index = table.getColumns().indexOf(param.getTableColumn());
        final List<StringProperty> value = param.getValue();
        if (value == MAGIC_LAST_ROW)
            return col_index == 0 ? MAGIC_LAST_ROW.get(0): new SimpleStringProperty("");
        else if (col_index < value.size())
            return value.get(col_index);
        return new SimpleStringProperty("<col " + col_index + "?>");
    };

    private final boolean editable;

    private Color background_color = Color.WHITE;

    private Color text_color = Color.BLACK;

    private Color last_row_color = text_color.deriveColor(0, 0, 0, 0.5);

    private Font font = Font.font(12);

    /** Table cell that displays a String,
     *  with special coloring of the MAGIC_LAST_ROW
     */
    private class StringTextCell extends TextFieldTableCell<List<StringProperty>, String>
    {
        private TextField editor = null;

        public StringTextCell()
        {
            super(new DefaultStringConverter());
        }

        // Track the text field used for editing
        @Override
        public void startEdit()
        {
            super.startEdit();
            // TextFieldTableCell happens to create the editor once,
            // so attach event filter once and then remember the editor
            // to prevent multiple event filters
            if (editor == null)
            {
                editor = (TextField) getGraphic();
                editor.addEventFilter(KeyEvent.KEY_PRESSED, this::handleKey);
            }
            // In last row, don't edit "Click to edit" but show empty initial value
            final int row = getIndex();
            if (data.get(row) == MAGIC_LAST_ROW)
                editor.clear();
        }

        private void handleKey(final KeyEvent event)
        {
            if (event.getCode() == KeyCode.TAB)
            {
                // Save the current text field content, since lost focus would otherwise restore previous value
                commitEdit(editor.getText());

                // Edit next/prev column in same row
                final ObservableList<TableColumn<List<StringProperty>, ?>> columns = getTableView().getColumns();
                final int col = columns.indexOf(getTableColumn());
                final int next = event.isShiftDown()
                               ? (col + columns.size() - 1) % columns.size()
                               : (col + 1) % columns.size();
                editCell(getIndex(), columns.get(next));
                event.consume();
            }
        }

        @Override
        public void updateItem(final String item, final boolean empty)
        {
            super.updateItem(item, empty);
            if (empty)
                return;
            final int row = getIndex();
            final int col = getTableView().getColumns().indexOf(getTableColumn());
            setTextFill(data.get(row) == MAGIC_LAST_ROW ? last_row_color : text_color);
            setCellStyle(this, row, col);
        }
    }

    /** Column options used for {@link BooleanCell} */
    public static final List<String> BOOLEAN_OPTIONS = Arrays.asList("false", "true");

    /** Cell with checkbox, sets data to "true"/"false" */
    private class BooleanCell extends TableCell<List<StringProperty>, String>
    {
        private final CheckBox checkbox = new CheckBox();

        public BooleanCell()
        {
            getStyleClass().add("check-box-table-cell");

            checkbox.setOnAction(event ->
            {
                final int row = getIndex();
                final int col = getTableView().getColumns().indexOf(getTableColumn());
                final String value = Boolean.toString(checkbox.isSelected());
                data.get(row).get(col).set(value);
                fireDataChanged();
            });
            checkbox.addEventFilter(KeyEvent.KEY_PRESSED, this::handleKey);
        }

        @Override
        public void startEdit()
        {
            super.startEdit();
            // The checkbox is always visible and active,
            // no need to 'startEdit' as for other cells which
            // only then show the editor,
            // but when programatically starting edit, activate the checkbox
            Platform.runLater(() -> checkbox.requestFocus());
        }

        private void handleKey(final KeyEvent event)
        {
            if (event.getCode() == KeyCode.TAB)
            {
                // Edit next/prev column in same row
                final ObservableList<TableColumn<List<StringProperty>, ?>> columns = getTableView().getColumns();
                final int col = columns.indexOf(getTableColumn());
                final int next = event.isShiftDown()
                               ? (col + columns.size() - 1) % columns.size()
                               : (col + 1) % columns.size();
                editCell(getIndex(), columns.get(next));
                event.consume();
            }
            else if (event.getCode() == KeyCode.ENTER)
            {
                // Consume 'enter' and move to next row. Space can be used to toggle (or mouse click)
                event.consume();
                editCell(getIndex() + 1, getTableColumn());
            }
        }

        @Override
        protected void updateItem(final String item, final boolean empty)
        {
            super.updateItem(item, empty);

            final int row = getIndex();
            if (empty)
                setGraphic(null);
            else
            {
                if (data.get(row) == MAGIC_LAST_ROW)
                {
                    setText(item);
                    setGraphic(null);
                }
                else
                {
                    setText(null);
                    setGraphic(checkbox);
                    // Enable checkbox for editable column
                    checkbox.setDisable(! getTableColumn().isEditable());
                    checkbox.setSelected(item.equalsIgnoreCase("true"));
                    final int col = getTableView().getColumns().indexOf(getTableColumn());
                    setCellStyle(this, row, col);
                }
            }
        }
    };

    /** Cell that allows selecting options from a combo */
    private class ComboCell extends ComboBoxTableCell<List<StringProperty>, String>
    {
        private ComboBox<String> combo = null;

        public ComboCell(final List<String> options)
        {
            super(FXCollections.observableArrayList(options));
            setComboBoxEditable(true);
        }

        @SuppressWarnings("unchecked")
        @Override
        public void startEdit()
        {
            super.startEdit();
            if (combo == null)
            {
                combo = (ComboBox<String>) getGraphic();
                combo.addEventFilter(KeyEvent.KEY_PRESSED, this::handleKey);
            }

            // By default, the combo is shown, but not 'active'.
            // requestFocus activates the text field of the combo
            TIMER.schedule(() -> Platform.runLater(() ->  combo.requestFocus()),
                200, TimeUnit.MILLISECONDS);
        }

        private void handleKey(final KeyEvent event)
        {
            if (event.getCode() == KeyCode.TAB)
            {
                // Commit value from combo's text field into combo's value...
                combo.commitValue();
                // .. and use that for the cell
                commitEdit(combo.getValue());

                // Edit next/prev column in same row
                final ObservableList<TableColumn<List<StringProperty>, ?>> columns = getTableView().getColumns();
                final int col = columns.indexOf(getTableColumn());
                final int next = event.isShiftDown()
                               ? (col + columns.size() - 1) % columns.size()
                               : (col + 1) % columns.size();
                editCell(getIndex(), columns.get(next));
                event.consume();
            }
        }

        @Override
        public void updateItem(final String item, final boolean empty)
        {
            super.updateItem(item, empty);
            if (empty)
                return;
            final int row = getIndex();
            final int col = getTableView().getColumns().indexOf(getTableColumn());
            setCellStyle(this, row, col);
        }
    };


    private final ToolBar toolbar = new ToolBar();

    private final TableView<List<StringProperty>> table = new TableView<>(data);

    /** Currently editing a cell? */
    private boolean editing = false;

    private volatile StringTableListener listener = null;


    /** Constructor
     *  @param editable Allow user interaction (toolbar, edit), or just display data?
     */
    public StringTable(final boolean editable)
    {
        this.editable = editable;
        table.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
        table.getSelectionModel().setCellSelectionEnabled(true);
        table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        table.getSelectionModel().getSelectedIndices().addListener(this::selectionChanged);
        table.setPlaceholder(new Label());

        if (editable)
        {
            table.setEditable(true);
            // Check for keys in both toolbar and table
            addEventFilter(KeyEvent.KEY_PRESSED, this::handleKey);
        }
        updateStyle();
        fillToolbar();
        setTop(toolbar);

        // Scroll if table is larger than its screen space
        final ScrollPane scroll = new ScrollPane(table);
        scroll.setFitToWidth(true);
        scroll.setFitToHeight(true);
        setCenter(scroll);

        setData(Arrays.asList());
    }

    /** @param select_rows Select complete rows, or individual cells? */
    public void setRowSelectionMode(final boolean select_rows)
    {
    	if (select_rows)
            table.getSelectionModel().setCellSelectionEnabled(false);
    	else
            table.getSelectionModel().setCellSelectionEnabled(true);
    }

    /** Set selection
     *  @param sel_row_col Flattened list of row, col, row, col, .. cell indices. May be <code>null</code>
     */
    public void setSelection(final List<Integer> sel_row_col)
    {
        final TableViewSelectionModel<List<StringProperty>> selection = table.getSelectionModel();
        selection.clearSelection();

        if (sel_row_col == null)
            return;

        final ObservableList<TableColumn<List<StringProperty>, ?>> columns = table.getColumns();
        int i = 0;
        while (i < sel_row_col.size())
        {
            final int row = sel_row_col.get(i++);
            final int col = sel_row_col.get(i++);
            if (row < data.size()  &&  col < columns.size())
                selection.select(row, columns.get(col));
        }
    }

    /** @param listener Listener to notify of changes */
    public void setListener(final StringTableListener listener)
    {
        this.listener = listener;
    }

    private void fillToolbar()
    {
        toolbar.getItems().addAll(
            createToolbarButton("add_row", Messages.AddRow, event -> addRow()),
            createToolbarButton("remove_row", Messages.RemoveRow, event -> deleteRow()),
            createToolbarButton("row_up", Messages.MoveRowUp, event -> moveRowUp()),
            createToolbarButton("row_down", Messages.MoveRowDown, event -> moveRowDown()),
            createToolbarButton("rename_col", Messages.RenameColumn, event -> renameColumn()),
            createToolbarButton("add_col", Messages.AddColumn, event -> addColumn()),
            createToolbarButton("remove_col", Messages.RemoveColumn, event -> deleteColumn()),
            createToolbarButton("col_left", Messages.MoveColumnLeft, event -> moveColumnLeft()),
            createToolbarButton("col_right", Messages.MoveColumnRight, event -> moveColumnRight()));
        Platform.runLater(toolbar::layout);
    }

    private Button createToolbarButton(final String id, final String tool_tip, final EventHandler<ActionEvent> handler)
    {
        final Button button = new Button();
        try
        {
            // Icons are not centered inside the button until the
            // button is once pressed, or at least focused via "tab"
            button.setGraphic(ImageCache.getImageView(ImageCache.class, "/icons/" + id + ".png"));

            // Using the image as a background like this centers the image,
            // but replaces the complete characteristic button outline with just the icon.
            // button.setBackground(new Background(new BackgroundImage(new Image(Activator.getIcon(id)),
            //                      BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
            //                      BackgroundPosition.CENTER,
            //                      new BackgroundSize(16, 16, false, false, false, false))));
            button.setTooltip(new Tooltip(tool_tip));
        }
        catch (Exception ex)
        {
            logger.log(Level.WARNING, "Cannot load icon for " + id, ex);
            button.setText(tool_tip);
        }
        // Without defining the button size, the buttons may start out zero-sized
        // until they're first pressed/tabbed
        button.setMinSize(35, 25);
        button.setOnAction(handler);

        // Forcing a layout of the button on later UI ticks
        // tends to center the image
        Platform.runLater(() -> Platform.runLater(button::requestLayout));

        return button;
    }

    /** @return <code>true</code> if toolbar is visible */
    public boolean isToolbarVisible()
    {
        return getTop() != null;
    }

    /** @param show <code>true</code> if toolbar should be displayed */
    public void showToolbar(final boolean show)
    {
        if (isToolbarVisible() == show)
            return;
        if (show)
            setTop(toolbar);
        else
            setTop(null);

        // Force layout to reclaim space used by hidden toolbar,
        // or make room for the visible toolbar
        layoutChildren();

        ToolbarHelper.refreshHack(toolbar);
    }

    /** @param color Background color */
    public void setBackgroundColor(final Color color)
    {
        background_color = color;
        updateStyle();
    }

    /** @param color Text color */
    public void setTextColor(final Color color)
    {
        text_color = color;
        last_row_color = color.deriveColor(0, 0, 0, 0.5);
        updateStyle();
    }

    /** @param font Font */
    public void setFont(final Font font)
    {
        this.font = font;
        updateStyle();
    }

    /** Update style for colors and font */
    private void updateStyle()
    {
        table.setStyle("-fx-base: " + JFXUtil.webRGB(background_color) + "; " +
                       "-fx-text-background-color: " + JFXUtil.webRGB(text_color) + "; " +
                       "-fx-font-family: \"" + font.getFamily() + "\"; " +
                       "-fx-font-size: " + font.getSize()/12 + "em");
    }

    /** Set or update headers, i.e. define the columns
     *  @param headers Header labels
     */
    public void setHeaders(final List<String> headers)
    {
        // Remove all data
        table.setItems(NO_DATA);

        // Forcing this refresh avoided https://github.com/kasemir/org.csstudio.display.builder/issues/245,
        // an IndexOutOfBoundsException somewhere in CSS updates that uses the wrong table row count
        // Doesn't seem necessary any more?
        table.refresh();

        cell_colors = null;
        // Remove table columns, create new ones
        table.getColumns().clear();
        for (String header : headers)
            createTableColumn(-1, header);

        // Start over with no data, since table columns changed
        data.clear();
        if (editable)
            data.add(MAGIC_LAST_ROW);
        table.setItems(data);
    }

    /** Set (minimum and preferred) column width
     *
     *  @param column Column index, 0 .. <code>getHeaders().size()-1</code>
     *  @param width Width
     */
    public void setColumnWidth(final int column, final int width)
    {
        table.getColumns().get(column).setMinWidth(width);
        table.getColumns().get(column).setPrefWidth(width);
    }

    /** Allow editing a column
     *
     *  <p>By default, all columns of an 'active' table
     *  are editable, but this method can change it.
     *
     *  @param column Column index, 0 .. <code>getHeaders().size()-1</code>
     *  @param editable
     */
    public void setColumnEditable(final int column, final boolean editable)
    {
        table.getColumns().get(column).setEditable(editable);
    }

    /** Configure column options.
     *
     *  <p>If the list of options is empty,
     *  the cells in the column will offer a generic text field
     *  for entering values.
     *
     *  <p>If there are options, the column will use a drop-down
     *  list (combo box) for selecting one of the options.
     *
     *  @param column Column index, 0 .. <code>getHeaders().size()-1</code>
     *  @param options Options, may be <code>null</code>
     */
    public void setColumnOptions(final int column, final List<String> options)
    {
        @SuppressWarnings("unchecked")
        final TableColumn<List<StringProperty>, String> table_column = (TableColumn<List<StringProperty>, String>) table.getColumns().get(column);
        final Callback<TableColumn<List<StringProperty>, String>, TableCell<List<StringProperty>, String>> factory;

        if (options == null || options.isEmpty())
            factory = list -> new StringTextCell();
        else if (optionsAreBoolean(options))
            factory = list -> new BooleanCell();
        else
            factory = list -> new ComboCell(options);
        table_column.setCellFactory(factory);
    }

    /** Get options of a column
     *
     *  @param column Column index, 0 .. <code>getHeaders().size()-1</code>
     *  @return Options for boolean or combo-box cells, or empty list for plain string cell
     */
    public List<String> getColumnOptions(final int column)
    {
        final TableCell<List<StringProperty>, ?> cell = table.getColumns().get(column).getCellFactory().call(null);
        if (cell instanceof ComboCell)
            return ((ComboCell)cell).getItems();
        else if (cell instanceof BooleanCell)
            return BOOLEAN_OPTIONS;
        return Collections.emptyList();
    }

    /** Check if list of options suggest a boolean value
     *
     *  @param options Possible values of a column
     *  @return <code>true</code> if options are some variation of "true", "false"
     */
    private boolean optionsAreBoolean(final List<String> options)
    {
        return options.size() == 2   &&
               options.containsAll(BOOLEAN_OPTIONS);
    }

    /** @param index Column index, -1 to add to end
     *  @param header Header text
     */
    private void createTableColumn(final int index, final String header)
    {
        final TableColumn<List<StringProperty>, String> table_column = new TableColumn<>(header);
        table_column.setCellValueFactory(VALUE_FACTORY);
        // Prevent column re-ordering
        // (handled via moveColumn which also re-orders the data)
        table_column.setReorderable(false);

        // By default, use text field editor. setColumnOptions() can replace
        table_column.setCellFactory(list -> new StringTextCell());
        table_column.setOnEditStart(event -> editing = true);
        table_column.setOnEditCommit(event ->
        {
            editing = false;
            final int col = event.getTablePosition().getColumn();
            List<StringProperty> row = event.getRowValue();
            if (row == MAGIC_LAST_ROW)
            {
                // Entered in last row? Create new row
                row = createEmptyRow();
                final List<List<StringProperty>> data = table.getItems();
                data.add(data.size()-1, row);
            }
            row.get(col).set(event.getNewValue());
            fireDataChanged();

            // Automatically edit the next row, same column
            editCell(event.getTablePosition().getRow() + 1, table_column);
        });
        table_column.setOnEditCancel(event -> editing = false);
        table_column.setSortable(false);

        if (index >= 0)
            table.getColumns().add(index, table_column);
        else
            table.getColumns().add(table_column);
    }

    /** Start 'edit' mode for a cell
     *  @param row
     *  @param table_column
     */
    private void editCell(final int row, final TableColumn<List<StringProperty>, ?> table_column)
    {
        TIMER.schedule(() ->
            Platform.runLater(() ->
            {
                table.getSelectionModel().clearAndSelect(row, table_column);
                table.edit(row, table_column);
            }),
            100, TimeUnit.MILLISECONDS);
    }

    /** @return Header labels */
    public List<String> getHeaders()
    {
        return table.getColumns().stream().map(col -> col.getText()).collect(Collectors.toList());
    }

    private List<StringProperty> createEmptyRow()
    {
        final int size = getColumnCount();
        final List<StringProperty> row = new ArrayList<>(size);
        for (int i=0; i<size; ++i)
            row.add(new SimpleStringProperty(""));
        return row;
    }

    private int getColumnCount()
    {
        return table.getColumns().size();
    }

    /** @return Number of data rows, excluding the MAGIC_LAST_ROW when editable */
    private int getDataRowCount()
    {
        int rows = data.size();
        if (editable && rows > 0 && data.get(rows-1) == MAGIC_LAST_ROW)
            return rows-1;
        return rows;
    }

    /** Set or update data
     *
     *  @param new_data Rows of data,
     *                  where each row must contain the same number
     *                  of elements as the column headers
     */
    public void setData(final List<List<String>> new_data)
    {
        // Try to update existing StringProperty cells for common rows
        final int rows = getDataRowCount();
        final int both = Math.min(rows, new_data.size());
        for (int r=0; r<both; ++r)
            copyRow(r, new_data.get(r), data.get(r));

        // Add new rows
        for (int r=rows; r<new_data.size(); ++r)
        {
            final List<StringProperty> row = createEmptyRow();
            copyRow(r, new_data.get(r), row);
            data.add(r, row);
        }

        // Delete superfluous rows
        for (int r=rows-1; r>=new_data.size(); --r)
            data.remove(r);

        if (editable  &&  data.size() <= new_data.size())
            data.add(MAGIC_LAST_ROW);

        // Don't fire, since external source changed data, not user
        // fireDataChanged();
    }

    /** @param row Row index (for error message)
     *  @param src Strings to place into table row
     *  @param dst Table row
     */
    private void copyRow(final int row, final List<String> src, final List<StringProperty> dst)
    {
        if (src.size() != dst.size())
            logger.log(Level.WARNING, "Table needs " + dst.size() + " columns " + getHeaders() +
                       " but row " + row + " received just " + src.size() + " cells: " + src);

        // Update common cells
        int both = Math.min(src.size(), dst.size());
        for (int c=0; c<both; ++c)
            dst.get(c).set(src.get(c));

        // Clear remaining cells
        for (int c=src.size(); c<dst.size(); ++c)
            dst.get(c).set("");
    }

    /** Get complete table content
     *  @return List of rows, where each row contains the list of cell strings
     */
    public List<List<String>> getData()
    {
        final int rows = getDataRowCount();
        final int cols = getColumnCount();
        final List<List<String>> result = new ArrayList<>(rows);
        for (int r=0; r<rows; ++r)
        {
            final List<String> row = new ArrayList<>(cols);
            for (StringProperty cell : data.get(r))
                row.add(cell.get());
            result.add(row);
        }
        return result;
    }

    /** Get data of one table cell
     *  @param row Table row
     *  @param col Table column
     *  @return Value of that cell or "" for invalid row, column
     */
    public String getCell(final int row, final int col)
    {
        try
        {
            final List<StringProperty> row_data = data.get(row);
            if (row_data == MAGIC_LAST_ROW)
                return "";
            return row_data.get(col).get();
        }
        catch (IndexOutOfBoundsException ex)
        {
            return "";
        }
    }

    /** Update one table cell
     *  @param row Table row
     *  @param col Table column
     *  @param value New cell value
     */
    public void updateCell(final int row, final int col, final String value)
    {
        try
        {
            final List<StringProperty> row_data = data.get(row);
            if (row_data == MAGIC_LAST_ROW)
                throw new IndexOutOfBoundsException("Magic Last Row");
            row_data.get(col).set(value);
        }
        catch (IndexOutOfBoundsException ex)
        {
            logger.log(Level.WARNING,
                       "Cannot update row " + row + ", col " + col + " to '" + value +
                       "' in table sized " + getDataRowCount() + " by " + getColumnCount(), ex);
        }
    }

    /** Set background color for specific cells
     *
     *  <p>Expects a list of rows,
     *  where each row contains a list of colors for each cell
     *  in that row.
     *  The list may be sparse, i.e. the list for a certain row
     *  may be <code>null</code>, or contain <code>null</code>
     *  instead of a color for a specific cell.
     *
     *  <p><b>Note:</b> The cell colors are used as provided,
     *  no deep copy is created!
     *  Caller needs to either provide a static or a thread-safe
     *  table of colors.
     *
     *  @param row Table row
     *  @param col Table column
     *  @param color Color of that cell, <code>null</code> for default
     */
    public void setCellColors(final List<List<Color>> colors)
    {
        cell_colors = colors;
        table.refresh();
    }

    /** Get background color for a specific cell
     *  @param row Table row
     *  @param col Table column
     *  @return Color of that cell, <code>null</code> for default
     */
    private Color getCellColor(final int row, final int col)
    {
        final List<List<Color>> colors = cell_colors;
        if (colors != null  &&  row < colors.size())
        {
            final List<Color> row_colors = colors.get(row);
            if (row_colors != null  &&  col < row_colors.size())
                return row_colors.get(col);
        }
        return null;
    }

    /** Set style of table cell to reflect optional background color
     * @param cell
     *  @param row Table row
     *  @param col Table column
     */
    private void setCellStyle(final Cell<String> cell, final int row, final int col)
    {
        final Color color = getCellColor(row, col);
        if (color == null)
            cell.setStyle(null);
        else
        {   // Based on modena.css
            // .table-cell has no -fx-background-color to see overall background,
            // but .table-cell:selected uses this to get border with an inset color
            cell.setStyle("-fx-background-color: -fx-table-cell-border-color, " + JFXUtil.webRGB(color) +
                          ";-fx-background-insets: 0, 0 0 1 0;");
        }
    }

    /** Handle key pressed on the table
     *
     *  <p>Ignores keystrokes while editing a cell.
     *
     *  @param event Key pressed
     */
    private void handleKey(final KeyEvent event)
    {
        if (! editing)
        {
            // Toggle toolbar on Ctrl/Command T
            if (event.getCode() == KeyCode.T  &&  event.isShortcutDown())
            {
                showToolbar(! isToolbarVisible());
                event.consume();
                return;
            }

            // Switch to edit mode on keypress
            if  (event.getCode().isLetterKey() || event.getCode().isDigitKey())
            {
                @SuppressWarnings("unchecked")
                final TablePosition<List<StringProperty>, ?> pos = table.getFocusModel().getFocusedCell();
                table.edit(pos.getRow(), pos.getTableColumn());

                // TODO If the cell had been edited before, i.e. the editor already exists,
                // that editor will be shown and it will receive the key.
                // But if the editor needed to be created for a new cell,
                // it won't receive the key?!
                // Attempts to re-send the event via a delayed
                //   Event.fireEvent(table, event.copyFor(event.getSource(), table));
                // failed to have any effect.
            }
        }
    }

    /** Add a row above the selected column,
     *  or on the very bottom if nothing selected
     */
    private void addRow()
    {
        int row = table.getSelectionModel().getSelectedIndex();
        final int len = data.size();
        if (row < 0  ||  row > len-1)
            row = len-1;
        data.add(row, createEmptyRow());
        // If cell_colors in use, add new row there as well
        if (cell_colors != null && row < cell_colors.size())
            cell_colors.add(row, new ArrayList<>());
        fireDataChanged();
    }

    /** Move selected row up  */
    private void moveRowUp()
    {
        int row = table.getSelectionModel().getSelectedIndex();
        final int num = data.size() - 1;
        if (row < 0 || num < 1)
            return;
        moveRow(row, (row - 1 + num) % num);
    }

    /** Move selected row down  */
    private void moveRowDown()
    {
        int row = table.getSelectionModel().getSelectedIndex();
        final int num = data.size() - 1;
        if (row < 0 || num < 1)
            return;
        moveRow(row, (row + 1) % num);
    }

    /** Move a row up/down
     *  @param row Row to move
     *  @param target Desired location
     */
    private void moveRow(final int row, final int target)
    {
        final int column = getSelectedColumn();
        final List<StringProperty> line = data.remove(row);
        data.add(target, line);

        if (cell_colors != null  && row < cell_colors.size()  && target < cell_colors.size())
            cell_colors.add(target, cell_colors.remove(row));

        table.getSelectionModel().clearAndSelect(target, table.getColumns().get(column));
        table.refresh();
        fireDataChanged();
    }

    /** Delete currently selected row */
    private void deleteRow()
    {
        int row = table.getSelectionModel().getSelectedIndex();
        final int len = data.size();
        if (row < 0  ||  row >= len-1)
            return;
        data.remove(row);
        if (cell_colors != null  &&  row < cell_colors.size())
            cell_colors.remove(row);
        table.refresh();
        fireDataChanged();
    }

    /** Listener to table selection */
    private void selectionChanged(final Observable what)
    {
        final StringTableListener copy = listener;
        if (copy == null)
            return;

        @SuppressWarnings("rawtypes")
        final ObservableList<TablePosition> cells = table.getSelectionModel().getSelectedCells();
        int num = cells.size();
        // Don't select the magic last row
        if (num > 0  &&  data.get(cells.get(num-1).getRow()) == MAGIC_LAST_ROW)
            --num;
        final int[] rows = new int[num], cols = new int[num];
        for (int i=0; i<num; ++i)
        {
            rows[i] = cells.get(i).getRow();
            cols[i] = cells.get(i).getColumn();
        }
        copy.selectionChanged(this, rows, cols);
    }

    /** @return Currently selected table column or -1 */
    private int getSelectedColumn()
    {
        @SuppressWarnings("rawtypes")
        final ObservableList<TablePosition> cells = table.getSelectionModel().getSelectedCells();
        if (cells.isEmpty())
            return -1;
        return cells.get(0).getColumn();
    }

    /** Prompt for column name
     *  @param name Suggested name
     *  @return Name entered by user or <code>null</code>
     */
    private String getColumnName(final String name)
    {
        final TextInputDialog dialog = new TextInputDialog(name);
        // Position dialog near table
        final Bounds absolute = localToScreen(getBoundsInLocal());
        dialog.setX(absolute.getMinX() + 10);
        dialog.setY(absolute.getMinY() + 10);
        dialog.setTitle(Messages.RenameColumnTitle);
        dialog.setHeaderText(Messages.RenameColumnInfo);
        return dialog.showAndWait().orElse(null);
    }

    /** Renames the currently selected column */
    private void renameColumn()
    {
        final int column = getSelectedColumn();
        if (column < 0)
            return;
        final TableColumn<List<StringProperty>, ?> table_col = table.getColumns().get(column);
        final String name = getColumnName(table_col.getText());
        if (name == null)
            return;
        table_col.setText(name);
        fireTableChanged();
    }

    /** Add a column to the left of the selected column,
     *  or on the very right if nothing selected
     */
    private void addColumn()
    {
        int column = getSelectedColumn();
        final String name = getColumnName(Messages.DefaultNewColumnName);
        if (name == null)
            return;
        if (column < 0)
            column = table.getColumns().size();

        // Cannot update data and table concurrently, so detach data from table:
        table.setItems(NO_DATA);
        // Add new column
        createTableColumn(column, name);
        // Add empty col. to data
        for (int r=0; r<data.size(); ++r)
        {
            final List<StringProperty> row = data.get(r);
            if (row == MAGIC_LAST_ROW)
                break;
            row.add(column, new SimpleStringProperty(""));
            if (cell_colors != null)
            {
                final List<Color> colors = cell_colors.get(r);
                if (colors != null  &&  column < colors.size())
                    colors.add(column, null);
            }
        }

        // Show the updated data
        table.setItems(data);
        table.refresh();

        fireTableChanged();
    }

    /** Move selected column to the left */
    private void moveColumnLeft()
    {
        final int column = getSelectedColumn();
        final int num = table.getColumns().size();
        if (column < 0 || num < 1)
            return;
        moveColumn(column, (column - 1 + num) % num);
    }

    /** Move selected column to the right */
    private void moveColumnRight()
    {
        final int column = getSelectedColumn();
        final int num = table.getColumns().size();
        if (column < 0 || num < 1)
            return;
        moveColumn(column, (column + 1) % num);
    }

    /** Move a column left/right
     *  @param column Column to move
     *  @param target Desired location
     */
    private void moveColumn(final int column, final int target)
    {
        int row = table.getSelectionModel().getSelectedIndex();

        // Some table columns have special cell factories to
        // represent boolean column data as a checkbox etc.
        // In principle, need to update both the data and the table columns
        // concurrently because otherwise a checkbox cell would briefly try to
        // represent non-boolean data, resulting in a long stack trace.
        // Cannot update data and table concurrently, so detach data from table:
        table.setItems(NO_DATA);

        // Move table column
        final TableColumn<List<StringProperty>, ?> col = table.getColumns().remove(column);
        table.getColumns().add(target, col);

        // Move column in data
        for (int r=0; r<data.size(); ++r)
        {
            final List<StringProperty> data_row = data.get(r);
            if (data_row == MAGIC_LAST_ROW)
                break;
            data_row.add(target, data_row.remove(column));
            if (cell_colors != null  &&  r < cell_colors.size())
            {
                // If Row or col has no colors, create them as null
                List<Color> colors = cell_colors.get(r);
                if (colors == null)
                {
                    colors = new ArrayList<>();
                    cell_colors.set(r, colors);
                }
                while (colors.size() <= Math.max(column,  target))
                    colors.add(null);
                // Move just like the data
                colors.add(target, colors.remove(column));
            }
        }

        // Re-attach data to table
        table.setItems(data);

        // Select the moved cell
        table.getSelectionModel().clearAndSelect(row, table.getColumns().get(target));
        table.refresh();
        fireTableChanged();
    }

    /** Delete currently selected column */
    private void deleteColumn()
    {
        final int column = getSelectedColumn();
        if (column < 0)
            return;
        // Detach data from table
        table.setItems(NO_DATA);
        // Update table columns
        table.getColumns().remove(column);
        // Remove that column from data
        for (int r=0; r<data.size(); ++r)
        {
            final List<StringProperty> row = data.get(r);
            if (row == MAGIC_LAST_ROW)
                break;
            if (column < row.size())
                row.remove(column);
            if (cell_colors != null)
            {
                final List<Color> colors = cell_colors.get(r);
                if (colors != null  &&  column < colors.size())
                    colors.remove(column);
            }
        }
        // Re-attach data to table
        table.setItems(data);
        fireTableChanged();
    }

    private void fireTableChanged()
    {
        final StringTableListener copy = listener;
        if (copy != null)
            copy.tableChanged(this);
    }

    private void fireDataChanged()
    {
        final StringTableListener copy = listener;
        if (copy != null)
            copy.dataChanged(this);
    }
}
