package net.minecraft.client.renderer.block.statemap;

import net.minecraft.block.properties.*;
import com.google.common.collect.*;
import java.util.*;

public static class Builder
{
    private final List field_178444_c;
    private IProperty field_178445_a;
    private String field_178443_b;
    
    public Builder() {
        this.field_178444_c = Lists.newArrayList();
    }
    
    public Builder func_178440_a(final IProperty p_178440_1_) {
        this.field_178445_a = p_178440_1_;
        return this;
    }
    
    public Builder func_178439_a(final String p_178439_1_) {
        this.field_178443_b = p_178439_1_;
        return this;
    }
    
    public Builder func_178442_a(final IProperty... p_178442_1_) {
        Collections.addAll(this.field_178444_c, p_178442_1_);
        return this;
    }
    
    public StateMap build() {
        return new StateMap(this.field_178445_a, this.field_178443_b, this.field_178444_c, null);
    }
}
