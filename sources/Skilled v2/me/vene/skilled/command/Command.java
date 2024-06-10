package me.vene.skilled.command;

import me.vene.skilled.*;

public interface Command
{
    String getCommandName();
    
    void execute(final SkilledClient p0, final String[] p1);
}
