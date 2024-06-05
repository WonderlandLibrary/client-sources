package alphentus.utils;

import net.minecraft.entity.Entity;

import java.util.ArrayList;

/**
 * @author avox | lmao
 * @since on 29/07/2020.
 */
public class FriendSystem {

    public ArrayList<String> friends = new ArrayList<>();

    public void addFriend(String name) {
        friends.add(name);
    }

    public void removeFriend(String name) {
        friends.remove(name);
    }

    public ArrayList<String> getFriends() {
        return friends;
    }

    public boolean isFriend(Entity e) {
        return friends.contains(e.getName());
    }
}
