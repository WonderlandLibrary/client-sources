/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.ui.ab.render.impl;

import com.mojang.blaze3d.matrix.MatrixStack;

public interface IComponent {
    public void render(MatrixStack var1, int var2, int var3);

    public void mouseClicked(double var1, double var3, int var5);

    public void mouseReleased(double var1, double var3, int var5);

    public void mouseScrolled(double var1, double var3, double var5);

    public void keyTyped(int var1, int var2, int var3);

    public void charTyped(char var1, int var2);
}

