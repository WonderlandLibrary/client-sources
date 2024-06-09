package io.github.liticane.clients.feature.command;

import io.github.liticane.clients.Client;
import io.github.liticane.clients.feature.event.api.annotations.SubscribeEvent;
import io.github.liticane.clients.feature.event.impl.input.ChatInputEvent;
import io.github.liticane.clients.util.misc.ChatUtil;
import io.github.liticane.clients.feature.event.api.EventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

public final class CommandManager extends ArrayList<Command> {

    public void init() {
        Client.INSTANCE.getEventManager().register(this);
    }

    @SubscribeEvent
    private final EventListener<ChatInputEvent> onChatInput = e -> {
        String message = e.getMessage();

        if (!message.startsWith("."))
            return;

        message = message.substring(1);
        final String[] args = message.split(" ");

        final AtomicBoolean commandFound = new AtomicBoolean(false);

        try {
            this.stream().filter(cmd ->
                    Arrays.stream(cmd.getExpressions())
                            .anyMatch(exp -> exp.equalsIgnoreCase(args[0])))
                    .forEach(cmd -> {
                        commandFound.set(true);
                        cmd.execute(args);
                    });
        } catch (final Exception ex) {
            ex.printStackTrace();
        }

        if (!commandFound.get())
            ChatUtil.display("Not found.");

        e.setCancelled(true);
    };
}
