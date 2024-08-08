package com.example.editme.util.client;

import java.util.Iterator;
import java.util.List;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemEgg;
import net.minecraft.item.ItemEnderPearl;
import net.minecraft.item.ItemExpBottle;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemSnowball;
import net.minecraft.item.ItemSplashPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;

public class TrajectoryCalculator {
   public static TrajectoryCalculator.ThrowingType getThrowType(EntityLivingBase var0) {
      if (var0.func_184586_b(EnumHand.MAIN_HAND).func_190926_b()) {
         return TrajectoryCalculator.ThrowingType.NONE;
      } else {
         ItemStack var1 = var0.func_184586_b(EnumHand.MAIN_HAND);
         Item var2 = var1.func_77973_b();
         if (var2 instanceof ItemPotion) {
            if (var1.func_77973_b() instanceof ItemSplashPotion) {
               return TrajectoryCalculator.ThrowingType.POTION;
            }
         } else {
            if (var2 instanceof ItemBow && var0.func_184587_cr()) {
               return TrajectoryCalculator.ThrowingType.BOW;
            }

            if (var2 instanceof ItemExpBottle) {
               return TrajectoryCalculator.ThrowingType.EXPERIENCE;
            }

            if (var2 instanceof ItemSnowball || var2 instanceof ItemEgg || var2 instanceof ItemEnderPearl) {
               return TrajectoryCalculator.ThrowingType.NORMAL;
            }
         }

         return TrajectoryCalculator.ThrowingType.NONE;
      }
   }

   public static Vec3d div(Vec3d var0, float var1) {
      return new Vec3d(var0.field_72450_a / (double)var1, var0.field_72448_b / (double)var1, var0.field_72449_c / (double)var1);
   }

   public static Vec3d mult(Vec3d var0, float var1) {
      return new Vec3d(var0.field_72450_a * (double)var1, var0.field_72448_b * (double)var1, var0.field_72449_c * (double)var1);
   }

   public static double[] interpolate(Entity var0) {
      double var1 = interpolate(var0.field_70165_t, var0.field_70142_S) - Wrapper.getMinecraft().field_175616_W.field_78725_b;
      double var3 = interpolate(var0.field_70163_u, var0.field_70137_T) - Wrapper.getMinecraft().field_175616_W.field_78726_c;
      double var5 = interpolate(var0.field_70161_v, var0.field_70136_U) - Wrapper.getMinecraft().field_175616_W.field_78723_d;
      return new double[]{var1, var3, var5};
   }

   public static double interpolate(double var0, double var2) {
      return var2 + (var0 - var2) * (double)Wrapper.getMinecraft().func_184121_ak();
   }

   public static enum ThrowingType {
      BOW;

      private static final TrajectoryCalculator.ThrowingType[] $VALUES = new TrajectoryCalculator.ThrowingType[]{NONE, BOW, EXPERIENCE, POTION, NORMAL};
      NORMAL,
      NONE,
      POTION,
      EXPERIENCE;
   }

   public static final class FlightPath {
      private Vec3d motion;
      public Vec3d position;
      private AxisAlignedBB boundingBox;
      private RayTraceResult target;
      private float yaw;
      private float pitch;
      private EntityLivingBase shooter;
      private boolean collided;
      private TrajectoryCalculator.ThrowingType throwingType;

      public RayTraceResult getCollidingTarget() {
         return this.target;
      }

      private void setLocationAndAngles(double var1, double var3, double var5, float var7, float var8) {
         this.position = new Vec3d(var1, var3, var5);
         this.yaw = var7;
         this.pitch = var8;
      }

      private void setThrowableHeading(Vec3d var1, float var2) {
         this.motion = TrajectoryCalculator.div(var1, (float)var1.func_72433_c());
         this.motion = TrajectoryCalculator.mult(this.motion, var2);
      }

      public void onUpdate() {
         Vec3d var1 = this.position.func_178787_e(this.motion);
         RayTraceResult var2 = this.shooter.func_130014_f_().func_147447_a(this.position, var1, false, true, false);
         if (var2 != null) {
            var1 = var2.field_72307_f;
         }

         this.onCollideWithEntity(var1, var2);
         if (this.target != null) {
            this.collided = true;
            this.setPosition(this.target.field_72307_f);
         } else if (this.position.field_72448_b <= 0.0D) {
            this.collided = true;
         } else {
            this.position = this.position.func_178787_e(this.motion);
            float var3 = 0.99F;
            if (this.shooter.func_130014_f_().func_72875_a(this.boundingBox, Material.field_151586_h)) {
               var3 = this.throwingType == TrajectoryCalculator.ThrowingType.BOW ? 0.6F : 0.8F;
            }

            this.motion = TrajectoryCalculator.mult(this.motion, var3);
            this.motion = this.motion.func_178786_a(0.0D, (double)this.getGravityVelocity(), 0.0D);
            this.setPosition(this.position);
         }
      }

      public boolean isCollided() {
         return this.collided;
      }

      private float getInitialVelocity() {
         Item var1 = this.shooter.func_184586_b(EnumHand.MAIN_HAND).func_77973_b();
         switch(this.throwingType) {
         case BOW:
            ItemBow var2 = (ItemBow)var1;
            int var3 = var2.func_77626_a(this.shooter.func_184586_b(EnumHand.MAIN_HAND)) - this.shooter.func_184605_cv();
            float var4 = (float)var3 / 20.0F;
            var4 = (var4 * var4 + var4 * 2.0F) / 3.0F;
            if (var4 > 1.0F) {
               var4 = 1.0F;
            }

            return var4 * 2.0F * 1.5F;
         case POTION:
            return 0.5F;
         case EXPERIENCE:
            return 0.7F;
         case NORMAL:
            return 1.5F;
         default:
            return 1.5F;
         }
      }

      private void onCollideWithEntity(Vec3d var1, RayTraceResult var2) {
         Entity var3 = null;
         double var4 = 0.0D;
         List var6 = this.shooter.field_70170_p.func_72839_b(this.shooter, this.boundingBox.func_72321_a(this.motion.field_72450_a, this.motion.field_72448_b, this.motion.field_72449_c).func_72321_a(1.0D, 1.0D, 1.0D));
         Iterator var7 = var6.iterator();

         while(true) {
            Entity var8;
            double var12;
            do {
               RayTraceResult var11;
               do {
                  do {
                     if (!var7.hasNext()) {
                        if (var3 != null) {
                           this.target = new RayTraceResult(var3);
                        } else {
                           this.target = var2;
                        }

                        return;
                     }

                     var8 = (Entity)var7.next();
                  } while(!var8.func_70067_L() && var8 != this.shooter);

                  float var9 = var8.func_70111_Y();
                  AxisAlignedBB var10 = var8.func_174813_aQ().func_72321_a((double)var9, (double)var9, (double)var9);
                  var11 = var10.func_72327_a(this.position, var1);
               } while(var11 == null);

               var12 = this.position.func_72438_d(var11.field_72307_f);
            } while(var12 >= var4 && var4 != 0.0D);

            var3 = var8;
            var4 = var12;
         }
      }

      public FlightPath(EntityLivingBase var1, TrajectoryCalculator.ThrowingType var2) {
         this.shooter = var1;
         this.throwingType = var2;
         double[] var3 = TrajectoryCalculator.interpolate(this.shooter);
         this.setLocationAndAngles(var3[0] + Wrapper.getMinecraft().func_175598_ae().field_78725_b, var3[1] + (double)this.shooter.func_70047_e() + Wrapper.getMinecraft().func_175598_ae().field_78726_c, var3[2] + Wrapper.getMinecraft().func_175598_ae().field_78723_d, this.shooter.field_70177_z, this.shooter.field_70125_A);
         Vec3d var4 = new Vec3d((double)(MathHelper.func_76134_b(this.yaw / 180.0F * 3.1415927F) * 0.16F), 0.1D, (double)(MathHelper.func_76126_a(this.yaw / 180.0F * 3.1415927F) * 0.16F));
         this.position = this.position.func_178788_d(var4);
         this.setPosition(this.position);
         this.motion = new Vec3d((double)(-MathHelper.func_76126_a(this.yaw / 180.0F * 3.1415927F) * MathHelper.func_76134_b(this.pitch / 180.0F * 3.1415927F)), (double)(-MathHelper.func_76126_a(this.pitch / 180.0F * 3.1415927F)), (double)(MathHelper.func_76134_b(this.yaw / 180.0F * 3.1415927F) * MathHelper.func_76134_b(this.pitch / 180.0F * 3.1415927F)));
         this.setThrowableHeading(this.motion, this.getInitialVelocity());
      }

      private void setPosition(Vec3d var1) {
         this.position = new Vec3d(var1.field_72450_a, var1.field_72448_b, var1.field_72449_c);
         double var2 = (this.throwingType == TrajectoryCalculator.ThrowingType.BOW ? 0.5D : 0.25D) / 2.0D;
         this.boundingBox = new AxisAlignedBB(var1.field_72450_a - var2, var1.field_72448_b - var2, var1.field_72449_c - var2, var1.field_72450_a + var2, var1.field_72448_b + var2, var1.field_72449_c + var2);
      }

      private float getGravityVelocity() {
         switch(this.throwingType) {
         case BOW:
         case POTION:
            return 0.05F;
         case EXPERIENCE:
            return 0.07F;
         case NORMAL:
            return 0.03F;
         default:
            return 0.03F;
         }
      }
   }
}
