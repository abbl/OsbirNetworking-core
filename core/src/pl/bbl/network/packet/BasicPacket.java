package pl.bbl.network.packet;

import pl.bbl.network.serialization.DataSerialization;

import javax.xml.crypto.Data;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class BasicPacket implements Serializable{
    public String packetType;
    public Map<String, Object> values;

    public BasicPacket(String packetType){
        this.packetType = packetType;
        values = new HashMap<>();
    }

    public void addData(String key, Object value){
        values.put(key, value);
    }

    public Object getData(String key){
        return values.get(key);
    }

    public byte[] serializeThis(){
        return DataSerialization.serialize(this);
    }

}
