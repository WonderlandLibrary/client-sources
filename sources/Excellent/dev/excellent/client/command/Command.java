package dev.excellent.client.command;

import dev.excellent.api.interfaces.game.IMinecraft;
import dev.excellent.impl.util.chat.ChatUtil;
import lombok.Getter;

@Getter
public abstract class Command implements IMinecraft {

    private final String description;
    private final String[] expressions;

    public Command(final String description, final String... expressions) {
        this.description = description;
        this.expressions = expressions;
    }

    public abstract void execute(String[] args);

    protected final void error() {
        ChatUtil.addText("Impermissible command arguments.");
    }

    protected final void usage(String usage) {
        ChatUtil.addText(usage);
    }
}