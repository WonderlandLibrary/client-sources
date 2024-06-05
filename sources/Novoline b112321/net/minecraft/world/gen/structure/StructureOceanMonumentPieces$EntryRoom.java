package net.minecraft.world.gen.structure;

import java.util.Random;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureOceanMonumentPieces$Piece;
import net.minecraft.world.gen.structure.StructureOceanMonumentPieces$RoomDefinition;

public class StructureOceanMonumentPieces$EntryRoom extends StructureOceanMonumentPieces$Piece {
   public StructureOceanMonumentPieces$EntryRoom() {
   }

   public StructureOceanMonumentPieces$EntryRoom(EnumFacing var1, StructureOceanMonumentPieces$RoomDefinition var2) {
      super(1, var1, var2, 1, 1, 1);
   }

   public boolean addComponentParts(World var1, Random var2, StructureBoundingBox var3) {
      this.fillWithBlocks(var1, var3, 0, 3, 0, 2, 3, 7, field_175826_b, field_175826_b, false);
      this.fillWithBlocks(var1, var3, 5, 3, 0, 7, 3, 7, field_175826_b, field_175826_b, false);
      this.fillWithBlocks(var1, var3, 0, 2, 0, 1, 2, 7, field_175826_b, field_175826_b, false);
      this.fillWithBlocks(var1, var3, 6, 2, 0, 7, 2, 7, field_175826_b, field_175826_b, false);
      this.fillWithBlocks(var1, var3, 0, 1, 0, 0, 1, 7, field_175826_b, field_175826_b, false);
      this.fillWithBlocks(var1, var3, 7, 1, 0, 7, 1, 7, field_175826_b, field_175826_b, false);
      this.fillWithBlocks(var1, var3, 0, 1, 7, 7, 3, 7, field_175826_b, field_175826_b, false);
      this.fillWithBlocks(var1, var3, 1, 1, 0, 2, 3, 0, field_175826_b, field_175826_b, false);
      this.fillWithBlocks(var1, var3, 5, 1, 0, 6, 3, 0, field_175826_b, field_175826_b, false);
      if(this.field_175830_k.field_175966_c[EnumFacing.NORTH.getIndex()]) {
         this.func_181655_a(var1, var3, 3, 1, 7, 4, 2, 7, false);
      }

      if(this.field_175830_k.field_175966_c[EnumFacing.WEST.getIndex()]) {
         this.func_181655_a(var1, var3, 0, 1, 3, 1, 2, 4, false);
      }

      if(this.field_175830_k.field_175966_c[EnumFacing.EAST.getIndex()]) {
         this.func_181655_a(var1, var3, 6, 1, 3, 7, 2, 4, false);
      }

      return true;
   }
}
