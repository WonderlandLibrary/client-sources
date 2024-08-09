/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.realmsclient.gui.screens;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.realmsclient.dto.RealmsServer;
import com.mojang.realmsclient.gui.screens.RealmsConfigureWorldScreen;
import com.mojang.realmsclient.gui.screens.RealmsLongConfirmationScreen;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.realms.RealmsLabel;
import net.minecraft.realms.RealmsScreen;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class RealmsSettingsScreen
extends RealmsScreen {
    private static final ITextComponent field_243169_a = new TranslationTextComponent("mco.configure.world.name");
    private static final ITextComponent field_243170_b = new TranslationTextComponent("mco.configure.world.description");
    private final RealmsConfigureWorldScreen field_224565_a;
    private final RealmsServer field_224566_b;
    private Button field_224568_d;
    private TextFieldWidget field_224569_e;
    private TextFieldWidget field_224570_f;
    private RealmsLabel field_224571_g;

    public RealmsSettingsScreen(RealmsConfigureWorldScreen realmsConfigureWorldScreen, RealmsServer realmsServer) {
        this.field_224565_a = realmsConfigureWorldScreen;
        this.field_224566_b = realmsServer;
    }

    @Override
    public void tick() {
        this.field_224570_f.tick();
        this.field_224569_e.tick();
        this.field_224568_d.active = !this.field_224570_f.getText().trim().isEmpty();
    }

    @Override
    public void init() {
        this.minecraft.keyboardListener.enableRepeatEvents(false);
        int n = this.width / 2 - 106;
        this.field_224568_d = this.addButton(new Button(n - 2, RealmsSettingsScreen.func_239562_k_(12), 106, 20, new TranslationTextComponent("mco.configure.world.buttons.done"), this::lambda$init$0));
        this.addButton(new Button(this.width / 2 + 2, RealmsSettingsScreen.func_239562_k_(12), 106, 20, DialogTexts.GUI_CANCEL, this::lambda$init$1));
        String string = this.field_224566_b.field_230586_e_ == RealmsServer.Status.OPEN ? "mco.configure.world.buttons.close" : "mco.configure.world.buttons.open";
        Button button = new Button(this.width / 2 - 53, RealmsSettingsScreen.func_239562_k_(0), 106, 20, new TranslationTextComponent(string), this::lambda$init$3);
        this.addButton(button);
        this.field_224570_f = new TextFieldWidget(this.minecraft.fontRenderer, n, RealmsSettingsScreen.func_239562_k_(4), 212, 20, null, new TranslationTextComponent("mco.configure.world.name"));
        this.field_224570_f.setMaxStringLength(32);
        this.field_224570_f.setText(this.field_224566_b.func_230775_b_());
        this.addListener(this.field_224570_f);
        this.setListenerDefault(this.field_224570_f);
        this.field_224569_e = new TextFieldWidget(this.minecraft.fontRenderer, n, RealmsSettingsScreen.func_239562_k_(8), 212, 20, null, new TranslationTextComponent("mco.configure.world.description"));
        this.field_224569_e.setMaxStringLength(32);
        this.field_224569_e.setText(this.field_224566_b.func_230768_a_());
        this.addListener(this.field_224569_e);
        this.field_224571_g = this.addListener(new RealmsLabel(new TranslationTextComponent("mco.configure.world.settings.title"), this.width / 2, 17, 0xFFFFFF));
        this.func_231411_u_();
    }

    @Override
    public void onClose() {
        this.minecraft.keyboardListener.enableRepeatEvents(true);
    }

    @Override
    public boolean keyPressed(int n, int n2, int n3) {
        if (n == 256) {
            this.minecraft.displayGuiScreen(this.field_224565_a);
            return false;
        }
        return super.keyPressed(n, n2, n3);
    }

    @Override
    public void render(MatrixStack matrixStack, int n, int n2, float f) {
        this.renderBackground(matrixStack);
        this.field_224571_g.func_239560_a_(this, matrixStack);
        this.font.func_243248_b(matrixStack, field_243169_a, this.width / 2 - 106, RealmsSettingsScreen.func_239562_k_(3), 0xA0A0A0);
        this.font.func_243248_b(matrixStack, field_243170_b, this.width / 2 - 106, RealmsSettingsScreen.func_239562_k_(7), 0xA0A0A0);
        this.field_224570_f.render(matrixStack, n, n2, f);
        this.field_224569_e.render(matrixStack, n, n2, f);
        super.render(matrixStack, n, n2, f);
    }

    public void func_224563_a() {
        this.field_224565_a.func_224410_a(this.field_224570_f.getText(), this.field_224569_e.getText());
    }

    private void lambda$init$3(Button button) {
        if (this.field_224566_b.field_230586_e_ == RealmsServer.Status.OPEN) {
            TranslationTextComponent translationTextComponent = new TranslationTextComponent("mco.configure.world.close.question.line1");
            TranslationTextComponent translationTextComponent2 = new TranslationTextComponent("mco.configure.world.close.question.line2");
            this.minecraft.displayGuiScreen(new RealmsLongConfirmationScreen(this::lambda$init$2, RealmsLongConfirmationScreen.Type.Info, translationTextComponent, translationTextComponent2, true));
        } else {
            this.field_224565_a.func_237802_a_(false, this);
        }
    }

    private void lambda$init$2(boolean bl) {
        if (bl) {
            this.field_224565_a.func_237800_a_(this);
        } else {
            this.minecraft.displayGuiScreen(this);
        }
    }

    private void lambda$init$1(Button button) {
        this.minecraft.displayGuiScreen(this.field_224565_a);
    }

    private void lambda$init$0(Button button) {
        this.func_224563_a();
    }
}

