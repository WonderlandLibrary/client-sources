package com.darkcart.xdolf.util;

import java.util.ArrayList;

import net.minecraft.util.StringUtils;

public class FriendManager
{
	public static ArrayList<Friend> friendsList = new ArrayList<Friend>();
	
	public void addFriend(String name, String alias)
	{
		friendsList.add(new Friend(name, alias));
	}
	
	public void removeFriend(String name)
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
	
	public boolean isFriend(String name)
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
}
