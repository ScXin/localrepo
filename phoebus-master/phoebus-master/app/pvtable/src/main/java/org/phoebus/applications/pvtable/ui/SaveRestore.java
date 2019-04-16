package org.phoebus.applications.pvtable.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Callback;
import org.phoebus.applications.pvtable.PVTableApplication;
import org.phoebus.applications.pvtable.model.PVTableModel;
import org.phoebus.applications.pvtable.persistence.PVTablePersistence;
import org.phoebus.applications.pvtable.persistence.PersistByRedis;
import org.phoebus.applications.pvtable.persistence.PersistenceByJson;
import org.phoebus.framework.util.ResourceParser;

import java.io.File;
import java.net.URI;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static javafx.scene.paint.Color.BLUE;
import static javafx.scene.shape.StrokeType.INSIDE;
import static jdk.nashorn.internal.objects.Global.Infinity;

/**
 * @author ScXin
 * @date 1/20/2019 1:13 PM
 * create a class named SaveRestore as user's interface including
 * pvs file sources,snapshot lists,displaying region of snapshots
 */

public class SaveRestore extends AnchorPane {

    //image for the treeView root
    ImageView iv = new ImageView(new Image(getClass().getResourceAsStream("/icons/source.png")));

    //filename as a source of pvGroup configuration
    private String pvGroupName;


    // PVTableModel
    private PVTableModel model;

    // persistence button
    private Button dump;

    //pvTable
    private PVTable table;

    //define a treeview
    private TreeView treeView;

    //snapshot list title
    private Label snapShotListLabel;

    //edge rectange
    private Label edge;


    // define a region of ListView to display snapshot lists
    private ListView snapshot;

    private Label label;

    private Rectangle rectangle;
    //
    private Button back;

    //    public SaveRestore(PVTable table) {
//        this.table = table;
//        table.setLayoutX(281.0);
//        table.setLayoutY(0.0);
//    }
    public SaveRestore() {

    }

    public SaveRestore(PVTableModel model) {
//        model = new PVTableModel();
//        table = new PVTable(model);
//        this = new AnchorPane();


        this.model = model;
        this.table = new PVTable(model);
        this.maxHeight(1000);
        this.maxWidth(1000);
//        this.minHeight(-Infinity);
//        this.minWidth(-Infinity);
        this.minHeight(2000);
        this.minWidth(2000);
        this.prefHeight(1000.0);
        // dialog


        //   Image image = new Image(getClass().getResourceAsStream("/icons/button2.png"),247,50,false,false);
        //snapshot title
        snapShotListLabel = new Label();
        snapShotListLabel.setAlignment(Pos.CENTER);
//        snapShotListLabel.setBackground();
        snapShotListLabel.setStyle("-fx-background-color: #b4b3b1");
        snapShotListLabel.setText("SNAPSHOT LIST");
        snapShotListLabel.setLayoutY(398);
        snapShotListLabel.setPrefHeight(50);
        snapShotListLabel.setPrefWidth(247);
//        snapShotListLabel


        //ListView part
        snapshot = new ListView();
        snapshot.setEditable(true);
        snapshot.setLayoutY(448);//old value 398
//        snapshot.prefHeight(571.0);
        snapshot.prefHeight(541.0);
        snapshot.prefWidth(247.0);

        TableColumn SNAPSHOT = new TableColumn("SNAPSHOT");
        SNAPSHOT.setPrefWidth(247.0);

        /**
         *   create dump button and bind Event
         *   write PvTbableModel into local file
         */
        dump = createDumpButton("dump2.png", "Save snapshot into local file", new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                PersistByRedis.write(pvGroupName, model);

//                PersistenceByJson.write(mysqlTableName,model);
            }


        });
        dump.setLayoutX(284);
        dump.setLayoutY(6);
        dump.prefHeight(50);
        dump.prefWidth(50);


        //rectangle part
        rectangle = new Rectangle();
//        rectangle.prefHeight(973.0);
        rectangle.prefHeight(943.0);
        rectangle.prefWidth(34.0);

        rectangle.setLayoutX(247.0);
        rectangle.setLayoutY(0.0);
        rectangle.setStrokeType(INSIDE);
        rectangle.setStroke(Color.BLACK);
        rectangle.setFill(BLUE);
        table.setLayoutX(281.0);
        table.setLayoutY(0.0);

////        table.maxHeight(2000);
//////      table.maxHeight(2000);
        table.setPrefWidth(973);
//        table.setPrefHeight(850);
        table.setPrefHeight(820);


        edge = new Label();
        edge.setAlignment(Pos.CENTER);
        edge.setStyle("-fx-background-color: #b4b3b1");
        edge.setText("SNAPSHOT ACQUISITION SYSTEM");
        edge.setFont(Font.font("Calibri", FontWeight.BOLD, 18));
//        edge.setTextFill(Color.CYAN);
//        edge.setLayoutY(850);
        edge.setLayoutY(820);
        edge.setPrefHeight(50);
        edge.setPrefWidth(1254);


//        table.minHeight(973);
//        table.minWidth(1000);
//        table.prefHeight(973.0);
//        table.prefWidth(1028.0);
//        table.prefHeight(2000);
//        table.prefWidth(2000);

        this.getChildren().add(snapShotListLabel);
        this.getChildren().add(rectangle);
        this.getChildren().add(snapshot);
        this.getChildren().add(table);
        this.getChildren().add(dump);
        this.getChildren().add(edge);

        buildTree(this);
        treeView.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                Node node = event.getPickResult().getIntersectedNode();
                if (node instanceof Text || (node instanceof TreeCell && ((TreeCell) node).getText() != null)) {
                    File name = (File) ((TreeItem) treeView.getSelectionModel().getSelectedItem()).getValue();
                    /**
                     * get file URI by click to choose a treeItem
                     */

                    /**
                     * parse File path to get the mysqlTableName
                     */
                    File tempFile = new File(name.toString().trim());

                    String fileName = tempFile.getName();

                    String tableName = fileName.split("\\.")[0];
                    pvGroupName = tableName;//this is the table name;
//                    System.out.println(((TreeItem)treeView.getSelectionModel().getSelectedItem()).getValue());
//                    URI file = name.toURI();
//                    model=new PVTableModel();
                    URI input = name.toURI();
//                    PVTableModel modell=changeModel();
                    model.dispose();


                    try {
                        PVTablePersistence.forFilename(input.getPath())
                                .read(model, ResourceParser.getContent(input));
                    } catch (Exception ex) {




                        ex.printStackTrace();
                    }
                    List<String> snapShotList = PersistenceByJson.getSnapShotSet(pvGroupName);


                    ObservableList details = FXCollections.observableArrayList(snapShotList);
//                    ObservableList details=(ObservableList)snapShotList;
                    Collections.sort(details,new Comparator<String>() {
                        @Override
                        public int compare(String o1,String o2) {
                            return o2.compareTo(o1);
                        }
                    });


                            snapshot.setItems(details);

                         snapshot.setStyle("-fx-text-fill:#2e7ccc");


//                    changeModel(model);

//                    try {
//                        BufferedReader in = new BufferedReader(new FileReader("G:\\"+"pvGroups"+name));
//                        String str;
//                        while ((str = in.readLine()) != null) {
//                            System.out.println(str);
//                        }
//                        System.out.println(str);
//                    } catch (IOException e) {
//
//                    }
//                    System.out.println("schema:   " + file.getScheme());
//                    try {
//                        System.out.println(file);
//                        final List<String> pvs = ResourceParser.parsePVs(file);
//                        System.out.println(pvs.size());
//                        if (pvs.size() > 0) {
//
//                            for (String pv : pvs)
//                                System.out.println(pv);
//                        } else {
//                            System.out.println("到这儿了");
//
//                        }
//                    } catch (Exception ex) {
////                            logger.log(Level.WARNING, "PV Table cannot open '" + file + "'", ex);
//                        System.out.println("PV Table cannot open '" + file + "'" + ex);
//                    }

//                    System.out.println(file);

//                    try {
//                        BufferedReader in = new BufferedReader(new FileReader("G:\\"+"pvGroups"+name));
//                        String str;
//                        while ((str = in.readLine()) != null) {
//                            System.out.println(str);
//                        }
//                        System.out.println(str);
//                    } catch (IOException e) {
//
//                    }

//                    List<String> pvs = ResourceParser.parsePVs("G:\\"+"pvGroups"+"name");


//                    ObservableSet<String>snapshots=FXCollections.observableSet(snapShotSet);

//                    TableColumn<String, String> col1 = new TableColumn<String, String>();
//                    snapshot.getColumns().addAll(col1);
//                    snapshot.setItems(details);
//                    col1.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()));
//                    int num=1;
//                   Iterator it=snapShotSet.iterator();
//                      while(it.hasNext())
//                     {
//                           TableCell<String,String>cell=new TableCell<>();
//                            snapshot.getColumns().add(cell);
//                     }
                }
            }
        });
        /**
         * create a listener of snapshot List and bind a event of CLICK
         */
        snapshot.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                String snapShotName = (String) snapshot.getSelectionModel().selectedItemProperty().getValue();


                model.dispose();

//                PersistenceByJson.read(model,snapShotName);
                PersistByRedis.read(model, snapShotName);
//                System.out.println(newModel==null);

//
//                for(PVTableItem item:newModel.getItems())
//                {
//                    System.out.println(item.getName());
//                }

//                System.out.println(snapShotName);
//                try {
//                    File file = new File("G:\\" + "snapshots" + snapShotName + ".txt");
////            BufferedReader in = new BufferedReader(new FileReader("G:\\"+"snapshots"+snapShotName+".txt"));
//                    BufferedReader reader = null;
//                    reader = new BufferedReader(new FileReader(file));
//                    String str;
//                    while ((str = reader.readLine()) != null) {
//                        System.out.println(str);
//
//                    }
//                    System.out.println(str);
//                } catch (IOException e) {
//                }
            }
        });
    }

    // create a method to change the model of table
//
//    public PVTableModel changeModel()
//    {
//        this.table=new PVTable(model);
//        return new PVTableModel();
//    }
    public Button createDumpButton(final String icon, final String tooltip, final EventHandler<ActionEvent> handler) {
        final Button button = new Button();
        button.setGraphic(new ImageView(PVTableApplication.getIcon(icon)));
        button.setTooltip(new Tooltip(tooltip));
        button.setOnAction(handler);
        return button;
    }

    /**
     * create a treeView on a pane
     *
     * @param pane
     */
    public void buildTree(Pane pane) {
        File file = new File("G:/pvGroups");
        treeView = new TreeView<File>(new MyTreeItem(file));
       treeView.setStyle("");
        treeView.setCellFactory(new Callback<TreeView<File>, TreeCell<File>>() {
            public TreeCell<File> call(TreeView<File> arg0) {
                return new TreeCell<File>() {

                    @Override
                    protected void updateItem(File f, boolean empty) {
                        // TODO Auto-generated method stub
                        super.updateItem(f, empty);
                        if (empty) {
                            setText(null);
                            setGraphic(null);
                        } else {

                            setText(f.getName());
                            setFont(Font.font ("Verdana",FontWeight.BOLD, 12));
                            if (f.isDirectory()) {
                                setGraphic(iv);
                            } else if (f.isFile()) {
                                setStyle("-fx-text-fill:#2e7ccc");
                            } else if (f.getName().endsWith("cfg")) {
                                setStyle("-fx-font-weight:BOLD");
                            } else {
                                setStyle("-fx-text-fill:#9fb1cc");
                            }
                        }
                    }
                };
            }
        });
        pane.getChildren().add(treeView);
//       pane.setHgrow(treeView, Priority.ALWAYS);

    }

    // parses the file name and return the file name not including absolutely path

}

/**
 * Using a self-defined class to implement treeItem
 */
class MyTreeItem extends TreeItem<File> {
    ImageView iv = new ImageView(new Image(getClass().getResourceAsStream("/icons/source.png")));
    private boolean notInitialized = true;

    public MyTreeItem(final File file) {
        super(file);
    }

    @Override
    public ObservableList<TreeItem<File>> getChildren() {
        if (notInitialized) {
            notInitialized = false;
            if (getValue().isDirectory()) {
                for (final File file : getValue().listFiles()) {

                    super.getChildren().add(new MyTreeItem(file));
//                    {
//                        super.getChildren().add(new MyTreeItem(file));
//                    }
                }
            }
        }
        return super.getChildren();
    }

    @Override
    public boolean isLeaf() {
        return !getValue().isDirectory();
    }
}

