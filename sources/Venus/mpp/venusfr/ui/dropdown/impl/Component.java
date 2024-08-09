/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.ui.dropdown.impl;

import mpp.venusfr.ui.dropdown.Panel;
import mpp.venusfr.ui.dropdown.impl.IBuilder;

public class Component
implements IBuilder {
    private float x;
    private float y;
    private float width;
    private float height;
    private Panel panel;

    public boolean isHovered(float f, float f2) {
        return f >= this.x && f <= this.x + this.width && f2 >= this.y && f2 <= this.y + this.height;
    }

    public boolean isHovered(float f, float f2, float f3) {
        return f >= this.x && f <= this.x + this.width && f2 >= this.y && f2 <= this.y + f3;
    }

    public boolean isVisible() {
        return false;
    }

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    public float getWidth() {
        return this.width;
    }

    public float getHeight() {
        return this.height;
    }

    public Panel getPanel() {
        return this.panel;
    }

    public void setX(float f) {
        this.x = f;
    }

    public void setY(float f) {
        this.y = f;
    }

    public void setWidth(float f) {
        this.width = f;
    }

    public void setHeight(float f) {
        this.height = f;
    }

    public void setPanel(Panel panel) {
        this.panel = panel;
    }
}

