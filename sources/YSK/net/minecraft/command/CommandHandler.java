package net.minecraft.command;

import net.minecraft.util.*;
import com.google.common.collect.*;
import java.util.*;
import org.apache.logging.log4j.*;
import net.minecraft.entity.*;

public class CommandHandler implements ICommandManager
{
    private final Map<String, ICommand> commandMap;
    private static final Logger logger;
    private final Set<ICommand> commandSet;
    private static final String[] I;
    
    protected boolean tryExecute(final ICommandSender commandSender, final String[] array, final ICommand command, final String s) {
        try {
            command.processCommand(commandSender, array);
            return " ".length() != 0;
        }
        catch (WrongUsageException ex) {
            final String s2 = CommandHandler.I[0x7E ^ 0x7A];
            final Object[] array2 = new Object[" ".length()];
            array2["".length()] = new ChatComponentTranslation(ex.getMessage(), ex.getErrorObjects());
            final ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation(s2, array2);
            chatComponentTranslation.getChatStyle().setColor(EnumChatFormatting.RED);
            commandSender.addChatMessage(chatComponentTranslation);
            "".length();
            if (2 == 4) {
                throw null;
            }
        }
        catch (CommandException ex2) {
            final ChatComponentTranslation chatComponentTranslation2 = new ChatComponentTranslation(ex2.getMessage(), ex2.getErrorObjects());
            chatComponentTranslation2.getChatStyle().setColor(EnumChatFormatting.RED);
            commandSender.addChatMessage(chatComponentTranslation2);
            "".length();
            if (2 < 0) {
                throw null;
            }
        }
        catch (Throwable t) {
            final ChatComponentTranslation chatComponentTranslation3 = new ChatComponentTranslation(CommandHandler.I[0x28 ^ 0x2D], new Object["".length()]);
            chatComponentTranslation3.getChatStyle().setColor(EnumChatFormatting.RED);
            commandSender.addChatMessage(chatComponentTranslation3);
            CommandHandler.logger.warn(CommandHandler.I[0x17 ^ 0x11] + s + CommandHandler.I[0x0 ^ 0x7]);
        }
        return "".length() != 0;
    }
    
    private static String[] dropFirstString(final String[] array) {
        final String[] array2 = new String[array.length - " ".length()];
        System.arraycopy(array, " ".length(), array2, "".length(), array.length - " ".length());
        return array2;
    }
    
    public CommandHandler() {
        this.commandMap = (Map<String, ICommand>)Maps.newHashMap();
        this.commandSet = (Set<ICommand>)Sets.newHashSet();
    }
    
    private static void I() {
        (I = new String[0x4 ^ 0xD])["".length()] = I("K", "dmWYG");
        CommandHandler.I[" ".length()] = I("d", "DnDub");
        CommandHandler.I["  ".length()] = I("\u0005#\u001d?8\b(\u0003|>\u0003\"\u0015 0\u0005b\u001e=- #\u0005<=", "fLpRY");
        CommandHandler.I["   ".length()] = I("!9;\u000b\f,2%H\n'83\u0014\u0004!x&\u0003\u001f/?%\u0015\u0004-8", "BVVfm");
        CommandHandler.I[0x68 ^ 0x6C] = I("4\b\u0005\u0019\u00059\u0003\u001bZ\u00032\t\r\u0006\r4I\u001d\u0007\u00050\u0002", "Wghtd");
        CommandHandler.I[0x4A ^ 0x4F] = I("%\"<\u0003\u0002()\"@\u0004##4\u001c\n%c4\u0016\u0000#=%\u0007\f(", "FMQnc");
        CommandHandler.I[0xB9 ^ 0xBF] = I("\u0002?!<*/w p>3?75=2p7?#,1:4taw", "APTPN");
        CommandHandler.I[0x48 ^ 0x4F] = I("C", "dMZGz");
        CommandHandler.I[0x67 ^ 0x6F] = I("g", "GtwDQ");
    }
    
    @Override
    public List<String> getTabCompletionOptions(final ICommandSender commandSender, final String s, final BlockPos blockPos) {
        final String[] split = s.split(CommandHandler.I[0xAF ^ 0xA7], -" ".length());
        final String s2 = split["".length()];
        if (split.length != " ".length()) {
            if (split.length > " ".length()) {
                final ICommand command = this.commandMap.get(s2);
                if (command != null && command.canCommandSenderUseCommand(commandSender)) {
                    return command.addTabCompletionOptions(commandSender, dropFirstString(split), blockPos);
                }
            }
            return null;
        }
        final ArrayList arrayList = Lists.newArrayList();
        final Iterator<Map.Entry<String, ICommand>> iterator = this.commandMap.entrySet().iterator();
        "".length();
        if (1 < -1) {
            throw null;
        }
        while (iterator.hasNext()) {
            final Map.Entry<String, ICommand> entry = iterator.next();
            if (CommandBase.doesStringStartWith(s2, entry.getKey()) && entry.getValue().canCommandSenderUseCommand(commandSender)) {
                arrayList.add(entry.getKey());
            }
        }
        return (List<String>)arrayList;
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
            if (4 <= -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public ICommand registerCommand(final ICommand command) {
        this.commandMap.put(command.getCommandName(), command);
        this.commandSet.add(command);
        final Iterator<String> iterator = command.getCommandAliases().iterator();
        "".length();
        if (4 <= -1) {
            throw null;
        }
        while (iterator.hasNext()) {
            final String s = iterator.next();
            final ICommand command2 = this.commandMap.get(s);
            if (command2 == null || !command2.getCommandName().equals(s)) {
                this.commandMap.put(s, command);
            }
        }
        return command;
    }
    
    static {
        I();
        logger = LogManager.getLogger();
    }
    
    @Override
    public List<ICommand> getPossibleCommands(final ICommandSender commandSender) {
        final ArrayList arrayList = Lists.newArrayList();
        final Iterator<ICommand> iterator = this.commandSet.iterator();
        "".length();
        if (2 == 0) {
            throw null;
        }
        while (iterator.hasNext()) {
            final ICommand command = iterator.next();
            if (command.canCommandSenderUseCommand(commandSender)) {
                arrayList.add(command);
            }
        }
        return (List<ICommand>)arrayList;
    }
    
    @Override
    public Map<String, ICommand> getCommands() {
        return this.commandMap;
    }
    
    @Override
    public int executeCommand(final ICommandSender commandSender, String s) {
        s = s.trim();
        if (s.startsWith(CommandHandler.I["".length()])) {
            s = s.substring(" ".length());
        }
        final String[] split = s.split(CommandHandler.I[" ".length()]);
        final String s2 = split["".length()];
        final String[] dropFirstString = dropFirstString(split);
        final ICommand command = this.commandMap.get(s2);
        final int usernameIndex = this.getUsernameIndex(command, dropFirstString);
        int length = "".length();
        if (command == null) {
            final ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation(CommandHandler.I["  ".length()], new Object["".length()]);
            chatComponentTranslation.getChatStyle().setColor(EnumChatFormatting.RED);
            commandSender.addChatMessage(chatComponentTranslation);
            "".length();
            if (0 >= 3) {
                throw null;
            }
        }
        else if (command.canCommandSenderUseCommand(commandSender)) {
            if (usernameIndex > -" ".length()) {
                final List<Entity> matchEntities = PlayerSelector.matchEntities(commandSender, dropFirstString[usernameIndex], (Class<? extends Entity>)Entity.class);
                final String s3 = dropFirstString[usernameIndex];
                commandSender.setCommandStat(CommandResultStats.Type.AFFECTED_ENTITIES, matchEntities.size());
                final Iterator<Entity> iterator = matchEntities.iterator();
                "".length();
                if (2 < 0) {
                    throw null;
                }
                while (iterator.hasNext()) {
                    dropFirstString[usernameIndex] = iterator.next().getUniqueID().toString();
                    if (this.tryExecute(commandSender, dropFirstString, command, s)) {
                        ++length;
                    }
                }
                dropFirstString[usernameIndex] = s3;
                "".length();
                if (3 != 3) {
                    throw null;
                }
            }
            else {
                commandSender.setCommandStat(CommandResultStats.Type.AFFECTED_ENTITIES, " ".length());
                if (this.tryExecute(commandSender, dropFirstString, command, s)) {
                    ++length;
                    "".length();
                    if (2 <= 1) {
                        throw null;
                    }
                }
            }
        }
        else {
            final ChatComponentTranslation chatComponentTranslation2 = new ChatComponentTranslation(CommandHandler.I["   ".length()], new Object["".length()]);
            chatComponentTranslation2.getChatStyle().setColor(EnumChatFormatting.RED);
            commandSender.addChatMessage(chatComponentTranslation2);
        }
        commandSender.setCommandStat(CommandResultStats.Type.SUCCESS_COUNT, length);
        return length;
    }
    
    private int getUsernameIndex(final ICommand command, final String[] array) {
        if (command == null) {
            return -" ".length();
        }
        int i = "".length();
        "".length();
        if (1 == 2) {
            throw null;
        }
        while (i < array.length) {
            if (command.isUsernameIndex(array, i) && PlayerSelector.matchesMultiplePlayers(array[i])) {
                return i;
            }
            ++i;
        }
        return -" ".length();
    }
}
