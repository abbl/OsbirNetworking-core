package pl.bbl.network.server.connection;

import pl.bbl.network.packet.BasicPacket;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public abstract class AbstractUser {
    private SocketChannel socketChannel;
    private String key;

    public AbstractUser(SocketChannel socketChannel){
        this.socketChannel = socketChannel;
    }

    public AbstractUser(SocketChannel socketChannel, String key){
        this.socketChannel = socketChannel;
        this.key = key;
    }

    public void sendPacket(BasicPacket packet){
        try {
            socketChannel.write(ByteBuffer.wrap(packet.serializeThis().packetData));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close(){
        try {
            socketChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isKeyTheSame(String key){
        return this.key.equals(key);
    }
}
