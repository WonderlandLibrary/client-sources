// 
// Decompiled by KaktusWasser
// https://www.youtube.com/KaktusWasserReal

package me.kaktuswasser.client.event.events;

import java.util.Iterator;

import me.kaktuswasser.client.Client;
import me.kaktuswasser.client.command.Command;
import me.kaktuswasser.client.command.CommandManager;
import me.kaktuswasser.client.event.Cancellable;
import me.kaktuswasser.client.event.Event;
import me.kaktuswasser.client.utilities.Logger;

public class ChatSent extends Event implements Cancellable
{
    private boolean cancel;
    private String message;
    
    public ChatSent(final String message) {
        this.message = message;
    }
    
    public void checkForCommands() {
        if (this.message.startsWith(CommandManager.prefix) && !Client.getEventManager().isCancelled()) {
            for (final Command command : Client.getCommandManager().getCommands()) {
                if (this.message.split(" ")[0].equalsIgnoreCase(String.valueOf(CommandManager.prefix) + command.getCommand())) {
                    try {
                        command.run(this.message);
                    }
                    catch (Exception e) {
                        Logger.writeChat("Wrong arguments! " + command.getCommand() + " " + command.getArguments());
                    }
                    this.cancel = true;
                }
            }
            if (!this.cancel) {
                Logger.writeChat("Command \"" + this.message + "\" was not found!");
                this.cancel = true;
            }
        }
    }
    
    public String getMessage() {
        return this.message;
    }
    
    @Override
    public boolean isCancelled() {
        return this.cancel;
    }
    
    @Override
    public void setCancelled(final boolean cancel) {
        this.cancel = cancel;
    }
    
    public void setMessage(final String message) {
        this.message = message;
    }
}
