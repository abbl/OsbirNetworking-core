package pl.bbl.network.server.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import pl.bbl.network.client.ClientConnection;
import pl.bbl.network.packet.Packet;
import pl.bbl.network.server.Server;
import pl.bbl.network.server.connection.AbstractUser;
import pl.bbl.network.tools.LogType;
import pl.bbl.network.tools.NetworkLogger;

public class PacketHandler extends ChannelInboundHandlerAdapter{
    private PacketDistributor packetDistributor;
    private ClientConnection clientConnection;
    private Server server;

    public PacketHandler(PacketDistributor packetDistributor){
        this.packetDistributor = packetDistributor;
    }

    public PacketHandler(PacketDistributor packetDistributor, Server server){
        this(packetDistributor);
        this.server = server;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        passPacket(ctx, msg);
    }

    private void passPacket(ChannelHandlerContext ctx, Object msg) {
        NetworkLogger.log(LogType.DEBUG, "Received packet : [" + ((Packet)msg).getPacketType() + "]");
        if(server != null){ // Passes packet to server type receiver
            AbstractUser abstractUser = getUser(ctx.channel());
            if(abstractUser != null){
                packetDistributor.distributePacket((Packet)msg, abstractUser);
            }else{
                NetworkLogger.log(LogType.DEBUG,"Packet wasn't passed to receiver because it channel doesn't belong to any user.");
            }
        }else{ // Passes packet to client type receiver.
            updateClientConnection(ctx.channel());
            packetDistributor.distributePacket((Packet)msg, clientConnection);
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        if(server != null){
            server.removeUserWithoutDisconnecting(ctx.channel());
        }
    }

    private void updateClientConnection(Channel channel){
        if(clientConnection == null)
            clientConnection = new ClientConnection(channel);
    }

    private AbstractUser getUser(Channel channel){
        return server.getUser(channel);
    }
}
