package pl.bbl.network.server.connection;

import io.netty.channel.ChannelHandlerContext;
import pl.bbl.network.packet.BasicPacket;
import java.nio.ByteBuffer;

public abstract class AbstractUser {
    private ChannelHandlerContext channelHandlerContext;
    private String key;

    public AbstractUser(ChannelHandlerContext channelHandlerContext){
        this.channelHandlerContext = channelHandlerContext;
    }

    public AbstractUser(ChannelHandlerContext channelHandlerContext, String key){
        this.channelHandlerContext = channelHandlerContext;
        this.key = key;
    }

    public void sendPacket(BasicPacket packet){
        channelHandlerContext.write(ByteBuffer.wrap(packet.serializeThis().packetData));
    }

    public void close(){
        channelHandlerContext.close();
    }

    public boolean isKeyTheSame(String key){
        return this.key.equals(key);
    }
}
