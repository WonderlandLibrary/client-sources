/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.clickgui;

import ru.govno.client.clickgui.CheckBox;
import ru.govno.client.clickgui.Colors;
import ru.govno.client.clickgui.Comp;
import ru.govno.client.clickgui.Modes;
import ru.govno.client.clickgui.Slider;
import ru.govno.client.module.settings.Settings;

public class Set
extends Comp {
    public Settings setting;

    public Set(Settings setting) {
        this.setting = setting;
    }

    public Modes getHasModes() {
        return this instanceof Modes ? (Modes)this : null;
    }

    public Colors getHasColors() {
        return this instanceof Colors ? (Colors)this : null;
    }

    public Slider getHasSlider() {
        return this instanceof Slider ? (Slider)this : null;
    }

    public CheckBox getHasCheckBox() {
        return this instanceof CheckBox ? (CheckBox)this : null;
    }

    public void onGuiClosed() {
    }
}

