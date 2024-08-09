/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package kotlin.jvm.internal;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import kotlin.Function;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function10;
import kotlin.jvm.functions.Function11;
import kotlin.jvm.functions.Function12;
import kotlin.jvm.functions.Function13;
import kotlin.jvm.functions.Function14;
import kotlin.jvm.functions.Function15;
import kotlin.jvm.functions.Function16;
import kotlin.jvm.functions.Function17;
import kotlin.jvm.functions.Function18;
import kotlin.jvm.functions.Function19;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function20;
import kotlin.jvm.functions.Function21;
import kotlin.jvm.functions.Function22;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.functions.Function5;
import kotlin.jvm.functions.Function6;
import kotlin.jvm.functions.Function7;
import kotlin.jvm.functions.Function8;
import kotlin.jvm.functions.Function9;
import kotlin.jvm.internal.FunctionBase;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMappedMarker;
import kotlin.jvm.internal.markers.KMutableCollection;
import kotlin.jvm.internal.markers.KMutableIterable;
import kotlin.jvm.internal.markers.KMutableIterator;
import kotlin.jvm.internal.markers.KMutableList;
import kotlin.jvm.internal.markers.KMutableListIterator;
import kotlin.jvm.internal.markers.KMutableMap;
import kotlin.jvm.internal.markers.KMutableSet;

public class TypeIntrinsics {
    private static <T extends Throwable> T sanitizeStackTrace(T t) {
        return Intrinsics.sanitizeStackTrace(t, TypeIntrinsics.class.getName());
    }

    public static void throwCce(Object object, String string) {
        String string2 = object == null ? "null" : object.getClass().getName();
        TypeIntrinsics.throwCce(string2 + " cannot be cast to " + string);
    }

    public static void throwCce(String string) {
        throw TypeIntrinsics.throwCce(new ClassCastException(string));
    }

    public static ClassCastException throwCce(ClassCastException classCastException) {
        throw TypeIntrinsics.sanitizeStackTrace(classCastException);
    }

    public static boolean isMutableIterator(Object object) {
        return object instanceof Iterator && (!(object instanceof KMappedMarker) || object instanceof KMutableIterator);
    }

    public static Iterator asMutableIterator(Object object) {
        if (object instanceof KMappedMarker && !(object instanceof KMutableIterator)) {
            TypeIntrinsics.throwCce(object, "kotlin.collections.MutableIterator");
        }
        return TypeIntrinsics.castToIterator(object);
    }

    public static Iterator asMutableIterator(Object object, String string) {
        if (object instanceof KMappedMarker && !(object instanceof KMutableIterator)) {
            TypeIntrinsics.throwCce(string);
        }
        return TypeIntrinsics.castToIterator(object);
    }

    public static Iterator castToIterator(Object object) {
        try {
            return (Iterator)object;
        } catch (ClassCastException classCastException) {
            throw TypeIntrinsics.throwCce(classCastException);
        }
    }

    public static boolean isMutableListIterator(Object object) {
        return object instanceof ListIterator && (!(object instanceof KMappedMarker) || object instanceof KMutableListIterator);
    }

    public static ListIterator asMutableListIterator(Object object) {
        if (object instanceof KMappedMarker && !(object instanceof KMutableListIterator)) {
            TypeIntrinsics.throwCce(object, "kotlin.collections.MutableListIterator");
        }
        return TypeIntrinsics.castToListIterator(object);
    }

    public static ListIterator asMutableListIterator(Object object, String string) {
        if (object instanceof KMappedMarker && !(object instanceof KMutableListIterator)) {
            TypeIntrinsics.throwCce(string);
        }
        return TypeIntrinsics.castToListIterator(object);
    }

    public static ListIterator castToListIterator(Object object) {
        try {
            return (ListIterator)object;
        } catch (ClassCastException classCastException) {
            throw TypeIntrinsics.throwCce(classCastException);
        }
    }

    public static boolean isMutableIterable(Object object) {
        return object instanceof Iterable && (!(object instanceof KMappedMarker) || object instanceof KMutableIterable);
    }

    public static Iterable asMutableIterable(Object object) {
        if (object instanceof KMappedMarker && !(object instanceof KMutableIterable)) {
            TypeIntrinsics.throwCce(object, "kotlin.collections.MutableIterable");
        }
        return TypeIntrinsics.castToIterable(object);
    }

    public static Iterable asMutableIterable(Object object, String string) {
        if (object instanceof KMappedMarker && !(object instanceof KMutableIterable)) {
            TypeIntrinsics.throwCce(string);
        }
        return TypeIntrinsics.castToIterable(object);
    }

    public static Iterable castToIterable(Object object) {
        try {
            return (Iterable)object;
        } catch (ClassCastException classCastException) {
            throw TypeIntrinsics.throwCce(classCastException);
        }
    }

    public static boolean isMutableCollection(Object object) {
        return object instanceof Collection && (!(object instanceof KMappedMarker) || object instanceof KMutableCollection);
    }

    public static Collection asMutableCollection(Object object) {
        if (object instanceof KMappedMarker && !(object instanceof KMutableCollection)) {
            TypeIntrinsics.throwCce(object, "kotlin.collections.MutableCollection");
        }
        return TypeIntrinsics.castToCollection(object);
    }

    public static Collection asMutableCollection(Object object, String string) {
        if (object instanceof KMappedMarker && !(object instanceof KMutableCollection)) {
            TypeIntrinsics.throwCce(string);
        }
        return TypeIntrinsics.castToCollection(object);
    }

    public static Collection castToCollection(Object object) {
        try {
            return (Collection)object;
        } catch (ClassCastException classCastException) {
            throw TypeIntrinsics.throwCce(classCastException);
        }
    }

    public static boolean isMutableList(Object object) {
        return object instanceof List && (!(object instanceof KMappedMarker) || object instanceof KMutableList);
    }

    public static List asMutableList(Object object) {
        if (object instanceof KMappedMarker && !(object instanceof KMutableList)) {
            TypeIntrinsics.throwCce(object, "kotlin.collections.MutableList");
        }
        return TypeIntrinsics.castToList(object);
    }

    public static List asMutableList(Object object, String string) {
        if (object instanceof KMappedMarker && !(object instanceof KMutableList)) {
            TypeIntrinsics.throwCce(string);
        }
        return TypeIntrinsics.castToList(object);
    }

    public static List castToList(Object object) {
        try {
            return (List)object;
        } catch (ClassCastException classCastException) {
            throw TypeIntrinsics.throwCce(classCastException);
        }
    }

    public static boolean isMutableSet(Object object) {
        return object instanceof Set && (!(object instanceof KMappedMarker) || object instanceof KMutableSet);
    }

    public static Set asMutableSet(Object object) {
        if (object instanceof KMappedMarker && !(object instanceof KMutableSet)) {
            TypeIntrinsics.throwCce(object, "kotlin.collections.MutableSet");
        }
        return TypeIntrinsics.castToSet(object);
    }

    public static Set asMutableSet(Object object, String string) {
        if (object instanceof KMappedMarker && !(object instanceof KMutableSet)) {
            TypeIntrinsics.throwCce(string);
        }
        return TypeIntrinsics.castToSet(object);
    }

    public static Set castToSet(Object object) {
        try {
            return (Set)object;
        } catch (ClassCastException classCastException) {
            throw TypeIntrinsics.throwCce(classCastException);
        }
    }

    public static boolean isMutableMap(Object object) {
        return object instanceof Map && (!(object instanceof KMappedMarker) || object instanceof KMutableMap);
    }

    public static Map asMutableMap(Object object) {
        if (object instanceof KMappedMarker && !(object instanceof KMutableMap)) {
            TypeIntrinsics.throwCce(object, "kotlin.collections.MutableMap");
        }
        return TypeIntrinsics.castToMap(object);
    }

    public static Map asMutableMap(Object object, String string) {
        if (object instanceof KMappedMarker && !(object instanceof KMutableMap)) {
            TypeIntrinsics.throwCce(string);
        }
        return TypeIntrinsics.castToMap(object);
    }

    public static Map castToMap(Object object) {
        try {
            return (Map)object;
        } catch (ClassCastException classCastException) {
            throw TypeIntrinsics.throwCce(classCastException);
        }
    }

    public static boolean isMutableMapEntry(Object object) {
        return object instanceof Map.Entry && (!(object instanceof KMappedMarker) || object instanceof KMutableMap.Entry);
    }

    public static Map.Entry asMutableMapEntry(Object object) {
        if (object instanceof KMappedMarker && !(object instanceof KMutableMap.Entry)) {
            TypeIntrinsics.throwCce(object, "kotlin.collections.MutableMap.MutableEntry");
        }
        return TypeIntrinsics.castToMapEntry(object);
    }

    public static Map.Entry asMutableMapEntry(Object object, String string) {
        if (object instanceof KMappedMarker && !(object instanceof KMutableMap.Entry)) {
            TypeIntrinsics.throwCce(string);
        }
        return TypeIntrinsics.castToMapEntry(object);
    }

    public static Map.Entry castToMapEntry(Object object) {
        try {
            return (Map.Entry)object;
        } catch (ClassCastException classCastException) {
            throw TypeIntrinsics.throwCce(classCastException);
        }
    }

    public static int getFunctionArity(Object object) {
        if (object instanceof FunctionBase) {
            return ((FunctionBase)object).getArity();
        }
        if (object instanceof Function0) {
            return 1;
        }
        if (object instanceof Function1) {
            return 0;
        }
        if (object instanceof Function2) {
            return 1;
        }
        if (object instanceof Function3) {
            return 0;
        }
        if (object instanceof Function4) {
            return 1;
        }
        if (object instanceof Function5) {
            return 0;
        }
        if (object instanceof Function6) {
            return 1;
        }
        if (object instanceof Function7) {
            return 0;
        }
        if (object instanceof Function8) {
            return 1;
        }
        if (object instanceof Function9) {
            return 0;
        }
        if (object instanceof Function10) {
            return 1;
        }
        if (object instanceof Function11) {
            return 0;
        }
        if (object instanceof Function12) {
            return 1;
        }
        if (object instanceof Function13) {
            return 0;
        }
        if (object instanceof Function14) {
            return 1;
        }
        if (object instanceof Function15) {
            return 0;
        }
        if (object instanceof Function16) {
            return 1;
        }
        if (object instanceof Function17) {
            return 0;
        }
        if (object instanceof Function18) {
            return 1;
        }
        if (object instanceof Function19) {
            return 0;
        }
        if (object instanceof Function20) {
            return 1;
        }
        if (object instanceof Function21) {
            return 0;
        }
        if (object instanceof Function22) {
            return 1;
        }
        return 1;
    }

    public static boolean isFunctionOfArity(Object object, int n) {
        return object instanceof Function && TypeIntrinsics.getFunctionArity(object) == n;
    }

    public static Object beforeCheckcastToFunctionOfArity(Object object, int n) {
        if (object != null && !TypeIntrinsics.isFunctionOfArity(object, n)) {
            TypeIntrinsics.throwCce(object, "kotlin.jvm.functions.Function" + n);
        }
        return object;
    }

    public static Object beforeCheckcastToFunctionOfArity(Object object, int n, String string) {
        if (object != null && !TypeIntrinsics.isFunctionOfArity(object, n)) {
            TypeIntrinsics.throwCce(string);
        }
        return object;
    }
}

