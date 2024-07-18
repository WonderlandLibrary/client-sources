package com.alan.clients.command;

import com.alan.clients.util.Accessor;
import com.alan.clients.util.chat.ChatUtil;
import lombok.Getter;
@Getter
public abstract class Command implements Accessor {

    private final String description;
    private final String[] expressions;

    public Command(final String description, final String... expressions) {
        this.description = description;
        this.expressions = expressions;
    }

    public abstract void execute(String[] args);

    protected final void error() {
        ChatUtil.display("§cInvalid command arguments.");
    }

    protected final void error(String usage) {
        error();
        ChatUtil.display("Correct Usage: " + usage);
    }
}