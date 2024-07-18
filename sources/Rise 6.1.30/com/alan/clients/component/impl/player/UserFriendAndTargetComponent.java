package com.alan.clients.component.impl.player;

import java.util.ArrayList;

public class UserFriendAndTargetComponent {
    private static final ArrayList<String> friends = new ArrayList<>();
    private static final ArrayList<String> targets = new ArrayList<>();

    public static void addFriend(String friend) {
        if (!friends.contains(friend)) {
            friends.add(friend);
        }
    }

    public static void removeFriend(String friend) {
        friends.remove(friend);
    }
    public static void addTarget(String target) {
        if (!targets.contains(target)) {
            targets.add(target);
        }
    }

    public static void removeTarget(String target) {
        targets.remove(target);
    }

    public static boolean isFriend(String friend) {
        return friends.contains(friend);
    }
    public static boolean isTarget(String target) {
        return targets.contains(target);
    }

}
