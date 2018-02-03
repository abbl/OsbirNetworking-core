package pl.bbl.network.client;

import io.netty.channel.Channel;
import pl.bbl.network.packet.Packet;

/**
 * Its used to encapsulate Channel variable to protect it from not allowed operations.
 */
public class ClientConnection {
    private Channel channel;

    public ClientConnection(Channel channel){
        this.channel = channel;
    }

    public void sendPacket(Packet packet){
        channel.writeAndFlush(packet);
    }
}
