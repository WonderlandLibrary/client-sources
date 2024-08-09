/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.util.concurrent;

import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.EventExecutorChooserFactory;
import java.util.concurrent.atomic.AtomicInteger;

public final class DefaultEventExecutorChooserFactory
implements EventExecutorChooserFactory {
    public static final DefaultEventExecutorChooserFactory INSTANCE = new DefaultEventExecutorChooserFactory();

    private DefaultEventExecutorChooserFactory() {
    }

    @Override
    public EventExecutorChooserFactory.EventExecutorChooser newChooser(EventExecutor[] eventExecutorArray) {
        if (DefaultEventExecutorChooserFactory.isPowerOfTwo(eventExecutorArray.length)) {
            return new PowerOfTwoEventExecutorChooser(eventExecutorArray);
        }
        return new GenericEventExecutorChooser(eventExecutorArray);
    }

    private static boolean isPowerOfTwo(int n) {
        return (n & -n) == n;
    }

    private static final class GenericEventExecutorChooser
    implements EventExecutorChooserFactory.EventExecutorChooser {
        private final AtomicInteger idx = new AtomicInteger();
        private final EventExecutor[] executors;

        GenericEventExecutorChooser(EventExecutor[] eventExecutorArray) {
            this.executors = eventExecutorArray;
        }

        @Override
        public EventExecutor next() {
            return this.executors[Math.abs(this.idx.getAndIncrement() % this.executors.length)];
        }
    }

    private static final class PowerOfTwoEventExecutorChooser
    implements EventExecutorChooserFactory.EventExecutorChooser {
        private final AtomicInteger idx = new AtomicInteger();
        private final EventExecutor[] executors;

        PowerOfTwoEventExecutorChooser(EventExecutor[] eventExecutorArray) {
            this.executors = eventExecutorArray;
        }

        @Override
        public EventExecutor next() {
            return this.executors[this.idx.getAndIncrement() & this.executors.length - 1];
        }
    }
}

