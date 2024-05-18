package net.minecraft.command.server;

import net.minecraft.server.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.command.*;
import java.util.*;

public class CommandMessage extends CommandBase
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
            if (1 < 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public String getCommandName() {
        return CommandMessage.I["  ".length()];
    }
    
    private static void I() {
        (I = new String[0xCF ^ 0xC7])["".length()] = I("\u0019", "nqzgQ");
        CommandMessage.I[" ".length()] = I("\u001a5\t", "wFnQW");
        CommandMessage.I["  ".length()] = I("\u001a1\u0002'", "nTnKw");
        CommandMessage.I["   ".length()] = I(".&\u001a<.#-\u0004\u007f\"(:\u00040((g\u0002\".*,", "MIwQO");
        CommandMessage.I[0x82 ^ 0x86] = I(")7\u0002' $<\u001cd,/+\u001c+&/v\u001a9 -=", "JXoJA");
        CommandMessage.I[0x5B ^ 0x5E] = I("\u0002\u0018\b\u0018-\u000f\u0013\u0016[!\u0004\u0004\u0016\u0014+\u0004Y\u0016\u0014!\u0004#\u0004\u0007+\u0004\u0003", "aweuL");
        CommandMessage.I[0x30 ^ 0x36] = I("\u0002\u001b<\u000b\u0016\u000f\u0010\"H\u001a\u0004\u0007\"\u0007\u0010\u0004Z5\u000f\u0004\u0011\u00180\u001fY\b\u001a2\t\u001a\b\u001a6", "atQfw");
        CommandMessage.I[0xB7 ^ 0xB0] = I("\u0019\u001c+\f\u0010\u0014\u00175O\u001c\u001f\u00005\u0000\u0016\u001f]\"\b\u0002\n\u001f'\u0018_\u0015\u00062\u0006\u001e\u0013\u001d!", "zsFaq");
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
        return CommandBase.getListOfStringsMatchingLastWord(array, MinecraftServer.getServer().getAllUsernames());
    }
    
    @Override
    public String getCommandUsage(final ICommandSender commandSender) {
        return CommandMessage.I["   ".length()];
    }
    
    static {
        I();
    }
    
    @Override
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        if (array.length < "  ".length()) {
            throw new WrongUsageException(CommandMessage.I[0x95 ^ 0x91], new Object["".length()]);
        }
        final EntityPlayerMP player = CommandBase.getPlayer(commandSender, array["".length()]);
        if (player == commandSender) {
            throw new PlayerNotFoundException(CommandMessage.I[0x97 ^ 0x92], new Object["".length()]);
        }
        final int length = " ".length();
        int n;
        if (commandSender instanceof EntityPlayer) {
            n = "".length();
            "".length();
            if (2 != 2) {
                throw null;
            }
        }
        else {
            n = " ".length();
        }
        final IChatComponent chatComponentFromNthArg = CommandBase.getChatComponentFromNthArg(commandSender, array, length, n != 0);
        final String s = CommandMessage.I[0x7 ^ 0x1];
        final Object[] array2 = new Object["  ".length()];
        array2["".length()] = commandSender.getDisplayName();
        array2[" ".length()] = chatComponentFromNthArg.createCopy();
        final ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation(s, array2);
        final String s2 = CommandMessage.I[0x3B ^ 0x3C];
        final Object[] array3 = new Object["  ".length()];
        array3["".length()] = player.getDisplayName();
        array3[" ".length()] = chatComponentFromNthArg.createCopy();
        final ChatComponentTranslation chatComponentTranslation2 = new ChatComponentTranslation(s2, array3);
        chatComponentTranslation.getChatStyle().setColor(EnumChatFormatting.GRAY).setItalic(" ".length() != 0);
        chatComponentTranslation2.getChatStyle().setColor(EnumChatFormatting.GRAY).setItalic(" ".length() != 0);
        player.addChatMessage(chatComponentTranslation);
        commandSender.addChatMessage(chatComponentTranslation2);
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return "".length();
    }
    
    @Override
    public List<String> getCommandAliases() {
        final String[] array = new String["  ".length()];
        array["".length()] = CommandMessage.I["".length()];
        array[" ".length()] = CommandMessage.I[" ".length()];
        return Arrays.asList(array);
    }
}
