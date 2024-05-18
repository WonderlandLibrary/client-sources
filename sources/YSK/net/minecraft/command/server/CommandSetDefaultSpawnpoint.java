package net.minecraft.command.server;

import net.minecraft.server.*;
import net.minecraft.network.play.server.*;
import net.minecraft.network.*;
import net.minecraft.util.*;
import net.minecraft.command.*;
import java.util.*;

public class CommandSetDefaultSpawnpoint extends CommandBase
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
            if (4 == 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public String getCommandName() {
        return CommandSetDefaultSpawnpoint.I["".length()];
    }
    
    @Override
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        BlockPos spawnPoint;
        if (array.length == 0) {
            spawnPoint = CommandBase.getCommandSenderAsPlayer(commandSender).getPosition();
            "".length();
            if (0 >= 1) {
                throw null;
            }
        }
        else {
            if (array.length != "   ".length() || commandSender.getEntityWorld() == null) {
                throw new WrongUsageException(CommandSetDefaultSpawnpoint.I["  ".length()], new Object["".length()]);
            }
            spawnPoint = CommandBase.parseBlockPos(commandSender, array, "".length(), " ".length() != 0);
        }
        commandSender.getEntityWorld().setSpawnPoint(spawnPoint);
        MinecraftServer.getServer().getConfigurationManager().sendPacketToAllPlayers(new S05PacketSpawnPosition(spawnPoint));
        final String s = CommandSetDefaultSpawnpoint.I["   ".length()];
        final Object[] array2 = new Object["   ".length()];
        array2["".length()] = spawnPoint.getX();
        array2[" ".length()] = spawnPoint.getY();
        array2["  ".length()] = spawnPoint.getZ();
        CommandBase.notifyOperators(commandSender, this, s, array2);
    }
    
    static {
        I();
    }
    
    @Override
    public String getCommandUsage(final ICommandSender commandSender) {
        return CommandSetDefaultSpawnpoint.I[" ".length()];
    }
    
    private static void I() {
        (I = new String[0xA6 ^ 0xA2])["".length()] = I("\u001b \u0007\u001b%\u001a)\u0017\u001f:\t2\u001d", "hEslJ");
        CommandSetDefaultSpawnpoint.I[" ".length()] = I("0\u001a\u001e>\u0005=\u0011\u0000}\u00176\u0001\u0004<\u0016?\u0011\u0000#\u0005$\u001b]&\u00172\u0012\u0016", "SusSd");
        CommandSetDefaultSpawnpoint.I["  ".length()] = I(";\u0017\u0001\u0017\b6\u001c\u001fT\u001a=\f\u001b\u0015\u001b4\u001c\u001f\n\b/\u0016B\u000f\u001a9\u001f\t", "Xxlzi");
        CommandSetDefaultSpawnpoint.I["   ".length()] = I("4\u0019\u001f\u0006\u00039\u0012\u0001E\u00112\u0002\u0005\u0004\u0010;\u0012\u0001\u001b\u0003 \u0018\\\u0018\u00174\u0015\u0017\u0018\u0011", "Wvrkb");
    }
    
    @Override
    public List<String> addTabCompletionOptions(final ICommandSender commandSender, final String[] array, final BlockPos blockPos) {
        List<String> func_175771_a;
        if (array.length > 0 && array.length <= "   ".length()) {
            func_175771_a = CommandBase.func_175771_a(array, "".length(), blockPos);
            "".length();
            if (0 < -1) {
                throw null;
            }
        }
        else {
            func_175771_a = null;
        }
        return func_175771_a;
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return "  ".length();
    }
}
