package wtf.diablo.client.setting.impl;

import wtf.diablo.client.setting.api.AbstractSetting;
import wtf.diablo.client.setting.api.IMode;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;

public class ModeSetting<T extends Enum<T> & IMode> extends AbstractSetting<T> {
    private final T[] modes;

    public ModeSetting(final String name, final T value) {
        super(name, value);
        this.modes = value.getDeclaringClass().getEnumConstants();
    }

    public T[] getModes() {
        return this.modes;
    }

    public void cycle(final boolean reverse) {
        final int ordinal = this.getValue().ordinal();
        final int length = this.modes.length;
        final int index = reverse ? (ordinal - 1) : (ordinal + 1);
        this.setValue(this.modes[(index < 0) ? (length - 1) : ((index >= length) ? 0 : index)]);
    }

    @Override
    public T parse(String value) {
        for(final T constant : modes) {
            if(constant.name().equalsIgnoreCase(value)) {
                return constant;
            }
        }

        return this.getValue();
    }
}
