/*
 * Decompiled with CFR 0.145.
 */
package us.amerikan.manager;

import java.util.ArrayList;

public class FriendManager {
    private final ArrayList<String> friends = new ArrayList();

    public void addFriend(String username) {
        this.friends.add(username.toLowerCase());
    }

    public void removeFriend(String username) {
        this.friends.remove(username.toLowerCase());
    }

    public boolean isFriend(String username) {
        return this.friends.contains(username.toLowerCase());
    }

    public ArrayList<String> getFriends() {
        return this.friends;
    }
}

