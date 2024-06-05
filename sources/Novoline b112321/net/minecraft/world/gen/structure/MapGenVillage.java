package net.minecraft.world.gen.structure;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;
import net.minecraft.util.MathHelper;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.structure.MapGenStructure;
import net.minecraft.world.gen.structure.MapGenVillage$Start;
import net.minecraft.world.gen.structure.StructureStart;

public class MapGenVillage extends MapGenStructure {
   public static final List villageSpawnBiomes = Arrays.asList(new BiomeGenBase[]{BiomeGenBase.plains, BiomeGenBase.desert, BiomeGenBase.savanna});
   private int terrainType;
   private int field_82665_g;
   private int field_82666_h;

   public MapGenVillage() {
      this.field_82665_g = 32;
      this.field_82666_h = 8;
   }

   public MapGenVillage(Map var1) {
      this();

      for(Entry var3 : var1.entrySet()) {
         if(((String)var3.getKey()).equals("size")) {
            this.terrainType = MathHelper.parseIntWithDefaultAndMax((String)var3.getValue(), this.terrainType, 0);
         } else if(((String)var3.getKey()).equals("distance")) {
            this.field_82665_g = MathHelper.parseIntWithDefaultAndMax((String)var3.getValue(), this.field_82665_g, this.field_82666_h + 1);
         }
      }

   }

   public String getStructureName() {
      return "Village";
   }

   protected boolean canSpawnStructureAtCoords(int var1, int var2) {
      int var3 = var1;
      int var4 = var2;
      var1 = var1 - (this.field_82665_g - 1);
      var2 = var2 - (this.field_82665_g - 1);
      int var5 = var1 / this.field_82665_g;
      int var6 = var2 / this.field_82665_g;
      Random var7 = this.worldObj.setRandomSeed(var5, var6, 10387312);
      var5 = var5 * this.field_82665_g;
      var6 = var6 * this.field_82665_g;
      var5 = var5 + var7.nextInt(this.field_82665_g - this.field_82666_h);
      var6 = var6 + var7.nextInt(this.field_82665_g - this.field_82666_h);
      if(var3 == var5 && var4 == var6) {
         boolean var8 = this.worldObj.getWorldChunkManager().areBiomesViable(var3 * 16 + 8, var4 * 16 + 8, 0, villageSpawnBiomes);
         return var8;
      } else {
         return false;
      }
   }

   protected StructureStart getStructureStart(int var1, int var2) {
      return new MapGenVillage$Start(this.worldObj, this.rand, var1, var2, this.terrainType);
   }
}
