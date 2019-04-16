package org.phoebus.applications.pvtable.persistence;
/**
 * @author ScXin
 * @date 1/20/2019 1:26 PM
 */
/****************************************************************************************
 *  this is the Prisistence module,which takes responsible for wirting the snapshots
 *  into redis DB and reading snapshot from redis.it is concerned that the PvTableModel
 *  will be persisted by the local file
 ****************************************************************************************/

import java.io.*;

import java.net.URI;

import java.text.SimpleDateFormat;
import java.util.*;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import org.phoebus.applications.pvtable.model.PVTableModel;

import org.phoebus.framework.util.ResourceParser;

import redis.clients.jedis.Jedis;

public class PersistByRedis {
    private final static Jedis jedis = new Jedis("localhost");

    //static final ListView<String> snapShotSet=new ListView<>();

    private static List<String> snapShotList = new ArrayList<>();

    /**
     * Srialize PvTableModel into Byte Array
     * @param model
     * @return
     */

    /************************************************************************************/
//    public void write(ListView<String> list,final OutputStream stream)
//    {
//        final PrintWriter out = new PrintWriter(stream);
//        out.println("this is the test");
//        String snapShotName=list.getSelectionModel().selectedItemProperty().getValue();
//
//    }

    /**
     * changed the file name for the Window System
     *
     * @param SnapshotName
     * @return
     */
    public static String parseSnapshotName(String SnapshotName) {
        char arr[] = SnapshotName.toCharArray();
        for (int i = -0; i < arr.length; i++) {
            if (arr[i] == ':') {
                arr[i] = '-';
            }
        }
        SnapshotName = new String(arr);
        return SnapshotName;
    }
    /*************************************************************************************/

    /**
     * write serialized PVTableModel into a file under the path of "G:\snapshots\"
     *
     * @param keyName
     * @param model
     */
    public static void write(String keyName, PVTableModel model) {

//        byte[] modelByte = serialize(model);
        Date dNow = new Date( );
        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd hh:mm:ss");
        String snapshotName = keyName + " " + ft.format(dNow);
        String snapshotName2 = parseSnapshotName(snapshotName);
        jedis.sadd(keyName, snapshotName2);
//        jedis.set(snapshotName.getBytes(), modelByte);
        String snapshotspath = "G:\\snapshots\\"+keyName;
        File file = new File(snapshotspath, snapshotName2 + ".sav");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        try {
            OutputStream ops = new FileOutputStream(file);
            URI path = file.toURI();
            PVTablePersistence.forFilename(path.getPath()).write(model, ops);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        jedis.set(snapshotName2.getBytes(), SerializeUtil.serialize(file));
    }
    /**
     * display the snapshot by click the snapshotName in the TableViewItem of SNAPSHOT LIST
     *
     * @param snapshotName
     * @return
     */
    public static void read(PVTableModel model, String snapshotName) {
        File snapshotFile = (File) SerializeUtil.deserialize(jedis.get(snapshotName.getBytes()));
//        System.out.println("snapshot===" + snapshot.length);
////         deserialize(snapshot);/
        try {
            URI snapshotUri = snapshotFile.toURI();
            PVTablePersistence.forFilename(snapshotUri.getPath())
                    .read(model, ResourceParser.getContent(snapshotUri));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    /**
     * get the snapshot Sets by click treeviewItem
     *
     * @param filename
     * @return
     */
    public static List<String> getSnapShotSet(String filename) {
//        long length=jedis.scard(filename);
//        snapShotList=jedis.lrange(filename,0,length);
        Set<String> snapshotSet = jedis.smembers(filename);
        snapShotList = new ArrayList<>(snapshotSet);
        return snapShotList;
    }
}