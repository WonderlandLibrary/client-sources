/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.friendsystem;

import java.util.ArrayList;
import java.util.List;
import ru.govno.client.friendsystem.Friend;

public class FriendManager {
    private final List<Friend> friends = new ArrayList<Friend>();

    public void addFriend(String name) {
        this.friends.add(new Friend(name));
    }

    public boolean isFriend(String name) {
        return this.friends.stream().anyMatch(paramFriend -> paramFriend.getName().equalsIgnoreCase(name));
    }

    public void removeFriend(String name) {
        for (Friend friend : this.getFriends()) {
            if (!friend.getName().equalsIgnoreCase(name)) continue;
            this.friends.remove(friend);
            break;
        }
    }

    public void clearFriends() {
        this.friends.clear();
    }

    public List<Friend> getFriends() {
        return this.friends;
    }

    public Friend getFriend(String name) {
        return this.friends.stream().filter(paramFriend -> paramFriend.getName().equals(name)).findFirst().orElse(null);
    }
}

