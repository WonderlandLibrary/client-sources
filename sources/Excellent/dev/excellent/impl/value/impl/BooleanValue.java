package dev.excellent.impl.value.impl;

import dev.excellent.client.module.api.Module;
import dev.excellent.impl.util.animation.Animation;
import dev.excellent.impl.util.animation.Easing;
import dev.excellent.impl.value.Value;
import dev.excellent.impl.value.mode.Mode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.function.BooleanSupplier;

@Getter
@Setter
public class BooleanValue extends Value<Boolean> {
    public final Animation animation = new Animation(Easing.LINEAR, 100);

    private final Mode<?> mode;

    public BooleanValue(final String name, final Boolean defaultValue) {
        super(name, defaultValue);
        this.mode = null;
    }

    public BooleanValue(final String name, final Module parent, final Boolean defaultValue) {
        super(name, parent, defaultValue);
        this.mode = null;
    }

    public BooleanValue(final String name, final Mode<?> parent, final Boolean defaultValue) {
        super(name, parent, defaultValue);
        this.mode = null;
    }

    public BooleanValue(final String name, final Module parent, final Boolean defaultValue, final BooleanSupplier hideIf) {
        super(name, parent, defaultValue, hideIf);
        this.mode = null;
    }

    public BooleanValue(final String name, final Mode<?> parent, final Boolean defaultValue, final BooleanSupplier hideIf) {
        super(name, parent, defaultValue, hideIf);
        this.mode = null;
    }

    public BooleanValue(final String name, final Module parent, final Boolean defaultValue, final Mode<?> mode) {
        super(name, parent, defaultValue);
        this.mode = mode;
        getMode().getValues().forEach(value -> value.setHideIf(() -> !getValue()));
    }

    public BooleanValue(final String name, final Mode<?> parent, final Boolean defaultValue, final Mode<?> mode) {
        super(name, parent, defaultValue);
        this.mode = mode;
    }

    public BooleanValue(final String name, final Module parent, final Boolean defaultValue, final BooleanSupplier hideIf, final Mode<?> mode) {
        super(name, parent, defaultValue, hideIf);
        this.mode = mode;
    }

    public BooleanValue(final String name, final Mode<?> parent, final Boolean defaultValue, final BooleanSupplier hideIf, final Mode<?> mode) {
        super(name, parent, defaultValue, hideIf);
        this.mode = mode;
    }

    @Override
    public void setValue(final Boolean value) {
        super.setValue(value);

        if (this.mode != null && this.getParent() != null) {
            if (this.getParent().isEnabled()) {
                if (this.getValue()) {
                    this.mode.register();
                } else {
                    this.mode.unregister();
                }
            }
        }
    }

    @Override
    public List<Value<?>> getSubValues() {
        if (getMode() == null) return null;
        else return getMode().getValues();
    }

    @Override
    public void setValueAsObject(final Object value) {
        super.setValueAsObject(value);
        if (this.mode != null && this.getParent() != null) {
            if (this.getParent().isEnabled() && this.getValue()) {
                this.mode.onEnable();
            }
        }
    }
}