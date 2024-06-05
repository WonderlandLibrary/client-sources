package net.minecraft.world.gen.structure;

import net.minecraft.util.*;
import java.util.*;

static class FitSimpleRoomTopHelper implements MonumentRoomFitHelper
{
    private FitSimpleRoomTopHelper() {
    }
    
    FitSimpleRoomTopHelper(final SwitchEnumFacing p_i45600_1_) {
        this();
    }
    
    @Override
    public boolean func_175969_a(final RoomDefinition p_175969_1_) {
        return !p_175969_1_.field_175966_c[EnumFacing.WEST.getIndex()] && !p_175969_1_.field_175966_c[EnumFacing.EAST.getIndex()] && !p_175969_1_.field_175966_c[EnumFacing.NORTH.getIndex()] && !p_175969_1_.field_175966_c[EnumFacing.SOUTH.getIndex()] && !p_175969_1_.field_175966_c[EnumFacing.UP.getIndex()];
    }
    
    @Override
    public Piece func_175968_a(final EnumFacing p_175968_1_, final RoomDefinition p_175968_2_, final Random p_175968_3_) {
        p_175968_2_.field_175963_d = true;
        return new SimpleTopRoom(p_175968_1_, p_175968_2_, p_175968_3_);
    }
}
