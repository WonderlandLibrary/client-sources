package net.minecraft.command;

import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.server.*;
import net.minecraft.entity.player.*;
import java.util.*;

public class CommandDefaultGameMode extends CommandGameMode
{
    private static final String[] I;
    
    static {
        I();
    }
    
    @Override
    public String getCommandUsage(final ICommandSender commandSender) {
        return CommandDefaultGameMode.I[" ".length()];
    }
    
    private static void I() {
        (I = new String[0x8A ^ 0x8F])["".length()] = I("2\"\n.-:3\u000b.53*\u0003+=", "VGlOX");
        CommandDefaultGameMode.I[" ".length()] = I("*\u0000#\u0015\u0019'\u000b=V\u001c,\t/\r\u0014=\b/\u0015\u001d$\u0000*\u001dV<\u001c/\u001f\u001d", "IoNxx");
        CommandDefaultGameMode.I["  ".length()] = I("\u00005\u001d$(\r>\u0003g-\u0006<\u0011<%\u0017=\u0011$,\u000e5\u0014,g\u0016)\u0011.,", "cZpII");
        CommandDefaultGameMode.I["   ".length()] = I("*>\t\u0000/'5\u0017C*,7\u0005\u0018\"=6\u0005\u0000+$>\u0000\b`:$\u0007\u000e+:\"", "IQdmN");
        CommandDefaultGameMode.I[0x52 ^ 0x56] = I(".\r?);&\b7b", "IlRLv");
    }
    
    @Override
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        if (array.length <= 0) {
            throw new WrongUsageException(CommandDefaultGameMode.I["  ".length()], new Object["".length()]);
        }
        final WorldSettings.GameType gameModeFromCommand = this.getGameModeFromCommand(commandSender, array["".length()]);
        this.setGameType(gameModeFromCommand);
        final String s = CommandDefaultGameMode.I["   ".length()];
        final Object[] array2 = new Object[" ".length()];
        array2["".length()] = new ChatComponentTranslation(CommandDefaultGameMode.I[0x84 ^ 0x80] + gameModeFromCommand.getName(), new Object["".length()]);
        CommandBase.notifyOperators(commandSender, this, s, array2);
    }
    
    protected void setGameType(final WorldSettings.GameType gameType) {
        final MinecraftServer server = MinecraftServer.getServer();
        server.setGameType(gameType);
        if (server.getForceGamemode()) {
            final Iterator<EntityPlayerMP> iterator = MinecraftServer.getServer().getConfigurationManager().func_181057_v().iterator();
            "".length();
            if (2 == 4) {
                throw null;
            }
            while (iterator.hasNext()) {
                final EntityPlayerMP entityPlayerMP = iterator.next();
                entityPlayerMP.setGameType(gameType);
                entityPlayerMP.fallDistance = 0.0f;
            }
        }
    }
    
    @Override
    public String getCommandName() {
        return CommandDefaultGameMode.I["".length()];
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
            if (3 < 2) {
                throw null;
            }
        }
        return sb.toString();
    }
}
