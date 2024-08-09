/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.ui.dropdown.impl;

import com.mojang.blaze3d.matrix.MatrixStack;

public interface IBuilder {
    default public void render(MatrixStack matrixStack, float f, float f2) {
    }

    default public void mouseClick(float f, float f2, int n) {
    }

    default public void charTyped(char c, int n) {
    }

    default public void mouseRelease(float f, float f2, int n) {
    }

    default public void keyPressed(int n, int n2, int n3) {
    }
}

