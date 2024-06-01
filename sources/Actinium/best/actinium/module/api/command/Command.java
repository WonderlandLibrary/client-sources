package best.actinium.module.api.command;


import best.actinium.util.IAccess;

public abstract class Command implements IAccess {
    private final String[] expressions;

    public Command(final String... expressions) {
        this.expressions = expressions;
    }

    public abstract void execute(String[] args);

    public String[] getExpressions() {
        return expressions;
    }
}
