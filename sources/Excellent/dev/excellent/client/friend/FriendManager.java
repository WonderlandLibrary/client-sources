package dev.excellent.client.friend;

import dev.excellent.Excellent;
import dev.excellent.impl.util.file.FileManager;
import i.gishreloaded.protection.annotation.Native;

import java.io.File;
import java.util.ArrayList;

public class FriendManager extends ArrayList<Friend> {
    public static File FRIEND_DIRECTORY;

    @Native
    public void init() {
        FRIEND_DIRECTORY = new File(FileManager.DIRECTORY, "friends");
        if (!FRIEND_DIRECTORY.exists()) {
            if (FRIEND_DIRECTORY.mkdir()) {
                System.out.println("Папка с списком друзей успешно создана.");
            } else {
                System.out.println("Произошла ошибка при создании папки с списком друзей.");
            }
        }

        Excellent.getInst().getEventBus().register(this);

    }

    public FriendFile get() {
        final File file = new File(FRIEND_DIRECTORY, "friends." + Excellent.getInst().getInfo().getNamespace());
        return new FriendFile(file);
    }

    public void set() {
        final File file = new File(FRIEND_DIRECTORY, "friends." + Excellent.getInst().getInfo().getNamespace());
        FriendFile friendFile = get();
        if (friendFile == null) {
            friendFile = new FriendFile(file);
        }
        friendFile.write();
    }


    public void addFriend(String name) {
        this.add(new Friend(name));
        set();
    }

    public Friend getFriend(String friend) {
        return this.stream().filter(isFriend -> isFriend.getName().equalsIgnoreCase(friend)).findFirst().orElse(null);
    }

    public boolean isFriend(String friend) {
        return this.stream().anyMatch(isFriend -> isFriend.getName().equalsIgnoreCase(friend));
    }

    public void removeFriend(String name) {
        this.removeIf(friend -> friend.getName().equalsIgnoreCase(name));
        set();
    }

    public void clearFriends() {
        this.clear();
        set();
    }

}
