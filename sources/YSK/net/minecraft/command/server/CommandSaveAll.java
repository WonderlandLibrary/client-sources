package net.minecraft.command.server;

import net.minecraft.server.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.command.*;

public class CommandSaveAll extends CommandBase
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
            if (4 != 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    private static void I() {
        (I = new String[0x2E ^ 0x26])["".length()] = I("\u000b-=\u0000f\u0019 '", "xLKeK");
        CommandSaveAll.I[" ".length()] = I("$>\u0014\b\u0012)5\nK\u0000&'\u001cK\u000640\u001e\u0000", "GQyes");
        CommandSaveAll.I["  ".length()] = I("\n74,\u000f\u0007<*o\u001d\b.<o\u001d\u001d9+5", "iXYAn");
        CommandSaveAll.I["   ".length()] = I("!\u001a10\u001c", "GvDCt");
        CommandSaveAll.I[0x6 ^ 0x2] = I("\"\u001b\u001d\b9/\u0010\u0003K+ \u0002\u0015K>-\u0001\u0003\r\u000b5\u0015\u0002\u0011", "AtpeX");
        CommandSaveAll.I[0xA3 ^ 0xA6] = I("2\"*\u0017\u001b?)4T\t0;\"T\u001c=84\u0012??)", "QMGzz");
        CommandSaveAll.I[0x2A ^ 0x2C] = I("\u0014+$>)\u0019 :};\u00162,}.\u0016-%6,", "wDISH");
        CommandSaveAll.I[0x32 ^ 0x35] = I("1?\u000e\u001c*<4\u0010_83&\u0006_8'3\u0000\u00148!", "RPcqK");
    }
    
    @Override
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        final MinecraftServer server = MinecraftServer.getServer();
        commandSender.addChatMessage(new ChatComponentTranslation(CommandSaveAll.I["  ".length()], new Object["".length()]));
        if (server.getConfigurationManager() != null) {
            server.getConfigurationManager().saveAllPlayerData();
        }
        try {
            int i = "".length();
            "".length();
            if (4 < -1) {
                throw null;
            }
            while (i < server.worldServers.length) {
                if (server.worldServers[i] != null) {
                    final WorldServer worldServer = server.worldServers[i];
                    final boolean disableLevelSaving = worldServer.disableLevelSaving;
                    worldServer.disableLevelSaving = ("".length() != 0);
                    worldServer.saveAllChunks(" ".length() != 0, null);
                    worldServer.disableLevelSaving = disableLevelSaving;
                }
                ++i;
            }
            if (array.length > 0 && CommandSaveAll.I["   ".length()].equals(array["".length()])) {
                commandSender.addChatMessage(new ChatComponentTranslation(CommandSaveAll.I[0xB4 ^ 0xB0], new Object["".length()]));
                int j = "".length();
                "".length();
                if (3 < 2) {
                    throw null;
                }
                while (j < server.worldServers.length) {
                    if (server.worldServers[j] != null) {
                        final WorldServer worldServer2 = server.worldServers[j];
                        final boolean disableLevelSaving2 = worldServer2.disableLevelSaving;
                        worldServer2.disableLevelSaving = ("".length() != 0);
                        worldServer2.saveChunkData();
                        worldServer2.disableLevelSaving = disableLevelSaving2;
                    }
                    ++j;
                }
                commandSender.addChatMessage(new ChatComponentTranslation(CommandSaveAll.I[0x34 ^ 0x31], new Object["".length()]));
                "".length();
                if (3 < 1) {
                    throw null;
                }
            }
        }
        catch (MinecraftException ex) {
            final String s = CommandSaveAll.I[0x4A ^ 0x4C];
            final Object[] array2 = new Object[" ".length()];
            array2["".length()] = ex.getMessage();
            CommandBase.notifyOperators(commandSender, this, s, array2);
            return;
        }
        CommandBase.notifyOperators(commandSender, this, CommandSaveAll.I[0x58 ^ 0x5F], new Object["".length()]);
    }
    
    static {
        I();
    }
    
    @Override
    public String getCommandUsage(final ICommandSender commandSender) {
        return CommandSaveAll.I[" ".length()];
    }
    
    @Override
    public String getCommandName() {
        return CommandSaveAll.I["".length()];
    }
}
