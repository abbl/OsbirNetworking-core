package pl.bbl.network.server.connection;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.SocketChannel;
import pl.bbl.network.packet.Packet;
import java.nio.ByteBuffer;

public class AbstractUser {
    private Channel channel;
    private String id;
    private boolean isAuthenticated;

    public AbstractUser(String id, Channel channel){
        this.id = id;
        this.channel = channel;
    }

    public void sendPacket(Packet packet){
        channel.writeAndFlush(packet);
    }

    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        isAuthenticated = authenticated;
    }

    public String getId(){
        return id;
    }
}
