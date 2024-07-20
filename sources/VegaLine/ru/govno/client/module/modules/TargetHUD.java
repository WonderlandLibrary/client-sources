/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.module.modules;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import javax.vecmath.Vector2f;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector4d;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import ru.govno.client.Client;
import ru.govno.client.event.EventTarget;
import ru.govno.client.event.events.EventRender2D;
import ru.govno.client.module.Module;
import ru.govno.client.module.modules.BowAimbot;
import ru.govno.client.module.modules.ClientColors;
import ru.govno.client.module.modules.ClientTune;
import ru.govno.client.module.modules.FreeCam;
import ru.govno.client.module.modules.HitAura;
import ru.govno.client.module.modules.WorldRender;
import ru.govno.client.module.settings.Settings;
import ru.govno.client.newfont.CFontRenderer;
import ru.govno.client.newfont.Fonts;
import ru.govno.client.utils.CrystalField;
import ru.govno.client.utils.Math.MathUtils;
import ru.govno.client.utils.Render.AnimationUtils;
import ru.govno.client.utils.Render.ColorUtils;
import ru.govno.client.utils.Render.GaussianBlur;
import ru.govno.client.utils.Render.RenderUtils;
import ru.govno.client.utils.Render.StencilUtil;

public class TargetHUD
extends Module {
    public static TargetHUD get;
    public static EntityLivingBase curTarget;
    public static EntityLivingBase soundTarget;
    static ArrayList<particle> particles;
    public static float xPosHud;
    public static float yPosHud;
    public static float widthHud;
    public static float heightHud;
    AnimationUtils Scale = new AnimationUtils(1.0f, 1.0f, 0.07f);
    private float armorHealth;
    public Settings Mode;
    public Settings THudX;
    public Settings THudY;
    public Settings PreRangedTarget;
    public Settings RaycastTarget;
    public Settings CastPosition;
    public Settings TargettingSFX;
    float hpRectAnim = 0.0f;
    float hurtRectAnim = 0.0f;
    float bgAlphaChange = 26.0f;
    private final IntBuffer viewport = GLAllocation.createDirectIntBuffer(16);
    private final FloatBuffer modelview = GLAllocation.createDirectFloatBuffer(16);
    private final FloatBuffer projection = GLAllocation.createDirectFloatBuffer(16);
    private final FloatBuffer vector = GLAllocation.createDirectFloatBuffer(4);
    String targetName = "";
    AnimationUtils alphaHp = new AnimationUtils(0.0f, 0.0f, 0.075f);
    final AnimationUtils hpAnim = new AnimationUtils(1.0f, 1.0f, 0.05f);
    final AnimationUtils absorbAnim = new AnimationUtils(0.0f, 0.0f, 0.05f);
    final AnimationUtils hurtHpAnim = new AnimationUtils(1.0f, 1.0f, 0.05f);
    public static ResourceLocation skin;
    public static ResourceLocation OldSkin;
    int targetHurt;

    float Scale() {
        return this.Scale.getAnim();
    }

    float Scaleclamp() {
        return MathUtils.clamp(this.Scale(), 0.0f, 1.0f);
    }

    public TargetHUD() {
        super("TargetHUD", 0, Module.Category.COMBAT);
        this.Mode = new Settings("Mode", "Light", (Module)this, new String[]{"Light", "WetWorn", "Neomoin", "Modern", "Bushy", "Subtle"});
        this.settings.add(this.Mode);
        this.THudX = new Settings("T-Hud X", 0.45f, 1.0f, 0.0f, this, () -> false);
        this.settings.add(this.THudX);
        this.THudY = new Settings("T-Hud Y", 0.6f, 1.0f, 0.0f, this, () -> false);
        this.settings.add(this.THudY);
        this.PreRangedTarget = new Settings("PreRangedTarget", true, (Module)this);
        this.settings.add(this.PreRangedTarget);
        this.RaycastTarget = new Settings("RaycastTarget", true, (Module)this);
        this.settings.add(this.RaycastTarget);
        this.CastPosition = new Settings("CastPosition", false, (Module)this);
        this.settings.add(this.CastPosition);
        this.TargettingSFX = new Settings("TargettingSFX", true, (Module)this);
        this.settings.add(this.TargettingSFX);
        get = this;
    }

    private Vector3d project2D(int scaleFactor, double x, double y, double z) {
        GL11.glGetFloat(2982, this.modelview);
        GL11.glGetFloat(2983, this.projection);
        GL11.glGetInteger(2978, this.viewport);
        return GLU.gluProject((float)x, (float)y, (float)z, this.modelview, this.projection, this.viewport, this.vector) ? new Vector3d(this.vector.get(0) / (float)scaleFactor, ((float)Display.getHeight() - this.vector.get(1)) / (float)scaleFactor, this.vector.get(2)) : null;
    }

    float[] castPosition(EventRender2D event) {
        float xn = -1.0f;
        float yn = -1.0f;
        EntityLivingBase entity = curTarget;
        if (entity != null && entity != Minecraft.player) {
            ScaledResolution scaledResolution = event.getResolution();
            int scaleFactor = ScaledResolution.getScaleFactor();
            float scale = 1.0f;
            double x = RenderUtils.interpolate(entity.posX, entity.prevPosX, event.getPartialTicks());
            double y = RenderUtils.interpolate(entity.posY, entity.prevPosY, event.getPartialTicks());
            double z = RenderUtils.interpolate(entity.posZ, entity.prevPosZ, event.getPartialTicks());
            double height = (double)entity.getEyeHeight() / (entity.isChild() ? 1.80042 : 1.0);
            AxisAlignedBB aabb = new AxisAlignedBB(x, y, z, x, y + height, z);
            Vector3d[] vectors = new Vector3d[]{new Vector3d(aabb.minX, aabb.minY, aabb.minZ), new Vector3d(aabb.minX, aabb.maxY, aabb.minZ), new Vector3d(aabb.maxX, aabb.minY, aabb.minZ), new Vector3d(aabb.maxX, aabb.maxY, aabb.minZ), new Vector3d(aabb.minX, aabb.minY, aabb.maxZ), new Vector3d(aabb.minX, aabb.maxY, aabb.maxZ), new Vector3d(aabb.maxX, aabb.minY, aabb.maxZ), new Vector3d(aabb.maxX, aabb.maxY, aabb.maxZ)};
            TargetHUD.mc.entityRenderer.setupCameraTransform(event.getPartialTicks(), 1);
            Vector4d position = null;
            Vector3d[] vecList = vectors;
            int vecLength = vectors.length;
            for (int l = 0; l < vecLength; ++l) {
                Vector3d vector = this.project2D(scaleFactor, vecList[l].x - RenderManager.viewerPosX, vecList[l].y - RenderManager.viewerPosY, vecList[l].z - RenderManager.viewerPosZ);
                if (vector == null || !(vector.z >= 0.0) || !(vector.z < 1.0)) continue;
                if (position == null) {
                    position = new Vector4d(vector.x, vector.y, vector.z, 0.0);
                }
                position.x = Math.min(vector.x, position.x);
                position.y = Math.min(vector.y, position.y);
                position.z = Math.max(vector.x, position.z);
                position.w = Math.max(vector.y, position.w);
            }
            TargetHUD.mc.entityRenderer.setupOverlayRendering();
            if (position != null) {
                double posX = position.x;
                double posY = position.y;
                double endPosX = position.z;
                double endPosY = position.w;
                xn = (float)(posX + (endPosX - posX) / 2.0) - widthHud / 2.0f;
                yn = (float)posY + heightHud / 2.0f;
            }
        }
        return new float[]{xn, yn};
    }

    private boolean castPosIsValid(float[] position) {
        return position[0] != -1.0f && position[1] != -1.0f;
    }

    void updatePosition(EventRender2D event) {
        float animSpeed = (float)Minecraft.frameTime * 0.035f;
        float animSpeedCast = (float)Minecraft.frameTime * 0.0075f;
        float[] castedPosition = new float[]{-1.0f, -1.0f};
        if (this.CastPosition.bValue) {
            castedPosition = this.castPosition(event);
        }
        if (this.CastPosition.bValue && castedPosition[0] > 0.0f && castedPosition[0] < (float)event.getResolution().getScaledWidth() - widthHud && castedPosition[1] > 0.0f && castedPosition[1] < (float)event.getResolution().getScaledHeight() - heightHud && !(TargetHUD.mc.currentScreen instanceof GuiChat) && TargetHUD.getTarget() != null && this.castPosIsValid(castedPosition)) {
            xPosHud = MathUtils.harp(xPosHud, castedPosition[0], animSpeedCast);
            yPosHud = MathUtils.harp(yPosHud, castedPosition[1], animSpeedCast);
        } else {
            xPosHud = MathUtils.harp(xPosHud, this.THudX.fValue * (float)event.getResolution().getScaledWidth() - widthHud / 2.0f, animSpeed);
            yPosHud = MathUtils.harp(yPosHud, this.THudY.fValue * (float)event.getResolution().getScaledHeight() - heightHud / 2.0f, animSpeed);
        }
    }

    void renderLight(EntityLivingBase target) {
        float hpX3;
        float hpX1;
        float hpX2;
        ResourceLocation res = OldSkin != null ? OldSkin : (skin != null ? skin : null);
        String hp = target.getHealth() == 0.0f ? "" : String.format("%.1f", Float.valueOf(this.hpRectAnim * target.getMaxHealth() + target.getAbsorptionAmount())) + "hp";
        float w = (int)widthHud;
        float h = (int)heightHud;
        CFontRenderer namefont = Fonts.mntsb_16;
        float x = xPosHud;
        float y = yPosHud;
        float texX = x + h;
        widthHud = MathUtils.clamp(texX - x + (float)namefont.getStringWidth(this.targetName) + 4.0f, 100.0f, 900.0f);
        heightHud = 38.0f;
        this.hpRectAnim = MathUtils.lerp(this.hpRectAnim, MathUtils.clamp(target.getHealth() / target.getMaxHealth(), 0.0f, 1.0f), 0.015f * (float)Minecraft.frameTime);
        float percScaled = MathUtils.clamp(this.Scale() * this.Scale(), 0.0f, 1.0f);
        int bgC = ColorUtils.swapAlpha(-1, 255.0f * percScaled);
        int roundC = ColorUtils.swapAlpha(-1, 225.0f * percScaled * percScaled);
        int bgSH = ColorUtils.swapAlpha(-1, 30.0f * percScaled);
        int bgSH2 = ColorUtils.swapAlpha(-1, 85.0f * percScaled * percScaled);
        int hpbgC = ColorUtils.swapAlpha(0, 55.0f * percScaled);
        int hpSC = ColorUtils.getColor(0, 0, 0, this.alphaHp.getAnim() * percScaled * percScaled);
        int texC = ColorUtils.swapAlpha(ColorUtils.getColor(20, 20, 20), 255.0f * percScaled);
        int itC = ColorUtils.swapAlpha(ColorUtils.getColor(0, 0, 0), 255.0f * percScaled);
        int hpC = ColorUtils.swapAlpha(ClientColors.getColor1(0), 255.0f * percScaled);
        int hpC2 = ColorUtils.getOverallColorFrom(hpC, ColorUtils.swapAlpha(ClientColors.getColor2(30), 255.0f * percScaled), this.hpRectAnim);
        int hpbgC2 = ColorUtils.swapAlpha(hpC, 85.0f * percScaled);
        int hpbgC3 = ColorUtils.swapAlpha(hpC2, 85.0f * percScaled);
        GL11.glPushMatrix();
        GL11.glTranslated(0.0, ((this.Scale() > 1.0f ? this.Scale() * this.Scale() : this.Scale()) - 1.0f) * -8.0f, 0.0);
        StencilUtil.initStencilToWrite();
        RenderUtils.drawRoundedFullGradientShadowFullGradientRoundedFullGradientRectWithBloomBool(2.5f, 2.5f, h - 2.5f, h - 2.5f, 5.0f, 0.0f, -1, -1, -1, -1, false, true, false);
        StencilUtil.readStencilBuffer(0);
        RenderUtils.drawRoundedFullGradientShadowFullGradientRoundedFullGradientRectWithBloomBool(x, y, x + w, y + h, 5.0f, 1.25f, bgC, bgC, bgC, bgC, true, true, true);
        RenderUtils.drawRoundedFullGradientShadowFullGradientRoundedFullGradientRectWithBloomBool(x, y, x + w, y + h, 5.0f, 18.0f, bgSH, bgSH, bgSH, bgSH, true, false, true);
        StencilUtil.uninitStencilBuffer();
        GlStateManager.enableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        for (int i = 0; i < particles.size(); ++i) {
            particle particle2 = particles.get(i);
            float timePC = (float)particle2.getTime() / 700.0f;
            if (timePC >= 1.0f) {
                particles.remove(particle2);
                continue;
            }
            int pc = ColorUtils.getOverallColorFrom(hpC, hpC2, timePC);
            int pColor = ColorUtils.swapAlpha(pc, 255.0f * (1.0f - timePC) * percScaled);
            particle2.update(pColor);
        }
        if (target.hurtTime > 8) {
            for (int count = 0; count < 2; ++count) {
                particles.add(new particle(x + h / 2.0f, y + h / 2.0f));
            }
        }
        if (res != null) {
            GL11.glTranslated(x, y, 0.0);
            mc.getTextureManager().bindTexture(res);
            StencilUtil.initStencilToWrite();
            RenderUtils.drawRoundedFullGradientShadowFullGradientRoundedFullGradientRectWithBloomBool(2.5f, 2.5f, h - 2.5f, h - 2.5f, 5.0f, 0.0f, -1, -1, -1, -1, false, true, false);
            StencilUtil.readStencilBuffer(1);
            RenderUtils.glColor(ColorUtils.swapAlpha(ColorUtils.swapDark(ColorUtils.getColor(255, 255 - 80 * (target.hurtTime / 9), 255 - 80 * (target.hurtTime / 9)), 0.5f + percScaled / 2.0f), 255.0f * percScaled * (0.5f + percScaled / 2.0f)));
            Gui.drawScaledCustomSizeModalRect2(2.5f, 2.5f, 8.0f, 8.0f, 8.0f, 8.0f, h - 5.0f, h - 5.0f, 64.0f, 64.0f);
            StencilUtil.uninitStencilBuffer();
            GL11.glTranslated(-x, -y, 0.0);
            RenderUtils.roundedFullRoundedOutline(x + 2.5f, y + 2.5f, x + h - 2.5f, y + h - 2.5f, 5.1f, 5.25f, 5.25f, 5.25f, roundC);
        }
        if (ColorUtils.getAlphaFromColor(texC) >= 28) {
            namefont.drawStringWithShadow(this.targetName, texX, y + 4.5f, texC);
        }
        this.alphaHp.to = MathUtils.getDifferenceOf(hpX2 = MathUtils.clamp(x + h + 1.0f + (w - h - 5.0f) * this.hpRectAnim, (hpX1 = x + h + 1.0f) + 4.0f, x + h + 1.0f + (w - h - 5.0f) * this.hpRectAnim), hpX3 = x + h + 1.0f + (w - h - 5.0f)) > 9.0 ? 255.0f : 0.0f;
        RenderUtils.drawRoundedFullGradientShadowFullGradientRoundedFullGradientRectWithBloomBool(hpX1, y + h - 8.5f, hpX3, y + h - 4.0f, 2.0f, 1.0f, hpbgC, hpbgC, hpbgC, hpbgC, false, true, true);
        RenderUtils.drawRoundedFullGradientShadowFullGradientRoundedFullGradientRectWithBloomBool(hpX1, y + h - 8.5f, hpX2, y + h - 4.0f, 2.0f, 3.0f, hpbgC2, hpbgC3, hpbgC3, hpbgC2, false, true, true);
        RenderUtils.drawRoundedFullGradientShadowFullGradientRoundedFullGradientRectWithBloomBool(hpX1, y + h - 8.5f, hpX2, y + h - 4.0f, 2.0f, 0.5f, hpC, hpC2, hpC2, hpC, false, true, true);
        StencilUtil.initStencilToWrite();
        RenderUtils.drawRoundedFullGradientShadowFullGradientRoundedFullGradientRectWithBloomBool(hpX1, y + h - 8.5f, hpX3, y + h - 3.5f, 2.0f, 0.5f, -1, -1, -1, -1, false, true, false);
        StencilUtil.readStencilBuffer(1);
        if (ColorUtils.getAlphaFromColor(hpSC) >= 28) {
            Fonts.mntsb_12.drawString(hp, hpX2 + 1.0f, y + h - 7.0f, hpSC);
        }
        StencilUtil.uninitStencilBuffer();
        int stacksCount = 0;
        ItemStack offhand = target.getHeldItemOffhand();
        ItemStack boots = target.getItemStackFromSlot(EntityEquipmentSlot.FEET);
        ItemStack leggings = target.getItemStackFromSlot(EntityEquipmentSlot.LEGS);
        ItemStack body = target.getItemStackFromSlot(EntityEquipmentSlot.CHEST);
        ItemStack helm = target.getItemStackFromSlot(EntityEquipmentSlot.HEAD);
        ItemStack inHand = target.getHeldItemMainhand();
        ItemStack[] stuff = new ItemStack[]{offhand, inHand, boots, leggings, body, helm};
        ArrayList<Object> stacks = new ArrayList<Object>();
        int j = 0;
        ItemStack[] array = stuff;
        int length = stuff.length;
        for (j = 0; j < length; ++j) {
            ItemStack i = array[j];
            if (i == null) continue;
            i.getItem();
            stacks.add(i);
        }
        for (ItemStack itemStack : stacks) {
            if (itemStack.isEmpty()) continue;
            ++stacksCount;
        }
        if (stacksCount != 0) {
            RenderUtils.drawRoundedFullGradientShadowFullGradientRoundedFullGradientRectWithBloomBool(x + h + 2.0f, y + h - 23.0f, x + h + 4.0f + (float)stacksCount * 8.6f, y + h - 13.0f, 4.0f, 0.5f, itC, itC, itC, itC, false, true, true);
            RenderUtils.drawRoundedFullGradientShadowFullGradientRoundedFullGradientRectWithBloomBool(x + h + 2.0f, y + h - 23.0f, x + h + 4.0f + (float)stacksCount * 8.6f, y + h - 13.0f, 4.0f, 5.5f, hpbgC, hpbgC, hpbgC, hpbgC, false, false, true);
        }
        RenderItem itemRender = mc.getRenderItem();
        RenderHelper.enableGUIStandardItemLighting();
        GlStateManager.enableDepth();
        float f = h - 5.0f;
        float yn = 16.0f;
        GL11.glTranslated(x, y, 0.0);
        f *= 2.0f;
        yn *= 2.0f;
        GL11.glScaled(0.5, 0.5, 0.5);
        for (ItemStack itemStack : stacks) {
            if (!itemStack.isEmpty()) {
                f += 17.0f;
            }
            GL11.glTranslated(f, yn, 0.0);
            GL11.glTranslated(8.0, 8.0, 0.0);
            GL11.glScaled(percScaled * percScaled, percScaled * percScaled, 1.0);
            GL11.glTranslated(-8.0, -8.0, 0.0);
            itemRender.zLevel = 200.0f;
            itemRender.renderItemAndEffectIntoGUI(itemStack, 0, 0);
            if (255.0f * percScaled >= 26.0f) {
                itemRender.renderItemOverlayIntoGUIWithTextColor(Fonts.minecraftia_16, itemStack, 0, 0, itemStack.getCount(), ColorUtils.swapAlpha(ColorUtils.getFixedWhiteColor(), MathUtils.clamp(255.0f * percScaled, 30.0f, 255.0f)));
            }
            RenderUtils.drawItemWarnIfLowDur(itemStack, 0.0f, 0.0f, percScaled, 1.0f);
            itemRender.zLevel = 0.0f;
            GL11.glTranslated(8.0, 8.0, 0.0);
            GL11.glScaled(1.0f / (percScaled * percScaled), 1.0f / (percScaled * percScaled), 1.0);
            GL11.glTranslated(-8.0, -8.0, 0.0);
            GL11.glTranslated(-f, -yn, 0.0);
        }
        GL11.glScaled(2.0, 2.0, 2.0);
        f /= 2.0f;
        yn /= 2.0f;
        GL11.glTranslated(-x, -y, 0.0);
        RenderHelper.disableStandardItemLighting();
        GL11.glPopMatrix();
    }

    void renderWetWorn(EntityLivingBase target) {
        ScaledResolution scale = new ScaledResolution(mc);
        ResourceLocation res = OldSkin != null ? OldSkin : (skin != null ? skin : null);
        float aPC = this.Scaleclamp();
        String hp = target.getHealth() == 0.0f ? "" : String.format("%.1f", Float.valueOf(this.hpRectAnim * target.getMaxHealth() + target.getAbsorptionAmount())) + "hp";
        float w = widthHud;
        float h = heightHud;
        CFontRenderer namefont = Fonts.mntsb_15;
        float x = xPosHud;
        float y = yPosHud;
        float texX = x + h;
        widthHud = MathUtils.clamp(texX - x + (float)namefont.getStringWidth(this.targetName) + 4.0f, 100.0f, 900.0f);
        heightHud = 36.0f;
        this.hpRectAnim = MathUtils.lerp(this.hpRectAnim, MathUtils.clamp(target.getHealth() / target.getMaxHealth(), 0.0f, 1.0f), 0.015f * (float)Minecraft.frameTime);
        this.armorHealth = MathUtils.lerp(this.armorHealth, this.getArmorPercent01(target), 0.015f * (float)Minecraft.frameTime);
        int stacksCount = 0;
        ItemStack offhand = target.getHeldItemOffhand();
        ItemStack boots = target.getItemStackFromSlot(EntityEquipmentSlot.FEET);
        ItemStack leggings = target.getItemStackFromSlot(EntityEquipmentSlot.LEGS);
        ItemStack body = target.getItemStackFromSlot(EntityEquipmentSlot.CHEST);
        ItemStack helm = target.getItemStackFromSlot(EntityEquipmentSlot.HEAD);
        ItemStack inHand = target.getHeldItemMainhand();
        ItemStack[] stuff = new ItemStack[]{offhand, inHand, boots, leggings, body, helm};
        ArrayList<Object> stacks = new ArrayList<Object>();
        int j = 0;
        ItemStack[] array = stuff;
        int length = stuff.length;
        for (j = 0; j < length; ++j) {
            ItemStack i = array[j];
            if (i == null) continue;
            i.getItem();
            stacks.add(i);
        }
        for (ItemStack itemStack : stacks) {
            if (itemStack.isEmpty()) continue;
            ++stacksCount;
        }
        int bgOutC = ColorUtils.swapAlpha(ClientColors.getColor1(), (float)ColorUtils.getAlphaFromColor(ClientColors.getColor1()) * 0.85f * aPC * aPC);
        int n = ColorUtils.swapAlpha(ClientColors.getColor2(50), (float)ColorUtils.getAlphaFromColor(ClientColors.getColor2()) * 0.85f * aPC * aPC);
        int bgOutC3 = ColorUtils.swapAlpha(ClientColors.getColor2(150), (float)ColorUtils.getAlphaFromColor(ClientColors.getColor2()) * 0.85f * aPC * aPC);
        int bgOutC4 = ColorUtils.swapAlpha(ClientColors.getColor1(100), (float)ColorUtils.getAlphaFromColor(ClientColors.getColor1()) * 0.85f * aPC * aPC);
        int bgC1 = ColorUtils.getOverallColorFrom(bgOutC, ColorUtils.getColor(0, 0, 0, 100.0f * aPC), 0.7f);
        int bgC2 = ColorUtils.getOverallColorFrom(n, ColorUtils.getColor(0, 0, 0, 100.0f * aPC), 0.7f);
        int hpBG = ColorUtils.getColor(0, 0, 0, 50.0f * aPC);
        int texC = ColorUtils.swapAlpha(-1, 200.0f * aPC * aPC);
        int texC2 = ColorUtils.swapAlpha(0, 175.0f * aPC);
        GlStateManager.pushMatrix();
        GlStateManager.translate(0.0f, 5.0f - aPC * 5.0f, 0.0f);
        RenderUtils.customScaledObject2D(x, y, w, h, this.Scale());
        RenderUtils.drawRoundedFullGradientShadowFullGradientRoundedFullGradientRectWithBloomBool(x, y, x + w, y + h, 4.0f, 2.5f, bgOutC, n, bgOutC3, bgOutC4, false, false, true);
        RenderUtils.drawRoundedFullGradientShadowFullGradientRoundedFullGradientRectWithBloomBool(x, y, x + w, y + h, 4.0f, 1.0f, bgC1, bgC2, bgC2, bgC1, false, true, true);
        RenderUtils.drawRoundedFullGradientShadowFullGradientRoundedFullGradientRectWithBloomBool(x, y, x + w, y + h, 4.0f, 1.5f, bgC1, bgC2, bgC2, bgC1, false, false, true);
        if (target.hurtTime > 4) {
            float rt = (float)(-5.0 * Math.random() + 10.0 * Math.random());
            float rt2 = (float)(-5.0 * Math.random() + 10.0 * Math.random());
            particles.add(new particle(x + h / 2.0f + rt, y + h / 2.0f + rt2));
        }
        for (int i = 0; i < particles.size(); ++i) {
            particle particle2 = particles.get(i);
            float timePC = (float)particle2.getTime() / 700.0f;
            if (timePC >= 1.0f) {
                particles.remove(particle2);
                continue;
            }
            int pc = ColorUtils.getOverallColorFrom(bgOutC, n, timePC);
            int pColor = ColorUtils.swapAlpha(pc, 255.0f * (1.0f - timePC) * aPC);
            particle2.update(pColor);
        }
        RenderUtils.customScaledObject2D(x + 4.0f, y + 4.0f, h - 4.0f, h - 4.0f, 0.8f + aPC / 5.0f);
        if (res != null) {
            GL11.glTranslated(x, y, 0.0);
            mc.getTextureManager().bindTexture(res);
            StencilUtil.initStencilToWrite();
            RenderUtils.drawRoundedFullGradientShadowFullGradientRoundedFullGradientRectWithBloomBool(4.0f, 4.0f, h - 4.0f, h - 4.0f, 4.5f, 1.0f, -1, -1, -1, -1, false, true, false);
            StencilUtil.readStencilBuffer(1);
            RenderUtils.glColor(ColorUtils.swapAlpha(ColorUtils.swapDark(ColorUtils.getColor(255, (int)(255.0f - 80.0f * ((float)target.hurtTime / 9.0f)), (int)(255.0f - 80.0f * ((float)target.hurtTime / 9.0f))), 0.5f + aPC / 2.0f), 255.0f * aPC * aPC));
            Gui.drawScaledCustomSizeModalRect2(4.0f, 4.0f, 8.0f, 8.0f, 8.0f, 8.0f, h - 8.0f, h - 8.0f, 64.0f, 64.0f);
            StencilUtil.uninitStencilBuffer();
            GL11.glTranslated(-x, -y, 0.0);
            RenderUtils.drawOutsideAndInsideFullRoundedFullGradientShadowRectWithBloomBoolShadowsBoolChangeShadowSize(x + 4.0f, y + 4.0f, x + h - 4.0f, y + h - 4.0f, 3.0f, 1.5f, 1.0f, bgOutC, n, bgOutC3, bgOutC4, false, true, true);
        } else {
            Fonts.noise_24.drawString("?", x + h / 2.0f - 2.5f, y + h / 2.0f - 5.0f, bgOutC);
        }
        RenderUtils.customScaledObject2D(x + 4.0f, y + 4.0f, h - 4.0f, h - 4.0f, 1.0f / (0.8f + aPC / 5.0f));
        RenderUtils.customScaledObject2D(x + h + 2.0f, y + h - 13.0f, x + w - 4.0f - (x + h + 2.0f), 9.0f, 0.8f + aPC / 5.0f);
        RenderUtils.drawRoundedFullGradientShadowFullGradientRoundedFullGradientRectWithBloomBool(x + h + 2.0f, y + h - 13.0f, x + w - 4.0f, y + h - 4.0f, 2.5f, 0.5f, hpBG, hpBG, hpBG, hpBG, false, true, true);
        float hp2x = x + h + 3.0f + (x + w - 5.0f - (x + h + 3.0f)) * this.hpRectAnim;
        StencilUtil.initStencilToWrite();
        RenderUtils.drawRect(x + h + 2.0f, y + h - 12.5f, hp2x + 1.0f, y + h - 4.5f, -1);
        StencilUtil.readStencilBuffer(1);
        RenderUtils.drawRoundedFullGradientShadowFullGradientRoundedFullGradientRectWithBloomBool(x + h + 3.0f, y + h - 12.0f, x + w - 5.0f, y + h - 5.0f, 2.0f, 1.0f, bgOutC, n, bgOutC3, bgOutC4, false, true, true);
        if (ColorUtils.getAlphaFromColor(texC2) >= 33) {
            Fonts.mntsb_12.drawString(hp, MathUtils.clamp(hp2x - (float)Fonts.mntsb_12.getStringWidth(hp), x + h + 3.5f, x + h + 3.0f + (x + w - 5.0f - (x + h + 3.0f)) * 0.5f - (float)(Fonts.mntsb_12.getStringWidth(hp) / 2) + 1.0f), y + h - 9.5f, texC2);
        }
        StencilUtil.uninitStencilBuffer();
        RenderUtils.customScaledObject2D(x + h + 2.0f, y + h - 13.0f, x + w - 4.0f - (x + h + 2.0f), 9.0f, 1.0f / (0.8f + aPC / 5.0f));
        if (ColorUtils.getAlphaFromColor(texC) >= 33) {
            if (stacksCount == 0) {
                y += 5.0f;
            }
            RenderUtils.customScaledObject2D(x + h + 3.0f + (x + w - 5.0f - (x + h + 3.0f)) * 0.5f - (float)(Fonts.mntsb_13.getStringWidth(this.targetName) / 2), y + 6.0f, Fonts.mntsb_13.getStringWidth(this.targetName), Fonts.mntsb_13.getStringHeight(this.targetName) / 2, 0.8f + aPC / 5.0f);
            namefont.drawStringWithShadow(this.targetName, x + h + 3.0f + (x + w - 5.0f - (x + h + 3.0f)) * 0.5f - (float)namefont.getStringWidth(this.targetName) / 2.0f - 1.0f, y + 5.0f, texC);
            RenderUtils.customScaledObject2D(x + h + 3.0f + (x + w - 5.0f - (x + h + 3.0f)) * 0.5f - (float)(Fonts.mntsb_13.getStringWidth(this.targetName) / 2), y + 6.0f, Fonts.mntsb_13.getStringWidth(this.targetName), Fonts.mntsb_13.getStringHeight(this.targetName) / 2, 1.0f / (0.8f + aPC / 5.0f));
            if (stacksCount == 0) {
                y -= 5.0f;
            }
        }
        RenderItem itemRender = mc.getRenderItem();
        RenderHelper.enableGUIStandardItemLighting();
        GlStateManager.enableDepth();
        float xn = h - 5.0f;
        float yn = 12.0f;
        GL11.glTranslated(x, y, 0.0);
        xn *= 2.0f;
        yn *= 2.0f;
        GL11.glScaled(0.5, 0.5, 0.5);
        for (ItemStack itemStack : stacks) {
            if (!itemStack.isEmpty()) {
                xn += 17.0f;
            }
            GL11.glTranslated(xn, yn, 0.0);
            GL11.glTranslated(8.0, 8.0, 0.0);
            GL11.glScaled(aPC, aPC, 1.0);
            GL11.glTranslated(-8.0, -8.0, 0.0);
            itemRender.zLevel = 200.0f;
            itemRender.renderItemAndEffectIntoGUI(itemStack, 0, 0);
            if (ColorUtils.getAlphaFromColor(texC) >= 33) {
                itemRender.renderItemOverlayIntoGUIWithTextColor(Fonts.minecraftia_16, itemStack, 0, 0, itemStack.getCount(), texC);
            }
            RenderUtils.drawItemWarnIfLowDur(itemStack, 0.0f, 0.0f, aPC, 1.0f);
            itemRender.zLevel = 0.0f;
            GL11.glTranslated(8.0, 8.0, 0.0);
            GL11.glScaled(1.0f / aPC, 1.0f / aPC, 1.0);
            GL11.glTranslated(-8.0, -8.0, 0.0);
            GL11.glTranslated(-xn, -yn, 0.0);
        }
        GL11.glScaled(2.0, 2.0, 2.0);
        xn /= 2.0f;
        yn /= 2.0f;
        GL11.glTranslated(-x, -y, 0.0);
        RenderHelper.disableStandardItemLighting();
        GlStateManager.popMatrix();
    }

    private void neomoinCircle(float x, float y, float r, float lineWHP, float lineWDMG, float aPC, float pcHP, float pcDMG, boolean isBG, int colBG) {
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.disableAlpha();
        GlStateManager.shadeModel(7425);
        GL11.glScaled(0.5, 0.5, 0.5);
        GL11.glTranslated(x, y, 0.0);
        GL11.glEnable(2832);
        GL11.glDisable(3008);
        GL11.glPointSize(lineWHP);
        GL11.glBegin(0);
        float startHP = isBG ? 0.0f : 180.0f;
        float endHP = isBG ? 360.0f : 180.0f + pcHP * 360.0f;
        float endDMG = endHP + (pcDMG - pcHP) * 360.0f;
        int step = isBG ? 15 : 3;
        float end = 0.0f;
        for (float i = startHP; i < endHP; i += (float)step) {
            end += 1.0f;
            int c = isBG ? colBG : ClientColors.getColor1((int)(i * 3.0f));
            int color = ColorUtils.swapAlpha(c, (float)ColorUtils.getAlphaFromColor(c) * aPC * aPC * aPC * 0.5f);
            double x1 = (double)x + Math.sin((double)i * Math.PI / 180.0) * (double)r * 2.0;
            double y1 = (double)y + Math.cos((double)i * Math.PI / 180.0) * (double)r * 2.0;
            RenderUtils.glColor(color);
            GL11.glVertex2d(x1, y1);
        }
        GL11.glEnd();
        GL11.glPointSize(lineWDMG);
        GL11.glBegin(0);
        if (!isBG) {
            float cucan = 0.0f;
            for (float i = endHP; i < endDMG; i += (float)step) {
                cucan += 1.0f;
            }
            float cucan2 = 0.0f;
            for (float i = endHP; i < endDMG; i += (float)step) {
                int c = ClientColors.getColor1((int)(i * 3.0f));
                int color = ColorUtils.swapAlpha(c, (float)ColorUtils.getAlphaFromColor(c) * aPC * aPC * aPC * (cucan2 / cucan) * 0.5f);
                double x1 = (double)x + Math.sin((double)i * Math.PI / 180.0) * (double)r * 2.0;
                double y1 = (double)y + Math.cos((double)i * Math.PI / 180.0) * (double)r * 2.0;
                RenderUtils.glColor(color);
                GL11.glVertex2d(x1, y1);
                cucan2 += 1.0f;
            }
        }
        GL11.glEnd();
        GL11.glTranslated(-x, -y, 0.0);
        GL11.glScaled(2.0, 2.0, 2.0);
        GL11.glPointSize(1.0f);
        GL11.glDisable(2832);
        GL11.glEnable(3008);
        GlStateManager.disableBlend();
        GlStateManager.enableTexture2D();
        GlStateManager.enableAlpha();
        GlStateManager.shadeModel(7424);
        GlStateManager.resetColor();
    }

    private final void renderNeomoin(EntityLivingBase target) {
        ScaledResolution scale = new ScaledResolution(mc);
        ResourceLocation res = skin;
        float aPC = this.Scaleclamp();
        String hp = target.getHealth() == 0.0f ? "" : String.format("%.1f", Float.valueOf(this.hpRectAnim * target.getMaxHealth() + target.getAbsorptionAmount())) + "hp";
        float w = widthHud;
        float h = heightHud;
        CFontRenderer namefont = Fonts.mntsb_13;
        float x = xPosHud;
        float y = yPosHud + (1.0f - aPC) * 3.0f;
        float texX = x + h * 2.0f - 4.0f;
        int stacksCount = 0;
        ItemStack offhand = target.getHeldItemOffhand();
        ItemStack boots = target.getItemStackFromSlot(EntityEquipmentSlot.FEET);
        ItemStack leggings = target.getItemStackFromSlot(EntityEquipmentSlot.LEGS);
        ItemStack body = target.getItemStackFromSlot(EntityEquipmentSlot.CHEST);
        ItemStack helm = target.getItemStackFromSlot(EntityEquipmentSlot.HEAD);
        ItemStack inHand = target.getHeldItemMainhand();
        ItemStack[] stuff = new ItemStack[]{offhand, inHand, boots, leggings, body, helm};
        ArrayList<Object> stacks = new ArrayList<Object>();
        int j = 0;
        ItemStack[] array = stuff;
        int length = stuff.length;
        for (j = 0; j < length; ++j) {
            ItemStack i = array[j];
            if (i == null) continue;
            i.getItem();
            stacks.add(i);
        }
        for (ItemStack itemStack : stacks) {
            if (itemStack.isEmpty()) continue;
            ++stacksCount;
        }
        float wHD = texX - x + (float)namefont.getStringWidth(this.targetName) + 4.0f;
        if (h * 2.0f + 8.5f * (float)stacksCount + 5.0f > wHD) {
            wHD = h * 2.0f + 8.5f * (float)stacksCount + 5.0f;
        }
        widthHud = MathUtils.clamp(MathUtils.lerp(widthHud, wHD, 0.01f * (float)Minecraft.frameTime), h * 2.0f, 900.0f);
        heightHud = 30.0f;
        this.hpAnim.speed = 0.075f;
        this.hurtHpAnim.speed = 0.1f;
        this.hpAnim.to = MathUtils.clamp(target.getHealth() / target.getMaxHealth(), 0.0f, 1.0f);
        if (target.hurtTime < 3) {
            this.hurtHpAnim.to = MathUtils.clamp(target.getHealth() / target.getMaxHealth(), 0.0f, 1.0f);
        }
        if (this.hurtHpAnim.getAnim() < this.hpAnim.getAnim()) {
            this.hurtHpAnim.setAnim(this.hpAnim.getAnim());
        }
        float f = this.hpAnim.getAnim();
        float hurtRectAnim = this.hurtHpAnim.getAnim();
        int accentV1 = ColorUtils.swapAlpha(ClientColors.getColor1(), (float)ColorUtils.getAlphaFromColor(ClientColors.getColor1()) * aPC);
        int accentV2 = ColorUtils.swapAlpha(ClientColors.getColor2(), (float)ColorUtils.getAlphaFromColor(ClientColors.getColor2()) * aPC);
        int accent1 = ColorUtils.swapAlpha(accentV1, (float)ColorUtils.getAlphaFromColor(accentV1) * aPC);
        int accent2 = ColorUtils.swapAlpha(accentV2, (float)ColorUtils.getAlphaFromColor(accentV2) * aPC);
        int bgc1 = ColorUtils.getOverallColorFrom(accent1, ColorUtils.getColor(0, 0, 0, ColorUtils.getAlphaFromColor(accent1)), 0.8f);
        int bgc2 = ColorUtils.getOverallColorFrom(accent2, ColorUtils.getColor(0, 0, 0, ColorUtils.getAlphaFromColor(accent2)), 0.8f);
        int bg1 = ColorUtils.swapAlpha(bgc1, (float)ColorUtils.getAlphaFromColor(bgc1) * aPC / 2.2f);
        int bg2 = ColorUtils.swapAlpha(bgc2, (float)ColorUtils.getAlphaFromColor(bgc2) * aPC / 2.2f);
        int bgOut1 = ColorUtils.swapAlpha(ColorUtils.getOverallColorFrom(accent1, bgc1, 0.6f), (float)ColorUtils.getAlphaFromColor(bgc1) * aPC);
        int bgOut2 = ColorUtils.swapAlpha(ColorUtils.getOverallColorFrom(accent2, bgc2, 0.6f), (float)ColorUtils.getAlphaFromColor(bgc2) * aPC);
        int headCol = ColorUtils.swapAlpha(ColorUtils.swapDark(ColorUtils.getColor(255, (int)(255.0f - 80.0f * ((float)target.hurtTime / 9.0f)), (int)(255.0f - 80.0f * ((float)target.hurtTime / 9.0f))), 0.5f + aPC / 2.0f), 255.0f * aPC * aPC);
        int hpBgC = ColorUtils.getColor(11, 11, 11, 60.0f * aPC);
        int hpBgCOut = ColorUtils.getColor(11, 11, 11, 100.0f * aPC);
        int texCol = ColorUtils.swapAlpha(ColorUtils.getFixedWhiteColor(), 255.0f * aPC * aPC);
        int itemsBgCol = ColorUtils.getColor(11, 11, 11, 80.0f * aPC);
        GL11.glPushMatrix();
        RenderUtils.drawOutsideAndInsideFullRoundedFullGradientShadowRectWithBloomBoolShadowsBoolChangeShadowSize(x, y, x + w, y + h, 3.5f, 2.0f, 6.0f, bg1, bg2, bg2, bg1, true, true, true);
        RenderUtils.drawRoundedFullGradientShadowFullGradientRoundedFullGradientRectWithBloomBool(x, y, x + w, y + h, 5.0f, 1.0f, bg1, bg2, bg2, bg1, false, true, true);
        if (target.hurtTime > 7) {
            float rt = (float)(-5.0 * Math.random() + 10.0 * Math.random());
            float rt2 = (float)(-5.0 * Math.random() + 10.0 * Math.random());
            particles.add(new particle(x + h / 2.0f + rt, y + h / 2.0f + rt2));
        }
        for (int i = 0; i < particles.size(); ++i) {
            particle particle2 = particles.get(i);
            float timePC = (float)particle2.getTime() / 700.0f;
            if (timePC >= 1.0f) {
                particles.remove(particle2);
                continue;
            }
            int pC = ColorUtils.swapAlpha(ClientColors.getColor1(i * 10), (float)ColorUtils.getAlphaFromColor(ClientColors.getColor1(i * 10)) * aPC);
            int pc1 = ColorUtils.swapAlpha(pC, (float)ColorUtils.getAlphaFromColor(pC) * aPC * aPC);
            int pColor = ColorUtils.swapAlpha(pc1, 255.0f * (1.0f - timePC) * aPC);
            particle2.update(pColor);
        }
        if (res != null) {
            StencilUtil.renderInStencil(() -> RenderUtils.drawRoundedFullGradientShadowFullGradientRoundedFullGradientRectWithBloomBool(x + 3.0f, y + 3.0f, x + h - 3.0f, y + h - 3.0f, 3.8f, 0.0f, -1, -1, -1, -1, false, true, false), () -> {
                GL11.glTranslated(x, y, 0.0);
                mc.getTextureManager().bindTexture(res);
                RenderUtils.glColor(headCol);
                Gui.drawScaledCustomSizeModalRect2(3.0f, 3.0f, 8.0f, 8.0f, 8.0f, 8.0f, h - 6.0f, h - 6.0f, 64.0f, 64.0f);
                GL11.glTranslated(-x, -y, 0.0);
            }, 1);
            RenderUtils.drawOutsideAndInsideFullRoundedFullGradientShadowRectWithBloomBoolShadowsBoolChangeShadowSize(x + 3.0f, y + 3.0f, x + h - 3.0f, y + h - 3.0f, 3.0f, 1.0f, 1.0f, accent1, ColorUtils.getOverallColorFrom(accent1, accent2, h / w), ColorUtils.getOverallColorFrom(accent1, accent2, h / w), accent1, false, true, true);
        } else if (ColorUtils.getAlphaFromColor(bgOut1) >= 33) {
            Fonts.noise_24.drawString("?", x + h / 2.0f - 2.5f, y + h / 2.0f - 5.0f, bgOut1);
        }
        float rPlus = MathUtils.clamp((hurtRectAnim - f) * (target.getMaxHealth() / 3.0f), 0.0f, 3.0f) * 1.2f;
        float cx = x + w - h / 2.0f;
        float cy = y + h / 2.0f;
        float cr = h / 2.0f - 4.0f + rPlus;
        float cwHP = 3.5f + rPlus;
        float cwDMG = 2.0f + rPlus;
        this.neomoinCircle(cx, cy, cr, cwHP, cwDMG, aPC, 1.0f, 1.0f, true, hpBgCOut);
        this.neomoinCircle(cx, cy, cr, cwHP, cwDMG, aPC, f, hurtRectAnim, false, 0);
        float r = 8.5f;
        RenderUtils.drawSmoothCircle(x + w - h / 2.0f, y + h / 2.0f, r, hpBgC);
        String hpStr = String.format("%.1f", Float.valueOf(f * target.getMaxHealth())).replace(".", ",");
        if (ColorUtils.getAlphaFromColor(texCol) >= 32) {
            Fonts.mntsb_12.drawString(hpStr, x + w - h / 2.0f - (float)(Fonts.mntsb_12.getStringWidth(hpStr) / 2), y + h / 2.0f - 1.0f, texCol);
        }
        if (ColorUtils.getAlphaFromColor(texCol) >= 32) {
            namefont.drawString(this.targetName, x + h + 1.0f, y + 5.0f, texCol);
        }
        RenderItem itemRender = mc.getRenderItem();
        RenderHelper.enableGUIStandardItemLighting();
        GlStateManager.enableDepth();
        float xn = h - 5.0f;
        float yn = 16.0f;
        if (stacksCount != 0) {
            RenderUtils.drawRoundedFullGradientShadowFullGradientRoundedFullGradientRectWithBloomBool(x + h + 2.5f, y + yn - 1.0f, x + h + 4.0f + 8.5f * (float)stacksCount, y + yn + 9.0f, 2.0f, 1.0f, itemsBgCol, itemsBgCol, itemsBgCol, itemsBgCol, false, true, true);
        }
        GlStateManager.enableDepth();
        GL11.glTranslated(x, y, 0.0);
        xn *= 2.0f;
        yn *= 2.0f;
        GL11.glScaled(0.5, 0.5, 0.5);
        for (ItemStack itemStack : stacks) {
            if (!itemStack.isEmpty()) {
                xn += 17.0f;
            }
            GL11.glTranslated(xn, yn, 0.0);
            GL11.glTranslated(8.0, 8.0, 0.0);
            GL11.glScaled(aPC, aPC, 1.0);
            GL11.glTranslated(-8.0, -8.0, 0.0);
            itemRender.zLevel = 200.0f;
            itemRender.renderItemAndEffectIntoGUI(itemStack, 0, 0);
            if (ColorUtils.getAlphaFromColor(texCol) >= 32) {
                itemRender.renderItemOverlayIntoGUIWithTextColor(Fonts.minecraftia_16, itemStack, 0, 0, itemStack.getCount(), texCol);
            }
            RenderUtils.drawItemWarnIfLowDur(itemStack, 0.0f, 0.0f, aPC, 1.0f);
            itemRender.zLevel = 0.0f;
            GL11.glTranslated(8.0, 8.0, 0.0);
            GL11.glScaled(1.0f / aPC, 1.0f / aPC, 1.0);
            GL11.glTranslated(-8.0, -8.0, 0.0);
            GL11.glTranslated(-xn, -yn, 0.0);
        }
        GL11.glScaled(2.0, 2.0, 2.0);
        xn /= 2.0f;
        yn /= 2.0f;
        GL11.glTranslated(-x, -y, 0.0);
        RenderHelper.disableStandardItemLighting();
        GL11.glPopMatrix();
    }

    private void renderModern(EntityLivingBase target) {
        float hpALLPC;
        ScaledResolution scale = new ScaledResolution(mc);
        ResourceLocation res = OldSkin != null ? OldSkin : (skin != null ? skin : null);
        float aPC = this.Scaleclamp();
        float w = widthHud;
        float h = heightHud;
        CFontRenderer namefont = Fonts.noise_17;
        CFontRenderer hpfont = Fonts.mntsb_10;
        float x = xPosHud;
        float y = yPosHud + (1.0f - aPC) * 3.0f;
        widthHud = 86.0f;
        heightHud = 21.0f;
        float hOf = 14.0f;
        int texCol = ColorUtils.swapAlpha(-1, 255.0f * aPC * aPC);
        int bgOutC = ColorUtils.swapAlpha(0, 150.0f * aPC);
        int bgOutCBG = ColorUtils.swapAlpha(0, 45.0f * aPC);
        this.absorbAnim.to = target.getAbsorptionAmount();
        float hpMax = target.getMaxHealth() + this.absorbAnim.getAnim();
        float hpALLPC2 = hpALLPC = (target.smoothHealth.getAnim() + this.absorbAnim.getAnim()) / hpMax;
        float absALLPC = this.absorbAnim.getAnim() / hpMax;
        hpALLPC -= absALLPC;
        String hpStr = target.getHealth() == 0.0f ? "" : String.format("%.1f", Float.valueOf(hpALLPC2 * hpMax)) + "hp";
        GL11.glPushMatrix();
        float sclaePC = 0.45f + aPC * 0.55f;
        RenderUtils.customScaledObject2D(x, y, w, h, sclaePC);
        float round = 2.0f;
        RenderUtils.drawRoundedFullGradientShadowFullGradientRoundedFullGradientRectWithBloomBool(x, y + hOf, x + w, y + h, round, 1.5f, bgOutC, bgOutC, bgOutC, bgOutC, false, false, true);
        RenderUtils.drawRoundedFullGradientShadowFullGradientRoundedFullGradientRectWithBloomBool(x, y + hOf, x + w, y + h, round, 0.0f, bgOutCBG, bgOutCBG, bgOutCBG, bgOutCBG, false, true, false);
        if (target.getHealth() != 0.0f) {
            float offsetX = 2.5f;
            float offsetY = 2.5f;
            float offsetDC = 0.0f;
            float hpYMin = y + hOf + offsetY;
            float hpYMax = y + h - offsetY;
            float hpMinX = x + offsetX;
            float hpMaxX = x + w - offsetX;
            float hpX1 = hpMinX + (w - offsetX * 2.0f) * hpALLPC;
            float hpX2 = hpMinX + (w - offsetX * 2.0f) * hpALLPC2;
            int hpCol = ColorUtils.getColor(0, 255, 120, 255.0f * aPC);
            int absCol = ColorUtils.getColor(255, 220, 0, 255.0f * aPC);
            float grandX1 = MathUtils.clamp(hpX1 - offsetDC, hpMinX, hpMaxX);
            float grandX2 = MathUtils.clamp(hpX1 + offsetDC, hpMinX, hpMaxX);
            RenderUtils.drawRect(hpMinX, hpYMin, grandX1, hpYMax, hpCol);
            hpCol = ColorUtils.swapAlpha(hpCol, (float)ColorUtils.getAlphaFromColor(hpCol) / 1.7f);
            RenderUtils.drawLightContureRectSidewaysSmooth(hpMinX, hpYMin, grandX1, hpYMax, hpCol, 0);
            RenderUtils.drawRect(MathUtils.clamp(hpX1 + offsetDC, hpMinX, hpMaxX), hpYMin, hpX2, hpYMax, absCol);
            absCol = ColorUtils.swapAlpha(absCol, (float)ColorUtils.getAlphaFromColor(hpCol) / 1.7f);
            RenderUtils.drawLightContureRectSidewaysSmooth(MathUtils.clamp(hpX1 + offsetDC, hpMinX, hpMaxX), hpYMin, hpX2, hpYMax, 0, absCol);
            if ((float)ColorUtils.getAlphaFromColor(texCol) / 2.0f >= 33.0f) {
                StencilUtil.initStencilToWrite();
                RenderUtils.drawRect(x + 0.5f, y + hOf, x + w - 0.5f, y + h, -1);
                StencilUtil.readStencilBuffer(1);
                hpfont.drawString(hpStr, hpX2 + 2.0f, y + hOf + 3.5f, ColorUtils.swapAlpha(texCol, (float)ColorUtils.getAlphaFromColor(texCol) / 2.0f));
                StencilUtil.uninitStencilBuffer();
            }
        } else if (ColorUtils.getAlphaFromColor(texCol) >= 33) {
            hpfont.drawString("KILLED", x + w / 2.0f - (float)hpfont.getStringWidth("KILLED") / 2.0f, y + hOf + 3.5f, texCol);
        }
        RenderUtils.customScaledObject2DPro(x, y, w, h, sclaePC * sclaePC, 1.0f);
        if (ColorUtils.getAlphaFromColor(texCol) >= 33) {
            namefont.drawStringWithShadow(this.targetName, x + w / 2.0f - (float)namefont.getStringWidth(this.targetName) / 2.0f, y + 2.0f, texCol);
        }
        GL11.glPopMatrix();
    }

    private void renderBushy(EntityLivingBase target) {
        float f;
        GL11.glPushMatrix();
        ScaledResolution scale = new ScaledResolution(mc);
        int stacksCount = 0;
        ItemStack offhand = target.getHeldItemOffhand();
        ItemStack boots = target.getItemStackFromSlot(EntityEquipmentSlot.FEET);
        ItemStack leggings = target.getItemStackFromSlot(EntityEquipmentSlot.LEGS);
        ItemStack body = target.getItemStackFromSlot(EntityEquipmentSlot.CHEST);
        ItemStack helm = target.getItemStackFromSlot(EntityEquipmentSlot.HEAD);
        ItemStack inHand = target.getHeldItemMainhand();
        ItemStack[] stuff = new ItemStack[]{offhand, inHand, boots, leggings, body, helm};
        ArrayList<Object> stacks = new ArrayList<Object>();
        int j = 0;
        ItemStack[] array = stuff;
        int length = stuff.length;
        for (j = 0; j < length; ++j) {
            ItemStack i = array[j];
            if (i == null) continue;
            i.getItem();
            stacks.add(i);
        }
        for (ItemStack itemStack : stacks) {
            if (itemStack.isEmpty()) continue;
            ++stacksCount;
        }
        ResourceLocation res = OldSkin != null ? OldSkin : (skin != null ? skin : null);
        float f2 = this.Scaleclamp();
        f2 *= f2;
        CFontRenderer nameFont = Fonts.comfortaaBold_12;
        float w = widthHud;
        float h = heightHud;
        float x = xPosHud;
        float y = yPosHud;
        float texOrItemW = (float)stacksCount * 8.0f + 0.5f;
        float strW = nameFont.getStringWidth(this.targetName);
        if (texOrItemW < strW) {
            texOrItemW = strW;
        }
        widthHud = MathUtils.clamp(19.0f + texOrItemW, 60.0f, 1000.0f);
        heightHud = 18.0f;
        float hpHeight = 1.5f;
        int colBG1 = ColorUtils.getColor(0, 0, 0, 180.0f * f2 / 2.0f);
        int colBG2 = ColorUtils.getColor(0, 0, 0, 100.0f * f2 / 2.0f);
        int texCol = ColorUtils.getColor(255, 255, 255, 255.0f * f2);
        GL11.glTranslated(0.0, 10.0f - this.Scale() * 10.0f, 0.0);
        if (res != null) {
            RenderUtils.setupColor(ColorUtils.getOverallColorFrom(-1, ColorUtils.getColor(155, 0, 0), (float)target.hurtTime / 15.0f), 255.0f * f2 * f2);
            GL11.glTranslated(x, y, 0.0);
            GL11.glDisable(3008);
            GL11.glEnable(3042);
            Gui.drawScaledHead(res, 0, 0, 16, 16);
            GL11.glEnable(3008);
            GL11.glTranslated(-x, -y, 0.0);
            GlStateManager.resetColor();
            RenderUtils.drawAlphedRect(x, y + 16.0f, x + 16.0f, y + 16.5f, colBG1);
        } else if (255.0f * f2 >= 33.0f) {
            Fonts.mntsb_15.drawString("?", x + 5.0f, y + 6.0f, texCol);
        }
        RenderUtils.drawAlphedRect(x + (float)(res == null ? 0 : 16), y, x + w, y + h - hpHeight, colBG1);
        RenderUtils.drawLightContureRectSmooth(x, y, x + w, y + h, colBG1);
        RenderUtils.drawRoundedFullGradientShadowFullGradientRoundedFullGradientRectWithBloomBool(x, y, x + w, y + h, 0.0f, 4.0f, colBG2, colBG2, colBG2, colBG2, false, false, true);
        if (255.0f * f2 >= 33.0f) {
            float tw = w / 2.0f - 16.0f + 3.0f - strW / 2.0f + 16.0f + 5.5f;
            nameFont.drawString(this.targetName, x + tw, y + (stacksCount == 0 ? 6.5f : 2.5f), texCol);
        }
        RenderItem itemRender = mc.getRenderItem();
        RenderHelper.enableGUIStandardItemLighting();
        GlStateManager.enableDepth();
        float xn = 7.5f;
        float yn = 7.5f;
        int itemsBgCol = ColorUtils.getColor(0, 0, 0, 40.0f * f2);
        if (stacksCount != 0) {
            RenderUtils.drawAlphedRect(x + 16.5f, y + 7.5f, x + 16.5f + (float)stacksCount * 8.5f, y + 16.0f, itemsBgCol);
        }
        GlStateManager.enableDepth();
        GL11.glTranslated(x, y, 0.0);
        xn *= 2.0f;
        yn *= 2.0f;
        GL11.glScaled(0.5, 0.5, 0.5);
        for (ItemStack itemStack : stacks) {
            if (!itemStack.isEmpty()) {
                xn += 17.0f;
            }
            GL11.glTranslated(xn, yn, 0.0);
            GL11.glTranslated(8.0, 8.0, 0.0);
            GL11.glScaled(f2, f2, 1.0);
            GL11.glTranslated(-8.0, -8.0, 0.0);
            itemRender.zLevel = 200.0f;
            itemRender.renderItemAndEffectIntoGUI(itemStack, 0, 0);
            if (ColorUtils.getAlphaFromColor(texCol) >= 32) {
                itemRender.renderItemOverlayIntoGUIWithTextColor(Fonts.minecraftia_16, itemStack, 0, 0, itemStack.getCount(), texCol);
            }
            RenderUtils.drawItemWarnIfLowDur(itemStack, 0.0f, 0.0f, f2, 1.0f);
            itemRender.zLevel = 0.0f;
            GL11.glTranslated(8.0, 8.0, 0.0);
            GL11.glScaled(1.0f / f2, 1.0f / f2, 1.0);
            GL11.glTranslated(-8.0, -8.0, 0.0);
            GL11.glTranslated(-xn, -yn, 0.0);
        }
        GL11.glScaled(2.0, 2.0, 2.0);
        xn /= 2.0f;
        yn /= 2.0f;
        GL11.glTranslated(-x, -y, 0.0);
        RenderHelper.disableStandardItemLighting();
        this.absorbAnim.to = target.getAbsorptionAmount();
        float hpMax = target.getMaxHealth() + this.absorbAnim.getAnim();
        float hpALLPC2 = f = (target.smoothHealth.getAnim() + this.absorbAnim.getAnim()) / hpMax;
        float absALLPC = this.absorbAnim.getAnim() / hpMax;
        f -= absALLPC;
        String hpStr = target.getHealth() == 0.0f ? "" : String.format("%.1f", Float.valueOf(hpALLPC2 * hpMax)) + "hp";
        float hOf = heightHud - hpHeight;
        float offsetX = 0.0f;
        float offsetY = 0.0f;
        float offsetDC = 0.0f;
        float hpYMin = y + hOf + offsetY;
        float hpYMax = y + h - offsetY;
        float hpMinX = x + offsetX;
        float hpMaxX = x + w - offsetX;
        float hpX1 = hpMinX + (w - offsetX * 2.0f) * f;
        float hpX2 = hpMinX + (w - offsetX * 2.0f) * hpALLPC2;
        int hpCol = ColorUtils.getColor(0, 255, 120, 255.0f * f2);
        int absCol = ColorUtils.getColor(255, 220, 0, 255.0f * f2);
        float grandX1 = MathUtils.clamp(hpX1 - offsetDC, hpMinX, hpMaxX);
        float grandX2 = MathUtils.clamp(hpX1 + offsetDC, hpMinX, hpMaxX);
        RenderUtils.drawAlphedRect(hpMinX, hpYMin, grandX1, hpYMax, hpCol);
        RenderUtils.drawAlphedRect(MathUtils.clamp(hpX1 + offsetDC, hpMinX, hpMaxX), hpYMin, hpX2, hpYMax, absCol);
        RenderUtils.drawAlphedRect(hpX2, hpYMin, x + w, hpYMax, colBG1);
        GL11.glPopMatrix();
    }

    private void renderSubtle(EntityLivingBase target) {
        ResourceLocation res = OldSkin != null ? OldSkin : (skin != null ? skin : null);
        this.absorbAnim.to = target.getAbsorptionAmount();
        float targetHealth = target.getSmoothHealth() + this.absorbAnim.getAnim();
        float targetHealtMax = target.getMaxHealth() + this.absorbAnim.anim;
        float targetHealthPC = Math.max(Math.min(targetHealth / targetHealtMax, 1.0f), 0.0f);
        String hpStr = String.format("%.1f", Float.valueOf(targetHealth)).replace(".0", "") + "HP";
        CFontRenderer hpFont = Fonts.comfortaaBold_12;
        if (target.getDisplayName() != null) {
            this.targetName = target.getDisplayName().getFormattedText().replace("  ", " ").replace("\u00a7l", "").replace("[]", "").replace("\u00a7k", "").replace("\u00a7m", "").replace("\u00a7n", "").replace("\u00a7o", "");
        }
        int stacksCount = 0;
        ItemStack offhand = target.getHeldItemOffhand();
        ItemStack boots = target.getItemStackFromSlot(EntityEquipmentSlot.FEET);
        ItemStack leggings = target.getItemStackFromSlot(EntityEquipmentSlot.LEGS);
        ItemStack body = target.getItemStackFromSlot(EntityEquipmentSlot.CHEST);
        ItemStack helm = target.getItemStackFromSlot(EntityEquipmentSlot.HEAD);
        ItemStack inHand = target.getHeldItemMainhand();
        ItemStack[] stuff = new ItemStack[]{offhand, inHand, boots, leggings, body, helm};
        ArrayList<Object> stacks = new ArrayList<Object>();
        int j = 0;
        ItemStack[] array = stuff;
        int length = stuff.length;
        for (j = 0; j < length; ++j) {
            ItemStack i = array[j];
            if (i == null) continue;
            i.getItem();
            stacks.add(i);
        }
        for (ItemStack itemStack : stacks) {
            if (itemStack.isEmpty()) continue;
            ++stacksCount;
        }
        float alphaPC = this.Scaleclamp();
        alphaPC *= alphaPC;
        CFontRenderer cFontRenderer = Fonts.comfortaaBold_12;
        float w = widthHud;
        float h = heightHud;
        float x = (float)((int)(xPosHud * 2.0f)) / 2.0f;
        float y = yPosHud;
        float texOrItemW = (float)stacksCount * 8.0f + 0.5f;
        float strW = cFontRenderer.getStringWidth(this.targetName) - 22;
        if (texOrItemW < strW) {
            texOrItemW = strW;
        }
        widthHud = MathUtils.clamp(h + 16.0f + (float)hpFont.getStringWidth(hpStr) + texOrItemW, 100.0f, 1000.0f);
        heightHud = 34.0f;
        int col1 = ColorUtils.swapAlpha(ClientColors.getColor1(0), 40.0f * alphaPC);
        int col2 = ColorUtils.swapAlpha(ClientColors.getColor2(-324), 60.0f * alphaPC);
        int col3 = ColorUtils.swapAlpha(ClientColors.getColor2(0), 60.0f * alphaPC);
        int col4 = ColorUtils.swapAlpha(ClientColors.getColor1(972), 60.0f * alphaPC);
        int colS1 = ColorUtils.swapAlpha(ClientColors.getColor1(0), 50.0f * alphaPC);
        int colS2 = ColorUtils.swapAlpha(ClientColors.getColor2(-324), 50.0f * alphaPC);
        int colS3 = ColorUtils.swapAlpha(ClientColors.getColor2(0), 50.0f * alphaPC);
        int colS4 = ColorUtils.swapAlpha(ClientColors.getColor1(972), 50.0f * alphaPC);
        float round = 6.0f;
        float shadow = 7.0f * alphaPC;
        float blur = 1.0f + 3.0f * alphaPC;
        if ((double)alphaPC < 0.1) {
            return;
        }
        GL11.glPushMatrix();
        this.hudScale(x, y, w, h);
        RenderUtils.drawRoundedFullGradientShadowFullGradientRoundedFullGradientRectWithBloomBool(x, y, x + w, y + h, round, 0.0f, col1, col2, col3, col4, false, true, false);
        RenderUtils.drawRoundedFullGradientShadowFullGradientRoundedFullGradientRectWithBloomBool(x, y, x + w, y + h, round, shadow, colS1, colS2, colS3, colS4, true, false, true);
        GL11.glPushMatrix();
        RenderUtils.customScaledObject2D(x, y, w, h, 1.0f / this.Scale());
        float aPC = alphaPC;
        GaussianBlur.drawBlur(blur, () -> RenderUtils.drawRoundedFullGradientShadowFullGradientRoundedFullGradientRectWithBloomBool(x + w * (1.0f - aPC) / 2.0f, y + h * (1.0f - aPC) / 2.0f, x + w - w * (1.0f - aPC) / 2.0f, y + h - h * (1.0f - aPC) / 2.0f, round * aPC, 0.0f, -1, -1, -1, -1, false, true, false));
        GL11.glPopMatrix();
        RenderUtils.drawInsideFullRoundedFullGradientShadowRectWithBloomBool(x, y, x + w, y + h, round - 2.0f, 2.0f, colS1, colS2, colS3, colS4, true);
        int headSize = 24;
        float headX = x + 5.0f;
        float headY = y + 5.0f;
        if (res == null) {
            RenderUtils.drawRect(headX, headY, headX + (float)headSize, headY + (float)headSize, ColorUtils.getColor(0, 0, 0, 90.0f * alphaPC));
            Fonts.comfortaaBold_18.drawString("?", x + (float)headSize / 2.0f + 2.5f, y + (float)headSize / 2.0f + 2.0f, ColorUtils.getColor(255, 255, 255, 255.0f * alphaPC));
        } else {
            GlStateManager.enableTexture2D();
            RenderUtils.glColor(ColorUtils.swapAlpha(ColorUtils.getOverallColorFrom(-1, ColorUtils.getColor(255, 190, 190), MathUtils.clamp((float)target.hurtTime - mc.getRenderPartialTicks(), 0.0f, 10.0f) / 10.0f), 255.0f * alphaPC));
            mc.getTextureManager().bindTexture(res);
            GL11.glTranslated(headX, headY, 0.0);
            Gui.drawScaledCustomSizeModalRect(0.0f, 0.0f, 8.0f, 8.0f, 8.0f, 8.0f, headSize, headSize, 64.0f, 64.0f);
            Gui.drawScaledCustomSizeModalRect(-2.0f, -2.0f, 39.0f, 8.0f, 10.0f, 8.0f, (float)headSize + 4.0f, (float)headSize + 4.0f, 64.0f, 64.0f);
            GL11.glTranslated(-headX, -headY, 0.0);
            GlStateManager.resetColor();
            if (target.getHealth() == 0.0f) {
                RenderUtils.drawRect(x + 3.0f, y + 10.0f, x + (float)headSize + 8.0f, y + h - 11.0f, ColorUtils.getColor(0, 0, 0, 195.0f * alphaPC));
                Fonts.mntsb_18.drawStringWithShadow("DEAD", x + 3.5f, y + (float)headSize / 2.0f + 2.0f, ColorUtils.getColor(255, 65, 65, 255.0f * alphaPC));
            }
        }
        float postHeadX = headX + (float)headSize + 4.0f;
        RenderUtils.drawTwoAlphedSideways(postHeadX, y + 4.5f, postHeadX + 2.0f, y + h - 4.5f, ColorUtils.getColor(0, 0, 0, 70.0f * alphaPC), 0, false);
        if (255.0f * alphaPC >= 33.0f) {
            float nameTextX = postHeadX + 7.0f + (w - (postHeadX - x) - 12.0f) / 2.0f - (float)cFontRenderer.getStringWidth(this.targetName) / 2.0f;
            cFontRenderer.drawStringWithShadow(this.targetName, nameTextX, y + 5.5f, ColorUtils.getColor(255, 255, 255, 255.0f * alphaPC));
        }
        float hpWidth = w - (postHeadX - x) - 12.0f;
        float hpX1 = postHeadX + 7.0f;
        float hpX2 = hpX1 + hpWidth * targetHealthPC;
        float hpX3 = hpX1 + hpWidth;
        int hpCol1 = ColorUtils.swapAlpha(ClientColors.getColor1(0), 255.0f * alphaPC);
        int hpCol2 = ColorUtils.swapAlpha(ClientColors.getColor2(-324), 255.0f * alphaPC);
        int hpBGCol = ColorUtils.swapAlpha(0, 65.0f * alphaPC);
        float hpRoundRect = 1.5f;
        float hpShadowRect = 1.0f;
        RenderUtils.drawRoundedFullGradientShadowFullGradientRoundedFullGradientRectWithBloomBool(hpX1 - 0.5f, y + h - 9.0f, hpX3 + 0.5f, y + h - 4.5f, hpRoundRect, hpShadowRect + 0.5f, hpBGCol, hpBGCol, hpBGCol, hpBGCol, false, true, true);
        StencilUtil.initStencilToWrite();
        RenderUtils.drawRect(hpX1 - 0.5f, y + h - 12.0f, hpX2 + 0.5f, y + h - 4.5f, -1);
        StencilUtil.readStencilBuffer(1);
        RenderUtils.drawRoundedFullGradientShadowFullGradientRoundedFullGradientRectWithBloomBool(hpX1, y + h - 8.5f, hpX3, y + h - 5.0f, hpRoundRect, hpShadowRect, hpCol1, hpCol2, hpCol2, hpCol1, true, true, true);
        StencilUtil.uninitStencilBuffer();
        if (255.0f * alphaPC >= 33.0f) {
            hpFont.drawString(hpStr, postHeadX + 6.0f, y + 16.5f, ColorUtils.getColor(255, 255, 255, 255.0f * alphaPC));
        }
        float postHpTextX = postHeadX + 7.5f + (float)hpFont.getStringWidth(hpStr);
        if (stacksCount > 0) {
            RenderUtils.drawVGradientRect(postHpTextX + 0.5f, y + 13.0f, postHpTextX + 1.0f, y + 22.0f - 4.0f, ColorUtils.getColor(255, 255, 255, 10.0f * alphaPC), ColorUtils.getColor(255, 255, 255, 255.0f * alphaPC));
            RenderUtils.drawVGradientRect(postHpTextX + 0.5f, y + 13.0f + 4.0f, postHpTextX + 1.0f, y + 22.0f, ColorUtils.getColor(255, 255, 255, 255.0f * alphaPC), ColorUtils.getColor(255, 255, 255, 10.0f * alphaPC));
        }
        RenderItem itemRender = mc.getRenderItem();
        RenderHelper.enableGUIStandardItemLighting();
        GlStateManager.enableDepth();
        float xn = postHpTextX - x - 5.5f;
        float yn = 13.0f;
        int itemsBgCol = ColorUtils.getColor(0, 0, 0, 40.0f * alphaPC);
        GlStateManager.enableDepth();
        GL11.glTranslated(x, y, 0.0);
        xn *= 2.0f;
        yn *= 2.0f;
        GL11.glScaled(0.5, 0.5, 0.5);
        for (ItemStack itemStack : stacks) {
            if (!itemStack.isEmpty()) {
                xn += 17.0f;
            }
            GL11.glTranslated(xn, yn, 0.0);
            GL11.glTranslated(8.0, 8.0, 0.0);
            GL11.glScaled(alphaPC, alphaPC, 1.0);
            GL11.glTranslated(-8.0, -8.0, 0.0);
            itemRender.zLevel = 200.0f;
            itemRender.renderItemAndEffectIntoGUI(itemStack, 0, 0);
            if (255.0f * alphaPC >= 33.0f) {
                itemRender.renderItemOverlayIntoGUIWithTextColor(Fonts.minecraftia_16, itemStack, 0, 0, itemStack.getCount(), ColorUtils.getColor(255, 255, 255, 255.0f * alphaPC));
            }
            RenderUtils.drawItemWarnIfLowDur(itemStack, 0.0f, 0.0f, alphaPC, 1.0f);
            itemRender.zLevel = 0.0f;
            GL11.glTranslated(8.0, 8.0, 0.0);
            GL11.glScaled(1.0f / alphaPC, 1.0f / alphaPC, 1.0);
            GL11.glTranslated(-8.0, -8.0, 0.0);
            GL11.glTranslated(-xn, -yn, 0.0);
        }
        GL11.glScaled(2.0, 2.0, 2.0);
        xn /= 2.0f;
        yn /= 2.0f;
        GL11.glTranslated(-x, -y, 0.0);
        RenderHelper.disableStandardItemLighting();
        GL11.glPopMatrix();
    }

    public static EntityLivingBase getTarget() {
        EntityLivingBase base;
        boolean pre = TargetHUD.get.PreRangedTarget.bValue;
        if ((pre ? HitAura.TARGET_ROTS : HitAura.TARGET) != null) {
            return pre ? HitAura.TARGET_ROTS : HitAura.TARGET;
        }
        if (TargetHUD.get.RaycastTarget.bValue && (base = MathUtils.getPointedEntity(new Vector2f(Minecraft.player.rotationYaw, Minecraft.player.rotationPitch), 60.0, 1.0f, false)) != null && base != Minecraft.player && (FreeCam.fakePlayer == null || base != FreeCam.fakePlayer) && base.isEntityAlive() && !Client.friendManager.isFriend(base.getName())) {
            return base;
        }
        if (BowAimbot.target != null) {
            return BowAimbot.target;
        }
        if (CrystalField.getTargets() != null && CrystalField.getTargets().size() != 0) {
            return CrystalField.getTargets().get(0);
        }
        return TargetHUD.mc.currentScreen instanceof GuiChat ? Minecraft.player : null;
    }

    @EventTarget
    public void onRender2D(EventRender2D event) {
        float curScale;
        EntityLivingBase target = TargetHUD.getTarget();
        this.updatePosition(event);
        float f = curScale = target == null ? 0.0f : 1.0f;
        if (curScale == 0.0f) {
            if (this.Scale.to == 1.0f) {
                float f2 = this.Scale.to = (double)this.Scale.getAnim() > 0.995 ? 1.125f : 0.0f;
                if (MathUtils.getDifferenceOf(this.Scale.getAnim(), 1.0f) < 0.03) {
                    this.Scale.setAnim(1.0f);
                }
            } else {
                float f3 = this.Scale();
                float f4 = curTarget != null && curTarget != Minecraft.player && !curTarget.isEntityAlive() ? 1.12499f : 1.075f;
                if (f3 >= f4) {
                    this.Scale.to = 0.0f;
                }
            }
        } else {
            if (this.Scale() < 0.7f) {
                this.Scale.setAnim(0.7f);
            }
            if (this.Scale() >= 1.075f) {
                this.Scale.to = 1.0f;
            } else if (this.Scale.to == 0.0f) {
                this.Scale.to = 1.125f;
            }
        }
        if (target != null && (target != Minecraft.player || TargetHUD.mc.currentScreen instanceof GuiChat)) {
            curTarget = target;
        }
        if ((double)this.Scale() < 0.002) {
            return;
        }
        if (soundTarget != target && this.TargettingSFX.bValue) {
            if (target != null && target != Minecraft.player) {
                ClientTune.get.playTargetSelect();
            }
            soundTarget = target;
        }
        if (curTarget != null) {
            if (this.targetHurt != TargetHUD.curTarget.hurtTime) {
                this.targetHurt = TargetHUD.curTarget.hurtTime;
            }
            if (skin != null && OldSkin != skin && target != null) {
                OldSkin = skin;
            }
        }
        if (Minecraft.player != null && TargetHUD.getTarget() == Minecraft.player && TargetHUD.mc.currentScreen instanceof GuiChat && Minecraft.player.connection.getPlayerInfo(Minecraft.player.getName()) != null) {
            OldSkin = WorldRender.get.updatedResourceSkin(Minecraft.player.connection.getPlayerInfo(Minecraft.player.getName()).getLocationSkin(), Minecraft.player);
        }
        if (curTarget == null) {
            curTarget = Minecraft.player;
        }
        this.targetName = curTarget.getName().replace("  ", " ");
        switch (this.Mode.currentMode) {
            case "Light": {
                this.renderLight(curTarget);
                break;
            }
            case "WetWorn": {
                this.renderWetWorn(curTarget);
                break;
            }
            case "Neomoin": {
                this.renderNeomoin(curTarget);
                break;
            }
            case "Modern": {
                this.renderModern(curTarget);
                break;
            }
            case "Bushy": {
                this.renderBushy(curTarget);
                break;
            }
            case "Subtle": {
                this.renderSubtle(curTarget);
                break;
            }
        }
    }

    public void hudScale(float x, float y, float width, float height) {
        if (MathUtils.getDifferenceOf(this.Scale(), 1.0f) > 0.01) {
            RenderUtils.customScaledObject2D(x, y, width, height, this.Scale());
        }
    }

    public void hudScalePro(float x, float y, float width, float height) {
        float scale = MathUtils.clamp(this.Scale(), 0.0f, 2.0f) / 4.0f + 0.75f;
        RenderUtils.customScaledObject2DPro(x, y, width, height, scale, scale);
    }

    private final float getArmorPercent01(EntityLivingBase entity) {
        float armPC = 0.0f;
        for (ItemStack armorElement : entity.getArmorInventoryList()) {
            if (armorElement.isEmpty() || armorElement == null) continue;
            float maxDurable = armorElement.getMaxDamage();
            float armorHealth = maxDurable - (float)armorElement.getItemDamage();
            float durrablePC = armorHealth / maxDurable;
            armPC += durrablePC;
        }
        return MathUtils.clamp(armPC / 4.0f, 0.0f, 1.0f);
    }

    static {
        curTarget = null;
        soundTarget = null;
        particles = new ArrayList();
        skin = null;
        OldSkin = null;
    }

    private class particle {
        long time = System.currentTimeMillis();
        float x;
        float y;
        AnimationUtils xs = new AnimationUtils(0.0f, 0.0f, 0.0075f);
        AnimationUtils ys = new AnimationUtils(0.0f, 0.0f, 0.0075f);
        float motionX;
        float motionY;

        public particle(float x, float y) {
            this.x = x;
            this.y = y;
            float rand = 0.003f * (Math.max((float)Minecraft.getDebugFPS(), 5.0f) * 15.0f);
            this.motionX = (float)MathUtils.getRandomInRange(-1.0, 1.0);
            this.motionY = (float)MathUtils.getRandomInRange(-1.0, 1.0);
            this.xs.setAnim(x);
            this.ys.setAnim(y);
            this.xs.to = this.motionX * 100.0f + x;
            this.ys.to = this.motionY * 100.0f + y;
        }

        public long getTime() {
            return System.currentTimeMillis() - this.time;
        }

        public void update(int color) {
            float rand = 0.0035f * (Math.max((float)Minecraft.getDebugFPS(), 5.0f) * 15.0f);
            this.motionX = this.motionX = (float)((double)this.motionX / 1.02);
            this.x += this.motionX * rand;
            this.motionY = this.motionY = (float)((double)this.motionY / 1.02);
            this.y += this.motionY * rand;
            float ss = 3.0f * ((float)ColorUtils.getAlphaFromColor(color) / 255.0f);
            float x = this.xs.getAnim();
            float y = this.ys.getAnim();
            RenderUtils.drawRoundedFullGradientShadowFullGradientRoundedFullGradientRectWithBloomBool(x - ss, y - ss, x + ss, y + ss, ss, 1.0f, color, color, color, color, false, true, true);
        }
    }
}

