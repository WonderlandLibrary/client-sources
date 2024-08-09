/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.channel.nio;

import io.netty.channel.ChannelException;
import io.netty.channel.EventLoopException;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SelectStrategy;
import io.netty.channel.SingleThreadEventLoop;
import io.netty.channel.nio.AbstractNioChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.nio.NioTask;
import io.netty.channel.nio.SelectedSelectionKeySet;
import io.netty.channel.nio.SelectedSelectionKeySetSelector;
import io.netty.util.IntSupplier;
import io.netty.util.concurrent.RejectedExecutionHandler;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.ReflectionUtil;
import io.netty.util.internal.SystemPropertyUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.channels.CancelledKeyException;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.spi.AbstractSelector;
import java.nio.channels.spi.SelectorProvider;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public final class NioEventLoop
extends SingleThreadEventLoop {
    private static final InternalLogger logger;
    private static final int CLEANUP_INTERVAL = 256;
    private static final boolean DISABLE_KEYSET_OPTIMIZATION;
    private static final int MIN_PREMATURE_SELECTOR_RETURNS = 3;
    private static final int SELECTOR_AUTO_REBUILD_THRESHOLD;
    private final IntSupplier selectNowSupplier = new IntSupplier(this){
        final NioEventLoop this$0;
        {
            this.this$0 = nioEventLoop;
        }

        @Override
        public int get() throws Exception {
            return this.this$0.selectNow();
        }
    };
    private final Callable<Integer> pendingTasksCallable = new Callable<Integer>(this){
        final NioEventLoop this$0;
        {
            this.this$0 = nioEventLoop;
        }

        @Override
        public Integer call() throws Exception {
            return NioEventLoop.access$001(this.this$0);
        }

        @Override
        public Object call() throws Exception {
            return this.call();
        }
    };
    static final long MAX_SCHEDULED_DAYS = 1095L;
    private Selector selector;
    private Selector unwrappedSelector;
    private SelectedSelectionKeySet selectedKeys;
    private final SelectorProvider provider;
    private final AtomicBoolean wakenUp = new AtomicBoolean();
    private final SelectStrategy selectStrategy;
    private volatile int ioRatio = 50;
    private int cancelledKeys;
    private boolean needsToSelectAgain;

    NioEventLoop(NioEventLoopGroup nioEventLoopGroup, Executor executor, SelectorProvider selectorProvider, SelectStrategy selectStrategy, RejectedExecutionHandler rejectedExecutionHandler) {
        super((EventLoopGroup)nioEventLoopGroup, executor, false, DEFAULT_MAX_PENDING_TASKS, rejectedExecutionHandler);
        if (selectorProvider == null) {
            throw new NullPointerException("selectorProvider");
        }
        if (selectStrategy == null) {
            throw new NullPointerException("selectStrategy");
        }
        this.provider = selectorProvider;
        SelectorTuple selectorTuple = this.openSelector();
        this.selector = selectorTuple.selector;
        this.unwrappedSelector = selectorTuple.unwrappedSelector;
        this.selectStrategy = selectStrategy;
    }

    private SelectorTuple openSelector() {
        AbstractSelector abstractSelector;
        try {
            abstractSelector = this.provider.openSelector();
        } catch (IOException iOException) {
            throw new ChannelException("failed to open a new selector", iOException);
        }
        if (DISABLE_KEYSET_OPTIMIZATION) {
            return new SelectorTuple(abstractSelector);
        }
        SelectedSelectionKeySet selectedSelectionKeySet = new SelectedSelectionKeySet();
        Object object = AccessController.doPrivileged(new PrivilegedAction<Object>(this){
            final NioEventLoop this$0;
            {
                this.this$0 = nioEventLoop;
            }

            @Override
            public Object run() {
                try {
                    return Class.forName("sun.nio.ch.SelectorImpl", false, PlatformDependent.getSystemClassLoader());
                } catch (Throwable throwable) {
                    return throwable;
                }
            }
        });
        if (!(object instanceof Class) || !((Class)object).isAssignableFrom(abstractSelector.getClass())) {
            if (object instanceof Throwable) {
                Throwable throwable = (Throwable)object;
                logger.trace("failed to instrument a special java.util.Set into: {}", (Object)abstractSelector, (Object)throwable);
            }
            return new SelectorTuple(abstractSelector);
        }
        Class clazz = (Class)object;
        Object object2 = AccessController.doPrivileged(new PrivilegedAction<Object>(this, clazz, (Selector)abstractSelector, selectedSelectionKeySet){
            final Class val$selectorImplClass;
            final Selector val$unwrappedSelector;
            final SelectedSelectionKeySet val$selectedKeySet;
            final NioEventLoop this$0;
            {
                this.this$0 = nioEventLoop;
                this.val$selectorImplClass = clazz;
                this.val$unwrappedSelector = selector;
                this.val$selectedKeySet = selectedSelectionKeySet;
            }

            @Override
            public Object run() {
                try {
                    Field field = this.val$selectorImplClass.getDeclaredField("selectedKeys");
                    Field field2 = this.val$selectorImplClass.getDeclaredField("publicSelectedKeys");
                    Throwable throwable = ReflectionUtil.trySetAccessible(field, true);
                    if (throwable != null) {
                        return throwable;
                    }
                    throwable = ReflectionUtil.trySetAccessible(field2, true);
                    if (throwable != null) {
                        return throwable;
                    }
                    field.set(this.val$unwrappedSelector, this.val$selectedKeySet);
                    field2.set(this.val$unwrappedSelector, this.val$selectedKeySet);
                    return null;
                } catch (NoSuchFieldException noSuchFieldException) {
                    return noSuchFieldException;
                } catch (IllegalAccessException illegalAccessException) {
                    return illegalAccessException;
                }
            }
        });
        if (object2 instanceof Exception) {
            this.selectedKeys = null;
            Exception exception = (Exception)object2;
            logger.trace("failed to instrument a special java.util.Set into: {}", (Object)abstractSelector, (Object)exception);
            return new SelectorTuple(abstractSelector);
        }
        this.selectedKeys = selectedSelectionKeySet;
        logger.trace("instrumented a special java.util.Set into: {}", (Object)abstractSelector);
        return new SelectorTuple(abstractSelector, new SelectedSelectionKeySetSelector(abstractSelector, selectedSelectionKeySet));
    }

    public SelectorProvider selectorProvider() {
        return this.provider;
    }

    @Override
    protected Queue<Runnable> newTaskQueue(int n) {
        return n == Integer.MAX_VALUE ? PlatformDependent.newMpscQueue() : PlatformDependent.newMpscQueue(n);
    }

    @Override
    public int pendingTasks() {
        if (this.inEventLoop()) {
            return super.pendingTasks();
        }
        return (Integer)this.submit(this.pendingTasksCallable).syncUninterruptibly().getNow();
    }

    public void register(SelectableChannel selectableChannel, int n, NioTask<?> nioTask) {
        if (selectableChannel == null) {
            throw new NullPointerException("ch");
        }
        if (n == 0) {
            throw new IllegalArgumentException("interestOps must be non-zero.");
        }
        if ((n & ~selectableChannel.validOps()) != 0) {
            throw new IllegalArgumentException("invalid interestOps: " + n + "(validOps: " + selectableChannel.validOps() + ')');
        }
        if (nioTask == null) {
            throw new NullPointerException("task");
        }
        if (this.isShutdown()) {
            throw new IllegalStateException("event loop shut down");
        }
        try {
            selectableChannel.register(this.selector, n, nioTask);
        } catch (Exception exception) {
            throw new EventLoopException("failed to register a channel", exception);
        }
    }

    public int getIoRatio() {
        return this.ioRatio;
    }

    public void setIoRatio(int n) {
        if (n <= 0 || n > 100) {
            throw new IllegalArgumentException("ioRatio: " + n + " (expected: 0 < ioRatio <= 100)");
        }
        this.ioRatio = n;
    }

    public void rebuildSelector() {
        if (!this.inEventLoop()) {
            this.execute(new Runnable(this){
                final NioEventLoop this$0;
                {
                    this.this$0 = nioEventLoop;
                }

                @Override
                public void run() {
                    NioEventLoop.access$100(this.this$0);
                }
            });
            return;
        }
        this.rebuildSelector0();
    }

    private void rebuildSelector0() {
        int n;
        block10: {
            SelectorTuple selectorTuple;
            Selector selector = this.selector;
            if (selector == null) {
                return;
            }
            try {
                selectorTuple = this.openSelector();
            } catch (Exception exception) {
                logger.warn("Failed to create a new Selector.", exception);
                return;
            }
            n = 0;
            for (SelectionKey selectionKey : selector.keys()) {
                Object object;
                Object object2 = selectionKey.attachment();
                try {
                    if (!selectionKey.isValid() || selectionKey.channel().keyFor(selectorTuple.unwrappedSelector) != null) continue;
                    int n2 = selectionKey.interestOps();
                    selectionKey.cancel();
                    object = selectionKey.channel().register(selectorTuple.unwrappedSelector, n2, object2);
                    if (object2 instanceof AbstractNioChannel) {
                        ((AbstractNioChannel)object2).selectionKey = object;
                    }
                    ++n;
                } catch (Exception exception) {
                    logger.warn("Failed to re-register a Channel to the new Selector.", exception);
                    if (object2 instanceof AbstractNioChannel) {
                        object = (AbstractNioChannel)object2;
                        ((AbstractNioChannel)object).unsafe().close(((AbstractNioChannel)object).unsafe().voidPromise());
                        continue;
                    }
                    object = (NioTask)object2;
                    NioEventLoop.invokeChannelUnregistered((NioTask<SelectableChannel>)object, selectionKey, exception);
                }
            }
            this.selector = selectorTuple.selector;
            this.unwrappedSelector = selectorTuple.unwrappedSelector;
            try {
                selector.close();
            } catch (Throwable throwable) {
                if (!logger.isWarnEnabled()) break block10;
                logger.warn("Failed to close the old Selector.", throwable);
            }
        }
        logger.info("Migrated " + n + " channel(s) to the new Selector.");
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    protected void run() {
        while (true) {
            block17: {
                try {
                    block15: while (true) {
                        switch (this.selectStrategy.calculateStrategy(this.selectNowSupplier, this.hasTasks())) {
                            case -2: {
                                continue block15;
                            }
                            case -1: {
                                this.select(this.wakenUp.getAndSet(true));
                                if (!this.wakenUp.get()) break block15;
                                this.selector.wakeup();
                            }
                        }
                        break;
                    }
                    this.cancelledKeys = 0;
                    this.needsToSelectAgain = false;
                    int n = this.ioRatio;
                    if (n == 100) {
                        try {
                            this.processSelectedKeys();
                            break block17;
                        } finally {
                            this.runAllTasks();
                        }
                    }
                    long l = System.nanoTime();
                    try {
                        this.processSelectedKeys();
                    } finally {
                        long l2 = System.nanoTime() - l;
                        this.runAllTasks(l2 * (long)(100 - n) / (long)n);
                    }
                } catch (Throwable throwable) {
                    NioEventLoop.handleLoopException(throwable);
                }
            }
            try {
                if (!this.isShuttingDown()) continue;
                this.closeAll();
                if (!this.confirmShutdown()) continue;
                return;
            } catch (Throwable throwable) {
                NioEventLoop.handleLoopException(throwable);
                continue;
            }
            break;
        }
    }

    private static void handleLoopException(Throwable throwable) {
        logger.warn("Unexpected exception in the selector loop.", throwable);
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException interruptedException) {
            // empty catch block
        }
    }

    private void processSelectedKeys() {
        if (this.selectedKeys != null) {
            this.processSelectedKeysOptimized();
        } else {
            this.processSelectedKeysPlain(this.selector.selectedKeys());
        }
    }

    @Override
    protected void cleanup() {
        try {
            this.selector.close();
        } catch (IOException iOException) {
            logger.warn("Failed to close a selector.", iOException);
        }
    }

    void cancel(SelectionKey selectionKey) {
        selectionKey.cancel();
        ++this.cancelledKeys;
        if (this.cancelledKeys >= 256) {
            this.cancelledKeys = 0;
            this.needsToSelectAgain = true;
        }
    }

    @Override
    protected Runnable pollTask() {
        Runnable runnable = super.pollTask();
        if (this.needsToSelectAgain) {
            this.selectAgain();
        }
        return runnable;
    }

    private void processSelectedKeysPlain(Set<SelectionKey> set) {
        if (set.isEmpty()) {
            return;
        }
        Iterator<SelectionKey> iterator2 = set.iterator();
        while (true) {
            SelectionKey selectionKey = iterator2.next();
            Object object = selectionKey.attachment();
            iterator2.remove();
            if (object instanceof AbstractNioChannel) {
                this.processSelectedKey(selectionKey, (AbstractNioChannel)object);
            } else {
                NioTask nioTask = (NioTask)object;
                NioEventLoop.processSelectedKey(selectionKey, nioTask);
            }
            if (!iterator2.hasNext()) break;
            if (!this.needsToSelectAgain) continue;
            this.selectAgain();
            set = this.selector.selectedKeys();
            if (set.isEmpty()) break;
            iterator2 = set.iterator();
        }
    }

    private void processSelectedKeysOptimized() {
        for (int i = 0; i < this.selectedKeys.size; ++i) {
            SelectionKey selectionKey = this.selectedKeys.keys[i];
            this.selectedKeys.keys[i] = null;
            Object object = selectionKey.attachment();
            if (object instanceof AbstractNioChannel) {
                this.processSelectedKey(selectionKey, (AbstractNioChannel)object);
            } else {
                NioTask nioTask = (NioTask)object;
                NioEventLoop.processSelectedKey(selectionKey, nioTask);
            }
            if (!this.needsToSelectAgain) continue;
            this.selectedKeys.reset(i + 1);
            this.selectAgain();
            i = -1;
        }
    }

    private void processSelectedKey(SelectionKey selectionKey, AbstractNioChannel abstractNioChannel) {
        AbstractNioChannel.NioUnsafe nioUnsafe = abstractNioChannel.unsafe();
        if (!selectionKey.isValid()) {
            NioEventLoop nioEventLoop;
            try {
                nioEventLoop = abstractNioChannel.eventLoop();
            } catch (Throwable throwable) {
                return;
            }
            if (nioEventLoop != this || nioEventLoop == null) {
                return;
            }
            nioUnsafe.close(nioUnsafe.voidPromise());
            return;
        }
        try {
            int n = selectionKey.readyOps();
            if ((n & 8) != 0) {
                int n2 = selectionKey.interestOps();
                selectionKey.interestOps(n2 &= 0xFFFFFFF7);
                nioUnsafe.finishConnect();
            }
            if ((n & 4) != 0) {
                abstractNioChannel.unsafe().forceFlush();
            }
            if ((n & 0x11) != 0 || n == 0) {
                nioUnsafe.read();
            }
        } catch (CancelledKeyException cancelledKeyException) {
            nioUnsafe.close(nioUnsafe.voidPromise());
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static void processSelectedKey(SelectionKey selectionKey, NioTask<SelectableChannel> nioTask) {
        int n = 0;
        try {
            nioTask.channelReady(selectionKey.channel(), selectionKey);
            n = 1;
        } catch (Exception exception) {
            selectionKey.cancel();
            NioEventLoop.invokeChannelUnregistered(nioTask, selectionKey, exception);
            n = 2;
        } finally {
            switch (n) {
                case 0: {
                    selectionKey.cancel();
                    NioEventLoop.invokeChannelUnregistered(nioTask, selectionKey, null);
                    break;
                }
                case 1: {
                    if (selectionKey.isValid()) break;
                    NioEventLoop.invokeChannelUnregistered(nioTask, selectionKey, null);
                }
            }
        }
    }

    private void closeAll() {
        this.selectAgain();
        Set<SelectionKey> set = this.selector.keys();
        ArrayList<AbstractNioChannel> arrayList = new ArrayList<AbstractNioChannel>(set.size());
        for (SelectionKey object : set) {
            Object object2 = object.attachment();
            if (object2 instanceof AbstractNioChannel) {
                arrayList.add((AbstractNioChannel)object2);
                continue;
            }
            object.cancel();
            NioTask nioTask = (NioTask)object2;
            NioEventLoop.invokeChannelUnregistered(nioTask, object, null);
        }
        for (AbstractNioChannel abstractNioChannel : arrayList) {
            abstractNioChannel.unsafe().close(abstractNioChannel.unsafe().voidPromise());
        }
    }

    private static void invokeChannelUnregistered(NioTask<SelectableChannel> nioTask, SelectionKey selectionKey, Throwable throwable) {
        try {
            nioTask.channelUnregistered(selectionKey.channel(), throwable);
        } catch (Exception exception) {
            logger.warn("Unexpected exception while running NioTask.channelUnregistered()", exception);
        }
    }

    @Override
    protected void wakeup(boolean bl) {
        if (!bl && this.wakenUp.compareAndSet(false, false)) {
            this.selector.wakeup();
        }
    }

    Selector unwrappedSelector() {
        return this.unwrappedSelector;
    }

    int selectNow() throws IOException {
        try {
            int n = this.selector.selectNow();
            return n;
        } finally {
            if (this.wakenUp.get()) {
                this.selector.wakeup();
            }
        }
    }

    private void select(boolean bl) throws IOException {
        block11: {
            Selector selector = this.selector;
            try {
                int n = 0;
                long l = System.nanoTime();
                long l2 = l + this.delayNanos(l);
                while (true) {
                    long l3;
                    if ((l3 = (l2 - l + 500000L) / 1000000L) <= 0L) {
                        if (n != 0) break;
                        selector.selectNow();
                        n = 1;
                        break;
                    }
                    if (this.hasTasks() && this.wakenUp.compareAndSet(false, false)) {
                        selector.selectNow();
                        n = 1;
                        break;
                    }
                    int n2 = selector.select(l3);
                    ++n;
                    if (n2 != 0 || bl || this.wakenUp.get() || this.hasTasks() || this.hasScheduledTasks()) break;
                    if (Thread.interrupted()) {
                        if (logger.isDebugEnabled()) {
                            logger.debug("Selector.select() returned prematurely because Thread.currentThread().interrupt() was called. Use NioEventLoop.shutdownGracefully() to shutdown the NioEventLoop.");
                        }
                        n = 1;
                        break;
                    }
                    long l4 = System.nanoTime();
                    if (l4 - TimeUnit.MILLISECONDS.toNanos(l3) >= l) {
                        n = 1;
                    } else if (SELECTOR_AUTO_REBUILD_THRESHOLD > 0 && n >= SELECTOR_AUTO_REBUILD_THRESHOLD) {
                        logger.warn("Selector.select() returned prematurely {} times in a row; rebuilding Selector {}.", (Object)n, (Object)selector);
                        this.rebuildSelector();
                        selector = this.selector;
                        selector.selectNow();
                        n = 1;
                        break;
                    }
                    l = l4;
                }
                if (n > 3 && logger.isDebugEnabled()) {
                    logger.debug("Selector.select() returned prematurely {} times in a row for Selector {}.", (Object)(n - 1), (Object)selector);
                }
            } catch (CancelledKeyException cancelledKeyException) {
                if (!logger.isDebugEnabled()) break block11;
                logger.debug(CancelledKeyException.class.getSimpleName() + " raised by a Selector {} - JDK bug?", (Object)selector, (Object)cancelledKeyException);
            }
        }
    }

    private void selectAgain() {
        this.needsToSelectAgain = false;
        try {
            this.selector.selectNow();
        } catch (Throwable throwable) {
            logger.warn("Failed to update SelectionKeys.", throwable);
        }
    }

    @Override
    protected void validateScheduled(long l, TimeUnit timeUnit) {
        long l2 = timeUnit.toDays(l);
        if (l2 > 1095L) {
            throw new IllegalArgumentException("days: " + l2 + " (expected: < " + 1095L + ')');
        }
    }

    static int access$001(NioEventLoop nioEventLoop) {
        return super.pendingTasks();
    }

    static void access$100(NioEventLoop nioEventLoop) {
        nioEventLoop.rebuildSelector0();
    }

    static {
        int n;
        logger = InternalLoggerFactory.getInstance(NioEventLoop.class);
        DISABLE_KEYSET_OPTIMIZATION = SystemPropertyUtil.getBoolean("io.netty.noKeySetOptimization", false);
        String string = "sun.nio.ch.bugLevel";
        String string2 = SystemPropertyUtil.get("sun.nio.ch.bugLevel");
        if (string2 == null) {
            try {
                AccessController.doPrivileged(new PrivilegedAction<Void>(){

                    @Override
                    public Void run() {
                        System.setProperty("sun.nio.ch.bugLevel", "");
                        return null;
                    }

                    @Override
                    public Object run() {
                        return this.run();
                    }
                });
            } catch (SecurityException securityException) {
                logger.debug("Unable to get/set System Property: sun.nio.ch.bugLevel", securityException);
            }
        }
        if ((n = SystemPropertyUtil.getInt("io.netty.selectorAutoRebuildThreshold", 512)) < 3) {
            n = 0;
        }
        SELECTOR_AUTO_REBUILD_THRESHOLD = n;
        if (logger.isDebugEnabled()) {
            logger.debug("-Dio.netty.noKeySetOptimization: {}", (Object)DISABLE_KEYSET_OPTIMIZATION);
            logger.debug("-Dio.netty.selectorAutoRebuildThreshold: {}", (Object)SELECTOR_AUTO_REBUILD_THRESHOLD);
        }
    }

    private static final class SelectorTuple {
        final Selector unwrappedSelector;
        final Selector selector;

        SelectorTuple(Selector selector) {
            this.unwrappedSelector = selector;
            this.selector = selector;
        }

        SelectorTuple(Selector selector, Selector selector2) {
            this.unwrappedSelector = selector;
            this.selector = selector2;
        }
    }
}

