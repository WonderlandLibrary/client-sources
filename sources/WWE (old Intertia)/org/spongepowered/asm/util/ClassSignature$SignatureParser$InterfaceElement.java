package org.spongepowered.asm.util;

class InterfaceElement extends TokenElement
{
    final SignatureParser this$1;
    
    InterfaceElement(final SignatureParser this$1) {
        this.this$1 = this$1.super();
    }
    
    @Override
    public void visitEnd() {
        this.this$1.this$0.addInterface(this.token);
    }
}
