package dev.lvstrng.argon.modules.setting.settings;

import dev.lvstrng.argon.modules.setting.Setting;

import java.util.Arrays;
import java.util.List;

public final class EnumSetting extends Setting<EnumSetting> {
    private final List<Enum<?>> types;
    public int mode;

    public EnumSetting(final CharSequence name, final Enum<?> base, final Class<?> type) {
        super(name);
        this.types = Arrays.asList((Enum<?>[]) type.getEnumConstants());
        this.mode = this.types.indexOf(base);
    }

    public Enum<?> current() {
        return this.types.get(this.mode);
    }

    public int getMode() {
        return this.mode;
    }

    public void setMode(final Enum<?> mode) {
        this.mode = this.types.indexOf(mode);
    }

    public void setMode(final int mode) {
        this.mode = mode;
    }

    public void next() {
        if (this.mode < this.types.size() - 1) this.mode++;
        else this.mode = 0;
    }

    public boolean is(final Enum<?> mode) {
        return this.mode == this.types.indexOf(mode);
    }

    public List<Enum<?>> getTypes() {
        return this.types;
    }
}