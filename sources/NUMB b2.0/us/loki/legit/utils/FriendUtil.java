package us.loki.legit.utils;

import java.io.PrintStream;
import java.util.ArrayList;

public class FriendUtil {
    private static ArrayList<Friend> friends;

    public static void setup() {
        System.out.println("Setting up FriendManager...");
        friends = new ArrayList();
        System.out.println("FriendManager setup finished!");
    }

    public static void addWithAlias(String name, String alias) {
        friends.add(new Friend(name, alias));
    }

    public static void addWithoutAlias(String name) {
        friends.add(new Friend(name, name));
    }

    public static void removeFriend(String name) {
        if (!friends.isEmpty() && FriendUtil.isAFriend(name)) {
            friends.remove(FriendUtil.getFriend(name));
        }
    }

    public static Friend getFriend(String name) {
        Friend friend = null;
        if (!friends.isEmpty()) {
            for (Friend friend2 : friends) {
                if (!friend2.getName().equals(name)) continue;
                friend = friend2;
            }
        }
        return friend;
    }

    public static boolean isAFriend(String name) {
        if (!friends.isEmpty()) {
            for (Friend friend : friends) {
                if (!friend.getName().equals(name)) continue;
                return true;
            }
        }
        return false;
    }

    public static class Friend {
        private String name;
        private String alias;

        public Friend(String name, String alias) {
            this.name = name;
            this.alias = alias;
        }

        public String getName() {
            return this.name;
        }

        public String getAlias() {
            return this.alias;
        }
    }

}

