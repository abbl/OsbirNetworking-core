package pl.bbl.network.server.connection;

import io.netty.channel.Channel;
import pl.bbl.network.packet.Packet;
import pl.bbl.network.server.AbstractServer;

public abstract class AbstractUser {
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

    public void disconnect(){
        channel.close();
    }

    public PacketReceiver getPacketReceiver(String receiverType){
        return retrievePacketHandlerFromPipeline().getReceiver(receiverType);
    }

    public PacketHandler retrievePacketHandlerFromPipeline(){
        return (PacketHandler) channel.pipeline().get(AbstractServer.PACKET_HANDLER_NAME);
    }

    public String getId(){
        return id;
    }

    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        isAuthenticated = authenticated;
    }
}
