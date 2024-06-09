// 
// Decompiled by Procyon v0.5.30
// 

package net.andrewsnetwork.icarus.command.commands;

import net.andrewsnetwork.icarus.module.Module;
import net.andrewsnetwork.icarus.utilities.Logger;
import net.andrewsnetwork.icarus.Icarus;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import net.andrewsnetwork.icarus.command.Command;

public class Toggle extends Command
{
    public Toggle() {
        super("t", "<mod name>");
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
        final String[] arguments = message.split(" ");
        final Module mod = Icarus.getModuleManager().getModuleByName(arguments[1]);
        if (mod == null) {
            Logger.writeChat("Module \"" + arguments[1] + "\" is not valid!");
        }
        else {
            mod.toggle();
            Logger.writeChat("Module \"" + mod.getName() + "\" toggled " + (mod.isEnabled() ? "§2on" : "§4off") + "§f.");
        }
    }
}
