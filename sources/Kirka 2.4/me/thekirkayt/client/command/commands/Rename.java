/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.client.command.commands;

import me.thekirkayt.client.command.Com;
import me.thekirkayt.client.command.Command;
import me.thekirkayt.client.module.Module;
import me.thekirkayt.client.module.ModuleManager;
import me.thekirkayt.client.module.modules.render.Hud;
import me.thekirkayt.client.module.modules.render.hud.tabgui.LucidTabGui;
import me.thekirkayt.utils.ClientUtils;

@Com(names={"rename", "rn"})
public class Rename
extends Command {
    @Override
    public void runCommand(String[] args) {
        Module module;
        String modName = "";
        String newName = "";
        if (args.length > 1) {
            modName = args[1];
            if (args.length > 2 && (newName = args[2]).startsWith("\"") && args[args.length - 1].endsWith("\"")) {
                newName = newName.substring(1, newName.length());
                for (int i = 3; i < args.length; ++i) {
                    newName = String.valueOf(String.valueOf(newName)) + " " + args[i].replace("\"", "");
                }
            }
        }
        if ((module = ModuleManager.getModule(modName)).getId().equalsIgnoreCase("null")) {
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
        ((Hud)new Hud().getInstance()).lucidTab.setupSizes();
    }

    @Override
    public String getHelp() {
        return "Rename (module) (name) - Rename a module.";
    }
}

