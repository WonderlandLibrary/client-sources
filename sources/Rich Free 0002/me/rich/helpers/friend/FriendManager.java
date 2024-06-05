package me.rich.helpers.friend;

import java.util.ArrayList;

import net.minecraft.util.StringUtils;

public class FriendManager
{
	public static FriendManager friendManager;
	public static ArrayList<Friend> friendsList = new ArrayList<Friend>();
	
	public void addFriend(String name, String alias)
	{
		friendsList.add(new Friend(name, alias));
	}
	
	public static void removeFriend(String name)
	{
		for(Friend friend: friendsList)
		{
			if(friend.getName().equalsIgnoreCase(name))
			{
				friendsList.remove(friend);
				break;
			}
		}
	}
	
	public static boolean isFriend(String name)
	{
		boolean isFriend = false;
		for(Friend friend: friendsList)
		{
			if(friend.getName().equalsIgnoreCase(StringUtils.stripControlCodes(name)))
			{
				isFriend = true;
				break;
			}
		}
		return isFriend;
	}
	
	public static FriendManager getFriends()
	{
		if(friendManager == null) friendManager = new FriendManager();
		
		return friendManager;
	}
}
