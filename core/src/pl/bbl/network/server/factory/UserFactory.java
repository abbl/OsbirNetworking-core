package pl.bbl.network.server.factory;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.unix.Socket;
import pl.bbl.network.server.connection.AbstractUser;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.channels.SocketChannel;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserFactory {
    private static final int ID_LENGTH = 32;
    private String className;

    public UserFactory(Class className){
        setClassName(className);
    }

    public void setClassName(Class className){
        if(AbstractUser.class.isAssignableFrom(className))
            this.className = className.getName();
        else
            Logger.getLogger(this.getClass().getName()).log(Level.WARNING, "Class which is not a subclass of AbstractUser was passed in UserFactory.setClassName()");
    }

    public AbstractUser buildUser(Channel channel){
        Logger.getLogger(getClass().getName()).log(Level.INFO, "User has been built");
        try {
            Constructor constructor = Class.forName(className).getConstructor(String.class, Channel.class);
            return (AbstractUser) constructor.newInstance(generateId(), channel);
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String generateId(){
        char[] chars = "abcdefghijklmnopqrstuvwxyz1234567890@!".toCharArray();
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < ID_LENGTH; i++) {
            char c = chars[random.nextInt(chars.length)];
            sb.append(c);
        }
        return sb.toString();
    }
}
