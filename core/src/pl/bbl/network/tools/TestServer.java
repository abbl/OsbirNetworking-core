package pl.bbl.network.tools;

import io.netty.channel.ChannelPipeline;
import pl.bbl.network.server.AbstractServer;
import pl.bbl.network.server.connection.AbstractUser;

public class TestServer extends AbstractServer {
    public TestServer(int port, AbstractUser abstractUser) {
        super(port, abstractUser);
    }

    @Override
    protected AbstractUser addHandlersToChannel(ChannelPipeline pipeline) {
        AbstractUser abstractUser = super.addHandlersToChannel(pipeline);
        return abstractUser;
    }
}
