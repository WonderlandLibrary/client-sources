// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.client.triton.impl.commands;

import net.minecraft.client.triton.management.command.Com;
import net.minecraft.client.triton.management.command.Command;
import net.minecraft.client.triton.management.module.Module;
import net.minecraft.client.triton.management.module.ModuleManager;
import net.minecraft.client.triton.utils.ClientUtils;

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
        ClientUtils.sendMessage(String.valueOf(module.getDisplayName()) + " is now " + (module.isEnabled() ? "enabled" : "disabled"));
        ModuleManager.save();
    }
    
    @Override
    public String getHelp() {
        return "Toggle - toggle <t, tog> (module) - Toggles a module on or off";
    }
}
