package net.minecraft.command;

import org.apache.logging.log4j.*;
import net.minecraft.network.play.server.*;
import net.minecraft.network.*;
import org.apache.commons.lang3.exception.*;
import com.google.gson.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.server.*;

public class CommandTitle extends CommandBase
{
    private static final Logger LOGGER;
    private static final String[] I;
    
    static {
        I();
        LOGGER = LogManager.getLogger();
    }
    
    @Override
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        if (array.length < "  ".length()) {
            throw new WrongUsageException(CommandTitle.I["  ".length()], new Object["".length()]);
        }
        if (array.length < "   ".length()) {
            if (CommandTitle.I["   ".length()].equals(array[" ".length()]) || CommandTitle.I[0x5D ^ 0x59].equals(array[" ".length()])) {
                throw new WrongUsageException(CommandTitle.I[0x64 ^ 0x61], new Object["".length()]);
            }
            if (CommandTitle.I[0x77 ^ 0x71].equals(array[" ".length()])) {
                throw new WrongUsageException(CommandTitle.I[0x8 ^ 0xF], new Object["".length()]);
            }
        }
        final EntityPlayerMP player = CommandBase.getPlayer(commandSender, array["".length()]);
        final S45PacketTitle.Type byName = S45PacketTitle.Type.byName(array[" ".length()]);
        if (byName != S45PacketTitle.Type.CLEAR && byName != S45PacketTitle.Type.RESET) {
            if (byName == S45PacketTitle.Type.TIMES) {
                if (array.length != (0x1E ^ 0x1B)) {
                    throw new WrongUsageException(CommandTitle.I[0xBC ^ 0xB4], new Object["".length()]);
                }
                player.playerNetServerHandler.sendPacket(new S45PacketTitle(CommandBase.parseInt(array["  ".length()]), CommandBase.parseInt(array["   ".length()]), CommandBase.parseInt(array[0x7D ^ 0x79])));
                CommandBase.notifyOperators(commandSender, this, CommandTitle.I[0x25 ^ 0x2C], new Object["".length()]);
                "".length();
                if (-1 >= 0) {
                    throw null;
                }
            }
            else {
                if (array.length < "   ".length()) {
                    throw new WrongUsageException(CommandTitle.I[0x93 ^ 0x99], new Object["".length()]);
                }
                final String buildString = CommandBase.buildString(array, "  ".length());
                IChatComponent jsonToComponent;
                try {
                    jsonToComponent = IChatComponent.Serializer.jsonToComponent(buildString);
                    "".length();
                    if (3 <= 1) {
                        throw null;
                    }
                }
                catch (JsonParseException ex) {
                    final Throwable rootCause = ExceptionUtils.getRootCause((Throwable)ex);
                    final String s = CommandTitle.I[0xB3 ^ 0xB8];
                    final Object[] array2 = new Object[" ".length()];
                    final int length = "".length();
                    String message;
                    if (rootCause == null) {
                        message = CommandTitle.I[0x9C ^ 0x90];
                        "".length();
                        if (3 <= -1) {
                            throw null;
                        }
                    }
                    else {
                        message = rootCause.getMessage();
                    }
                    array2[length] = message;
                    throw new SyntaxErrorException(s, array2);
                }
                player.playerNetServerHandler.sendPacket(new S45PacketTitle(byName, ChatComponentProcessor.processComponent(commandSender, jsonToComponent, player)));
                CommandBase.notifyOperators(commandSender, this, CommandTitle.I[0x62 ^ 0x6F], new Object["".length()]);
                "".length();
                if (false) {
                    throw null;
                }
            }
        }
        else {
            if (array.length != "  ".length()) {
                throw new WrongUsageException(CommandTitle.I[0x5B ^ 0x55], new Object["".length()]);
            }
            player.playerNetServerHandler.sendPacket(new S45PacketTitle(byName, null));
            CommandBase.notifyOperators(commandSender, this, CommandTitle.I[0xAB ^ 0xA4], new Object["".length()]);
        }
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return "  ".length();
    }
    
    @Override
    public String getCommandUsage(final ICommandSender commandSender) {
        return CommandTitle.I[" ".length()];
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
            if (-1 != -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public List<String> addTabCompletionOptions(final ICommandSender commandSender, final String[] array, final BlockPos blockPos) {
        List<String> list;
        if (array.length == " ".length()) {
            list = CommandBase.getListOfStringsMatchingLastWord(array, MinecraftServer.getServer().getAllUsernames());
            "".length();
            if (1 < 0) {
                throw null;
            }
        }
        else if (array.length == "  ".length()) {
            list = CommandBase.getListOfStringsMatchingLastWord(array, S45PacketTitle.Type.getNames());
            "".length();
            if (3 < 1) {
                throw null;
            }
        }
        else {
            list = null;
        }
        return list;
    }
    
    @Override
    public boolean isUsernameIndex(final String[] array, final int n) {
        if (n == 0) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    private static void I() {
        (I = new String[0x36 ^ 0x26])["".length()] = I("\u001c'\u0004\"\r", "hNpNh");
        CommandTitle.I[" ".length()] = I("6.\u000f,0;%\u0011o%<5\u000e$\u007f 2\u0003&4", "UAbAQ");
        CommandTitle.I["  ".length()] = I("1;\u0006 &<0\u0018c3; \u0007(i''\n*\"", "RTkMG");
        CommandTitle.I["   ".length()] = I("#'\"\u0016?", "WNVzZ");
        CommandTitle.I[0x21 ^ 0x25] = I("\u001a\u001d\u0005.8\u001d\u0004\u0002", "ihgZQ");
        CommandTitle.I[0x73 ^ 0x76] = I("(\f\u000e=-%\u0007\u0010~8\"\u0017\u000f5b>\u0010\u00027)e\u0017\n$ .", "KccPL");
        CommandTitle.I[0x61 ^ 0x67] = I(":\u0006?\t\u000b", "NoRlx");
        CommandTitle.I[0x35 ^ 0x32] = I("2\f\t, ?\u0007\u0017o58\u0017\b$o$\u0010\u0005&$\u007f\u0017\r,$\"", "QcdAA");
        CommandTitle.I[0x26 ^ 0x2E] = I("\n\u0002;+\b\u0007\t%h\u001d\u0000\u0019:#G\u001c\u001e7!\f", "imVFi");
        CommandTitle.I[0xBC ^ 0xB5] = I(" \u0015.(5-\u001e0k *\u000e/ z0\u000f &10\t", "CzCET");
        CommandTitle.I[0xCE ^ 0xC4] = I("\u0000\t\b\u0002\r\r\u0002\u0016A\u0018\n\u0012\t\nB\u0016\u0015\u0004\b\t", "cfeol");
        CommandTitle.I[0x34 ^ 0x3F] = I("!\u0005<\u00198,\u000e\"Z-'\u0006=\u000685D;\u00076,/)\u0017<2\u001e8\u001b7", "BjQtY");
        CommandTitle.I[0x7 ^ 0xB] = I("", "mzIJb");
        CommandTitle.I[0xB8 ^ 0xB5] = I("!\u001f\f\"\u000e,\u0014\u0012a\u001b+\u0004\r*A1\u0005\u0002,\n1\u0003", "BpaOo");
        CommandTitle.I[0x37 ^ 0x39] = I("\r\u0004\u0005\u001c\t\u0000\u000f\u001b_\u001c\u0007\u001f\u0004\u0014F\u001b\u0018\t\u0016\r", "nkhqh");
        CommandTitle.I[0xE ^ 0x1] = I("\t\u0016,;\u0000\u0004\u001d2x\u0015\u0003\r-3O\u0019\f\"5\u0004\u0019\n", "jyAVa");
    }
    
    @Override
    public String getCommandName() {
        return CommandTitle.I["".length()];
    }
}
