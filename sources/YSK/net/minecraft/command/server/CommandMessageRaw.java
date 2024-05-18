package net.minecraft.command.server;

import net.minecraft.entity.*;
import org.apache.commons.lang3.exception.*;
import com.google.gson.*;
import net.minecraft.entity.player.*;
import net.minecraft.command.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.server.*;

public class CommandMessageRaw extends CommandBase
{
    private static final String[] I;
    
    static {
        I();
    }
    
    @Override
    public String getCommandUsage(final ICommandSender commandSender) {
        return CommandMessageRaw.I[" ".length()];
    }
    
    private static void I() {
        (I = new String[0x2F ^ 0x2A])["".length()] = I("5 \r\u001a\u0005 2", "AEavw");
        CommandMessageRaw.I[" ".length()] = I("\n\u000b\u0018)3\u0007\u0000\u0006j&\f\b\u001963\u001eJ\u000073\u000e\u0001", "iduDR");
        CommandMessageRaw.I["  ".length()] = I("0),\u0007\r=\"2D\u00186*-\u0018\r$h4\u0019\r4#", "SFAjl");
        CommandMessageRaw.I["   ".length()] = I("\b\u0015>\"\u0018\u0005\u001e a\r\u000e\u0016?=\u0018\u001cT9<\u0016\u0005?+,\u001c\u001b\u000e: \u0017", "kzSOy");
        CommandMessageRaw.I[0xB ^ 0xF] = I("", "nZOFq");
    }
    
    @Override
    public String getCommandName() {
        return CommandMessageRaw.I["".length()];
    }
    
    @Override
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        if (array.length < "  ".length()) {
            throw new WrongUsageException(CommandMessageRaw.I["  ".length()], new Object["".length()]);
        }
        final EntityPlayerMP player = CommandBase.getPlayer(commandSender, array["".length()]);
        final String buildString = CommandBase.buildString(array, " ".length());
        try {
            player.addChatMessage(ChatComponentProcessor.processComponent(commandSender, IChatComponent.Serializer.jsonToComponent(buildString), player));
            "".length();
            if (3 <= -1) {
                throw null;
            }
        }
        catch (JsonParseException ex) {
            final Throwable rootCause = ExceptionUtils.getRootCause((Throwable)ex);
            final String s = CommandMessageRaw.I["   ".length()];
            final Object[] array2 = new Object[" ".length()];
            final int length = "".length();
            String message;
            if (rootCause == null) {
                message = CommandMessageRaw.I[0x87 ^ 0x83];
                "".length();
                if (4 <= -1) {
                    throw null;
                }
            }
            else {
                message = rootCause.getMessage();
            }
            array2[length] = message;
            throw new SyntaxErrorException(s, array2);
        }
    }
    
    @Override
    public boolean isUsernameIndex(final String[] array, final int n) {
        if (n == 0) {
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
            if (1 < -1) {
                throw null;
            }
        }
        else {
            listOfStringsMatchingLastWord = null;
        }
        return listOfStringsMatchingLastWord;
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
            if (true != true) {
                throw null;
            }
        }
        return sb.toString();
    }
}
