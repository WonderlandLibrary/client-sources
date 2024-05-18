/*
 * Decompiled with CFR 0_118.
 */
package me.imfr0zen.guiapi.listeners;

import java.util.ArrayList;
import me.imfr0zen.guiapi.components.GuiComponent;

public abstract class ExtendListener {
    private final ArrayList<GuiComponent> components = new ArrayList();

    protected void add(GuiComponent component) {
        this.components.add(component);
    }

    public void clearComponents() {
        this.components.clear();
    }

    public ArrayList<GuiComponent> getComponents() {
        return this.components;
    }

    public abstract void addComponents();
}

