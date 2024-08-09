/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.realmsclient.gui.screens;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.realmsclient.RealmsMainScreen;
import com.mojang.realmsclient.client.RealmsClient;
import com.mojang.realmsclient.dto.RealmsServer;
import com.mojang.realmsclient.exception.RealmsServiceException;
import com.mojang.realmsclient.gui.screens.RealmsLongRunningMcoTaskScreen;
import java.util.concurrent.locks.ReentrantLock;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.realms.RealmsScreen;
import net.minecraft.realms.action.ConnectingToRealmsAction;
import net.minecraft.util.Util;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TranslationTextComponent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RealmsTermsScreen
extends RealmsScreen {
    private static final Logger field_224722_a = LogManager.getLogger();
    private static final ITextComponent field_243184_b = new TranslationTextComponent("mco.terms.title");
    private static final ITextComponent field_243185_c = new TranslationTextComponent("mco.terms.sentence.1");
    private static final ITextComponent field_243186_p = new StringTextComponent(" ").append(new TranslationTextComponent("mco.terms.sentence.2").mergeStyle(Style.EMPTY.func_244282_c(true)));
    private final Screen field_224723_b;
    private final RealmsMainScreen field_224724_c;
    private final RealmsServer guiScreenServer;
    private boolean field_224727_f;
    private final String field_224728_g = "https://aka.ms/MinecraftRealmsTerms";

    public RealmsTermsScreen(Screen screen, RealmsMainScreen realmsMainScreen, RealmsServer realmsServer) {
        this.field_224723_b = screen;
        this.field_224724_c = realmsMainScreen;
        this.guiScreenServer = realmsServer;
    }

    @Override
    public void init() {
        this.minecraft.keyboardListener.enableRepeatEvents(false);
        int n = this.width / 4 - 2;
        this.addButton(new Button(this.width / 4, RealmsTermsScreen.func_239562_k_(12), n, 20, new TranslationTextComponent("mco.terms.buttons.agree"), this::lambda$init$0));
        this.addButton(new Button(this.width / 2 + 4, RealmsTermsScreen.func_239562_k_(12), n, 20, new TranslationTextComponent("mco.terms.buttons.disagree"), this::lambda$init$1));
    }

    @Override
    public void onClose() {
        this.minecraft.keyboardListener.enableRepeatEvents(true);
    }

    @Override
    public boolean keyPressed(int n, int n2, int n3) {
        if (n == 256) {
            this.minecraft.displayGuiScreen(this.field_224723_b);
            return false;
        }
        return super.keyPressed(n, n2, n3);
    }

    private void func_224721_a() {
        RealmsClient realmsClient = RealmsClient.func_224911_a();
        try {
            realmsClient.func_224937_l();
            this.minecraft.displayGuiScreen(new RealmsLongRunningMcoTaskScreen(this.field_224723_b, new ConnectingToRealmsAction(this.field_224724_c, this.field_224723_b, this.guiScreenServer, new ReentrantLock())));
        } catch (RealmsServiceException realmsServiceException) {
            field_224722_a.error("Couldn't agree to TOS");
        }
    }

    @Override
    public boolean mouseClicked(double d, double d2, int n) {
        if (this.field_224727_f) {
            this.minecraft.keyboardListener.setClipboardString("https://aka.ms/MinecraftRealmsTerms");
            Util.getOSType().openURI("https://aka.ms/MinecraftRealmsTerms");
            return false;
        }
        return super.mouseClicked(d, d2, n);
    }

    @Override
    public String getNarrationMessage() {
        return super.getNarrationMessage() + ". " + field_243185_c.getString() + " " + field_243186_p.getString();
    }

    @Override
    public void render(MatrixStack matrixStack, int n, int n2, float f) {
        this.renderBackground(matrixStack);
        RealmsTermsScreen.drawCenteredString(matrixStack, this.font, field_243184_b, this.width / 2, 17, 0xFFFFFF);
        this.font.func_243248_b(matrixStack, field_243185_c, this.width / 2 - 120, RealmsTermsScreen.func_239562_k_(5), 0xFFFFFF);
        int n3 = this.font.getStringPropertyWidth(field_243185_c);
        int n4 = this.width / 2 - 121 + n3;
        int n5 = RealmsTermsScreen.func_239562_k_(5);
        int n6 = n4 + this.font.getStringPropertyWidth(field_243186_p) + 1;
        int n7 = n5 + 1 + 9;
        this.field_224727_f = n4 <= n && n <= n6 && n5 <= n2 && n2 <= n7;
        this.font.func_243248_b(matrixStack, field_243186_p, this.width / 2 - 120 + n3, RealmsTermsScreen.func_239562_k_(5), this.field_224727_f ? 7107012 : 0x3366BB);
        super.render(matrixStack, n, n2, f);
    }

    private void lambda$init$1(Button button) {
        this.minecraft.displayGuiScreen(this.field_224723_b);
    }

    private void lambda$init$0(Button button) {
        this.func_224721_a();
    }
}

