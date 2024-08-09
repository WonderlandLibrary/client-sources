/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package fun.ellant.ui.ab.render.impl;

import com.mojang.blaze3d.matrix.MatrixStack;
import fun.ellant.ui.ab.render.impl.IComponent;

public abstract class Component
implements IComponent {
    public float x;
    public float y;
    public float width;
    public float height;

    public boolean isHovered(int mouseX, int mouseY, float width, float height) {
        return (float)mouseX >= this.x && (float)mouseY >= this.y && (float)mouseX < this.x + width && (float)mouseY < this.y + height;
    }

    public boolean isHovered(double mouseX, double mouseY, float x, float y, float width, float height) {
        return mouseX >= (double)x && mouseY >= (double)y && mouseX < (double)(x + width) && mouseY < (double)(y + height);
    }

    public boolean isHovered(double mouseX, double mouseY) {
        return mouseX > (double)this.x && mouseX < (double)(this.x + this.width) && mouseY > (double)this.y && mouseY < (double)(this.y + this.height);
    }

    public boolean isHovered(double mouseX, double mouseY, float height) {
        return mouseX > (double)this.x && mouseX < (double)(this.x + this.width) && mouseY > (double)this.y && mouseY < (double)(this.y + height);
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

