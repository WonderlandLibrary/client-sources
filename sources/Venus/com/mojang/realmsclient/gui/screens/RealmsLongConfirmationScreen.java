/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.realmsclient.gui.screens;

import com.mojang.blaze3d.matrix.MatrixStack;
import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.realms.RealmsNarratorHelper;
import net.minecraft.realms.RealmsScreen;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class RealmsLongConfirmationScreen
extends RealmsScreen {
    private final Type field_224254_e;
    private final ITextComponent field_224255_f;
    private final ITextComponent field_224256_g;
    protected final BooleanConsumer field_237845_a_;
    private final boolean field_224258_i;

    public RealmsLongConfirmationScreen(BooleanConsumer booleanConsumer, Type type, ITextComponent iTextComponent, ITextComponent iTextComponent2, boolean bl) {
        this.field_237845_a_ = booleanConsumer;
        this.field_224254_e = type;
        this.field_224255_f = iTextComponent;
        this.field_224256_g = iTextComponent2;
        this.field_224258_i = bl;
    }

    @Override
    public void init() {
        RealmsNarratorHelper.func_239551_a_(this.field_224254_e.field_225144_d, this.field_224255_f.getString(), this.field_224256_g.getString());
        if (this.field_224258_i) {
            this.addButton(new Button(this.width / 2 - 105, RealmsLongConfirmationScreen.func_239562_k_(8), 100, 20, DialogTexts.GUI_YES, this::lambda$init$0));
            this.addButton(new Button(this.width / 2 + 5, RealmsLongConfirmationScreen.func_239562_k_(8), 100, 20, DialogTexts.GUI_NO, this::lambda$init$1));
        } else {
            this.addButton(new Button(this.width / 2 - 50, RealmsLongConfirmationScreen.func_239562_k_(8), 100, 20, new TranslationTextComponent("mco.gui.ok"), this::lambda$init$2));
        }
    }

    @Override
    public boolean keyPressed(int n, int n2, int n3) {
        if (n == 256) {
            this.field_237845_a_.accept(false);
            return false;
        }
        return super.keyPressed(n, n2, n3);
    }

    @Override
    public void render(MatrixStack matrixStack, int n, int n2, float f) {
        this.renderBackground(matrixStack);
        RealmsLongConfirmationScreen.drawCenteredString(matrixStack, this.font, this.field_224254_e.field_225144_d, this.width / 2, RealmsLongConfirmationScreen.func_239562_k_(2), this.field_224254_e.field_225143_c);
        RealmsLongConfirmationScreen.drawCenteredString(matrixStack, this.font, this.field_224255_f, this.width / 2, RealmsLongConfirmationScreen.func_239562_k_(4), 0xFFFFFF);
        RealmsLongConfirmationScreen.drawCenteredString(matrixStack, this.font, this.field_224256_g, this.width / 2, RealmsLongConfirmationScreen.func_239562_k_(6), 0xFFFFFF);
        super.render(matrixStack, n, n2, f);
    }

    private void lambda$init$2(Button button) {
        this.field_237845_a_.accept(true);
    }

    private void lambda$init$1(Button button) {
        this.field_237845_a_.accept(false);
    }

    private void lambda$init$0(Button button) {
        this.field_237845_a_.accept(true);
    }

    public static enum Type {
        Warning("Warning!", 0xFF0000),
        Info("Info!", 8226750);

        public final int field_225143_c;
        public final String field_225144_d;

        private Type(String string2, int n2) {
            this.field_225144_d = string2;
            this.field_225143_c = n2;
        }
    }
}

