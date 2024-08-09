/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.pool;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.apache.http.annotation.Contract;
import org.apache.http.annotation.ThreadingBehavior;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.pool.ConnFactory;
import org.apache.http.pool.ConnPool;
import org.apache.http.pool.ConnPoolControl;
import org.apache.http.pool.PoolEntry;
import org.apache.http.pool.PoolEntryCallback;
import org.apache.http.pool.PoolStats;
import org.apache.http.pool.RouteSpecificPool;
import org.apache.http.util.Args;
import org.apache.http.util.Asserts;

@Contract(threading=ThreadingBehavior.SAFE_CONDITIONAL)
public abstract class AbstractConnPool<T, C, E extends PoolEntry<T, C>>
implements ConnPool<T, E>,
ConnPoolControl<T> {
    private final Lock lock;
    private final Condition condition;
    private final ConnFactory<T, C> connFactory;
    private final Map<T, RouteSpecificPool<T, C, E>> routeToPool;
    private final Set<E> leased;
    private final LinkedList<E> available;
    private final LinkedList<Future<E>> pending;
    private final Map<T, Integer> maxPerRoute;
    private volatile boolean isShutDown;
    private volatile int defaultMaxPerRoute;
    private volatile int maxTotal;
    private volatile int validateAfterInactivity;

    public AbstractConnPool(ConnFactory<T, C> connFactory, int n, int n2) {
        this.connFactory = Args.notNull(connFactory, "Connection factory");
        this.defaultMaxPerRoute = Args.positive(n, "Max per route value");
        this.maxTotal = Args.positive(n2, "Max total value");
        this.lock = new ReentrantLock();
        this.condition = this.lock.newCondition();
        this.routeToPool = new HashMap<T, RouteSpecificPool<T, C, E>>();
        this.leased = new HashSet();
        this.available = new LinkedList();
        this.pending = new LinkedList();
        this.maxPerRoute = new HashMap<T, Integer>();
    }

    protected abstract E createEntry(T var1, C var2);

    protected void onLease(E e) {
    }

    protected void onRelease(E e) {
    }

    protected void onReuse(E e) {
    }

    protected boolean validate(E e) {
        return false;
    }

    public boolean isShutdown() {
        return this.isShutDown;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void shutdown() throws IOException {
        if (this.isShutDown) {
            return;
        }
        this.isShutDown = true;
        this.lock.lock();
        try {
            for (PoolEntry object : this.available) {
                object.close();
            }
            for (PoolEntry poolEntry : this.leased) {
                poolEntry.close();
            }
            for (RouteSpecificPool routeSpecificPool : this.routeToPool.values()) {
                routeSpecificPool.shutdown();
            }
            this.routeToPool.clear();
            this.leased.clear();
            this.available.clear();
        } finally {
            this.lock.unlock();
        }
    }

    private RouteSpecificPool<T, C, E> getPool(T t) {
        RouteSpecificPool routeSpecificPool = this.routeToPool.get(t);
        if (routeSpecificPool == null) {
            routeSpecificPool = new RouteSpecificPool<T, C, E>(this, t, t){
                final Object val$route;
                final AbstractConnPool this$0;
                {
                    this.this$0 = abstractConnPool;
                    this.val$route = object2;
                    super(object);
                }

                @Override
                protected E createEntry(C c) {
                    return this.this$0.createEntry(this.val$route, c);
                }
            };
            this.routeToPool.put(t, routeSpecificPool);
        }
        return routeSpecificPool;
    }

    private static Exception operationAborted() {
        return new CancellationException("Operation aborted");
    }

    @Override
    public Future<E> lease(T t, Object object, FutureCallback<E> futureCallback) {
        Args.notNull(t, "Route");
        Asserts.check(!this.isShutDown, "Connection pool shut down");
        return new Future<E>(this, futureCallback, t, object){
            private final AtomicBoolean cancelled;
            private final AtomicBoolean done;
            private final AtomicReference<E> entryRef;
            final FutureCallback val$callback;
            final Object val$route;
            final Object val$state;
            final AbstractConnPool this$0;
            {
                this.this$0 = abstractConnPool;
                this.val$callback = futureCallback;
                this.val$route = object;
                this.val$state = object2;
                this.cancelled = new AtomicBoolean(false);
                this.done = new AtomicBoolean(false);
                this.entryRef = new AtomicReference<Object>(null);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            @Override
            public boolean cancel(boolean bl) {
                if (this.done.compareAndSet(false, false)) {
                    this.cancelled.set(false);
                    AbstractConnPool.access$000(this.this$0).lock();
                    try {
                        AbstractConnPool.access$100(this.this$0).signalAll();
                    } finally {
                        AbstractConnPool.access$000(this.this$0).unlock();
                    }
                    if (this.val$callback != null) {
                        this.val$callback.cancelled();
                    }
                    return false;
                }
                return true;
            }

            @Override
            public boolean isCancelled() {
                return this.cancelled.get();
            }

            @Override
            public boolean isDone() {
                return this.done.get();
            }

            @Override
            public E get() throws InterruptedException, ExecutionException {
                try {
                    return this.get(0L, TimeUnit.MILLISECONDS);
                } catch (TimeoutException timeoutException) {
                    throw new ExecutionException(timeoutException);
                }
            }

            /*
             * Exception decompiling
             */
            @Override
            public E get(long var1_1, TimeUnit var3_2) throws InterruptedException, ExecutionException, TimeoutException {
                /*
                 * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
                 * 
                 * org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [5[UNCONDITIONALDOLOOP]], but top level block is 1[TRYBLOCK]
                 *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:435)
                 *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:484)
                 *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:736)
                 *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:850)
                 *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
                 *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
                 *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
                 *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:538)
                 *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
                 *     at org.benf.cfr.reader.entities.ClassFile.analyseInnerClassesPass1(ClassFile.java:923)
                 *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1035)
                 *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
                 *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
                 *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
                 *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
                 *     at org.benf.cfr.reader.Main.main(Main.java:54)
                 *     at async.DecompilerRunnable.cfrDecompilation(DecompilerRunnable.java:348)
                 *     at async.DecompilerRunnable.call(DecompilerRunnable.java:309)
                 *     at async.DecompilerRunnable.call(DecompilerRunnable.java:31)
                 *     at java.util.concurrent.FutureTask.run(FutureTask.java:266)
                 *     at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1149)
                 *     at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624)
                 *     at java.lang.Thread.run(Thread.java:750)
                 */
                throw new IllegalStateException("Decompilation failed");
            }

            @Override
            public Object get(long l, TimeUnit timeUnit) throws InterruptedException, ExecutionException, TimeoutException {
                return this.get(l, timeUnit);
            }

            @Override
            public Object get() throws InterruptedException, ExecutionException {
                return this.get();
            }
        };
    }

    public Future<E> lease(T t, Object object) {
        return this.lease(t, object, null);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private E getPoolEntryBlocking(T t, Object object, long l, TimeUnit timeUnit, Future<E> future) throws IOException, InterruptedException, ExecutionException, TimeoutException {
        Date date = null;
        if (l > 0L) {
            date = new Date(System.currentTimeMillis() + timeUnit.toMillis(l));
        }
        this.lock.lock();
        try {
            int n;
            do {
                int n2;
                E e;
                Asserts.check(!this.isShutDown, "Connection pool shut down");
                if (future.isCancelled()) {
                    throw new ExecutionException(AbstractConnPool.operationAborted());
                }
                RouteSpecificPool routeSpecificPool = this.getPool(t);
                while ((e = routeSpecificPool.getFree(object)) != null) {
                    if (((PoolEntry)e).isExpired(System.currentTimeMillis())) {
                        ((PoolEntry)e).close();
                    }
                    if (!((PoolEntry)e).isClosed()) break;
                    this.available.remove(e);
                    routeSpecificPool.free(e, true);
                }
                if (e != null) {
                    this.available.remove(e);
                    this.leased.add(e);
                    this.onReuse(e);
                    E e2 = e;
                    return e2;
                }
                int n3 = this.getMax(t);
                int n4 = Math.max(0, routeSpecificPool.getAllocatedCount() + 1 - n3);
                if (n4 > 0) {
                    E e3;
                    for (n = 0; n < n4 && (e3 = routeSpecificPool.getLastUsed()) != null; ++n) {
                        ((PoolEntry)e3).close();
                        this.available.remove(e3);
                        routeSpecificPool.remove(e3);
                    }
                }
                if (routeSpecificPool.getAllocatedCount() < n3 && (n2 = Math.max(this.maxTotal - (n = this.leased.size()), 0)) > 0) {
                    RouteSpecificPool routeSpecificPool2;
                    PoolEntry poolEntry;
                    int n5 = this.available.size();
                    if (n5 > n2 - 1) {
                        poolEntry = (PoolEntry)this.available.removeLast();
                        poolEntry.close();
                        routeSpecificPool2 = this.getPool(poolEntry.getRoute());
                        routeSpecificPool2.remove(poolEntry);
                    }
                    poolEntry = this.connFactory.create(t);
                    e = routeSpecificPool.add(poolEntry);
                    this.leased.add(e);
                    routeSpecificPool2 = e;
                    return (E)routeSpecificPool2;
                }
                n = 0;
                try {
                    routeSpecificPool.queue(future);
                    this.pending.add(future);
                    if (date != null) {
                        n = this.condition.awaitUntil(date) ? 1 : 0;
                    } else {
                        this.condition.await();
                        n = 1;
                    }
                    if (future.isCancelled()) {
                        throw new ExecutionException(AbstractConnPool.operationAborted());
                    }
                } finally {
                    routeSpecificPool.unqueue(future);
                    this.pending.remove(future);
                }
            } while (n != 0 || date == null || date.getTime() > System.currentTimeMillis());
            throw new TimeoutException("Timeout waiting for connection");
        } finally {
            this.lock.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void release(E e, boolean bl) {
        this.lock.lock();
        try {
            if (this.leased.remove(e)) {
                RouteSpecificPool routeSpecificPool = this.getPool(((PoolEntry)e).getRoute());
                routeSpecificPool.free(e, bl);
                if (bl && !this.isShutDown) {
                    this.available.addFirst(e);
                } else {
                    ((PoolEntry)e).close();
                }
                this.onRelease(e);
                Future<E> future = routeSpecificPool.nextPending();
                if (future != null) {
                    this.pending.remove(future);
                } else {
                    future = this.pending.poll();
                }
                if (future != null) {
                    this.condition.signalAll();
                }
            }
        } finally {
            this.lock.unlock();
        }
    }

    private int getMax(T t) {
        Integer n = this.maxPerRoute.get(t);
        return n != null ? n : this.defaultMaxPerRoute;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void setMaxTotal(int n) {
        Args.positive(n, "Max value");
        this.lock.lock();
        try {
            this.maxTotal = n;
        } finally {
            this.lock.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public int getMaxTotal() {
        this.lock.lock();
        try {
            int n = this.maxTotal;
            return n;
        } finally {
            this.lock.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void setDefaultMaxPerRoute(int n) {
        Args.positive(n, "Max per route value");
        this.lock.lock();
        try {
            this.defaultMaxPerRoute = n;
        } finally {
            this.lock.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public int getDefaultMaxPerRoute() {
        this.lock.lock();
        try {
            int n = this.defaultMaxPerRoute;
            return n;
        } finally {
            this.lock.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void setMaxPerRoute(T t, int n) {
        Args.notNull(t, "Route");
        this.lock.lock();
        try {
            if (n > -1) {
                this.maxPerRoute.put(t, n);
            } else {
                this.maxPerRoute.remove(t);
            }
        } finally {
            this.lock.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public int getMaxPerRoute(T t) {
        Args.notNull(t, "Route");
        this.lock.lock();
        try {
            int n = this.getMax(t);
            return n;
        } finally {
            this.lock.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public PoolStats getTotalStats() {
        this.lock.lock();
        try {
            PoolStats poolStats = new PoolStats(this.leased.size(), this.pending.size(), this.available.size(), this.maxTotal);
            return poolStats;
        } finally {
            this.lock.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public PoolStats getStats(T t) {
        Args.notNull(t, "Route");
        this.lock.lock();
        try {
            RouteSpecificPool<T, C, E> routeSpecificPool = this.getPool(t);
            PoolStats poolStats = new PoolStats(routeSpecificPool.getLeasedCount(), routeSpecificPool.getPendingCount(), routeSpecificPool.getAvailableCount(), this.getMax(t));
            return poolStats;
        } finally {
            this.lock.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Set<T> getRoutes() {
        this.lock.lock();
        try {
            HashSet<T> hashSet = new HashSet<T>(this.routeToPool.keySet());
            return hashSet;
        } finally {
            this.lock.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void enumAvailable(PoolEntryCallback<T, C> poolEntryCallback) {
        this.lock.lock();
        try {
            Iterator iterator2 = this.available.iterator();
            while (iterator2.hasNext()) {
                PoolEntry poolEntry = (PoolEntry)iterator2.next();
                poolEntryCallback.process(poolEntry);
                if (!poolEntry.isClosed()) continue;
                RouteSpecificPool routeSpecificPool = this.getPool(poolEntry.getRoute());
                routeSpecificPool.remove(poolEntry);
                iterator2.remove();
            }
            this.purgePoolMap();
        } finally {
            this.lock.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void enumLeased(PoolEntryCallback<T, C> poolEntryCallback) {
        this.lock.lock();
        try {
            for (PoolEntry poolEntry : this.leased) {
                poolEntryCallback.process(poolEntry);
            }
        } finally {
            this.lock.unlock();
        }
    }

    private void purgePoolMap() {
        Iterator<Map.Entry<T, RouteSpecificPool<T, C, E>>> iterator2 = this.routeToPool.entrySet().iterator();
        while (iterator2.hasNext()) {
            Map.Entry<T, RouteSpecificPool<T, C, E>> entry = iterator2.next();
            RouteSpecificPool<T, C, E> routeSpecificPool = entry.getValue();
            if (routeSpecificPool.getPendingCount() + routeSpecificPool.getAllocatedCount() != 0) continue;
            iterator2.remove();
        }
    }

    public void closeIdle(long l, TimeUnit timeUnit) {
        Args.notNull(timeUnit, "Time unit");
        long l2 = timeUnit.toMillis(l);
        if (l2 < 0L) {
            l2 = 0L;
        }
        long l3 = System.currentTimeMillis() - l2;
        this.enumAvailable(new PoolEntryCallback<T, C>(this, l3){
            final long val$deadline;
            final AbstractConnPool this$0;
            {
                this.this$0 = abstractConnPool;
                this.val$deadline = l;
            }

            @Override
            public void process(PoolEntry<T, C> poolEntry) {
                if (poolEntry.getUpdated() <= this.val$deadline) {
                    poolEntry.close();
                }
            }
        });
    }

    public void closeExpired() {
        long l = System.currentTimeMillis();
        this.enumAvailable(new PoolEntryCallback<T, C>(this, l){
            final long val$now;
            final AbstractConnPool this$0;
            {
                this.this$0 = abstractConnPool;
                this.val$now = l;
            }

            @Override
            public void process(PoolEntry<T, C> poolEntry) {
                if (poolEntry.isExpired(this.val$now)) {
                    poolEntry.close();
                }
            }
        });
    }

    public int getValidateAfterInactivity() {
        return this.validateAfterInactivity;
    }

    public void setValidateAfterInactivity(int n) {
        this.validateAfterInactivity = n;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public String toString() {
        this.lock.lock();
        try {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("[leased: ");
            stringBuilder.append(this.leased);
            stringBuilder.append("][available: ");
            stringBuilder.append(this.available);
            stringBuilder.append("][pending: ");
            stringBuilder.append(this.pending);
            stringBuilder.append("]");
            String string = stringBuilder.toString();
            return string;
        } finally {
            this.lock.unlock();
        }
    }

    @Override
    public void release(Object object, boolean bl) {
        this.release((E)((PoolEntry)object), bl);
    }

    static Lock access$000(AbstractConnPool abstractConnPool) {
        return abstractConnPool.lock;
    }

    static Condition access$100(AbstractConnPool abstractConnPool) {
        return abstractConnPool.condition;
    }

    static Exception access$200() {
        return AbstractConnPool.operationAborted();
    }

    static PoolEntry access$300(AbstractConnPool abstractConnPool, Object object, Object object2, long l, TimeUnit timeUnit, Future future) throws IOException, InterruptedException, ExecutionException, TimeoutException {
        return abstractConnPool.getPoolEntryBlocking(object, object2, l, timeUnit, future);
    }

    static int access$400(AbstractConnPool abstractConnPool) {
        return abstractConnPool.validateAfterInactivity;
    }
}

