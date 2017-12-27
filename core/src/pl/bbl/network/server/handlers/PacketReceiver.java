package pl.bbl.network.server.handlers;

import pl.bbl.network.client.AbstractClient;
import pl.bbl.network.packet.Packet;
import pl.bbl.network.tools.NetworkLogger;

public abstract class PacketReceiver {
    private String receiverType;
    private AbstractClient abstractClient;

    protected PacketReceiver(String receiverType){
        this.receiverType = receiverType;
    }

    protected PacketReceiver(String receiverType, AbstractClient abstractClient){
        this(receiverType);
        this.abstractClient = abstractClient;
    }

    public boolean isReceiverTypeEqual(String otherReceiverType){
        return receiverType.equals(otherReceiverType);
    }
    public abstract boolean receive(Packet packet);

    protected void sendPacket(Packet packet){
        if(abstractClient != null)
            abstractClient.write(packet);
        else
            NetworkLogger.log("Stopped a attempt to send a packet in PacketReceiver because AbstractClient is equal null.");
    }
}
