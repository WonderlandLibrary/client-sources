package dev.star.commands.impl;

import dev.star.Client;
import dev.star.commands.Command;
import dev.star.module.Module;

public class ClearConfigCommand extends Command {

    public ClearConfigCommand() {
        super("clearconfig", "Turns off all enabled modules", ".clearconfig");
    }

    @Override
    public void execute(String[] args) {
        Client.INSTANCE.getModuleCollection().getModules().stream().filter(Module::isEnabled).forEach(Module::toggle);
    }
}
