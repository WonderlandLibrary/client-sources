package net.minecraft.command.server;

import net.minecraft.server.*;
import net.minecraft.util.*;
import net.minecraft.server.management.*;
import net.minecraft.command.*;

public class CommandListPlayers extends CommandBase
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
            if (1 >= 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return "".length();
    }
    
    private static void I() {
        (I = new String[0x12 ^ 0x16])["".length()] = I("\u0019\r>>", "udMJl");
        CommandListPlayers.I[" ".length()] = I("\u0010\u001d\u00179\u0013\u001d\u0016\tz\u0002\u001f\u0013\u00031\u0000\u0000\\\u000f'\u0013\u0014\u0017", "srzTr");
        CommandListPlayers.I["  ".length()] = I("$!%\t2)*;J#+/1\u0001!4`$\r 3", "GNHdS");
        CommandListPlayers.I["   ".length()] = I("\u0013\u001d\u0018\u000b>", "fhqoM");
    }
    
    static {
        I();
    }
    
    @Override
    public String getCommandName() {
        return CommandListPlayers.I["".length()];
    }
    
    @Override
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        final int currentPlayerCount = MinecraftServer.getServer().getCurrentPlayerCount();
        final String s = CommandListPlayers.I["  ".length()];
        final Object[] array2 = new Object["  ".length()];
        array2["".length()] = currentPlayerCount;
        array2[" ".length()] = MinecraftServer.getServer().getMaxPlayers();
        commandSender.addChatMessage(new ChatComponentTranslation(s, array2));
        final ServerConfigurationManager configurationManager = MinecraftServer.getServer().getConfigurationManager();
        int n;
        if (array.length > 0 && CommandListPlayers.I["   ".length()].equalsIgnoreCase(array["".length()])) {
            n = " ".length();
            "".length();
            if (2 < -1) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        commandSender.addChatMessage(new ChatComponentText(configurationManager.func_181058_b(n != 0)));
        commandSender.setCommandStat(CommandResultStats.Type.QUERY_RESULT, currentPlayerCount);
    }
    
    @Override
    public String getCommandUsage(final ICommandSender commandSender) {
        return CommandListPlayers.I[" ".length()];
    }
}
