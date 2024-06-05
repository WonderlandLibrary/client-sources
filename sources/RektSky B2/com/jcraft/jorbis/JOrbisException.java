package com.jcraft.jorbis;

public class JOrbisException extends Exception
{
    private static final long serialVersionUID = 1L;
    
    public JOrbisException() {
    }
    
    public JOrbisException(final String s) {
        super("JOrbis: " + s);
    }
}
