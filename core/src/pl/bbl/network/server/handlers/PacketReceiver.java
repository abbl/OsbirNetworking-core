package pl.bbl.network.server.handlers;

import pl.bbl.network.client.AbstractClient;
import pl.bbl.network.packet.Packet;
import pl.bbl.network.server.connection.AbstractUser;
import pl.bbl.network.tools.NetworkLogger;

public abstract class PacketReceiver {
    protected AbstractClient abstractClient;
    protected AbstractUser abstractUser;
    protected String receiverType;

    protected PacketReceiver(String receiverType){
        this.receiverType = receiverType;
    }

    protected PacketReceiver(String receiverType, AbstractClient abstractClient){
        this(receiverType);
        this.abstractClient = abstractClient;
    }

    protected PacketReceiver(String receiverType, AbstractUser abstractUser){
        this(receiverType);
        this.abstractUser = abstractUser;
    }

    public boolean isReceiverTypeEqual(String otherReceiverType){
        return receiverType.equals(otherReceiverType);
    }

    protected void sendPacket(Packet packet){
        if(abstractClient != null)
            abstractClient.write(packet);
        else if(abstractUser != null)
            abstractUser.sendPacket(packet);
        else
            NetworkLogger.log("Stopped a attempt to send a packet in PacketReceiver because AbstractClient or AbstractUser is equal null.");
    }

    public abstract boolean receive(Packet packet);
}
