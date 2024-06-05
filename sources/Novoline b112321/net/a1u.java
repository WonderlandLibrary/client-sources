package net;

import java.util.List;
import java.util.Random;
import net.mZ;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureVillagePieces$Start;
import net.minecraft.world.gen.structure.StructureVillagePieces$Village;

public class a1u extends StructureVillagePieces$Village {
   public a1u() {
   }

   public a1u(StructureVillagePieces$Start var1, int var2, Random var3, StructureBoundingBox var4, EnumFacing var5) {
      super(var1, var2);
      this.coordBaseMode = var5;
      this.boundingBox = var4;
   }

   public static a1u a(StructureVillagePieces$Start var0, List var1, Random var2, int var3, int var4, int var5, EnumFacing var6, int var7) {
      StructureBoundingBox var8 = mZ.a(var3, var4, var5, 0, 0, 0, 9, 9, 6, var6);
      return canVillageGoDeeper(var8) && StructureComponent.findIntersecting(var1, var8) == null?new a1u(var0, var7, var2, var8, var6):null;
   }

   public boolean addComponentParts(World var1, Random var2, StructureBoundingBox var3) {
      if(this.field_143015_k < 0) {
         this.field_143015_k = this.getAverageGroundLevel(var1, var3);
         if(this.field_143015_k < 0) {
            return true;
         }

         this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.maxY + 9 - 1, 0);
      }

      this.fillWithBlocks(var1, var3, 1, 1, 1, 7, 5, 4, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 0, 0, 0, 8, 0, 5, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 0, 5, 0, 8, 5, 5, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 0, 6, 1, 8, 6, 4, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 0, 7, 2, 8, 7, 3, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
      int var4 = this.getMetadataWithOffset(Blocks.oak_stairs, 3);
      int var5 = this.getMetadataWithOffset(Blocks.oak_stairs, 2);

      for(int var6 = -1; var6 <= 2; ++var6) {
         for(int var7 = 0; var7 <= 8; ++var7) {
            this.setBlockState(var1, Blocks.oak_stairs.getStateFromMeta(var4), var7, 6 + var6, var6, var3);
            this.setBlockState(var1, Blocks.oak_stairs.getStateFromMeta(var5), var7, 6 + var6, 5 - var6, var3);
         }
      }

      this.fillWithBlocks(var1, var3, 0, 1, 0, 0, 1, 5, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 1, 1, 5, 8, 1, 5, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 8, 1, 0, 8, 1, 4, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 2, 1, 0, 7, 1, 0, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 0, 2, 0, 0, 4, 0, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 0, 2, 5, 0, 4, 5, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 8, 2, 5, 8, 4, 5, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 8, 2, 0, 8, 4, 0, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 0, 2, 1, 0, 4, 4, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 1, 2, 5, 7, 4, 5, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 8, 2, 1, 8, 4, 4, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 1, 2, 0, 7, 4, 0, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
      this.setBlockState(var1, Blocks.glass_pane.getDefaultState(), 4, 2, 0, var3);
      this.setBlockState(var1, Blocks.glass_pane.getDefaultState(), 5, 2, 0, var3);
      this.setBlockState(var1, Blocks.glass_pane.getDefaultState(), 6, 2, 0, var3);
      this.setBlockState(var1, Blocks.glass_pane.getDefaultState(), 4, 3, 0, var3);
      this.setBlockState(var1, Blocks.glass_pane.getDefaultState(), 5, 3, 0, var3);
      this.setBlockState(var1, Blocks.glass_pane.getDefaultState(), 6, 3, 0, var3);
      this.setBlockState(var1, Blocks.glass_pane.getDefaultState(), 0, 2, 2, var3);
      this.setBlockState(var1, Blocks.glass_pane.getDefaultState(), 0, 2, 3, var3);
      this.setBlockState(var1, Blocks.glass_pane.getDefaultState(), 0, 3, 2, var3);
      this.setBlockState(var1, Blocks.glass_pane.getDefaultState(), 0, 3, 3, var3);
      this.setBlockState(var1, Blocks.glass_pane.getDefaultState(), 8, 2, 2, var3);
      this.setBlockState(var1, Blocks.glass_pane.getDefaultState(), 8, 2, 3, var3);
      this.setBlockState(var1, Blocks.glass_pane.getDefaultState(), 8, 3, 2, var3);
      this.setBlockState(var1, Blocks.glass_pane.getDefaultState(), 8, 3, 3, var3);
      this.setBlockState(var1, Blocks.glass_pane.getDefaultState(), 2, 2, 5, var3);
      this.setBlockState(var1, Blocks.glass_pane.getDefaultState(), 3, 2, 5, var3);
      this.setBlockState(var1, Blocks.glass_pane.getDefaultState(), 5, 2, 5, var3);
      this.setBlockState(var1, Blocks.glass_pane.getDefaultState(), 6, 2, 5, var3);
      this.fillWithBlocks(var1, var3, 1, 4, 1, 7, 4, 1, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 1, 4, 4, 7, 4, 4, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 1, 3, 4, 7, 3, 4, Blocks.bookshelf.getDefaultState(), Blocks.bookshelf.getDefaultState(), false);
      this.setBlockState(var1, Blocks.planks.getDefaultState(), 7, 1, 4, var3);
      this.setBlockState(var1, Blocks.oak_stairs.getStateFromMeta(this.getMetadataWithOffset(Blocks.oak_stairs, 0)), 7, 1, 3, var3);
      int var9 = this.getMetadataWithOffset(Blocks.oak_stairs, 3);
      this.setBlockState(var1, Blocks.oak_stairs.getStateFromMeta(var9), 6, 1, 4, var3);
      this.setBlockState(var1, Blocks.oak_stairs.getStateFromMeta(var9), 5, 1, 4, var3);
      this.setBlockState(var1, Blocks.oak_stairs.getStateFromMeta(var9), 4, 1, 4, var3);
      this.setBlockState(var1, Blocks.oak_stairs.getStateFromMeta(var9), 3, 1, 4, var3);
      this.setBlockState(var1, Blocks.oak_fence.getDefaultState(), 6, 1, 3, var3);
      this.setBlockState(var1, Blocks.wooden_pressure_plate.getDefaultState(), 6, 2, 3, var3);
      this.setBlockState(var1, Blocks.oak_fence.getDefaultState(), 4, 1, 3, var3);
      this.setBlockState(var1, Blocks.wooden_pressure_plate.getDefaultState(), 4, 2, 3, var3);
      this.setBlockState(var1, Blocks.crafting_table.getDefaultState(), 7, 1, 1, var3);
      this.setBlockState(var1, Blocks.air.getDefaultState(), 1, 1, 0, var3);
      this.setBlockState(var1, Blocks.air.getDefaultState(), 1, 2, 0, var3);
      this.placeDoorCurrentPosition(var1, var3, var2, 1, 1, 0, EnumFacing.getHorizontal(this.getMetadataWithOffset(Blocks.oak_door, 1)));
      if(this.getBlockStateFromPos(var1, 1, 0, -1, var3).getBlock().getMaterial() == Material.air && this.getBlockStateFromPos(var1, 1, -1, -1, var3).getBlock().getMaterial() != Material.air) {
         this.setBlockState(var1, Blocks.stone_stairs.getStateFromMeta(this.getMetadataWithOffset(Blocks.stone_stairs, 3)), 1, 0, -1, var3);
      }

      for(int var10 = 0; var10 < 6; ++var10) {
         for(int var8 = 0; var8 < 9; ++var8) {
            this.clearCurrentPositionBlocksUpwards(var1, var8, 9, var10, var3);
            this.replaceAirAndLiquidDownwards(var1, Blocks.cobblestone.getDefaultState(), var8, -1, var10, var3);
         }
      }

      this.spawnVillagers(var1, var3, 2, 1, 2, 1);
      return true;
   }

   protected int func_180779_c(int var1, int var2) {
      return 1;
   }
}
