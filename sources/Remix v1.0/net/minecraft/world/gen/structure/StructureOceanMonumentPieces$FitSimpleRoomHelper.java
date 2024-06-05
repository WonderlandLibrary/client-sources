package net.minecraft.world.gen.structure;

import net.minecraft.util.*;
import java.util.*;

static class FitSimpleRoomHelper implements MonumentRoomFitHelper
{
    private FitSimpleRoomHelper() {
    }
    
    FitSimpleRoomHelper(final SwitchEnumFacing p_i45601_1_) {
        this();
    }
    
    @Override
    public boolean func_175969_a(final RoomDefinition p_175969_1_) {
        return true;
    }
    
    @Override
    public Piece func_175968_a(final EnumFacing p_175968_1_, final RoomDefinition p_175968_2_, final Random p_175968_3_) {
        p_175968_2_.field_175963_d = true;
        return new SimpleRoom(p_175968_1_, p_175968_2_, p_175968_3_);
    }
}
