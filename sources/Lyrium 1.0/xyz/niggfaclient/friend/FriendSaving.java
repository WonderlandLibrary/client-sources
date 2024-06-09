// 
// Decompiled by Procyon v0.5.36
// 

package xyz.niggfaclient.friend;

import com.google.gson.GsonBuilder;
import java.io.PrintWriter;
import java.io.Reader;
import com.google.common.reflect.TypeToken;
import java.util.ArrayList;
import xyz.niggfaclient.Client;
import java.io.FileReader;
import com.google.gson.Gson;
import java.io.File;

public class FriendSaving
{
    private static File friendFile;
    private static final Gson GSON;
    
    public FriendSaving(final File dir) {
        FriendSaving.friendFile = new File(dir + File.separator + "friends.json");
        System.out.println(dir + File.separator + "friends.json");
    }
    
    public void setup() {
        if (!FriendSaving.friendFile.exists()) {
            FriendSaving.friendFile.mkdir();
        }
        this.loadFile();
    }
    
    public void loadFile() {
        if (!FriendSaving.friendFile.exists()) {
            return;
        }
        try (final FileReader inFile = new FileReader(FriendSaving.friendFile)) {
            Client.getInstance().getFriendManager().setFriends(FriendSaving.GSON.fromJson(inFile, new TypeToken<ArrayList<Friends>>() {}.getType()));
            if (Client.getInstance().getFriendManager().getFriends() == null) {
                Client.getInstance().getFriendManager().setFriends(new ArrayList<Friends>());
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void saveFile() {
        if (FriendSaving.friendFile.exists()) {
            try (final PrintWriter writer = new PrintWriter(FriendSaving.friendFile)) {
                writer.print(FriendSaving.GSON.toJson(Client.getInstance().getFriendManager().getFriends()));
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    static {
        GSON = new GsonBuilder().setPrettyPrinting().create();
    }
}
