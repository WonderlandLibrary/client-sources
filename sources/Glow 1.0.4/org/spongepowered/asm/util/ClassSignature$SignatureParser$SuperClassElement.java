package org.spongepowered.asm.util;

class SuperClassElement extends TokenElement
{
    final SignatureParser this$1;
    
    SuperClassElement(final SignatureParser this$1) {
        this.this$1 = this$1.super();
    }
    
    @Override
    public void visitEnd() {
        this.this$1.this$0.setSuperClass(this.token);
    }
}
