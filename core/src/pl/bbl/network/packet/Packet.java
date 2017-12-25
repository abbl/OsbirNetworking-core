package pl.bbl.network.packet;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Packet implements Serializable{
    private Map<String, Object> values;
    public String packetType;
    public String packetPurpose;

    public Packet(String packetType, String packetPurpose){
        values = new HashMap<>();
        this.packetType = packetType;
        this.packetPurpose = packetPurpose;
    }

    public Packet addData(String key, Object value){
        values.put(key, value);
        return this;
    }

    public Object getData(String key){
        return values.get(key);
    }
}
