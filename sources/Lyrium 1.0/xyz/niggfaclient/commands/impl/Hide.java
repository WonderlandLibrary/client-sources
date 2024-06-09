// 
// Decompiled by Procyon v0.5.36
// 

package xyz.niggfaclient.commands.impl;

import java.util.Iterator;
import xyz.niggfaclient.utils.other.Printer;
import xyz.niggfaclient.module.Module;
import xyz.niggfaclient.Client;
import xyz.niggfaclient.commands.Command;

public class Hide extends Command
{
    public Hide() {
        super("Hide", "", "", new String[] { "h" });
    }
    
    @Override
    public void onCommand(final String[] args, final String command) {
        if (args.length > 0) {
            if (args[0].equalsIgnoreCase("reset") || args[0].equalsIgnoreCase("clear")) {
                for (final Module m : Client.getInstance().getModuleManager().getModules()) {
                    m.setVisible(true);
                }
                return;
            }
            for (final Module m : Client.getInstance().getModuleManager().getModules()) {
                if (m.getName().equalsIgnoreCase(args[0])) {
                    Printer.addMessage((m.isVisible() ? "Hidden " : "Showing ") + m.getName());
                    m.setVisible(!m.isVisible());
                }
            }
        }
    }
}
