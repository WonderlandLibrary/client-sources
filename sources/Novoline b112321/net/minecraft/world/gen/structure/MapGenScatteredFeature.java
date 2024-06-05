package net.minecraft.world.gen.structure;

import com.google.common.collect.Lists;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;
import net.nA;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.structure.ComponentScatteredFeaturePieces$SwampHut;
import net.minecraft.world.gen.structure.MapGenScatteredFeature$Start;
import net.minecraft.world.gen.structure.MapGenStructure;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureStart;

public class MapGenScatteredFeature extends MapGenStructure {
   private static final List g = Arrays.asList(new BiomeGenBase[]{BiomeGenBase.desert, BiomeGenBase.desertHills, BiomeGenBase.jungle, BiomeGenBase.jungleHills, BiomeGenBase.swampland});
   private List scatteredFeatureSpawnList;
   private int maxDistanceBetweenScatteredFeatures;
   private int minDistanceBetweenScatteredFeatures;

   public MapGenScatteredFeature() {
      this.scatteredFeatureSpawnList = Lists.newArrayList();
      this.maxDistanceBetweenScatteredFeatures = 32;
      this.minDistanceBetweenScatteredFeatures = 8;
      this.scatteredFeatureSpawnList.add(new nA(EntityWitch.class, 1, 1, 1));
   }

   public MapGenScatteredFeature(Map var1) {
      this();

      for(Entry var3 : var1.entrySet()) {
         if(((String)var3.getKey()).equals("distance")) {
            this.maxDistanceBetweenScatteredFeatures = MathHelper.parseIntWithDefaultAndMax((String)var3.getValue(), this.maxDistanceBetweenScatteredFeatures, this.minDistanceBetweenScatteredFeatures + 1);
         }
      }

   }

   public String getStructureName() {
      return "Temple";
   }

   protected boolean canSpawnStructureAtCoords(int var1, int var2) {
      int var3 = var1;
      int var4 = var2;
      var1 = var1 - (this.maxDistanceBetweenScatteredFeatures - 1);
      var2 = var2 - (this.maxDistanceBetweenScatteredFeatures - 1);
      int var5 = var1 / this.maxDistanceBetweenScatteredFeatures;
      int var6 = var2 / this.maxDistanceBetweenScatteredFeatures;
      Random var7 = this.worldObj.setRandomSeed(var5, var6, 14357617);
      var5 = var5 * this.maxDistanceBetweenScatteredFeatures;
      var6 = var6 * this.maxDistanceBetweenScatteredFeatures;
      var5 = var5 + var7.nextInt(this.maxDistanceBetweenScatteredFeatures - this.minDistanceBetweenScatteredFeatures);
      var6 = var6 + var7.nextInt(this.maxDistanceBetweenScatteredFeatures - this.minDistanceBetweenScatteredFeatures);
      if(var3 == var5 && var4 == var6) {
         BiomeGenBase var8 = this.worldObj.getWorldChunkManager().getBiomeGenerator(new BlockPos(var3 * 16 + 8, 0, var4 * 16 + 8));
         return false;
      } else {
         return false;
      }
   }

   protected StructureStart getStructureStart(int var1, int var2) {
      return new MapGenScatteredFeature$Start(this.worldObj, this.rand, var1, var2);
   }

   public boolean func_175798_a(BlockPos var1) {
      StructureStart var2 = this.func_175797_c(var1);
      if(var2 instanceof MapGenScatteredFeature$Start && !var2.components.isEmpty()) {
         StructureComponent var3 = (StructureComponent)var2.components.getFirst();
         return var3 instanceof ComponentScatteredFeaturePieces$SwampHut;
      } else {
         return false;
      }
   }

   public List getScatteredFeatureSpawnList() {
      return this.scatteredFeatureSpawnList;
   }
}
