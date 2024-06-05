/*
 * Decompiled with CFR 0.152.
 */
package cc.diablo.manager.friend;

import cc.diablo.helpers.render.ChatColor;
import cc.diablo.helpers.render.ChatHelper;
import cc.diablo.manager.friend.Friend;
import cc.diablo.manager.map.MapManager;
import java.util.ArrayList;
import java.util.HashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.util.StringUtils;

public class FriendManager
extends MapManager<String, String> {
    public static ArrayList<Friend> friendsList = new ArrayList();

    public void addFriend(String name, String alias) {
        this.contents.put(name, alias);
    }

    public static void addFriendToList(String name, String alias) {
        ChatHelper.addChat((Object)((Object)ChatColor.GREEN) + "Added " + (Object)((Object)ChatColor.WHITE) + name + " as a friend");
        friendsList.add(new Friend(name, alias));
    }

    public static String getAliasName(String name) {
        String alias = "";
        for (Friend friend : friendsList) {
            if (!friend.name.equalsIgnoreCase(StringUtils.stripControlCodes(name))) continue;
            alias = friend.alias;
            break;
        }
        if (Minecraft.getMinecraft().thePlayer != null && Minecraft.getMinecraft().thePlayer.getGameProfile().getName() == name) {
            return name;
        }
        return alias;
    }

    public static void removeFriend(String name) {
        ChatHelper.addChat((Object)((Object)ChatColor.RED) + "Removed " + (Object)((Object)ChatColor.WHITE) + name + " as a friend");
        for (Friend friend : friendsList) {
            if (!friend.name.equalsIgnoreCase(name)) continue;
            friendsList.remove(friend);
            break;
        }
    }

    public static String replace(String text) {
        for (Friend friend : friendsList) {
            if (!text.contains(friend.name)) continue;
            text = friend.alias;
        }
        return text;
    }

    public static boolean isFriend(String name) {
        boolean isFriend = false;
        for (Friend friend : friendsList) {
            if (!friend.name.equalsIgnoreCase(StringUtils.stripControlCodes(name))) continue;
            isFriend = true;
            break;
        }
        if (Minecraft.getMinecraft().thePlayer.getGameProfile().getName() == name) {
            isFriend = true;
        }
        return isFriend;
    }

    public void addFriend(Friend friend) {
        friendsList.add(friend);
    }

    @Override
    public void setup() {
        this.contents = new HashMap();
    }
}

