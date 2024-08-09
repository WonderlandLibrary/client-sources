/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.Function;
import it.unimi.dsi.fastutil.doubles.AbstractDouble2BooleanFunction;
import it.unimi.dsi.fastutil.doubles.Double2BooleanFunction;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Objects;
import java.util.function.DoublePredicate;

public final class Double2BooleanFunctions {
    public static final EmptyFunction EMPTY_FUNCTION = new EmptyFunction();

    private Double2BooleanFunctions() {
    }

    public static Double2BooleanFunction singleton(double d, boolean bl) {
        return new Singleton(d, bl);
    }

    public static Double2BooleanFunction singleton(Double d, Boolean bl) {
        return new Singleton(d, bl);
    }

    public static Double2BooleanFunction synchronize(Double2BooleanFunction double2BooleanFunction) {
        return new SynchronizedFunction(double2BooleanFunction);
    }

    public static Double2BooleanFunction synchronize(Double2BooleanFunction double2BooleanFunction, Object object) {
        return new SynchronizedFunction(double2BooleanFunction, object);
    }

    public static Double2BooleanFunction unmodifiable(Double2BooleanFunction double2BooleanFunction) {
        return new UnmodifiableFunction(double2BooleanFunction);
    }

    public static Double2BooleanFunction primitive(java.util.function.Function<? super Double, ? extends Boolean> function) {
        Objects.requireNonNull(function);
        if (function instanceof Double2BooleanFunction) {
            return (Double2BooleanFunction)function;
        }
        if (function instanceof DoublePredicate) {
            return ((DoublePredicate)((Object)function))::test;
        }
        return new PrimitiveFunction(function);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class PrimitiveFunction
    implements Double2BooleanFunction {
        protected final java.util.function.Function<? super Double, ? extends Boolean> function;

        protected PrimitiveFunction(java.util.function.Function<? super Double, ? extends Boolean> function) {
            this.function = function;
        }

        @Override
        public boolean containsKey(double d) {
            return this.function.apply((Double)d) != null;
        }

        @Override
        @Deprecated
        public boolean containsKey(Object object) {
            if (object == null) {
                return true;
            }
            return this.function.apply((Double)object) != null;
        }

        @Override
        public boolean get(double d) {
            Boolean bl = this.function.apply((Double)d);
            if (bl == null) {
                return this.defaultReturnValue();
            }
            return bl;
        }

        @Override
        @Deprecated
        public Boolean get(Object object) {
            if (object == null) {
                return null;
            }
            return this.function.apply((Double)object);
        }

        @Override
        @Deprecated
        public Boolean put(Double d, Boolean bl) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Object get(Object object) {
            return this.get(object);
        }

        @Override
        @Deprecated
        public Object put(Object object, Object object2) {
            return this.put((Double)object, (Boolean)object2);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class UnmodifiableFunction
    extends AbstractDouble2BooleanFunction
    implements Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Double2BooleanFunction function;

        protected UnmodifiableFunction(Double2BooleanFunction double2BooleanFunction) {
            if (double2BooleanFunction == null) {
                throw new NullPointerException();
            }
            this.function = double2BooleanFunction;
        }

        @Override
        public int size() {
            return this.function.size();
        }

        @Override
        public boolean defaultReturnValue() {
            return this.function.defaultReturnValue();
        }

        @Override
        public void defaultReturnValue(boolean bl) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean containsKey(double d) {
            return this.function.containsKey(d);
        }

        @Override
        public boolean put(double d, boolean bl) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean get(double d) {
            return this.function.get(d);
        }

        @Override
        public boolean remove(double d) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void clear() {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Boolean put(Double d, Boolean bl) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Boolean get(Object object) {
            return this.function.get(object);
        }

        @Override
        @Deprecated
        public Boolean remove(Object object) {
            throw new UnsupportedOperationException();
        }

        public int hashCode() {
            return this.function.hashCode();
        }

        public boolean equals(Object object) {
            return object == this || this.function.equals(object);
        }

        public String toString() {
            return this.function.toString();
        }

        @Override
        @Deprecated
        public Object remove(Object object) {
            return this.remove(object);
        }

        @Override
        @Deprecated
        public Object get(Object object) {
            return this.get(object);
        }

        @Override
        @Deprecated
        public Object put(Object object, Object object2) {
            return this.put((Double)object, (Boolean)object2);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class SynchronizedFunction
    implements Double2BooleanFunction,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Double2BooleanFunction function;
        protected final Object sync;

        protected SynchronizedFunction(Double2BooleanFunction double2BooleanFunction, Object object) {
            if (double2BooleanFunction == null) {
                throw new NullPointerException();
            }
            this.function = double2BooleanFunction;
            this.sync = object;
        }

        protected SynchronizedFunction(Double2BooleanFunction double2BooleanFunction) {
            if (double2BooleanFunction == null) {
                throw new NullPointerException();
            }
            this.function = double2BooleanFunction;
            this.sync = this;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean test(double d) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.test(d);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Boolean apply(Double d) {
            Object object = this.sync;
            synchronized (object) {
                return (Boolean)this.function.apply(d);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public int size() {
            Object object = this.sync;
            synchronized (object) {
                return this.function.size();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean defaultReturnValue() {
            Object object = this.sync;
            synchronized (object) {
                return this.function.defaultReturnValue();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void defaultReturnValue(boolean bl) {
            Object object = this.sync;
            synchronized (object) {
                this.function.defaultReturnValue(bl);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean containsKey(double d) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.containsKey(d);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public boolean containsKey(Object object) {
            Object object2 = this.sync;
            synchronized (object2) {
                return this.function.containsKey(object);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean put(double d, boolean bl) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.put(d, bl);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean get(double d) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.get(d);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean remove(double d) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.remove(d);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void clear() {
            Object object = this.sync;
            synchronized (object) {
                this.function.clear();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Boolean put(Double d, Boolean bl) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.put(d, bl);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Boolean get(Object object) {
            Object object2 = this.sync;
            synchronized (object2) {
                return this.function.get(object);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Boolean remove(Object object) {
            Object object2 = this.sync;
            synchronized (object2) {
                return this.function.remove(object);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        public int hashCode() {
            Object object = this.sync;
            synchronized (object) {
                return this.function.hashCode();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        public boolean equals(Object object) {
            if (object == this) {
                return false;
            }
            Object object2 = this.sync;
            synchronized (object2) {
                return this.function.equals(object);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        public String toString() {
            Object object = this.sync;
            synchronized (object) {
                return this.function.toString();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
            Object object = this.sync;
            synchronized (object) {
                objectOutputStream.defaultWriteObject();
            }
        }

        @Override
        @Deprecated
        public Object remove(Object object) {
            return this.remove(object);
        }

        @Override
        @Deprecated
        public Object get(Object object) {
            return this.get(object);
        }

        @Override
        @Deprecated
        public Object put(Object object, Object object2) {
            return this.put((Double)object, (Boolean)object2);
        }

        @Override
        @Deprecated
        public Object apply(Object object) {
            return this.apply((Double)object);
        }
    }

    public static class Singleton
    extends AbstractDouble2BooleanFunction
    implements Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final double key;
        protected final boolean value;

        protected Singleton(double d, boolean bl) {
            this.key = d;
            this.value = bl;
        }

        @Override
        public boolean containsKey(double d) {
            return Double.doubleToLongBits(this.key) == Double.doubleToLongBits(d);
        }

        @Override
        public boolean get(double d) {
            return Double.doubleToLongBits(this.key) == Double.doubleToLongBits(d) ? this.value : this.defRetValue;
        }

        @Override
        public int size() {
            return 0;
        }

        public Object clone() {
            return this;
        }
    }

    public static class EmptyFunction
    extends AbstractDouble2BooleanFunction
    implements Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected EmptyFunction() {
        }

        @Override
        public boolean get(double d) {
            return true;
        }

        @Override
        public boolean containsKey(double d) {
            return true;
        }

        @Override
        public boolean defaultReturnValue() {
            return true;
        }

        @Override
        public void defaultReturnValue(boolean bl) {
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

