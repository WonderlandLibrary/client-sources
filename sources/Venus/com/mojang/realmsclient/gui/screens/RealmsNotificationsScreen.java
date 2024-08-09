/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.realmsclient.gui.screens;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.realmsclient.client.RealmsClient;
import com.mojang.realmsclient.exception.RealmsServiceException;
import com.mojang.realmsclient.gui.RealmsDataFetcher;
import net.minecraft.client.gui.screen.MainMenuScreen;
import net.minecraft.realms.RealmsScreen;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;

public class RealmsNotificationsScreen
extends RealmsScreen {
    private static final ResourceLocation field_237853_a_ = new ResourceLocation("realms", "textures/gui/realms/invite_icon.png");
    private static final ResourceLocation field_237854_b_ = new ResourceLocation("realms", "textures/gui/realms/trial_icon.png");
    private static final ResourceLocation field_237855_c_ = new ResourceLocation("realms", "textures/gui/realms/news_notification_mainscreen.png");
    private static final RealmsDataFetcher field_237856_p_ = new RealmsDataFetcher();
    private volatile int field_224266_b;
    private static boolean field_224267_c;
    private static boolean field_224268_d;
    private static boolean field_224269_e;
    private static boolean field_224270_f;

    @Override
    public void init() {
        this.func_224261_a();
        this.minecraft.keyboardListener.enableRepeatEvents(false);
    }

    @Override
    public void tick() {
        if (!(this.func_237858_g_() && this.func_237859_j_() && field_224269_e || field_237856_p_.func_225065_a())) {
            field_237856_p_.func_225070_k();
        } else if (field_224269_e && this.func_237858_g_()) {
            field_237856_p_.func_237710_c_();
            if (field_237856_p_.func_225083_a(RealmsDataFetcher.Task.PENDING_INVITE)) {
                this.field_224266_b = field_237856_p_.func_225081_f();
            }
            if (field_237856_p_.func_225083_a(RealmsDataFetcher.Task.TRIAL_AVAILABLE)) {
                field_224268_d = field_237856_p_.func_225071_g();
            }
            if (field_237856_p_.func_225083_a(RealmsDataFetcher.Task.UNREAD_NEWS)) {
                field_224270_f = field_237856_p_.func_225059_i();
            }
            field_237856_p_.func_225072_c();
        }
    }

    private boolean func_237858_g_() {
        return this.minecraft.gameSettings.realmsNotifications;
    }

    private boolean func_237859_j_() {
        return this.minecraft.currentScreen instanceof MainMenuScreen;
    }

    private void func_224261_a() {
        if (!field_224267_c) {
            field_224267_c = true;
            new Thread(this, "Realms Notification Availability checker #1"){
                final RealmsNotificationsScreen this$0;
                {
                    this.this$0 = realmsNotificationsScreen;
                    super(string);
                }

                @Override
                public void run() {
                    RealmsClient realmsClient = RealmsClient.func_224911_a();
                    try {
                        RealmsClient.CompatibleVersionResponse compatibleVersionResponse = realmsClient.func_224939_i();
                        if (compatibleVersionResponse != RealmsClient.CompatibleVersionResponse.COMPATIBLE) {
                            return;
                        }
                    } catch (RealmsServiceException realmsServiceException) {
                        if (realmsServiceException.field_224981_a != 401) {
                            field_224267_c = false;
                        }
                        return;
                    }
                    field_224269_e = true;
                }
            }.start();
        }
    }

    @Override
    public void render(MatrixStack matrixStack, int n, int n2, float f) {
        if (field_224269_e) {
            this.func_237857_a_(matrixStack, n, n2);
        }
        super.render(matrixStack, n, n2, f);
    }

    private void func_237857_a_(MatrixStack matrixStack, int n, int n2) {
        int n3 = this.field_224266_b;
        int n4 = 24;
        int n5 = this.height / 4 + 48;
        int n6 = this.width / 2 + 80;
        int n7 = n5 + 48 + 2;
        int n8 = 0;
        if (field_224270_f) {
            this.minecraft.getTextureManager().bindTexture(field_237855_c_);
            RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
            RenderSystem.pushMatrix();
            RenderSystem.scalef(0.4f, 0.4f, 0.4f);
            RealmsNotificationsScreen.blit(matrixStack, (int)((double)(n6 + 2 - n8) * 2.5), (int)((double)n7 * 2.5), 0.0f, 0.0f, 40, 40, 40, 40);
            RenderSystem.popMatrix();
            n8 += 14;
        }
        if (n3 != 0) {
            this.minecraft.getTextureManager().bindTexture(field_237853_a_);
            RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
            RealmsNotificationsScreen.blit(matrixStack, n6 - n8, n7 - 6, 0.0f, 0.0f, 15, 25, 31, 25);
            n8 += 16;
        }
        if (field_224268_d) {
            this.minecraft.getTextureManager().bindTexture(field_237854_b_);
            RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
            int n9 = 0;
            if ((Util.milliTime() / 800L & 1L) == 1L) {
                n9 = 8;
            }
            RealmsNotificationsScreen.blit(matrixStack, n6 + 4 - n8, n7 + 4, 0.0f, n9, 8, 8, 8, 16);
        }
    }

    @Override
    public void onClose() {
        field_237856_p_.func_225070_k();
    }
}

