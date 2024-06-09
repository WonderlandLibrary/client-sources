package dev.vertic.setting.impl;

import dev.vertic.setting.Setting;
import lombok.Getter;
import lombok.Setter;

import java.util.function.BooleanSupplier;

@Getter
@Setter
public class BooleanSetting extends Setting {

    private boolean enabled;

    public BooleanSetting(final String name, final boolean defaultEnabled) {
        super(name);
        this.enabled = defaultEnabled;
    }

    public BooleanSetting(final String name, final BooleanSupplier visibility, final boolean defaultEnabled) {
        super(name, visibility);
        this.enabled = defaultEnabled;
    }

    public void toggle() {
        enabled = !enabled;
    }

}
