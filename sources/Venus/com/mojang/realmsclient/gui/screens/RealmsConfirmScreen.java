/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.realmsclient.gui.screens;

import com.mojang.blaze3d.matrix.MatrixStack;
import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.realms.RealmsScreen;
import net.minecraft.util.text.ITextComponent;

public class RealmsConfirmScreen
extends RealmsScreen {
    protected BooleanConsumer field_237824_a_;
    private final ITextComponent field_224142_b;
    private final ITextComponent field_224146_f;
    private int field_224147_g;

    public RealmsConfirmScreen(BooleanConsumer booleanConsumer, ITextComponent iTextComponent, ITextComponent iTextComponent2) {
        this.field_237824_a_ = booleanConsumer;
        this.field_224142_b = iTextComponent;
        this.field_224146_f = iTextComponent2;
    }

    @Override
    public void init() {
        this.addButton(new Button(this.width / 2 - 105, RealmsConfirmScreen.func_239562_k_(9), 100, 20, DialogTexts.GUI_YES, this::lambda$init$0));
        this.addButton(new Button(this.width / 2 + 5, RealmsConfirmScreen.func_239562_k_(9), 100, 20, DialogTexts.GUI_NO, this::lambda$init$1));
    }

    @Override
    public void render(MatrixStack matrixStack, int n, int n2, float f) {
        this.renderBackground(matrixStack);
        RealmsConfirmScreen.drawCenteredString(matrixStack, this.font, this.field_224142_b, this.width / 2, RealmsConfirmScreen.func_239562_k_(3), 0xFFFFFF);
        RealmsConfirmScreen.drawCenteredString(matrixStack, this.font, this.field_224146_f, this.width / 2, RealmsConfirmScreen.func_239562_k_(5), 0xFFFFFF);
        super.render(matrixStack, n, n2, f);
    }

    @Override
    public void tick() {
        super.tick();
        if (--this.field_224147_g == 0) {
            for (Widget widget : this.buttons) {
                widget.active = true;
            }
        }
    }

    private void lambda$init$1(Button button) {
        this.field_237824_a_.accept(false);
    }

    private void lambda$init$0(Button button) {
        this.field_237824_a_.accept(true);
    }
}

