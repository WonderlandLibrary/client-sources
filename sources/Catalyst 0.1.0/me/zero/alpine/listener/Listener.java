// 
// Decompiled by Procyon v0.5.36
// 

package me.zero.alpine.listener;

import net.jodah.typetools.TypeResolver;
import java.util.function.Predicate;

public class Listener<T> implements EventHook<T>
{
    private final Class<T> target;
    private final EventHook<T> hook;
    private final Predicate<T>[] filters;
    private final int priority;
    
    @SafeVarargs
    public Listener(final EventHook<T> hook, final Predicate<T>... filters) {
        this(hook, 0, (Predicate[])filters);
    }
    
    @SafeVarargs
    public Listener(final EventHook<T> hook, final int priority, final Predicate<T>... filters) {
        this.hook = hook;
        this.priority = priority;
        this.target = (Class<T>)TypeResolver.resolveRawArgument(EventHook.class, hook.getClass());
        this.filters = filters;
    }
    
    public Class<T> getTarget() {
        return this.target;
    }
    
    public int getPriority() {
        return this.priority;
    }
    
    @Override
    public void invoke(final T event) {
        if (this.filters.length > 0) {
            for (final Predicate<T> filter : this.filters) {
                if (!filter.test(event)) {
                    return;
                }
            }
        }
        this.hook.invoke(event);
    }
}
