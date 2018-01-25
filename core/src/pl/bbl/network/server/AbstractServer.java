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
import pl.bbl.network.server.handler.PacketDistributor;
import pl.bbl.network.server.handler.PacketHandler;
import pl.bbl.network.server.hive.UserHive;

public class AbstractServer implements Runnable{
    protected UserHive userHive;
    private PacketDistributor packetDistributor;
    private int port;

    public AbstractServer(int port, Class className, PacketDistributor packetDistributor){
        this.port = port;
        this.userHive = new UserHive(className);
        this.packetDistributor = packetDistributor;
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

    private void addHandlersToChannel(ChannelPipeline pipeline){
        userHive.createUser(pipeline.channel());
        pipeline.addLast(new ObjectEncoder(),
                new ObjectDecoder(ClassResolvers.cacheDisabled(null)), new PacketHandler(packetDistributor, this));
    }

    public AbstractUser getUser(Channel channel){
        return userHive.getUserByChannel(channel);
    }
}
