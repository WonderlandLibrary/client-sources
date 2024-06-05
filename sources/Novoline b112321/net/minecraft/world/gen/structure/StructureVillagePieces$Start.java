package net.minecraft.world.gen.structure;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Random;
import net.minecraft.util.BlockPos;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.gen.structure.StructureVillagePieces$PieceWeight;
import net.minecraft.world.gen.structure.StructureVillagePieces$Well;

public class StructureVillagePieces$Start extends StructureVillagePieces$Well {
   public WorldChunkManager worldChunkMngr;
   public boolean inDesert;
   public int terrainType;
   public StructureVillagePieces$PieceWeight m;
   public List structureVillageWeightedPieceList;
   public List field_74932_i = Lists.newArrayList();
   public List field_74930_j = Lists.newArrayList();

   public StructureVillagePieces$Start() {
   }

   public StructureVillagePieces$Start(WorldChunkManager var1, int var2, Random var3, int var4, int var5, List var6, int var7) {
      super((StructureVillagePieces$Start)null, 0, var3, var4, var5);
      this.worldChunkMngr = var1;
      this.structureVillageWeightedPieceList = var6;
      this.terrainType = var7;
      BiomeGenBase var8 = var1.getBiomeGenerator(new BlockPos(var4, 0, var5), BiomeGenBase.field_180279_ad);
      this.inDesert = var8 == BiomeGenBase.desert || var8 == BiomeGenBase.desertHills;
      this.func_175846_a(this.inDesert);
   }

   public WorldChunkManager getWorldChunkManager() {
      return this.worldChunkMngr;
   }
}
