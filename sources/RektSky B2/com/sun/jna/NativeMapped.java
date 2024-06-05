package com.sun.jna;

public interface NativeMapped
{
    Object fromNative(final Object p0, final FromNativeContext p1);
    
    Object toNative();
    
    Class<?> nativeType();
}
