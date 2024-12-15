package com.alan.clients.value.impl;

import com.alan.clients.module.Module;
import com.alan.clients.ui.click.standard.components.value.impl.NumberValueComponent;
import com.alan.clients.value.Mode;
import com.alan.clients.value.Value;
import lombok.Getter;

import java.util.List;
import java.util.function.BooleanSupplier;
@Getter
public class NumberValue extends Value<Number> {

    private final Number min;
    private final Number max;
    private final Number decimalPlaces;

    public NumberValue(final String name, final Module parent, final Number defaultValue,
                       final Number min, final Number max, final Number decimalPlaces) {
        super(name, parent, defaultValue);
        this.decimalPlaces = decimalPlaces;

        this.min = min;
        this.max = max;
    }

    public NumberValue(final String name, final Mode<?> parent, final Number defaultValue,
                       final Number min, final Number max, final Number decimalPlaces) {
        super(name, parent, defaultValue);
        this.decimalPlaces = decimalPlaces;

        this.min = min;
        this.max = max;
    }

    public NumberValue(final String name, final Module parent, final Number defaultValue,
                       final Number min, final Number max, final Number decimalPlaces, final BooleanSupplier hideIf) {
        super(name, parent, defaultValue, hideIf);
        this.decimalPlaces = decimalPlaces;

        this.min = min;
        this.max = max;
    }

    public NumberValue(final String name, final Mode<?> parent, final Number defaultValue,
                       final Number min, final Number max, final Number decimalPlaces, final BooleanSupplier hideIf) {
        super(name, parent, defaultValue, hideIf);
        this.decimalPlaces = decimalPlaces;

        this.min = min;
        this.max = max;
    }

    @Override
    public List<Value<?>> getSubValues() {
        return null;
    }

    @Override
    public NumberValueComponent createUIComponent() {
        return new NumberValueComponent(this);
    }
}