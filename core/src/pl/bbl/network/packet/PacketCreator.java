package pl.bbl.network.packet;

import pl.bbl.network.serialization.DataSerialization;

import java.io.Serializable;

/*
 * CanonicalClassName|SerializedData
 */
public class PacketCreator implements Serializable{
    public static final char SEPARATOR = '|';

    public static String createPacket(Class name, Object classObject){
        return prepareHeader(name) + SEPARATOR + DataSerialization.serializeClass(classObject);
    }

    private static String prepareHeader(Class name){
        return name.getCanonicalName();
    }
}
