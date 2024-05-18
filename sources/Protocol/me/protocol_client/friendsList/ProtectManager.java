package me.protocol_client.friendsList;

import java.util.ArrayList;

public class ProtectManager {
	public static ArrayList<Protect>	friendsList	= new ArrayList<Protect>();

	public void addFriend(String name, String protection) {
		friendsList.add(new Protect(name, protection));
	}

	public void removeFriend(String name) {
		for (Protect friend : friendsList) {
			if (friend.getName().equalsIgnoreCase(name) || friend.getAlias().equalsIgnoreCase(name)) {
				friendsList.remove(friend);
				break;
			}
		}
	}

	public static boolean isProtect(String name) {
		boolean isFriend = false;
		for (Protect friend : friendsList) {
			if (friend.getName().equalsIgnoreCase(net.minecraft.util.StringUtils.stripControlCodes(name))) {
				isFriend = true;
				break;
			}
		}
		return isFriend;
	}

}
