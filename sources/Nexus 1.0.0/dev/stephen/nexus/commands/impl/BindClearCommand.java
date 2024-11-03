package dev.stephen.nexus.commands.impl;

import dev.stephen.nexus.Client;
import dev.stephen.nexus.commands.Command;
import dev.stephen.nexus.module.Module;

public class BindClearCommand extends Command {
    public BindClearCommand() {
        super("bindclear");
    }

    @Override
    public void execute(String[] commands) {
        for (Module module : Client.INSTANCE.getModuleManager().getModules()) {
            module.setKey(0);
        }
        sendMessage("Reset all binds");
    }
}
