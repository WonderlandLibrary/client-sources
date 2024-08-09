/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.render;

import com.mojang.blaze3d.platform.GlStateManager;

public class GlBlendState {
    private boolean enabled;
    private int srcFactor;
    private int dstFactor;
    private int srcFactorAlpha;
    private int dstFactorAlpha;

    public GlBlendState() {
        this(false, 1, 0);
    }

    public GlBlendState(boolean bl) {
        this(bl, 1, 0);
    }

    public GlBlendState(boolean bl, int n, int n2, int n3, int n4) {
        this.enabled = bl;
        this.srcFactor = n;
        this.dstFactor = n2;
        this.srcFactorAlpha = n3;
        this.dstFactorAlpha = n4;
    }

    public GlBlendState(boolean bl, int n, int n2) {
        this(bl, n, n2, n, n2);
    }

    public void setState(boolean bl, int n, int n2, int n3, int n4) {
        this.enabled = bl;
        this.srcFactor = n;
        this.dstFactor = n2;
        this.srcFactorAlpha = n3;
        this.dstFactorAlpha = n4;
    }

    public void setState(GlBlendState glBlendState) {
        this.enabled = glBlendState.enabled;
        this.srcFactor = glBlendState.srcFactor;
        this.dstFactor = glBlendState.dstFactor;
        this.srcFactorAlpha = glBlendState.srcFactorAlpha;
        this.dstFactorAlpha = glBlendState.dstFactorAlpha;
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

    public void setFactors(int n, int n2) {
        this.srcFactor = n;
        this.dstFactor = n2;
        this.srcFactorAlpha = n;
        this.dstFactorAlpha = n2;
    }

    public void setFactors(int n, int n2, int n3, int n4) {
        this.srcFactor = n;
        this.dstFactor = n2;
        this.srcFactorAlpha = n3;
        this.dstFactorAlpha = n4;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public int getSrcFactor() {
        return this.srcFactor;
    }

    public int getDstFactor() {
        return this.dstFactor;
    }

    public int getSrcFactorAlpha() {
        return this.srcFactorAlpha;
    }

    public int getDstFactorAlpha() {
        return this.dstFactorAlpha;
    }

    public boolean isSeparate() {
        return this.srcFactor != this.srcFactorAlpha || this.dstFactor != this.dstFactorAlpha;
    }

    public String toString() {
        return "enabled: " + this.enabled + ", src: " + this.srcFactor + ", dst: " + this.dstFactor + ", srcAlpha: " + this.srcFactorAlpha + ", dstAlpha: " + this.dstFactorAlpha;
    }

    public void apply() {
        if (!this.enabled) {
            GlStateManager.disableBlend();
        } else {
            GlStateManager.enableBlend();
            GlStateManager.blendFuncSeparate(this.srcFactor, this.dstFactor, this.srcFactorAlpha, this.dstFactorAlpha);
        }
    }
}

