package cc.slack.features.modules.impl.render;

import cc.slack.events.impl.render.RenderEvent;
import cc.slack.features.modules.api.Category;
import cc.slack.features.modules.api.Module;
import cc.slack.features.modules.api.ModuleInfo;
import cc.slack.features.modules.api.settings.Value;
import cc.slack.features.modules.api.settings.impl.NumberValue;
import cc.slack.utils.client.mc;
import io.github.nevalackin.radbus.Listen;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
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

@ModuleInfo(
   name = "Projectiles",
   category = Category.RENDER
)
public class Projectiles extends Module {
   private NumberValue<Float> color = new NumberValue("Color (H/S/B)", 190.0F, 0.0F, 350.0F, 10.0F);
   private final ArrayList<Vec3> positions = new ArrayList();

   public Projectiles() {
      this.addSettings(new Value[]{this.color});
   }

   public void onDisable() {
      super.onDisable();
      this.positions.clear();
   }

   @Listen
   public void onRender(RenderEvent e) {
      if (e.state == RenderEvent.State.RENDER_3D) {
         this.positions.clear();
         ItemStack itemStack = mc.getPlayer().getCurrentEquippedItem();
         MovingObjectPosition m = null;
         if (itemStack != null && (itemStack.getItem() instanceof ItemSnowball || itemStack.getItem() instanceof ItemEgg || itemStack.getItem() instanceof ItemBow || itemStack.getItem() instanceof ItemEnderPearl)) {
            EntityLivingBase thrower = mc.getPlayer();
            float rotationYaw = thrower.prevRotationYaw + (thrower.rotationYaw - thrower.prevRotationYaw) * mc.getTimer().renderPartialTicks;
            float rotationPitch = thrower.prevRotationPitch + (thrower.rotationPitch - thrower.prevRotationPitch) * mc.getTimer().renderPartialTicks;
            double posX = thrower.lastTickPosX + (thrower.posX - thrower.lastTickPosX) * (double)mc.getTimer().renderPartialTicks;
            double posY = thrower.lastTickPosY + (double)thrower.getEyeHeight() + (thrower.posY - thrower.lastTickPosY) * (double)mc.getTimer().renderPartialTicks;
            double posZ = thrower.lastTickPosZ + (thrower.posZ - thrower.lastTickPosZ) * (double)mc.getTimer().renderPartialTicks;
            posX -= (double)(MathHelper.cos(rotationYaw / 180.0F * 3.1415927F) * 0.16F);
            posY -= 0.10000000149011612D;
            posZ -= (double)(MathHelper.sin(rotationYaw / 180.0F * 3.1415927F) * 0.16F);
            float multipicator = 0.4F;
            if (itemStack.getItem() instanceof ItemBow) {
               multipicator = 1.0F;
            }

            double motionX = (double)(-MathHelper.sin(rotationYaw / 180.0F * 3.1415927F) * MathHelper.cos(rotationPitch / 180.0F * 3.1415927F) * multipicator);
            double motionZ = (double)(MathHelper.cos(rotationYaw / 180.0F * 3.1415927F) * MathHelper.cos(rotationPitch / 180.0F * 3.1415927F) * multipicator);
            double motionY = (double)(-MathHelper.sin(rotationPitch / 180.0F * 3.1415927F) * multipicator);
            float inaccuracy = 0.0F;
            float velocity = 1.5F;
            float f;
            if (itemStack.getItem() instanceof ItemBow) {
               int i = mc.getPlayer().getItemInUseDuration() - mc.getPlayer().getItemInUseCount();
               f = (float)i / 20.0F;
               f = (f * f + f * 2.0F) / 3.0F;
               if ((double)f < 0.1D) {
                  return;
               }

               if (f > 1.0F) {
                  f = 1.0F;
               }

               velocity = f * 2.0F * 1.5F;
            }

            Random rand = new Random();
            f = MathHelper.sqrt_double(motionX * motionX + motionY * motionY + motionZ * motionZ);
            double x = motionX / (double)f;
            double y = motionY / (double)f;
            double z = motionZ / (double)f;
            x += rand.nextGaussian() * 0.007499999832361937D * 0.0D;
            y += rand.nextGaussian() * 0.007499999832361937D * 0.0D;
            z += rand.nextGaussian() * 0.007499999832361937D * 0.0D;
            x *= (double)velocity;
            y *= (double)velocity;
            z *= (double)velocity;
            motionX = x;
            motionY = y;
            motionZ = z;
            float prevRotationYaw = (float)(MathHelper.func_181159_b(x, z) * 180.0D / 3.141592653589793D);
            float prevRotationPitch = (float)(MathHelper.func_181159_b(y, (double)MathHelper.sqrt_double(x * x + z * z)) * 180.0D / 3.141592653589793D);
            boolean b = true;
            int ticksInAir = 0;

            Iterator var37;
            float minX;
            while(b) {
               if (ticksInAir > 300) {
                  b = false;
               }

               ++ticksInAir;
               Vec3 vec3 = new Vec3(posX, posY, posZ);
               Vec3 vec4 = new Vec3(posX + motionX, posY + motionY, posZ + motionZ);
               MovingObjectPosition movingobjectposition = mc.getWorld().rayTraceBlocks(vec3, vec4);
               vec3 = new Vec3(posX, posY, posZ);
               vec4 = new Vec3(posX + motionX, posY + motionY, posZ + motionZ);
               if (movingobjectposition != null) {
                  vec4 = new Vec3(movingobjectposition.hitVec.xCoord, movingobjectposition.hitVec.yCoord, movingobjectposition.hitVec.zCoord);
               }

               var37 = mc.getWorld().loadedEntityList.iterator();

               while(var37.hasNext()) {
                  Entity entity = (Entity)var37.next();
                  if (entity != mc.getPlayer() && entity instanceof EntityLivingBase) {
                     minX = 0.3F;
                     AxisAlignedBB localAxisAlignedBB = entity.getEntityBoundingBox().expand(0.30000001192092896D, 0.30000001192092896D, 0.30000001192092896D);
                     MovingObjectPosition localMovingObjectPosition = localAxisAlignedBB.calculateIntercept(vec3, vec4);
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
               float f3 = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
               rotationYaw = (float)(MathHelper.func_181159_b(motionX, motionZ) * 180.0D / 3.141592653589793D);

               for(rotationPitch = (float)(MathHelper.func_181159_b(motionY, (double)f3) * 180.0D / 3.141592653589793D); rotationPitch - prevRotationPitch < -180.0F; prevRotationPitch -= 360.0F) {
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

               float f4 = 0.99F;
               minX = 0.03F;
               if (itemStack.getItem() instanceof ItemBow) {
                  minX = 0.05F;
               }

               motionX *= 0.9900000095367432D;
               motionY *= 0.9900000095367432D;
               motionZ *= 0.9900000095367432D;
               motionY -= (double)minX;
               this.positions.add(new Vec3(posX, posY, posZ));
            }

            if (this.positions.size() > 1) {
               Color col = Color.getHSBColor((Float)this.color.getValue() % 360.0F / 360.0F, 1.0F, 1.0F);
               GL11.glEnable(3042);
               GL11.glBlendFunc(770, 771);
               GL11.glEnable(2848);
               GL11.glDisable(3553);
               GlStateManager.disableCull();
               GL11.glDepthMask(false);
               GL11.glColor4f((float)col.getRed() / 255.0F, (float)col.getGreen() / 255.0F, (float)col.getBlue() / 255.0F, 0.7F);
               GL11.glLineWidth(3.0F);
               Tessellator tessellator = Tessellator.getInstance();
               WorldRenderer worldrenderer = tessellator.getWorldRenderer();
               GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
               worldrenderer.begin(3, DefaultVertexFormats.field_181705_e);
               var37 = this.positions.iterator();

               while(var37.hasNext()) {
                  Vec3 vec5 = (Vec3)var37.next();
                  worldrenderer.pos((double)((float)vec5.xCoord) - mc.getRenderManager().renderPosX, (double)((float)vec5.yCoord) - mc.getRenderManager().renderPosY, (double)((float)vec5.zCoord) - mc.getRenderManager().renderPosZ).endVertex();
               }

               tessellator.draw();
               if (m != null) {
                  GL11.glColor4f((float)col.getRed() / 255.0F, (float)col.getGreen() / 255.0F, (float)col.getBlue() / 255.0F, 0.3F);
                  Vec3 hitVec = m.hitVec;
                  EnumFacing enumFacing1 = m.sideHit;
                  minX = (float)(hitVec.xCoord - mc.getRenderManager().renderPosX);
                  float maxX = (float)(hitVec.xCoord - mc.getRenderManager().renderPosX);
                  float minY = (float)(hitVec.yCoord - mc.getRenderManager().renderPosY);
                  float maxY = (float)(hitVec.yCoord - mc.getRenderManager().renderPosY);
                  float minZ = (float)(hitVec.zCoord - mc.getRenderManager().renderPosZ);
                  float maxZ = (float)(hitVec.zCoord - mc.getRenderManager().renderPosZ);
                  if (enumFacing1 == EnumFacing.SOUTH) {
                     minX -= 0.4F;
                     maxX += 0.4F;
                     minY -= 0.4F;
                     maxY += 0.4F;
                     maxZ += 0.02F;
                     minZ += 0.05F;
                  } else if (enumFacing1 == EnumFacing.NORTH) {
                     minX -= 0.4F;
                     maxX += 0.4F;
                     minY -= 0.4F;
                     maxY += 0.4F;
                     maxZ -= 0.02F;
                     minZ -= 0.05F;
                  } else if (enumFacing1 == EnumFacing.EAST) {
                     maxX += 0.02F;
                     minX += 0.05F;
                     minY -= 0.4F;
                     maxY += 0.4F;
                     minZ -= 0.4F;
                     maxZ += 0.4F;
                  } else if (enumFacing1 == EnumFacing.WEST) {
                     maxX -= 0.02F;
                     minX -= 0.05F;
                     minY -= 0.4F;
                     maxY += 0.4F;
                     minZ -= 0.4F;
                     maxZ += 0.4F;
                  } else if (enumFacing1 == EnumFacing.UP) {
                     minX -= 0.4F;
                     maxX += 0.4F;
                     maxY += 0.02F;
                     minY += 0.05F;
                     minZ -= 0.4F;
                     maxZ += 0.4F;
                  } else if (enumFacing1 == EnumFacing.DOWN) {
                     minX -= 0.4F;
                     maxX += 0.4F;
                     maxY -= 0.02F;
                     minY -= 0.05F;
                     minZ -= 0.4F;
                     maxZ += 0.4F;
                  }

                  worldrenderer.begin(7, DefaultVertexFormats.field_181705_e);
                  worldrenderer.pos((double)minX, (double)minY, (double)minZ).endVertex();
                  worldrenderer.pos((double)minX, (double)minY, (double)maxZ).endVertex();
                  worldrenderer.pos((double)minX, (double)maxY, (double)maxZ).endVertex();
                  worldrenderer.pos((double)minX, (double)maxY, (double)minZ).endVertex();
                  worldrenderer.pos((double)minX, (double)minY, (double)maxZ).endVertex();
                  worldrenderer.pos((double)maxX, (double)minY, (double)maxZ).endVertex();
                  worldrenderer.pos((double)maxX, (double)maxY, (double)maxZ).endVertex();
                  worldrenderer.pos((double)minX, (double)maxY, (double)maxZ).endVertex();
                  worldrenderer.pos((double)maxX, (double)minY, (double)maxZ).endVertex();
                  worldrenderer.pos((double)maxX, (double)minY, (double)minZ).endVertex();
                  worldrenderer.pos((double)maxX, (double)maxY, (double)minZ).endVertex();
                  worldrenderer.pos((double)maxX, (double)maxY, (double)maxZ).endVertex();
                  worldrenderer.pos((double)maxX, (double)minY, (double)minZ).endVertex();
                  worldrenderer.pos((double)minX, (double)minY, (double)minZ).endVertex();
                  worldrenderer.pos((double)minX, (double)maxY, (double)minZ).endVertex();
                  worldrenderer.pos((double)maxX, (double)maxY, (double)minZ).endVertex();
                  worldrenderer.pos((double)minX, (double)minY, (double)minZ).endVertex();
                  worldrenderer.pos((double)minX, (double)minY, (double)maxZ).endVertex();
                  worldrenderer.pos((double)maxX, (double)minY, (double)maxZ).endVertex();
                  worldrenderer.pos((double)maxX, (double)minY, (double)minZ).endVertex();
                  worldrenderer.pos((double)minX, (double)maxY, (double)minZ).endVertex();
                  worldrenderer.pos((double)minX, (double)maxY, (double)maxZ).endVertex();
                  worldrenderer.pos((double)maxX, (double)maxY, (double)maxZ).endVertex();
                  worldrenderer.pos((double)maxX, (double)maxY, (double)minZ).endVertex();
                  worldrenderer.endVertex();
                  tessellator.draw();
                  GL11.glLineWidth(2.0F);
                  worldrenderer.begin(3, DefaultVertexFormats.field_181705_e);
                  worldrenderer.pos((double)minX, (double)minY, (double)minZ).endVertex();
                  worldrenderer.pos((double)minX, (double)minY, (double)maxZ).endVertex();
                  worldrenderer.pos((double)minX, (double)maxY, (double)maxZ).endVertex();
                  worldrenderer.pos((double)minX, (double)maxY, (double)minZ).endVertex();
                  worldrenderer.pos((double)minX, (double)minY, (double)minZ).endVertex();
                  worldrenderer.pos((double)maxX, (double)minY, (double)minZ).endVertex();
                  worldrenderer.pos((double)maxX, (double)maxY, (double)minZ).endVertex();
                  worldrenderer.pos((double)maxX, (double)maxY, (double)maxZ).endVertex();
                  worldrenderer.pos((double)maxX, (double)minY, (double)maxZ).endVertex();
                  worldrenderer.pos((double)maxX, (double)minY, (double)minZ).endVertex();
                  worldrenderer.pos((double)maxX, (double)minY, (double)maxZ).endVertex();
                  worldrenderer.pos((double)minX, (double)minY, (double)maxZ).endVertex();
                  worldrenderer.pos((double)minX, (double)maxY, (double)maxZ).endVertex();
                  worldrenderer.pos((double)maxX, (double)maxY, (double)maxZ).endVertex();
                  worldrenderer.pos((double)maxX, (double)maxY, (double)minZ).endVertex();
                  worldrenderer.pos((double)minX, (double)maxY, (double)minZ).endVertex();
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
}
