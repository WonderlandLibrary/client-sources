package net.minecraft.client.resources.data;

class Registration
{
    final IMetadataSectionSerializer field_110502_a;
    final Class field_110500_b;
    
    private Registration(final IMetadataSectionSerializer p_i1305_2_, final Class p_i1305_3_) {
        this.field_110502_a = p_i1305_2_;
        this.field_110500_b = p_i1305_3_;
    }
    
    Registration(final IMetadataSerializer this$0, final IMetadataSectionSerializer p_i1306_2_, final Class p_i1306_3_, final Object p_i1306_4_) {
        this(this$0, p_i1306_2_, p_i1306_3_);
    }
}
