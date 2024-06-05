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

public class StructureNetherBridgePieces$Corridor3 extends StructureNetherBridgePieces$Piece {
   public StructureNetherBridgePieces$Corridor3() {
   }

   public StructureNetherBridgePieces$Corridor3(int var1, Random var2, StructureBoundingBox var3, EnumFacing var4) {
      super(var1);
      this.coordBaseMode = var4;
      this.boundingBox = var3;
   }

   public void buildComponent(StructureComponent var1, List var2, Random var3) {
      this.getNextComponentNormal((StructureNetherBridgePieces$Start)var1, var2, var3, 1, 0, true);
   }

   public static StructureNetherBridgePieces$Corridor3 func_175883_a(List var0, Random var1, int var2, int var3, int var4, EnumFacing var5, int var6) {
      StructureBoundingBox var7 = mZ.a(var2, var3, var4, -1, -7, 0, 5, 14, 10, var5);
      return isAboveGround(var7) && StructureComponent.findIntersecting(var0, var7) == null?new StructureNetherBridgePieces$Corridor3(var6, var1, var7, var5):null;
   }

   public boolean addComponentParts(World var1, Random var2, StructureBoundingBox var3) {
      int var4 = this.getMetadataWithOffset(Blocks.nether_brick_stairs, 2);

      for(int var5 = 0; var5 <= 9; ++var5) {
         int var6 = Math.max(1, 7 - var5);
         int var7 = Math.min(Math.max(var6 + 5, 14 - var5), 13);
         int var8 = var5;
         this.fillWithBlocks(var1, var3, 0, 0, var5, 4, var6, var5, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
         this.fillWithBlocks(var1, var3, 1, var6 + 1, var5, 3, var7 - 1, var5, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
         if(var5 <= 6) {
            this.setBlockState(var1, Blocks.nether_brick_stairs.getStateFromMeta(var4), 1, var6 + 1, var5, var3);
            this.setBlockState(var1, Blocks.nether_brick_stairs.getStateFromMeta(var4), 2, var6 + 1, var5, var3);
            this.setBlockState(var1, Blocks.nether_brick_stairs.getStateFromMeta(var4), 3, var6 + 1, var5, var3);
         }

         this.fillWithBlocks(var1, var3, 0, var7, var5, 4, var7, var5, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
         this.fillWithBlocks(var1, var3, 0, var6 + 1, var5, 0, var7 - 1, var5, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
         this.fillWithBlocks(var1, var3, 4, var6 + 1, var5, 4, var7 - 1, var5, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
         if((var5 & 1) == 0) {
            this.fillWithBlocks(var1, var3, 0, var6 + 2, var5, 0, var6 + 3, var5, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
            this.fillWithBlocks(var1, var3, 4, var6 + 2, var5, 4, var6 + 3, var5, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
         }

         for(int var9 = 0; var9 <= 4; ++var9) {
            this.replaceAirAndLiquidDownwards(var1, Blocks.nether_brick.getDefaultState(), var9, -1, var8, var3);
         }
      }

      return true;
   }
}
