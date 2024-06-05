package net.minecraft.nbt;

import com.google.common.collect.*;
import java.util.*;

static class List extends Any
{
    protected java.util.List field_150492_b;
    
    public List(final String p_i45138_1_) {
        this.field_150492_b = Lists.newArrayList();
        this.field_150490_a = p_i45138_1_;
    }
    
    @Override
    public NBTBase func_150489_a() {
        final NBTTagList var1 = new NBTTagList();
        for (final Any var3 : this.field_150492_b) {
            var1.appendTag(var3.func_150489_a());
        }
        return var1;
    }
}
