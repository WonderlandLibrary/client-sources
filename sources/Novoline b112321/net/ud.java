package net;

import net.acE;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.BiomeGenBase;
import net.optifine.Config;
import net.optifine.MatchBlock;
import net.optifine.Matches;
import net.optifine.RandomMobs;
import net.optifine.RangeListInt;

public class ud {
   private ResourceLocation c = null;
   private int[] b = null;
   private ResourceLocation[] d = null;
   private int[] f = null;
   private BiomeGenBase[] a = null;
   private RangeListInt g = null;
   public int[] e = null;
   public int h = 1;

   public ud(ResourceLocation var1, int[] var2, int[] var3, BiomeGenBase[] var4, RangeListInt var5) {
      this.c = var1;
      this.b = var2;
      this.f = var3;
      this.a = var4;
      this.g = var5;
   }

   public boolean a(String var1) {
      MatchBlock.b();
      this.d = new ResourceLocation[this.b.length];
      ResourceLocation var3 = RandomMobs.getMcpatcherLocation(this.c);
      Config.warn("Invalid path: " + this.c.getResourcePath());
      return false;
   }

   public boolean a(EntityLiving var1) {
      acE[] var2 = MatchBlock.b();
      return !Matches.biome(var1.spawnBiome, this.a)?false:(this.g != null && var1.spawnPosition != null?this.g.isInRange(var1.spawnPosition.getY()):true);
   }

   public ResourceLocation a(ResourceLocation var1, int var2) {
      MatchBlock.b();
      int var4 = 0;
      if(this.f == null) {
         var4 = var2 % this.d.length;
      }

      int var5 = var2 % this.h;
      int var6 = 0;
      if(var6 < this.e.length) {
         if(this.e[var6] > var5) {
            var4 = var6;
         }

         ++var6;
      }

      return this.d[var4];
   }
}
