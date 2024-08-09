/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.ui.ab.render.impl;

import com.mojang.blaze3d.matrix.MatrixStack;
import mpp.venusfr.ui.ab.render.impl.IComponent;

public abstract class Component
implements IComponent {
    public float x;
    public float y;
    public float width;
    public float height;

    public boolean isHovered(int n, int n2, float f, float f2) {
        return (float)n >= this.x && (float)n2 >= this.y && (float)n < this.x + f && (float)n2 < this.y + f2;
    }

    public boolean isHovered(double d, double d2, float f, float f2, float f3, float f4) {
        return d >= (double)f && d2 >= (double)f2 && d < (double)(f + f3) && d2 < (double)(f2 + f4);
    }

    public boolean isHovered(double d, double d2) {
        return d > (double)this.x && d < (double)(this.x + this.width) && d2 > (double)this.y && d2 < (double)(this.y + this.height);
    }

    public boolean isHovered(double d, double d2, float f) {
        return d > (double)this.x && d < (double)(this.x + this.width) && d2 > (double)this.y && d2 < (double)(this.y + f);
    }

    @Override
    public abstract void mouseScrolled(double var1, double var3, double var5);

    @Override
    public abstract void mouseClicked(double var1, double var3, int var5);

    @Override
    public abstract void mouseReleased(double var1, double var3, int var5);

    @Override
    public abstract void keyTyped(int var1, int var2, int var3);

    @Override
    public abstract void charTyped(char var1, int var2);

    @Override
    public abstract void render(MatrixStack var1, int var2, int var3);
}

