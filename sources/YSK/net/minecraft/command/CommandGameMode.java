package net.minecraft.command;

import java.util.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.server.*;

public class CommandGameMode extends CommandBase
{
    private static final String[] I;
    
    @Override
    public List<String> addTabCompletionOptions(final ICommandSender commandSender, final String[] array, final BlockPos blockPos) {
        List<String> list;
        if (array.length == " ".length()) {
            final String[] array2 = new String[0x60 ^ 0x64];
            array2["".length()] = CommandGameMode.I[0x5E ^ 0x52];
            array2[" ".length()] = CommandGameMode.I[0x10 ^ 0x1D];
            array2["  ".length()] = CommandGameMode.I[0x58 ^ 0x56];
            array2["   ".length()] = CommandGameMode.I[0x8E ^ 0x81];
            list = CommandBase.getListOfStringsMatchingLastWord(array, array2);
            "".length();
            if (0 == 3) {
                throw null;
            }
        }
        else if (array.length == "  ".length()) {
            list = CommandBase.getListOfStringsMatchingLastWord(array, this.getListOfPlayerUsernames());
            "".length();
            if (1 == 3) {
                throw null;
            }
        }
        else {
            list = null;
        }
        return list;
    }
    
    @Override
    public String getCommandName() {
        return CommandGameMode.I["".length()];
    }
    
    static {
        I();
    }
    
    @Override
    public boolean isUsernameIndex(final String[] array, final int n) {
        if (n == " ".length()) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        if (array.length <= 0) {
            throw new WrongUsageException(CommandGameMode.I["  ".length()], new Object["".length()]);
        }
        final WorldSettings.GameType gameModeFromCommand = this.getGameModeFromCommand(commandSender, array["".length()]);
        EntityPlayerMP entityPlayerMP;
        if (array.length >= "  ".length()) {
            entityPlayerMP = CommandBase.getPlayer(commandSender, array[" ".length()]);
            "".length();
            if (4 == 2) {
                throw null;
            }
        }
        else {
            entityPlayerMP = CommandBase.getCommandSenderAsPlayer(commandSender);
        }
        final EntityPlayerMP entityPlayerMP2 = entityPlayerMP;
        entityPlayerMP2.setGameType(gameModeFromCommand);
        entityPlayerMP2.fallDistance = 0.0f;
        if (commandSender.getEntityWorld().getGameRules().getBoolean(CommandGameMode.I["   ".length()])) {
            entityPlayerMP2.addChatMessage(new ChatComponentTranslation(CommandGameMode.I[0x2B ^ 0x2F], new Object["".length()]));
        }
        final ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation(CommandGameMode.I[0xA0 ^ 0xA5] + gameModeFromCommand.getName(), new Object["".length()]);
        if (entityPlayerMP2 != commandSender) {
            final int length = " ".length();
            final String s = CommandGameMode.I[0x3A ^ 0x3C];
            final Object[] array2 = new Object["  ".length()];
            array2["".length()] = entityPlayerMP2.getName();
            array2[" ".length()] = chatComponentTranslation;
            CommandBase.notifyOperators(commandSender, this, length, s, array2);
            "".length();
            if (4 <= -1) {
                throw null;
            }
        }
        else {
            final int length2 = " ".length();
            final String s2 = CommandGameMode.I[0x68 ^ 0x6F];
            final Object[] array3 = new Object[" ".length()];
            array3["".length()] = chatComponentTranslation;
            CommandBase.notifyOperators(commandSender, this, length2, s2, array3);
        }
    }
    
    protected String[] getListOfPlayerUsernames() {
        return MinecraftServer.getServer().getAllUsernames();
    }
    
    protected WorldSettings.GameType getGameModeFromCommand(final ICommandSender commandSender, final String s) throws NumberInvalidException, CommandException {
        WorldSettings.GameType gameType;
        if (!s.equalsIgnoreCase(WorldSettings.GameType.SURVIVAL.getName()) && !s.equalsIgnoreCase(CommandGameMode.I[0x75 ^ 0x7D])) {
            if (!s.equalsIgnoreCase(WorldSettings.GameType.CREATIVE.getName()) && !s.equalsIgnoreCase(CommandGameMode.I[0x34 ^ 0x3D])) {
                if (!s.equalsIgnoreCase(WorldSettings.GameType.ADVENTURE.getName()) && !s.equalsIgnoreCase(CommandGameMode.I[0x19 ^ 0x13])) {
                    if (!s.equalsIgnoreCase(WorldSettings.GameType.SPECTATOR.getName()) && !s.equalsIgnoreCase(CommandGameMode.I[0x57 ^ 0x5C])) {
                        gameType = WorldSettings.getGameTypeById(CommandBase.parseInt(s, "".length(), WorldSettings.GameType.values().length - "  ".length()));
                        "".length();
                        if (0 >= 4) {
                            throw null;
                        }
                    }
                    else {
                        gameType = WorldSettings.GameType.SPECTATOR;
                        "".length();
                        if (0 >= 4) {
                            throw null;
                        }
                    }
                }
                else {
                    gameType = WorldSettings.GameType.ADVENTURE;
                    "".length();
                    if (false == true) {
                        throw null;
                    }
                }
            }
            else {
                gameType = WorldSettings.GameType.CREATIVE;
                "".length();
                if (2 < 0) {
                    throw null;
                }
            }
        }
        else {
            gameType = WorldSettings.GameType.SURVIVAL;
        }
        return gameType;
    }
    
    @Override
    public String getCommandUsage(final ICommandSender commandSender) {
        return CommandGameMode.I[" ".length()];
    }
    
    private static void I() {
        (I = new String[0x7 ^ 0x17])["".length()] = I("\u0015\n\u000b\u0016,\u001d\u000f\u0003", "rkfsA");
        CommandGameMode.I[" ".length()] = I("\u0010!,\u00177\u001d*2T1\u0012#$\u00179\u0017+o\u000f%\u0012)$", "sNAzV");
        CommandGameMode.I["  ".length()] = I("!6\u001b%\u0012,=\u0005f\u0014#4\u0013%\u001c&<X=\u0000#>\u0013", "BYvHs");
        CommandGameMode.I["   ".length()] = I("\u001f?\u0005\u0007\u0016\u00037\u0006\u0002;\b\u001c\u000e\u00061\u000e;\b\b", "lZkcU");
        CommandGameMode.I[0x1A ^ 0x1E] = I("$'4,\u000f,\"<g!+'7.''", "CFYIB");
        CommandGameMode.I[0x26 ^ 0x23] = I(")\u0000\u0007\u00018!\u0005\u000fJ", "Najdu");
        CommandGameMode.I[0xF ^ 0x9] = I("&\u0015\u0000 \f+\u001e\u001ec\n$\u0017\b \u0002!\u001fC>\u0018&\u0019\b>\u001ek\u0015\u0019%\b7", "EzmMm");
        CommandGameMode.I[0x16 ^ 0x11] = I("\u0010&\u0007\u0002 \u001d-\u0019A&\u0012$\u000f\u0002.\u0017,D\u001c4\u0010*\u000f\u001c2]:\u000f\u0003'", "sIjoA");
        CommandGameMode.I[0x58 ^ 0x50] = I("\u0000", "sAEmj");
        CommandGameMode.I[0x66 ^ 0x6F] = I("\u0017", "tbcKU");
        CommandGameMode.I[0x39 ^ 0x33] = I("4", "UnoMv");
        CommandGameMode.I[0xBE ^ 0xB5] = I("\u00075", "tEvrn");
        CommandGameMode.I[0x2D ^ 0x21] = I("#:6\u001e:&.(", "PODhS");
        CommandGameMode.I[0x8A ^ 0x87] = I("-7\u000f\r3'3\u000f", "NEjlG");
        CommandGameMode.I[0x8B ^ 0x85] = I("\n\r=\"+\u001f\u001c9\"", "kiKGE");
        CommandGameMode.I[0x88 ^ 0x87] = I("\u000b\u001c\u0001+#\u0019\u0018\u000b:", "xldHW");
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
            if (false) {
                throw null;
            }
        }
        return sb.toString();
    }
}
