package dev.excellent.impl.value.impl;

import dev.excellent.client.module.api.Module;
import dev.excellent.impl.value.Value;
import dev.excellent.impl.value.mode.Mode;
import lombok.Getter;
import lombok.Setter;

import java.util.*;
import java.util.function.BooleanSupplier;

@Getter
@Setter
public class MultiBooleanValue extends Value<Boolean> {

    private final Map<String, BooleanValue> boolSettings;

    private final Mode<?> mode;

    public MultiBooleanValue(final String name, final Module parent) {
        super(name, parent, null);
        boolSettings = new LinkedHashMap<>();
        this.mode = null;
    }

    public MultiBooleanValue(final String name, final Module parent, final BooleanSupplier hideIf) {
        super(name, parent, null, hideIf);
        boolSettings = new LinkedHashMap<>();
        this.mode = null;
    }

    public MultiBooleanValue(final String name, final Mode<?> parent, final BooleanSupplier hideIf) {
        super(name, parent, null, hideIf);
        boolSettings = new LinkedHashMap<>();
        this.mode = null;
    }

    public MultiBooleanValue(final String name, final Module parent, final Mode<?> mode) {
        super(name, parent, null);
        boolSettings = new LinkedHashMap<>();
        this.mode = mode;
        getMode().getValues().forEach(value -> value.setHideIf(() -> !getValue()));
    }

    public MultiBooleanValue(final String name, final Mode<?> parent, final Mode<?> mode) {
        super(name, parent, null);
        boolSettings = new LinkedHashMap<>();
        this.mode = mode;
    }

    public MultiBooleanValue(final String name, final Module parent, final BooleanSupplier hideIf, final Mode<?> mode) {
        super(name, parent, null, hideIf);
        boolSettings = new LinkedHashMap<>();
        this.mode = mode;
    }

    public MultiBooleanValue(final String name, final Mode<?> parent, final BooleanSupplier hideIf, final Mode<?> mode) {
        super(name, parent, null, hideIf);
        boolSettings = new LinkedHashMap<>();
        this.mode = mode;
    }

    public MultiBooleanValue add(final BooleanValue... booleanValues) {
        if (booleanValues == null) {
            return this;
        }
        Arrays.stream(booleanValues).forEach(booleanValue -> boolSettings.put(booleanValue.getName().toLowerCase(), booleanValue));
        return this;
    }


    public boolean isEnabled(String valueName) {
        return boolSettings.get(valueName.toLowerCase()).getValue();
    }

    public BooleanValue get(String valueName) {
        return boolSettings.computeIfAbsent(valueName.toLowerCase(), value -> null);
    }

    public Collection<BooleanValue> getValues() {
        return boolSettings.values();
    }

    @Override
    public List<Value<?>> getSubValues() {
        if (getMode() == null) return null;
        else return getMode().getValues();
    }
}
