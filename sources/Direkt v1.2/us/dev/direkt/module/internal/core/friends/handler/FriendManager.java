package us.dev.direkt.module.internal.core.friends.handler;

import com.google.common.collect.Maps;
import net.minecraft.entity.player.EntityPlayer;
import us.dev.direkt.Direkt;
import us.dev.direkt.file.internal.files.FriendsFile;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

/**
 * @author Foundry
 */
public class FriendManager {
    private final Map<String, Friend> friendRegistry = Maps.newHashMap();

    public boolean isFriend(EntityPlayer ep) {
        return friendRegistry.containsKey(ep.getName().toLowerCase());
    }

    public String getAlias(String name) {
        Friend friend;
        if ((friend = friendRegistry.get(name.toLowerCase())) != null) {
            return friend.getAlias();
        } else {
            return null;
        }
    }

    public boolean isFriend(String playerName) {
        return friendRegistry.containsKey(playerName.toLowerCase());
    }

    public Collection<Friend> getFriendsList() {
        return this.friendRegistry.values();
    }

    public void reloadFriends() {
        this.friendRegistry.clear();
        try {
            Direkt.getInstance().getFileManager().getFile(FriendsFile.class).load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addFriend(String name, String alias) {
        this.friendRegistry.put(name.toLowerCase(), new Friend(name, alias));
        try {
            Direkt.getInstance().getFileManager().getFile(FriendsFile.class).save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addFriendNoSave(String name, String alias) {
        this.friendRegistry.put(name.toLowerCase(), new Friend(name, alias));
    }

    public void removeFriend(String name) {
        this.friendRegistry.remove(name.toLowerCase());
        try {
            Direkt.getInstance().getFileManager().getFile(FriendsFile.class).save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static class Friend {
        final String name, alias;

        Friend(String name, String alias) {
            this.name = name;
            this.alias = alias;
        }

        public String getName() {
            return this.name;
        }

        public String getAlias() {
            return this.alias;
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Friend)) {
                return false;
            }
            Friend friend = (Friend) o;
            return friend.name.equals(this.name) && friend.alias.equals(this.alias);
        }

        @Override
        public int hashCode() {
            return name.hashCode();
        }

    }
}
