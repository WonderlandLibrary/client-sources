package net.augustus.modules.render;

import java.awt.Color;
import java.util.Random;
import net.augustus.events.EventRender3D;
import net.augustus.modules.Categorys;
import net.augustus.modules.Module;
import net.augustus.settings.ColorSetting;
import net.augustus.settings.DoubleValue;
import net.lenni0451.eventapi.reflection.EventTarget;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemEgg;
import net.minecraft.item.ItemEnderPearl;
import net.minecraft.item.ItemSnowball;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import org.lwjgl.opengl.GL11;

public class Trajectories extends Module {
   public final ColorSetting color = new ColorSetting(1, "Color", this, new Color(21, 121, 230));
   public final DoubleValue lineWidth = new DoubleValue(2, "LineWidth", this, 6.0, 1.0, 12.0, 0);
   private final java.util.ArrayList<Vec3> positions = new java.util.ArrayList<>();

   public Trajectories() {
      super("Trajectories", new Color(29, 202, 103), Categorys.RENDER);
   }

   @Override
   public void onDisable() {
      super.onDisable();
      this.positions.clear();
   }

   @EventTarget
   public void onEvent3D(EventRender3D eventRender3D) {
      this.positions.clear();
      ItemStack itemStack = mc.thePlayer.getCurrentEquippedItem();
      MovingObjectPosition m = null;
      if (itemStack != null
         && (
            itemStack.getItem() instanceof ItemSnowball
               || itemStack.getItem() instanceof ItemEgg
               || itemStack.getItem() instanceof ItemBow
               || itemStack.getItem() instanceof ItemEnderPearl
         )) {
         EntityLivingBase thrower = mc.thePlayer;
         float rotationYaw = thrower.prevRotationYaw + (thrower.rotationYaw - thrower.prevRotationYaw) * mc.getTimer().renderPartialTicks;
         float rotationPitch = thrower.prevRotationPitch + (thrower.rotationPitch - thrower.prevRotationPitch) * mc.getTimer().renderPartialTicks;
         double posX = thrower.lastTickPosX + (thrower.posX - thrower.lastTickPosX) * (double)mc.getTimer().renderPartialTicks;
         double posY = thrower.lastTickPosY + (double)thrower.getEyeHeight() + (thrower.posY - thrower.lastTickPosY) * (double)mc.getTimer().renderPartialTicks;
         double posZ = thrower.lastTickPosZ + (thrower.posZ - thrower.lastTickPosZ) * (double)mc.getTimer().renderPartialTicks;
         posX -= MathHelper.cos(rotationYaw / 180.0F * (float) Math.PI) * 0.16F;
         posY -= 0.1F;
         posZ -= MathHelper.sin(rotationYaw / 180.0F * (float) Math.PI) * 0.16F;
         float multipicator = 0.4F;
         if (itemStack.getItem() instanceof ItemBow) {
            multipicator = 1.0F;
         }

         double motionX = -MathHelper.sin(rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(rotationPitch / 180.0F * (float) Math.PI) * multipicator;
         double motionZ = MathHelper.cos(rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(rotationPitch / 180.0F * (float) Math.PI) * multipicator;
         double motionY = -MathHelper.sin(rotationPitch / 180.0F * (float) Math.PI) * multipicator;
         float inaccuracy = 0.0F;
         float velocity = 1.5F;
         if (itemStack.getItem() instanceof ItemBow) {
            int i = mc.thePlayer.getItemInUseDuration() - mc.thePlayer.getItemInUseCount();
            float f = (float)i / 20.0F;
            f = (f * f + f * 2.0F) / 3.0F;
            if ((double)f < 0.1) {
               return;
            }

            if (f > 1.0F) {
               f = 1.0F;
            }

            velocity = f * 2.0F * 1.5F;
         }

         Random rand = new Random();
         float ff = MathHelper.sqrt_double(motionX * motionX + motionY * motionY + motionZ * motionZ);
         double x = motionX / (double)ff;
         double y = motionY / (double)ff;
         double z = motionZ / (double)ff;
         x += rand.nextGaussian() * 0.0075F * (double)inaccuracy;
         y += rand.nextGaussian() * 0.0075F * (double)inaccuracy;
         z += rand.nextGaussian() * 0.0075F * (double)inaccuracy;
         x *= velocity;
         y *= velocity;
         z *= velocity;
         motionX = x;
         motionY = y;
         motionZ = z;
         float prevRotationYaw = (float)(MathHelper.func_181159_b(x, z) * 180.0 / Math.PI);
         float prevRotationPitch = (float)(MathHelper.func_181159_b(y, MathHelper.sqrt_double(x * x + z * z)) * 180.0 / Math.PI);
         boolean b = true;
         int ticksInAir = 0;

         while(b) {
            if (ticksInAir > 300) {
               b = false;
            }

            ++ticksInAir;
            Vec3 vec3 = new Vec3(posX, posY, posZ);
            Vec3 vec31 = new Vec3(posX + motionX, posY + motionY, posZ + motionZ);
            MovingObjectPosition movingobjectposition = mc.theWorld.rayTraceBlocks(vec3, vec31);
            vec3 = new Vec3(posX, posY, posZ);
            vec31 = new Vec3(posX + motionX, posY + motionY, posZ + motionZ);
            if (movingobjectposition != null) {
               vec31 = new Vec3(movingobjectposition.hitVec.xCoord, movingobjectposition.hitVec.yCoord, movingobjectposition.hitVec.zCoord);
            }

            for(Entity entity : mc.theWorld.loadedEntityList) {
               if (entity != mc.thePlayer && entity instanceof EntityLivingBase) {
                  float f = 0.3F;
                  AxisAlignedBB localAxisAlignedBB = entity.getEntityBoundingBox().expand(f, f, f);
                  MovingObjectPosition localMovingObjectPosition = localAxisAlignedBB.calculateIntercept(vec3, vec31);
                  if (localMovingObjectPosition != null) {
                     movingobjectposition = localMovingObjectPosition;
                     break;
                  }
               }
            }

            if (movingobjectposition != null) {
               b = false;
            }

            m = movingobjectposition;
            posX += motionX;
            posY += motionY;
            posZ += motionZ;
            float f1 = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
            rotationYaw = (float)(MathHelper.func_181159_b(motionX, motionZ) * 180.0 / Math.PI);
            rotationPitch = (float)(MathHelper.func_181159_b(motionY, f1) * 180.0 / Math.PI);

            while(rotationPitch - prevRotationPitch < -180.0F) {
               prevRotationPitch -= 360.0F;
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
            float f3 = 0.03F;
            if (itemStack.getItem() instanceof ItemBow) {
               f3 = 0.05F;
            }

            motionX *= f2;
            motionY *= f2;
            motionZ *= f2;
            motionY -= f3;
            this.positions.add(new Vec3(posX, posY, posZ));
         }

         if (this.positions.size() > 1) {
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            GL11.glEnable(2848);
            GL11.glDisable(3553);
            GlStateManager.disableCull();
            GL11.glDepthMask(false);
            GL11.glColor4f(
               (float)this.color.getColor().getRed() / 255.0F,
               (float)this.color.getColor().getGreen() / 255.0F,
               (float)this.color.getColor().getBlue() / 255.0F,
               0.7F
            );
            GL11.glLineWidth((float)this.lineWidth.getValue() / 2.0F);
            Tessellator tessellator = Tessellator.getInstance();
            WorldRenderer worldrenderer = tessellator.getWorldRenderer();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            worldrenderer.begin(3, DefaultVertexFormats.POSITION);

            for(Vec3 vec3 : this.positions) {
               worldrenderer.pos(
                     (double)((float)vec3.xCoord) - mc.getRenderManager().getRenderPosX(),
                     (double)((float)vec3.yCoord) - mc.getRenderManager().getRenderPosY(),
                     (double)((float)vec3.zCoord) - mc.getRenderManager().getRenderPosZ()
                  )
                  .endVertex();
            }

            tessellator.draw();
            if (m != null) {
               GL11.glColor4f(
                  (float)this.color.getColor().getRed() / 255.0F,
                  (float)this.color.getColor().getGreen() / 255.0F,
                  (float)this.color.getColor().getBlue() / 255.0F,
                  0.3F
               );
               Vec3 hitVec = m.hitVec;
               EnumFacing enumFacing1 = m.sideHit;
               float minX = (float)(hitVec.xCoord - mc.getRenderManager().getRenderPosX());
               float maxX = (float)(hitVec.xCoord - mc.getRenderManager().getRenderPosX());
               float minY = (float)(hitVec.yCoord - mc.getRenderManager().getRenderPosY());
               float maxY = (float)(hitVec.yCoord - mc.getRenderManager().getRenderPosY());
               float minZ = (float)(hitVec.zCoord - mc.getRenderManager().getRenderPosZ());
               float maxZ = (float)(hitVec.zCoord - mc.getRenderManager().getRenderPosZ());
               if (enumFacing1 == EnumFacing.SOUTH) {
                  minX = (float)((double)minX - 0.4);
                  maxX = (float)((double)maxX + 0.4);
                  minY = (float)((double)minY - 0.4);
                  maxY = (float)((double)maxY + 0.4);
                  maxZ = (float)((double)maxZ + 0.02);
                  minZ = (float)((double)minZ + 0.05);
               } else if (enumFacing1 == EnumFacing.NORTH) {
                  minX = (float)((double)minX - 0.4);
                  maxX = (float)((double)maxX + 0.4);
                  minY = (float)((double)minY - 0.4);
                  maxY = (float)((double)maxY + 0.4);
                  maxZ = (float)((double)maxZ - 0.02);
                  minZ = (float)((double)minZ - 0.05);
               } else if (enumFacing1 == EnumFacing.EAST) {
                  maxX = (float)((double)maxX + 0.02);
                  minX = (float)((double)minX + 0.05);
                  minY = (float)((double)minY - 0.4);
                  maxY = (float)((double)maxY + 0.4);
                  minZ = (float)((double)minZ - 0.4);
                  maxZ = (float)((double)maxZ + 0.4);
               } else if (enumFacing1 == EnumFacing.WEST) {
                  maxX = (float)((double)maxX - 0.02);
                  minX = (float)((double)minX - 0.05);
                  minY = (float)((double)minY - 0.4);
                  maxY = (float)((double)maxY + 0.4);
                  minZ = (float)((double)minZ - 0.4);
                  maxZ = (float)((double)maxZ + 0.4);
               } else if (enumFacing1 == EnumFacing.UP) {
                  minX = (float)((double)minX - 0.4);
                  maxX = (float)((double)maxX + 0.4);
                  maxY = (float)((double)maxY + 0.02);
                  minY = (float)((double)minY + 0.05);
                  minZ = (float)((double)minZ - 0.4);
                  maxZ = (float)((double)maxZ + 0.4);
               } else if (enumFacing1 == EnumFacing.DOWN) {
                  minX = (float)((double)minX - 0.4);
                  maxX = (float)((double)maxX + 0.4);
                  maxY = (float)((double)maxY - 0.02);
                  minY = (float)((double)minY - 0.05);
                  minZ = (float)((double)minZ - 0.4);
                  maxZ = (float)((double)maxZ + 0.4);
               }

               worldrenderer.begin(7, DefaultVertexFormats.POSITION);
               worldrenderer.pos(minX, minY, minZ).endVertex();
               worldrenderer.pos(minX, minY, maxZ).endVertex();
               worldrenderer.pos(minX, maxY, maxZ).endVertex();
               worldrenderer.pos(minX, maxY, minZ).endVertex();
               worldrenderer.pos(minX, minY, maxZ).endVertex();
               worldrenderer.pos(maxX, minY, maxZ).endVertex();
               worldrenderer.pos(maxX, maxY, maxZ).endVertex();
               worldrenderer.pos(minX, maxY, maxZ).endVertex();
               worldrenderer.pos(maxX, minY, maxZ).endVertex();
               worldrenderer.pos(maxX, minY, minZ).endVertex();
               worldrenderer.pos(maxX, maxY, minZ).endVertex();
               worldrenderer.pos(maxX, maxY, maxZ).endVertex();
               worldrenderer.pos(maxX, minY, minZ).endVertex();
               worldrenderer.pos(minX, minY, minZ).endVertex();
               worldrenderer.pos(minX, maxY, minZ).endVertex();
               worldrenderer.pos(maxX, maxY, minZ).endVertex();
               worldrenderer.pos(minX, minY, minZ).endVertex();
               worldrenderer.pos(minX, minY, maxZ).endVertex();
               worldrenderer.pos(maxX, minY, maxZ).endVertex();
               worldrenderer.pos(maxX, minY, minZ).endVertex();
               worldrenderer.pos(minX, maxY, minZ).endVertex();
               worldrenderer.pos(minX, maxY, maxZ).endVertex();
               worldrenderer.pos(maxX, maxY, maxZ).endVertex();
               worldrenderer.pos(maxX, maxY, minZ).endVertex();
               worldrenderer.endVertex();
               tessellator.draw();
               GL11.glLineWidth(2.0F);
               worldrenderer.begin(3, DefaultVertexFormats.POSITION);
               worldrenderer.pos(minX, minY, minZ).endVertex();
               worldrenderer.pos(minX, minY, maxZ).endVertex();
               worldrenderer.pos(minX, maxY, maxZ).endVertex();
               worldrenderer.pos(minX, maxY, minZ).endVertex();
               worldrenderer.pos(minX, minY, minZ).endVertex();
               worldrenderer.pos(maxX, minY, minZ).endVertex();
               worldrenderer.pos(maxX, maxY, minZ).endVertex();
               worldrenderer.pos(maxX, maxY, maxZ).endVertex();
               worldrenderer.pos(maxX, minY, maxZ).endVertex();
               worldrenderer.pos(maxX, minY, minZ).endVertex();
               worldrenderer.pos(maxX, minY, maxZ).endVertex();
               worldrenderer.pos(minX, minY, maxZ).endVertex();
               worldrenderer.pos(minX, maxY, maxZ).endVertex();
               worldrenderer.pos(maxX, maxY, maxZ).endVertex();
               worldrenderer.pos(maxX, maxY, minZ).endVertex();
               worldrenderer.pos(minX, maxY, minZ).endVertex();
               worldrenderer.endVertex();
               tessellator.draw();
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
   }
}
