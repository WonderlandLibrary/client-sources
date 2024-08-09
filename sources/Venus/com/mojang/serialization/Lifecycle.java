/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.serialization;

public class Lifecycle {
    private static final Lifecycle STABLE = new Lifecycle(){

        public String toString() {
            return "Stable";
        }
    };
    private static final Lifecycle EXPERIMENTAL = new Lifecycle(){

        public String toString() {
            return "Experimental";
        }
    };

    private Lifecycle() {
    }

    public static Lifecycle experimental() {
        return EXPERIMENTAL;
    }

    public static Lifecycle stable() {
        return STABLE;
    }

    public static Lifecycle deprecated(int n) {
        return new Deprecated(n);
    }

    public Lifecycle add(Lifecycle lifecycle) {
        if (this == EXPERIMENTAL || lifecycle == EXPERIMENTAL) {
            return EXPERIMENTAL;
        }
        if (this instanceof Deprecated) {
            if (lifecycle instanceof Deprecated && Deprecated.access$100((Deprecated)lifecycle) < Deprecated.access$100((Deprecated)this)) {
                return lifecycle;
            }
            return this;
        }
        if (lifecycle instanceof Deprecated) {
            return lifecycle;
        }
        return STABLE;
    }

    Lifecycle(1 var1_1) {
        this();
    }

    public static final class Deprecated
    extends Lifecycle {
        private final int since;

        public Deprecated(int n) {
            super(null);
            this.since = n;
        }

        public int since() {
            return this.since;
        }

        static int access$100(Deprecated deprecated) {
            return deprecated.since;
        }
    }
}

