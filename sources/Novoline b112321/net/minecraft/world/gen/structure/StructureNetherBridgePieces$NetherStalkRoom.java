package net.minecraft.world.gen.structure;

import java.util.List;
import java.util.Random;
import net.mZ;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureNetherBridgePieces$Piece;
import net.minecraft.world.gen.structure.StructureNetherBridgePieces$Start;

public class StructureNetherBridgePieces$NetherStalkRoom extends StructureNetherBridgePieces$Piece {
   public StructureNetherBridgePieces$NetherStalkRoom() {
   }

   public StructureNetherBridgePieces$NetherStalkRoom(int var1, Random var2, StructureBoundingBox var3, EnumFacing var4) {
      super(var1);
      this.coordBaseMode = var4;
      this.boundingBox = var3;
   }

   public void buildComponent(StructureComponent var1, List var2, Random var3) {
      this.getNextComponentNormal((StructureNetherBridgePieces$Start)var1, var2, var3, 5, 3, true);
      this.getNextComponentNormal((StructureNetherBridgePieces$Start)var1, var2, var3, 5, 11, true);
   }

   public static StructureNetherBridgePieces$NetherStalkRoom func_175875_a(List var0, Random var1, int var2, int var3, int var4, EnumFacing var5, int var6) {
      StructureBoundingBox var7 = mZ.a(var2, var3, var4, -5, -3, 0, 13, 14, 13, var5);
      return isAboveGround(var7) && StructureComponent.findIntersecting(var0, var7) == null?new StructureNetherBridgePieces$NetherStalkRoom(var6, var1, var7, var5):null;
   }

   public boolean addComponentParts(World var1, Random var2, StructureBoundingBox var3) {
      this.fillWithBlocks(var1, var3, 0, 3, 0, 12, 4, 12, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 0, 5, 0, 12, 13, 12, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 0, 5, 0, 1, 12, 12, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 11, 5, 0, 12, 12, 12, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 2, 5, 11, 4, 12, 12, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 8, 5, 11, 10, 12, 12, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 5, 9, 11, 7, 12, 12, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 2, 5, 0, 4, 12, 1, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 8, 5, 0, 10, 12, 1, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 5, 9, 0, 7, 12, 1, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 2, 11, 2, 10, 12, 10, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);

      for(int var4 = 1; var4 <= 11; var4 += 2) {
         this.fillWithBlocks(var1, var3, var4, 10, 0, var4, 11, 0, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
         this.fillWithBlocks(var1, var3, var4, 10, 12, var4, 11, 12, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
         this.fillWithBlocks(var1, var3, 0, 10, var4, 0, 11, var4, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
         this.fillWithBlocks(var1, var3, 12, 10, var4, 12, 11, var4, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
         this.setBlockState(var1, Blocks.nether_brick.getDefaultState(), var4, 13, 0, var3);
         this.setBlockState(var1, Blocks.nether_brick.getDefaultState(), var4, 13, 12, var3);
         this.setBlockState(var1, Blocks.nether_brick.getDefaultState(), 0, 13, var4, var3);
         this.setBlockState(var1, Blocks.nether_brick.getDefaultState(), 12, 13, var4, var3);
         this.setBlockState(var1, Blocks.nether_brick_fence.getDefaultState(), var4 + 1, 13, 0, var3);
         this.setBlockState(var1, Blocks.nether_brick_fence.getDefaultState(), var4 + 1, 13, 12, var3);
         this.setBlockState(var1, Blocks.nether_brick_fence.getDefaultState(), 0, 13, var4 + 1, var3);
         this.setBlockState(var1, Blocks.nether_brick_fence.getDefaultState(), 12, 13, var4 + 1, var3);
      }

      this.setBlockState(var1, Blocks.nether_brick_fence.getDefaultState(), 0, 13, 0, var3);
      this.setBlockState(var1, Blocks.nether_brick_fence.getDefaultState(), 0, 13, 12, var3);
      this.setBlockState(var1, Blocks.nether_brick_fence.getDefaultState(), 0, 13, 0, var3);
      this.setBlockState(var1, Blocks.nether_brick_fence.getDefaultState(), 12, 13, 0, var3);

      for(int var9 = 3; var9 <= 9; var9 += 2) {
         this.fillWithBlocks(var1, var3, 1, 7, var9, 1, 8, var9, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
         this.fillWithBlocks(var1, var3, 11, 7, var9, 11, 8, var9, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
      }

      int var10 = this.getMetadataWithOffset(Blocks.nether_brick_stairs, 3);

      for(int var5 = 0; var5 <= 6; ++var5) {
         int var6 = var5 + 4;

         for(int var7 = 5; var7 <= 7; ++var7) {
            this.setBlockState(var1, Blocks.nether_brick_stairs.getStateFromMeta(var10), var7, 5 + var5, var6, var3);
         }

         if(var6 >= 5 && var6 <= 8) {
            this.fillWithBlocks(var1, var3, 5, 5, var6, 7, var5 + 4, var6, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
         } else if(var6 >= 9 && var6 <= 10) {
            this.fillWithBlocks(var1, var3, 5, 8, var6, 7, var5 + 4, var6, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
         }

         if(var5 >= 1) {
            this.fillWithBlocks(var1, var3, 5, 6 + var5, var6, 7, 9 + var5, var6, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
         }
      }

      for(int var11 = 5; var11 <= 7; ++var11) {
         this.setBlockState(var1, Blocks.nether_brick_stairs.getStateFromMeta(var10), var11, 12, 11, var3);
      }

      this.fillWithBlocks(var1, var3, 5, 6, 7, 5, 7, 7, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 7, 6, 7, 7, 7, 7, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 5, 13, 12, 7, 13, 12, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 2, 5, 2, 3, 5, 3, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 2, 5, 9, 3, 5, 10, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 2, 5, 4, 2, 5, 8, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 9, 5, 2, 10, 5, 3, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 9, 5, 9, 10, 5, 10, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 10, 5, 4, 10, 5, 8, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
      int var12 = this.getMetadataWithOffset(Blocks.nether_brick_stairs, 0);
      int var13 = this.getMetadataWithOffset(Blocks.nether_brick_stairs, 1);
      this.setBlockState(var1, Blocks.nether_brick_stairs.getStateFromMeta(var13), 4, 5, 2, var3);
      this.setBlockState(var1, Blocks.nether_brick_stairs.getStateFromMeta(var13), 4, 5, 3, var3);
      this.setBlockState(var1, Blocks.nether_brick_stairs.getStateFromMeta(var13), 4, 5, 9, var3);
      this.setBlockState(var1, Blocks.nether_brick_stairs.getStateFromMeta(var13), 4, 5, 10, var3);
      this.setBlockState(var1, Blocks.nether_brick_stairs.getStateFromMeta(var12), 8, 5, 2, var3);
      this.setBlockState(var1, Blocks.nether_brick_stairs.getStateFromMeta(var12), 8, 5, 3, var3);
      this.setBlockState(var1, Blocks.nether_brick_stairs.getStateFromMeta(var12), 8, 5, 9, var3);
      this.setBlockState(var1, Blocks.nether_brick_stairs.getStateFromMeta(var12), 8, 5, 10, var3);
      this.fillWithBlocks(var1, var3, 3, 4, 4, 4, 4, 8, Blocks.soul_sand.getDefaultState(), Blocks.soul_sand.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 8, 4, 4, 9, 4, 8, Blocks.soul_sand.getDefaultState(), Blocks.soul_sand.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 3, 5, 4, 4, 5, 8, Blocks.nether_wart.getDefaultState(), Blocks.nether_wart.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 8, 5, 4, 9, 5, 8, Blocks.nether_wart.getDefaultState(), Blocks.nether_wart.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 4, 2, 0, 8, 2, 12, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 0, 2, 4, 12, 2, 8, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 4, 0, 0, 8, 1, 3, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 4, 0, 9, 8, 1, 12, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 0, 0, 4, 3, 1, 8, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 9, 0, 4, 12, 1, 8, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);

      for(int var14 = 4; var14 <= 8; ++var14) {
         for(int var8 = 0; var8 <= 2; ++var8) {
            this.replaceAirAndLiquidDownwards(var1, Blocks.nether_brick.getDefaultState(), var14, -1, var8, var3);
            this.replaceAirAndLiquidDownwards(var1, Blocks.nether_brick.getDefaultState(), var14, -1, 12 - var8, var3);
         }
      }

      for(int var15 = 0; var15 <= 2; ++var15) {
         for(int var16 = 4; var16 <= 8; ++var16) {
            this.replaceAirAndLiquidDownwards(var1, Blocks.nether_brick.getDefaultState(), var15, -1, var16, var3);
            this.replaceAirAndLiquidDownwards(var1, Blocks.nether_brick.getDefaultState(), 12 - var15, -1, var16, var3);
         }
      }

      return true;
   }
}
