package pl.bbl.network.server.factory;

import io.netty.channel.socket.SocketChannel;
import pl.bbl.network.server.connection.AbstractUser;



public class UserFactory {
    private String className;

    public UserFactory(){}

    public UserFactory(AbstractUser abstractUser){
        setClassName(abstractUser);
    }

    public void setClassName(AbstractUser abstractUser){
        className = abstractUser.getClass().getName();
    }

    public AbstractUser buildUser(SocketChannel socketChannel){
        try {
            return (AbstractUser) Class.forName(className).newInstance();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
