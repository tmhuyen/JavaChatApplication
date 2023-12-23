package Server;

import java.util.*;

public class OnlineUser {
    Set<String> onlineUsers = new HashSet<>();
    //Singleton
    private static OnlineUser instance = null;
    public static OnlineUser getInstance(){
        if(instance == null){
            instance = new OnlineUser();
        }
        return instance;
    }
    OnlineUser(){
        onlineUsers = new HashSet<String>();
    }
    public void addOnlineUser(String username){
        onlineUsers.add(username);
    }
    public void removeOnlineUser(String username){
        onlineUsers.remove(username);
    }

    public ArrayList<String> getOnlineUsers() {
        ArrayList<String> onlineUserList = new ArrayList<>(onlineUsers);
        return onlineUserList;
    }
}
