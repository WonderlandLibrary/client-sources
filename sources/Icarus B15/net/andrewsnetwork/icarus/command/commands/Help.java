// 
// Decompiled by Procyon v0.5.30
// 

package net.andrewsnetwork.icarus.command.commands;

import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Iterator;
import net.andrewsnetwork.icarus.utilities.Logger;
import net.andrewsnetwork.icarus.Icarus;
import net.andrewsnetwork.icarus.command.Command;

public class Help extends Command
{
    public Help() {
        super("help", "none");
    }
    
    @Override
    public void run(final String message) {
        final String[] arguments = message.split(" ");
        if (arguments.length == 1) {
            final StringBuilder commands = new StringBuilder("Commands: ");
            for (final Command command : Icarus.getCommandManager().getCommands()) {
                commands.append(String.valueOf(command.getCommand()) + ", ");
            }
            Logger.writeChat(commands.substring(0, commands.length() - 2).toString());
        }
    }
}
