/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.IBidiRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class DisconnectedScreen
extends Screen {
    private final ITextComponent message;
    private IBidiRenderer field_243289_b = IBidiRenderer.field_243257_a;
    private final Screen nextScreen;
    private int textHeight;

    public DisconnectedScreen(Screen screen, ITextComponent iTextComponent, ITextComponent iTextComponent2) {
        super(iTextComponent);
        this.nextScreen = screen;
        this.message = iTextComponent2;
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return true;
    }

    @Override
    protected void init() {
        this.field_243289_b = IBidiRenderer.func_243258_a(this.font, this.message, this.width - 50);
        this.textHeight = this.field_243289_b.func_241862_a() * 9;
        this.addButton(new Button(this.width / 2 - 100, Math.min(this.height / 2 + this.textHeight / 2 + 9, this.height - 30), 200, 20, new TranslationTextComponent("gui.toMenu"), this::lambda$init$0));
    }

    @Override
    public void render(MatrixStack matrixStack, int n, int n2, float f) {
        this.renderBackground(matrixStack);
        DisconnectedScreen.drawCenteredString(matrixStack, this.font, this.title, this.width / 2, this.height / 2 - this.textHeight / 2 - 18, 0xAAAAAA);
        this.field_243289_b.func_241863_a(matrixStack, this.width / 2, this.height / 2 - this.textHeight / 2);
        super.render(matrixStack, n, n2, f);
    }

    private void lambda$init$0(Button button) {
        this.minecraft.displayGuiScreen(this.nextScreen);
    }
}

