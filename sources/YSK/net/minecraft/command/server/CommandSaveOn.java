package net.minecraft.command.server;

import net.minecraft.server.*;
import net.minecraft.command.*;
import net.minecraft.world.*;

public class CommandSaveOn extends CommandBase
{
    private static final String[] I;
    
    static {
        I();
    }
    
    @Override
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        final MinecraftServer server = MinecraftServer.getServer();
        int n = "".length();
        int i = "".length();
        "".length();
        if (!true) {
            throw null;
        }
        while (i < server.worldServers.length) {
            if (server.worldServers[i] != null) {
                final WorldServer worldServer = server.worldServers[i];
                if (worldServer.disableLevelSaving) {
                    worldServer.disableLevelSaving = ("".length() != 0);
                    n = " ".length();
                }
            }
            ++i;
        }
        if (n == 0) {
            throw new CommandException(CommandSaveOn.I["   ".length()], new Object["".length()]);
        }
        CommandBase.notifyOperators(commandSender, this, CommandSaveOn.I["  ".length()], new Object["".length()]);
        "".length();
        if (false) {
            throw null;
        }
    }
    
    private static void I() {
        (I = new String[0xC4 ^ 0xC0])["".length()] = I("\u001f;9\u0002F\u00034", "lZOgk");
        CommandSaveOn.I[" ".length()] = I("\u001b:\u001d\u0001\u0003\u00161\u0003B\u0011\u0019#\u0015A\r\u0016{\u0005\u001f\u0003\u001f0", "xUplb");
        CommandSaveOn.I["  ".length()] = I("\n-\"\u001c(\u0007&<_:\b4*_,\u0007#-\u001d,\r", "iBOqI");
        CommandSaveOn.I["   ".length()] = I("\u000f7\u0017/\u001b\u0002<\tl\t\r.\u001fo\u0015\u0002v\u001b.\b\t9\u001e;5\u0002", "lXzBz");
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
            if (-1 != -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public String getCommandUsage(final ICommandSender commandSender) {
        return CommandSaveOn.I[" ".length()];
    }
    
    @Override
    public String getCommandName() {
        return CommandSaveOn.I["".length()];
    }
}
