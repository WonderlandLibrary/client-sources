// 
// Decompiled by the rizzer xd
// 

package dev.lvstrng.argon.modules.setting.settings;

import dev.lvstrng.argon.modules.setting.Setting;

public class BooleanSetting extends Setting<BooleanSetting> {
    private boolean value;

    public BooleanSetting(final CharSequence name, final boolean value) {
        super(name);
        this.value = value;
    }

    public void toggle() {
        this.setValue(!this.value);
    }

    public boolean getValue() {
        return this.value;
    }

    public void setValue(final boolean value) {
        this.value = value;
    }
}
