package net.minecraft.command;

import net.minecraft.server.*;

public class CommandSetPlayerTimeout extends CommandBase
{
    private static final String[] I;
    
    private static void I() {
        (I = new String[0x31 ^ 0x35])["".length()] = I("?\u0000\u0016\u0010\u0005 \u0000\u0016\u0010\f)\n\u0017\r", "Lebya");
        CommandSetPlayerTimeout.I[" ".length()] = I("0\u0007:/%=\f$l76\u001c>&(6\u001c>/!<\u001d#l1 \t0'", "ShWBD");
        CommandSetPlayerTimeout.I["  ".length()] = I(",,\b8+!'\u0016{9*7\f1&*7\f8/ 6\u0011{?<\"\u00020", "OCeUJ");
        CommandSetPlayerTimeout.I["   ".length()] = I("\f\t\u0017\"\u0010\u0001\u0002\ta\u0002\n\u0012\u0013+\u001d\n\u0012\u0013\"\u0014\u0000\u0013\u000ea\u0002\u001a\u0005\u0019*\u0002\u001c", "ofzOq");
    }
    
    @Override
    public String getCommandName() {
        return CommandSetPlayerTimeout.I["".length()];
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return "   ".length();
    }
    
    @Override
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        if (array.length != " ".length()) {
            throw new WrongUsageException(CommandSetPlayerTimeout.I["  ".length()], new Object["".length()]);
        }
        final int int1 = CommandBase.parseInt(array["".length()], "".length());
        MinecraftServer.getServer().setPlayerIdleTimeout(int1);
        final String s = CommandSetPlayerTimeout.I["   ".length()];
        final Object[] array2 = new Object[" ".length()];
        array2["".length()] = int1;
        CommandBase.notifyOperators(commandSender, this, s, array2);
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
            if (4 != 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public String getCommandUsage(final ICommandSender commandSender) {
        return CommandSetPlayerTimeout.I[" ".length()];
    }
    
    static {
        I();
    }
}
