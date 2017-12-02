package pl.bbl.network.serialization;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class DataSerialization {

    public static byte[] serialize(Object object){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            ObjectOutputStream out = new ObjectOutputStream(byteArrayOutputStream);
            out.writeObject(object);
            out.close();
            byteArrayOutputStream.close();
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Object deserialize(byte[] bytes){
        Object object;
        try {
            ObjectInputStream input = new ObjectInputStream(new ByteArrayInputStream(bytes));
            object = input.readObject();
            input.close();
            return object;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String convertByteArrayToString(ByteArrayOutputStream byteArrayOutputStream){
        try {
            return byteArrayOutputStream.toString(StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
