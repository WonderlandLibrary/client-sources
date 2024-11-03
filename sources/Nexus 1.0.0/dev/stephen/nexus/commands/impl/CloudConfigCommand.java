package dev.stephen.nexus.commands.impl;

import dev.stephen.nexus.Client;
import dev.stephen.nexus.commands.Command;

public class CloudConfigCommand extends Command {
    public CloudConfigCommand() {
        super("cloudconfig", new String[]{"<load/list>", "<configName>"});
    }

    @Override
    public void execute(String[] args) {
        if (args[0] == null) {
            sendMessage("Please specify an action");
            return;
        }

        if (args[0].equalsIgnoreCase("load")) {
            if (args[1] == null) {
                sendMessage("Please enter a config name");
                return;
            }

            Client.INSTANCE.getConfigManager().loadCloudConfig(args[1]);
        } else if (args[0].equalsIgnoreCase("list")) {
            sendMessage(Client.INSTANCE.getConfigManager().getCloudConfigList());
        } else {
            sendMessage("Invalid Action");
        }
    }
}
