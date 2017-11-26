package pl.bbl.network.packet;

import pl.bbl.network.tools.DataTypes;

public abstract class AbstractPacket<T> {
    public String packetType;
    public T[] data;

    public AbstractPacket(String packetType){
        this.packetType = packetType;
    }

    public void addData(DataTypes dataType, T data){

    }

    public PacketReadyToSend serializeThis(){

    }
}
