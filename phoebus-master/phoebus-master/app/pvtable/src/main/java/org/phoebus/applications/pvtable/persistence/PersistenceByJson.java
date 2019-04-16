package org.phoebus.applications.pvtable.persistence;

/**
 * @author ScXin
 * @date 1/26/2019 11:13 AM
 */

import java.io.*;
import java.sql.SQLOutput;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Stream;

import com.alibaba.fastjson.JSON;
import javafx.scene.control.ListView;
import org.phoebus.applications.pvtable.model.PVTableItem;
import org.phoebus.applications.pvtable.model.PVTableModel;
import org.phoebus.applications.pvtable.ui.PVTable;
import org.phoebus.pv.PV;
import redis.clients.jedis.Jedis;

public class PersistenceByJson {

    private final static Jedis jedis = new Jedis("localhost");
    //static final ListView<String> snapShotSet=new ListView<>();
    private static List<String> snapShotList = new ArrayList<>();

    public static void write(String keyName, PVTableModel model) {
        Date dNow = new Date( );
//        String snapshotName = keyName + " " + new Date();
        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd hh:mm:ss");
        String snapshotName=keyName+" "+ft.format(dNow);
//        String snapshot= JSON.toJSONString(model);
        jedis.sadd(keyName, snapshotName);
//        jedis.set(snapshotName,snapshot);
        for (PVTableItem item : model.getItems()) {
            String snapshotNow = JSON.toJSONString(item);
            jedis.lpush(snapshotName, snapshotNow);
        }
    }

    /**
     * display the snapshot by click the snapshot
     *
     * @param snapshotName
     * @return
     */
    public static void read(PVTableModel model, String snapshotName) {
        System.out.println(snapshotName);
        model.dispose();
        int length = jedis.llen(snapshotName).intValue();
        System.out.println(length);
        for (int i = 0; i < length; i++) {
            PVTableItem itemll = JSON.parseObject(jedis.lindex(snapshotName, i), PVTableItem.class);
            System.out.println(itemll);
            model.addItem(itemll);
        }
//         model=JSON.parseObject(jedis.get(snapshotName),PVTableModel.class);
        System.out.println(model.getItems() == null);
    }

    /**
     * get the snapshotSet by the click treeviewItem
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

