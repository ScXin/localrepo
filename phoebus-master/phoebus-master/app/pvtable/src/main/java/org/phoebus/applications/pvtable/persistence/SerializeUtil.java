package org.phoebus.applications.pvtable.persistence;

import java.io.*;

/**
 * @author ScXin
 * @date 1/28/2019 2:15 PM
 */
public class SerializeUtil {
    public static byte[] serialize(Object model) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream out = null;

        try {
            out = new ObjectOutputStream(baos);
            out.writeObject(model);
            return baos.toByteArray();

        } catch (IOException ex) {
            System.out.println("the Object can not be null");
        } finally {
            try {
                out.close();
                baos.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                System.out.println(e.getMessage());
            }
        }
        return null;
    }

    /**
     * deserialize file Bytes from redis into File Object by click snapshotName
     *
     * @param snapShotByte
     * @return
     */
    public static Object deserialize(byte[] snapShotByte) {

        Object obj = null;
        ByteArrayInputStream in = new ByteArrayInputStream(snapShotByte);
        try {
            ObjectInputStream objIn = new ObjectInputStream(in);

            return objIn.readObject();  //readObject will have an Exception,and return null

//            System.out.println("objModel.getItems.size()==="+obj.getItems().size());

        } catch (IOException e) {
            // TODO Auto-generated catch block
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            System.out.println(e.getMessage());
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                System.out.println(e.getMessage());
            }
        }
        return null;
    }
}
