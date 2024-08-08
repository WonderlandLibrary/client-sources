package com.example.editme.util.client;

import java.util.Iterator;
import net.minecraft.block.BlockLiquid;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.passive.EntityAmbientCreature;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class EntityUtil {
   public static boolean isInWater(Entity var0) {
      if (var0 == null) {
         return false;
      } else {
         double var1 = var0.field_70163_u + 0.01D;

         for(int var3 = MathHelper.func_76128_c(var0.field_70165_t); var3 < MathHelper.func_76143_f(var0.field_70165_t); ++var3) {
            for(int var4 = MathHelper.func_76128_c(var0.field_70161_v); var4 < MathHelper.func_76143_f(var0.field_70161_v); ++var4) {
               BlockPos var5 = new BlockPos(var3, (int)var1, var4);
               if (Wrapper.getWorld().func_180495_p(var5).func_177230_c() instanceof BlockLiquid) {
                  return true;
               }
            }
         }

         return false;
      }
   }

   public static boolean isMobAggressive(Entity var0) {
      if (var0 instanceof EntityPigZombie) {
         if (((EntityPigZombie)var0).func_184734_db() || ((EntityPigZombie)var0).func_175457_ck()) {
            return true;
         }
      } else {
         if (var0 instanceof EntityWolf) {
            return ((EntityWolf)var0).func_70919_bu() && !Wrapper.getPlayer().equals(((EntityWolf)var0).func_70902_q());
         }

         if (var0 instanceof EntityEnderman) {
            return ((EntityEnderman)var0).func_70823_r();
         }
      }

      return isHostileMob(var0);
   }

   public static double getRelativeX(float var0) {
      return (double)MathHelper.func_76126_a(-var0 * 0.017453292F);
   }

   public static double[] calculateLookAt(double var0, double var2, double var4, EntityPlayer var6) {
      double var7 = var6.field_70165_t - var0;
      double var9 = var6.field_70163_u - var2;
      double var11 = var6.field_70161_v - var4;
      double var13 = Math.sqrt(var7 * var7 + var9 * var9 + var11 * var11);
      var7 /= var13;
      var9 /= var13;
      var11 /= var13;
      double var15 = Math.asin(var9);
      double var17 = Math.atan2(var11, var7);
      var15 = var15 * 180.0D / 3.141592653589793D;
      var17 = var17 * 180.0D / 3.141592653589793D;
      var17 += 90.0D;
      return new double[]{var17, var15};
   }

   public static boolean isFakeLocalPlayer(Entity var0) {
      return var0 != null && var0.func_145782_y() == -100 && Wrapper.getPlayer() != var0;
   }

   public static Vec3d getInterpolatedRenderPos(Entity var0, float var1) {
      return getInterpolatedPos(var0, var1).func_178786_a(Wrapper.getMinecraft().func_175598_ae().field_78725_b, Wrapper.getMinecraft().func_175598_ae().field_78726_c, Wrapper.getMinecraft().func_175598_ae().field_78723_d);
   }

   public static Vec3d getInterpolatedAmount(Entity var0, double var1) {
      return getInterpolatedAmount(var0, var1, var1, var1);
   }

   public static Vec3d getInterpolatedPos(Entity var0, float var1) {
      return (new Vec3d(var0.field_70142_S, var0.field_70137_T, var0.field_70136_U)).func_178787_e(getInterpolatedAmount(var0, (double)var1));
   }

   public static Vec3d getInterpolatedAmount(Entity var0, double var1, double var3, double var5) {
      return new Vec3d((var0.field_70165_t - var0.field_70142_S) * var1, (var0.field_70163_u - var0.field_70137_T) * var3, (var0.field_70161_v - var0.field_70136_U) * var5);
   }

   public static Vec3d getInterpolatedAmount(Entity var0, Vec3d var1) {
      return getInterpolatedAmount(var0, var1.field_72450_a, var1.field_72448_b, var1.field_72449_c);
   }

   public static boolean isLiving(Entity var0) {
      return var0 instanceof EntityLivingBase;
   }

   public static boolean isDrivenByPlayer(Entity var0) {
      return Wrapper.getPlayer() != null && var0 != null && var0.equals(Wrapper.getPlayer().func_184187_bx());
   }

   public static boolean isAboveWater(Entity var0, boolean var1) {
      if (var0 == null) {
         return false;
      } else {
         double var2 = var0.field_70163_u - (var1 ? 0.03D : (isPlayer(var0) ? 0.2D : 0.5D));

         for(int var4 = MathHelper.func_76128_c(var0.field_70165_t); var4 < MathHelper.func_76143_f(var0.field_70165_t); ++var4) {
            for(int var5 = MathHelper.func_76128_c(var0.field_70161_v); var5 < MathHelper.func_76143_f(var0.field_70161_v); ++var5) {
               BlockPos var6 = new BlockPos(var4, MathHelper.func_76128_c(var2), var5);
               if (Wrapper.getWorld().func_180495_p(var6).func_177230_c() instanceof BlockLiquid) {
                  return true;
               }
            }
         }

         return false;
      }
   }

   public static double GetDistance(double var0, double var2, double var4, double var6, double var8, double var10) {
      double var12 = var0 - var6;
      double var14 = var2 - var8;
      double var16 = var4 - var10;
      return (double)MathHelper.func_76133_a(var12 * var12 + var14 * var14 + var16 * var16);
   }

   public static Entity getClosestEntity() {
      Entity var0 = null;
      Iterator var1 = Minecraft.func_71410_x().field_71441_e.field_72996_f.iterator();

      while(true) {
         Entity var2;
         do {
            if (!var1.hasNext()) {
               return var0;
            }

            var2 = (Entity)var1.next();
         } while(var0 != null && Minecraft.func_71410_x().field_71439_g.func_70032_d(var2) >= Minecraft.func_71410_x().field_71439_g.func_70032_d(var0));

         var0 = var2;
      }
   }

   public static double GetDistanceOfEntityToBlock(Entity var0, BlockPos var1) {
      return GetDistance(var0.field_70165_t, var0.field_70163_u, var0.field_70161_v, (double)var1.func_177958_n(), (double)var1.func_177956_o(), (double)var1.func_177952_p());
   }

   public static boolean isPlayer(Entity var0) {
      return var0 instanceof EntityPlayer;
   }

   public static boolean isFriendlyMob(Entity var0) {
      return var0.isCreatureType(EnumCreatureType.CREATURE, false) && !isNeutralMob(var0) || var0.isCreatureType(EnumCreatureType.AMBIENT, false) || var0 instanceof EntityVillager || var0 instanceof EntityIronGolem || isNeutralMob(var0) && !isMobAggressive(var0);
   }

   public static double getRelativeZ(float var0) {
      return (double)MathHelper.func_76134_b(var0 * 0.017453292F);
   }

   public static boolean isAboveWater(Entity var0) {
      return isAboveWater(var0, false);
   }

   public static boolean isNeutralMob(Entity var0) {
      return var0 instanceof EntityPigZombie || var0 instanceof EntityWolf || var0 instanceof EntityEnderman;
   }

   public static boolean isPassive(Entity var0) {
      if (var0 instanceof EntityWolf && ((EntityWolf)var0).func_70919_bu()) {
         return false;
      } else if (!(var0 instanceof EntityAnimal) && !(var0 instanceof EntityAgeable) && !(var0 instanceof EntityTameable) && !(var0 instanceof EntityAmbientCreature) && !(var0 instanceof EntitySquid)) {
         return var0 instanceof EntityIronGolem && ((EntityIronGolem)var0).func_70643_av() == null;
      } else {
         return true;
      }
   }

   public static boolean isHostileMob(Entity var0) {
      return var0.isCreatureType(EnumCreatureType.MONSTER, false) && !isNeutralMob(var0);
   }
}
