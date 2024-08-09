package ez.h.managers;

import java.util.*;
import java.io.*;

public class FriendManager
{
    public static ArrayList<String> friends;
    
    public static void load() throws IOException {
    }
    
    static {
        FriendManager.friends = new ArrayList<String>();
    }
    
    public static boolean isFriend(final String s) {
        return FriendManager.friends.contains(s);
    }
    
    public static void removeFriend(final String s) {
        FriendManager.friends.removeIf(s2 -> s2.equals(s));
    }
    
    public static void addFriend(final String s) {
        FriendManager.friends.add(s);
    }
    
    public static void save() throws IOException {
    }
}
