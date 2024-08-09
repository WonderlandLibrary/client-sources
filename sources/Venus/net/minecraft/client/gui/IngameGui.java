/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Ordering;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.datafixers.util.Pair;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import mpp.venusfr.events.EventCancelOverlay;
import mpp.venusfr.events.EventDisplay;
import mpp.venusfr.functions.api.FunctionRegistry;
import mpp.venusfr.functions.impl.render.Crosshair;
import mpp.venusfr.functions.impl.render.HUD;
import mpp.venusfr.utils.math.StopWatch;
import mpp.venusfr.utils.render.ColorUtils;
import mpp.venusfr.utils.render.DisplayUtils;
import mpp.venusfr.venusfr;
import net.minecraft.block.Blocks;
import net.minecraft.client.GameSettings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.NewChatGui;
import net.minecraft.client.gui.SpectatorGui;
import net.minecraft.client.gui.chat.IChatListener;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.gui.chat.NormalChatListener;
import net.minecraft.client.gui.chat.OverlayChatListener;
import net.minecraft.client.gui.overlay.BossOverlayGui;
import net.minecraft.client.gui.overlay.DebugOverlayGui;
import net.minecraft.client.gui.overlay.PlayerTabOverlayGui;
import net.minecraft.client.gui.overlay.SubtitleOverlayGui;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.PotionSpriteUploader;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.settings.AttackIndicatorStatus;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.ColorHelper;
import net.minecraft.util.FoodStats;
import net.minecraft.util.HandSide;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TextProcessing;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.GameType;
import net.minecraft.world.border.WorldBorder;
import net.optifine.Config;
import net.optifine.CustomColors;
import net.optifine.CustomItems;
import net.optifine.TextureAnimations;
import net.optifine.reflect.Reflector;
import org.lwjgl.opengl.GL11;

public class IngameGui
extends AbstractGui {
    private static final ResourceLocation VIGNETTE_TEX_PATH = new ResourceLocation("textures/misc/vignette.png");
    private static final ResourceLocation WIDGETS_TEX_PATH = new ResourceLocation("textures/gui/widgets.png");
    private static final ResourceLocation PUMPKIN_BLUR_TEX_PATH = new ResourceLocation("textures/misc/pumpkinblur.png");
    private static final ITextComponent field_243249_e = new TranslationTextComponent("demo.demoExpired");
    private final Random rand = new Random();
    private final Minecraft mc;
    private final ItemRenderer itemRenderer;
    private final NewChatGui persistantChatGUI;
    private int ticks;
    @Nullable
    private ITextComponent overlayMessage;
    private int overlayMessageTime;
    private boolean animateOverlayMessageColor;
    public float prevVignetteBrightness = 1.0f;
    private int remainingHighlightTicks;
    private ItemStack highlightingItemStack = ItemStack.EMPTY;
    private final DebugOverlayGui overlayDebug;
    private final SubtitleOverlayGui overlaySubtitle;
    private final SpectatorGui spectatorGui;
    private final PlayerTabOverlayGui overlayPlayerList;
    private final BossOverlayGui overlayBoss;
    private int titlesTimer;
    @Nullable
    private ITextComponent displayedTitle;
    @Nullable
    private ITextComponent displayedSubTitle;
    private int titleFadeIn;
    private int titleDisplayTime;
    private int titleFadeOut;
    private int playerHealth;
    private int lastPlayerHealth;
    private long lastSystemTime;
    private long healthUpdateCounter;
    private int scaledWidth;
    private int scaledHeight;
    private final Map<ChatType, List<IChatListener>> chatListeners = Maps.newHashMap();
    public StopWatch watch = new StopWatch();
    EventDisplay eventDisplay = new EventDisplay(new MatrixStack(), 1.0f);

    public IngameGui(Minecraft minecraft) {
        this.mc = minecraft;
        this.itemRenderer = minecraft.getItemRenderer();
        this.overlayDebug = new DebugOverlayGui(minecraft);
        this.spectatorGui = new SpectatorGui(minecraft);
        this.persistantChatGUI = new NewChatGui(minecraft);
        this.overlayPlayerList = new PlayerTabOverlayGui(minecraft, this);
        this.overlayBoss = new BossOverlayGui(minecraft);
        this.overlaySubtitle = new SubtitleOverlayGui(minecraft);
        for (ChatType chatType : ChatType.values()) {
            this.chatListeners.put(chatType, Lists.newArrayList());
        }
        NarratorChatListener narratorChatListener = NarratorChatListener.INSTANCE;
        this.chatListeners.get((Object)ChatType.CHAT).add(new NormalChatListener(minecraft));
        this.chatListeners.get((Object)ChatType.CHAT).add(narratorChatListener);
        this.chatListeners.get((Object)ChatType.SYSTEM).add(new NormalChatListener(minecraft));
        this.chatListeners.get((Object)ChatType.SYSTEM).add(narratorChatListener);
        this.chatListeners.get((Object)ChatType.GAME_INFO).add(new OverlayChatListener(minecraft));
        this.setDefaultTitlesTimes();
    }

    public void setDefaultTitlesTimes() {
        this.titleFadeIn = 10;
        this.titleDisplayTime = 70;
        this.titleFadeOut = 20;
    }

    public void renderIngameGui(MatrixStack matrixStack, float f) {
        int n;
        float f2;
        this.eventDisplay.setMatrixStack(matrixStack);
        this.eventDisplay.setPartialTicks(f);
        this.mc.gameRenderer.setupOverlayRendering(2);
        this.eventDisplay.setType(EventDisplay.Type.PRE);
        if (this.eventDisplay.getType() == EventDisplay.Type.PRE) {
            venusfr.getInstance().getEventBus().post(this.eventDisplay);
        }
        this.mc.gameRenderer.setupOverlayRendering();
        this.mc.gameRenderer.setupOverlayRendering(2);
        this.eventDisplay.setType(EventDisplay.Type.HIGH);
        if (this.eventDisplay.getType() == EventDisplay.Type.HIGH) {
            venusfr.getInstance().getEventBus().post(this.eventDisplay);
        }
        this.mc.gameRenderer.setupOverlayRendering();
        FunctionRegistry functionRegistry = venusfr.getInstance().getFunctionRegistry();
        HUD hUD = functionRegistry.getHud();
        Crosshair crosshair = functionRegistry.getCrosshair();
        this.scaledWidth = this.mc.getMainWindow().getScaledWidth();
        this.scaledHeight = this.mc.getMainWindow().getScaledHeight();
        FontRenderer fontRenderer = this.getFontRenderer();
        RenderSystem.enableBlend();
        if (Config.isVignetteEnabled()) {
            this.renderVignette(this.mc.getRenderViewEntity());
        } else {
            RenderSystem.enableDepthTest();
            RenderSystem.defaultBlendFunc();
        }
        ItemStack itemStack = this.mc.player.inventory.armorItemInSlot(3);
        if (this.mc.gameSettings.getPointOfView().func_243192_a() && itemStack.getItem() == Blocks.CARVED_PUMPKIN.asItem()) {
            this.renderPumpkinOverlay();
        }
        if ((f2 = MathHelper.lerp(f, this.mc.player.prevTimeInPortal, this.mc.player.timeInPortal)) > 0.0f && !this.mc.player.isPotionActive(Effects.NAUSEA)) {
            this.renderPortal(f2);
        }
        if (this.mc.playerController.getCurrentGameType() == GameType.SPECTATOR) {
            this.spectatorGui.func_238528_a_(matrixStack, f);
        } else if (!this.mc.gameSettings.hideGUI) {
            this.renderHotbar(f, matrixStack);
        }
        if (!this.mc.gameSettings.hideGUI) {
            RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
            this.mc.getTextureManager().bindTexture(GUI_ICONS_LOCATION);
            RenderSystem.enableBlend();
            RenderSystem.enableAlphaTest();
            if (!crosshair.isState()) {
                this.func_238456_d_(matrixStack);
            }
            GlStateManager.enableAlphaTest();
            RenderSystem.defaultBlendFunc();
            this.mc.getProfiler().startSection("bossHealth");
            this.overlayBoss.func_238484_a_(matrixStack);
            this.mc.getProfiler().endSection();
            RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
            this.mc.getTextureManager().bindTexture(GUI_ICONS_LOCATION);
            if (this.mc.playerController.shouldDrawHUD()) {
                this.func_238457_e_(matrixStack);
            }
            this.func_238458_f_(matrixStack);
            RenderSystem.disableBlend();
            int n2 = this.scaledWidth / 2 - 91;
            if (this.mc.player.isRidingHorse()) {
                this.renderHorseJumpBar(matrixStack, n2);
            } else if (this.mc.playerController.gameIsSurvivalOrAdventure()) {
                this.func_238454_b_(matrixStack, n2);
            }
            if (this.mc.gameSettings.heldItemTooltips && this.mc.playerController.getCurrentGameType() != GameType.SPECTATOR) {
                this.func_238453_b_(matrixStack);
            } else if (this.mc.player.isSpectator()) {
                this.spectatorGui.func_238527_a_(matrixStack);
            }
        }
        if (this.mc.player.getSleepTimer() > 0) {
            this.mc.getProfiler().startSection("sleep");
            RenderSystem.disableDepthTest();
            RenderSystem.disableAlphaTest();
            float f3 = this.mc.player.getSleepTimer();
            float f4 = f3 / 100.0f;
            if (f4 > 1.0f) {
                f4 = 1.0f - (f3 - 100.0f) / 10.0f;
            }
            n = (int)(220.0f * f4) << 24 | 0x101020;
            IngameGui.fill(matrixStack, 0, 0, this.scaledWidth, this.scaledHeight, n);
            RenderSystem.enableAlphaTest();
            RenderSystem.enableDepthTest();
            this.mc.getProfiler().endSection();
            RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        }
        if (this.mc.isDemo()) {
            this.func_238455_c_(matrixStack);
        }
        if (!hUD.isState()) {
            this.renderPotionIcons(matrixStack);
        }
        if (this.mc.gameSettings.showDebugInfo) {
            this.overlayDebug.render(matrixStack);
        }
        if (!this.mc.gameSettings.hideGUI) {
            ScoreObjective scoreObjective;
            int n3;
            int n4;
            if (this.overlayMessage != null && this.overlayMessageTime > 0) {
                this.mc.getProfiler().startSection("overlayMessage");
                float f5 = (float)this.overlayMessageTime - f;
                int n5 = (int)(f5 * 255.0f / 20.0f);
                if (n5 > 255) {
                    n5 = 255;
                }
                if (n5 > 8) {
                    RenderSystem.pushMatrix();
                    RenderSystem.translatef(this.scaledWidth / 2, this.scaledHeight - 68, 0.0f);
                    RenderSystem.enableBlend();
                    RenderSystem.defaultBlendFunc();
                    n = 0xFFFFFF;
                    if (this.animateOverlayMessageColor) {
                        n = MathHelper.hsvToRGB(f5 / 50.0f, 0.7f, 0.6f) & 0xFFFFFF;
                    }
                    n4 = n5 << 24 & 0xFF000000;
                    n3 = fontRenderer.getStringPropertyWidth(this.overlayMessage);
                    this.func_238448_a_(matrixStack, fontRenderer, -4, n3, 0xFFFFFF | n4);
                    fontRenderer.func_243248_b(matrixStack, this.overlayMessage, -n3 / 2, -4.0f, n | n4);
                    RenderSystem.disableBlend();
                    RenderSystem.popMatrix();
                }
                this.mc.getProfiler().endSection();
            }
            EventCancelOverlay eventCancelOverlay = new EventCancelOverlay(EventCancelOverlay.Overlays.TITLES);
            venusfr.getInstance().getEventBus().post(eventCancelOverlay);
            if (eventCancelOverlay.isCancel()) {
                eventCancelOverlay.open();
            } else if (this.displayedTitle != null && this.titlesTimer > 0) {
                this.mc.getProfiler().startSection("titleAndSubtitle");
                float f6 = (float)this.titlesTimer - f;
                n = 255;
                if (this.titlesTimer > this.titleFadeOut + this.titleDisplayTime) {
                    float f7 = (float)(this.titleFadeIn + this.titleDisplayTime + this.titleFadeOut) - f6;
                    n = (int)(f7 * 255.0f / (float)this.titleFadeIn);
                }
                if (this.titlesTimer <= this.titleFadeOut) {
                    n = (int)(f6 * 255.0f / (float)this.titleFadeOut);
                }
                if ((n = MathHelper.clamp(n, 0, 255)) > 8) {
                    RenderSystem.pushMatrix();
                    RenderSystem.translatef(this.scaledWidth / 2, this.scaledHeight / 2, 0.0f);
                    RenderSystem.enableBlend();
                    RenderSystem.defaultBlendFunc();
                    RenderSystem.pushMatrix();
                    RenderSystem.scalef(4.0f, 4.0f, 4.0f);
                    n4 = n << 24 & 0xFF000000;
                    n3 = fontRenderer.getStringPropertyWidth(this.displayedTitle);
                    this.func_238448_a_(matrixStack, fontRenderer, -10, n3, 0xFFFFFF | n4);
                    fontRenderer.func_243246_a(matrixStack, this.displayedTitle, -n3 / 2, -10.0f, 0xFFFFFF | n4);
                    RenderSystem.popMatrix();
                    if (this.displayedSubTitle != null) {
                        RenderSystem.pushMatrix();
                        RenderSystem.scalef(2.0f, 2.0f, 2.0f);
                        int n6 = fontRenderer.getStringPropertyWidth(this.displayedSubTitle);
                        this.func_238448_a_(matrixStack, fontRenderer, 5, n6, 0xFFFFFF | n4);
                        fontRenderer.func_243246_a(matrixStack, this.displayedSubTitle, -n6 / 2, 5.0f, 0xFFFFFF | n4);
                        RenderSystem.popMatrix();
                    }
                    RenderSystem.disableBlend();
                    RenderSystem.popMatrix();
                }
                this.mc.getProfiler().endSection();
            }
            this.overlaySubtitle.render(matrixStack);
            Scoreboard scoreboard = this.mc.world.getScoreboard();
            ScoreObjective scoreObjective2 = null;
            ScorePlayerTeam scorePlayerTeam = scoreboard.getPlayersTeam(this.mc.player.getScoreboardName());
            if (scorePlayerTeam != null && (n3 = scorePlayerTeam.getColor().getColorIndex()) >= 0) {
                scoreObjective2 = scoreboard.getObjectiveInDisplaySlot(3 + n3);
            }
            ScoreObjective scoreObjective3 = scoreObjective = scoreObjective2 != null ? scoreObjective2 : scoreboard.getObjectiveInDisplaySlot(1);
            if (scoreObjective != null) {
                this.func_238447_a_(matrixStack, scoreObjective);
            }
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.disableAlphaTest();
            RenderSystem.pushMatrix();
            RenderSystem.translatef(0.0f, this.scaledHeight - 48, 0.0f);
            this.mc.getProfiler().startSection("chat");
            this.persistantChatGUI.func_238492_a_(matrixStack, this.ticks);
            this.mc.getProfiler().endSection();
            RenderSystem.popMatrix();
            scoreObjective = scoreboard.getObjectiveInDisplaySlot(0);
            if (this.mc.gameSettings.keyBindPlayerList.isKeyDown() && (!this.mc.isIntegratedServerRunning() || this.mc.player.connection.getPlayerInfoMap().size() > 1 || scoreObjective != null)) {
                this.overlayPlayerList.setVisible(false);
                this.overlayPlayerList.func_238523_a_(matrixStack, this.scaledWidth, scoreboard, scoreObjective);
            } else {
                this.overlayPlayerList.setVisible(true);
            }
        }
        this.eventDisplay.setMatrixStack(matrixStack);
        this.eventDisplay.setPartialTicks(f);
        venusfr.getInstance().getNotificationManager().draw(matrixStack);
        this.mc.gameRenderer.setupOverlayRendering(2);
        this.eventDisplay.setType(EventDisplay.Type.POST);
        if (this.eventDisplay.getType() == EventDisplay.Type.POST) {
            venusfr.getInstance().getEventBus().post(this.eventDisplay);
        }
        this.mc.gameRenderer.setupOverlayRendering();
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.enableAlphaTest();
    }

    private void func_238448_a_(MatrixStack matrixStack, FontRenderer fontRenderer, int n, int n2, int n3) {
        int n4 = this.mc.gameSettings.getTextBackgroundColor(0.0f);
        if (n4 != 0) {
            int n5 = -n2 / 2;
            IngameGui.fill(matrixStack, n5 - 2, n - 2, n5 + n2 + 2, n + 9 + 2, ColorHelper.PackedColor.blendColors(n4, n3));
        }
    }

    private void func_238456_d_(MatrixStack matrixStack) {
        GameSettings gameSettings = this.mc.gameSettings;
        if (gameSettings.getPointOfView().func_243192_a() && (this.mc.playerController.getCurrentGameType() != GameType.SPECTATOR || this.isTargetNamedMenuProvider(this.mc.objectMouseOver))) {
            if (gameSettings.showDebugInfo && !gameSettings.hideGUI && !this.mc.player.hasReducedDebug() && !gameSettings.reducedDebugInfo) {
                RenderSystem.pushMatrix();
                RenderSystem.translatef(this.scaledWidth / 2, this.scaledHeight / 2, this.getBlitOffset());
                ActiveRenderInfo activeRenderInfo = this.mc.gameRenderer.getActiveRenderInfo();
                RenderSystem.rotatef(activeRenderInfo.getPitch(), -1.0f, 0.0f, 0.0f);
                RenderSystem.rotatef(activeRenderInfo.getYaw(), 0.0f, 1.0f, 0.0f);
                RenderSystem.scalef(-1.0f, -1.0f, -1.0f);
                RenderSystem.renderCrosshair(10);
                RenderSystem.popMatrix();
            } else {
                RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.ONE_MINUS_DST_COLOR, GlStateManager.DestFactor.ONE_MINUS_SRC_COLOR, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
                int n = 15;
                this.blit(matrixStack, (this.scaledWidth - 15) / 2, (this.scaledHeight - 15) / 2, 0, 0, 15, 15);
                if (this.mc.gameSettings.attackIndicator == AttackIndicatorStatus.CROSSHAIR) {
                    float f = this.mc.player.getCooledAttackStrength(0.0f);
                    boolean bl = false;
                    if (this.mc.pointedEntity != null && this.mc.pointedEntity instanceof LivingEntity && f >= 1.0f) {
                        bl = this.mc.player.getCooldownPeriod() > 5.0f;
                        bl &= this.mc.pointedEntity.isAlive();
                    }
                    int n2 = this.scaledHeight / 2 - 7 + 16;
                    int n3 = this.scaledWidth / 2 - 8;
                    if (bl) {
                        this.blit(matrixStack, n3, n2, 68, 94, 16, 16);
                    } else if (f < 1.0f) {
                        int n4 = (int)(f * 17.0f);
                        this.blit(matrixStack, n3, n2, 36, 94, 16, 4);
                        this.blit(matrixStack, n3, n2, 52, 94, n4, 4);
                    }
                }
            }
        }
    }

    private boolean isTargetNamedMenuProvider(RayTraceResult rayTraceResult) {
        if (rayTraceResult == null) {
            return true;
        }
        if (rayTraceResult.getType() == RayTraceResult.Type.ENTITY) {
            return ((EntityRayTraceResult)rayTraceResult).getEntity() instanceof INamedContainerProvider;
        }
        if (rayTraceResult.getType() == RayTraceResult.Type.BLOCK) {
            ClientWorld clientWorld = this.mc.world;
            BlockPos blockPos = ((BlockRayTraceResult)rayTraceResult).getPos();
            return clientWorld.getBlockState(blockPos).getContainer(clientWorld, blockPos) != null;
        }
        return true;
    }

    protected void renderPotionIcons(MatrixStack matrixStack) {
        Collection<EffectInstance> collection = this.mc.player.getActivePotionEffects();
        if (!collection.isEmpty()) {
            RenderSystem.enableBlend();
            int n = 0;
            int n2 = 0;
            PotionSpriteUploader potionSpriteUploader = this.mc.getPotionSpriteUploader();
            ArrayList<Runnable> arrayList = Lists.newArrayListWithExpectedSize(collection.size());
            this.mc.getTextureManager().bindTexture(ContainerScreen.INVENTORY_BACKGROUND);
            Iterator<EffectInstance> iterator2 = Ordering.natural().reverse().sortedCopy(collection).iterator();
            while (true) {
                if (!iterator2.hasNext()) {
                    arrayList.forEach(Runnable::run);
                    return;
                }
                EffectInstance effectInstance = iterator2.next();
                Effect effect = effectInstance.getPotion();
                if (Reflector.IForgeEffectInstance_shouldRenderHUD.exists()) {
                    if (!Reflector.callBoolean(effectInstance, Reflector.IForgeEffectInstance_shouldRenderHUD, new Object[0])) continue;
                    this.mc.getTextureManager().bindTexture(ContainerScreen.INVENTORY_BACKGROUND);
                }
                if (!effectInstance.isShowIcon()) continue;
                int n3 = this.scaledWidth;
                int n4 = 1;
                if (this.mc.isDemo()) {
                    n4 += 15;
                }
                if (effect.isBeneficial()) {
                    n3 -= 25 * ++n;
                } else {
                    n3 -= 25 * ++n2;
                    n4 += 26;
                }
                RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
                float f = 1.0f;
                if (effectInstance.isAmbient()) {
                    this.blit(matrixStack, n3, n4, 165, 166, 24, 24);
                } else {
                    this.blit(matrixStack, n3, n4, 141, 166, 24, 24);
                    if (effectInstance.getDuration() <= 200) {
                        int n5 = 10 - effectInstance.getDuration() / 20;
                        f = MathHelper.clamp((float)effectInstance.getDuration() / 10.0f / 5.0f * 0.5f, 0.0f, 0.5f) + MathHelper.cos((float)effectInstance.getDuration() * (float)Math.PI / 5.0f) * MathHelper.clamp((float)n5 / 10.0f * 0.25f, 0.0f, 0.25f);
                    }
                }
                TextureAtlasSprite textureAtlasSprite = potionSpriteUploader.getSprite(effect);
                int n6 = n3;
                int n7 = n4;
                float f2 = f;
                arrayList.add(() -> this.lambda$renderPotionIcons$0(textureAtlasSprite, f2, matrixStack, n6, n7));
                if (!Reflector.IForgeEffectInstance_renderHUDEffect.exists()) continue;
                Reflector.call(effectInstance, Reflector.IForgeEffectInstance_renderHUDEffect, this, matrixStack, n3, n4, this.getBlitOffset(), Float.valueOf(f));
            }
        }
    }

    protected void renderHotbar(float f, MatrixStack matrixStack) {
        PlayerEntity playerEntity = this.getRenderViewPlayer();
        if (playerEntity != null) {
            float f2;
            Runnable runnable;
            int n;
            int n2;
            int n3;
            int n4;
            RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
            this.mc.getTextureManager().bindTexture(WIDGETS_TEX_PATH);
            ItemStack itemStack = playerEntity.getHeldItemOffhand();
            HandSide handSide = playerEntity.getPrimaryHand().opposite();
            int n5 = this.scaledWidth / 2;
            int n6 = this.getBlitOffset();
            int n7 = 182;
            int n8 = 91;
            int n9 = ColorUtils.getColor(1);
            int n10 = ColorUtils.getColor(1);
            boolean bl = venusfr.getInstance().getFunctionRegistry().getHotbar().isState();
            int n11 = this.scaledHeight - 22;
            int n12 = n11 + 3;
            this.setBlitOffset(-90);
            if (bl) {
                n4 = n5 - 91;
                n3 = n11;
                n2 = 182;
                n = 22;
                runnable = () -> IngameGui.lambda$renderHotbar$1(n4, n3, n2, n);
                DisplayUtils.drawRoundedRect((float)n4, (float)n3, (float)n2, (float)(n - 2), 2.5f, new Color(0, 0, 0, 100).getRGB());
                n4 = n5 - 91 - 1 + playerEntity.inventory.currentItem * 20;
                n3 = n11 - 1;
                n2 = 24;
                n = 22;
                DisplayUtils.drawShadow(n4 + 2, n3 + 2, n2 - 4, n - 4, 15, n9, n10);
                DisplayUtils.drawGradientRound(n4 + 2, n3 + 2, n2 - 4, n - 4, 3.0f, n9, n10, n9, n10);
                DisplayUtils.drawRoundedRect((float)(n4 + 2), (float)(n3 + 2), (float)(n2 - 4), (float)(n - 4), 3.0f, new Color(0, 0, 0, 100).getRGB());
            } else {
                this.blit(matrixStack, n5 - 91, n11, 0, 0, 182, 22);
                this.blit(matrixStack, n5 - 91 - 1 + playerEntity.inventory.currentItem * 20, n11 - 1, 0, 22, 24, 22);
            }
            if (!itemStack.isEmpty()) {
                if (handSide == HandSide.LEFT) {
                    if (!bl) {
                        this.blit(matrixStack, n5 - 91 - 29, n11 - 1, 24, 22, 29, 24);
                    } else {
                        n4 = n5 - 91 - 28;
                        n3 = n11;
                        n2 = 20;
                        n = 21;
                        runnable = () -> IngameGui.lambda$renderHotbar$2(n4, n3, n2, n);
                        DisplayUtils.drawShadow(n4, n3, n2, n - 1, 7, n9, n10);
                        DisplayUtils.drawGradientRound(n4, n3, n2, n - 1, 3.0f, n9, n10, n9, n10);
                        DisplayUtils.drawRoundedRect((float)n4, (float)n3, (float)n2, (float)(n - 1), 2.5f, ColorUtils.rgba(0, 0, 0, 100));
                    }
                } else {
                    this.blit(matrixStack, n5 + 91, n11 - 1, 53, 22, 29, 24);
                }
            }
            this.setBlitOffset(n6);
            RenderSystem.enableRescaleNormal();
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            GL11.glEnable(2929);
            CustomItems.setRenderOffHand(false);
            for (n4 = 0; n4 < 9; ++n4) {
                n3 = n5 - 90 + n4 * 20 + 2;
                n2 = n12 - 1;
                this.renderHotbarItem(n3, n2, f, playerEntity, playerEntity.inventory.mainInventory.get(n4));
            }
            if (!itemStack.isEmpty()) {
                CustomItems.setRenderOffHand(true);
                n4 = n12 - 1;
                if (handSide == HandSide.LEFT) {
                    this.renderHotbarItem(n5 - 91 - 26, n4, f, playerEntity, itemStack);
                } else {
                    this.renderHotbarItem(n5 + 91 + 10, n4, f, playerEntity, itemStack);
                }
                CustomItems.setRenderOffHand(false);
            }
            if (this.mc.gameSettings.attackIndicator == AttackIndicatorStatus.HOTBAR && (f2 = this.mc.player.getCooledAttackStrength(0.0f)) < 1.0f) {
                n3 = n11 - 2;
                n2 = n5 + 91 + 6;
                if (handSide == HandSide.RIGHT) {
                    n2 = n5 - 91 - 22;
                }
                this.mc.getTextureManager().bindTexture(AbstractGui.GUI_ICONS_LOCATION);
                n = (int)(f2 * 19.0f);
                RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
                this.blit(matrixStack, n2, n3, 0, 94, 18, 18);
                this.blit(matrixStack, n2, n3 + 18 - n, 18, 112 - n, 18, n);
            }
            RenderSystem.disableRescaleNormal();
            RenderSystem.disableBlend();
        }
    }

    public void renderHorseJumpBar(MatrixStack matrixStack, int n) {
        this.mc.getProfiler().startSection("jumpBar");
        this.mc.getTextureManager().bindTexture(AbstractGui.GUI_ICONS_LOCATION);
        float f = this.mc.player.getHorseJumpPower();
        int n2 = 182;
        int n3 = (int)(f * 183.0f);
        int n4 = this.scaledHeight - 32 + 3;
        this.blit(matrixStack, n, n4, 0, 84, 182, 5);
        if (n3 > 0) {
            this.blit(matrixStack, n, n4, 0, 89, n3, 5);
        }
        this.mc.getProfiler().endSection();
    }

    public void func_238454_b_(MatrixStack matrixStack, int n) {
        int n2;
        int n3;
        this.mc.getProfiler().startSection("expBar");
        this.mc.getTextureManager().bindTexture(AbstractGui.GUI_ICONS_LOCATION);
        int n4 = this.mc.player.xpBarCap();
        if (n4 > 0) {
            n3 = 182;
            int n5 = (int)(this.mc.player.experience * 183.0f);
            n2 = this.scaledHeight - 32 + 3;
            this.blit(matrixStack, n, n2, 0, 64, 182, 5);
            if (n5 > 0) {
                this.blit(matrixStack, n, n2, 0, 69, n5, 5);
            }
        }
        this.mc.getProfiler().endSection();
        if (this.mc.player.experienceLevel > 0) {
            this.mc.getProfiler().startSection("expLevel");
            n3 = 8453920;
            if (Config.isCustomColors()) {
                n3 = CustomColors.getExpBarTextColor(n3);
            }
            String string = "" + this.mc.player.experienceLevel;
            n2 = (this.scaledWidth - this.getFontRenderer().getStringWidth(string)) / 2;
            int n6 = this.scaledHeight - 31 - 4;
            this.getFontRenderer().drawString(matrixStack, string, n2 + 1, n6, 0);
            this.getFontRenderer().drawString(matrixStack, string, n2 - 1, n6, 0);
            this.getFontRenderer().drawString(matrixStack, string, n2, n6 + 1, 0);
            this.getFontRenderer().drawString(matrixStack, string, n2, n6 - 1, 0);
            this.getFontRenderer().drawString(matrixStack, string, n2, n6, n3);
            this.mc.getProfiler().endSection();
        }
    }

    public void func_238453_b_(MatrixStack matrixStack) {
        this.mc.getProfiler().startSection("selectedItemName");
        if (this.remainingHighlightTicks > 0 && !this.highlightingItemStack.isEmpty()) {
            int n;
            IFormattableTextComponent iFormattableTextComponent = new StringTextComponent("").append(this.highlightingItemStack.getDisplayName()).mergeStyle(this.highlightingItemStack.getRarity().color);
            if (this.highlightingItemStack.hasDisplayName()) {
                iFormattableTextComponent.mergeStyle(TextFormatting.ITALIC);
            }
            ITextComponent iTextComponent = iFormattableTextComponent;
            if (Reflector.IForgeItemStack_getHighlightTip.exists()) {
                iTextComponent = (ITextComponent)Reflector.call(this.highlightingItemStack, Reflector.IForgeItemStack_getHighlightTip, iFormattableTextComponent);
            }
            int n2 = this.getFontRenderer().getStringPropertyWidth(iTextComponent);
            int n3 = (this.scaledWidth - n2) / 2;
            int n4 = this.scaledHeight - 59;
            if (!this.mc.playerController.shouldDrawHUD()) {
                n4 += 14;
            }
            if ((n = (int)((float)this.remainingHighlightTicks * 256.0f / 10.0f)) > 255) {
                n = 255;
            }
            if (n > 0) {
                RenderSystem.pushMatrix();
                RenderSystem.enableBlend();
                RenderSystem.defaultBlendFunc();
                IngameGui.fill(matrixStack, n3 - 2, n4 - 2, n3 + n2 + 2, n4 + 9 + 2, this.mc.gameSettings.getChatBackgroundColor(0));
                FontRenderer fontRenderer = null;
                if (Reflector.IForgeItem_getFontRenderer.exists()) {
                    fontRenderer = (FontRenderer)Reflector.call(this.highlightingItemStack.getItem(), Reflector.IForgeItem_getFontRenderer, this.highlightingItemStack);
                }
                if (fontRenderer != null) {
                    n2 = (this.scaledWidth - fontRenderer.getStringPropertyWidth(iTextComponent)) / 2;
                    fontRenderer.func_238422_b_(matrixStack, iTextComponent.func_241878_f(), n3, n4, 0xFFFFFF + (n << 24));
                } else {
                    this.getFontRenderer().func_243246_a(matrixStack, iTextComponent, n3, n4, 0xFFFFFF + (n << 24));
                }
                RenderSystem.disableBlend();
                RenderSystem.popMatrix();
            }
        }
        this.mc.getProfiler().endSection();
    }

    public void func_238455_c_(MatrixStack matrixStack) {
        this.mc.getProfiler().startSection("demo");
        ITextComponent iTextComponent = this.mc.world.getGameTime() >= 120500L ? field_243249_e : new TranslationTextComponent("demo.remainingTime", StringUtils.ticksToElapsedTime((int)(120500L - this.mc.world.getGameTime())));
        int n = this.getFontRenderer().getStringPropertyWidth(iTextComponent);
        this.getFontRenderer().func_243246_a(matrixStack, iTextComponent, this.scaledWidth - n - 10, 5.0f, 0xFFFFFF);
        this.mc.getProfiler().endSection();
    }

    private void func_238447_a_(MatrixStack matrixStack, ScoreObjective scoreObjective) {
        int n;
        EventCancelOverlay eventCancelOverlay = new EventCancelOverlay(EventCancelOverlay.Overlays.SCOREBOARD);
        venusfr.getInstance().getEventBus().post(eventCancelOverlay);
        if (eventCancelOverlay.isCancel()) {
            eventCancelOverlay.open();
            return;
        }
        Scoreboard scoreboard = scoreObjective.getScoreboard();
        Collection<Score> collection = scoreboard.getSortedScores(scoreObjective);
        List list = collection.stream().filter(IngameGui::lambda$func_238447_a_$3).collect(Collectors.toList());
        collection = list.size() > 15 ? Lists.newArrayList(Iterables.skip(list, collection.size() - 15)) : list;
        ArrayList<Pair<Score, IFormattableTextComponent>> arrayList = Lists.newArrayListWithCapacity(collection.size());
        ITextComponent iTextComponent = scoreObjective.getDisplayName();
        int n2 = n = this.getFontRenderer().getStringPropertyWidth(iTextComponent);
        int n3 = this.getFontRenderer().getStringWidth(": ");
        for (Score score : collection) {
            ScorePlayerTeam scorePlayerTeam = scoreboard.getPlayersTeam(score.getPlayerName());
            IFormattableTextComponent iFormattableTextComponent = ScorePlayerTeam.formatPlayerName(scorePlayerTeam, new StringTextComponent(score.getPlayerName()));
            arrayList.add(Pair.of(score, iFormattableTextComponent));
            n2 = Math.max(n2, this.getFontRenderer().getStringPropertyWidth(iFormattableTextComponent) + n3 + this.getFontRenderer().getStringWidth(Integer.toString(score.getScorePoints())));
        }
        int n4 = collection.size() * 9;
        int n5 = this.scaledHeight / 2 + n4 / 3;
        int n6 = 3;
        int n7 = this.scaledWidth - n2 - 3;
        int n8 = 0;
        int n9 = this.mc.gameSettings.getTextBackgroundColor(0.3f);
        int n10 = this.mc.gameSettings.getTextBackgroundColor(0.4f);
        for (Pair pair : arrayList) {
            Score score = (Score)pair.getFirst();
            ITextComponent iTextComponent2 = (ITextComponent)pair.getSecond();
            String string = "" + TextFormatting.RED + score.getScorePoints();
            int n11 = n5 - ++n8 * 9;
            int n12 = this.scaledWidth - 3 + 2;
            IngameGui.fill(matrixStack, n7 - 2, n11, n12, n11 + 9, n9);
            this.getFontRenderer().func_243248_b(matrixStack, iTextComponent2, n7, n11, -1);
            this.getFontRenderer().drawString(matrixStack, string, n12 - this.getFontRenderer().getStringWidth(string), n11, -1);
            if (n8 != collection.size()) continue;
            IngameGui.fill(matrixStack, n7 - 2, n11 - 9 - 1, n12, n11 - 1, n10);
            IngameGui.fill(matrixStack, n7 - 2, n11 - 1, n12, n11, n9);
            this.getFontRenderer().func_243248_b(matrixStack, iTextComponent, n7 + n2 / 2 - n / 2, n11 - 9, -1);
        }
    }

    private PlayerEntity getRenderViewPlayer() {
        return !(this.mc.getRenderViewEntity() instanceof PlayerEntity) ? null : (PlayerEntity)this.mc.getRenderViewEntity();
    }

    private LivingEntity getMountEntity() {
        PlayerEntity playerEntity = this.getRenderViewPlayer();
        if (playerEntity != null) {
            Entity entity2 = playerEntity.getRidingEntity();
            if (entity2 == null) {
                return null;
            }
            if (entity2 instanceof LivingEntity) {
                return (LivingEntity)entity2;
            }
        }
        return null;
    }

    private int getRenderMountHealth(LivingEntity livingEntity) {
        return 1;
    }

    private int getVisibleMountHealthRows(int n) {
        return (int)Math.ceil((double)n / 10.0);
    }

    private void func_238457_e_(MatrixStack matrixStack) {
        PlayerEntity playerEntity = this.getRenderViewPlayer();
        if (playerEntity != null) {
            int n;
            int n2;
            int n3;
            int n4;
            int n5;
            int n6;
            int n7;
            int n8 = MathHelper.ceil(playerEntity.getHealth());
            boolean bl = this.healthUpdateCounter > (long)this.ticks && (this.healthUpdateCounter - (long)this.ticks) / 3L % 2L == 1L;
            long l = Util.milliTime();
            if (n8 < this.playerHealth && playerEntity.hurtResistantTime > 0) {
                this.lastSystemTime = l;
                this.healthUpdateCounter = this.ticks + 20;
            } else if (n8 > this.playerHealth && playerEntity.hurtResistantTime > 0) {
                this.lastSystemTime = l;
                this.healthUpdateCounter = this.ticks + 10;
            }
            if (l - this.lastSystemTime > 1000L) {
                this.playerHealth = n8;
                this.lastPlayerHealth = n8;
                this.lastSystemTime = l;
            }
            this.playerHealth = n8;
            int n9 = this.lastPlayerHealth;
            this.rand.setSeed(this.ticks * 312871);
            FoodStats foodStats = playerEntity.getFoodStats();
            int n10 = foodStats.getFoodLevel();
            int n11 = this.scaledWidth / 2 - 91;
            int n12 = this.scaledWidth / 2 + 91;
            int n13 = this.scaledHeight - 39;
            float f = (float)playerEntity.getAttributeValue(Attributes.MAX_HEALTH);
            int n14 = MathHelper.ceil(playerEntity.getAbsorptionAmount());
            int n15 = MathHelper.ceil((f + (float)n14) / 2.0f / 10.0f);
            int n16 = Math.max(10 - (n15 - 2), 3);
            int n17 = n13 - (n15 - 1) * n16 - 10;
            int n18 = n13 - 10;
            int n19 = n14;
            int n20 = playerEntity.getTotalArmorValue();
            int n21 = -1;
            if (playerEntity.isPotionActive(Effects.REGENERATION)) {
                n21 = this.ticks % MathHelper.ceil(f + 5.0f);
            }
            this.mc.getProfiler().startSection("armor");
            for (n7 = 0; n7 < 10; ++n7) {
                if (n20 <= 0) continue;
                n6 = n11 + n7 * 8;
                if (n7 * 2 + 1 < n20) {
                    this.blit(matrixStack, n6, n17, 34, 9, 9, 9);
                }
                if (n7 * 2 + 1 == n20) {
                    this.blit(matrixStack, n6, n17, 25, 9, 9, 9);
                }
                if (n7 * 2 + 1 <= n20) continue;
                this.blit(matrixStack, n6, n17, 16, 9, 9, 9);
            }
            this.mc.getProfiler().endStartSection("health");
            for (n7 = MathHelper.ceil((f + (float)n14) / 2.0f) - 1; n7 >= 0; --n7) {
                n6 = 16;
                if (playerEntity.isPotionActive(Effects.POISON)) {
                    n6 += 36;
                } else if (playerEntity.isPotionActive(Effects.WITHER)) {
                    n6 += 72;
                }
                n5 = 0;
                if (bl) {
                    n5 = 1;
                }
                n4 = MathHelper.ceil((float)(n7 + 1) / 10.0f) - 1;
                n3 = n11 + n7 % 10 * 8;
                n2 = n13 - n4 * n16;
                if (n8 <= 4) {
                    n2 += this.rand.nextInt(2);
                }
                if (n19 <= 0 && n7 == n21) {
                    n2 -= 2;
                }
                n = 0;
                if (playerEntity.world.getWorldInfo().isHardcore()) {
                    n = 5;
                }
                this.blit(matrixStack, n3, n2, 16 + n5 * 9, 9 * n, 9, 9);
                if (bl) {
                    if (n7 * 2 + 1 < n9) {
                        this.blit(matrixStack, n3, n2, n6 + 54, 9 * n, 9, 9);
                    }
                    if (n7 * 2 + 1 == n9) {
                        this.blit(matrixStack, n3, n2, n6 + 63, 9 * n, 9, 9);
                    }
                }
                if (n19 > 0) {
                    if (n19 == n14 && n14 % 2 == 1) {
                        this.blit(matrixStack, n3, n2, n6 + 153, 9 * n, 9, 9);
                        --n19;
                        continue;
                    }
                    this.blit(matrixStack, n3, n2, n6 + 144, 9 * n, 9, 9);
                    n19 -= 2;
                    continue;
                }
                if (n7 * 2 + 1 < n8) {
                    this.blit(matrixStack, n3, n2, n6 + 36, 9 * n, 9, 9);
                }
                if (n7 * 2 + 1 != n8) continue;
                this.blit(matrixStack, n3, n2, n6 + 45, 9 * n, 9, 9);
            }
            LivingEntity livingEntity = this.getMountEntity();
            n6 = this.getRenderMountHealth(livingEntity);
            if (n6 == 0) {
                this.mc.getProfiler().endStartSection("food");
                for (n5 = 0; n5 < 10; ++n5) {
                    n4 = n13;
                    n3 = 16;
                    n2 = 0;
                    if (playerEntity.isPotionActive(Effects.HUNGER)) {
                        n3 += 36;
                        n2 = 13;
                    }
                    if (playerEntity.getFoodStats().getSaturationLevel() <= 0.0f && this.ticks % (n10 * 3 + 1) == 0) {
                        n4 = n13 + (this.rand.nextInt(3) - 1);
                    }
                    n = n12 - n5 * 8 - 9;
                    this.blit(matrixStack, n, n4, 16 + n2 * 9, 27, 9, 9);
                    if (n5 * 2 + 1 < n10) {
                        this.blit(matrixStack, n, n4, n3 + 36, 27, 9, 9);
                    }
                    if (n5 * 2 + 1 != n10) continue;
                    this.blit(matrixStack, n, n4, n3 + 45, 27, 9, 9);
                }
                n18 -= 10;
            }
            this.mc.getProfiler().endStartSection("air");
            n5 = playerEntity.getMaxAir();
            n4 = Math.min(playerEntity.getAir(), n5);
            if (playerEntity.areEyesInFluid(FluidTags.WATER) || n4 < n5) {
                n3 = this.getVisibleMountHealthRows(n6) - 1;
                n18 -= n3 * 10;
                n2 = MathHelper.ceil((double)(n4 - 2) * 10.0 / (double)n5);
                n = MathHelper.ceil((double)n4 * 10.0 / (double)n5) - n2;
                for (int i = 0; i < n2 + n; ++i) {
                    if (i < n2) {
                        this.blit(matrixStack, n12 - i * 8 - 9, n18, 16, 18, 9, 9);
                        continue;
                    }
                    this.blit(matrixStack, n12 - i * 8 - 9, n18, 25, 18, 9, 9);
                }
            }
            this.mc.getProfiler().endSection();
        }
    }

    private void func_238458_f_(MatrixStack matrixStack) {
        int n;
        LivingEntity livingEntity = this.getMountEntity();
        if (livingEntity != null && (n = this.getRenderMountHealth(livingEntity)) != 0) {
            int n2 = (int)Math.ceil(livingEntity.getHealth());
            this.mc.getProfiler().endStartSection("mountHealth");
            int n3 = this.scaledHeight - 39;
            int n4 = this.scaledWidth / 2 + 91;
            int n5 = n3;
            int n6 = 0;
            boolean bl = false;
            while (n > 0) {
                int n7 = Math.min(n, 10);
                n -= n7;
                for (int i = 0; i < n7; ++i) {
                    int n8 = 52;
                    int n9 = 0;
                    int n10 = n4 - i * 8 - 9;
                    this.blit(matrixStack, n10, n5, 52 + n9 * 9, 9, 9, 9);
                    if (i * 2 + 1 + n6 < n2) {
                        this.blit(matrixStack, n10, n5, 88, 9, 9, 9);
                    }
                    if (i * 2 + 1 + n6 != n2) continue;
                    this.blit(matrixStack, n10, n5, 97, 9, 9, 9);
                }
                n5 -= 10;
                n6 += 20;
            }
        }
    }

    private void renderPumpkinOverlay() {
        RenderSystem.disableDepthTest();
        RenderSystem.depthMask(false);
        RenderSystem.defaultBlendFunc();
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.disableAlphaTest();
        this.mc.getTextureManager().bindTexture(PUMPKIN_BLUR_TEX_PATH);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        bufferBuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferBuilder.pos(0.0, this.scaledHeight, -90.0).tex(0.0f, 1.0f).endVertex();
        bufferBuilder.pos(this.scaledWidth, this.scaledHeight, -90.0).tex(1.0f, 1.0f).endVertex();
        bufferBuilder.pos(this.scaledWidth, 0.0, -90.0).tex(1.0f, 0.0f).endVertex();
        bufferBuilder.pos(0.0, 0.0, -90.0).tex(0.0f, 0.0f).endVertex();
        tessellator.draw();
        RenderSystem.depthMask(true);
        RenderSystem.enableDepthTest();
        RenderSystem.enableAlphaTest();
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
    }

    private void updateVignetteBrightness(Entity entity2) {
        if (entity2 != null) {
            float f = MathHelper.clamp(1.0f - entity2.getBrightness(), 0.0f, 1.0f);
            this.prevVignetteBrightness = (float)((double)this.prevVignetteBrightness + (double)(f - this.prevVignetteBrightness) * 0.01);
        }
    }

    private void renderVignette(Entity entity2) {
        if (!Config.isVignetteEnabled()) {
            RenderSystem.enableDepthTest();
            RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        } else {
            WorldBorder worldBorder = this.mc.world.getWorldBorder();
            float f = (float)worldBorder.getClosestDistance(entity2);
            double d = Math.min(worldBorder.getResizeSpeed() * (double)worldBorder.getWarningTime() * 1000.0, Math.abs(worldBorder.getTargetSize() - worldBorder.getDiameter()));
            double d2 = Math.max((double)worldBorder.getWarningDistance(), d);
            f = (double)f < d2 ? 1.0f - (float)((double)f / d2) : 0.0f;
            RenderSystem.disableDepthTest();
            RenderSystem.depthMask(false);
            RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.ZERO, GlStateManager.DestFactor.ONE_MINUS_SRC_COLOR, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            if (f > 0.0f) {
                RenderSystem.color4f(0.0f, f, f, 1.0f);
            } else {
                RenderSystem.color4f(this.prevVignetteBrightness, this.prevVignetteBrightness, this.prevVignetteBrightness, 1.0f);
            }
            this.mc.getTextureManager().bindTexture(VIGNETTE_TEX_PATH);
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder bufferBuilder = tessellator.getBuffer();
            bufferBuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
            bufferBuilder.pos(0.0, this.scaledHeight, -90.0).tex(0.0f, 1.0f).endVertex();
            bufferBuilder.pos(this.scaledWidth, this.scaledHeight, -90.0).tex(1.0f, 1.0f).endVertex();
            bufferBuilder.pos(this.scaledWidth, 0.0, -90.0).tex(1.0f, 0.0f).endVertex();
            bufferBuilder.pos(0.0, 0.0, -90.0).tex(0.0f, 0.0f).endVertex();
            tessellator.draw();
            RenderSystem.depthMask(true);
            RenderSystem.enableDepthTest();
            RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
            RenderSystem.defaultBlendFunc();
        }
    }

    private void renderPortal(float f) {
        if (f < 1.0f) {
            f *= f;
            f *= f;
            f = f * 0.8f + 0.2f;
        }
        RenderSystem.disableAlphaTest();
        RenderSystem.disableDepthTest();
        RenderSystem.depthMask(false);
        RenderSystem.defaultBlendFunc();
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, f);
        this.mc.getTextureManager().bindTexture(AtlasTexture.LOCATION_BLOCKS_TEXTURE);
        TextureAtlasSprite textureAtlasSprite = this.mc.getBlockRendererDispatcher().getBlockModelShapes().getTexture(Blocks.NETHER_PORTAL.getDefaultState());
        float f2 = textureAtlasSprite.getMinU();
        float f3 = textureAtlasSprite.getMinV();
        float f4 = textureAtlasSprite.getMaxU();
        float f5 = textureAtlasSprite.getMaxV();
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        bufferBuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferBuilder.pos(0.0, this.scaledHeight, -90.0).tex(f2, f5).endVertex();
        bufferBuilder.pos(this.scaledWidth, this.scaledHeight, -90.0).tex(f4, f5).endVertex();
        bufferBuilder.pos(this.scaledWidth, 0.0, -90.0).tex(f4, f3).endVertex();
        bufferBuilder.pos(0.0, 0.0, -90.0).tex(f2, f3).endVertex();
        tessellator.draw();
        RenderSystem.depthMask(true);
        RenderSystem.enableDepthTest();
        RenderSystem.enableAlphaTest();
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
    }

    private void renderHotbarItem(int n, int n2, float f, PlayerEntity playerEntity, ItemStack itemStack) {
        if (!itemStack.isEmpty()) {
            float f2 = (float)itemStack.getAnimationsToGo() - f;
            if (f2 > 0.0f) {
                RenderSystem.pushMatrix();
                float f3 = 1.0f + f2 / 5.0f;
                RenderSystem.translatef(n + 8, n2 + 12, 0.0f);
                RenderSystem.scalef(1.0f / f3, (f3 + 1.0f) / 2.0f, 1.0f);
                RenderSystem.translatef(-(n + 8), -(n2 + 12), 0.0f);
            }
            this.itemRenderer.renderItemAndEffectIntoGUI(playerEntity, itemStack, n, n2);
            if (f2 > 0.0f) {
                RenderSystem.popMatrix();
            }
            this.itemRenderer.renderItemOverlays(this.mc.fontRenderer, itemStack, n, n2);
        }
    }

    public void tick() {
        if (this.mc.world == null) {
            TextureAnimations.updateAnimations();
        }
        if (this.overlayMessageTime > 0) {
            --this.overlayMessageTime;
        }
        if (this.titlesTimer > 0) {
            --this.titlesTimer;
            if (this.titlesTimer <= 0) {
                this.displayedTitle = null;
                this.displayedSubTitle = null;
            }
        }
        ++this.ticks;
        Entity entity2 = this.mc.getRenderViewEntity();
        if (entity2 != null) {
            this.updateVignetteBrightness(entity2);
        }
        if (this.mc.player != null) {
            ItemStack itemStack = this.mc.player.inventory.getCurrentItem();
            boolean bl = true;
            if (Reflector.IForgeItemStack_getHighlightTip.exists()) {
                ITextComponent iTextComponent = (ITextComponent)Reflector.call(itemStack, Reflector.IForgeItemStack_getHighlightTip, itemStack.getDisplayName());
                ITextComponent iTextComponent2 = (ITextComponent)Reflector.call(this.highlightingItemStack, Reflector.IForgeItemStack_getHighlightTip, this.highlightingItemStack.getDisplayName());
                bl = Config.equals(iTextComponent, iTextComponent2);
            }
            if (itemStack.isEmpty()) {
                this.remainingHighlightTicks = 0;
            } else if (!this.highlightingItemStack.isEmpty() && itemStack.getItem() == this.highlightingItemStack.getItem() && itemStack.getDisplayName().equals(this.highlightingItemStack.getDisplayName()) && bl) {
                if (this.remainingHighlightTicks > 0) {
                    --this.remainingHighlightTicks;
                }
            } else {
                this.remainingHighlightTicks = 40;
            }
            this.highlightingItemStack = itemStack;
        }
    }

    public void func_238451_a_(ITextComponent iTextComponent) {
        this.setOverlayMessage(new TranslationTextComponent("record.nowPlaying", iTextComponent), false);
    }

    public void setOverlayMessage(ITextComponent iTextComponent, boolean bl) {
        this.overlayMessage = iTextComponent;
        this.overlayMessageTime = 60;
        this.animateOverlayMessageColor = bl;
    }

    public void func_238452_a_(@Nullable ITextComponent iTextComponent, @Nullable ITextComponent iTextComponent2, int n, int n2, int n3) {
        if (iTextComponent == null && iTextComponent2 == null && n < 0 && n2 < 0 && n3 < 0) {
            this.displayedTitle = null;
            this.displayedSubTitle = null;
            this.titlesTimer = 0;
        } else if (iTextComponent != null) {
            this.displayedTitle = iTextComponent;
            this.titlesTimer = this.titleFadeIn + this.titleDisplayTime + this.titleFadeOut;
        } else if (iTextComponent2 != null) {
            this.displayedSubTitle = iTextComponent2;
        } else {
            if (n >= 0) {
                this.titleFadeIn = n;
            }
            if (n2 >= 0) {
                this.titleDisplayTime = n2;
            }
            if (n3 >= 0) {
                this.titleFadeOut = n3;
            }
            if (this.titlesTimer > 0) {
                this.titlesTimer = this.titleFadeIn + this.titleDisplayTime + this.titleFadeOut;
            }
        }
    }

    public UUID func_244795_b(ITextComponent iTextComponent) {
        String string = TextProcessing.func_244782_a(iTextComponent);
        String string2 = org.apache.commons.lang3.StringUtils.substringBetween(string, "<", ">");
        return string2 == null ? Util.DUMMY_UUID : this.mc.func_244599_aA().func_244797_a(string2);
    }

    public void func_238450_a_(ChatType chatType, ITextComponent iTextComponent, UUID uUID) {
        if (!(this.mc.cannotSendChatMessages(uUID) || this.mc.gameSettings.field_244794_ae && this.mc.cannotSendChatMessages(this.func_244795_b(iTextComponent)))) {
            for (IChatListener iChatListener : this.chatListeners.get((Object)chatType)) {
                iChatListener.say(chatType, iTextComponent, uUID);
            }
        }
    }

    public NewChatGui getChatGUI() {
        return this.persistantChatGUI;
    }

    public int getTicks() {
        return this.ticks;
    }

    public FontRenderer getFontRenderer() {
        return this.mc.fontRenderer;
    }

    public SpectatorGui getSpectatorGui() {
        return this.spectatorGui;
    }

    public PlayerTabOverlayGui getTabList() {
        return this.overlayPlayerList;
    }

    public void resetPlayersOverlayFooterHeader() {
        this.overlayPlayerList.resetFooterHeader();
        this.overlayBoss.clearBossInfos();
        this.mc.getToastGui().clear();
    }

    public BossOverlayGui getBossOverlay() {
        return this.overlayBoss;
    }

    public void reset() {
        this.overlayDebug.resetChunk();
    }

    private static boolean lambda$func_238447_a_$3(Score score) {
        return score.getPlayerName() != null && !score.getPlayerName().startsWith("#");
    }

    private static void lambda$renderHotbar$2(int n, int n2, int n3, int n4) {
        DisplayUtils.drawRoundedRect((float)n, (float)n2, (float)n3, (float)(n4 - 1), 3.0f, Color.WHITE.getRGB());
    }

    private static void lambda$renderHotbar$1(int n, int n2, int n3, int n4) {
        DisplayUtils.drawRoundedRect((float)n, (float)n2, (float)n3, (float)(n4 - 2), 4.0f, Color.WHITE.getRGB());
    }

    private void lambda$renderPotionIcons$0(TextureAtlasSprite textureAtlasSprite, float f, MatrixStack matrixStack, int n, int n2) {
        this.mc.getTextureManager().bindTexture(textureAtlasSprite.getAtlasTexture().getTextureLocation());
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, f);
        IngameGui.blit(matrixStack, n + 3, n2 + 3, this.getBlitOffset(), 18, 18, textureAtlasSprite);
    }
}

