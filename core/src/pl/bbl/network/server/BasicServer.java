package pl.bbl.network.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.GlobalEventExecutor;
import pl.bbl.network.server.connection.ConnectedUser;
import pl.bbl.network.server.handlers.AbstractHandler;

import java.util.ArrayList;

public class BasicServer {
    private ArrayList<AbstractHandler> handlers;
    private ArrayList<ConnectedUser>
    private ChannelGroup channelGroup;
    private int port;

    public BasicServer(int port){
        this.port = port;
        handlers = new ArrayList<>();
        channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    }

    public void addHandlers(ArrayList<AbstractHandler> handlers){
         this.handlers.addAll(handlers);
    }

    public void run() throws Exception {
        //Receive new connections and handles them to connectionHandler
        EventLoopGroup connectionReceiver = new NioEventLoopGroup();
        EventLoopGroup connectionHandler = new NioEventLoopGroup();

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(connectionReceiver, connectionHandler)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            channelGroup.add(socketChannel);
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            ChannelFuture channelFuture = bootstrap.bind(port).sync();

            channelFuture.channel().closeFuture().sync();
        } finally {
            connectionReceiver.shutdownGracefully();
            connectionHandler.shutdownGracefully();
        }
    }

    private void addHandlersToChannel(){
        for()
    }
}
