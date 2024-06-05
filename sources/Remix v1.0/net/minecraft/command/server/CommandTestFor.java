package net.minecraft.command.server;

import net.minecraft.nbt.*;
import net.minecraft.command.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.server.*;

public class CommandTestFor extends CommandBase
{
    @Override
    public String getCommandName() {
        return "testfor";
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }
    
    @Override
    public String getCommandUsage(final ICommandSender sender) {
        return "commands.testfor.usage";
    }
    
    @Override
    public void processCommand(final ICommandSender sender, final String[] args) throws CommandException {
        if (args.length < 1) {
            throw new WrongUsageException("commands.testfor.usage", new Object[0]);
        }
        final Entity var3 = CommandBase.func_175768_b(sender, args[0]);
        NBTTagCompound var4 = null;
        if (args.length >= 2) {
            try {
                var4 = JsonToNBT.func_180713_a(CommandBase.func_180529_a(args, 1));
            }
            catch (NBTException var5) {
                throw new CommandException("commands.testfor.tagError", new Object[] { var5.getMessage() });
            }
        }
        if (var4 != null) {
            final NBTTagCompound var6 = new NBTTagCompound();
            var3.writeToNBT(var6);
            if (!CommandTestForBlock.func_175775_a(var4, var6, true)) {
                throw new CommandException("commands.testfor.failure", new Object[] { var3.getName() });
            }
        }
        CommandBase.notifyOperators(sender, this, "commands.testfor.success", var3.getName());
    }
    
    @Override
    public boolean isUsernameIndex(final String[] args, final int index) {
        return index == 0;
    }
    
    @Override
    public List addTabCompletionOptions(final ICommandSender sender, final String[] args, final BlockPos pos) {
        return (args.length == 1) ? CommandBase.getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getAllUsernames()) : null;
    }
}
