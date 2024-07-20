/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.module.modules;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import ru.govno.client.Client;
import ru.govno.client.module.Module;
import ru.govno.client.module.modules.ClientColors;
import ru.govno.client.module.modules.Crosshair;
import ru.govno.client.module.modules.HitAura;
import ru.govno.client.module.settings.Settings;
import ru.govno.client.utils.Math.MathUtils;
import ru.govno.client.utils.Render.AnimationUtils;
import ru.govno.client.utils.Render.ColorUtils;
import ru.govno.client.utils.Render.RenderUtils;
import ru.govno.client.utils.Render.StencilUtil;

public class Triangular
extends Module {
    private static final ResourceLocation TRIANGLE_TEXTURE = new ResourceLocation("vegaline/modules/triangular/triangle.png");
    private final Settings Players;
    private final Settings PlayerColorMode;
    private final Settings PickPlayerColor;
    private final Settings PickPlayerColor2;
    private final Settings Friends;
    private final Settings FriendColorMode;
    private final Settings PickFriendColor;
    private final Settings PickFriendColor2;
    private final Settings Mobs;
    private final Settings MobColorMode;
    private final Settings PickMobColor;
    private final Settings PickMobColor2;
    private Settings HealthWobble = new Settings("HealthWobble", true, (Module)this, () -> new Settings((String)"Players", (boolean)true, (Module)this).bValue || new Settings((String)"Friends", (boolean)true, (Module)this).bValue || new Settings((String)"Mobs", (boolean)true, (Module)this).bValue);
    private final Settings TriangleScale;
    private final Settings TriangleRange;
    AnimationUtils yawSmooth = new AnimationUtils(0.0f, 0.0f, 0.125f);
    private final Tessellator tessellator = Tessellator.getInstance();
    private final BufferBuilder buffer = this.tessellator.getBuffer();

    public Triangular() {
        super("Triangular", 0, Module.Category.RENDER);
        mc.getTextureManager().bindTexture(TRIANGLE_TEXTURE);
        this.Players = new Settings("Players", true, (Module)this);
        this.settings.add(this.Players);
        this.PlayerColorMode = new Settings("PlayerColorMode", "PickColor", this, new String[]{"PickColor", "ClientColor"}, () -> this.Players.bValue);
        this.settings.add(this.PlayerColorMode);
        this.PickPlayerColor = new Settings("PickPlayerColor", ColorUtils.getColor(255, 10, 70), (Module)this, () -> this.Players.bValue && this.PlayerColorMode.currentMode.equalsIgnoreCase("PickColor"));
        this.settings.add(this.PickPlayerColor);
        this.PickPlayerColor2 = new Settings("PickPlayerColor2", ColorUtils.getColor(255, 255, 255), (Module)this, () -> this.Players.bValue && this.PlayerColorMode.currentMode.equalsIgnoreCase("PickColor") && this.HealthWobble.bValue);
        this.settings.add(this.PickPlayerColor2);
        this.Friends = new Settings("Friends", true, (Module)this);
        this.settings.add(this.Friends);
        this.FriendColorMode = new Settings("FriendColorMode", "PickColor", this, new String[]{"PickColor", "ClientColor"}, () -> this.Friends.bValue);
        this.settings.add(this.FriendColorMode);
        this.PickFriendColor = new Settings("PickFriendColor", ColorUtils.getColor(70, 255, 70), (Module)this, () -> this.Friends.bValue && this.FriendColorMode.currentMode.equalsIgnoreCase("PickColor"));
        this.settings.add(this.PickFriendColor);
        this.PickFriendColor2 = new Settings("PickFriendColor2", ColorUtils.getColor(255, 255, 255), (Module)this, () -> this.Friends.bValue && this.FriendColorMode.currentMode.equalsIgnoreCase("PickColor") && this.HealthWobble.bValue);
        this.settings.add(this.PickFriendColor2);
        this.Mobs = new Settings("Mobs", true, (Module)this);
        this.settings.add(this.Mobs);
        this.MobColorMode = new Settings("MobColorMode", "PickColor", this, new String[]{"PickColor", "ClientColor"}, () -> this.Mobs.bValue);
        this.settings.add(this.MobColorMode);
        this.PickMobColor = new Settings("PickMobColor", ColorUtils.getColor(20, 255, 255), (Module)this, () -> this.Mobs.bValue && this.MobColorMode.currentMode.equalsIgnoreCase("PickColor"));
        this.settings.add(this.PickMobColor);
        this.PickMobColor2 = new Settings("PickMobColor2", ColorUtils.getColor(255, 255, 255), (Module)this, () -> this.Mobs.bValue && this.MobColorMode.currentMode.equalsIgnoreCase("PickColor") && this.HealthWobble.bValue);
        this.settings.add(this.PickMobColor2);
        this.HealthWobble = new Settings("HealthWobble", true, (Module)this, () -> this.Players.bValue || this.Friends.bValue || this.Mobs.bValue);
        this.settings.add(this.HealthWobble);
        this.TriangleScale = new Settings("TriangleScale", 0.6f, 1.5f, 0.5f, this, () -> this.Players.bValue || this.Friends.bValue || this.Mobs.bValue);
        this.settings.add(this.TriangleScale);
        this.TriangleRange = new Settings("TriangleRange", 0.75f, 2.0f, 0.25f, this, () -> this.Players.bValue || this.Friends.bValue || this.Mobs.bValue);
        this.settings.add(this.TriangleRange);
    }

    private float getAlphaPC() {
        this.stateAnim.to = this.isActived() && Triangular.mc.gameSettings.thirdPersonView == 0 ? 1.0f : 0.0f;
        float animAlphaPC = this.stateAnim.getAnim();
        return animAlphaPC < 0.03f ? 0.0f : (animAlphaPC > 0.995f ? 1.0f : animAlphaPC);
    }

    @Override
    public void alwaysRender2D(ScaledResolution sr) {
        boolean[] PFM;
        double d;
        float alphaPC = this.getAlphaPC();
        if (alphaPC == 0.0f) {
            return;
        }
        double yawDiff = MathUtils.getDifferenceOf(this.yawSmooth.getAnim(), Minecraft.player.rotationYaw);
        if (d >= 0.1) {
            this.yawSmooth.to = Minecraft.player.rotationYaw;
            if (yawDiff >= 180.0) {
                this.yawSmooth.setAnim(Minecraft.player.rotationYaw);
            }
        }
        if (!((PFM = new boolean[]{this.Players.bValue, this.Friends.bValue, this.Mobs.bValue})[0] || PFM[1] || PFM[2])) {
            if (this.isActived()) {
                this.toggle(false);
            }
            return;
        }
        List<EntityLivingBase> collect = this.collects(PFM[0], PFM[1], PFM[2]);
        if (collect.isEmpty()) {
            return;
        }
        float pTicks = mc.getRenderPartialTicks();
        float containerAnim = GuiContainer.inter.getAnim();
        float scalePix = 11.0f * this.TriangleScale.fValue;
        float minRadius = 10.0f + 20.0f * this.TriangleRange.fValue;
        boolean healthWobble = this.HealthWobble.bValue;
        mc.getTextureManager().bindTexture(TRIANGLE_TEXTURE);
        GL11.glEnable(3042);
        GL11.glTexParameteri(3553, 10240, 9729);
        GL11.glBlendFunc(770, 1);
        GlStateManager.disableAlpha();
        collect.forEach(forDraw -> this.drawArrowToEntity(sr, pTicks, (EntityLivingBase)forDraw, this.yawSmooth.getAnim(), Crosshair.get.crossPosMotions, alphaPC, containerAnim, healthWobble, scalePix, minRadius));
        GlStateManager.enableAlpha();
        GL11.glBlendFunc(770, 771);
    }

    private void drawArrowToEntity(ScaledResolution sr, float pTicks, EntityLivingBase livingIn, float yaw, float[] extXY, float alphaPC, float containerAnim, boolean healthWobble, float scalePix, float minRadius) {
        double rotX;
        double sin;
        yaw = MathUtils.wrapAngleTo180_float(yaw % 360.0f);
        double xMe = Minecraft.player.lastTickPosX + (Minecraft.player.posX - Minecraft.player.lastTickPosX) * (double)pTicks;
        double zMe = Minecraft.player.lastTickPosZ + (Minecraft.player.posZ - Minecraft.player.lastTickPosZ) * (double)pTicks;
        double xPos = livingIn.lastTickPosX + (livingIn.posX - livingIn.lastTickPosX) * (double)pTicks;
        double zPos = livingIn.lastTickPosZ + (livingIn.posZ - livingIn.lastTickPosZ) * (double)pTicks;
        double cos = Math.cos((double)yaw * (Math.PI / 180));
        double rotY = -(zPos - zMe) * cos - (xPos - xMe) * (sin = -Math.sin((double)yaw * (Math.PI / 180)));
        double angle = Math.atan2(rotY, rotX = -(xPos - xMe) * cos + (zPos - zMe) * sin) * 180.0 / Math.PI;
        float angle45PC = (float)(Math.abs(angle) % 90.0 / 90.0);
        angle45PC = ((double)angle45PC > 0.5 ? 1.0f - angle45PC : angle45PC) * 2.0f;
        float radius = minRadius + (livingIn == HitAura.TARGET_ROTS ? minRadius / 3.0f : 0.0f) + (75.0f + angle45PC * 37.5f + (30.0f - minRadius)) * (Triangular.mc.currentScreen instanceof GuiContainer ? containerAnim : 0.0f);
        float texExtend = scalePix * MathUtils.clamp(((float)livingIn.ticksExisted + pTicks) / 14.0f, 0.0f, 1.0f) * (livingIn == HitAura.TARGET_ROTS ? 1.25f : 1.0f);
        double x = (double)radius * Math.cos(Math.toRadians(angle));
        double y = (double)radius * Math.sin(Math.toRadians(angle));
        GL11.glPushMatrix();
        GL11.glTranslated((double)sr.getScaledWidth() / 2.0 + x + (double)extXY[0], (double)sr.getScaledHeight() / 2.0 + y + (double)extXY[1], 0.0);
        GL11.glRotated(angle, 0.0, 0.0, 1.0);
        if (healthWobble && livingIn.getHealth() == livingIn.getMaxHealth()) {
            healthWobble = false;
        }
        float hpPC = MathUtils.clamp((livingIn.getSmoothHealth() + 0.01f + livingIn.getAbsorptionAmount() / 4.0f) / (livingIn.getMaxHealth() + livingIn.getAbsorptionAmount() / 4.0f), 0.0f, 1.0f);
        if (healthWobble) {
            StencilUtil.initStencilToWrite();
            float extMiddle = texExtend * 0.9f;
            RenderUtils.drawRect(-texExtend, -texExtend, -extMiddle + extMiddle * (hpPC + 0.1f) * 2.0f, texExtend, -1);
            StencilUtil.readStencilBuffer(1);
        }
        this.arrow(this.getEntityColor(livingIn, (int)(angle * 3.0), alphaPC, false), texExtend);
        if (healthWobble) {
            StencilUtil.readStencilBuffer(0);
            this.arrow(this.getEntityColor(livingIn, (int)(angle * 3.0), alphaPC, true), texExtend);
            StencilUtil.uninitStencilBuffer();
        }
        GL11.glPopMatrix();
    }

    private int[] getEntityColor(EntityLivingBase livingIn, int index, float alphaPC, boolean healthWobbleMode) {
        EntityOtherPlayerMP player;
        String playerColorMode = this.PlayerColorMode.currentMode;
        String friendColorMode = this.FriendColorMode.currentMode;
        String mobColorMode = this.MobColorMode.currentMode;
        boolean playerColorIsPicker = playerColorMode.equalsIgnoreCase("PickColor");
        boolean friendColorIsPicker = friendColorMode.equalsIgnoreCase("PickColor");
        boolean mobColorIsPicker = mobColorMode.equalsIgnoreCase("PickColor");
        int playerPicker = this.PickPlayerColor.color;
        int friendPicker = this.PickFriendColor.color;
        int mobPicker = this.PickMobColor.color;
        int playerPicker2 = this.PickPlayerColor2.color;
        int friendPicker2 = this.PickFriendColor2.color;
        int mobPicker2 = this.PickMobColor2.color;
        int clientColor = ClientColors.getColor2(index);
        int clientColor2 = ClientColors.getColor1(index);
        int color = -1;
        int color2 = -1;
        if (livingIn instanceof EntityOtherPlayerMP) {
            player = (EntityOtherPlayerMP)livingIn;
            color = Client.friendManager.isFriend(player.getName()) ? (friendColorIsPicker ? (healthWobbleMode ? friendPicker2 : friendPicker) : clientColor) : (playerColorIsPicker ? (healthWobbleMode ? playerPicker2 : playerPicker) : clientColor);
        } else if (livingIn instanceof EntityMob || livingIn instanceof EntityAnimal) {
            color = mobColorIsPicker ? (healthWobbleMode ? mobPicker2 : mobPicker) : clientColor;
        }
        color = ColorUtils.swapAlpha(color, (float)ColorUtils.getAlphaFromColor(color) * alphaPC);
        if (livingIn instanceof EntityOtherPlayerMP) {
            player = (EntityOtherPlayerMP)livingIn;
            color2 = Client.friendManager.isFriend(player.getName()) ? (friendColorIsPicker ? (healthWobbleMode ? friendPicker2 : friendPicker) : clientColor2) : (playerColorIsPicker ? (healthWobbleMode ? playerPicker2 : playerPicker) : clientColor2);
        } else if (livingIn instanceof EntityMob || livingIn instanceof EntityAnimal) {
            color2 = mobColorIsPicker ? (healthWobbleMode ? mobPicker2 : mobPicker) : clientColor2;
        }
        color2 = ColorUtils.swapAlpha(color2, (float)ColorUtils.getAlphaFromColor(color2) * alphaPC);
        return new int[]{color, color2};
    }

    private final List<EntityLivingBase> collects(boolean players, boolean friends, boolean mobs) {
        CopyOnWriteArrayList<EntityLivingBase> entities = new CopyOnWriteArrayList<EntityLivingBase>();
        if (Triangular.mc.world != null) {
            Triangular.mc.world.getLoadedEntityList().forEach(entity -> {
                if (entity == null) return;
                if (!(entity instanceof EntityLivingBase)) return;
                EntityLivingBase livingBase = (EntityLivingBase)entity;
                if (livingBase.getHealth() == 0.0f) return;
                if (livingBase.isDead) return;
                if (livingBase instanceof EntityOtherPlayerMP) {
                    EntityOtherPlayerMP player = (EntityOtherPlayerMP)livingBase;
                    if (players || friends) {
                        if (!(friends && players || friends && Client.friendManager.isFriend(player.getName()))) {
                            if (!players) return;
                            if (Client.friendManager.isFriend(player.getName())) return;
                        }
                        entities.add(player);
                        return;
                    }
                }
                if (!mobs) return;
                if (!(livingBase instanceof EntityMob)) {
                    if (!(livingBase instanceof EntityAnimal)) return;
                }
                entities.add(livingBase);
            });
        }
        return entities;
    }

    private void arrow(int[] color, float scale) {
        this.buffer.begin(9, DefaultVertexFormats.POSITION_TEX_COLOR);
        this.buffer.pos(-scale, -scale, 0.0).tex(0.0, 0.0).color(color[0]).endVertex();
        this.buffer.pos(-scale, scale, 0.0).tex(0.0, 1.0).color(color[0]).endVertex();
        this.buffer.pos(scale, scale, 0.0).tex(1.0, 1.0).color(color[1]).endVertex();
        this.buffer.pos(scale, -scale, 0.0).tex(1.0, 0.0).color(color[1]).endVertex();
        this.tessellator.draw();
    }
}

