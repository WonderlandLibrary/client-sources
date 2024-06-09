package com.kilo.util;

import jaco.mp3.player.MP3Player;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONObject;

import com.kilo.Kilo;
import com.kilo.manager.DatabaseManager;
import com.kilo.manager.Notification;
import com.kilo.manager.NotificationManager;
import com.kilo.ui.inter.slotlist.part.Activity;
import com.kilo.ui.inter.slotlist.part.Friend;
import com.kilo.ui.inter.slotlist.part.Message;
import com.kilo.ui.inter.slotlist.part.Playlist;
import com.kilo.ui.inter.slotlist.part.Server;
import com.kilo.ui.inter.slotlist.part.Song;
import com.kilo.users.User;

public class ServerUtil {

	public static String hideStatus;
	
	public static String[] login(String username, String password) {
		String encrypt = encrypt(password);
		if (encrypt.equalsIgnoreCase(password)) {
			return null;
		}
		return loginSecure(username, encrypt);
	}
	
	private static String[] loginSecure(String u, String e) {
		try {
			URL url = new URL(String.format(DatabaseManager.login, u, e));
			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

			String inputLine;
			String line = "";
			while ((inputLine = in.readLine()) != null) {
				line = inputLine;
			}
			in.close();

			JSONObject jo = new JSONObject(line);
			
			String cid = jo.getString("client_id");
			String message = jo.getString("message");
			return new String[] {cid, message};
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	public static String[] createAccount(String a, String u, String e, String p, boolean i) {
		a = a.replace(" ", "%20");
		u = u.replace(" ", "%20");
		p = encrypt(p);
		try {
			URL url = new URL(String.format(DatabaseManager.createAccount, a, e, p, u, String.valueOf(i)));

			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

			String inputLine;
			String line = "";
			while ((inputLine = in.readLine()) != null) {
				line = inputLine;
			}
			in.close();
			if (line.length() == 0) {
				return null;
			}

			JSONObject jo = new JSONObject(line);
			
			String cid = String.valueOf(jo.get("client_id"));
			String message = jo.getString("message");
			return new String[] {cid, message};
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	private static String encrypt(String s) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(s.getBytes());
	
			byte byteData[] = md.digest();
	
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < byteData.length; i++) {
				sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
			}
	
			StringBuffer hexString = new StringBuffer();
			for (int i = 0; i < byteData.length; i++) {
				String hex = Integer.toHexString(0xff & byteData[i]);
				if (hex.length() == 1)
					hexString.append('0');
				hexString.append(hex);
			}
			
			return hexString.toString();
		} catch (Exception e) {
			return s;
		}
	}
	
	public static String[] getClientDetails(String cid) {
		try {
			URL url = new URL(String.format(DatabaseManager.clientDetails, cid));
			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

			String inputLine;
			String line = "";
			while ((inputLine = in.readLine()) != null) {
				line = inputLine;
			}
			in.close();

			JSONObject obj = new JSONObject(line);
			if (obj.length() == 7) {
				String kiloName = isString(obj.get("name"));
				String minecraftName = isString(obj.get("ingame_name"));
				String premium = isString(obj.get("premium"));
				String email = isString(obj.get("email"));
				String status = isString(obj.get("status"));
				String nametag = isString(obj.get("nametag")).replace("&", "\u00a7");
				String checkTime = isString(obj.get("check_time"));
				
				return new String[] {cid, kiloName, minecraftName, premium, email, status, nametag, checkTime};
			} else {
				String message = obj.getString("message");
				return new String[] {message};
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	public static List<Server> getServers(String cid) {
		List<Server> list = new ArrayList<Server>();
		try {
			URL url = new URL(String.format(DatabaseManager.clientServers, cid));
			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

			String inputLine;
			String line = "";
			while ((inputLine = in.readLine()) != null) {
				line = inputLine;
			}
			in.close();

			JSONArray ja = new JSONArray(line);
			for(int i = 0; i < ja.length(); i++) {
				JSONObject jo = ja.getJSONObject(i);
				String ip = null, port = null, serverName = null, serverDescription = null, serverWebsite = null, serverWebstore = null;
				int maxPlayers = -1;
				boolean extraDetails;
				
				ip = jo.getString("ip");
				extraDetails = Boolean.parseBoolean(jo.getString("extra_details"));
				if (extraDetails) {
					serverName = jo.getString("server_name");
					serverDescription = jo.getString("server_description");
					serverWebsite = jo.getString("server_website");
					serverWebstore = jo.getString("server_webstore");
				}
				list.add(new Server(ip, serverName, serverDescription, serverWebsite, serverWebstore));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return list;
	}

	public static String addServer(String i, String p) {
		i = i.replace(" ", "%20");
		p = p.replace(" ", "%20");
		if (Kilo.kilo().client == null) {
			return null;
		}
		try {
			URL url = new URL(Kilo.kilo().client.clientID, i, p);

			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

			String inputLine;
			String line = "";
			while ((inputLine = in.readLine()) != null) {
				line = inputLine;
			}
			in.close();
			if (line.length() == 0) {
				return null;
			}

			JSONObject jo = new JSONObject(line);
			
			String response = "true";
			return response;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	public static String delServer(String i) {
		i = i.replace(" ", "%20");
		if (Kilo.kilo().client == null) {
			return null;
		}
		try {
			URL url = new URL(String.format(DatabaseManager.delServer, Kilo.kilo().client.clientID, i));

			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

			String inputLine;
			String line = "";
			while ((inputLine = in.readLine()) != null) {
				line = inputLine;
			}
			in.close();
			if (line.length() == 0) {
				return null;
			}

			JSONObject jo = new JSONObject(line);
			
			String response = String.valueOf(jo.get("response"));
			return response;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	public static void moveServer(String i, String method) {
		i = i.replace(" ", "%20");
		if (Kilo.kilo().client == null) {
			return;
		}
		try {
			URL url = new URL(String.format(DatabaseManager.moveServer, Kilo.kilo().client.clientID, i, method));
			url.openStream();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private static String isString(Object o) {
		if (o instanceof String) {
			return (String)o;
		} else {
			return "";
		}
	}
	
	public static List<Friend> getFriends(String cid) {
		List<Friend> list = new ArrayList<Friend>();
		try {
			URL url = new URL(String.format(DatabaseManager.clientFriends, cid));
			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

			String inputLine;
			String line = "";
			while ((inputLine = in.readLine()) != null) {
				line = inputLine;
			}
			in.close();

			JSONArray ja = new JSONArray(line);
			for(int i = 0; i < ja.length(); i++) {
				JSONObject jo = ja.getJSONObject(i);
				String kiloname = null, mcname = null, status = null, ip = null;
				
				kiloname = jo.getString("nickname");
				mcname = jo.getString("username");
				status = jo.getString("ingame_status");
				ip = jo.getString("server_ip");

				list.add(new Friend(kiloname, mcname, status, ip));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return list;
	}
	
	public static List<Activity> getLatestActivity(String cid) {
		List<Activity> list = new ArrayList<Activity>();
		try {
			URL url = new URL(String.format(DatabaseManager.latestActivity, cid));
			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

			String inputLine;
			String line = "";
			while ((inputLine = in.readLine()) != null) {
				line = inputLine;
			}
			in.close();

			JSONArray ja = new JSONArray(line);
			for(int i = 0; i < ja.length(); i++) {
				JSONObject jo = ja.getJSONObject(i);
				String id = null, picture = null, firstname = null, ingamename = null, ip = null, message = null;

				if (jo.has("id")) { id = jo.getString("id"); }
				if (jo.has("picture")) { picture = jo.getString("picture"); }
				if (jo.has("first_name")) { firstname = jo.getString("first_name"); }
				if (jo.has("ingamename")) { ingamename = jo.getString("ingamename"); }
				if (jo.has("ip")) { ip = jo.getString("ip"); }
				if (jo.has("message")) { message = jo.getString("message"); }

				list.add(new Activity(id, ActivityType.valueOf(jo.getString("type").toLowerCase()), picture, firstname, ingamename, ip, message));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return list;
	}
	
	public static void manageAddonsLogin() {
		if (Kilo.kilo().client == null) {
			return;
		}
		try {
			URL url = new URL(String.format(DatabaseManager.manageAddonsLogin, Kilo.kilo().client.clientID));
			url.openStream();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public static void hideStatus() {
		hideStatus = null;
		if (Kilo.kilo().client == null) {
			hideStatus = "Hide Status: Off";
			return;
		}
		try {
			URL url = new URL(String.format(DatabaseManager.hideStatus, Kilo.kilo().client.clientID));

			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

			String inputLine;
			String line = "";
			while ((inputLine = in.readLine()) != null) {
				line = inputLine;
			}
			in.close();
			if (line.length() == 0) {
				hideStatus = "Hide Status: Off";
				return;
			}
			
			if (line.equalsIgnoreCase("set shown")) {
				hideStatus = "Hide Status: Off";
				return;
			} else {
				hideStatus = "Hide Status: On";
				return;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		hideStatus = "Hide Status: Off";
	}
	
	public static void getStatus() {
		hideStatus = null;
		if (Kilo.kilo().client == null) {
			hideStatus = "Hide Status: Off";
			return;
		}
		try {
			URL url = new URL(String.format(DatabaseManager.getStatus, Kilo.kilo().client.clientID));

			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

			String inputLine;
			String line = "";
			while ((inputLine = in.readLine()) != null) {
				line = inputLine;
			}
			in.close();
			if (line.length() == 0) {
				hideStatus = "Hide Status: Off";
				return;
			}
			
			if (line.equalsIgnoreCase("set shown")) {
				hideStatus = "Hide Status: On";
				return;
			} else {
				hideStatus = "Hide Status: Off";
				return;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		hideStatus = "Hide Status: Off";
	}
	
	public static List<Message> getMessages(String cid) {
		List<Message> list = new ArrayList<Message>();
		try {
			URL url = new URL(String.format(DatabaseManager.clientMessages, cid));
			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

			String inputLine;
			String line = "";
			while ((inputLine = in.readLine()) != null) {
				line = inputLine;
			}
			in.close();

			JSONArray ja = new JSONArray(line);
			for(int i = 0; i < ja.length(); i++) {
				JSONObject jo = ja.getJSONObject(i);
				String username = null, message = null;

				if (jo.has("username")) { username = jo.getString("username"); }
				if (jo.has("message")) { message = jo.getString("message"); }

				list.add(new Message(username, message, String.format(DatabaseManager.head, username, "128")));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return list;
	}
	
	public static List<Notification> getUpdates(String cid, String status, String ip) {
		List<Notification> list = NotificationManager.list;
		try {
			URL url = new URL(String.format(DatabaseManager.updates, cid, status, ip));
			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

			String inputLine;
			String line = "";
			while ((inputLine = in.readLine()) != null) {
				line = inputLine;
			}
			in.close();

			JSONArray ja = new JSONArray(line);
			for(int i = 0; i < ja.length(); i++) {
				JSONObject jo = ja.getJSONObject(i);
				String type = null, picture = null, kiloName = null, minecraftName = null, message = null, ipp = null;

				if (jo.has("type")) { type = jo.getString("type"); }
				if (jo.has("picture")) { picture = jo.getString("picture"); }
				if (jo.has("first_name")) { kiloName = jo.getString("first_name"); }
				if (jo.has("ingamename")) { minecraftName = jo.getString("ingamename"); }
				if (jo.has("message")) { message = jo.getString("message"); }
				if (jo.has("ip")) { ipp = jo.getString("ip"); }
				
				String text = "";
				
				switch(ActivityType.valueOf(type)) {
				case new_message:
					text = String.format("\u00a73%s \u00a77sent you a message", minecraftName);
					break;
				case friend_accepted:
					text = String.format("\u00a73%s \u00a77accepted your friend request", minecraftName);
					break;
				case friend_request:
					text = String.format("\u00a73%s \u00a77sent you a friend request", minecraftName);
					break;
				case invite:
					text = String.format("\u00a73%s \u00a77invited you to join a server", minecraftName);
					break;
				}
				
				list.add(new Notification(picture, text, false));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		if (list.size() > 1) {
			int size = list.size();
			list.clear();
			list.add(new Notification(null, "You have "+size+" notifications", true));
		}
		if (!list.isEmpty()) {
			if (Resources.soundNotification != null) {
				MP3Player sound = new MP3Player(Resources.soundNotification);
				sound.play();
			}
		}
		return list;
	}
	
	public static void removeActivity(String cid, String id) {
		if (Kilo.kilo().client == null) {
			return;
		}
		try {
			URL url = new URL(String.format(DatabaseManager.latestActivityDelete, cid, id));
			url.openStream();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public static boolean sendMessage(String cid, String to, String message) {
		message = message.replace(" ", "%20");
		if (Kilo.kilo().client == null) {
			return false;
		}
		try {
			URL url = new URL(String.format(DatabaseManager.newMessage, cid, to, message));
			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

			String inputLine;
			String line = "";
			while ((inputLine = in.readLine()) != null) {
				line = inputLine;
			}
			in.close();
			
			return line.length()>0;

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}
	
	public static boolean friendAccept(String cid, String username) {
		if (Kilo.kilo().client == null) {
			return false;
		}
		try {
			URL url = new URL(String.format(DatabaseManager.acceptFriend, cid, username));
			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

			String inputLine;
			String line = "";
			while ((inputLine = in.readLine()) != null) {
				line = inputLine;
			}
			in.close();
			
			return line.length()>0;

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}
	
	public static boolean friendAdd(String cid, String username, String message) {
		message = message.replace(" ", "%20");
		if (Kilo.kilo().client == null) {
			return false;
		}
		try {
			URL url = new URL(String.format(DatabaseManager.addFriend, cid, username, message));
			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

			String inputLine;
			String line = "";
			while ((inputLine = in.readLine()) != null) {
				line = inputLine;
			}
			in.close();
			
			return true;//line.length()>0;

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}
	
	public static boolean sendServerInvite(String cid, String ip, String username, String message) {
		message = message.replace(" ", "%20");
		if (Kilo.kilo().client == null) {
			return false;
		}
		try {
			URL url = new URL(String.format(DatabaseManager.invite, cid, ip, username, message));
			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

			String inputLine;
			String line = "";
			while ((inputLine = in.readLine()) != null) {
				line = inputLine;
			}
			in.close();
			
			return line.length()>0;

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}
	
	public static List<User> getUserExtras() {
		if (Kilo.kilo().client == null) {
			return null;
		}
		try {
			URL url = new URL(String.format(DatabaseManager.extras));
			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

			String inputLine;
			String line = "";
			while ((inputLine = in.readLine()) != null) {
				line = inputLine;
			}
			in.close();
			
			List<User> list = new ArrayList<User>();
			
			JSONArray ja = new JSONArray(line);
			for(int i = 0; i < ja.length(); i++) {
				JSONObject jo = ja.getJSONObject(i);
				String minecraftName = null, ircTag = "", earSize = "l";
				boolean sizeEnabled = false, earsEnabled = false, flipEnabled = false, ircEnabled = false;
				float size = 1;

				if (jo.has("ingame_name")) { minecraftName = jo.getString("ingame_name"); }
				if (jo.has("size_enable")) { sizeEnabled = Boolean.parseBoolean(jo.getString("size_enable")); }
				if (jo.has("ears_enable")) { earsEnabled = Boolean.parseBoolean(jo.getString("ears_enable")); }
				if (jo.has("size_variable")) { size = Float.parseFloat(jo.getString("size_variable")); }
				if (jo.has("ears_variable")) { earSize = jo.getString("ears_variable").toLowerCase(); }
				if (jo.has("upsidedown_enable")) { flipEnabled = Boolean.parseBoolean(jo.getString("upsidedown_enable")); }
				if (jo.has("irc_enable")) { ircEnabled = Boolean.parseBoolean(jo.getString("irc_enable")); }
				if (jo.has("irc_variable")) { ircTag = jo.get("irc_variable").toString(); }
				
				if (minecraftName != null) {
					list.add(new User(minecraftName, sizeEnabled, earsEnabled, flipEnabled, ircEnabled, size, earSize, ircTag));
				}
			}
			return list;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	public static List<Playlist> getPlaylists(String cid) {
		List<Playlist> list = new ArrayList<Playlist>();
		try {
			URL url = new URL(String.format(DatabaseManager.clientPlaylists, cid));
			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

			String inputLine;
			String line = "";
			while ((inputLine = in.readLine()) != null) {
				line = inputLine;
			}
			in.close();

			JSONArray ja = new JSONArray(line);
			for(int i = 0; i < ja.length(); i++) {
				JSONObject jo = ja.getJSONObject(i);
				String name = null, id = null;

				id = jo.getString("id");
				name = jo.getString("name");

				list.add(new Playlist(name, id));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return list;
	}
	
	public static List<Song> getPlaylistSongs(String cid, String pid) {
		List<Song> list = new ArrayList<Song>();
		try {
			URL url = new URL(String.format(DatabaseManager.playlistSongs, cid, pid));
			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
			
			String inputLine;
			String line = "";
			while ((inputLine = in.readLine()) != null) {
				line = inputLine;
			}
			in.close();

			JSONArray ja = new JSONArray(line);
			for(int i = 0; i < ja.length(); i++) {
				JSONObject jo = ja.getJSONObject(i);
				int id, duration;
				String title = null, artwork = null;
				boolean starred = false;
				
				id = (int)jo.getLong("id");
				title = jo.getString("title");
				if (!String.valueOf(jo.get("artwork")).equalsIgnoreCase("null")) {
					artwork = jo.getString("artwork");
				}
				duration = (int)jo.getLong("duration");
				if (jo.has("starred")) {
					starred = jo.getBoolean("starred");
				}

				list.add(new Song(id, title, artwork, duration, starred));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return list;
	}
	
	public static List<Song> getSearchSongs(String cid, String tags) {
		tags = tags.replace(" ", "+");
		List<Song> list = new ArrayList<Song>();
		try {
			URL url = new URL(String.format(DatabaseManager.musicSearch, cid, tags));
			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

			String inputLine;
			String line = "";
			while ((inputLine = in.readLine()) != null) {
				line = inputLine;
			}
			in.close();

			JSONArray ja = new JSONArray(line);
			for(int i = 0; i < ja.length(); i++) {
				JSONObject jo = ja.getJSONObject(i);
				int id, duration;
				String title = null, artwork = null;
				boolean starred = false;
				
				id = (int)jo.getLong("id");
				title = jo.getString("title");
				if (!String.valueOf(jo.get("artwork")).equalsIgnoreCase("null")) {
					artwork = jo.getString("artwork");
				}
				duration = (int)jo.getLong("duration");
				if (jo.has("starred")) {
					starred = jo.getBoolean("starred");
				}

				list.add(new Song(id, title, artwork, duration, starred));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return list;
	}
	
	public static List<Song> getSongCharts(String cid) {
		List<Song> list = new ArrayList<Song>();
		try {
			URL url = new URL(String.format(DatabaseManager.musicCharts, cid));
			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

			String inputLine;
			String line = "";
			while ((inputLine = in.readLine()) != null) {
				line = inputLine;
			}
			in.close();

			JSONArray ja = new JSONArray(line);
			for(int i = 0; i < ja.length(); i++) {
				JSONObject jo = ja.getJSONObject(i);
				int id, duration;
				String title = null, artwork = null;
				boolean starred = false;
				
				id = (int)jo.getLong("id");
				title = jo.getString("title");
				if (!String.valueOf(jo.get("artwork")).equalsIgnoreCase("null")) {
					artwork = jo.getString("artwork");
				}
				duration = (int)jo.getLong("duration");
				if (jo.has("starred")) {
					starred = jo.getBoolean("starred");
				}
				
				list.add(new Song(id, title, artwork, duration, starred));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return list;
	}
	
	public static List<Song> getSongStars(String cid) {
		List<Song> list = new ArrayList<Song>();
		try {
			URL url = new URL(String.format(DatabaseManager.musicGetStar, cid));
			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

			String inputLine;
			String line = "";
			while ((inputLine = in.readLine()) != null) {
				line = inputLine;
			}
			in.close();

			JSONArray ja = new JSONArray(line);
			for(int i = 0; i < ja.length(); i++) {
				JSONObject jo = ja.getJSONObject(i);
				int id, duration;
				String title = null, artwork = null;
				boolean starred = true;
				
				id = (int)jo.getLong("id");
				title = jo.getString("title");
				if (!String.valueOf(jo.get("artwork")).equalsIgnoreCase("null")) {
					artwork = jo.getString("artwork");
				}
				duration = (int)jo.getLong("duration");

				list.add(new Song(id, title, artwork, duration, starred));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return list;
	}
	
	public static String getSongStream(int sid) {
		try {
			URL url = new URL(String.format(DatabaseManager.musicPlay, String.valueOf(sid)));
			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

			String inputLine;
			String line = "";
			while ((inputLine = in.readLine()) != null) {
				line = inputLine;
			}
			in.close();
			
			JSONObject jo = new JSONObject(line);
			String u = null, status = null;
			
			if (jo.getString("status").equalsIgnoreCase("error")) {
				return null;
			} else {
				u = jo.getString("url");
				return u;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	public static boolean playlistAdd(String cid, String n) {
		n = n.replace(" ", "%20");
		try {
			URL url = new URL(String.format(DatabaseManager.playlistAdd, cid, n));
			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

			String inputLine;
			String line = "";
			while ((inputLine = in.readLine()) != null) {
				line = inputLine;
			}
			in.close();
			
			if (line.equalsIgnoreCase("Created")) {
				return true;
			} else {
				return false;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}
	
	public static boolean playlistRemove(String cid, String pid) {
		try {
			URL url = new URL(String.format(DatabaseManager.playlistRemove, cid, pid));
			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}
	
	public static boolean playlistRename(String cid, String pid, String pn) {
		pn = pn.replace(" ", "%20");
		try {
			URL url = new URL(String.format(DatabaseManager.playlistRename, cid, pid, pn));
			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}
	
	public static boolean songAddStar(String cid, String sid) {
		try {
			URL url = new URL(String.format(DatabaseManager.musicAddStar, cid, sid));
			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}
	
	public static boolean songRemoveStar(String cid, String sid) {
		try {
			URL url = new URL(String.format(DatabaseManager.musicRemoveStar, cid, sid));
			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}
	
	public static boolean playlistAddSong(String cid, String pid, String sid) {
		try {
			URL url = new URL(String.format(DatabaseManager.playlistAddSong, cid, pid, sid));
			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}
	
	public static boolean playlistRemoveSong(String cid, String pid, String sid) {
		try {
			URL url = new URL(String.format(DatabaseManager.playlistRemoveSong, cid, pid, sid));
			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}
	
	public static boolean allowedToHack(String cid, String ip) {
		ip = ip.replace(" ", "%20");
		try {
			URL url = new URL(String.format(DatabaseManager.allowedToHack, cid, ip));
			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
			
			String inputLine;
			String line = "";
			while ((inputLine = in.readLine()) != null) {
				line = inputLine;
			}
			in.close();
			
			JSONObject jo = new JSONObject(line);
			return jo.getBoolean("cheats");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return true;
	}
}
