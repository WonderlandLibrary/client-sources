package me.dinozoid.strife.command.argument.implementations;

import me.dinozoid.strife.command.argument.Argument;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class MultiChoiceArgument<T> extends Argument {

    private List<Argument> arguments;

    public MultiChoiceArgument(Class<T> type, String label, Supplier<Boolean> required, String... arguments) {
        super(type, label, required);
        this.arguments = new ArrayList<>();
        for(String string : arguments) {
            this.arguments.add(new Argument(type, string, required));
        }
    }

    public MultiChoiceArgument(Class<T> type, String label, String... arguments) {
        this(type, label, () -> true, arguments);
    }

    public List<Argument> arguments() {
        return arguments;
    }

}
