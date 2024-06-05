package me.dinozoid.strife.command.argument;

import java.util.function.Supplier;

public class Argument {

    private String label;
    private Object value;
    private Class type;
    private Supplier<Boolean> required;

    public enum ArgumentType {
        NUMBER, STRING
    }

    public Argument(Class type, String label, Supplier<Boolean> required) {
        this.label = label;
        this.type = type;
        this.required = required;
    }

    public Argument(Class type, String label) {
        this(type, label, () -> true);
    }

    public String label() {
        return label;
    }

    public Boolean required() {
        return required.get();
    }

    public void label(String label) {
        this.label = label;
    }

    public void required(Supplier<Boolean> required) {
        this.required = required;
    }

    public Class type() {
        return type;
    }

    public void type(Class type) {
        this.type = type;
    }

    public Object value() {
        return value;
    }

    public void value(Object value) {
        this.value = value;
    }
}
