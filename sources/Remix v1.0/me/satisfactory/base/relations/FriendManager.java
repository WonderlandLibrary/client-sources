package me.satisfactory.base.relations;

import java.util.regex.*;
import net.minecraft.client.*;
import java.util.*;

public class FriendManager
{
    private static final Pattern patternControlCode;
    public static ArrayList<Friend> friendsList;
    
    public static void addFriend(final String name, final String alias) {
        FriendManager.friendsList.add(new Friend(name, alias));
    }
    
    public static void addFriendNoAlias(final String name) {
        FriendManager.friendsList.add(new Friend(name, name));
    }
    
    public static String getAliasName(final String name) {
        String alias = "";
        for (final Friend friend : FriendManager.friendsList) {
            if (friend.getName().equalsIgnoreCase(stripControlCodes(name))) {
                alias = friend.getAlias();
                break;
            }
        }
        if (Minecraft.getMinecraft().thePlayer != null && Minecraft.getMinecraft().thePlayer.getGameProfile().getName() == name) {
            return name;
        }
        return alias;
    }
    
    public static void removeFriend(final String name) {
        for (final Friend friend : FriendManager.friendsList) {
            if (friend.getName().equalsIgnoreCase(name)) {
                FriendManager.friendsList.remove(friend);
                break;
            }
        }
    }
    
    public static String replaceText(String text) {
        for (final Friend friend : FriendManager.friendsList) {
            if (text.contains(friend.getName())) {
                text = friend.getAlias();
            }
        }
        return text;
    }
    
    public static boolean isFriend(final String name) {
        boolean isFriend = false;
        for (final Friend friend : FriendManager.friendsList) {
            if (friend.getName().equalsIgnoreCase(stripControlCodes(name))) {
                isFriend = true;
                break;
            }
        }
        if (Minecraft.getMinecraft().thePlayer.getGameProfile().getName() == name) {
            isFriend = true;
        }
        return isFriend;
    }
    
    public static ArrayList<Friend> getFriendsList() {
        return FriendManager.friendsList;
    }
    
    public static String stripControlCodes(final String s) {
        return FriendManager.patternControlCode.matcher(s).replaceAll("");
    }
    
    static {
        patternControlCode = Pattern.compile("(?i)\\u00A7[0-9A-FK-OR]");
        FriendManager.friendsList = new ArrayList<Friend>();
    }
}
