package dev.excellent.impl.value.impl;

import dev.excellent.client.module.api.Module;
import dev.excellent.impl.value.Value;
import dev.excellent.impl.value.mode.Mode;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BooleanSupplier;

@Getter
@Setter
public class ListValue<T> extends Value<T> {

    private final List<T> modes = new ArrayList<>();

    public ListValue(final String name, final Module parent) {
        super(name, parent, null);
    }

    public ListValue(final String name, final Mode<?> parent) {
        super(name, parent, null);
    }

    public ListValue(final String name, final Module parent, final BooleanSupplier hideIf) {
        super(name, parent, null, hideIf);
    }

    public ListValue(final String name, final Mode<?> parent, final BooleanSupplier hideIf) {
        super(name, parent, null, hideIf);
    }

    public ListValue<T> add(final T... modes) {
        if (modes == null) {
            return this;
        }
        this.modes.addAll(Arrays.asList(modes));
        setDefault(Arrays.stream(modes).findFirst().orElse(getModes().get(0)));
        return this;
    }

    public ListValue<T> setDefault(final int index) {
        setValue(modes.get(index));
        return this;
    }

    public ListValue<T> setDefault(final T mode) {
        setValue(mode);
        return this;
    }

    @Override
    public List<Value<?>> getSubValues() {
        return null;
    }

}