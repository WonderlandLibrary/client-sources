package me.Emir.Karaguc.friend;

import java.util.ArrayList;

public class FriendManager {
    private ArrayList<Friend> friends = new ArrayList<>();

    public boolean hasFriend(String name) {
        for(Friend f : getFriends())
            if(f.getName().equalsIgnoreCase(name))
                return true;
        return false;
    }

    public void addFriend(Friend friend) {
        friends.add(friend);
    }
    public void removeFriend(Friend friend) {
        if(hasFriend(friend.getName()) || hasFriend(friend.getNickname()))
            friends.remove(friend);
    }

    public Friend getFriend(String name) {
        for(Friend f : getFriends())
            if(f.getName().equalsIgnoreCase(name))
                return f;
        return null;
    }

    public ArrayList<Friend> getFriends() {
        return friends;
    }
}
