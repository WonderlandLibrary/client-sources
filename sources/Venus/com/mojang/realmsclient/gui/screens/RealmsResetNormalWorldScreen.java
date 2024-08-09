/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.realmsclient.gui.screens;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.realmsclient.gui.screens.RealmsResetWorldScreen;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.realms.RealmsLabel;
import net.minecraft.realms.RealmsScreen;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class RealmsResetNormalWorldScreen
extends RealmsScreen {
    private static final ITextComponent field_243144_a = new TranslationTextComponent("mco.reset.world.seed");
    private static final ITextComponent[] field_243145_b = new ITextComponent[]{new TranslationTextComponent("generator.default"), new TranslationTextComponent("generator.flat"), new TranslationTextComponent("generator.large_biomes"), new TranslationTextComponent("generator.amplified")};
    private final RealmsResetWorldScreen field_224354_b;
    private RealmsLabel field_224355_c;
    private TextFieldWidget field_224356_d;
    private Boolean field_224357_e = true;
    private Integer field_224358_f = 0;
    private ITextComponent field_224365_m;

    public RealmsResetNormalWorldScreen(RealmsResetWorldScreen realmsResetWorldScreen, ITextComponent iTextComponent) {
        this.field_224354_b = realmsResetWorldScreen;
        this.field_224365_m = iTextComponent;
    }

    @Override
    public void tick() {
        this.field_224356_d.tick();
        super.tick();
    }

    @Override
    public void init() {
        this.minecraft.keyboardListener.enableRepeatEvents(false);
        this.field_224355_c = new RealmsLabel(new TranslationTextComponent("mco.reset.world.generate"), this.width / 2, 17, 0xFFFFFF);
        this.addListener(this.field_224355_c);
        this.field_224356_d = new TextFieldWidget(this.minecraft.fontRenderer, this.width / 2 - 100, RealmsResetNormalWorldScreen.func_239562_k_(2), 200, 20, null, new TranslationTextComponent("mco.reset.world.seed"));
        this.field_224356_d.setMaxStringLength(32);
        this.addListener(this.field_224356_d);
        this.setFocusedDefault(this.field_224356_d);
        this.addButton(new Button(this.width / 2 - 102, RealmsResetNormalWorldScreen.func_239562_k_(4), 205, 20, this.func_237937_g_(), this::lambda$init$0));
        this.addButton(new Button(this.width / 2 - 102, RealmsResetNormalWorldScreen.func_239562_k_(6) - 2, 205, 20, this.func_237938_j_(), this::lambda$init$1));
        this.addButton(new Button(this.width / 2 - 102, RealmsResetNormalWorldScreen.func_239562_k_(12), 97, 20, this.field_224365_m, this::lambda$init$2));
        this.addButton(new Button(this.width / 2 + 8, RealmsResetNormalWorldScreen.func_239562_k_(12), 97, 20, DialogTexts.GUI_BACK, this::lambda$init$3));
        this.func_231411_u_();
    }

    @Override
    public void onClose() {
        this.minecraft.keyboardListener.enableRepeatEvents(true);
    }

    @Override
    public boolean keyPressed(int n, int n2, int n3) {
        if (n == 256) {
            this.minecraft.displayGuiScreen(this.field_224354_b);
            return false;
        }
        return super.keyPressed(n, n2, n3);
    }

    @Override
    public void render(MatrixStack matrixStack, int n, int n2, float f) {
        this.renderBackground(matrixStack);
        this.field_224355_c.func_239560_a_(this, matrixStack);
        this.font.func_243248_b(matrixStack, field_243144_a, this.width / 2 - 100, RealmsResetNormalWorldScreen.func_239562_k_(1), 0xA0A0A0);
        this.field_224356_d.render(matrixStack, n, n2, f);
        super.render(matrixStack, n, n2, f);
    }

    private ITextComponent func_237937_g_() {
        return new TranslationTextComponent("selectWorld.mapType").appendString(" ").append(field_243145_b[this.field_224358_f]);
    }

    private ITextComponent func_237938_j_() {
        return DialogTexts.getComposedOptionMessage(new TranslationTextComponent("selectWorld.mapFeatures"), this.field_224357_e);
    }

    private void lambda$init$3(Button button) {
        this.minecraft.displayGuiScreen(this.field_224354_b);
    }

    private void lambda$init$2(Button button) {
        this.field_224354_b.func_224438_a(new RealmsResetWorldScreen.ResetWorldInfo(this.field_224356_d.getText(), this.field_224358_f, this.field_224357_e));
    }

    private void lambda$init$1(Button button) {
        this.field_224357_e = this.field_224357_e == false;
        button.setMessage(this.func_237938_j_());
    }

    private void lambda$init$0(Button button) {
        this.field_224358_f = (this.field_224358_f + 1) % field_243145_b.length;
        button.setMessage(this.func_237937_g_());
    }
}

