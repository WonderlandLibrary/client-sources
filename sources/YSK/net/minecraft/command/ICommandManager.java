package net.minecraft.command;

import net.minecraft.util.*;
import java.util.*;

public interface ICommandManager
{
    List<ICommand> getPossibleCommands(final ICommandSender p0);
    
    List<String> getTabCompletionOptions(final ICommandSender p0, final String p1, final BlockPos p2);
    
    Map<String, ICommand> getCommands();
    
    int executeCommand(final ICommandSender p0, final String p1);
}
