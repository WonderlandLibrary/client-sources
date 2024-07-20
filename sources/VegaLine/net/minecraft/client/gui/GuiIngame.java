/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Ordering;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiBossOverlay;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.client.gui.GuiOverlayDebug;
import net.minecraft.client.gui.GuiPlayerTabOverlay;
import net.minecraft.client.gui.GuiSpectator;
import net.minecraft.client.gui.GuiSubtitleOverlay;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.chat.IChatListener;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.gui.chat.NormalChatListener;
import net.minecraft.client.gui.chat.OverlayChatListener;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAir;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.FoodStats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.border.WorldBorder;
import optifine.Config;
import optifine.CustomColors;
import optifine.CustomItems;
import optifine.Reflector;
import optifine.ReflectorForge;
import optifine.TextureAnimations;
import org.lwjgl.opengl.GL11;
import ru.govno.client.Client;
import ru.govno.client.cfg.GuiConfig;
import ru.govno.client.clickgui.ClickGuiScreen;
import ru.govno.client.event.events.EventRender2D;
import ru.govno.client.module.Module;
import ru.govno.client.module.modules.CaveFinder;
import ru.govno.client.module.modules.ClientColors;
import ru.govno.client.module.modules.ComfortUi;
import ru.govno.client.module.modules.Crosshair;
import ru.govno.client.module.modules.ESP;
import ru.govno.client.module.modules.Hud;
import ru.govno.client.module.modules.NameSecurity;
import ru.govno.client.module.modules.NoRender;
import ru.govno.client.module.modules.ViewModel;
import ru.govno.client.newfont.Fonts;
import ru.govno.client.utils.Command.impl.Panic;
import ru.govno.client.utils.Math.MathUtils;
import ru.govno.client.utils.Math.TimerHelper;
import ru.govno.client.utils.Render.AnimationUtils;
import ru.govno.client.utils.Render.ColorUtils;
import ru.govno.client.utils.Render.RenderUtils;
import ru.govno.client.utils.Utility;

public class GuiIngame
extends Gui {
    private static final ResourceLocation VIGNETTE_TEX_PATH = new ResourceLocation("textures/misc/vignette.png");
    public static final ResourceLocation WIDGETS_TEX_PATH = new ResourceLocation("textures/gui/widgets.png");
    private static final ResourceLocation PUMPKIN_BLUR_TEX_PATH = new ResourceLocation("textures/misc/pumpkinblur.png");
    private final Random rand = new Random();
    private final Minecraft mc;
    private final RenderItem itemRenderer;
    private final GuiNewChat persistantChatGUI;
    private int updateCounter;
    private String recordPlaying = "";
    private int recordPlayingUpFor;
    private boolean recordIsPlaying;
    public float prevVignetteBrightness = 1.0f;
    private int remainingHighlightTicks;
    private ItemStack highlightingItemStack = ItemStack.field_190927_a;
    private final GuiOverlayDebug overlayDebug;
    private final GuiSubtitleOverlay overlaySubtitle;
    private final GuiSpectator spectatorGui;
    private final GuiPlayerTabOverlay overlayPlayerList;
    private final GuiBossOverlay overlayBoss;
    private int titlesTimer;
    private String displayedTitle = "";
    private String displayedSubTitle = "";
    private int titleFadeIn;
    private int titleDisplayTime;
    private int titleFadeOut;
    private int playerHealth;
    private int lastPlayerHealth;
    private long lastSystemTime;
    private long healthUpdateCounter;
    private final Map<ChatType, List<IChatListener>> field_191743_I = Maps.newHashMap();
    public static float sizer = 1.0f;
    public static boolean openedTab;
    public static float tabScale;
    public static float tabAlpha;
    boolean showing = true;
    float alphaDrop = 255.0f;
    float expandX = 40.0f;
    float widthControl = 1.0f;
    float percentDifference = 1.0f;
    TimerHelper showOut = new TimerHelper();
    private final boolean isReseted = false;
    private final boolean asksFailled = false;
    private final float trottleExti = 0.0f;
    private final float asksesCharge = 1.0f;
    private final float failledToner = 0.0f;
    TimerHelper asksCharge = new TimerHelper();
    TimerHelper nuCharge = new TimerHelper();
    TimerHelper theTrottled = new TimerHelper();
    float sizerScaff;
    float alphaScaff;
    public static float trottleScaff;
    private float x;
    private final AnimationUtils animSlot0 = new AnimationUtils(0.0f, 0.0f, 0.1f);
    private final AnimationUtils animSlot1 = new AnimationUtils(0.0f, 0.0f, 0.1f);
    private final AnimationUtils animSlot2 = new AnimationUtils(0.0f, 0.0f, 0.1f);
    private final AnimationUtils animSlot3 = new AnimationUtils(0.0f, 0.0f, 0.1f);
    private final AnimationUtils animSlot4 = new AnimationUtils(0.0f, 0.0f, 0.1f);
    private final AnimationUtils animSlot5 = new AnimationUtils(0.0f, 0.0f, 0.1f);
    private final AnimationUtils animSlot6 = new AnimationUtils(0.0f, 0.0f, 0.1f);
    private final AnimationUtils animSlot7 = new AnimationUtils(0.0f, 0.0f, 0.1f);
    private final AnimationUtils animSlot8 = new AnimationUtils(0.0f, 0.0f, 0.1f);

    public GuiIngame(Minecraft mcIn) {
        this.mc = mcIn;
        this.itemRenderer = mcIn.getRenderItem();
        this.overlayDebug = new GuiOverlayDebug(mcIn);
        this.spectatorGui = new GuiSpectator(mcIn);
        this.persistantChatGUI = new GuiNewChat(mcIn);
        this.overlayPlayerList = new GuiPlayerTabOverlay(mcIn, this);
        this.overlayBoss = new GuiBossOverlay(mcIn);
        this.overlaySubtitle = new GuiSubtitleOverlay(mcIn);
        for (ChatType chattype : ChatType.values()) {
            this.field_191743_I.put(chattype, Lists.newArrayList());
        }
        NarratorChatListener ichatlistener = NarratorChatListener.field_193643_a;
        this.field_191743_I.get((Object)ChatType.CHAT).add(new NormalChatListener(mcIn));
        this.field_191743_I.get((Object)ChatType.CHAT).add(ichatlistener);
        this.field_191743_I.get((Object)ChatType.SYSTEM).add(new NormalChatListener(mcIn));
        this.field_191743_I.get((Object)ChatType.SYSTEM).add(ichatlistener);
        this.field_191743_I.get((Object)ChatType.GAME_INFO).add(new OverlayChatListener(mcIn));
        this.setDefaultTitlesTimes();
    }

    public void setDefaultTitlesTimes() {
        this.titleFadeIn = 10;
        this.titleDisplayTime = 70;
        this.titleFadeOut = 20;
    }

    public void renderGameOverlay(float partialTicks) {
        boolean render;
        boolean renderTab;
        ScoreObjective scoreobjective1;
        int i1;
        float f;
        GlStateManager.enableDepth();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        ScaledResolution scaledresolution = new ScaledResolution(this.mc);
        ESP.get.alwaysPreRender2D(partialTicks, scaledresolution);
        if (!Panic.stop) {
            Client.pointRenderer.render2D();
        }
        ViewModel.drawGlowHands();
        int i = scaledresolution.getScaledWidth();
        int j = scaledresolution.getScaledHeight();
        FontRenderer fontrenderer = this.getFontRenderer();
        GlStateManager.enableBlend();
        if (Config.isVignetteEnabled()) {
            this.renderVignette(Minecraft.player.getBrightness(), scaledresolution);
        } else {
            GlStateManager.enableDepth();
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        }
        ItemStack itemstack = Minecraft.player.inventory.armorItemInSlot(3);
        if (this.mc.gameSettings.thirdPersonView == 0 && itemstack.getItem() == Item.getItemFromBlock(Blocks.PUMPKIN)) {
            this.renderPumpkinOverlay(scaledresolution);
        }
        if (!Minecraft.player.isPotionActive(MobEffects.NAUSEA) && (f = Minecraft.player.prevTimeInPortal + (Minecraft.player.timeInPortal - Minecraft.player.prevTimeInPortal) * partialTicks) > 0.0f) {
            this.renderPortal(f, scaledresolution);
        }
        if (this.mc.playerController.isSpectator()) {
            this.spectatorGui.renderTooltip(scaledresolution, partialTicks);
        } else if (Hud.get.isCustomHotbar()) {
            this.renderHotbarCustom(scaledresolution, partialTicks, Hud.get.isSleekHotbar());
        } else {
            this.renderHotbar(scaledresolution, partialTicks);
        }
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.getTextureManager().bindTexture(ICONS);
        GlStateManager.enableBlend();
        this.renderAttackIndicator(partialTicks, scaledresolution);
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        this.mc.mcProfiler.startSection("bossHealth");
        this.overlayBoss.renderBossHealth();
        this.mc.mcProfiler.endSection();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.getTextureManager().bindTexture(ICONS);
        if (this.mc.playerController.shouldDrawHUD()) {
            this.renderPlayerStats(scaledresolution);
        }
        this.renderMountHealth(scaledresolution);
        GlStateManager.disableBlend();
        if (Minecraft.player.getSleepTimer() > 0) {
            this.mc.mcProfiler.startSection("sleep");
            GlStateManager.disableDepth();
            GlStateManager.disableAlpha();
            int j1 = Minecraft.player.getSleepTimer();
            float f1 = (float)j1 / 100.0f;
            if (f1 > 1.0f) {
                f1 = 1.0f - (float)(j1 - 100) / 10.0f;
            }
            int k = (int)(220.0f * f1) << 24 | 0x101020;
            GuiIngame.drawRect(0, 0.0, (double)i, (double)j, k);
            GlStateManager.enableAlpha();
            GlStateManager.enableDepth();
            this.mc.mcProfiler.endSection();
        }
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        int k1 = i / 2 - 91;
        if (!(!Minecraft.player.isRidingHorse() || NoRender.get.actived && NoRender.get.ExpBar.bValue)) {
            this.renderHorseJumpBar(scaledresolution, k1);
        } else if (!(!this.mc.playerController.gameIsSurvivalOrAdventure() || NoRender.get.actived && NoRender.get.ExpBar.bValue)) {
            this.renderExpBar(scaledresolution, k1);
        }
        if (!(!this.mc.gameSettings.heldItemTooltips || this.mc.playerController.isSpectator() || NoRender.get.actived && NoRender.get.HeldTooltips.bValue)) {
            this.renderSelectedItem(scaledresolution);
        } else if (Minecraft.player.isSpectator()) {
            this.spectatorGui.renderSelectedItem(scaledresolution);
        }
        if (this.mc.isDemo()) {
            this.renderDemo(scaledresolution);
        }
        this.renderPotionEffects(scaledresolution);
        if (this.recordPlayingUpFor > 0) {
            this.mc.mcProfiler.startSection("overlayMessage");
            float f2 = (float)this.recordPlayingUpFor - partialTicks;
            int l1 = (int)(f2 * 255.0f / 20.0f);
            if (l1 > 255) {
                l1 = 255;
            }
            if (l1 > 8) {
                GlStateManager.pushMatrix();
                GlStateManager.translate(i / 2, j - 68, 0.0f);
                GlStateManager.enableBlend();
                GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
                int l = 0xFFFFFF;
                if (this.recordIsPlaying) {
                    l = MathHelper.hsvToRGB(f2 / 50.0f, 0.7f, 0.6f) & 0xFFFFFF;
                }
                fontrenderer.drawString(this.recordPlaying, -fontrenderer.getStringWidth(this.recordPlaying) / 2, -4.0, l + (l1 << 24 & 0xFF000000));
                GlStateManager.disableBlend();
                GlStateManager.popMatrix();
            }
            this.mc.mcProfiler.endSection();
        }
        if (!NoRender.get.actived || !NoRender.get.TitleScreen.bValue) {
            this.overlaySubtitle.renderSubtitles(scaledresolution);
            if (this.titlesTimer > 0) {
                this.mc.mcProfiler.startSection("titleAndSubtitle");
                float f3 = (float)this.titlesTimer - partialTicks;
                int i2 = 255;
                if (this.titlesTimer > this.titleFadeOut + this.titleDisplayTime) {
                    float f4 = (float)(this.titleFadeIn + this.titleDisplayTime + this.titleFadeOut) - f3;
                    i2 = (int)(f4 * 255.0f / (float)this.titleFadeIn);
                }
                if (this.titlesTimer <= this.titleFadeOut) {
                    i2 = (int)(f3 * 255.0f / (float)this.titleFadeOut);
                }
                if ((i2 = MathHelper.clamp(i2, 0, 255)) > 8) {
                    GlStateManager.pushMatrix();
                    GlStateManager.translate(i / 2, j / 2, 0.0f);
                    GlStateManager.enableBlend();
                    GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
                    GlStateManager.pushMatrix();
                    GlStateManager.scale(4.0f, 4.0f, 4.0f);
                    int j2 = i2 << 24 & 0xFF000000;
                    fontrenderer.drawString(this.displayedTitle, -fontrenderer.getStringWidth(this.displayedTitle) / 2, -10.0f, 0xFFFFFF | j2, true);
                    GlStateManager.popMatrix();
                    GlStateManager.pushMatrix();
                    GlStateManager.scale(2.0f, 2.0f, 2.0f);
                    fontrenderer.drawString(this.displayedSubTitle, -fontrenderer.getStringWidth(this.displayedSubTitle) / 2, 5.0f, 0xFFFFFF | j2, true);
                    GlStateManager.popMatrix();
                    GlStateManager.disableBlend();
                    GlStateManager.popMatrix();
                }
                this.mc.mcProfiler.endSection();
            }
        }
        Scoreboard scoreboard = this.mc.world.getScoreboard();
        ScoreObjective scoreobjective = null;
        ScorePlayerTeam scoreplayerteam = scoreboard.getPlayersTeam(Minecraft.player.getName());
        if (scoreplayerteam != null && (i1 = scoreplayerteam.getChatFormat().getColorIndex()) >= 0) {
            scoreobjective = scoreboard.getObjectiveInDisplaySlot(3 + i1);
        }
        ScoreObjective scoreObjective = scoreobjective1 = scoreobjective != null ? scoreobjective : scoreboard.getObjectiveInDisplaySlot(1);
        if (!(scoreobjective1 == null || NoRender.get.actived && NoRender.get.ScoreBoard.bValue)) {
            this.renderScoreboard(scoreobjective1, scaledresolution);
        }
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.disableAlpha();
        GlStateManager.pushMatrix();
        GlStateManager.translate(0.0f, j - 48, 0.0f);
        this.mc.mcProfiler.startSection("chat");
        this.persistantChatGUI.drawChat(this.updateCounter);
        this.mc.mcProfiler.endSection();
        GlStateManager.popMatrix();
        scoreobjective1 = scoreboard.getObjectiveInDisplaySlot(0);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.disableLighting();
        GlStateManager.enableAlpha();
        if (!Panic.stop) {
            EventRender2D eventRender2D = new EventRender2D(scaledresolution, partialTicks);
            eventRender2D.call();
            Utility.render2DRunnables();
            for (Module module : Client.moduleManager.modules) {
                if (!Panic.stop) {
                    module.alwaysRender2D(scaledresolution);
                    module.alwaysRender2D(partialTicks, scaledresolution);
                }
                if (!module.actived || Panic.stop) continue;
                module.onRender2D(scaledresolution);
                module.onRenderUpdate();
            }
        }
        GlStateManager.enableAlpha();
        boolean bl = renderTab = !Panic.stop && openedTab || (Panic.stop || !ComfortUi.get.isBetterTabOverlay()) && this.mc.gameSettings.keyBindPlayerList.isKeyDown();
        if (Panic.stop || !ComfortUi.get.isBetterTabOverlay()) {
            if (tabScale != 1.15f) {
                tabScale = 1.15f;
            }
            if (tabAlpha != 0.0f) {
                tabAlpha = 0.0f;
            }
        } else {
            if (this.mc.gameSettings.keyBindPlayerList.isKeyDown() && tabScale > 1.0f || tabScale < 1.19f) {
                tabScale = MathUtils.lerp(tabScale, this.mc.gameSettings.keyBindPlayerList.isKeyDown() ? 0.99f : 1.2f, (float)Minecraft.frameTime * 0.02f);
            }
            if (this.mc.gameSettings.keyBindPlayerList.isKeyDown() && openedTab && (double)tabScale < 1.005) {
                tabScale = 1.0f;
            }
            if ((double)tabScale < 1.15 && !openedTab) {
                openedTab = true;
            }
            if ((double)tabScale > 1.15 && openedTab) {
                openedTab = false;
            }
            openedTab = tabAlpha > 1.0f;
            tabAlpha = MathUtils.harp(tabAlpha, this.mc.gameSettings.keyBindPlayerList.isKeyDown() ? 105 : -5, (float)Minecraft.frameTime * 0.02f);
            tabAlpha = MathUtils.clamp(tabAlpha, 0.0f, 90.0f);
        }
        try {
            if (renderTab && (!this.mc.isIntegratedServerRunning() || Minecraft.player.connection.getPlayerInfoMap().size() > 1 || scoreobjective1 != null)) {
                this.overlayPlayerList.updatePlayerList(true);
                GL11.glPushMatrix();
                if (Panic.stop || !ComfortUi.get.isBetterTabOverlay()) {
                    this.overlayPlayerList.renderPlayerlist2(i, scoreboard, scoreobjective1);
                } else {
                    this.overlayPlayerList.renderPlayerlist3(i, scoreboard, scoreobjective1);
                }
                GL11.glPopMatrix();
            } else {
                this.overlayPlayerList.updatePlayerList(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (Panic.stop && !ComfortUi.get.isBetterDebugF3()) {
            if (sizer != 1.0f) {
                sizer = 1.0f;
            }
        } else if (this.mc.gameSettings.showDebugInfo) {
            if (MathUtils.getDifferenceOf(sizer, 0.0f) != 0.0) {
                sizer = MathUtils.harp(sizer, 0.0f, (float)Minecraft.frameTime * 0.01f);
            }
        } else if (MathUtils.getDifferenceOf(sizer, 1.0f) != 0.0) {
            sizer = MathUtils.harp(sizer, 1.0f, (float)Minecraft.frameTime * 0.0125f);
        }
        boolean bl2 = render = (double)sizer < 0.95 || Panic.stop && this.mc.gameSettings.showDebugInfo;
        if (render) {
            this.overlayDebug.renderDebugInfo(scaledresolution);
        }
        if (!Panic.stop) {
            for (Module module : Client.moduleManager.modules) {
                if (!module.actived) continue;
                module.onPostRender2D(scaledresolution);
            }
        }
        Panic.onHasShowPanicCode((float)scaledresolution.getScaledWidth() / 2.0f, (float)scaledresolution.getScaledHeight() / 2.0f);
        if (CaveFinder.get != null) {
            CaveFinder.get.post2DDark(scaledresolution);
        }
        if (!Panic.stop && ComfortUi.get.isScreensDarking()) {
            GuiIngame.uiSmoothness();
        }
    }

    public static void uiSmoothness() {
        Minecraft mc = Minecraft.getMinecraft();
        ScaledResolution scaled = new ScaledResolution(mc);
        float animSpeed = MathUtils.clamp(((float)Minecraft.frameTime * 0.0075f + (float)Minecraft.frameTime * ((float)(ComfortUi.alphaTransition / 255) - 0.5f) / 500.0f) * (float)(mc.currentScreen == null ? 1 : 2), 0.01f, 1.0f);
        ComfortUi.alphaTransition = ComfortUi.get.actived ? (!(mc.currentScreen == null || mc.currentScreen instanceof ClickGuiScreen || mc.currentScreen instanceof GuiChat || mc.currentScreen instanceof GuiConfig) ? (int)MathUtils.lerp(ComfortUi.alphaTransition, 175.0f, animSpeed) : (ComfortUi.alphaTransition > 1 ? (int)MathUtils.lerp(ComfortUi.alphaTransition, 0.0f, animSpeed) : 0)) : (ComfortUi.alphaTransition > 1 ? (int)MathUtils.lerp(ComfortUi.alphaTransition, 0.0f, animSpeed) : 0);
        if (ComfortUi.alphaTransition != 0) {
            RenderUtils.fixShadows();
            GlStateManager.disableDepth();
            RenderUtils.drawAlphedRect(0.0, 0.0, scaled.getScaledWidth(), scaled.getScaledHeight(), ColorUtils.getColor(0, 0, 0, ComfortUi.alphaTransition));
            GlStateManager.enableDepth();
        }
    }

    private void renderAttackIndicator(float p_184045_1_, ScaledResolution p_184045_2_) {
        if (Crosshair.get != null && Crosshair.get.actived && !Panic.stop) {
            return;
        }
        GameSettings gamesettings = this.mc.gameSettings;
        if (gamesettings.thirdPersonView == 0) {
            if (this.mc.playerController.isSpectator() && this.mc.pointedEntity == null) {
                RayTraceResult raytraceresult = this.mc.objectMouseOver;
                if (raytraceresult == null || raytraceresult.typeOfHit != RayTraceResult.Type.BLOCK) {
                    return;
                }
                BlockPos blockpos = raytraceresult.getBlockPos();
                IBlockState iblockstate = this.mc.world.getBlockState(blockpos);
                if (!ReflectorForge.blockHasTileEntity(iblockstate) || !(this.mc.world.getTileEntity(blockpos) instanceof IInventory)) {
                    return;
                }
            }
            int l = p_184045_2_.getScaledWidth();
            int i1 = p_184045_2_.getScaledHeight();
            if (gamesettings.showDebugInfo && !gamesettings.hideGUI && !Minecraft.player.hasReducedDebug() && !gamesettings.reducedDebugInfo) {
                GlStateManager.pushMatrix();
                GlStateManager.translate(l / 2, i1 / 2, this.zLevel);
                Entity entity = this.mc.getRenderViewEntity();
                GlStateManager.rotate(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * p_184045_1_, -1.0f, 0.0f, 0.0f);
                GlStateManager.rotate(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * p_184045_1_, 0.0f, 1.0f, 0.0f);
                GlStateManager.scale(-1.0f, -1.0f, -1.0f);
                OpenGlHelper.renderDirections(10);
                GlStateManager.popMatrix();
            } else {
                GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.ONE_MINUS_DST_COLOR, GlStateManager.DestFactor.ONE_MINUS_SRC_COLOR, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
                GlStateManager.enableAlpha();
                this.drawTexturedModalRect(l / 2 - 7, i1 / 2 - 7, 0, 0, 16, 16);
                if (this.mc.gameSettings.attackIndicator == 1) {
                    float f = Minecraft.player.getCooledAttackStrength(0.0f);
                    boolean flag = false;
                    if (this.mc.pointedEntity != null && this.mc.pointedEntity instanceof EntityLivingBase && f >= 1.0f) {
                        flag = Minecraft.player.getCooldownPeriod() > 5.0f;
                        flag &= this.mc.pointedEntity.isEntityAlive();
                    }
                    int i = i1 / 2 - 7 + 16;
                    int j = l / 2 - 8;
                    if (flag) {
                        this.drawTexturedModalRect(j, i, 68, 94, 16, 16);
                    } else if (f < 1.0f) {
                        int k = (int)(f * 17.0f);
                        this.drawTexturedModalRect(j, i, 36, 94, 16, 4);
                        this.drawTexturedModalRect(j, i, 52, 94, k, 4);
                    }
                }
                if (Crosshair.get != null && Crosshair.get.actived) {
                    RenderUtils.resetColor();
                }
            }
        }
    }

    protected void renderPotionEffects(ScaledResolution resolution) {
        Collection<PotionEffect> collection = Minecraft.player.getActivePotionEffects();
        if (!collection.isEmpty() && !Hud.get.isPotsCustom()) {
            this.mc.getTextureManager().bindTexture(GuiContainer.INVENTORY_BACKGROUND);
            GlStateManager.enableBlend();
            int i = 0;
            int j = 0;
            Iterator<PotionEffect> iterator = Ordering.natural().reverse().sortedCopy(collection).iterator();
            while (true) {
                if (!iterator.hasNext()) {
                    return;
                }
                PotionEffect potioneffect = iterator.next();
                Potion potion = potioneffect.getPotion();
                boolean flag = potion.hasStatusIcon();
                if (Reflector.ForgePotion_shouldRenderHUD.exists()) {
                    if (!Reflector.callBoolean(potion, Reflector.ForgePotion_shouldRenderHUD, potioneffect)) continue;
                    this.mc.getTextureManager().bindTexture(GuiContainer.INVENTORY_BACKGROUND);
                    flag = true;
                }
                if (!flag || !potioneffect.doesShowParticles()) continue;
                int k = resolution.getScaledWidth();
                int l = 1;
                if (this.mc.isDemo()) {
                    l += 15;
                }
                int i1 = potion.getStatusIconIndex();
                if (potion.isBeneficial()) {
                    k -= 25 * ++i;
                } else {
                    k -= 25 * ++j;
                    l += 26;
                }
                GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
                float f = 1.0f;
                if (potioneffect.getIsAmbient()) {
                    this.drawTexturedModalRect(k, l, 165, 166, 24, 24);
                } else {
                    this.drawTexturedModalRect(k, l, 141, 166, 24, 24);
                    if (potioneffect.getDuration() <= 200) {
                        int j1 = 10 - potioneffect.getDuration() / 20;
                        f = MathHelper.clamp((float)potioneffect.getDuration() / 10.0f / 5.0f * 0.5f, 0.0f, 0.5f) + MathHelper.cos((float)potioneffect.getDuration() * (float)Math.PI / 5.0f) * MathHelper.clamp((float)j1 / 10.0f * 0.25f, 0.0f, 0.25f);
                    }
                }
                GlStateManager.color(1.0f, 1.0f, 1.0f, f);
                if (Reflector.ForgePotion_renderHUDEffect.exists()) {
                    if (potion.hasStatusIcon()) {
                        this.drawTexturedModalRect(k + 3, l + 3, i1 % 8 * 18, 198 + i1 / 8 * 18, 18, 18);
                    }
                    Reflector.call(potion, Reflector.ForgePotion_renderHUDEffect, k, l, potioneffect, this.mc, Float.valueOf(f));
                    continue;
                }
                this.drawTexturedModalRect(k + 3, l + 3, i1 % 8 * 18, 198 + i1 / 8 * 18, 18, 18);
            }
        }
    }

    protected void renderHotbar(ScaledResolution sr, float partialTicks) {
        Entity entity = this.mc.getRenderViewEntity();
        if (entity instanceof EntityPlayer) {
            float f1;
            EntityPlayer entityplayer = (EntityPlayer)entity;
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            this.mc.getTextureManager().bindTexture(WIDGETS_TEX_PATH);
            ItemStack itemstack = entityplayer.getHeldItemOffhand();
            EnumHandSide enumhandside = entityplayer.getPrimaryHand().opposite();
            int i = sr.getScaledWidth() / 2;
            RenderHelper.disableStandardItemLighting();
            float curX = i - 90 + entityplayer.inventory.currentItem * 20;
            if (!Panic.stop && ComfortUi.get.isUsement()) {
                if (MathUtils.getDifferenceOf(curX, this.x) != 0.0) {
                    this.x = MathUtils.harp(this.x, curX, (float)Minecraft.frameTime * 0.075f);
                }
            } else {
                this.x = i - 90 + entityplayer.inventory.currentItem * 20;
            }
            float f = this.zLevel;
            int j = 182;
            int k = 91;
            this.zLevel = -90.0f;
            this.drawTexturedModalRect(i - 91, sr.getScaledHeight() - 22, 0, 0, 182, 22);
            this.drawTexturedModalRect(this.x - 2.0f, (float)(sr.getScaledHeight() - 22 - 1), 0, 22, 24, 22);
            if (!itemstack.func_190926_b()) {
                if (enumhandside == EnumHandSide.LEFT) {
                    this.drawTexturedModalRect(i - 91 - 29, sr.getScaledHeight() - 23, 24, 22, 29, 24);
                } else {
                    this.drawTexturedModalRect(i + 91, sr.getScaledHeight() - 23, 53, 22, 29, 24);
                }
            }
            this.zLevel = f;
            GlStateManager.enableRescaleNormal();
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            RenderHelper.enableGUIStandardItemLighting();
            CustomItems.setRenderOffHand(false);
            GL11.glDepthMask(true);
            for (int l = 0; l < 9; ++l) {
                int i1 = i - 90 + l * 20 + 2;
                int j1 = sr.getScaledHeight() - 16 - 3;
                this.renderHotbarItem(i1, j1, partialTicks, entityplayer, entityplayer.inventory.getStackInSlot(l));
            }
            if (!itemstack.func_190926_b()) {
                CustomItems.setRenderOffHand(true);
                int l1 = sr.getScaledHeight() - 16 - 3;
                if (enumhandside == EnumHandSide.LEFT) {
                    this.renderHotbarItem(i - 91 - 26, l1, partialTicks, entityplayer, itemstack);
                } else {
                    this.renderHotbarItem(i + 91 + 10, l1, partialTicks, entityplayer, itemstack);
                }
                CustomItems.setRenderOffHand(false);
            }
            if (this.mc.gameSettings.attackIndicator == 2 && (f1 = Minecraft.player.getCooledAttackStrength(0.0f)) < 1.0f) {
                int i2 = sr.getScaledHeight() - 20;
                int j2 = i + 91 + 6;
                if (enumhandside == EnumHandSide.RIGHT) {
                    j2 = i - 91 - 22;
                }
                GL11.glDepthMask(true);
                this.mc.getTextureManager().bindTexture(Gui.ICONS);
                int k1 = (int)(f1 * 19.0f);
                GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
                this.drawTexturedModalRect(j2, i2, 0, 94, 18, 18);
                this.drawTexturedModalRect(j2, i2 + 18 - k1, 18, 112 - k1, 18, k1);
            }
            RenderHelper.disableStandardItemLighting();
            GlStateManager.disableRescaleNormal();
            GlStateManager.disableBlend();
        }
    }

    protected void renderHotbarCustom(ScaledResolution sr, float partialTicks, boolean isSleekRender) {
        Entity entity = this.mc.getRenderViewEntity();
        if (entity instanceof EntityPlayer) {
            EntityPlayer entityplayer = (EntityPlayer)entity;
            RenderHelper.disableStandardItemLighting();
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            this.mc.getTextureManager().bindTexture(WIDGETS_TEX_PATH);
            ItemStack itemstack = entityplayer.getHeldItemOffhand();
            EnumHandSide enumhandside = entityplayer.getPrimaryHand().opposite();
            int i = sr.getScaledWidth() / 2;
            if (MathUtils.getDifferenceOf((float)(90 + entityplayer.inventory.currentItem * 20), this.x) != 0.0) {
                this.x = MathUtils.harp(this.x, entityplayer.inventory.currentItem * 20, (float)Minecraft.frameTime * 0.055f / (isSleekRender ? 1.0f : 2.0f));
            }
            float f = this.zLevel;
            int j = 182;
            int k = 91;
            this.zLevel = -90.0f;
            if (isSleekRender) {
                int ccc1 = ClientColors.getColor1(50);
                int ccc2 = ClientColors.getColor2(150);
                int ccc3 = ClientColors.getColor1(200);
                int ccc4 = ClientColors.getColor2(100);
                int c1 = ColorUtils.swapAlpha(ccc1, (float)ColorUtils.getAlphaFromColor(ccc1) / 1.8f);
                int c2 = ColorUtils.swapAlpha(ccc2, (float)ColorUtils.getAlphaFromColor(ccc2) / 1.8f);
                int c4 = ColorUtils.swapAlpha(ccc3, (float)ColorUtils.getAlphaFromColor(ccc3) / 4.4f);
                int c3 = ColorUtils.swapAlpha(ccc4, (float)ColorUtils.getAlphaFromColor(ccc4) / 4.4f);
                RenderUtils.drawRoundedFullGradientShadowFullGradientRoundedFullGradientRectWithBloomBool(i - 90, sr.getScaledHeight() - 22, i + 90, sr.getScaledHeight() - 2, 4.0f, 1.25f, c1, c2, c3, c4, true, false, true);
                int c1q = ColorUtils.swapAlpha(ccc1, (float)ColorUtils.getAlphaFromColor(ccc1) / 2.2f / 4.0f);
                int c2q = ColorUtils.swapAlpha(ccc2, (float)ColorUtils.getAlphaFromColor(ccc2) / 2.2f / 4.0f);
                RenderUtils.drawRoundedFullGradientShadowFullGradientRoundedFullGradientRectWithBloomBool(i - 90, sr.getScaledHeight() - 22, i + 90, sr.getScaledHeight() - 2, 4.0f, 1.0f, c1q, c2q, c3, c4, true, true, false);
                int cBG = ColorUtils.getColor(7, 7, 7, 110);
                RenderUtils.drawRoundedFullGradientShadowFullGradientRoundedFullGradientRectWithBloomBool((float)(sr.getScaledWidth() / 2 - 86) + this.x - 1.0f, sr.getScaledHeight() - 19, (float)(sr.getScaledWidth() / 2 - 86) + this.x + 13.0f, sr.getScaledHeight() - 5, 3.0f, 2.0f, cBG, cBG, cBG, cBG, false, true, true);
                RenderUtils.drawInsideFullRoundedFullGradientShadowRectWithBloomBool((float)(sr.getScaledWidth() / 2 - 87) + this.x - 3.0f, sr.getScaledHeight() - 22, (float)(sr.getScaledWidth() / 2 - 87) + this.x + 17.0f, sr.getScaledHeight() - 2, 4.0f, 1.0f, ccc1, ccc2, ccc3, ccc4, false);
                RenderUtils.drawRoundedFullGradientOutsideShadow((float)(sr.getScaledWidth() / 2 - 87) + this.x - 3.0f, sr.getScaledHeight() - 22, (float)(sr.getScaledWidth() / 2 - 87) + this.x + 17.0f, sr.getScaledHeight() - 2, 5.0f, 0.75f, ccc1, ccc2, ccc3, ccc4, false);
                if (!itemstack.func_190926_b()) {
                    if (enumhandside == EnumHandSide.LEFT) {
                        RenderUtils.drawRoundedFullGradientShadowFullGradientRoundedFullGradientRectWithBloomBool(i - 91 - 26, sr.getScaledHeight() - 20, i - 91 - 26 + 17, sr.getScaledHeight() - 3, 3.0f, 2.0f, c1, c2, c3, c4, false, true, true);
                    } else {
                        RenderUtils.drawRoundedFullGradientShadowFullGradientRoundedFullGradientRectWithBloomBool(i + 91 + 27 - 17, sr.getScaledHeight() - 20, i + 91 + 27, sr.getScaledHeight() - 3, 3.0f, 2.0f, c1, c2, c3, c4, false, true, true);
                    }
                }
                GlStateManager.enableDepth();
                GL11.glDepthMask(true);
                this.zLevel = f;
                GlStateManager.enableRescaleNormal();
                GlStateManager.enableBlend();
                GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
                RenderHelper.enableGUIStandardItemLighting();
                CustomItems.setRenderOffHand(false);
                for (int l = 0; l < 9; ++l) {
                    int i1 = i - 90 + l * 20 + 2;
                    int j1 = sr.getScaledHeight() - 16 - 4;
                    try {
                        this.renderHotbarItem(i1, j1, partialTicks, entityplayer, entityplayer.inventory.mainInventory.get(l));
                        continue;
                    } catch (Exception exception) {
                        // empty catch block
                    }
                }
                if (!itemstack.func_190926_b()) {
                    CustomItems.setRenderOffHand(true);
                    int l1 = sr.getScaledHeight() - 16 - 3;
                    if (enumhandside == EnumHandSide.LEFT) {
                        this.renderHotbarItem(i - 91 - 26, l1, partialTicks, entityplayer, itemstack);
                    } else {
                        this.renderHotbarItem(i + 91 + 10, l1, partialTicks, entityplayer, itemstack);
                    }
                    CustomItems.setRenderOffHand(false);
                }
            } else {
                int l;
                int cl = ColorUtils.getOverallColorFrom(-1, ColorUtils.getColor(50, 255, 50), MathUtils.clamp(Math.abs(this.x - (float)(entityplayer.inventory.currentItem * 20)) / 16.0f, 0.0f, 1.0f));
                int bgC = ColorUtils.getColor(21, 21, 21, 190);
                int bgCOut = ColorUtils.getColor(9, 50);
                RenderUtils.drawRoundOutline(i - 91, (float)sr.getScaledHeight() - 23.5f, 182.0f, 22.5f, 4.0f, 0.25f, bgC, bgCOut);
                GL11.glEnable(3042);
                GL11.glDisable(2929);
                GL11.glDisable(2896);
                this.mc.entityRenderer.disableLightmap();
                if (!itemstack.func_190926_b()) {
                    if (enumhandside == EnumHandSide.LEFT) {
                        RenderUtils.drawRoundOutline(i - 91 - 27, sr.getScaledHeight() - 20, 19.0f, 19.0f, 4.0f, 0.25f, bgC, bgCOut);
                    } else {
                        RenderUtils.drawRoundOutline(i + 91 + 26 - 17, sr.getScaledHeight() - 20, 19.0f, 19.0f, 4.0f, 0.25f, bgC, bgCOut);
                    }
                }
                GL11.glEnable(2929);
                GlStateManager.enableDepth();
                GL11.glDepthMask(true);
                this.zLevel = f;
                GlStateManager.enableRescaleNormal();
                GlStateManager.enableBlend();
                GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
                RenderHelper.enableGUIStandardItemLighting();
                CustomItems.setRenderOffHand(false);
                int cef1 = this.x > (float)(entityplayer.inventory.currentItem * 20) ? 0 : cl;
                int cef2 = this.x > (float)entityplayer.inventory.currentItem * 20.0f ? cl : 0;
                float cmX = entityplayer.inventory.currentItem * 20 + 8;
                float cmX2 = this.x + 8.0f;
                float cgX = cmX2 > cmX ? cmX : cmX2;
                float cgX2 = cmX2 > cmX ? cmX2 : cmX;
                GL11.glDisable(2896);
                RenderUtils.drawAlphedSideways((float)(i - 90) + cgX, (float)sr.getScaledHeight() - 5.0f, (float)(i - 90) + cgX2, (float)sr.getScaledHeight() - 3.5f, cef1, cef2, true);
                RenderUtils.drawRoundedFullGradientShadowFullGradientRoundedFullGradientRectWithBloomBool((float)(i - 88) + cmX2 - 5.0f, (float)sr.getScaledHeight() - 5.0f, (float)(i - 88) + cmX2 + 5.0f, (float)sr.getScaledHeight() - 4.0f, 0.5f, 1.0f, cl, cl, cl, cl, false, true, true);
                GL11.glDepthMask(true);
                GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
                int es = 0;
                for (l = 0; l < 9; ++l) {
                    if (entityplayer.inventory.mainInventory.get(l).getItem() instanceof ItemAir) continue;
                    es = l;
                }
                this.renderHotbarItem(-10000, -10000, partialTicks, entityplayer, entityplayer.inventory.mainInventory.get(es));
                for (l = 0; l < 9; ++l) {
                    int i1 = i - 90 + l * 20 + 2;
                    int j1 = sr.getScaledHeight() - 16 - 4;
                    double diffX = MathUtils.getDifferenceOf((float)(l * 20 + 8), this.x + 8.0f);
                    double hPC = MathUtils.clamp(1.0 - diffX / 34.0, 0.0, 1.0);
                    hPC *= hPC * hPC;
                    try {
                        GL11.glTranslated(0.0, -hPC * 2.0, 0.0);
                        if (Math.abs(this.x - (float)(l * 20)) > 0.0f) {
                            RenderUtils.customRotatedObject2D(i1, (float)((double)j1 - hPC * 2.0), 16.0f, 16.0f, -(this.x - (float)(l * 20)) * 5.0f * (float)hPC * (float)(!(Minecraft.player.inventory.getCurrentItem().getItem() instanceof ItemAir) ? 1 : 0));
                        }
                        this.renderHotbarItem(i1, j1, partialTicks, entityplayer, entityplayer.inventory.mainInventory.get(l));
                        this.itemRenderer.renderItemOverlays(Fonts.comfortaa_18, entityplayer.inventory.mainInventory.get(l), i1, j1);
                        if (Math.abs(this.x - (float)(l * 20)) > 0.0f) {
                            RenderUtils.customRotatedObject2D(i1, (float)((double)j1 - hPC * 2.0), 16.0f, 16.0f, (this.x - (float)(l * 20)) * 5.0f * (float)hPC * (float)(!(Minecraft.player.inventory.getCurrentItem().getItem() instanceof ItemAir) ? 1 : 0));
                        }
                        GL11.glTranslated(0.0, hPC * 2.0, 0.0);
                        continue;
                    } catch (Exception exception) {
                        // empty catch block
                    }
                }
                if (!itemstack.func_190926_b()) {
                    CustomItems.setRenderOffHand(true);
                    int l1 = sr.getScaledHeight() - 16 - 3;
                    if (enumhandside == EnumHandSide.LEFT) {
                        this.renderHotbarItem(i - 91 - 26, l1, partialTicks, entityplayer, itemstack);
                    } else {
                        this.renderHotbarItem(i + 91 + 10, l1, partialTicks, entityplayer, itemstack);
                    }
                    CustomItems.setRenderOffHand(false);
                }
            }
            RenderHelper.disableStandardItemLighting();
            GlStateManager.disableRescaleNormal();
            GlStateManager.disableBlend();
        }
    }

    public void renderHorseJumpBar(ScaledResolution scaledRes, int x) {
        if (NoRender.get.actived && NoRender.get.ExpBar.bValue) {
            return;
        }
        this.mc.mcProfiler.startSection("jumpBar");
        this.mc.getTextureManager().bindTexture(Gui.ICONS);
        float f = Minecraft.player.getHorseJumpPower();
        int i = 182;
        int j = (int)(f * 183.0f);
        int k = scaledRes.getScaledHeight() - 32 + 3;
        this.drawTexturedModalRect(x, k, 0, 84, 182, 5);
        if (j > 0) {
            this.drawTexturedModalRect(x, k, 0, 89, j, 5);
        }
        this.mc.mcProfiler.endSection();
    }

    public void renderExpBar(ScaledResolution scaledRes, int x) {
        this.mc.mcProfiler.startSection("expBar");
        this.mc.getTextureManager().bindTexture(Gui.ICONS);
        int i = Minecraft.player.xpBarCap();
        GlStateManager.enableAlpha();
        GlStateManager.enableBlend();
        if (i > 0) {
            int j = 182;
            int k = (int)(Minecraft.player.experience * 183.0f);
            int l = scaledRes.getScaledHeight() - 32 + 3;
            this.drawTexturedModalRect(x, l, 0, 64, 182, 5);
            if (k > 0) {
                this.drawTexturedModalRect(x, l, 0, 69, k, 5);
            }
        }
        this.mc.mcProfiler.endSection();
        if (Minecraft.player.experienceLevel > 0) {
            this.mc.mcProfiler.startSection("expLevel");
            int j1 = 8453920;
            if (Config.isCustomColors()) {
                j1 = CustomColors.getExpBarTextColor(j1);
            }
            String s = "" + Minecraft.player.experienceLevel;
            int k1 = (scaledRes.getScaledWidth() - this.getFontRenderer().getStringWidth(s)) / 2;
            int i1 = scaledRes.getScaledHeight() - 31 - 4;
            this.getFontRenderer().drawString(s, k1 + 1, i1, 0);
            this.getFontRenderer().drawString(s, k1 - 1, i1, 0);
            this.getFontRenderer().drawString(s, k1, i1 + 1, 0);
            this.getFontRenderer().drawString(s, k1, i1 - 1, 0);
            this.getFontRenderer().drawString(s, k1, i1, j1);
            this.mc.mcProfiler.endSection();
        }
    }

    public void renderSelectedItem(ScaledResolution scaledRes) {
        this.mc.mcProfiler.startSection("selectedItemName");
        if (this.remainingHighlightTicks > 0 && !this.highlightingItemStack.func_190926_b()) {
            int k;
            Object s = this.highlightingItemStack.getDisplayName();
            if (this.highlightingItemStack.hasDisplayName()) {
                s = TextFormatting.ITALIC + (String)s;
            }
            int i = (scaledRes.getScaledWidth() - this.getFontRenderer().getStringWidth((String)s)) / 2;
            int j = scaledRes.getScaledHeight() - 59;
            if (!this.mc.playerController.shouldDrawHUD()) {
                j += 14;
            }
            if ((k = (int)((float)this.remainingHighlightTicks * 256.0f / 10.0f)) > 255) {
                k = 255;
            }
            if (k > 0) {
                GlStateManager.pushMatrix();
                GlStateManager.enableBlend();
                GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
                this.getFontRenderer().drawStringWithShadow((String)s, i, j, 0xFFFFFF + (k << 24));
                GlStateManager.disableBlend();
                GlStateManager.popMatrix();
            }
        }
        this.mc.mcProfiler.endSection();
    }

    public void renderDemo(ScaledResolution scaledRes) {
        this.mc.mcProfiler.startSection("demo");
        String s = this.mc.world.getTotalWorldTime() >= 120500L ? I18n.format("demo.demoExpired", new Object[0]) : I18n.format("demo.remainingTime", StringUtils.ticksToElapsedTime((int)(120500L - this.mc.world.getTotalWorldTime())));
        int i = this.getFontRenderer().getStringWidth(s);
        this.getFontRenderer().drawStringWithShadow(s, scaledRes.getScaledWidth() - i - 10, 5.0f, 0xFFFFFF);
        this.mc.mcProfiler.endSection();
    }

    private void renderScoreboard(ScoreObjective objective, ScaledResolution scaledRes) {
        Scoreboard scoreboard = objective.getScoreboard();
        Collection<Score> collection = scoreboard.getSortedScores(objective);
        ArrayList<Score> list = Lists.newArrayList(Iterables.filter(collection, new Predicate<Score>(){

            @Override
            public boolean apply(@Nullable Score p_apply_1_) {
                return p_apply_1_.getPlayerName() != null && !p_apply_1_.getPlayerName().startsWith("#");
            }
        }));
        collection = list.size() > 15 ? Lists.newArrayList(Iterables.skip(list, collection.size() - 15)) : list;
        int i = this.getFontRenderer().getStringWidth(objective.getDisplayName());
        for (Score score : collection) {
            ScorePlayerTeam scoreplayerteam = scoreboard.getPlayersTeam(score.getPlayerName());
            String s = ScorePlayerTeam.formatPlayerName(scoreplayerteam, score.getPlayerName()) + ": " + TextFormatting.RED + score.getScorePoints();
            i = Math.max(i, this.getFontRenderer().getStringWidth(s));
        }
        int i1 = collection.size() * this.getFontRenderer().FONT_HEIGHT;
        int j1 = scaledRes.getScaledHeight() / 2 + i1 / 3;
        int k1 = 3;
        int l1 = scaledRes.getScaledWidth() - i - 3;
        int j = 0;
        for (Score score1 : collection) {
            ScorePlayerTeam scoreplayerteam1 = scoreboard.getPlayersTeam(score1.getPlayerName());
            Object s1 = ScorePlayerTeam.formatPlayerName(scoreplayerteam1, score1.getPlayerName());
            if ((!NameSecurity.get.actived || ++j != 8 || !this.mc.isSingleplayer()) && this.mc.getCurrentServerData().serverIP.contains("reallyworld") && ((String)s1).contains("\u041d\u0438\u043a")) {
                s1 = TextFormatting.GRAY + "\u2551" + TextFormatting.WHITE + " \u041d\u0438\u043a: " + TextFormatting.RED + NameSecurity.replacedName();
            }
            if ((!NameSecurity.get.actived || j != 8 || !this.mc.isSingleplayer()) && this.mc.getCurrentServerData().serverIP.contains("reallyworld") && ((String)s1).contains("\u0420\u0438\u043b\u043b\u0438\u043a\u043e\u0432")) {
                s1 = TextFormatting.GRAY + "\u2551" + TextFormatting.WHITE + " \u0420\u0438\u043b\u043b\u0438\u043a\u043e\u0432: " + TextFormatting.GOLD + "18721" + TextFormatting.GRAY + " \u211c";
            }
            if ((!NameSecurity.get.actived || j != 8 || !this.mc.isSingleplayer()) && this.mc.getCurrentServerData().serverIP.contains("reallyworld") && ((String)s1).contains("\u0420\u0430\u043d\u0433")) {
                s1 = TextFormatting.GRAY + "\u2551" + TextFormatting.WHITE + " \u0420\u0430\u043d\u0433: " + TextFormatting.DARK_AQUA + TextFormatting.BOLD + "MODER" + TextFormatting.GOLD + TextFormatting.BOLD + "+";
            }
            if (!(NameSecurity.get.actived && j == 8 && this.mc.isSingleplayer() || !this.mc.getCurrentServerData().serverIP.contains("reallyworld") || ((String)s1).contains("\u0421\u0435\u0440\u0432\u0435\u0440:") || !((String)s1).contains("\u0421\u0435\u0440\u0432\u0435\u0440"))) {
                s1 = TextFormatting.GRAY + "\u2551" + TextFormatting.WHITE + " \u0421\u0435\u0440\u0432\u0435\u0440: " + TextFormatting.GOLD + "GRIEF-0";
            }
            if (!NameSecurity.get.actived) {
                s1 = ScorePlayerTeam.formatPlayerName(scoreplayerteam1, score1.getPlayerName());
            }
            String s2 = "" + TextFormatting.RED + score1.getScorePoints();
            int k = j1 - j * this.getFontRenderer().FONT_HEIGHT;
            int l = scaledRes.getScaledWidth() - 3 + 2;
            GuiIngame.drawRect(l1 - 2, (double)k, (double)l, (double)(k + this.getFontRenderer().FONT_HEIGHT), 0x50000000);
            this.mc.fontRendererObj.drawString((String)s1, l1, k, 0x20FFFFFF);
            this.mc.fontRendererObj.drawString(s2, l - this.getFontRenderer().getStringWidth(s2), k, 0x20FFFFFF);
            if (j != collection.size()) continue;
            String s3 = objective.getDisplayName();
            GuiIngame.drawRect(l1 - 2, (double)(k - this.getFontRenderer().FONT_HEIGHT - 1), (double)l, (double)(k - 1), 0x60000000);
            GuiIngame.drawRect(l1 - 2, (double)(k - 1), (double)l, (double)k, 0x50000000);
            this.getFontRenderer().drawString(s3, l1 + i / 2 - this.getFontRenderer().getStringWidth(s3) / 2, k - this.getFontRenderer().FONT_HEIGHT, 0x20FFFFFF);
        }
    }

    private void renderPlayerStats(ScaledResolution scaledRes) {
        int g = NoRender.get.actived && NoRender.get.ExpBar.bValue ? 28 : 20;
        Entity entity = this.mc.getRenderViewEntity();
        if (entity instanceof EntityPlayer) {
            boolean flag;
            EntityPlayer entityplayer = (EntityPlayer)entity;
            int i = MathHelper.ceil(entityplayer.getHealth());
            boolean bl = flag = this.healthUpdateCounter > (long)this.updateCounter && (this.healthUpdateCounter - (long)this.updateCounter) / 3L % 2L == 1L;
            if (i < this.playerHealth && entityplayer.hurtResistantTime > 0) {
                this.lastSystemTime = Minecraft.getSystemTime();
                this.healthUpdateCounter = this.updateCounter + 20;
            } else if (i > this.playerHealth && entityplayer.hurtResistantTime > 0) {
                this.lastSystemTime = Minecraft.getSystemTime();
                this.healthUpdateCounter = this.updateCounter + 10;
            }
            if (Minecraft.getSystemTime() - this.lastSystemTime > 1000L) {
                this.playerHealth = i;
                this.lastPlayerHealth = i;
                this.lastSystemTime = Minecraft.getSystemTime();
            }
            this.playerHealth = i;
            int j = this.lastPlayerHealth;
            this.rand.setSeed((long)this.updateCounter * 312871L);
            FoodStats foodstats = entityplayer.getFoodStats();
            int k = foodstats.getFoodLevel();
            IAttributeInstance iattributeinstance = entityplayer.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH);
            int l = scaledRes.getScaledWidth() / 2 - 91;
            int i1 = scaledRes.getScaledWidth() / 2 + 91;
            int j1 = scaledRes.getScaledHeight() - 39;
            float f = (float)iattributeinstance.getAttributeValue();
            int k1 = MathHelper.ceil(entityplayer.getAbsorptionAmount());
            int l1 = MathHelper.ceil((f + (float)k1) / 2.0f / 10.0f);
            int i2 = Math.max(10 - (l1 - 2), 3);
            int j2 = j1 - (l1 - 1) * i2 - 10;
            int k2 = j1 - 10;
            int l2 = k1;
            int i3 = entityplayer.getTotalArmorValue();
            int j3 = -1;
            if (entityplayer.isPotionActive(MobEffects.REGENERATION)) {
                j3 = this.updateCounter % MathHelper.ceil(f + 5.0f);
            }
            if (!Hud.get.isArmorHud()) {
                this.mc.mcProfiler.startSection("armor");
                for (int k3 = 0; k3 < 10; ++k3) {
                    if (i3 <= 0) continue;
                    int l3 = l + k3 * 8;
                    if (k3 * 2 + 1 < i3) {
                        this.drawTexturedModalRect(l3, j2, 34, 9, 9, 9);
                    }
                    if (k3 * 2 + 1 == i3) {
                        this.drawTexturedModalRect(l3, j2, 25, 9, 9, 9);
                    }
                    if (k3 * 2 + 1 <= i3) continue;
                    this.drawTexturedModalRect(l3, j2, 16, 9, 9, 9);
                }
            }
            this.mc.mcProfiler.endStartSection("health");
            for (int j5 = MathHelper.ceil((f + (float)k1) / 2.0f) - 1; j5 >= 0; --j5) {
                int k5 = 16;
                if (entityplayer.isPotionActive(MobEffects.POISON)) {
                    k5 += 36;
                } else if (entityplayer.isPotionActive(MobEffects.WITHER)) {
                    k5 += 72;
                }
                int i4 = 0;
                if (flag) {
                    i4 = 1;
                }
                int j4 = MathHelper.ceil((float)(j5 + 1) / 10.0f) - 1;
                int k4 = l + j5 % 10 * 8;
                int l4 = j1 - j4 * i2;
                if (i <= 4) {
                    l4 += this.rand.nextInt(2);
                }
                if (l2 <= 0 && j5 == j3) {
                    l4 -= 2;
                }
                int i5 = 0;
                if (entityplayer.world.getWorldInfo().isHardcoreModeEnabled()) {
                    i5 = 5;
                }
                this.drawTexturedModalRect(k4, l4, 16 + i4 * 9, 9 * i5, 9, 9);
                if (flag) {
                    if (j5 * 2 + 1 < j) {
                        this.drawTexturedModalRect(k4, l4, k5 + 54, 9 * i5, 9, 9);
                    }
                    if (j5 * 2 + 1 == j) {
                        this.drawTexturedModalRect(k4, l4, k5 + 63, 9 * i5, 9, 9);
                    }
                }
                if (l2 > 0) {
                    if (l2 == k1 && k1 % 2 == 1) {
                        this.drawTexturedModalRect(k4, l4, k5 + 153, 9 * i5, 9, 9);
                        --l2;
                        continue;
                    }
                    this.drawTexturedModalRect(k4, l4, k5 + 144, 9 * i5, 9, 9);
                    l2 -= 2;
                    continue;
                }
                if (j5 * 2 + 1 < i) {
                    this.drawTexturedModalRect(k4, l4, k5 + 36, 9 * i5, 9, 9);
                }
                if (j5 * 2 + 1 != i) continue;
                this.drawTexturedModalRect(k4, l4, k5 + 45, 9 * i5, 9, 9);
            }
            Entity entity2 = entityplayer.getRidingEntity();
            if (entity2 == null || !(entity2 instanceof EntityLivingBase)) {
                this.mc.mcProfiler.endStartSection("food");
                for (int l5 = 0; l5 < 10; ++l5) {
                    int j6 = j1;
                    int l6 = 16;
                    int j7 = 0;
                    if (entityplayer.isPotionActive(MobEffects.HUNGER)) {
                        l6 += 36;
                        j7 = 13;
                    }
                    if (entityplayer.getFoodStats().getSaturationLevel() <= 0.0f && this.updateCounter % (k * 3 + 1) == 0) {
                        j6 = j1 + (this.rand.nextInt(3) - 1);
                    }
                    int l7 = i1 - l5 * 8 - 9;
                    this.drawTexturedModalRect(l7, j6, 16 + j7 * 9, 27, 9, 9);
                    if (l5 * 2 + 1 < k) {
                        this.drawTexturedModalRect(l7, j6, l6 + 36, 27, 9, 9);
                    }
                    if (l5 * 2 + 1 != k) continue;
                    this.drawTexturedModalRect(l7, j6, l6 + 45, 27, 9, 9);
                }
            }
            this.mc.mcProfiler.endStartSection("air");
            if (entityplayer.isInsideOfMaterial(Material.WATER)) {
                int i6 = Minecraft.player.getAir();
                int k6 = MathHelper.ceil((double)(i6 - 2) * 10.0 / 300.0);
                int i7 = MathHelper.ceil((double)i6 * 10.0 / 300.0) - k6;
                for (int k7 = 0; k7 < k6 + i7; ++k7) {
                    if (k7 < k6) {
                        this.drawTexturedModalRect(i1 - k7 * 8 - 9, k2, 16, 18, 9, 9);
                        continue;
                    }
                    this.drawTexturedModalRect(i1 - k7 * 8 - 9, k2, 25, 18, 9, 9);
                }
            }
            this.mc.mcProfiler.endSection();
        }
    }

    private void renderMountHealth(ScaledResolution p_184047_1_) {
        EntityPlayer entityplayer;
        Entity entity;
        Entity entity2 = this.mc.getRenderViewEntity();
        if (entity2 instanceof EntityPlayer && (entity = (entityplayer = (EntityPlayer)entity2).getRidingEntity()) instanceof EntityLivingBase) {
            EntityLivingBase entitylivingbase = (EntityLivingBase)entity;
            this.mc.mcProfiler.endStartSection("mountHealth");
            int i = (int)Math.ceil(entitylivingbase.getHealth());
            float f = entitylivingbase.getMaxHealth();
            int j = (int)(f + 0.5f) / 2;
            if (j > 30) {
                j = 30;
            }
            int k = p_184047_1_.getScaledHeight() - 39;
            int l = p_184047_1_.getScaledWidth() / 2 + 91;
            int i1 = k;
            int j1 = 0;
            boolean flag = false;
            while (j > 0) {
                int k1 = Math.min(j, 10);
                j -= k1;
                for (int l1 = 0; l1 < k1; ++l1) {
                    int i2 = 52;
                    int j2 = 0;
                    int k2 = l - l1 * 8 - 9;
                    this.drawTexturedModalRect(k2, i1, 52 + j2 * 9, 9, 9, 9);
                    if (l1 * 2 + 1 + j1 < i) {
                        this.drawTexturedModalRect(k2, i1, 88, 9, 9, 9);
                    }
                    if (l1 * 2 + 1 + j1 != i) continue;
                    this.drawTexturedModalRect(k2, i1, 97, 9, 9, 9);
                }
                i1 -= 10;
                j1 += 20;
            }
        }
    }

    private void renderPumpkinOverlay(ScaledResolution scaledRes) {
        GlStateManager.disableDepth();
        GlStateManager.depthMask(false);
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.disableAlpha();
        this.mc.getTextureManager().bindTexture(PUMPKIN_BLUR_TEX_PATH);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferbuilder.pos(0.0, scaledRes.getScaledHeight(), -90.0).tex(0.0, 1.0).endVertex();
        bufferbuilder.pos(scaledRes.getScaledWidth(), scaledRes.getScaledHeight(), -90.0).tex(1.0, 1.0).endVertex();
        bufferbuilder.pos(scaledRes.getScaledWidth(), 0.0, -90.0).tex(1.0, 0.0).endVertex();
        bufferbuilder.pos(0.0, 0.0, -90.0).tex(0.0, 0.0).endVertex();
        tessellator.draw();
        GlStateManager.depthMask(true);
        GlStateManager.enableDepth();
        GlStateManager.enableAlpha();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
    }

    private void renderVignette(float lightLevel, ScaledResolution scaledRes) {
        if (!Config.isVignetteEnabled()) {
            GlStateManager.enableDepth();
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        } else {
            lightLevel = 1.0f - lightLevel;
            lightLevel = MathHelper.clamp(lightLevel, 0.0f, 1.0f);
            WorldBorder worldborder = this.mc.world.getWorldBorder();
            float f = (float)worldborder.getClosestDistance(Minecraft.player);
            double d0 = Math.min(worldborder.getResizeSpeed() * (double)worldborder.getWarningTime() * 1000.0, Math.abs(worldborder.getTargetSize() - worldborder.getDiameter()));
            double d1 = Math.max((double)worldborder.getWarningDistance(), d0);
            f = (double)f < d1 ? 1.0f - (float)((double)f / d1) : 0.0f;
            this.prevVignetteBrightness = (float)((double)this.prevVignetteBrightness + (double)(lightLevel - this.prevVignetteBrightness) * 0.01);
            GlStateManager.disableDepth();
            GlStateManager.depthMask(false);
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.ZERO, GlStateManager.DestFactor.ONE_MINUS_SRC_COLOR, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            if (f > 0.0f) {
                GlStateManager.color(0.0f, f, f, 1.0f);
            } else {
                GlStateManager.color(this.prevVignetteBrightness, this.prevVignetteBrightness, this.prevVignetteBrightness, 1.0f);
            }
            this.mc.getTextureManager().bindTexture(VIGNETTE_TEX_PATH);
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder bufferbuilder = tessellator.getBuffer();
            bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
            bufferbuilder.pos(0.0, scaledRes.getScaledHeight(), -90.0).tex(0.0, 1.0).endVertex();
            bufferbuilder.pos(scaledRes.getScaledWidth(), scaledRes.getScaledHeight(), -90.0).tex(1.0, 1.0).endVertex();
            bufferbuilder.pos(scaledRes.getScaledWidth(), 0.0, -90.0).tex(1.0, 0.0).endVertex();
            bufferbuilder.pos(0.0, 0.0, -90.0).tex(0.0, 0.0).endVertex();
            tessellator.draw();
            GlStateManager.depthMask(true);
            GlStateManager.enableDepth();
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        }
    }

    private void renderPortal(float timeInPortal, ScaledResolution scaledRes) {
        if (timeInPortal < 1.0f) {
            timeInPortal *= timeInPortal;
            timeInPortal *= timeInPortal;
            timeInPortal = timeInPortal * 0.8f + 0.2f;
        }
        GlStateManager.disableAlpha();
        GlStateManager.disableDepth();
        GlStateManager.depthMask(false);
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.color(1.0f, 1.0f, 1.0f, timeInPortal);
        this.mc.getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        TextureAtlasSprite textureatlassprite = this.mc.getBlockRendererDispatcher().getBlockModelShapes().getTexture(Blocks.PORTAL.getDefaultState());
        float f = textureatlassprite.getMinU();
        float f1 = textureatlassprite.getMinV();
        float f2 = textureatlassprite.getMaxU();
        float f3 = textureatlassprite.getMaxV();
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferbuilder.pos(0.0, scaledRes.getScaledHeight(), -90.0).tex(f, f3).endVertex();
        bufferbuilder.pos(scaledRes.getScaledWidth(), scaledRes.getScaledHeight(), -90.0).tex(f2, f3).endVertex();
        bufferbuilder.pos(scaledRes.getScaledWidth(), 0.0, -90.0).tex(f2, f1).endVertex();
        bufferbuilder.pos(0.0, 0.0, -90.0).tex(f, f1).endVertex();
        tessellator.draw();
        GlStateManager.depthMask(true);
        GlStateManager.enableDepth();
        GlStateManager.enableAlpha();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
    }

    private void renderHotbarItem(int p_184044_1_, int p_184044_2_, float p_184044_3_, EntityPlayer player, ItemStack stack) {
        if (!stack.func_190926_b()) {
            float f = (float)stack.func_190921_D() - p_184044_3_;
            if (f > 0.0f) {
                GlStateManager.pushMatrix();
                float f1 = 1.0f + f / 5.0f;
                GlStateManager.translate(p_184044_1_ + 8, p_184044_2_ + 12, 0.0f);
                GlStateManager.scale(1.0f / f1, (f1 + 1.0f) / 2.0f, 1.0f);
                GlStateManager.translate(-(p_184044_1_ + 8), -(p_184044_2_ + 12), 0.0f);
            }
            this.itemRenderer.renderItemAndEffectIntoGUI(player, stack, p_184044_1_, p_184044_2_);
            if (f > 0.0f) {
                GlStateManager.popMatrix();
            }
            this.itemRenderer.renderItemOverlays(Fonts.comfortaa_18, stack, p_184044_1_, p_184044_2_);
        }
    }

    public void updateTick() {
        if (this.mc.world == null) {
            TextureAnimations.updateAnimations();
        }
        if (this.recordPlayingUpFor > 0) {
            --this.recordPlayingUpFor;
        }
        if (this.titlesTimer > 0) {
            --this.titlesTimer;
            if (this.titlesTimer <= 0) {
                this.displayedTitle = "";
                this.displayedSubTitle = "";
            }
        }
        ++this.updateCounter;
        if (Minecraft.player != null) {
            ItemStack itemstack = Minecraft.player.inventory.getCurrentItem();
            if (itemstack.func_190926_b()) {
                this.remainingHighlightTicks = 0;
            } else if (!this.highlightingItemStack.func_190926_b() && itemstack.getItem() == this.highlightingItemStack.getItem() && ItemStack.areItemStackTagsEqual(itemstack, this.highlightingItemStack) && (itemstack.isItemStackDamageable() || itemstack.getMetadata() == this.highlightingItemStack.getMetadata())) {
                if (this.remainingHighlightTicks > 0) {
                    --this.remainingHighlightTicks;
                }
            } else {
                this.remainingHighlightTicks = 40;
            }
            this.highlightingItemStack = itemstack;
        }
    }

    public void setRecordPlayingMessage(String recordName) {
        this.setRecordPlaying(I18n.format("record.nowPlaying", recordName), true);
    }

    public void setRecordPlaying(String message, boolean isPlaying) {
        this.recordPlaying = message;
        this.recordPlayingUpFor = 60;
        this.recordIsPlaying = isPlaying;
    }

    public void displayTitle(String title, String subTitle, int timeFadeIn, int displayTime, int timeFadeOut) {
        if (title == null && subTitle == null && timeFadeIn < 0 && displayTime < 0 && timeFadeOut < 0) {
            this.displayedTitle = "";
            this.displayedSubTitle = "";
            this.titlesTimer = 0;
        } else if (title != null) {
            this.displayedTitle = title;
            this.titlesTimer = this.titleFadeIn + this.titleDisplayTime + this.titleFadeOut;
        } else if (subTitle != null) {
            this.displayedSubTitle = subTitle;
        } else {
            if (timeFadeIn >= 0) {
                this.titleFadeIn = timeFadeIn;
            }
            if (displayTime >= 0) {
                this.titleDisplayTime = displayTime;
            }
            if (timeFadeOut >= 0) {
                this.titleFadeOut = timeFadeOut;
            }
            if (this.titlesTimer > 0) {
                this.titlesTimer = this.titleFadeIn + this.titleDisplayTime + this.titleFadeOut;
            }
        }
    }

    public void setRecordPlaying(ITextComponent component, boolean isPlaying) {
        this.setRecordPlaying(component.getUnformattedText(), isPlaying);
    }

    public void func_191742_a(ChatType p_191742_1_, ITextComponent p_191742_2_) {
        for (IChatListener ichatlistener : this.field_191743_I.get((Object)p_191742_1_)) {
            ichatlistener.func_192576_a(p_191742_1_, p_191742_2_);
        }
    }

    public GuiNewChat getChatGUI() {
        return this.persistantChatGUI;
    }

    public int getUpdateCounter() {
        return this.updateCounter;
    }

    public FontRenderer getFontRenderer() {
        return this.mc.fontRendererObj;
    }

    public GuiSpectator getSpectatorGui() {
        return this.spectatorGui;
    }

    public GuiPlayerTabOverlay getTabList() {
        return this.overlayPlayerList;
    }

    public void resetPlayersOverlayFooterHeader() {
        this.overlayPlayerList.resetFooterHeader();
        this.overlayBoss.clearBossInfos();
        this.mc.func_193033_an().func_191788_b();
    }

    public GuiBossOverlay getBossOverlay() {
        return this.overlayBoss;
    }
}

