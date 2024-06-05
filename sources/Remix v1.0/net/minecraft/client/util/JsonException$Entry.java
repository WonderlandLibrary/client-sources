package net.minecraft.client.util;

import java.util.*;
import com.google.common.collect.*;
import org.apache.commons.lang3.*;

public static class Entry
{
    private final List field_151375_b;
    private String field_151376_a;
    
    private Entry() {
        this.field_151376_a = null;
        this.field_151375_b = Lists.newArrayList();
    }
    
    Entry(final Object p_i45278_1_) {
        this();
    }
    
    private void func_151373_a(final String p_151373_1_) {
        this.field_151375_b.add(0, p_151373_1_);
    }
    
    public String func_151372_b() {
        return StringUtils.join((Iterable)this.field_151375_b, "->");
    }
    
    @Override
    public String toString() {
        return (this.field_151376_a != null) ? (this.field_151375_b.isEmpty() ? this.field_151376_a : (this.field_151376_a + " " + this.func_151372_b())) : (this.field_151375_b.isEmpty() ? "(Unknown file)" : ("(Unknown file) " + this.func_151372_b()));
    }
}
