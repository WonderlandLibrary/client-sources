/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jctools.queues.MessagePassingQueue$Consumer
 *  org.jctools.queues.MpscArrayQueue
 */
package org.apache.logging.log4j.core.async;

import java.util.Collection;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;
import org.apache.logging.log4j.core.async.BlockingQueueFactory;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.jctools.queues.MessagePassingQueue;
import org.jctools.queues.MpscArrayQueue;

@Plugin(name="JCToolsBlockingQueue", category="Core", elementType="BlockingQueueFactory")
public class JCToolsBlockingQueueFactory<E>
implements BlockingQueueFactory<E> {
    private final WaitStrategy waitStrategy;

    private JCToolsBlockingQueueFactory(WaitStrategy waitStrategy) {
        this.waitStrategy = waitStrategy;
    }

    @Override
    public BlockingQueue<E> create(int n) {
        return new MpscBlockingQueue(n, this.waitStrategy);
    }

    @PluginFactory
    public static <E> JCToolsBlockingQueueFactory<E> createFactory(@PluginAttribute(value="WaitStrategy", defaultString="PARK") WaitStrategy waitStrategy) {
        return new JCToolsBlockingQueueFactory<E>(waitStrategy);
    }

    private static interface Idle {
        public int idle(int var1);
    }

    public static enum WaitStrategy {
        SPIN(new Idle(){

            @Override
            public int idle(int n) {
                return n + 1;
            }
        }),
        YIELD(new Idle(){

            @Override
            public int idle(int n) {
                Thread.yield();
                return n + 1;
            }
        }),
        PARK(new Idle(){

            @Override
            public int idle(int n) {
                LockSupport.parkNanos(1L);
                return n + 1;
            }
        }),
        PROGRESSIVE(new Idle(){

            @Override
            public int idle(int n) {
                if (n > 200) {
                    LockSupport.parkNanos(1L);
                } else if (n > 100) {
                    Thread.yield();
                }
                return n + 1;
            }
        });

        private final Idle idle;

        private int idle(int n) {
            return this.idle.idle(n);
        }

        private WaitStrategy(Idle idle) {
            this.idle = idle;
        }

        static int access$000(WaitStrategy waitStrategy, int n) {
            return waitStrategy.idle(n);
        }
    }

    private static final class MpscBlockingQueue<E>
    extends MpscArrayQueue<E>
    implements BlockingQueue<E> {
        private final WaitStrategy waitStrategy;

        MpscBlockingQueue(int n, WaitStrategy waitStrategy) {
            super(n);
            this.waitStrategy = waitStrategy;
        }

        @Override
        public int drainTo(Collection<? super E> collection) {
            return this.drainTo(collection, this.capacity());
        }

        @Override
        public int drainTo(Collection<? super E> collection, int n) {
            return this.drain(new MessagePassingQueue.Consumer<E>(this, collection){
                final Collection val$c;
                final MpscBlockingQueue this$0;
                {
                    this.this$0 = mpscBlockingQueue;
                    this.val$c = collection;
                }

                public void accept(E e) {
                    this.val$c.add(e);
                }
            }, n);
        }

        @Override
        public boolean offer(E e, long l, TimeUnit timeUnit) throws InterruptedException {
            int n = 0;
            long l2 = System.nanoTime() + timeUnit.toNanos(l);
            do {
                if (this.offer(e)) {
                    return false;
                }
                if (System.nanoTime() - l2 > 0L) {
                    return true;
                }
                n = WaitStrategy.access$000(this.waitStrategy, n);
            } while (!Thread.interrupted());
            throw new InterruptedException();
        }

        @Override
        public E poll(long l, TimeUnit timeUnit) throws InterruptedException {
            int n = 0;
            long l2 = System.nanoTime() + timeUnit.toNanos(l);
            do {
                Object object;
                if ((object = this.poll()) != null) {
                    return (E)object;
                }
                if (System.nanoTime() - l2 > 0L) {
                    return null;
                }
                n = WaitStrategy.access$000(this.waitStrategy, n);
            } while (!Thread.interrupted());
            throw new InterruptedException();
        }

        @Override
        public void put(E e) throws InterruptedException {
            int n = 0;
            do {
                if (this.offer(e)) {
                    return;
                }
                n = WaitStrategy.access$000(this.waitStrategy, n);
            } while (!Thread.interrupted());
            throw new InterruptedException();
        }

        @Override
        public boolean offer(E e) {
            return this.offerIfBelowThreshold(e, this.capacity() - 32);
        }

        @Override
        public int remainingCapacity() {
            return this.capacity() - this.size();
        }

        @Override
        public E take() throws InterruptedException {
            int n = 100;
            do {
                Object object;
                if ((object = this.relaxedPoll()) != null) {
                    return (E)object;
                }
                n = WaitStrategy.access$000(this.waitStrategy, n);
            } while (!Thread.interrupted());
            throw new InterruptedException();
        }
    }
}

