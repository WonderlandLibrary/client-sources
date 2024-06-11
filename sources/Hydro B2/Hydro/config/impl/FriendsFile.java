package Hydro.config.impl;

import java.io.File;
import java.util.Arrays;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import Hydro.config.IFile;
import Hydro.friend.Friend;
import Hydro.friend.FriendManager;

public class FriendsFile implements IFile {
	
	//TODO doesn't save anything at all for some reason.
	
	private File file;
	FriendManager friends2 = new FriendManager();
	
	@Override
	public void save(Gson gson) {
		JsonArray array = new JsonArray();
				
		for(Friend friend : friends2.getContents()) {
			JsonObject object = new JsonObject();
			
			object.addProperty("name", friend.getName());
			object.addProperty("alias", friend.getAlias());
			
			array.add(object);
		}
		
        writeFile(gson.toJson(array), file);
	}

	@Override
	public void load(Gson gson) {
		if (!file.exists()) {
            return;
        }
        Friend[] friends = gson.fromJson(readFile(file), Friend[].class);
        if (friends != null) {
            Arrays.stream(friends).forEach(friend -> friends2.addFriend(friend.getName(), friend.getAlias()));
        }
	}

	@Override
	public void setFile(File root) {
		file = new File(root, "/friends.txt");
	}

}
