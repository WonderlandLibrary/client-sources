/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package kotlin.jvm.internal;

import java.util.Arrays;
import kotlin.KotlinNullPointerException;
import kotlin.SinceKotlin;
import kotlin.UninitializedPropertyAccessException;

public class Intrinsics {
    private Intrinsics() {
    }

    public static String stringPlus(String string, Object object) {
        return string + object;
    }

    public static void checkNotNull(Object object) {
        if (object == null) {
            Intrinsics.throwJavaNpe();
        }
    }

    public static void checkNotNull(Object object, String string) {
        if (object == null) {
            Intrinsics.throwJavaNpe(string);
        }
    }

    public static void throwNpe() {
        throw Intrinsics.sanitizeStackTrace(new KotlinNullPointerException());
    }

    public static void throwNpe(String string) {
        throw Intrinsics.sanitizeStackTrace(new KotlinNullPointerException(string));
    }

    @SinceKotlin(version="1.4")
    public static void throwJavaNpe() {
        throw Intrinsics.sanitizeStackTrace(new NullPointerException());
    }

    @SinceKotlin(version="1.4")
    public static void throwJavaNpe(String string) {
        throw Intrinsics.sanitizeStackTrace(new NullPointerException(string));
    }

    public static void throwUninitializedProperty(String string) {
        throw Intrinsics.sanitizeStackTrace(new UninitializedPropertyAccessException(string));
    }

    public static void throwUninitializedPropertyAccessException(String string) {
        Intrinsics.throwUninitializedProperty("lateinit property " + string + " has not been initialized");
    }

    public static void throwAssert() {
        throw Intrinsics.sanitizeStackTrace(new AssertionError());
    }

    public static void throwAssert(String string) {
        throw Intrinsics.sanitizeStackTrace(new AssertionError((Object)string));
    }

    public static void throwIllegalArgument() {
        throw Intrinsics.sanitizeStackTrace(new IllegalArgumentException());
    }

    public static void throwIllegalArgument(String string) {
        throw Intrinsics.sanitizeStackTrace(new IllegalArgumentException(string));
    }

    public static void throwIllegalState() {
        throw Intrinsics.sanitizeStackTrace(new IllegalStateException());
    }

    public static void throwIllegalState(String string) {
        throw Intrinsics.sanitizeStackTrace(new IllegalStateException(string));
    }

    public static void checkExpressionValueIsNotNull(Object object, String string) {
        if (object == null) {
            throw Intrinsics.sanitizeStackTrace(new IllegalStateException(string + " must not be null"));
        }
    }

    public static void checkNotNullExpressionValue(Object object, String string) {
        if (object == null) {
            throw Intrinsics.sanitizeStackTrace(new NullPointerException(string + " must not be null"));
        }
    }

    public static void checkReturnedValueIsNotNull(Object object, String string, String string2) {
        if (object == null) {
            throw Intrinsics.sanitizeStackTrace(new IllegalStateException("Method specified as non-null returned null: " + string + "." + string2));
        }
    }

    public static void checkReturnedValueIsNotNull(Object object, String string) {
        if (object == null) {
            throw Intrinsics.sanitizeStackTrace(new IllegalStateException(string));
        }
    }

    public static void checkFieldIsNotNull(Object object, String string, String string2) {
        if (object == null) {
            throw Intrinsics.sanitizeStackTrace(new IllegalStateException("Field specified as non-null is null: " + string + "." + string2));
        }
    }

    public static void checkFieldIsNotNull(Object object, String string) {
        if (object == null) {
            throw Intrinsics.sanitizeStackTrace(new IllegalStateException(string));
        }
    }

    public static void checkParameterIsNotNull(Object object, String string) {
        if (object == null) {
            Intrinsics.throwParameterIsNullIAE(string);
        }
    }

    public static void checkNotNullParameter(Object object, String string) {
        if (object == null) {
            Intrinsics.throwParameterIsNullNPE(string);
        }
    }

    private static void throwParameterIsNullIAE(String string) {
        throw Intrinsics.sanitizeStackTrace(new IllegalArgumentException(Intrinsics.createParameterIsNullExceptionMessage(string)));
    }

    private static void throwParameterIsNullNPE(String string) {
        throw Intrinsics.sanitizeStackTrace(new NullPointerException(Intrinsics.createParameterIsNullExceptionMessage(string)));
    }

    private static String createParameterIsNullExceptionMessage(String string) {
        StackTraceElement[] stackTraceElementArray = Thread.currentThread().getStackTrace();
        String string2 = Intrinsics.class.getName();
        int n = 0;
        while (!stackTraceElementArray[n].getClassName().equals(string2)) {
            ++n;
        }
        while (stackTraceElementArray[n].getClassName().equals(string2)) {
            ++n;
        }
        StackTraceElement stackTraceElement = stackTraceElementArray[n];
        String string3 = stackTraceElement.getClassName();
        String string4 = stackTraceElement.getMethodName();
        return "Parameter specified as non-null is null: method " + string3 + "." + string4 + ", parameter " + string;
    }

    public static int compare(long l, long l2) {
        return l < l2 ? -1 : (l == l2 ? 0 : 1);
    }

    public static int compare(int n, int n2) {
        return n < n2 ? -1 : (n == n2 ? 0 : 1);
    }

    public static boolean areEqual(Object object, Object object2) {
        return object == null ? object2 == null : object.equals(object2);
    }

    @SinceKotlin(version="1.1")
    public static boolean areEqual(Double d, Double d2) {
        return d == null ? d2 == null : d2 != null && d.doubleValue() == d2.doubleValue();
    }

    @SinceKotlin(version="1.1")
    public static boolean areEqual(Double d, double d2) {
        return d != null && d == d2;
    }

    @SinceKotlin(version="1.1")
    public static boolean areEqual(double d, Double d2) {
        return d2 != null && d == d2;
    }

    @SinceKotlin(version="1.1")
    public static boolean areEqual(Float f, Float f2) {
        return f == null ? f2 == null : f2 != null && f.floatValue() == f2.floatValue();
    }

    @SinceKotlin(version="1.1")
    public static boolean areEqual(Float f, float f2) {
        return f != null && f.floatValue() == f2;
    }

    @SinceKotlin(version="1.1")
    public static boolean areEqual(float f, Float f2) {
        return f2 != null && f == f2.floatValue();
    }

    public static void throwUndefinedForReified() {
        Intrinsics.throwUndefinedForReified("This function has a reified type parameter and thus can only be inlined at compilation time, not called directly.");
    }

    public static void throwUndefinedForReified(String string) {
        throw new UnsupportedOperationException(string);
    }

    public static void reifiedOperationMarker(int n, String string) {
        Intrinsics.throwUndefinedForReified();
    }

    public static void reifiedOperationMarker(int n, String string, String string2) {
        Intrinsics.throwUndefinedForReified(string2);
    }

    public static void needClassReification() {
        Intrinsics.throwUndefinedForReified();
    }

    public static void needClassReification(String string) {
        Intrinsics.throwUndefinedForReified(string);
    }

    public static void checkHasClass(String string) throws ClassNotFoundException {
        String string2 = string.replace('/', '.');
        try {
            Class.forName(string2);
        } catch (ClassNotFoundException classNotFoundException) {
            throw Intrinsics.sanitizeStackTrace(new ClassNotFoundException("Class " + string2 + " is not found. Please update the Kotlin runtime to the latest version", classNotFoundException));
        }
    }

    public static void checkHasClass(String string, String string2) throws ClassNotFoundException {
        String string3 = string.replace('/', '.');
        try {
            Class.forName(string3);
        } catch (ClassNotFoundException classNotFoundException) {
            throw Intrinsics.sanitizeStackTrace(new ClassNotFoundException("Class " + string3 + " is not found: this code requires the Kotlin runtime of version at least " + string2, classNotFoundException));
        }
    }

    private static <T extends Throwable> T sanitizeStackTrace(T t) {
        return Intrinsics.sanitizeStackTrace(t, Intrinsics.class.getName());
    }

    static <T extends Throwable> T sanitizeStackTrace(T t, String string) {
        StackTraceElement[] stackTraceElementArray = t.getStackTrace();
        int n = stackTraceElementArray.length;
        int n2 = -1;
        for (int i = 0; i < n; ++i) {
            if (!string.equals(stackTraceElementArray[i].getClassName())) continue;
            n2 = i;
        }
        StackTraceElement[] stackTraceElementArray2 = Arrays.copyOfRange(stackTraceElementArray, n2 + 1, n);
        t.setStackTrace(stackTraceElementArray2);
        return t;
    }

    @SinceKotlin(version="1.4")
    public static class Kotlin {
        private Kotlin() {
        }
    }
}

