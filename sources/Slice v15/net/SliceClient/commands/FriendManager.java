package net.SliceClient.commands;

import java.util.ArrayList;
import net.minecraft.util.StringUtils;


public class FriendManager
{
  public static ArrayList<Friend> friendslist = new ArrayList();
  
  public FriendManager() {}
  
  public void addFriend(String name) { friendslist.add(new Friend(name)); }
  

  public void removeFriend(String name)
  {
    for (Friend friend : friendslist) {
      if (friend.getName().equalsIgnoreCase(name))
      {
        friendslist.remove(friend);
        break;
      }
    }
  }
  
  public static boolean isFriend(String name)
  {
    boolean isFriend = false;
    for (Friend friend : friendslist) {
      if (friend.getName().equalsIgnoreCase(StringUtils.stripControlCodes(name)))
      {
        isFriend = true;
        break;
      }
    }
    return isFriend;
  }
}
