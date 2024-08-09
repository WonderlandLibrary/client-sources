/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.render;

public class GlCullState {
    private boolean enabled;
    private int mode;

    public GlCullState() {
        this(false, 1029);
    }

    public GlCullState(boolean bl) {
        this(bl, 1029);
    }

    public GlCullState(boolean bl, int n) {
        this.enabled = bl;
        this.mode = n;
    }

    public void setState(boolean bl, int n) {
        this.enabled = bl;
        this.mode = n;
    }

    public void setState(GlCullState glCullState) {
        this.enabled = glCullState.enabled;
        this.mode = glCullState.mode;
    }

    public void setMode(int n) {
        this.mode = n;
    }

    public void setEnabled(boolean bl) {
        this.enabled = bl;
    }

    public void setEnabled() {
        this.enabled = true;
    }

    public void setDisabled() {
        this.enabled = false;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public int getMode() {
        return this.mode;
    }

    public String toString() {
        return "enabled: " + this.enabled + ", mode: " + this.mode;
    }
}

