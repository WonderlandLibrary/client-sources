package net.minecraft.command;

import net.minecraft.server.*;
import net.minecraft.world.storage.*;

public class CommandToggleDownfall extends CommandBase
{
    private static final String[] I;
    
    protected void toggleDownfall() {
        final WorldInfo worldInfo;
        int raining;
        if ((worldInfo = MinecraftServer.getServer().worldServers["".length()].getWorldInfo()).isRaining()) {
            raining = "".length();
            "".length();
            if (2 <= -1) {
                throw null;
            }
        }
        else {
            raining = " ".length();
        }
        worldInfo.setRaining(raining != 0);
    }
    
    @Override
    public String getCommandUsage(final ICommandSender commandSender) {
        return CommandToggleDownfall.I[" ".length()];
    }
    
    static {
        I();
    }
    
    private static void I() {
        (I = new String["   ".length()])["".length()] = I("'\u000b\u00154\u00006\u0000\u001d$\u00025\u0005\u001e?", "SdrSl");
        CommandToggleDownfall.I[" ".length()] = I("\u000f\u0017$\b9\u0002\u001c:K<\u0003\u000f'\u00039\u0000\u0014g\u0010+\r\u001f,", "lxIeX");
        CommandToggleDownfall.I["  ".length()] = I("&  \u001f\u0004++>\\\u0001*8#\u0014\u0004)#c\u0001\u0010&,(\u0001\u0016", "EOMre");
    }
    
    @Override
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        this.toggleDownfall();
        CommandBase.notifyOperators(commandSender, this, CommandToggleDownfall.I["  ".length()], new Object["".length()]);
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return "  ".length();
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
            if (1 >= 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public String getCommandName() {
        return CommandToggleDownfall.I["".length()];
    }
}
