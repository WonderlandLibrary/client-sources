package com.kilo.manager;

import net.minecraft.client.Minecraft;

import com.kilo.Kilo;
import com.kilo.util.ServerUtil;
import com.kilo.util.Timer;

public class UpdateManager {

	public static void initUpdating(final float time) {
		new Thread() {
			@Override
			public void run() {
				
				Kilo.kilo().client.playlists = ServerUtil.getPlaylists(Kilo.kilo().client.clientID);
				
				updateFriendsList();
				updateLatestActivityList();
				updateMessageList();
				
				Timer timer = new Timer();
				timer.reset();
				while (true) {
					if (timer.isTime(time)) {
						checkUpdate();
						updateFriendsList();
						updateLatestActivityList();
						updateMessageList();
						updateExtrasList();
						timer.reset();
					}
				}
			}
		}.start();
	}
	
	public static void updateMultiplayerServerList() {
		if (Kilo.kilo().client == null) {
			return;
		}
		ServerManager.loadServers();
	}
	
	public static void updateFriendsList() {
		if (Kilo.kilo().client == null) {
			return;
		}
		FriendManager.loadFriends();
	}
	
	public static void updateLatestActivityList() {
		if (Kilo.kilo().client == null) {
			return;
		}
		ActivityManager.loadActivities();
	}
	
	public static void updateMessageList() {
		if (Kilo.kilo().client == null) {
			return;
		}
		MessageManager.loadMessages();
	}
	
	public static void checkUpdate() {
		if (Kilo.kilo().client == null) {
			return;
		}
		new Thread() {
			@Override
			public void run() {
				Minecraft mc = Minecraft.getMinecraft();
				boolean singleplayer = mc.isSingleplayer();
				boolean ingame = mc.theWorld != null;
				boolean onServer = mc.getCurrentServerData() != null;
				String ip = onServer?mc.getCurrentServerData().serverIP:"";
				
				NotificationManager.list = ServerUtil.getUpdates(Kilo.kilo().client.clientID, singleplayer?"singleplayer":ingame && onServer?"multiplayer":(ip.length() > 0?"online":"online"), ip); 
			}
		}.start();
	}
	
	public static void updateExtrasList() {
		if (Kilo.kilo().client == null) {
			return;
		}
		AddonManager.loadUsers();
	}
}
