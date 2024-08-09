/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.render;

import com.mojang.blaze3d.platform.GlStateManager;

public class GlAlphaState {
    private boolean enabled;
    private int func;
    private float ref;

    public GlAlphaState() {
        this(false, 519, 0.0f);
    }

    public GlAlphaState(boolean bl) {
        this(bl, 519, 0.0f);
    }

    public GlAlphaState(boolean bl, int n, float f) {
        this.enabled = bl;
        this.func = n;
        this.ref = f;
    }

    public void setState(boolean bl, int n, float f) {
        this.enabled = bl;
        this.func = n;
        this.ref = f;
    }

    public void setState(GlAlphaState glAlphaState) {
        this.enabled = glAlphaState.enabled;
        this.func = glAlphaState.func;
        this.ref = glAlphaState.ref;
    }

    public void setFuncRef(int n, float f) {
        this.func = n;
        this.ref = f;
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

    public int getFunc() {
        return this.func;
    }

    public float getRef() {
        return this.ref;
    }

    public void apply() {
        if (!this.enabled) {
            GlStateManager.disableAlphaTest();
        } else {
            GlStateManager.enableAlphaTest();
            GlStateManager.alphaFunc(this.func, this.ref);
        }
    }

    public String toString() {
        return "enabled: " + this.enabled + ", func: " + this.func + ", ref: " + this.ref;
    }
}

