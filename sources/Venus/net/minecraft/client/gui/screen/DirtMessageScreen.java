/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.text.ITextComponent;

public class DirtMessageScreen
extends Screen {
    public DirtMessageScreen(ITextComponent iTextComponent) {
        super(iTextComponent);
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return true;
    }

    @Override
    public void render(MatrixStack matrixStack, int n, int n2, float f) {
        this.renderDirtBackground(0);
        DirtMessageScreen.drawCenteredString(matrixStack, this.font, this.title, this.width / 2, 70, 0xFFFFFF);
        super.render(matrixStack, n, n2, f);
    }
}

