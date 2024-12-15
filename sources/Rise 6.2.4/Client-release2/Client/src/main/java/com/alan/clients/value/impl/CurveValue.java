package com.alan.clients.value.impl;

import com.alan.clients.module.Module;
import com.alan.clients.ui.click.standard.components.value.impl.CurveValueComponent;
import com.alan.clients.value.Mode;
import com.alan.clients.value.Value;

import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

public class CurveValue extends Value<Supplier<Double>> {

    public CurveValue(final String name, final Module parent) {
        super(name, parent, null);
    }

    public CurveValue(final String name, final Mode<?> parent) {
        super(name, parent, null);
    }

    public CurveValue(final String name, final Module parent, final BooleanSupplier hideIf) {
        super(name, parent, null, hideIf);
    }

    public CurveValue(final String name, final Mode<?> parent, final BooleanSupplier hideIf) {
        super(name, parent, null, hideIf);
    }

    @Override
    public List<Value<?>> getSubValues() {
        return null;
    }

    @Override
    public CurveValueComponent createUIComponent() {
        return new CurveValueComponent(this);
    }
}