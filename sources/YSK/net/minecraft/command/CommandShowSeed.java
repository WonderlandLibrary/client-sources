package net.minecraft.command;

import net.minecraft.entity.player.*;
import net.minecraft.server.*;
import net.minecraft.util.*;
import net.minecraft.world.*;

public class CommandShowSeed extends CommandBase
{
    private static final String[] I;
    
    static {
        I();
    }
    
    @Override
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        World world;
        if (commandSender instanceof EntityPlayer) {
            world = ((EntityPlayer)commandSender).worldObj;
            "".length();
            if (-1 >= 1) {
                throw null;
            }
        }
        else {
            world = MinecraftServer.getServer().worldServerForDimension("".length());
        }
        final World world2 = world;
        final String s = CommandShowSeed.I["  ".length()];
        final Object[] array2 = new Object[" ".length()];
        array2["".length()] = world2.getSeed();
        commandSender.addChatMessage(new ChatComponentTranslation(s, array2));
    }
    
    @Override
    public String getCommandName() {
        return CommandShowSeed.I["".length()];
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (4 <= 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return "  ".length();
    }
    
    @Override
    public String getCommandUsage(final ICommandSender commandSender) {
        return CommandShowSeed.I[" ".length()];
    }
    
    private static void I() {
        (I = new String["   ".length()])["".length()] = I("\u001d145", "nTQQx");
        CommandShowSeed.I[" ".length()] = I("\u0013\u0018\b\u001e&\u001e\u0013\u0016]4\u0015\u0012\u0001]2\u0003\u0016\u0002\u0016", "pwesG");
        CommandShowSeed.I["  ".length()] = I("2#\u001c'\u0010?(\u0002d\u00024)\u0015d\u0002$/\u0012/\u0002\"", "QLqJq");
    }
    
    @Override
    public boolean canCommandSenderUseCommand(final ICommandSender commandSender) {
        if (!MinecraftServer.getServer().isSinglePlayer() && !super.canCommandSenderUseCommand(commandSender)) {
            return "".length() != 0;
        }
        return " ".length() != 0;
    }
}
