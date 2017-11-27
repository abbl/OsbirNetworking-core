package pl.bbl.network.server.handlers;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import pl.bbl.network.server.hive.UserHive;

public abstract class AbstractUserHandler extends ChannelInboundHandlerAdapter {
    private UserHive userHive;

    public AbstractUserHandler(UserHive userHive){
        this.userHive = userHive;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
