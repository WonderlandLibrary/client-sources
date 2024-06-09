// 
// Decompiled by Procyon v0.5.36
// 

package xyz.niggfaclient.commands.impl;

import java.util.Iterator;
import xyz.niggfaclient.utils.other.Printer;
import org.lwjgl.input.Keyboard;
import xyz.niggfaclient.module.Module;
import xyz.niggfaclient.Client;
import xyz.niggfaclient.commands.Command;

public class Bind extends Command
{
    public Bind() {
        super("Bind", "", "", new String[] { "b" });
    }
    
    @Override
    public void onCommand(final String[] args, final String command) {
        if (args.length == 2) {
            for (final Module module : Client.getInstance().getModuleManager().getModules()) {
                if (module.getName().equalsIgnoreCase(args[0])) {
                    module.setKey(Keyboard.getKeyIndex(args[1].toUpperCase()));
                    Printer.addMessage(String.format("Bound %s to %s", module.getName(), Keyboard.getKeyName(module.getKey())));
                    break;
                }
                Printer.addMessage("Could not find the module.");
            }
        }
        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("clear")) {
                for (final Module module2 : Client.getInstance().getModuleManager().getModules()) {
                    if (!module2.getName().equalsIgnoreCase("clickgui")) {
                        module2.setKey(0);
                    }
                }
                Printer.addMessage("Cleared all your keybinds.");
            }
            else {
                Printer.addMessage("Could not find the module.");
            }
        }
    }
}
