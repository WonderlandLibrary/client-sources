package com.sun.jna;

import java.lang.ref.*;
import java.util.*;

public class NativeMappedConverter implements TypeConverter
{
    private static final Map<Class<?>, Reference<NativeMappedConverter>> converters;
    private final Class<?> type;
    private final Class<?> nativeType;
    private final NativeMapped instance;
    
    public static NativeMappedConverter getInstance(final Class<?> cls) {
        synchronized (NativeMappedConverter.converters) {
            final Reference<NativeMappedConverter> r = NativeMappedConverter.converters.get(cls);
            NativeMappedConverter nmc = (r != null) ? r.get() : null;
            if (nmc == null) {
                nmc = new NativeMappedConverter(cls);
                NativeMappedConverter.converters.put(cls, new SoftReference<NativeMappedConverter>(nmc));
            }
            return nmc;
        }
    }
    
    public NativeMappedConverter(final Class<?> type) {
        if (!NativeMapped.class.isAssignableFrom(type)) {
            throw new IllegalArgumentException("Type must derive from " + NativeMapped.class);
        }
        this.type = type;
        this.instance = this.defaultValue();
        this.nativeType = this.instance.nativeType();
    }
    
    public NativeMapped defaultValue() {
        try {
            return (NativeMapped)this.type.newInstance();
        }
        catch (InstantiationException e) {
            final String msg = "Can't create an instance of " + this.type + ", requires a no-arg constructor: " + e;
            throw new IllegalArgumentException(msg);
        }
        catch (IllegalAccessException e2) {
            final String msg = "Not allowed to create an instance of " + this.type + ", requires a public, no-arg constructor: " + e2;
            throw new IllegalArgumentException(msg);
        }
    }
    
    @Override
    public Object fromNative(final Object nativeValue, final FromNativeContext context) {
        return this.instance.fromNative(nativeValue, context);
    }
    
    @Override
    public Class<?> nativeType() {
        return this.nativeType;
    }
    
    @Override
    public Object toNative(Object value, final ToNativeContext context) {
        if (value == null) {
            if (Pointer.class.isAssignableFrom(this.nativeType)) {
                return null;
            }
            value = this.defaultValue();
        }
        return ((NativeMapped)value).toNative();
    }
    
    static {
        converters = new WeakHashMap<Class<?>, Reference<NativeMappedConverter>>();
    }
}
