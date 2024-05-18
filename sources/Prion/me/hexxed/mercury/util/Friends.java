package me.hexxed.mercury.util;

import java.util.HashMap;

public class Friends
{
  public Friends() {}
  
  public static HashMap<String, String> getFriends()
  {
    return FileUtils.getFriendsFromFile();
  }
  
  public static boolean addFriend(String name, String alias) {
    if (getFriends().containsKey(name)) return false;
    getFriends().put(name, alias);
    java.util.List<String> friend = FileUtils.readFile(me.hexxed.mercury.Mercury.raidriarDir.getAbsolutePath() + "\\friends.txt");
    friend.add(name + ":" + alias);
    FileUtils.writeFile(me.hexxed.mercury.Mercury.raidriarDir.getAbsolutePath() + "\\friends.txt", friend);
    return true;
  }
  
  public static boolean removeFriends(String name) {
    if (!getFriends().containsKey(name)) return false;
    getFriends().remove(name);
    java.util.List<String> friend = FileUtils.readFile(me.hexxed.mercury.Mercury.raidriarDir.getAbsolutePath() + "\\friends.txt");
    friend.remove(name + ":" + getAlias(name));
    FileUtils.writeFile(me.hexxed.mercury.Mercury.raidriarDir.getAbsolutePath() + "\\friends.txt", friend);
    return true;
  }
  
  public static boolean isFriend(String name) {
    for (String s : getFriends().keySet()) {
      if (ChatColor.stripColor(s).trim().equals(name)) {
        return true;
      }
    }
    return false;
  }
  
  public static String getAlias(String friend) {
    for (String s : getFriends().keySet()) {
      if (ChatColor.stripColor(s).trim().equals(friend)) {
        return (String)getFriends().get(s);
      }
    }
    return friend;
  }
  
  public static boolean setAlias(String friend, String alias) {
    if (!getFriends().containsKey(friend)) return false;
    getFriends().put(friend, alias);
    java.util.List<String> file = FileUtils.readFile(me.hexxed.mercury.Mercury.raidriarDir.getAbsolutePath() + "\\friends.txt");
    file.remove(friend + ":" + getAlias(friend));
    file.add(friend + ":" + alias);
    FileUtils.writeFile(me.hexxed.mercury.Mercury.raidriarDir.getAbsolutePath() + "\\friends.txt", file);
    return true;
  }
}
