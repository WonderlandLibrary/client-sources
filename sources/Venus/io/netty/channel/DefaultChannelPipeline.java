/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.channel;

import io.netty.channel.AbstractChannelHandlerContext;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;
import io.netty.channel.ChannelInboundInvoker;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelOutboundBuffer;
import io.netty.channel.ChannelOutboundHandler;
import io.netty.channel.ChannelOutboundInvoker;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.ChannelPipelineException;
import io.netty.channel.ChannelProgressivePromise;
import io.netty.channel.ChannelPromise;
import io.netty.channel.DefaultChannelHandlerContext;
import io.netty.channel.DefaultChannelProgressivePromise;
import io.netty.channel.DefaultChannelPromise;
import io.netty.channel.FailedChannelFuture;
import io.netty.channel.MessageSizeEstimator;
import io.netty.channel.SucceededChannelFuture;
import io.netty.channel.VoidChannelPromise;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.ResourceLeakDetector;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.EventExecutorGroup;
import io.netty.util.concurrent.FastThreadLocal;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.StringUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.WeakHashMap;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class DefaultChannelPipeline
implements ChannelPipeline {
    static final InternalLogger logger;
    private static final String HEAD_NAME;
    private static final String TAIL_NAME;
    private static final FastThreadLocal<Map<Class<?>, String>> nameCaches;
    private static final AtomicReferenceFieldUpdater<DefaultChannelPipeline, MessageSizeEstimator.Handle> ESTIMATOR;
    final AbstractChannelHandlerContext head;
    final AbstractChannelHandlerContext tail;
    private final Channel channel;
    private final ChannelFuture succeededFuture;
    private final VoidChannelPromise voidPromise;
    private final boolean touch = ResourceLeakDetector.isEnabled();
    private Map<EventExecutorGroup, EventExecutor> childExecutors;
    private volatile MessageSizeEstimator.Handle estimatorHandle;
    private boolean firstRegistration = true;
    private PendingHandlerCallback pendingHandlerCallbackHead;
    private boolean registered;
    static final boolean $assertionsDisabled;

    protected DefaultChannelPipeline(Channel channel) {
        this.channel = ObjectUtil.checkNotNull(channel, "channel");
        this.succeededFuture = new SucceededChannelFuture(channel, null);
        this.voidPromise = new VoidChannelPromise(channel, true);
        this.tail = new TailContext(this, this);
        this.head = new HeadContext(this, this);
        this.head.next = this.tail;
        this.tail.prev = this.head;
    }

    final MessageSizeEstimator.Handle estimatorHandle() {
        MessageSizeEstimator.Handle handle = this.estimatorHandle;
        if (handle == null && !ESTIMATOR.compareAndSet(this, null, handle = this.channel.config().getMessageSizeEstimator().newHandle())) {
            handle = this.estimatorHandle;
        }
        return handle;
    }

    final Object touch(Object object, AbstractChannelHandlerContext abstractChannelHandlerContext) {
        return this.touch ? ReferenceCountUtil.touch(object, abstractChannelHandlerContext) : object;
    }

    private AbstractChannelHandlerContext newContext(EventExecutorGroup eventExecutorGroup, String string, ChannelHandler channelHandler) {
        return new DefaultChannelHandlerContext(this, this.childExecutor(eventExecutorGroup), string, channelHandler);
    }

    private EventExecutor childExecutor(EventExecutorGroup eventExecutorGroup) {
        EventExecutor eventExecutor;
        if (eventExecutorGroup == null) {
            return null;
        }
        Boolean bl = this.channel.config().getOption(ChannelOption.SINGLE_EVENTEXECUTOR_PER_GROUP);
        if (bl != null && !bl.booleanValue()) {
            return eventExecutorGroup.next();
        }
        Map<EventExecutorGroup, EventExecutor> map = this.childExecutors;
        if (map == null) {
            map = this.childExecutors = new IdentityHashMap<EventExecutorGroup, EventExecutor>(4);
        }
        if ((eventExecutor = map.get(eventExecutorGroup)) == null) {
            eventExecutor = eventExecutorGroup.next();
            map.put(eventExecutorGroup, eventExecutor);
        }
        return eventExecutor;
    }

    @Override
    public final Channel channel() {
        return this.channel;
    }

    @Override
    public final ChannelPipeline addFirst(String string, ChannelHandler channelHandler) {
        return this.addFirst(null, string, channelHandler);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public final ChannelPipeline addFirst(EventExecutorGroup eventExecutorGroup, String string, ChannelHandler channelHandler) {
        AbstractChannelHandlerContext abstractChannelHandlerContext;
        DefaultChannelPipeline defaultChannelPipeline = this;
        synchronized (defaultChannelPipeline) {
            DefaultChannelPipeline.checkMultiplicity(channelHandler);
            string = this.filterName(string, channelHandler);
            abstractChannelHandlerContext = this.newContext(eventExecutorGroup, string, channelHandler);
            this.addFirst0(abstractChannelHandlerContext);
            if (!this.registered) {
                abstractChannelHandlerContext.setAddPending();
                this.callHandlerCallbackLater(abstractChannelHandlerContext, true);
                return this;
            }
            EventExecutor eventExecutor = abstractChannelHandlerContext.executor();
            if (!eventExecutor.inEventLoop()) {
                abstractChannelHandlerContext.setAddPending();
                eventExecutor.execute(new Runnable(this, abstractChannelHandlerContext){
                    final AbstractChannelHandlerContext val$newCtx;
                    final DefaultChannelPipeline this$0;
                    {
                        this.this$0 = defaultChannelPipeline;
                        this.val$newCtx = abstractChannelHandlerContext;
                    }

                    @Override
                    public void run() {
                        DefaultChannelPipeline.access$000(this.this$0, this.val$newCtx);
                    }
                });
                return this;
            }
        }
        this.callHandlerAdded0(abstractChannelHandlerContext);
        return this;
    }

    private void addFirst0(AbstractChannelHandlerContext abstractChannelHandlerContext) {
        AbstractChannelHandlerContext abstractChannelHandlerContext2 = this.head.next;
        abstractChannelHandlerContext.prev = this.head;
        abstractChannelHandlerContext.next = abstractChannelHandlerContext2;
        this.head.next = abstractChannelHandlerContext;
        abstractChannelHandlerContext2.prev = abstractChannelHandlerContext;
    }

    @Override
    public final ChannelPipeline addLast(String string, ChannelHandler channelHandler) {
        return this.addLast(null, string, channelHandler);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public final ChannelPipeline addLast(EventExecutorGroup eventExecutorGroup, String string, ChannelHandler channelHandler) {
        AbstractChannelHandlerContext abstractChannelHandlerContext;
        DefaultChannelPipeline defaultChannelPipeline = this;
        synchronized (defaultChannelPipeline) {
            DefaultChannelPipeline.checkMultiplicity(channelHandler);
            abstractChannelHandlerContext = this.newContext(eventExecutorGroup, this.filterName(string, channelHandler), channelHandler);
            this.addLast0(abstractChannelHandlerContext);
            if (!this.registered) {
                abstractChannelHandlerContext.setAddPending();
                this.callHandlerCallbackLater(abstractChannelHandlerContext, true);
                return this;
            }
            EventExecutor eventExecutor = abstractChannelHandlerContext.executor();
            if (!eventExecutor.inEventLoop()) {
                abstractChannelHandlerContext.setAddPending();
                eventExecutor.execute(new Runnable(this, abstractChannelHandlerContext){
                    final AbstractChannelHandlerContext val$newCtx;
                    final DefaultChannelPipeline this$0;
                    {
                        this.this$0 = defaultChannelPipeline;
                        this.val$newCtx = abstractChannelHandlerContext;
                    }

                    @Override
                    public void run() {
                        DefaultChannelPipeline.access$000(this.this$0, this.val$newCtx);
                    }
                });
                return this;
            }
        }
        this.callHandlerAdded0(abstractChannelHandlerContext);
        return this;
    }

    private void addLast0(AbstractChannelHandlerContext abstractChannelHandlerContext) {
        AbstractChannelHandlerContext abstractChannelHandlerContext2;
        abstractChannelHandlerContext.prev = abstractChannelHandlerContext2 = this.tail.prev;
        abstractChannelHandlerContext.next = this.tail;
        abstractChannelHandlerContext2.next = abstractChannelHandlerContext;
        this.tail.prev = abstractChannelHandlerContext;
    }

    @Override
    public final ChannelPipeline addBefore(String string, String string2, ChannelHandler channelHandler) {
        return this.addBefore(null, string, string2, channelHandler);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public final ChannelPipeline addBefore(EventExecutorGroup eventExecutorGroup, String string, String string2, ChannelHandler channelHandler) {
        AbstractChannelHandlerContext abstractChannelHandlerContext;
        DefaultChannelPipeline defaultChannelPipeline = this;
        synchronized (defaultChannelPipeline) {
            DefaultChannelPipeline.checkMultiplicity(channelHandler);
            string2 = this.filterName(string2, channelHandler);
            AbstractChannelHandlerContext abstractChannelHandlerContext2 = this.getContextOrDie(string);
            abstractChannelHandlerContext = this.newContext(eventExecutorGroup, string2, channelHandler);
            DefaultChannelPipeline.addBefore0(abstractChannelHandlerContext2, abstractChannelHandlerContext);
            if (!this.registered) {
                abstractChannelHandlerContext.setAddPending();
                this.callHandlerCallbackLater(abstractChannelHandlerContext, true);
                return this;
            }
            EventExecutor eventExecutor = abstractChannelHandlerContext.executor();
            if (!eventExecutor.inEventLoop()) {
                abstractChannelHandlerContext.setAddPending();
                eventExecutor.execute(new Runnable(this, abstractChannelHandlerContext){
                    final AbstractChannelHandlerContext val$newCtx;
                    final DefaultChannelPipeline this$0;
                    {
                        this.this$0 = defaultChannelPipeline;
                        this.val$newCtx = abstractChannelHandlerContext;
                    }

                    @Override
                    public void run() {
                        DefaultChannelPipeline.access$000(this.this$0, this.val$newCtx);
                    }
                });
                return this;
            }
        }
        this.callHandlerAdded0(abstractChannelHandlerContext);
        return this;
    }

    private static void addBefore0(AbstractChannelHandlerContext abstractChannelHandlerContext, AbstractChannelHandlerContext abstractChannelHandlerContext2) {
        abstractChannelHandlerContext2.prev = abstractChannelHandlerContext.prev;
        abstractChannelHandlerContext2.next = abstractChannelHandlerContext;
        abstractChannelHandlerContext.prev.next = abstractChannelHandlerContext2;
        abstractChannelHandlerContext.prev = abstractChannelHandlerContext2;
    }

    private String filterName(String string, ChannelHandler channelHandler) {
        if (string == null) {
            return this.generateName(channelHandler);
        }
        this.checkDuplicateName(string);
        return string;
    }

    @Override
    public final ChannelPipeline addAfter(String string, String string2, ChannelHandler channelHandler) {
        return this.addAfter(null, string, string2, channelHandler);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public final ChannelPipeline addAfter(EventExecutorGroup eventExecutorGroup, String string, String string2, ChannelHandler channelHandler) {
        AbstractChannelHandlerContext abstractChannelHandlerContext;
        DefaultChannelPipeline defaultChannelPipeline = this;
        synchronized (defaultChannelPipeline) {
            DefaultChannelPipeline.checkMultiplicity(channelHandler);
            string2 = this.filterName(string2, channelHandler);
            AbstractChannelHandlerContext abstractChannelHandlerContext2 = this.getContextOrDie(string);
            abstractChannelHandlerContext = this.newContext(eventExecutorGroup, string2, channelHandler);
            DefaultChannelPipeline.addAfter0(abstractChannelHandlerContext2, abstractChannelHandlerContext);
            if (!this.registered) {
                abstractChannelHandlerContext.setAddPending();
                this.callHandlerCallbackLater(abstractChannelHandlerContext, true);
                return this;
            }
            EventExecutor eventExecutor = abstractChannelHandlerContext.executor();
            if (!eventExecutor.inEventLoop()) {
                abstractChannelHandlerContext.setAddPending();
                eventExecutor.execute(new Runnable(this, abstractChannelHandlerContext){
                    final AbstractChannelHandlerContext val$newCtx;
                    final DefaultChannelPipeline this$0;
                    {
                        this.this$0 = defaultChannelPipeline;
                        this.val$newCtx = abstractChannelHandlerContext;
                    }

                    @Override
                    public void run() {
                        DefaultChannelPipeline.access$000(this.this$0, this.val$newCtx);
                    }
                });
                return this;
            }
        }
        this.callHandlerAdded0(abstractChannelHandlerContext);
        return this;
    }

    private static void addAfter0(AbstractChannelHandlerContext abstractChannelHandlerContext, AbstractChannelHandlerContext abstractChannelHandlerContext2) {
        abstractChannelHandlerContext2.prev = abstractChannelHandlerContext;
        abstractChannelHandlerContext2.next = abstractChannelHandlerContext.next;
        abstractChannelHandlerContext.next.prev = abstractChannelHandlerContext2;
        abstractChannelHandlerContext.next = abstractChannelHandlerContext2;
    }

    public final ChannelPipeline addFirst(ChannelHandler channelHandler) {
        return this.addFirst((String)null, channelHandler);
    }

    @Override
    public final ChannelPipeline addFirst(ChannelHandler ... channelHandlerArray) {
        return this.addFirst((EventExecutorGroup)null, channelHandlerArray);
    }

    @Override
    public final ChannelPipeline addFirst(EventExecutorGroup eventExecutorGroup, ChannelHandler ... channelHandlerArray) {
        if (channelHandlerArray == null) {
            throw new NullPointerException("handlers");
        }
        if (channelHandlerArray.length == 0 || channelHandlerArray[0] == null) {
            return this;
        }
        for (int i = 1; i < channelHandlerArray.length && channelHandlerArray[i] != null; ++i) {
        }
        for (int i = i - 1; i >= 0; --i) {
            ChannelHandler channelHandler = channelHandlerArray[i];
            this.addFirst(eventExecutorGroup, (String)null, channelHandler);
        }
        return this;
    }

    public final ChannelPipeline addLast(ChannelHandler channelHandler) {
        return this.addLast((String)null, channelHandler);
    }

    @Override
    public final ChannelPipeline addLast(ChannelHandler ... channelHandlerArray) {
        return this.addLast((EventExecutorGroup)null, channelHandlerArray);
    }

    @Override
    public final ChannelPipeline addLast(EventExecutorGroup eventExecutorGroup, ChannelHandler ... channelHandlerArray) {
        if (channelHandlerArray == null) {
            throw new NullPointerException("handlers");
        }
        for (ChannelHandler channelHandler : channelHandlerArray) {
            if (channelHandler == null) break;
            this.addLast(eventExecutorGroup, (String)null, channelHandler);
        }
        return this;
    }

    private String generateName(ChannelHandler channelHandler) {
        Class<?> clazz;
        Map<Class<?>, String> map = nameCaches.get();
        String string = map.get(clazz = channelHandler.getClass());
        if (string == null) {
            string = DefaultChannelPipeline.generateName0(clazz);
            map.put(clazz, string);
        }
        if (this.context0(string) != null) {
            String string2 = string.substring(0, string.length() - 1);
            int n = 1;
            while (true) {
                String string3;
                if (this.context0(string3 = string2 + n) == null) {
                    string = string3;
                    break;
                }
                ++n;
            }
        }
        return string;
    }

    private static String generateName0(Class<?> clazz) {
        return StringUtil.simpleClassName(clazz) + "#0";
    }

    @Override
    public final ChannelPipeline remove(ChannelHandler channelHandler) {
        this.remove(this.getContextOrDie(channelHandler));
        return this;
    }

    @Override
    public final ChannelHandler remove(String string) {
        return this.remove(this.getContextOrDie(string)).handler();
    }

    @Override
    public final <T extends ChannelHandler> T remove(Class<T> clazz) {
        return (T)this.remove(this.getContextOrDie(clazz)).handler();
    }

    public final <T extends ChannelHandler> T removeIfExists(String string) {
        return this.removeIfExists(this.context(string));
    }

    public final <T extends ChannelHandler> T removeIfExists(Class<T> clazz) {
        return this.removeIfExists(this.context(clazz));
    }

    public final <T extends ChannelHandler> T removeIfExists(ChannelHandler channelHandler) {
        return this.removeIfExists(this.context(channelHandler));
    }

    private <T extends ChannelHandler> T removeIfExists(ChannelHandlerContext channelHandlerContext) {
        if (channelHandlerContext == null) {
            return null;
        }
        return (T)this.remove((AbstractChannelHandlerContext)channelHandlerContext).handler();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private AbstractChannelHandlerContext remove(AbstractChannelHandlerContext abstractChannelHandlerContext) {
        if (!($assertionsDisabled || abstractChannelHandlerContext != this.head && abstractChannelHandlerContext != this.tail)) {
            throw new AssertionError();
        }
        DefaultChannelPipeline defaultChannelPipeline = this;
        synchronized (defaultChannelPipeline) {
            DefaultChannelPipeline.remove0(abstractChannelHandlerContext);
            if (!this.registered) {
                this.callHandlerCallbackLater(abstractChannelHandlerContext, false);
                return abstractChannelHandlerContext;
            }
            EventExecutor eventExecutor = abstractChannelHandlerContext.executor();
            if (!eventExecutor.inEventLoop()) {
                eventExecutor.execute(new Runnable(this, abstractChannelHandlerContext){
                    final AbstractChannelHandlerContext val$ctx;
                    final DefaultChannelPipeline this$0;
                    {
                        this.this$0 = defaultChannelPipeline;
                        this.val$ctx = abstractChannelHandlerContext;
                    }

                    @Override
                    public void run() {
                        DefaultChannelPipeline.access$100(this.this$0, this.val$ctx);
                    }
                });
                return abstractChannelHandlerContext;
            }
        }
        this.callHandlerRemoved0(abstractChannelHandlerContext);
        return abstractChannelHandlerContext;
    }

    private static void remove0(AbstractChannelHandlerContext abstractChannelHandlerContext) {
        AbstractChannelHandlerContext abstractChannelHandlerContext2;
        AbstractChannelHandlerContext abstractChannelHandlerContext3 = abstractChannelHandlerContext.prev;
        abstractChannelHandlerContext3.next = abstractChannelHandlerContext2 = abstractChannelHandlerContext.next;
        abstractChannelHandlerContext2.prev = abstractChannelHandlerContext3;
    }

    @Override
    public final ChannelHandler removeFirst() {
        if (this.head.next == this.tail) {
            throw new NoSuchElementException();
        }
        return this.remove(this.head.next).handler();
    }

    @Override
    public final ChannelHandler removeLast() {
        if (this.head.next == this.tail) {
            throw new NoSuchElementException();
        }
        return this.remove(this.tail.prev).handler();
    }

    @Override
    public final ChannelPipeline replace(ChannelHandler channelHandler, String string, ChannelHandler channelHandler2) {
        this.replace(this.getContextOrDie(channelHandler), string, channelHandler2);
        return this;
    }

    @Override
    public final ChannelHandler replace(String string, String string2, ChannelHandler channelHandler) {
        return this.replace(this.getContextOrDie(string), string2, channelHandler);
    }

    @Override
    public final <T extends ChannelHandler> T replace(Class<T> clazz, String string, ChannelHandler channelHandler) {
        return (T)this.replace(this.getContextOrDie(clazz), string, channelHandler);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private ChannelHandler replace(AbstractChannelHandlerContext abstractChannelHandlerContext, String string, ChannelHandler channelHandler) {
        AbstractChannelHandlerContext abstractChannelHandlerContext2;
        if (!($assertionsDisabled || abstractChannelHandlerContext != this.head && abstractChannelHandlerContext != this.tail)) {
            throw new AssertionError();
        }
        DefaultChannelPipeline defaultChannelPipeline = this;
        synchronized (defaultChannelPipeline) {
            DefaultChannelPipeline.checkMultiplicity(channelHandler);
            if (string == null) {
                string = this.generateName(channelHandler);
            } else {
                boolean bl = abstractChannelHandlerContext.name().equals(string);
                if (!bl) {
                    this.checkDuplicateName(string);
                }
            }
            abstractChannelHandlerContext2 = this.newContext(abstractChannelHandlerContext.executor, string, channelHandler);
            DefaultChannelPipeline.replace0(abstractChannelHandlerContext, abstractChannelHandlerContext2);
            if (!this.registered) {
                this.callHandlerCallbackLater(abstractChannelHandlerContext2, true);
                this.callHandlerCallbackLater(abstractChannelHandlerContext, false);
                return abstractChannelHandlerContext.handler();
            }
            EventExecutor eventExecutor = abstractChannelHandlerContext.executor();
            if (!eventExecutor.inEventLoop()) {
                eventExecutor.execute(new Runnable(this, abstractChannelHandlerContext2, abstractChannelHandlerContext){
                    final AbstractChannelHandlerContext val$newCtx;
                    final AbstractChannelHandlerContext val$ctx;
                    final DefaultChannelPipeline this$0;
                    {
                        this.this$0 = defaultChannelPipeline;
                        this.val$newCtx = abstractChannelHandlerContext;
                        this.val$ctx = abstractChannelHandlerContext2;
                    }

                    @Override
                    public void run() {
                        DefaultChannelPipeline.access$000(this.this$0, this.val$newCtx);
                        DefaultChannelPipeline.access$100(this.this$0, this.val$ctx);
                    }
                });
                return abstractChannelHandlerContext.handler();
            }
        }
        this.callHandlerAdded0(abstractChannelHandlerContext2);
        this.callHandlerRemoved0(abstractChannelHandlerContext);
        return abstractChannelHandlerContext.handler();
    }

    private static void replace0(AbstractChannelHandlerContext abstractChannelHandlerContext, AbstractChannelHandlerContext abstractChannelHandlerContext2) {
        AbstractChannelHandlerContext abstractChannelHandlerContext3 = abstractChannelHandlerContext.prev;
        AbstractChannelHandlerContext abstractChannelHandlerContext4 = abstractChannelHandlerContext.next;
        abstractChannelHandlerContext2.prev = abstractChannelHandlerContext3;
        abstractChannelHandlerContext2.next = abstractChannelHandlerContext4;
        abstractChannelHandlerContext3.next = abstractChannelHandlerContext2;
        abstractChannelHandlerContext4.prev = abstractChannelHandlerContext2;
        abstractChannelHandlerContext.prev = abstractChannelHandlerContext2;
        abstractChannelHandlerContext.next = abstractChannelHandlerContext2;
    }

    private static void checkMultiplicity(ChannelHandler channelHandler) {
        if (channelHandler instanceof ChannelHandlerAdapter) {
            ChannelHandlerAdapter channelHandlerAdapter = (ChannelHandlerAdapter)channelHandler;
            if (!channelHandlerAdapter.isSharable() && channelHandlerAdapter.added) {
                throw new ChannelPipelineException(channelHandlerAdapter.getClass().getName() + " is not a @Sharable handler, so can't be added or removed multiple times.");
            }
            channelHandlerAdapter.added = true;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void callHandlerAdded0(AbstractChannelHandlerContext abstractChannelHandlerContext) {
        try {
            abstractChannelHandlerContext.setAddComplete();
            abstractChannelHandlerContext.handler().handlerAdded(abstractChannelHandlerContext);
        } catch (Throwable throwable) {
            boolean bl;
            block9: {
                bl = false;
                try {
                    DefaultChannelPipeline.remove0(abstractChannelHandlerContext);
                    try {
                        abstractChannelHandlerContext.handler().handlerRemoved(abstractChannelHandlerContext);
                    } finally {
                        abstractChannelHandlerContext.setRemoved();
                    }
                    bl = true;
                } catch (Throwable throwable2) {
                    if (!logger.isWarnEnabled()) break block9;
                    logger.warn("Failed to remove a handler: " + abstractChannelHandlerContext.name(), throwable2);
                }
            }
            if (bl) {
                this.fireExceptionCaught(new ChannelPipelineException(abstractChannelHandlerContext.handler().getClass().getName() + ".handlerAdded() has thrown an exception; removed.", throwable));
            }
            this.fireExceptionCaught(new ChannelPipelineException(abstractChannelHandlerContext.handler().getClass().getName() + ".handlerAdded() has thrown an exception; also failed to remove.", throwable));
        }
    }

    private void callHandlerRemoved0(AbstractChannelHandlerContext abstractChannelHandlerContext) {
        try {
            try {
                abstractChannelHandlerContext.handler().handlerRemoved(abstractChannelHandlerContext);
            } finally {
                abstractChannelHandlerContext.setRemoved();
            }
        } catch (Throwable throwable) {
            this.fireExceptionCaught(new ChannelPipelineException(abstractChannelHandlerContext.handler().getClass().getName() + ".handlerRemoved() has thrown an exception.", throwable));
        }
    }

    final void invokeHandlerAddedIfNeeded() {
        if (!$assertionsDisabled && !this.channel.eventLoop().inEventLoop()) {
            throw new AssertionError();
        }
        if (this.firstRegistration) {
            this.firstRegistration = false;
            this.callHandlerAddedForAllHandlers();
        }
    }

    @Override
    public final ChannelHandler first() {
        ChannelHandlerContext channelHandlerContext = this.firstContext();
        if (channelHandlerContext == null) {
            return null;
        }
        return channelHandlerContext.handler();
    }

    @Override
    public final ChannelHandlerContext firstContext() {
        AbstractChannelHandlerContext abstractChannelHandlerContext = this.head.next;
        if (abstractChannelHandlerContext == this.tail) {
            return null;
        }
        return this.head.next;
    }

    @Override
    public final ChannelHandler last() {
        AbstractChannelHandlerContext abstractChannelHandlerContext = this.tail.prev;
        if (abstractChannelHandlerContext == this.head) {
            return null;
        }
        return abstractChannelHandlerContext.handler();
    }

    @Override
    public final ChannelHandlerContext lastContext() {
        AbstractChannelHandlerContext abstractChannelHandlerContext = this.tail.prev;
        if (abstractChannelHandlerContext == this.head) {
            return null;
        }
        return abstractChannelHandlerContext;
    }

    @Override
    public final ChannelHandler get(String string) {
        ChannelHandlerContext channelHandlerContext = this.context(string);
        if (channelHandlerContext == null) {
            return null;
        }
        return channelHandlerContext.handler();
    }

    @Override
    public final <T extends ChannelHandler> T get(Class<T> clazz) {
        ChannelHandlerContext channelHandlerContext = this.context(clazz);
        if (channelHandlerContext == null) {
            return null;
        }
        return (T)channelHandlerContext.handler();
    }

    @Override
    public final ChannelHandlerContext context(String string) {
        if (string == null) {
            throw new NullPointerException("name");
        }
        return this.context0(string);
    }

    @Override
    public final ChannelHandlerContext context(ChannelHandler channelHandler) {
        if (channelHandler == null) {
            throw new NullPointerException("handler");
        }
        AbstractChannelHandlerContext abstractChannelHandlerContext = this.head.next;
        while (abstractChannelHandlerContext != null) {
            if (abstractChannelHandlerContext.handler() == channelHandler) {
                return abstractChannelHandlerContext;
            }
            abstractChannelHandlerContext = abstractChannelHandlerContext.next;
        }
        return null;
    }

    @Override
    public final ChannelHandlerContext context(Class<? extends ChannelHandler> clazz) {
        if (clazz == null) {
            throw new NullPointerException("handlerType");
        }
        AbstractChannelHandlerContext abstractChannelHandlerContext = this.head.next;
        while (abstractChannelHandlerContext != null) {
            if (clazz.isAssignableFrom(abstractChannelHandlerContext.handler().getClass())) {
                return abstractChannelHandlerContext;
            }
            abstractChannelHandlerContext = abstractChannelHandlerContext.next;
        }
        return null;
    }

    @Override
    public final List<String> names() {
        ArrayList<String> arrayList = new ArrayList<String>();
        AbstractChannelHandlerContext abstractChannelHandlerContext = this.head.next;
        while (abstractChannelHandlerContext != null) {
            arrayList.add(abstractChannelHandlerContext.name());
            abstractChannelHandlerContext = abstractChannelHandlerContext.next;
        }
        return arrayList;
    }

    @Override
    public final Map<String, ChannelHandler> toMap() {
        LinkedHashMap<String, ChannelHandler> linkedHashMap = new LinkedHashMap<String, ChannelHandler>();
        AbstractChannelHandlerContext abstractChannelHandlerContext = this.head.next;
        while (abstractChannelHandlerContext != this.tail) {
            linkedHashMap.put(abstractChannelHandlerContext.name(), abstractChannelHandlerContext.handler());
            abstractChannelHandlerContext = abstractChannelHandlerContext.next;
        }
        return linkedHashMap;
    }

    @Override
    public final Iterator<Map.Entry<String, ChannelHandler>> iterator() {
        return this.toMap().entrySet().iterator();
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder().append(StringUtil.simpleClassName(this)).append('{');
        AbstractChannelHandlerContext abstractChannelHandlerContext = this.head.next;
        while (abstractChannelHandlerContext != this.tail) {
            stringBuilder.append('(').append(abstractChannelHandlerContext.name()).append(" = ").append(abstractChannelHandlerContext.handler().getClass().getName()).append(')');
            abstractChannelHandlerContext = abstractChannelHandlerContext.next;
            if (abstractChannelHandlerContext == this.tail) break;
            stringBuilder.append(", ");
        }
        stringBuilder.append('}');
        return stringBuilder.toString();
    }

    @Override
    public final ChannelPipeline fireChannelRegistered() {
        AbstractChannelHandlerContext.invokeChannelRegistered(this.head);
        return this;
    }

    @Override
    public final ChannelPipeline fireChannelUnregistered() {
        AbstractChannelHandlerContext.invokeChannelUnregistered(this.head);
        return this;
    }

    private synchronized void destroy() {
        this.destroyUp(this.head.next, false);
    }

    private void destroyUp(AbstractChannelHandlerContext abstractChannelHandlerContext, boolean bl) {
        Thread thread2 = Thread.currentThread();
        AbstractChannelHandlerContext abstractChannelHandlerContext2 = this.tail;
        while (true) {
            if (abstractChannelHandlerContext == abstractChannelHandlerContext2) {
                this.destroyDown(thread2, abstractChannelHandlerContext2.prev, bl);
                break;
            }
            EventExecutor eventExecutor = abstractChannelHandlerContext.executor();
            if (!bl && !eventExecutor.inEventLoop(thread2)) {
                AbstractChannelHandlerContext abstractChannelHandlerContext3 = abstractChannelHandlerContext;
                eventExecutor.execute(new Runnable(this, abstractChannelHandlerContext3){
                    final AbstractChannelHandlerContext val$finalCtx;
                    final DefaultChannelPipeline this$0;
                    {
                        this.this$0 = defaultChannelPipeline;
                        this.val$finalCtx = abstractChannelHandlerContext;
                    }

                    @Override
                    public void run() {
                        DefaultChannelPipeline.access$200(this.this$0, this.val$finalCtx, true);
                    }
                });
                break;
            }
            abstractChannelHandlerContext = abstractChannelHandlerContext.next;
            bl = false;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void destroyDown(Thread thread2, AbstractChannelHandlerContext abstractChannelHandlerContext, boolean bl) {
        AbstractChannelHandlerContext abstractChannelHandlerContext2 = this.head;
        while (abstractChannelHandlerContext != abstractChannelHandlerContext2) {
            ChannelOutboundInvoker channelOutboundInvoker;
            EventExecutor eventExecutor = abstractChannelHandlerContext.executor();
            if (bl || eventExecutor.inEventLoop(thread2)) {
                channelOutboundInvoker = this;
                synchronized (channelOutboundInvoker) {
                    DefaultChannelPipeline.remove0(abstractChannelHandlerContext);
                }
            } else {
                channelOutboundInvoker = abstractChannelHandlerContext;
                eventExecutor.execute(new Runnable(this, (AbstractChannelHandlerContext)channelOutboundInvoker){
                    final AbstractChannelHandlerContext val$finalCtx;
                    final DefaultChannelPipeline this$0;
                    {
                        this.this$0 = defaultChannelPipeline;
                        this.val$finalCtx = abstractChannelHandlerContext;
                    }

                    @Override
                    public void run() {
                        DefaultChannelPipeline.access$300(this.this$0, Thread.currentThread(), this.val$finalCtx, true);
                    }
                });
                break;
            }
            this.callHandlerRemoved0(abstractChannelHandlerContext);
            abstractChannelHandlerContext = abstractChannelHandlerContext.prev;
            bl = false;
        }
    }

    @Override
    public final ChannelPipeline fireChannelActive() {
        AbstractChannelHandlerContext.invokeChannelActive(this.head);
        return this;
    }

    @Override
    public final ChannelPipeline fireChannelInactive() {
        AbstractChannelHandlerContext.invokeChannelInactive(this.head);
        return this;
    }

    @Override
    public final ChannelPipeline fireExceptionCaught(Throwable throwable) {
        AbstractChannelHandlerContext.invokeExceptionCaught(this.head, throwable);
        return this;
    }

    @Override
    public final ChannelPipeline fireUserEventTriggered(Object object) {
        AbstractChannelHandlerContext.invokeUserEventTriggered(this.head, object);
        return this;
    }

    @Override
    public final ChannelPipeline fireChannelRead(Object object) {
        AbstractChannelHandlerContext.invokeChannelRead(this.head, object);
        return this;
    }

    @Override
    public final ChannelPipeline fireChannelReadComplete() {
        AbstractChannelHandlerContext.invokeChannelReadComplete(this.head);
        return this;
    }

    @Override
    public final ChannelPipeline fireChannelWritabilityChanged() {
        AbstractChannelHandlerContext.invokeChannelWritabilityChanged(this.head);
        return this;
    }

    @Override
    public final ChannelFuture bind(SocketAddress socketAddress) {
        return this.tail.bind(socketAddress);
    }

    @Override
    public final ChannelFuture connect(SocketAddress socketAddress) {
        return this.tail.connect(socketAddress);
    }

    @Override
    public final ChannelFuture connect(SocketAddress socketAddress, SocketAddress socketAddress2) {
        return this.tail.connect(socketAddress, socketAddress2);
    }

    @Override
    public final ChannelFuture disconnect() {
        return this.tail.disconnect();
    }

    @Override
    public final ChannelFuture close() {
        return this.tail.close();
    }

    @Override
    public final ChannelFuture deregister() {
        return this.tail.deregister();
    }

    @Override
    public final ChannelPipeline flush() {
        this.tail.flush();
        return this;
    }

    @Override
    public final ChannelFuture bind(SocketAddress socketAddress, ChannelPromise channelPromise) {
        return this.tail.bind(socketAddress, channelPromise);
    }

    @Override
    public final ChannelFuture connect(SocketAddress socketAddress, ChannelPromise channelPromise) {
        return this.tail.connect(socketAddress, channelPromise);
    }

    @Override
    public final ChannelFuture connect(SocketAddress socketAddress, SocketAddress socketAddress2, ChannelPromise channelPromise) {
        return this.tail.connect(socketAddress, socketAddress2, channelPromise);
    }

    @Override
    public final ChannelFuture disconnect(ChannelPromise channelPromise) {
        return this.tail.disconnect(channelPromise);
    }

    @Override
    public final ChannelFuture close(ChannelPromise channelPromise) {
        return this.tail.close(channelPromise);
    }

    @Override
    public final ChannelFuture deregister(ChannelPromise channelPromise) {
        return this.tail.deregister(channelPromise);
    }

    @Override
    public final ChannelPipeline read() {
        this.tail.read();
        return this;
    }

    @Override
    public final ChannelFuture write(Object object) {
        return this.tail.write(object);
    }

    @Override
    public final ChannelFuture write(Object object, ChannelPromise channelPromise) {
        return this.tail.write(object, channelPromise);
    }

    @Override
    public final ChannelFuture writeAndFlush(Object object, ChannelPromise channelPromise) {
        return this.tail.writeAndFlush(object, channelPromise);
    }

    @Override
    public final ChannelFuture writeAndFlush(Object object) {
        return this.tail.writeAndFlush(object);
    }

    @Override
    public final ChannelPromise newPromise() {
        return new DefaultChannelPromise(this.channel);
    }

    @Override
    public final ChannelProgressivePromise newProgressivePromise() {
        return new DefaultChannelProgressivePromise(this.channel);
    }

    @Override
    public final ChannelFuture newSucceededFuture() {
        return this.succeededFuture;
    }

    @Override
    public final ChannelFuture newFailedFuture(Throwable throwable) {
        return new FailedChannelFuture(this.channel, null, throwable);
    }

    @Override
    public final ChannelPromise voidPromise() {
        return this.voidPromise;
    }

    private void checkDuplicateName(String string) {
        if (this.context0(string) != null) {
            throw new IllegalArgumentException("Duplicate handler name: " + string);
        }
    }

    private AbstractChannelHandlerContext context0(String string) {
        AbstractChannelHandlerContext abstractChannelHandlerContext = this.head.next;
        while (abstractChannelHandlerContext != this.tail) {
            if (abstractChannelHandlerContext.name().equals(string)) {
                return abstractChannelHandlerContext;
            }
            abstractChannelHandlerContext = abstractChannelHandlerContext.next;
        }
        return null;
    }

    private AbstractChannelHandlerContext getContextOrDie(String string) {
        AbstractChannelHandlerContext abstractChannelHandlerContext = (AbstractChannelHandlerContext)this.context(string);
        if (abstractChannelHandlerContext == null) {
            throw new NoSuchElementException(string);
        }
        return abstractChannelHandlerContext;
    }

    private AbstractChannelHandlerContext getContextOrDie(ChannelHandler channelHandler) {
        AbstractChannelHandlerContext abstractChannelHandlerContext = (AbstractChannelHandlerContext)this.context(channelHandler);
        if (abstractChannelHandlerContext == null) {
            throw new NoSuchElementException(channelHandler.getClass().getName());
        }
        return abstractChannelHandlerContext;
    }

    private AbstractChannelHandlerContext getContextOrDie(Class<? extends ChannelHandler> clazz) {
        AbstractChannelHandlerContext abstractChannelHandlerContext = (AbstractChannelHandlerContext)this.context(clazz);
        if (abstractChannelHandlerContext == null) {
            throw new NoSuchElementException(clazz.getName());
        }
        return abstractChannelHandlerContext;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void callHandlerAddedForAllHandlers() {
        PendingHandlerCallback pendingHandlerCallback;
        Object object = this;
        synchronized (object) {
            if (!$assertionsDisabled && this.registered) {
                throw new AssertionError();
            }
            this.registered = true;
            pendingHandlerCallback = this.pendingHandlerCallbackHead;
            this.pendingHandlerCallbackHead = null;
        }
        object = pendingHandlerCallback;
        while (object != null) {
            ((PendingHandlerCallback)object).execute();
            object = ((PendingHandlerCallback)object).next;
        }
    }

    private void callHandlerCallbackLater(AbstractChannelHandlerContext abstractChannelHandlerContext, boolean bl) {
        if (!$assertionsDisabled && this.registered) {
            throw new AssertionError();
        }
        PendingHandlerCallback pendingHandlerCallback = bl ? new PendingHandlerAddedTask(this, abstractChannelHandlerContext) : new PendingHandlerRemovedTask(this, abstractChannelHandlerContext);
        PendingHandlerCallback pendingHandlerCallback2 = this.pendingHandlerCallbackHead;
        if (pendingHandlerCallback2 == null) {
            this.pendingHandlerCallbackHead = pendingHandlerCallback;
        } else {
            while (pendingHandlerCallback2.next != null) {
                pendingHandlerCallback2 = pendingHandlerCallback2.next;
            }
            pendingHandlerCallback2.next = pendingHandlerCallback;
        }
    }

    protected void onUnhandledInboundException(Throwable throwable) {
        try {
            logger.warn("An exceptionCaught() event was fired, and it reached at the tail of the pipeline. It usually means the last handler in the pipeline did not handle the exception.", throwable);
        } finally {
            ReferenceCountUtil.release(throwable);
        }
    }

    protected void onUnhandledInboundChannelActive() {
    }

    protected void onUnhandledInboundChannelInactive() {
    }

    protected void onUnhandledInboundMessage(Object object) {
        try {
            logger.debug("Discarded inbound message {} that reached at the tail of the pipeline. Please check your pipeline configuration.", object);
        } finally {
            ReferenceCountUtil.release(object);
        }
    }

    protected void onUnhandledInboundChannelReadComplete() {
    }

    protected void onUnhandledInboundUserEventTriggered(Object object) {
        ReferenceCountUtil.release(object);
    }

    protected void onUnhandledChannelWritabilityChanged() {
    }

    protected void incrementPendingOutboundBytes(long l) {
        ChannelOutboundBuffer channelOutboundBuffer = this.channel.unsafe().outboundBuffer();
        if (channelOutboundBuffer != null) {
            channelOutboundBuffer.incrementPendingOutboundBytes(l);
        }
    }

    protected void decrementPendingOutboundBytes(long l) {
        ChannelOutboundBuffer channelOutboundBuffer = this.channel.unsafe().outboundBuffer();
        if (channelOutboundBuffer != null) {
            channelOutboundBuffer.decrementPendingOutboundBytes(l);
        }
    }

    @Override
    public ChannelInboundInvoker fireChannelWritabilityChanged() {
        return this.fireChannelWritabilityChanged();
    }

    @Override
    public ChannelInboundInvoker fireChannelReadComplete() {
        return this.fireChannelReadComplete();
    }

    @Override
    public ChannelInboundInvoker fireChannelRead(Object object) {
        return this.fireChannelRead(object);
    }

    @Override
    public ChannelInboundInvoker fireUserEventTriggered(Object object) {
        return this.fireUserEventTriggered(object);
    }

    @Override
    public ChannelInboundInvoker fireExceptionCaught(Throwable throwable) {
        return this.fireExceptionCaught(throwable);
    }

    @Override
    public ChannelInboundInvoker fireChannelInactive() {
        return this.fireChannelInactive();
    }

    @Override
    public ChannelInboundInvoker fireChannelActive() {
        return this.fireChannelActive();
    }

    @Override
    public ChannelInboundInvoker fireChannelUnregistered() {
        return this.fireChannelUnregistered();
    }

    @Override
    public ChannelInboundInvoker fireChannelRegistered() {
        return this.fireChannelRegistered();
    }

    @Override
    public ChannelOutboundInvoker flush() {
        return this.flush();
    }

    @Override
    public ChannelOutboundInvoker read() {
        return this.read();
    }

    static void access$000(DefaultChannelPipeline defaultChannelPipeline, AbstractChannelHandlerContext abstractChannelHandlerContext) {
        defaultChannelPipeline.callHandlerAdded0(abstractChannelHandlerContext);
    }

    static void access$100(DefaultChannelPipeline defaultChannelPipeline, AbstractChannelHandlerContext abstractChannelHandlerContext) {
        defaultChannelPipeline.callHandlerRemoved0(abstractChannelHandlerContext);
    }

    static void access$200(DefaultChannelPipeline defaultChannelPipeline, AbstractChannelHandlerContext abstractChannelHandlerContext, boolean bl) {
        defaultChannelPipeline.destroyUp(abstractChannelHandlerContext, bl);
    }

    static void access$300(DefaultChannelPipeline defaultChannelPipeline, Thread thread2, AbstractChannelHandlerContext abstractChannelHandlerContext, boolean bl) {
        defaultChannelPipeline.destroyDown(thread2, abstractChannelHandlerContext, bl);
    }

    static String access$400() {
        return TAIL_NAME;
    }

    static String access$500() {
        return HEAD_NAME;
    }

    static Channel access$600(DefaultChannelPipeline defaultChannelPipeline) {
        return defaultChannelPipeline.channel;
    }

    static void access$700(DefaultChannelPipeline defaultChannelPipeline) {
        defaultChannelPipeline.destroy();
    }

    static void access$800(AbstractChannelHandlerContext abstractChannelHandlerContext) {
        DefaultChannelPipeline.remove0(abstractChannelHandlerContext);
    }

    static {
        $assertionsDisabled = !DefaultChannelPipeline.class.desiredAssertionStatus();
        logger = InternalLoggerFactory.getInstance(DefaultChannelPipeline.class);
        HEAD_NAME = DefaultChannelPipeline.generateName0(HeadContext.class);
        TAIL_NAME = DefaultChannelPipeline.generateName0(TailContext.class);
        nameCaches = new FastThreadLocal<Map<Class<?>, String>>(){

            @Override
            protected Map<Class<?>, String> initialValue() throws Exception {
                return new WeakHashMap();
            }

            @Override
            protected Object initialValue() throws Exception {
                return this.initialValue();
            }
        };
        ESTIMATOR = AtomicReferenceFieldUpdater.newUpdater(DefaultChannelPipeline.class, MessageSizeEstimator.Handle.class, "estimatorHandle");
    }

    private final class PendingHandlerRemovedTask
    extends PendingHandlerCallback {
        final DefaultChannelPipeline this$0;

        PendingHandlerRemovedTask(DefaultChannelPipeline defaultChannelPipeline, AbstractChannelHandlerContext abstractChannelHandlerContext) {
            this.this$0 = defaultChannelPipeline;
            super(abstractChannelHandlerContext);
        }

        @Override
        public void run() {
            DefaultChannelPipeline.access$100(this.this$0, this.ctx);
        }

        @Override
        void execute() {
            EventExecutor eventExecutor = this.ctx.executor();
            if (eventExecutor.inEventLoop()) {
                DefaultChannelPipeline.access$100(this.this$0, this.ctx);
            } else {
                try {
                    eventExecutor.execute(this);
                } catch (RejectedExecutionException rejectedExecutionException) {
                    if (logger.isWarnEnabled()) {
                        logger.warn("Can't invoke handlerRemoved() as the EventExecutor {} rejected it, removing handler {}.", eventExecutor, this.ctx.name(), rejectedExecutionException);
                    }
                    this.ctx.setRemoved();
                }
            }
        }
    }

    private final class PendingHandlerAddedTask
    extends PendingHandlerCallback {
        final DefaultChannelPipeline this$0;

        PendingHandlerAddedTask(DefaultChannelPipeline defaultChannelPipeline, AbstractChannelHandlerContext abstractChannelHandlerContext) {
            this.this$0 = defaultChannelPipeline;
            super(abstractChannelHandlerContext);
        }

        @Override
        public void run() {
            DefaultChannelPipeline.access$000(this.this$0, this.ctx);
        }

        @Override
        void execute() {
            EventExecutor eventExecutor = this.ctx.executor();
            if (eventExecutor.inEventLoop()) {
                DefaultChannelPipeline.access$000(this.this$0, this.ctx);
            } else {
                try {
                    eventExecutor.execute(this);
                } catch (RejectedExecutionException rejectedExecutionException) {
                    if (logger.isWarnEnabled()) {
                        logger.warn("Can't invoke handlerAdded() as the EventExecutor {} rejected it, removing handler {}.", eventExecutor, this.ctx.name(), rejectedExecutionException);
                    }
                    DefaultChannelPipeline.access$800(this.ctx);
                    this.ctx.setRemoved();
                }
            }
        }
    }

    private static abstract class PendingHandlerCallback
    implements Runnable {
        final AbstractChannelHandlerContext ctx;
        PendingHandlerCallback next;

        PendingHandlerCallback(AbstractChannelHandlerContext abstractChannelHandlerContext) {
            this.ctx = abstractChannelHandlerContext;
        }

        abstract void execute();
    }

    final class HeadContext
    extends AbstractChannelHandlerContext
    implements ChannelOutboundHandler,
    ChannelInboundHandler {
        private final Channel.Unsafe unsafe;
        final DefaultChannelPipeline this$0;

        HeadContext(DefaultChannelPipeline defaultChannelPipeline, DefaultChannelPipeline defaultChannelPipeline2) {
            this.this$0 = defaultChannelPipeline;
            super(defaultChannelPipeline2, null, DefaultChannelPipeline.access$500(), false, true);
            this.unsafe = defaultChannelPipeline2.channel().unsafe();
            this.setAddComplete();
        }

        @Override
        public ChannelHandler handler() {
            return this;
        }

        @Override
        public void handlerAdded(ChannelHandlerContext channelHandlerContext) throws Exception {
        }

        @Override
        public void handlerRemoved(ChannelHandlerContext channelHandlerContext) throws Exception {
        }

        @Override
        public void bind(ChannelHandlerContext channelHandlerContext, SocketAddress socketAddress, ChannelPromise channelPromise) throws Exception {
            this.unsafe.bind(socketAddress, channelPromise);
        }

        @Override
        public void connect(ChannelHandlerContext channelHandlerContext, SocketAddress socketAddress, SocketAddress socketAddress2, ChannelPromise channelPromise) throws Exception {
            this.unsafe.connect(socketAddress, socketAddress2, channelPromise);
        }

        @Override
        public void disconnect(ChannelHandlerContext channelHandlerContext, ChannelPromise channelPromise) throws Exception {
            this.unsafe.disconnect(channelPromise);
        }

        @Override
        public void close(ChannelHandlerContext channelHandlerContext, ChannelPromise channelPromise) throws Exception {
            this.unsafe.close(channelPromise);
        }

        @Override
        public void deregister(ChannelHandlerContext channelHandlerContext, ChannelPromise channelPromise) throws Exception {
            this.unsafe.deregister(channelPromise);
        }

        @Override
        public void read(ChannelHandlerContext channelHandlerContext) {
            this.unsafe.beginRead();
        }

        @Override
        public void write(ChannelHandlerContext channelHandlerContext, Object object, ChannelPromise channelPromise) throws Exception {
            this.unsafe.write(object, channelPromise);
        }

        @Override
        public void flush(ChannelHandlerContext channelHandlerContext) throws Exception {
            this.unsafe.flush();
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable throwable) throws Exception {
            channelHandlerContext.fireExceptionCaught(throwable);
        }

        @Override
        public void channelRegistered(ChannelHandlerContext channelHandlerContext) throws Exception {
            this.this$0.invokeHandlerAddedIfNeeded();
            channelHandlerContext.fireChannelRegistered();
        }

        @Override
        public void channelUnregistered(ChannelHandlerContext channelHandlerContext) throws Exception {
            channelHandlerContext.fireChannelUnregistered();
            if (!DefaultChannelPipeline.access$600(this.this$0).isOpen()) {
                DefaultChannelPipeline.access$700(this.this$0);
            }
        }

        @Override
        public void channelActive(ChannelHandlerContext channelHandlerContext) throws Exception {
            channelHandlerContext.fireChannelActive();
            this.readIfIsAutoRead();
        }

        @Override
        public void channelInactive(ChannelHandlerContext channelHandlerContext) throws Exception {
            channelHandlerContext.fireChannelInactive();
        }

        @Override
        public void channelRead(ChannelHandlerContext channelHandlerContext, Object object) throws Exception {
            channelHandlerContext.fireChannelRead(object);
        }

        @Override
        public void channelReadComplete(ChannelHandlerContext channelHandlerContext) throws Exception {
            channelHandlerContext.fireChannelReadComplete();
            this.readIfIsAutoRead();
        }

        private void readIfIsAutoRead() {
            if (DefaultChannelPipeline.access$600(this.this$0).config().isAutoRead()) {
                DefaultChannelPipeline.access$600(this.this$0).read();
            }
        }

        @Override
        public void userEventTriggered(ChannelHandlerContext channelHandlerContext, Object object) throws Exception {
            channelHandlerContext.fireUserEventTriggered(object);
        }

        @Override
        public void channelWritabilityChanged(ChannelHandlerContext channelHandlerContext) throws Exception {
            channelHandlerContext.fireChannelWritabilityChanged();
        }
    }

    final class TailContext
    extends AbstractChannelHandlerContext
    implements ChannelInboundHandler {
        final DefaultChannelPipeline this$0;

        TailContext(DefaultChannelPipeline defaultChannelPipeline, DefaultChannelPipeline defaultChannelPipeline2) {
            this.this$0 = defaultChannelPipeline;
            super(defaultChannelPipeline2, null, DefaultChannelPipeline.access$400(), true, false);
            this.setAddComplete();
        }

        @Override
        public ChannelHandler handler() {
            return this;
        }

        @Override
        public void channelRegistered(ChannelHandlerContext channelHandlerContext) throws Exception {
        }

        @Override
        public void channelUnregistered(ChannelHandlerContext channelHandlerContext) throws Exception {
        }

        @Override
        public void channelActive(ChannelHandlerContext channelHandlerContext) throws Exception {
            this.this$0.onUnhandledInboundChannelActive();
        }

        @Override
        public void channelInactive(ChannelHandlerContext channelHandlerContext) throws Exception {
            this.this$0.onUnhandledInboundChannelInactive();
        }

        @Override
        public void channelWritabilityChanged(ChannelHandlerContext channelHandlerContext) throws Exception {
            this.this$0.onUnhandledChannelWritabilityChanged();
        }

        @Override
        public void handlerAdded(ChannelHandlerContext channelHandlerContext) throws Exception {
        }

        @Override
        public void handlerRemoved(ChannelHandlerContext channelHandlerContext) throws Exception {
        }

        @Override
        public void userEventTriggered(ChannelHandlerContext channelHandlerContext, Object object) throws Exception {
            this.this$0.onUnhandledInboundUserEventTriggered(object);
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable throwable) throws Exception {
            this.this$0.onUnhandledInboundException(throwable);
        }

        @Override
        public void channelRead(ChannelHandlerContext channelHandlerContext, Object object) throws Exception {
            this.this$0.onUnhandledInboundMessage(object);
        }

        @Override
        public void channelReadComplete(ChannelHandlerContext channelHandlerContext) throws Exception {
            this.this$0.onUnhandledInboundChannelReadComplete();
        }
    }
}

