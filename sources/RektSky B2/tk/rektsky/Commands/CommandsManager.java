package tk.rektsky.Commands;

import org.reflections.scanners.*;
import org.reflections.*;
import tk.rektsky.*;
import com.mojang.realmsclient.gui.*;
import tk.rektsky.Event.Events.*;
import java.util.*;

public class CommandsManager
{
    public static final ArrayList<Command> COMMANDS;
    
    public static void reloadCommands() {
        CommandsManager.COMMANDS.clear();
        final Reflections reflections = new Reflections("tk.rektsky.Commands.Commands", new Scanner[0]);
        final Set<Class<? extends Command>> commands = reflections.getSubTypesOf(Command.class);
        for (final Class<? extends Command> cmd : commands) {
            try {
                final Command m = (Command)cmd.newInstance();
                if (Client.finishedSetup) {
                    Client.addClientChat(ChatFormatting.GREEN + "Reloading Command: " + ChatFormatting.YELLOW + m.getName());
                }
                CommandsManager.COMMANDS.add(m);
            }
            catch (InstantiationException | IllegalAccessException ex2) {
                final ReflectiveOperationException ex;
                final ReflectiveOperationException e = ex;
                e.printStackTrace();
            }
        }
    }
    
    public static void init() {
        reloadCommands();
    }
    
    public static void processCommand(final ChatEvent event) {
        if (event.getMessage().startsWith(".")) {
            event.setCanceled(true);
            for (final Command cmd : CommandsManager.COMMANDS) {
                if (cmd.getName().equalsIgnoreCase(event.getMessage().split(" ")[0].replaceFirst(".", ""))) {
                    final List<String> arguments = new ArrayList<String>();
                    for (final String arg : event.getMessage().split(" ")) {
                        arguments.add(arg);
                    }
                    arguments.remove(0);
                    cmd.onCommand(event.getMessage(), arguments.toArray(new String[0]));
                    return;
                }
                for (final String s : cmd.getAliases()) {
                    if (s.equalsIgnoreCase(event.getMessage().split(" ")[0].replaceFirst(".", ""))) {
                        final List<String> arguments2 = new ArrayList<String>();
                        for (final String arg2 : event.getMessage().split(" ")) {
                            arguments2.add(arg2);
                        }
                        arguments2.remove(0);
                        cmd.onCommand(event.getMessage(), arguments2.toArray(new String[0]));
                        return;
                    }
                }
            }
            Client.addClientChat(ChatFormatting.RED + "Unknown Command! Try .help");
        }
    }
    
    static {
        COMMANDS = new ArrayList<Command>();
    }
}
