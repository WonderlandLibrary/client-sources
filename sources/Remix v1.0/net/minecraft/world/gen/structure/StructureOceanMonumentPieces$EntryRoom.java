package net.minecraft.world.gen.structure;

import net.minecraft.util.*;
import net.minecraft.world.*;
import java.util.*;

public static class EntryRoom extends Piece
{
    public EntryRoom() {
    }
    
    public EntryRoom(final EnumFacing p_i45592_1_, final RoomDefinition p_i45592_2_) {
        super(1, p_i45592_1_, p_i45592_2_, 1, 1, 1);
    }
    
    @Override
    public boolean addComponentParts(final World worldIn, final Random p_74875_2_, final StructureBoundingBox p_74875_3_) {
        this.func_175804_a(worldIn, p_74875_3_, 0, 3, 0, 2, 3, 7, EntryRoom.field_175826_b, EntryRoom.field_175826_b, false);
        this.func_175804_a(worldIn, p_74875_3_, 5, 3, 0, 7, 3, 7, EntryRoom.field_175826_b, EntryRoom.field_175826_b, false);
        this.func_175804_a(worldIn, p_74875_3_, 0, 2, 0, 1, 2, 7, EntryRoom.field_175826_b, EntryRoom.field_175826_b, false);
        this.func_175804_a(worldIn, p_74875_3_, 6, 2, 0, 7, 2, 7, EntryRoom.field_175826_b, EntryRoom.field_175826_b, false);
        this.func_175804_a(worldIn, p_74875_3_, 0, 1, 0, 0, 1, 7, EntryRoom.field_175826_b, EntryRoom.field_175826_b, false);
        this.func_175804_a(worldIn, p_74875_3_, 7, 1, 0, 7, 1, 7, EntryRoom.field_175826_b, EntryRoom.field_175826_b, false);
        this.func_175804_a(worldIn, p_74875_3_, 0, 1, 7, 7, 3, 7, EntryRoom.field_175826_b, EntryRoom.field_175826_b, false);
        this.func_175804_a(worldIn, p_74875_3_, 1, 1, 0, 2, 3, 0, EntryRoom.field_175826_b, EntryRoom.field_175826_b, false);
        this.func_175804_a(worldIn, p_74875_3_, 5, 1, 0, 6, 3, 0, EntryRoom.field_175826_b, EntryRoom.field_175826_b, false);
        if (this.field_175830_k.field_175966_c[EnumFacing.NORTH.getIndex()]) {
            this.func_175804_a(worldIn, p_74875_3_, 3, 1, 7, 4, 2, 7, EntryRoom.field_175822_f, EntryRoom.field_175822_f, false);
        }
        if (this.field_175830_k.field_175966_c[EnumFacing.WEST.getIndex()]) {
            this.func_175804_a(worldIn, p_74875_3_, 0, 1, 3, 1, 2, 4, EntryRoom.field_175822_f, EntryRoom.field_175822_f, false);
        }
        if (this.field_175830_k.field_175966_c[EnumFacing.EAST.getIndex()]) {
            this.func_175804_a(worldIn, p_74875_3_, 6, 1, 3, 7, 2, 4, EntryRoom.field_175822_f, EntryRoom.field_175822_f, false);
        }
        return true;
    }
}
