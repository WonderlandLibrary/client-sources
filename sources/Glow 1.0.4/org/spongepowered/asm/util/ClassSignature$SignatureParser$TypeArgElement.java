package org.spongepowered.asm.util;

import org.spongepowered.asm.lib.signature.*;

class TypeArgElement extends TokenElement
{
    private final TokenElement type;
    private final char wildcard;
    final SignatureParser this$1;
    
    TypeArgElement(final SignatureParser this$1, final TokenElement type, final char wildcard) {
        this.this$1 = this$1.super();
        this.type = type;
        this.wildcard = wildcard;
    }
    
    @Override
    public SignatureVisitor visitArrayType() {
        this.type.setArray();
        return this;
    }
    
    @Override
    public void visitBaseType(final char c) {
        this.token = this.type.addTypeArgument(c).asToken();
    }
    
    @Override
    public void visitTypeVariable(final String s) {
        this.token = this.type.addTypeArgument(this.this$1.this$0.getType(s)).setWildcard(this.wildcard).asToken();
    }
    
    @Override
    public void visitClassType(final String s) {
        this.token = this.type.addTypeArgument(s).setWildcard(this.wildcard).asToken();
    }
    
    @Override
    public void visitTypeArgument() {
        this.token.addTypeArgument('*');
    }
    
    @Override
    public SignatureVisitor visitTypeArgument(final char c) {
        return this.this$1.new TypeArgElement(this, c);
    }
    
    @Override
    public void visitEnd() {
    }
}
