package net.minecraft.entity.passive;

import net.minecraft.util.*;
import java.util.*;

static class PriceInfo extends Tuple
{
    public PriceInfo(final int p_i45810_1_, final int p_i45810_2_) {
        super(p_i45810_1_, p_i45810_2_);
    }
    
    public int func_179412_a(final Random p_179412_1_) {
        return (int)(((int)this.getFirst() >= (int)this.getSecond()) ? this.getFirst() : ((int)this.getFirst() + p_179412_1_.nextInt((int)this.getSecond() - (int)this.getFirst() + 1)));
    }
}
