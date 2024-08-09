/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.IBidiRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.TranslationTextComponent;

public class DatapackFailureScreen
extends Screen {
    private IBidiRenderer field_243284_a = IBidiRenderer.field_243257_a;
    private final Runnable field_238620_b_;

    public DatapackFailureScreen(Runnable runnable) {
        super(new TranslationTextComponent("datapackFailure.title"));
        this.field_238620_b_ = runnable;
    }

    @Override
    protected void init() {
        super.init();
        this.field_243284_a = IBidiRenderer.func_243258_a(this.font, this.getTitle(), this.width - 50);
        this.addButton(new Button(this.width / 2 - 155, this.height / 6 + 96, 150, 20, new TranslationTextComponent("datapackFailure.safeMode"), this::lambda$init$0));
        this.addButton(new Button(this.width / 2 - 155 + 160, this.height / 6 + 96, 150, 20, new TranslationTextComponent("gui.toTitle"), this::lambda$init$1));
    }

    @Override
    public void render(MatrixStack matrixStack, int n, int n2, float f) {
        this.renderBackground(matrixStack);
        this.field_243284_a.func_241863_a(matrixStack, this.width / 2, 70);
        super.render(matrixStack, n, n2, f);
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return true;
    }

    private void lambda$init$1(Button button) {
        this.minecraft.displayGuiScreen(null);
    }

    private void lambda$init$0(Button button) {
        this.field_238620_b_.run();
    }
}

