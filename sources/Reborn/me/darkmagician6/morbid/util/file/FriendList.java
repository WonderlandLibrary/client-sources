package me.darkmagician6.morbid.util.file;

import java.util.*;
import me.darkmagician6.morbid.*;
import net.minecraft.client.*;
import me.darkmagician6.morbid.util.*;
import java.io.*;

public class FriendList
{
    public ArrayList friends;
    private ArrayList alias;
    private File friendsList;
    
    public FriendList() {
        this.friends = new ArrayList();
        this.alias = new ArrayList();
        MorbidWrapper.mcObj();
        this.friendsList = new File(Minecraft.b(), "/Morbid/friends.txt");
    }
    
    public boolean isFriend(final sq e) {
        final String s = lf.a(e.bS);
        return this.friends.contains(s);
    }
    
    public void addFriend(final String name, final String fake) {
        this.friends.add(name);
        this.alias.add(fake);
        this.saveFriends();
    }
    
    public void delFriend(final String s) {
        for (int i = 0; i < this.friends.size(); ++i) {
            if (this.friends.get(i).equals(s)) {
                this.friends.remove(i);
                this.alias.remove(i);
            }
        }
    }
    
    public String replaceNameWithColor(String s) {
        if (!this.friendsList.exists()) {
            return s;
        }
        for (int i = 0; i < this.friends.size(); ++i) {
            s = s.replaceAll(this.friends.get(i), "§9" + this.alias.get(i) + "§r");
        }
        return s;
    }
    
    public String replaceName(String s) {
        if (!this.friendsList.exists()) {
            return s;
        }
        for (int i = 0; i < this.friends.size(); ++i) {
            s = s.replaceAll(this.friends.get(i), this.alias.get(i));
        }
        return s;
    }
    
    public String replaceNameForChat(String s) {
        if (!this.friendsList.exists()) {
            return s;
        }
        for (int i = 0; i < this.alias.size(); ++i) {
            if (MorbidWrapper.getPlayer().a.k.containsKey(this.friends.get(i))) {
                s = s.replaceAll("-" + this.alias.get(i), this.friends.get(i));
            }
        }
        System.out.println(s);
        return s;
    }
    
    public void saveFriends() {
        if (!this.friendsList.exists()) {
            try {
                this.friendsList.createNewFile();
                this.friends.add("DarkMagician6");
                this.alias.add("DarkMagician");
                this.saveFriends();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            final BufferedWriter writer = new BufferedWriter(new FileWriter(this.friendsList));
            if (this.friends.size() > 0) {
                for (int i = 0; i < this.friends.size(); ++i) {
                    writer.write(this.friends.get(i) + ":" + this.alias.get(i) + "\r\n");
                }
            }
            writer.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        MorbidHelper.gc();
    }
    
    public void loadFriends() {
        if (this.friendsList.exists()) {
            try {
                final BufferedReader reader = new BufferedReader(new FileReader(this.friendsList));
                String s = "";
                while ((s = reader.readLine()) != null) {
                    final String[] split = s.split(":");
                    final String s2 = split[0];
                    final String s3 = split[1];
                    this.friends.add(s2);
                    this.alias.add(s3);
                }
                reader.close();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        MorbidHelper.gc();
    }
}
