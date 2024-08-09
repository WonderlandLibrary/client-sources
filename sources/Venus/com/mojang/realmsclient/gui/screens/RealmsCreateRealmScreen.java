/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.realmsclient.gui.screens;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.realmsclient.RealmsMainScreen;
import com.mojang.realmsclient.dto.RealmsServer;
import com.mojang.realmsclient.gui.screens.RealmsLongRunningMcoTaskScreen;
import com.mojang.realmsclient.gui.screens.RealmsResetWorldScreen;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.realms.RealmsLabel;
import net.minecraft.realms.RealmsScreen;
import net.minecraft.realms.action.CreateWorldRealmsAction;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class RealmsCreateRealmScreen
extends RealmsScreen {
    private static final ITextComponent field_243116_a = new TranslationTextComponent("mco.configure.world.name");
    private static final ITextComponent field_243117_b = new TranslationTextComponent("mco.configure.world.description");
    private final RealmsServer field_224135_a;
    private final RealmsMainScreen field_224136_b;
    private TextFieldWidget field_224137_c;
    private TextFieldWidget field_224138_d;
    private Button field_224139_e;
    private RealmsLabel field_224140_f;

    public RealmsCreateRealmScreen(RealmsServer realmsServer, RealmsMainScreen realmsMainScreen) {
        this.field_224135_a = realmsServer;
        this.field_224136_b = realmsMainScreen;
    }

    @Override
    public void tick() {
        if (this.field_224137_c != null) {
            this.field_224137_c.tick();
        }
        if (this.field_224138_d != null) {
            this.field_224138_d.tick();
        }
    }

    @Override
    public void init() {
        this.minecraft.keyboardListener.enableRepeatEvents(false);
        this.field_224139_e = this.addButton(new Button(this.width / 2 - 100, this.height / 4 + 120 + 17, 97, 20, new TranslationTextComponent("mco.create.world"), this::lambda$init$0));
        this.addButton(new Button(this.width / 2 + 5, this.height / 4 + 120 + 17, 95, 20, DialogTexts.GUI_CANCEL, this::lambda$init$1));
        this.field_224139_e.active = false;
        this.field_224137_c = new TextFieldWidget(this.minecraft.fontRenderer, this.width / 2 - 100, 65, 200, 20, null, new TranslationTextComponent("mco.configure.world.name"));
        this.addListener(this.field_224137_c);
        this.setFocusedDefault(this.field_224137_c);
        this.field_224138_d = new TextFieldWidget(this.minecraft.fontRenderer, this.width / 2 - 100, 115, 200, 20, null, new TranslationTextComponent("mco.configure.world.description"));
        this.addListener(this.field_224138_d);
        this.field_224140_f = new RealmsLabel(new TranslationTextComponent("mco.selectServer.create"), this.width / 2, 11, 0xFFFFFF);
        this.addListener(this.field_224140_f);
        this.func_231411_u_();
    }

    @Override
    public void onClose() {
        this.minecraft.keyboardListener.enableRepeatEvents(true);
    }

    @Override
    public boolean charTyped(char c, int n) {
        boolean bl = super.charTyped(c, n);
        this.field_224139_e.active = this.func_224133_b();
        return bl;
    }

    @Override
    public boolean keyPressed(int n, int n2, int n3) {
        if (n == 256) {
            this.minecraft.displayGuiScreen(this.field_224136_b);
            return false;
        }
        boolean bl = super.keyPressed(n, n2, n3);
        this.field_224139_e.active = this.func_224133_b();
        return bl;
    }

    private void func_224132_a() {
        if (this.func_224133_b()) {
            RealmsResetWorldScreen realmsResetWorldScreen = new RealmsResetWorldScreen(this.field_224136_b, this.field_224135_a, new TranslationTextComponent("mco.selectServer.create"), new TranslationTextComponent("mco.create.world.subtitle"), 0xA0A0A0, new TranslationTextComponent("mco.create.world.skip"), this::lambda$func_224132_a$2, this::lambda$func_224132_a$3);
            realmsResetWorldScreen.func_224432_a(new TranslationTextComponent("mco.create.world.reset.title"));
            this.minecraft.displayGuiScreen(new RealmsLongRunningMcoTaskScreen(this.field_224136_b, new CreateWorldRealmsAction(this.field_224135_a.field_230582_a_, this.field_224137_c.getText(), this.field_224138_d.getText(), realmsResetWorldScreen)));
        }
    }

    private boolean func_224133_b() {
        return !this.field_224137_c.getText().trim().isEmpty();
    }

    @Override
    public void render(MatrixStack matrixStack, int n, int n2, float f) {
        this.renderBackground(matrixStack);
        this.field_224140_f.func_239560_a_(this, matrixStack);
        this.font.func_243248_b(matrixStack, field_243116_a, this.width / 2 - 100, 52.0f, 0xA0A0A0);
        this.font.func_243248_b(matrixStack, field_243117_b, this.width / 2 - 100, 102.0f, 0xA0A0A0);
        if (this.field_224137_c != null) {
            this.field_224137_c.render(matrixStack, n, n2, f);
        }
        if (this.field_224138_d != null) {
            this.field_224138_d.render(matrixStack, n, n2, f);
        }
        super.render(matrixStack, n, n2, f);
    }

    private void lambda$func_224132_a$3() {
        this.minecraft.displayGuiScreen(this.field_224136_b.func_223942_f());
    }

    private void lambda$func_224132_a$2() {
        this.minecraft.displayGuiScreen(this.field_224136_b.func_223942_f());
    }

    private void lambda$init$1(Button button) {
        this.minecraft.displayGuiScreen(this.field_224136_b);
    }

    private void lambda$init$0(Button button) {
        this.func_224132_a();
    }
}

