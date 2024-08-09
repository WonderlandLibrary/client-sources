/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.util;

import io.netty.util.NettyRuntime;
import io.netty.util.concurrent.FastThreadLocal;
import io.netty.util.internal.MathUtil;
import io.netty.util.internal.ObjectCleaner;
import io.netty.util.internal.SystemPropertyUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class Recycler<T> {
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(Recycler.class);
    private static final Handle NOOP_HANDLE = new Handle(){

        public void recycle(Object object) {
        }
    };
    private static final AtomicInteger ID_GENERATOR = new AtomicInteger(Integer.MIN_VALUE);
    private static final int OWN_THREAD_ID = ID_GENERATOR.getAndIncrement();
    private static final int DEFAULT_INITIAL_MAX_CAPACITY_PER_THREAD = 4096;
    private static final int DEFAULT_MAX_CAPACITY_PER_THREAD;
    private static final int INITIAL_CAPACITY;
    private static final int MAX_SHARED_CAPACITY_FACTOR;
    private static final int MAX_DELAYED_QUEUES_PER_THREAD;
    private static final int LINK_CAPACITY;
    private static final int RATIO;
    private final int maxCapacityPerThread;
    private final int maxSharedCapacityFactor;
    private final int ratioMask;
    private final int maxDelayedQueuesPerThread;
    private final FastThreadLocal<Stack<T>> threadLocal = new FastThreadLocal<Stack<T>>(this){
        final Recycler this$0;
        {
            this.this$0 = recycler;
        }

        @Override
        protected Stack<T> initialValue() {
            return new Stack(this.this$0, Thread.currentThread(), Recycler.access$000(this.this$0), Recycler.access$100(this.this$0), Recycler.access$200(this.this$0), Recycler.access$300(this.this$0));
        }

        @Override
        protected void onRemoval(Stack<T> stack) {
            if (stack.threadRef.get() == Thread.currentThread() && Recycler.access$400().isSet()) {
                ((Map)Recycler.access$400().get()).remove(stack);
            }
        }

        @Override
        protected void onRemoval(Object object) throws Exception {
            this.onRemoval((Stack)object);
        }

        @Override
        protected Object initialValue() throws Exception {
            return this.initialValue();
        }
    };
    private static final FastThreadLocal<Map<Stack<?>, WeakOrderQueue>> DELAYED_RECYCLED;

    protected Recycler() {
        this(DEFAULT_MAX_CAPACITY_PER_THREAD);
    }

    protected Recycler(int n) {
        this(n, MAX_SHARED_CAPACITY_FACTOR);
    }

    protected Recycler(int n, int n2) {
        this(n, n2, RATIO, MAX_DELAYED_QUEUES_PER_THREAD);
    }

    protected Recycler(int n, int n2, int n3, int n4) {
        this.ratioMask = MathUtil.safeFindNextPositivePowerOfTwo(n3) - 1;
        if (n <= 0) {
            this.maxCapacityPerThread = 0;
            this.maxSharedCapacityFactor = 1;
            this.maxDelayedQueuesPerThread = 0;
        } else {
            this.maxCapacityPerThread = n;
            this.maxSharedCapacityFactor = Math.max(1, n2);
            this.maxDelayedQueuesPerThread = Math.max(0, n4);
        }
    }

    public final T get() {
        if (this.maxCapacityPerThread == 0) {
            return this.newObject(NOOP_HANDLE);
        }
        Stack<T> stack = this.threadLocal.get();
        DefaultHandle<T> defaultHandle = stack.pop();
        if (defaultHandle == null) {
            defaultHandle = stack.newHandle();
            DefaultHandle.access$502(defaultHandle, this.newObject(defaultHandle));
        }
        return (T)DefaultHandle.access$500(defaultHandle);
    }

    @Deprecated
    public final boolean recycle(T t, Handle<T> handle) {
        if (handle == NOOP_HANDLE) {
            return true;
        }
        DefaultHandle defaultHandle = (DefaultHandle)handle;
        if (DefaultHandle.access$600((DefaultHandle)defaultHandle).parent != this) {
            return true;
        }
        defaultHandle.recycle((Object)t);
        return false;
    }

    final int threadLocalCapacity() {
        return Stack.access$700(this.threadLocal.get()).length;
    }

    final int threadLocalSize() {
        return Stack.access$800(this.threadLocal.get());
    }

    protected abstract T newObject(Handle<T> var1);

    static int access$000(Recycler recycler) {
        return recycler.maxCapacityPerThread;
    }

    static int access$100(Recycler recycler) {
        return recycler.maxSharedCapacityFactor;
    }

    static int access$200(Recycler recycler) {
        return recycler.ratioMask;
    }

    static int access$300(Recycler recycler) {
        return recycler.maxDelayedQueuesPerThread;
    }

    static FastThreadLocal access$400() {
        return DELAYED_RECYCLED;
    }

    static int access$900() {
        return LINK_CAPACITY;
    }

    static AtomicInteger access$1000() {
        return ID_GENERATOR;
    }

    static int access$1500() {
        return INITIAL_CAPACITY;
    }

    static int access$1900() {
        return OWN_THREAD_ID;
    }

    static {
        int n = SystemPropertyUtil.getInt("io.netty.recycler.maxCapacityPerThread", SystemPropertyUtil.getInt("io.netty.recycler.maxCapacity", 4096));
        if (n < 0) {
            n = 4096;
        }
        DEFAULT_MAX_CAPACITY_PER_THREAD = n;
        MAX_SHARED_CAPACITY_FACTOR = Math.max(2, SystemPropertyUtil.getInt("io.netty.recycler.maxSharedCapacityFactor", 2));
        MAX_DELAYED_QUEUES_PER_THREAD = Math.max(0, SystemPropertyUtil.getInt("io.netty.recycler.maxDelayedQueuesPerThread", NettyRuntime.availableProcessors() * 2));
        LINK_CAPACITY = MathUtil.safeFindNextPositivePowerOfTwo(Math.max(SystemPropertyUtil.getInt("io.netty.recycler.linkCapacity", 16), 16));
        RATIO = MathUtil.safeFindNextPositivePowerOfTwo(SystemPropertyUtil.getInt("io.netty.recycler.ratio", 8));
        if (logger.isDebugEnabled()) {
            if (DEFAULT_MAX_CAPACITY_PER_THREAD == 0) {
                logger.debug("-Dio.netty.recycler.maxCapacityPerThread: disabled");
                logger.debug("-Dio.netty.recycler.maxSharedCapacityFactor: disabled");
                logger.debug("-Dio.netty.recycler.linkCapacity: disabled");
                logger.debug("-Dio.netty.recycler.ratio: disabled");
            } else {
                logger.debug("-Dio.netty.recycler.maxCapacityPerThread: {}", (Object)DEFAULT_MAX_CAPACITY_PER_THREAD);
                logger.debug("-Dio.netty.recycler.maxSharedCapacityFactor: {}", (Object)MAX_SHARED_CAPACITY_FACTOR);
                logger.debug("-Dio.netty.recycler.linkCapacity: {}", (Object)LINK_CAPACITY);
                logger.debug("-Dio.netty.recycler.ratio: {}", (Object)RATIO);
            }
        }
        INITIAL_CAPACITY = Math.min(DEFAULT_MAX_CAPACITY_PER_THREAD, 256);
        DELAYED_RECYCLED = new FastThreadLocal<Map<Stack<?>, WeakOrderQueue>>(){

            @Override
            protected Map<Stack<?>, WeakOrderQueue> initialValue() {
                return new WeakHashMap();
            }

            @Override
            protected Object initialValue() throws Exception {
                return this.initialValue();
            }
        };
    }

    static final class Stack<T> {
        final Recycler<T> parent;
        final WeakReference<Thread> threadRef;
        final AtomicInteger availableSharedCapacity;
        final int maxDelayedQueues;
        private final int maxCapacity;
        private final int ratioMask;
        private DefaultHandle<?>[] elements;
        private int size;
        private int handleRecycleCount = -1;
        private WeakOrderQueue cursor;
        private WeakOrderQueue prev;
        private volatile WeakOrderQueue head;

        Stack(Recycler<T> recycler, Thread thread2, int n, int n2, int n3, int n4) {
            this.parent = recycler;
            this.threadRef = new WeakReference<Thread>(thread2);
            this.maxCapacity = n;
            this.availableSharedCapacity = new AtomicInteger(Math.max(n / n2, Recycler.access$900()));
            this.elements = new DefaultHandle[Math.min(Recycler.access$1500(), n)];
            this.ratioMask = n3;
            this.maxDelayedQueues = n4;
        }

        synchronized void setHead(WeakOrderQueue weakOrderQueue) {
            WeakOrderQueue.access$1600(weakOrderQueue, this.head);
            this.head = weakOrderQueue;
        }

        int increaseCapacity(int n) {
            int n2 = this.elements.length;
            int n3 = this.maxCapacity;
            while ((n2 <<= 1) < n && n2 < n3) {
            }
            if ((n2 = Math.min(n2, n3)) != this.elements.length) {
                this.elements = Arrays.copyOf(this.elements, n2);
            }
            return n2;
        }

        DefaultHandle<T> pop() {
            int n = this.size;
            if (n == 0) {
                if (!this.scavenge()) {
                    return null;
                }
                n = this.size;
            }
            DefaultHandle<?> defaultHandle = this.elements[--n];
            this.elements[n] = null;
            if (DefaultHandle.access$1100(defaultHandle) != DefaultHandle.access$1400(defaultHandle)) {
                throw new IllegalStateException("recycled multiple times");
            }
            DefaultHandle.access$1402(defaultHandle, 0);
            DefaultHandle.access$1102(defaultHandle, 0);
            this.size = n;
            return defaultHandle;
        }

        boolean scavenge() {
            if (this.scavengeSome()) {
                return false;
            }
            this.prev = null;
            this.cursor = this.head;
            return true;
        }

        boolean scavengeSome() {
            WeakOrderQueue weakOrderQueue;
            WeakOrderQueue weakOrderQueue2;
            WeakOrderQueue weakOrderQueue3 = this.cursor;
            if (weakOrderQueue3 == null) {
                weakOrderQueue2 = null;
                weakOrderQueue3 = this.head;
                if (weakOrderQueue3 == null) {
                    return true;
                }
            } else {
                weakOrderQueue2 = this.prev;
            }
            boolean bl = false;
            do {
                if (weakOrderQueue3.transfer(this)) {
                    bl = true;
                    break;
                }
                weakOrderQueue = WeakOrderQueue.access$1700(weakOrderQueue3);
                if (WeakOrderQueue.access$1800(weakOrderQueue3).get() == null) {
                    if (weakOrderQueue3.hasFinalData()) {
                        while (weakOrderQueue3.transfer(this)) {
                            bl = true;
                        }
                    }
                    if (weakOrderQueue2 == null) continue;
                    WeakOrderQueue.access$1600(weakOrderQueue2, weakOrderQueue);
                    continue;
                }
                weakOrderQueue2 = weakOrderQueue3;
            } while ((weakOrderQueue3 = weakOrderQueue) != null && !bl);
            this.prev = weakOrderQueue2;
            this.cursor = weakOrderQueue3;
            return bl;
        }

        void push(DefaultHandle<?> defaultHandle) {
            Thread thread2 = Thread.currentThread();
            if (this.threadRef.get() == thread2) {
                this.pushNow(defaultHandle);
            } else {
                this.pushLater(defaultHandle, thread2);
            }
        }

        private void pushNow(DefaultHandle<?> defaultHandle) {
            if ((DefaultHandle.access$1400(defaultHandle) | DefaultHandle.access$1100(defaultHandle)) != 0) {
                throw new IllegalStateException("recycled already");
            }
            DefaultHandle.access$1402(defaultHandle, DefaultHandle.access$1102(defaultHandle, Recycler.access$1900()));
            int n = this.size;
            if (n >= this.maxCapacity || this.dropHandle(defaultHandle)) {
                return;
            }
            if (n == this.elements.length) {
                this.elements = Arrays.copyOf(this.elements, Math.min(n << 1, this.maxCapacity));
            }
            this.elements[n] = defaultHandle;
            this.size = n + 1;
        }

        private void pushLater(DefaultHandle<?> defaultHandle, Thread thread2) {
            Map map = (Map)Recycler.access$400().get();
            WeakOrderQueue weakOrderQueue = (WeakOrderQueue)map.get(this);
            if (weakOrderQueue == null) {
                if (map.size() >= this.maxDelayedQueues) {
                    map.put(this, WeakOrderQueue.DUMMY);
                    return;
                }
                weakOrderQueue = WeakOrderQueue.allocate(this, thread2);
                if (weakOrderQueue == null) {
                    return;
                }
                map.put(this, weakOrderQueue);
            } else if (weakOrderQueue == WeakOrderQueue.DUMMY) {
                return;
            }
            weakOrderQueue.add(defaultHandle);
        }

        boolean dropHandle(DefaultHandle<?> defaultHandle) {
            if (!defaultHandle.hasBeenRecycled) {
                if ((++this.handleRecycleCount & this.ratioMask) != 0) {
                    return false;
                }
                defaultHandle.hasBeenRecycled = true;
            }
            return true;
        }

        DefaultHandle<T> newHandle() {
            return new DefaultHandle(this);
        }

        static DefaultHandle[] access$700(Stack stack) {
            return stack.elements;
        }

        static int access$800(Stack stack) {
            return stack.size;
        }

        static int access$802(Stack stack, int n) {
            stack.size = n;
            return stack.size;
        }
    }

    private static final class WeakOrderQueue {
        static final WeakOrderQueue DUMMY;
        private final Head head;
        private Link tail;
        private WeakOrderQueue next;
        private final WeakReference<Thread> owner;
        private final int id = Recycler.access$1000().getAndIncrement();
        static final boolean $assertionsDisabled;

        private WeakOrderQueue() {
            this.owner = null;
            this.head = new Head(null);
        }

        private WeakOrderQueue(Stack<?> stack, Thread thread2) {
            this.tail = new Link();
            this.head = new Head(stack.availableSharedCapacity);
            this.head.link = this.tail;
            this.owner = new WeakReference<Thread>(thread2);
        }

        static WeakOrderQueue newQueue(Stack<?> stack, Thread thread2) {
            WeakOrderQueue weakOrderQueue = new WeakOrderQueue(stack, thread2);
            stack.setHead(weakOrderQueue);
            Head head = weakOrderQueue.head;
            ObjectCleaner.register(weakOrderQueue, head);
            return weakOrderQueue;
        }

        private void setNext(WeakOrderQueue weakOrderQueue) {
            if (!$assertionsDisabled && weakOrderQueue == this) {
                throw new AssertionError();
            }
            this.next = weakOrderQueue;
        }

        static WeakOrderQueue allocate(Stack<?> stack, Thread thread2) {
            return Head.reserveSpace(stack.availableSharedCapacity, Recycler.access$900()) ? WeakOrderQueue.newQueue(stack, thread2) : null;
        }

        void add(DefaultHandle<?> defaultHandle) {
            DefaultHandle.access$1102(defaultHandle, this.id);
            Link link = this.tail;
            int n = link.get();
            if (n == Recycler.access$900()) {
                if (!this.head.reserveSpace(Recycler.access$900())) {
                    return;
                }
                link = link.next = new Link();
                this.tail = link.next;
                n = link.get();
            }
            Link.access$1200((Link)link)[n] = defaultHandle;
            DefaultHandle.access$602(defaultHandle, null);
            link.lazySet(n + 1);
        }

        boolean hasFinalData() {
            return Link.access$1300(this.tail) != this.tail.get();
        }

        boolean transfer(Stack<?> stack) {
            Link link = this.head.link;
            if (link == null) {
                return true;
            }
            if (Link.access$1300(link) == Recycler.access$900()) {
                if (link.next == null) {
                    return true;
                }
                this.head.link = link = link.next;
            }
            int n = Link.access$1300(link);
            int n2 = link.get();
            int n3 = n2 - n;
            if (n3 == 0) {
                return true;
            }
            int n4 = Stack.access$800(stack);
            int n5 = n4 + n3;
            if (n5 > Stack.access$700(stack).length) {
                int n6 = stack.increaseCapacity(n5);
                n2 = Math.min(n + n6 - n4, n2);
            }
            if (n != n2) {
                DefaultHandle[] defaultHandleArray = Link.access$1200(link);
                DefaultHandle[] defaultHandleArray2 = Stack.access$700(stack);
                int n7 = n4;
                for (int i = n; i < n2; ++i) {
                    DefaultHandle defaultHandle = defaultHandleArray[i];
                    if (DefaultHandle.access$1400(defaultHandle) == 0) {
                        DefaultHandle.access$1402(defaultHandle, DefaultHandle.access$1100(defaultHandle));
                    } else if (DefaultHandle.access$1400(defaultHandle) != DefaultHandle.access$1100(defaultHandle)) {
                        throw new IllegalStateException("recycled already");
                    }
                    defaultHandleArray[i] = null;
                    if (stack.dropHandle(defaultHandle)) continue;
                    DefaultHandle.access$602(defaultHandle, stack);
                    defaultHandleArray2[n7++] = defaultHandle;
                }
                if (n2 == Recycler.access$900() && link.next != null) {
                    this.head.reclaimSpace(Recycler.access$900());
                    this.head.link = link.next;
                }
                Link.access$1302(link, n2);
                if (Stack.access$800(stack) == n7) {
                    return true;
                }
                Stack.access$802(stack, n7);
                return false;
            }
            return true;
        }

        static void access$1600(WeakOrderQueue weakOrderQueue, WeakOrderQueue weakOrderQueue2) {
            weakOrderQueue.setNext(weakOrderQueue2);
        }

        static WeakOrderQueue access$1700(WeakOrderQueue weakOrderQueue) {
            return weakOrderQueue.next;
        }

        static WeakReference access$1800(WeakOrderQueue weakOrderQueue) {
            return weakOrderQueue.owner;
        }

        static {
            $assertionsDisabled = !Recycler.class.desiredAssertionStatus();
            DUMMY = new WeakOrderQueue();
        }

        static final class Head
        implements Runnable {
            private final AtomicInteger availableSharedCapacity;
            Link link;
            static final boolean $assertionsDisabled = !Recycler.class.desiredAssertionStatus();

            Head(AtomicInteger atomicInteger) {
                this.availableSharedCapacity = atomicInteger;
            }

            @Override
            public void run() {
                Link link = this.link;
                while (link != null) {
                    this.reclaimSpace(Recycler.access$900());
                    link = link.next;
                }
            }

            void reclaimSpace(int n) {
                if (!$assertionsDisabled && n < 0) {
                    throw new AssertionError();
                }
                this.availableSharedCapacity.addAndGet(n);
            }

            boolean reserveSpace(int n) {
                return Head.reserveSpace(this.availableSharedCapacity, n);
            }

            static boolean reserveSpace(AtomicInteger atomicInteger, int n) {
                int n2;
                if (!$assertionsDisabled && n < 0) {
                    throw new AssertionError();
                }
                do {
                    if ((n2 = atomicInteger.get()) >= n) continue;
                    return true;
                } while (!atomicInteger.compareAndSet(n2, n2 - n));
                return false;
            }
        }

        static final class Link
        extends AtomicInteger {
            private final DefaultHandle<?>[] elements = new DefaultHandle[Recycler.access$900()];
            private int readIndex;
            Link next;

            Link() {
            }

            static DefaultHandle[] access$1200(Link link) {
                return link.elements;
            }

            static int access$1300(Link link) {
                return link.readIndex;
            }

            static int access$1302(Link link, int n) {
                link.readIndex = n;
                return link.readIndex;
            }
        }
    }

    static final class DefaultHandle<T>
    implements Handle<T> {
        private int lastRecycledId;
        private int recycleId;
        boolean hasBeenRecycled;
        private Stack<?> stack;
        private Object value;

        DefaultHandle(Stack<?> stack) {
            this.stack = stack;
        }

        @Override
        public void recycle(Object object) {
            if (object != this.value) {
                throw new IllegalArgumentException("object does not belong to handle");
            }
            this.stack.push(this);
        }

        static Object access$502(DefaultHandle defaultHandle, Object object) {
            defaultHandle.value = object;
            return defaultHandle.value;
        }

        static Object access$500(DefaultHandle defaultHandle) {
            return defaultHandle.value;
        }

        static Stack access$600(DefaultHandle defaultHandle) {
            return defaultHandle.stack;
        }

        static int access$1102(DefaultHandle defaultHandle, int n) {
            defaultHandle.lastRecycledId = n;
            return defaultHandle.lastRecycledId;
        }

        static Stack access$602(DefaultHandle defaultHandle, Stack stack) {
            defaultHandle.stack = stack;
            return defaultHandle.stack;
        }

        static int access$1400(DefaultHandle defaultHandle) {
            return defaultHandle.recycleId;
        }

        static int access$1402(DefaultHandle defaultHandle, int n) {
            defaultHandle.recycleId = n;
            return defaultHandle.recycleId;
        }

        static int access$1100(DefaultHandle defaultHandle) {
            return defaultHandle.lastRecycledId;
        }
    }

    public static interface Handle<T> {
        public void recycle(T var1);
    }
}

