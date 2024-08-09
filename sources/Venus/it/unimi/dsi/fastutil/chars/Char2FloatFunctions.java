/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.Function;
import it.unimi.dsi.fastutil.SafeMath;
import it.unimi.dsi.fastutil.chars.AbstractChar2FloatFunction;
import it.unimi.dsi.fastutil.chars.Char2FloatFunction;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Objects;
import java.util.function.IntToDoubleFunction;

public final class Char2FloatFunctions {
    public static final EmptyFunction EMPTY_FUNCTION = new EmptyFunction();

    private Char2FloatFunctions() {
    }

    public static Char2FloatFunction singleton(char c, float f) {
        return new Singleton(c, f);
    }

    public static Char2FloatFunction singleton(Character c, Float f) {
        return new Singleton(c.charValue(), f.floatValue());
    }

    public static Char2FloatFunction synchronize(Char2FloatFunction char2FloatFunction) {
        return new SynchronizedFunction(char2FloatFunction);
    }

    public static Char2FloatFunction synchronize(Char2FloatFunction char2FloatFunction, Object object) {
        return new SynchronizedFunction(char2FloatFunction, object);
    }

    public static Char2FloatFunction unmodifiable(Char2FloatFunction char2FloatFunction) {
        return new UnmodifiableFunction(char2FloatFunction);
    }

    public static Char2FloatFunction primitive(java.util.function.Function<? super Character, ? extends Float> function) {
        Objects.requireNonNull(function);
        if (function instanceof Char2FloatFunction) {
            return (Char2FloatFunction)function;
        }
        if (function instanceof IntToDoubleFunction) {
            return arg_0 -> Char2FloatFunctions.lambda$primitive$0(function, arg_0);
        }
        return new PrimitiveFunction(function);
    }

    private static float lambda$primitive$0(java.util.function.Function function, char c) {
        return SafeMath.safeDoubleToFloat(((IntToDoubleFunction)((Object)function)).applyAsDouble(c));
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class PrimitiveFunction
    implements Char2FloatFunction {
        protected final java.util.function.Function<? super Character, ? extends Float> function;

        protected PrimitiveFunction(java.util.function.Function<? super Character, ? extends Float> function) {
            this.function = function;
        }

        @Override
        public boolean containsKey(char c) {
            return this.function.apply(Character.valueOf(c)) != null;
        }

        @Override
        @Deprecated
        public boolean containsKey(Object object) {
            if (object == null) {
                return true;
            }
            return this.function.apply((Character)object) != null;
        }

        @Override
        public float get(char c) {
            Float f = this.function.apply(Character.valueOf(c));
            if (f == null) {
                return this.defaultReturnValue();
            }
            return f.floatValue();
        }

        @Override
        @Deprecated
        public Float get(Object object) {
            if (object == null) {
                return null;
            }
            return this.function.apply((Character)object);
        }

        @Override
        @Deprecated
        public Float put(Character c, Float f) {
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
            return this.put((Character)object, (Float)object2);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class UnmodifiableFunction
    extends AbstractChar2FloatFunction
    implements Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Char2FloatFunction function;

        protected UnmodifiableFunction(Char2FloatFunction char2FloatFunction) {
            if (char2FloatFunction == null) {
                throw new NullPointerException();
            }
            this.function = char2FloatFunction;
        }

        @Override
        public int size() {
            return this.function.size();
        }

        @Override
        public float defaultReturnValue() {
            return this.function.defaultReturnValue();
        }

        @Override
        public void defaultReturnValue(float f) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean containsKey(char c) {
            return this.function.containsKey(c);
        }

        @Override
        public float put(char c, float f) {
            throw new UnsupportedOperationException();
        }

        @Override
        public float get(char c) {
            return this.function.get(c);
        }

        @Override
        public float remove(char c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void clear() {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Float put(Character c, Float f) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Float get(Object object) {
            return this.function.get(object);
        }

        @Override
        @Deprecated
        public Float remove(Object object) {
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
            return this.put((Character)object, (Float)object2);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class SynchronizedFunction
    implements Char2FloatFunction,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Char2FloatFunction function;
        protected final Object sync;

        protected SynchronizedFunction(Char2FloatFunction char2FloatFunction, Object object) {
            if (char2FloatFunction == null) {
                throw new NullPointerException();
            }
            this.function = char2FloatFunction;
            this.sync = object;
        }

        protected SynchronizedFunction(Char2FloatFunction char2FloatFunction) {
            if (char2FloatFunction == null) {
                throw new NullPointerException();
            }
            this.function = char2FloatFunction;
            this.sync = this;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public double applyAsDouble(int n) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.applyAsDouble(n);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Float apply(Character c) {
            Object object = this.sync;
            synchronized (object) {
                return (Float)this.function.apply(c);
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
        public float defaultReturnValue() {
            Object object = this.sync;
            synchronized (object) {
                return this.function.defaultReturnValue();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void defaultReturnValue(float f) {
            Object object = this.sync;
            synchronized (object) {
                this.function.defaultReturnValue(f);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean containsKey(char c) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.containsKey(c);
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
        public float put(char c, float f) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.put(c, f);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public float get(char c) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.get(c);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public float remove(char c) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.remove(c);
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
        public Float put(Character c, Float f) {
            Object object = this.sync;
            synchronized (object) {
                return this.function.put(c, f);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Float get(Object object) {
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
        public Float remove(Object object) {
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
            return this.put((Character)object, (Float)object2);
        }

        @Override
        @Deprecated
        public Object apply(Object object) {
            return this.apply((Character)object);
        }
    }

    public static class Singleton
    extends AbstractChar2FloatFunction
    implements Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final char key;
        protected final float value;

        protected Singleton(char c, float f) {
            this.key = c;
            this.value = f;
        }

        @Override
        public boolean containsKey(char c) {
            return this.key == c;
        }

        @Override
        public float get(char c) {
            return this.key == c ? this.value : this.defRetValue;
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
    extends AbstractChar2FloatFunction
    implements Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected EmptyFunction() {
        }

        @Override
        public float get(char c) {
            return 0.0f;
        }

        @Override
        public boolean containsKey(char c) {
            return true;
        }

        @Override
        public float defaultReturnValue() {
            return 0.0f;
        }

        @Override
        public void defaultReturnValue(float f) {
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

