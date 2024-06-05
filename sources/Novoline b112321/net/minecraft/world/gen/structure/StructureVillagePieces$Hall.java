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

public class StructureVillagePieces$Hall extends StructureVillagePieces$Village {
   public StructureVillagePieces$Hall() {
   }

   public StructureVillagePieces$Hall(StructureVillagePieces$Start var1, int var2, Random var3, StructureBoundingBox var4, EnumFacing var5) {
      super(var1, var2);
      this.coordBaseMode = var5;
      this.boundingBox = var4;
   }

   public static StructureVillagePieces$Hall func_175857_a(StructureVillagePieces$Start var0, List var1, Random var2, int var3, int var4, int var5, EnumFacing var6, int var7) {
      StructureBoundingBox var8 = mZ.a(var3, var4, var5, 0, 0, 0, 9, 7, 11, var6);
      return canVillageGoDeeper(var8) && StructureComponent.findIntersecting(var1, var8) == null?new StructureVillagePieces$Hall(var0, var7, var2, var8, var6):null;
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
      this.fillWithBlocks(var1, var3, 2, 0, 6, 8, 0, 10, Blocks.dirt.getDefaultState(), Blocks.dirt.getDefaultState(), false);
      this.setBlockState(var1, Blocks.cobblestone.getDefaultState(), 6, 0, 6, var3);
      this.fillWithBlocks(var1, var3, 2, 1, 6, 2, 1, 10, Blocks.oak_fence.getDefaultState(), Blocks.oak_fence.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 8, 1, 6, 8, 1, 10, Blocks.oak_fence.getDefaultState(), Blocks.oak_fence.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 3, 1, 10, 7, 1, 10, Blocks.oak_fence.getDefaultState(), Blocks.oak_fence.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 1, 0, 1, 7, 0, 4, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 0, 0, 0, 0, 3, 5, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 8, 0, 0, 8, 3, 5, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 1, 0, 0, 7, 1, 0, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 1, 0, 5, 7, 1, 5, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 1, 2, 0, 7, 3, 0, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 1, 2, 5, 7, 3, 5, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 0, 4, 1, 8, 4, 1, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 0, 4, 4, 8, 4, 4, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 0, 5, 2, 8, 5, 3, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
      this.setBlockState(var1, Blocks.planks.getDefaultState(), 0, 4, 2, var3);
      this.setBlockState(var1, Blocks.planks.getDefaultState(), 0, 4, 3, var3);
      this.setBlockState(var1, Blocks.planks.getDefaultState(), 8, 4, 2, var3);
      this.setBlockState(var1, Blocks.planks.getDefaultState(), 8, 4, 3, var3);
      int var4 = this.getMetadataWithOffset(Blocks.oak_stairs, 3);
      int var5 = this.getMetadataWithOffset(Blocks.oak_stairs, 2);

      for(int var6 = -1; var6 <= 2; ++var6) {
         for(int var7 = 0; var7 <= 8; ++var7) {
            this.setBlockState(var1, Blocks.oak_stairs.getStateFromMeta(var4), var7, 4 + var6, var6, var3);
            this.setBlockState(var1, Blocks.oak_stairs.getStateFromMeta(var5), var7, 4 + var6, 5 - var6, var3);
         }
      }

      this.setBlockState(var1, Blocks.log.getDefaultState(), 0, 2, 1, var3);
      this.setBlockState(var1, Blocks.log.getDefaultState(), 0, 2, 4, var3);
      this.setBlockState(var1, Blocks.log.getDefaultState(), 8, 2, 1, var3);
      this.setBlockState(var1, Blocks.log.getDefaultState(), 8, 2, 4, var3);
      this.setBlockState(var1, Blocks.glass_pane.getDefaultState(), 0, 2, 2, var3);
      this.setBlockState(var1, Blocks.glass_pane.getDefaultState(), 0, 2, 3, var3);
      this.setBlockState(var1, Blocks.glass_pane.getDefaultState(), 8, 2, 2, var3);
      this.setBlockState(var1, Blocks.glass_pane.getDefaultState(), 8, 2, 3, var3);
      this.setBlockState(var1, Blocks.glass_pane.getDefaultState(), 2, 2, 5, var3);
      this.setBlockState(var1, Blocks.glass_pane.getDefaultState(), 3, 2, 5, var3);
      this.setBlockState(var1, Blocks.glass_pane.getDefaultState(), 5, 2, 0, var3);
      this.setBlockState(var1, Blocks.glass_pane.getDefaultState(), 6, 2, 5, var3);
      this.setBlockState(var1, Blocks.oak_fence.getDefaultState(), 2, 1, 3, var3);
      this.setBlockState(var1, Blocks.wooden_pressure_plate.getDefaultState(), 2, 2, 3, var3);
      this.setBlockState(var1, Blocks.planks.getDefaultState(), 1, 1, 4, var3);
      this.setBlockState(var1, Blocks.oak_stairs.getStateFromMeta(this.getMetadataWithOffset(Blocks.oak_stairs, 3)), 2, 1, 4, var3);
      this.setBlockState(var1, Blocks.oak_stairs.getStateFromMeta(this.getMetadataWithOffset(Blocks.oak_stairs, 1)), 1, 1, 3, var3);
      this.fillWithBlocks(var1, var3, 5, 0, 1, 7, 0, 3, Blocks.double_stone_slab.getDefaultState(), Blocks.double_stone_slab.getDefaultState(), false);
      this.setBlockState(var1, Blocks.double_stone_slab.getDefaultState(), 6, 1, 1, var3);
      this.setBlockState(var1, Blocks.double_stone_slab.getDefaultState(), 6, 1, 2, var3);
      this.setBlockState(var1, Blocks.air.getDefaultState(), 2, 1, 0, var3);
      this.setBlockState(var1, Blocks.air.getDefaultState(), 2, 2, 0, var3);
      this.setBlockState(var1, Blocks.torch.getDefaultState().withProperty(BlockTorch.FACING, this.coordBaseMode), 2, 3, 1, var3);
      this.placeDoorCurrentPosition(var1, var3, var2, 2, 1, 0, EnumFacing.getHorizontal(this.getMetadataWithOffset(Blocks.oak_door, 1)));
      if(this.getBlockStateFromPos(var1, 2, 0, -1, var3).getBlock().getMaterial() == Material.air && this.getBlockStateFromPos(var1, 2, -1, -1, var3).getBlock().getMaterial() != Material.air) {
         this.setBlockState(var1, Blocks.stone_stairs.getStateFromMeta(this.getMetadataWithOffset(Blocks.stone_stairs, 3)), 2, 0, -1, var3);
      }

      this.setBlockState(var1, Blocks.air.getDefaultState(), 6, 1, 5, var3);
      this.setBlockState(var1, Blocks.air.getDefaultState(), 6, 2, 5, var3);
      this.setBlockState(var1, Blocks.torch.getDefaultState().withProperty(BlockTorch.FACING, this.coordBaseMode.getOpposite()), 6, 3, 4, var3);
      this.placeDoorCurrentPosition(var1, var3, var2, 6, 1, 5, EnumFacing.getHorizontal(this.getMetadataWithOffset(Blocks.oak_door, 1)));

      for(int var8 = 0; var8 < 5; ++var8) {
         for(int var9 = 0; var9 < 9; ++var9) {
            this.clearCurrentPositionBlocksUpwards(var1, var9, 7, var8, var3);
            this.replaceAirAndLiquidDownwards(var1, Blocks.cobblestone.getDefaultState(), var9, -1, var8, var3);
         }
      }

      this.spawnVillagers(var1, var3, 4, 1, 2, 2);
      return true;
   }

   protected int func_180779_c(int var1, int var2) {
      return 4;
   }
}
