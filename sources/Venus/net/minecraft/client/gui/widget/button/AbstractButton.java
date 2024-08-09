/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.widget.button;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.util.text.ITextComponent;

public abstract class AbstractButton
extends Widget {
    public AbstractButton(int n, int n2, int n3, int n4, ITextComponent iTextComponent) {
        super(n, n2, n3, n4, iTextComponent);
    }

    public abstract void onPress();

    @Override
    public void onClick(double d, double d2) {
        this.onPress();
    }

    @Override
    public boolean keyPressed(int n, int n2, int n3) {
        if (this.active && this.visible) {
            if (n != 257 && n != 32 && n != 335) {
                return true;
            }
            this.playDownSound(Minecraft.getInstance().getSoundHandler());
            this.onPress();
            return false;
        }
        return true;
    }
}

