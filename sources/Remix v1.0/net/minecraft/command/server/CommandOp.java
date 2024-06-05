package net.minecraft.command.server;

import net.minecraft.server.*;
import net.minecraft.command.*;
import com.mojang.authlib.*;
import net.minecraft.util.*;
import com.google.common.collect.*;
import java.util.*;

public class CommandOp extends CommandBase
{
    @Override
    public String getCommandName() {
        return "op";
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 3;
    }
    
    @Override
    public String getCommandUsage(final ICommandSender sender) {
        return "commands.op.usage";
    }
    
    @Override
    public void processCommand(final ICommandSender sender, final String[] args) throws CommandException {
        if (args.length != 1 || args[0].length() <= 0) {
            throw new WrongUsageException("commands.op.usage", new Object[0]);
        }
        final MinecraftServer var3 = MinecraftServer.getServer();
        final GameProfile var4 = var3.getPlayerProfileCache().getGameProfileForUsername(args[0]);
        if (var4 == null) {
            throw new CommandException("commands.op.failed", new Object[] { args[0] });
        }
        var3.getConfigurationManager().addOp(var4);
        CommandBase.notifyOperators(sender, this, "commands.op.success", args[0]);
    }
    
    @Override
    public List addTabCompletionOptions(final ICommandSender sender, final String[] args, final BlockPos pos) {
        if (args.length == 1) {
            final String var4 = args[args.length - 1];
            final ArrayList var5 = Lists.newArrayList();
            for (final GameProfile var9 : MinecraftServer.getServer().getGameProfiles()) {
                if (!MinecraftServer.getServer().getConfigurationManager().canSendCommands(var9) && CommandBase.doesStringStartWith(var4, var9.getName())) {
                    var5.add(var9.getName());
                }
            }
            return var5;
        }
        return null;
    }
}
