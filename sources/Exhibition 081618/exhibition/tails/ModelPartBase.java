package exhibition.tails;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;

public abstract class ModelPartBase extends ModelBase {
   public static final float SCALE = 0.0625F;

   public abstract void render(EntityLivingBase var1, int var2, float var3);

   protected void setRotationRadians(ModelRenderer model, double x, double y, double z) {
      model.rotateAngleX = (float)x;
      model.rotateAngleY = (float)y;
      model.rotateAngleZ = (float)z;
   }

   protected void setRotationDegrees(ModelRenderer model, float x, float y, float z) {
      this.setRotationRadians(model, (double)((float)Math.toRadians((double)x)), (double)((float)Math.toRadians((double)y)), (double)((float)Math.toRadians((double)z)));
   }

   public static float getAnimationTime(double cycleTime, Entity entity) {
      return (float)((double)((long)entity.hashCode() + System.currentTimeMillis()) % cycleTime / cycleTime * 2.0D * 3.141592653589793D);
   }

   protected double[] getMotionAngles(EntityPlayer player, double partialTicks) {
      double xMotion = player.field_71091_bM + (player.field_71094_bP - player.field_71091_bM) * partialTicks - (player.prevPosX + (player.posX - player.prevPosX) * partialTicks);
      double yMotion = player.field_71096_bN + (player.field_71095_bQ - player.field_71096_bN) * partialTicks - (player.prevPosY + (player.posY - player.prevPosY) * partialTicks);
      double zMotion = player.field_71097_bO + (player.field_71085_bR - player.field_71097_bO) * partialTicks - (player.prevPosZ + (player.posZ - player.prevPosZ) * partialTicks);
      float bodyYaw = player.prevRotationYaw + (player.rotationYaw - player.prevRotationYaw) * (float)partialTicks;
      double bodyYawSin = Math.sin((double)(bodyYaw * 3.1415927F / 180.0F));
      double bodyYawCos = -Math.cos((double)(bodyYaw * 3.1415927F / 180.0F));
      float xOffset = MathHelper.clamp_float((float)yMotion * 10.0F, -6.0F, 32.0F);
      float f1 = (float)(xMotion * bodyYawSin + zMotion * bodyYawCos) * 100.0F;
      float f2 = (float)(xMotion * bodyYawCos - zMotion * bodyYawSin) * 100.0F;
      if (f1 < 0.0F) {
         f1 = 0.0F;
      }

      return new double[]{Math.toRadians((double)(f1 / 2.5F + xOffset + this.getTailBob(player, (float)partialTicks))), Math.toRadians((double)(-f2 / 20.0F)), Math.toRadians((double)(f2 / 2.0F))};
   }

   protected float getTailBob(EntityPlayer player, float partialTicks) {
      float cameraYaw = player.prevCameraYaw + (player.cameraYaw - player.prevCameraYaw) * partialTicks;
      return MathHelper.sin((player.prevDistanceWalkedModified + (player.distanceWalkedModified - player.prevDistanceWalkedModified) * partialTicks) * 6.0F) * 12.0F * cameraYaw;
   }
}
