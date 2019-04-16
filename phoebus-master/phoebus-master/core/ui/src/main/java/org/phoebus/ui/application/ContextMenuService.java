package org.phoebus.ui.application;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ServiceLoader;
import java.util.ServiceLoader.Provider;
import java.util.stream.Collectors;

import org.phoebus.framework.adapter.AdapterService;
import org.phoebus.framework.selection.SelectionService;
import org.phoebus.ui.spi.ContextMenuEntry;

@SuppressWarnings("rawtypes")
public class ContextMenuService {

    private static ContextMenuService contextMenuService;
    private static ServiceLoader<ContextMenuEntry> loader;
    private List<ContextMenuEntry> contextMenuEntries;

    private ContextMenuService() {
        loader = ServiceLoader.load(ContextMenuEntry.class);
        contextMenuEntries = Collections
                .unmodifiableList(loader.stream().map(Provider::get).collect(Collectors.toList()));
    }

    public static synchronized ContextMenuService getInstance() {
        if (contextMenuService == null) {
            contextMenuService = new ContextMenuService();
        }
        return contextMenuService;
    }

    /**
     * Get the list of registered context menu providers
     *
     * @return
     */
    public List<ContextMenuEntry> listContextMenuEntries() {
        return contextMenuEntries;
    }

    /**
     *
     * @param selectionType
     * @return
     */
    public List<ContextMenuEntry> listSupportedContextMenuEntries() {
        // List of types of the current selection
        List<Class> selectionTypes = SelectionService.getInstance().getSelection().getSelections().stream().map(s -> {
            return s.getClass();
        }).collect(Collectors.toList());

        // Take into account the types the selected objects can be converted into
        List<Class> allAdaptableSelectionType = new ArrayList<>();
        selectionTypes.forEach(s -> {
            // Class can certainly be converted to the class itself,
            // but also to all its super classes
            Class sc = s;
            do
            {
                // System.out.println("Selection is of type " + sc);
                allAdaptableSelectionType.add(sc);
                for (Class inter : sc.getInterfaces())
                {
                    // System.out.println(".. and interface " + inter);
                    allAdaptableSelectionType.add(inter);
                }
                sc = sc.getSuperclass();
            }
            while (sc != Object.class);

            AdapterService.getInstance().getAdaptersforAdaptable(s).ifPresent(a -> {
                a.forEach(f -> {
                    allAdaptableSelectionType.addAll(f.getAdapterList());
                });
            });
        });

        //
        final List<ContextMenuEntry> result = contextMenuEntries
            .stream()
            .filter(p -> {
                  return !Collections.disjoint(p.getSupportedTypes(), allAdaptableSelectionType);
            })
            .collect(Collectors.toList());
        result.sort((a, b) -> a.getName().compareTo(b.getName()));
        return result;
    }
}
