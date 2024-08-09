/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.realmsclient.gui.screens;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.realms.RealmsScreen;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class RealmsClientOutdatedScreen
extends RealmsScreen {
    private static final ITextComponent field_243104_a = new TranslationTextComponent("mco.client.outdated.title");
    private static final ITextComponent[] field_243105_b = new ITextComponent[]{new TranslationTextComponent("mco.client.outdated.msg.line1"), new TranslationTextComponent("mco.client.outdated.msg.line2")};
    private static final ITextComponent field_243106_c = new TranslationTextComponent("mco.client.incompatible.title");
    private static final ITextComponent[] field_243107_p = new ITextComponent[]{new TranslationTextComponent("mco.client.incompatible.msg.line1"), new TranslationTextComponent("mco.client.incompatible.msg.line2"), new TranslationTextComponent("mco.client.incompatible.msg.line3")};
    private final Screen field_224129_a;
    private final boolean field_224130_b;

    public RealmsClientOutdatedScreen(Screen screen, boolean bl) {
        this.field_224129_a = screen;
        this.field_224130_b = bl;
    }

    @Override
    public void init() {
        this.addButton(new Button(this.width / 2 - 100, RealmsClientOutdatedScreen.func_239562_k_(12), 200, 20, DialogTexts.GUI_BACK, this::lambda$init$0));
    }

    @Override
    public void render(MatrixStack matrixStack, int n, int n2, float f) {
        ITextComponent[] iTextComponentArray;
        ITextComponent iTextComponent;
        this.renderBackground(matrixStack);
        if (this.field_224130_b) {
            iTextComponent = field_243106_c;
            iTextComponentArray = field_243107_p;
        } else {
            iTextComponent = field_243104_a;
            iTextComponentArray = field_243105_b;
        }
        RealmsClientOutdatedScreen.drawCenteredString(matrixStack, this.font, iTextComponent, this.width / 2, RealmsClientOutdatedScreen.func_239562_k_(3), 0xFF0000);
        for (int i = 0; i < iTextComponentArray.length; ++i) {
            RealmsClientOutdatedScreen.drawCenteredString(matrixStack, this.font, iTextComponentArray[i], this.width / 2, RealmsClientOutdatedScreen.func_239562_k_(5) + i * 12, 0xFFFFFF);
        }
        super.render(matrixStack, n, n2, f);
    }

    @Override
    public boolean keyPressed(int n, int n2, int n3) {
        if (n != 257 && n != 335 && n != 256) {
            return super.keyPressed(n, n2, n3);
        }
        this.minecraft.displayGuiScreen(this.field_224129_a);
        return false;
    }

    private void lambda$init$0(Button button) {
        this.minecraft.displayGuiScreen(this.field_224129_a);
    }
}

