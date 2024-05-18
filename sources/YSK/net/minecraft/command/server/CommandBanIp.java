package net.minecraft.command.server;

import java.util.regex.*;
import net.minecraft.server.*;
import net.minecraft.entity.player.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.command.*;
import net.minecraft.server.management.*;

public class CommandBanIp extends CommandBase
{
    public static final Pattern field_147211_a;
    private static final String[] I;
    
    protected void func_147210_a(final ICommandSender commandSender, final String s, final String s2) {
        ((UserList<K, IPBanEntry>)MinecraftServer.getServer().getConfigurationManager().getBannedIPs()).addEntry(new IPBanEntry(s, null, commandSender.getName(), null, s2));
        final List<EntityPlayerMP> playersMatchingAddress = MinecraftServer.getServer().getConfigurationManager().getPlayersMatchingAddress(s);
        final String[] array = new String[playersMatchingAddress.size()];
        int length = "".length();
        final Iterator<EntityPlayerMP> iterator = playersMatchingAddress.iterator();
        "".length();
        if (0 >= 1) {
            throw null;
        }
        while (iterator.hasNext()) {
            final EntityPlayerMP entityPlayerMP = iterator.next();
            entityPlayerMP.playerNetServerHandler.kickPlayerFromServer(CommandBanIp.I[0x45 ^ 0x40]);
            array[length++] = entityPlayerMP.getName();
        }
        if (playersMatchingAddress.isEmpty()) {
            final String s3 = CommandBanIp.I[0xAD ^ 0xAB];
            final Object[] array2 = new Object[" ".length()];
            array2["".length()] = s;
            CommandBase.notifyOperators(commandSender, this, s3, array2);
            "".length();
            if (3 < 3) {
                throw null;
            }
        }
        else {
            final String s4 = CommandBanIp.I[0x2D ^ 0x2A];
            final Object[] array3 = new Object["  ".length()];
            array3["".length()] = s;
            array3[" ".length()] = CommandBase.joinNiceString(array);
            CommandBase.notifyOperators(commandSender, this, s4, array3);
        }
    }
    
    @Override
    public String getCommandName() {
        return CommandBanIp.I[" ".length()];
    }
    
    static {
        I();
        field_147211_a = Pattern.compile(CommandBanIp.I["".length()]);
    }
    
    @Override
    public boolean canCommandSenderUseCommand(final ICommandSender commandSender) {
        if (MinecraftServer.getServer().getConfigurationManager().getBannedIPs().isLanServer() && super.canCommandSenderUseCommand(commandSender)) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    public List<String> addTabCompletionOptions(final ICommandSender commandSender, final String[] array, final BlockPos blockPos) {
        List<String> listOfStringsMatchingLastWord;
        if (array.length == " ".length()) {
            listOfStringsMatchingLastWord = CommandBase.getListOfStringsMatchingLastWord(array, MinecraftServer.getServer().getAllUsernames());
            "".length();
            if (-1 < -1) {
                throw null;
            }
        }
        else {
            listOfStringsMatchingLastWord = null;
        }
        return listOfStringsMatchingLastWord;
    }
    
    @Override
    public String getCommandUsage(final ICommandSender commandSender) {
        return CommandBanIp.I["  ".length()];
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return "   ".length();
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
            if (0 >= 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        if (array.length >= " ".length() && array["".length()].length() > " ".length()) {
            IChatComponent chatComponentFromNthArg;
            if (array.length >= "  ".length()) {
                chatComponentFromNthArg = CommandBase.getChatComponentFromNthArg(commandSender, array, " ".length());
                "".length();
                if (-1 >= 3) {
                    throw null;
                }
            }
            else {
                chatComponentFromNthArg = null;
            }
            final IChatComponent chatComponent = chatComponentFromNthArg;
            if (CommandBanIp.field_147211_a.matcher(array["".length()]).matches()) {
                final String s = array["".length()];
                String unformattedText;
                if (chatComponent == null) {
                    unformattedText = null;
                    "".length();
                    if (4 < -1) {
                        throw null;
                    }
                }
                else {
                    unformattedText = chatComponent.getUnformattedText();
                }
                this.func_147210_a(commandSender, s, unformattedText);
                "".length();
                if (4 < 3) {
                    throw null;
                }
            }
            else {
                final EntityPlayerMP playerByUsername = MinecraftServer.getServer().getConfigurationManager().getPlayerByUsername(array["".length()]);
                if (playerByUsername == null) {
                    throw new PlayerNotFoundException(CommandBanIp.I["   ".length()], new Object["".length()]);
                }
                final String playerIP = playerByUsername.getPlayerIP();
                String unformattedText2;
                if (chatComponent == null) {
                    unformattedText2 = null;
                    "".length();
                    if (0 == 2) {
                        throw null;
                    }
                }
                else {
                    unformattedText2 = chatComponent.getUnformattedText();
                }
                this.func_147210_a(commandSender, playerIP, unformattedText2);
                "".length();
                if (4 == 1) {
                    throw null;
                }
            }
            return;
        }
        throw new WrongUsageException(CommandBanIp.I[0xC7 ^ 0xC3], new Object["".length()]);
    }
    
    private static void I() {
        (I = new String[0x9B ^ 0x93])["".length()] = I("\u0017R\u0011qV\u0014E\u0016%;-E6s<yW~\u001c;-\u0006xt<yW\u007f\u001cN\u0015Tb\u001aWx'u\u001d\u0003\u0015\u001eu=U\u0012Jgu:\u0015\u001e6sR\u0012Jgt:`&di<yK\u0017~;-&.~\u001b{!zlS\u0014&.=U|!zlR\u0014S\u0016oO\u0012J{\u001cX\u0015\u001e\u0016%X5H\u0011qJ}'\u0016%\u001b{O\u0011qJ|'ce", "IzJAg");
        CommandBanIp.I[" ".length()] = I("/ ;U1=", "MAUxX");
        CommandBanIp.I["  ".length()] = I("\u000b9\u000e=/\u00062\u0010~,\t8\n `\u001d%\u00027+", "hVcPN");
        CommandBanIp.I["   ".length()] = I("%\u001c#\u0015\u0002(\u0017=V\u0001'\u001d'\bM/\u001d8\u0019\u000f/\u0017", "FsNxc");
        CommandBanIp.I[0x64 ^ 0x60] = I("\u000b\u0016;\u000e\t\u0006\u001d%M\n\t\u0017?\u0013F\u001d\n7\u0004\r", "hyVch");
        CommandBanIp.I[0x68 ^ 0x6D] = I("\f$\u0011Q\u001d4=\u0001Q\u00170.\nQ<\u0005k\u0006\u0010\u001b;.\u0000_", "UKdqu");
        CommandBanIp.I[0x7F ^ 0x79] = I("\u0014\u00057\u00189\u0019\u000e)[:\u0016\u00043\u0005v\u0004\u001f9\u0016=\u0004\u0019", "wjZuX");
        CommandBanIp.I[0xAD ^ 0xAA] = I("\u000f.\u0003\u001b\f\u0002%\u001dX\u000f\r/\u0007\u0006C\u001f4\r\u0015\b\u001f2@\u0006\u0001\r8\u000b\u0004\u001e", "lAnvm");
    }
}
