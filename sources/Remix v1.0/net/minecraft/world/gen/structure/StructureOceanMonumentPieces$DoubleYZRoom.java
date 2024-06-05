package net.minecraft.world.gen.structure;

import net.minecraft.util.*;
import java.util.*;
import net.minecraft.world.*;
import net.minecraft.block.state.*;

public static class DoubleYZRoom extends Piece
{
    public DoubleYZRoom() {
    }
    
    public DoubleYZRoom(final EnumFacing p_i45594_1_, final RoomDefinition p_i45594_2_, final Random p_i45594_3_) {
        super(1, p_i45594_1_, p_i45594_2_, 1, 2, 2);
    }
    
    @Override
    public boolean addComponentParts(final World worldIn, final Random p_74875_2_, final StructureBoundingBox p_74875_3_) {
        final RoomDefinition var4 = this.field_175830_k.field_175965_b[EnumFacing.NORTH.getIndex()];
        final RoomDefinition var5 = this.field_175830_k;
        final RoomDefinition var6 = var4.field_175965_b[EnumFacing.UP.getIndex()];
        final RoomDefinition var7 = var5.field_175965_b[EnumFacing.UP.getIndex()];
        if (this.field_175830_k.field_175967_a / 25 > 0) {
            this.func_175821_a(worldIn, p_74875_3_, 0, 8, var4.field_175966_c[EnumFacing.DOWN.getIndex()]);
            this.func_175821_a(worldIn, p_74875_3_, 0, 0, var5.field_175966_c[EnumFacing.DOWN.getIndex()]);
        }
        if (var7.field_175965_b[EnumFacing.UP.getIndex()] == null) {
            this.func_175819_a(worldIn, p_74875_3_, 1, 8, 1, 6, 8, 7, DoubleYZRoom.field_175828_a);
        }
        if (var6.field_175965_b[EnumFacing.UP.getIndex()] == null) {
            this.func_175819_a(worldIn, p_74875_3_, 1, 8, 8, 6, 8, 14, DoubleYZRoom.field_175828_a);
        }
        for (int var8 = 1; var8 <= 7; ++var8) {
            IBlockState var9 = DoubleYZRoom.field_175826_b;
            if (var8 == 2 || var8 == 6) {
                var9 = DoubleYZRoom.field_175828_a;
            }
            this.func_175804_a(worldIn, p_74875_3_, 0, var8, 0, 0, var8, 15, var9, var9, false);
            this.func_175804_a(worldIn, p_74875_3_, 7, var8, 0, 7, var8, 15, var9, var9, false);
            this.func_175804_a(worldIn, p_74875_3_, 1, var8, 0, 6, var8, 0, var9, var9, false);
            this.func_175804_a(worldIn, p_74875_3_, 1, var8, 15, 6, var8, 15, var9, var9, false);
        }
        for (int var8 = 1; var8 <= 7; ++var8) {
            IBlockState var9 = DoubleYZRoom.field_175827_c;
            if (var8 == 2 || var8 == 6) {
                var9 = DoubleYZRoom.field_175825_e;
            }
            this.func_175804_a(worldIn, p_74875_3_, 3, var8, 7, 4, var8, 8, var9, var9, false);
        }
        if (var5.field_175966_c[EnumFacing.SOUTH.getIndex()]) {
            this.func_175804_a(worldIn, p_74875_3_, 3, 1, 0, 4, 2, 0, DoubleYZRoom.field_175822_f, DoubleYZRoom.field_175822_f, false);
        }
        if (var5.field_175966_c[EnumFacing.EAST.getIndex()]) {
            this.func_175804_a(worldIn, p_74875_3_, 7, 1, 3, 7, 2, 4, DoubleYZRoom.field_175822_f, DoubleYZRoom.field_175822_f, false);
        }
        if (var5.field_175966_c[EnumFacing.WEST.getIndex()]) {
            this.func_175804_a(worldIn, p_74875_3_, 0, 1, 3, 0, 2, 4, DoubleYZRoom.field_175822_f, DoubleYZRoom.field_175822_f, false);
        }
        if (var4.field_175966_c[EnumFacing.NORTH.getIndex()]) {
            this.func_175804_a(worldIn, p_74875_3_, 3, 1, 15, 4, 2, 15, DoubleYZRoom.field_175822_f, DoubleYZRoom.field_175822_f, false);
        }
        if (var4.field_175966_c[EnumFacing.WEST.getIndex()]) {
            this.func_175804_a(worldIn, p_74875_3_, 0, 1, 11, 0, 2, 12, DoubleYZRoom.field_175822_f, DoubleYZRoom.field_175822_f, false);
        }
        if (var4.field_175966_c[EnumFacing.EAST.getIndex()]) {
            this.func_175804_a(worldIn, p_74875_3_, 7, 1, 11, 7, 2, 12, DoubleYZRoom.field_175822_f, DoubleYZRoom.field_175822_f, false);
        }
        if (var7.field_175966_c[EnumFacing.SOUTH.getIndex()]) {
            this.func_175804_a(worldIn, p_74875_3_, 3, 5, 0, 4, 6, 0, DoubleYZRoom.field_175822_f, DoubleYZRoom.field_175822_f, false);
        }
        if (var7.field_175966_c[EnumFacing.EAST.getIndex()]) {
            this.func_175804_a(worldIn, p_74875_3_, 7, 5, 3, 7, 6, 4, DoubleYZRoom.field_175822_f, DoubleYZRoom.field_175822_f, false);
            this.func_175804_a(worldIn, p_74875_3_, 5, 4, 2, 6, 4, 5, DoubleYZRoom.field_175826_b, DoubleYZRoom.field_175826_b, false);
            this.func_175804_a(worldIn, p_74875_3_, 6, 1, 2, 6, 3, 2, DoubleYZRoom.field_175826_b, DoubleYZRoom.field_175826_b, false);
            this.func_175804_a(worldIn, p_74875_3_, 6, 1, 5, 6, 3, 5, DoubleYZRoom.field_175826_b, DoubleYZRoom.field_175826_b, false);
        }
        if (var7.field_175966_c[EnumFacing.WEST.getIndex()]) {
            this.func_175804_a(worldIn, p_74875_3_, 0, 5, 3, 0, 6, 4, DoubleYZRoom.field_175822_f, DoubleYZRoom.field_175822_f, false);
            this.func_175804_a(worldIn, p_74875_3_, 1, 4, 2, 2, 4, 5, DoubleYZRoom.field_175826_b, DoubleYZRoom.field_175826_b, false);
            this.func_175804_a(worldIn, p_74875_3_, 1, 1, 2, 1, 3, 2, DoubleYZRoom.field_175826_b, DoubleYZRoom.field_175826_b, false);
            this.func_175804_a(worldIn, p_74875_3_, 1, 1, 5, 1, 3, 5, DoubleYZRoom.field_175826_b, DoubleYZRoom.field_175826_b, false);
        }
        if (var6.field_175966_c[EnumFacing.NORTH.getIndex()]) {
            this.func_175804_a(worldIn, p_74875_3_, 3, 5, 15, 4, 6, 15, DoubleYZRoom.field_175822_f, DoubleYZRoom.field_175822_f, false);
        }
        if (var6.field_175966_c[EnumFacing.WEST.getIndex()]) {
            this.func_175804_a(worldIn, p_74875_3_, 0, 5, 11, 0, 6, 12, DoubleYZRoom.field_175822_f, DoubleYZRoom.field_175822_f, false);
            this.func_175804_a(worldIn, p_74875_3_, 1, 4, 10, 2, 4, 13, DoubleYZRoom.field_175826_b, DoubleYZRoom.field_175826_b, false);
            this.func_175804_a(worldIn, p_74875_3_, 1, 1, 10, 1, 3, 10, DoubleYZRoom.field_175826_b, DoubleYZRoom.field_175826_b, false);
            this.func_175804_a(worldIn, p_74875_3_, 1, 1, 13, 1, 3, 13, DoubleYZRoom.field_175826_b, DoubleYZRoom.field_175826_b, false);
        }
        if (var6.field_175966_c[EnumFacing.EAST.getIndex()]) {
            this.func_175804_a(worldIn, p_74875_3_, 7, 5, 11, 7, 6, 12, DoubleYZRoom.field_175822_f, DoubleYZRoom.field_175822_f, false);
            this.func_175804_a(worldIn, p_74875_3_, 5, 4, 10, 6, 4, 13, DoubleYZRoom.field_175826_b, DoubleYZRoom.field_175826_b, false);
            this.func_175804_a(worldIn, p_74875_3_, 6, 1, 10, 6, 3, 10, DoubleYZRoom.field_175826_b, DoubleYZRoom.field_175826_b, false);
            this.func_175804_a(worldIn, p_74875_3_, 6, 1, 13, 6, 3, 13, DoubleYZRoom.field_175826_b, DoubleYZRoom.field_175826_b, false);
        }
        return true;
    }
}
