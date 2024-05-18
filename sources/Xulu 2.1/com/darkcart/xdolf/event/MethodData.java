package com.darkcart.xdolf.event;

import java.lang.reflect.Method;

public class MethodData
{
    public final Object source;
    public final Method target;
    public final byte priority;
    
    MethodData(final Object source, final Method target, final byte priority) {
        this.source = source;
        this.target = target;
        this.priority = priority;
    }
}
