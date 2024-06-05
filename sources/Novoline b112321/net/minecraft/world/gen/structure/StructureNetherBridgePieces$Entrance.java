package net.minecraft.world.gen.structure;

import java.util.List;
import java.util.Random;
import net.mZ;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureNetherBridgePieces$Piece;
import net.minecraft.world.gen.structure.StructureNetherBridgePieces$Start;

public class StructureNetherBridgePieces$Entrance extends StructureNetherBridgePieces$Piece {
   public StructureNetherBridgePieces$Entrance() {
   }

   public StructureNetherBridgePieces$Entrance(int var1, Random var2, StructureBoundingBox var3, EnumFacing var4) {
      super(var1);
      this.coordBaseMode = var4;
      this.boundingBox = var3;
   }

   public void buildComponent(StructureComponent var1, List var2, Random var3) {
      this.getNextComponentNormal((StructureNetherBridgePieces$Start)var1, var2, var3, 5, 3, true);
   }

   public static StructureNetherBridgePieces$Entrance func_175881_a(List var0, Random var1, int var2, int var3, int var4, EnumFacing var5, int var6) {
      StructureBoundingBox var7 = mZ.a(var2, var3, var4, -5, -3, 0, 13, 14, 13, var5);
      return isAboveGround(var7) && StructureComponent.findIntersecting(var0, var7) == null?new StructureNetherBridgePieces$Entrance(var6, var1, var7, var5):null;
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
      this.fillWithBlocks(var1, var3, 5, 8, 0, 7, 8, 0, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);

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

      for(int var6 = 3; var6 <= 9; var6 += 2) {
         this.fillWithBlocks(var1, var3, 1, 7, var6, 1, 8, var6, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
         this.fillWithBlocks(var1, var3, 11, 7, var6, 11, 8, var6, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
      }

      this.fillWithBlocks(var1, var3, 4, 2, 0, 8, 2, 12, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 0, 2, 4, 12, 2, 8, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 4, 0, 0, 8, 1, 3, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 4, 0, 9, 8, 1, 12, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 0, 0, 4, 3, 1, 8, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 9, 0, 4, 12, 1, 8, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);

      for(int var7 = 4; var7 <= 8; ++var7) {
         for(int var5 = 0; var5 <= 2; ++var5) {
            this.replaceAirAndLiquidDownwards(var1, Blocks.nether_brick.getDefaultState(), var7, -1, var5, var3);
            this.replaceAirAndLiquidDownwards(var1, Blocks.nether_brick.getDefaultState(), var7, -1, 12 - var5, var3);
         }
      }

      for(int var8 = 0; var8 <= 2; ++var8) {
         for(int var10 = 4; var10 <= 8; ++var10) {
            this.replaceAirAndLiquidDownwards(var1, Blocks.nether_brick.getDefaultState(), var8, -1, var10, var3);
            this.replaceAirAndLiquidDownwards(var1, Blocks.nether_brick.getDefaultState(), 12 - var8, -1, var10, var3);
         }
      }

      this.fillWithBlocks(var1, var3, 5, 5, 5, 7, 5, 7, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 6, 1, 6, 6, 4, 6, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
      this.setBlockState(var1, Blocks.nether_brick.getDefaultState(), 6, 0, 6, var3);
      this.setBlockState(var1, Blocks.flowing_lava.getDefaultState(), 6, 5, 6, var3);
      BlockPos var9 = new BlockPos(this.getXWithOffset(6, 6), this.getYWithOffset(5), this.getZWithOffset(6, 6));
      if(var3.isVecInside(var9)) {
         var1.forceBlockUpdateTick(Blocks.flowing_lava, var9, var2);
      }

      return true;
   }
}
