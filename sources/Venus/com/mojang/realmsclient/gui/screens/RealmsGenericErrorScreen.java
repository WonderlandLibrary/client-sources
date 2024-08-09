/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.realmsclient.gui.screens;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.realmsclient.exception.RealmsServiceException;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.resources.I18n;
import net.minecraft.realms.RealmsNarratorHelper;
import net.minecraft.realms.RealmsScreen;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class RealmsGenericErrorScreen
extends RealmsScreen {
    private final Screen field_224228_a;
    private ITextComponent field_224229_b;
    private ITextComponent field_224230_c;

    public RealmsGenericErrorScreen(RealmsServiceException realmsServiceException, Screen screen) {
        this.field_224228_a = screen;
        this.func_224224_a(realmsServiceException);
    }

    public RealmsGenericErrorScreen(ITextComponent iTextComponent, Screen screen) {
        this.field_224228_a = screen;
        this.func_237841_a_(iTextComponent);
    }

    public RealmsGenericErrorScreen(ITextComponent iTextComponent, ITextComponent iTextComponent2, Screen screen) {
        this.field_224228_a = screen;
        this.func_237842_a_(iTextComponent, iTextComponent2);
    }

    private void func_224224_a(RealmsServiceException realmsServiceException) {
        if (realmsServiceException.field_224983_c == -1) {
            this.field_224229_b = new StringTextComponent("An error occurred (" + realmsServiceException.field_224981_a + "):");
            this.field_224230_c = new StringTextComponent(realmsServiceException.field_224982_b);
        } else {
            this.field_224229_b = new StringTextComponent("Realms (" + realmsServiceException.field_224983_c + "):");
            String string = "mco.errorMessage." + realmsServiceException.field_224983_c;
            this.field_224230_c = I18n.hasKey(string) ? new TranslationTextComponent(string) : ITextComponent.getTextComponentOrEmpty(realmsServiceException.field_224984_d);
        }
    }

    private void func_237841_a_(ITextComponent iTextComponent) {
        this.field_224229_b = new StringTextComponent("An error occurred: ");
        this.field_224230_c = iTextComponent;
    }

    private void func_237842_a_(ITextComponent iTextComponent, ITextComponent iTextComponent2) {
        this.field_224229_b = iTextComponent;
        this.field_224230_c = iTextComponent2;
    }

    @Override
    public void init() {
        RealmsNarratorHelper.func_239550_a_(this.field_224229_b.getString() + ": " + this.field_224230_c.getString());
        this.addButton(new Button(this.width / 2 - 100, this.height - 52, 200, 20, new StringTextComponent("Ok"), this::lambda$init$0));
    }

    @Override
    public void render(MatrixStack matrixStack, int n, int n2, float f) {
        this.renderBackground(matrixStack);
        RealmsGenericErrorScreen.drawCenteredString(matrixStack, this.font, this.field_224229_b, this.width / 2, 80, 0xFFFFFF);
        RealmsGenericErrorScreen.drawCenteredString(matrixStack, this.font, this.field_224230_c, this.width / 2, 100, 0xFF0000);
        super.render(matrixStack, n, n2, f);
    }

    private void lambda$init$0(Button button) {
        this.minecraft.displayGuiScreen(this.field_224228_a);
    }
}

