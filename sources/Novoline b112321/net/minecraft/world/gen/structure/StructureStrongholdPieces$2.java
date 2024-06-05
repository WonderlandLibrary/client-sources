package net.minecraft.world.gen.structure;

import net.minecraft.world.gen.structure.StructureStrongholdPieces$PieceWeight;

final class StructureStrongholdPieces$2 extends StructureStrongholdPieces$PieceWeight {
   StructureStrongholdPieces$2(Class var1, int var2, int var3) {
      super(var1, var2, var3);
   }

   public boolean canSpawnMoreStructuresOfType(int var1) {
      return super.canSpawnMoreStructuresOfType(var1) && var1 > 5;
   }
}
