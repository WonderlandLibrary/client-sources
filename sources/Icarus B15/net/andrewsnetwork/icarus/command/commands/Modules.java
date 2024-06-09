// 
// Decompiled by Procyon v0.5.30
// 

package net.andrewsnetwork.icarus.command.commands;

import java.util.Iterator;
import net.andrewsnetwork.icarus.utilities.Logger;
import net.andrewsnetwork.icarus.module.Module;
import net.andrewsnetwork.icarus.Icarus;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import net.andrewsnetwork.icarus.command.Command;

public class Modules extends Command
{
    public Modules() {
        super("modules", "none");
    }
    
    @Override
    public void run(final String message) {
        OutputStreamWriter request = new OutputStreamWriter(System.out);
        Label_0033: {
            try {
                request.flush();
            }
            catch (IOException ex) {
                break Label_0033;
            }
            finally {
                request = null;
            }
            request = null;
        }
        final StringBuilder list = new StringBuilder("Modules (" + Icarus.getModuleManager().getModules().size() + "): ");
        for (final Module module : Icarus.getModuleManager().getModules()) {
            list.append(module.isEnabled() ? "§a" : "§f").append(module.getName()).append("§f, ");
        }
        Logger.writeChat(list.toString().substring(0, list.toString().length() - 2));
    }
}
