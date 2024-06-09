package me.teus.eclipse.commands.impl;

import me.teus.eclipse.Client;
import me.teus.eclipse.commands.Command;
import me.teus.eclipse.modules.Module;
import me.teus.eclipse.utils.managers.ModuleManager;
import org.lwjgl.input.Keyboard;

public class BindCommand extends Command {

    public BindCommand() {
        super("bind", "binds duh", ".bind <mod> <key>", "b", "bind");
    }

    @Override
    public void onCommand(String[] args, String command){
        String moduleName = args[0];
        String keyName = args[1];

        for (Module m : ModuleManager.modules){
            if(m.name.equalsIgnoreCase(moduleName)) {
                m.setKey(Keyboard.getKeyIndex(keyName.toUpperCase()));
                Client.getInstance().addChatMessage(String.format("Bound %s to %s :)", m.name, Keyboard.getKeyName(m.getKeybind())));
                break;
            }
        }
    }
}
