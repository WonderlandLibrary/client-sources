package net.minecraft.util;

import com.google.common.base.*;
import java.util.*;

static class GetList implements Function
{
    private GetList() {
    }
    
    GetList(final Object p_i46022_1_) {
        this();
    }
    
    public List apply(final Object[] array) {
        return Arrays.asList((Object[])array);
    }
    
    public Object apply(final Object p_apply_1_) {
        return this.apply((Object[])p_apply_1_);
    }
}
