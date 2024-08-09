/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.cache;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import java.util.concurrent.Executor;

@GwtIncompatible
public final class RemovalListeners {
    private RemovalListeners() {
    }

    public static <K, V> RemovalListener<K, V> asynchronous(RemovalListener<K, V> removalListener, Executor executor) {
        Preconditions.checkNotNull(removalListener);
        Preconditions.checkNotNull(executor);
        return new RemovalListener<K, V>(executor, removalListener){
            final Executor val$executor;
            final RemovalListener val$listener;
            {
                this.val$executor = executor;
                this.val$listener = removalListener;
            }

            @Override
            public void onRemoval(RemovalNotification<K, V> removalNotification) {
                this.val$executor.execute(new Runnable(this, removalNotification){
                    final RemovalNotification val$notification;
                    final 1 this$0;
                    {
                        this.this$0 = var1_1;
                        this.val$notification = removalNotification;
                    }

                    @Override
                    public void run() {
                        this.this$0.val$listener.onRemoval(this.val$notification);
                    }
                });
            }
        };
    }
}

