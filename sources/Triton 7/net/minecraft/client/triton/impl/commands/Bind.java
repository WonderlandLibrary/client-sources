// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.client.triton.impl.commands;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.triton.management.command.Com;
import net.minecraft.client.triton.management.command.Command;
import net.minecraft.client.triton.management.module.Module;
import net.minecraft.client.triton.management.module.ModuleManager;
import net.minecraft.client.triton.utils.ClientUtils;

@Com(names = { "bind", "b" })
public class Bind extends Command
{
    @Override
    public void runCommand(final String[] args) {
        String modName = "";
        String keyName = "";
        if (args.length > 1) {
            modName = args[1];
            if (args.length > 2) {
                keyName = args[2];
            }
        }
        final Module module = ModuleManager.getModule(modName);
        if (module.getId().equalsIgnoreCase("null")) {
            ClientUtils.sendMessage("Invalid Module.");
            return;
        }
        if (keyName == "") {
            ClientUtils.sendMessage(String.valueOf(module.getDisplayName()) + "'s bind has been cleared.");
            module.setKeybind(0);
            ModuleManager.save();
            return;
        }
        module.setKeybind(Keyboard.getKeyIndex(keyName.toUpperCase()));
        ModuleManager.save();
        if (Keyboard.getKeyIndex(keyName.toUpperCase()) == 0) {
            ClientUtils.sendMessage("Invalid Key entered, Bind cleared.");
        }
        else {
            ClientUtils.sendMessage(String.valueOf(module.getDisplayName()) + " bound to " + keyName);
        }
    }
    
    @Override
    public String getHelp() {
        return "Bind - bind <b> (module) (key) - Bind a module to a key.";
    }
}
