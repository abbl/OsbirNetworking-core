package pl.bbl.network.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import pl.bbl.network.server.connection.AbstractUser;
import pl.bbl.network.server.hive.UserHive;

public abstract class BasicServer {
    private UserHive userHive;
    private int port;

    public BasicServer(int port){
        this.port = port;
    }

    public BasicServer(int port, AbstractUser abstractUser){
        this(port);
        userHive = new UserHive(abstractUser);
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
                            addHandlersToChannel(socketChannel.pipeline());
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

    protected void addHandlersToChannel(ChannelPipeline pipeline){}

    public boolean isUserHiveAvailable(){
        return userHive != null;
    }
}
