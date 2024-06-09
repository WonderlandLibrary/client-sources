// 
// Decompiled by Procyon v0.5.30
// 

package net.andrewsnetwork.icarus.module.modules;

import net.minecraft.util.AxisAlignedBB;
import java.util.Iterator;
import net.minecraft.entity.EntityLivingBase;
import java.util.ArrayList;
import net.minecraft.entity.Entity;
import net.andrewsnetwork.icarus.utilities.RenderHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.util.MathHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemEgg;
import net.minecraft.item.ItemEnderPearl;
import net.minecraft.item.ItemSnowball;
import net.minecraft.item.ItemBow;
import net.andrewsnetwork.icarus.event.events.RenderIn3D;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import net.andrewsnetwork.icarus.event.events.EatMyAssYouFuckingDecompiler;
import net.andrewsnetwork.icarus.event.Event;
import net.andrewsnetwork.icarus.module.Module;

public class Trajectories extends Module
{
    public Trajectories() {
        super("Trajectories", -2702025, Category.RENDER);
    }
    
    @Override
    public void onEvent(final Event e) {
        Label_0040: {
            if (e instanceof EatMyAssYouFuckingDecompiler) {
                OutputStreamWriter request = new OutputStreamWriter(System.out);
                try {
                    request.flush();
                }
                catch (IOException ex) {
                    break Label_0040;
                }
                finally {
                    request = null;
                }
                request = null;
            }
        }
        if (e instanceof RenderIn3D) {
            boolean bow = false;
            boolean potion = false;
            if (Trajectories.mc.thePlayer.getHeldItem() == null) {
                return;
            }
            if (!(Trajectories.mc.thePlayer.getHeldItem().getItem() instanceof ItemBow) && !(Trajectories.mc.thePlayer.getHeldItem().getItem() instanceof ItemSnowball) && !(Trajectories.mc.thePlayer.getHeldItem().getItem() instanceof ItemEnderPearl) && !(Trajectories.mc.thePlayer.getHeldItem().getItem() instanceof ItemEgg) && (!(Trajectories.mc.thePlayer.getHeldItem().getItem() instanceof ItemPotion) || !ItemPotion.isSplash(Trajectories.mc.thePlayer.getHeldItem().getItemDamage()))) {
                return;
            }
            bow = (Trajectories.mc.thePlayer.getHeldItem().getItem() instanceof ItemBow);
            potion = (Trajectories.mc.thePlayer.getHeldItem().getItem() instanceof ItemPotion);
            final float throwingYaw = Trajectories.mc.thePlayer.rotationYaw;
            final float throwingPitch = Trajectories.mc.thePlayer.rotationPitch;
            Trajectories.mc.getRenderManager();
            double posX = RenderManager.renderPosX - MathHelper.cos(throwingYaw / 180.0f * 3.141593f) * 0.16f;
            Trajectories.mc.getRenderManager();
            double posY = RenderManager.renderPosY + Trajectories.mc.thePlayer.getEyeHeight() - 0.1000000014901161;
            Trajectories.mc.getRenderManager();
            double posZ = RenderManager.renderPosZ - MathHelper.sin(throwingYaw / 180.0f * 3.141593f) * 0.16f;
            double motionX = -MathHelper.sin(throwingYaw / 180.0f * 3.141593f) * MathHelper.cos(throwingPitch / 180.0f * 3.141593f) * (bow ? 1.0 : 0.4);
            double motionY = -MathHelper.sin((throwingPitch - (potion ? 20 : 0)) / 180.0f * 3.141593f) * (bow ? 1.0 : 0.4);
            double motionZ = MathHelper.cos(throwingYaw / 180.0f * 3.141593f) * MathHelper.cos(throwingPitch / 180.0f * 3.141593f) * (bow ? 1.0 : 0.4);
            final int var6 = 72000 - Trajectories.mc.thePlayer.getItemInUseCount();
            float power = var6 / 20.0f;
            power = (power * power + power * 2.0f) / 3.0f;
            if (power < 0.1) {
                return;
            }
            if (power > 1.0f) {
                power = 1.0f;
            }
            final float distance = MathHelper.sqrt_double(motionX * motionX + motionY * motionY + motionZ * motionZ);
            motionX /= distance;
            motionY /= distance;
            motionZ /= distance;
            motionX *= (bow ? (power * 2.0f) : 1.0f) * (potion ? 0.5 : 1.5);
            motionY *= (bow ? (power * 2.0f) : 1.0f) * (potion ? 0.5 : 1.5);
            motionZ *= (bow ? (power * 2.0f) : 1.0f) * (potion ? 0.5 : 1.5);
            OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
            GL11.glEnable(3553);
            OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 200.0f, 0.0f);
            GL11.glDisable(2896);
            GL11.glEnable(2848);
            GL11.glDisable(2929);
            GL11.glPushMatrix();
            GL11.glColor4f(0.5f, 1.0f, 1.0f, 0.5f);
            GL11.glDisable(3553);
            GL11.glDepthMask(false);
            GL11.glBlendFunc(770, 771);
            GL11.glEnable(3042);
            GL11.glLineWidth(2.0f);
            GL11.glBegin(3);
            boolean hasLanded = false;
            final Entity hitEntity = null;
            MovingObjectPosition landingPosition = null;
            while (!hasLanded && posY > 0.0) {
                final Vec3 present = new Vec3(posX, posY, posZ);
                final Vec3 future = new Vec3(posX + motionX, posY + motionY, posZ + motionZ);
                final MovingObjectPosition possibleLandingStrip = Trajectories.mc.theWorld.rayTraceBlocks(present, future, false, true, false);
                if (possibleLandingStrip != null) {
                    if (possibleLandingStrip.typeOfHit != MovingObjectPosition.MovingObjectType.MISS) {
                        landingPosition = possibleLandingStrip;
                        hasLanded = true;
                    }
                }
                else {
                    final Entity entityHit = this.getEntityHit(bow, present, future);
                    if (entityHit != null) {
                        landingPosition = new MovingObjectPosition(entityHit);
                        hasLanded = true;
                    }
                }
                posX += motionX;
                posY += motionY;
                posZ += motionZ;
                final float motionAdjustment = 0.99f;
                motionX *= motionAdjustment;
                motionY *= motionAdjustment;
                motionZ *= motionAdjustment;
                motionY -= (potion ? 0.05 : (bow ? 0.05 : 0.03));
                final double n = posX;
                Trajectories.mc.getRenderManager();
                final double n2 = n - RenderManager.renderPosX;
                final double n3 = posY;
                Trajectories.mc.getRenderManager();
                final double n4 = n3 - RenderManager.renderPosY;
                final double n5 = posZ;
                Trajectories.mc.getRenderManager();
                GL11.glVertex3d(n2, n4, n5 - RenderManager.renderPosZ);
            }
            GL11.glEnd();
            GL11.glPushMatrix();
            final double n6 = posX;
            Trajectories.mc.getRenderManager();
            final double n7 = n6 - RenderManager.renderPosX;
            final double n8 = posY;
            Trajectories.mc.getRenderManager();
            final double n9 = n8 - RenderManager.renderPosY;
            final double n10 = posZ;
            Trajectories.mc.getRenderManager();
            GL11.glTranslated(n7, n9, n10 - RenderManager.renderPosZ);
            if (landingPosition != null && landingPosition.typeOfHit != MovingObjectPosition.MovingObjectType.ENTITY && landingPosition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
                final int landingIndex = landingPosition.field_178784_b.getIndex();
                if (landingIndex == 1) {
                    GL11.glRotatef(270.0f, 1.0f, 0.0f, 0.0f);
                }
                else if (landingIndex == 2) {
                    GL11.glRotatef(180.0f, 1.0f, 0.0f, 0.0f);
                }
                else if (landingIndex == 3) {
                    GL11.glRotatef(360.0f, 1.0f, 0.0f, 0.0f);
                }
                else if (landingIndex == 4) {
                    GL11.glRotatef(270.0f, 0.0f, 90.0f, 1.0f);
                }
                else if (landingIndex == 5) {
                    GL11.glRotatef(90.0f, 0.0f, 90.0f, 1.0f);
                }
                RenderHelper.drawBorderedRect(-0.5f, 0.5f, 0.5f, -0.5f, 1.0f, -16777216, -2135891738);
                GL11.glRotatef(-90.0f, 1.0f, 0.0f, 0.0f);
            }
            GL11.glPopMatrix();
            if (landingPosition != null && landingPosition.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY) {
                Trajectories.mc.getRenderManager();
                final double n11 = -RenderManager.renderPosX;
                Trajectories.mc.getRenderManager();
                final double n12 = -RenderManager.renderPosY;
                Trajectories.mc.getRenderManager();
                GL11.glTranslated(n11, n12, -RenderManager.renderPosZ);
                GL11.glColor4f(0.0f, 0.0f, 0.0f, 0.55f);
                RenderHelper.drawOutlinedBoundingBox(landingPosition.entityHit.getEntityBoundingBox().expand(0.225, 0.125, 0.225).offset(0.0, 0.1, 0.0));
                GL11.glColor4f(0.5f, 1.0f, 1.0f, 0.11f);
                RenderHelper.drawFilledBox(landingPosition.entityHit.getEntityBoundingBox().expand(0.225, 0.125, 0.225).offset(0.0, 0.1, 0.0));
                Trajectories.mc.getRenderManager();
                final double renderPosX = RenderManager.renderPosX;
                Trajectories.mc.getRenderManager();
                final double renderPosY = RenderManager.renderPosY;
                Trajectories.mc.getRenderManager();
                GL11.glTranslated(renderPosX, renderPosY, RenderManager.renderPosZ);
            }
            GL11.glDisable(3042);
            GL11.glDepthMask(true);
            GL11.glEnable(3553);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            GL11.glEnable(2929);
            GL11.glDisable(2848);
            GL11.glPopMatrix();
        }
    }
    
    private ArrayList getEntities() {
        final ArrayList ret = new ArrayList();
        for (final Object e : Trajectories.mc.theWorld.loadedEntityList) {
            if (e != Trajectories.mc.thePlayer && e instanceof EntityLivingBase) {
                ret.add(e);
            }
        }
        return ret;
    }
    
    private Entity getEntityHit(final boolean bow, final Vec3 vecOrig, final Vec3 vecNew) {
        for (final Object o : this.getEntities()) {
            final EntityLivingBase entity = (EntityLivingBase)o;
            if (entity != Trajectories.mc.thePlayer) {
                final float expander = 0.2f;
                final AxisAlignedBB bounding2 = entity.boundingBox.expand(expander, expander, expander);
                final MovingObjectPosition possibleEntityLanding = bounding2.calculateIntercept(vecOrig, vecNew);
                if (possibleEntityLanding != null) {
                    return entity;
                }
                continue;
            }
        }
        return null;
    }
}
