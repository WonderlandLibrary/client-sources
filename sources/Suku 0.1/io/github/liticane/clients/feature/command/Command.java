package io.github.liticane.clients.feature.command;

import io.github.liticane.clients.util.interfaces.IMethods;

public abstract class Command implements IMethods {
    private final String[] expressions;

    public Command(final String... expressions) {
        this.expressions = expressions;
    }

    public abstract void execute(String[] args);

    public String[] getExpressions() {
        return expressions;
    }
}
