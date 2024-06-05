package net.minecraft.world.gen.structure;

import java.util.Random;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.gen.structure.StructureOceanMonumentPieces$1;
import net.minecraft.world.gen.structure.StructureOceanMonumentPieces$DoubleZRoom;
import net.minecraft.world.gen.structure.StructureOceanMonumentPieces$MonumentRoomFitHelper;
import net.minecraft.world.gen.structure.StructureOceanMonumentPieces$Piece;
import net.minecraft.world.gen.structure.StructureOceanMonumentPieces$RoomDefinition;

class StructureOceanMonumentPieces$ZDoubleRoomFitHelper implements StructureOceanMonumentPieces$MonumentRoomFitHelper {
   private StructureOceanMonumentPieces$ZDoubleRoomFitHelper() {
   }

   public boolean func_175969_a(StructureOceanMonumentPieces$RoomDefinition var1) {
      return var1.field_175966_c[EnumFacing.NORTH.getIndex()] && !var1.field_175965_b[EnumFacing.NORTH.getIndex()].field_175963_d;
   }

   public StructureOceanMonumentPieces$Piece func_175968_a(EnumFacing var1, StructureOceanMonumentPieces$RoomDefinition var2, Random var3) {
      StructureOceanMonumentPieces$RoomDefinition var4 = var2;
      if(!var2.field_175966_c[EnumFacing.NORTH.getIndex()] || var2.field_175965_b[EnumFacing.NORTH.getIndex()].field_175963_d) {
         var4 = var2.field_175965_b[EnumFacing.SOUTH.getIndex()];
      }

      var4.field_175963_d = true;
      var4.field_175965_b[EnumFacing.NORTH.getIndex()].field_175963_d = true;
      return new StructureOceanMonumentPieces$DoubleZRoom(var1, var4, var3);
   }

   StructureOceanMonumentPieces$ZDoubleRoomFitHelper(StructureOceanMonumentPieces$1 var1) {
      this();
   }
}
