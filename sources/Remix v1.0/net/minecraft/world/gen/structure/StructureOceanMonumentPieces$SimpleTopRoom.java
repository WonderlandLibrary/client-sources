package net.minecraft.world.gen.structure;

import net.minecraft.util.*;
import java.util.*;
import net.minecraft.world.*;
import net.minecraft.init.*;

public static class SimpleTopRoom extends Piece
{
    public SimpleTopRoom() {
    }
    
    public SimpleTopRoom(final EnumFacing p_i45586_1_, final RoomDefinition p_i45586_2_, final Random p_i45586_3_) {
        super(1, p_i45586_1_, p_i45586_2_, 1, 1, 1);
    }
    
    @Override
    public boolean addComponentParts(final World worldIn, final Random p_74875_2_, final StructureBoundingBox p_74875_3_) {
        if (this.field_175830_k.field_175967_a / 25 > 0) {
            this.func_175821_a(worldIn, p_74875_3_, 0, 0, this.field_175830_k.field_175966_c[EnumFacing.DOWN.getIndex()]);
        }
        if (this.field_175830_k.field_175965_b[EnumFacing.UP.getIndex()] == null) {
            this.func_175819_a(worldIn, p_74875_3_, 1, 4, 1, 6, 4, 6, SimpleTopRoom.field_175828_a);
        }
        for (int var4 = 1; var4 <= 6; ++var4) {
            for (int var5 = 1; var5 <= 6; ++var5) {
                if (p_74875_2_.nextInt(3) != 0) {
                    final int var6 = 2 + ((p_74875_2_.nextInt(4) != 0) ? 1 : 0);
                    this.func_175804_a(worldIn, p_74875_3_, var4, var6, var5, var4, 3, var5, Blocks.sponge.getStateFromMeta(1), Blocks.sponge.getStateFromMeta(1), false);
                }
            }
        }
        this.func_175804_a(worldIn, p_74875_3_, 0, 1, 0, 0, 1, 7, SimpleTopRoom.field_175826_b, SimpleTopRoom.field_175826_b, false);
        this.func_175804_a(worldIn, p_74875_3_, 7, 1, 0, 7, 1, 7, SimpleTopRoom.field_175826_b, SimpleTopRoom.field_175826_b, false);
        this.func_175804_a(worldIn, p_74875_3_, 1, 1, 0, 6, 1, 0, SimpleTopRoom.field_175826_b, SimpleTopRoom.field_175826_b, false);
        this.func_175804_a(worldIn, p_74875_3_, 1, 1, 7, 6, 1, 7, SimpleTopRoom.field_175826_b, SimpleTopRoom.field_175826_b, false);
        this.func_175804_a(worldIn, p_74875_3_, 0, 2, 0, 0, 2, 7, SimpleTopRoom.field_175827_c, SimpleTopRoom.field_175827_c, false);
        this.func_175804_a(worldIn, p_74875_3_, 7, 2, 0, 7, 2, 7, SimpleTopRoom.field_175827_c, SimpleTopRoom.field_175827_c, false);
        this.func_175804_a(worldIn, p_74875_3_, 1, 2, 0, 6, 2, 0, SimpleTopRoom.field_175827_c, SimpleTopRoom.field_175827_c, false);
        this.func_175804_a(worldIn, p_74875_3_, 1, 2, 7, 6, 2, 7, SimpleTopRoom.field_175827_c, SimpleTopRoom.field_175827_c, false);
        this.func_175804_a(worldIn, p_74875_3_, 0, 3, 0, 0, 3, 7, SimpleTopRoom.field_175826_b, SimpleTopRoom.field_175826_b, false);
        this.func_175804_a(worldIn, p_74875_3_, 7, 3, 0, 7, 3, 7, SimpleTopRoom.field_175826_b, SimpleTopRoom.field_175826_b, false);
        this.func_175804_a(worldIn, p_74875_3_, 1, 3, 0, 6, 3, 0, SimpleTopRoom.field_175826_b, SimpleTopRoom.field_175826_b, false);
        this.func_175804_a(worldIn, p_74875_3_, 1, 3, 7, 6, 3, 7, SimpleTopRoom.field_175826_b, SimpleTopRoom.field_175826_b, false);
        this.func_175804_a(worldIn, p_74875_3_, 0, 1, 3, 0, 2, 4, SimpleTopRoom.field_175827_c, SimpleTopRoom.field_175827_c, false);
        this.func_175804_a(worldIn, p_74875_3_, 7, 1, 3, 7, 2, 4, SimpleTopRoom.field_175827_c, SimpleTopRoom.field_175827_c, false);
        this.func_175804_a(worldIn, p_74875_3_, 3, 1, 0, 4, 2, 0, SimpleTopRoom.field_175827_c, SimpleTopRoom.field_175827_c, false);
        this.func_175804_a(worldIn, p_74875_3_, 3, 1, 7, 4, 2, 7, SimpleTopRoom.field_175827_c, SimpleTopRoom.field_175827_c, false);
        if (this.field_175830_k.field_175966_c[EnumFacing.SOUTH.getIndex()]) {
            this.func_175804_a(worldIn, p_74875_3_, 3, 1, 0, 4, 2, 0, SimpleTopRoom.field_175822_f, SimpleTopRoom.field_175822_f, false);
        }
        return true;
    }
}
