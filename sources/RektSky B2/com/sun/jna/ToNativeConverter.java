package com.sun.jna;

public interface ToNativeConverter
{
    Object toNative(final Object p0, final ToNativeContext p1);
    
    Class<?> nativeType();
}
