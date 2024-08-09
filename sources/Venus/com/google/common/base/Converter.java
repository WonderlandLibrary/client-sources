/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.base;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.concurrent.LazyInit;
import java.io.Serializable;
import java.util.Iterator;
import javax.annotation.Nullable;

@GwtCompatible
public abstract class Converter<A, B>
implements Function<A, B> {
    private final boolean handleNullAutomatically;
    @LazyInit
    private transient Converter<B, A> reverse;

    protected Converter() {
        this(true);
    }

    Converter(boolean bl) {
        this.handleNullAutomatically = bl;
    }

    protected abstract B doForward(A var1);

    protected abstract A doBackward(B var1);

    @Nullable
    @CanIgnoreReturnValue
    public final B convert(@Nullable A a) {
        return this.correctedDoForward(a);
    }

    @Nullable
    B correctedDoForward(@Nullable A a) {
        if (this.handleNullAutomatically) {
            return a == null ? null : (B)Preconditions.checkNotNull(this.doForward(a));
        }
        return this.doForward(a);
    }

    @Nullable
    A correctedDoBackward(@Nullable B b) {
        if (this.handleNullAutomatically) {
            return b == null ? null : (A)Preconditions.checkNotNull(this.doBackward(b));
        }
        return this.doBackward(b);
    }

    @CanIgnoreReturnValue
    public Iterable<B> convertAll(Iterable<? extends A> iterable) {
        Preconditions.checkNotNull(iterable, "fromIterable");
        return new Iterable<B>(this, iterable){
            final Iterable val$fromIterable;
            final Converter this$0;
            {
                this.this$0 = converter;
                this.val$fromIterable = iterable;
            }

            @Override
            public Iterator<B> iterator() {
                return new Iterator<B>(this){
                    private final Iterator<? extends A> fromIterator;
                    final 1 this$1;
                    {
                        this.this$1 = var1_1;
                        this.fromIterator = this.this$1.val$fromIterable.iterator();
                    }

                    @Override
                    public boolean hasNext() {
                        return this.fromIterator.hasNext();
                    }

                    @Override
                    public B next() {
                        return this.this$1.this$0.convert(this.fromIterator.next());
                    }

                    @Override
                    public void remove() {
                        this.fromIterator.remove();
                    }
                };
            }
        };
    }

    @CanIgnoreReturnValue
    public Converter<B, A> reverse() {
        Converter<A, B> converter = this.reverse;
        return converter == null ? (this.reverse = new ReverseConverter(this)) : converter;
    }

    @Override
    public final <C> Converter<A, C> andThen(Converter<B, C> converter) {
        return this.doAndThen(converter);
    }

    <C> Converter<A, C> doAndThen(Converter<B, C> converter) {
        return new ConverterComposition(this, Preconditions.checkNotNull(converter));
    }

    @Override
    @Deprecated
    @Nullable
    @CanIgnoreReturnValue
    public final B apply(@Nullable A a) {
        return this.convert(a);
    }

    @Override
    public boolean equals(@Nullable Object object) {
        return super.equals(object);
    }

    public static <A, B> Converter<A, B> from(Function<? super A, ? extends B> function, Function<? super B, ? extends A> function2) {
        return new FunctionBasedConverter(function, function2, null);
    }

    public static <T> Converter<T, T> identity() {
        return IdentityConverter.INSTANCE;
    }

    private static final class IdentityConverter<T>
    extends Converter<T, T>
    implements Serializable {
        static final IdentityConverter INSTANCE = new IdentityConverter();
        private static final long serialVersionUID = 0L;

        private IdentityConverter() {
        }

        @Override
        protected T doForward(T t) {
            return t;
        }

        @Override
        protected T doBackward(T t) {
            return t;
        }

        public IdentityConverter<T> reverse() {
            return this;
        }

        @Override
        <S> Converter<T, S> doAndThen(Converter<T, S> converter) {
            return Preconditions.checkNotNull(converter, "otherConverter");
        }

        public String toString() {
            return "Converter.identity()";
        }

        private Object readResolve() {
            return INSTANCE;
        }

        @Override
        public Converter reverse() {
            return this.reverse();
        }
    }

    private static final class FunctionBasedConverter<A, B>
    extends Converter<A, B>
    implements Serializable {
        private final Function<? super A, ? extends B> forwardFunction;
        private final Function<? super B, ? extends A> backwardFunction;

        private FunctionBasedConverter(Function<? super A, ? extends B> function, Function<? super B, ? extends A> function2) {
            this.forwardFunction = Preconditions.checkNotNull(function);
            this.backwardFunction = Preconditions.checkNotNull(function2);
        }

        @Override
        protected B doForward(A a) {
            return this.forwardFunction.apply(a);
        }

        @Override
        protected A doBackward(B b) {
            return this.backwardFunction.apply(b);
        }

        @Override
        public boolean equals(@Nullable Object object) {
            if (object instanceof FunctionBasedConverter) {
                FunctionBasedConverter functionBasedConverter = (FunctionBasedConverter)object;
                return this.forwardFunction.equals(functionBasedConverter.forwardFunction) && this.backwardFunction.equals(functionBasedConverter.backwardFunction);
            }
            return true;
        }

        public int hashCode() {
            return this.forwardFunction.hashCode() * 31 + this.backwardFunction.hashCode();
        }

        public String toString() {
            return "Converter.from(" + this.forwardFunction + ", " + this.backwardFunction + ")";
        }

        FunctionBasedConverter(Function function, Function function2, 1 var3_3) {
            this(function, function2);
        }
    }

    private static final class ConverterComposition<A, B, C>
    extends Converter<A, C>
    implements Serializable {
        final Converter<A, B> first;
        final Converter<B, C> second;
        private static final long serialVersionUID = 0L;

        ConverterComposition(Converter<A, B> converter, Converter<B, C> converter2) {
            this.first = converter;
            this.second = converter2;
        }

        @Override
        protected C doForward(A a) {
            throw new AssertionError();
        }

        @Override
        protected A doBackward(C c) {
            throw new AssertionError();
        }

        @Override
        @Nullable
        C correctedDoForward(@Nullable A a) {
            return this.second.correctedDoForward(this.first.correctedDoForward(a));
        }

        @Override
        @Nullable
        A correctedDoBackward(@Nullable C c) {
            return this.first.correctedDoBackward(this.second.correctedDoBackward(c));
        }

        @Override
        public boolean equals(@Nullable Object object) {
            if (object instanceof ConverterComposition) {
                ConverterComposition converterComposition = (ConverterComposition)object;
                return this.first.equals(converterComposition.first) && this.second.equals(converterComposition.second);
            }
            return true;
        }

        public int hashCode() {
            return 31 * this.first.hashCode() + this.second.hashCode();
        }

        public String toString() {
            return this.first + ".andThen(" + this.second + ")";
        }
    }

    private static final class ReverseConverter<A, B>
    extends Converter<B, A>
    implements Serializable {
        final Converter<A, B> original;
        private static final long serialVersionUID = 0L;

        ReverseConverter(Converter<A, B> converter) {
            this.original = converter;
        }

        @Override
        protected A doForward(B b) {
            throw new AssertionError();
        }

        @Override
        protected B doBackward(A a) {
            throw new AssertionError();
        }

        @Override
        @Nullable
        A correctedDoForward(@Nullable B b) {
            return this.original.correctedDoBackward(b);
        }

        @Override
        @Nullable
        B correctedDoBackward(@Nullable A a) {
            return this.original.correctedDoForward(a);
        }

        @Override
        public Converter<A, B> reverse() {
            return this.original;
        }

        @Override
        public boolean equals(@Nullable Object object) {
            if (object instanceof ReverseConverter) {
                ReverseConverter reverseConverter = (ReverseConverter)object;
                return this.original.equals(reverseConverter.original);
            }
            return true;
        }

        public int hashCode() {
            return ~this.original.hashCode();
        }

        public String toString() {
            return this.original + ".reverse()";
        }
    }
}

