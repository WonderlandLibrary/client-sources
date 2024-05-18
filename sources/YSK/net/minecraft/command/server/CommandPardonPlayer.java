package net.minecraft.command.server;

import net.minecraft.util.*;
import java.util.*;
import net.minecraft.server.*;
import net.minecraft.command.*;
import com.mojang.authlib.*;
import net.minecraft.server.management.*;

public class CommandPardonPlayer extends CommandBase
{
    private static final String[] I;
    
    @Override
    public String getCommandName() {
        return CommandPardonPlayer.I["".length()];
    }
    
    static {
        I();
    }
    
    @Override
    public List<String> addTabCompletionOptions(final ICommandSender commandSender, final String[] array, final BlockPos blockPos) {
        List<String> listOfStringsMatchingLastWord;
        if (array.length == " ".length()) {
            listOfStringsMatchingLastWord = CommandBase.getListOfStringsMatchingLastWord(array, MinecraftServer.getServer().getConfigurationManager().getBannedPlayers().getKeys());
            "".length();
            if (2 <= -1) {
                throw null;
            }
        }
        else {
            listOfStringsMatchingLastWord = null;
        }
        return listOfStringsMatchingLastWord;
    }
    
    private static void I() {
        (I = new String[0x75 ^ 0x70])["".length()] = I("\u00124\u00032+\f", "bUqVD");
        CommandPardonPlayer.I[" ".length()] = I("46\u0006\u001f/9=\u0018\\;9;\n\u001c`\"*\n\u0015+", "WYkrN");
        CommandPardonPlayer.I["  ".length()] = I("-5\u0019\u0015\" >\u0007V6 8\u0015\u0016m(;\u001d\u0014&*", "NZtxC");
        CommandPardonPlayer.I["   ".length()] = I("\r\u000e\u000b$3\u0000\u0005\u0015g'\u0000\u0003\u0007'|\u001d\u0014\u0005*7\u001d\u0012", "nafIR");
        CommandPardonPlayer.I[0x9C ^ 0x98] = I("\u0007<,5\t\n72v\u001d\n1 6F\u0011  ?\r", "dSAXh");
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
            if (-1 >= 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return "   ".length();
    }
    
    @Override
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        if (array.length != " ".length() || array["".length()].length() <= 0) {
            throw new WrongUsageException(CommandPardonPlayer.I[0x83 ^ 0x87], new Object["".length()]);
        }
        final MinecraftServer server = MinecraftServer.getServer();
        final GameProfile usernameBanned = server.getConfigurationManager().getBannedPlayers().isUsernameBanned(array["".length()]);
        if (usernameBanned == null) {
            final String s = CommandPardonPlayer.I["  ".length()];
            final Object[] array2 = new Object[" ".length()];
            array2["".length()] = array["".length()];
            throw new CommandException(s, array2);
        }
        ((UserList<GameProfile, V>)server.getConfigurationManager().getBannedPlayers()).removeEntry(usernameBanned);
        final String s2 = CommandPardonPlayer.I["   ".length()];
        final Object[] array3 = new Object[" ".length()];
        array3["".length()] = array["".length()];
        CommandBase.notifyOperators(commandSender, this, s2, array3);
        "".length();
        if (1 == -1) {
            throw null;
        }
    }
    
    @Override
    public String getCommandUsage(final ICommandSender commandSender) {
        return CommandPardonPlayer.I[" ".length()];
    }
    
    @Override
    public boolean canCommandSenderUseCommand(final ICommandSender commandSender) {
        if (MinecraftServer.getServer().getConfigurationManager().getBannedPlayers().isLanServer() && super.canCommandSenderUseCommand(commandSender)) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
}
