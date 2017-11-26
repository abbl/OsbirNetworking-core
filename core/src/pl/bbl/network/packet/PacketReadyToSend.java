package pl.bbl.network.packet;

public class PacketReadyToSend {
    public byte[] packetData;

    public PacketReadyToSend(byte[] packetData){
        this.packetData = packetData;
    }
}
