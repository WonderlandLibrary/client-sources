package com.kilo.ui.inter.slotlist.part;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.kilo.Kilo;
import com.kilo.util.ServerUtil;

public class Playlist {

	public String name, id;
	public List<Song> songs = new CopyOnWriteArrayList<Song>();
	
	public Playlist(String n, String i) {
		name = n;
		id = i;
		loadSongs();
	}

	public void loadSongs() {
		new Thread() {
			@Override
			public void run() {
				songs = ServerUtil.getPlaylistSongs(Kilo.kilo().client.clientID, id);
			}
		}.start();
	}
	
	public List<Song> getList() {
		return songs;
	}
	
	public void addSong(int i, String t, String a, int d, boolean s) {
		for(Song p : songs) {
			if (p.id == i) {
				return;
			}
		}
		songs.add(new Song(i, t, a, d, s));
	}
	
	public void addSong(Song f) {
		songs.add(f);
	}
	
	public void addSong(int index, Song f) {
		songs.add(index, f);
	}
	
	public void removeSong(Song f) {
		songs.remove(f);
	}
	
	public void removeSong(int index) {
		songs.remove(songs.get(index));
	}
	
	public Song getSong(int index) {
		if (songs.size() == 0) {
			return null;
		}
		return songs.get(index);
	}
	
	public Song getSongByID(int i) {
		for(Song p : songs) {
			if (p.id == i) {
				return p;
			}
		}
		return null;
	}
	
	public int getIndex(Song f) {
		return songs.indexOf(f);
	}
	
	public int getSize() {
		return getList().size();
	}
}