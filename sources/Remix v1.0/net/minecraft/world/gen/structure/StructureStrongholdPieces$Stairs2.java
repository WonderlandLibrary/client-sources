package net.minecraft.world.gen.structure;

import com.google.common.collect.*;
import java.util.*;
import net.minecraft.util.*;

public static class Stairs2 extends Stairs
{
    public PieceWeight strongholdPieceWeight;
    public PortalRoom strongholdPortalRoom;
    public List field_75026_c;
    
    public Stairs2() {
        this.field_75026_c = Lists.newArrayList();
    }
    
    public Stairs2(final int p_i2083_1_, final Random p_i2083_2_, final int p_i2083_3_, final int p_i2083_4_) {
        super(0, p_i2083_2_, p_i2083_3_, p_i2083_4_);
        this.field_75026_c = Lists.newArrayList();
    }
    
    @Override
    public BlockPos func_180776_a() {
        return (this.strongholdPortalRoom != null) ? this.strongholdPortalRoom.func_180776_a() : super.func_180776_a();
    }
}
