package dev.excellent.client.command;

import dev.excellent.Excellent;
import dev.excellent.api.event.impl.input.ChatInputEvent;
import dev.excellent.api.interfaces.event.Listener;
import dev.excellent.client.command.impl.*;
import dev.excellent.impl.util.chat.ChatUtil;
import i.gishreloaded.protection.annotation.Native;
import i.gishreloaded.protection.annotation.Protect;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

public final class CommandManager extends ArrayList<Command> {

    @Protect(Protect.Type.VIRTUALIZATION)
    @Native
    public void init() {
        add(new BindCommand());
        add(new HelpCommand());
        add(new LoginCommand());
        add(new NameCommand());
        add(new PanicCommand());
        add(new ClipCommand());
        add(new SayCommand());
        add(new FriendCommand());
        add(new MacrosCommand());
        add(new ToggleCommand());
        add(new ConfigCommand());
        add(new GPSCommand());
        add(new RctCommand());
        add(new StaffCommand());
        add(new IgnoreCommand());

//        if (DeadCodeConstants.IDE) {
//            add(new ReloadCommand());
//        }

        Excellent.getInst().getEventBus().register(this);
        iterate();
    }

    @Native
    private void iterate() {
        for (Command command : this) {
            Excellent.getInst().getEventBus().register(command);
        }
    }

    public <T extends Command> T get(final String name) {
        return this.stream()
                .filter(command -> Arrays.stream(command.getExpressions())
                        .anyMatch(expression -> expression.equalsIgnoreCase(name))
                )
                .map(command -> (T) command)
                .findAny()
                .orElse(null);
    }

    public <T extends Command> T get(final Class<T> clazz) {
        return this.stream()
                .filter(command -> command.getClass() == clazz)
                .map(command -> (T) command)
                .findAny()
                .orElse(null);
    }


    private final Listener<ChatInputEvent> onChatInput = event -> {
        String message = event.getMessage();

        if (!message.startsWith(".")) return;

        message = message.substring(1);
        final String[] args = message.split(" ");

        final AtomicBoolean commandFound = new AtomicBoolean(false);

        try {
            this.stream()
                    .filter(command ->
                            Arrays.stream(command.getExpressions())
                                    .anyMatch(expression ->
                                            expression.equalsIgnoreCase(args[0])))
                    .forEach(command -> {
                        commandFound.set(true);
                        command.execute(args);
                    });
        } catch (final Exception ignored) {
        }

        if (!commandFound.get()) {
            ChatUtil.addText("&cUnknown command, use &f.help &cfor assistance.");
        }

        event.setCancelled(true);
    };

}