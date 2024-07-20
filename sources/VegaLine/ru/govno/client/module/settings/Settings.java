/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.module.settings;

import java.util.ArrayList;
import java.util.function.Supplier;
import ru.govno.client.module.Module;

public class Settings {
    private static ArrayList<Settings> settings;
    public String name;
    public String currentMode;
    public float fValue;
    public float fMin;
    public float fMax;
    public boolean bValue;
    public int color;
    public Category category;
    public Module module;
    public String[] modes;
    Supplier<Boolean> visible = () -> true;

    public boolean isVisible() {
        return this.visible.get();
    }

    public void setVisible(Supplier<Boolean> visible) {
        this.visible = visible;
    }

    public Settings(String name, boolean bValue, Module module) {
        this.name = name;
        this.bValue = bValue;
        this.module = module;
        this.category = Category.Boolean;
    }

    public Settings(String name, float fValue, float fMax, float fMin, Module module) {
        this.name = name;
        this.fValue = fValue;
        this.fMax = fMax;
        this.fMin = fMin;
        this.module = module;
        this.category = Category.Float;
    }

    public Settings(String name, String currentMode, Module module, String[] modes) {
        this.name = name;
        this.currentMode = currentMode;
        this.module = module;
        this.modes = modes;
        this.category = Category.String_Massive;
    }

    public Settings(String name, int color, Module module) {
        this.name = name;
        this.module = module;
        this.color = color;
        this.category = Category.Color;
    }

    public Settings(String name, boolean bValue, Module module, Supplier<Boolean> visible) {
        this.name = name;
        this.bValue = bValue;
        this.module = module;
        this.category = Category.Boolean;
        this.visible = visible;
    }

    public Settings(String name, float fValue, float fMax, float fMin, Module module, Supplier<Boolean> visible) {
        this.name = name;
        this.fValue = fValue;
        this.fMax = fMax;
        this.fMin = fMin;
        this.module = module;
        this.category = Category.Float;
        this.visible = visible;
    }

    public Settings(String name, String currentMode, Module module, String[] modes, Supplier<Boolean> visible) {
        this.name = name;
        this.currentMode = currentMode;
        this.module = module;
        this.modes = modes;
        this.category = Category.String_Massive;
        this.visible = visible;
    }

    public Settings(String name, int color, Module module, Supplier<Boolean> visible) {
        this.name = name;
        this.module = module;
        this.color = color;
        this.category = Category.Color;
        this.visible = visible;
    }

    public Category getCategory() {
        return this.category;
    }

    public String getName() {
        return this.name;
    }

    public Settings() {
    }

    public static ArrayList<Settings> getSettings() {
        return settings;
    }

    public Settings getSettingByName(String name) {
        for (Settings set : Settings.getSettings()) {
            if (!set.getName().equalsIgnoreCase(name)) continue;
            return set;
        }
        return null;
    }

    public void getSettingByString(String name) {
        for (Settings set : Settings.getSettings()) {
            set.currentMode = name;
        }
    }

    public ArrayList<Settings> getSettingsByMod(Module mod) {
        ArrayList<Settings> out = new ArrayList<Settings>();
        for (Settings s : Settings.getSettings()) {
            if (s.module != mod) continue;
            out.add(s);
        }
        if (out.isEmpty()) {
            return null;
        }
        return out;
    }

    public boolean getBool() {
        return this != null && this.bValue;
    }

    public float getFloat() {
        return this == null ? 0.0f : this.fValue;
    }

    public String getMode() {
        return this == null ? "" : this.currentMode;
    }

    public int getCol() {
        return this == null ? 0 : this.color;
    }

    public void setBool(boolean value) {
        this.bValue = value;
    }

    public void setFloat(float value) {
        this.fValue = value;
    }

    public void setMode(String value) {
        this.currentMode = value;
    }

    public void setCol(int value) {
        this.color = value;
    }

    public static enum Category {
        Boolean,
        Float,
        String_Massive,
        Color;

    }
}

