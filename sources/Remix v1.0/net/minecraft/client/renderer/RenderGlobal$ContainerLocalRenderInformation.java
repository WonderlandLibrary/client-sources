package net.minecraft.client.renderer;

import net.minecraft.client.renderer.chunk.*;
import net.minecraft.util.*;
import java.util.*;

public static class ContainerLocalRenderInformation
{
    final RenderChunk field_178036_a;
    final EnumFacing field_178034_b;
    final Set field_178035_c;
    final int field_178032_d;
    
    public ContainerLocalRenderInformation(final RenderChunk p_i46248_2_, final EnumFacing p_i46248_3_, final int p_i46248_4_) {
        this.field_178035_c = EnumSet.noneOf(EnumFacing.class);
        this.field_178036_a = p_i46248_2_;
        this.field_178034_b = p_i46248_3_;
        this.field_178032_d = p_i46248_4_;
    }
    
    ContainerLocalRenderInformation(final RenderChunk p_i46249_2_, final EnumFacing p_i46249_3_, final int p_i46249_4_, final Object p_i46249_5_) {
        this(p_i46249_2_, p_i46249_3_, p_i46249_4_);
    }
}
