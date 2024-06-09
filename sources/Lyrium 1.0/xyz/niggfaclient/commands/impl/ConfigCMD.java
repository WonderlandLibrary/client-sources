// 
// Decompiled by Procyon v0.5.36
// 

package xyz.niggfaclient.commands.impl;

import java.util.Iterator;
import xyz.niggfaclient.config.Config;
import xyz.niggfaclient.utils.other.Printer;
import xyz.niggfaclient.Client;
import xyz.niggfaclient.commands.Command;

public class ConfigCMD extends Command
{
    public ConfigCMD() {
        super("Config", "", "", new String[] { "c" });
    }
    
    @Override
    public void onCommand(final String[] args, final String command) {
        if (args.length == 2) {
            final String lowerCase = args[0].toLowerCase();
            switch (lowerCase) {
                case "load": {
                    if (Client.getInstance().getConfigManager().loadConfig(args[1])) {
                        Printer.addMessage("Loaded config " + args[1]);
                    }
                    else {
                        Printer.addMessage("Failed to load config!");
                    }
                    return;
                }
                case "save": {
                    if (Client.getInstance().getConfigManager().saveConfig(args[1])) {
                        Printer.addMessage("Saved config " + args[1]);
                    }
                    else {
                        Printer.addMessage("Failed to save config!");
                    }
                    return;
                }
                case "delete": {
                    if (Client.getInstance().getConfigManager().deleteConfig(args[1])) {
                        Printer.addMessage("Deleted config " + args[1]);
                    }
                    else {
                        Printer.addMessage("Failed to delete config!");
                    }
                    return;
                }
            }
        }
        else if (args.length == 1 && args[0].equalsIgnoreCase("LIST")) {
            Printer.addMessage("Available Configs:");
            for (final Config config : Client.getInstance().getConfigManager().getElements()) {
                Printer.addMessage(config.getName());
            }
            return;
        }
        Printer.addMessage("Correct Usage: config/c <load/save/delete/list>");
    }
}
