// 
// Decompiled by Procyon v0.5.36
// 

package intent.AquaDev.aqua.command.impl.commands;

import intent.AquaDev.aqua.Aqua;
import intent.AquaDev.aqua.command.Command;

public class Global extends Command
{
    public Global() {
        super("c");
    }
    
    @Override
    public void execute(final String[] args) {
        Aqua.INSTANCE.ircClient.sendChatMessage(String.join(" ", (CharSequence[])args));
    }
}
