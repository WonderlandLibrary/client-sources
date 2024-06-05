package io.github.nevalackin.radium.command;

import io.github.nevalackin.radium.command.impl.ClientNameCommand;
import me.zane.basicbus.api.annotations.Listener;
import io.github.nevalackin.radium.RadiumClient;
import io.github.nevalackin.radium.command.impl.ConfigCommand;
import io.github.nevalackin.radium.command.impl.HelpCommand;
import io.github.nevalackin.radium.command.impl.ToggleCommand;
import io.github.nevalackin.radium.event.impl.player.SendMessageEvent;
import io.github.nevalackin.radium.utils.handler.Manager;
import io.github.nevalackin.radium.utils.Wrapper;

import java.util.Arrays;

public final class CommandManager extends Manager<Command> {

    private static final String PREFIX = ".";
    private static final String HELP_MESSAGE = "Try '" + PREFIX + "help'";

    public CommandManager() {
        super(Arrays.asList(
                new HelpCommand(),
                new ToggleCommand(),
                new ConfigCommand(),
                new ClientNameCommand())
        );
        RadiumClient.getInstance().getEventBus().subscribe(this);
    }

    @Listener
    public void onSendMessageEvent(SendMessageEvent event) {
        String message;
        if ((message = event.getMessage()).startsWith(PREFIX)) {
            event.setCancelled();
            String removedPrefix = message.substring(1);
            String[] arguments = removedPrefix.split(" ");
            if (!removedPrefix.isEmpty() && arguments.length > 0) {
                for (Command command : getElements()) {
                    for (String alias : command.getAliases()) {
                        if (alias.equalsIgnoreCase(arguments[0])) {
                            try {
                                command.execute(arguments);
                            } catch (CommandExecutionException e) {
                                Wrapper.addChatMessage("Invalid command syntax. Hint: " + e.getMessage());
                            }
                            return;
                        }
                    }
                }
                Wrapper.addChatMessage("'" + arguments[0] + "' is not a command. " + HELP_MESSAGE);
            } else
                Wrapper.addChatMessage("No arguments were supplied. " + HELP_MESSAGE);
        }
    }
}
