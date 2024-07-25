package club.bluezenith.util.friends;

import club.bluezenith.BlueZenith;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;

public class FriendManager {

    final List<String> friendsList = Lists.newArrayList();

    public boolean isFriend(String name) {
        return friendsList.stream().anyMatch(friend -> friend.equalsIgnoreCase(name));
    }

    public void addFriend(String name) {
        BlueZenith.getBlueZenith().getTargetManager().removeTarget(name);
        friendsList.add(name);
    }

    public void removeFriend(String name) {
        friendsList.removeIf(friend -> friend.equalsIgnoreCase(name));
    }

    public void clearList() {
        friendsList.clear();
    }

    public List<String> getFriendsList() {
        return new ArrayList<>(friendsList);
    }

}
