/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.resolver;

import io.netty.resolver.AddressResolver;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.FutureListener;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.io.Closeable;
import java.net.SocketAddress;
import java.util.IdentityHashMap;
import java.util.Map;

public abstract class AddressResolverGroup<T extends SocketAddress>
implements Closeable {
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(AddressResolverGroup.class);
    private final Map<EventExecutor, AddressResolver<T>> resolvers = new IdentityHashMap<EventExecutor, AddressResolver<T>>();

    protected AddressResolverGroup() {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public AddressResolver<T> getResolver(EventExecutor eventExecutor) {
        AddressResolver<T> addressResolver;
        if (eventExecutor == null) {
            throw new NullPointerException("executor");
        }
        if (eventExecutor.isShuttingDown()) {
            throw new IllegalStateException("executor not accepting a task");
        }
        Map<EventExecutor, AddressResolver<T>> map = this.resolvers;
        synchronized (map) {
            addressResolver = this.resolvers.get(eventExecutor);
            if (addressResolver == null) {
                AddressResolver<T> addressResolver2;
                try {
                    addressResolver2 = this.newResolver(eventExecutor);
                } catch (Exception exception) {
                    throw new IllegalStateException("failed to create a new resolver", exception);
                }
                this.resolvers.put(eventExecutor, addressResolver2);
                eventExecutor.terminationFuture().addListener(new FutureListener<Object>(this, eventExecutor, addressResolver2){
                    final EventExecutor val$executor;
                    final AddressResolver val$newResolver;
                    final AddressResolverGroup this$0;
                    {
                        this.this$0 = addressResolverGroup;
                        this.val$executor = eventExecutor;
                        this.val$newResolver = addressResolver;
                    }

                    /*
                     * WARNING - Removed try catching itself - possible behaviour change.
                     */
                    @Override
                    public void operationComplete(Future<Object> future) throws Exception {
                        Map map = AddressResolverGroup.access$000(this.this$0);
                        synchronized (map) {
                            AddressResolverGroup.access$000(this.this$0).remove(this.val$executor);
                        }
                        this.val$newResolver.close();
                    }
                });
                addressResolver = addressResolver2;
            }
        }
        return addressResolver;
    }

    protected abstract AddressResolver<T> newResolver(EventExecutor var1) throws Exception;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void close() {
        AddressResolver[] addressResolverArray = this.resolvers;
        synchronized (this.resolvers) {
            AddressResolver[] addressResolverArray2 = this.resolvers.values().toArray(new AddressResolver[this.resolvers.size()]);
            this.resolvers.clear();
            // ** MonitorExit[var2_1] (shouldn't be in output)
            for (AddressResolver addressResolver : addressResolverArray2) {
                try {
                    addressResolver.close();
                } catch (Throwable throwable) {
                    logger.warn("Failed to close a resolver:", throwable);
                }
            }
            return;
        }
    }

    static Map access$000(AddressResolverGroup addressResolverGroup) {
        return addressResolverGroup.resolvers;
    }
}

