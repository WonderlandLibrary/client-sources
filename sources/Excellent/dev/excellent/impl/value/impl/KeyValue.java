package dev.excellent.impl.value.impl;

import dev.excellent.client.module.api.Module;
import dev.excellent.impl.value.Value;
import dev.excellent.impl.value.mode.Mode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.function.BooleanSupplier;

@Getter
@Setter
public class KeyValue extends Value<Integer> {
    private boolean enabled = false;


    public KeyValue(String name, Module parent, Integer defaultValue) {
        super(name, parent, defaultValue);
    }

    public KeyValue(String name, Mode<?> parent, Integer defaultValue) {
        super(name, parent, defaultValue);
    }

    public KeyValue(String name, Module parent, Integer defaultValue, BooleanSupplier hideIf) {
        super(name, parent, defaultValue, hideIf);
    }

    public KeyValue(String name, Mode<?> parent, Integer defaultValue, BooleanSupplier hideIf) {
        super(name, parent, defaultValue, hideIf);
    }

    @Override
    public void setValue(Integer value) {
        super.setValue(value);
        if (!this.getParent().isEnabled()) {
            this.setEnabled(false);
        }
    }

    @Override
    public List<Value<?>> getSubValues() {
        return null;
    }
}
