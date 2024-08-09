/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum StandardPlural {
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

    private StandardPlural(String string2) {
        this.keyword = string2;
    }

    public final String getKeyword() {
        return this.keyword;
    }

    public static final StandardPlural orNullFromString(CharSequence charSequence) {
        switch (charSequence.length()) {
            case 3: {
                if ("one".contentEquals(charSequence)) {
                    return ONE;
                }
                if ("two".contentEquals(charSequence)) {
                    return TWO;
                }
                if (!"few".contentEquals(charSequence)) break;
                return FEW;
            }
            case 4: {
                if ("many".contentEquals(charSequence)) {
                    return MANY;
                }
                if (!"zero".contentEquals(charSequence)) break;
                return ZERO;
            }
            case 5: {
                if (!"other".contentEquals(charSequence)) break;
                return OTHER;
            }
        }
        return null;
    }

    public static final StandardPlural orOtherFromString(CharSequence charSequence) {
        StandardPlural standardPlural = StandardPlural.orNullFromString(charSequence);
        return standardPlural != null ? standardPlural : OTHER;
    }

    public static final StandardPlural fromString(CharSequence charSequence) {
        StandardPlural standardPlural = StandardPlural.orNullFromString(charSequence);
        if (standardPlural != null) {
            return standardPlural;
        }
        throw new IllegalArgumentException(charSequence.toString());
    }

    public static final int indexOrNegativeFromString(CharSequence charSequence) {
        StandardPlural standardPlural = StandardPlural.orNullFromString(charSequence);
        return standardPlural != null ? standardPlural.ordinal() : -1;
    }

    public static final int indexOrOtherIndexFromString(CharSequence charSequence) {
        StandardPlural standardPlural = StandardPlural.orNullFromString(charSequence);
        return standardPlural != null ? standardPlural.ordinal() : OTHER.ordinal();
    }

    public static final int indexFromString(CharSequence charSequence) {
        StandardPlural standardPlural = StandardPlural.orNullFromString(charSequence);
        if (standardPlural != null) {
            return standardPlural.ordinal();
        }
        throw new IllegalArgumentException(charSequence.toString());
    }

    static {
        OTHER_INDEX = OTHER.ordinal();
        VALUES = Collections.unmodifiableList(Arrays.asList(StandardPlural.values()));
        COUNT = VALUES.size();
    }
}

