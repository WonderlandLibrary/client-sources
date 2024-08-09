/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.util.concurrent;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableCollection;
import com.google.common.util.concurrent.AbstractFuture;
import com.google.common.util.concurrent.AggregateFutureState;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.MoreExecutors;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Nullable;

@GwtCompatible
abstract class AggregateFuture<InputT, OutputT>
extends AbstractFuture.TrustedFuture<OutputT> {
    private static final Logger logger = Logger.getLogger(AggregateFuture.class.getName());
    private RunningState runningState;

    AggregateFuture() {
    }

    @Override
    protected final void afterDone() {
        super.afterDone();
        RunningState runningState = this.runningState;
        if (runningState != null) {
            this.runningState = null;
            ImmutableCollection immutableCollection = RunningState.access$000(runningState);
            boolean bl = this.wasInterrupted();
            if (this.wasInterrupted()) {
                runningState.interruptTask();
            }
            if (this.isCancelled() & immutableCollection != null) {
                for (ListenableFuture listenableFuture : immutableCollection) {
                    listenableFuture.cancel(bl);
                }
            }
        }
    }

    final void init(RunningState runningState) {
        this.runningState = runningState;
        RunningState.access$100(runningState);
    }

    private static boolean addCausalChain(Set<Throwable> set, Throwable throwable) {
        while (throwable != null) {
            boolean bl = set.add(throwable);
            if (!bl) {
                return true;
            }
            throwable = throwable.getCause();
        }
        return false;
    }

    static boolean access$400(Set set, Throwable throwable) {
        return AggregateFuture.addCausalChain(set, throwable);
    }

    static Logger access$500() {
        return logger;
    }

    static RunningState access$602(AggregateFuture aggregateFuture, RunningState runningState) {
        aggregateFuture.runningState = runningState;
        return aggregateFuture.runningState;
    }

    abstract class RunningState
    extends AggregateFutureState
    implements Runnable {
        private ImmutableCollection<? extends ListenableFuture<? extends InputT>> futures;
        private final boolean allMustSucceed;
        private final boolean collectsValues;
        final AggregateFuture this$0;

        RunningState(AggregateFuture aggregateFuture, ImmutableCollection<? extends ListenableFuture<? extends InputT>> immutableCollection, boolean bl, boolean bl2) {
            this.this$0 = aggregateFuture;
            super(immutableCollection.size());
            this.futures = Preconditions.checkNotNull(immutableCollection);
            this.allMustSucceed = bl;
            this.collectsValues = bl2;
        }

        @Override
        public final void run() {
            this.decrementCountAndMaybeComplete();
        }

        private void init() {
            if (this.futures.isEmpty()) {
                this.handleAllCompleted();
                return;
            }
            if (this.allMustSucceed) {
                int n = 0;
                for (ListenableFuture listenableFuture : this.futures) {
                    int n2 = n++;
                    listenableFuture.addListener(new Runnable(this, n2, listenableFuture){
                        final int val$index;
                        final ListenableFuture val$listenable;
                        final RunningState this$1;
                        {
                            this.this$1 = runningState;
                            this.val$index = n;
                            this.val$listenable = listenableFuture;
                        }

                        @Override
                        public void run() {
                            try {
                                RunningState.access$200(this.this$1, this.val$index, this.val$listenable);
                            } finally {
                                RunningState.access$300(this.this$1);
                            }
                        }
                    }, MoreExecutors.directExecutor());
                }
            } else {
                for (ListenableFuture listenableFuture : this.futures) {
                    listenableFuture.addListener(this, MoreExecutors.directExecutor());
                }
            }
        }

        private void handleException(Throwable throwable) {
            Preconditions.checkNotNull(throwable);
            boolean bl = false;
            boolean bl2 = true;
            if (this.allMustSucceed) {
                bl = this.this$0.setException(throwable);
                if (bl) {
                    this.releaseResourcesAfterFailure();
                } else {
                    bl2 = AggregateFuture.access$400(this.getOrInitSeenExceptions(), throwable);
                }
            }
            if (throwable instanceof Error | this.allMustSucceed & !bl & bl2) {
                String string = throwable instanceof Error ? "Input Future failed with Error" : "Got more than one input Future failure. Logging failures after the first";
                AggregateFuture.access$500().log(Level.SEVERE, string, throwable);
            }
        }

        @Override
        final void addInitialException(Set<Throwable> set) {
            if (!this.this$0.isCancelled()) {
                boolean bl = AggregateFuture.access$400(set, this.this$0.trustedGetException());
            }
        }

        private void handleOneInputDone(int n, Future<? extends InputT> future) {
            Preconditions.checkState(this.allMustSucceed || !this.this$0.isDone() || this.this$0.isCancelled(), "Future was done before all dependencies completed");
            try {
                Preconditions.checkState(future.isDone(), "Tried to set value from future which is not done");
                if (this.allMustSucceed) {
                    if (future.isCancelled()) {
                        AggregateFuture.access$602(this.this$0, null);
                        this.this$0.cancel(true);
                    } else {
                        Object InputT = Futures.getDone(future);
                        if (this.collectsValues) {
                            this.collectOneValue(this.allMustSucceed, n, InputT);
                        }
                    }
                } else if (this.collectsValues && !future.isCancelled()) {
                    this.collectOneValue(this.allMustSucceed, n, Futures.getDone(future));
                }
            } catch (ExecutionException executionException) {
                this.handleException(executionException.getCause());
            } catch (Throwable throwable) {
                this.handleException(throwable);
            }
        }

        private void decrementCountAndMaybeComplete() {
            int n = this.decrementRemainingAndGet();
            Preconditions.checkState(n >= 0, "Less than 0 remaining futures");
            if (n == 0) {
                this.processCompleted();
            }
        }

        private void processCompleted() {
            if (this.collectsValues & !this.allMustSucceed) {
                int n = 0;
                for (ListenableFuture listenableFuture : this.futures) {
                    this.handleOneInputDone(n++, listenableFuture);
                }
            }
            this.handleAllCompleted();
        }

        void releaseResourcesAfterFailure() {
            this.futures = null;
        }

        abstract void collectOneValue(boolean var1, int var2, @Nullable InputT var3);

        abstract void handleAllCompleted();

        void interruptTask() {
        }

        static ImmutableCollection access$000(RunningState runningState) {
            return runningState.futures;
        }

        static void access$100(RunningState runningState) {
            runningState.init();
        }

        static void access$200(RunningState runningState, int n, Future future) {
            runningState.handleOneInputDone(n, future);
        }

        static void access$300(RunningState runningState) {
            runningState.decrementCountAndMaybeComplete();
        }
    }
}

