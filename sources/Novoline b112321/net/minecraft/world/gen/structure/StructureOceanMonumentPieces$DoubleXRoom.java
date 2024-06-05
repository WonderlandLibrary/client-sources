package net.minecraft.world.gen.structure;

import java.util.Random;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureOceanMonumentPieces$Piece;
import net.minecraft.world.gen.structure.StructureOceanMonumentPieces$RoomDefinition;

public class StructureOceanMonumentPieces$DoubleXRoom extends StructureOceanMonumentPieces$Piece {
   public StructureOceanMonumentPieces$DoubleXRoom() {
   }

   public StructureOceanMonumentPieces$DoubleXRoom(EnumFacing var1, StructureOceanMonumentPieces$RoomDefinition var2, Random var3) {
      super(1, var1, var2, 2, 1, 1);
   }

   public boolean addComponentParts(World var1, Random var2, StructureBoundingBox var3) {
      StructureOceanMonumentPieces$RoomDefinition var4 = this.field_175830_k.field_175965_b[EnumFacing.EAST.getIndex()];
      StructureOceanMonumentPieces$RoomDefinition var5 = this.field_175830_k;
      if(this.field_175830_k.field_175967_a / 25 > 0) {
         this.func_175821_a(var1, var3, 8, 0, var4.field_175966_c[EnumFacing.DOWN.getIndex()]);
         this.func_175821_a(var1, var3, 0, 0, var5.field_175966_c[EnumFacing.DOWN.getIndex()]);
      }

      if(var5.field_175965_b[EnumFacing.UP.getIndex()] == null) {
         this.func_175819_a(var1, var3, 1, 4, 1, 7, 4, 6, field_175828_a);
      }

      if(var4.field_175965_b[EnumFacing.UP.getIndex()] == null) {
         this.func_175819_a(var1, var3, 8, 4, 1, 14, 4, 6, field_175828_a);
      }

      this.fillWithBlocks(var1, var3, 0, 3, 0, 0, 3, 7, field_175826_b, field_175826_b, false);
      this.fillWithBlocks(var1, var3, 15, 3, 0, 15, 3, 7, field_175826_b, field_175826_b, false);
      this.fillWithBlocks(var1, var3, 1, 3, 0, 15, 3, 0, field_175826_b, field_175826_b, false);
      this.fillWithBlocks(var1, var3, 1, 3, 7, 14, 3, 7, field_175826_b, field_175826_b, false);
      this.fillWithBlocks(var1, var3, 0, 2, 0, 0, 2, 7, field_175828_a, field_175828_a, false);
      this.fillWithBlocks(var1, var3, 15, 2, 0, 15, 2, 7, field_175828_a, field_175828_a, false);
      this.fillWithBlocks(var1, var3, 1, 2, 0, 15, 2, 0, field_175828_a, field_175828_a, false);
      this.fillWithBlocks(var1, var3, 1, 2, 7, 14, 2, 7, field_175828_a, field_175828_a, false);
      this.fillWithBlocks(var1, var3, 0, 1, 0, 0, 1, 7, field_175826_b, field_175826_b, false);
      this.fillWithBlocks(var1, var3, 15, 1, 0, 15, 1, 7, field_175826_b, field_175826_b, false);
      this.fillWithBlocks(var1, var3, 1, 1, 0, 15, 1, 0, field_175826_b, field_175826_b, false);
      this.fillWithBlocks(var1, var3, 1, 1, 7, 14, 1, 7, field_175826_b, field_175826_b, false);
      this.fillWithBlocks(var1, var3, 5, 1, 0, 10, 1, 4, field_175826_b, field_175826_b, false);
      this.fillWithBlocks(var1, var3, 6, 2, 0, 9, 2, 3, field_175828_a, field_175828_a, false);
      this.fillWithBlocks(var1, var3, 5, 3, 0, 10, 3, 4, field_175826_b, field_175826_b, false);
      this.setBlockState(var1, field_175825_e, 6, 2, 3, var3);
      this.setBlockState(var1, field_175825_e, 9, 2, 3, var3);
      if(var5.field_175966_c[EnumFacing.SOUTH.getIndex()]) {
         this.func_181655_a(var1, var3, 3, 1, 0, 4, 2, 0, false);
      }

      if(var5.field_175966_c[EnumFacing.NORTH.getIndex()]) {
         this.func_181655_a(var1, var3, 3, 1, 7, 4, 2, 7, false);
      }

      if(var5.field_175966_c[EnumFacing.WEST.getIndex()]) {
         this.func_181655_a(var1, var3, 0, 1, 3, 0, 2, 4, false);
      }

      if(var4.field_175966_c[EnumFacing.SOUTH.getIndex()]) {
         this.func_181655_a(var1, var3, 11, 1, 0, 12, 2, 0, false);
      }

      if(var4.field_175966_c[EnumFacing.NORTH.getIndex()]) {
         this.func_181655_a(var1, var3, 11, 1, 7, 12, 2, 7, false);
      }

      if(var4.field_175966_c[EnumFacing.EAST.getIndex()]) {
         this.func_181655_a(var1, var3, 15, 1, 3, 15, 2, 4, false);
      }

      return true;
   }
}
