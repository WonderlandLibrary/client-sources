package net.minecraft.client.resources.model;

import com.google.common.collect.*;
import java.util.*;

public static class Builder
{
    private List field_177678_a;
    
    public Builder() {
        this.field_177678_a = Lists.newArrayList();
    }
    
    public Builder add(final IBakedModel p_177677_1_, final int p_177677_2_) {
        this.field_177678_a.add(new MyWeighedRandomItem(p_177677_1_, p_177677_2_));
        return this;
    }
    
    public WeightedBakedModel build() {
        Collections.sort((List<Comparable>)this.field_177678_a);
        return new WeightedBakedModel(this.field_177678_a);
    }
    
    public IBakedModel first() {
        return this.field_177678_a.get(0).model;
    }
}
