/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.client.command.commands;

import me.thekirkayt.client.command.Com;
import me.thekirkayt.client.command.Command;
import me.thekirkayt.client.module.Module;
import me.thekirkayt.client.module.ModuleManager;
import me.thekirkayt.utils.ClientUtils;

@Com(names={"toggle", "t", "tog"})
public class Toggle
extends Command {
    @Override
    public void runCommand(String[] args) {
        Module module;
        String modName = "";
        if (args.length > 1) {
            modName = args[1];
        }
        if ((module = ModuleManager.getModule(modName)).getId().equalsIgnoreCase("null")) {
            ClientUtils.sendMessage("Invalid Module.");
            return;
        }
        module.toggle();
        ClientUtils.sendMessage(String.valueOf(String.valueOf(module.getDisplayName())) + " is now " + (module.isEnabled() ? "enabled" : "disabled"));
        ModuleManager.save();
    }

    @Override
    public String getHelp() {
        return "\".t killaura\" - Toggles a module on or off";
    }
}

