/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.Function;
import it.unimi.dsi.fastutil.SafeMath;
import it.unimi.dsi.fastutil.doubles.AbstractDouble2CharFunction;
import it.unimi.dsi.fastutil.doubles.Double2CharFunction;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Objects;
import java.util.function.DoubleToIntFunction;

public final class Double2CharFunctions {
    public static final EmptyFunction EMPTY_FUNCTION = new EmptyFunction();

    private Double2CharFunctions() {
    }

    public static Double2CharFunction singleton(double d, char c) {
        return new Singleton(d, c);
    }

    public static Double2CharFunction singleton(Double d, Character c) {
        return new Singleton(d, c.charValue());
    }

    public static Double2CharFunction synchronize(Double2CharFunction double2CharFunction) {
        return new SynchronizedFunction(double2CharFunction);
    }

    public static Double2CharFunction synchronize(Double2CharFunction double2CharFunction, Object object) {
        return new SynchronizedFunction(double2CharFunction, object);
    }

    public static Double2CharFunction unmodifiable(Double2CharFunction double2CharFunction) {
        return new UnmodifiableFunction(double2CharFunction);
    }

    public static Double2CharFunction primitive(java.util.function.Function<? super Double, ? extends Character> function) {
        Objects.requireNonNull(function);
        if (function instanceof Double2CharFunction) {
            return (Double2CharFunction)function;
        }
        if (function instanceof DoubleToIntFunction) {
            return arg_0 -> Double2CharFunctions.lambda$primitive$0(function, arg_0);
        }
        return new PrimitiveFunction(function);
    }

    private static char lambda$primitive$0(java.util.function.Function function, double d) {
        return SafeMath.safeIntToChar(((DoubleToIntFunction)((Object)function)).applyAsInt(d));
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class PrimitiveFunction
    implements Double2CharFunction {
        protected final java.util.function.Function<? super Double, ? extends Character> function;

        protected PrimitiveFunction(java.util.function.Function<? super Double, ? extends Character> function) {
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
        public char get(double d) {
            Character c = this.function.apply((Double)d);
            if (c == null) {
                return this.defaultReturnValue();
            }
            return c.charValue();
        }

        @Override
        @Deprecated
        public Character get(Object object) {
            if (object == null) {
                return null;
            }
            return this.function.apply((Double)object);
        }

        @Override
        @Deprecated
        public Character put(Double d, Character c) {
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
            return this.put((Double)object, (Character)object2);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class UnmodifiableFunction
    extends AbstractDouble2CharFunction
    implements Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Double2CharFunction function;

        protected UnmodifiableFunction(Double2CharFunction double2CharFunction) {
            if (double2CharFunction == null) {
                throw new NullPointerException();
            }
            this.function = double2CharFunction;
        }

        @Override
        public int size() {
            return this.function.size();
        }

        @Override
        public char defaultReturnValue() {
            return this.function.defaultReturnValue();
        }

        @Override
        public void defaultReturnValue(char c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean containsKey(double d) {
            return this.function.containsKey(d);
        }

        @Override
        public char put(double d, char c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public char get(double d) {
            return this.function.get(d);
        }

        @Override
        public char remove(double d) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void clear() {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Character put(Double d, Character c) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Character get(Object object) {
            return this.function.get(object);
        }

        @Override
        @Deprecated
        public Character remove(Object object) {
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
            return this.put((Double)object, (Character)object2);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class SynchronizedFunction
    implements Double2CharFunction,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Double2CharFunction function;
        protected final Object sync;

        protected SynchronizedFunction(Double2CharFunction double2CharFunction, Object object) {
            if (double2CharFunction == null) {
                throw new NullPointerException();
            }
            this.function = double2CharFunction;
            this.sync = object;
        }

        protected SynchronizedFunction(Double2CharFunction double2CharFunction) {
            if (double2CharFunction == null) {
                throw new NullPointerException();
            }
            this.function = double2CharFunction;
            this.sync = this;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public int applyAsInt(double d) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.applyAsInt(d);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Character apply(Double d) {
            Object object = this.sync;
            synchronized (object) {
                return (Character)this.function.apply(d);
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
        public char defaultReturnValue() {
            Object object = this.sync;
            synchronized (object) {
                return this.function.defaultReturnValue();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void defaultReturnValue(char c) {
            Object object = this.sync;
            synchronized (object) {
                this.function.defaultReturnValue(c);
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
        public char put(double d, char c) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.put(d, c);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public char get(double d) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.get(d);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public char remove(double d) {
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
        public Character put(Double d, Character c) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.put(d, c);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Character get(Object object) {
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
        public Character remove(Object object) {
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
            return this.put((Double)object, (Character)object2);
        }

        @Override
        @Deprecated
        public Object apply(Object object) {
            return this.apply((Double)object);
        }
    }

    public static class Singleton
    extends AbstractDouble2CharFunction
    implements Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final double key;
        protected final char value;

        protected Singleton(double d, char c) {
            this.key = d;
            this.value = c;
        }

        @Override
        public boolean containsKey(double d) {
            return Double.doubleToLongBits(this.key) == Double.doubleToLongBits(d);
        }

        @Override
        public char get(double d) {
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
    extends AbstractDouble2CharFunction
    implements Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected EmptyFunction() {
        }

        @Override
        public char get(double d) {
            return '\u0001';
        }

        @Override
        public boolean containsKey(double d) {
            return true;
        }

        @Override
        public char defaultReturnValue() {
            return '\u0001';
        }

        @Override
        public void defaultReturnValue(char c) {
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

