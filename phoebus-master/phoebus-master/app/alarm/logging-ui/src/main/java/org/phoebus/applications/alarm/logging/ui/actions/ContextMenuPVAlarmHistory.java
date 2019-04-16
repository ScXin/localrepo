package org.phoebus.applications.alarm.logging.ui.actions;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.phoebus.applications.alarm.logging.ui.AlarmLogTable;
import org.phoebus.applications.alarm.logging.ui.AlarmLogTableApp;
import org.phoebus.core.types.ProcessVariable;
import org.phoebus.framework.adapter.AdapterService;
import org.phoebus.framework.selection.Selection;
import org.phoebus.framework.workbench.ApplicationService;
import org.phoebus.ui.spi.ContextMenuEntry;

import javafx.scene.image.Image;

/**
 * A headless context menu entry for creating log entries from adaptable
 * selections. TODO this temporary headless action needs to removed once the
 * create log entry dialog is complete.
 * 
 * @author Kunal Shroff
 *
 */
@SuppressWarnings("rawtypes")
public class ContextMenuPVAlarmHistory implements ContextMenuEntry {

    private static final List<Class> supportedTypes = List.of(ProcessVariable.class);
    private static final String NAME = "Alarm History";

    @Override
    public String getName() {
        return NAME;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object callWithSelection(Selection selection) throws URISyntaxException {

        List<ProcessVariable> selectedPvs = new ArrayList<ProcessVariable>();
        selection.getSelections().stream().forEach(s -> {
            if (s instanceof ProcessVariable) {
                selectedPvs.add((ProcessVariable) s);
            } else {
                AdapterService.getInstance().getAdaptersforAdaptable(s.getClass()).ifPresent(a -> {
                    a.forEach(af -> {
                        af.getAdapter(s, ProcessVariable.class).ifPresent(adapted -> {
                            selectedPvs.add((ProcessVariable) adapted);
                        });
                    });
                });
            }
        });
        AlarmLogTable table = ApplicationService.createInstance(AlarmLogTableApp.NAME);
        URI uri = new URI(AlarmLogTableApp.SUPPORTED_SCHEMA, "", "", "pv="+selectedPvs.stream().map(ProcessVariable::getName).collect(Collectors.joining(",")), "");
        table.setResource(uri);
        
        return null;
    }

    @Override
    public List<Class> getSupportedTypes() {
        return supportedTypes;
    }

    @Override
    public Image getIcon() {
        return AlarmLogTableApp.icon;
    }
}
