// 
// Decompiled by Procyon v0.5.30
// 

package me.chrest.client.command.commands;

import me.chrest.client.module.Module;
import me.chrest.utils.ClientUtils;
import me.chrest.client.module.ModuleManager;
import me.chrest.client.command.Com;
import me.chrest.client.command.Command;

@Com(names = { "toggle", "t", "tog" })
public class Toggle extends Command
{
    @Override
    public void runCommand(final String[] args) {
        String modName = "";
        if (args.length > 1) {
            modName = args[1];
        }
        final Module module = ModuleManager.getModule(modName);
        if (module.getId().equalsIgnoreCase("null")) {
            ClientUtils.sendMessage("Invalid Module.");
            return;
        }
        module.toggle();
        ClientUtils.sendMessage(String.valueOf(String.valueOf(module.getDisplayName())) + " is now " + (module.isEnabled() ? "enabled" : "disabled"));
        ModuleManager.save();
    }
    
    @Override
    public String getHelp() {
        return "Toggle - toggle <t, tog> (module) - Toggles a module on or off";
    }
}
