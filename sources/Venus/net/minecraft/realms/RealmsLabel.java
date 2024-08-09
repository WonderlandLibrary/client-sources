/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.realms;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.text.ITextComponent;

public class RealmsLabel
implements IGuiEventListener {
    private final ITextComponent field_230723_a_;
    private final int field_230724_b_;
    private final int field_230725_c_;
    private final int field_230726_d_;

    public RealmsLabel(ITextComponent iTextComponent, int n, int n2, int n3) {
        this.field_230723_a_ = iTextComponent;
        this.field_230724_b_ = n;
        this.field_230725_c_ = n2;
        this.field_230726_d_ = n3;
    }

    public void func_239560_a_(Screen screen, MatrixStack matrixStack) {
        Screen.drawCenteredString(matrixStack, Minecraft.getInstance().fontRenderer, this.field_230723_a_, this.field_230724_b_, this.field_230725_c_, this.field_230726_d_);
    }

    public String func_231399_a_() {
        return this.field_230723_a_.getString();
    }
}

