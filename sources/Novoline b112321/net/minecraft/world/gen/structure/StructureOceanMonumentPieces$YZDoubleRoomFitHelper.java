package net.minecraft.world.gen.structure;

import java.util.Random;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.gen.structure.StructureOceanMonumentPieces$1;
import net.minecraft.world.gen.structure.StructureOceanMonumentPieces$DoubleYZRoom;
import net.minecraft.world.gen.structure.StructureOceanMonumentPieces$MonumentRoomFitHelper;
import net.minecraft.world.gen.structure.StructureOceanMonumentPieces$Piece;
import net.minecraft.world.gen.structure.StructureOceanMonumentPieces$RoomDefinition;

class StructureOceanMonumentPieces$YZDoubleRoomFitHelper implements StructureOceanMonumentPieces$MonumentRoomFitHelper {
   private StructureOceanMonumentPieces$YZDoubleRoomFitHelper() {
   }

   public boolean func_175969_a(StructureOceanMonumentPieces$RoomDefinition var1) {
      if(var1.field_175966_c[EnumFacing.NORTH.getIndex()] && !var1.field_175965_b[EnumFacing.NORTH.getIndex()].field_175963_d && var1.field_175966_c[EnumFacing.UP.getIndex()] && !var1.field_175965_b[EnumFacing.UP.getIndex()].field_175963_d) {
         StructureOceanMonumentPieces$RoomDefinition var2 = var1.field_175965_b[EnumFacing.NORTH.getIndex()];
         return var2.field_175966_c[EnumFacing.UP.getIndex()] && !var2.field_175965_b[EnumFacing.UP.getIndex()].field_175963_d;
      } else {
         return false;
      }
   }

   public StructureOceanMonumentPieces$Piece func_175968_a(EnumFacing var1, StructureOceanMonumentPieces$RoomDefinition var2, Random var3) {
      var2.field_175963_d = true;
      var2.field_175965_b[EnumFacing.NORTH.getIndex()].field_175963_d = true;
      var2.field_175965_b[EnumFacing.UP.getIndex()].field_175963_d = true;
      var2.field_175965_b[EnumFacing.NORTH.getIndex()].field_175965_b[EnumFacing.UP.getIndex()].field_175963_d = true;
      return new StructureOceanMonumentPieces$DoubleYZRoom(var1, var2, var3);
   }

   StructureOceanMonumentPieces$YZDoubleRoomFitHelper(StructureOceanMonumentPieces$1 var1) {
      this();
   }
}
