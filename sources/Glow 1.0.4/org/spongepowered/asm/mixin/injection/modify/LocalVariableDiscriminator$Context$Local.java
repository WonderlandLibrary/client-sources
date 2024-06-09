package org.spongepowered.asm.mixin.injection.modify;

import org.spongepowered.asm.lib.*;

public class Local
{
    int ord;
    String name;
    Type type;
    final Context this$0;
    
    public Local(final Context this$0, final String name, final Type type) {
        this.this$0 = this$0;
        super();
        this.ord = 0;
        this.name = name;
        this.type = type;
    }
    
    @Override
    public String toString() {
        return String.format("Local[ordinal=%d, name=%s, type=%s]", this.ord, this.name, this.type);
    }
}
