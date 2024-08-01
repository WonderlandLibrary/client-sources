package wtf.diablo.client.friend;

import com.google.gson.JsonObject;
import wtf.diablo.client.core.impl.Diablo;
import wtf.diablo.client.util.Constants;

import java.io.*;
import java.util.ArrayList;

public final class FriendRepository {
    private final ArrayList<Friend> friends;
    private final File friendsFile;

    public FriendRepository(final File friendsFile) {
        this.friends = new ArrayList<>();
        this.friendsFile = friendsFile;

        Runtime.getRuntime().addShutdownHook(new Thread(this::saveFriends));
    }

    public void initialize() {
        try {
            if (!friendsFile.exists()) {
                this.saveFriends();
            } else {
                final JsonObject jsonObject = Constants.GSON.fromJson(new FileReader(friendsFile), JsonObject.class);

                jsonObject.entrySet().forEach(entry -> this.friends.add(new Friend(entry.getKey(), entry.getValue().getAsString())));
            }
        } catch (final FileNotFoundException e) {
            this.saveFriends();

            throw new RuntimeException(e);
        }
    }

    public void addFriend(final String name) {
        this.friends.add(new Friend(name));
    }

    public void removeFriend(final String name) {
        this.friends.removeIf(friend -> friend.getName().equalsIgnoreCase(name));
    }

    public boolean isFriend(final String name) {
        return this.friends.stream().anyMatch(friend -> friend.getName().equalsIgnoreCase(name));
    }

    public ArrayList<Friend> getFriends() {
        return this.friends;
    }

    private void saveFriends() {
        final JsonObject jsonObject = new JsonObject();
        Diablo.getInstance().getFriendRepository().getFriends().forEach(friend -> jsonObject.addProperty(friend.getName(), friend.getAlias()));

        try {
            final String result = Constants.GSON.toJson(jsonObject);

            final FileWriter fileWriter = new FileWriter(friendsFile);
            fileWriter.write(result);
            fileWriter.close();
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }
}
