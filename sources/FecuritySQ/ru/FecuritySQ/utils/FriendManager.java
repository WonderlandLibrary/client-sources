package ru.FecuritySQ.utils;

import net.minecraft.client.Minecraft;
import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class FriendManager {

    private final List<Friend> friends = new ArrayList<>();
    public static final File friendFile = new File(Minecraft.getInstance().gameDir, "\\FecuritySQ\\friends.w");

    public void init() throws Exception {
        if (!friendFile.exists()) {
            friendFile.createNewFile();
        } else {
            readFriends();
        }
    }

    public void addFriend(String name) {
        friends.add(new Friend(name));
        updateFile();
    }
    public Friend getFriend(String friend) {
        return friends.stream().filter(isFriend -> isFriend.getName().equals(friend)).findFirst().get();
    }
    public boolean isFriend(String friend) {
        return friends.stream().anyMatch(isFriend -> isFriend.getName().equals(friend));
    }

    public void removeFriend(String name) {
        friends.removeIf(friend -> friend.getName().equalsIgnoreCase(name));
    }

    public void clearFriend() {
        friends.clear();
        updateFile();
    }

    public List<Friend> getFriends() {
        return this.friends;
    }

    public void updateFile() {
        try {
            StringBuilder builder = new StringBuilder();
            friends.forEach(friend -> builder.append(friend.getName()).append("\n"));
            Files.write(friendFile.toPath(), builder.toString().getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void readFriends() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new DataInputStream(new FileInputStream(friendFile.getAbsolutePath()))));
            String line;
            while ((line = reader.readLine()) != null) {
                friends.add(new Friend(line));
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
