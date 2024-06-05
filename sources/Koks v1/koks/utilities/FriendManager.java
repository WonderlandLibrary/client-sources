package koks.utilities;

import java.util.HashMap;

/**
 * @author avox | lmao | kroko
 * @created on 05.09.2020 : 17:30
 */
public class FriendManager {

    public HashMap<String, String> friends = new HashMap<>();

    public void addFriend(String name, String alias) {
        friends.put(name, alias);
    }

    public void removeFriend(String name) {
        friends.remove(name);
    }

    public String[] getFriend(String name) {
        return new String[] {name, friends.get(name)};
    }

    public boolean isFriend(String name) {
        return friends.containsKey(name);
    }

}