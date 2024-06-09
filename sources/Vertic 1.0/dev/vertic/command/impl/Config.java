package dev.vertic.command.impl;

import dev.vertic.Client;
import dev.vertic.command.Command;
import net.minecraft.client.gui.ChatLine;
import net.minecraft.util.ChatComponentText;

import java.awt.*;
import java.io.File;
import java.io.IOException;

import static net.minecraft.util.EnumChatFormatting.GOLD;
import static net.minecraft.util.EnumChatFormatting.WHITE;

public class Config extends Command {

    public Config() {
        super("Config", "config", "c", "conf");
    }

    @Override
    public void call(String[] args) throws IOException {
        if (args.length > 2) {
            if (args[1].equalsIgnoreCase("save")) {
                if (Client.instance.getConfigManager().saveConfig(args[2].toLowerCase())) {
                    addChatMessage("Saved config " + args[2] + ".");
                } else {
                    addChatMessage("Error saving config. WTF!?");
                }
            }
            if (args[1].equalsIgnoreCase("load")) {
                if (Client.instance.getConfigManager().loadConfig(args[2].toLowerCase())) {
                    addChatMessage("Loaded config " + args[2] + ".");
                } else {
                    addChatMessage("Error loading config. WTF!?");
                }
            }
        } else if (args.length > 1) {
            if (args[1].equalsIgnoreCase("list")) {
                addChatMessage("List of configs: ");
                for (String names : Client.instance.getConfigManager().listConfigs()) {
                    addChatMessageNoPrefix(names);
                }
            }
            if (args[1].equalsIgnoreCase("folder")) {
                Desktop desktop = Desktop.getDesktop();
                File dirToOpen = new File(String.valueOf(Client.instance.getConfigManager().configDir));
                desktop.open(dirToOpen);
                addChatMessage("Opened config folder.");
            }
        } else {
            addChatMessage(".config/.c load <config>");
            addChatMessage(".config/.c save <config>");
            addChatMessage(".config/.c folder");
            addChatMessage(".config/.c list");
        }
    }
    private void addChatMessageNoPrefix(Object message) {
        if (mc.thePlayer != null) {
            mc.thePlayer.addChatMessage(new ChatComponentText(GOLD + " » " + WHITE + message));
        }
    }
}
