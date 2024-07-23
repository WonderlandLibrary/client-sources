package io.github.liticane.monoxide.command.impl;

import io.github.liticane.monoxide.command.data.CommandInfo;
import io.github.liticane.monoxide.module.api.Module;
import io.github.liticane.monoxide.module.ModuleManager;
import io.github.liticane.monoxide.command.Command;

@CommandInfo(name = "toggle", aliases = {"t"}, description = "toggle a module")
public class ToggleCommand extends Command {

    @Override
    public boolean execute(String[] args) {
        if(args.length == 1) {
            final Module module = ModuleManager.getInstance().getModule(args[0]);
            if(module != null) {
                module.toggle();
                sendMessage((module.isEnabled() ? "§a" : "§c") + "Toggled §e" + module.getName());
            } else
                sendError("DOES NOT EXIST", "§aModule §l" + args[0] + " §anot found!");
        } else if (args.length == 0){
            sendHelp(this, "[Module]");
        } else {
            return false;
        }
        return true;
    }
}