package us.dev.direkt.file.internal.files;

import com.google.common.io.Files;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import us.dev.direkt.Direkt;
import us.dev.direkt.file.internal.AbstractClientFile;
import us.dev.direkt.file.internal.FileData;
import us.dev.direkt.module.internal.core.friends.handler.FriendManager;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Foundry
 */
@FileData(fileName = "friends")
public class FriendsFile extends AbstractClientFile {
    private final Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .setPrettyPrinting()
            .create();

    @Override
    public void load() throws IOException {
        try (Reader reader = new BufferedReader(new FileReader(this.getFile()))){
            final Type serializedObject = new TypeToken<ArrayList<FriendManager.Friend>>(){}.getType();
            final List<FriendManager.Friend> serializedData = gson.fromJson(reader, serializedObject);
            if (serializedData != null)
                serializedData.forEach(friend -> Direkt.getInstance().getFriendManager().addFriendNoSave(friend.getName(), friend.getAlias()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void save() throws IOException {
        final List<FriendManager.Friend> serializedData = Direkt.getInstance().getFriendManager().getFriendsList().stream()
                .filter(f -> f != null)
                .collect(Collectors.toList());
        try {
            Files.write(gson.toJson(serializedData).getBytes("UTF-8"), this.getFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
