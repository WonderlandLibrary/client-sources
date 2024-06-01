package com.polarware.value.impl;

import com.polarware.module.Module;
import com.polarware.value.Mode;
import com.polarware.value.Value;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BooleanSupplier;

/**
 * @author Strikeless
 * @since 01.07.2022
 */
@Getter
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