/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.util.concurrent;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Beta
@GwtIncompatible
public interface Service {
    @CanIgnoreReturnValue
    public Service startAsync();

    public boolean isRunning();

    public State state();

    @CanIgnoreReturnValue
    public Service stopAsync();

    public void awaitRunning();

    public void awaitRunning(long var1, TimeUnit var3) throws TimeoutException;

    public void awaitTerminated();

    public void awaitTerminated(long var1, TimeUnit var3) throws TimeoutException;

    public Throwable failureCause();

    public void addListener(Listener var1, Executor var2);

    @Beta
    public static abstract class Listener {
        public void starting() {
        }

        public void running() {
        }

        public void stopping(State state) {
        }

        public void terminated(State state) {
        }

        public void failed(State state, Throwable throwable) {
        }
    }

    @Beta
    public static enum State {
        NEW{

            @Override
            boolean isTerminal() {
                return true;
            }
        }
        ,
        STARTING{

            @Override
            boolean isTerminal() {
                return true;
            }
        }
        ,
        RUNNING{

            @Override
            boolean isTerminal() {
                return true;
            }
        }
        ,
        STOPPING{

            @Override
            boolean isTerminal() {
                return true;
            }
        }
        ,
        TERMINATED{

            @Override
            boolean isTerminal() {
                return false;
            }
        }
        ,
        FAILED{

            @Override
            boolean isTerminal() {
                return false;
            }
        };


        private State() {
        }

        abstract boolean isTerminal();

        State(1 var3_3) {
            this();
        }
    }
}

