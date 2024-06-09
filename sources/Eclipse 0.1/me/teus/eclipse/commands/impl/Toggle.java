package me.teus.eclipse.commands.impl;

import me.teus.eclipse.Client;
import me.teus.eclipse.commands.Command;
import me.teus.eclipse.modules.Module;
import me.teus.eclipse.utils.managers.ModuleManager;

public class Toggle extends Command {
    public Toggle() {
        super("toggle", "Toggle shit", "toggle <mod>", "t", "toggle");
    }

    @Override
    public void onCommand(String[] args, String command) {
        String mName = args[0];

        boolean found = false;

        for (Module module : ModuleManager.modules) {
            if (module.name.equalsIgnoreCase(mName)) {
                found = true;
                module.toggle();
                Client.getInstance().addChatMessage((module.isToggled() ? "Enabled " : "Disabled ") + module.name);
                return;
            }
        }

        if(!found) {
            Client.getInstance().addChatMessage("You fucking brain dead type the module name right :skull:");
        }
    }

}
