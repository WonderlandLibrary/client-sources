/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.realmsclient.gui.screens;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.realmsclient.RealmsMainScreen;
import com.mojang.realmsclient.client.RealmsClient;
import com.mojang.realmsclient.dto.RealmsServer;
import com.mojang.realmsclient.dto.RealmsWorldOptions;
import com.mojang.realmsclient.dto.WorldDownload;
import com.mojang.realmsclient.exception.RealmsServiceException;
import com.mojang.realmsclient.gui.RealmsServerSlotButton;
import com.mojang.realmsclient.gui.screens.RealmsDownloadLatestWorldScreen;
import com.mojang.realmsclient.gui.screens.RealmsGenericErrorScreen;
import com.mojang.realmsclient.gui.screens.RealmsLongConfirmationScreen;
import com.mojang.realmsclient.gui.screens.RealmsLongRunningMcoTaskScreen;
import com.mojang.realmsclient.gui.screens.RealmsResetWorldScreen;
import com.mojang.realmsclient.util.RealmsTextureManager;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.realms.RealmsNarratorHelper;
import net.minecraft.realms.RealmsScreen;
import net.minecraft.realms.action.OpeningWorldRealmsAction;
import net.minecraft.realms.action.SwitchMinigameRealmsAction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RealmsBrokenWorldScreen
extends RealmsScreen {
    private static final Logger field_224071_a = LogManager.getLogger();
    private final Screen field_224072_b;
    private final RealmsMainScreen field_224073_c;
    private RealmsServer field_224074_d;
    private final long field_224075_e;
    private final ITextComponent field_237769_r_;
    private final ITextComponent[] field_224077_g = new ITextComponent[]{new TranslationTextComponent("mco.brokenworld.message.line1"), new TranslationTextComponent("mco.brokenworld.message.line2")};
    private int field_224078_h;
    private int field_224079_i;
    private final List<Integer> field_224086_p = Lists.newArrayList();
    private int field_224087_q;

    public RealmsBrokenWorldScreen(Screen screen, RealmsMainScreen realmsMainScreen, long l, boolean bl) {
        this.field_224072_b = screen;
        this.field_224073_c = realmsMainScreen;
        this.field_224075_e = l;
        this.field_237769_r_ = bl ? new TranslationTextComponent("mco.brokenworld.minigame.title") : new TranslationTextComponent("mco.brokenworld.title");
    }

    @Override
    public void init() {
        this.field_224078_h = this.width / 2 - 150;
        this.field_224079_i = this.width / 2 + 190;
        this.addButton(new Button(this.field_224079_i - 80 + 8, RealmsBrokenWorldScreen.func_239562_k_(13) - 5, 70, 20, DialogTexts.GUI_BACK, this::lambda$init$0));
        if (this.field_224074_d == null) {
            this.func_224068_a(this.field_224075_e);
        } else {
            this.func_224058_a();
        }
        this.minecraft.keyboardListener.enableRepeatEvents(false);
        RealmsNarratorHelper.func_239550_a_(Stream.concat(Stream.of(this.field_237769_r_), Stream.of(this.field_224077_g)).map(ITextComponent::getString).collect(Collectors.joining(" ")));
    }

    private void func_224058_a() {
        for (Map.Entry<Integer, RealmsWorldOptions> entry : this.field_224074_d.field_230590_i_.entrySet()) {
            int n = entry.getKey();
            boolean bl = n != this.field_224074_d.field_230595_n_ || this.field_224074_d.field_230594_m_ == RealmsServer.ServerType.MINIGAME;
            Button button = bl ? new Button(this.func_224065_a(n), RealmsBrokenWorldScreen.func_239562_k_(8), 80, 20, new TranslationTextComponent("mco.brokenworld.play"), arg_0 -> this.lambda$func_224058_a$2(n, arg_0)) : new Button(this.func_224065_a(n), RealmsBrokenWorldScreen.func_239562_k_(8), 80, 20, new TranslationTextComponent("mco.brokenworld.download"), arg_0 -> this.lambda$func_224058_a$4(n, arg_0));
            if (this.field_224086_p.contains(n)) {
                button.active = false;
                button.setMessage(new TranslationTextComponent("mco.brokenworld.downloaded"));
            }
            this.addButton(button);
            this.addButton(new Button(this.func_224065_a(n), RealmsBrokenWorldScreen.func_239562_k_(10), 80, 20, new TranslationTextComponent("mco.brokenworld.reset"), arg_0 -> this.lambda$func_224058_a$6(n, arg_0)));
        }
    }

    @Override
    public void tick() {
        ++this.field_224087_q;
    }

    @Override
    public void render(MatrixStack matrixStack, int n, int n2, float f) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, n, n2, f);
        RealmsBrokenWorldScreen.drawCenteredString(matrixStack, this.font, this.field_237769_r_, this.width / 2, 17, 0xFFFFFF);
        for (int i = 0; i < this.field_224077_g.length; ++i) {
            RealmsBrokenWorldScreen.drawCenteredString(matrixStack, this.font, this.field_224077_g[i], this.width / 2, RealmsBrokenWorldScreen.func_239562_k_(-1) + 3 + i * 12, 0xA0A0A0);
        }
        if (this.field_224074_d != null) {
            for (Map.Entry<Integer, RealmsWorldOptions> entry : this.field_224074_d.field_230590_i_.entrySet()) {
                if (entry.getValue().field_230625_l_ != null && entry.getValue().field_230624_k_ != -1L) {
                    this.func_237775_a_(matrixStack, this.func_224065_a(entry.getKey()), RealmsBrokenWorldScreen.func_239562_k_(1) + 5, n, n2, this.field_224074_d.field_230595_n_ == entry.getKey() && !this.func_224069_f(), entry.getValue().func_230787_a_(entry.getKey()), entry.getKey(), entry.getValue().field_230624_k_, entry.getValue().field_230625_l_, entry.getValue().field_230627_n_);
                    continue;
                }
                this.func_237775_a_(matrixStack, this.func_224065_a(entry.getKey()), RealmsBrokenWorldScreen.func_239562_k_(1) + 5, n, n2, this.field_224074_d.field_230595_n_ == entry.getKey() && !this.func_224069_f(), entry.getValue().func_230787_a_(entry.getKey()), entry.getKey(), -1L, null, entry.getValue().field_230627_n_);
            }
        }
    }

    private int func_224065_a(int n) {
        return this.field_224078_h + (n - 1) * 110;
    }

    @Override
    public void onClose() {
        this.minecraft.keyboardListener.enableRepeatEvents(true);
    }

    @Override
    public boolean keyPressed(int n, int n2, int n3) {
        if (n == 256) {
            this.func_224060_e();
            return false;
        }
        return super.keyPressed(n, n2, n3);
    }

    private void func_224060_e() {
        this.minecraft.displayGuiScreen(this.field_224072_b);
    }

    private void func_224068_a(long l) {
        new Thread(() -> this.lambda$func_224068_a$7(l)).start();
    }

    public void func_237772_a_() {
        new Thread(this::lambda$func_237772_a_$10).start();
    }

    private void func_224066_b(int n) {
        RealmsClient realmsClient = RealmsClient.func_224911_a();
        try {
            WorldDownload worldDownload = realmsClient.func_224917_b(this.field_224074_d.field_230582_a_, n);
            RealmsDownloadLatestWorldScreen realmsDownloadLatestWorldScreen = new RealmsDownloadLatestWorldScreen(this, worldDownload, this.field_224074_d.func_237696_a_(n), arg_0 -> this.lambda$func_224066_b$11(n, arg_0));
            this.minecraft.displayGuiScreen(realmsDownloadLatestWorldScreen);
        } catch (RealmsServiceException realmsServiceException) {
            field_224071_a.error("Couldn't download world data");
            this.minecraft.displayGuiScreen(new RealmsGenericErrorScreen(realmsServiceException, (Screen)this));
        }
    }

    private boolean func_224069_f() {
        return this.field_224074_d != null && this.field_224074_d.field_230594_m_ == RealmsServer.ServerType.MINIGAME;
    }

    private void func_237775_a_(MatrixStack matrixStack, int n, int n2, int n3, int n4, boolean bl, String string, int n5, long l, String string2, boolean bl2) {
        if (bl2) {
            this.minecraft.getTextureManager().bindTexture(RealmsServerSlotButton.field_237713_b_);
        } else if (string2 != null && l != -1L) {
            RealmsTextureManager.func_225202_a(String.valueOf(l), string2);
        } else if (n5 == 1) {
            this.minecraft.getTextureManager().bindTexture(RealmsServerSlotButton.field_237714_c_);
        } else if (n5 == 2) {
            this.minecraft.getTextureManager().bindTexture(RealmsServerSlotButton.field_237715_d_);
        } else if (n5 == 3) {
            this.minecraft.getTextureManager().bindTexture(RealmsServerSlotButton.field_237716_e_);
        } else {
            RealmsTextureManager.func_225202_a(String.valueOf(this.field_224074_d.field_230597_p_), this.field_224074_d.field_230598_q_);
        }
        if (!bl) {
            RenderSystem.color4f(0.56f, 0.56f, 0.56f, 1.0f);
        } else if (bl) {
            float f = 0.9f + 0.1f * MathHelper.cos((float)this.field_224087_q * 0.2f);
            RenderSystem.color4f(f, f, f, 1.0f);
        }
        RealmsBrokenWorldScreen.blit(matrixStack, n + 3, n2 + 3, 0.0f, 0.0f, 74, 74, 74, 74);
        this.minecraft.getTextureManager().bindTexture(RealmsServerSlotButton.field_237712_a_);
        if (bl) {
            RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        } else {
            RenderSystem.color4f(0.56f, 0.56f, 0.56f, 1.0f);
        }
        RealmsBrokenWorldScreen.blit(matrixStack, n, n2, 0.0f, 0.0f, 80, 80, 80, 80);
        RealmsBrokenWorldScreen.drawCenteredString(matrixStack, this.font, string, n + 40, n2 + 66, 0xFFFFFF);
    }

    private void lambda$func_224066_b$11(int n, boolean bl) {
        if (bl) {
            this.field_224086_p.add(n);
            this.children.clear();
            this.func_224058_a();
        } else {
            this.minecraft.displayGuiScreen(this);
        }
    }

    private void lambda$func_237772_a_$10() {
        RealmsClient realmsClient = RealmsClient.func_224911_a();
        if (this.field_224074_d.field_230586_e_ == RealmsServer.Status.CLOSED) {
            this.minecraft.execute(this::lambda$func_237772_a_$8);
        } else {
            try {
                this.field_224073_c.func_223942_f().func_223911_a(realmsClient.func_224935_a(this.field_224075_e), this);
            } catch (RealmsServiceException realmsServiceException) {
                field_224071_a.error("Couldn't get own world");
                this.minecraft.execute(this::lambda$func_237772_a_$9);
            }
        }
    }

    private void lambda$func_237772_a_$9() {
        this.minecraft.displayGuiScreen(this.field_224072_b);
    }

    private void lambda$func_237772_a_$8() {
        this.minecraft.displayGuiScreen(new RealmsLongRunningMcoTaskScreen(this, new OpeningWorldRealmsAction(this.field_224074_d, this, this.field_224073_c, true)));
    }

    private void lambda$func_224068_a$7(long l) {
        RealmsClient realmsClient = RealmsClient.func_224911_a();
        try {
            this.field_224074_d = realmsClient.func_224935_a(l);
            this.func_224058_a();
        } catch (RealmsServiceException realmsServiceException) {
            field_224071_a.error("Couldn't get own world");
            this.minecraft.displayGuiScreen(new RealmsGenericErrorScreen(ITextComponent.getTextComponentOrEmpty(realmsServiceException.getMessage()), this.field_224072_b));
        }
    }

    private void lambda$func_224058_a$6(int n, Button button) {
        RealmsResetWorldScreen realmsResetWorldScreen = new RealmsResetWorldScreen(this, this.field_224074_d, this::func_237772_a_, this::lambda$func_224058_a$5);
        if (n != this.field_224074_d.field_230595_n_ || this.field_224074_d.field_230594_m_ == RealmsServer.ServerType.MINIGAME) {
            realmsResetWorldScreen.func_224445_b(n);
        }
        this.minecraft.displayGuiScreen(realmsResetWorldScreen);
    }

    private void lambda$func_224058_a$5() {
        this.minecraft.displayGuiScreen(this);
        this.func_237772_a_();
    }

    private void lambda$func_224058_a$4(int n, Button button) {
        TranslationTextComponent translationTextComponent = new TranslationTextComponent("mco.configure.world.restore.download.question.line1");
        TranslationTextComponent translationTextComponent2 = new TranslationTextComponent("mco.configure.world.restore.download.question.line2");
        this.minecraft.displayGuiScreen(new RealmsLongConfirmationScreen(arg_0 -> this.lambda$func_224058_a$3(n, arg_0), RealmsLongConfirmationScreen.Type.Info, translationTextComponent, translationTextComponent2, true));
    }

    private void lambda$func_224058_a$3(int n, boolean bl) {
        if (bl) {
            this.func_224066_b(n);
        } else {
            this.minecraft.displayGuiScreen(this);
        }
    }

    private void lambda$func_224058_a$2(int n, Button button) {
        if (this.field_224074_d.field_230590_i_.get((Object)Integer.valueOf((int)n)).field_230627_n_) {
            RealmsResetWorldScreen realmsResetWorldScreen = new RealmsResetWorldScreen(this, this.field_224074_d, new TranslationTextComponent("mco.configure.world.switch.slot"), new TranslationTextComponent("mco.configure.world.switch.slot.subtitle"), 0xA0A0A0, DialogTexts.GUI_CANCEL, this::func_237772_a_, this::lambda$func_224058_a$1);
            realmsResetWorldScreen.func_224445_b(n);
            realmsResetWorldScreen.func_224432_a(new TranslationTextComponent("mco.create.world.reset.title"));
            this.minecraft.displayGuiScreen(realmsResetWorldScreen);
        } else {
            this.minecraft.displayGuiScreen(new RealmsLongRunningMcoTaskScreen(this.field_224072_b, new SwitchMinigameRealmsAction(this.field_224074_d.field_230582_a_, n, this::func_237772_a_)));
        }
    }

    private void lambda$func_224058_a$1() {
        this.minecraft.displayGuiScreen(this);
        this.func_237772_a_();
    }

    private void lambda$init$0(Button button) {
        this.func_224060_e();
    }
}

