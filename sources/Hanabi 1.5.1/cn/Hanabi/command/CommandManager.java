package cn.Hanabi.command;

import org.jetbrains.annotations.*;
import cn.Hanabi.*;
import cn.Hanabi.modules.*;
import ClassSub.*;
import java.util.function.*;
import java.util.stream.*;
import java.util.*;

public class CommandManager
{
    @NotNull
    private List<Command> commands;
    
    public CommandManager() {
        this.commands = new ArrayList<Command>();
    }
    
    public void addCommands() {
        this.addCommand(new Class271());
        this.addCommand(new Bind());
        this.addCommand(new Prefix());
        this.addCommand(new Spammer());
        this.addCommand(new Class138());
        this.addCommand(new Class267());
        this.addCommand(new Binds());
        this.addCommand(new Config());
        this.addCommand(new Fcmd());
        this.addCommand(new Crash());
        this.addCommand(new Checkuser());
        this.addCommand(new Whois());
        Hanabi.INSTANCE.hmlManager.onCommandManagerLoading(this);
    }
    
    public void addCommand(final Command cmd) {
        this.commands.add(cmd);
    }
    
    public boolean executeCommand(@NotNull final String string) {
        final String raw = string.substring(1);
        final String[] split = raw.split(" ");
        if (split.length == 0) {
            return false;
        }
        final String cmdName = split[0];
        final Command command = this.commands.stream().filter(CommandManager::lambda$executeCommand$0).findFirst().orElse(null);
        try {
            if (command == null) {
                if (ModManager.getModule("IRC").isEnabled()) {
                    new Class199(Class203.type, Class194.getIRCUserByNameAndType(Class203.type, Class203.username), raw).sendPacketToServer(Class203.socket.writer);
                }
                else {
                    Class120.sendClientMessage("Open your IRC first then you can send message!", Class84.Class307.ERROR);
                }
                return true;
            }
            final String[] args = new String[split.length - 1];
            System.arraycopy(split, 1, args, 0, split.length - 1);
            command.run(split[0], args);
            return true;
        }
        catch (Class226 e) {
            Class64.send("§c" + e.getMessage());
            return true;
        }
    }
    
    public Collection<String> autoComplete(@NotNull final String currCmd) {
        final String raw = currCmd.substring(1);
        final String[] split = raw.split(" ");
        final List<String> ret = new ArrayList<String>();
        final Command currentCommand = (split.length >= 1) ? this.commands.stream().filter(CommandManager::lambda$autoComplete$1).findFirst().orElse(null) : null;
        if (split.length >= 2 || (currentCommand != null && currCmd.endsWith(" "))) {
            if (currentCommand == null) {
                return ret;
            }
            final String[] args = new String[split.length - 1];
            System.arraycopy(split, 1, args, 0, split.length - 1);
            final List<String> autocomplete = currentCommand.autocomplete(args.length + (currCmd.endsWith(" ") ? 1 : 0), args);
            return (autocomplete == null) ? new ArrayList<String>() : autocomplete;
        }
        else {
            if (split.length == 1) {
                for (final Command command : this.commands) {
                    ret.addAll(command.getNameAndAliases());
                }
                return ret.stream().map((Function<? super Object, ?>)CommandManager::lambda$autoComplete$2).filter(CommandManager::lambda$autoComplete$3).collect((Collector<? super Object, ?, Collection<String>>)Collectors.toList());
            }
            return ret;
        }
    }
    
    private static boolean lambda$autoComplete$3(final String currCmd, final String str) {
        return str.toLowerCase().startsWith(currCmd.toLowerCase());
    }
    
    private static String lambda$autoComplete$2(final String str) {
        return "." + str;
    }
    
    private static boolean lambda$autoComplete$1(final String[] split, final Command cmd) {
        return cmd.match(split[0]);
    }
    
    private static boolean lambda$executeCommand$0(final String cmdName, final Command cmd) {
        return cmd.match(cmdName);
    }
}
