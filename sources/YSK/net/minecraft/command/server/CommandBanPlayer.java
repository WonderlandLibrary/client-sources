package net.minecraft.command.server;

import net.minecraft.util.*;
import net.minecraft.server.*;
import java.util.*;
import net.minecraft.command.*;
import com.mojang.authlib.*;
import net.minecraft.entity.player.*;
import net.minecraft.server.management.*;

public class CommandBanPlayer extends CommandBase
{
    private static final String[] I;
    
    @Override
    public String getCommandName() {
        return CommandBanPlayer.I["".length()];
    }
    
    @Override
    public String getCommandUsage(final ICommandSender commandSender) {
        return CommandBanPlayer.I[" ".length()];
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
            if (-1 >= 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public List<String> addTabCompletionOptions(final ICommandSender commandSender, final String[] array, final BlockPos blockPos) {
        List<String> listOfStringsMatchingLastWord;
        if (array.length >= " ".length()) {
            listOfStringsMatchingLastWord = CommandBase.getListOfStringsMatchingLastWord(array, MinecraftServer.getServer().getAllUsernames());
            "".length();
            if (3 <= 1) {
                throw null;
            }
        }
        else {
            listOfStringsMatchingLastWord = null;
        }
        return listOfStringsMatchingLastWord;
    }
    
    private static void I() {
        (I = new String[0x65 ^ 0x63])["".length()] = I("\u00017\n", "cVdVM");
        CommandBanPlayer.I[" ".length()] = I("2\u0000=#\u0011?\u000b#`\u00120\u0001~;\u00030\b5", "QoPNp");
        CommandBanPlayer.I["  ".length()] = I("65\u001c*\u0019;>\u0002i\u001a44_!\u0019<6\u0014#", "UZqGx");
        CommandBanPlayer.I["   ".length()] = I(">;\u001cx6\u00151I:6\t:\f<w\u0001&\u00065w\u0013<\u0000+w\u00141\u001b.2\u0015z", "gTiXW");
        CommandBanPlayer.I[0x94 ^ 0x90] = I("\u0015.\u0007*\t\u0018%\u0019i\n\u0017/D4\u001d\u0015\"\u000f4\u001b", "vAjGh");
        CommandBanPlayer.I[0x87 ^ 0x82] = I("0(\u0006\b#=#\u0018K 2)E\u001012 \u000e", "SGkeB");
    }
    
    @Override
    public boolean canCommandSenderUseCommand(final ICommandSender commandSender) {
        if (MinecraftServer.getServer().getConfigurationManager().getBannedPlayers().isLanServer() && super.canCommandSenderUseCommand(commandSender)) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        if (array.length < " ".length() || array["".length()].length() <= 0) {
            throw new WrongUsageException(CommandBanPlayer.I[0xA9 ^ 0xAC], new Object["".length()]);
        }
        final MinecraftServer server = MinecraftServer.getServer();
        final GameProfile gameProfileForUsername = server.getPlayerProfileCache().getGameProfileForUsername(array["".length()]);
        if (gameProfileForUsername == null) {
            final String s = CommandBanPlayer.I["  ".length()];
            final Object[] array2 = new Object[" ".length()];
            array2["".length()] = array["".length()];
            throw new CommandException(s, array2);
        }
        String unformattedText = null;
        if (array.length >= "  ".length()) {
            unformattedText = CommandBase.getChatComponentFromNthArg(commandSender, array, " ".length()).getUnformattedText();
        }
        ((UserList<K, UserListBansEntry>)server.getConfigurationManager().getBannedPlayers()).addEntry(new UserListBansEntry(gameProfileForUsername, null, commandSender.getName(), null, unformattedText));
        final EntityPlayerMP playerByUsername = server.getConfigurationManager().getPlayerByUsername(array["".length()]);
        if (playerByUsername != null) {
            playerByUsername.playerNetServerHandler.kickPlayerFromServer(CommandBanPlayer.I["   ".length()]);
        }
        final String s2 = CommandBanPlayer.I[0x4A ^ 0x4E];
        final Object[] array3 = new Object[" ".length()];
        array3["".length()] = array["".length()];
        CommandBase.notifyOperators(commandSender, this, s2, array3);
        "".length();
        if (-1 != -1) {
            throw null;
        }
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return "   ".length();
    }
}
