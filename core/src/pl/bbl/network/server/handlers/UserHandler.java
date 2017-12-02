package pl.bbl.network.server.handlers;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import pl.bbl.network.server.hive.UserHive;

/**
 * This handler add user to UserHive.
 */
public class UserHandler extends ChannelInboundHandlerAdapter {
    private UserHive userHive;

    public UserHandler(UserHive userHive){
        this.userHive = userHive;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        userHive.createUser(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        userHive.removeUser(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
