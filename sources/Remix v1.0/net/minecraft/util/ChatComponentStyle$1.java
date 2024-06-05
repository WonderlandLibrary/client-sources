package net.minecraft.util;

import com.google.common.base.*;
import java.util.*;

static final class ChatComponentStyle$1 implements Function {
    public Iterator apply(final IChatComponent p_apply_1_) {
        return p_apply_1_.iterator();
    }
    
    public Object apply(final Object p_apply_1_) {
        return this.apply((IChatComponent)p_apply_1_);
    }
}