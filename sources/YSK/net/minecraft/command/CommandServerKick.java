package net.minecraft.command;

import net.minecraft.server.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import java.util.*;

public class CommandServerKick extends CommandBase
{
    private static final String[] I;
    
    @Override
    public int getRequiredPermissionLevel() {
        return "   ".length();
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
    public String getCommandUsage(final ICommandSender commandSender) {
        return CommandServerKick.I[" ".length()];
    }
    
    static {
        I();
    }
    
    @Override
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        if (array.length <= 0 || array["".length()].length() <= " ".length()) {
            throw new WrongUsageException(CommandServerKick.I[0x5C ^ 0x59], new Object["".length()]);
        }
        final EntityPlayerMP playerByUsername = MinecraftServer.getServer().getConfigurationManager().getPlayerByUsername(array["".length()]);
        String unformattedText = CommandServerKick.I["  ".length()];
        int n = "".length();
        if (playerByUsername == null) {
            throw new PlayerNotFoundException();
        }
        if (array.length >= "  ".length()) {
            unformattedText = CommandBase.getChatComponentFromNthArg(commandSender, array, " ".length()).getUnformattedText();
            n = " ".length();
        }
        playerByUsername.playerNetServerHandler.kickPlayerFromServer(unformattedText);
        if (n != 0) {
            final String s = CommandServerKick.I["   ".length()];
            final Object[] array2 = new Object["  ".length()];
            array2["".length()] = playerByUsername.getName();
            array2[" ".length()] = unformattedText;
            CommandBase.notifyOperators(commandSender, this, s, array2);
            "".length();
            if (-1 < -1) {
                throw null;
            }
        }
        else {
            final String s2 = CommandServerKick.I[0x5D ^ 0x59];
            final Object[] array3 = new Object[" ".length()];
            array3["".length()] = playerByUsername.getName();
            CommandBase.notifyOperators(commandSender, this, s2, array3);
            "".length();
            if (1 == -1) {
                throw null;
            }
        }
    }
    
    @Override
    public String getCommandName() {
        return CommandServerKick.I["".length()];
    }
    
    @Override
    public List<String> addTabCompletionOptions(final ICommandSender commandSender, final String[] array, final BlockPos blockPos) {
        List<String> listOfStringsMatchingLastWord;
        if (array.length >= " ".length()) {
            listOfStringsMatchingLastWord = CommandBase.getListOfStringsMatchingLastWord(array, MinecraftServer.getServer().getAllUsernames());
            "".length();
            if (4 != 4) {
                throw null;
            }
        }
        else {
            listOfStringsMatchingLastWord = null;
        }
        return listOfStringsMatchingLastWord;
    }
    
    private static void I() {
        (I = new String[0x40 ^ 0x46])["".length()] = I("\u0000*6.", "kCUEH");
        CommandServerKick.I[" ".length()] = I("0\"\u00057\r=)\u001bt\u0007:.\u0003t\u0019 ,\u000f?", "SMhZl");
        CommandServerKick.I["  ".length()] = I("(\u0011\"\u0011\u001f\u0007X#\u0003Z\u0002\u0016a\u0015\n\u0006\n \u000e\u0015\u0011V", "cxAzz");
        CommandServerKick.I["   ".length()] = I("\u0007\u0002\n;\u000e\n\t\u0014x\u0004\r\u000e\fx\u001c\u0011\u000e\u00043\u001c\u0017C\u00153\u000e\u0017\u0002\t", "dmgVo");
        CommandServerKick.I[0xAB ^ 0xAF] = I(".=*\n&#64I,$1,I481$\u00024>", "MRGgG");
        CommandServerKick.I[0x2A ^ 0x2F] = I("\u001a\u0007\u0000\u0015\u0013\u0017\f\u001eV\u0019\u0010\u000b\u0006V\u0007\n\t\n\u001d", "yhmxr");
    }
}
