package net.minecraft.world.gen.structure;

import net.minecraft.util.*;
import java.util.*;
import net.minecraft.world.*;

public static class SimpleRoom extends Piece
{
    private int field_175833_o;
    
    public SimpleRoom() {
    }
    
    public SimpleRoom(final EnumFacing p_i45587_1_, final RoomDefinition p_i45587_2_, final Random p_i45587_3_) {
        super(1, p_i45587_1_, p_i45587_2_, 1, 1, 1);
        this.field_175833_o = p_i45587_3_.nextInt(3);
    }
    
    @Override
    public boolean addComponentParts(final World worldIn, final Random p_74875_2_, final StructureBoundingBox p_74875_3_) {
        if (this.field_175830_k.field_175967_a / 25 > 0) {
            this.func_175821_a(worldIn, p_74875_3_, 0, 0, this.field_175830_k.field_175966_c[EnumFacing.DOWN.getIndex()]);
        }
        if (this.field_175830_k.field_175965_b[EnumFacing.UP.getIndex()] == null) {
            this.func_175819_a(worldIn, p_74875_3_, 1, 4, 1, 6, 4, 6, SimpleRoom.field_175828_a);
        }
        final boolean var4 = this.field_175833_o != 0 && p_74875_2_.nextBoolean() && !this.field_175830_k.field_175966_c[EnumFacing.DOWN.getIndex()] && !this.field_175830_k.field_175966_c[EnumFacing.UP.getIndex()] && this.field_175830_k.func_175960_c() > 1;
        if (this.field_175833_o == 0) {
            this.func_175804_a(worldIn, p_74875_3_, 0, 1, 0, 2, 1, 2, SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, false);
            this.func_175804_a(worldIn, p_74875_3_, 0, 3, 0, 2, 3, 2, SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, false);
            this.func_175804_a(worldIn, p_74875_3_, 0, 2, 0, 0, 2, 2, SimpleRoom.field_175828_a, SimpleRoom.field_175828_a, false);
            this.func_175804_a(worldIn, p_74875_3_, 1, 2, 0, 2, 2, 0, SimpleRoom.field_175828_a, SimpleRoom.field_175828_a, false);
            this.func_175811_a(worldIn, SimpleRoom.field_175825_e, 1, 2, 1, p_74875_3_);
            this.func_175804_a(worldIn, p_74875_3_, 5, 1, 0, 7, 1, 2, SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, false);
            this.func_175804_a(worldIn, p_74875_3_, 5, 3, 0, 7, 3, 2, SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, false);
            this.func_175804_a(worldIn, p_74875_3_, 7, 2, 0, 7, 2, 2, SimpleRoom.field_175828_a, SimpleRoom.field_175828_a, false);
            this.func_175804_a(worldIn, p_74875_3_, 5, 2, 0, 6, 2, 0, SimpleRoom.field_175828_a, SimpleRoom.field_175828_a, false);
            this.func_175811_a(worldIn, SimpleRoom.field_175825_e, 6, 2, 1, p_74875_3_);
            this.func_175804_a(worldIn, p_74875_3_, 0, 1, 5, 2, 1, 7, SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, false);
            this.func_175804_a(worldIn, p_74875_3_, 0, 3, 5, 2, 3, 7, SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, false);
            this.func_175804_a(worldIn, p_74875_3_, 0, 2, 5, 0, 2, 7, SimpleRoom.field_175828_a, SimpleRoom.field_175828_a, false);
            this.func_175804_a(worldIn, p_74875_3_, 1, 2, 7, 2, 2, 7, SimpleRoom.field_175828_a, SimpleRoom.field_175828_a, false);
            this.func_175811_a(worldIn, SimpleRoom.field_175825_e, 1, 2, 6, p_74875_3_);
            this.func_175804_a(worldIn, p_74875_3_, 5, 1, 5, 7, 1, 7, SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, false);
            this.func_175804_a(worldIn, p_74875_3_, 5, 3, 5, 7, 3, 7, SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, false);
            this.func_175804_a(worldIn, p_74875_3_, 7, 2, 5, 7, 2, 7, SimpleRoom.field_175828_a, SimpleRoom.field_175828_a, false);
            this.func_175804_a(worldIn, p_74875_3_, 5, 2, 7, 6, 2, 7, SimpleRoom.field_175828_a, SimpleRoom.field_175828_a, false);
            this.func_175811_a(worldIn, SimpleRoom.field_175825_e, 6, 2, 6, p_74875_3_);
            if (this.field_175830_k.field_175966_c[EnumFacing.SOUTH.getIndex()]) {
                this.func_175804_a(worldIn, p_74875_3_, 3, 3, 0, 4, 3, 0, SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, false);
            }
            else {
                this.func_175804_a(worldIn, p_74875_3_, 3, 3, 0, 4, 3, 1, SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, false);
                this.func_175804_a(worldIn, p_74875_3_, 3, 2, 0, 4, 2, 0, SimpleRoom.field_175828_a, SimpleRoom.field_175828_a, false);
                this.func_175804_a(worldIn, p_74875_3_, 3, 1, 0, 4, 1, 1, SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, false);
            }
            if (this.field_175830_k.field_175966_c[EnumFacing.NORTH.getIndex()]) {
                this.func_175804_a(worldIn, p_74875_3_, 3, 3, 7, 4, 3, 7, SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, false);
            }
            else {
                this.func_175804_a(worldIn, p_74875_3_, 3, 3, 6, 4, 3, 7, SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, false);
                this.func_175804_a(worldIn, p_74875_3_, 3, 2, 7, 4, 2, 7, SimpleRoom.field_175828_a, SimpleRoom.field_175828_a, false);
                this.func_175804_a(worldIn, p_74875_3_, 3, 1, 6, 4, 1, 7, SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, false);
            }
            if (this.field_175830_k.field_175966_c[EnumFacing.WEST.getIndex()]) {
                this.func_175804_a(worldIn, p_74875_3_, 0, 3, 3, 0, 3, 4, SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, false);
            }
            else {
                this.func_175804_a(worldIn, p_74875_3_, 0, 3, 3, 1, 3, 4, SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, false);
                this.func_175804_a(worldIn, p_74875_3_, 0, 2, 3, 0, 2, 4, SimpleRoom.field_175828_a, SimpleRoom.field_175828_a, false);
                this.func_175804_a(worldIn, p_74875_3_, 0, 1, 3, 1, 1, 4, SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, false);
            }
            if (this.field_175830_k.field_175966_c[EnumFacing.EAST.getIndex()]) {
                this.func_175804_a(worldIn, p_74875_3_, 7, 3, 3, 7, 3, 4, SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, false);
            }
            else {
                this.func_175804_a(worldIn, p_74875_3_, 6, 3, 3, 7, 3, 4, SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, false);
                this.func_175804_a(worldIn, p_74875_3_, 7, 2, 3, 7, 2, 4, SimpleRoom.field_175828_a, SimpleRoom.field_175828_a, false);
                this.func_175804_a(worldIn, p_74875_3_, 6, 1, 3, 7, 1, 4, SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, false);
            }
        }
        else if (this.field_175833_o == 1) {
            this.func_175804_a(worldIn, p_74875_3_, 2, 1, 2, 2, 3, 2, SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, false);
            this.func_175804_a(worldIn, p_74875_3_, 2, 1, 5, 2, 3, 5, SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, false);
            this.func_175804_a(worldIn, p_74875_3_, 5, 1, 5, 5, 3, 5, SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, false);
            this.func_175804_a(worldIn, p_74875_3_, 5, 1, 2, 5, 3, 2, SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, false);
            this.func_175811_a(worldIn, SimpleRoom.field_175825_e, 2, 2, 2, p_74875_3_);
            this.func_175811_a(worldIn, SimpleRoom.field_175825_e, 2, 2, 5, p_74875_3_);
            this.func_175811_a(worldIn, SimpleRoom.field_175825_e, 5, 2, 5, p_74875_3_);
            this.func_175811_a(worldIn, SimpleRoom.field_175825_e, 5, 2, 2, p_74875_3_);
            this.func_175804_a(worldIn, p_74875_3_, 0, 1, 0, 1, 3, 0, SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, false);
            this.func_175804_a(worldIn, p_74875_3_, 0, 1, 1, 0, 3, 1, SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, false);
            this.func_175804_a(worldIn, p_74875_3_, 0, 1, 7, 1, 3, 7, SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, false);
            this.func_175804_a(worldIn, p_74875_3_, 0, 1, 6, 0, 3, 6, SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, false);
            this.func_175804_a(worldIn, p_74875_3_, 6, 1, 7, 7, 3, 7, SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, false);
            this.func_175804_a(worldIn, p_74875_3_, 7, 1, 6, 7, 3, 6, SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, false);
            this.func_175804_a(worldIn, p_74875_3_, 6, 1, 0, 7, 3, 0, SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, false);
            this.func_175804_a(worldIn, p_74875_3_, 7, 1, 1, 7, 3, 1, SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, false);
            this.func_175811_a(worldIn, SimpleRoom.field_175828_a, 1, 2, 0, p_74875_3_);
            this.func_175811_a(worldIn, SimpleRoom.field_175828_a, 0, 2, 1, p_74875_3_);
            this.func_175811_a(worldIn, SimpleRoom.field_175828_a, 1, 2, 7, p_74875_3_);
            this.func_175811_a(worldIn, SimpleRoom.field_175828_a, 0, 2, 6, p_74875_3_);
            this.func_175811_a(worldIn, SimpleRoom.field_175828_a, 6, 2, 7, p_74875_3_);
            this.func_175811_a(worldIn, SimpleRoom.field_175828_a, 7, 2, 6, p_74875_3_);
            this.func_175811_a(worldIn, SimpleRoom.field_175828_a, 6, 2, 0, p_74875_3_);
            this.func_175811_a(worldIn, SimpleRoom.field_175828_a, 7, 2, 1, p_74875_3_);
            if (!this.field_175830_k.field_175966_c[EnumFacing.SOUTH.getIndex()]) {
                this.func_175804_a(worldIn, p_74875_3_, 1, 3, 0, 6, 3, 0, SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, false);
                this.func_175804_a(worldIn, p_74875_3_, 1, 2, 0, 6, 2, 0, SimpleRoom.field_175828_a, SimpleRoom.field_175828_a, false);
                this.func_175804_a(worldIn, p_74875_3_, 1, 1, 0, 6, 1, 0, SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, false);
            }
            if (!this.field_175830_k.field_175966_c[EnumFacing.NORTH.getIndex()]) {
                this.func_175804_a(worldIn, p_74875_3_, 1, 3, 7, 6, 3, 7, SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, false);
                this.func_175804_a(worldIn, p_74875_3_, 1, 2, 7, 6, 2, 7, SimpleRoom.field_175828_a, SimpleRoom.field_175828_a, false);
                this.func_175804_a(worldIn, p_74875_3_, 1, 1, 7, 6, 1, 7, SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, false);
            }
            if (!this.field_175830_k.field_175966_c[EnumFacing.WEST.getIndex()]) {
                this.func_175804_a(worldIn, p_74875_3_, 0, 3, 1, 0, 3, 6, SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, false);
                this.func_175804_a(worldIn, p_74875_3_, 0, 2, 1, 0, 2, 6, SimpleRoom.field_175828_a, SimpleRoom.field_175828_a, false);
                this.func_175804_a(worldIn, p_74875_3_, 0, 1, 1, 0, 1, 6, SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, false);
            }
            if (!this.field_175830_k.field_175966_c[EnumFacing.EAST.getIndex()]) {
                this.func_175804_a(worldIn, p_74875_3_, 7, 3, 1, 7, 3, 6, SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, false);
                this.func_175804_a(worldIn, p_74875_3_, 7, 2, 1, 7, 2, 6, SimpleRoom.field_175828_a, SimpleRoom.field_175828_a, false);
                this.func_175804_a(worldIn, p_74875_3_, 7, 1, 1, 7, 1, 6, SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, false);
            }
        }
        else if (this.field_175833_o == 2) {
            this.func_175804_a(worldIn, p_74875_3_, 0, 1, 0, 0, 1, 7, SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, false);
            this.func_175804_a(worldIn, p_74875_3_, 7, 1, 0, 7, 1, 7, SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, false);
            this.func_175804_a(worldIn, p_74875_3_, 1, 1, 0, 6, 1, 0, SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, false);
            this.func_175804_a(worldIn, p_74875_3_, 1, 1, 7, 6, 1, 7, SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, false);
            this.func_175804_a(worldIn, p_74875_3_, 0, 2, 0, 0, 2, 7, SimpleRoom.field_175827_c, SimpleRoom.field_175827_c, false);
            this.func_175804_a(worldIn, p_74875_3_, 7, 2, 0, 7, 2, 7, SimpleRoom.field_175827_c, SimpleRoom.field_175827_c, false);
            this.func_175804_a(worldIn, p_74875_3_, 1, 2, 0, 6, 2, 0, SimpleRoom.field_175827_c, SimpleRoom.field_175827_c, false);
            this.func_175804_a(worldIn, p_74875_3_, 1, 2, 7, 6, 2, 7, SimpleRoom.field_175827_c, SimpleRoom.field_175827_c, false);
            this.func_175804_a(worldIn, p_74875_3_, 0, 3, 0, 0, 3, 7, SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, false);
            this.func_175804_a(worldIn, p_74875_3_, 7, 3, 0, 7, 3, 7, SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, false);
            this.func_175804_a(worldIn, p_74875_3_, 1, 3, 0, 6, 3, 0, SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, false);
            this.func_175804_a(worldIn, p_74875_3_, 1, 3, 7, 6, 3, 7, SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, false);
            this.func_175804_a(worldIn, p_74875_3_, 0, 1, 3, 0, 2, 4, SimpleRoom.field_175827_c, SimpleRoom.field_175827_c, false);
            this.func_175804_a(worldIn, p_74875_3_, 7, 1, 3, 7, 2, 4, SimpleRoom.field_175827_c, SimpleRoom.field_175827_c, false);
            this.func_175804_a(worldIn, p_74875_3_, 3, 1, 0, 4, 2, 0, SimpleRoom.field_175827_c, SimpleRoom.field_175827_c, false);
            this.func_175804_a(worldIn, p_74875_3_, 3, 1, 7, 4, 2, 7, SimpleRoom.field_175827_c, SimpleRoom.field_175827_c, false);
            if (this.field_175830_k.field_175966_c[EnumFacing.SOUTH.getIndex()]) {
                this.func_175804_a(worldIn, p_74875_3_, 3, 1, 0, 4, 2, 0, SimpleRoom.field_175822_f, SimpleRoom.field_175822_f, false);
            }
            if (this.field_175830_k.field_175966_c[EnumFacing.NORTH.getIndex()]) {
                this.func_175804_a(worldIn, p_74875_3_, 3, 1, 7, 4, 2, 7, SimpleRoom.field_175822_f, SimpleRoom.field_175822_f, false);
            }
            if (this.field_175830_k.field_175966_c[EnumFacing.WEST.getIndex()]) {
                this.func_175804_a(worldIn, p_74875_3_, 0, 1, 3, 0, 2, 4, SimpleRoom.field_175822_f, SimpleRoom.field_175822_f, false);
            }
            if (this.field_175830_k.field_175966_c[EnumFacing.EAST.getIndex()]) {
                this.func_175804_a(worldIn, p_74875_3_, 7, 1, 3, 7, 2, 4, SimpleRoom.field_175822_f, SimpleRoom.field_175822_f, false);
            }
        }
        if (var4) {
            this.func_175804_a(worldIn, p_74875_3_, 3, 1, 3, 4, 1, 4, SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, false);
            this.func_175804_a(worldIn, p_74875_3_, 3, 2, 3, 4, 2, 4, SimpleRoom.field_175828_a, SimpleRoom.field_175828_a, false);
            this.func_175804_a(worldIn, p_74875_3_, 3, 3, 3, 4, 3, 4, SimpleRoom.field_175826_b, SimpleRoom.field_175826_b, false);
        }
        return true;
    }
}
