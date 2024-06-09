// 
// Decompiled by Procyon v0.5.36
// 

package intent.AquaDev.aqua.command.impl.commands;

import intent.AquaDev.aqua.Aqua;
import intent.AquaDev.aqua.command.Command;

public class SkidIrcCommand extends Command
{
    public SkidIrcCommand() {
        super("irc");
    }
    
    @Override
    public void execute(final String[] args) {
        try {
            Aqua.INSTANCE.ircClient.executeCommand(String.join(" ", (CharSequence[])args));
        }
        catch (Exception ex) {}
    }
}
