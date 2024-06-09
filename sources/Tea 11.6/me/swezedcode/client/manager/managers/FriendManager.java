package me.swezedcode.client.manager.managers;

import java.util.ArrayList;
import java.util.List;

import me.swezedcode.client.manager.managers.more.Friend;

public class FriendManager {

	public final static List<Friend> friends = new ArrayList<Friend>();

	public static void addFriend(String user, String alias) {
		getFriends().add(new Friend(user, alias));
	}

	public static Friend getFriend(String username) {
		for (Friend friends : getFriends())
			if (friends.getUser().equalsIgnoreCase(username) || friends.getAlias().equalsIgnoreCase(username))
				return friends;
		return null;
	}

	public static boolean isFriend(String username) {
		for (Friend friends : getFriends())
			if (friends.getUser().equalsIgnoreCase(username) || friends.getAlias().equalsIgnoreCase(username))
				return true;
		return false;
	}

	public static void removeFriend(String username) {
		for (Friend friends : getFriends())
			if (friends.getUser().equalsIgnoreCase(username) || friends.getAlias().equalsIgnoreCase(username)) {
				getFriends().remove(friends);
				break;
			}
	}

	public static List<Friend> getFriends() {
		return friends;
	}

}