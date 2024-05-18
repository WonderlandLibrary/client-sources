/*
 * Decompiled with CFR 0_118.
 */
package pw.vertexcode.nemphis.friend;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import pw.vertexcode.nemphis.Nemphis;
import pw.vertexcode.nemphis.friend.Friend;
import pw.vertexcode.util.ILoadable;

public class FriendManager
implements ILoadable {
    private List<Friend> friends = new ArrayList<Friend>();

    public void reload() {
        this.friends.clear();
        try {
            this.load();
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Friend> getFriends() {
        return this.friends;
    }

    public void newFriend(Friend friend) {
        this.friends.add(friend);
        System.out.println("-> Loaded Friend " + friend.getUsername());
    }

    public void removeFriend(Friend friend) {
        this.friends.remove(friend);
        System.out.println("-> Removed Friend " + friend.getUsername());
    }

    @Override
    public void load() throws IOException, ClassNotFoundException {
        File frFile = new File(Nemphis.instance.directory, "friends.cfg");
        if (frFile.exists()) {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(frFile));
            String result = "";
            while ((result = bufferedReader.readLine()) != null) {
                String[] spl = result.split(":");
                this.newFriend(new Friend(spl[0], spl[1]));
            }
            bufferedReader.close();
        }
    }

    @Override
    public void save() throws IOException {
        File frFile = new File(Nemphis.instance.directory, "friends.cfg");
        frFile.createNewFile();
        FileWriter fw = new FileWriter(frFile);
        int i = 0;
        while (i < this.getFriends().size()) {
            if (this.friends.get(i).getAlias() != null) {
                fw.write(String.valueOf(this.friends.get(i).getUsername()) + ":" + this.friends.get(i).getAlias());
            } else {
                fw.write(String.valueOf(this.friends.get(i).getUsername()) + ":" + this.friends.get(i).getUsername());
            }
            ++i;
        }
        fw.close();
    }

    public String replaceAllNames(String p_13345_1) {
        for (Friend friend : this.getFriends()) {
            p_13345_1 = p_13345_1.replaceAll(friend.getUsername(), "\u00a73" + friend.getAlias());
        }
        return p_13345_1;
    }

    public boolean isRegisteredAsFriend(String username) {
        for (Friend f : this.getFriends()) {
            if (!f.getUsername().equalsIgnoreCase(username)) continue;
            return true;
        }
        return false;
    }
}

