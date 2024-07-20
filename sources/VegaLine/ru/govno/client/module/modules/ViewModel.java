/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.module.modules;

import java.util.Arrays;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumHandSide;
import org.lwjgl.opengl.GL11;
import ru.govno.client.event.EventTarget;
import ru.govno.client.event.events.EventTransformSideFirstPerson;
import ru.govno.client.module.Module;
import ru.govno.client.module.modules.ClientColors;
import ru.govno.client.module.settings.Settings;
import ru.govno.client.utils.Command.impl.Panic;
import ru.govno.client.utils.Math.MathUtils;
import ru.govno.client.utils.Render.BloomUtil;
import ru.govno.client.utils.Render.ColorUtils;
import ru.govno.client.utils.Render.ShaderUtility;

public class ViewModel
extends Module {
    public static ViewModel get;
    public Settings Animation = new Settings("Animation", "Smooth", (Module)this, new String[]{"None", "Smooth", "Smooth2", "Lower", "Tower", "VEbalo", "Plunge"});
    public Settings AnimationSpeed;
    public Settings BlendFunction;
    public Settings ResizeItems;
    public Settings ItemsScale;
    public Settings HandsGlow;
    public Settings GlowColor;
    public Settings GlowPickColor;
    public Settings GlowRadius;
    public Settings GlowExplosure;
    public Settings GlowFill;
    public Settings GlowBloom;
    public static Framebuffer glowBufferLHand;
    public static Framebuffer glowBufferRHand;

    public ViewModel() {
        super("ViewModel", 0, Module.Category.RENDER);
        this.settings.add(this.Animation);
        this.AnimationSpeed = new Settings("AnimationSpeed", 0.8f, 1.25f, 0.15f, this, () -> !this.Animation.currentMode.equalsIgnoreCase("None"));
        this.settings.add(this.AnimationSpeed);
        this.BlendFunction = new Settings("BlendFunction", "None", (Module)this, new String[]{"None", "Saturation", "BrightToAlpha"});
        this.settings.add(this.BlendFunction);
        this.ResizeItems = new Settings("ResizeItems", false, (Module)this);
        this.settings.add(this.ResizeItems);
        this.ItemsScale = new Settings("ItemsScale", 0.4f, 1.25f, 0.025f, this, () -> this.ResizeItems.bValue);
        this.settings.add(this.ItemsScale);
        this.HandsGlow = new Settings("HandsGlow", false, (Module)this);
        this.settings.add(this.HandsGlow);
        this.GlowColor = new Settings("GlowColor", "PickColor", this, new String[]{"PickColor", "ClientColor"}, () -> this.HandsGlow.bValue);
        this.settings.add(this.GlowColor);
        this.GlowPickColor = new Settings("GlowPickColor", ColorUtils.getColor(124, 124, 124, 226), (Module)this, () -> this.HandsGlow.bValue && this.GlowColor.currentMode.equalsIgnoreCase("PickColor"));
        this.settings.add(this.GlowPickColor);
        this.GlowRadius = new Settings("GlowRadius", 10.0f, 50.0f, 1.0f, this, () -> this.HandsGlow.bValue);
        this.settings.add(this.GlowRadius);
        this.GlowExplosure = new Settings("GlowExplosure", 2.0f, 5.0f, 0.1f, this, () -> this.HandsGlow.bValue);
        this.settings.add(this.GlowExplosure);
        this.GlowFill = new Settings("GlowFill", false, (Module)this, () -> this.HandsGlow.bValue);
        this.settings.add(this.GlowFill);
        this.GlowBloom = new Settings("GlowBloom", false, (Module)this, () -> this.HandsGlow.bValue);
        this.settings.add(this.GlowBloom);
        get = this;
    }

    public static Framebuffer getBuffer(EnumHand hand) {
        return hand == EnumHand.MAIN_HAND ? glowBufferRHand : glowBufferLHand;
    }

    public static void readBuffers(EnumHand hand) {
        if (hand == EnumHand.MAIN_HAND) {
            glowBufferRHand = null;
        } else {
            glowBufferLHand = null;
        }
    }

    public static void updateBuffer(Framebuffer buffer) {
        if (buffer == null) {
            buffer = ShaderUtility.createFrameBuffer(new Framebuffer(new ScaledResolution(Minecraft.getMinecraft()).getScaledWidth(), new ScaledResolution(Minecraft.getMinecraft()).getScaledHeight(), true));
        }
        if (buffer != null && (buffer.framebufferWidth != ViewModel.mc.displayWidth || buffer.framebufferHeight != ViewModel.mc.displayHeight)) {
            buffer.createBindFramebuffer(ViewModel.mc.displayWidth, ViewModel.mc.displayHeight);
        }
    }

    public static void startBuffer(Framebuffer buffer) {
        if (buffer == null) {
            return;
        }
        GlStateManager.enableDepth();
        GlStateManager.enableAlpha();
        GL11.glDepthMask(true);
        ViewModel.updateBuffer(buffer);
        buffer.framebufferClear();
        buffer.bindFramebuffer(false);
    }

    public static void endBuffer(Framebuffer buffer) {
        if (buffer == null) {
            return;
        }
        buffer.unbindFramebuffer();
    }

    public static boolean readRenderHandStart(EntityLivingBase baseIn, EnumHand hand) {
        if (!(baseIn instanceof EntityPlayerSP)) {
            return false;
        }
        if (ViewModel.get.actived && ViewModel.get.HandsGlow.bValue) {
            ViewModel.startBuffer(ViewModel.getBuffer(hand));
            return true;
        }
        return false;
    }

    public static void readRenderHandStop(EnumHand hand) {
        ViewModel.endBuffer(ViewModel.getBuffer(hand));
    }

    public static boolean readRenderHandNullateOnEmpty(EnumHand hand, boolean silent) {
        Item stackItem;
        ItemStack heldStack = Minecraft.player.getHeldItem(hand);
        if (heldStack != null && ((stackItem = heldStack.getItem()) == Items.air || ViewModel.mc.gameSettings.thirdPersonView != 0)) {
            if (!silent) {
                ViewModel.getBuffer(hand).framebufferClear();
            }
            return true;
        }
        return false;
    }

    public static void draw2dBuffers(float radius, float explosure, boolean fillTexture, int colorLeft, int colorRight, boolean bloom) {
        Arrays.asList(EnumHand.OFF_HAND, EnumHand.MAIN_HAND).forEach(hand -> {
            int color;
            Framebuffer buffer = ViewModel.getBuffer(hand);
            int n = color = hand.equals((Object)EnumHand.OFF_HAND) ? colorLeft : colorRight;
            if (buffer == null) {
                return;
            }
            if (buffer.framebufferTexture != 0 && !ViewModel.readRenderHandNullateOnEmpty(hand, true)) {
                GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, bloom ? GlStateManager.DestFactor.ONE : GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
                GlStateManager.disableAlpha();
                BloomUtil.renderBlur(buffer.framebufferTexture, (int)radius + 1, 1, color, explosure, !fillTexture);
                GlStateManager.enableAlpha();
                if (bloom) {
                    GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
                }
            }
        });
    }

    public static void drawGlowHands() {
        if (!ViewModel.get.actived || !ViewModel.get.HandsGlow.bValue) {
            return;
        }
        int pickC = ViewModel.get.GlowPickColor.color;
        int clientC1 = ClientColors.getColor1();
        int clientC2 = ClientColors.getColor2();
        boolean isPickCol = ViewModel.get.GlowColor.currentMode.equalsIgnoreCase("PickColor");
        int colorLeft = isPickCol ? pickC : clientC1;
        int colorRight = isPickCol ? pickC : clientC2;
        float radius = ViewModel.get.GlowRadius.fValue;
        float explosure = ViewModel.get.GlowExplosure.fValue;
        boolean fill = ViewModel.get.GlowFill.bValue;
        boolean bloom = ViewModel.get.GlowBloom.bValue;
        ViewModel.draw2dBuffers(radius, explosure, fill, colorLeft, colorRight, bloom);
    }

    public static int getSwingSpeed(EntityLivingBase baseIn, int prevSpeed) {
        if (!Panic.stop && baseIn instanceof EntityPlayerSP && ViewModel.get.actived && ViewModel.get.AnimationSpeed.fValue != 1.0f && !ViewModel.get.Animation.currentMode.equalsIgnoreCase("None")) {
            return (int)((float)prevSpeed / MathUtils.clamp(ViewModel.get.AnimationSpeed.fValue, 0.5f, 3.0f));
        }
        return prevSpeed;
    }

    public static void setupTransperentModel(EntityLivingBase baseIn, boolean isStart) {
        String func = ViewModel.get.BlendFunction.currentMode;
        if (!(baseIn instanceof EntityPlayerSP)) {
            return;
        }
        if (!Panic.stop && ViewModel.get.actived && !func.isEmpty() && !func.equalsIgnoreCase("Default")) {
            if (isStart) {
                switch (func) {
                    case "Default": {
                        break;
                    }
                    case "Saturation": {
                        GL11.glBlendFunc(768, 771);
                        break;
                    }
                    case "BrightToAlpha": {
                        GL11.glBlendFunc(768, 1);
                        break;
                    }
                    default: {
                        return;
                    }
                }
                return;
            }
            GL11.glBlendFunc(770, 771);
        }
    }

    public static void translate(String mode, float f, float f1, int i) {
        if (mode.isEmpty()) {
            return;
        }
        switch (mode) {
            case "None": {
                break;
            }
            case "Smooth": {
                GlStateManager.rotate(f1 * -80.0f + f * 20.0f, 310.0f, 40.0f + f * 53.0f + f * 100.0f * (float)i, -140.0f * (float)i);
                break;
            }
            case "Smooth2": {
                GlStateManager.translate(0.0f, -f1 * 0.1f, 0.0f);
                GlStateManager.rotate(f1 * -100.0f * (0.5f + f), 310.0f, 40.0f, -110.0f * (float)i);
                break;
            }
            case "Lower": {
                GlStateManager.translate((double)(0.1f * (float)i), (double)0.2f, -0.125);
                GlStateManager.rotate(70 * i, 0.0f, 1.0f, 0.0f);
                GlStateManager.translate(0.0, (double)0.038f, 0.008);
                GlStateManager.rotate(-90.0f - 5.0f * f * f1 * 10.0f * f1, 1.0f, 0.0f, 0.0f);
                GlStateManager.translate(0.0, (double)-0.038f, -0.008);
                break;
            }
            case "Tower": {
                GlStateManager.translate(0.4 * (double)i, 0.1, -0.4);
                GlStateManager.translate(0.0, (double)0.038f, 0.008);
                GlStateManager.rotate(10.0f * f * f1 * 15.0f * f1 * (float)i, 0.0f, 1.0f, 1.0f);
                GlStateManager.translate(0.0, (double)-0.038f, -0.008);
                GlStateManager.rotate(-115.0f, 1.0f, 0.0f, 0.0f);
                GlStateManager.rotate(90 * i, 0.0f, 0.0f, 1.0f);
                GlStateManager.rotate(30 * i, 0.0f, 1.0f, 0.0f);
                break;
            }
            case "VEbalo": {
                GlStateManager.rotate(-90.0f, 1.0f, 0.0f, 0.0f);
                GlStateManager.translate(0.0f, 0.2f + f1, 0.0f);
                GlStateManager.translate(-0.2f, -0.2f, 0.0f);
                GlStateManager.scale(1.0f, 1.0f + f1 * 2.0f, 1.0f);
                GlStateManager.translate(0.2f, 0.2f, 0.0f);
                break;
            }
            case "Plunge": {
                GlStateManager.translate(0.2 * (double)i, 0.1, -0.2);
                GlStateManager.rotate(-115.0f, 1.0f, 0.0f, 0.0f);
                GlStateManager.rotate(10 * i, 0.0f, 1.0f, 0.0f);
                GlStateManager.translate(0.0, (double)0.038f, 0.008);
                GlStateManager.translate(0.0f, 0.0f, -f * 0.1f * (float)i);
                GlStateManager.rotate((f1 * 60.0f - (1.0f - f) * 20.0f - 10.0f) * (float)i, 0.0f, -1.0f, 0.0f);
                GlStateManager.rotate(f * 40.0f * (float)i, 1.0f, 1.0f, -1.0f);
                GlStateManager.translate(0.0, (double)-0.038f, -0.008);
                GlStateManager.rotate(90 * i, 0.0f, 0.0f, 1.0f);
                break;
            }
        }
    }

    public static boolean animate(boolean silent, float f, float f1, int i) {
        String animMode;
        if (!Panic.stop && ViewModel.get.actived && !(animMode = ViewModel.get.Animation.currentMode).equalsIgnoreCase("None")) {
            int sideIndex;
            int n = sideIndex = Minecraft.player.getPrimaryHand() == EnumHandSide.RIGHT ? 1 : -1;
            if (!silent && i == sideIndex) {
                ViewModel.translate(animMode, f, f1, i);
            }
            return true;
        }
        return false;
    }

    @EventTarget
    public void onSidePerson(EventTransformSideFirstPerson event) {
        if (this.ResizeItems.bValue) {
            GlStateManager.translate(0.0f, 0.35f, 0.0f);
            GlStateManager.scale(this.ItemsScale.fValue, this.ItemsScale.fValue, this.ItemsScale.fValue);
            GlStateManager.translate(0.0f, -0.35f, 0.0f);
        }
    }

    static {
        glowBufferLHand = ShaderUtility.createFrameBuffer(new Framebuffer(new ScaledResolution(Minecraft.getMinecraft()).getScaledWidth(), new ScaledResolution(Minecraft.getMinecraft()).getScaledHeight(), true));
        glowBufferRHand = ShaderUtility.createFrameBuffer(new Framebuffer(new ScaledResolution(Minecraft.getMinecraft()).getScaledWidth(), new ScaledResolution(Minecraft.getMinecraft()).getScaledHeight(), true));
    }
}

