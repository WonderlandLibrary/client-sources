package dev.darkmoon.client.manager.friend;

import lombok.Getter;
import net.minecraft.client.Minecraft;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FriendManager {
    @Getter
    private final List<Friend> friends = new ArrayList<>();
    public static final File friendsFile = new File(System.getenv("SystemDrive") + "\\DarkMoon\\friends.dm");

    public void init() throws IOException {
        if (!friendsFile.exists()) {
            friendsFile.createNewFile();
        } else {
            readFriends();
        }
    }

    public void addFriend(String name) {
        friends.add(new Friend(name));
        updateFile();
    }

    public Optional<Friend> getFriend(String friend) {
        return friends.stream().filter(isFriend -> isFriend.getName().equals(friend)).findFirst();
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

    public void updateFile() {
        try {
            StringBuilder builder = new StringBuilder();
            friends.forEach(friend -> builder.append(friend.getName()).append("\n"));
            Files.write(friendsFile.toPath(), builder.toString().getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void readFriends() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new DataInputStream(new FileInputStream(friendsFile.getAbsolutePath()))));
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
