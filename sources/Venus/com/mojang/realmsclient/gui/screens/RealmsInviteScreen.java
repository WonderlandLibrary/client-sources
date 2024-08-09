/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.realmsclient.gui.screens;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.realmsclient.client.RealmsClient;
import com.mojang.realmsclient.dto.RealmsServer;
import com.mojang.realmsclient.gui.screens.RealmsConfigureWorldScreen;
import com.mojang.realmsclient.gui.screens.RealmsPlayerScreen;
import javax.annotation.Nullable;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.realms.RealmsNarratorHelper;
import net.minecraft.realms.RealmsScreen;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RealmsInviteScreen
extends RealmsScreen {
    private static final Logger field_224213_a = LogManager.getLogger();
    private static final ITextComponent field_243118_b = new TranslationTextComponent("mco.configure.world.invite.profile.name");
    private static final ITextComponent field_243119_c = new TranslationTextComponent("mco.configure.world.players.error");
    private TextFieldWidget field_224214_b;
    private final RealmsServer field_224215_c;
    private final RealmsConfigureWorldScreen field_224216_d;
    private final Screen field_224217_e;
    @Nullable
    private ITextComponent field_224222_j;

    public RealmsInviteScreen(RealmsConfigureWorldScreen realmsConfigureWorldScreen, Screen screen, RealmsServer realmsServer) {
        this.field_224216_d = realmsConfigureWorldScreen;
        this.field_224217_e = screen;
        this.field_224215_c = realmsServer;
    }

    @Override
    public void tick() {
        this.field_224214_b.tick();
    }

    @Override
    public void init() {
        this.minecraft.keyboardListener.enableRepeatEvents(false);
        this.field_224214_b = new TextFieldWidget(this.minecraft.fontRenderer, this.width / 2 - 100, RealmsInviteScreen.func_239562_k_(2), 200, 20, null, new TranslationTextComponent("mco.configure.world.invite.profile.name"));
        this.addListener(this.field_224214_b);
        this.setFocusedDefault(this.field_224214_b);
        this.addButton(new Button(this.width / 2 - 100, RealmsInviteScreen.func_239562_k_(10), 200, 20, new TranslationTextComponent("mco.configure.world.buttons.invite"), this::lambda$init$0));
        this.addButton(new Button(this.width / 2 - 100, RealmsInviteScreen.func_239562_k_(12), 200, 20, DialogTexts.GUI_CANCEL, this::lambda$init$1));
    }

    @Override
    public void onClose() {
        this.minecraft.keyboardListener.enableRepeatEvents(true);
    }

    private void func_224211_a() {
        block5: {
            RealmsClient realmsClient = RealmsClient.func_224911_a();
            if (this.field_224214_b.getText() != null && !this.field_224214_b.getText().isEmpty()) {
                try {
                    RealmsServer realmsServer = realmsClient.func_224910_b(this.field_224215_c.field_230582_a_, this.field_224214_b.getText().trim());
                    if (realmsServer != null) {
                        this.field_224215_c.field_230589_h_ = realmsServer.field_230589_h_;
                        this.minecraft.displayGuiScreen(new RealmsPlayerScreen(this.field_224216_d, this.field_224215_c));
                        break block5;
                    }
                    this.func_224209_a(field_243119_c);
                } catch (Exception exception) {
                    field_224213_a.error("Couldn't invite user");
                    this.func_224209_a(field_243119_c);
                }
            } else {
                this.func_224209_a(field_243119_c);
            }
        }
    }

    private void func_224209_a(ITextComponent iTextComponent) {
        this.field_224222_j = iTextComponent;
        RealmsNarratorHelper.func_239550_a_(iTextComponent.getString());
    }

    @Override
    public boolean keyPressed(int n, int n2, int n3) {
        if (n == 256) {
            this.minecraft.displayGuiScreen(this.field_224217_e);
            return false;
        }
        return super.keyPressed(n, n2, n3);
    }

    @Override
    public void render(MatrixStack matrixStack, int n, int n2, float f) {
        this.renderBackground(matrixStack);
        this.font.func_243248_b(matrixStack, field_243118_b, this.width / 2 - 100, RealmsInviteScreen.func_239562_k_(1), 0xA0A0A0);
        if (this.field_224222_j != null) {
            RealmsInviteScreen.drawCenteredString(matrixStack, this.font, this.field_224222_j, this.width / 2, RealmsInviteScreen.func_239562_k_(5), 0xFF0000);
        }
        this.field_224214_b.render(matrixStack, n, n2, f);
        super.render(matrixStack, n, n2, f);
    }

    private void lambda$init$1(Button button) {
        this.minecraft.displayGuiScreen(this.field_224217_e);
    }

    private void lambda$init$0(Button button) {
        this.func_224211_a();
    }
}

