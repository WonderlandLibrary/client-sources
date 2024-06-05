package net.minecraft.client.renderer;

import net.minecraft.client.renderer.texture.*;

public class StitcherException extends RuntimeException
{
    private final Stitcher.Holder field_98149_a;
    
    public StitcherException(final Stitcher.Holder p_i2344_1_, final String p_i2344_2_) {
        super(p_i2344_2_);
        this.field_98149_a = p_i2344_1_;
    }
}
