package net.minecraft.util;

import com.google.common.base.*;

static final class ChatComponentStyle$2 implements Function {
    public IChatComponent apply(final IChatComponent p_apply_1_) {
        final IChatComponent var2 = p_apply_1_.createCopy();
        var2.setChatStyle(var2.getChatStyle().createDeepCopy());
        return var2;
    }
    
    public Object apply(final Object p_apply_1_) {
        return this.apply((IChatComponent)p_apply_1_);
    }
}