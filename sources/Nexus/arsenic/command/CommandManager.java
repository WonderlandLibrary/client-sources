package arsenic.command;

import arsenic.command.impl.*;
import arsenic.utils.minecraft.PlayerUtils;

import java.util.*;
import java.util.stream.Collectors;

public class CommandManager {

    private final ArrayList<Command> commands;
    private List<String> autoCompletions;
    public CommandManager() {
        commands = new ArrayList<>();
        autoCompletions = new ArrayList<>();
    }

    public final int initialize() {
        commands.addAll(Arrays.asList(new BindCommand(), new HelpCommand(), new BindsCommand(), new TestCommand(), new ThemeCommand(), new ConfigCommand()));
        return commands.size();
    }

    public void executeCommand(String str) {
        str = str.substring(1);
        String name = str.split(" ")[0];
        String[] args = str.length() > name.length() ? str.substring(name.length() + 1, str.length()).split(" ") : new String[] {};
        Command command = getCommandByName(name);

        if (command != null) {
            if(command.getMinArgs() < args.length + 1)
                command.execute(args);
            else {
                PlayerUtils.addWaterMarkedMessageToChat(("Insufficient Arguments. Correct usage is:"));
                PlayerUtils.addWaterMarkedMessageToChat((command.getUsage()));
            }
            return;
        }

        PlayerUtils.addWaterMarkedMessageToChat("unable to find " + name);
    }

    public void add(Command command) {
        commands.add(command);
    }

    public Set<String> getCommands() { return commands.stream().map(Command::getName).collect(Collectors.toSet()); }

    public void updateAutoCompletions(String str) {
        str = str.substring(1);
        String name = str.split(" ")[0];
        String[] args = str.length() > name.length() ? str.substring(name.length() + 1, str.length()).split(" ")
                : new String[] {};
        if (args.length == 0) {
            setAutoCompletions(getClosestCommandName(name));
            return;
        }
        Command command = getCommandByName(name);
        if (command == null) {
            autoCompletions.clear();
            return;
        }
        setAutoCompletions(command.getAutoComplete(args[args.length - 1], args.length - 1));
    }

    // sorts alphabetically
    private void setAutoCompletions(List<String> list) {
        list.sort(Comparator.naturalOrder());
        autoCompletions = list;
    }

    public String getAutoCompletionWithoutRotation() {
        if (autoCompletions.isEmpty()) { return ""; }
        return autoCompletions.get(0);
    }

    public String getAutoCompletion() {
        if (autoCompletions.isEmpty()) { return ""; }
        Collections.rotate(autoCompletions, -1);
        return autoCompletions.get(autoCompletions.size() - 1);
    }

    public List<String> getClosestCommandName(String name) {
        return commands.stream().filter(
                        c -> c.getName().toLowerCase().startsWith(name.toLowerCase()) && c.getName().length() > name.length())
                .map(Command::getName).collect(Collectors.toList());
    }

    public Command getCommandByName(String name) {
        for (Command command : commands) { if (command.isName(name)) { return command; } }
        return null;
    }
}