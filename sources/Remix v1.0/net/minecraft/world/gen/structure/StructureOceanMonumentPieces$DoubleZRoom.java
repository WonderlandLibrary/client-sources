package net.minecraft.world.gen.structure;

import net.minecraft.util.*;
import java.util.*;
import net.minecraft.world.*;

public static class DoubleZRoom extends Piece
{
    public DoubleZRoom() {
    }
    
    public DoubleZRoom(final EnumFacing p_i45593_1_, final RoomDefinition p_i45593_2_, final Random p_i45593_3_) {
        super(1, p_i45593_1_, p_i45593_2_, 1, 1, 2);
    }
    
    @Override
    public boolean addComponentParts(final World worldIn, final Random p_74875_2_, final StructureBoundingBox p_74875_3_) {
        final RoomDefinition var4 = this.field_175830_k.field_175965_b[EnumFacing.NORTH.getIndex()];
        final RoomDefinition var5 = this.field_175830_k;
        if (this.field_175830_k.field_175967_a / 25 > 0) {
            this.func_175821_a(worldIn, p_74875_3_, 0, 8, var4.field_175966_c[EnumFacing.DOWN.getIndex()]);
            this.func_175821_a(worldIn, p_74875_3_, 0, 0, var5.field_175966_c[EnumFacing.DOWN.getIndex()]);
        }
        if (var5.field_175965_b[EnumFacing.UP.getIndex()] == null) {
            this.func_175819_a(worldIn, p_74875_3_, 1, 4, 1, 6, 4, 7, DoubleZRoom.field_175828_a);
        }
        if (var4.field_175965_b[EnumFacing.UP.getIndex()] == null) {
            this.func_175819_a(worldIn, p_74875_3_, 1, 4, 8, 6, 4, 14, DoubleZRoom.field_175828_a);
        }
        this.func_175804_a(worldIn, p_74875_3_, 0, 3, 0, 0, 3, 15, DoubleZRoom.field_175826_b, DoubleZRoom.field_175826_b, false);
        this.func_175804_a(worldIn, p_74875_3_, 7, 3, 0, 7, 3, 15, DoubleZRoom.field_175826_b, DoubleZRoom.field_175826_b, false);
        this.func_175804_a(worldIn, p_74875_3_, 1, 3, 0, 7, 3, 0, DoubleZRoom.field_175826_b, DoubleZRoom.field_175826_b, false);
        this.func_175804_a(worldIn, p_74875_3_, 1, 3, 15, 6, 3, 15, DoubleZRoom.field_175826_b, DoubleZRoom.field_175826_b, false);
        this.func_175804_a(worldIn, p_74875_3_, 0, 2, 0, 0, 2, 15, DoubleZRoom.field_175828_a, DoubleZRoom.field_175828_a, false);
        this.func_175804_a(worldIn, p_74875_3_, 7, 2, 0, 7, 2, 15, DoubleZRoom.field_175828_a, DoubleZRoom.field_175828_a, false);
        this.func_175804_a(worldIn, p_74875_3_, 1, 2, 0, 7, 2, 0, DoubleZRoom.field_175828_a, DoubleZRoom.field_175828_a, false);
        this.func_175804_a(worldIn, p_74875_3_, 1, 2, 15, 6, 2, 15, DoubleZRoom.field_175828_a, DoubleZRoom.field_175828_a, false);
        this.func_175804_a(worldIn, p_74875_3_, 0, 1, 0, 0, 1, 15, DoubleZRoom.field_175826_b, DoubleZRoom.field_175826_b, false);
        this.func_175804_a(worldIn, p_74875_3_, 7, 1, 0, 7, 1, 15, DoubleZRoom.field_175826_b, DoubleZRoom.field_175826_b, false);
        this.func_175804_a(worldIn, p_74875_3_, 1, 1, 0, 7, 1, 0, DoubleZRoom.field_175826_b, DoubleZRoom.field_175826_b, false);
        this.func_175804_a(worldIn, p_74875_3_, 1, 1, 15, 6, 1, 15, DoubleZRoom.field_175826_b, DoubleZRoom.field_175826_b, false);
        this.func_175804_a(worldIn, p_74875_3_, 1, 1, 1, 1, 1, 2, DoubleZRoom.field_175826_b, DoubleZRoom.field_175826_b, false);
        this.func_175804_a(worldIn, p_74875_3_, 6, 1, 1, 6, 1, 2, DoubleZRoom.field_175826_b, DoubleZRoom.field_175826_b, false);
        this.func_175804_a(worldIn, p_74875_3_, 1, 3, 1, 1, 3, 2, DoubleZRoom.field_175826_b, DoubleZRoom.field_175826_b, false);
        this.func_175804_a(worldIn, p_74875_3_, 6, 3, 1, 6, 3, 2, DoubleZRoom.field_175826_b, DoubleZRoom.field_175826_b, false);
        this.func_175804_a(worldIn, p_74875_3_, 1, 1, 13, 1, 1, 14, DoubleZRoom.field_175826_b, DoubleZRoom.field_175826_b, false);
        this.func_175804_a(worldIn, p_74875_3_, 6, 1, 13, 6, 1, 14, DoubleZRoom.field_175826_b, DoubleZRoom.field_175826_b, false);
        this.func_175804_a(worldIn, p_74875_3_, 1, 3, 13, 1, 3, 14, DoubleZRoom.field_175826_b, DoubleZRoom.field_175826_b, false);
        this.func_175804_a(worldIn, p_74875_3_, 6, 3, 13, 6, 3, 14, DoubleZRoom.field_175826_b, DoubleZRoom.field_175826_b, false);
        this.func_175804_a(worldIn, p_74875_3_, 2, 1, 6, 2, 3, 6, DoubleZRoom.field_175826_b, DoubleZRoom.field_175826_b, false);
        this.func_175804_a(worldIn, p_74875_3_, 5, 1, 6, 5, 3, 6, DoubleZRoom.field_175826_b, DoubleZRoom.field_175826_b, false);
        this.func_175804_a(worldIn, p_74875_3_, 2, 1, 9, 2, 3, 9, DoubleZRoom.field_175826_b, DoubleZRoom.field_175826_b, false);
        this.func_175804_a(worldIn, p_74875_3_, 5, 1, 9, 5, 3, 9, DoubleZRoom.field_175826_b, DoubleZRoom.field_175826_b, false);
        this.func_175804_a(worldIn, p_74875_3_, 3, 2, 6, 4, 2, 6, DoubleZRoom.field_175826_b, DoubleZRoom.field_175826_b, false);
        this.func_175804_a(worldIn, p_74875_3_, 3, 2, 9, 4, 2, 9, DoubleZRoom.field_175826_b, DoubleZRoom.field_175826_b, false);
        this.func_175804_a(worldIn, p_74875_3_, 2, 2, 7, 2, 2, 8, DoubleZRoom.field_175826_b, DoubleZRoom.field_175826_b, false);
        this.func_175804_a(worldIn, p_74875_3_, 5, 2, 7, 5, 2, 8, DoubleZRoom.field_175826_b, DoubleZRoom.field_175826_b, false);
        this.func_175811_a(worldIn, DoubleZRoom.field_175825_e, 2, 2, 5, p_74875_3_);
        this.func_175811_a(worldIn, DoubleZRoom.field_175825_e, 5, 2, 5, p_74875_3_);
        this.func_175811_a(worldIn, DoubleZRoom.field_175825_e, 2, 2, 10, p_74875_3_);
        this.func_175811_a(worldIn, DoubleZRoom.field_175825_e, 5, 2, 10, p_74875_3_);
        this.func_175811_a(worldIn, DoubleZRoom.field_175826_b, 2, 3, 5, p_74875_3_);
        this.func_175811_a(worldIn, DoubleZRoom.field_175826_b, 5, 3, 5, p_74875_3_);
        this.func_175811_a(worldIn, DoubleZRoom.field_175826_b, 2, 3, 10, p_74875_3_);
        this.func_175811_a(worldIn, DoubleZRoom.field_175826_b, 5, 3, 10, p_74875_3_);
        if (var5.field_175966_c[EnumFacing.SOUTH.getIndex()]) {
            this.func_175804_a(worldIn, p_74875_3_, 3, 1, 0, 4, 2, 0, DoubleZRoom.field_175822_f, DoubleZRoom.field_175822_f, false);
        }
        if (var5.field_175966_c[EnumFacing.EAST.getIndex()]) {
            this.func_175804_a(worldIn, p_74875_3_, 7, 1, 3, 7, 2, 4, DoubleZRoom.field_175822_f, DoubleZRoom.field_175822_f, false);
        }
        if (var5.field_175966_c[EnumFacing.WEST.getIndex()]) {
            this.func_175804_a(worldIn, p_74875_3_, 0, 1, 3, 0, 2, 4, DoubleZRoom.field_175822_f, DoubleZRoom.field_175822_f, false);
        }
        if (var4.field_175966_c[EnumFacing.NORTH.getIndex()]) {
            this.func_175804_a(worldIn, p_74875_3_, 3, 1, 15, 4, 2, 15, DoubleZRoom.field_175822_f, DoubleZRoom.field_175822_f, false);
        }
        if (var4.field_175966_c[EnumFacing.WEST.getIndex()]) {
            this.func_175804_a(worldIn, p_74875_3_, 0, 1, 11, 0, 2, 12, DoubleZRoom.field_175822_f, DoubleZRoom.field_175822_f, false);
        }
        if (var4.field_175966_c[EnumFacing.EAST.getIndex()]) {
            this.func_175804_a(worldIn, p_74875_3_, 7, 1, 11, 7, 2, 12, DoubleZRoom.field_175822_f, DoubleZRoom.field_175822_f, false);
        }
        return true;
    }
}
