package net.minecraft.command.server;

import net.minecraft.util.*;
import java.util.*;
import net.minecraft.server.*;
import net.minecraft.command.*;
import com.mojang.authlib.*;

public class CommandDeOp extends CommandBase
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
            if (2 == 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public List<String> addTabCompletionOptions(final ICommandSender commandSender, final String[] array, final BlockPos blockPos) {
        List<String> listOfStringsMatchingLastWord;
        if (array.length == " ".length()) {
            listOfStringsMatchingLastWord = CommandBase.getListOfStringsMatchingLastWord(array, MinecraftServer.getServer().getConfigurationManager().getOppedPlayerNames());
            "".length();
            if (true != true) {
                throw null;
            }
        }
        else {
            listOfStringsMatchingLastWord = null;
        }
        return listOfStringsMatchingLastWord;
    }
    
    @Override
    public String getCommandName() {
        return CommandDeOp.I["".length()];
    }
    
    static {
        I();
    }
    
    @Override
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        if (array.length != " ".length() || array["".length()].length() <= 0) {
            throw new WrongUsageException(CommandDeOp.I[0x80 ^ 0x84], new Object["".length()]);
        }
        final MinecraftServer server = MinecraftServer.getServer();
        final GameProfile gameProfileFromName = server.getConfigurationManager().getOppedPlayers().getGameProfileFromName(array["".length()]);
        if (gameProfileFromName == null) {
            final String s = CommandDeOp.I["  ".length()];
            final Object[] array2 = new Object[" ".length()];
            array2["".length()] = array["".length()];
            throw new CommandException(s, array2);
        }
        server.getConfigurationManager().removeOp(gameProfileFromName);
        final String s2 = CommandDeOp.I["   ".length()];
        final Object[] array3 = new Object[" ".length()];
        array3["".length()] = array["".length()];
        CommandBase.notifyOperators(commandSender, this, s2, array3);
        "".length();
        if (3 < -1) {
            throw null;
        }
    }
    
    private static void I() {
        (I = new String[0x6 ^ 0x3])["".length()] = I("\n'\u000e\u0003", "nBasI");
        CommandDeOp.I[" ".length()] = I("\u0017*5,1\u001a!+o4\u0011*(o%\u0007$?$", "tEXAP");
        CommandDeOp.I["  ".length()] = I("\u0004?,;-\t42x(\u0002?1x*\u00069-3(", "gPAVL");
        CommandDeOp.I["   ".length()] = I("\u000f:\u0005\f\u0017\u00021\u001bO\u0012\t:\u0018O\u0005\u00196\u000b\u0004\u0005\u001f", "lUhav");
        CommandDeOp.I[0xAE ^ 0xAA] = I("%)\u0017%4(\"\tf1#)\nf 5'\u001d-", "FFzHU");
    }
    
    @Override
    public String getCommandUsage(final ICommandSender commandSender) {
        return CommandDeOp.I[" ".length()];
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return "   ".length();
    }
}
