package pl.bbl.network.client;


import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import pl.bbl.network.packet.Packet;

public abstract class AbstractClient implements Runnable{
    private ChannelFuture channelFuture;
    private String host;
    private int port;

    protected AbstractClient(String host, int port){
        this.host = host;
        this.port = port;
    }

    @Override
    public void run(){
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();

        try {
            bootstrap.group(workerGroup);
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    addHandlersToChannel(ch.pipeline());
                }
            });
        channelFuture = bootstrap.connect(host, port).sync();
        channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }

    protected void addHandlersToChannel(ChannelPipeline pipeline){
        pipeline.addLast(new ObjectEncoder(),
                new ObjectDecoder(ClassResolvers.cacheDisabled(null)));
    }

    public void write(Packet packet){
        if(channelFuture == null)
            waitForChannelFutureAndSendPacket(packet);
        else
            channelFuture.channel().writeAndFlush(packet);
    }

    private void waitForChannelFutureAndSendPacket(Packet packet){
        new Thread(() -> {
            while(channelFuture == null){
                //just waiting for ChannelFuture to initialize.
            }
            write(packet);
        }).start();
    }
}
