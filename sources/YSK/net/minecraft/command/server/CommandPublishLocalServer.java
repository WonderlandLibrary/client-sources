package net.minecraft.command.server;

import net.minecraft.server.*;
import net.minecraft.world.*;
import net.minecraft.command.*;

public class CommandPublishLocalServer extends CommandBase
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
            if (1 < -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public String getCommandUsage(final ICommandSender commandSender) {
        return CommandPublishLocalServer.I[" ".length()];
    }
    
    private static void I() {
        (I = new String[0xA1 ^ 0xA5])["".length()] = I("\t\u0004\u001a/0\n\u0019", "yqxCY");
        CommandPublishLocalServer.I[" ".length()] = I("\u001a<.\u0004\u0002\u001770G\u0013\f1/\u0000\u0010\u0011}6\u001a\u0002\u001e6", "ySCic");
        CommandPublishLocalServer.I["  ".length()] = I("$\f\u0000/;)\u0007\u001el*2\u0001\u0001+)/M\u001e6;5\u0017\b&", "GcmBZ");
        CommandPublishLocalServer.I["   ".length()] = I("\u000b$\u000e<\u0017\u0006/\u0010\u007f\u0006\u001d)\u000f8\u0005\u0000e\u00050\u001f\u0004.\u0007", "hKcQv");
    }
    
    @Override
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        final String shareToLAN = MinecraftServer.getServer().shareToLAN(WorldSettings.GameType.SURVIVAL, "".length() != 0);
        if (shareToLAN != null) {
            final String s = CommandPublishLocalServer.I["  ".length()];
            final Object[] array2 = new Object[" ".length()];
            array2["".length()] = shareToLAN;
            CommandBase.notifyOperators(commandSender, this, s, array2);
            "".length();
            if (4 <= -1) {
                throw null;
            }
        }
        else {
            CommandBase.notifyOperators(commandSender, this, CommandPublishLocalServer.I["   ".length()], new Object["".length()]);
        }
    }
    
    @Override
    public String getCommandName() {
        return CommandPublishLocalServer.I["".length()];
    }
    
    static {
        I();
    }
}
