// 
// Decompiled by Procyon v0.5.36
// 

package me.zero.alpine.event.type;

public interface ICancellable
{
    void cancel();
    
    boolean isCancelled();
}
