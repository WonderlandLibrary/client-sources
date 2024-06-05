package me.vaziak.sensation.client.api.friend;

import java.util.ArrayList;
import java.util.List;

public class FriendManager {
    private List<Friend> friends = new ArrayList<>();

    public void addFriend(Friend friend) {
        friends.add(friend);
    }

    public Friend getFriend(String name) {
        return friends.stream().filter(friend -> friend.getName().equalsIgnoreCase(name)).findAny().orElse(null);
    }
    public void removeFriend(Friend friend) {
        friends.remove(friend);
    }

    public List<Friend> getFriends() {
        return friends;
    }
}
