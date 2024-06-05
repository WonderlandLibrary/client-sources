package net.minecraft.world.gen.structure;

import java.util.Random;
import net.aUL;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.structure.ComponentScatteredFeaturePieces$DesertPyramid;
import net.minecraft.world.gen.structure.ComponentScatteredFeaturePieces$SwampHut;
import net.minecraft.world.gen.structure.StructureStart;

public class MapGenScatteredFeature$Start extends StructureStart {
   public MapGenScatteredFeature$Start() {
   }

   public MapGenScatteredFeature$Start(World var1, Random var2, int var3, int var4) {
      super(var3, var4);
      BiomeGenBase var5 = var1.getBiomeGenForCoords(new BlockPos(var3 * 16 + 8, 0, var4 * 16 + 8));
      if(var5 != BiomeGenBase.jungle && var5 != BiomeGenBase.jungleHills) {
         if(var5 == BiomeGenBase.swampland) {
            ComponentScatteredFeaturePieces$SwampHut var7 = new ComponentScatteredFeaturePieces$SwampHut(var2, var3 * 16, var4 * 16);
            this.components.add(var7);
         } else if(var5 == BiomeGenBase.desert || var5 == BiomeGenBase.desertHills) {
            ComponentScatteredFeaturePieces$DesertPyramid var8 = new ComponentScatteredFeaturePieces$DesertPyramid(var2, var3 * 16, var4 * 16);
            this.components.add(var8);
         }
      } else {
         aUL var6 = new aUL(var2, var3 * 16, var4 * 16);
         this.components.add(var6);
      }

      this.updateBoundingBox();
   }
}
