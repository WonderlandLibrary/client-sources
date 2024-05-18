package net.minecraft.command.server;

import java.util.*;
import net.minecraft.server.*;
import net.minecraft.util.*;
import net.minecraft.server.management.*;
import net.minecraft.command.*;

public class CommandBroadcast extends CommandBase
{
    private static final String[] I;
    
    @Override
    public List<String> addTabCompletionOptions(final ICommandSender commandSender, final String[] array, final BlockPos blockPos) {
        List<String> listOfStringsMatchingLastWord;
        if (array.length >= " ".length()) {
            listOfStringsMatchingLastWord = CommandBase.getListOfStringsMatchingLastWord(array, MinecraftServer.getServer().getAllUsernames());
            "".length();
            if (0 <= -1) {
                throw null;
            }
        }
        else {
            listOfStringsMatchingLastWord = null;
        }
        return listOfStringsMatchingLastWord;
    }
    
    @Override
    public String getCommandUsage(final ICommandSender commandSender) {
        return CommandBroadcast.I[" ".length()];
    }
    
    @Override
    public String getCommandName() {
        return CommandBroadcast.I["".length()];
    }
    
    static {
        I();
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
            if (4 <= 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return " ".length();
    }
    
    private static void I() {
        (I = new String[0x38 ^ 0x3C])["".length()] = I("&1\u0015", "UPlkD");
        CommandBroadcast.I[" ".length()] = I("\f7\u0014\u001d\u0018\u0001<\n^\n\u000e!W\u0005\n\u000e?\u001c", "oXypy");
        CommandBroadcast.I["  ".length()] = I("&\u001036I1\u0001\"'I$\u0016<-\u0012+\u001b7/\u0002+\f", "ExRBg");
        CommandBroadcast.I["   ".length()] = I("\u000e\u00165)\f\u0003\u001d+j\u001e\f\u0000v1\u001e\f\u001e=", "myXDm");
    }
    
    @Override
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        if (array.length <= 0 || array["".length()].length() <= 0) {
            throw new WrongUsageException(CommandBroadcast.I["   ".length()], new Object["".length()]);
        }
        final IChatComponent chatComponentFromNthArg = CommandBase.getChatComponentFromNthArg(commandSender, array, "".length(), " ".length() != 0);
        final ServerConfigurationManager configurationManager = MinecraftServer.getServer().getConfigurationManager();
        final String s = CommandBroadcast.I["  ".length()];
        final Object[] array2 = new Object["  ".length()];
        array2["".length()] = commandSender.getDisplayName();
        array2[" ".length()] = chatComponentFromNthArg;
        configurationManager.sendChatMsg(new ChatComponentTranslation(s, array2));
        "".length();
        if (2 == -1) {
            throw null;
        }
    }
}
