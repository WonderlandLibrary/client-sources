/*
 * Decompiled with CFR 0_122.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Keyboard
 */
package Monix.Commands.commands;

import Monix.Commands.Command;
import Monix.Files.FileManager;
import Monix.Mod.Mod;
import Monix.Mod.ModManager;
import Monix.Monix;
import org.lwjgl.input.Keyboard;

public class Bind
extends Command {
    @Override
    public String getAlias() {
        return "bind";
    }

    @Override
    public String getSyntax() {
        return "-bind mod key | -bind del mod";
    }

    @Override
    public void onCommand(String command, String[] args) throws Exception {
        args[1] = args[1].toUpperCase();
        int key = Keyboard.getKeyIndex((String)args[1]);
        for (Mod m : ModManager.getMods()) {
            if (!args[0].equalsIgnoreCase(m.getName())) continue;
            m.setKey(Keyboard.getKeyIndex((String)Keyboard.getKeyName((int)key)));
            Monix.addChatMessage(String.valueOf(args[0]) + "\u00a77 has been binded to \u00a77" + args[1]);
        }
        if (args[0].equalsIgnoreCase("del")) {
            for (Mod m : ModManager.getMods()) {
                if (!m.getName().equalsIgnoreCase(args[1])) continue;
                m.setKey(0);
                Monix.addChatMessage(String.valueOf(args[1]) + " has been unbinded");
            }
        }
        Monix.getInstance();
        Monix.fileManager.saveFiles();
    }
}

