package com.ibm.icu.impl;

import com.ibm.icu.util.*;
import java.lang.ref.*;

public abstract class CacheValue<V>
{
    private static volatile Strength strength;
    private static final CacheValue NULL_VALUE;
    
    public static void setStrength(final Strength strength) {
        CacheValue.strength = strength;
    }
    
    public static boolean futureInstancesWillBeStrong() {
        return CacheValue.strength == Strength.STRONG;
    }
    
    public static <V> CacheValue<V> getInstance(final V value) {
        if (value == null) {
            return (CacheValue<V>)CacheValue.NULL_VALUE;
        }
        return (CacheValue<V>)((CacheValue.strength == Strength.STRONG) ? new StrongValue<V>(value) : new SoftValue<V>(value));
    }
    
    public boolean isNull() {
        return false;
    }
    
    public abstract V get();
    
    public abstract V resetIfCleared(final V p0);
    
    static {
        CacheValue.strength = Strength.SOFT;
        NULL_VALUE = new NullValue();
    }
    
    public enum Strength
    {
        STRONG, 
        SOFT;
    }
    
    private static final class NullValue<V> extends CacheValue<V>
    {
        @Override
        public boolean isNull() {
            return true;
        }
        
        @Override
        public V get() {
            return null;
        }
        
        @Override
        public V resetIfCleared(final V value) {
            if (value != null) {
                throw new ICUException("resetting a null value to a non-null value");
            }
            return null;
        }
    }
    
    private static final class StrongValue<V> extends CacheValue<V>
    {
        private V value;
        
        StrongValue(final V value) {
            this.value = value;
        }
        
        @Override
        public V get() {
            return this.value;
        }
        
        @Override
        public V resetIfCleared(final V value) {
            return this.value;
        }
    }
    
    private static final class SoftValue<V> extends CacheValue<V>
    {
        private volatile Reference<V> ref;
        
        SoftValue(final V value) {
            this.ref = new SoftReference<V>(value);
        }
        
        @Override
        public V get() {
            return this.ref.get();
        }
        
        @Override
        public synchronized V resetIfCleared(final V value) {
            final V oldValue = this.ref.get();
            if (oldValue == null) {
                this.ref = new SoftReference<V>(value);
                return value;
            }
            return oldValue;
        }
    }
}
