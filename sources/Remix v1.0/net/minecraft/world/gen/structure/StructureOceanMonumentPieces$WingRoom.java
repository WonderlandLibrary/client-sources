package net.minecraft.world.gen.structure;

import net.minecraft.util.*;
import net.minecraft.world.*;
import java.util.*;

public static class WingRoom extends Piece
{
    private int field_175834_o;
    
    public WingRoom() {
    }
    
    public WingRoom(final EnumFacing p_i45585_1_, final StructureBoundingBox p_i45585_2_, final int p_i45585_3_) {
        super(p_i45585_1_, p_i45585_2_);
        this.field_175834_o = (p_i45585_3_ & 0x1);
    }
    
    @Override
    public boolean addComponentParts(final World worldIn, final Random p_74875_2_, final StructureBoundingBox p_74875_3_) {
        if (this.field_175834_o == 0) {
            for (int var4 = 0; var4 < 4; ++var4) {
                this.func_175804_a(worldIn, p_74875_3_, 10 - var4, 3 - var4, 20 - var4, 12 + var4, 3 - var4, 20, WingRoom.field_175826_b, WingRoom.field_175826_b, false);
            }
            this.func_175804_a(worldIn, p_74875_3_, 7, 0, 6, 15, 0, 16, WingRoom.field_175826_b, WingRoom.field_175826_b, false);
            this.func_175804_a(worldIn, p_74875_3_, 6, 0, 6, 6, 3, 20, WingRoom.field_175826_b, WingRoom.field_175826_b, false);
            this.func_175804_a(worldIn, p_74875_3_, 16, 0, 6, 16, 3, 20, WingRoom.field_175826_b, WingRoom.field_175826_b, false);
            this.func_175804_a(worldIn, p_74875_3_, 7, 1, 7, 7, 1, 20, WingRoom.field_175826_b, WingRoom.field_175826_b, false);
            this.func_175804_a(worldIn, p_74875_3_, 15, 1, 7, 15, 1, 20, WingRoom.field_175826_b, WingRoom.field_175826_b, false);
            this.func_175804_a(worldIn, p_74875_3_, 7, 1, 6, 9, 3, 6, WingRoom.field_175826_b, WingRoom.field_175826_b, false);
            this.func_175804_a(worldIn, p_74875_3_, 13, 1, 6, 15, 3, 6, WingRoom.field_175826_b, WingRoom.field_175826_b, false);
            this.func_175804_a(worldIn, p_74875_3_, 8, 1, 7, 9, 1, 7, WingRoom.field_175826_b, WingRoom.field_175826_b, false);
            this.func_175804_a(worldIn, p_74875_3_, 13, 1, 7, 14, 1, 7, WingRoom.field_175826_b, WingRoom.field_175826_b, false);
            this.func_175804_a(worldIn, p_74875_3_, 9, 0, 5, 13, 0, 5, WingRoom.field_175826_b, WingRoom.field_175826_b, false);
            this.func_175804_a(worldIn, p_74875_3_, 10, 0, 7, 12, 0, 7, WingRoom.field_175827_c, WingRoom.field_175827_c, false);
            this.func_175804_a(worldIn, p_74875_3_, 8, 0, 10, 8, 0, 12, WingRoom.field_175827_c, WingRoom.field_175827_c, false);
            this.func_175804_a(worldIn, p_74875_3_, 14, 0, 10, 14, 0, 12, WingRoom.field_175827_c, WingRoom.field_175827_c, false);
            for (int var4 = 18; var4 >= 7; var4 -= 3) {
                this.func_175811_a(worldIn, WingRoom.field_175825_e, 6, 3, var4, p_74875_3_);
                this.func_175811_a(worldIn, WingRoom.field_175825_e, 16, 3, var4, p_74875_3_);
            }
            this.func_175811_a(worldIn, WingRoom.field_175825_e, 10, 0, 10, p_74875_3_);
            this.func_175811_a(worldIn, WingRoom.field_175825_e, 12, 0, 10, p_74875_3_);
            this.func_175811_a(worldIn, WingRoom.field_175825_e, 10, 0, 12, p_74875_3_);
            this.func_175811_a(worldIn, WingRoom.field_175825_e, 12, 0, 12, p_74875_3_);
            this.func_175811_a(worldIn, WingRoom.field_175825_e, 8, 3, 6, p_74875_3_);
            this.func_175811_a(worldIn, WingRoom.field_175825_e, 14, 3, 6, p_74875_3_);
            this.func_175811_a(worldIn, WingRoom.field_175826_b, 4, 2, 4, p_74875_3_);
            this.func_175811_a(worldIn, WingRoom.field_175825_e, 4, 1, 4, p_74875_3_);
            this.func_175811_a(worldIn, WingRoom.field_175826_b, 4, 0, 4, p_74875_3_);
            this.func_175811_a(worldIn, WingRoom.field_175826_b, 18, 2, 4, p_74875_3_);
            this.func_175811_a(worldIn, WingRoom.field_175825_e, 18, 1, 4, p_74875_3_);
            this.func_175811_a(worldIn, WingRoom.field_175826_b, 18, 0, 4, p_74875_3_);
            this.func_175811_a(worldIn, WingRoom.field_175826_b, 4, 2, 18, p_74875_3_);
            this.func_175811_a(worldIn, WingRoom.field_175825_e, 4, 1, 18, p_74875_3_);
            this.func_175811_a(worldIn, WingRoom.field_175826_b, 4, 0, 18, p_74875_3_);
            this.func_175811_a(worldIn, WingRoom.field_175826_b, 18, 2, 18, p_74875_3_);
            this.func_175811_a(worldIn, WingRoom.field_175825_e, 18, 1, 18, p_74875_3_);
            this.func_175811_a(worldIn, WingRoom.field_175826_b, 18, 0, 18, p_74875_3_);
            this.func_175811_a(worldIn, WingRoom.field_175826_b, 9, 7, 20, p_74875_3_);
            this.func_175811_a(worldIn, WingRoom.field_175826_b, 13, 7, 20, p_74875_3_);
            this.func_175804_a(worldIn, p_74875_3_, 6, 0, 21, 7, 4, 21, WingRoom.field_175826_b, WingRoom.field_175826_b, false);
            this.func_175804_a(worldIn, p_74875_3_, 15, 0, 21, 16, 4, 21, WingRoom.field_175826_b, WingRoom.field_175826_b, false);
            this.func_175817_a(worldIn, p_74875_3_, 11, 2, 16);
        }
        else if (this.field_175834_o == 1) {
            this.func_175804_a(worldIn, p_74875_3_, 9, 3, 18, 13, 3, 20, WingRoom.field_175826_b, WingRoom.field_175826_b, false);
            this.func_175804_a(worldIn, p_74875_3_, 9, 0, 18, 9, 2, 18, WingRoom.field_175826_b, WingRoom.field_175826_b, false);
            this.func_175804_a(worldIn, p_74875_3_, 13, 0, 18, 13, 2, 18, WingRoom.field_175826_b, WingRoom.field_175826_b, false);
            byte var5 = 9;
            final byte var6 = 20;
            final byte var7 = 5;
            for (int var8 = 0; var8 < 2; ++var8) {
                this.func_175811_a(worldIn, WingRoom.field_175826_b, var5, var7 + 1, var6, p_74875_3_);
                this.func_175811_a(worldIn, WingRoom.field_175825_e, var5, var7, var6, p_74875_3_);
                this.func_175811_a(worldIn, WingRoom.field_175826_b, var5, var7 - 1, var6, p_74875_3_);
                var5 = 13;
            }
            this.func_175804_a(worldIn, p_74875_3_, 7, 3, 7, 15, 3, 14, WingRoom.field_175826_b, WingRoom.field_175826_b, false);
            var5 = 10;
            for (int var8 = 0; var8 < 2; ++var8) {
                this.func_175804_a(worldIn, p_74875_3_, var5, 0, 10, var5, 6, 10, WingRoom.field_175826_b, WingRoom.field_175826_b, false);
                this.func_175804_a(worldIn, p_74875_3_, var5, 0, 12, var5, 6, 12, WingRoom.field_175826_b, WingRoom.field_175826_b, false);
                this.func_175811_a(worldIn, WingRoom.field_175825_e, var5, 0, 10, p_74875_3_);
                this.func_175811_a(worldIn, WingRoom.field_175825_e, var5, 0, 12, p_74875_3_);
                this.func_175811_a(worldIn, WingRoom.field_175825_e, var5, 4, 10, p_74875_3_);
                this.func_175811_a(worldIn, WingRoom.field_175825_e, var5, 4, 12, p_74875_3_);
                var5 = 12;
            }
            var5 = 8;
            for (int var8 = 0; var8 < 2; ++var8) {
                this.func_175804_a(worldIn, p_74875_3_, var5, 0, 7, var5, 2, 7, WingRoom.field_175826_b, WingRoom.field_175826_b, false);
                this.func_175804_a(worldIn, p_74875_3_, var5, 0, 14, var5, 2, 14, WingRoom.field_175826_b, WingRoom.field_175826_b, false);
                var5 = 14;
            }
            this.func_175804_a(worldIn, p_74875_3_, 8, 3, 8, 8, 3, 13, WingRoom.field_175827_c, WingRoom.field_175827_c, false);
            this.func_175804_a(worldIn, p_74875_3_, 14, 3, 8, 14, 3, 13, WingRoom.field_175827_c, WingRoom.field_175827_c, false);
            this.func_175817_a(worldIn, p_74875_3_, 11, 5, 13);
        }
        return true;
    }
}
