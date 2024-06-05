package net.minecraft.client.renderer.block.model;

import javax.vecmath.*;
import net.minecraft.util.*;

public class BlockPartRotation
{
    public final Vector3f field_178344_a;
    public final EnumFacing.Axis field_178342_b;
    public final float field_178343_c;
    public final boolean field_178341_d;
    
    public BlockPartRotation(final Vector3f p_i46229_1_, final EnumFacing.Axis p_i46229_2_, final float p_i46229_3_, final boolean p_i46229_4_) {
        this.field_178344_a = p_i46229_1_;
        this.field_178342_b = p_i46229_2_;
        this.field_178343_c = p_i46229_3_;
        this.field_178341_d = p_i46229_4_;
    }
}
