/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.ITextComponent;

public class ErrorScreen
extends Screen {
    private final ITextComponent message;

    public ErrorScreen(ITextComponent iTextComponent, ITextComponent iTextComponent2) {
        super(iTextComponent);
        this.message = iTextComponent2;
    }

    @Override
    protected void init() {
        super.init();
        this.addButton(new Button(this.width / 2 - 100, 140, 200, 20, DialogTexts.GUI_CANCEL, this::lambda$init$0));
    }

    @Override
    public void render(MatrixStack matrixStack, int n, int n2, float f) {
        this.fillGradient(matrixStack, 0, 0, this.width, this.height, -12574688, -11530224);
        ErrorScreen.drawCenteredString(matrixStack, this.font, this.title, this.width / 2, 90, 0xFFFFFF);
        ErrorScreen.drawCenteredString(matrixStack, this.font, this.message, this.width / 2, 110, 0xFFFFFF);
        super.render(matrixStack, n, n2, f);
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return true;
    }

    private void lambda$init$0(Button button) {
        this.minecraft.displayGuiScreen(null);
    }
}

