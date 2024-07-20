/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.module.modules;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemEgg;
import net.minecraft.item.ItemEnderPearl;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.item.ItemLingeringPotion;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemSnowball;
import net.minecraft.item.ItemSplashPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;
import ru.govno.client.module.Module;
import ru.govno.client.utils.Math.MathUtils;
import ru.govno.client.utils.Render.ColorUtils;
import ru.govno.client.utils.Render.RenderUtils;

public class ThrowDirs
extends Module {
    private final Random rand = new Random();
    private double[] randoms = new double[]{0.0, 0.0, 0.0};
    private List<EntityLivingBase> toRenderEntities = new ArrayList<EntityLivingBase>();

    public ThrowDirs() {
        super("ThrowDirs", 0, Module.Category.RENDER);
    }

    private boolean itemIsCorrectToThrow(Item itemIn) {
        return itemIn instanceof ItemBow || itemIn instanceof ItemSnowball || itemIn instanceof ItemEgg || itemIn instanceof ItemEnderPearl || itemIn instanceof ItemSplashPotion || itemIn instanceof ItemLingeringPotion || itemIn instanceof ItemFishingRod;
    }

    private ItemStackWithHand getCorrectThrowStackOfEntity(EntityLivingBase entityOf) {
        return entityOf == null ? null : (this.itemIsCorrectToThrow(entityOf.getHeldItemMainhand().getItem()) ? new ItemStackWithHand(entityOf.getHeldItemMainhand(), EnumHand.MAIN_HAND) : (this.itemIsCorrectToThrow(entityOf.getHeldItemOffhand().getItem()) ? new ItemStackWithHand(entityOf.getHeldItemOffhand(), EnumHand.OFF_HAND) : null));
    }

    private List<Vec3d> getPointsOfThrowable(World worldIn, ItemStackWithHand handStack, float partialTicks, EntityLivingBase entityOf, int maxDensity) {
        boolean calcTight;
        Item item;
        ArrayList<Vec3d> vecs = new ArrayList<Vec3d>();
        if (handStack != null && entityOf != null && worldIn != null && worldIn.isBlockLoaded(entityOf.getPosition().down((int)entityOf.posY - 1)) && this.itemIsCorrectToThrow(item = handStack.getItemStack().getItem()) && (!(calcTight = item instanceof ItemBow) || entityOf.isBowing())) {
            float throwFactor = calcTight ? 1.0f : 0.4f;
            double[] selfHeadRotateWR = new double[]{entityOf.prevRotationYawHead + (entityOf.rotationYawHead - entityOf.prevRotationYawHead) * partialTicks, entityOf.prevRotationPitchHead + (entityOf.rotationPitchHead - entityOf.prevRotationPitchHead) * partialTicks, Math.toRadians(entityOf.prevRotationYawHead + (entityOf.rotationYawHead - entityOf.prevRotationYawHead) * partialTicks), Math.toRadians(entityOf.prevRotationPitchHead + (entityOf.rotationPitchHead - entityOf.prevRotationPitchHead) * partialTicks)};
            Vec3d playerVector = new Vec3d(entityOf.lastTickPosX + (entityOf.posX - entityOf.lastTickPosX) * (double)partialTicks, entityOf.lastTickPosY + (entityOf.posY - entityOf.lastTickPosY) * (double)partialTicks + (double)entityOf.getEyeHeight(), entityOf.lastTickPosZ + (entityOf.posZ - entityOf.lastTickPosZ) * (double)partialTicks);
            double offsetStartDir = handStack.getEnumHand() == EnumHand.MAIN_HAND ? 1.0 : -1.0;
            double throwOfX = playerVector.xCoord - Math.cos(selfHeadRotateWR[2]) * 0.1 * offsetStartDir;
            double throwOfY = playerVector.yCoord;
            double throwOfZ = playerVector.zCoord - Math.sin(selfHeadRotateWR[2]) * 0.1 * offsetStartDir;
            double shiftX = -Math.sin(selfHeadRotateWR[2]) * Math.cos(selfHeadRotateWR[3]) * (double)throwFactor;
            double shiftY = -Math.sin(selfHeadRotateWR[3]) * (double)throwFactor;
            double shiftZ = Math.cos(selfHeadRotateWR[2]) * Math.cos(selfHeadRotateWR[3]) * (double)throwFactor;
            double throwMotion = Math.sqrt(shiftX * shiftX + shiftY * shiftY + shiftZ * shiftZ);
            if (calcTight && entityOf instanceof EntityPlayerSP) {
                shiftX += this.randoms[0];
                shiftY += this.randoms[1];
                shiftZ += this.randoms[2];
            }
            shiftX /= throwMotion;
            shiftY /= throwMotion;
            shiftZ /= throwMotion;
            if (calcTight) {
                float tightPower = (72000.0f - (float)entityOf.getItemInUseCount() + partialTicks) / 20.0f;
                tightPower = (tightPower = (tightPower * tightPower + tightPower * 2.0f) / 3.0f) < 0.1f ? 1.0f : (tightPower > 1.0f ? 1.0f : tightPower);
                shiftX *= (double)(tightPower *= 3.0f);
                shiftY *= (double)tightPower;
                shiftZ *= (double)tightPower;
            } else {
                shiftX *= 1.5;
                shiftY *= 1.5;
                shiftZ *= 1.5;
            }
            double gravityFactor = calcTight ? 0.005 : (item instanceof ItemPotion ? 0.04 : (item instanceof ItemFishingRod ? 0.015 : 0.003));
            int max = maxDensity;
            while (maxDensity > 0) {
                vecs.add(new Vec3d(throwOfX, throwOfY, throwOfZ));
                double asellate = 0.999;
                shiftY = shiftY * asellate - gravityFactor;
                if (worldIn.rayTraceBlocks(playerVector, new Vec3d(throwOfX += (shiftX *= asellate) * 0.1, throwOfY += shiftY * 0.1, throwOfZ += (shiftZ *= asellate) * 0.1)) != null) break;
                --maxDensity;
            }
        }
        return vecs;
    }

    private List<Vec3d> getThowingVecsListOfEntity(EntityLivingBase entityFor) {
        return this.getPointsOfThrowable(ThrowDirs.mc.world, this.getCorrectThrowStackOfEntity(entityFor), mc.getRenderPartialTicks(), entityFor, 500);
    }

    private void start3DRendering(boolean ignoreDepth, RenderManager renderMngr) {
        GL11.glPushMatrix();
        GL11.glEnable(2848);
        GL11.glBlendFunc(770, 32772);
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        if (ignoreDepth) {
            GL11.glDisable(2929);
        } else {
            GL11.glEnable(2929);
        }
        GL11.glDisable(3008);
        GL11.glShadeModel(7425);
        GL11.glHint(3154, 4354);
        GL11.glEnable(2832);
        GL11.glDepthMask(false);
        GL11.glPointSize(2.0f);
        GL11.glLineWidth(1.8f);
        GL11.glDisable(2896);
        GL11.glTranslated(-RenderManager.renderPosX, -RenderManager.renderPosY, -RenderManager.renderPosZ);
    }

    private void end3DRendering() {
        RenderUtils.resetBlender();
        GL11.glBlendFunc(770, 771);
        GL11.glLineWidth(1.0f);
        GL11.glPointSize(1.0f);
        GL11.glDepthMask(true);
        GL11.glHint(3154, 4352);
        GL11.glShadeModel(7424);
        GL11.glEnable(3008);
        GL11.glEnable(2929);
        GL11.glPopMatrix();
    }

    private void renderLineBegin(List<Vec3d> vecs, int color1, int color2, float alphaPC, float alphaPass) {
        int max = vecs.size();
        if ((alphaPC *= MathUtils.clamp((float)max / 50.0f, 0.0f, 1.0f)) == 0.0f) {
            return;
        }
        color1 = ColorUtils.swapAlpha(color1, (float)ColorUtils.getAlphaFromColor(color1) * alphaPC);
        color2 = ColorUtils.swapAlpha(color2, (float)ColorUtils.getAlphaFromColor(color2) * alphaPC);
        GL11.glBegin(3);
        int index = 0;
        int lastColor = 0;
        Vec3d last = null;
        for (Vec3d vec : vecs) {
            float pcOfStartVec = (float)index / (float)max;
            float pcOfMiddleVec = (float)Math.abs(index - max / 2) / ((float)max / 2.0f);
            float darkPass = 1.0f - pcOfMiddleVec * alphaPass;
            lastColor = ColorUtils.getOverallColorFrom(color1, color2, pcOfStartVec);
            int currentColor = lastColor;
            int finalColor = ColorUtils.swapAlpha(currentColor, (float)ColorUtils.getAlphaFromColor(currentColor) * darkPass);
            if (ColorUtils.getAlphaFromColor(finalColor) >= 1) {
                RenderUtils.glColor(ColorUtils.toDark(finalColor, darkPass));
                GL11.glVertex3d(vec.xCoord, vec.yCoord, vec.zCoord);
            }
            ++index;
            last = vec;
        }
        GL11.glEnd();
        if (lastColor != 0 && last != null) {
            if (ColorUtils.getAlphaFromColor(lastColor) < 60) {
                lastColor = ColorUtils.swapAlpha(lastColor, 60.0f);
            }
            RenderUtils.glColor(lastColor);
            GL11.glBegin(0);
            GL11.glVertex3d(last.xCoord, last.yCoord, last.zCoord);
            GL11.glEnd();
        }
        GlStateManager.resetColor();
    }

    @Override
    public void onToggled(boolean actived) {
        this.stateAnim.to = this.actived ? 1.0f : 0.0f;
        super.onToggled(actived);
    }

    private float getAlphaPC() {
        this.stateAnim.to = this.actived ? 1.0f : 0.0f;
        float aPC = this.stateAnim.getAnim();
        return aPC < 0.03f ? 0.0f : (aPC > 0.97f ? 1.0f : aPC);
    }

    @Override
    public void onUpdate() {
        if (!this.toRenderEntities.isEmpty()) {
            if (Minecraft.player != null && this.toRenderEntities.stream().anyMatch(base -> base.equals(Minecraft.player))) {
                this.randoms[0] = this.rand.nextGaussian() * (double)0.0075f;
            }
        }
        this.randoms[1] = this.rand.nextGaussian() * (double)0.0075f;
        this.randoms[2] = this.rand.nextGaussian() * (double)0.0075f;
    }

    @Override
    public void alwaysRender3D() {
        float alphaPC = this.getAlphaPC();
        if (alphaPC == 0.0f) {
            return;
        }
        this.toRenderEntities = ThrowDirs.mc.world.getLoadedEntityList().stream().map(Entity::getLivingBaseOf).filter(Objects::nonNull).filter(EntityLivingBase::isEntityAlive).filter(base -> base instanceof EntityPlayerSP || RenderUtils.isInView(base)).collect(Collectors.toList());
        if (this.toRenderEntities.isEmpty()) {
            return;
        }
        ArrayList<List<Vec3d>> pageVecLists = new ArrayList<List<Vec3d>>();
        for (EntityLivingBase entity : this.toRenderEntities) {
            List<Vec3d> vecsList = this.getThowingVecsListOfEntity(entity);
            if (vecsList.isEmpty()) continue;
            pageVecLists.add(vecsList);
        }
        if (!pageVecLists.isEmpty()) {
            int color1 = -1;
            int color2 = ColorUtils.getColor(255, 0, 0);
            float alphaPass = 0.9f;
            this.start3DRendering(false, mc.getRenderManager());
            pageVecLists.forEach(list -> {
                if (list.size() > 1 && ((Vec3d)list.get(0)).distanceTo((Vec3d)list.get(list.size() - 1)) > 1.0) {
                    this.renderLineBegin((List<Vec3d>)list, -1, color2, alphaPC, 0.9f);
                }
            });
            this.end3DRendering();
        }
    }

    private class ItemStackWithHand {
        private ItemStack stack;
        private EnumHand hand;

        private ItemStackWithHand(ItemStack stack, EnumHand hand) {
            this.stack = stack;
            this.hand = hand;
        }

        public ItemStack getItemStack() {
            return this.stack;
        }

        public EnumHand getEnumHand() {
            return this.hand;
        }
    }
}

