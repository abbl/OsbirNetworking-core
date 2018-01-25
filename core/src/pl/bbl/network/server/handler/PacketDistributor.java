package pl.bbl.network.server.handler;

import pl.bbl.network.client.ClientConnection;
import pl.bbl.network.packet.Packet;
import pl.bbl.network.server.connection.AbstractUser;
import pl.bbl.network.tools.LogType;
import pl.bbl.network.tools.NetworkLogger;

import java.util.ArrayList;

public class PacketDistributor {
    private ArrayList<PacketReceiver> packetReceiverArrayList;

    public PacketDistributor(){
        packetReceiverArrayList = new ArrayList<>();
    }

    public void registerPacketReceiver(PacketReceiver packetReceiver){
        NetworkLogger.log(LogType.DEBUG, "PacketReceiver " + packetReceiver.getReceiverName() + " has been registered.");
        packetReceiverArrayList.add(packetReceiver);
    }

    public void unregisterPacketReceiver(PacketReceiver packetReceiver){
        NetworkLogger.log(LogType.DEBUG, "PacketReceiver " + packetReceiver.getReceiverName() + " has been unregistered.");
        packetReceiverArrayList.remove(packetReceiver);
    }

    public void distributePacket(Packet packet, ClientConnection clientConnection) {
        for(PacketReceiver packetReceiver : packetReceiverArrayList){
            packetReceiver.receivePacket(packet, clientConnection);
        }
    }

    public void distributePacket(Packet packet, AbstractUser abstractUser){
        for(PacketReceiver packetReceiver : packetReceiverArrayList){
            packetReceiver.receivePacket(packet, abstractUser);
        }
    }
}
