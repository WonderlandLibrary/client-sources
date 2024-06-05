package club.mega.module.setting.impl;

import club.mega.module.Module;
import club.mega.module.setting.Setting;

import java.util.function.Supplier;

public class BooleanSetting extends Setting {

    private boolean toggled;

    public BooleanSetting(final String name, final Module parent, final boolean toggled, final boolean configurable, final Supplier<Boolean> visible) {
        super(name, parent, configurable, visible);
        this.toggled = toggled;
    }

    public BooleanSetting(final String name, final Module parent, final boolean toggled, final Supplier<Boolean> visible) {
        this(name, parent, toggled, true, visible);
    }

    public BooleanSetting(final String name, final Module parent, final boolean toggled, final boolean configurable) {
        this(name, parent, toggled, configurable, () -> true);
    }

    public BooleanSetting(final String name, final Module parent, final boolean toggled) {
        this(name, parent, toggled, true);
    }

    public final boolean get() {
        return toggled;
    }

    public final void set(final boolean toggled) {
        this.toggled = toggled;
    }

}
