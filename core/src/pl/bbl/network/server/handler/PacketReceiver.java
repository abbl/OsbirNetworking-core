package pl.bbl.network.server.handler;

import pl.bbl.network.client.ClientConnection;
import pl.bbl.network.packet.Packet;
import pl.bbl.network.server.connection.AbstractUser;

/**
 * Just override one of receive methods to handle packets.
 * Handling depends on which side of connection you're working with either client or server.
 */
public abstract class PacketReceiver {
    private String receiverName;

    public PacketReceiver(String receiverName){
        this.receiverName = receiverName;
    }

    public void receivePacket(Packet packet, ClientConnection clientConnection){}

    public void receivePacket(Packet packet, AbstractUser abstractUser){}

    public String getReceiverName(){
        return receiverName;
    }
}
