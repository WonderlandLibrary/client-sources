package net.minecraft.command;

import java.util.List;
import net.minecraft.util.BlockPos;

public interface ICommand extends Comparable {
   boolean canCommandSenderUseCommand(ICommandSender var1);

   List getCommandAliases();

   String getCommandUsage(ICommandSender var1);

   void processCommand(ICommandSender var1, String[] var2) throws CommandException;

   String getCommandName();

   boolean isUsernameIndex(String[] var1, int var2);

   List addTabCompletionOptions(ICommandSender var1, String[] var2, BlockPos var3);
}
