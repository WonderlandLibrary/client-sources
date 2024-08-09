/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.blaze3d.vertex;

import com.mojang.blaze3d.vertex.IVertexBuilder;

public abstract class DefaultColorVertexBuilder
implements IVertexBuilder {
    protected boolean defaultColor = false;
    protected int defaultRed = 255;
    protected int defaultGreen = 255;
    protected int defaultBlue = 255;
    protected int defaultAlpha = 255;

    public void setDefaultColor(int n, int n2, int n3, int n4) {
        this.defaultRed = n;
        this.defaultGreen = n2;
        this.defaultBlue = n3;
        this.defaultAlpha = n4;
        this.defaultColor = true;
    }
}

