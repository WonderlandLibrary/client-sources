/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.util.concurrent;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.lang.reflect.Field;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.concurrent.locks.LockSupport;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Nullable;
import sun.misc.Unsafe;

@GwtCompatible(emulated=true)
public abstract class AbstractFuture<V>
implements ListenableFuture<V> {
    private static final boolean GENERATE_CANCELLATION_CAUSES;
    private static final Logger log;
    private static final long SPIN_THRESHOLD_NANOS = 1000L;
    private static final AtomicHelper ATOMIC_HELPER;
    private static final Object NULL;
    private volatile Object value;
    private volatile Listener listeners;
    private volatile Waiter waiters;

    /*
     * Unable to fully structure code
     */
    private void removeWaiter(Waiter var1_1) {
        var1_1.thread = null;
        block0: while (true) {
            var2_2 = null;
            var3_3 = this.waiters;
            if (var3_3 == Waiter.TOMBSTONE) {
                return;
            }
            while (var3_3 != null) {
                var4_4 = var3_3.next;
                if (var3_3.thread != null) {
                    var2_2 = var3_3;
                } else if (var2_2 != null) {
                    var2_2.next = var4_4;
                    if (var2_2.thread == null) {
                        continue block0;
                    }
                } else {
                    if (AbstractFuture.ATOMIC_HELPER.casWaiters(this, var3_3, var4_4)) ** break;
                    continue block0;
                }
                var3_3 = var4_4;
            }
            break;
        }
    }

    protected AbstractFuture() {
    }

    @Override
    @CanIgnoreReturnValue
    public V get(long l, TimeUnit timeUnit) throws InterruptedException, TimeoutException, ExecutionException {
        long l2;
        Object object;
        long l3;
        block10: {
            l3 = timeUnit.toNanos(l);
            if (Thread.interrupted()) {
                throw new InterruptedException();
            }
            object = this.value;
            if (object != null & !(object instanceof SetFuture)) {
                return this.getDoneValue(object);
            }
            long l4 = l2 = l3 > 0L ? System.nanoTime() + l3 : 0L;
            if (l3 >= 1000L) {
                Waiter waiter = this.waiters;
                if (waiter != Waiter.TOMBSTONE) {
                    Waiter waiter2 = new Waiter();
                    do {
                        waiter2.setNext(waiter);
                        if (!ATOMIC_HELPER.casWaiters(this, waiter, waiter2)) continue;
                        do {
                            LockSupport.parkNanos(this, l3);
                            if (Thread.interrupted()) {
                                this.removeWaiter(waiter2);
                                throw new InterruptedException();
                            }
                            object = this.value;
                            if (!(object != null & !(object instanceof SetFuture))) continue;
                            return this.getDoneValue(object);
                        } while ((l3 = l2 - System.nanoTime()) >= 1000L);
                        this.removeWaiter(waiter2);
                        break block10;
                    } while ((waiter = this.waiters) != Waiter.TOMBSTONE);
                }
                return this.getDoneValue(this.value);
            }
        }
        while (l3 > 0L) {
            object = this.value;
            if (object != null & !(object instanceof SetFuture)) {
                return this.getDoneValue(object);
            }
            if (Thread.interrupted()) {
                throw new InterruptedException();
            }
            l3 = l2 - System.nanoTime();
        }
        throw new TimeoutException();
    }

    @Override
    @CanIgnoreReturnValue
    public V get() throws InterruptedException, ExecutionException {
        if (Thread.interrupted()) {
            throw new InterruptedException();
        }
        Object object = this.value;
        if (object != null & !(object instanceof SetFuture)) {
            return this.getDoneValue(object);
        }
        Waiter waiter = this.waiters;
        if (waiter != Waiter.TOMBSTONE) {
            Waiter waiter2 = new Waiter();
            do {
                waiter2.setNext(waiter);
                if (!ATOMIC_HELPER.casWaiters(this, waiter, waiter2)) continue;
                do {
                    LockSupport.park(this);
                    if (!Thread.interrupted()) continue;
                    this.removeWaiter(waiter2);
                    throw new InterruptedException();
                } while (!((object = this.value) != null & !(object instanceof SetFuture)));
                return this.getDoneValue(object);
            } while ((waiter = this.waiters) != Waiter.TOMBSTONE);
        }
        return this.getDoneValue(this.value);
    }

    private V getDoneValue(Object object) throws ExecutionException {
        if (object instanceof Cancellation) {
            throw AbstractFuture.cancellationExceptionWithCause("Task was cancelled.", ((Cancellation)object).cause);
        }
        if (object instanceof Failure) {
            throw new ExecutionException(((Failure)object).exception);
        }
        if (object == NULL) {
            return null;
        }
        Object object2 = object;
        return (V)object2;
    }

    @Override
    public boolean isDone() {
        Object object = this.value;
        return object != null & !(object instanceof SetFuture);
    }

    @Override
    public boolean isCancelled() {
        Object object = this.value;
        return object instanceof Cancellation;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    @CanIgnoreReturnValue
    public boolean cancel(boolean bl) {
        Object object = this.value;
        boolean bl2 = false;
        if (!(object == null | object instanceof SetFuture)) return bl2;
        CancellationException cancellationException = GENERATE_CANCELLATION_CAUSES ? new CancellationException("Future.cancel() was called.") : null;
        Cancellation cancellation = new Cancellation(bl, cancellationException);
        AbstractFuture abstractFuture = this;
        while (true) {
            if (ATOMIC_HELPER.casValue(abstractFuture, object, cancellation)) {
                bl2 = true;
                if (bl) {
                    abstractFuture.interruptTask();
                }
                AbstractFuture.complete(abstractFuture);
                if (!(object instanceof SetFuture)) return bl2;
                ListenableFuture listenableFuture = ((SetFuture)object).future;
                if (listenableFuture instanceof TrustedFuture) {
                    AbstractFuture abstractFuture2 = (AbstractFuture)listenableFuture;
                    object = abstractFuture2.value;
                    if (!(object == null | object instanceof SetFuture)) return bl2;
                    abstractFuture = abstractFuture2;
                    continue;
                }
                listenableFuture.cancel(bl);
                return bl2;
            }
            object = abstractFuture.value;
            if (!(object instanceof SetFuture)) return bl2;
        }
    }

    protected void interruptTask() {
    }

    protected final boolean wasInterrupted() {
        Object object = this.value;
        return object instanceof Cancellation && ((Cancellation)object).wasInterrupted;
    }

    @Override
    public void addListener(Runnable runnable, Executor executor) {
        Preconditions.checkNotNull(runnable, "Runnable was null.");
        Preconditions.checkNotNull(executor, "Executor was null.");
        Listener listener = this.listeners;
        if (listener != Listener.TOMBSTONE) {
            Listener listener2 = new Listener(runnable, executor);
            do {
                listener2.next = listener;
                if (!ATOMIC_HELPER.casListeners(this, listener, listener2)) continue;
                return;
            } while ((listener = this.listeners) != Listener.TOMBSTONE);
        }
        AbstractFuture.executeListener(runnable, executor);
    }

    @CanIgnoreReturnValue
    protected boolean set(@Nullable V v) {
        Object object;
        Object object2 = object = v == null ? NULL : v;
        if (ATOMIC_HELPER.casValue(this, null, object)) {
            AbstractFuture.complete(this);
            return false;
        }
        return true;
    }

    @CanIgnoreReturnValue
    protected boolean setException(Throwable throwable) {
        Failure failure = new Failure(Preconditions.checkNotNull(throwable));
        if (ATOMIC_HELPER.casValue(this, null, failure)) {
            AbstractFuture.complete(this);
            return false;
        }
        return true;
    }

    @Beta
    @CanIgnoreReturnValue
    protected boolean setFuture(ListenableFuture<? extends V> listenableFuture) {
        Preconditions.checkNotNull(listenableFuture);
        Object object = this.value;
        if (object == null) {
            if (listenableFuture.isDone()) {
                Object object2 = AbstractFuture.getFutureValue(listenableFuture);
                if (ATOMIC_HELPER.casValue(this, null, object2)) {
                    AbstractFuture.complete(this);
                    return false;
                }
                return true;
            }
            SetFuture<? extends V> setFuture = new SetFuture<V>(this, listenableFuture);
            if (ATOMIC_HELPER.casValue(this, null, setFuture)) {
                try {
                    listenableFuture.addListener(setFuture, MoreExecutors.directExecutor());
                } catch (Throwable throwable) {
                    Failure failure;
                    try {
                        failure = new Failure(throwable);
                    } catch (Throwable throwable2) {
                        failure = Failure.FALLBACK_INSTANCE;
                    }
                    boolean bl = ATOMIC_HELPER.casValue(this, setFuture, failure);
                }
                return false;
            }
            object = this.value;
        }
        if (object instanceof Cancellation) {
            listenableFuture.cancel(((Cancellation)object).wasInterrupted);
        }
        return true;
    }

    private static Object getFutureValue(ListenableFuture<?> listenableFuture) {
        Object object;
        if (listenableFuture instanceof TrustedFuture) {
            return ((AbstractFuture)listenableFuture).value;
        }
        try {
            Object obj = Futures.getDone(listenableFuture);
            object = obj == null ? NULL : obj;
        } catch (ExecutionException executionException) {
            object = new Failure(executionException.getCause());
        } catch (CancellationException cancellationException) {
            object = new Cancellation(false, cancellationException);
        } catch (Throwable throwable) {
            object = new Failure(throwable);
        }
        return object;
    }

    private static void complete(AbstractFuture<?> abstractFuture) {
        Listener listener = null;
        block0: while (true) {
            super.releaseWaiters();
            abstractFuture.afterDone();
            listener = super.clearListeners(listener);
            abstractFuture = null;
            while (listener != null) {
                Listener listener2 = listener;
                listener = listener.next;
                Runnable runnable = listener2.task;
                if (runnable instanceof SetFuture) {
                    Object object;
                    SetFuture setFuture = (SetFuture)runnable;
                    abstractFuture = setFuture.owner;
                    if (abstractFuture.value != setFuture || !ATOMIC_HELPER.casValue(abstractFuture, setFuture, object = AbstractFuture.getFutureValue(setFuture.future))) continue;
                    continue block0;
                }
                AbstractFuture.executeListener(runnable, listener2.executor);
            }
            break;
        }
    }

    @Beta
    protected void afterDone() {
    }

    final Throwable trustedGetException() {
        return ((Failure)this.value).exception;
    }

    final void maybePropagateCancellation(@Nullable Future<?> future) {
        if (future != null & this.isCancelled()) {
            future.cancel(this.wasInterrupted());
        }
    }

    private void releaseWaiters() {
        Waiter waiter;
        while (!ATOMIC_HELPER.casWaiters(this, waiter = this.waiters, Waiter.TOMBSTONE)) {
        }
        Waiter waiter2 = waiter;
        while (waiter2 != null) {
            waiter2.unpark();
            waiter2 = waiter2.next;
        }
    }

    private Listener clearListeners(Listener listener) {
        Listener listener2;
        while (!ATOMIC_HELPER.casListeners(this, listener2 = this.listeners, Listener.TOMBSTONE)) {
        }
        Listener listener3 = listener;
        while (listener2 != null) {
            Listener listener4 = listener2;
            listener2 = listener2.next;
            listener4.next = listener3;
            listener3 = listener4;
        }
        return listener3;
    }

    private static void executeListener(Runnable runnable, Executor executor) {
        try {
            executor.execute(runnable);
        } catch (RuntimeException runtimeException) {
            log.log(Level.SEVERE, "RuntimeException while executing runnable " + runnable + " with executor " + executor, runtimeException);
        }
    }

    private static CancellationException cancellationExceptionWithCause(@Nullable String string, @Nullable Throwable throwable) {
        CancellationException cancellationException = new CancellationException(string);
        cancellationException.initCause(throwable);
        return cancellationException;
    }

    static AtomicHelper access$200() {
        return ATOMIC_HELPER;
    }

    static Object access$300(AbstractFuture abstractFuture) {
        return abstractFuture.value;
    }

    static Object access$400(ListenableFuture listenableFuture) {
        return AbstractFuture.getFutureValue(listenableFuture);
    }

    static void access$500(AbstractFuture abstractFuture) {
        AbstractFuture.complete(abstractFuture);
    }

    static Waiter access$700(AbstractFuture abstractFuture) {
        return abstractFuture.waiters;
    }

    static Waiter access$702(AbstractFuture abstractFuture, Waiter waiter) {
        abstractFuture.waiters = waiter;
        return abstractFuture.waiters;
    }

    static Listener access$800(AbstractFuture abstractFuture) {
        return abstractFuture.listeners;
    }

    static Listener access$802(AbstractFuture abstractFuture, Listener listener) {
        abstractFuture.listeners = listener;
        return abstractFuture.listeners;
    }

    static Object access$302(AbstractFuture abstractFuture, Object object) {
        abstractFuture.value = object;
        return abstractFuture.value;
    }

    static {
        AtomicHelper atomicHelper;
        GENERATE_CANCELLATION_CAUSES = Boolean.parseBoolean(System.getProperty("guava.concurrent.generate_cancellation_cause", "false"));
        log = Logger.getLogger(AbstractFuture.class.getName());
        try {
            atomicHelper = new UnsafeAtomicHelper(null);
        } catch (Throwable throwable) {
            try {
                atomicHelper = new SafeAtomicHelper(AtomicReferenceFieldUpdater.newUpdater(Waiter.class, Thread.class, "thread"), AtomicReferenceFieldUpdater.newUpdater(Waiter.class, Waiter.class, "next"), AtomicReferenceFieldUpdater.newUpdater(AbstractFuture.class, Waiter.class, "waiters"), AtomicReferenceFieldUpdater.newUpdater(AbstractFuture.class, Listener.class, "listeners"), AtomicReferenceFieldUpdater.newUpdater(AbstractFuture.class, Object.class, "value"));
            } catch (Throwable throwable2) {
                log.log(Level.SEVERE, "UnsafeAtomicHelper is broken!", throwable);
                log.log(Level.SEVERE, "SafeAtomicHelper is broken!", throwable2);
                atomicHelper = new SynchronizedHelper(null);
            }
        }
        ATOMIC_HELPER = atomicHelper;
        Class<LockSupport> clazz = LockSupport.class;
        NULL = new Object();
    }

    private static final class SynchronizedHelper
    extends AtomicHelper {
        private SynchronizedHelper() {
            super(null);
        }

        @Override
        void putThread(Waiter waiter, Thread thread2) {
            waiter.thread = thread2;
        }

        @Override
        void putNext(Waiter waiter, Waiter waiter2) {
            waiter.next = waiter2;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        boolean casWaiters(AbstractFuture<?> abstractFuture, Waiter waiter, Waiter waiter2) {
            AbstractFuture<?> abstractFuture2 = abstractFuture;
            synchronized (abstractFuture2) {
                if (AbstractFuture.access$700(abstractFuture) == waiter) {
                    AbstractFuture.access$702(abstractFuture, waiter2);
                    return true;
                }
                return false;
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        boolean casListeners(AbstractFuture<?> abstractFuture, Listener listener, Listener listener2) {
            AbstractFuture<?> abstractFuture2 = abstractFuture;
            synchronized (abstractFuture2) {
                if (AbstractFuture.access$800(abstractFuture) == listener) {
                    AbstractFuture.access$802(abstractFuture, listener2);
                    return true;
                }
                return false;
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        boolean casValue(AbstractFuture<?> abstractFuture, Object object, Object object2) {
            AbstractFuture<?> abstractFuture2 = abstractFuture;
            synchronized (abstractFuture2) {
                if (AbstractFuture.access$300(abstractFuture) == object) {
                    AbstractFuture.access$302(abstractFuture, object2);
                    return true;
                }
                return false;
            }
        }

        SynchronizedHelper(1 var1_1) {
            this();
        }
    }

    private static final class SafeAtomicHelper
    extends AtomicHelper {
        final AtomicReferenceFieldUpdater<Waiter, Thread> waiterThreadUpdater;
        final AtomicReferenceFieldUpdater<Waiter, Waiter> waiterNextUpdater;
        final AtomicReferenceFieldUpdater<AbstractFuture, Waiter> waitersUpdater;
        final AtomicReferenceFieldUpdater<AbstractFuture, Listener> listenersUpdater;
        final AtomicReferenceFieldUpdater<AbstractFuture, Object> valueUpdater;

        SafeAtomicHelper(AtomicReferenceFieldUpdater<Waiter, Thread> atomicReferenceFieldUpdater, AtomicReferenceFieldUpdater<Waiter, Waiter> atomicReferenceFieldUpdater2, AtomicReferenceFieldUpdater<AbstractFuture, Waiter> atomicReferenceFieldUpdater3, AtomicReferenceFieldUpdater<AbstractFuture, Listener> atomicReferenceFieldUpdater4, AtomicReferenceFieldUpdater<AbstractFuture, Object> atomicReferenceFieldUpdater5) {
            super(null);
            this.waiterThreadUpdater = atomicReferenceFieldUpdater;
            this.waiterNextUpdater = atomicReferenceFieldUpdater2;
            this.waitersUpdater = atomicReferenceFieldUpdater3;
            this.listenersUpdater = atomicReferenceFieldUpdater4;
            this.valueUpdater = atomicReferenceFieldUpdater5;
        }

        @Override
        void putThread(Waiter waiter, Thread thread2) {
            this.waiterThreadUpdater.lazySet(waiter, thread2);
        }

        @Override
        void putNext(Waiter waiter, Waiter waiter2) {
            this.waiterNextUpdater.lazySet(waiter, waiter2);
        }

        @Override
        boolean casWaiters(AbstractFuture<?> abstractFuture, Waiter waiter, Waiter waiter2) {
            return this.waitersUpdater.compareAndSet(abstractFuture, waiter, waiter2);
        }

        @Override
        boolean casListeners(AbstractFuture<?> abstractFuture, Listener listener, Listener listener2) {
            return this.listenersUpdater.compareAndSet(abstractFuture, listener, listener2);
        }

        @Override
        boolean casValue(AbstractFuture<?> abstractFuture, Object object, Object object2) {
            return this.valueUpdater.compareAndSet(abstractFuture, object, object2);
        }
    }

    private static final class UnsafeAtomicHelper
    extends AtomicHelper {
        static final Unsafe UNSAFE;
        static final long LISTENERS_OFFSET;
        static final long WAITERS_OFFSET;
        static final long VALUE_OFFSET;
        static final long WAITER_THREAD_OFFSET;
        static final long WAITER_NEXT_OFFSET;

        private UnsafeAtomicHelper() {
            super(null);
        }

        @Override
        void putThread(Waiter waiter, Thread thread2) {
            UNSAFE.putObject((Object)waiter, WAITER_THREAD_OFFSET, (Object)thread2);
        }

        @Override
        void putNext(Waiter waiter, Waiter waiter2) {
            UNSAFE.putObject((Object)waiter, WAITER_NEXT_OFFSET, (Object)waiter2);
        }

        @Override
        boolean casWaiters(AbstractFuture<?> abstractFuture, Waiter waiter, Waiter waiter2) {
            return UNSAFE.compareAndSwapObject(abstractFuture, WAITERS_OFFSET, waiter, waiter2);
        }

        @Override
        boolean casListeners(AbstractFuture<?> abstractFuture, Listener listener, Listener listener2) {
            return UNSAFE.compareAndSwapObject(abstractFuture, LISTENERS_OFFSET, listener, listener2);
        }

        @Override
        boolean casValue(AbstractFuture<?> abstractFuture, Object object, Object object2) {
            return UNSAFE.compareAndSwapObject(abstractFuture, VALUE_OFFSET, object, object2);
        }

        UnsafeAtomicHelper(1 var1_1) {
            this();
        }

        static {
            Unsafe unsafe = null;
            try {
                unsafe = Unsafe.getUnsafe();
            } catch (SecurityException securityException) {
                try {
                    unsafe = AccessController.doPrivileged(new PrivilegedExceptionAction<Unsafe>(){

                        @Override
                        public Unsafe run() throws Exception {
                            Class<Unsafe> clazz = Unsafe.class;
                            for (Field field : clazz.getDeclaredFields()) {
                                field.setAccessible(false);
                                Object object = field.get(null);
                                if (!clazz.isInstance(object)) continue;
                                return (Unsafe)clazz.cast(object);
                            }
                            throw new NoSuchFieldError("the Unsafe");
                        }

                        @Override
                        public Object run() throws Exception {
                            return this.run();
                        }
                    });
                } catch (PrivilegedActionException privilegedActionException) {
                    throw new RuntimeException("Could not initialize intrinsics", privilegedActionException.getCause());
                }
            }
            try {
                Class<AbstractFuture> clazz = AbstractFuture.class;
                WAITERS_OFFSET = unsafe.objectFieldOffset(clazz.getDeclaredField("waiters"));
                LISTENERS_OFFSET = unsafe.objectFieldOffset(clazz.getDeclaredField("listeners"));
                VALUE_OFFSET = unsafe.objectFieldOffset(clazz.getDeclaredField("value"));
                WAITER_THREAD_OFFSET = unsafe.objectFieldOffset(Waiter.class.getDeclaredField("thread"));
                WAITER_NEXT_OFFSET = unsafe.objectFieldOffset(Waiter.class.getDeclaredField("next"));
                UNSAFE = unsafe;
            } catch (Exception exception) {
                Throwables.throwIfUnchecked(exception);
                throw new RuntimeException(exception);
            }
        }
    }

    private static abstract class AtomicHelper {
        private AtomicHelper() {
        }

        abstract void putThread(Waiter var1, Thread var2);

        abstract void putNext(Waiter var1, Waiter var2);

        abstract boolean casWaiters(AbstractFuture<?> var1, Waiter var2, Waiter var3);

        abstract boolean casListeners(AbstractFuture<?> var1, Listener var2, Listener var3);

        abstract boolean casValue(AbstractFuture<?> var1, Object var2, Object var3);

        AtomicHelper(1 var1_1) {
            this();
        }
    }

    private static final class SetFuture<V>
    implements Runnable {
        final AbstractFuture<V> owner;
        final ListenableFuture<? extends V> future;

        SetFuture(AbstractFuture<V> abstractFuture, ListenableFuture<? extends V> listenableFuture) {
            this.owner = abstractFuture;
            this.future = listenableFuture;
        }

        @Override
        public void run() {
            if (AbstractFuture.access$300(this.owner) != this) {
                return;
            }
            Object object = AbstractFuture.access$400(this.future);
            if (AbstractFuture.access$200().casValue(this.owner, this, object)) {
                AbstractFuture.access$500(this.owner);
            }
        }
    }

    private static final class Cancellation {
        final boolean wasInterrupted;
        @Nullable
        final Throwable cause;

        Cancellation(boolean bl, @Nullable Throwable throwable) {
            this.wasInterrupted = bl;
            this.cause = throwable;
        }
    }

    private static final class Failure {
        static final Failure FALLBACK_INSTANCE = new Failure(new Throwable("Failure occurred while trying to finish a future."){

            @Override
            public synchronized Throwable fillInStackTrace() {
                return this;
            }
        });
        final Throwable exception;

        Failure(Throwable throwable) {
            this.exception = Preconditions.checkNotNull(throwable);
        }
    }

    private static final class Listener {
        static final Listener TOMBSTONE = new Listener(null, null);
        final Runnable task;
        final Executor executor;
        @Nullable
        Listener next;

        Listener(Runnable runnable, Executor executor) {
            this.task = runnable;
            this.executor = executor;
        }
    }

    private static final class Waiter {
        static final Waiter TOMBSTONE = new Waiter(false);
        @Nullable
        volatile Thread thread;
        @Nullable
        volatile Waiter next;

        Waiter(boolean bl) {
        }

        Waiter() {
            AbstractFuture.access$200().putThread(this, Thread.currentThread());
        }

        void setNext(Waiter waiter) {
            AbstractFuture.access$200().putNext(this, waiter);
        }

        void unpark() {
            Thread thread2 = this.thread;
            if (thread2 != null) {
                this.thread = null;
                LockSupport.unpark(thread2);
            }
        }
    }

    static abstract class TrustedFuture<V>
    extends AbstractFuture<V> {
        TrustedFuture() {
        }

        @Override
        @CanIgnoreReturnValue
        public final V get() throws InterruptedException, ExecutionException {
            return super.get();
        }

        @Override
        @CanIgnoreReturnValue
        public final V get(long l, TimeUnit timeUnit) throws InterruptedException, ExecutionException, TimeoutException {
            return super.get(l, timeUnit);
        }

        @Override
        public final boolean isDone() {
            return super.isDone();
        }

        @Override
        public final boolean isCancelled() {
            return super.isCancelled();
        }

        @Override
        public final void addListener(Runnable runnable, Executor executor) {
            super.addListener(runnable, executor);
        }

        @Override
        @CanIgnoreReturnValue
        public final boolean cancel(boolean bl) {
            return super.cancel(bl);
        }
    }
}

