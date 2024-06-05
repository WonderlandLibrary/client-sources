package com.sun.jna;

public interface TypeMapper
{
    FromNativeConverter getFromNativeConverter(final Class<?> p0);
    
    ToNativeConverter getToNativeConverter(final Class<?> p0);
}
