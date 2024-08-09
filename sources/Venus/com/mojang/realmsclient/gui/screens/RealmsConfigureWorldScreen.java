/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.realmsclient.gui.screens;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.realmsclient.RealmsMainScreen;
import com.mojang.realmsclient.client.RealmsClient;
import com.mojang.realmsclient.dto.RealmsServer;
import com.mojang.realmsclient.dto.RealmsWorldOptions;
import com.mojang.realmsclient.dto.WorldTemplate;
import com.mojang.realmsclient.exception.RealmsServiceException;
import com.mojang.realmsclient.gui.RealmsServerSlotButton;
import com.mojang.realmsclient.gui.screens.NotifableRealmsScreen;
import com.mojang.realmsclient.gui.screens.RealmsBackupScreen;
import com.mojang.realmsclient.gui.screens.RealmsGenericErrorScreen;
import com.mojang.realmsclient.gui.screens.RealmsLongConfirmationScreen;
import com.mojang.realmsclient.gui.screens.RealmsLongRunningMcoTaskScreen;
import com.mojang.realmsclient.gui.screens.RealmsPlayerScreen;
import com.mojang.realmsclient.gui.screens.RealmsResetWorldScreen;
import com.mojang.realmsclient.gui.screens.RealmsSelectWorldTemplateScreen;
import com.mojang.realmsclient.gui.screens.RealmsSettingsScreen;
import com.mojang.realmsclient.gui.screens.RealmsSlotOptionsScreen;
import com.mojang.realmsclient.gui.screens.RealmsSubscriptionInfoScreen;
import javax.annotation.Nullable;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.realms.action.CloseRealmsAction;
import net.minecraft.realms.action.OpeningWorldRealmsAction;
import net.minecraft.realms.action.StartMinigameRealmsAction;
import net.minecraft.realms.action.SwitchMinigameRealmsAction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RealmsConfigureWorldScreen
extends NotifableRealmsScreen {
    private static final Logger field_224413_a = LogManager.getLogger();
    private static final ResourceLocation field_237787_b_ = new ResourceLocation("realms", "textures/gui/realms/on_icon.png");
    private static final ResourceLocation field_237788_c_ = new ResourceLocation("realms", "textures/gui/realms/off_icon.png");
    private static final ResourceLocation field_237789_p_ = new ResourceLocation("realms", "textures/gui/realms/expired_icon.png");
    private static final ResourceLocation field_237790_q_ = new ResourceLocation("realms", "textures/gui/realms/expires_soon_icon.png");
    private static final ITextComponent field_243108_r = new TranslationTextComponent("mco.configure.worlds.title");
    private static final ITextComponent field_243109_s = new TranslationTextComponent("mco.configure.world.title");
    private static final ITextComponent field_243110_t = new TranslationTextComponent("mco.configure.current.minigame").appendString(": ");
    private static final ITextComponent field_243111_u = new TranslationTextComponent("mco.selectServer.expired");
    private static final ITextComponent field_243112_v = new TranslationTextComponent("mco.selectServer.expires.soon");
    private static final ITextComponent field_243113_w = new TranslationTextComponent("mco.selectServer.expires.day");
    private static final ITextComponent field_243114_x = new TranslationTextComponent("mco.selectServer.open");
    private static final ITextComponent field_243115_y = new TranslationTextComponent("mco.selectServer.closed");
    @Nullable
    private ITextComponent field_224414_b;
    private final RealmsMainScreen field_224415_c;
    @Nullable
    private RealmsServer field_224416_d;
    private final long field_224417_e;
    private int field_224418_f;
    private int field_224419_g;
    private Button field_224422_j;
    private Button field_224423_k;
    private Button field_224424_l;
    private Button field_224425_m;
    private Button field_224426_n;
    private Button field_224427_o;
    private Button field_224428_p;
    private boolean field_224429_q;
    private int field_224430_r;
    private int field_224431_s;

    public RealmsConfigureWorldScreen(RealmsMainScreen realmsMainScreen, long l) {
        this.field_224415_c = realmsMainScreen;
        this.field_224417_e = l;
    }

    @Override
    public void init() {
        if (this.field_224416_d == null) {
            this.func_224387_a(this.field_224417_e);
        }
        this.field_224418_f = this.width / 2 - 187;
        this.field_224419_g = this.width / 2 + 190;
        this.minecraft.keyboardListener.enableRepeatEvents(false);
        this.field_224422_j = this.addButton(new Button(this.func_224374_a(0, 3), RealmsConfigureWorldScreen.func_239562_k_(0), 100, 20, new TranslationTextComponent("mco.configure.world.buttons.players"), this::lambda$init$0));
        this.field_224423_k = this.addButton(new Button(this.func_224374_a(1, 3), RealmsConfigureWorldScreen.func_239562_k_(0), 100, 20, new TranslationTextComponent("mco.configure.world.buttons.settings"), this::lambda$init$1));
        this.field_224424_l = this.addButton(new Button(this.func_224374_a(2, 3), RealmsConfigureWorldScreen.func_239562_k_(0), 100, 20, new TranslationTextComponent("mco.configure.world.buttons.subscription"), this::lambda$init$2));
        for (int i = 1; i < 5; ++i) {
            this.func_224402_a(i);
        }
        this.field_224428_p = this.addButton(new Button(this.func_224411_b(0), RealmsConfigureWorldScreen.func_239562_k_(13) - 5, 100, 20, new TranslationTextComponent("mco.configure.world.buttons.switchminigame"), this::lambda$init$3));
        this.field_224425_m = this.addButton(new Button(this.func_224411_b(0), RealmsConfigureWorldScreen.func_239562_k_(13) - 5, 90, 20, new TranslationTextComponent("mco.configure.world.buttons.options"), this::lambda$init$4));
        this.field_224426_n = this.addButton(new Button(this.func_224411_b(1), RealmsConfigureWorldScreen.func_239562_k_(13) - 5, 90, 20, new TranslationTextComponent("mco.configure.world.backup"), this::lambda$init$5));
        this.field_224427_o = this.addButton(new Button(this.func_224411_b(2), RealmsConfigureWorldScreen.func_239562_k_(13) - 5, 90, 20, new TranslationTextComponent("mco.configure.world.buttons.resetworld"), this::lambda$init$8));
        this.addButton(new Button(this.field_224419_g - 80 + 8, RealmsConfigureWorldScreen.func_239562_k_(13) - 5, 70, 20, DialogTexts.GUI_BACK, this::lambda$init$9));
        this.field_224426_n.active = true;
        if (this.field_224416_d == null) {
            this.func_224412_j();
            this.func_224377_h();
            this.field_224422_j.active = false;
            this.field_224423_k.active = false;
            this.field_224424_l.active = false;
        } else {
            this.func_224400_e();
            if (this.func_224376_g()) {
                this.func_224377_h();
            } else {
                this.func_224412_j();
            }
        }
    }

    private void func_224402_a(int n) {
        int n2 = this.func_224368_c(n);
        int n3 = RealmsConfigureWorldScreen.func_239562_k_(5) + 5;
        RealmsServerSlotButton realmsServerSlotButton = new RealmsServerSlotButton(n2, n3, 80, 80, this::lambda$func_224402_a$10, this::lambda$func_224402_a$11, n, arg_0 -> this.lambda$func_224402_a$12(n, arg_0));
        this.addButton(realmsServerSlotButton);
    }

    private int func_224411_b(int n) {
        return this.field_224418_f + n * 95;
    }

    private int func_224374_a(int n, int n2) {
        return this.width / 2 - (n2 * 105 - 5) / 2 + n * 105;
    }

    @Override
    public void tick() {
        super.tick();
        ++this.field_224430_r;
        --this.field_224431_s;
        if (this.field_224431_s < 0) {
            this.field_224431_s = 0;
        }
    }

    @Override
    public void render(MatrixStack matrixStack, int n, int n2, float f) {
        this.field_224414_b = null;
        this.renderBackground(matrixStack);
        RealmsConfigureWorldScreen.drawCenteredString(matrixStack, this.font, field_243108_r, this.width / 2, RealmsConfigureWorldScreen.func_239562_k_(4), 0xFFFFFF);
        super.render(matrixStack, n, n2, f);
        if (this.field_224416_d == null) {
            RealmsConfigureWorldScreen.drawCenteredString(matrixStack, this.font, field_243109_s, this.width / 2, 17, 0xFFFFFF);
        } else {
            String string = this.field_224416_d.func_230775_b_();
            int n3 = this.font.getStringWidth(string);
            int n4 = this.field_224416_d.field_230586_e_ == RealmsServer.Status.CLOSED ? 0xA0A0A0 : 0x7FFF7F;
            int n5 = this.font.getStringPropertyWidth(field_243109_s);
            RealmsConfigureWorldScreen.drawCenteredString(matrixStack, this.font, field_243109_s, this.width / 2, 12, 0xFFFFFF);
            RealmsConfigureWorldScreen.drawCenteredString(matrixStack, this.font, string, this.width / 2, 24, n4);
            int n6 = Math.min(this.func_224374_a(2, 3) + 80 - 11, this.width / 2 + n3 / 2 + n5 / 2 + 10);
            this.func_237807_c_(matrixStack, n6, 7, n, n2);
            if (this.func_224376_g()) {
                this.font.func_243248_b(matrixStack, field_243110_t.deepCopy().appendString(this.field_224416_d.func_230778_c_()), this.field_224418_f + 80 + 20 + 10, RealmsConfigureWorldScreen.func_239562_k_(13), 0xFFFFFF);
            }
            if (this.field_224414_b != null) {
                this.func_237796_a_(matrixStack, this.field_224414_b, n, n2);
            }
        }
    }

    private int func_224368_c(int n) {
        return this.field_224418_f + (n - 1) * 98;
    }

    @Override
    public void onClose() {
        this.minecraft.keyboardListener.enableRepeatEvents(true);
    }

    @Override
    public boolean keyPressed(int n, int n2, int n3) {
        if (n == 256) {
            this.func_224390_d();
            return false;
        }
        return super.keyPressed(n, n2, n3);
    }

    private void func_224390_d() {
        if (this.field_224429_q) {
            this.field_224415_c.func_223978_e();
        }
        this.minecraft.displayGuiScreen(this.field_224415_c);
    }

    private void func_224387_a(long l) {
        new Thread(() -> this.lambda$func_224387_a$14(l)).start();
    }

    private void func_224400_e() {
        this.field_224422_j.active = !this.field_224416_d.field_230591_j_;
        this.field_224423_k.active = !this.field_224416_d.field_230591_j_;
        this.field_224424_l.active = true;
        this.field_224428_p.active = !this.field_224416_d.field_230591_j_;
        this.field_224425_m.active = !this.field_224416_d.field_230591_j_;
        this.field_224427_o.active = !this.field_224416_d.field_230591_j_;
    }

    private void func_224385_a(RealmsServer realmsServer) {
        if (this.field_224416_d.field_230586_e_ == RealmsServer.Status.OPEN) {
            this.field_224415_c.func_223911_a(realmsServer, new RealmsConfigureWorldScreen(this.field_224415_c.func_223942_f(), this.field_224417_e));
        } else {
            this.func_237802_a_(true, new RealmsConfigureWorldScreen(this.field_224415_c.func_223942_f(), this.field_224417_e));
        }
    }

    private void func_224401_f() {
        RealmsSelectWorldTemplateScreen realmsSelectWorldTemplateScreen = new RealmsSelectWorldTemplateScreen(this, RealmsServer.ServerType.MINIGAME);
        realmsSelectWorldTemplateScreen.func_238001_a_(new TranslationTextComponent("mco.template.title.minigame"));
        realmsSelectWorldTemplateScreen.func_238002_a_(new TranslationTextComponent("mco.minigame.world.info.line1"), new TranslationTextComponent("mco.minigame.world.info.line2"));
        this.minecraft.displayGuiScreen(realmsSelectWorldTemplateScreen);
    }

    private void func_224403_a(int n, RealmsServer realmsServer) {
        TranslationTextComponent translationTextComponent = new TranslationTextComponent("mco.configure.world.slot.switch.question.line1");
        TranslationTextComponent translationTextComponent2 = new TranslationTextComponent("mco.configure.world.slot.switch.question.line2");
        this.minecraft.displayGuiScreen(new RealmsLongConfirmationScreen(arg_0 -> this.lambda$func_224403_a$16(realmsServer, n, arg_0), RealmsLongConfirmationScreen.Type.Info, translationTextComponent, translationTextComponent2, true));
    }

    private void func_224388_b(int n, RealmsServer realmsServer) {
        TranslationTextComponent translationTextComponent = new TranslationTextComponent("mco.configure.world.slot.switch.question.line1");
        TranslationTextComponent translationTextComponent2 = new TranslationTextComponent("mco.configure.world.slot.switch.question.line2");
        this.minecraft.displayGuiScreen(new RealmsLongConfirmationScreen(arg_0 -> this.lambda$func_224388_b$19(realmsServer, n, arg_0), RealmsLongConfirmationScreen.Type.Info, translationTextComponent, translationTextComponent2, true));
    }

    protected void func_237796_a_(MatrixStack matrixStack, @Nullable ITextComponent iTextComponent, int n, int n2) {
        int n3 = n + 12;
        int n4 = n2 - 12;
        int n5 = this.font.getStringPropertyWidth(iTextComponent);
        if (n3 + n5 + 3 > this.field_224419_g) {
            n3 = n3 - n5 - 20;
        }
        this.fillGradient(matrixStack, n3 - 3, n4 - 3, n3 + n5 + 3, n4 + 8 + 3, -1073741824, -1073741824);
        this.font.func_243246_a(matrixStack, iTextComponent, n3, n4, 0xFFFFFF);
    }

    private void func_237807_c_(MatrixStack matrixStack, int n, int n2, int n3, int n4) {
        if (this.field_224416_d.field_230591_j_) {
            this.func_237809_d_(matrixStack, n, n2, n3, n4);
        } else if (this.field_224416_d.field_230586_e_ == RealmsServer.Status.CLOSED) {
            this.func_237813_f_(matrixStack, n, n2, n3, n4);
        } else if (this.field_224416_d.field_230586_e_ == RealmsServer.Status.OPEN) {
            if (this.field_224416_d.field_230593_l_ < 7) {
                this.func_237804_b_(matrixStack, n, n2, n3, n4, this.field_224416_d.field_230593_l_);
            } else {
                this.func_237811_e_(matrixStack, n, n2, n3, n4);
            }
        }
    }

    private void func_237809_d_(MatrixStack matrixStack, int n, int n2, int n3, int n4) {
        this.minecraft.getTextureManager().bindTexture(field_237789_p_);
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        RealmsConfigureWorldScreen.blit(matrixStack, n, n2, 0.0f, 0.0f, 10, 28, 10, 28);
        if (n3 >= n && n3 <= n + 9 && n4 >= n2 && n4 <= n2 + 27) {
            this.field_224414_b = field_243111_u;
        }
    }

    private void func_237804_b_(MatrixStack matrixStack, int n, int n2, int n3, int n4, int n5) {
        this.minecraft.getTextureManager().bindTexture(field_237790_q_);
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        if (this.field_224430_r % 20 < 10) {
            RealmsConfigureWorldScreen.blit(matrixStack, n, n2, 0.0f, 0.0f, 10, 28, 20, 28);
        } else {
            RealmsConfigureWorldScreen.blit(matrixStack, n, n2, 10.0f, 0.0f, 10, 28, 20, 28);
        }
        if (n3 >= n && n3 <= n + 9 && n4 >= n2 && n4 <= n2 + 27) {
            this.field_224414_b = n5 <= 0 ? field_243112_v : (n5 == 1 ? field_243113_w : new TranslationTextComponent("mco.selectServer.expires.days", n5));
        }
    }

    private void func_237811_e_(MatrixStack matrixStack, int n, int n2, int n3, int n4) {
        this.minecraft.getTextureManager().bindTexture(field_237787_b_);
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        RealmsConfigureWorldScreen.blit(matrixStack, n, n2, 0.0f, 0.0f, 10, 28, 10, 28);
        if (n3 >= n && n3 <= n + 9 && n4 >= n2 && n4 <= n2 + 27) {
            this.field_224414_b = field_243114_x;
        }
    }

    private void func_237813_f_(MatrixStack matrixStack, int n, int n2, int n3, int n4) {
        this.minecraft.getTextureManager().bindTexture(field_237788_c_);
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        RealmsConfigureWorldScreen.blit(matrixStack, n, n2, 0.0f, 0.0f, 10, 28, 10, 28);
        if (n3 >= n && n3 <= n + 9 && n4 >= n2 && n4 <= n2 + 27) {
            this.field_224414_b = field_243115_y;
        }
    }

    private boolean func_224376_g() {
        return this.field_224416_d != null && this.field_224416_d.field_230594_m_ == RealmsServer.ServerType.MINIGAME;
    }

    private void func_224377_h() {
        this.func_237799_a_(this.field_224425_m);
        this.func_237799_a_(this.field_224426_n);
        this.func_237799_a_(this.field_224427_o);
    }

    private void func_237799_a_(Button button) {
        button.visible = false;
        this.children.remove(button);
        this.buttons.remove(button);
    }

    private void func_237806_b_(Button button) {
        button.visible = true;
        this.addButton(button);
    }

    private void func_224412_j() {
        this.func_237799_a_(this.field_224428_p);
    }

    public void func_224386_a(RealmsWorldOptions realmsWorldOptions) {
        RealmsWorldOptions realmsWorldOptions2 = this.field_224416_d.field_230590_i_.get(this.field_224416_d.field_230595_n_);
        realmsWorldOptions.field_230624_k_ = realmsWorldOptions2.field_230624_k_;
        realmsWorldOptions.field_230625_l_ = realmsWorldOptions2.field_230625_l_;
        RealmsClient realmsClient = RealmsClient.func_224911_a();
        try {
            realmsClient.func_224925_a(this.field_224416_d.field_230582_a_, this.field_224416_d.field_230595_n_, realmsWorldOptions);
            this.field_224416_d.field_230590_i_.put(this.field_224416_d.field_230595_n_, realmsWorldOptions);
        } catch (RealmsServiceException realmsServiceException) {
            field_224413_a.error("Couldn't save slot settings");
            this.minecraft.displayGuiScreen(new RealmsGenericErrorScreen(realmsServiceException, (Screen)this));
            return;
        }
        this.minecraft.displayGuiScreen(this);
    }

    public void func_224410_a(String string, String string2) {
        String string3 = string2.trim().isEmpty() ? null : string2;
        RealmsClient realmsClient = RealmsClient.func_224911_a();
        try {
            realmsClient.func_224922_b(this.field_224416_d.field_230582_a_, string, string3);
            this.field_224416_d.func_230773_a_(string);
            this.field_224416_d.func_230777_b_(string3);
        } catch (RealmsServiceException realmsServiceException) {
            field_224413_a.error("Couldn't save settings");
            this.minecraft.displayGuiScreen(new RealmsGenericErrorScreen(realmsServiceException, (Screen)this));
            return;
        }
        this.minecraft.displayGuiScreen(this);
    }

    public void func_237802_a_(boolean bl, Screen screen) {
        this.minecraft.displayGuiScreen(new RealmsLongRunningMcoTaskScreen(screen, new OpeningWorldRealmsAction(this.field_224416_d, this, this.field_224415_c, bl)));
    }

    public void func_237800_a_(Screen screen) {
        this.minecraft.displayGuiScreen(new RealmsLongRunningMcoTaskScreen(screen, new CloseRealmsAction(this.field_224416_d, this)));
    }

    public void func_224398_a() {
        this.field_224429_q = true;
    }

    @Override
    protected void func_223627_a_(@Nullable WorldTemplate worldTemplate) {
        if (worldTemplate != null && WorldTemplate.Type.MINIGAME == worldTemplate.field_230655_i_) {
            this.minecraft.displayGuiScreen(new RealmsLongRunningMcoTaskScreen(this.field_224415_c, new StartMinigameRealmsAction(this.field_224416_d.field_230582_a_, worldTemplate, this.func_224407_b())));
        }
    }

    public RealmsConfigureWorldScreen func_224407_b() {
        return new RealmsConfigureWorldScreen(this.field_224415_c, this.field_224417_e);
    }

    private void lambda$func_224388_b$19(RealmsServer realmsServer, int n, boolean bl) {
        if (bl) {
            RealmsResetWorldScreen realmsResetWorldScreen = new RealmsResetWorldScreen(this, realmsServer, new TranslationTextComponent("mco.configure.world.switch.slot"), new TranslationTextComponent("mco.configure.world.switch.slot.subtitle"), 0xA0A0A0, DialogTexts.GUI_CANCEL, this::lambda$func_224388_b$17, this::lambda$func_224388_b$18);
            realmsResetWorldScreen.func_224445_b(n);
            realmsResetWorldScreen.func_224432_a(new TranslationTextComponent("mco.create.world.reset.title"));
            this.minecraft.displayGuiScreen(realmsResetWorldScreen);
        } else {
            this.minecraft.displayGuiScreen(this);
        }
    }

    private void lambda$func_224388_b$18() {
        this.minecraft.displayGuiScreen(this.func_224407_b());
    }

    private void lambda$func_224388_b$17() {
        this.minecraft.displayGuiScreen(this.func_224407_b());
    }

    private void lambda$func_224403_a$16(RealmsServer realmsServer, int n, boolean bl) {
        if (bl) {
            this.minecraft.displayGuiScreen(new RealmsLongRunningMcoTaskScreen(this.field_224415_c, new SwitchMinigameRealmsAction(realmsServer.field_230582_a_, n, this::lambda$func_224403_a$15)));
        } else {
            this.minecraft.displayGuiScreen(this);
        }
    }

    private void lambda$func_224403_a$15() {
        this.minecraft.displayGuiScreen(this.func_224407_b());
    }

    private void lambda$func_224387_a$14(long l) {
        RealmsClient realmsClient = RealmsClient.func_224911_a();
        try {
            this.field_224416_d = realmsClient.func_224935_a(l);
            this.func_224400_e();
            if (this.func_224376_g()) {
                this.func_237806_b_(this.field_224428_p);
            } else {
                this.func_237806_b_(this.field_224425_m);
                this.func_237806_b_(this.field_224426_n);
                this.func_237806_b_(this.field_224427_o);
            }
        } catch (RealmsServiceException realmsServiceException) {
            field_224413_a.error("Couldn't get own world");
            this.minecraft.execute(() -> this.lambda$func_224387_a$13(realmsServiceException));
        }
    }

    private void lambda$func_224387_a$13(RealmsServiceException realmsServiceException) {
        this.minecraft.displayGuiScreen(new RealmsGenericErrorScreen(ITextComponent.getTextComponentOrEmpty(realmsServiceException.getMessage()), (Screen)this.field_224415_c));
    }

    private void lambda$func_224402_a$12(int n, Button button) {
        RealmsServerSlotButton.ServerData serverData = ((RealmsServerSlotButton)button).func_237717_a_();
        if (serverData != null) {
            switch (1.$SwitchMap$com$mojang$realmsclient$gui$RealmsServerSlotButton$Action[serverData.field_225116_g.ordinal()]) {
                case 1: {
                    break;
                }
                case 2: {
                    this.func_224385_a(this.field_224416_d);
                    break;
                }
                case 3: {
                    if (serverData.field_225115_f) {
                        this.func_224401_f();
                        break;
                    }
                    if (serverData.field_225114_e) {
                        this.func_224388_b(n, this.field_224416_d);
                        break;
                    }
                    this.func_224403_a(n, this.field_224416_d);
                    break;
                }
                default: {
                    throw new IllegalStateException("Unknown action " + serverData.field_225116_g);
                }
            }
        }
    }

    private void lambda$func_224402_a$11(ITextComponent iTextComponent) {
        this.field_224414_b = iTextComponent;
    }

    private RealmsServer lambda$func_224402_a$10() {
        return this.field_224416_d;
    }

    private void lambda$init$9(Button button) {
        this.func_224390_d();
    }

    private void lambda$init$8(Button button) {
        this.minecraft.displayGuiScreen(new RealmsResetWorldScreen(this, this.field_224416_d.clone(), this::lambda$init$6, this::lambda$init$7));
    }

    private void lambda$init$7() {
        this.minecraft.displayGuiScreen(this.func_224407_b());
    }

    private void lambda$init$6() {
        this.minecraft.displayGuiScreen(this.func_224407_b());
    }

    private void lambda$init$5(Button button) {
        this.minecraft.displayGuiScreen(new RealmsBackupScreen(this, this.field_224416_d.clone(), this.field_224416_d.field_230595_n_));
    }

    private void lambda$init$4(Button button) {
        this.minecraft.displayGuiScreen(new RealmsSlotOptionsScreen(this, this.field_224416_d.field_230590_i_.get(this.field_224416_d.field_230595_n_).clone(), this.field_224416_d.field_230594_m_, this.field_224416_d.field_230595_n_));
    }

    private void lambda$init$3(Button button) {
        RealmsSelectWorldTemplateScreen realmsSelectWorldTemplateScreen = new RealmsSelectWorldTemplateScreen(this, RealmsServer.ServerType.MINIGAME);
        realmsSelectWorldTemplateScreen.func_238001_a_(new TranslationTextComponent("mco.template.title.minigame"));
        this.minecraft.displayGuiScreen(realmsSelectWorldTemplateScreen);
    }

    private void lambda$init$2(Button button) {
        this.minecraft.displayGuiScreen(new RealmsSubscriptionInfoScreen(this, this.field_224416_d.clone(), this.field_224415_c));
    }

    private void lambda$init$1(Button button) {
        this.minecraft.displayGuiScreen(new RealmsSettingsScreen(this, this.field_224416_d.clone()));
    }

    private void lambda$init$0(Button button) {
        this.minecraft.displayGuiScreen(new RealmsPlayerScreen(this, this.field_224416_d));
    }
}

