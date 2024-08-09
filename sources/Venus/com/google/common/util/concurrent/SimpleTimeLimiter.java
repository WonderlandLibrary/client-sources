/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.util.concurrent;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.ObjectArrays;
import com.google.common.collect.Sets;
import com.google.common.util.concurrent.TimeLimiter;
import com.google.common.util.concurrent.UncheckedTimeoutException;
import com.google.common.util.concurrent.Uninterruptibles;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Beta
@GwtIncompatible
public final class SimpleTimeLimiter
implements TimeLimiter {
    private final ExecutorService executor;

    public SimpleTimeLimiter(ExecutorService executorService) {
        this.executor = Preconditions.checkNotNull(executorService);
    }

    public SimpleTimeLimiter() {
        this(Executors.newCachedThreadPool());
    }

    @Override
    public <T> T newProxy(T t, Class<T> clazz, long l, TimeUnit timeUnit) {
        Preconditions.checkNotNull(t);
        Preconditions.checkNotNull(clazz);
        Preconditions.checkNotNull(timeUnit);
        Preconditions.checkArgument(l > 0L, "bad timeout: %s", l);
        Preconditions.checkArgument(clazz.isInterface(), "interfaceType must be an interface type");
        Set<Method> set = SimpleTimeLimiter.findInterruptibleMethods(clazz);
        InvocationHandler invocationHandler = new InvocationHandler(this, t, l, timeUnit, set){
            final Object val$target;
            final long val$timeoutDuration;
            final TimeUnit val$timeoutUnit;
            final Set val$interruptibleMethods;
            final SimpleTimeLimiter this$0;
            {
                this.this$0 = simpleTimeLimiter;
                this.val$target = object;
                this.val$timeoutDuration = l;
                this.val$timeoutUnit = timeUnit;
                this.val$interruptibleMethods = set;
            }

            @Override
            public Object invoke(Object object, Method method, Object[] objectArray) throws Throwable {
                Callable<Object> callable = new Callable<Object>(this, method, objectArray){
                    final Method val$method;
                    final Object[] val$args;
                    final 1 this$1;
                    {
                        this.this$1 = var1_1;
                        this.val$method = method;
                        this.val$args = objectArray;
                    }

                    @Override
                    public Object call() throws Exception {
                        try {
                            return this.val$method.invoke(this.this$1.val$target, this.val$args);
                        } catch (InvocationTargetException invocationTargetException) {
                            throw SimpleTimeLimiter.access$000(invocationTargetException, false);
                        }
                    }
                };
                return this.this$0.callWithTimeout(callable, this.val$timeoutDuration, this.val$timeoutUnit, this.val$interruptibleMethods.contains(method));
            }
        };
        return SimpleTimeLimiter.newProxy(clazz, invocationHandler);
    }

    @Override
    @CanIgnoreReturnValue
    public <T> T callWithTimeout(Callable<T> callable, long l, TimeUnit timeUnit, boolean bl) throws Exception {
        Preconditions.checkNotNull(callable);
        Preconditions.checkNotNull(timeUnit);
        Preconditions.checkArgument(l > 0L, "timeout must be positive: %s", l);
        Future<T> future = this.executor.submit(callable);
        try {
            if (bl) {
                try {
                    return future.get(l, timeUnit);
                } catch (InterruptedException interruptedException) {
                    future.cancel(true);
                    throw interruptedException;
                }
            }
            return Uninterruptibles.getUninterruptibly(future, l, timeUnit);
        } catch (ExecutionException executionException) {
            throw SimpleTimeLimiter.throwCause(executionException, true);
        } catch (TimeoutException timeoutException) {
            future.cancel(true);
            throw new UncheckedTimeoutException(timeoutException);
        }
    }

    private static Exception throwCause(Exception exception, boolean bl) throws Exception {
        Throwable throwable = exception.getCause();
        if (throwable == null) {
            throw exception;
        }
        if (bl) {
            StackTraceElement[] stackTraceElementArray = ObjectArrays.concat(throwable.getStackTrace(), exception.getStackTrace(), StackTraceElement.class);
            throwable.setStackTrace(stackTraceElementArray);
        }
        if (throwable instanceof Exception) {
            throw (Exception)throwable;
        }
        if (throwable instanceof Error) {
            throw (Error)throwable;
        }
        throw exception;
    }

    private static Set<Method> findInterruptibleMethods(Class<?> clazz) {
        HashSet<Method> hashSet = Sets.newHashSet();
        for (Method method : clazz.getMethods()) {
            if (!SimpleTimeLimiter.declaresInterruptedEx(method)) continue;
            hashSet.add(method);
        }
        return hashSet;
    }

    private static boolean declaresInterruptedEx(Method method) {
        for (Class<?> clazz : method.getExceptionTypes()) {
            if (clazz != InterruptedException.class) continue;
            return false;
        }
        return true;
    }

    private static <T> T newProxy(Class<T> clazz, InvocationHandler invocationHandler) {
        Object object = Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, invocationHandler);
        return clazz.cast(object);
    }

    static Exception access$000(Exception exception, boolean bl) throws Exception {
        return SimpleTimeLimiter.throwCause(exception, bl);
    }
}

