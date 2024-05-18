package net.minecraft.command.server;

import net.minecraft.server.*;
import net.minecraft.command.*;
import net.minecraft.world.*;

public class CommandSaveOff extends CommandBase
{
    private static final String[] I;
    
    @Override
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        final MinecraftServer server = MinecraftServer.getServer();
        int n = "".length();
        int i = "".length();
        "".length();
        if (4 != 4) {
            throw null;
        }
        while (i < server.worldServers.length) {
            if (server.worldServers[i] != null) {
                final WorldServer worldServer = server.worldServers[i];
                if (!worldServer.disableLevelSaving) {
                    worldServer.disableLevelSaving = (" ".length() != 0);
                    n = " ".length();
                }
            }
            ++i;
        }
        if (n == 0) {
            throw new CommandException(CommandSaveOff.I["   ".length()], new Object["".length()]);
        }
        CommandBase.notifyOperators(commandSender, this, CommandSaveOff.I["  ".length()], new Object["".length()]);
        "".length();
        if (3 == 4) {
            throw null;
        }
    }
    
    private static void I() {
        (I = new String[0xAB ^ 0xAF])["".length()] = I("\u0001\u001b\u00036i\u001d\u001c\u0013", "rzuSD");
        CommandSaveOff.I[" ".length()] = I("'\u001e\u0015>\u0003*\u0015\u000b}\u0011%\u0007\u001d~\r\"\u0017V&\u0011%\u0016\u001d", "DqxSb");
        CommandSaveOff.I["  ".length()] = I("\u0011%%9\u0012\u001c.;z\u0000\u0013<-z\u0017\u001b9)6\u001f\u0017.", "rJHTs");
        CommandSaveOff.I["   ".length()] = I(")\u001a'\u001c\u0012$\u00119_\u0000+\u0003/\\\u001c,\u0013d\u0010\u001f8\u0010+\u0015\n\u0005\u0013,", "JuJqs");
    }
    
    static {
        I();
    }
    
    @Override
    public String getCommandName() {
        return CommandSaveOff.I["".length()];
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
        return CommandSaveOff.I[" ".length()];
    }
}
