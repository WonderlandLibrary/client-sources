package rip.helium.cheat;

import java.util.HashMap;

public class FriendManager {
	
	public static HashMap<String, String> friends = new HashMap<>();
	
	public static boolean isFriend(String name) {
		return friends.containsKey(name);
	}

}
