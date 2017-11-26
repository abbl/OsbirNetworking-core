package pl.bbl.network.serialization;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

public class DataSerialization {

    public static byte[] serializeClass(Object classData){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            ObjectOutputStream out = new ObjectOutputStream(byteArrayOutputStream);
            out.writeObject(classData);
            out.close();
            byteArrayOutputStream.close();
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
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
