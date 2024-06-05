package net.minecraft.world.gen.structure;

import net.minecraft.util.*;
import java.util.*;
import net.minecraft.world.*;

public static class DoubleYRoom extends Piece
{
    public DoubleYRoom() {
    }
    
    public DoubleYRoom(final EnumFacing p_i45595_1_, final RoomDefinition p_i45595_2_, final Random p_i45595_3_) {
        super(1, p_i45595_1_, p_i45595_2_, 1, 2, 1);
    }
    
    @Override
    public boolean addComponentParts(final World worldIn, final Random p_74875_2_, final StructureBoundingBox p_74875_3_) {
        if (this.field_175830_k.field_175967_a / 25 > 0) {
            this.func_175821_a(worldIn, p_74875_3_, 0, 0, this.field_175830_k.field_175966_c[EnumFacing.DOWN.getIndex()]);
        }
        final RoomDefinition var4 = this.field_175830_k.field_175965_b[EnumFacing.UP.getIndex()];
        if (var4.field_175965_b[EnumFacing.UP.getIndex()] == null) {
            this.func_175819_a(worldIn, p_74875_3_, 1, 8, 1, 6, 8, 6, DoubleYRoom.field_175828_a);
        }
        this.func_175804_a(worldIn, p_74875_3_, 0, 4, 0, 0, 4, 7, DoubleYRoom.field_175826_b, DoubleYRoom.field_175826_b, false);
        this.func_175804_a(worldIn, p_74875_3_, 7, 4, 0, 7, 4, 7, DoubleYRoom.field_175826_b, DoubleYRoom.field_175826_b, false);
        this.func_175804_a(worldIn, p_74875_3_, 1, 4, 0, 6, 4, 0, DoubleYRoom.field_175826_b, DoubleYRoom.field_175826_b, false);
        this.func_175804_a(worldIn, p_74875_3_, 1, 4, 7, 6, 4, 7, DoubleYRoom.field_175826_b, DoubleYRoom.field_175826_b, false);
        this.func_175804_a(worldIn, p_74875_3_, 2, 4, 1, 2, 4, 2, DoubleYRoom.field_175826_b, DoubleYRoom.field_175826_b, false);
        this.func_175804_a(worldIn, p_74875_3_, 1, 4, 2, 1, 4, 2, DoubleYRoom.field_175826_b, DoubleYRoom.field_175826_b, false);
        this.func_175804_a(worldIn, p_74875_3_, 5, 4, 1, 5, 4, 2, DoubleYRoom.field_175826_b, DoubleYRoom.field_175826_b, false);
        this.func_175804_a(worldIn, p_74875_3_, 6, 4, 2, 6, 4, 2, DoubleYRoom.field_175826_b, DoubleYRoom.field_175826_b, false);
        this.func_175804_a(worldIn, p_74875_3_, 2, 4, 5, 2, 4, 6, DoubleYRoom.field_175826_b, DoubleYRoom.field_175826_b, false);
        this.func_175804_a(worldIn, p_74875_3_, 1, 4, 5, 1, 4, 5, DoubleYRoom.field_175826_b, DoubleYRoom.field_175826_b, false);
        this.func_175804_a(worldIn, p_74875_3_, 5, 4, 5, 5, 4, 6, DoubleYRoom.field_175826_b, DoubleYRoom.field_175826_b, false);
        this.func_175804_a(worldIn, p_74875_3_, 6, 4, 5, 6, 4, 5, DoubleYRoom.field_175826_b, DoubleYRoom.field_175826_b, false);
        RoomDefinition var5 = this.field_175830_k;
        for (int var6 = 1; var6 <= 5; var6 += 4) {
            byte var7 = 0;
            if (var5.field_175966_c[EnumFacing.SOUTH.getIndex()]) {
                this.func_175804_a(worldIn, p_74875_3_, 2, var6, var7, 2, var6 + 2, var7, DoubleYRoom.field_175826_b, DoubleYRoom.field_175826_b, false);
                this.func_175804_a(worldIn, p_74875_3_, 5, var6, var7, 5, var6 + 2, var7, DoubleYRoom.field_175826_b, DoubleYRoom.field_175826_b, false);
                this.func_175804_a(worldIn, p_74875_3_, 3, var6 + 2, var7, 4, var6 + 2, var7, DoubleYRoom.field_175826_b, DoubleYRoom.field_175826_b, false);
            }
            else {
                this.func_175804_a(worldIn, p_74875_3_, 0, var6, var7, 7, var6 + 2, var7, DoubleYRoom.field_175826_b, DoubleYRoom.field_175826_b, false);
                this.func_175804_a(worldIn, p_74875_3_, 0, var6 + 1, var7, 7, var6 + 1, var7, DoubleYRoom.field_175828_a, DoubleYRoom.field_175828_a, false);
            }
            var7 = 7;
            if (var5.field_175966_c[EnumFacing.NORTH.getIndex()]) {
                this.func_175804_a(worldIn, p_74875_3_, 2, var6, var7, 2, var6 + 2, var7, DoubleYRoom.field_175826_b, DoubleYRoom.field_175826_b, false);
                this.func_175804_a(worldIn, p_74875_3_, 5, var6, var7, 5, var6 + 2, var7, DoubleYRoom.field_175826_b, DoubleYRoom.field_175826_b, false);
                this.func_175804_a(worldIn, p_74875_3_, 3, var6 + 2, var7, 4, var6 + 2, var7, DoubleYRoom.field_175826_b, DoubleYRoom.field_175826_b, false);
            }
            else {
                this.func_175804_a(worldIn, p_74875_3_, 0, var6, var7, 7, var6 + 2, var7, DoubleYRoom.field_175826_b, DoubleYRoom.field_175826_b, false);
                this.func_175804_a(worldIn, p_74875_3_, 0, var6 + 1, var7, 7, var6 + 1, var7, DoubleYRoom.field_175828_a, DoubleYRoom.field_175828_a, false);
            }
            byte var8 = 0;
            if (var5.field_175966_c[EnumFacing.WEST.getIndex()]) {
                this.func_175804_a(worldIn, p_74875_3_, var8, var6, 2, var8, var6 + 2, 2, DoubleYRoom.field_175826_b, DoubleYRoom.field_175826_b, false);
                this.func_175804_a(worldIn, p_74875_3_, var8, var6, 5, var8, var6 + 2, 5, DoubleYRoom.field_175826_b, DoubleYRoom.field_175826_b, false);
                this.func_175804_a(worldIn, p_74875_3_, var8, var6 + 2, 3, var8, var6 + 2, 4, DoubleYRoom.field_175826_b, DoubleYRoom.field_175826_b, false);
            }
            else {
                this.func_175804_a(worldIn, p_74875_3_, var8, var6, 0, var8, var6 + 2, 7, DoubleYRoom.field_175826_b, DoubleYRoom.field_175826_b, false);
                this.func_175804_a(worldIn, p_74875_3_, var8, var6 + 1, 0, var8, var6 + 1, 7, DoubleYRoom.field_175828_a, DoubleYRoom.field_175828_a, false);
            }
            var8 = 7;
            if (var5.field_175966_c[EnumFacing.EAST.getIndex()]) {
                this.func_175804_a(worldIn, p_74875_3_, var8, var6, 2, var8, var6 + 2, 2, DoubleYRoom.field_175826_b, DoubleYRoom.field_175826_b, false);
                this.func_175804_a(worldIn, p_74875_3_, var8, var6, 5, var8, var6 + 2, 5, DoubleYRoom.field_175826_b, DoubleYRoom.field_175826_b, false);
                this.func_175804_a(worldIn, p_74875_3_, var8, var6 + 2, 3, var8, var6 + 2, 4, DoubleYRoom.field_175826_b, DoubleYRoom.field_175826_b, false);
            }
            else {
                this.func_175804_a(worldIn, p_74875_3_, var8, var6, 0, var8, var6 + 2, 7, DoubleYRoom.field_175826_b, DoubleYRoom.field_175826_b, false);
                this.func_175804_a(worldIn, p_74875_3_, var8, var6 + 1, 0, var8, var6 + 1, 7, DoubleYRoom.field_175828_a, DoubleYRoom.field_175828_a, false);
            }
            var5 = var4;
        }
        return true;
    }
}
