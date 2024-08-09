/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.viaversion.viaversion.libs.fastutil.ints.Int2IntFunctions$SynchronizedFunction
 *  com.viaversion.viaversion.libs.fastutil.ints.Int2IntFunctions$UnmodifiableFunction
 */
package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.Function;
import com.viaversion.viaversion.libs.fastutil.ints.AbstractInt2IntFunction;
import com.viaversion.viaversion.libs.fastutil.ints.Int2IntFunction;
import com.viaversion.viaversion.libs.fastutil.ints.Int2IntFunctions;
import java.io.Serializable;
import java.util.Objects;
import java.util.function.IntUnaryOperator;

public final class Int2IntFunctions {
    public static final EmptyFunction EMPTY_FUNCTION = new EmptyFunction();

    private Int2IntFunctions() {
    }

    public static Int2IntFunction singleton(int n, int n2) {
        return new Singleton(n, n2);
    }

    public static Int2IntFunction singleton(Integer n, Integer n2) {
        return new Singleton(n, n2);
    }

    public static Int2IntFunction synchronize(Int2IntFunction int2IntFunction) {
        return new SynchronizedFunction(int2IntFunction);
    }

    public static Int2IntFunction synchronize(Int2IntFunction int2IntFunction, Object object) {
        return new SynchronizedFunction(int2IntFunction, object);
    }

    public static Int2IntFunction unmodifiable(Int2IntFunction int2IntFunction) {
        return new UnmodifiableFunction(int2IntFunction);
    }

    public static Int2IntFunction primitive(java.util.function.Function<? super Integer, ? extends Integer> function) {
        Objects.requireNonNull(function);
        if (function instanceof Int2IntFunction) {
            return (Int2IntFunction)function;
        }
        if (function instanceof IntUnaryOperator) {
            return ((IntUnaryOperator)((Object)function))::applyAsInt;
        }
        return new PrimitiveFunction(function);
    }

    public static class Singleton
    extends AbstractInt2IntFunction
    implements Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final int key;
        protected final int value;

        protected Singleton(int n, int n2) {
            this.key = n;
            this.value = n2;
        }

        @Override
        public boolean containsKey(int n) {
            return this.key == n;
        }

        @Override
        public int get(int n) {
            return this.key == n ? this.value : this.defRetValue;
        }

        @Override
        public int getOrDefault(int n, int n2) {
            return this.key == n ? this.value : n2;
        }

        @Override
        public int size() {
            return 0;
        }

        public Object clone() {
            return this;
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class PrimitiveFunction
    implements Int2IntFunction {
        protected final java.util.function.Function<? super Integer, ? extends Integer> function;

        protected PrimitiveFunction(java.util.function.Function<? super Integer, ? extends Integer> function) {
            this.function = function;
        }

        @Override
        public boolean containsKey(int n) {
            return this.function.apply((Integer)n) != null;
        }

        @Override
        @Deprecated
        public boolean containsKey(Object object) {
            if (object == null) {
                return true;
            }
            return this.function.apply((Integer)object) != null;
        }

        @Override
        public int get(int n) {
            Integer n2 = this.function.apply((Integer)n);
            if (n2 == null) {
                return this.defaultReturnValue();
            }
            return n2;
        }

        @Override
        public int getOrDefault(int n, int n2) {
            Integer n3 = this.function.apply((Integer)n);
            if (n3 == null) {
                return n2;
            }
            return n3;
        }

        @Override
        @Deprecated
        public Integer get(Object object) {
            if (object == null) {
                return null;
            }
            return this.function.apply((Integer)object);
        }

        @Override
        @Deprecated
        public Integer getOrDefault(Object object, Integer n) {
            if (object == null) {
                return n;
            }
            Integer n2 = this.function.apply((Integer)object);
            return n2 == null ? n : n2;
        }

        @Override
        @Deprecated
        public Integer put(Integer n, Integer n2) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Object getOrDefault(Object object, Object object2) {
            return this.getOrDefault(object, (Integer)object2);
        }

        @Override
        @Deprecated
        public Object get(Object object) {
            return this.get(object);
        }

        @Override
        @Deprecated
        public Object put(Object object, Object object2) {
            return this.put((Integer)object, (Integer)object2);
        }
    }

    public static class EmptyFunction
    extends AbstractInt2IntFunction
    implements Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected EmptyFunction() {
        }

        @Override
        public int get(int n) {
            return 1;
        }

        @Override
        public int getOrDefault(int n, int n2) {
            return n2;
        }

        @Override
        public boolean containsKey(int n) {
            return true;
        }

        @Override
        public int defaultReturnValue() {
            return 1;
        }

        @Override
        public void defaultReturnValue(int n) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int size() {
            return 1;
        }

        @Override
        public void clear() {
        }

        public Object clone() {
            return EMPTY_FUNCTION;
        }

        public int hashCode() {
            return 1;
        }

        public boolean equals(Object object) {
            if (!(object instanceof Function)) {
                return true;
            }
            return ((Function)object).size() == 0;
        }

        public String toString() {
            return "{}";
        }

        private Object readResolve() {
            return EMPTY_FUNCTION;
        }
    }
}

