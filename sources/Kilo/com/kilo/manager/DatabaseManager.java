package com.kilo.manager;

import java.net.URL;

import com.kilo.util.Resources;

public class DatabaseManager {
	
	public static final String domain = "meetkilo";
	public static final String api = "http://api."+domain+".com/2015-05-15";

	public static final String serverImage = api+"/multiplayer/server-image/%s";
	
	public static final String login = api+"/login/%s/%s";
	public static final String createAccount = api+"/create-account/?1234567=%s&2234567=%s&3234567=%s&f_name=%s&info=%s";
	
	public static final String clientDetails = api+"/details/%s/";
	public static final String clientServers = api+"/multiplayer/list/%s";
	public static final String clientFriends = api+"/friends/view/?client_id=%s";
	public static final String clientMessages = api+"/messages/view/?client_id=%s";
	public static final String clientPlaylists = api+"/music/playlists.php?client_id=%s&action=list";

	public static final String playlistAdd = api+"/music/playlists.php?client_id=%s&action=create&name=%s";
	public static final String playlistRemove = api+"/music/playlists.php?client_id=%s&action=delete&playlist=%s";
	public static final String playlistRename = api+"/music/playlists.php?client_id=%s&action=rename&playlist=%s&newname=%s";
	
	public static final String playlistSongs = api+"/music/playlists.php?client_id=%s&action=view&playlist=%s";
	public static final String playlistAddSong = api+"/music/playlists.php?client_id=%s&action=addsong&playlist=%s&songid=%s";
	public static final String playlistRemoveSong = api+"/music/playlists.php?client_id=%s&action=deletesong&playlist=%s&songid=%s";

	public static final String musicGetStar = api+"/music/star.php?client_id=%s&action=view";
	public static final String musicAddStar = api+"/music/star.php?client_id=%s&action=star&song=%s";
	public static final String musicRemoveStar = api+"/music/star.php?client_id=%s&action=unstar&song=%s";
	
	public static final String musicPlay = api+"/music/get_stream.php?id=%s";
	public static final String musicHomeImage = api+"/music/home.png";
	public static final String musicCharts = api+"/music/charts.php?client_id=%s";
	public static final String musicSearch = api+"/music/search.php?client_id=%s&t=%s";
	
	public static final String addServer = api+"/add-multiplayerserver/?client_id=%s&server_ip=%s&server_port=%s";
	public static final String delServer = api+"/del-multiplayerserver/?client_id=%s&ip=%s";
	public static final String moveServer = api+"/reorder_multiplayer/?client_id=%s&ip=%s&method=%s";

	public static final String addFriend = api+"/friends/add/?client_id=%s&username=%s&message=%s";
	public static final String acceptFriend = api+"/friends/accept/?client_id=%s&username=%s";

	public static final String newMessage = api+"/messages/new/?client_id=%s&username=%s&message=%s";
	
	public static final String invite = api+"/invite/?client_id=%s&username=%s&ip=%s&message=%s";
	
	public static final String hideStatus= api+"/hide-status/?client_id=%s";
	public static final String getStatus = api+"/my-status/?client_id=%s";
	
	public static final String allowedToHack = api+"/session/server.php?clientid=%s&server=%s";
	
	public static final String latestActivity = api+"/latest-activity/?client_id=%s";
	public static final String latestActivityDelete = api+"/latest-activity/delete-activity.php?client_id=%s&id=%s";
	public static final String updates = api+"/updates/?client_id=%s&status=%s&ip=%s";
	
	public static final String pods = "http://"+domain+".com/hosting";
	public static final String forums = "http://forums."+domain+".com";
	public static final String account = "https://"+domain+".com/account";
	public static final String accountAbout = "https://"+domain+".com/about/my";
	public static final String terms = "https://"+domain+".com/about/tos";
	
	public static final String emailSupport = "support@"+domain+".com";
	public static final String manageAddons = "https://"+domain+".com/account/addons";
	public static final String manageAddonsLogin = "https://"+domain+".com/account/addons/?client_id=%s";
	public static final String profile = "https://"+domain+".com/account/friends/profile/%s";

	public static final String head = "https://mcapi.ca/avatar/2d/%s";
	public static final String model = "https://mcapi.ca/skin/2d/%s";
	
	public static final String capes = "http://static."+domain+".com/capes/%s.png";
	public static final String extras = api+"/extras/?names=all";
	
	public static final String irc = "irc.meetkilo.com";
	public static final String ircChannel = "#KiLOClient";
	
	public static final String notificationSound = api+"/assets/notification.mp3";
}
