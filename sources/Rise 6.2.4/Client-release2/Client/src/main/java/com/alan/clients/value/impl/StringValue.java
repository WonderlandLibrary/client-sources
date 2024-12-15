package com.alan.clients.value.impl;

import com.alan.clients.module.Module;
import com.alan.clients.ui.click.standard.components.value.impl.StringValueComponent;
import com.alan.clients.value.Mode;
import com.alan.clients.value.Value;

import java.util.List;
import java.util.function.BooleanSupplier;
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

    @Override
    public StringValueComponent createUIComponent() {
        return new StringValueComponent(this);
    }
}