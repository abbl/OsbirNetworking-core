package pl.bbl.network.server.handlers;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import pl.bbl.network.packet.Packet;
import java.util.ArrayList;

public class PacketHandler extends ChannelInboundHandlerAdapter{
    private ArrayList<PacketReceiver> receivers;

    public PacketHandler(){
        receivers = new ArrayList<>();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        passPacketToReceivers((Packet)msg);
    }

    private void passPacketToReceivers(Packet packet){
        for(PacketReceiver packetReceiver : receivers){
            if(packetReceiver.isReceiverTypeEqual(packet.packetType))
                if(packetReceiver.receive(packet))
                    return;
        }
    }

    public void addReceiver(PacketReceiver packetReceiver){
        receivers.add(packetReceiver);
    }

    public void removeReceiver(PacketReceiver packetReceiver){
        receivers.remove(packetReceiver);
    }
}
