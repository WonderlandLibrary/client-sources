package net.minecraft.command.server;

import net.minecraft.server.*;
import net.minecraft.command.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.server.management.*;

public class CommandPardonIp extends CommandBase
{
    private static final String[] I;
    
    @Override
    public String getCommandUsage(final ICommandSender commandSender) {
        return CommandPardonIp.I[" ".length()];
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
            if (3 != 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public boolean canCommandSenderUseCommand(final ICommandSender commandSender) {
        if (MinecraftServer.getServer().getConfigurationManager().getBannedIPs().isLanServer() && super.canCommandSenderUseCommand(commandSender)) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    public String getCommandName() {
        return CommandPardonIp.I["".length()];
    }
    
    private static void I() {
        (I = new String[0x58 ^ 0x5D])["".length()] = I("4\u0016\u0006 \u0017*Z\u001d4", "DwtDx");
        CommandPardonIp.I[" ".length()] = I("\n=#\u0017\f\u00076=T\u0018\u00070/\u0014\u0004\u0019|;\t\f\u000e7", "iRNzm");
        CommandPardonIp.I["  ".length()] = I("'#:#&*($`2*.6 .4b$;$')$=", "DLWNG");
        CommandPardonIp.I["   ".length()] = I("18)\u0002\u0015<37A\u0001<5%\u0001\u001d\"y-\u0001\u00023;-\u000b", "RWDot");
        CommandPardonIp.I[0x14 ^ 0x10] = I("\u0007\u0019\u001f\"\u0019\n\u0012\u0001a\r\n\u0014\u0013!\u0011\u0014X\u0007<\u0019\u0003\u0013", "dvrOx");
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return "   ".length();
    }
    
    @Override
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        if (array.length != " ".length() || array["".length()].length() <= " ".length()) {
            throw new WrongUsageException(CommandPardonIp.I[0x23 ^ 0x27], new Object["".length()]);
        }
        if (!CommandBanIp.field_147211_a.matcher(array["".length()]).matches()) {
            throw new SyntaxErrorException(CommandPardonIp.I["   ".length()], new Object["".length()]);
        }
        ((UserList<String, V>)MinecraftServer.getServer().getConfigurationManager().getBannedIPs()).removeEntry(array["".length()]);
        final String s = CommandPardonIp.I["  ".length()];
        final Object[] array2 = new Object[" ".length()];
        array2["".length()] = array["".length()];
        CommandBase.notifyOperators(commandSender, this, s, array2);
        "".length();
        if (3 <= -1) {
            throw null;
        }
    }
    
    @Override
    public List<String> addTabCompletionOptions(final ICommandSender commandSender, final String[] array, final BlockPos blockPos) {
        List<String> listOfStringsMatchingLastWord;
        if (array.length == " ".length()) {
            listOfStringsMatchingLastWord = CommandBase.getListOfStringsMatchingLastWord(array, MinecraftServer.getServer().getConfigurationManager().getBannedIPs().getKeys());
            "".length();
            if (3 < 1) {
                throw null;
            }
        }
        else {
            listOfStringsMatchingLastWord = null;
        }
        return listOfStringsMatchingLastWord;
    }
    
    static {
        I();
    }
}
