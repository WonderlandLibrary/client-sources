package org.spongepowered.asm.util;

interface IToken
{
    public static final String WILDCARDS = "+-";
    
    String asType();
    
    String asBound();
    
    Token asToken();
    
    IToken setArray(final boolean p0);
    
    IToken setWildcard(final char p0);
}
