package wtf.diablo.client.command.api.management;

import best.azura.eventbus.handler.EventHandler;
import best.azura.eventbus.handler.Listener;
import wtf.diablo.client.command.api.AbstractCommand;
import wtf.diablo.client.event.impl.player.chat.ChatEvent;
import wtf.diablo.client.util.mc.player.chat.ChatUtil;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class CommandRepository {
    private static final char PREFIX = '.';
    private final Builder builder;

    private CommandRepository(final Builder builder) {
        this.builder = builder;
    }

    public List<AbstractCommand> getCommands() {
        return this.builder.commands;
    }

    @EventHandler
    private final Listener<ChatEvent> chatEventListener = event -> {
        String message = event.getMessage();

        if (message.charAt(0) == PREFIX) {
            event.setCancelled(true);

            message = message.substring(1);

            final String[] split = message.split(" ");
            final String commandName = split[0];

            for (final AbstractCommand command : this.getCommands()) {
                final List<String> strings = new ArrayList<>(Arrays.asList(command.getAliases()));
                strings.add(command.getName());

                for (final String alias : strings) {
                    if (alias.equalsIgnoreCase(commandName)) {
                        command.execute(Arrays.copyOfRange(split, 1, split.length));
                        return;
                    }
                }
            }

            ChatUtil.addChatMessage("Unknown command. Type .help for a list of commands");
        }
    };

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private final List<AbstractCommand> commands;

        private Builder() {
            this.commands = new ArrayList<>();
        }

        public Builder addCommand(final Class<? extends AbstractCommand> commandClass) {
            try {
                this.commands.add(commandClass.getConstructor().newInstance());
            } catch (final InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                throw new RuntimeException("There was a problem while adding a command to the command repository (this can be caused if a default constructor is missing)", e);
            }
            return this;
        }

        public CommandRepository build() {
            return new CommandRepository(this);
        }
    }

}
