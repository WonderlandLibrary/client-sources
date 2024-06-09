/*
 * Decompiled with CFR 0.143.
 */
package javassist.runtime;

public class Cflow
extends ThreadLocal {
    protected synchronized Object initialValue() {
        return new Depth();
    }

    public void enter() {
        ((Depth)this.get()).inc();
    }

    public void exit() {
        ((Depth)this.get()).dec();
    }

    public int value() {
        return ((Depth)this.get()).get();
    }

    private static class Depth {
        private int depth = 0;

        Depth() {
        }

        int get() {
            return this.depth;
        }

        void inc() {
            ++this.depth;
        }

        void dec() {
            --this.depth;
        }
    }

}

