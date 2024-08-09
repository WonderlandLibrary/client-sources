/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.client.methods;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicMarkableReference;
import org.apache.http.HttpRequest;
import org.apache.http.client.methods.AbortableHttpRequest;
import org.apache.http.client.methods.HttpExecutionAware;
import org.apache.http.client.utils.CloneUtils;
import org.apache.http.concurrent.Cancellable;
import org.apache.http.conn.ClientConnectionRequest;
import org.apache.http.conn.ConnectionReleaseTrigger;
import org.apache.http.message.AbstractHttpMessage;

public abstract class AbstractExecutionAwareRequest
extends AbstractHttpMessage
implements HttpExecutionAware,
AbortableHttpRequest,
Cloneable,
HttpRequest {
    private final AtomicMarkableReference<Cancellable> cancellableRef = new AtomicMarkableReference<Object>(null, false);

    protected AbstractExecutionAwareRequest() {
    }

    @Override
    @Deprecated
    public void setConnectionRequest(ClientConnectionRequest clientConnectionRequest) {
        this.setCancellable(new Cancellable(this, clientConnectionRequest){
            final ClientConnectionRequest val$connRequest;
            final AbstractExecutionAwareRequest this$0;
            {
                this.this$0 = abstractExecutionAwareRequest;
                this.val$connRequest = clientConnectionRequest;
            }

            @Override
            public boolean cancel() {
                this.val$connRequest.abortRequest();
                return false;
            }
        });
    }

    @Override
    @Deprecated
    public void setReleaseTrigger(ConnectionReleaseTrigger connectionReleaseTrigger) {
        this.setCancellable(new Cancellable(this, connectionReleaseTrigger){
            final ConnectionReleaseTrigger val$releaseTrigger;
            final AbstractExecutionAwareRequest this$0;
            {
                this.this$0 = abstractExecutionAwareRequest;
                this.val$releaseTrigger = connectionReleaseTrigger;
            }

            @Override
            public boolean cancel() {
                try {
                    this.val$releaseTrigger.abortConnection();
                    return true;
                } catch (IOException iOException) {
                    return true;
                }
            }
        });
    }

    @Override
    public void abort() {
        while (!this.cancellableRef.isMarked()) {
            Cancellable cancellable = this.cancellableRef.getReference();
            if (!this.cancellableRef.compareAndSet(cancellable, cancellable, false, false) || cancellable == null) continue;
            cancellable.cancel();
        }
    }

    @Override
    public boolean isAborted() {
        return this.cancellableRef.isMarked();
    }

    @Override
    public void setCancellable(Cancellable cancellable) {
        Cancellable cancellable2 = this.cancellableRef.getReference();
        if (!this.cancellableRef.compareAndSet(cancellable2, cancellable, false, true)) {
            cancellable.cancel();
        }
    }

    public Object clone() throws CloneNotSupportedException {
        AbstractExecutionAwareRequest abstractExecutionAwareRequest = (AbstractExecutionAwareRequest)super.clone();
        abstractExecutionAwareRequest.headergroup = CloneUtils.cloneObject(this.headergroup);
        abstractExecutionAwareRequest.params = CloneUtils.cloneObject(this.params);
        return abstractExecutionAwareRequest;
    }

    @Deprecated
    public void completed() {
        this.cancellableRef.set(null, true);
    }

    public void reset() {
        boolean bl;
        Cancellable cancellable;
        do {
            bl = this.cancellableRef.isMarked();
            cancellable = this.cancellableRef.getReference();
            if (cancellable == null) continue;
            cancellable.cancel();
        } while (!this.cancellableRef.compareAndSet(cancellable, null, bl, true));
    }
}

