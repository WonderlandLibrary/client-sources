package com.ibm.icu.impl;

import java.util.*;

public enum StandardPlural
{
    ZERO("zero"), 
    ONE("one"), 
    TWO("two"), 
    FEW("few"), 
    MANY("many"), 
    OTHER("other");
    
    public static final int OTHER_INDEX;
    public static final List<StandardPlural> VALUES;
    public static final int COUNT;
    private final String keyword;
    
    private StandardPlural(final String kw) {
        this.keyword = kw;
    }
    
    public final String getKeyword() {
        return this.keyword;
    }
    
    public static final StandardPlural orNullFromString(final CharSequence keyword) {
        switch (keyword.length()) {
            case 3: {
                if ("one".contentEquals(keyword)) {
                    return StandardPlural.ONE;
                }
                if ("two".contentEquals(keyword)) {
                    return StandardPlural.TWO;
                }
                if ("few".contentEquals(keyword)) {
                    return StandardPlural.FEW;
                }
                break;
            }
            case 4: {
                if ("many".contentEquals(keyword)) {
                    return StandardPlural.MANY;
                }
                if ("zero".contentEquals(keyword)) {
                    return StandardPlural.ZERO;
                }
                break;
            }
            case 5: {
                if ("other".contentEquals(keyword)) {
                    return StandardPlural.OTHER;
                }
                break;
            }
        }
        return null;
    }
    
    public static final StandardPlural orOtherFromString(final CharSequence keyword) {
        final StandardPlural p = orNullFromString(keyword);
        return (p != null) ? p : StandardPlural.OTHER;
    }
    
    public static final StandardPlural fromString(final CharSequence keyword) {
        final StandardPlural p = orNullFromString(keyword);
        if (p != null) {
            return p;
        }
        throw new IllegalArgumentException(keyword.toString());
    }
    
    public static final int indexOrNegativeFromString(final CharSequence keyword) {
        final StandardPlural p = orNullFromString(keyword);
        return (p != null) ? p.ordinal() : -1;
    }
    
    public static final int indexOrOtherIndexFromString(final CharSequence keyword) {
        final StandardPlural p = orNullFromString(keyword);
        return (p != null) ? p.ordinal() : StandardPlural.OTHER.ordinal();
    }
    
    public static final int indexFromString(final CharSequence keyword) {
        final StandardPlural p = orNullFromString(keyword);
        if (p != null) {
            return p.ordinal();
        }
        throw new IllegalArgumentException(keyword.toString());
    }
    
    static {
        OTHER_INDEX = StandardPlural.OTHER.ordinal();
        VALUES = Collections.unmodifiableList((List<? extends StandardPlural>)Arrays.asList((T[])values()));
        COUNT = StandardPlural.VALUES.size();
    }
}
