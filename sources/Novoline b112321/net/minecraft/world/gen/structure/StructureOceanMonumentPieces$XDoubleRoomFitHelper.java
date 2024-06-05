package net.minecraft.world.gen.structure;

import java.util.Random;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.gen.structure.StructureOceanMonumentPieces$1;
import net.minecraft.world.gen.structure.StructureOceanMonumentPieces$DoubleXRoom;
import net.minecraft.world.gen.structure.StructureOceanMonumentPieces$MonumentRoomFitHelper;
import net.minecraft.world.gen.structure.StructureOceanMonumentPieces$Piece;
import net.minecraft.world.gen.structure.StructureOceanMonumentPieces$RoomDefinition;

class StructureOceanMonumentPieces$XDoubleRoomFitHelper implements StructureOceanMonumentPieces$MonumentRoomFitHelper {
   private StructureOceanMonumentPieces$XDoubleRoomFitHelper() {
   }

   public boolean func_175969_a(StructureOceanMonumentPieces$RoomDefinition var1) {
      return var1.field_175966_c[EnumFacing.EAST.getIndex()] && !var1.field_175965_b[EnumFacing.EAST.getIndex()].field_175963_d;
   }

   public StructureOceanMonumentPieces$Piece func_175968_a(EnumFacing var1, StructureOceanMonumentPieces$RoomDefinition var2, Random var3) {
      var2.field_175963_d = true;
      var2.field_175965_b[EnumFacing.EAST.getIndex()].field_175963_d = true;
      return new StructureOceanMonumentPieces$DoubleXRoom(var1, var2, var3);
   }

   StructureOceanMonumentPieces$XDoubleRoomFitHelper(StructureOceanMonumentPieces$1 var1) {
      this();
   }
}
