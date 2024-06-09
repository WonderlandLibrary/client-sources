package org.spongepowered.asm.util;

import org.spongepowered.asm.lib.signature.*;

class BoundElement extends TokenElement
{
    private final TokenElement type;
    private final boolean classBound;
    final SignatureParser this$1;
    
    BoundElement(final SignatureParser this$1, final TokenElement type, final boolean classBound) {
        this.this$1 = this$1.super();
        this.type = type;
        this.classBound = classBound;
    }
    
    @Override
    public void visitClassType(final String s) {
        this.token = this.type.token.addBound(s, this.classBound);
    }
    
    @Override
    public void visitTypeArgument() {
        this.token.addTypeArgument('*');
    }
    
    @Override
    public SignatureVisitor visitTypeArgument(final char c) {
        return this.this$1.new TypeArgElement(this, c);
    }
}
