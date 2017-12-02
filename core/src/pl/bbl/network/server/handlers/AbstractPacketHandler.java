package pl.bbl.network.server.handlers;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import pl.bbl.network.packet.BasicPacket;
import pl.bbl.network.serialization.DataSerialization;

public class AbstractPacketHandler extends ChannelInboundHandlerAdapter{

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf)msg;
        try {
            while (byteBuf.isReadable()) { // (1)
                handlePacket((BasicPacket) DataSerialization.deserialize(byteBuf.array()));
            }
        } finally {
            ReferenceCountUtil.release(msg); // (2)
        }
    }

    protected void handlePacket(BasicPacket basicPacket){}
}
