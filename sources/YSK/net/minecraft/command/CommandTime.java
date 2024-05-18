package net.minecraft.command;

import net.minecraft.server.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.world.*;

public class CommandTime extends CommandBase
{
    private static final String[] I;
    
    private static void I() {
        (I = new String[0x6F ^ 0x78])["".length()] = I("\u0005\u001a8\u0004", "qsUaf");
        CommandTime.I[" ".length()] = I("/?:\t\b\"4$J\u001d%=2J\u001c?10\u0001", "LPWdi");
        CommandTime.I["  ".length()] = I("\u0005)8", "vLLKD");
        CommandTime.I["   ".length()] = I("\u000b#\u0016", "oBojP");
        CommandTime.I[0x57 ^ 0x53] = I("#0!2\u0003", "MYFZw");
        CommandTime.I[0x94 ^ 0x91] = I(",.?;\u0018!%!x\r&,7x\n*5", "OARVy");
        CommandTime.I[0x83 ^ 0x85] = I("\n\u0016.", "krJpN");
        CommandTime.I[0x13 ^ 0x14] = I("'-*'\t*&4d\u001c-/\"d\t &\".", "DBGJh");
        CommandTime.I[0x51 ^ 0x59] = I(")\u0013\u0004=7", "XfaON");
        CommandTime.I[0x2C ^ 0x25] = I(">8?%'7<", "ZYFQN");
        CommandTime.I[0x13 ^ 0x19] = I("1\t\u001e4\b<\u0002\u0000w\u001d;\u000b\u0016w\u0018'\u0003\u0001 ", "RfsYi");
        CommandTime.I[0xA ^ 0x1] = I("\u0014$8\"\u0011\u001a(0", "sEUGe");
        CommandTime.I[0x97 ^ 0x9B] = I("4\u001a)\u0001\u00059\u00117B\u0010>\u0018!B\u0015\"\u00106\u0015", "WuDld");
        CommandTime.I[0x72 ^ 0x7F] = I("!*\u0018\u000f5,!\u0006L +(\u0010L!1$\u0012\u0007", "BEubT");
        CommandTime.I[0x12 ^ 0x1C] = I("\u0001\u0010\u0019", "rumWH");
        CommandTime.I[0xA8 ^ 0xA7] = I("\f\u0006'", "mbCSl");
        CommandTime.I[0xAD ^ 0xBD] = I("%\u0017\u00008+", "TbeJR");
        CommandTime.I[0x80 ^ 0x91] = I("\u0010\u0004\u000e", "cazcG");
        CommandTime.I[0x2F ^ 0x3D] = I(")5\f", "MTubN");
        CommandTime.I[0x99 ^ 0x8A] = I("/\u001f\n>;", "AvmVO");
        CommandTime.I[0x65 ^ 0x71] = I(";%\t\u0006 ", "JPltY");
        CommandTime.I[0x5E ^ 0x4B] = I("\u000b\u000f\r\u0015!\u0002\u000b", "ontaH");
        CommandTime.I[0x8C ^ 0x9A] = I("15\u0002+\u0012?9\n", "VToNf");
    }
    
    @Override
    public String getCommandUsage(final ICommandSender commandSender) {
        return CommandTime.I[" ".length()];
    }
    
    protected void setTime(final ICommandSender commandSender, final int n) {
        int i = "".length();
        "".length();
        if (3 <= 2) {
            throw null;
        }
        while (i < MinecraftServer.getServer().worldServers.length) {
            MinecraftServer.getServer().worldServers[i].setWorldTime(n);
            ++i;
        }
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return "  ".length();
    }
    
    static {
        I();
    }
    
    @Override
    public List<String> addTabCompletionOptions(final ICommandSender commandSender, final String[] array, final BlockPos blockPos) {
        List<String> list;
        if (array.length == " ".length()) {
            final String[] array2 = new String["   ".length()];
            array2["".length()] = CommandTime.I[0x2E ^ 0x20];
            array2[" ".length()] = CommandTime.I[0x83 ^ 0x8C];
            array2["  ".length()] = CommandTime.I[0x9B ^ 0x8B];
            list = CommandBase.getListOfStringsMatchingLastWord(array, array2);
            "".length();
            if (1 >= 3) {
                throw null;
            }
        }
        else if (array.length == "  ".length() && array["".length()].equals(CommandTime.I[0x93 ^ 0x82])) {
            final String[] array3 = new String["  ".length()];
            array3["".length()] = CommandTime.I[0xD0 ^ 0xC2];
            array3[" ".length()] = CommandTime.I[0x75 ^ 0x66];
            list = CommandBase.getListOfStringsMatchingLastWord(array, array3);
            "".length();
            if (0 >= 1) {
                throw null;
            }
        }
        else if (array.length == "  ".length() && array["".length()].equals(CommandTime.I[0x77 ^ 0x63])) {
            final String[] array4 = new String["  ".length()];
            array4["".length()] = CommandTime.I[0x29 ^ 0x3C];
            array4[" ".length()] = CommandTime.I[0x29 ^ 0x3F];
            list = CommandBase.getListOfStringsMatchingLastWord(array, array4);
            "".length();
            if (3 <= 0) {
                throw null;
            }
        }
        else {
            list = null;
        }
        return list;
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
            if (3 != 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        if (array.length > " ".length()) {
            if (array["".length()].equals(CommandTime.I["  ".length()])) {
                int int1;
                if (array[" ".length()].equals(CommandTime.I["   ".length()])) {
                    int1 = 620 + 196 - 667 + 851;
                    "".length();
                    if (-1 >= 0) {
                        throw null;
                    }
                }
                else if (array[" ".length()].equals(CommandTime.I[0x7B ^ 0x7F])) {
                    int1 = 1258 + 9984 - 2702 + 4460;
                    "".length();
                    if (4 == -1) {
                        throw null;
                    }
                }
                else {
                    int1 = CommandBase.parseInt(array[" ".length()], "".length());
                }
                this.setTime(commandSender, int1);
                final String s = CommandTime.I[0x8A ^ 0x8F];
                final Object[] array2 = new Object[" ".length()];
                array2["".length()] = int1;
                CommandBase.notifyOperators(commandSender, this, s, array2);
                return;
            }
            if (array["".length()].equals(CommandTime.I[0x35 ^ 0x33])) {
                final int int2 = CommandBase.parseInt(array[" ".length()], "".length());
                this.addTime(commandSender, int2);
                final String s2 = CommandTime.I[0xB4 ^ 0xB3];
                final Object[] array3 = new Object[" ".length()];
                array3["".length()] = int2;
                CommandBase.notifyOperators(commandSender, this, s2, array3);
                return;
            }
            if (array["".length()].equals(CommandTime.I[0xAC ^ 0xA4])) {
                if (array[" ".length()].equals(CommandTime.I[0x8A ^ 0x83])) {
                    final int n = (int)(commandSender.getEntityWorld().getWorldTime() % 2147483647L);
                    commandSender.setCommandStat(CommandResultStats.Type.QUERY_RESULT, n);
                    final String s3 = CommandTime.I[0x89 ^ 0x83];
                    final Object[] array4 = new Object[" ".length()];
                    array4["".length()] = n;
                    CommandBase.notifyOperators(commandSender, this, s3, array4);
                    return;
                }
                if (array[" ".length()].equals(CommandTime.I[0x45 ^ 0x4E])) {
                    final int n2 = (int)(commandSender.getEntityWorld().getTotalWorldTime() % 2147483647L);
                    commandSender.setCommandStat(CommandResultStats.Type.QUERY_RESULT, n2);
                    final String s4 = CommandTime.I[0x2 ^ 0xE];
                    final Object[] array5 = new Object[" ".length()];
                    array5["".length()] = n2;
                    CommandBase.notifyOperators(commandSender, this, s4, array5);
                    return;
                }
            }
        }
        throw new WrongUsageException(CommandTime.I[0x97 ^ 0x9A], new Object["".length()]);
    }
    
    @Override
    public String getCommandName() {
        return CommandTime.I["".length()];
    }
    
    protected void addTime(final ICommandSender commandSender, final int n) {
        int i = "".length();
        "".length();
        if (-1 != -1) {
            throw null;
        }
        while (i < MinecraftServer.getServer().worldServers.length) {
            final WorldServer worldServer = MinecraftServer.getServer().worldServers[i];
            worldServer.setWorldTime(worldServer.getWorldTime() + n);
            ++i;
        }
    }
}
