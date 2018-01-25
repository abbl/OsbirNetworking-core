package pl.bbl.network.packet;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Packet implements Serializable{
    private Map<String, Object> values;
    private String packetType;

    public Packet(String packetType){
        values = new HashMap<>();
        this.packetType = packetType;
    }

    public Packet addData(String key, Object value){
        values.put(key, value);
        return this;
    }

    public Object getData(String key){
        return values.get(key);
    }

    public String getPacketType(){
        return packetType;
    }
}
