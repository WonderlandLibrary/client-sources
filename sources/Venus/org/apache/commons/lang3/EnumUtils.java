/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.lang3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.Validate;

public class EnumUtils {
    private static final String NULL_ELEMENTS_NOT_PERMITTED = "null elements not permitted";
    private static final String CANNOT_STORE_S_S_VALUES_IN_S_BITS = "Cannot store %s %s values in %s bits";
    private static final String S_DOES_NOT_SEEM_TO_BE_AN_ENUM_TYPE = "%s does not seem to be an Enum type";
    private static final String ENUM_CLASS_MUST_BE_DEFINED = "EnumClass must be defined.";

    public static <E extends Enum<E>> Map<String, E> getEnumMap(Class<E> clazz) {
        LinkedHashMap<String, Enum> linkedHashMap = new LinkedHashMap<String, Enum>();
        for (Enum enum_ : (Enum[])clazz.getEnumConstants()) {
            linkedHashMap.put(enum_.name(), enum_);
        }
        return linkedHashMap;
    }

    public static <E extends Enum<E>> List<E> getEnumList(Class<E> clazz) {
        return new ArrayList<E>(Arrays.asList(clazz.getEnumConstants()));
    }

    public static <E extends Enum<E>> boolean isValidEnum(Class<E> clazz, String string) {
        if (string == null) {
            return true;
        }
        try {
            Enum.valueOf(clazz, string);
            return true;
        } catch (IllegalArgumentException illegalArgumentException) {
            return true;
        }
    }

    public static <E extends Enum<E>> E getEnum(Class<E> clazz, String string) {
        if (string == null) {
            return null;
        }
        try {
            return Enum.valueOf(clazz, string);
        } catch (IllegalArgumentException illegalArgumentException) {
            return null;
        }
    }

    public static <E extends Enum<E>> long generateBitVector(Class<E> clazz, Iterable<? extends E> iterable) {
        EnumUtils.checkBitVectorable(clazz);
        Validate.notNull(iterable);
        long l = 0L;
        for (Enum enum_ : iterable) {
            Validate.isTrue(enum_ != null, NULL_ELEMENTS_NOT_PERMITTED, new Object[0]);
            l |= 1L << enum_.ordinal();
        }
        return l;
    }

    public static <E extends Enum<E>> long[] generateBitVectors(Class<E> clazz, Iterable<? extends E> iterable) {
        EnumUtils.asEnum(clazz);
        Validate.notNull(iterable);
        EnumSet<Enum> enumSet = EnumSet.noneOf(clazz);
        for (Object object : iterable) {
            Validate.isTrue(object != null, NULL_ELEMENTS_NOT_PERMITTED, new Object[0]);
            enumSet.add((Enum)object);
        }
        Object object = new long[(((Enum[])clazz.getEnumConstants()).length - 1) / 64 + 1];
        for (Enum enum_ : enumSet) {
            Object object2 = object;
            int n = enum_.ordinal() / 64;
            object2[n] = object2[n] | 1L << enum_.ordinal() % 64;
        }
        ArrayUtils.reverse((long[])object);
        return object;
    }

    public static <E extends Enum<E>> long generateBitVector(Class<E> clazz, E ... EArray) {
        Validate.noNullElements(EArray);
        return EnumUtils.generateBitVector(clazz, Arrays.asList(EArray));
    }

    public static <E extends Enum<E>> long[] generateBitVectors(Class<E> clazz, E ... EArray) {
        EnumUtils.asEnum(clazz);
        Validate.noNullElements(EArray);
        EnumSet<Enum> enumSet = EnumSet.noneOf(clazz);
        Collections.addAll(enumSet, EArray);
        long[] lArray = new long[(((Enum[])clazz.getEnumConstants()).length - 1) / 64 + 1];
        for (Enum enum_ : enumSet) {
            int n = enum_.ordinal() / 64;
            lArray[n] = lArray[n] | 1L << enum_.ordinal() % 64;
        }
        ArrayUtils.reverse(lArray);
        return lArray;
    }

    public static <E extends Enum<E>> EnumSet<E> processBitVector(Class<E> clazz, long l) {
        EnumUtils.checkBitVectorable(clazz).getEnumConstants();
        return EnumUtils.processBitVectors(clazz, l);
    }

    public static <E extends Enum<E>> EnumSet<E> processBitVectors(Class<E> clazz, long ... lArray) {
        EnumSet<Enum> enumSet = EnumSet.noneOf(EnumUtils.asEnum(clazz));
        long[] lArray2 = ArrayUtils.clone(Validate.notNull(lArray));
        ArrayUtils.reverse(lArray2);
        for (Enum enum_ : (Enum[])clazz.getEnumConstants()) {
            int n = enum_.ordinal() / 64;
            if (n >= lArray2.length || (lArray2[n] & 1L << enum_.ordinal() % 64) == 0L) continue;
            enumSet.add(enum_);
        }
        return enumSet;
    }

    private static <E extends Enum<E>> Class<E> checkBitVectorable(Class<E> clazz) {
        Enum[] enumArray = (Enum[])EnumUtils.asEnum(clazz).getEnumConstants();
        Validate.isTrue(enumArray.length <= 64, CANNOT_STORE_S_S_VALUES_IN_S_BITS, enumArray.length, clazz.getSimpleName(), 64);
        return clazz;
    }

    private static <E extends Enum<E>> Class<E> asEnum(Class<E> clazz) {
        Validate.notNull(clazz, ENUM_CLASS_MUST_BE_DEFINED, new Object[0]);
        Validate.isTrue(clazz.isEnum(), S_DOES_NOT_SEEM_TO_BE_AN_ENUM_TYPE, clazz);
        return clazz;
    }
}

