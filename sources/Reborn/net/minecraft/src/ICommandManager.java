package net.minecraft.src;

import java.util.*;

public interface ICommandManager
{
    int executeCommand(final ICommandSender p0, final String p1);
    
    List getPossibleCommands(final ICommandSender p0, final String p1);
    
    List getPossibleCommands(final ICommandSender p0);
    
    Map getCommands();
}
