package org.spongepowered.asm.util;

import com.google.common.base.*;

class HorizontalRule implements ISpecialEntry
{
    private final char[] hrChars;
    final PrettyPrinter this$0;
    
    public HorizontalRule(final PrettyPrinter this$0, final char... hrChars) {
        this.this$0 = this$0;
        super();
        this.hrChars = hrChars;
    }
    
    @Override
    public String toString() {
        return Strings.repeat(new String(this.hrChars), this.this$0.width + 2);
    }
}
