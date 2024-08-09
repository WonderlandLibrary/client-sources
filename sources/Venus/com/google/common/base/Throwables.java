/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.base;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;

@GwtCompatible(emulated=true)
public final class Throwables {
    @GwtIncompatible
    private static final String JAVA_LANG_ACCESS_CLASSNAME = "sun.misc.JavaLangAccess";
    @GwtIncompatible
    @VisibleForTesting
    static final String SHARED_SECRETS_CLASSNAME = "sun.misc.SharedSecrets";
    @Nullable
    @GwtIncompatible
    private static final Object jla = Throwables.getJLA();
    @Nullable
    @GwtIncompatible
    private static final Method getStackTraceElementMethod = jla == null ? null : Throwables.getGetMethod();
    @Nullable
    @GwtIncompatible
    private static final Method getStackTraceDepthMethod = jla == null ? null : Throwables.getSizeMethod();

    private Throwables() {
    }

    @GwtIncompatible
    public static <X extends Throwable> void throwIfInstanceOf(Throwable throwable, Class<X> clazz) throws X {
        Preconditions.checkNotNull(throwable);
        if (clazz.isInstance(throwable)) {
            throw (Throwable)clazz.cast(throwable);
        }
    }

    @Deprecated
    @GwtIncompatible
    public static <X extends Throwable> void propagateIfInstanceOf(@Nullable Throwable throwable, Class<X> clazz) throws X {
        if (throwable != null) {
            Throwables.throwIfInstanceOf(throwable, clazz);
        }
    }

    public static void throwIfUnchecked(Throwable throwable) {
        Preconditions.checkNotNull(throwable);
        if (throwable instanceof RuntimeException) {
            throw (RuntimeException)throwable;
        }
        if (throwable instanceof Error) {
            throw (Error)throwable;
        }
    }

    @Deprecated
    @GwtIncompatible
    public static void propagateIfPossible(@Nullable Throwable throwable) {
        if (throwable != null) {
            Throwables.throwIfUnchecked(throwable);
        }
    }

    @GwtIncompatible
    public static <X extends Throwable> void propagateIfPossible(@Nullable Throwable throwable, Class<X> clazz) throws X {
        Throwables.propagateIfInstanceOf(throwable, clazz);
        Throwables.propagateIfPossible(throwable);
    }

    @GwtIncompatible
    public static <X1 extends Throwable, X2 extends Throwable> void propagateIfPossible(@Nullable Throwable throwable, Class<X1> clazz, Class<X2> clazz2) throws X1, X2 {
        Preconditions.checkNotNull(clazz2);
        Throwables.propagateIfInstanceOf(throwable, clazz);
        Throwables.propagateIfPossible(throwable, clazz2);
    }

    @Deprecated
    @CanIgnoreReturnValue
    @GwtIncompatible
    public static RuntimeException propagate(Throwable throwable) {
        Throwables.throwIfUnchecked(throwable);
        throw new RuntimeException(throwable);
    }

    public static Throwable getRootCause(Throwable throwable) {
        Throwable throwable2;
        while ((throwable2 = throwable.getCause()) != null) {
            throwable = throwable2;
        }
        return throwable;
    }

    @Beta
    public static List<Throwable> getCausalChain(Throwable throwable) {
        Preconditions.checkNotNull(throwable);
        ArrayList<Throwable> arrayList = new ArrayList<Throwable>(4);
        while (throwable != null) {
            arrayList.add(throwable);
            throwable = throwable.getCause();
        }
        return Collections.unmodifiableList(arrayList);
    }

    @GwtIncompatible
    public static String getStackTraceAsString(Throwable throwable) {
        StringWriter stringWriter = new StringWriter();
        throwable.printStackTrace(new PrintWriter(stringWriter));
        return stringWriter.toString();
    }

    @Beta
    @GwtIncompatible
    public static List<StackTraceElement> lazyStackTrace(Throwable throwable) {
        return Throwables.lazyStackTraceIsLazy() ? Throwables.jlaStackTrace(throwable) : Collections.unmodifiableList(Arrays.asList(throwable.getStackTrace()));
    }

    @Beta
    @GwtIncompatible
    public static boolean lazyStackTraceIsLazy() {
        return getStackTraceElementMethod != null & getStackTraceDepthMethod != null;
    }

    @GwtIncompatible
    private static List<StackTraceElement> jlaStackTrace(Throwable throwable) {
        Preconditions.checkNotNull(throwable);
        return new AbstractList<StackTraceElement>(throwable){
            final Throwable val$t;
            {
                this.val$t = throwable;
            }

            @Override
            public StackTraceElement get(int n) {
                return (StackTraceElement)Throwables.access$200(Throwables.access$000(), Throwables.access$100(), new Object[]{this.val$t, n});
            }

            @Override
            public int size() {
                return (Integer)Throwables.access$200(Throwables.access$300(), Throwables.access$100(), new Object[]{this.val$t});
            }

            @Override
            public Object get(int n) {
                return this.get(n);
            }
        };
    }

    @GwtIncompatible
    private static Object invokeAccessibleNonThrowingMethod(Method method, Object object, Object ... objectArray) {
        try {
            return method.invoke(object, objectArray);
        } catch (IllegalAccessException illegalAccessException) {
            throw new RuntimeException(illegalAccessException);
        } catch (InvocationTargetException invocationTargetException) {
            throw Throwables.propagate(invocationTargetException.getCause());
        }
    }

    @Nullable
    @GwtIncompatible
    private static Object getJLA() {
        try {
            Class<?> clazz = Class.forName(SHARED_SECRETS_CLASSNAME, false, null);
            Method method = clazz.getMethod("getJavaLangAccess", new Class[0]);
            return method.invoke(null, new Object[0]);
        } catch (ThreadDeath threadDeath) {
            throw threadDeath;
        } catch (Throwable throwable) {
            return null;
        }
    }

    @Nullable
    @GwtIncompatible
    private static Method getGetMethod() {
        return Throwables.getJlaMethod("getStackTraceElement", Throwable.class, Integer.TYPE);
    }

    @Nullable
    @GwtIncompatible
    private static Method getSizeMethod() {
        return Throwables.getJlaMethod("getStackTraceDepth", Throwable.class);
    }

    @Nullable
    @GwtIncompatible
    private static Method getJlaMethod(String string, Class<?> ... classArray) throws ThreadDeath {
        try {
            return Class.forName(JAVA_LANG_ACCESS_CLASSNAME, false, null).getMethod(string, classArray);
        } catch (ThreadDeath threadDeath) {
            throw threadDeath;
        } catch (Throwable throwable) {
            return null;
        }
    }

    static Method access$000() {
        return getStackTraceElementMethod;
    }

    static Object access$100() {
        return jla;
    }

    static Object access$200(Method method, Object object, Object[] objectArray) {
        return Throwables.invokeAccessibleNonThrowingMethod(method, object, objectArray);
    }

    static Method access$300() {
        return getStackTraceDepthMethod;
    }
}

