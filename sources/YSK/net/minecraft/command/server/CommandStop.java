package net.minecraft.command.server;

import net.minecraft.server.*;
import net.minecraft.command.*;

public class CommandStop extends CommandBase
{
    private static final String[] I;
    
    @Override
    public String getCommandName() {
        return CommandStop.I["".length()];
    }
    
    @Override
    public String getCommandUsage(final ICommandSender commandSender) {
        return CommandStop.I[" ".length()];
    }
    
    @Override
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        if (MinecraftServer.getServer().worldServers != null) {
            CommandBase.notifyOperators(commandSender, this, CommandStop.I["  ".length()], new Object["".length()]);
        }
        MinecraftServer.getServer().initiateShutdown();
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
            if (0 == 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    private static void I() {
        (I = new String["   ".length()])["".length()] = I("\u00186%&", "kBJVQ");
        CommandStop.I[" ".length()] = I("\u000b69#5\u0006='`'\u001c6$`!\u001b83+", "hYTNT");
        CommandStop.I["  ".length()] = I(":#\"\u001b27(<X -#?X --=\u0002", "YLOvS");
    }
    
    static {
        I();
    }
}
