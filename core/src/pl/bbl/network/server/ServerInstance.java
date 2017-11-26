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
import pl.bbl.network.server.connection.AbstractUser;
import pl.bbl.network.server.factory.UserFactory;
import pl.bbl.network.server.handlers.AbstractHandler;
import pl.bbl.network.server.hive.UserHive;

import java.util.ArrayList;

public class ServerInstance {
    private ArrayList<AbstractHandler> handlers;
    private ChannelGroup channelGroup;
    private int port;

    private UserHive userHive;

    /**
     * Initialize server without user feature.
     * @param port
     */
    public ServerInstance(int port){
        this.port = port;
        handlers = new ArrayList<>();
        channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    }

    /**
     * Initialize server with user feature.
     * @param port
     * @param abstractUser a copy of class which extends AbstractUser.
     */
    public ServerInstance(int port, AbstractUser abstractUser){
        this(port);
        userHive = new UserHive(abstractUser);
    }

    public void addHandlers(ArrayList<AbstractHandler> handlers){
         this.handlers.addAll(handlers);
    }


    private void addHandlersToChannel(){

    }

    public void run() throws Exception {
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

                            if(isUserHiveAvailable())
                                userHive.createUser(socketChannel);
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

    public boolean isUserHiveAvailable(){
        return userHive != null;
    }
}
