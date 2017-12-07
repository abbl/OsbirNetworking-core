package pl.bbl.network.packet;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Packet implements Serializable{
    public String packetType;
    public Map<String, Object> values;

    public Packet(String packetType){
        this.packetType = packetType;
        values = new HashMap<>();
    }

    public void addData(String key, Object value){
        values.put(key, value);
    }

    public Object getData(String key){
        return values.get(key);
    }
}
