/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.client.friend;

import com.mojang.authlib.GameProfile;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import me.thekirkayt.client.friend.Friend;
import me.thekirkayt.utils.FileUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.StringUtils;

public class FriendManager {
    private static final File FRIEND_DIR = FileUtils.getConfigFile("Friends");
    public static ArrayList<Friend> friendsList = new ArrayList();

    public static void start() {
        FriendManager.load();
        FriendManager.save();
    }

    public static void addFriend(String name, String alias) {
        friendsList.add(new Friend(name, alias));
        FriendManager.save();
    }

    public static String getAliasName(String name) {
        String alias = "";
        for (Friend friend : friendsList) {
            if (!friend.name.equalsIgnoreCase(StringUtils.stripControlCodes(name))) continue;
            alias = friend.alias;
            break;
        }
        Minecraft.getMinecraft();
        if (Minecraft.thePlayer != null) {
            Minecraft.getMinecraft();
            if (Minecraft.thePlayer.getGameProfile().getName() == name) {
                return name;
            }
        }
        return alias;
    }

    public static void removeFriend(String name) {
        for (Friend friend : friendsList) {
            if (!friend.name.equalsIgnoreCase(name)) continue;
            friendsList.remove(friend);
            break;
        }
        FriendManager.save();
    }

    public static String replaceText(String text) {
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
        Minecraft.getMinecraft();
        if (Minecraft.thePlayer.getGameProfile().getName() == name) {
            isFriend = true;
        }
        return isFriend;
    }

    public static void load() {
        friendsList.clear();
        List<String> fileContent = FileUtils.read(FRIEND_DIR);
        for (String line : fileContent) {
            try {
                String[] split = line.split(":");
                String name = split[0];
                String alias = split[1];
                FriendManager.addFriend(name, alias);
            }
            catch (Exception split) {
                // empty catch block
            }
        }
    }

    public static void save() {
        ArrayList<String> fileContent = new ArrayList<String>();
        for (Friend friend : friendsList) {
            String alias = FriendManager.getAliasName(friend.name);
            fileContent.add(String.format("%s:%s", friend.name, alias));
        }
        FileUtils.write(FRIEND_DIR, fileContent, true);
    }
}

