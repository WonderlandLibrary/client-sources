package net.minecraft.nbt;

import com.google.common.collect.*;
import java.util.*;

static class Compound extends Any
{
    protected java.util.List field_150491_b;
    
    public Compound(final String p_i45137_1_) {
        this.field_150491_b = Lists.newArrayList();
        this.field_150490_a = p_i45137_1_;
    }
    
    @Override
    public NBTBase func_150489_a() {
        final NBTTagCompound var1 = new NBTTagCompound();
        for (final Any var3 : this.field_150491_b) {
            var1.setTag(var3.field_150490_a, var3.func_150489_a());
        }
        return var1;
    }
}
