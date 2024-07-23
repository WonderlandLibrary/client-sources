package io.github.liticane.monoxide.value.impl;

import java.util.function.Supplier;
import io.github.liticane.monoxide.value.Value;

public class ModeValue extends Value<String> {

    private final String[] values;

    public ModeValue(String name, Object owner, String values[], Supplier<Boolean>[] suppliers) {
        super(name, owner, values[0], suppliers);
        this.values = values;
    }

    public ModeValue(String name, Object owner, String values[]) {
        super(name, owner, values[0]);
        this.values = values;
    }

    public boolean is(String string) {
        return this.getValue().equalsIgnoreCase(string);
    }
    public boolean is(int index) {
        return this.getValue().equalsIgnoreCase(values[index]);
    }

    @Override
    public String getValueAsString() {
        return getValue();
    }

    @Override
    public void setValue(String string) {
        this.value = string;
    }

    public String[] getValues() {
        return values;
    }

}
