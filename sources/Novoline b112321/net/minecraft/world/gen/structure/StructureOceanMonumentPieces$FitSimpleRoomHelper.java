package net.minecraft.world.gen.structure;

import java.util.Random;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.gen.structure.StructureOceanMonumentPieces$1;
import net.minecraft.world.gen.structure.StructureOceanMonumentPieces$MonumentRoomFitHelper;
import net.minecraft.world.gen.structure.StructureOceanMonumentPieces$Piece;
import net.minecraft.world.gen.structure.StructureOceanMonumentPieces$RoomDefinition;
import net.minecraft.world.gen.structure.StructureOceanMonumentPieces$SimpleRoom;

class StructureOceanMonumentPieces$FitSimpleRoomHelper implements StructureOceanMonumentPieces$MonumentRoomFitHelper {
   private StructureOceanMonumentPieces$FitSimpleRoomHelper() {
   }

   public boolean func_175969_a(StructureOceanMonumentPieces$RoomDefinition var1) {
      return true;
   }

   public StructureOceanMonumentPieces$Piece func_175968_a(EnumFacing var1, StructureOceanMonumentPieces$RoomDefinition var2, Random var3) {
      var2.field_175963_d = true;
      return new StructureOceanMonumentPieces$SimpleRoom(var1, var2, var3);
   }

   StructureOceanMonumentPieces$FitSimpleRoomHelper(StructureOceanMonumentPieces$1 var1) {
      this();
   }
}
