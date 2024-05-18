package me.enrythebest.reborn.cracked.util.file;

import java.util.*;
import me.enrythebest.reborn.cracked.*;
import net.minecraft.client.*;
import net.minecraft.src.*;
import me.enrythebest.reborn.cracked.util.*;
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
        this.friendsList = new File(Minecraft.getMinecraftDir(), "/Morbid/friends.txt");
    }
    
    public boolean isFriend(final EntityPlayer var1) {
        final String var2 = StringUtils.stripControlCodes(var1.username);
        return this.friends.contains(var2);
    }
    
    public void addFriend(final String var1, final String var2) {
        this.friends.add(var1);
        this.alias.add(var2);
        this.saveFriends();
    }
    
    public void delFriend(final String var1) {
        for (int var2 = 0; var2 < this.friends.size(); ++var2) {
            if (this.friends.get(var2).equals(var1)) {
                this.friends.remove(var2);
                this.alias.remove(var2);
            }
        }
    }
    
    public String replaceNameWithColor(String var1) {
        if (!this.friendsList.exists()) {
            return var1;
        }
        for (int var2 = 0; var2 < this.friends.size(); ++var2) {
            var1 = var1.replaceAll(this.friends.get(var2), "§9" + this.alias.get(var2) + "§r");
        }
        return var1;
    }
    
    public String replaceName(String var1) {
        if (!this.friendsList.exists()) {
            return var1;
        }
        for (int var2 = 0; var2 < this.friends.size(); ++var2) {
            var1 = var1.replaceAll(this.friends.get(var2), this.alias.get(var2));
        }
        return var1;
    }
    
    public String replaceNameForChat(String var1) {
        if (!this.friendsList.exists()) {
            return var1;
        }
        for (int var2 = 0; var2 < this.alias.size(); ++var2) {
            if (MorbidWrapper.getPlayer().sendQueue.playerInfoMap.containsKey(this.friends.get(var2))) {
                var1 = var1.replaceAll("-" + this.alias.get(var2), this.friends.get(var2));
            }
        }
        System.out.println(var1);
        return var1;
    }
    
    public void saveFriends() {
        if (!this.friendsList.exists()) {
            try {
                this.friendsList.createNewFile();
                this.friends.add("DarkMagician6");
                this.alias.add("DarkMagician");
                this.saveFriends();
            }
            catch (Exception var3) {
                var3.printStackTrace();
            }
        }
        try {
            final BufferedWriter var4 = new BufferedWriter(new FileWriter(this.friendsList));
            if (this.friends.size() > 0) {
                for (int var5 = 0; var5 < this.friends.size(); ++var5) {
                    var4.write(String.valueOf(this.friends.get(var5)) + ":" + this.alias.get(var5) + "\r\n");
                }
            }
            var4.close();
        }
        catch (Exception var6) {
            var6.printStackTrace();
        }
        MorbidHelper.gc();
    }
    
    public void loadFriends() {
        if (this.friendsList.exists()) {
            try {
                final BufferedReader var1 = new BufferedReader(new FileReader(this.friendsList));
                String var2 = "";
                while ((var2 = var1.readLine()) != null) {
                    final String[] var3 = var2.split(":");
                    final String var4 = var3[0];
                    final String var5 = var3[1];
                    this.friends.add(var4);
                    this.alias.add(var5);
                }
                var1.close();
            }
            catch (Exception var6) {
                var6.printStackTrace();
            }
        }
        MorbidHelper.gc();
    }
}
