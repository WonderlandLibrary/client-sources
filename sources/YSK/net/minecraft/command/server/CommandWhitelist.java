package net.minecraft.command.server;

import net.minecraft.server.*;
import net.minecraft.command.*;
import com.mojang.authlib.*;
import net.minecraft.util.*;
import java.util.*;

public class CommandWhitelist extends CommandBase
{
    private static final String[] I;
    
    @Override
    public int getRequiredPermissionLevel() {
        return "   ".length();
    }
    
    static {
        I();
    }
    
    private static void I() {
        (I = new String[0x95 ^ 0x8E])["".length()] = I("5!\u001e\u0002\u0006. \u0004\u0002", "BIwvc");
        CommandWhitelist.I[" ".length()] = I("\n!\u0002%%\u0007*\u001cf3\u0001'\u001b-(\u0000=\u001bf1\u001a/\b-", "iNoHD");
        CommandWhitelist.I["  ".length()] = I("'\u0006\u0018*,*\r\u0006i:,\u0000\u0001\"!-\u001a\u0001i87\b\u0012\"", "DiuGM");
        CommandWhitelist.I["   ".length()] = I("\u000e\u001d", "asjjo");
        CommandWhitelist.I[0x73 ^ 0x77] = I("\u0010 9+\u000e\u001d+'h\u0018\u001b& #\u0003\u001a< h\n\u001d.6*\n\u0017", "sOTFo");
        CommandWhitelist.I[0x1D ^ 0x18] = I(" \u0003\u0014", "OeryW");
        CommandWhitelist.I[0xAB ^ 0xAD] = I("\u001a+\n%9\u0017 \u0014f/\u0011-\u0013-4\u00107\u0013f<\u00107\u0006*4\u001c ", "yDgHX");
        CommandWhitelist.I[0x3B ^ 0x3C] = I("-\u0002\u001b\"", "AkhVi");
        CommandWhitelist.I[0x2F ^ 0x27] = I("-:\u001e7; 1\u0000t-&<\u0007?6'&\u0007t6'&\u0007", "NUsZZ");
        CommandWhitelist.I[0x37 ^ 0x3E] = I("1\u0011\u0016", "PurEg");
        CommandWhitelist.I[0xB4 ^ 0xBE] = I("\u0016\u000b\"4\u0013\u001b\u0000<w\u0005\u001d\r;<\u001e\u001c\u0017;w\u0013\u0011\u0000a,\u0001\u0014\u0003*", "udOYr");
        CommandWhitelist.I[0xD ^ 0x6] = I("\u001a\u0017\u0014!5\u0017\u001c\nb#\u0011\u0011\r)8\u0010\u000b\rb5\u001d\u001cW*5\u0010\u0014\u001c(", "yxyLT");
        CommandWhitelist.I[0x68 ^ 0x64] = I("\u001a;5!\u0014\u00170+b\u0002\u0011=,)\u0019\u0010',b\u0014\u001d0v?\u0000\u001a7=?\u0006", "yTXLu");
        CommandWhitelist.I[0xCE ^ 0xC3] = I("\u0019=5*2\u000e", "kXXED");
        CommandWhitelist.I[0x81 ^ 0x8F] = I("+\r\u0018?\r&\u0006\u0006|\u001b \u000b\u00017\u0000!\u0011\u0001|\u001e-\u000f\u001a$\tf\u0017\u00063\u000b-", "HbuRl");
        CommandWhitelist.I[0x4F ^ 0x40] = I(";8/.0631m&0>6&=1$6m#=:-54v1#*==3", "XWBCQ");
        CommandWhitelist.I[0x60 ^ 0x70] = I("\b\u0015\u001d\u0007\b\u0005\u001e\u0003D\u001e\u0003\u0013\u0004\u000f\u0005\u0002\t\u0004D\u001b\u000e\u0017\u001f\u001c\fE\t\u0005\t\n\u000e\t\u0003", "kzpji");
        CommandWhitelist.I[0xE ^ 0x1F] = I("\u0018\u000397(\u000e", "jfUXI");
        CommandWhitelist.I[0x6C ^ 0x7E] = I("(\u001f#\u000b\u0003%\u0014=H\u0015#\u0019:\u0003\u000e\"\u0003:H\u0010.\u001c!\u0007\u0006.\u0014", "KpNfb");
        CommandWhitelist.I[0x8E ^ 0x9D] = I("((", "GFIKl");
        CommandWhitelist.I[0x3D ^ 0x29] = I("\u0016,-", "yJKWS");
        CommandWhitelist.I[0x9F ^ 0x8A] = I("\t:):", "eSZNY");
        CommandWhitelist.I[0x34 ^ 0x22] = I("(&\u000f", "IBkSQ");
        CommandWhitelist.I[0x1 ^ 0x16] = I("\u0016/\u0019\u0016\u0007\u0001", "dJtyq");
        CommandWhitelist.I[0x5F ^ 0x47] = I("\u0007\u0011&\b\n\u0011", "utJgk");
        CommandWhitelist.I[0x52 ^ 0x4B] = I("\u001e0\u0014\t\u0001\t", "lUyfw");
        CommandWhitelist.I[0xDB ^ 0xC1] = I("%\u00174", "DsPKl");
    }
    
    @Override
    public String getCommandUsage(final ICommandSender commandSender) {
        return CommandWhitelist.I[" ".length()];
    }
    
    @Override
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        if (array.length < " ".length()) {
            throw new WrongUsageException(CommandWhitelist.I["  ".length()], new Object["".length()]);
        }
        final MinecraftServer server = MinecraftServer.getServer();
        if (array["".length()].equals(CommandWhitelist.I["   ".length()])) {
            server.getConfigurationManager().setWhiteListEnabled(" ".length() != 0);
            CommandBase.notifyOperators(commandSender, this, CommandWhitelist.I[0x3A ^ 0x3E], new Object["".length()]);
            "".length();
            if (-1 != -1) {
                throw null;
            }
        }
        else if (array["".length()].equals(CommandWhitelist.I[0x1A ^ 0x1F])) {
            server.getConfigurationManager().setWhiteListEnabled("".length() != 0);
            CommandBase.notifyOperators(commandSender, this, CommandWhitelist.I[0x19 ^ 0x1F], new Object["".length()]);
            "".length();
            if (4 <= -1) {
                throw null;
            }
        }
        else if (array["".length()].equals(CommandWhitelist.I[0x7F ^ 0x78])) {
            final String s = CommandWhitelist.I[0x2D ^ 0x25];
            final Object[] array2 = new Object["  ".length()];
            array2["".length()] = server.getConfigurationManager().getWhitelistedPlayerNames().length;
            array2[" ".length()] = server.getConfigurationManager().getAvailablePlayerDat().length;
            commandSender.addChatMessage(new ChatComponentTranslation(s, array2));
            commandSender.addChatMessage(new ChatComponentText(CommandBase.joinNiceString(server.getConfigurationManager().getWhitelistedPlayerNames())));
            "".length();
            if (false) {
                throw null;
            }
        }
        else if (array["".length()].equals(CommandWhitelist.I[0x50 ^ 0x59])) {
            if (array.length < "  ".length()) {
                throw new WrongUsageException(CommandWhitelist.I[0xAE ^ 0xA4], new Object["".length()]);
            }
            final GameProfile gameProfileForUsername = server.getPlayerProfileCache().getGameProfileForUsername(array[" ".length()]);
            if (gameProfileForUsername == null) {
                final String s2 = CommandWhitelist.I[0xA2 ^ 0xA9];
                final Object[] array3 = new Object[" ".length()];
                array3["".length()] = array[" ".length()];
                throw new CommandException(s2, array3);
            }
            server.getConfigurationManager().addWhitelistedPlayer(gameProfileForUsername);
            final String s3 = CommandWhitelist.I[0x80 ^ 0x8C];
            final Object[] array4 = new Object[" ".length()];
            array4["".length()] = array[" ".length()];
            CommandBase.notifyOperators(commandSender, this, s3, array4);
            "".length();
            if (4 < 0) {
                throw null;
            }
        }
        else if (array["".length()].equals(CommandWhitelist.I[0x23 ^ 0x2E])) {
            if (array.length < "  ".length()) {
                throw new WrongUsageException(CommandWhitelist.I[0x2D ^ 0x23], new Object["".length()]);
            }
            final GameProfile func_152706_a = server.getConfigurationManager().getWhitelistedPlayers().func_152706_a(array[" ".length()]);
            if (func_152706_a == null) {
                final String s4 = CommandWhitelist.I[0x48 ^ 0x47];
                final Object[] array5 = new Object[" ".length()];
                array5["".length()] = array[" ".length()];
                throw new CommandException(s4, array5);
            }
            server.getConfigurationManager().removePlayerFromWhitelist(func_152706_a);
            final String s5 = CommandWhitelist.I[0x2A ^ 0x3A];
            final Object[] array6 = new Object[" ".length()];
            array6["".length()] = array[" ".length()];
            CommandBase.notifyOperators(commandSender, this, s5, array6);
            "".length();
            if (-1 >= 3) {
                throw null;
            }
        }
        else if (array["".length()].equals(CommandWhitelist.I[0xA ^ 0x1B])) {
            server.getConfigurationManager().loadWhiteList();
            CommandBase.notifyOperators(commandSender, this, CommandWhitelist.I[0x3A ^ 0x28], new Object["".length()]);
        }
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
            if (4 == 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public List<String> addTabCompletionOptions(final ICommandSender commandSender, final String[] array, final BlockPos blockPos) {
        if (array.length == " ".length()) {
            final String[] array2 = new String[0x1C ^ 0x1A];
            array2["".length()] = CommandWhitelist.I[0x64 ^ 0x77];
            array2[" ".length()] = CommandWhitelist.I[0x18 ^ 0xC];
            array2["  ".length()] = CommandWhitelist.I[0xB9 ^ 0xAC];
            array2["   ".length()] = CommandWhitelist.I[0x9 ^ 0x1F];
            array2[0xBE ^ 0xBA] = CommandWhitelist.I[0x5 ^ 0x12];
            array2[0x2D ^ 0x28] = CommandWhitelist.I[0xBF ^ 0xA7];
            return CommandBase.getListOfStringsMatchingLastWord(array, array2);
        }
        if (array.length == "  ".length()) {
            if (array["".length()].equals(CommandWhitelist.I[0x75 ^ 0x6C])) {
                return CommandBase.getListOfStringsMatchingLastWord(array, MinecraftServer.getServer().getConfigurationManager().getWhitelistedPlayerNames());
            }
            if (array["".length()].equals(CommandWhitelist.I[0x95 ^ 0x8F])) {
                return CommandBase.getListOfStringsMatchingLastWord(array, MinecraftServer.getServer().getPlayerProfileCache().getUsernames());
            }
        }
        return null;
    }
    
    @Override
    public String getCommandName() {
        return CommandWhitelist.I["".length()];
    }
}
