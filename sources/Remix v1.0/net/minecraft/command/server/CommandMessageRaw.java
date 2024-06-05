package net.minecraft.command.server;

import net.minecraft.entity.*;
import org.apache.commons.lang3.exception.*;
import com.google.gson.*;
import net.minecraft.entity.player.*;
import net.minecraft.command.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.server.*;

public class CommandMessageRaw extends CommandBase
{
    @Override
    public String getCommandName() {
        return "tellraw";
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }
    
    @Override
    public String getCommandUsage(final ICommandSender sender) {
        return "commands.tellraw.usage";
    }
    
    @Override
    public void processCommand(final ICommandSender sender, final String[] args) throws CommandException {
        if (args.length < 2) {
            throw new WrongUsageException("commands.tellraw.usage", new Object[0]);
        }
        final EntityPlayerMP var3 = CommandBase.getPlayer(sender, args[0]);
        final String var4 = CommandBase.func_180529_a(args, 1);
        try {
            final IChatComponent var5 = IChatComponent.Serializer.jsonToComponent(var4);
            var3.addChatMessage(ChatComponentProcessor.func_179985_a(sender, var5, var3));
        }
        catch (JsonParseException var7) {
            final Throwable var6 = ExceptionUtils.getRootCause((Throwable)var7);
            throw new SyntaxErrorException("commands.tellraw.jsonException", new Object[] { (var6 == null) ? "" : var6.getMessage() });
        }
    }
    
    @Override
    public List addTabCompletionOptions(final ICommandSender sender, final String[] args, final BlockPos pos) {
        return (args.length == 1) ? CommandBase.getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getAllUsernames()) : null;
    }
    
    @Override
    public boolean isUsernameIndex(final String[] args, final int index) {
        return index == 0;
    }
}
