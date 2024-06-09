package me.valk.manager.managers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import me.valk.Vital;
import me.valk.manager.Manager;
import me.valk.manager.ManagerFileHandler;
import me.valk.manager.managers.FriendManager.Friend;

public class FriendManager extends Manager<Friend> {

	public FriendManager() {
		this.setFileHandler(new ManagerFileHandler(){

			@Override
			public void save(File file) throws IOException {
				PrintWriter exception = new PrintWriter(new FileWriter(file));
				Gson gson = new GsonBuilder().setPrettyPrinting().create();
				
				String data = gson.toJson(Vital.getManagers().getFriendManager());

				exception.println(data);

				exception.close();
			}

			@Override
			public void load(File file) throws IOException {
				BufferedReader bufferedreader = new BufferedReader(new FileReader(file));

				FriendManager manager = new GsonBuilder().setPrettyPrinting().create().fromJson(bufferedreader, FriendManager.class);
				
				Vital.getManagers().getFriendManager().getContents().clear();
				
				for(Friend friend : manager.getContents()){
					Vital.getManagers().getFriendManager().addContent(friend);
				}

				bufferedreader.close();
			}
		
		}, "friend");
	}
	
	public boolean hasFriend(String name){
		for(Friend friend : getContents()){
			if(friend.getName().equalsIgnoreCase(name)){
				return true;
			}
		}
		
		return false;
	}
	
	public Friend getFriend(String name){
		for(Friend friend : getContents()){
			if(friend.getName().equalsIgnoreCase(name)){
				return friend;
			}
		}
		
		return null;
	}
	
	public static class Friend {
		
		private String name;
		private String nickname;
		
		public Friend(String name, String nickname) {
			this.name = name;
			this.nickname = nickname;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getNickname() {
			return nickname;
		}

		public void setNickname(String nickname) {
			this.nickname = nickname;
		}
				
	}
	
}
