/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.resolver;

import io.netty.resolver.AddressResolver;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.Promise;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.TypeParameterMatcher;
import java.net.SocketAddress;
import java.nio.channels.UnsupportedAddressTypeException;
import java.util.Collections;
import java.util.List;

public abstract class AbstractAddressResolver<T extends SocketAddress>
implements AddressResolver<T> {
    private final EventExecutor executor;
    private final TypeParameterMatcher matcher;

    protected AbstractAddressResolver(EventExecutor eventExecutor) {
        this.executor = ObjectUtil.checkNotNull(eventExecutor, "executor");
        this.matcher = TypeParameterMatcher.find(this, AbstractAddressResolver.class, "T");
    }

    protected AbstractAddressResolver(EventExecutor eventExecutor, Class<? extends T> clazz) {
        this.executor = ObjectUtil.checkNotNull(eventExecutor, "executor");
        this.matcher = TypeParameterMatcher.get(clazz);
    }

    protected EventExecutor executor() {
        return this.executor;
    }

    @Override
    public boolean isSupported(SocketAddress socketAddress) {
        return this.matcher.match(socketAddress);
    }

    @Override
    public final boolean isResolved(SocketAddress socketAddress) {
        if (!this.isSupported(socketAddress)) {
            throw new UnsupportedAddressTypeException();
        }
        SocketAddress socketAddress2 = socketAddress;
        return this.doIsResolved(socketAddress2);
    }

    protected abstract boolean doIsResolved(T var1);

    @Override
    public final Future<T> resolve(SocketAddress socketAddress) {
        if (!this.isSupported(ObjectUtil.checkNotNull(socketAddress, "address"))) {
            return this.executor().newFailedFuture(new UnsupportedAddressTypeException());
        }
        if (this.isResolved(socketAddress)) {
            SocketAddress socketAddress2 = socketAddress;
            return this.executor.newSucceededFuture(socketAddress2);
        }
        try {
            SocketAddress socketAddress3 = socketAddress;
            Promise promise = this.executor().newPromise();
            this.doResolve(socketAddress3, promise);
            return promise;
        } catch (Exception exception) {
            return this.executor().newFailedFuture(exception);
        }
    }

    @Override
    public final Future<T> resolve(SocketAddress socketAddress, Promise<T> promise) {
        ObjectUtil.checkNotNull(socketAddress, "address");
        ObjectUtil.checkNotNull(promise, "promise");
        if (!this.isSupported(socketAddress)) {
            return promise.setFailure(new UnsupportedAddressTypeException());
        }
        if (this.isResolved(socketAddress)) {
            SocketAddress socketAddress2 = socketAddress;
            return promise.setSuccess(socketAddress2);
        }
        try {
            SocketAddress socketAddress3 = socketAddress;
            this.doResolve(socketAddress3, promise);
            return promise;
        } catch (Exception exception) {
            return promise.setFailure(exception);
        }
    }

    @Override
    public final Future<List<T>> resolveAll(SocketAddress socketAddress) {
        if (!this.isSupported(ObjectUtil.checkNotNull(socketAddress, "address"))) {
            return this.executor().newFailedFuture(new UnsupportedAddressTypeException());
        }
        if (this.isResolved(socketAddress)) {
            SocketAddress socketAddress2 = socketAddress;
            return this.executor.newSucceededFuture(Collections.singletonList(socketAddress2));
        }
        try {
            SocketAddress socketAddress3 = socketAddress;
            Promise<List<T>> promise = this.executor().newPromise();
            this.doResolveAll(socketAddress3, promise);
            return promise;
        } catch (Exception exception) {
            return this.executor().newFailedFuture(exception);
        }
    }

    @Override
    public final Future<List<T>> resolveAll(SocketAddress socketAddress, Promise<List<T>> promise) {
        ObjectUtil.checkNotNull(socketAddress, "address");
        ObjectUtil.checkNotNull(promise, "promise");
        if (!this.isSupported(socketAddress)) {
            return promise.setFailure(new UnsupportedAddressTypeException());
        }
        if (this.isResolved(socketAddress)) {
            SocketAddress socketAddress2 = socketAddress;
            return promise.setSuccess(Collections.singletonList(socketAddress2));
        }
        try {
            SocketAddress socketAddress3 = socketAddress;
            this.doResolveAll(socketAddress3, promise);
            return promise;
        } catch (Exception exception) {
            return promise.setFailure(exception);
        }
    }

    protected abstract void doResolve(T var1, Promise<T> var2) throws Exception;

    protected abstract void doResolveAll(T var1, Promise<List<T>> var2) throws Exception;

    @Override
    public void close() {
    }
}

