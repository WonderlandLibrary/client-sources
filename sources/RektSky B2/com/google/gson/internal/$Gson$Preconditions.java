package com.google.gson.internal;

public final class $Gson$Preconditions
{
    private $Gson$Preconditions() {
        throw new UnsupportedOperationException();
    }
    
    public static <T> T checkNotNull(final T obj) {
        if (obj == null) {
            throw new NullPointerException();
        }
        return obj;
    }
    
    public static void checkArgument(final boolean condition) {
        if (!condition) {
            throw new IllegalArgumentException();
        }
    }
}
