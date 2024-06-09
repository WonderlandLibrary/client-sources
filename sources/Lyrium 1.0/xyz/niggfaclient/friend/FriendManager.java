// 
// Decompiled by Procyon v0.5.36
// 

package xyz.niggfaclient.friend;

import java.util.Iterator;
import java.io.File;
import java.util.ArrayList;

public class FriendManager
{
    private ArrayList<Friends> friends;
    private FriendSaving friendSaving;
    
    public FriendManager(final File dir) {
        this.friends = new ArrayList<Friends>();
        (this.friendSaving = new FriendSaving(dir)).setup();
    }
    
    public FriendSaving getFriendSaving() {
        return this.friendSaving;
    }
    
    public ArrayList<Friends> getFriends() {
        return this.friends;
    }
    
    public void addFriend(final String name) {
        this.friends.add(new Friends(name));
    }
    
    public void addFriendWithAlias(final String name, final String alias) {
        this.friends.add(new Friends(name, alias));
    }
    
    public Friends getFriend(final String ign) {
        for (final Friends friend : this.friends) {
            if (friend.getName().equalsIgnoreCase(ign)) {
                return friend;
            }
        }
        return null;
    }
    
    public void setFriends(final ArrayList<Friends> friends) {
        this.friends = friends;
    }
    
    public boolean isFriend(final String ign) {
        return this.getFriend(ign) != null;
    }
    
    public void clearFriends() {
        this.friends.clear();
    }
    
    public void removeFriend(final String name) {
        final Friends f = this.getFriend(name);
        if (f != null) {
            this.friends.remove(f);
        }
    }
}
