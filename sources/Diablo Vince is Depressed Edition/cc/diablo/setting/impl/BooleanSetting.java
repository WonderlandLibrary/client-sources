/*
 * Decompiled with CFR 0.152.
 */
package cc.diablo.setting.impl;

import cc.diablo.setting.Setting;

public class BooleanSetting
extends Setting {
    public boolean checked;

    public BooleanSetting(String name, boolean defaultChecked) {
        this.name = name;
        this.checked = defaultChecked;
    }

    public void toggle() {
        this.checked = !this.checked;
    }

    public boolean isChecked() {
        return this.checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}

