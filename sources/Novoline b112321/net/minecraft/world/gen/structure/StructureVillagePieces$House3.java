package net.minecraft.world.gen.structure;

import java.util.List;
import java.util.Random;
import net.mZ;
import net.minecraft.block.BlockTorch;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureVillagePieces$Start;
import net.minecraft.world.gen.structure.StructureVillagePieces$Village;

public class StructureVillagePieces$House3 extends StructureVillagePieces$Village {
   public StructureVillagePieces$House3() {
   }

   public StructureVillagePieces$House3(StructureVillagePieces$Start var1, int var2, Random var3, StructureBoundingBox var4, EnumFacing var5) {
      super(var1, var2);
      this.coordBaseMode = var5;
      this.boundingBox = var4;
   }

   public static StructureVillagePieces$House3 func_175849_a(StructureVillagePieces$Start var0, List var1, Random var2, int var3, int var4, int var5, EnumFacing var6, int var7) {
      StructureBoundingBox var8 = mZ.a(var3, var4, var5, 0, 0, 0, 9, 7, 12, var6);
      return canVillageGoDeeper(var8) && StructureComponent.findIntersecting(var1, var8) == null?new StructureVillagePieces$House3(var0, var7, var2, var8, var6):null;
   }

   public boolean addComponentParts(World var1, Random var2, StructureBoundingBox var3) {
      if(this.field_143015_k < 0) {
         this.field_143015_k = this.getAverageGroundLevel(var1, var3);
         if(this.field_143015_k < 0) {
            return true;
         }

         this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.maxY + 7 - 1, 0);
      }

      this.fillWithBlocks(var1, var3, 1, 1, 1, 7, 4, 4, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 2, 1, 6, 8, 4, 10, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 2, 0, 5, 8, 0, 10, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 1, 0, 1, 7, 0, 4, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 0, 0, 0, 0, 3, 5, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 8, 0, 0, 8, 3, 10, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 1, 0, 0, 7, 2, 0, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 1, 0, 5, 2, 1, 5, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 2, 0, 6, 2, 3, 10, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 3, 0, 10, 7, 3, 10, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 1, 2, 0, 7, 3, 0, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 1, 2, 5, 2, 3, 5, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 0, 4, 1, 8, 4, 1, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 0, 4, 4, 3, 4, 4, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 0, 5, 2, 8, 5, 3, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
      this.setBlockState(var1, Blocks.planks.getDefaultState(), 0, 4, 2, var3);
      this.setBlockState(var1, Blocks.planks.getDefaultState(), 0, 4, 3, var3);
      this.setBlockState(var1, Blocks.planks.getDefaultState(), 8, 4, 2, var3);
      this.setBlockState(var1, Blocks.planks.getDefaultState(), 8, 4, 3, var3);
      this.setBlockState(var1, Blocks.planks.getDefaultState(), 8, 4, 4, var3);
      int var4 = this.getMetadataWithOffset(Blocks.oak_stairs, 3);
      int var5 = this.getMetadataWithOffset(Blocks.oak_stairs, 2);

      for(int var6 = -1; var6 <= 2; ++var6) {
         for(int var7 = 0; var7 <= 8; ++var7) {
            this.setBlockState(var1, Blocks.oak_stairs.getStateFromMeta(var4), var7, 4 + var6, var6, var3);
            if((var6 > -1 || var7 <= 1) && var7 <= 3 && (var6 > 1 || var7 <= 4 || var7 >= 6)) {
               this.setBlockState(var1, Blocks.oak_stairs.getStateFromMeta(var5), var7, 4 + var6, 5 - var6, var3);
            }
         }
      }

      this.fillWithBlocks(var1, var3, 3, 4, 5, 3, 4, 10, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 7, 4, 2, 7, 4, 10, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 4, 5, 4, 4, 5, 10, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 6, 5, 4, 6, 5, 10, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 5, 6, 3, 5, 6, 10, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
      int var10 = this.getMetadataWithOffset(Blocks.oak_stairs, 0);

      for(int var11 = 4; var11 >= 1; --var11) {
         this.setBlockState(var1, Blocks.planks.getDefaultState(), var11, 2 + var11, 7 - var11, var3);

         for(int var8 = 8 - var11; var8 <= 10; ++var8) {
            this.setBlockState(var1, Blocks.oak_stairs.getStateFromMeta(var10), var11, 2 + var11, var8, var3);
         }
      }

      int var12 = this.getMetadataWithOffset(Blocks.oak_stairs, 1);
      this.setBlockState(var1, Blocks.planks.getDefaultState(), 6, 6, 3, var3);
      this.setBlockState(var1, Blocks.planks.getDefaultState(), 7, 5, 4, var3);
      this.setBlockState(var1, Blocks.oak_stairs.getStateFromMeta(var12), 6, 6, 4, var3);

      for(int var13 = 6; var13 <= 8; ++var13) {
         for(int var9 = 5; var9 <= 10; ++var9) {
            this.setBlockState(var1, Blocks.oak_stairs.getStateFromMeta(var12), var13, 12 - var13, var9, var3);
         }
      }

      this.setBlockState(var1, Blocks.log.getDefaultState(), 0, 2, 1, var3);
      this.setBlockState(var1, Blocks.log.getDefaultState(), 0, 2, 4, var3);
      this.setBlockState(var1, Blocks.glass_pane.getDefaultState(), 0, 2, 2, var3);
      this.setBlockState(var1, Blocks.glass_pane.getDefaultState(), 0, 2, 3, var3);
      this.setBlockState(var1, Blocks.log.getDefaultState(), 4, 2, 0, var3);
      this.setBlockState(var1, Blocks.glass_pane.getDefaultState(), 5, 2, 0, var3);
      this.setBlockState(var1, Blocks.log.getDefaultState(), 6, 2, 0, var3);
      this.setBlockState(var1, Blocks.log.getDefaultState(), 8, 2, 1, var3);
      this.setBlockState(var1, Blocks.glass_pane.getDefaultState(), 8, 2, 2, var3);
      this.setBlockState(var1, Blocks.glass_pane.getDefaultState(), 8, 2, 3, var3);
      this.setBlockState(var1, Blocks.log.getDefaultState(), 8, 2, 4, var3);
      this.setBlockState(var1, Blocks.planks.getDefaultState(), 8, 2, 5, var3);
      this.setBlockState(var1, Blocks.log.getDefaultState(), 8, 2, 6, var3);
      this.setBlockState(var1, Blocks.glass_pane.getDefaultState(), 8, 2, 7, var3);
      this.setBlockState(var1, Blocks.glass_pane.getDefaultState(), 8, 2, 8, var3);
      this.setBlockState(var1, Blocks.log.getDefaultState(), 8, 2, 9, var3);
      this.setBlockState(var1, Blocks.log.getDefaultState(), 2, 2, 6, var3);
      this.setBlockState(var1, Blocks.glass_pane.getDefaultState(), 2, 2, 7, var3);
      this.setBlockState(var1, Blocks.glass_pane.getDefaultState(), 2, 2, 8, var3);
      this.setBlockState(var1, Blocks.log.getDefaultState(), 2, 2, 9, var3);
      this.setBlockState(var1, Blocks.log.getDefaultState(), 4, 4, 10, var3);
      this.setBlockState(var1, Blocks.glass_pane.getDefaultState(), 5, 4, 10, var3);
      this.setBlockState(var1, Blocks.log.getDefaultState(), 6, 4, 10, var3);
      this.setBlockState(var1, Blocks.planks.getDefaultState(), 5, 5, 10, var3);
      this.setBlockState(var1, Blocks.air.getDefaultState(), 2, 1, 0, var3);
      this.setBlockState(var1, Blocks.air.getDefaultState(), 2, 2, 0, var3);
      this.setBlockState(var1, Blocks.torch.getDefaultState().withProperty(BlockTorch.FACING, this.coordBaseMode), 2, 3, 1, var3);
      this.placeDoorCurrentPosition(var1, var3, var2, 2, 1, 0, EnumFacing.getHorizontal(this.getMetadataWithOffset(Blocks.oak_door, 1)));
      this.fillWithBlocks(var1, var3, 1, 0, -1, 3, 2, -1, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
      if(this.getBlockStateFromPos(var1, 2, 0, -1, var3).getBlock().getMaterial() == Material.air && this.getBlockStateFromPos(var1, 2, -1, -1, var3).getBlock().getMaterial() != Material.air) {
         this.setBlockState(var1, Blocks.stone_stairs.getStateFromMeta(this.getMetadataWithOffset(Blocks.stone_stairs, 3)), 2, 0, -1, var3);
      }

      for(int var14 = 0; var14 < 5; ++var14) {
         for(int var16 = 0; var16 < 9; ++var16) {
            this.clearCurrentPositionBlocksUpwards(var1, var16, 7, var14, var3);
            this.replaceAirAndLiquidDownwards(var1, Blocks.cobblestone.getDefaultState(), var16, -1, var14, var3);
         }
      }

      for(int var15 = 5; var15 < 11; ++var15) {
         for(int var17 = 2; var17 < 9; ++var17) {
            this.clearCurrentPositionBlocksUpwards(var1, var17, 7, var15, var3);
            this.replaceAirAndLiquidDownwards(var1, Blocks.cobblestone.getDefaultState(), var17, -1, var15, var3);
         }
      }

      this.spawnVillagers(var1, var3, 4, 1, 2, 2);
      return true;
   }
}
