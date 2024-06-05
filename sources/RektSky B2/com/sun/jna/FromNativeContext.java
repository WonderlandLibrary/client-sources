package com.sun.jna;

public class FromNativeContext
{
    private Class<?> type;
    
    FromNativeContext(final Class<?> javaType) {
        this.type = javaType;
    }
    
    public Class<?> getTargetType() {
        return this.type;
    }
}
