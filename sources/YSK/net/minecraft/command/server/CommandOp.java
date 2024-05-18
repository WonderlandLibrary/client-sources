package net.minecraft.command.server;

import net.minecraft.server.*;
import net.minecraft.command.*;
import com.mojang.authlib.*;
import net.minecraft.util.*;
import com.google.common.collect.*;
import java.util.*;

public class CommandOp extends CommandBase
{
    private static final String[] I;
    
    @Override
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        if (array.length != " ".length() || array["".length()].length() <= 0) {
            throw new WrongUsageException(CommandOp.I[0x9E ^ 0x9A], new Object["".length()]);
        }
        final MinecraftServer server = MinecraftServer.getServer();
        final GameProfile gameProfileForUsername = server.getPlayerProfileCache().getGameProfileForUsername(array["".length()]);
        if (gameProfileForUsername == null) {
            final String s = CommandOp.I["  ".length()];
            final Object[] array2 = new Object[" ".length()];
            array2["".length()] = array["".length()];
            throw new CommandException(s, array2);
        }
        server.getConfigurationManager().addOp(gameProfileForUsername);
        final String s2 = CommandOp.I["   ".length()];
        final Object[] array3 = new Object[" ".length()];
        array3["".length()] = array["".length()];
        CommandBase.notifyOperators(commandSender, this, s2, array3);
        "".length();
        if (-1 != -1) {
            throw null;
        }
    }
    
    @Override
    public String getCommandUsage(final ICommandSender commandSender) {
        return CommandOp.I[" ".length()];
    }
    
    @Override
    public List<String> addTabCompletionOptions(final ICommandSender commandSender, final String[] array, final BlockPos blockPos) {
        if (array.length != " ".length()) {
            return null;
        }
        final String s = array[array.length - " ".length()];
        final ArrayList arrayList = Lists.newArrayList();
        final GameProfile[] gameProfiles;
        final int length = (gameProfiles = MinecraftServer.getServer().getGameProfiles()).length;
        int i = "".length();
        "".length();
        if (-1 != -1) {
            throw null;
        }
        while (i < length) {
            final GameProfile gameProfile = gameProfiles[i];
            if (!MinecraftServer.getServer().getConfigurationManager().canSendCommands(gameProfile) && CommandBase.doesStringStartWith(s, gameProfile.getName())) {
                arrayList.add(gameProfile.getName());
            }
            ++i;
        }
        return (List<String>)arrayList;
    }
    
    private static void I() {
        (I = new String[0xAA ^ 0xAF])["".length()] = I("\u00187", "wGnKO");
        CommandOp.I[" ".length()] = I("5\u0006(#;8\r6`5&G0=;1\f", "ViENZ");
        CommandOp.I["  ".length()] = I(" \b=5\u000f-\u0003#v\u00013I69\u0007/\u00024", "CgPXn");
        CommandOp.I["   ".length()] = I("\u00015!!9\f>?b7\u0012t?9;\u0001???", "bZLLX");
        CommandOp.I[0x33 ^ 0x37] = I(".<#4 #7=w.=};* *6", "MSNYA");
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return "   ".length();
    }
    
    static {
        I();
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
            if (4 < 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public String getCommandName() {
        return CommandOp.I["".length()];
    }
}
