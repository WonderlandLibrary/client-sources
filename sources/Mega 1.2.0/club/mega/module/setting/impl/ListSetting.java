package club.mega.module.setting.impl;

import club.mega.module.Module;
import club.mega.module.setting.Setting;

import java.util.Arrays;
import java.util.function.Supplier;

public class ListSetting extends Setting {

    private int index;

    private final String[] modes;
    private String current;

    public ListSetting(final String name, final Module parent, final String[] modes, final boolean configurable, final Supplier<Boolean> visible) {
        super(name, parent, configurable, visible);
        this.modes = modes;
        current = Arrays.asList(this.modes).get(0);
    }

    public ListSetting(final String name, final Module parent, final String[] modes, final Supplier<Boolean> visible) {
        this(name, parent, modes, true, visible);
    }

    public ListSetting(final String name, final Module parent, final String[] modes, final boolean configurable) {
        this(name, parent, modes, configurable, () -> true);
    }

    public ListSetting(final String name, final Module parent, final String[] modes) {
        this(name, parent, modes, true, () -> true);
    }

    public final void loopNext() {
        if (index + 1 <= modes.length - 1) {
            index++;
            setCurrent(modes[index]);
        } else {
            setCurrent(modes[0]);
            index = 0;
        }
    }
    public final void loopPrev() {
        if (index - 1 != -1) {
            index--;
            setCurrent(modes[index]);
        } else {
            setCurrent(modes[getModes().length - 1]);
            index = modes.length - 1;
        }
    }

    public final String[] getModes() {
        return modes;
    }

    public final String getCurrent() {
        return current;
    }

    public final void setCurrent(final String current) {
        this.current = current;
    }

    public final boolean is(final String mode) {
        return current.equalsIgnoreCase(mode);
    }

}
