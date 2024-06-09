// 
// Decompiled by Procyon v0.5.30
// 

package net.andrewsnetwork.icarus.event.events;

import java.util.Iterator;
import net.andrewsnetwork.icarus.utilities.Logger;
import net.andrewsnetwork.icarus.command.Command;
import net.andrewsnetwork.icarus.Icarus;
import net.andrewsnetwork.icarus.command.CommandManager;
import net.andrewsnetwork.icarus.event.Cancellable;
import net.andrewsnetwork.icarus.event.Event;

public class ChatSent extends Event implements Cancellable
{
    private boolean cancel;
    private String message;
    
    public ChatSent(final String message) {
        this.message = message;
    }
    
    public void checkForCommands() {
        if (this.message.startsWith(CommandManager.prefix) && !Icarus.getEventManager().isCancelled()) {
            for (final Command command : Icarus.getCommandManager().getCommands()) {
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
