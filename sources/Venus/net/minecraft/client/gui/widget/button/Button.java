/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.widget.button;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.widget.button.AbstractButton;
import net.minecraft.util.text.ITextComponent;

public class Button
extends AbstractButton {
    public static final ITooltip field_238486_s_ = Button::lambda$static$0;
    protected final IPressable onPress;
    protected final ITooltip onTooltip;

    public Button(int n, int n2, int n3, int n4, ITextComponent iTextComponent, IPressable iPressable) {
        this(n, n2, n3, n4, iTextComponent, iPressable, field_238486_s_);
    }

    public Button(int n, int n2, int n3, int n4, ITextComponent iTextComponent, IPressable iPressable, ITooltip iTooltip) {
        super(n, n2, n3, n4, iTextComponent);
        this.onPress = iPressable;
        this.onTooltip = iTooltip;
    }

    @Override
    public void onPress() {
        this.onPress.onPress(this);
    }

    @Override
    public void renderButton(MatrixStack matrixStack, int n, int n2, float f) {
        super.renderButton(matrixStack, n, n2, f);
        if (this.isHovered()) {
            this.renderToolTip(matrixStack, n, n2);
        }
    }

    @Override
    public void renderToolTip(MatrixStack matrixStack, int n, int n2) {
        this.onTooltip.onTooltip(this, matrixStack, n, n2);
    }

    private static void lambda$static$0(Button button, MatrixStack matrixStack, int n, int n2) {
    }

    public static interface ITooltip {
        public void onTooltip(Button var1, MatrixStack var2, int var3, int var4);
    }

    public static interface IPressable {
        public void onPress(Button var1);
    }
}

