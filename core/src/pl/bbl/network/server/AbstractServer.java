package pl.bbl.network.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import pl.bbl.network.server.connection.AbstractUser;
import pl.bbl.network.server.hive.UserHive;

public abstract class AbstractServer implements Runnable{
    protected UserHive userHive;
    private int port;

    public AbstractServer(int port){
        this.port = port;
    }

    public AbstractServer(int port, Class className){
        this(port);
        userHive = new UserHive(className);
    }

    public void run() {
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
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            connectionReceiver.shutdownGracefully();
            connectionHandler.shutdownGracefully();
        }
    }

    protected AbstractUser addHandlersToChannel(ChannelPipeline pipeline){
        AbstractUser abstractUser = userHive.createUser(pipeline.channel());
        pipeline.addLast(new ObjectEncoder(),
                new ObjectDecoder(ClassResolvers.cacheDisabled(null)));
        return abstractUser;
    }

    public boolean isUserHiveAvailable(){
        return userHive != null;
    }
}
