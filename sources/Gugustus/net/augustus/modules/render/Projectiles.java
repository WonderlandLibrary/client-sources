package net.augustus.modules.render;

import java.awt.Color;
import java.util.HashMap;
import net.augustus.events.EventRender3D;
import net.augustus.events.EventTick;
import net.augustus.modules.Categorys;
import net.augustus.modules.Module;
import net.augustus.settings.BooleanValue;
import net.augustus.settings.ColorSetting;
import net.augustus.settings.DoubleValue;
import net.lenni0451.eventapi.reflection.EventTarget;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderPearl;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityEgg;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import org.lwjgl.opengl.GL11;

public class Projectiles extends Module {
   public final ColorSetting color = new ColorSetting(1, "Color", this, new Color(21, 121, 230));
   public final BooleanValue calculatedColor = new BooleanValue(1, "CalculateColor", this, true);
   public final DoubleValue lineWidth = new DoubleValue(2, "LineWidth", this, 6.0, 1.0, 12.0, 0);
   private final java.util.ArrayList<java.util.ArrayList<Vec3>> points = new java.util.ArrayList<>();
   private final HashMap<java.util.ArrayList<Vec3>, MovingObjectPosition> hashMap = new HashMap<>();

   public Projectiles() {
      super("Projectiles", new Color(80, 107, 91), Categorys.RENDER);
   }

   @Override
   public void onEnable() {
      super.onEnable();
   }

   @Override
   public void onDisable() {
      super.onDisable();
      this.points.clear();
   }

   @EventTarget
   public void onEventTickEventTick(EventTick eventTick) {
      this.points.clear();
      this.hashMap.clear();

      for(Entity entity : mc.theWorld.loadedEntityList) {
         if (entity.ticksExisted >= 0
            && !entity.onGround
            && !entity.isInWater()
            && (
               entity instanceof EntityArrow
                  || entity instanceof EntitySnowball
                  || entity instanceof EntityEgg
                  || entity instanceof EntityEnderPearl
                  || entity instanceof EntityFireball
            )) {
            boolean b = true;
            int ticksInAir = 0;
            double posX = entity.posX;
            double posY = entity.posY;
            double posZ = entity.posZ;
            double motionX = entity.motionX;
            double motionY = entity.motionY;
            double motionZ = entity.motionZ;
            float rotationYaw = entity.rotationYaw;
            float rotationPitch = entity.rotationPitch;
            float prevRotationPitch = entity.prevRotationPitch;
            float prevRotationYaw = entity.prevRotationYaw;
            java.util.ArrayList<Vec3> vec3s = new java.util.ArrayList<>();
            MovingObjectPosition objectPosition = null;

            while(b) {
               if (ticksInAir > 300) {
                  b = false;
               }

               ++ticksInAir;
               Vec3 vec3 = new Vec3(posX, posY, posZ);
               Vec3 vec31 = new Vec3(posX + motionX, posY + motionY, posZ + motionZ);
               MovingObjectPosition movingobjectposition = mc.theWorld.rayTraceBlocks(vec3, vec31);
               if (movingobjectposition != null) {
                  b = false;
               }

               posX += motionX;
               posY += motionY;
               posZ += motionZ;
               float f1 = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
               rotationYaw = (float)(MathHelper.func_181159_b(motionX, motionZ) * 180.0 / Math.PI);
               if (entity instanceof EntityFireball) {
                  rotationYaw = (float)(MathHelper.func_181159_b(motionX, motionZ) * 180.0 / Math.PI) + 90.0F;
               }

               while(rotationPitch - prevRotationPitch >= 180.0F) {
                  prevRotationPitch += 360.0F;
               }

               while(rotationYaw - prevRotationYaw < -180.0F) {
                  prevRotationYaw -= 360.0F;
               }

               while(rotationYaw - prevRotationYaw >= 180.0F) {
                  prevRotationYaw += 360.0F;
               }

               float f2 = 0.99F;
               if (entity instanceof EntityFireball) {
                  f2 = 0.95F;
               }

               float f3 = 0.03F;
               if (entity instanceof EntityArrow) {
                  f3 = 0.05F;
               } else if (entity instanceof EntityFireball) {
                  f3 = 0.0F;
               }

               if (entity instanceof EntityFireball) {
                  EntityFireball entityFireball = (EntityFireball)entity;
                  motionX += entityFireball.accelerationX;
                  motionY += entityFireball.accelerationY;
                  motionZ += entityFireball.accelerationZ;
               }

               motionX *= (double)f2;
               motionY *= (double)f2;
               motionZ *= (double)f2;
               motionY -= (double)f3;
               vec3s.add(new Vec3(posX, posY, posZ));
            }

            this.points.add(vec3s);
            this.hashMap.put(vec3s, objectPosition);
         }
      }
   }

   @EventTarget
   public void onEventRender3D(EventRender3D eventRender3D) {
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      GL11.glEnable(2848);
      GL11.glDisable(3553);
      GlStateManager.disableCull();
      GL11.glDepthMask(false);
      GL11.glLineWidth((float)this.lineWidth.getValue() / 2.0F);

      for(java.util.ArrayList<Vec3> vec3s : this.points) {
         if (vec3s.size() > 1) {
            if (this.calculatedColor.getBoolean()) {
               double dist = Math.min(Math.max(mc.thePlayer.getDistance(vec3s.get(1).xCoord, vec3s.get(1).yCoord, vec3s.get(1).zCoord), 6.0), 36.0) - 6.0;
               Color color = new Color(
                  this.color.getColor().getRed(), this.color.getColor().getGreen(), this.color.getColor().getBlue(), this.color.getColor().getAlpha()
               );
               float[] hsbColor = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
               int colorRGB = Color.HSBtoRGB((float)(0.011415525F * dist), hsbColor[1], hsbColor[2]);
               float f = (float)(colorRGB >> 24 & 0xFF) / 255.0F;
               float red = (float)(colorRGB >> 16 & 0xFF) / 255.0F;
               float green = (float)(colorRGB >> 8 & 0xFF) / 255.0F;
               float blue = (float)(colorRGB & 0xFF) / 255.0F;
               GL11.glColor4f(red, green, blue, 0.85F);
            } else {
               GL11.glColor4f(
                  (float)this.color.getColor().getRed() / 255.0F,
                  (float)this.color.getColor().getGreen() / 255.0F,
                  (float)this.color.getColor().getBlue() / 255.0F,
                  0.85F
               );
            }

            Tessellator tessellator = Tessellator.getInstance();
            WorldRenderer worldrenderer = tessellator.getWorldRenderer();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            worldrenderer.begin(3, DefaultVertexFormats.POSITION);

            for(Vec3 vec3 : vec3s) {
               worldrenderer.pos(
                     (double)((float)vec3.xCoord) - mc.getRenderManager().getRenderPosX(),
                     (double)((float)vec3.yCoord) - mc.getRenderManager().getRenderPosY(),
                     (double)((float)vec3.zCoord) - mc.getRenderManager().getRenderPosZ()
                  )
                  .endVertex();
            }

            tessellator.draw();
         }
      }

      GL11.glLineWidth(1.0F);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      GL11.glDepthMask(true);
      GlStateManager.enableCull();
      GL11.glEnable(3553);
      GL11.glEnable(2929);
      GL11.glDisable(3042);
      GL11.glBlendFunc(770, 771);
      GL11.glDisable(2848);
   }
}
