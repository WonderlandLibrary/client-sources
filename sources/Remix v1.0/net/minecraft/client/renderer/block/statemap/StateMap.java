package net.minecraft.client.renderer.block.statemap;

import net.minecraft.block.properties.*;
import net.minecraft.block.state.*;
import net.minecraft.client.resources.model.*;
import net.minecraft.util.*;
import net.minecraft.block.*;
import com.google.common.collect.*;
import java.util.*;

public class StateMap extends StateMapperBase
{
    private final IProperty field_178142_a;
    private final String field_178141_c;
    private final List field_178140_d;
    
    private StateMap(final IProperty p_i46210_1_, final String p_i46210_2_, final List p_i46210_3_) {
        this.field_178142_a = p_i46210_1_;
        this.field_178141_c = p_i46210_2_;
        this.field_178140_d = p_i46210_3_;
    }
    
    StateMap(final IProperty p_i46211_1_, final String p_i46211_2_, final List p_i46211_3_, final Object p_i46211_4_) {
        this(p_i46211_1_, p_i46211_2_, p_i46211_3_);
    }
    
    @Override
    protected ModelResourceLocation func_178132_a(final IBlockState p_178132_1_) {
        final LinkedHashMap var2 = Maps.newLinkedHashMap((Map)p_178132_1_.getProperties());
        String var3;
        if (this.field_178142_a == null) {
            var3 = ((ResourceLocation)Block.blockRegistry.getNameForObject(p_178132_1_.getBlock())).toString();
        }
        else {
            var3 = this.field_178142_a.getName((Comparable)var2.remove(this.field_178142_a));
        }
        if (this.field_178141_c != null) {
            var3 += this.field_178141_c;
        }
        for (final IProperty var5 : this.field_178140_d) {
            var2.remove(var5);
        }
        return new ModelResourceLocation(var3, this.func_178131_a(var2));
    }
    
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
}
