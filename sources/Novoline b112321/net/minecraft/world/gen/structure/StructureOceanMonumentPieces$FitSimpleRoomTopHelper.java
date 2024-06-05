package net.minecraft.world.gen.structure;

import java.util.Random;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.gen.structure.StructureOceanMonumentPieces$1;
import net.minecraft.world.gen.structure.StructureOceanMonumentPieces$MonumentRoomFitHelper;
import net.minecraft.world.gen.structure.StructureOceanMonumentPieces$Piece;
import net.minecraft.world.gen.structure.StructureOceanMonumentPieces$RoomDefinition;
import net.minecraft.world.gen.structure.StructureOceanMonumentPieces$SimpleTopRoom;

class StructureOceanMonumentPieces$FitSimpleRoomTopHelper implements StructureOceanMonumentPieces$MonumentRoomFitHelper {
   private StructureOceanMonumentPieces$FitSimpleRoomTopHelper() {
   }

   public boolean func_175969_a(StructureOceanMonumentPieces$RoomDefinition var1) {
      return !var1.field_175966_c[EnumFacing.WEST.getIndex()] && !var1.field_175966_c[EnumFacing.EAST.getIndex()] && !var1.field_175966_c[EnumFacing.NORTH.getIndex()] && !var1.field_175966_c[EnumFacing.SOUTH.getIndex()] && !var1.field_175966_c[EnumFacing.UP.getIndex()];
   }

   public StructureOceanMonumentPieces$Piece func_175968_a(EnumFacing var1, StructureOceanMonumentPieces$RoomDefinition var2, Random var3) {
      var2.field_175963_d = true;
      return new StructureOceanMonumentPieces$SimpleTopRoom(var1, var2, var3);
   }

   StructureOceanMonumentPieces$FitSimpleRoomTopHelper(StructureOceanMonumentPieces$1 var1) {
      this();
   }
}
