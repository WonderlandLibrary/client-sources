package com.kilo.users;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import net.minecraft.client.Minecraft;

import com.kilo.Kilo;
import com.kilo.manager.DatabaseManager;
import com.kilo.manager.UpdateManager;
import com.kilo.render.TextureImage;
import com.kilo.ui.inter.slotlist.part.Playlist;
import com.kilo.util.Resources;
import com.kilo.util.ServerUtil;

public class UserControl {
	public String clientID, kiloName, minecraftName, email, gameStatus, ircTag;
	public Player minecraftPlayer;
	public boolean isPremium;
	public int updateTime;
	public TextureImage kiloHead;
	
	public List<Playlist> playlists = new CopyOnWriteArrayList<Playlist>();
	
	public UserControl(String clientID, String kiloName, String minecraftName, String isPremium, String email, String gameStatus, String ircTag, String updateTime) {
		this.clientID = clientID;
		this.kiloName = kiloName;
		this.minecraftName = minecraftName;
		this.minecraftPlayer = new Player(Minecraft.getMinecraft().getSession().getProfile(), true);
		this.isPremium = Boolean.parseBoolean(isPremium);
		this.email = email;
		this.gameStatus = gameStatus;
		this.ircTag = ircTag;
		this.updateTime = Integer.parseInt(updateTime);
	}

	public void addPlaylist(String n, String i) {
		for(Playlist p : playlists) {
			if (p.id.equalsIgnoreCase(i)) {
				return;
			}
		}
		playlists.add(new Playlist(n, i));
	}
	
	public List<Playlist> getPlaylists() {
		return playlists;
	}
	
	public void addPlaylist(Playlist f) {
		playlists.add(f);
	}
	
	public void addPlaylist(int index, Playlist f) {
		playlists.add(index, f);
	}
	
	public void removePlaylist(Playlist f) {
		playlists.remove(f);
	}
	
	public void removePlaylist(int index) {
		playlists.remove(playlists.get(index));
	}
	
	public Playlist getPlaylist(int index) {
		if (playlists.size() == 0 || index < 0 || index >= playlists.size()) {
			return null;
		}
		return playlists.get(index);
	}
	
	public Playlist getPlaylist(String i) {
		for(Playlist p : playlists) {
			if (p.id.equalsIgnoreCase(i)) {
				return p;
			}
		}
		return null;
	}
	
	public int getPlaylistIndex(Playlist f) {
		return playlists.indexOf(f);
	}
	
	public int getPlaylistsSize() {
		return getPlaylists().size();
	}
}
