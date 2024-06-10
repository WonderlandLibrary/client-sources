// 
// Decompiled by KaktusWasser
// https://www.youtube.com/KaktusWasserReal

package me.kaktuswasser.client.command.commands;

import java.io.IOException;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import me.kaktuswasser.client.command.Command;
import me.kaktuswasser.client.utilities.Logger;

public class OldNames extends Command
{
    public OldNames() {
        super("oldnames", "<name>");
    }
    
    @Override
    public void run(final String message) {
        String uuid = "";
        try {
            URL e = new URL("https://api.mojang.com/users/profiles/minecraft/" + message.split(" ")[1]);
            BufferedReader br = new BufferedReader(new InputStreamReader(e.openStream()));
            String line;
            while ((line = br.readLine()) != null) {
                uuid = line.split("\"")[3];
            }
            br.close();
            e = new URL("https://api.mojang.com/user/profiles/" + uuid + "/names");
            br = new BufferedReader(new InputStreamReader(e.openStream()));
            String oldNames = "";
            while ((line = br.readLine()) != null) {
                final String originalName = line.split("\"")[3];
                for (int i = 0; i < line.split("\"").length; ++i) {
                    if (i != line.split("\"").length - 1 && line.split("\"")[i + 1].equals(",")) {
                        if (oldNames.equals("")) {
                            oldNames = line.split("\"")[i];
                        }
                        else {
                            oldNames = String.valueOf(oldNames) + ", " + line.split("\"")[i];
                        }
                    }
                }
                if (oldNames.equals("")) {
                    Logger.writeChat(String.valueOf(message.split(" ")[1]) + " never changed their name.");
                }
                else {
                    Logger.writeChat(String.valueOf(message.split(" ")[1]) + "'s old names: " + originalName + ", " + oldNames + ".");
                }
            }
        }
        catch (IOException var10) {
            Logger.writeChat("Unable to retrieve UUID of player.");
        }
    }
}
