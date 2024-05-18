package net.minecraft.command.server;

import net.minecraft.server.*;
import net.minecraft.command.*;
import net.minecraft.util.*;
import java.util.*;

public class CommandListBans extends CommandBase
{
    private static final String[] I;
    
    @Override
    public boolean canCommandSenderUseCommand(final ICommandSender commandSender) {
        if ((MinecraftServer.getServer().getConfigurationManager().getBannedIPs().isLanServer() || MinecraftServer.getServer().getConfigurationManager().getBannedPlayers().isLanServer()) && super.canCommandSenderUseCommand(commandSender)) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    public String getCommandUsage(final ICommandSender commandSender) {
        return CommandListBans.I[" ".length()];
    }
    
    @Override
    public String getCommandName() {
        return CommandListBans.I["".length()];
    }
    
    static {
        I();
    }
    
    @Override
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        if (array.length >= " ".length() && array["".length()].equalsIgnoreCase(CommandListBans.I["  ".length()])) {
            final String s = CommandListBans.I["   ".length()];
            final Object[] array2 = new Object[" ".length()];
            array2["".length()] = MinecraftServer.getServer().getConfigurationManager().getBannedIPs().getKeys().length;
            commandSender.addChatMessage(new ChatComponentTranslation(s, array2));
            commandSender.addChatMessage(new ChatComponentText(CommandBase.joinNiceString(MinecraftServer.getServer().getConfigurationManager().getBannedIPs().getKeys())));
            "".length();
            if (2 == 1) {
                throw null;
            }
        }
        else {
            final String s2 = CommandListBans.I[0x16 ^ 0x12];
            final Object[] array3 = new Object[" ".length()];
            array3["".length()] = MinecraftServer.getServer().getConfigurationManager().getBannedPlayers().getKeys().length;
            commandSender.addChatMessage(new ChatComponentTranslation(s2, array3));
            commandSender.addChatMessage(new ChatComponentText(CommandBase.joinNiceString(MinecraftServer.getServer().getConfigurationManager().getBannedPlayers().getKeys())));
        }
    }
    
    @Override
    public List<String> addTabCompletionOptions(final ICommandSender commandSender, final String[] array, final BlockPos blockPos) {
        List<String> listOfStringsMatchingLastWord;
        if (array.length == " ".length()) {
            final String[] array2 = new String["  ".length()];
            array2["".length()] = CommandListBans.I[0xB3 ^ 0xB6];
            array2[" ".length()] = CommandListBans.I[0x90 ^ 0x96];
            listOfStringsMatchingLastWord = CommandBase.getListOfStringsMatchingLastWord(array, array2);
            "".length();
            if (4 <= 0) {
                throw null;
            }
        }
        else {
            listOfStringsMatchingLastWord = null;
        }
        return listOfStringsMatchingLastWord;
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
            if (1 < 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return "   ".length();
    }
    
    private static void I() {
        (I = new String[0x12 ^ 0x15])["".length()] = I("\u00011\n\u000f\u0001\u0010$", "cPdch");
        CommandListBans.I[" ".length()] = I("3=(!'>66b$1<)%5$|0?'77", "PRELF");
        CommandListBans.I["  ".length()] = I("\u0004!\u001c", "mQoNu");
        CommandListBans.I["   ".length()] = I("4%:\"\f9.$a\u000f6$;&\u001e#d>?\u001e", "WJWOm");
        CommandListBans.I[0x94 ^ 0x90] = I("$!:\u0005\u0003)*$F\u0000& ;\u0001\u00113`'\u0004\u0003>+%\u001b", "GNWhb");
        CommandListBans.I[0x46 ^ 0x43] = I("\u0019\u001b\u0000\t\u0003\u001b\u0004", "iwapf");
        CommandListBans.I[0xBB ^ 0xBD] = I(".3\u0000", "GCsta");
    }
}
