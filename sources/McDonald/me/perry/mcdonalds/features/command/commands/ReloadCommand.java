// 
// Decompiled by Procyon v0.5.36
// 

package me.perry.mcdonalds.features.command.commands;

import me.perry.mcdonalds.McDonalds;
import me.perry.mcdonalds.features.command.Command;

public class ReloadCommand extends Command
{
    public ReloadCommand() {
        super("reload", new String[0]);
    }
    
    @Override
    public void execute(final String[] commands) {
        McDonalds.reload();
    }
}
