package optifine;

import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import java.util.ArrayList;
import java.util.Properties;
import net.ud;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.BiomeGenBase;
import optifine.Config;
import optifine.ConnectedParser;
import optifine.MatchBlock;
import optifine.RangeInt;
import optifine.RangeListInt;

public class RandomMobsProperties {
   public String name = null;
   public String basePath = null;
   public ResourceLocation[] resourceLocations = null;
   public ud[] a = null;

   public RandomMobsProperties(String var1, ResourceLocation[] var2) {
      ConnectedParser var3 = new ConnectedParser("RandomMobs");
      this.name = var3.parseName(var1);
      this.basePath = var3.parseBasePath(var1);
      this.resourceLocations = var2;
   }

   public RandomMobsProperties(Properties var1, String var2, ResourceLocation var3) {
      ConnectedParser var4 = new ConnectedParser("RandomMobs");
      this.name = var4.parseName(var2);
      this.basePath = var4.parseBasePath(var2);
      this.a = this.a(var1, var3, var4);
   }

   public ResourceLocation getTextureLocation(ResourceLocation var1, EntityLiving var2) {
      PacketRemapper[] var3 = MatchBlock.b();
      if(this.a != null) {
         int var4 = 0;
         if(var4 < this.a.length) {
            ud var5 = this.a[var4];
            if(var5.a(var2)) {
               return var5.a(var1, var2.randomMobsId);
            }

            ++var4;
         }
      }

      if(this.resourceLocations != null) {
         int var7 = var2.randomMobsId;
         int var8 = var7 % this.resourceLocations.length;
         return this.resourceLocations[var8];
      } else {
         return var1;
      }
   }

   private ud[] a(Properties var1, ResourceLocation var2, ConnectedParser var3) {
      MatchBlock.b();
      ArrayList var5 = new ArrayList();
      int var6 = var1.size();
      int var7 = 0;
      if(var7 < var6) {
         int var8 = var7 + 1;
         String var9 = var1.getProperty("skins." + var8);
         int[] var10 = var3.m(var9);
         int[] var11 = var3.m(var1.getProperty("weights." + var8));
         BiomeGenBase[] var12 = var3.parseBiomes(var1.getProperty("biomes." + var8));
         RangeListInt var13 = var3.f(var1.getProperty("heights." + var8));
         if(var13 == null) {
            var13 = this.parseMinMaxHeight(var1, var8);
         }

         ud var14 = new ud(var2, var10, var11, var12, var13);
         var5.add(var14);
         ++var7;
      }

      ud[] var16 = (ud[])((ud[])((ud[])var5.toArray(new ud[var5.size()])));
      return var16;
   }

   private RangeListInt parseMinMaxHeight(Properties var1, int var2) {
      MatchBlock.b();
      String var4 = var1.getProperty("minHeight." + var2);
      String var5 = var1.getProperty("maxHeight." + var2);
      if(var4 == null && var5 == null) {
         return null;
      } else {
         int var6 = 0;
         if(var4 != null) {
            var6 = Config.parseInt(var4, -1);
            if(var6 < 0) {
               Config.warn("Invalid minHeight: " + var4);
               return null;
            }
         }

         int var7 = 256;
         if(var5 != null) {
            var7 = Config.parseInt(var5, -1);
            if(var7 < 0) {
               Config.warn("Invalid maxHeight: " + var5);
               return null;
            }
         }

         if(var7 < 0) {
            Config.warn("Invalid minHeight, maxHeight: " + var4 + ", " + var5);
            return null;
         } else {
            RangeListInt var8 = new RangeListInt();
            var8.addRange(new RangeInt(var6, var7));
            return var8;
         }
      }
   }

   public boolean a(String var1) {
      PacketRemapper[] var2 = MatchBlock.b();
      if(this.resourceLocations == null && this.a == null) {
         Config.warn("No skins specified: " + var1);
         return false;
      } else {
         if(this.a != null) {
            int var3 = 0;
            if(var3 < this.a.length) {
               ud var4 = this.a[var3];
               if(!var4.a(var1)) {
                  return false;
               }

               ++var3;
            }
         }

         if(this.resourceLocations != null) {
            int var6 = 0;
            if(var6 < this.resourceLocations.length) {
               ResourceLocation var8 = this.resourceLocations[var6];
               if(!Config.hasResource(var8)) {
                  Config.warn("Texture not found: " + var8.getResourcePath());
                  return false;
               }

               ++var6;
            }
         }

         return true;
      }
   }
}
