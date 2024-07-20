/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.module.modules;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector4d;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSkull;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec2f;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextFormatting;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import ru.govno.client.Client;
import ru.govno.client.module.Module;
import ru.govno.client.module.modules.ClientColors;
import ru.govno.client.module.settings.Settings;
import ru.govno.client.newfont.CFontRenderer;
import ru.govno.client.newfont.Fonts;
import ru.govno.client.utils.Combat.RotationUtil;
import ru.govno.client.utils.Math.MathUtils;
import ru.govno.client.utils.Math.ReplaceStrUtils;
import ru.govno.client.utils.Render.ColorUtils;
import ru.govno.client.utils.Render.RenderUtils;

public class NameTags
extends Module {
    public static NameTags get;
    public Settings Items;
    public Settings Armor;
    public Settings Enchants;
    public Settings Shadow;
    public Settings Potions;
    public Settings HealthLine;
    public Settings ShowSelf;
    public Settings OutfovMarkers;
    private final IntBuffer viewport = GLAllocation.createDirectIntBuffer(16);
    private final FloatBuffer modelview = GLAllocation.createDirectFloatBuffer(16);
    private final FloatBuffer projection = GLAllocation.createDirectFloatBuffer(16);
    private final FloatBuffer vector = GLAllocation.createDirectFloatBuffer(4);
    private final Tessellator tesellator = Tessellator.getInstance();
    private final BufferBuilder buffer = this.tesellator.getBuffer();
    protected static final ResourceLocation TAG_MARK_BASE;
    protected static final ResourceLocation TAG_MARK_OVERLAY;

    public NameTags() {
        super("NameTags", 0, Module.Category.RENDER);
        get = this;
        this.Items = new Settings("Items", true, (Module)this);
        this.settings.add(this.Items);
        this.Armor = new Settings("Armor", false, (Module)this, () -> this.Items.bValue);
        this.settings.add(this.Armor);
        this.Enchants = new Settings("Enchants", false, (Module)this, () -> this.Items.bValue);
        this.settings.add(this.Enchants);
        this.Shadow = new Settings("Shadow", true, (Module)this);
        this.settings.add(this.Shadow);
        this.Potions = new Settings("Potions", false, (Module)this);
        this.settings.add(this.Potions);
        this.HealthLine = new Settings("HealthLine", true, (Module)this);
        this.settings.add(this.HealthLine);
        this.ShowSelf = new Settings("ShowSelf", false, (Module)this);
        this.settings.add(this.ShowSelf);
        this.OutfovMarkers = new Settings("OutfovMarkers", true, (Module)this);
        this.settings.add(this.OutfovMarkers);
    }

    private Vector3d project2D(int scaleFactor, float x, float y, float z) {
        GL11.glGetFloat(2982, this.modelview);
        GL11.glGetFloat(2983, this.projection);
        GL11.glGetInteger(2978, this.viewport);
        return GLU.gluProject(x, y, z, this.modelview, this.projection, this.viewport, this.vector) ? new Vector3d(this.vector.get(0) / (float)scaleFactor, ((float)Display.getHeight() - this.vector.get(1)) / (float)scaleFactor, this.vector.get(2)) : null;
    }

    private Vector3d project2D(int scaleFactor, double x, double y, double z) {
        return this.project2D(scaleFactor, (float)x, (float)y, (float)z);
    }

    static String getName(EntityLivingBase entity) {
        Object gm = "";
        Object ping = "";
        Object name = entity.getDisplayName().getUnformattedText();
        name = ReplaceStrUtils.fixString((String)name);
        boolean isNpc = true;
        if (NameTags.mc.pointedEntity != null && entity == NameTags.mc.pointedEntity) {
            try {
                if (Minecraft.player.connection.getPlayerInfo(entity.getName()) != null) {
                    isNpc = false;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (!isNpc) {
                gm = Minecraft.player.connection.getPlayerInfo(entity.getName()).getGameType() + TextFormatting.GRAY + " | " + TextFormatting.RESET;
                ping = TextFormatting.GRAY + " | " + TextFormatting.RESET + Minecraft.player.connection.getPlayerInfo(entity.getName()).getResponseTime() + "ms" + TextFormatting.RESET;
            }
        }
        name = Client.friendManager.isFriend(entity.getName()) ? "\u00a7a\u0414\u0440\u0443\u0433\u00a77 | \u00a7r" + (String)gm + (String)name : (String)gm + (String)name;
        String absTd = ((String)name).contains(" ") ? "" : " ";
        name = (String)name + "\u00a78" + absTd + "|\u00a7r " + String.format("%.1f", Float.valueOf(entity.getSmoothHealth())).replace(".0", "");
        if (entity.getAbsorptionAmount() > 0.0f) {
            name = (String)name + "\u00a7e+" + (int)entity.getAbsorptionAmount();
        }
        name = (String)name + "\u00a77\u0425\u041f" + (String)ping;
        return name;
    }

    @Override
    public void onToggled(boolean actived) {
        this.stateAnim.setAnim(actived ? 0.0f : 1.0f);
        this.stateAnim.to = actived ? 1.0f : 0.0f;
        super.onToggled(actived);
    }

    @Override
    public void alwaysRender2D(ScaledResolution sr) {
        if (this.actived && (double)this.stateAnim.getAnim() < 0.01) {
            this.stateAnim.to = 1.0f;
        }
        if ((double)this.stateAnim.getAnim() < 0.01) {
            return;
        }
        this.drawNameTags(sr, this.stateAnim.getAnim());
    }

    private List<EntityPlayer> getShowedLivings(boolean players, boolean self) {
        return NameTags.mc.world.getLoadedEntityList().stream().map(Entity::getPlayerOf).filter(Objects::nonNull).filter(entity -> {
            if (players && entity instanceof EntityOtherPlayerMP) {
                EntityOtherPlayerMP otherPMP = (EntityOtherPlayerMP)entity;
                return true;
            } else {
                if (!self) return false;
                if (!(entity instanceof EntityPlayerSP)) return false;
                if (NameTags.mc.gameSettings.thirdPersonView == 0) return false;
            }
            return true;
        }).filter(Objects::nonNull).toList();
    }

    private Vec3d getEntityPosVec(Entity entity, float pTicks) {
        double x = RenderUtils.interpolate(entity.posX, entity.prevPosX, pTicks);
        double y = RenderUtils.interpolate(entity.posY, entity.prevPosY, pTicks);
        double z = RenderUtils.interpolate(entity.posZ, entity.prevPosZ, pTicks);
        return new Vec3d(x, y, z);
    }

    private Vec2f getNameTagPosVec(EntityLivingBase base, float pTicks, int scaleFactor, ScaledResolution sr, RenderManager renderMng) {
        Vec2f vec2f = new Vec2f(-1617.0f, -1617.0f);
        Vec3d posEnt = this.getEntityPosVec(base, pTicks);
        AxisAlignedBB aabb = new AxisAlignedBB(posEnt.xCoord, posEnt.yCoord + ((double)(base.height / (float)(base.isChild() ? 2 : 1)) + (base.isSneaking() ? 0.3 : 0.27499999999999997)), posEnt.zCoord);
        Vector3d[] vectors = new Vector3d[]{new Vector3d(aabb.minX, aabb.minY, aabb.minZ), new Vector3d(aabb.minX, aabb.maxY, aabb.minZ), new Vector3d(aabb.maxX, aabb.minY, aabb.minZ), new Vector3d(aabb.maxX, aabb.maxY, aabb.minZ), new Vector3d(aabb.minX, aabb.minY, aabb.maxZ), new Vector3d(aabb.minX, aabb.maxY, aabb.maxZ), new Vector3d(aabb.maxX, aabb.minY, aabb.maxZ), new Vector3d(aabb.maxX, aabb.maxY, aabb.maxZ)};
        NameTags.mc.entityRenderer.setupCameraTransform(pTicks, 1);
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
        NameTags.mc.entityRenderer.setupOverlayRendering();
        if (position != null) {
            double posX = position.x;
            double posY = position.y;
            double endPosX = position.z;
            double endPosY = position.w;
            vec2f.x = (float)(posX + (endPosX - posX) / 2.0);
            vec2f.y = (float)posY;
        }
        return vec2f;
    }

    private boolean nameTagVecSetSuccess(Vec2f vec2f) {
        return ((int)vec2f.x != -1617 || (int)vec2f.y != -1617) && vec2f.x > 0.0f && vec2f.x < (float)NameTags.mc.displayWidth / 2.0f && vec2f.y > 0.0f && vec2f.y < (float)NameTags.mc.displayHeight / 2.0f + 40.0f;
    }

    private void drawNameTags(ScaledResolution sr, float alphaPC) {
        float pTicks = mc.getRenderPartialTicks();
        int scaleFactor = ScaledResolution.getScaleFactor();
        RenderManager renderManager = mc.getRenderManager();
        RenderItem itemRender = mc.getRenderItem();
        int markOffset = 10;
        float partialTicks = mc.getRenderPartialTicks();
        float selfYaw = Minecraft.player.rotationYaw;
        this.getShowedLivings(true, this.ShowSelf.bValue).forEach(player -> {
            Vec2f pos = this.getNameTagPosVec((EntityLivingBase)player, pTicks, scaleFactor, sr, renderManager);
            if (this.nameTagVecSetSuccess(pos) && RenderUtils.isInView(player)) {
                this.drawEntity2DTag((EntityLivingBase)player, alphaPC, this.Items.bValue, this.Armor.bValue, this.Enchants.bValue, this.Potions.bValue, this.Shadow.bValue, this.HealthLine.bValue, pos.x, pos.y, itemRender);
            } else if (this.OutfovMarkers.bValue) {
                this.drawEntity2DMark((EntityLivingBase)player, 10, sr, selfYaw, partialTicks, renderManager);
            }
        });
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
    }

    private void drawEntity2DMark(EntityLivingBase base, int markOffset, ScaledResolution sr, float selfYaw, float partialTicks, RenderManager rmngr) {
        Vec3d posEnt = new Vec3d(base.lastTickPosX + (base.posX - base.lastTickPosX) * (double)partialTicks, base.lastTickPosY + (base.posY - base.lastTickPosY) * (double)partialTicks, base.lastTickPosZ + (base.posZ - base.lastTickPosZ) * (double)partialTicks);
        Vec3d selfVec = new Vec3d(rmngr.getRenderPosX(), rmngr.getRenderPosY(), rmngr.getRenderPosZ());
        double dxPoses = posEnt.xCoord - selfVec.xCoord;
        double dyPoses = posEnt.yCoord - selfVec.yCoord;
        double dzPoses = posEnt.zCoord - selfVec.zCoord;
        float radian = RotationUtil.getFacePosRemote(selfVec, posEnt)[0] - 90.0f - selfYaw;
        float[] xyOfRads = this.getPointOfRadian(0.0f, 0.0f, sr.getScaledWidth(), sr.getScaledHeight(), radian, markOffset);
        float x = xyOfRads[0];
        float y = xyOfRads[1];
        float markStature = 6.0f;
        float fadeSpeed = 0.3f;
        int markCol = ColorUtils.getColor(255, 255, 255, 125);
        int markC1 = ColorUtils.fadeColor(ColorUtils.getColor(255, 0, 0), -1, fadeSpeed, (int)(0.0f / fadeSpeed));
        int markC2 = ColorUtils.fadeColor(ColorUtils.getColor(255, 0, 0), -1, fadeSpeed, (int)(90.0f / fadeSpeed));
        int markC3 = ColorUtils.fadeColor(ColorUtils.getColor(255, 0, 0), -1, fadeSpeed, (int)(180.0f / fadeSpeed));
        int markC4 = ColorUtils.fadeColor(ColorUtils.getColor(255, 0, 0), -1, fadeSpeed, (int)(240.0f / fadeSpeed));
        if (Client.friendManager.isFriend(base.getName())) {
            markC4 = markC3 = (markC2 = (markC1 = ColorUtils.getColor(40, 255, 60)));
        }
        int textColor = ColorUtils.getOverallColorFrom(ColorUtils.getOverallColorFrom(ColorUtils.getOverallColorFrom(markC1, markC2), ColorUtils.getOverallColorFrom(markC3, markC4)), -1, 0.75f);
        GL11.glPushMatrix();
        float rotAngle = radian;
        rotAngle = rotAngle % 90.0f / 90.0f > 0.5f ? 1.0f - rotAngle % 90.0f / 90.0f : rotAngle % 90.0f / 90.0f;
        rotAngle *= 2.0f;
        rotAngle *= rotAngle;
        rotAngle *= 45.0f;
        GL11.glEnable(3042);
        GL11.glDisable(3008);
        GL11.glShadeModel(7425);
        GL11.glBlendFunc(770, 32772);
        mc.getTextureManager().bindTexture(TAG_MARK_BASE);
        this.buffer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        this.buffer.pos(x - 3.0f, y - 3.0f).tex(0.0, 0.0).color(markC1).endVertex();
        this.buffer.pos(x - 3.0f, y + 3.0f).tex(0.0, 1.0).color(markC2).endVertex();
        this.buffer.pos(x + 3.0f, y + 3.0f).tex(1.0, 1.0).color(markC3).endVertex();
        this.buffer.pos(x + 3.0f, y - 3.0f).tex(1.0, 0.0).color(markC4).endVertex();
        this.tesellator.draw();
        float animTimeMax = 500.0f;
        float animIndex = rotAngle;
        float timePC = (float)((System.currentTimeMillis() + (long)((int)(animIndex * (animTimeMax / 90.0f)))) % (long)((int)animTimeMax)) / animTimeMax;
        float animAlphaPC = (timePC > 0.5f ? 1.0f - timePC : timePC) * 2.0f;
        float markStature2 = 6.0f * (1.0f + timePC);
        mc.getTextureManager().bindTexture(TAG_MARK_OVERLAY);
        this.buffer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        this.buffer.pos(x - markStature2 / 2.0f, y - markStature2 / 2.0f).tex(0.0, 0.0).color(ColorUtils.swapAlpha(markC1, (float)ColorUtils.getAlphaFromColor(markC1) * animAlphaPC)).endVertex();
        this.buffer.pos(x - markStature2 / 2.0f, y + markStature2 / 2.0f).tex(0.0, 1.0).color(ColorUtils.swapAlpha(markC2, (float)ColorUtils.getAlphaFromColor(markC2) * animAlphaPC)).endVertex();
        this.buffer.pos(x + markStature2 / 2.0f, y + markStature2 / 2.0f).tex(1.0, 1.0).color(ColorUtils.swapAlpha(markC3, (float)ColorUtils.getAlphaFromColor(markC3) * animAlphaPC)).endVertex();
        this.buffer.pos(x + markStature2 / 2.0f, y - markStature2 / 2.0f).tex(1.0, 0.0).color(ColorUtils.swapAlpha(markC4, (float)ColorUtils.getAlphaFromColor(markC4) * animAlphaPC)).endVertex();
        this.tesellator.draw();
        GL11.glBlendFunc(770, 771);
        GL11.glShadeModel(7424);
        GL11.glPopMatrix();
        CFontRenderer textFont = Fonts.mntsb_10;
        String name = base.getName() + " " + String.format("%.1f", Float.valueOf(base.getSmoothHealth())).replace(".0", "");
        float textW = textFont.getStringWidth(name);
        float textX = MathUtils.clamp(x - textW / 2.0f, (float)markOffset - 6.0f, (float)(sr.getScaledWidth() - markOffset) - textW + 8.0f);
        float textY = y - 10.0f;
        if (ColorUtils.getAlphaFromColor(textColor) >= 33) {
            textFont.drawStringWithShadow(name, textX, textY, textColor);
        }
    }

    private void drawEntity2DTag(EntityLivingBase base, float alphaPC, boolean items, boolean armor, boolean enchants, boolean potions, boolean shadow, boolean healthLine, float x, float y, RenderItem renderItem) {
        List activeEffects;
        alphaPC *= base.getDeathAlpha();
        if ((double)(alphaPC *= Math.min(((float)base.ticksExisted + mc.getRenderPartialTicks()) / 12.0f, 1.0f)) > 0.96) {
            alphaPC = 1.0f;
        }
        if (base.getHealth() == 0.0f) {
            healthLine = false;
        }
        if (healthLine) {
            y -= 3.0f;
        }
        int texColor = ColorUtils.swapAlpha(-65537, 255.0f * alphaPC);
        int bgColor = ColorUtils.getColor(0, (int)(65.0f * alphaPC));
        int bgOutColor = ColorUtils.getColor(0, (int)(100.0f * alphaPC));
        String name = NameTags.getName(base);
        CFontRenderer font = Fonts.comfortaaBold_12;
        float w = font.getStringWidth(name);
        float extXYWH = 1.5f * alphaPC;
        if (shadow) {
            float offset = -1.0f + 1.5f * alphaPC;
            RenderUtils.drawOutsideAndInsideFullRoundedFullGradientShadowRectWithBloomBool(x - w / 2.0f - 2.0f - extXYWH + offset, y - 9.0f - extXYWH + offset, x - w / 2.0f - 2.0f - extXYWH + w + 4.0f + extXYWH * 2.0f - offset, y - 9.0f - extXYWH + 9.0f + extXYWH * 2.0f + (healthLine ? 3.0f : 0.0f) - offset, 3.0f, ClientColors.getColor1(45, 0.2f * alphaPC), ClientColors.getColor2(0, 0.2f * alphaPC), ClientColors.getColor2(45, 0.2f * alphaPC), ClientColors.getColor1(0, 0.2f * alphaPC), true);
        }
        RenderUtils.drawRoundOutline(x - w / 2.0f - 2.0f - extXYWH, y - 9.0f - extXYWH, w + 4.0f + extXYWH * 2.0f, 9.0f + extXYWH * 2.0f + (healthLine ? 3.0f : 0.0f), 4.0f, 0.1f, bgColor, shadow ? 0 : bgOutColor);
        if (healthLine) {
            float hpPercent = MathUtils.clamp(base.getSmoothHealth() / base.getMaxHealth(), 0.0f, 1.0f);
            float hpExtX = 3.5f;
            float hpExtY = 0.0f;
            float hpX1 = x - w / 2.0f - 2.0f - extXYWH + 3.5f;
            float hpX2 = hpX1 + (w + 4.0f + extXYWH * 2.0f - 7.0f) * hpPercent;
            float hpX3 = hpX1 + (w + 4.0f + extXYWH * 2.0f - 7.0f);
            int hpCol1 = ColorUtils.getProgressColor(0.25f).getRGB();
            hpCol1 = ColorUtils.swapAlpha(hpCol1, 255.0f * alphaPC);
            int hpCol2 = ColorUtils.getProgressColor(0.25f + hpPercent * 0.75f).getRGB();
            hpCol2 = ColorUtils.swapAlpha(hpCol2, 255.0f * alphaPC);
            RenderUtils.drawFullGradientRectPro(hpX1, y + hpExtY, hpX2, y + 1.0f + hpExtY, hpCol1, hpCol2, hpCol2, hpCol1, false);
            RenderUtils.drawAlphedRect(hpX2, y + hpExtY, hpX3, y + 1.0f + hpExtY, bgOutColor);
        }
        if (RenderUtils.alpha(texColor) >= 32) {
            font.drawStringWithShadow(name, x - w / 2.0f, y - 6.0f, texColor);
        }
        if (potions && base.getActivePotionEffects() != null && !(activeEffects = base.getActivePotionEffects().stream().filter(Objects::nonNull).filter(effect -> effect.getDuration() > 0).toList()).isEmpty()) {
            y -= this.drawPotionEffectsReturningHeight(activeEffects, x, y - 11.0f, alphaPC) * alphaPC;
        }
        if ((items || armor) && base instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer)base;
            ItemStack hOffHand = player.getHeldItemOffhand();
            ItemStack aHelmet = player.inventory.armorInventory.get(0);
            ItemStack aChestplate = player.inventory.armorInventory.get(1);
            ItemStack aLeggings = player.inventory.armorInventory.get(2);
            ItemStack aFeet = player.inventory.armorInventory.get(3);
            ItemStack hMainHand = player.getHeldItemMainhand();
            List stacks = (items && armor ? Arrays.asList(hOffHand, aHelmet, aChestplate, aLeggings, aFeet, hMainHand) : (items ? Arrays.asList(hOffHand, hMainHand) : Arrays.asList(aHelmet, aChestplate, aLeggings, aFeet))).stream().filter(Objects::nonNull).filter(stack -> !stack.func_190926_b()).toList();
            if (!stacks.isEmpty()) {
                float itemScale = 0.5f * alphaPC;
                this.drawItemStackList(stacks, enchants, x, (y -= 26.0f * itemScale) - 9.0f, alphaPC, renderItem, itemScale);
            }
        }
    }

    private String getPotionEffectString(PotionEffect potionEffect) {
        Object power = "";
        ChatFormatting potionColor = null;
        int duration = potionEffect.getDuration();
        if (duration != 0) {
            int level = potionEffect.getAmplifier() == 0 ? 0 : potionEffect.getAmplifier() + 1;
            power = TextFormatting.GRAY + I18n.format("enchantment.level." + level, new Object[0]);
            power = ((String)power).replace("enchantment.level.0", "");
            power = ((String)power).replace("enchantment.level.", "");
            if (duration > 1000) {
                potionColor = ChatFormatting.GREEN;
            }
            if (duration < 800) {
                potionColor = ChatFormatting.YELLOW;
            }
            if (duration < 600) {
                potionColor = ChatFormatting.GOLD;
            }
            if (duration < 200) {
                ChatFormatting chatFormatting = potionColor = System.currentTimeMillis() % 700L > 350L ? ChatFormatting.RED : ChatFormatting.DARK_RED;
            }
            if (potionEffect.getIsPotionDurationMax()) {
                potionColor = ChatFormatting.LIGHT_PURPLE;
            }
        }
        return (I18n.format(potionEffect.getPotion().getName(), new Object[0]) + " " + (String)power + TextFormatting.GRAY + " " + potionColor + Potion.getPotionDurationString(potionEffect, 1.0f)).replace("  ", " ").replace("null", "");
    }

    private float drawPotionEffectsReturningHeight(List<PotionEffect> potionEffects, float x, float y, float alphaPC) {
        CFontRenderer effectFont = Fonts.minecraftia_16;
        float iXP = (float)potionEffects.stream().filter(effect -> effect.getPotion().hasStatusIcon()).count() * -4.5f;
        for (PotionEffect effect2 : potionEffects) {
            String durStr = Potion.getPotionDurationString(effect2, 1.0f);
            float strW = effectFont.getStringWidth(durStr);
            iXP -= strW / 4.0f * alphaPC;
        }
        float iYP = -9.5f;
        for (PotionEffect effect3 : potionEffects) {
            if (!this.onDoDrawPotionEffectIcon(true, x + iXP, y + iYP, 9, effect3.getPotion())) continue;
            String durStr = Potion.getPotionDurationString(effect3, 1.0f);
            float strW = effectFont.getStringWidth(durStr);
            String level = "Lv" + (effect3.getAmplifier() + 1);
            int durColor = ColorUtils.swapAlpha(ColorUtils.getOverallColorFrom(ColorUtils.getOverallColorFrom(ColorUtils.getColor(255, 0, 0), ColorUtils.getColor(0, 255, 0), MathUtils.clamp((float)effect3.getDuration() / 1000.0f, 0.0f, 1.0f)), -1), 255.0f * alphaPC);
            if (ColorUtils.getAlphaFromColor(durColor) >= 33) {
                int lvColor = ColorUtils.swapAlpha(ColorUtils.getOverallColorFrom(ColorUtils.getColor(175, 175, 175), durColor), 255.0f * alphaPC);
                GL11.glScaled(0.5, 0.5, 1.0);
                effectFont.drawString(durStr, (x + iXP + 9.0f) * 2.0f, (y + iYP + 5.0f) * 2.0f, durColor);
                if (ColorUtils.getAlphaFromColor(lvColor) >= 33) {
                    effectFont.drawString(level, (x + iXP + 9.0f) * 2.0f, (y + iYP) * 2.0f, lvColor);
                }
                GL11.glScaled(2.0, 2.0, 1.0);
            }
            iXP += (10.0f + strW / 2.0f) * alphaPC;
        }
        return potionEffects.size() == 0 ? 0.0f : 10.0f * alphaPC;
    }

    private void drawItemStackList(List<ItemStack> stacks, boolean showEnchants, float x, float y, float alphaPC, RenderItem renderItem, float scale) {
        float itemPixScale = 16.0f * (scale *= alphaPC);
        x -= (float)stacks.size() * (itemPixScale / 2.0f) + itemPixScale / 4.0f;
        float prevZLevel = renderItem.zLevel;
        int index = 0;
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDepthMask(true);
        int stackNumber = 0;
        int texColor = ColorUtils.swapAlpha(-65537, 245.0f * alphaPC * alphaPC);
        for (ItemStack stack : stacks) {
            ++stackNumber;
            GL11.glDepthRange(0.0, 0.01);
            if (renderItem.zLevel != 300.0f) {
                renderItem.zLevel = 300.0f;
            }
            GL11.glPushMatrix();
            GlStateManager.enableDepth();
            RenderHelper.enableGUIStandardItemLighting();
            GL11.glTranslated(x, y, 0.0);
            RenderUtils.customScaledObject2D(0.0f, 0.0f, itemPixScale, itemPixScale, scale);
            renderItem.renderItemAndEffectIntoGUI(stack, 0, 0);
            if (alphaPC * 255.0f >= 32.0f) {
                renderItem.renderItemOverlayIntoGUI(Fonts.minecraftia_16, stack, 0, 0, stack.getCount());
            }
            RenderUtils.drawItemWarnIfLowDur(stack, 0.0f, 0.0f, 1.0f, 1.0f, 3);
            if ((stackNumber == 1 || stackNumber == stacks.size()) && stack.getItem() instanceof ItemSkull) {
                CFontRenderer skullFont = Fonts.mntsb_18;
                String skullDisplay = stack.getDisplayName().replace("\u00a7r", "").replace("\u00a7l", "").replace("\u00a7k", "").replace("\u00a7n", "").replace("\u00a7n", "").replace("\u00a7o", "");
                float headTextX = stacks.size() == 1 ? (float)(-skullFont.getStringWidth(skullDisplay)) / 2.0f + 8.0f : (stackNumber == stacks.size() ? 18.0f : (float)(-skullFont.getStringWidth(skullDisplay)) - 2.0f);
                RenderHelper.disableStandardItemLighting();
                GL11.glDepthMask(false);
                skullFont.drawStringWithShadow(skullDisplay, headTextX, stacks.size() == 1 ? -5.0 : 6.0, ColorUtils.swapAlpha(-1, 255.0f * alphaPC * alphaPC));
                GL11.glDepthMask(true);
                RenderHelper.enableGUIStandardItemLighting();
            }
            RenderUtils.customScaledObject2D(0.0f, 0.0f, itemPixScale, itemPixScale, 1.0f / scale);
            GL11.glTranslated(-x, -y, 0.0);
            RenderHelper.disableStandardItemLighting();
            GL11.glDepthRange(0.0, 1.0);
            if (showEnchants) {
                GL11.glDepthMask(false);
                CFontRenderer enchFont = Fonts.smallestpixel_20;
                float texY = y - 2.0f;
                index = 0;
                for (String enchStr : this.getEnchantNamesOfEnchantsMap(stack)) {
                    if (index > 6) continue;
                    float strW = enchFont.getStringWidth(enchStr);
                    float texX = x + (16.0f * scale - strW / 2.0f) * scale;
                    if (RenderUtils.alpha(texColor) >= 33) {
                        GL11.glScaled(0.5, 0.5, 1.0);
                        enchFont.drawStringWithShadow(enchStr, (texX + 2.5f) * 2.0f, texY * 2.0f, texColor);
                        GL11.glScaled(2.0, 2.0, 1.0);
                    }
                    texY -= 4.5f * alphaPC;
                    ++index;
                }
            }
            GlStateManager.enableDepth();
            GL11.glDepthMask(true);
            GL11.glPopMatrix();
            x += itemPixScale;
            ++index;
        }
        renderItem.zLevel = prevZLevel;
    }

    List<String> getEnchantNamesOfEnchantsMap(ItemStack stack) {
        ArrayList<String> list = new ArrayList<String>();
        for (Enchantment enchantment : EnchantmentHelper.getEnchantments(stack).keySet()) {
            Object translated = enchantment.getTranslatedName(-228).replace(" enchantment.level.-228", "").replace("\u0417\u0430\u0449\u0438\u0442\u0430", "\u00a73\u0417").replace("\u041e\u0433\u043d\u0435\u0443\u043f\u043e\u0440\u043d\u043e\u0441\u0442\u044c", "\u00a77\u041e").replace("\u041d\u0435\u0432\u0435\u0441\u043e\u043c\u043e\u0441\u0442\u044c", "\u00a7b\u041d").replace("\u0412\u0437\u0440\u044b\u0432\u043e\u0443\u0441\u0442\u043e\u0439\u0447\u0438\u0432\u043e\u0441\u0442\u044c", "\u00a77\u0412").replace("\u0417\u0430\u0449\u0438\u0442\u0430 \u043e\u0442 \u0441\u043d\u0430\u0440\u044f\u0434\u043e\u0432", "\u00a77\u0417").replace("\u041f\u043e\u0434\u0432\u043e\u0434\u043d\u043e\u0435 \u0434\u044b\u0445\u0430\u043d\u0438\u0435", "\u00a71\u041f").replace("\u041f\u043e\u0434\u0432\u043e\u0434\u043d\u0438\u043a", "\u00a71\u041f").replace("\u0428\u0438\u043f\u044b", "\u00a72\u0428").replace("\u041f\u043e\u0434\u0432\u043e\u0434\u043d\u0430\u044f \u0445\u043e\u0434\u044c\u0431\u0430", "\u00a71\u041f").replace("\u041b\u0435\u0434\u043e\u0445\u043e\u0434", "\u00a7b\u041b").replace("\u041f\u0440\u043e\u043a\u043b\u044f\u0442\u044c\u0435 \u043d\u0435\u0441\u044a\u0451\u043c\u043d\u043e\u0441\u0442\u0438", "\u00a74\u041f").replace("\u041e\u0441\u0442\u0440\u043e\u0442\u0430", "\u00a7c\u041e").replace("\u041d\u0435\u0431\u0435\u0441\u043d\u0430\u044f \u043a\u0430\u0440\u0430", "\u00a72\u041a").replace("\u0411\u0438\u0447 \u0447\u043b\u0435\u043d\u0438\u0441\u0442\u043e\u043d\u043e\u0433\u0438\u0445", "\u00a72\u0411").replace("\u041e\u0442\u0434\u0430\u0447\u0430", "\u00a77\u041e").replace("\u0417\u0430\u0433\u043e\u0432\u043e\u0440 \u043e\u0433\u043d\u044f", "\u00a76\u0417").replace("\u0414\u043e\u0431\u044b\u0447\u0430", "\u00a72\u0414").replace("\u0420\u0430\u0437\u044f\u0449\u0438\u0439 \u043a\u043b\u0438\u043d\u043e\u043a", "\u00a77\u0420").replace("\u041f\u0440\u043e\u0447\u043d\u043e\u0441\u0442\u044c", "\u00a77\u041f").replace("\u0421\u0438\u043b\u0430", "\u00a7c\u0421").replace("\u041e\u0442\u043a\u0438\u0434\u044b\u0432\u0430\u043d\u0438\u0435", "\u00a77\u041e").replace("\u0413\u043e\u0440\u044f\u0449\u0430\u044f \u0441\u0442\u0440\u0435\u043b\u0430", "\u00a76\u0413").replace("\u0411\u0435\u0441\u043a\u043e\u043d\u0435\u0447\u043d\u043e\u0441\u0442\u044c", "\u00a78\u0411").replace("\u041f\u043e\u0447\u0438\u043d\u043a\u0430", "\u00a7a\u041f").replace("\u041f\u0440\u043e\u043a\u043b\u044f\u0442\u0438\u0435 \u0443\u0442\u0440\u0430\u0442\u044b", "\u00a74\u0423").replace("\u042d\u0444\u0444\u0435\u043a\u0442\u0438\u0432\u043d\u043e\u0441\u0442\u044c", "\u00a72\u042d").replace("\u0428\u0451\u043b\u043a\u043e\u0432\u043e\u0435 \u043a\u0430\u0441\u0430\u043d\u0438\u0435", "\u00a72\u0428").replace("\u0423\u0434\u0430\u0447\u0430", "\u00a72\u0423").replace("\u0412\u0435\u0437\u0443\u0447\u0438\u0439 \u0440\u044b\u0431\u0430\u043a", "\u00a72\u0412").replace("\u041f\u0440\u0438\u043c\u0430\u043d\u043a\u0430", "\u00a72\u041f").replace("\u0414\u043e\u043b\u0433\u043e\u0432\u0435\u0447\u043d\u043e\u0441\u0442\u044c", "\u00a77\u041f").replace("\u041e\u0433\u043e\u043d\u044c", "\u00a76\u041e").replace("\u041d\u0435\u0432\u0435\u0441\u043e\u043c\u043a\u0430", "\u00a7bH");
            translated = (String)translated + EnchantmentHelper.getEnchantmentLevel(enchantment, stack);
            list.add((String)translated);
        }
        return list;
    }

    public float[] getPointOfRadian(float x, float y, float x2, float y2, double radian, float offset) {
        x += offset;
        y += offset;
        x2 -= offset;
        y2 -= offset;
        offset /= 2.0f;
        float w = x2 - x;
        float h = y2 - y;
        float xPos = x + w / 2.0f;
        float yPos = y + h / 2.0f;
        xPos = (float)((double)xPos + Math.cos(Math.toRadians(radian)) * (double)w / (double)1.443333f);
        yPos = (float)((double)yPos + Math.sin(Math.toRadians(radian)) * (double)h / (double)1.443333f);
        xPos = MathUtils.clamp(xPos, x + offset, x2 - offset);
        yPos = MathUtils.clamp(yPos, y + offset, y2 - offset);
        return new float[]{xPos, yPos};
    }

    public boolean onDoDrawPotionEffectIcon(boolean bindTex, float x, float y, int size, Potion potion) {
        if (potion == null) {
            return false;
        }
        if (potion.hasStatusIcon()) {
            if (bindTex) {
                mc.getTextureManager().bindTexture(GuiContainer.INVENTORY_BACKGROUND);
            }
            int indexTex = potion.getStatusIconIndex();
            GL11.glPushMatrix();
            GlStateManager.disableLighting();
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            GL11.glTranslated(x, y, 0.0);
            GL11.glScaled(0.05555555555555555, 0.05555555555555555, 1.0);
            GL11.glScaled(size, size, 1.0);
            new Gui().drawTexturedModalRect(0, 0, indexTex % 8 * 18, 198 + indexTex / 8 * 18, 18, 18);
            GL11.glPopMatrix();
            return true;
        }
        return false;
    }

    static {
        TAG_MARK_BASE = new ResourceLocation("vegaline/modules/nametags/tagmarkbase.png");
        TAG_MARK_OVERLAY = new ResourceLocation("vegaline/modules/nametags/tagmarkoverlay.png");
    }
}

