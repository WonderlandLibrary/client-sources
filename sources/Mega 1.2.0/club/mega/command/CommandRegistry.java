package club.mega.command;

import club.mega.command.impl.*;
import club.mega.util.ChatUtil;

import java.util.ArrayList;
import java.util.Collections;

public class CommandRegistry {

    private final ArrayList<Command> COMMANDS;

    public CommandRegistry() {

        COMMANDS = new ArrayList<>();

        addCommands(
                new ToggleCommand(),
                new BindCommand(),
                new ConfigCommand(),
                new HelpCommand()
        );

    }

    private final void addCommands(final Command... commands) {
        Collections.addAll(COMMANDS, commands);
    }

    public final ArrayList<Command> getCommands() {
        return COMMANDS;
    }

    public void execute(final String input) {
        final String[] split = input.split(" ");
        final String command = split[0];
        final String args = input.substring(command.length()).trim();

        for(final Command c : getCommands()) {
            for(final String alias : c.getAliases()) {
                if(command.equalsIgnoreCase(alias)) {
                    try {
                        c.execute(args.split(" "));
                    } catch (Exception e) {
                        c.usage();
                        e.printStackTrace();
                    }
                    return;
                }
            }
        }
        ChatUtil.sendMessage("Invalid Command!");
    }

    public final <T extends Command> T commandBy(final String name) {
        return (T) COMMANDS.stream().filter(command -> command.name().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

}