package net.minecraft.command;

import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.server.*;

public class CommandKill extends CommandBase
{
    private static final String[] I;
    
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
            if (3 <= 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    private static void I() {
        (I = new String[0x63 ^ 0x67])["".length()] = I(";9\u0016\u0015", "PPzya");
        CommandKill.I[" ".length()] = I("\u0016<,;\u0011\u001b72x\u001b\u001c?-x\u0005\u00062&3", "uSAVp");
        CommandKill.I["  ".length()] = I("!\u000e\u001a\u000b\t,\u0005\u0004H\u0003+\r\u001bH\u001b7\u0002\u0014\u0003\u001b1\u0007\u0002\n", "Bawfh");
        CommandKill.I["   ".length()] = I("(\u0007\u0004\u000e%%\f\u001aM/\"\u0004\u0005M7>\u000b\n\u000678\u000e\u001c\u000f", "KhicD");
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return "  ".length();
    }
    
    @Override
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        if (array.length == 0) {
            final EntityPlayerMP commandSenderAsPlayer = CommandBase.getCommandSenderAsPlayer(commandSender);
            commandSenderAsPlayer.onKillCommand();
            final String s = CommandKill.I["  ".length()];
            final Object[] array2 = new Object[" ".length()];
            array2["".length()] = commandSenderAsPlayer.getDisplayName();
            CommandBase.notifyOperators(commandSender, this, s, array2);
            "".length();
            if (0 < 0) {
                throw null;
            }
        }
        else {
            final Entity func_175768_b = CommandBase.func_175768_b(commandSender, array["".length()]);
            func_175768_b.onKillCommand();
            final String s2 = CommandKill.I["   ".length()];
            final Object[] array3 = new Object[" ".length()];
            array3["".length()] = func_175768_b.getDisplayName();
            CommandBase.notifyOperators(commandSender, this, s2, array3);
        }
    }
    
    @Override
    public boolean isUsernameIndex(final String[] array, final int n) {
        if (n == 0) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    public List<String> addTabCompletionOptions(final ICommandSender commandSender, final String[] array, final BlockPos blockPos) {
        List<String> listOfStringsMatchingLastWord;
        if (array.length == " ".length()) {
            listOfStringsMatchingLastWord = CommandBase.getListOfStringsMatchingLastWord(array, MinecraftServer.getServer().getAllUsernames());
            "".length();
            if (4 < 2) {
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
    
    @Override
    public String getCommandUsage(final ICommandSender commandSender) {
        return CommandKill.I[" ".length()];
    }
    
    @Override
    public String getCommandName() {
        return CommandKill.I["".length()];
    }
}
