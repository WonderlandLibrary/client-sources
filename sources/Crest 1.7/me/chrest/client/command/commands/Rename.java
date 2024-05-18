// 
// Decompiled by Procyon v0.5.30
// 

package me.chrest.client.command.commands;

import me.chrest.client.module.Module;
import me.chrest.utils.ClientUtils;
import me.chrest.client.module.ModuleManager;
import me.chrest.client.command.Com;
import me.chrest.client.command.Command;

@Com(names = { "rename", "rn" })
public class Rename extends Command
{
    @Override
    public void runCommand(final String[] args) {
        String modName = "";
        String newName = "";
        if (args.length > 1) {
            modName = args[1];
            if (args.length > 2) {
                newName = args[2];
                if (newName.startsWith("\"") && args[args.length - 1].endsWith("\"")) {
                    newName = newName.substring(1, newName.length());
                    for (int i = 3; i < args.length; ++i) {
                        newName = String.valueOf(String.valueOf(newName)) + " " + args[i].replace("\"", "");
                    }
                }
            }
        }
        final Module module = ModuleManager.getModule(modName);
        if (module.getId().equalsIgnoreCase("null")) {
            ClientUtils.sendMessage("Invalid Module.");
            return;
        }
        if (newName == "") {
            ClientUtils.sendMessage(String.valueOf(String.valueOf(module.getId())) + "'s name has been reset.");
            module.setDisplayName(module.getId());
            ModuleManager.save();
            return;
        }
        module.setDisplayName(newName);
        ModuleManager.save();
        ClientUtils.sendMessage(String.valueOf(String.valueOf(module.getId())) + " has been renamed to " + newName);
    }
    
    @Override
    public String getHelp() {
        return "Rename - rename <rn> (module) (name) - Rename a module.";
    }
}
