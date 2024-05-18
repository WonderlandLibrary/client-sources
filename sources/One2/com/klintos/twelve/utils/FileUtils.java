// 
// Decompiled by Procyon v0.5.30
// 

package com.klintos.twelve.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import com.klintos.twelve.Twelve;
import com.klintos.twelve.gui.screens.altmanager.slot.Alt;
import com.klintos.twelve.gui.screens.altmanager.slot.AltSlot;
import com.klintos.twelve.handlers.friend.Friend;
import com.klintos.twelve.mod.Mod;
import com.klintos.twelve.mod.Search;
import com.klintos.twelve.mod.cmd.Bots;
import com.klintos.twelve.mod.value.Value;
import com.klintos.twelve.mod.value.ValueBoolean;
import com.klintos.twelve.mod.value.ValueDouble;
import com.klintos.twelve.mod.value.ValueString;

import net.minecraft.client.Minecraft;

public class FileUtils
{
    public static void init() {
        final File file = new File(Minecraft.getMinecraft().mcDataDir + "\\ONE2");
        if (!file.exists()) {
            file.mkdir();
        }
        Twelve.getInstance().getModHandler();
        loadBinds();
        loadAltAccounts();
        loadFriends();
        loadSearchIDs();
        loadBots();
    }
    
    public static void loadAltAccounts() {
        try {
            File file = new File(Minecraft.getMinecraft().mcDataDir + "\\ONE2\\alts.txt");
            if (!file.exists()) {
                PrintWriter printWriter = new PrintWriter(new FileWriter(file));
                printWriter.println();
                printWriter.close();
            } else if (file.exists()) {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                String readString = "";
                while ((readString = bufferedReader.readLine()) != null) {
                    Alt alt = new Alt(readString.split(":")[0], readString.split(":")[1]);
                    AltSlot.altList.add(alt);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void saveAltAccounts() {
        final File file = new File(Minecraft.getMinecraft().mcDataDir + "\\ONE2\\alts.txt");
        try {
            final PrintWriter printWriter = new PrintWriter(new FileWriter(file));
            for (final Alt alt : AltSlot.altList) {
                printWriter.println(alt.getFileLine());
            }
            printWriter.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void saveBinds() {
        final File file = new File(Minecraft.getMinecraft().mcDataDir + "\\ONE2\\binds.txt");
        try {
            final PrintWriter printWriter = new PrintWriter(new FileWriter(file));
            for (final Mod mod : Twelve.getInstance().getModHandler().getMods()) {
                final String modname = mod.getModName();
                final int modkey = mod.getModKeybind();
                final String string = String.valueOf(modname) + ":" + modkey;
                System.out.println(string);
                printWriter.println(string);
            }
            printWriter.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void loadBinds() {
        try {
            final File file = new File(Minecraft.getMinecraft().mcDataDir + "\\ONE2\\binds.txt");
            if (!file.exists()) {
                final PrintWriter printWriter = new PrintWriter(new FileWriter(file));
                printWriter.println();
                printWriter.close();
            }
            else if (file.exists()) {
                final BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                String readString = "";
                while ((readString = bufferedReader.readLine()) != null) {
                    final String[] split = readString.split(":");
                    final Mod mod = Twelve.getInstance().getModHandler().getMod(split[0]);
                    final int key = Integer.valueOf(split[1]);
                    if (mod == null) {
                        continue;
                    }
                    mod.setModKeybind(key);
                }
                bufferedReader.close();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void saveFriends() {
        final File file = new File(Minecraft.getMinecraft().mcDataDir + "\\ONE2\\friends.txt");
        try {
            final PrintWriter printWriter = new PrintWriter(new FileWriter(file));
            for (final Friend friend : Twelve.getInstance().getFriendHandler().getFriendsArray()) {
                printWriter.println(String.valueOf(friend.getUsername()) + ":" + friend.getAlias());
            }
            printWriter.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void loadFriends() {
        try {
            final File file = new File(Minecraft.getMinecraft().mcDataDir + "\\ONE2\\friends.txt");
            if (!file.exists()) {
                final PrintWriter printWriter = new PrintWriter(new FileWriter(file));
                printWriter.println();
                printWriter.close();
            }
            else if (file.exists()) {
                final BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                String readString = "";
                while ((readString = bufferedReader.readLine()) != null) {
                    final String[] line = readString.split(":");
                    Twelve.getInstance().getFriendHandler().addFriend(new Friend(line[0], line[1]));
                }
                bufferedReader.close();
                
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void saveSearchIDs() {
        final File file = new File(Minecraft.getMinecraft().mcDataDir + "\\ONE2\\search.txt");
        try {
            final PrintWriter printWriter = new PrintWriter(new FileWriter(file));
            for (final int id : Search.ids) {
                printWriter.println(id);
            }
            printWriter.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void loadSearchIDs() {
        try {
            final File file = new File(Minecraft.getMinecraft().mcDataDir + "\\ONE2\\search.txt");
            if (!file.exists()) {
                final PrintWriter printWriter = new PrintWriter(new FileWriter(file));
                printWriter.println();
                printWriter.close();
            }
            else if (file.exists()) {
                final BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                String readString = "";
                while ((readString = bufferedReader.readLine()) != null) {
                    final int id = Integer.parseInt(readString);
                    Search.ids.add(id);
                }
            }
        }
        catch (NumberFormatException e) {
        	e.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void loadBots() {
        try {
            final File file = new File(Minecraft.getMinecraft().mcDataDir + "\\ONE2\\bots.txt");
            if (!file.exists()) {
                final PrintWriter printWriter = new PrintWriter(new FileWriter(file));
                printWriter.println();
                printWriter.close();
            }
            else if (file.exists()) {
                final BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                String readString = "";
                while ((readString = bufferedReader.readLine()) != null) {
                    Bots.accs.add(readString);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void saveValues() {
        final File file = new File(Minecraft.getMinecraft().mcDataDir + "\\ONE2\\values.txt");
        try {
            final PrintWriter printWriter = new PrintWriter(new FileWriter(file));
            for (final Mod mod : Twelve.getInstance().getModHandler().getMods()) {
                if (mod.getValues().size() > 0) {
                    for (final Value value : mod.getValues()) {
                        if (value instanceof ValueBoolean) {
                            final ValueBoolean v = (ValueBoolean)value;
                            printWriter.println(String.valueOf(mod.getModName()) + "-BOOLEAN-" + v.getName() + ":" + v.getValue());
                        }
                        else if (value instanceof ValueDouble) {
                            final ValueDouble v2 = (ValueDouble)value;
                            printWriter.println(String.valueOf(mod.getModName()) + "-DOUBLE-" + v2.getName() + ":" + v2.getValue() + ":" + v2.getMin() + ":" + v2.getMax() + ":" + v2.getRound());
                        }
                        else {
                            if (!(value instanceof ValueString)) {
                                continue;
                            }
                            final ValueString v3 = (ValueString)value;
                            printWriter.println(String.valueOf(mod.getModName()) + "-STRING-" + v3.getName() + ":" + v3.getValue());
                        }
                    }
                }
            }
            printWriter.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void loadValues() {
        try {
            final File file = new File(Minecraft.getMinecraft().mcDataDir + "\\ONE2\\values.txt");
            if (!file.exists()) {
                final PrintWriter printWriter = new PrintWriter(new FileWriter(file));
                printWriter.println();
                printWriter.close();
            }
            else if (file.exists()) {
                final BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                String readString = "";
                while ((readString = bufferedReader.readLine()) != null) {
                    final String[] split = readString.split("-");
                    final Mod mod = Twelve.getInstance().getModHandler().getMod(split[0]);
                    if (split[1].equals("BOOLEAN")) {
                        final String[] vSplit = split[2].split(":");
                        mod.addValue(new ValueBoolean(vSplit[0], Boolean.parseBoolean(vSplit[1])));
                    }
                    else if (split[1].equals("DOUBLE")) {
                        final String[] vSplit = split[2].split(":");
                        mod.addValue(new ValueDouble(vSplit[0], Double.parseDouble(vSplit[1]), Double.parseDouble(vSplit[2]), Double.parseDouble(vSplit[3]), Integer.parseInt(vSplit[4])));
                    }
                    else {
                        if (!split[1].equals("STRING")) {
                            continue;
                        }
                        final String[] vSplit = split[2].split(":");
                        mod.addValue(new ValueString(vSplit[0], vSplit[1]));
                    }
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
