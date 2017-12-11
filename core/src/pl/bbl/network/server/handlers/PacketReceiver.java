package pl.bbl.network.server.handlers;

import pl.bbl.network.packet.Packet;

public abstract class PacketReceiver {
    private String receiverType;

    protected PacketReceiver(String receiverType){
        this.receiverType = receiverType;
    }

    public boolean isReceiverTypeEqual(String otherReceiverType){
        return receiverType.equals(otherReceiverType);
    }
    public abstract boolean receive(Packet packet);
}
