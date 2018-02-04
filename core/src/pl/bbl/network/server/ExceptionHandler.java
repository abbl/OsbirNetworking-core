package pl.bbl.network.server;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import pl.bbl.network.tools.LogType;
import pl.bbl.network.tools.NetworkLogger;

public class ExceptionHandler extends ChannelDuplexHandler{
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        NetworkLogger.log(LogType.DEBUG, "Received exception:" + cause.getMessage());
    }
}
