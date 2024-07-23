package io.github.liticane.monoxide.command.impl;

import io.github.liticane.monoxide.command.data.CommandInfo;
import io.github.liticane.monoxide.module.api.Module;
import io.github.liticane.monoxide.module.ModuleManager;
import org.lwjglx.input.Keyboard;
import io.github.liticane.monoxide.command.Command;

@CommandInfo(name = "bind", description = "bind modules to a key")
public class BindCommand extends Command {

    @Override
    public boolean execute(String[] args) {
        if(args.length == 2) {
            final Module module = ModuleManager.getInstance().getModule(args[0]);
            if(module != null) {
                final int key = Keyboard.getKeyIndex(args[1].toUpperCase());
                module.setKey(key);
                sendMessage("Key set to §e§l" + args[1].toUpperCase());
            }else{
                sendError("DOES NOT EXIST", "§aModule §l" + args[0] + " §anot found!");
            }
        } else if (args.length == 0) {
            sendHelp(this, "[Module] [Key]");
        } else {
            return false;
        }
        return true;
    }
}