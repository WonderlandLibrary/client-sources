package net.minecraft.world.gen.structure;

import net.minecraft.util.*;
import java.util.*;

static class ZDoubleRoomFitHelper implements MonumentRoomFitHelper
{
    private ZDoubleRoomFitHelper() {
    }
    
    ZDoubleRoomFitHelper(final SwitchEnumFacing p_i45602_1_) {
        this();
    }
    
    @Override
    public boolean func_175969_a(final RoomDefinition p_175969_1_) {
        return p_175969_1_.field_175966_c[EnumFacing.NORTH.getIndex()] && !p_175969_1_.field_175965_b[EnumFacing.NORTH.getIndex()].field_175963_d;
    }
    
    @Override
    public Piece func_175968_a(final EnumFacing p_175968_1_, final RoomDefinition p_175968_2_, final Random p_175968_3_) {
        RoomDefinition var4 = p_175968_2_;
        if (!p_175968_2_.field_175966_c[EnumFacing.NORTH.getIndex()] || p_175968_2_.field_175965_b[EnumFacing.NORTH.getIndex()].field_175963_d) {
            var4 = p_175968_2_.field_175965_b[EnumFacing.SOUTH.getIndex()];
        }
        var4.field_175963_d = true;
        var4.field_175965_b[EnumFacing.NORTH.getIndex()].field_175963_d = true;
        return new DoubleZRoom(p_175968_1_, var4, p_175968_3_);
    }
}
