package com.sun.jna;

public interface FromNativeConverter
{
    Object fromNative(final Object p0, final FromNativeContext p1);
    
    Class<?> nativeType();
}
