package com.kilo.music;

import jaco.mp3.player.MP3Player;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;

import com.kilo.ui.inter.slotlist.part.Song;
import com.kilo.util.ServerUtil;

public class MusicHandler {

	public static MP3Player player;
	public static Song currentSong;
	public static boolean isPlaying = false;
	public static List<Song> currentSongList = new CopyOnWriteArrayList<Song>();
	public static float vol;
	
	public static void resume() {
		if (player != null) {setVolume(vol);
			player.play();
			isPlaying = true;
		}
	}
	
	public static void play(final List<Song> songList, final Song song) {
		MusicHandler.stop();
		new Thread() {
			@Override
			public void run() {
				try {
					String s = ServerUtil.getSongStream(song.id);
					if (s ==  null) {
						sayChat("Song could not be played");
						return;
					}
					URL u = new URL(s);
					player = new MP3Player(u);
					player.setRepeat(false);
					currentSong = song;
					currentSongList = songList;
					setVolume(vol);
					player.play();
					isPlaying = true;
				} catch (Exception e) {
					sayChat("Song could not be played");
					currentSong = null;
					e.printStackTrace();
				}
			}
		}.start();
	}
	
	public static void pause() {
		if (player != null) {
			player.pause();
			isPlaying = false;
		}
	}
	
	public static void next() {
		if (player != null) {
			int curInd = currentSongList.indexOf(currentSong);
			if (curInd == currentSongList.size()-1) {
				stop();
				return;
			}
			play(currentSongList, currentSongList.get(curInd+1));
		}
	}
	
	public static void prev() {
		if (player != null) {
			int curInd = currentSongList.indexOf(currentSong);
			if (curInd == 0) {
				stop();
				return;
			}
			play(currentSongList, currentSongList.get(curInd-1));
		}
	}
	
	public static void stop() {
		if (player != null) {
			player.stop();
			player = null;
			currentSong = null;
			isPlaying = false;
		}
	}
	
	public static void setVolume(float volume) {
		vol = volume;
		if (player != null) {
			player.setVolume((int)vol);
		}
	}
	
	public static void sayChat(String s) {
		ChatComponentText chat = new ChatComponentText("");
		chat.appendSibling(new ChatComponentText("[").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.GRAY)));
		chat.appendSibling(new ChatComponentText("Music").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED)));
		chat.appendSibling(new ChatComponentText("] ").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.GRAY)));
		chat.appendSibling(new ChatComponentText(s).setChatStyle(new ChatStyle().setColor(EnumChatFormatting.YELLOW)));
		Minecraft.getMinecraft().thePlayer.addChatMessage(chat);
	}
}
