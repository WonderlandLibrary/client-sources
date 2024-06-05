package net.minecraft.world.gen.structure;

import java.util.Random;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureOceanMonumentPieces$Piece;
import net.minecraft.world.gen.structure.StructureOceanMonumentPieces$RoomDefinition;

public class StructureOceanMonumentPieces$DoubleXYRoom extends StructureOceanMonumentPieces$Piece {
   public StructureOceanMonumentPieces$DoubleXYRoom() {
   }

   public StructureOceanMonumentPieces$DoubleXYRoom(EnumFacing var1, StructureOceanMonumentPieces$RoomDefinition var2, Random var3) {
      super(1, var1, var2, 2, 2, 1);
   }

   public boolean addComponentParts(World var1, Random var2, StructureBoundingBox var3) {
      StructureOceanMonumentPieces$RoomDefinition var4 = this.field_175830_k.field_175965_b[EnumFacing.EAST.getIndex()];
      StructureOceanMonumentPieces$RoomDefinition var5 = this.field_175830_k;
      StructureOceanMonumentPieces$RoomDefinition var6 = var5.field_175965_b[EnumFacing.UP.getIndex()];
      StructureOceanMonumentPieces$RoomDefinition var7 = var4.field_175965_b[EnumFacing.UP.getIndex()];
      if(this.field_175830_k.field_175967_a / 25 > 0) {
         this.func_175821_a(var1, var3, 8, 0, var4.field_175966_c[EnumFacing.DOWN.getIndex()]);
         this.func_175821_a(var1, var3, 0, 0, var5.field_175966_c[EnumFacing.DOWN.getIndex()]);
      }

      if(var6.field_175965_b[EnumFacing.UP.getIndex()] == null) {
         this.func_175819_a(var1, var3, 1, 8, 1, 7, 8, 6, field_175828_a);
      }

      if(var7.field_175965_b[EnumFacing.UP.getIndex()] == null) {
         this.func_175819_a(var1, var3, 8, 8, 1, 14, 8, 6, field_175828_a);
      }

      for(int var8 = 1; var8 <= 7; ++var8) {
         IBlockState var9 = field_175826_b;
         if(var8 == 2 || var8 == 6) {
            var9 = field_175828_a;
         }

         this.fillWithBlocks(var1, var3, 0, var8, 0, 0, var8, 7, var9, var9, false);
         this.fillWithBlocks(var1, var3, 15, var8, 0, 15, var8, 7, var9, var9, false);
         this.fillWithBlocks(var1, var3, 1, var8, 0, 15, var8, 0, var9, var9, false);
         this.fillWithBlocks(var1, var3, 1, var8, 7, 14, var8, 7, var9, var9, false);
      }

      this.fillWithBlocks(var1, var3, 2, 1, 3, 2, 7, 4, field_175826_b, field_175826_b, false);
      this.fillWithBlocks(var1, var3, 3, 1, 2, 4, 7, 2, field_175826_b, field_175826_b, false);
      this.fillWithBlocks(var1, var3, 3, 1, 5, 4, 7, 5, field_175826_b, field_175826_b, false);
      this.fillWithBlocks(var1, var3, 13, 1, 3, 13, 7, 4, field_175826_b, field_175826_b, false);
      this.fillWithBlocks(var1, var3, 11, 1, 2, 12, 7, 2, field_175826_b, field_175826_b, false);
      this.fillWithBlocks(var1, var3, 11, 1, 5, 12, 7, 5, field_175826_b, field_175826_b, false);
      this.fillWithBlocks(var1, var3, 5, 1, 3, 5, 3, 4, field_175826_b, field_175826_b, false);
      this.fillWithBlocks(var1, var3, 10, 1, 3, 10, 3, 4, field_175826_b, field_175826_b, false);
      this.fillWithBlocks(var1, var3, 5, 7, 2, 10, 7, 5, field_175826_b, field_175826_b, false);
      this.fillWithBlocks(var1, var3, 5, 5, 2, 5, 7, 2, field_175826_b, field_175826_b, false);
      this.fillWithBlocks(var1, var3, 10, 5, 2, 10, 7, 2, field_175826_b, field_175826_b, false);
      this.fillWithBlocks(var1, var3, 5, 5, 5, 5, 7, 5, field_175826_b, field_175826_b, false);
      this.fillWithBlocks(var1, var3, 10, 5, 5, 10, 7, 5, field_175826_b, field_175826_b, false);
      this.setBlockState(var1, field_175826_b, 6, 6, 2, var3);
      this.setBlockState(var1, field_175826_b, 9, 6, 2, var3);
      this.setBlockState(var1, field_175826_b, 6, 6, 5, var3);
      this.setBlockState(var1, field_175826_b, 9, 6, 5, var3);
      this.fillWithBlocks(var1, var3, 5, 4, 3, 6, 4, 4, field_175826_b, field_175826_b, false);
      this.fillWithBlocks(var1, var3, 9, 4, 3, 10, 4, 4, field_175826_b, field_175826_b, false);
      this.setBlockState(var1, field_175825_e, 5, 4, 2, var3);
      this.setBlockState(var1, field_175825_e, 5, 4, 5, var3);
      this.setBlockState(var1, field_175825_e, 10, 4, 2, var3);
      this.setBlockState(var1, field_175825_e, 10, 4, 5, var3);
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

      if(var6.field_175966_c[EnumFacing.SOUTH.getIndex()]) {
         this.func_181655_a(var1, var3, 3, 5, 0, 4, 6, 0, false);
      }

      if(var6.field_175966_c[EnumFacing.NORTH.getIndex()]) {
         this.func_181655_a(var1, var3, 3, 5, 7, 4, 6, 7, false);
      }

      if(var6.field_175966_c[EnumFacing.WEST.getIndex()]) {
         this.func_181655_a(var1, var3, 0, 5, 3, 0, 6, 4, false);
      }

      if(var7.field_175966_c[EnumFacing.SOUTH.getIndex()]) {
         this.func_181655_a(var1, var3, 11, 5, 0, 12, 6, 0, false);
      }

      if(var7.field_175966_c[EnumFacing.NORTH.getIndex()]) {
         this.func_181655_a(var1, var3, 11, 5, 7, 12, 6, 7, false);
      }

      if(var7.field_175966_c[EnumFacing.EAST.getIndex()]) {
         this.func_181655_a(var1, var3, 15, 5, 3, 15, 6, 4, false);
      }

      return true;
   }
}
