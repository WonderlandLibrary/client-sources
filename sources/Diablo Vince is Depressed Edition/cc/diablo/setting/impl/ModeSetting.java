/*
 * Decompiled with CFR 0.152.
 */
package cc.diablo.setting.impl;

import cc.diablo.setting.Setting;
import java.util.Arrays;
import java.util.List;

public class ModeSetting
extends Setting {
    public int index;
    public List<String> settings;
    public boolean expanded;
    public String defaultMode;

    public ModeSetting(String name, String defaultMode, String ... modes) {
        this.name = name;
        this.settings = Arrays.asList(modes);
        this.defaultMode = defaultMode;
        this.index = this.settings.indexOf(defaultMode);
        this.expanded = false;
    }

    public String getMode() {
        return this.settings.get(this.index);
    }

    public boolean isMode(String mode) {
        return this.index == this.settings.indexOf(mode);
    }

    public void setMode(String mode) {
        this.index = this.settings.indexOf(mode);
    }

    public boolean getExpanded() {
        return this.expanded;
    }

    public String getDefaultMode() {
        return this.defaultMode;
    }

    public void setExpanded(boolean bool) {
        this.expanded = bool;
    }

    public int getIndex() {
        return this.index;
    }

    public List<String> getSettings() {
        return this.settings;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setSettings(List<String> settings) {
        this.settings = settings;
    }

    public void setDefaultMode(String defaultMode) {
        this.defaultMode = defaultMode;
    }
}

