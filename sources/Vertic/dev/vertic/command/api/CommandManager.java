package dev.vertic.command.api;

import dev.vertic.Client;
import dev.vertic.command.Command;
import dev.vertic.command.impl.*;
import dev.vertic.event.api.EventLink;
import dev.vertic.event.impl.input.ChatSendEvent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class CommandManager {

    public final ArrayList<Command> commands = new ArrayList<>();

    public CommandManager() {
        commands.addAll(Arrays.asList(
                new Bind(),
                new Config(),
                new Help(),
                new Toggle()
        ));

        Client.instance.getEventBus().register(this);
    }

    @EventLink
    public void onChatSend(ChatSendEvent event) throws IOException {
        String message = event.getMessage();
        for (Command command : Client.instance.getCommandManager().commands) {
            final String[] strings = message.split(" ");
            if (strings.length > 0) {
                for (String s : command.getCalls()) {
                    if (strings[0].equalsIgnoreCase("." + s)) {
                        command.call(strings);
                        event.cancel();
                    }
                }
            }
        }
    }

}
