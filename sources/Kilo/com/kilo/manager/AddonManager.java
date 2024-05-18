package com.kilo.manager;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.kilo.Kilo;
import com.kilo.users.User;
import com.kilo.util.ServerUtil;

public class AddonManager {

	public static Map<String, User> users = new LinkedHashMap<String, User>();
	
	public static void loadUsers() {
		List<User> list = ServerUtil.getUserExtras();
		if (list != null) {
			for(User u : list) {
				users.put(u.minecraftName, u);
			}
		}
	}
}
