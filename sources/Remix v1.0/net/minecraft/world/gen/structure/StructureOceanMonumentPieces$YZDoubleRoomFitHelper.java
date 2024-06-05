package net.minecraft.world.gen.structure;

import net.minecraft.util.*;
import java.util.*;

static class YZDoubleRoomFitHelper implements MonumentRoomFitHelper
{
    private YZDoubleRoomFitHelper() {
    }
    
    YZDoubleRoomFitHelper(final SwitchEnumFacing p_i45603_1_) {
        this();
    }
    
    @Override
    public boolean func_175969_a(final RoomDefinition p_175969_1_) {
        if (p_175969_1_.field_175966_c[EnumFacing.NORTH.getIndex()] && !p_175969_1_.field_175965_b[EnumFacing.NORTH.getIndex()].field_175963_d && p_175969_1_.field_175966_c[EnumFacing.UP.getIndex()] && !p_175969_1_.field_175965_b[EnumFacing.UP.getIndex()].field_175963_d) {
            final RoomDefinition var2 = p_175969_1_.field_175965_b[EnumFacing.NORTH.getIndex()];
            return var2.field_175966_c[EnumFacing.UP.getIndex()] && !var2.field_175965_b[EnumFacing.UP.getIndex()].field_175963_d;
        }
        return false;
    }
    
    @Override
    public Piece func_175968_a(final EnumFacing p_175968_1_, final RoomDefinition p_175968_2_, final Random p_175968_3_) {
        p_175968_2_.field_175963_d = true;
        p_175968_2_.field_175965_b[EnumFacing.NORTH.getIndex()].field_175963_d = true;
        p_175968_2_.field_175965_b[EnumFacing.UP.getIndex()].field_175963_d = true;
        p_175968_2_.field_175965_b[EnumFacing.NORTH.getIndex()].field_175965_b[EnumFacing.UP.getIndex()].field_175963_d = true;
        return new DoubleYZRoom(p_175968_1_, p_175968_2_, p_175968_3_);
    }
}
