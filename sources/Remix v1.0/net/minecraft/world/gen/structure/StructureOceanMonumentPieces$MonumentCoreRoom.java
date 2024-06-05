package net.minecraft.world.gen.structure;

import net.minecraft.util.*;
import java.util.*;
import net.minecraft.world.*;
import net.minecraft.init.*;
import net.minecraft.block.state.*;

public static class MonumentCoreRoom extends Piece
{
    public MonumentCoreRoom() {
    }
    
    public MonumentCoreRoom(final EnumFacing p_i45598_1_, final RoomDefinition p_i45598_2_, final Random p_i45598_3_) {
        super(1, p_i45598_1_, p_i45598_2_, 2, 2, 2);
    }
    
    @Override
    public boolean addComponentParts(final World worldIn, final Random p_74875_2_, final StructureBoundingBox p_74875_3_) {
        this.func_175819_a(worldIn, p_74875_3_, 1, 8, 0, 14, 8, 14, MonumentCoreRoom.field_175828_a);
        final byte var4 = 7;
        IBlockState var5 = MonumentCoreRoom.field_175826_b;
        this.func_175804_a(worldIn, p_74875_3_, 0, var4, 0, 0, var4, 15, var5, var5, false);
        this.func_175804_a(worldIn, p_74875_3_, 15, var4, 0, 15, var4, 15, var5, var5, false);
        this.func_175804_a(worldIn, p_74875_3_, 1, var4, 0, 15, var4, 0, var5, var5, false);
        this.func_175804_a(worldIn, p_74875_3_, 1, var4, 15, 14, var4, 15, var5, var5, false);
        for (int var6 = 1; var6 <= 6; ++var6) {
            var5 = MonumentCoreRoom.field_175826_b;
            if (var6 == 2 || var6 == 6) {
                var5 = MonumentCoreRoom.field_175828_a;
            }
            for (int var7 = 0; var7 <= 15; var7 += 15) {
                this.func_175804_a(worldIn, p_74875_3_, var7, var6, 0, var7, var6, 1, var5, var5, false);
                this.func_175804_a(worldIn, p_74875_3_, var7, var6, 6, var7, var6, 9, var5, var5, false);
                this.func_175804_a(worldIn, p_74875_3_, var7, var6, 14, var7, var6, 15, var5, var5, false);
            }
            this.func_175804_a(worldIn, p_74875_3_, 1, var6, 0, 1, var6, 0, var5, var5, false);
            this.func_175804_a(worldIn, p_74875_3_, 6, var6, 0, 9, var6, 0, var5, var5, false);
            this.func_175804_a(worldIn, p_74875_3_, 14, var6, 0, 14, var6, 0, var5, var5, false);
            this.func_175804_a(worldIn, p_74875_3_, 1, var6, 15, 14, var6, 15, var5, var5, false);
        }
        this.func_175804_a(worldIn, p_74875_3_, 6, 3, 6, 9, 6, 9, MonumentCoreRoom.field_175827_c, MonumentCoreRoom.field_175827_c, false);
        this.func_175804_a(worldIn, p_74875_3_, 7, 4, 7, 8, 5, 8, Blocks.gold_block.getDefaultState(), Blocks.gold_block.getDefaultState(), false);
        for (int var6 = 3; var6 <= 6; var6 += 3) {
            for (int var8 = 6; var8 <= 9; var8 += 3) {
                this.func_175811_a(worldIn, MonumentCoreRoom.field_175825_e, var8, var6, 6, p_74875_3_);
                this.func_175811_a(worldIn, MonumentCoreRoom.field_175825_e, var8, var6, 9, p_74875_3_);
            }
        }
        this.func_175804_a(worldIn, p_74875_3_, 5, 1, 6, 5, 2, 6, MonumentCoreRoom.field_175826_b, MonumentCoreRoom.field_175826_b, false);
        this.func_175804_a(worldIn, p_74875_3_, 5, 1, 9, 5, 2, 9, MonumentCoreRoom.field_175826_b, MonumentCoreRoom.field_175826_b, false);
        this.func_175804_a(worldIn, p_74875_3_, 10, 1, 6, 10, 2, 6, MonumentCoreRoom.field_175826_b, MonumentCoreRoom.field_175826_b, false);
        this.func_175804_a(worldIn, p_74875_3_, 10, 1, 9, 10, 2, 9, MonumentCoreRoom.field_175826_b, MonumentCoreRoom.field_175826_b, false);
        this.func_175804_a(worldIn, p_74875_3_, 6, 1, 5, 6, 2, 5, MonumentCoreRoom.field_175826_b, MonumentCoreRoom.field_175826_b, false);
        this.func_175804_a(worldIn, p_74875_3_, 9, 1, 5, 9, 2, 5, MonumentCoreRoom.field_175826_b, MonumentCoreRoom.field_175826_b, false);
        this.func_175804_a(worldIn, p_74875_3_, 6, 1, 10, 6, 2, 10, MonumentCoreRoom.field_175826_b, MonumentCoreRoom.field_175826_b, false);
        this.func_175804_a(worldIn, p_74875_3_, 9, 1, 10, 9, 2, 10, MonumentCoreRoom.field_175826_b, MonumentCoreRoom.field_175826_b, false);
        this.func_175804_a(worldIn, p_74875_3_, 5, 2, 5, 5, 6, 5, MonumentCoreRoom.field_175826_b, MonumentCoreRoom.field_175826_b, false);
        this.func_175804_a(worldIn, p_74875_3_, 5, 2, 10, 5, 6, 10, MonumentCoreRoom.field_175826_b, MonumentCoreRoom.field_175826_b, false);
        this.func_175804_a(worldIn, p_74875_3_, 10, 2, 5, 10, 6, 5, MonumentCoreRoom.field_175826_b, MonumentCoreRoom.field_175826_b, false);
        this.func_175804_a(worldIn, p_74875_3_, 10, 2, 10, 10, 6, 10, MonumentCoreRoom.field_175826_b, MonumentCoreRoom.field_175826_b, false);
        this.func_175804_a(worldIn, p_74875_3_, 5, 7, 1, 5, 7, 6, MonumentCoreRoom.field_175826_b, MonumentCoreRoom.field_175826_b, false);
        this.func_175804_a(worldIn, p_74875_3_, 10, 7, 1, 10, 7, 6, MonumentCoreRoom.field_175826_b, MonumentCoreRoom.field_175826_b, false);
        this.func_175804_a(worldIn, p_74875_3_, 5, 7, 9, 5, 7, 14, MonumentCoreRoom.field_175826_b, MonumentCoreRoom.field_175826_b, false);
        this.func_175804_a(worldIn, p_74875_3_, 10, 7, 9, 10, 7, 14, MonumentCoreRoom.field_175826_b, MonumentCoreRoom.field_175826_b, false);
        this.func_175804_a(worldIn, p_74875_3_, 1, 7, 5, 6, 7, 5, MonumentCoreRoom.field_175826_b, MonumentCoreRoom.field_175826_b, false);
        this.func_175804_a(worldIn, p_74875_3_, 1, 7, 10, 6, 7, 10, MonumentCoreRoom.field_175826_b, MonumentCoreRoom.field_175826_b, false);
        this.func_175804_a(worldIn, p_74875_3_, 9, 7, 5, 14, 7, 5, MonumentCoreRoom.field_175826_b, MonumentCoreRoom.field_175826_b, false);
        this.func_175804_a(worldIn, p_74875_3_, 9, 7, 10, 14, 7, 10, MonumentCoreRoom.field_175826_b, MonumentCoreRoom.field_175826_b, false);
        this.func_175804_a(worldIn, p_74875_3_, 2, 1, 2, 2, 1, 3, MonumentCoreRoom.field_175826_b, MonumentCoreRoom.field_175826_b, false);
        this.func_175804_a(worldIn, p_74875_3_, 3, 1, 2, 3, 1, 2, MonumentCoreRoom.field_175826_b, MonumentCoreRoom.field_175826_b, false);
        this.func_175804_a(worldIn, p_74875_3_, 13, 1, 2, 13, 1, 3, MonumentCoreRoom.field_175826_b, MonumentCoreRoom.field_175826_b, false);
        this.func_175804_a(worldIn, p_74875_3_, 12, 1, 2, 12, 1, 2, MonumentCoreRoom.field_175826_b, MonumentCoreRoom.field_175826_b, false);
        this.func_175804_a(worldIn, p_74875_3_, 2, 1, 12, 2, 1, 13, MonumentCoreRoom.field_175826_b, MonumentCoreRoom.field_175826_b, false);
        this.func_175804_a(worldIn, p_74875_3_, 3, 1, 13, 3, 1, 13, MonumentCoreRoom.field_175826_b, MonumentCoreRoom.field_175826_b, false);
        this.func_175804_a(worldIn, p_74875_3_, 13, 1, 12, 13, 1, 13, MonumentCoreRoom.field_175826_b, MonumentCoreRoom.field_175826_b, false);
        this.func_175804_a(worldIn, p_74875_3_, 12, 1, 13, 12, 1, 13, MonumentCoreRoom.field_175826_b, MonumentCoreRoom.field_175826_b, false);
        return true;
    }
}
