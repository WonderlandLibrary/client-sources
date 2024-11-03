package dev.stephen.nexus.commands.impl;

import dev.stephen.nexus.Client;
import dev.stephen.nexus.commands.Command;
import dev.stephen.nexus.utils.mc.ChatUtils;
import dev.stephen.nexus.utils.mc.PlayerUtil;

import java.io.File;
import java.util.Objects;

public class ConfigCommand extends Command {
    public ConfigCommand() {
        super("config", new String[]{"<save/load/list>", "<configName>"});
    }

    @Override
    public void execute(String[] args) {
        if (args[0].equalsIgnoreCase("save")) {
            Client.INSTANCE.getConfigManager().saveConfig(args[1]);
        }

        if (args[0].equalsIgnoreCase("load")) {
            Client.INSTANCE.getConfigManager().loadConfig(args[1]);
        }

        if (args[0].equalsIgnoreCase("list")) {
            sendMessage("Configs:");

            boolean found = false;
            for (File file : Objects.requireNonNull(Client.INSTANCE.getConfigManager().getConfigDir().listFiles())) {
                if (file.getName().endsWith(".json")) {
                    sendMessage(" - " + file.getName());
                    found = true;
                }
            }
            if (!found) {
                sendMessage("No configs found.");
            }
        }
    }
}
