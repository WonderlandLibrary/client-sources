package net.minecraft.command;

import net.minecraft.world.*;
import net.minecraft.server.*;
import net.minecraft.entity.player.*;
import net.minecraft.network.play.server.*;
import net.minecraft.entity.*;
import net.minecraft.network.*;
import net.minecraft.util.*;
import java.util.*;

public class CommandGameRule extends CommandBase
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
            if (3 != 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    private static void I() {
        (I = new String[0x24 ^ 0x29])["".length()] = I("\n\u0012\u0007'5\u0018\u001f\u000f", "msjBG");
        CommandGameRule.I[" ".length()] = I("1\u001c9\u0006\u0015<\u0017'E\u00133\u001e1\u0019\u0001>\u0016z\u001e\u00073\u00141", "RsTkt");
        CommandGameRule.I["  ".length()] = I("", "dGyuT");
        CommandGameRule.I["   ".length()] = I("", "Jkyox");
        CommandGameRule.I[0x6E ^ 0x6A] = I("%!/\u000e6(*1M0'#'\u0011\"*+l\r84;.\u0006", "FNBcW");
        CommandGameRule.I[0x3C ^ 0x39] = I("Tnw", "tSWOO");
        CommandGameRule.I[0x89 ^ 0x8F] = I("\u00010\u0002\"", "uBwGR");
        CommandGameRule.I[0x55 ^ 0x52] = I("\u0001\u0019\u001f\u0011#", "gxsbF");
        CommandGameRule.I[0x73 ^ 0x7B] = I("\u0002\u001b7\u0005\b\u000f\u0010)F\u000e\u0004\u001a?\u001a\u0000\u0002Z8\u0007\u0006\r\u0011;\u0006G\b\u001a,\t\u0005\b\u0010", "atZhi");
        CommandGameRule.I[0x7 ^ 0xE] = I("\u00147$\n\u0017\u0019<:I\u0011\u00165,\u0015\u0003\u001b=g\u0014\u0003\u0014;,\u0014\u0005", "wXIgv");
        CommandGameRule.I[0x55 ^ 0x5F] = I("\u0016\u001d60\t\u0001\u001c\u0016 \b\u0011\u001f\u001b+\f\u000b", "dxREj");
        CommandGameRule.I[0x6E ^ 0x65] = I("\u0000(\u0005#", "tZpFr");
        CommandGameRule.I[0x1F ^ 0x13] = I("\u0012+\u00161,", "tJzBI");
    }
    
    @Override
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        final GameRules gameRules = this.getGameRules();
        String s;
        if (array.length > 0) {
            s = array["".length()];
            "".length();
            if (false) {
                throw null;
            }
        }
        else {
            s = CommandGameRule.I["  ".length()];
        }
        final String s2 = s;
        String buildString;
        if (array.length > " ".length()) {
            buildString = CommandBase.buildString(array, " ".length());
            "".length();
            if (4 < 1) {
                throw null;
            }
        }
        else {
            buildString = CommandGameRule.I["   ".length()];
        }
        final String s3 = buildString;
        switch (array.length) {
            case 0: {
                commandSender.addChatMessage(new ChatComponentText(CommandBase.joinNiceString(gameRules.getRules())));
                "".length();
                if (4 <= 1) {
                    throw null;
                }
                break;
            }
            case 1: {
                if (!gameRules.hasRule(s2)) {
                    final String s4 = CommandGameRule.I[0x50 ^ 0x54];
                    final Object[] array2 = new Object[" ".length()];
                    array2["".length()] = s2;
                    throw new CommandException(s4, array2);
                }
                commandSender.addChatMessage(new ChatComponentText(s2).appendText(CommandGameRule.I[0xAB ^ 0xAE]).appendText(gameRules.getString(s2)));
                commandSender.setCommandStat(CommandResultStats.Type.QUERY_RESULT, gameRules.getInt(s2));
                "".length();
                if (1 <= -1) {
                    throw null;
                }
                break;
            }
            default: {
                if (gameRules.areSameType(s2, GameRules.ValueType.BOOLEAN_VALUE) && !CommandGameRule.I[0x9B ^ 0x9D].equals(s3) && !CommandGameRule.I[0x20 ^ 0x27].equals(s3)) {
                    final String s5 = CommandGameRule.I[0x3E ^ 0x36];
                    final Object[] array3 = new Object[" ".length()];
                    array3["".length()] = s3;
                    throw new CommandException(s5, array3);
                }
                gameRules.setOrCreateGameRule(s2, s3);
                func_175773_a(gameRules, s2);
                CommandBase.notifyOperators(commandSender, this, CommandGameRule.I[0x3F ^ 0x36], new Object["".length()]);
                break;
            }
        }
    }
    
    private GameRules getGameRules() {
        return MinecraftServer.getServer().worldServerForDimension("".length()).getGameRules();
    }
    
    @Override
    public String getCommandUsage(final ICommandSender commandSender) {
        return CommandGameRule.I[" ".length()];
    }
    
    static {
        I();
    }
    
    public static void func_175773_a(final GameRules gameRules, final String s) {
        if (CommandGameRule.I[0x75 ^ 0x7F].equals(s)) {
            int n;
            if (gameRules.getBoolean(s)) {
                n = (0x51 ^ 0x47);
                "".length();
                if (4 <= 1) {
                    throw null;
                }
            }
            else {
                n = (0xA7 ^ 0xB0);
            }
            final byte b = (byte)n;
            final Iterator<EntityPlayerMP> iterator = MinecraftServer.getServer().getConfigurationManager().func_181057_v().iterator();
            "".length();
            if (0 >= 3) {
                throw null;
            }
            while (iterator.hasNext()) {
                final EntityPlayerMP entityPlayerMP = iterator.next();
                entityPlayerMP.playerNetServerHandler.sendPacket(new S19PacketEntityStatus(entityPlayerMP, b));
            }
        }
    }
    
    @Override
    public String getCommandName() {
        return CommandGameRule.I["".length()];
    }
    
    @Override
    public List<String> addTabCompletionOptions(final ICommandSender commandSender, final String[] array, final BlockPos blockPos) {
        if (array.length == " ".length()) {
            return CommandBase.getListOfStringsMatchingLastWord(array, this.getGameRules().getRules());
        }
        if (array.length == "  ".length() && this.getGameRules().areSameType(array["".length()], GameRules.ValueType.BOOLEAN_VALUE)) {
            final String[] array2 = new String["  ".length()];
            array2["".length()] = CommandGameRule.I[0xCF ^ 0xC4];
            array2[" ".length()] = CommandGameRule.I[0x90 ^ 0x9C];
            return CommandBase.getListOfStringsMatchingLastWord(array, array2);
        }
        return null;
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return "  ".length();
    }
}
