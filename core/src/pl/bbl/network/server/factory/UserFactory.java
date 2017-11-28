package pl.bbl.network.server.factory;

import io.netty.channel.ChannelHandlerContext;
import pl.bbl.network.server.connection.AbstractUser;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class UserFactory {
    private String className;

    public UserFactory(AbstractUser abstractUser){
        setClassName(abstractUser);
    }

    public void setClassName(AbstractUser abstractUser){
        className = abstractUser.getClass().getName();
    }

    public AbstractUser buildUser(ChannelHandlerContext channelHandlerContext){
        try {
            Constructor constructor = Class.forName(className).getConstructor(ChannelHandlerContext.class);
            return (AbstractUser) constructor.newInstance(channelHandlerContext);
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}
