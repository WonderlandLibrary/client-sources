package io.github.liticane.monoxide.value.impl;

import java.util.function.Supplier;
import io.github.liticane.monoxide.value.Value;
import io.github.liticane.monoxide.value.impl.bool.BooleanParser;

import java.util.Optional;

public class BooleanValue extends Value<Boolean> {

    public BooleanValue(String label, Object owner, Boolean value, Supplier<Boolean>[] suppliers) {
        super(label, owner, value, suppliers);
    }

    public BooleanValue(String label, Object owner, Boolean value) {
        super(label, owner, value);
    }

    @Override
    public String getValueAsString() {
        return getValue().toString();
    }

    @Override
    public void setValue(String input) {
        Optional<Boolean> result = BooleanParser.parse(input);
        result.ifPresent(aBoolean -> this.value = aBoolean);
    }

    public void toggle() {
        this.value ^= true;
    }

    public boolean isEnabled() {
        return this.value;
    }

    public void setEnabled(boolean enabled) {
        this.value = enabled;
    }
}