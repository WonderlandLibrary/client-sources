package org.spongepowered.asm.util;

class TokenHandle implements IToken
{
    final Token token;
    boolean array;
    char wildcard;
    final ClassSignature this$0;
    
    TokenHandle(final ClassSignature classSignature) {
        this(classSignature, new Token());
    }
    
    TokenHandle(final ClassSignature this$0, final Token token) {
        this.this$0 = this$0;
        super();
        this.token = token;
    }
    
    @Override
    public IToken setArray(final boolean b) {
        this.array |= b;
        return this;
    }
    
    @Override
    public IToken setWildcard(final char wildcard) {
        if ("+-".indexOf(wildcard) > -1) {
            this.wildcard = wildcard;
        }
        return this;
    }
    
    @Override
    public String asBound() {
        return this.token.asBound();
    }
    
    @Override
    public String asType() {
        final StringBuilder sb = new StringBuilder();
        if (this.wildcard > '\0') {
            sb.append(this.wildcard);
        }
        if (this.array) {
            sb.append('[');
        }
        return sb.append(this.this$0.getTypeVar(this)).toString();
    }
    
    @Override
    public Token asToken() {
        return this.token;
    }
    
    @Override
    public String toString() {
        return this.token.toString();
    }
    
    public TokenHandle clone() {
        return this.this$0.new TokenHandle(this.token);
    }
    
    public Object clone() throws CloneNotSupportedException {
        return this.clone();
    }
}
