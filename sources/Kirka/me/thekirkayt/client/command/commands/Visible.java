/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.client.command.commands;

import me.thekirkayt.client.command.Com;
import me.thekirkayt.client.command.Command;
import me.thekirkayt.client.module.Module;
import me.thekirkayt.client.module.ModuleManager;
import me.thekirkayt.utils.ClientUtils;

@Com(names={"visible", "v", "show", "hide"})
public class Visible
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
        module.setShown(!module.isShown());
        ClientUtils.sendMessage(String.valueOf(String.valueOf(module.getDisplayName())) + " is now " + (module.isEnabled() ? "shown" : "hidden"));
        ModuleManager.save();
    }

    @Override
    public String getHelp() {
        return "Visible - visible <v, show, hide> (module) - Shows or hides a module on the arraylist.";
    }
}

