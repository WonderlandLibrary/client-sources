package net.minecraft.world.gen.structure;

import net.minecraft.util.*;
import java.util.*;

static class XDoubleRoomFitHelper implements MonumentRoomFitHelper
{
    private XDoubleRoomFitHelper() {
    }
    
    XDoubleRoomFitHelper(final SwitchEnumFacing p_i45606_1_) {
        this();
    }
    
    @Override
    public boolean func_175969_a(final RoomDefinition p_175969_1_) {
        return p_175969_1_.field_175966_c[EnumFacing.EAST.getIndex()] && !p_175969_1_.field_175965_b[EnumFacing.EAST.getIndex()].field_175963_d;
    }
    
    @Override
    public Piece func_175968_a(final EnumFacing p_175968_1_, final RoomDefinition p_175968_2_, final Random p_175968_3_) {
        p_175968_2_.field_175963_d = true;
        p_175968_2_.field_175965_b[EnumFacing.EAST.getIndex()].field_175963_d = true;
        return new DoubleXRoom(p_175968_1_, p_175968_2_, p_175968_3_);
    }
}
