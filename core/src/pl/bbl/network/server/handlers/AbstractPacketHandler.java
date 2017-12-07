package pl.bbl.network.server.handlers;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import pl.bbl.network.packet.Packet;

public class AbstractPacketHandler extends ChannelInboundHandlerAdapter{

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        handlePacket((Packet)msg);
    }

    protected void handlePacket(Packet packet){}
}
