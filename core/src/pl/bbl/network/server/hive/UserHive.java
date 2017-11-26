package pl.bbl.network.server.hive;

import io.netty.channel.socket.SocketChannel;
import pl.bbl.network.server.connection.AbstractUser;
import pl.bbl.network.server.factory.UserFactory;

import java.util.ArrayList;

public class UserHive {
    private UserFactory userFactory;
    private ArrayList<AbstractUser> users;

    public UserHive(AbstractUser abstractUser){
        users = new ArrayList<>();
        userFactory = new UserFactory(abstractUser);
    }

    public void createUser(SocketChannel socketChannel){
        users.add(userFactory.buildUser(socketChannel));
    }

    public void removeUser(){

    }

    public AbstractUser getUserByKey(String key){
        for(AbstractUser abstractUser : users){
            if(abstractUser.isKeyTheSame(key))
                return abstractUser;
        }
        return null;
    }
}
