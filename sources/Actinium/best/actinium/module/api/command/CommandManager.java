package best.actinium.module.api.command;


import best.actinium.Actinium;
import best.actinium.event.api.Callback;
import best.actinium.event.impl.input.ChatInputEvent;
import best.actinium.util.render.ChatUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

public final class CommandManager extends ArrayList<Command> {

    public void init() {
        Actinium.INSTANCE.getEventManager().subscribe(this);
    }

    @Callback
    public void onInput(ChatInputEvent e) {
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
    }
}
