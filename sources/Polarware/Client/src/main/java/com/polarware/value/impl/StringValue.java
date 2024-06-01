package com.polarware.value.impl;

import com.polarware.module.Module;
import com.polarware.value.Mode;
import com.polarware.value.Value;

import java.util.List;
import java.util.function.BooleanSupplier;

/**
 * @author Patrick
 * @since 10/19/2021
 */
public class StringValue extends Value<String> {

    public StringValue(final String name, final Module parent, final String defaultValue) {
        super(name, parent, defaultValue);
    }

    public StringValue(final String name, final Mode<?> parent, final String defaultValue) {
        super(name, parent, defaultValue);
    }

    public StringValue(final String name, final Module parent, final String defaultValue, final BooleanSupplier hideIf) {
        super(name, parent, defaultValue, hideIf);
    }

    public StringValue(final String name, final Mode<?> parent, final String defaultValue, final BooleanSupplier hideIf) {
        super(name, parent, defaultValue, hideIf);
    }

    @Override
    public List<Value<?>> getSubValues() {
        return null;
    }
}