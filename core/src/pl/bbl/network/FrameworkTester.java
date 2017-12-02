package pl.bbl.network;


import pl.bbl.network.server.ServerInstance;
import pl.bbl.network.server.connection.AbstractUser;

public class FrameworkTester {
    public static void main(String[] args){
        try {
            new ServerInstance(9997, new AbstractUser()).run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
