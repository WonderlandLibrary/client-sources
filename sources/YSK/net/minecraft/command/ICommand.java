package net.minecraft.command;

import java.util.*;
import net.minecraft.util.*;

public interface ICommand extends Comparable<ICommand>
{
    String getCommandUsage(final ICommandSender p0);
    
    boolean isUsernameIndex(final String[] p0, final int p1);
    
    boolean canCommandSenderUseCommand(final ICommandSender p0);
    
    String getCommandName();
    
    void processCommand(final ICommandSender p0, final String[] p1) throws CommandException;
    
    List<String> getCommandAliases();
    
    List<String> addTabCompletionOptions(final ICommandSender p0, final String[] p1, final BlockPos p2);
}
