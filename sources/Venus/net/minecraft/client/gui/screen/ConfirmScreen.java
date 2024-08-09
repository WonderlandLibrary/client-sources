/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.IBidiRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.ITextComponent;

public class ConfirmScreen
extends Screen {
    private final ITextComponent messageLine2;
    private IBidiRenderer field_243276_q = IBidiRenderer.field_243257_a;
    protected ITextComponent confirmButtonText;
    protected ITextComponent cancelButtonText;
    private int ticksUntilEnable;
    protected final BooleanConsumer callbackFunction;

    public ConfirmScreen(BooleanConsumer booleanConsumer, ITextComponent iTextComponent, ITextComponent iTextComponent2) {
        this(booleanConsumer, iTextComponent, iTextComponent2, DialogTexts.GUI_YES, DialogTexts.GUI_NO);
    }

    public ConfirmScreen(BooleanConsumer booleanConsumer, ITextComponent iTextComponent, ITextComponent iTextComponent2, ITextComponent iTextComponent3, ITextComponent iTextComponent4) {
        super(iTextComponent);
        this.callbackFunction = booleanConsumer;
        this.messageLine2 = iTextComponent2;
        this.confirmButtonText = iTextComponent3;
        this.cancelButtonText = iTextComponent4;
    }

    @Override
    public String getNarrationMessage() {
        return super.getNarrationMessage() + ". " + this.messageLine2.getString();
    }

    @Override
    protected void init() {
        super.init();
        this.addButton(new Button(this.width / 2 - 155, this.height / 6 + 96, 150, 20, this.confirmButtonText, this::lambda$init$0));
        this.addButton(new Button(this.width / 2 - 155 + 160, this.height / 6 + 96, 150, 20, this.cancelButtonText, this::lambda$init$1));
        this.field_243276_q = IBidiRenderer.func_243258_a(this.font, this.messageLine2, this.width - 50);
    }

    @Override
    public void render(MatrixStack matrixStack, int n, int n2, float f) {
        this.renderBackground(matrixStack);
        ConfirmScreen.drawCenteredString(matrixStack, this.font, this.title, this.width / 2, 70, 0xFFFFFF);
        this.field_243276_q.func_241863_a(matrixStack, this.width / 2, 90);
        super.render(matrixStack, n, n2, f);
    }

    public void setButtonDelay(int n) {
        this.ticksUntilEnable = n;
        for (Widget widget : this.buttons) {
            widget.active = false;
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (--this.ticksUntilEnable == 0) {
            for (Widget widget : this.buttons) {
                widget.active = true;
            }
        }
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return true;
    }

    @Override
    public boolean keyPressed(int n, int n2, int n3) {
        if (n == 256) {
            this.callbackFunction.accept(false);
            return false;
        }
        return super.keyPressed(n, n2, n3);
    }

    private void lambda$init$1(Button button) {
        this.callbackFunction.accept(false);
    }

    private void lambda$init$0(Button button) {
        this.callbackFunction.accept(true);
    }
}

