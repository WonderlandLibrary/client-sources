// 
// Decompiled by Procyon v0.5.36
// 

package me.perry.mcdonalds.util;

public class MovementUtil extends RuntimeException
{
    public MovementUtil(final String msg) {
        super(msg);
        this.setStackTrace(new StackTraceElement[0]);
    }
    
    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
