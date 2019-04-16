package org.phoebus.applications.filebrowser;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import org.phoebus.ui.javafx.TreeHelper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

class FileTreeItem extends TreeItem<File> {

    private final DirectoryMonitor monitor;
    private AtomicBoolean isFirstTimeLeaf = new AtomicBoolean(true);
    private boolean isFirstTimeChildren = true;
    private volatile boolean isLeaf;

    public FileTreeItem(final DirectoryMonitor monitor, final File childFile) {
        super(childFile);
        this.monitor = monitor;
    }

    DirectoryMonitor getMonitor()
    {
        return monitor;
    }

    /** Reset so next time item is drawn, it fetches file system information */
    public void forceRefresh()
    {
        isFirstTimeLeaf.set(true);
        isFirstTimeChildren = true;

        TreeHelper.triggerTreeItemRefresh(this);
        setExpanded(false);
        setExpanded(true);
    }

    /** @param siblings List of FileTreeItem to sort by file name */
    static void sortSiblings(final List<TreeItem<File>> siblings)
    {
        siblings.sort((a, b) -> a.getValue().getName().compareTo(b.getValue().getName()));
    }

    @Override
    public ObservableList<TreeItem<File>> getChildren() {

        if (isFirstTimeChildren) {
            isFirstTimeChildren = false;
            super.getChildren().setAll(buildChildren(this));
        }
        return super.getChildren();
    }

    /** Check if item refers to a file.
     *
     *  <p>Note that calling this is more effective than
     *  continuing to call getValue().isFile(),
     *  because the check is only performed once
     */
    @Override
    public boolean isLeaf()
    {
        if (isFirstTimeLeaf.getAndSet(false))
        {
            final File f = getValue();
            isLeaf = f.isFile();
        }
        return isLeaf;
    }

    private ObservableList<TreeItem<File>> buildChildren(TreeItem<File> TreeItem) {
        File f = TreeItem.getValue();
        if (f != null && f.isDirectory()) {
            File[] files = f.listFiles();
            if (files != null) {
                Arrays.sort(files, (a, b) -> a.getName().compareTo(b.getName()));
                ObservableList<TreeItem<File>> children = FXCollections.observableArrayList();

                for (File childFile : files) {
                    // Keep hidden files hidden?
                    if (childFile.isHidden()  &&  !FileBrowserApp.show_hidden)
                        continue;
                    children.add(new FileTreeItem(monitor, childFile));
                }
                // Have current set of files, monitor from now on
                monitor.monitor(f);
                return children;
            }
            // No files, yet, but monitor directory to learn about additions
            monitor.monitor(f);
        }

        return FXCollections.emptyObservableList();
    }
}