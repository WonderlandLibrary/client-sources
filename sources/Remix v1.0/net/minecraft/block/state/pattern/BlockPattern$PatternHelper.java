package net.minecraft.block.state.pattern;

import net.minecraft.util.*;
import com.google.common.cache.*;
import net.minecraft.block.state.*;

public static class PatternHelper
{
    private final BlockPos field_177674_a;
    private final EnumFacing field_177672_b;
    private final EnumFacing field_177673_c;
    private final LoadingCache field_177671_d;
    
    public PatternHelper(final BlockPos p_i45655_1_, final EnumFacing p_i45655_2_, final EnumFacing p_i45655_3_, final LoadingCache p_i45655_4_) {
        this.field_177674_a = p_i45655_1_;
        this.field_177672_b = p_i45655_2_;
        this.field_177673_c = p_i45655_3_;
        this.field_177671_d = p_i45655_4_;
    }
    
    public EnumFacing func_177669_b() {
        return this.field_177672_b;
    }
    
    public EnumFacing func_177668_c() {
        return this.field_177673_c;
    }
    
    public BlockWorldState func_177670_a(final int p_177670_1_, final int p_177670_2_, final int p_177670_3_) {
        return (BlockWorldState)this.field_177671_d.getUnchecked((Object)BlockPattern.func_177683_a(this.field_177674_a, this.func_177669_b(), this.func_177668_c(), p_177670_1_, p_177670_2_, p_177670_3_));
    }
}
