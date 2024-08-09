/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.IBidiRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.ITextComponent;

public class AlertScreen
extends Screen {
    private final Runnable field_201552_h;
    protected final ITextComponent field_201550_f;
    private IBidiRenderer field_243274_p = IBidiRenderer.field_243257_a;
    protected final ITextComponent field_201551_g;
    private int buttonDelay;

    public AlertScreen(Runnable runnable, ITextComponent iTextComponent, ITextComponent iTextComponent2) {
        this(runnable, iTextComponent, iTextComponent2, DialogTexts.GUI_BACK);
    }

    public AlertScreen(Runnable runnable, ITextComponent iTextComponent, ITextComponent iTextComponent2, ITextComponent iTextComponent3) {
        super(iTextComponent);
        this.field_201552_h = runnable;
        this.field_201550_f = iTextComponent2;
        this.field_201551_g = iTextComponent3;
    }

    @Override
    protected void init() {
        super.init();
        this.addButton(new Button(this.width / 2 - 100, this.height / 6 + 168, 200, 20, this.field_201551_g, this::lambda$init$0));
        this.field_243274_p = IBidiRenderer.func_243258_a(this.font, this.field_201550_f, this.width - 50);
    }

    @Override
    public void render(MatrixStack matrixStack, int n, int n2, float f) {
        this.renderBackground(matrixStack);
        AlertScreen.drawCenteredString(matrixStack, this.font, this.title, this.width / 2, 70, 0xFFFFFF);
        this.field_243274_p.func_241863_a(matrixStack, this.width / 2, 90);
        super.render(matrixStack, n, n2, f);
    }

    @Override
    public void tick() {
        super.tick();
        if (--this.buttonDelay == 0) {
            for (Widget widget : this.buttons) {
                widget.active = true;
            }
        }
    }

    private void lambda$init$0(Button button) {
        this.field_201552_h.run();
    }
}

