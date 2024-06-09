package org.spongepowered.asm.util;

class FormalParamElement extends TokenElement
{
    private final TokenHandle handle;
    final SignatureParser this$1;
    
    FormalParamElement(final SignatureParser this$1, final String s) {
        this.this$1 = this$1.super();
        this.handle = this$1.this$0.getType(s);
        this.token = this.handle.asToken();
    }
}
