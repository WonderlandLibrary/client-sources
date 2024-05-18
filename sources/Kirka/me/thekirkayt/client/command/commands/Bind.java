/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.client.command.commands;

import me.thekirkayt.client.command.Com;
import me.thekirkayt.client.command.Command;
import me.thekirkayt.client.module.Module;
import me.thekirkayt.client.module.ModuleManager;
import me.thekirkayt.utils.ClientUtils;
import org.lwjgl.input.Keyboard;

@Com(names={"bind", "b"})
public class Bind
extends Command {
    @Override
    public void runCommand(String[] args) {
        Module module;
        String modName = "";
        String keyName = "";
        if (args.length > 1) {
            modName = args[1];
            if (args.length > 2) {
                keyName = args[2];
            }
        }
        if ((module = ModuleManager.getModule(modName)).getId().equalsIgnoreCase("null")) {
            ClientUtils.sendMessage("Bind [Module] [key]");
            return;
        }
        if (keyName == "") {
            ClientUtils.sendMessage(String.valueOf(String.valueOf(module.getDisplayName())) + "'s bind has been cleared.");
            module.setKeybind(0);
            ModuleManager.save();
            return;
        }
        module.setKeybind(Keyboard.getKeyIndex((String)keyName.toUpperCase()));
        ModuleManager.save();
        if (Keyboard.getKeyIndex((String)keyName.toUpperCase()) == 0) {
            ClientUtils.sendMessage("Invalid Key entered, Bind cleared.");
        } else {
            ClientUtils.sendMessage(String.valueOf(String.valueOf(module.getDisplayName())) + " bound to " + keyName);
        }
    }

    @Override
    public String getHelp() {
        return "Bind [Module] [key]";
    }
}

