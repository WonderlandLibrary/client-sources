package dev.excellent.impl.value.impl;

import dev.excellent.client.module.api.Module;
import dev.excellent.impl.value.Value;
import dev.excellent.impl.value.mode.Mode;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BooleanSupplier;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class ModeValue extends ListValue<Mode<?>> {

    private final List<Mode<?>> modes = new ArrayList<>();

    public ModeValue(final String name, final Module parent) {
        super(name, parent);
    }

    public ModeValue(final String name, final Mode<?> parent) {
        super(name, parent);
    }

    public ModeValue(final String name, final Module parent, final BooleanSupplier hideIf) {
        super(name, parent, hideIf);
    }

    public ModeValue(final String name, final Mode<?> parent, final BooleanSupplier hideIf) {
        super(name, parent, hideIf);
    }

    public void update(final Mode<?> value) {
        if (this.getParent() != null && this.getParent().isEnabled()) {
            getValue().unregister();
            setValue(value);
            getValue().register();
        } else {
            setValue(value);
        }
    }

    public ModeValue add(final Mode<?>... modes) {
        if (modes == null) {
            return this;
        }
        this.modes.addAll(Arrays.asList(modes));
        setDefault(Arrays.stream(modes).findFirst().orElse(getModes().get(0)));
        return this;
    }

    public ModeValue setDefault(final String name) {
        setValue(modes.stream()
                .filter(mode -> mode.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(modes.get(0))
        );

        setDefault(getValue());

        modes.forEach(mode -> mode.getValues().forEach(value -> value.setHideIf(() -> mode != this.getValue())));

        return this;
    }

    public boolean is(final String name) {
        if (this.getValue() == null) return false;

        return this.getValue().is(name);
    }

    public boolean isNot(final String name) {
        if (this.getValue() == null) return false;

        return !this.getValue().isNot(name);
    }

    @Override
    public List<Value<?>> getSubValues() {
        ArrayList<Value<?>> allValues = new ArrayList<>();

        for (Mode<?> mode : getModes()) {
            allValues.addAll(mode.getValues());
        }

        return allValues;
    }
}