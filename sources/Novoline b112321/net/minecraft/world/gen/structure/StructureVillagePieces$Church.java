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

public class StructureVillagePieces$Church extends StructureVillagePieces$Village {
   public StructureVillagePieces$Church() {
   }

   public StructureVillagePieces$Church(StructureVillagePieces$Start var1, int var2, Random var3, StructureBoundingBox var4, EnumFacing var5) {
      super(var1, var2);
      this.coordBaseMode = var5;
      this.boundingBox = var4;
   }

   public static StructureVillagePieces$Church func_175854_a(StructureVillagePieces$Start var0, List var1, Random var2, int var3, int var4, int var5, EnumFacing var6, int var7) {
      StructureBoundingBox var8 = mZ.a(var3, var4, var5, 0, 0, 0, 5, 12, 9, var6);
      return canVillageGoDeeper(var8) && StructureComponent.findIntersecting(var1, var8) == null?new StructureVillagePieces$Church(var0, var7, var2, var8, var6):null;
   }

   public boolean addComponentParts(World var1, Random var2, StructureBoundingBox var3) {
      if(this.field_143015_k < 0) {
         this.field_143015_k = this.getAverageGroundLevel(var1, var3);
         if(this.field_143015_k < 0) {
            return true;
         }

         this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.maxY + 12 - 1, 0);
      }

      this.fillWithBlocks(var1, var3, 1, 1, 1, 3, 3, 7, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 1, 5, 1, 3, 9, 3, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 1, 0, 0, 3, 0, 8, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 1, 1, 0, 3, 10, 0, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 0, 1, 1, 0, 10, 3, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 4, 1, 1, 4, 10, 3, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 0, 0, 4, 0, 4, 7, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 4, 0, 4, 4, 4, 7, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 1, 1, 8, 3, 4, 8, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 1, 5, 4, 3, 10, 4, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 1, 5, 5, 3, 5, 7, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 0, 9, 0, 4, 9, 4, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 0, 4, 0, 4, 4, 4, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
      this.setBlockState(var1, Blocks.cobblestone.getDefaultState(), 0, 11, 2, var3);
      this.setBlockState(var1, Blocks.cobblestone.getDefaultState(), 4, 11, 2, var3);
      this.setBlockState(var1, Blocks.cobblestone.getDefaultState(), 2, 11, 0, var3);
      this.setBlockState(var1, Blocks.cobblestone.getDefaultState(), 2, 11, 4, var3);
      this.setBlockState(var1, Blocks.cobblestone.getDefaultState(), 1, 1, 6, var3);
      this.setBlockState(var1, Blocks.cobblestone.getDefaultState(), 1, 1, 7, var3);
      this.setBlockState(var1, Blocks.cobblestone.getDefaultState(), 2, 1, 7, var3);
      this.setBlockState(var1, Blocks.cobblestone.getDefaultState(), 3, 1, 6, var3);
      this.setBlockState(var1, Blocks.cobblestone.getDefaultState(), 3, 1, 7, var3);
      this.setBlockState(var1, Blocks.stone_stairs.getStateFromMeta(this.getMetadataWithOffset(Blocks.stone_stairs, 3)), 1, 1, 5, var3);
      this.setBlockState(var1, Blocks.stone_stairs.getStateFromMeta(this.getMetadataWithOffset(Blocks.stone_stairs, 3)), 2, 1, 6, var3);
      this.setBlockState(var1, Blocks.stone_stairs.getStateFromMeta(this.getMetadataWithOffset(Blocks.stone_stairs, 3)), 3, 1, 5, var3);
      this.setBlockState(var1, Blocks.stone_stairs.getStateFromMeta(this.getMetadataWithOffset(Blocks.stone_stairs, 1)), 1, 2, 7, var3);
      this.setBlockState(var1, Blocks.stone_stairs.getStateFromMeta(this.getMetadataWithOffset(Blocks.stone_stairs, 0)), 3, 2, 7, var3);
      this.setBlockState(var1, Blocks.glass_pane.getDefaultState(), 0, 2, 2, var3);
      this.setBlockState(var1, Blocks.glass_pane.getDefaultState(), 0, 3, 2, var3);
      this.setBlockState(var1, Blocks.glass_pane.getDefaultState(), 4, 2, 2, var3);
      this.setBlockState(var1, Blocks.glass_pane.getDefaultState(), 4, 3, 2, var3);
      this.setBlockState(var1, Blocks.glass_pane.getDefaultState(), 0, 6, 2, var3);
      this.setBlockState(var1, Blocks.glass_pane.getDefaultState(), 0, 7, 2, var3);
      this.setBlockState(var1, Blocks.glass_pane.getDefaultState(), 4, 6, 2, var3);
      this.setBlockState(var1, Blocks.glass_pane.getDefaultState(), 4, 7, 2, var3);
      this.setBlockState(var1, Blocks.glass_pane.getDefaultState(), 2, 6, 0, var3);
      this.setBlockState(var1, Blocks.glass_pane.getDefaultState(), 2, 7, 0, var3);
      this.setBlockState(var1, Blocks.glass_pane.getDefaultState(), 2, 6, 4, var3);
      this.setBlockState(var1, Blocks.glass_pane.getDefaultState(), 2, 7, 4, var3);
      this.setBlockState(var1, Blocks.glass_pane.getDefaultState(), 0, 3, 6, var3);
      this.setBlockState(var1, Blocks.glass_pane.getDefaultState(), 4, 3, 6, var3);
      this.setBlockState(var1, Blocks.glass_pane.getDefaultState(), 2, 3, 8, var3);
      this.setBlockState(var1, Blocks.torch.getDefaultState().withProperty(BlockTorch.FACING, this.coordBaseMode.getOpposite()), 2, 4, 7, var3);
      this.setBlockState(var1, Blocks.torch.getDefaultState().withProperty(BlockTorch.FACING, this.coordBaseMode.rotateY()), 1, 4, 6, var3);
      this.setBlockState(var1, Blocks.torch.getDefaultState().withProperty(BlockTorch.FACING, this.coordBaseMode.rotateYCCW()), 3, 4, 6, var3);
      this.setBlockState(var1, Blocks.torch.getDefaultState().withProperty(BlockTorch.FACING, this.coordBaseMode), 2, 4, 5, var3);
      int var4 = this.getMetadataWithOffset(Blocks.ladder, 4);

      for(int var5 = 1; var5 <= 9; ++var5) {
         this.setBlockState(var1, Blocks.ladder.getStateFromMeta(var4), 3, var5, 3, var3);
      }

      this.setBlockState(var1, Blocks.air.getDefaultState(), 2, 1, 0, var3);
      this.setBlockState(var1, Blocks.air.getDefaultState(), 2, 2, 0, var3);
      this.placeDoorCurrentPosition(var1, var3, var2, 2, 1, 0, EnumFacing.getHorizontal(this.getMetadataWithOffset(Blocks.oak_door, 1)));
      if(this.getBlockStateFromPos(var1, 2, 0, -1, var3).getBlock().getMaterial() == Material.air && this.getBlockStateFromPos(var1, 2, -1, -1, var3).getBlock().getMaterial() != Material.air) {
         this.setBlockState(var1, Blocks.stone_stairs.getStateFromMeta(this.getMetadataWithOffset(Blocks.stone_stairs, 3)), 2, 0, -1, var3);
      }

      for(int var7 = 0; var7 < 9; ++var7) {
         for(int var6 = 0; var6 < 5; ++var6) {
            this.clearCurrentPositionBlocksUpwards(var1, var6, 12, var7, var3);
            this.replaceAirAndLiquidDownwards(var1, Blocks.cobblestone.getDefaultState(), var6, -1, var7, var3);
         }
      }

      this.spawnVillagers(var1, var3, 2, 1, 2, 1);
      return true;
   }

   protected int func_180779_c(int var1, int var2) {
      return 2;
   }
}
