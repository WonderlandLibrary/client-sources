package net.optifine;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import net.acE;
import net.minecraft.block.Block;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.optifine.Config;
import net.optifine.DynamicLight;
import net.optifine.IntegerCache;
import net.optifine.MatchBlock;

public class DynamicLights {
   private static Map mapDynamicLights = new HashMap();
   private static long timeUpdateMs = 0L;
   private static final double MAX_DIST = 7.5D;
   private static final double MAX_DIST_SQ = 56.25D;
   private static final int LIGHT_LEVEL_MAX = 15;
   private static final int LIGHT_LEVEL_FIRE = 15;
   private static final int LIGHT_LEVEL_BLAZE = 10;
   private static final int e = 8;
   private static final int LIGHT_LEVEL_MAGMA_CUBE_CORE = 13;
   private static final int a = 8;
   private static final int g = 8;

   public static void entityAdded(Entity var0, RenderGlobal var1) {
   }

   public static void entityRemoved(Entity param0, RenderGlobal param1) {
      // $FF: Couldn't be decompiled
   }

   public static void update(RenderGlobal param0) {
      // $FF: Couldn't be decompiled
   }

   private static void updateMapDynamicLights(RenderGlobal var0) {
      MatchBlock.b();
      WorldClient var2 = var0.getWorld();
      if(var2 != null) {
         Iterator var3 = var2.getLoadedEntityList().iterator();
         if(var3.hasNext()) {
            Entity var4 = (Entity)var3.next();
            int var5 = getLightLevel(var4);
            if(var5 > 0) {
               Integer var6 = IntegerCache.valueOf(var4.getEntityID());
               DynamicLight var7 = (DynamicLight)mapDynamicLights.get(var6);
               if(var7 == null) {
                  var7 = new DynamicLight(var4);
                  mapDynamicLights.put(var6, var7);
               }
            }

            Integer var8 = IntegerCache.valueOf(var4.getEntityID());
            DynamicLight var10 = (DynamicLight)mapDynamicLights.remove(var8);
            if(var10 != null) {
               var10.updateLitChunks(var0);
            }
         }
      }

   }

   public static int getCombinedLight(BlockPos var0, int var1) {
      double var2 = getLightLevel(var0);
      var1 = getCombinedLight(var2, var1);
      return var1;
   }

   public static int getCombinedLight(Entity var0, int var1) {
      double var2 = (double)getLightLevel(var0);
      var1 = getCombinedLight(var2, var1);
      return var1;
   }

   public static int getCombinedLight(double var0, int var2) {
      acE[] var3 = MatchBlock.b();
      if(var0 > 0.0D) {
         int var4 = (int)(var0 * 16.0D);
         int var5 = var2 & 255;
         if(var4 > var5) {
            var2 = var2 & -256;
            var2 = var2 | var4;
         }
      }

      return var2;
   }

   public static double getLightLevel(BlockPos param0) {
      // $FF: Couldn't be decompiled
   }

   public static int getLightLevel(ItemStack var0) {
      acE[] var1 = MatchBlock.b();
      if(var0 == null) {
         return 0;
      } else {
         Item var2 = var0.getItem();
         if(var2 instanceof ItemBlock) {
            ItemBlock var3 = (ItemBlock)var2;
            Block var4 = var3.getBlock();
            return var4.getLightValue();
         } else {
            return var2 == Items.lava_bucket?Blocks.lava.getLightValue():(var2 != Items.blaze_rod && var2 != Items.blaze_powder?(var2 == Items.glowstone_dust?8:(var2 == Items.prismarine_crystals?8:(var2 == Items.magma_cream?8:(var2 == Items.nether_star?Blocks.beacon.getLightValue() / 2:0)))):10);
         }
      }
   }

   public static int getLightLevel(Entity var0) {
      acE[] var1 = MatchBlock.b();
      if(var0 == Config.getMinecraft().getRenderViewEntity() && !Config.isDynamicHandLight()) {
         return 0;
      } else {
         if(var0 instanceof EntityPlayer) {
            EntityPlayer var2 = (EntityPlayer)var0;
            if(var2.isSpectator()) {
               return 0;
            }
         }

         if(var0.isBurning()) {
            return 15;
         } else if(var0 instanceof EntityFireball) {
            return 15;
         } else if(var0 instanceof EntityTNTPrimed) {
            return 15;
         } else if(var0 instanceof EntityBlaze) {
            EntityBlaze var11 = (EntityBlaze)var0;
            return var11.func_70845_n()?15:10;
         } else if(var0 instanceof EntityMagmaCube) {
            EntityMagmaCube var10 = (EntityMagmaCube)var0;
            return (double)var10.squishFactor > 0.6D?13:8;
         } else {
            if(var0 instanceof EntityCreeper) {
               EntityCreeper var7 = (EntityCreeper)var0;
               if((double)var7.getCreeperFlashIntensity(0.0F) > 0.001D) {
                  return 15;
               }
            }

            if(var0 instanceof EntityLivingBase) {
               EntityLivingBase var9 = (EntityLivingBase)var0;
               ItemStack var12 = var9.getHeldItem();
               int var4 = getLightLevel(var12);
               ItemStack var5 = var9.getEquipmentInSlot(4);
               int var6 = getLightLevel(var5);
               return Math.max(var4, var6);
            } else if(var0 instanceof EntityItem) {
               EntityItem var8 = (EntityItem)var0;
               ItemStack var3 = getItemStack(var8);
               return getLightLevel(var3);
            } else {
               return 0;
            }
         }
      }
   }

   public static void removeLights(RenderGlobal param0) {
      // $FF: Couldn't be decompiled
   }

   public static void clear() {
      // $FF: Couldn't be decompiled
   }

   public static int getCount() {
      // $FF: Couldn't be decompiled
   }

   public static ItemStack getItemStack(EntityItem var0) {
      ItemStack var1 = var0.k().d(10);
      return var1;
   }
}
