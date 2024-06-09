package org.spongepowered.asm.util;

import java.util.*;

static class Token implements IToken
{
    static final String SYMBOLS = "+-*";
    private final boolean inner;
    private boolean array;
    private char symbol;
    private String type;
    private List<Token> classBound;
    private List<Token> ifaceBound;
    private List<IToken> signature;
    private List<IToken> suffix;
    private Token tail;
    
    Token() {
        this(false);
    }
    
    Token(final String s) {
        this(s, false);
    }
    
    Token(final char symbol) {
        this();
        this.symbol = symbol;
    }
    
    Token(final boolean b) {
        this(null, b);
    }
    
    Token(final String type, final boolean inner) {
        super();
        this.symbol = '\0';
        this.inner = inner;
        this.type = type;
    }
    
    Token setSymbol(final char symbol) {
        if (this.symbol == '\0' && "+-*".indexOf(symbol) > -1) {
            this.symbol = symbol;
        }
        return this;
    }
    
    Token setType(final String type) {
        if (this.type == null) {
            this.type = type;
        }
        return this;
    }
    
    boolean hasClassBound() {
        return this.classBound != null;
    }
    
    boolean hasInterfaceBound() {
        return this.ifaceBound != null;
    }
    
    @Override
    public IToken setArray(final boolean b) {
        this.array |= b;
        return this;
    }
    
    @Override
    public IToken setWildcard(final char symbol) {
        if ("+-".indexOf(symbol) == -1) {
            return this;
        }
        return this.setSymbol(symbol);
    }
    
    private List<Token> getClassBound() {
        if (this.classBound == null) {
            this.classBound = new ArrayList<Token>();
        }
        return this.classBound;
    }
    
    private List<Token> getIfaceBound() {
        if (this.ifaceBound == null) {
            this.ifaceBound = new ArrayList<Token>();
        }
        return this.ifaceBound;
    }
    
    private List<IToken> getSignature() {
        if (this.signature == null) {
            this.signature = new ArrayList<IToken>();
        }
        return this.signature;
    }
    
    private List<IToken> getSuffix() {
        if (this.suffix == null) {
            this.suffix = new ArrayList<IToken>();
        }
        return this.suffix;
    }
    
    IToken addTypeArgument(final char c) {
        if (this.tail != null) {
            return this.tail.addTypeArgument(c);
        }
        final Token token = new Token(c);
        this.getSignature().add(token);
        return token;
    }
    
    IToken addTypeArgument(final String s) {
        if (this.tail != null) {
            return this.tail.addTypeArgument(s);
        }
        final Token token = new Token(s);
        this.getSignature().add(token);
        return token;
    }
    
    IToken addTypeArgument(final Token token) {
        if (this.tail != null) {
            return this.tail.addTypeArgument(token);
        }
        this.getSignature().add(token);
        return token;
    }
    
    IToken addTypeArgument(final TokenHandle tokenHandle) {
        if (this.tail != null) {
            return this.tail.addTypeArgument(tokenHandle);
        }
        final TokenHandle clone = tokenHandle.clone();
        this.getSignature().add(clone);
        return clone;
    }
    
    Token addBound(final String s, final boolean b) {
        if (b) {
            return this.addClassBound(s);
        }
        return this.addInterfaceBound(s);
    }
    
    Token addClassBound(final String s) {
        final Token token = new Token(s);
        this.getClassBound().add(token);
        return token;
    }
    
    Token addInterfaceBound(final String s) {
        final Token token = new Token(s);
        this.getIfaceBound().add(token);
        return token;
    }
    
    Token addInnerClass(final String s) {
        this.tail = new Token(s, true);
        this.getSuffix().add(this.tail);
        return this.tail;
    }
    
    @Override
    public String toString() {
        return this.asType();
    }
    
    @Override
    public String asBound() {
        final StringBuilder sb = new StringBuilder();
        if (this.type != null) {
            sb.append(this.type);
        }
        if (this.classBound != null) {
            final Iterator<Token> iterator = this.classBound.iterator();
            while (iterator.hasNext()) {
                sb.append(iterator.next().asType());
            }
        }
        if (this.ifaceBound != null) {
            final Iterator<Token> iterator2 = this.ifaceBound.iterator();
            while (iterator2.hasNext()) {
                sb.append(':').append(iterator2.next().asType());
            }
        }
        return sb.toString();
    }
    
    @Override
    public String asType() {
        return this.asType(false);
    }
    
    public String asType(final boolean b) {
        final StringBuilder sb = new StringBuilder();
        if (this.array) {
            sb.append('[');
        }
        if (this.symbol != '\0') {
            sb.append(this.symbol);
        }
        if (this.type == null) {
            return sb.toString();
        }
        if (!this.inner) {
            sb.append('L');
        }
        sb.append(this.type);
        if (!b) {
            if (this.signature != null) {
                sb.append('<');
                final Iterator<IToken> iterator = this.signature.iterator();
                while (iterator.hasNext()) {
                    sb.append(iterator.next().asType());
                }
                sb.append('>');
            }
            if (this.suffix != null) {
                final Iterator<IToken> iterator2 = this.suffix.iterator();
                while (iterator2.hasNext()) {
                    sb.append('.').append(iterator2.next().asType());
                }
            }
        }
        if (!this.inner) {
            sb.append(';');
        }
        return sb.toString();
    }
    
    boolean isRaw() {
        return this.signature == null;
    }
    
    String getClassType() {
        return (this.type != null) ? this.type : "java/lang/Object";
    }
    
    @Override
    public Token asToken() {
        return this;
    }
}
