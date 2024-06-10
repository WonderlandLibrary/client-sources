// 
// Decompiled by KaktusWasser
// https://www.youtube.com/KaktusWasserReal

package me.kaktuswasser.client.command.commands;

import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Iterator;

import me.kaktuswasser.client.Client;
import me.kaktuswasser.client.command.Command;
import me.kaktuswasser.client.utilities.Logger;

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
            for (final Command command : Client.getCommandManager().getCommands()) {
                commands.append(String.valueOf(command.getCommand()) + ", ");
            }
            Logger.writeChat(commands.substring(0, commands.length() - 2).toString());
            Logger.writeChat("§cIcarus SRC by KaktusWasser - Moddet Icarus(b17)");
        }
    }
}
