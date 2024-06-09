// 
// Decompiled by Procyon v0.5.36
// 

package intent.AquaDev.aqua.utils;

import java.util.ArrayList;

public class FriendSystem
{
    public static final ArrayList<String> friends;
    
    public static void addFriend(final String name) {
        System.out.println("Added " + name);
        FriendSystem.friends.add(name);
    }
    
    public static void removeFriend(final String name) {
        System.out.println("Removed " + name);
        FriendSystem.friends.remove(name);
    }
    
    public static ArrayList<String> getFriends() {
        return FriendSystem.friends;
    }
    
    public static boolean isFriend(final String name) {
        return FriendSystem.friends.contains(name);
    }
    
    public static void clearFriends() {
        System.out.println("Cleared friend list");
        FriendSystem.friends.clear();
    }
    
    static {
        friends = new ArrayList<String>();
    }
}
