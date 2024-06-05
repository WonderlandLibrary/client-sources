package net.minecraft.world.gen.structure;

import net.minecraft.util.*;
import java.util.*;
import net.minecraft.world.*;
import net.minecraft.block.state.*;

public static class DoubleXYRoom extends Piece
{
    public DoubleXYRoom() {
    }
    
    public DoubleXYRoom(final EnumFacing p_i45596_1_, final RoomDefinition p_i45596_2_, final Random p_i45596_3_) {
        super(1, p_i45596_1_, p_i45596_2_, 2, 2, 1);
    }
    
    @Override
    public boolean addComponentParts(final World worldIn, final Random p_74875_2_, final StructureBoundingBox p_74875_3_) {
        final RoomDefinition var4 = this.field_175830_k.field_175965_b[EnumFacing.EAST.getIndex()];
        final RoomDefinition var5 = this.field_175830_k;
        final RoomDefinition var6 = var5.field_175965_b[EnumFacing.UP.getIndex()];
        final RoomDefinition var7 = var4.field_175965_b[EnumFacing.UP.getIndex()];
        if (this.field_175830_k.field_175967_a / 25 > 0) {
            this.func_175821_a(worldIn, p_74875_3_, 8, 0, var4.field_175966_c[EnumFacing.DOWN.getIndex()]);
            this.func_175821_a(worldIn, p_74875_3_, 0, 0, var5.field_175966_c[EnumFacing.DOWN.getIndex()]);
        }
        if (var6.field_175965_b[EnumFacing.UP.getIndex()] == null) {
            this.func_175819_a(worldIn, p_74875_3_, 1, 8, 1, 7, 8, 6, DoubleXYRoom.field_175828_a);
        }
        if (var7.field_175965_b[EnumFacing.UP.getIndex()] == null) {
            this.func_175819_a(worldIn, p_74875_3_, 8, 8, 1, 14, 8, 6, DoubleXYRoom.field_175828_a);
        }
        for (int var8 = 1; var8 <= 7; ++var8) {
            IBlockState var9 = DoubleXYRoom.field_175826_b;
            if (var8 == 2 || var8 == 6) {
                var9 = DoubleXYRoom.field_175828_a;
            }
            this.func_175804_a(worldIn, p_74875_3_, 0, var8, 0, 0, var8, 7, var9, var9, false);
            this.func_175804_a(worldIn, p_74875_3_, 15, var8, 0, 15, var8, 7, var9, var9, false);
            this.func_175804_a(worldIn, p_74875_3_, 1, var8, 0, 15, var8, 0, var9, var9, false);
            this.func_175804_a(worldIn, p_74875_3_, 1, var8, 7, 14, var8, 7, var9, var9, false);
        }
        this.func_175804_a(worldIn, p_74875_3_, 2, 1, 3, 2, 7, 4, DoubleXYRoom.field_175826_b, DoubleXYRoom.field_175826_b, false);
        this.func_175804_a(worldIn, p_74875_3_, 3, 1, 2, 4, 7, 2, DoubleXYRoom.field_175826_b, DoubleXYRoom.field_175826_b, false);
        this.func_175804_a(worldIn, p_74875_3_, 3, 1, 5, 4, 7, 5, DoubleXYRoom.field_175826_b, DoubleXYRoom.field_175826_b, false);
        this.func_175804_a(worldIn, p_74875_3_, 13, 1, 3, 13, 7, 4, DoubleXYRoom.field_175826_b, DoubleXYRoom.field_175826_b, false);
        this.func_175804_a(worldIn, p_74875_3_, 11, 1, 2, 12, 7, 2, DoubleXYRoom.field_175826_b, DoubleXYRoom.field_175826_b, false);
        this.func_175804_a(worldIn, p_74875_3_, 11, 1, 5, 12, 7, 5, DoubleXYRoom.field_175826_b, DoubleXYRoom.field_175826_b, false);
        this.func_175804_a(worldIn, p_74875_3_, 5, 1, 3, 5, 3, 4, DoubleXYRoom.field_175826_b, DoubleXYRoom.field_175826_b, false);
        this.func_175804_a(worldIn, p_74875_3_, 10, 1, 3, 10, 3, 4, DoubleXYRoom.field_175826_b, DoubleXYRoom.field_175826_b, false);
        this.func_175804_a(worldIn, p_74875_3_, 5, 7, 2, 10, 7, 5, DoubleXYRoom.field_175826_b, DoubleXYRoom.field_175826_b, false);
        this.func_175804_a(worldIn, p_74875_3_, 5, 5, 2, 5, 7, 2, DoubleXYRoom.field_175826_b, DoubleXYRoom.field_175826_b, false);
        this.func_175804_a(worldIn, p_74875_3_, 10, 5, 2, 10, 7, 2, DoubleXYRoom.field_175826_b, DoubleXYRoom.field_175826_b, false);
        this.func_175804_a(worldIn, p_74875_3_, 5, 5, 5, 5, 7, 5, DoubleXYRoom.field_175826_b, DoubleXYRoom.field_175826_b, false);
        this.func_175804_a(worldIn, p_74875_3_, 10, 5, 5, 10, 7, 5, DoubleXYRoom.field_175826_b, DoubleXYRoom.field_175826_b, false);
        this.func_175811_a(worldIn, DoubleXYRoom.field_175826_b, 6, 6, 2, p_74875_3_);
        this.func_175811_a(worldIn, DoubleXYRoom.field_175826_b, 9, 6, 2, p_74875_3_);
        this.func_175811_a(worldIn, DoubleXYRoom.field_175826_b, 6, 6, 5, p_74875_3_);
        this.func_175811_a(worldIn, DoubleXYRoom.field_175826_b, 9, 6, 5, p_74875_3_);
        this.func_175804_a(worldIn, p_74875_3_, 5, 4, 3, 6, 4, 4, DoubleXYRoom.field_175826_b, DoubleXYRoom.field_175826_b, false);
        this.func_175804_a(worldIn, p_74875_3_, 9, 4, 3, 10, 4, 4, DoubleXYRoom.field_175826_b, DoubleXYRoom.field_175826_b, false);
        this.func_175811_a(worldIn, DoubleXYRoom.field_175825_e, 5, 4, 2, p_74875_3_);
        this.func_175811_a(worldIn, DoubleXYRoom.field_175825_e, 5, 4, 5, p_74875_3_);
        this.func_175811_a(worldIn, DoubleXYRoom.field_175825_e, 10, 4, 2, p_74875_3_);
        this.func_175811_a(worldIn, DoubleXYRoom.field_175825_e, 10, 4, 5, p_74875_3_);
        if (var5.field_175966_c[EnumFacing.SOUTH.getIndex()]) {
            this.func_175804_a(worldIn, p_74875_3_, 3, 1, 0, 4, 2, 0, DoubleXYRoom.field_175822_f, DoubleXYRoom.field_175822_f, false);
        }
        if (var5.field_175966_c[EnumFacing.NORTH.getIndex()]) {
            this.func_175804_a(worldIn, p_74875_3_, 3, 1, 7, 4, 2, 7, DoubleXYRoom.field_175822_f, DoubleXYRoom.field_175822_f, false);
        }
        if (var5.field_175966_c[EnumFacing.WEST.getIndex()]) {
            this.func_175804_a(worldIn, p_74875_3_, 0, 1, 3, 0, 2, 4, DoubleXYRoom.field_175822_f, DoubleXYRoom.field_175822_f, false);
        }
        if (var4.field_175966_c[EnumFacing.SOUTH.getIndex()]) {
            this.func_175804_a(worldIn, p_74875_3_, 11, 1, 0, 12, 2, 0, DoubleXYRoom.field_175822_f, DoubleXYRoom.field_175822_f, false);
        }
        if (var4.field_175966_c[EnumFacing.NORTH.getIndex()]) {
            this.func_175804_a(worldIn, p_74875_3_, 11, 1, 7, 12, 2, 7, DoubleXYRoom.field_175822_f, DoubleXYRoom.field_175822_f, false);
        }
        if (var4.field_175966_c[EnumFacing.EAST.getIndex()]) {
            this.func_175804_a(worldIn, p_74875_3_, 15, 1, 3, 15, 2, 4, DoubleXYRoom.field_175822_f, DoubleXYRoom.field_175822_f, false);
        }
        if (var6.field_175966_c[EnumFacing.SOUTH.getIndex()]) {
            this.func_175804_a(worldIn, p_74875_3_, 3, 5, 0, 4, 6, 0, DoubleXYRoom.field_175822_f, DoubleXYRoom.field_175822_f, false);
        }
        if (var6.field_175966_c[EnumFacing.NORTH.getIndex()]) {
            this.func_175804_a(worldIn, p_74875_3_, 3, 5, 7, 4, 6, 7, DoubleXYRoom.field_175822_f, DoubleXYRoom.field_175822_f, false);
        }
        if (var6.field_175966_c[EnumFacing.WEST.getIndex()]) {
            this.func_175804_a(worldIn, p_74875_3_, 0, 5, 3, 0, 6, 4, DoubleXYRoom.field_175822_f, DoubleXYRoom.field_175822_f, false);
        }
        if (var7.field_175966_c[EnumFacing.SOUTH.getIndex()]) {
            this.func_175804_a(worldIn, p_74875_3_, 11, 5, 0, 12, 6, 0, DoubleXYRoom.field_175822_f, DoubleXYRoom.field_175822_f, false);
        }
        if (var7.field_175966_c[EnumFacing.NORTH.getIndex()]) {
            this.func_175804_a(worldIn, p_74875_3_, 11, 5, 7, 12, 6, 7, DoubleXYRoom.field_175822_f, DoubleXYRoom.field_175822_f, false);
        }
        if (var7.field_175966_c[EnumFacing.EAST.getIndex()]) {
            this.func_175804_a(worldIn, p_74875_3_, 15, 5, 3, 15, 6, 4, DoubleXYRoom.field_175822_f, DoubleXYRoom.field_175822_f, false);
        }
        return true;
    }
}
