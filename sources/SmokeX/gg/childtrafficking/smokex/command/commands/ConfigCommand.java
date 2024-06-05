// 
// Decompiled by Procyon v0.6.0
// 

package gg.childtrafficking.smokex.command.commands;

import java.util.Iterator;
import java.io.File;
import gg.childtrafficking.smokex.utils.player.ChatUtils;
import gg.childtrafficking.smokex.config.Config;
import java.awt.Desktop;
import gg.childtrafficking.smokex.SmokeXClient;
import gg.childtrafficking.smokex.command.CommandInfo;
import gg.childtrafficking.smokex.command.Command;

@CommandInfo(name = "Config", description = "Load/Save a config", usage = ".config load <name> | .config save <name> | .config list | .config folder", aliases = { "b" })
public final class ConfigCommand extends Command
{
    @Override
    public void execute(final String[] args) {
        if (args.length < 1) {
            this.printUsage();
        }
        else {
            if (args[0].equalsIgnoreCase("folder")) {
                final File location = SmokeXClient.getInstance().getConfigManager().getConfigDirectory();
                if (location != null) {
                    try {
                        Desktop.getDesktop().open(location);
                    }
                    catch (final Exception e) {
                        e.printStackTrace();
                    }
                }
                return;
            }
            if (args[0].equalsIgnoreCase("list")) {
                for (final Config config : SmokeXClient.getInstance().getConfigManager().getConfigs()) {
                    ChatUtils.addChatMessage(" - " + config.getName());
                }
                return;
            }
            if (args.length < 2) {
                this.printUsage();
            }
            else {
                if (args[0].equalsIgnoreCase("load")) {
                    if (SmokeXClient.getInstance().getConfigManager().load(args[1])) {
                        ChatUtils.addChatMessage("Successfully loaded config.");
                    }
                    else {
                        ChatUtils.addChatMessage("§4Cannot find config.");
                    }
                }
                if (args[0].equalsIgnoreCase("save")) {
                    if (SmokeXClient.getInstance().getConfigManager().save(args[1])) {
                        ChatUtils.addChatMessage("Successfully saved config.");
                    }
                    else {
                        ChatUtils.addChatMessage("§4Failed to save config.");
                    }
                }
            }
        }
    }
}
