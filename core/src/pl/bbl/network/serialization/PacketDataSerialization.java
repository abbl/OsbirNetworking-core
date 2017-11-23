package pl.bbl.network.serialization;
/*
 * In this project I made a concept of packet which contains header with information about serialized data.
 * Something like this:
 *
 * ClassName|SerializedData
 */
public class PacketDataSerialization {

    private static final char SEPARATOR = '|';

    public static String getSerializedPacket(Class className, Object classData){
        return createPacket(className, classData);
    }

    private static String createPacket(Class className, Object classData){
        return joinHeaderWithObjectData(className, serializeClass(className, classData));
    }

    private static String serializeClass(Class className, Object classData){

        return null;
    }

    private static String joinHeaderWithObjectData(Class className, String serializedData){
        return preparePacketHeader() + SEPARATOR + serializeClass(className, serializedData);
    }
    
    private static String preparePacketHeader(Class){

    }

}
