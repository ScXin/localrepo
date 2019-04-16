package org.phoebus.applications.pvtable.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.phoebus.applications.pvtable.model.PVTableModel;
import org.phoebus.applications.pvtable.persistence.PVTablePersistence;
import org.phoebus.framework.util.ResourceParser;
import org.phoebus.applications.pvtable.persistence.PVTableAutosavePersistence;
import javax.swing.text.TabExpander;
import java.io.File;
import java.io.FileInputStream;

import java.io.InputStream;
import java.net.URI;
import java.util.List;

/**
 * @author ScXin
 * @date 1/22/2019 3:41 PM
 */
public class Demo extends Application {


    private SaveRestore saveRestore;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        PVTableModel model = new PVTableModel();
//        try {
//            File file = new File("G:\\pvGroups\\PS.sav");
//            URI input = file.toURI();
//            PVTablePersistence.forFilename(input.getPath())
//                    .read(model, ResourceParser.getContent(input));
//        }catch(Exception ex)
//        {
//            ex.printStackTrace();
//        }
//        PVTable table = new PVTable(model);
//        saveRestore = new SaveRestore(table);
        saveRestore = new SaveRestore(model);

        Scene scene = new Scene(saveRestore);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}