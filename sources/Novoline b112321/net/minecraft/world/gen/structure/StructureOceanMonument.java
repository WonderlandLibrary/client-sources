package net.minecraft.world.gen.structure;

import com.google.common.collect.Lists;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;
import net.nA;
import net.minecraft.entity.monster.EntityGuardian;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.structure.MapGenStructure;
import net.minecraft.world.gen.structure.StructureOceanMonument$StartMonument;
import net.minecraft.world.gen.structure.StructureStart;

public class StructureOceanMonument extends MapGenStructure {
   private int field_175800_f;
   private int field_175801_g;
   public static final List field_175802_d = Arrays.asList(new BiomeGenBase[]{BiomeGenBase.ocean, BiomeGenBase.deepOcean, BiomeGenBase.river, BiomeGenBase.frozenOcean, BiomeGenBase.frozenRiver});
   private static final List field_175803_h = Lists.newArrayList();

   public StructureOceanMonument() {
      this.field_175800_f = 32;
      this.field_175801_g = 5;
   }

   public StructureOceanMonument(Map var1) {
      this();

      for(Entry var3 : var1.entrySet()) {
         if(((String)var3.getKey()).equals("spacing")) {
            this.field_175800_f = MathHelper.parseIntWithDefaultAndMax((String)var3.getValue(), this.field_175800_f, 1);
         } else if(((String)var3.getKey()).equals("separation")) {
            this.field_175801_g = MathHelper.parseIntWithDefaultAndMax((String)var3.getValue(), this.field_175801_g, 1);
         }
      }

   }

   public String getStructureName() {
      return "Monument";
   }

   protected boolean canSpawnStructureAtCoords(int var1, int var2) {
      int var3 = var1;
      int var4 = var2;
      var1 = var1 - (this.field_175800_f - 1);
      var2 = var2 - (this.field_175800_f - 1);
      int var5 = var1 / this.field_175800_f;
      int var6 = var2 / this.field_175800_f;
      Random var7 = this.worldObj.setRandomSeed(var5, var6, 10387313);
      var5 = var5 * this.field_175800_f;
      var6 = var6 * this.field_175800_f;
      var5 = var5 + (var7.nextInt(this.field_175800_f - this.field_175801_g) + var7.nextInt(this.field_175800_f - this.field_175801_g)) / 2;
      var6 = var6 + (var7.nextInt(this.field_175800_f - this.field_175801_g) + var7.nextInt(this.field_175800_f - this.field_175801_g)) / 2;
      if(var3 == var5 && var4 == var6) {
         if(this.worldObj.getWorldChunkManager().getBiomeGenerator(new BlockPos(var3 * 16 + 8, 64, var4 * 16 + 8), (BiomeGenBase)null) != BiomeGenBase.deepOcean) {
            return false;
         } else {
            boolean var8 = this.worldObj.getWorldChunkManager().areBiomesViable(var3 * 16 + 8, var4 * 16 + 8, 29, field_175802_d);
            return var8;
         }
      } else {
         return false;
      }
   }

   protected StructureStart getStructureStart(int var1, int var2) {
      return new StructureOceanMonument$StartMonument(this.worldObj, this.rand, var1, var2);
   }

   public List func_175799_b() {
      return field_175803_h;
   }

   static {
      field_175803_h.add(new nA(EntityGuardian.class, 1, 2, 4));
   }
}
