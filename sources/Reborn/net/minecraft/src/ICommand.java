package net.minecraft.src;

import java.util.*;

public interface ICommand extends Comparable
{
    String getCommandName();
    
    String getCommandUsage(final ICommandSender p0);
    
    List getCommandAliases();
    
    void processCommand(final ICommandSender p0, final String[] p1);
    
    boolean canCommandSenderUseCommand(final ICommandSender p0);
    
    List addTabCompletionOptions(final ICommandSender p0, final String[] p1);
    
    boolean isUsernameIndex(final String[] p0, final int p1);
}
