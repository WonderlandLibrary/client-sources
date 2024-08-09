/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.concurrent;

import com.google.common.collect.Queues;
import java.util.Collection;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.annotation.Nullable;

public interface ITaskQueue<T, F> {
    @Nullable
    public F poll();

    public boolean enqueue(T var1);

    public boolean isEmpty();

    public static final class Single<T>
    implements ITaskQueue<T, T> {
        private final Queue<T> queue;

        public Single(Queue<T> queue) {
            this.queue = queue;
        }

        @Override
        @Nullable
        public T poll() {
            return this.queue.poll();
        }

        @Override
        public boolean enqueue(T t) {
            return this.queue.add(t);
        }

        @Override
        public boolean isEmpty() {
            return this.queue.isEmpty();
        }
    }

    public static final class RunnableWithPriority
    implements Runnable {
        private final int priority;
        private final Runnable runnable;

        public RunnableWithPriority(int n, Runnable runnable) {
            this.priority = n;
            this.runnable = runnable;
        }

        @Override
        public void run() {
            this.runnable.run();
        }

        public int getPriority() {
            return this.priority;
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static final class Priority
    implements ITaskQueue<RunnableWithPriority, Runnable> {
        private final List<ConcurrentLinkedQueue<Runnable>> queues;

        public Priority(int n) {
            this.queues = IntStream.range(0, n).mapToObj(Priority::lambda$new$0).collect(Collectors.toList());
        }

        @Override
        @Nullable
        public Runnable poll() {
            for (ConcurrentLinkedQueue<Runnable> concurrentLinkedQueue : this.queues) {
                Runnable runnable = concurrentLinkedQueue.poll();
                if (runnable == null) continue;
                return runnable;
            }
            return null;
        }

        @Override
        public boolean enqueue(RunnableWithPriority runnableWithPriority) {
            int n = runnableWithPriority.getPriority();
            this.queues.get(n).add(runnableWithPriority);
            return false;
        }

        @Override
        public boolean isEmpty() {
            return this.queues.stream().allMatch(Collection::isEmpty);
        }

        @Override
        public boolean enqueue(Object object) {
            return this.enqueue((RunnableWithPriority)object);
        }

        @Override
        @Nullable
        public Object poll() {
            return this.poll();
        }

        private static ConcurrentLinkedQueue lambda$new$0(int n) {
            return Queues.newConcurrentLinkedQueue();
        }
    }
}

