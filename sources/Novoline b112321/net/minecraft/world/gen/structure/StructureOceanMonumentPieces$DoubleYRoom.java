package net.minecraft.world.gen.structure;

import java.util.Random;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureOceanMonumentPieces$Piece;
import net.minecraft.world.gen.structure.StructureOceanMonumentPieces$RoomDefinition;

public class StructureOceanMonumentPieces$DoubleYRoom extends StructureOceanMonumentPieces$Piece {
   public StructureOceanMonumentPieces$DoubleYRoom() {
   }

   public StructureOceanMonumentPieces$DoubleYRoom(EnumFacing var1, StructureOceanMonumentPieces$RoomDefinition var2, Random var3) {
      super(1, var1, var2, 1, 2, 1);
   }

   public boolean addComponentParts(World var1, Random var2, StructureBoundingBox var3) {
      if(this.field_175830_k.field_175967_a / 25 > 0) {
         this.func_175821_a(var1, var3, 0, 0, this.field_175830_k.field_175966_c[EnumFacing.DOWN.getIndex()]);
      }

      StructureOceanMonumentPieces$RoomDefinition var4 = this.field_175830_k.field_175965_b[EnumFacing.UP.getIndex()];
      if(var4.field_175965_b[EnumFacing.UP.getIndex()] == null) {
         this.func_175819_a(var1, var3, 1, 8, 1, 6, 8, 6, field_175828_a);
      }

      this.fillWithBlocks(var1, var3, 0, 4, 0, 0, 4, 7, field_175826_b, field_175826_b, false);
      this.fillWithBlocks(var1, var3, 7, 4, 0, 7, 4, 7, field_175826_b, field_175826_b, false);
      this.fillWithBlocks(var1, var3, 1, 4, 0, 6, 4, 0, field_175826_b, field_175826_b, false);
      this.fillWithBlocks(var1, var3, 1, 4, 7, 6, 4, 7, field_175826_b, field_175826_b, false);
      this.fillWithBlocks(var1, var3, 2, 4, 1, 2, 4, 2, field_175826_b, field_175826_b, false);
      this.fillWithBlocks(var1, var3, 1, 4, 2, 1, 4, 2, field_175826_b, field_175826_b, false);
      this.fillWithBlocks(var1, var3, 5, 4, 1, 5, 4, 2, field_175826_b, field_175826_b, false);
      this.fillWithBlocks(var1, var3, 6, 4, 2, 6, 4, 2, field_175826_b, field_175826_b, false);
      this.fillWithBlocks(var1, var3, 2, 4, 5, 2, 4, 6, field_175826_b, field_175826_b, false);
      this.fillWithBlocks(var1, var3, 1, 4, 5, 1, 4, 5, field_175826_b, field_175826_b, false);
      this.fillWithBlocks(var1, var3, 5, 4, 5, 5, 4, 6, field_175826_b, field_175826_b, false);
      this.fillWithBlocks(var1, var3, 6, 4, 5, 6, 4, 5, field_175826_b, field_175826_b, false);
      StructureOceanMonumentPieces$RoomDefinition var5 = this.field_175830_k;

      for(int var6 = 1; var6 <= 5; var6 += 4) {
         byte var7 = 0;
         if(var5.field_175966_c[EnumFacing.SOUTH.getIndex()]) {
            this.fillWithBlocks(var1, var3, 2, var6, var7, 2, var6 + 2, var7, field_175826_b, field_175826_b, false);
            this.fillWithBlocks(var1, var3, 5, var6, var7, 5, var6 + 2, var7, field_175826_b, field_175826_b, false);
            this.fillWithBlocks(var1, var3, 3, var6 + 2, var7, 4, var6 + 2, var7, field_175826_b, field_175826_b, false);
         } else {
            this.fillWithBlocks(var1, var3, 0, var6, var7, 7, var6 + 2, var7, field_175826_b, field_175826_b, false);
            this.fillWithBlocks(var1, var3, 0, var6 + 1, var7, 7, var6 + 1, var7, field_175828_a, field_175828_a, false);
         }

         var7 = 7;
         if(var5.field_175966_c[EnumFacing.NORTH.getIndex()]) {
            this.fillWithBlocks(var1, var3, 2, var6, var7, 2, var6 + 2, var7, field_175826_b, field_175826_b, false);
            this.fillWithBlocks(var1, var3, 5, var6, var7, 5, var6 + 2, var7, field_175826_b, field_175826_b, false);
            this.fillWithBlocks(var1, var3, 3, var6 + 2, var7, 4, var6 + 2, var7, field_175826_b, field_175826_b, false);
         } else {
            this.fillWithBlocks(var1, var3, 0, var6, var7, 7, var6 + 2, var7, field_175826_b, field_175826_b, false);
            this.fillWithBlocks(var1, var3, 0, var6 + 1, var7, 7, var6 + 1, var7, field_175828_a, field_175828_a, false);
         }

         byte var8 = 0;
         if(var5.field_175966_c[EnumFacing.WEST.getIndex()]) {
            this.fillWithBlocks(var1, var3, var8, var6, 2, var8, var6 + 2, 2, field_175826_b, field_175826_b, false);
            this.fillWithBlocks(var1, var3, var8, var6, 5, var8, var6 + 2, 5, field_175826_b, field_175826_b, false);
            this.fillWithBlocks(var1, var3, var8, var6 + 2, 3, var8, var6 + 2, 4, field_175826_b, field_175826_b, false);
         } else {
            this.fillWithBlocks(var1, var3, var8, var6, 0, var8, var6 + 2, 7, field_175826_b, field_175826_b, false);
            this.fillWithBlocks(var1, var3, var8, var6 + 1, 0, var8, var6 + 1, 7, field_175828_a, field_175828_a, false);
         }

         var8 = 7;
         if(var5.field_175966_c[EnumFacing.EAST.getIndex()]) {
            this.fillWithBlocks(var1, var3, var8, var6, 2, var8, var6 + 2, 2, field_175826_b, field_175826_b, false);
            this.fillWithBlocks(var1, var3, var8, var6, 5, var8, var6 + 2, 5, field_175826_b, field_175826_b, false);
            this.fillWithBlocks(var1, var3, var8, var6 + 2, 3, var8, var6 + 2, 4, field_175826_b, field_175826_b, false);
         } else {
            this.fillWithBlocks(var1, var3, var8, var6, 0, var8, var6 + 2, 7, field_175826_b, field_175826_b, false);
            this.fillWithBlocks(var1, var3, var8, var6 + 1, 0, var8, var6 + 1, 7, field_175828_a, field_175828_a, false);
         }

         var5 = var4;
      }

      return true;
   }
}
