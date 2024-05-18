package net.minecraft.command;

import net.minecraft.event.*;
import net.minecraft.entity.player.*;
import net.minecraft.server.*;
import net.minecraft.util.*;
import java.util.*;

public class CommandHelp extends CommandBase
{
    private static final String[] I;
    
    @Override
    public int getRequiredPermissionLevel() {
        return "".length();
    }
    
    @Override
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        final List<ICommand> sortedPossibleCommands = this.getSortedPossibleCommands(commandSender);
        final int n = (sortedPossibleCommands.size() - " ".length()) / (0x18 ^ 0x1F);
        "".length();
        int n2;
        try {
            int length;
            if (array.length == 0) {
                length = "".length();
                "".length();
                if (false) {
                    throw null;
                }
            }
            else {
                length = CommandBase.parseInt(array["".length()], " ".length(), n + " ".length()) - " ".length();
            }
            n2 = length;
            "".length();
            if (2 != 2) {
                throw null;
            }
        }
        catch (NumberInvalidException ex) {
            final ICommand command = this.getCommands().get(array["".length()]);
            if (command != null) {
                throw new WrongUsageException(command.getCommandUsage(commandSender), new Object["".length()]);
            }
            if (MathHelper.parseIntWithDefault(array["".length()], -" ".length()) != -" ".length()) {
                throw ex;
            }
            throw new CommandNotFoundException();
        }
        final int min = Math.min((n2 + " ".length()) * (0x6F ^ 0x68), sortedPossibleCommands.size());
        final String s = CommandHelp.I["   ".length()];
        final Object[] array2 = new Object["  ".length()];
        array2["".length()] = n2 + " ".length();
        array2[" ".length()] = n + " ".length();
        final ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation(s, array2);
        chatComponentTranslation.getChatStyle().setColor(EnumChatFormatting.DARK_GREEN);
        commandSender.addChatMessage(chatComponentTranslation);
        int i = n2 * (0x80 ^ 0x87);
        "".length();
        if (2 == 4) {
            throw null;
        }
        while (i < min) {
            final ICommand command2 = sortedPossibleCommands.get(i);
            final ChatComponentTranslation chatComponentTranslation2 = new ChatComponentTranslation(command2.getCommandUsage(commandSender), new Object["".length()]);
            chatComponentTranslation2.getChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, CommandHelp.I[0xAA ^ 0xAE] + command2.getCommandName() + CommandHelp.I[0x17 ^ 0x12]));
            commandSender.addChatMessage(chatComponentTranslation2);
            ++i;
        }
        if (n2 == 0 && commandSender instanceof EntityPlayer) {
            final ChatComponentTranslation chatComponentTranslation3 = new ChatComponentTranslation(CommandHelp.I[0x7A ^ 0x7C], new Object["".length()]);
            chatComponentTranslation3.getChatStyle().setColor(EnumChatFormatting.GREEN);
            commandSender.addChatMessage(chatComponentTranslation3);
        }
    }
    
    @Override
    public List<String> getCommandAliases() {
        final String[] array = new String[" ".length()];
        array["".length()] = CommandHelp.I["  ".length()];
        return Arrays.asList(array);
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
            if (1 <= 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    protected List<ICommand> getSortedPossibleCommands(final ICommandSender commandSender) {
        final List<ICommand> possibleCommands = MinecraftServer.getServer().getCommandManager().getPossibleCommands(commandSender);
        Collections.sort((List<Comparable>)possibleCommands);
        return possibleCommands;
    }
    
    @Override
    public List<String> addTabCompletionOptions(final ICommandSender commandSender, final String[] array, final BlockPos blockPos) {
        if (array.length == " ".length()) {
            final Set<String> keySet = this.getCommands().keySet();
            return CommandBase.getListOfStringsMatchingLastWord(array, (String[])keySet.toArray(new String[keySet.size()]));
        }
        return null;
    }
    
    private static void I() {
        (I = new String[0x8A ^ 0x8D])["".length()] = I("\u0004*#\u0015", "lOOeI");
        CommandHelp.I[" ".length()] = I("-%)\u000b\u0018 .7H\u0011+&4H\f=+#\u0003", "NJDfy");
        CommandHelp.I["  ".length()] = I("f", "YisZT");
        CommandHelp.I["   ".length()] = I("\r.\u0007\u0006\u001b\u0000%\u0019E\u0012\u000b-\u001aE\u0012\u000b \u000e\u000e\b", "nAjkz");
        CommandHelp.I[0x7D ^ 0x79] = I("y", "VZutD");
        CommandHelp.I[0x0 ^ 0x5] = I("R", "ruXco");
        CommandHelp.I[0x95 ^ 0x93] = I(";\u0002#)\u00116\t=j\u0018=\u0001>j\u00167\u0002:!\u0002", "XmNDp");
    }
    
    protected Map<String, ICommand> getCommands() {
        return MinecraftServer.getServer().getCommandManager().getCommands();
    }
    
    @Override
    public String getCommandName() {
        return CommandHelp.I["".length()];
    }
    
    @Override
    public String getCommandUsage(final ICommandSender commandSender) {
        return CommandHelp.I[" ".length()];
    }
}
