package me.protocol_client.friendsList;

import java.util.ArrayList;


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
			if(friend.getName().equalsIgnoreCase(name) || friend.getAlias().equalsIgnoreCase(name))
			{
				friendsList.remove(friend);
				break;
			}
		}
	}
	public static String getAlias(String name){
		if(isFriend(name)){
			for(Friend nigger : friendsList){
				if(nigger.getName().equalsIgnoreCase(name)){
					return nigger.getAlias();
				}
			}
		}
		return null;
	}
	
	public static boolean isFriend(String name)
 {
		boolean isFriend = false;
		for(Friend friend: friendsList)
		{
			if(friend.getName().equalsIgnoreCase(net.minecraft.util.StringUtils.stripControlCodes(name)))
			{
				isFriend = true;
				break;
			}
		}
		return isFriend;
	}
}
