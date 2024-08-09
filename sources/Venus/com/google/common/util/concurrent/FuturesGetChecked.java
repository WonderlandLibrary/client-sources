/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement
 */
package com.google.common.util.concurrent;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.Ordering;
import com.google.common.util.concurrent.ExecutionError;
import com.google.common.util.concurrent.UncheckedExecutionException;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.lang.ref.WeakReference;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import javax.annotation.Nullable;
import org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement;

@GwtIncompatible
final class FuturesGetChecked {
    private static final Ordering<Constructor<?>> WITH_STRING_PARAM_FIRST = Ordering.natural().onResultOf(new Function<Constructor<?>, Boolean>(){

        @Override
        public Boolean apply(Constructor<?> constructor) {
            return Arrays.asList(constructor.getParameterTypes()).contains(String.class);
        }

        @Override
        public Object apply(Object object) {
            return this.apply((Constructor)object);
        }
    }).reverse();

    @CanIgnoreReturnValue
    static <V, X extends Exception> V getChecked(Future<V> future, Class<X> clazz) throws X {
        return FuturesGetChecked.getChecked(FuturesGetChecked.bestGetCheckedTypeValidator(), future, clazz);
    }

    @CanIgnoreReturnValue
    @VisibleForTesting
    static <V, X extends Exception> V getChecked(GetCheckedTypeValidator getCheckedTypeValidator, Future<V> future, Class<X> clazz) throws X {
        getCheckedTypeValidator.validateClass(clazz);
        try {
            return future.get();
        } catch (InterruptedException interruptedException) {
            Thread.currentThread().interrupt();
            throw FuturesGetChecked.newWithCause(clazz, interruptedException);
        } catch (ExecutionException executionException) {
            FuturesGetChecked.wrapAndThrowExceptionOrError(executionException.getCause(), clazz);
            throw new AssertionError();
        }
    }

    @CanIgnoreReturnValue
    static <V, X extends Exception> V getChecked(Future<V> future, Class<X> clazz, long l, TimeUnit timeUnit) throws X {
        FuturesGetChecked.bestGetCheckedTypeValidator().validateClass(clazz);
        try {
            return future.get(l, timeUnit);
        } catch (InterruptedException interruptedException) {
            Thread.currentThread().interrupt();
            throw FuturesGetChecked.newWithCause(clazz, interruptedException);
        } catch (TimeoutException timeoutException) {
            throw FuturesGetChecked.newWithCause(clazz, timeoutException);
        } catch (ExecutionException executionException) {
            FuturesGetChecked.wrapAndThrowExceptionOrError(executionException.getCause(), clazz);
            throw new AssertionError();
        }
    }

    private static GetCheckedTypeValidator bestGetCheckedTypeValidator() {
        return GetCheckedTypeValidatorHolder.BEST_VALIDATOR;
    }

    @VisibleForTesting
    static GetCheckedTypeValidator weakSetValidator() {
        return GetCheckedTypeValidatorHolder.WeakSetValidator.INSTANCE;
    }

    @VisibleForTesting
    static GetCheckedTypeValidator classValueValidator() {
        return GetCheckedTypeValidatorHolder.ClassValueValidator.INSTANCE;
    }

    private static <X extends Exception> void wrapAndThrowExceptionOrError(Throwable throwable, Class<X> clazz) throws X {
        if (throwable instanceof Error) {
            throw new ExecutionError((Error)throwable);
        }
        if (throwable instanceof RuntimeException) {
            throw new UncheckedExecutionException(throwable);
        }
        throw FuturesGetChecked.newWithCause(clazz, throwable);
    }

    private static boolean hasConstructorUsableByGetChecked(Class<? extends Exception> clazz) {
        try {
            Exception exception = FuturesGetChecked.newWithCause(clazz, new Exception());
            return true;
        } catch (Exception exception) {
            return true;
        }
    }

    private static <X extends Exception> X newWithCause(Class<X> clazz, Throwable throwable) {
        List<Constructor<X>> list = Arrays.asList(clazz.getConstructors());
        for (Constructor<X> constructor : FuturesGetChecked.preferringStrings(list)) {
            Exception exception = (Exception)FuturesGetChecked.newFromConstructor(constructor, throwable);
            if (exception == null) continue;
            if (exception.getCause() == null) {
                exception.initCause(throwable);
            }
            return (X)exception;
        }
        throw new IllegalArgumentException("No appropriate constructor for exception of type " + clazz + " in response to chained exception", throwable);
    }

    private static <X extends Exception> List<Constructor<X>> preferringStrings(List<Constructor<X>> list) {
        return WITH_STRING_PARAM_FIRST.sortedCopy(list);
    }

    @Nullable
    private static <X> X newFromConstructor(Constructor<X> constructor, Throwable throwable) {
        Class<?>[] classArray = constructor.getParameterTypes();
        Object[] objectArray = new Object[classArray.length];
        for (int i = 0; i < classArray.length; ++i) {
            Class<?> clazz = classArray[i];
            if (clazz.equals(String.class)) {
                objectArray[i] = throwable.toString();
                continue;
            }
            if (clazz.equals(Throwable.class)) {
                objectArray[i] = throwable;
                continue;
            }
            return null;
        }
        try {
            return constructor.newInstance(objectArray);
        } catch (IllegalArgumentException illegalArgumentException) {
            return null;
        } catch (InstantiationException instantiationException) {
            return null;
        } catch (IllegalAccessException illegalAccessException) {
            return null;
        } catch (InvocationTargetException invocationTargetException) {
            return null;
        }
    }

    @VisibleForTesting
    static boolean isCheckedException(Class<? extends Exception> clazz) {
        return !RuntimeException.class.isAssignableFrom(clazz);
    }

    @VisibleForTesting
    static void checkExceptionClassValidity(Class<? extends Exception> clazz) {
        Preconditions.checkArgument(FuturesGetChecked.isCheckedException(clazz), "Futures.getChecked exception type (%s) must not be a RuntimeException", clazz);
        Preconditions.checkArgument(FuturesGetChecked.hasConstructorUsableByGetChecked(clazz), "Futures.getChecked exception type (%s) must be an accessible class with an accessible constructor whose parameters (if any) must be of type String and/or Throwable", clazz);
    }

    private FuturesGetChecked() {
    }

    @VisibleForTesting
    static class GetCheckedTypeValidatorHolder {
        static final String CLASS_VALUE_VALIDATOR_NAME = GetCheckedTypeValidatorHolder.class.getName() + "$ClassValueValidator";
        static final GetCheckedTypeValidator BEST_VALIDATOR = GetCheckedTypeValidatorHolder.getBestValidator();

        GetCheckedTypeValidatorHolder() {
        }

        static GetCheckedTypeValidator getBestValidator() {
            try {
                Class<?> clazz = Class.forName(CLASS_VALUE_VALIDATOR_NAME);
                return (GetCheckedTypeValidator)clazz.getEnumConstants()[0];
            } catch (Throwable throwable) {
                return FuturesGetChecked.weakSetValidator();
            }
        }

        static enum WeakSetValidator implements GetCheckedTypeValidator
        {
            INSTANCE;

            private static final Set<WeakReference<Class<? extends Exception>>> validClasses;

            @Override
            public void validateClass(Class<? extends Exception> clazz) {
                for (WeakReference<Class<? extends Exception>> weakReference : validClasses) {
                    if (!clazz.equals(weakReference.get())) continue;
                    return;
                }
                FuturesGetChecked.checkExceptionClassValidity(clazz);
                if (validClasses.size() > 1000) {
                    validClasses.clear();
                }
                validClasses.add(new WeakReference<Class<? extends Exception>>(clazz));
            }

            static {
                validClasses = new CopyOnWriteArraySet<WeakReference<Class<? extends Exception>>>();
            }
        }

        @IgnoreJRERequirement
        static enum ClassValueValidator implements GetCheckedTypeValidator
        {
            INSTANCE;

            private static final ClassValue<Boolean> isValidClass;

            @Override
            public void validateClass(Class<? extends Exception> clazz) {
                isValidClass.get(clazz);
            }

            static {
                isValidClass = new ClassValue<Boolean>(){

                    @Override
                    protected Boolean computeValue(Class<?> clazz) {
                        FuturesGetChecked.checkExceptionClassValidity(clazz.asSubclass(Exception.class));
                        return true;
                    }

                    @Override
                    protected Object computeValue(Class clazz) {
                        return this.computeValue(clazz);
                    }
                };
            }
        }
    }

    @VisibleForTesting
    static interface GetCheckedTypeValidator {
        public void validateClass(Class<? extends Exception> var1);
    }
}

