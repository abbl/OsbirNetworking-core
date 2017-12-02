package pl.bbl.network.server.connection;

import io.netty.channel.ChannelHandlerContext;
import pl.bbl.network.packet.BasicPacket;
import java.nio.ByteBuffer;

public class AbstractUser {
    private ChannelHandlerContext channelHandlerContext;
    private boolean isAuthenticated;

    public AbstractUser(){}

    public AbstractUser(ChannelHandlerContext channelHandlerContext){
        this.channelHandlerContext = channelHandlerContext;
    }

    public void sendPacket(BasicPacket packet){
        channelHandlerContext.write(ByteBuffer.wrap(packet.serializeThis()));
    }

    public void close(){
        channelHandlerContext.close();
    }

    public boolean isContextHandlerEqual(ChannelHandlerContext channelHandlerContext){
        return this.channelHandlerContext.equals(channelHandlerContext);
    }

    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        isAuthenticated = authenticated;
    }

    public ChannelHandlerContext getContextHandler(){
        return channelHandlerContext;
    }
}
