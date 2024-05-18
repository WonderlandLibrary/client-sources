// 
// Decompiled by Procyon v0.5.30
// 

package com.klintos.twelve.handlers.friend;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

public class FriendHandler
{
    private CopyOnWriteArrayList<Friend> friends;
    
    public FriendHandler() {
        this.friends = new CopyOnWriteArrayList<Friend>();
    }
    
    public void addFriend(final Friend friend) {
        this.friends.add(friend);
    }
    
    public void addFriend(final String friend) {
        this.friends.add(new Friend(friend, null));
    }
    
    public void delFriend(final Friend friend) {
        this.friends.remove(friend);
    }
    
    public void delFriend(final String username) {
        if (this.friends.size() > 1) {
            for (final Friend friend : this.friends) {
                if (username.equals(friend.getUsername())) {
                    this.friends.remove(friend);
                }
            }
        }
        else {
            this.clearFriends();
        }
    }
    
    public boolean isFriend(final Friend friend) {
        return this.friends.contains(friend);
    }
    
    public boolean isFriend(final String username) {
        for (final Friend friend : this.friends) {
            if (friend.getUsername().equalsIgnoreCase(username)) {
                return true;
            }
        }
        return false;
    }
    
    public void clearFriends() {
        this.friends.clear();
    }
    
    public CopyOnWriteArrayList<Friend> getFriendsArray() {
        return this.friends;
    }
    
    public String getFriends() {
        String last = "";
        for (final Friend friend : this.friends) {
            last = String.valueOf(last) + friend.getUsername() + ", ";
        }
        return last.substring(0, last.length() - 2);
    }
    
    public Friend getFriend(final String username) {
        for (final Friend friend : this.friends) {
            if (friend.getUsername().equalsIgnoreCase(username)) {
                return friend;
            }
        }
        return null;
    }
}
