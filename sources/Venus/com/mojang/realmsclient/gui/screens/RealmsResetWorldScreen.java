/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.realmsclient.gui.screens;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.realmsclient.client.RealmsClient;
import com.mojang.realmsclient.dto.RealmsServer;
import com.mojang.realmsclient.dto.WorldTemplate;
import com.mojang.realmsclient.dto.WorldTemplatePaginatedList;
import com.mojang.realmsclient.exception.RealmsServiceException;
import com.mojang.realmsclient.gui.screens.NotifableRealmsScreen;
import com.mojang.realmsclient.gui.screens.RealmsLongRunningMcoTaskScreen;
import com.mojang.realmsclient.gui.screens.RealmsResetNormalWorldScreen;
import com.mojang.realmsclient.gui.screens.RealmsSelectFileToUploadScreen;
import com.mojang.realmsclient.gui.screens.RealmsSelectWorldTemplateScreen;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.realms.RealmsLabel;
import net.minecraft.realms.action.ResetWorldRealmsAction;
import net.minecraft.realms.action.SwitchMinigameRealmsAction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RealmsResetWorldScreen
extends NotifableRealmsScreen {
    private static final Logger field_224456_b = LogManager.getLogger();
    private final Screen field_224457_c;
    private final RealmsServer field_224458_d;
    private RealmsLabel field_224460_f;
    private RealmsLabel field_224461_g;
    private ITextComponent field_224462_h = new TranslationTextComponent("mco.reset.world.title");
    private ITextComponent field_224463_i = new TranslationTextComponent("mco.reset.world.warning");
    private ITextComponent field_224464_j = DialogTexts.GUI_CANCEL;
    private int field_224465_k = 0xFF0000;
    private static final ResourceLocation field_237944_w_ = new ResourceLocation("realms", "textures/gui/realms/slot_frame.png");
    private static final ResourceLocation field_237945_x_ = new ResourceLocation("realms", "textures/gui/realms/upload.png");
    private static final ResourceLocation field_237946_y_ = new ResourceLocation("realms", "textures/gui/realms/adventure.png");
    private static final ResourceLocation field_237947_z_ = new ResourceLocation("realms", "textures/gui/realms/survival_spawn.png");
    private static final ResourceLocation field_237939_A_ = new ResourceLocation("realms", "textures/gui/realms/new_world.png");
    private static final ResourceLocation field_237940_B_ = new ResourceLocation("realms", "textures/gui/realms/experience.png");
    private static final ResourceLocation field_237941_C_ = new ResourceLocation("realms", "textures/gui/realms/inspiration.png");
    private WorldTemplatePaginatedList field_224468_n;
    private WorldTemplatePaginatedList field_224469_o;
    private WorldTemplatePaginatedList field_224470_p;
    private WorldTemplatePaginatedList field_224471_q;
    public int field_224455_a = -1;
    private ResetType field_224472_r = ResetType.NONE;
    private ResetWorldInfo field_224473_s;
    private WorldTemplate field_224474_t;
    @Nullable
    private ITextComponent field_224475_u;
    private final Runnable field_237942_L_;
    private final Runnable field_237943_M_;

    public RealmsResetWorldScreen(Screen screen, RealmsServer realmsServer, Runnable runnable, Runnable runnable2) {
        this.field_224457_c = screen;
        this.field_224458_d = realmsServer;
        this.field_237942_L_ = runnable;
        this.field_237943_M_ = runnable2;
    }

    public RealmsResetWorldScreen(Screen screen, RealmsServer realmsServer, ITextComponent iTextComponent, ITextComponent iTextComponent2, int n, ITextComponent iTextComponent3, Runnable runnable, Runnable runnable2) {
        this(screen, realmsServer, runnable, runnable2);
        this.field_224462_h = iTextComponent;
        this.field_224463_i = iTextComponent2;
        this.field_224465_k = n;
        this.field_224464_j = iTextComponent3;
    }

    public void func_224445_b(int n) {
        this.field_224455_a = n;
    }

    public void func_224432_a(ITextComponent iTextComponent) {
        this.field_224475_u = iTextComponent;
    }

    @Override
    public void init() {
        this.addButton(new Button(this.width / 2 - 40, RealmsResetWorldScreen.func_239562_k_(14) - 10, 80, 20, this.field_224464_j, this::lambda$init$0));
        new Thread(this, "Realms-reset-world-fetcher"){
            final RealmsResetWorldScreen this$0;
            {
                this.this$0 = realmsResetWorldScreen;
                super(string);
            }

            @Override
            public void run() {
                RealmsClient realmsClient = RealmsClient.func_224911_a();
                try {
                    WorldTemplatePaginatedList worldTemplatePaginatedList = realmsClient.func_224930_a(1, 10, RealmsServer.ServerType.NORMAL);
                    WorldTemplatePaginatedList worldTemplatePaginatedList2 = realmsClient.func_224930_a(1, 10, RealmsServer.ServerType.ADVENTUREMAP);
                    WorldTemplatePaginatedList worldTemplatePaginatedList3 = realmsClient.func_224930_a(1, 10, RealmsServer.ServerType.EXPERIENCE);
                    WorldTemplatePaginatedList worldTemplatePaginatedList4 = realmsClient.func_224930_a(1, 10, RealmsServer.ServerType.INSPIRATION);
                    RealmsResetWorldScreen.access$000(this.this$0).execute(() -> this.lambda$run$0(worldTemplatePaginatedList, worldTemplatePaginatedList2, worldTemplatePaginatedList3, worldTemplatePaginatedList4));
                } catch (RealmsServiceException realmsServiceException) {
                    field_224456_b.error("Couldn't fetch templates in reset world", (Throwable)realmsServiceException);
                }
            }

            private void lambda$run$0(WorldTemplatePaginatedList worldTemplatePaginatedList, WorldTemplatePaginatedList worldTemplatePaginatedList2, WorldTemplatePaginatedList worldTemplatePaginatedList3, WorldTemplatePaginatedList worldTemplatePaginatedList4) {
                this.this$0.field_224468_n = worldTemplatePaginatedList;
                this.this$0.field_224469_o = worldTemplatePaginatedList2;
                this.this$0.field_224470_p = worldTemplatePaginatedList3;
                this.this$0.field_224471_q = worldTemplatePaginatedList4;
            }
        }.start();
        this.field_224460_f = this.addListener(new RealmsLabel(this.field_224462_h, this.width / 2, 7, 0xFFFFFF));
        this.field_224461_g = this.addListener(new RealmsLabel(this.field_224463_i, this.width / 2, 22, this.field_224465_k));
        this.addButton(new TexturedButton(this, this.func_224434_c(1), RealmsResetWorldScreen.func_239562_k_(0) + 10, new TranslationTextComponent("mco.reset.world.generate"), field_237939_A_, this::lambda$init$1));
        this.addButton(new TexturedButton(this, this.func_224434_c(2), RealmsResetWorldScreen.func_239562_k_(0) + 10, new TranslationTextComponent("mco.reset.world.upload"), field_237945_x_, this::lambda$init$2));
        this.addButton(new TexturedButton(this, this.func_224434_c(3), RealmsResetWorldScreen.func_239562_k_(0) + 10, new TranslationTextComponent("mco.reset.world.template"), field_237947_z_, this::lambda$init$3));
        this.addButton(new TexturedButton(this, this.func_224434_c(1), RealmsResetWorldScreen.func_239562_k_(6) + 20, new TranslationTextComponent("mco.reset.world.adventure"), field_237946_y_, this::lambda$init$4));
        this.addButton(new TexturedButton(this, this.func_224434_c(2), RealmsResetWorldScreen.func_239562_k_(6) + 20, new TranslationTextComponent("mco.reset.world.experience"), field_237940_B_, this::lambda$init$5));
        this.addButton(new TexturedButton(this, this.func_224434_c(3), RealmsResetWorldScreen.func_239562_k_(6) + 20, new TranslationTextComponent("mco.reset.world.inspiration"), field_237941_C_, this::lambda$init$6));
        this.func_231411_u_();
    }

    @Override
    public void onClose() {
        this.minecraft.keyboardListener.enableRepeatEvents(true);
    }

    @Override
    public boolean keyPressed(int n, int n2, int n3) {
        if (n == 256) {
            this.minecraft.displayGuiScreen(this.field_224457_c);
            return false;
        }
        return super.keyPressed(n, n2, n3);
    }

    private int func_224434_c(int n) {
        return this.width / 2 - 130 + (n - 1) * 100;
    }

    @Override
    public void render(MatrixStack matrixStack, int n, int n2, float f) {
        this.renderBackground(matrixStack);
        this.field_224460_f.func_239560_a_(this, matrixStack);
        this.field_224461_g.func_239560_a_(this, matrixStack);
        super.render(matrixStack, n, n2, f);
    }

    private void func_237948_a_(MatrixStack matrixStack, int n, int n2, ITextComponent iTextComponent, ResourceLocation resourceLocation, boolean bl, boolean bl2) {
        this.minecraft.getTextureManager().bindTexture(resourceLocation);
        if (bl) {
            RenderSystem.color4f(0.56f, 0.56f, 0.56f, 1.0f);
        } else {
            RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        }
        RealmsResetWorldScreen.blit(matrixStack, n + 2, n2 + 14, 0.0f, 0.0f, 56, 56, 56, 56);
        this.minecraft.getTextureManager().bindTexture(field_237944_w_);
        if (bl) {
            RenderSystem.color4f(0.56f, 0.56f, 0.56f, 1.0f);
        } else {
            RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        }
        RealmsResetWorldScreen.blit(matrixStack, n, n2 + 12, 0.0f, 0.0f, 60, 60, 60, 60);
        int n3 = bl ? 0xA0A0A0 : 0xFFFFFF;
        RealmsResetWorldScreen.drawCenteredString(matrixStack, this.font, iTextComponent, n + 30, n2, n3);
    }

    @Override
    protected void func_223627_a_(@Nullable WorldTemplate worldTemplate) {
        if (worldTemplate != null) {
            if (this.field_224455_a == -1) {
                this.func_224435_b(worldTemplate);
            } else {
                switch (worldTemplate.field_230655_i_) {
                    case WORLD_TEMPLATE: {
                        this.field_224472_r = ResetType.SURVIVAL_SPAWN;
                        break;
                    }
                    case ADVENTUREMAP: {
                        this.field_224472_r = ResetType.ADVENTURE;
                        break;
                    }
                    case EXPERIENCE: {
                        this.field_224472_r = ResetType.EXPERIENCE;
                        break;
                    }
                    case INSPIRATION: {
                        this.field_224472_r = ResetType.INSPIRATION;
                    }
                }
                this.field_224474_t = worldTemplate;
                this.func_224454_b();
            }
        }
    }

    private void func_224454_b() {
        this.func_237952_a_(this::lambda$func_224454_b$7);
    }

    public void func_237952_a_(Runnable runnable) {
        this.minecraft.displayGuiScreen(new RealmsLongRunningMcoTaskScreen(this.field_224457_c, new SwitchMinigameRealmsAction(this.field_224458_d.field_230582_a_, this.field_224455_a, runnable)));
    }

    public void func_224435_b(WorldTemplate worldTemplate) {
        this.func_237953_a_(null, worldTemplate, -1, false);
    }

    private void func_224437_b(ResetWorldInfo resetWorldInfo) {
        this.func_237953_a_(resetWorldInfo.field_225157_a, null, resetWorldInfo.field_225158_b, resetWorldInfo.field_225159_c);
    }

    private void func_237953_a_(@Nullable String string, @Nullable WorldTemplate worldTemplate, int n, boolean bl) {
        this.minecraft.displayGuiScreen(new RealmsLongRunningMcoTaskScreen(this.field_224457_c, new ResetWorldRealmsAction(string, worldTemplate, n, bl, this.field_224458_d.field_230582_a_, this.field_224475_u, this.field_237942_L_)));
    }

    public void func_224438_a(ResetWorldInfo resetWorldInfo) {
        if (this.field_224455_a == -1) {
            this.func_224437_b(resetWorldInfo);
        } else {
            this.field_224472_r = ResetType.GENERATE;
            this.field_224473_s = resetWorldInfo;
            this.func_224454_b();
        }
    }

    private void lambda$func_224454_b$7() {
        switch (this.field_224472_r) {
            case ADVENTURE: 
            case SURVIVAL_SPAWN: 
            case EXPERIENCE: 
            case INSPIRATION: {
                if (this.field_224474_t == null) break;
                this.func_224435_b(this.field_224474_t);
                break;
            }
            case GENERATE: {
                if (this.field_224473_s == null) break;
                this.func_224437_b(this.field_224473_s);
            }
        }
    }

    private void lambda$init$6(Button button) {
        RealmsSelectWorldTemplateScreen realmsSelectWorldTemplateScreen = new RealmsSelectWorldTemplateScreen(this, RealmsServer.ServerType.INSPIRATION, this.field_224471_q);
        realmsSelectWorldTemplateScreen.func_238001_a_(new TranslationTextComponent("mco.reset.world.inspiration"));
        this.minecraft.displayGuiScreen(realmsSelectWorldTemplateScreen);
    }

    private void lambda$init$5(Button button) {
        RealmsSelectWorldTemplateScreen realmsSelectWorldTemplateScreen = new RealmsSelectWorldTemplateScreen(this, RealmsServer.ServerType.EXPERIENCE, this.field_224470_p);
        realmsSelectWorldTemplateScreen.func_238001_a_(new TranslationTextComponent("mco.reset.world.experience"));
        this.minecraft.displayGuiScreen(realmsSelectWorldTemplateScreen);
    }

    private void lambda$init$4(Button button) {
        RealmsSelectWorldTemplateScreen realmsSelectWorldTemplateScreen = new RealmsSelectWorldTemplateScreen(this, RealmsServer.ServerType.ADVENTUREMAP, this.field_224469_o);
        realmsSelectWorldTemplateScreen.func_238001_a_(new TranslationTextComponent("mco.reset.world.adventure"));
        this.minecraft.displayGuiScreen(realmsSelectWorldTemplateScreen);
    }

    private void lambda$init$3(Button button) {
        RealmsSelectWorldTemplateScreen realmsSelectWorldTemplateScreen = new RealmsSelectWorldTemplateScreen(this, RealmsServer.ServerType.NORMAL, this.field_224468_n);
        realmsSelectWorldTemplateScreen.func_238001_a_(new TranslationTextComponent("mco.reset.world.template"));
        this.minecraft.displayGuiScreen(realmsSelectWorldTemplateScreen);
    }

    private void lambda$init$2(Button button) {
        RealmsSelectFileToUploadScreen realmsSelectFileToUploadScreen = new RealmsSelectFileToUploadScreen(this.field_224458_d.field_230582_a_, this.field_224455_a != -1 ? this.field_224455_a : this.field_224458_d.field_230595_n_, this, this.field_237943_M_);
        this.minecraft.displayGuiScreen(realmsSelectFileToUploadScreen);
    }

    private void lambda$init$1(Button button) {
        this.minecraft.displayGuiScreen(new RealmsResetNormalWorldScreen(this, this.field_224462_h));
    }

    private void lambda$init$0(Button button) {
        this.minecraft.displayGuiScreen(this.field_224457_c);
    }

    static Minecraft access$000(RealmsResetWorldScreen realmsResetWorldScreen) {
        return realmsResetWorldScreen.minecraft;
    }

    static enum ResetType {
        NONE,
        GENERATE,
        UPLOAD,
        ADVENTURE,
        SURVIVAL_SPAWN,
        EXPERIENCE,
        INSPIRATION;

    }

    class TexturedButton
    extends Button {
        private final ResourceLocation field_223824_c;
        final RealmsResetWorldScreen this$0;

        public TexturedButton(RealmsResetWorldScreen realmsResetWorldScreen, int n, int n2, ITextComponent iTextComponent, ResourceLocation resourceLocation, Button.IPressable iPressable) {
            this.this$0 = realmsResetWorldScreen;
            super(n, n2, 60, 72, iTextComponent, iPressable);
            this.field_223824_c = resourceLocation;
        }

        @Override
        public void renderButton(MatrixStack matrixStack, int n, int n2, float f) {
            this.this$0.func_237948_a_(matrixStack, this.x, this.y, this.getMessage(), this.field_223824_c, this.isHovered(), this.isMouseOver(n, n2));
        }
    }

    public static class ResetWorldInfo {
        private final String field_225157_a;
        private final int field_225158_b;
        private final boolean field_225159_c;

        public ResetWorldInfo(String string, int n, boolean bl) {
            this.field_225157_a = string;
            this.field_225158_b = n;
            this.field_225159_c = bl;
        }
    }
}

