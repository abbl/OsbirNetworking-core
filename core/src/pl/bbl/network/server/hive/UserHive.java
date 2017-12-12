package pl.bbl.network.server.hive;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import pl.bbl.network.server.connection.AbstractUser;
import pl.bbl.network.server.factory.UserFactory;

import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserHive {
    private UserFactory userFactory;
    private ArrayList<AbstractUser> users;

    public UserHive(Class className){
        users = new ArrayList<>();
        userFactory = new UserFactory(className);
    }

    public AbstractUser createUser(Channel channel){
        Logger.getLogger(getClass().getName()).log(Level.INFO, "Added new connection to UserHive.");
        AbstractUser abstractUser = userFactory.buildUser(channel);
       users.add(abstractUser);
       return abstractUser;
    }

    public void removeUser(String id){
        users.remove(getUserById(id));
    }

    public AbstractUser getUserById(String id){
        for(AbstractUser abstractUser : users){
            if(id.equals(abstractUser.getId())){
                return abstractUser;
            }
        }
        return null;
    }

    public ArrayList<AbstractUser> getUsers() {
        return users;
    }
}
