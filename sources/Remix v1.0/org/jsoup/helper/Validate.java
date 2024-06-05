package org.jsoup.helper;

public final class Validate
{
    private Validate() {
    }
    
    public static void notNull(final Object obj) {
        if (obj == null) {
            throw new IllegalArgumentException("Object must not be null");
        }
    }
    
    public static void notNull(final Object obj, final String msg) {
        if (obj == null) {
            throw new IllegalArgumentException(msg);
        }
    }
    
    public static void isTrue(final boolean val) {
        if (!val) {
            throw new IllegalArgumentException("Must be true");
        }
    }
    
    public static void isTrue(final boolean val, final String msg) {
        if (!val) {
            throw new IllegalArgumentException(msg);
        }
    }
    
    public static void isFalse(final boolean val) {
        if (val) {
            throw new IllegalArgumentException("Must be false");
        }
    }
    
    public static void isFalse(final boolean val, final String msg) {
        if (val) {
            throw new IllegalArgumentException(msg);
        }
    }
    
    public static void noNullElements(final Object[] objects) {
        noNullElements(objects, "Array must not contain any null objects");
    }
    
    public static void noNullElements(final Object[] objects, final String msg) {
        for (final Object obj : objects) {
            if (obj == null) {
                throw new IllegalArgumentException(msg);
            }
        }
    }
    
    public static void notEmpty(final String string) {
        if (string == null || string.length() == 0) {
            throw new IllegalArgumentException("String must not be empty");
        }
    }
    
    public static void notEmpty(final String string, final String msg) {
        if (string == null || string.length() == 0) {
            throw new IllegalArgumentException(msg);
        }
    }
    
    public static void fail(final String msg) {
        throw new IllegalArgumentException(msg);
    }
}
