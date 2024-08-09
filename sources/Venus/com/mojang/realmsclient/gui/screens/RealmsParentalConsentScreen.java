/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.realmsclient.gui.screens;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.IBidiRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.realms.RealmsNarratorHelper;
import net.minecraft.realms.RealmsScreen;
import net.minecraft.util.Util;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class RealmsParentalConsentScreen
extends RealmsScreen {
    private static final ITextComponent field_243122_a = new TranslationTextComponent("mco.account.privacyinfo");
    private final Screen field_224260_a;
    private IBidiRenderer field_243123_c = IBidiRenderer.field_243257_a;

    public RealmsParentalConsentScreen(Screen screen) {
        this.field_224260_a = screen;
    }

    @Override
    public void init() {
        RealmsNarratorHelper.func_239550_a_(field_243122_a.getString());
        TranslationTextComponent translationTextComponent = new TranslationTextComponent("mco.account.update");
        ITextComponent iTextComponent = DialogTexts.GUI_BACK;
        int n = Math.max(this.font.getStringPropertyWidth(translationTextComponent), this.font.getStringPropertyWidth(iTextComponent)) + 30;
        TranslationTextComponent translationTextComponent2 = new TranslationTextComponent("mco.account.privacy.info");
        int n2 = (int)((double)this.font.getStringPropertyWidth(translationTextComponent2) * 1.2);
        this.addButton(new Button(this.width / 2 - n2 / 2, RealmsParentalConsentScreen.func_239562_k_(11), n2, 20, translationTextComponent2, RealmsParentalConsentScreen::lambda$init$0));
        this.addButton(new Button(this.width / 2 - (n + 5), RealmsParentalConsentScreen.func_239562_k_(13), n, 20, translationTextComponent, RealmsParentalConsentScreen::lambda$init$1));
        this.addButton(new Button(this.width / 2 + 5, RealmsParentalConsentScreen.func_239562_k_(13), n, 20, iTextComponent, this::lambda$init$2));
        this.field_243123_c = IBidiRenderer.func_243258_a(this.font, field_243122_a, (int)Math.round((double)this.width * 0.9));
    }

    @Override
    public void render(MatrixStack matrixStack, int n, int n2, float f) {
        this.renderBackground(matrixStack);
        this.field_243123_c.func_241864_a(matrixStack, this.width / 2, 15, 15, 0xFFFFFF);
        super.render(matrixStack, n, n2, f);
    }

    private void lambda$init$2(Button button) {
        this.minecraft.displayGuiScreen(this.field_224260_a);
    }

    private static void lambda$init$1(Button button) {
        Util.getOSType().openURI("https://aka.ms/UpdateMojangAccount");
    }

    private static void lambda$init$0(Button button) {
        Util.getOSType().openURI("https://aka.ms/MinecraftGDPR");
    }
}

