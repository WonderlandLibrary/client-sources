// 
// Decompiled by Procyon v0.5.36
// 

package me.zero.alpine.listener;

import java.util.function.Predicate;

public class MethodRefListener<T> extends Listener<T>
{
    private Class<T> target;
    
    @SafeVarargs
    public MethodRefListener(final Class<T> target, final EventHook<T> hook, final Predicate<T>... filters) {
        super(hook, filters);
        this.target = target;
    }
    
    @SafeVarargs
    public MethodRefListener(final Class<T> target, final EventHook<T> hook, final int priority, final Predicate<T>... filters) {
        super(hook, priority, filters);
        this.target = target;
    }
    
    @Override
    public Class<T> getTarget() {
        return this.target;
    }
}
