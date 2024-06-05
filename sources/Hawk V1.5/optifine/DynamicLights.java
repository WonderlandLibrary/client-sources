package optifine;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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

public class DynamicLights {
   private static final double MAX_DIST = 7.5D;
   private static long timeUpdateMs = 0L;
   private static final int LIGHT_LEVEL_FIRE = 15;
   private static final int LIGHT_LEVEL_GLOWSTONE_DUST = 8;
   private static final int LIGHT_LEVEL_PRISMARINE_CRYSTALS = 8;
   private static final int LIGHT_LEVEL_BLAZE = 10;
   private static final int LIGHT_LEVEL_MAGMA_CUBE_CORE = 13;
   private static final double MAX_DIST_SQ = 56.25D;
   private static final int LIGHT_LEVEL_MAX = 15;
   private static final int LIGHT_LEVEL_MAGMA_CUBE = 8;
   private static Map<Integer, DynamicLight> mapDynamicLights = new HashMap();

   public static int getLightLevel(Entity var0) {
      if (var0 == Config.getMinecraft().func_175606_aa() && !Config.isDynamicHandLight()) {
         return 0;
      } else {
         if (var0 instanceof EntityPlayer) {
            EntityPlayer var1 = (EntityPlayer)var0;
            if (var1.func_175149_v()) {
               return 0;
            }
         }

         if (var0.isBurning()) {
            return 15;
         } else if (var0 instanceof EntityFireball) {
            return 15;
         } else if (var0 instanceof EntityTNTPrimed) {
            return 15;
         } else if (var0 instanceof EntityBlaze) {
            EntityBlaze var10 = (EntityBlaze)var0;
            return var10.func_70845_n() ? 15 : 10;
         } else if (var0 instanceof EntityMagmaCube) {
            EntityMagmaCube var8 = (EntityMagmaCube)var0;
            return (double)var8.squishFactor > 0.6D ? 13 : 8;
         } else {
            if (var0 instanceof EntityCreeper) {
               EntityCreeper var6 = (EntityCreeper)var0;
               if ((double)var6.getCreeperFlashIntensity(0.0F) > 0.001D) {
                  return 15;
               }
            }

            ItemStack var7;
            if (var0 instanceof EntityLivingBase) {
               EntityLivingBase var9 = (EntityLivingBase)var0;
               var7 = var9.getHeldItem();
               int var3 = getLightLevel(var7);
               ItemStack var4 = var9.getEquipmentInSlot(4);
               int var5 = getLightLevel(var4);
               return Math.max(var3, var5);
            } else if (var0 instanceof EntityItem) {
               EntityItem var2 = (EntityItem)var0;
               var7 = getItemStack(var2);
               return getLightLevel(var7);
            } else {
               return 0;
            }
         }
      }
   }

   public static void update(RenderGlobal var0) {
      long var1 = System.currentTimeMillis();
      if (var1 >= timeUpdateMs + 50L) {
         timeUpdateMs = var1;
         Map var3 = mapDynamicLights;
         synchronized(mapDynamicLights) {
            updateMapDynamicLights(var0);
            if (mapDynamicLights.size() > 0) {
               Collection var5 = mapDynamicLights.values();
               Iterator var6 = var5.iterator();

               while(var6.hasNext()) {
                  DynamicLight var7 = (DynamicLight)var6.next();
                  var7.update(var0);
               }
            }
         }
      }

   }

   public static int getLightLevel(ItemStack var0) {
      if (var0 == null) {
         return 0;
      } else {
         Item var1 = var0.getItem();
         if (var1 instanceof ItemBlock) {
            ItemBlock var2 = (ItemBlock)var1;
            Block var3 = var2.getBlock();
            if (var3 != null) {
               return var3.getLightValue();
            }
         }

         return var1 == Items.lava_bucket ? Blocks.lava.getLightValue() : (var1 != Items.blaze_rod && var1 != Items.blaze_powder ? (var1 == Items.glowstone_dust ? 8 : (var1 == Items.prismarine_crystals ? 8 : (var1 == Items.magma_cream ? 8 : (var1 == Items.nether_star ? Blocks.beacon.getLightValue() / 2 : 0)))) : 10);
      }
   }

   public static void entityAdded(Entity var0, RenderGlobal var1) {
   }

   public static int getCombinedLight(double var0, int var2) {
      if (var0 > 0.0D) {
         int var3 = (int)(var0 * 16.0D);
         int var4 = var2 & 255;
         if (var3 > var4) {
            var2 &= -256;
            var2 |= var3;
         }
      }

      return var2;
   }

   private static void updateMapDynamicLights(RenderGlobal var0) {
      WorldClient var1 = var0.getWorld();
      if (var1 != null) {
         List var2 = var1.getLoadedEntityList();
         Iterator var3 = var2.iterator();

         while(var3.hasNext()) {
            Entity var4 = (Entity)var3.next();
            int var5 = getLightLevel(var4);
            Integer var6;
            DynamicLight var7;
            if (var5 > 0) {
               var6 = IntegerCache.valueOf(var4.getEntityId());
               var7 = (DynamicLight)mapDynamicLights.get(var6);
               if (var7 == null) {
                  var7 = new DynamicLight(var4);
                  mapDynamicLights.put(var6, var7);
               }
            } else {
               var6 = IntegerCache.valueOf(var4.getEntityId());
               var7 = (DynamicLight)mapDynamicLights.remove(var6);
               if (var7 != null) {
                  var7.updateLitChunks(var0);
               }
            }
         }
      }

   }

   public static int getCount() {
      Map var0 = mapDynamicLights;
      synchronized(mapDynamicLights) {
         return mapDynamicLights.size();
      }
   }

   public static void removeLights(RenderGlobal var0) {
      Map var1 = mapDynamicLights;
      synchronized(mapDynamicLights) {
         Collection var3 = mapDynamicLights.values();
         Iterator var4 = var3.iterator();

         while(var4.hasNext()) {
            DynamicLight var5 = (DynamicLight)var4.next();
            var4.remove();
            var5.updateLitChunks(var0);
         }

      }
   }

   public static double getLightLevel(BlockPos var0) {
      double var1 = 0.0D;
      Map var3 = mapDynamicLights;
      synchronized(mapDynamicLights) {
         Collection var5 = mapDynamicLights.values();
         Iterator var6 = var5.iterator();

         while(var6.hasNext()) {
            DynamicLight var7 = (DynamicLight)var6.next();
            int var8 = var7.getLastLightLevel();
            if (var8 > 0) {
               double var9 = var7.getLastPosX();
               double var11 = var7.getLastPosY();
               double var13 = var7.getLastPosZ();
               double var15 = (double)var0.getX() - var9;
               double var17 = (double)var0.getY() - var11;
               double var19 = (double)var0.getZ() - var13;
               double var21 = var15 * var15 + var17 * var17 + var19 * var19;
               if (var7.isUnderwater() && !Config.isClearWater()) {
                  var8 = Config.limit(var8 - 2, 0, 15);
                  var21 *= 2.0D;
               }

               if (var21 <= 56.25D) {
                  double var23 = Math.sqrt(var21);
                  double var25 = 1.0D - var23 / 7.5D;
                  double var27 = var25 * (double)var8;
                  if (var27 > var1) {
                     var1 = var27;
                  }
               }
            }
         }
      }

      double var4 = Config.limit(var1, 0.0D, 15.0D);
      return var4;
   }

   public static void clear() {
      Map var0 = mapDynamicLights;
      synchronized(mapDynamicLights) {
         mapDynamicLights.clear();
      }
   }

   public static void entityRemoved(Entity var0, RenderGlobal var1) {
      Map var2 = mapDynamicLights;
      synchronized(mapDynamicLights) {
         DynamicLight var4 = (DynamicLight)mapDynamicLights.remove(IntegerCache.valueOf(var0.getEntityId()));
         if (var4 != null) {
            var4.updateLitChunks(var1);
         }

      }
   }

   public static int getCombinedLight(Entity var0, int var1) {
      double var2 = (double)getLightLevel(var0);
      var1 = getCombinedLight(var2, var1);
      return var1;
   }

   public static int getCombinedLight(BlockPos var0, int var1) {
      double var2 = getLightLevel(var0);
      var1 = getCombinedLight(var2, var1);
      return var1;
   }

   public static ItemStack getItemStack(EntityItem var0) {
      ItemStack var1 = var0.getDataWatcher().getWatchableObjectItemStack(10);
      return var1;
   }
}
