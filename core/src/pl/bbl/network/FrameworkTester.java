package pl.bbl.network;

import pl.bbl.network.serialization.DataSerialization;

public class FrameworkTester {
    public static void main(String[] args){
        System.out.println("Test:" + DataSerialization.serializeClass(new PacketCreator()));
    }
}
