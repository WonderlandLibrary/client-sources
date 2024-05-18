package net.minecraft.command.server;

import net.minecraft.entity.player.*;
import net.minecraft.server.*;
import net.minecraft.server.management.*;
import net.minecraft.command.*;
import net.minecraft.util.*;
import java.util.*;

public class CommandEmote extends CommandBase
{
    private static final String[] I;
    
    @Override
    public String getCommandUsage(final ICommandSender commandSender) {
        return CommandEmote.I[" ".length()];
    }
    
    @Override
    public String getCommandName() {
        return CommandEmote.I["".length()];
    }
    
    static {
        I();
    }
    
    @Override
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        if (array.length <= 0) {
            throw new WrongUsageException(CommandEmote.I["  ".length()], new Object["".length()]);
        }
        final int length = "".length();
        int n;
        if (commandSender instanceof EntityPlayer) {
            n = "".length();
            "".length();
            if (-1 != -1) {
                throw null;
            }
        }
        else {
            n = " ".length();
        }
        final IChatComponent chatComponentFromNthArg = CommandBase.getChatComponentFromNthArg(commandSender, array, length, n != 0);
        final ServerConfigurationManager configurationManager = MinecraftServer.getServer().getConfigurationManager();
        final String s = CommandEmote.I["   ".length()];
        final Object[] array2 = new Object["  ".length()];
        array2["".length()] = commandSender.getDisplayName();
        array2[" ".length()] = chatComponentFromNthArg;
        configurationManager.sendChatMsg(new ChatComponentTranslation(s, array2));
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
            if (-1 != -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public List<String> addTabCompletionOptions(final ICommandSender commandSender, final String[] array, final BlockPos blockPos) {
        return CommandBase.getListOfStringsMatchingLastWord(array, MinecraftServer.getServer().getAllUsernames());
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return "".length();
    }
    
    private static void I() {
        (I = new String[0xE ^ 0xA])["".length()] = I("  ", "MEJyI");
        CommandEmote.I[" ".length()] = I("\u000b=8?\u0002\u00066&|\u000e\r| !\u0002\u000f7", "hRURc");
        CommandEmote.I["  ".length()] = I("(>\u000e\u001b5%5\u0010X9.\u007f\u0016\u00055,4", "KQcvT");
        CommandEmote.I["   ".length()] = I("\b\u001e\u00132v\u001f\u000f\u0002#v\u000e\u001b\u001d2=", "kvrFX");
    }
}
