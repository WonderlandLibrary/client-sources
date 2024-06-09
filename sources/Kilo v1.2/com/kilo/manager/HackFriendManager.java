package com.kilo.manager;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.kilo.ui.inter.slotlist.part.HackFriend;

public class HackFriendManager {

	private static List<HackFriend> friends = new CopyOnWriteArrayList<HackFriend>();
	
	public static List<HackFriend> getList() {
		return friends;
	}
	
	public static void addHackFriend(HackFriend f) {
		friends.add(f);
	}
	
	public static void addHackFriend(int index, HackFriend w) {
		friends.add(index, w);
	}
	
	public static void removeHackFriend(HackFriend w) {
		friends.remove(w);
	}
	
	public static void removeHackFriend(int index) {
		friends.remove(friends.get(index));
	}
	
	public static HackFriend getHackFriend(int index) {
		if (friends.size() == 0) {
			return null;
		}
		return friends.get(index);
	}
	
	public static HackFriend getHackFriend(String n) {
		for(HackFriend w : friends) {
			if (w.name.equalsIgnoreCase(n)) {
				return w;
			}
		}
		return null;
	}
	
	public static int getIndex(HackFriend w) {
		return friends.indexOf(w);
	}
	
	public static int getSize() {
		return getList().size();
	}
}
