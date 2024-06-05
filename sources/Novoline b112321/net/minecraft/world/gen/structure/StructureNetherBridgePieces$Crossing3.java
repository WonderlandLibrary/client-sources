package net.minecraft.world.gen.structure;

import java.util.List;
import java.util.Random;
import net.mZ;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing$Plane;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureNetherBridgePieces$1;
import net.minecraft.world.gen.structure.StructureNetherBridgePieces$Piece;
import net.minecraft.world.gen.structure.StructureNetherBridgePieces$Start;

public class StructureNetherBridgePieces$Crossing3 extends StructureNetherBridgePieces$Piece {
   public StructureNetherBridgePieces$Crossing3() {
   }

   public StructureNetherBridgePieces$Crossing3(int var1, Random var2, StructureBoundingBox var3, EnumFacing var4) {
      super(var1);
      this.coordBaseMode = var4;
      this.boundingBox = var3;
   }

   protected StructureNetherBridgePieces$Crossing3(Random var1, int var2, int var3) {
      super(0);
      this.coordBaseMode = EnumFacing$Plane.HORIZONTAL.random(var1);
      switch(StructureNetherBridgePieces$1.$SwitchMap$net$minecraft$util$EnumFacing[this.coordBaseMode.ordinal()]) {
      case 1:
      case 2:
         this.boundingBox = new StructureBoundingBox(var2, 64, var3, var2 + 19 - 1, 73, var3 + 19 - 1);
         break;
      default:
         this.boundingBox = new StructureBoundingBox(var2, 64, var3, var2 + 19 - 1, 73, var3 + 19 - 1);
      }

   }

   public void buildComponent(StructureComponent var1, List var2, Random var3) {
      this.getNextComponentNormal((StructureNetherBridgePieces$Start)var1, var2, var3, 8, 3, false);
      this.getNextComponentX((StructureNetherBridgePieces$Start)var1, var2, var3, 3, 8, false);
      this.getNextComponentZ((StructureNetherBridgePieces$Start)var1, var2, var3, 3, 8, false);
   }

   public static StructureNetherBridgePieces$Crossing3 func_175885_a(List var0, Random var1, int var2, int var3, int var4, EnumFacing var5, int var6) {
      StructureBoundingBox var7 = mZ.a(var2, var3, var4, -8, -3, 0, 19, 10, 19, var5);
      return isAboveGround(var7) && StructureComponent.findIntersecting(var0, var7) == null?new StructureNetherBridgePieces$Crossing3(var6, var1, var7, var5):null;
   }

   public boolean addComponentParts(World var1, Random var2, StructureBoundingBox var3) {
      this.fillWithBlocks(var1, var3, 7, 3, 0, 11, 4, 18, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 0, 3, 7, 18, 4, 11, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 8, 5, 0, 10, 7, 18, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 0, 5, 8, 18, 7, 10, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 7, 5, 0, 7, 5, 7, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 7, 5, 11, 7, 5, 18, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 11, 5, 0, 11, 5, 7, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 11, 5, 11, 11, 5, 18, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 0, 5, 7, 7, 5, 7, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 11, 5, 7, 18, 5, 7, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 0, 5, 11, 7, 5, 11, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 11, 5, 11, 18, 5, 11, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 7, 2, 0, 11, 2, 5, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 7, 2, 13, 11, 2, 18, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 7, 0, 0, 11, 1, 3, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 7, 0, 15, 11, 1, 18, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);

      for(int var4 = 7; var4 <= 11; ++var4) {
         for(int var5 = 0; var5 <= 2; ++var5) {
            this.replaceAirAndLiquidDownwards(var1, Blocks.nether_brick.getDefaultState(), var4, -1, var5, var3);
            this.replaceAirAndLiquidDownwards(var1, Blocks.nether_brick.getDefaultState(), var4, -1, 18 - var5, var3);
         }
      }

      this.fillWithBlocks(var1, var3, 0, 2, 7, 5, 2, 11, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 13, 2, 7, 18, 2, 11, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 0, 0, 7, 3, 1, 11, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
      this.fillWithBlocks(var1, var3, 15, 0, 7, 18, 1, 11, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);

      for(int var6 = 0; var6 <= 2; ++var6) {
         for(int var7 = 7; var7 <= 11; ++var7) {
            this.replaceAirAndLiquidDownwards(var1, Blocks.nether_brick.getDefaultState(), var6, -1, var7, var3);
            this.replaceAirAndLiquidDownwards(var1, Blocks.nether_brick.getDefaultState(), 18 - var6, -1, var7, var3);
         }
      }

      return true;
   }
}
