package net.minecraft.world.gen.structure;

import net.minecraft.util.*;
import net.minecraft.world.*;
import java.util.*;

public static class Penthouse extends Piece
{
    public Penthouse() {
    }
    
    public Penthouse(final EnumFacing p_i45591_1_, final StructureBoundingBox p_i45591_2_) {
        super(p_i45591_1_, p_i45591_2_);
    }
    
    @Override
    public boolean addComponentParts(final World worldIn, final Random p_74875_2_, final StructureBoundingBox p_74875_3_) {
        this.func_175804_a(worldIn, p_74875_3_, 2, -1, 2, 11, -1, 11, Penthouse.field_175826_b, Penthouse.field_175826_b, false);
        this.func_175804_a(worldIn, p_74875_3_, 0, -1, 0, 1, -1, 11, Penthouse.field_175828_a, Penthouse.field_175828_a, false);
        this.func_175804_a(worldIn, p_74875_3_, 12, -1, 0, 13, -1, 11, Penthouse.field_175828_a, Penthouse.field_175828_a, false);
        this.func_175804_a(worldIn, p_74875_3_, 2, -1, 0, 11, -1, 1, Penthouse.field_175828_a, Penthouse.field_175828_a, false);
        this.func_175804_a(worldIn, p_74875_3_, 2, -1, 12, 11, -1, 13, Penthouse.field_175828_a, Penthouse.field_175828_a, false);
        this.func_175804_a(worldIn, p_74875_3_, 0, 0, 0, 0, 0, 13, Penthouse.field_175826_b, Penthouse.field_175826_b, false);
        this.func_175804_a(worldIn, p_74875_3_, 13, 0, 0, 13, 0, 13, Penthouse.field_175826_b, Penthouse.field_175826_b, false);
        this.func_175804_a(worldIn, p_74875_3_, 1, 0, 0, 12, 0, 0, Penthouse.field_175826_b, Penthouse.field_175826_b, false);
        this.func_175804_a(worldIn, p_74875_3_, 1, 0, 13, 12, 0, 13, Penthouse.field_175826_b, Penthouse.field_175826_b, false);
        for (int var4 = 2; var4 <= 11; var4 += 3) {
            this.func_175811_a(worldIn, Penthouse.field_175825_e, 0, 0, var4, p_74875_3_);
            this.func_175811_a(worldIn, Penthouse.field_175825_e, 13, 0, var4, p_74875_3_);
            this.func_175811_a(worldIn, Penthouse.field_175825_e, var4, 0, 0, p_74875_3_);
        }
        this.func_175804_a(worldIn, p_74875_3_, 2, 0, 3, 4, 0, 9, Penthouse.field_175826_b, Penthouse.field_175826_b, false);
        this.func_175804_a(worldIn, p_74875_3_, 9, 0, 3, 11, 0, 9, Penthouse.field_175826_b, Penthouse.field_175826_b, false);
        this.func_175804_a(worldIn, p_74875_3_, 4, 0, 9, 9, 0, 11, Penthouse.field_175826_b, Penthouse.field_175826_b, false);
        this.func_175811_a(worldIn, Penthouse.field_175826_b, 5, 0, 8, p_74875_3_);
        this.func_175811_a(worldIn, Penthouse.field_175826_b, 8, 0, 8, p_74875_3_);
        this.func_175811_a(worldIn, Penthouse.field_175826_b, 10, 0, 10, p_74875_3_);
        this.func_175811_a(worldIn, Penthouse.field_175826_b, 3, 0, 10, p_74875_3_);
        this.func_175804_a(worldIn, p_74875_3_, 3, 0, 3, 3, 0, 7, Penthouse.field_175827_c, Penthouse.field_175827_c, false);
        this.func_175804_a(worldIn, p_74875_3_, 10, 0, 3, 10, 0, 7, Penthouse.field_175827_c, Penthouse.field_175827_c, false);
        this.func_175804_a(worldIn, p_74875_3_, 6, 0, 10, 7, 0, 10, Penthouse.field_175827_c, Penthouse.field_175827_c, false);
        byte var5 = 3;
        for (int var6 = 0; var6 < 2; ++var6) {
            for (int var7 = 2; var7 <= 8; var7 += 3) {
                this.func_175804_a(worldIn, p_74875_3_, var5, 0, var7, var5, 2, var7, Penthouse.field_175826_b, Penthouse.field_175826_b, false);
            }
            var5 = 10;
        }
        this.func_175804_a(worldIn, p_74875_3_, 5, 0, 10, 5, 2, 10, Penthouse.field_175826_b, Penthouse.field_175826_b, false);
        this.func_175804_a(worldIn, p_74875_3_, 8, 0, 10, 8, 2, 10, Penthouse.field_175826_b, Penthouse.field_175826_b, false);
        this.func_175804_a(worldIn, p_74875_3_, 6, -1, 7, 7, -1, 8, Penthouse.field_175827_c, Penthouse.field_175827_c, false);
        this.func_175804_a(worldIn, p_74875_3_, 6, -1, 3, 7, -1, 4, Penthouse.field_175822_f, Penthouse.field_175822_f, false);
        this.func_175817_a(worldIn, p_74875_3_, 6, 1, 6);
        return true;
    }
}
