package io.github.liticane.monoxide.value.impl;

import java.util.function.Supplier;
import io.github.liticane.monoxide.value.Value;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MultiBooleanValue extends Value<List<String>> {

    String[] values;

    public MultiBooleanValue(String name, Object owner, String[] enabled, String values[], Supplier<Boolean>[] suppliers) {
        super(name, owner, new ArrayList<>(Arrays.asList(enabled)), suppliers);
        this.values = values;
    }

    public MultiBooleanValue(String name, Object owner, String[] enabled, String values[]) {
        super(name, owner, new ArrayList<>(Arrays.asList(enabled)));
        this.values = values;
    }

    public void toggle(String input) {
        this.set(input, !this.get(input));
    }

    public void set(String input, boolean state) {
        if(state) {
            if(!this.getValue().contains(input))
                this.getValue().add(input);
        } else {
            if(this.getValue().contains(input))
                this.getValue().remove(input);
        }
    }

    public String[] getValues() {
        return values;
    }

    public boolean get(String string) {
        return this.getValue().contains(string);
    }

    @Override
    public String getValueAsString() {
        return getValue().stream().collect(Collectors.joining(","));
    }

    @Override
    public void setValue(String string) {
        this.setValue(new ArrayList<>(Arrays.asList(string.split(","))));
    }

}
