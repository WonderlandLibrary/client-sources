package net.minecraft.world.gen.structure;

import net.minecraft.util.*;
import java.util.*;
import net.minecraft.world.*;

public static class DoubleXRoom extends Piece
{
    public DoubleXRoom() {
    }
    
    public DoubleXRoom(final EnumFacing p_i45597_1_, final RoomDefinition p_i45597_2_, final Random p_i45597_3_) {
        super(1, p_i45597_1_, p_i45597_2_, 2, 1, 1);
    }
    
    @Override
    public boolean addComponentParts(final World worldIn, final Random p_74875_2_, final StructureBoundingBox p_74875_3_) {
        final RoomDefinition var4 = this.field_175830_k.field_175965_b[EnumFacing.EAST.getIndex()];
        final RoomDefinition var5 = this.field_175830_k;
        if (this.field_175830_k.field_175967_a / 25 > 0) {
            this.func_175821_a(worldIn, p_74875_3_, 8, 0, var4.field_175966_c[EnumFacing.DOWN.getIndex()]);
            this.func_175821_a(worldIn, p_74875_3_, 0, 0, var5.field_175966_c[EnumFacing.DOWN.getIndex()]);
        }
        if (var5.field_175965_b[EnumFacing.UP.getIndex()] == null) {
            this.func_175819_a(worldIn, p_74875_3_, 1, 4, 1, 7, 4, 6, DoubleXRoom.field_175828_a);
        }
        if (var4.field_175965_b[EnumFacing.UP.getIndex()] == null) {
            this.func_175819_a(worldIn, p_74875_3_, 8, 4, 1, 14, 4, 6, DoubleXRoom.field_175828_a);
        }
        this.func_175804_a(worldIn, p_74875_3_, 0, 3, 0, 0, 3, 7, DoubleXRoom.field_175826_b, DoubleXRoom.field_175826_b, false);
        this.func_175804_a(worldIn, p_74875_3_, 15, 3, 0, 15, 3, 7, DoubleXRoom.field_175826_b, DoubleXRoom.field_175826_b, false);
        this.func_175804_a(worldIn, p_74875_3_, 1, 3, 0, 15, 3, 0, DoubleXRoom.field_175826_b, DoubleXRoom.field_175826_b, false);
        this.func_175804_a(worldIn, p_74875_3_, 1, 3, 7, 14, 3, 7, DoubleXRoom.field_175826_b, DoubleXRoom.field_175826_b, false);
        this.func_175804_a(worldIn, p_74875_3_, 0, 2, 0, 0, 2, 7, DoubleXRoom.field_175828_a, DoubleXRoom.field_175828_a, false);
        this.func_175804_a(worldIn, p_74875_3_, 15, 2, 0, 15, 2, 7, DoubleXRoom.field_175828_a, DoubleXRoom.field_175828_a, false);
        this.func_175804_a(worldIn, p_74875_3_, 1, 2, 0, 15, 2, 0, DoubleXRoom.field_175828_a, DoubleXRoom.field_175828_a, false);
        this.func_175804_a(worldIn, p_74875_3_, 1, 2, 7, 14, 2, 7, DoubleXRoom.field_175828_a, DoubleXRoom.field_175828_a, false);
        this.func_175804_a(worldIn, p_74875_3_, 0, 1, 0, 0, 1, 7, DoubleXRoom.field_175826_b, DoubleXRoom.field_175826_b, false);
        this.func_175804_a(worldIn, p_74875_3_, 15, 1, 0, 15, 1, 7, DoubleXRoom.field_175826_b, DoubleXRoom.field_175826_b, false);
        this.func_175804_a(worldIn, p_74875_3_, 1, 1, 0, 15, 1, 0, DoubleXRoom.field_175826_b, DoubleXRoom.field_175826_b, false);
        this.func_175804_a(worldIn, p_74875_3_, 1, 1, 7, 14, 1, 7, DoubleXRoom.field_175826_b, DoubleXRoom.field_175826_b, false);
        this.func_175804_a(worldIn, p_74875_3_, 5, 1, 0, 10, 1, 4, DoubleXRoom.field_175826_b, DoubleXRoom.field_175826_b, false);
        this.func_175804_a(worldIn, p_74875_3_, 6, 2, 0, 9, 2, 3, DoubleXRoom.field_175828_a, DoubleXRoom.field_175828_a, false);
        this.func_175804_a(worldIn, p_74875_3_, 5, 3, 0, 10, 3, 4, DoubleXRoom.field_175826_b, DoubleXRoom.field_175826_b, false);
        this.func_175811_a(worldIn, DoubleXRoom.field_175825_e, 6, 2, 3, p_74875_3_);
        this.func_175811_a(worldIn, DoubleXRoom.field_175825_e, 9, 2, 3, p_74875_3_);
        if (var5.field_175966_c[EnumFacing.SOUTH.getIndex()]) {
            this.func_175804_a(worldIn, p_74875_3_, 3, 1, 0, 4, 2, 0, DoubleXRoom.field_175822_f, DoubleXRoom.field_175822_f, false);
        }
        if (var5.field_175966_c[EnumFacing.NORTH.getIndex()]) {
            this.func_175804_a(worldIn, p_74875_3_, 3, 1, 7, 4, 2, 7, DoubleXRoom.field_175822_f, DoubleXRoom.field_175822_f, false);
        }
        if (var5.field_175966_c[EnumFacing.WEST.getIndex()]) {
            this.func_175804_a(worldIn, p_74875_3_, 0, 1, 3, 0, 2, 4, DoubleXRoom.field_175822_f, DoubleXRoom.field_175822_f, false);
        }
        if (var4.field_175966_c[EnumFacing.SOUTH.getIndex()]) {
            this.func_175804_a(worldIn, p_74875_3_, 11, 1, 0, 12, 2, 0, DoubleXRoom.field_175822_f, DoubleXRoom.field_175822_f, false);
        }
        if (var4.field_175966_c[EnumFacing.NORTH.getIndex()]) {
            this.func_175804_a(worldIn, p_74875_3_, 11, 1, 7, 12, 2, 7, DoubleXRoom.field_175822_f, DoubleXRoom.field_175822_f, false);
        }
        if (var4.field_175966_c[EnumFacing.EAST.getIndex()]) {
            this.func_175804_a(worldIn, p_74875_3_, 15, 1, 3, 15, 2, 4, DoubleXRoom.field_175822_f, DoubleXRoom.field_175822_f, false);
        }
        return true;
    }
}
