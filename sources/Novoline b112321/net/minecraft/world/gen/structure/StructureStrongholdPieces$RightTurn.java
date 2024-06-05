package net.minecraft.world.gen.structure;

import java.util.List;
import java.util.Random;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureStrongholdPieces;
import net.minecraft.world.gen.structure.StructureStrongholdPieces$LeftTurn;
import net.minecraft.world.gen.structure.StructureStrongholdPieces$Stairs2;

public class StructureStrongholdPieces$RightTurn extends StructureStrongholdPieces$LeftTurn {
   public void buildComponent(StructureComponent var1, List var2, Random var3) {
      if(this.coordBaseMode != EnumFacing.NORTH && this.coordBaseMode != EnumFacing.EAST) {
         this.b((StructureStrongholdPieces$Stairs2)var1, var2, var3, 1, 1);
      } else {
         this.a((StructureStrongholdPieces$Stairs2)var1, var2, var3, 1, 1);
      }

   }

   public boolean addComponentParts(World var1, Random var2, StructureBoundingBox var3) {
      if(this.isLiquidInStructureBoundingBox(var1, var3)) {
         return false;
      } else {
         this.fillWithRandomizedBlocks(var1, var3, 0, 0, 0, 4, 4, 4, true, var2, StructureStrongholdPieces.access$100());
         this.a(var1, var2, var3, this.d, 1, 1, 0);
         if(this.coordBaseMode != EnumFacing.NORTH && this.coordBaseMode != EnumFacing.EAST) {
            this.fillWithBlocks(var1, var3, 0, 1, 1, 0, 3, 3, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
         } else {
            this.fillWithBlocks(var1, var3, 4, 1, 1, 4, 3, 3, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
         }

         return true;
      }
   }
}
