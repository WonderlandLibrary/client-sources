/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.BoundType;
import com.google.common.collect.DiscreteDomain;
import com.google.common.collect.Range;
import com.google.common.primitives.Booleans;
import java.io.Serializable;
import java.util.NoSuchElementException;
import javax.annotation.Nullable;

@GwtCompatible
abstract class Cut<C extends Comparable>
implements Comparable<Cut<C>>,
Serializable {
    final C endpoint;
    private static final long serialVersionUID = 0L;

    Cut(@Nullable C c) {
        this.endpoint = c;
    }

    abstract boolean isLessThan(C var1);

    abstract BoundType typeAsLowerBound();

    abstract BoundType typeAsUpperBound();

    abstract Cut<C> withLowerBoundType(BoundType var1, DiscreteDomain<C> var2);

    abstract Cut<C> withUpperBoundType(BoundType var1, DiscreteDomain<C> var2);

    abstract void describeAsLowerBound(StringBuilder var1);

    abstract void describeAsUpperBound(StringBuilder var1);

    abstract C leastValueAbove(DiscreteDomain<C> var1);

    abstract C greatestValueBelow(DiscreteDomain<C> var1);

    Cut<C> canonical(DiscreteDomain<C> discreteDomain) {
        return this;
    }

    @Override
    public int compareTo(Cut<C> cut) {
        if (cut == Cut.belowAll()) {
            return 0;
        }
        if (cut == Cut.aboveAll()) {
            return 1;
        }
        int n = Range.compareOrThrow(this.endpoint, cut.endpoint);
        if (n != 0) {
            return n;
        }
        return Booleans.compare(this instanceof AboveValue, cut instanceof AboveValue);
    }

    C endpoint() {
        return this.endpoint;
    }

    public boolean equals(Object object) {
        if (object instanceof Cut) {
            Cut cut = (Cut)object;
            try {
                int n = this.compareTo(cut);
                return n == 0;
            } catch (ClassCastException classCastException) {
                // empty catch block
            }
        }
        return true;
    }

    static <C extends Comparable> Cut<C> belowAll() {
        return BelowAll.access$000();
    }

    static <C extends Comparable> Cut<C> aboveAll() {
        return AboveAll.access$100();
    }

    static <C extends Comparable> Cut<C> belowValue(C c) {
        return new BelowValue<C>(c);
    }

    static <C extends Comparable> Cut<C> aboveValue(C c) {
        return new AboveValue<C>(c);
    }

    @Override
    public int compareTo(Object object) {
        return this.compareTo((Cut)object);
    }

    private static final class AboveValue<C extends Comparable>
    extends Cut<C> {
        private static final long serialVersionUID = 0L;

        AboveValue(C c) {
            super((Comparable)Preconditions.checkNotNull(c));
        }

        @Override
        boolean isLessThan(C c) {
            return Range.compareOrThrow(this.endpoint, c) < 0;
        }

        @Override
        BoundType typeAsLowerBound() {
            return BoundType.OPEN;
        }

        @Override
        BoundType typeAsUpperBound() {
            return BoundType.CLOSED;
        }

        @Override
        Cut<C> withLowerBoundType(BoundType boundType, DiscreteDomain<C> discreteDomain) {
            switch (1.$SwitchMap$com$google$common$collect$BoundType[boundType.ordinal()]) {
                case 2: {
                    return this;
                }
                case 1: {
                    Comparable comparable = discreteDomain.next(this.endpoint);
                    return comparable == null ? Cut.belowAll() : AboveValue.belowValue(comparable);
                }
            }
            throw new AssertionError();
        }

        @Override
        Cut<C> withUpperBoundType(BoundType boundType, DiscreteDomain<C> discreteDomain) {
            switch (1.$SwitchMap$com$google$common$collect$BoundType[boundType.ordinal()]) {
                case 2: {
                    Comparable comparable = discreteDomain.next(this.endpoint);
                    return comparable == null ? Cut.aboveAll() : AboveValue.belowValue(comparable);
                }
                case 1: {
                    return this;
                }
            }
            throw new AssertionError();
        }

        @Override
        void describeAsLowerBound(StringBuilder stringBuilder) {
            stringBuilder.append('(').append(this.endpoint);
        }

        @Override
        void describeAsUpperBound(StringBuilder stringBuilder) {
            stringBuilder.append(this.endpoint).append(']');
        }

        @Override
        C leastValueAbove(DiscreteDomain<C> discreteDomain) {
            return (C)discreteDomain.next(this.endpoint);
        }

        @Override
        C greatestValueBelow(DiscreteDomain<C> discreteDomain) {
            return (C)this.endpoint;
        }

        @Override
        Cut<C> canonical(DiscreteDomain<C> discreteDomain) {
            C c = this.leastValueAbove(discreteDomain);
            return c != null ? AboveValue.belowValue(c) : Cut.aboveAll();
        }

        public int hashCode() {
            return ~this.endpoint.hashCode();
        }

        public String toString() {
            return "/" + this.endpoint + "\\";
        }

        @Override
        public int compareTo(Object object) {
            return super.compareTo((Cut)object);
        }
    }

    private static final class BelowValue<C extends Comparable>
    extends Cut<C> {
        private static final long serialVersionUID = 0L;

        BelowValue(C c) {
            super((Comparable)Preconditions.checkNotNull(c));
        }

        @Override
        boolean isLessThan(C c) {
            return Range.compareOrThrow(this.endpoint, c) <= 0;
        }

        @Override
        BoundType typeAsLowerBound() {
            return BoundType.CLOSED;
        }

        @Override
        BoundType typeAsUpperBound() {
            return BoundType.OPEN;
        }

        @Override
        Cut<C> withLowerBoundType(BoundType boundType, DiscreteDomain<C> discreteDomain) {
            switch (1.$SwitchMap$com$google$common$collect$BoundType[boundType.ordinal()]) {
                case 1: {
                    return this;
                }
                case 2: {
                    Comparable comparable = discreteDomain.previous(this.endpoint);
                    return comparable == null ? Cut.belowAll() : new AboveValue<Comparable>(comparable);
                }
            }
            throw new AssertionError();
        }

        @Override
        Cut<C> withUpperBoundType(BoundType boundType, DiscreteDomain<C> discreteDomain) {
            switch (1.$SwitchMap$com$google$common$collect$BoundType[boundType.ordinal()]) {
                case 1: {
                    Comparable comparable = discreteDomain.previous(this.endpoint);
                    return comparable == null ? Cut.aboveAll() : new AboveValue<Comparable>(comparable);
                }
                case 2: {
                    return this;
                }
            }
            throw new AssertionError();
        }

        @Override
        void describeAsLowerBound(StringBuilder stringBuilder) {
            stringBuilder.append('[').append(this.endpoint);
        }

        @Override
        void describeAsUpperBound(StringBuilder stringBuilder) {
            stringBuilder.append(this.endpoint).append(')');
        }

        @Override
        C leastValueAbove(DiscreteDomain<C> discreteDomain) {
            return (C)this.endpoint;
        }

        @Override
        C greatestValueBelow(DiscreteDomain<C> discreteDomain) {
            return (C)discreteDomain.previous(this.endpoint);
        }

        public int hashCode() {
            return this.endpoint.hashCode();
        }

        public String toString() {
            return "\\" + this.endpoint + "/";
        }

        @Override
        public int compareTo(Object object) {
            return super.compareTo((Cut)object);
        }
    }

    private static final class AboveAll
    extends Cut<Comparable<?>> {
        private static final AboveAll INSTANCE = new AboveAll();
        private static final long serialVersionUID = 0L;

        private AboveAll() {
            super(null);
        }

        @Override
        Comparable<?> endpoint() {
            throw new IllegalStateException("range unbounded on this side");
        }

        @Override
        boolean isLessThan(Comparable<?> comparable) {
            return true;
        }

        @Override
        BoundType typeAsLowerBound() {
            throw new AssertionError((Object)"this statement should be unreachable");
        }

        @Override
        BoundType typeAsUpperBound() {
            throw new IllegalStateException();
        }

        @Override
        Cut<Comparable<?>> withLowerBoundType(BoundType boundType, DiscreteDomain<Comparable<?>> discreteDomain) {
            throw new AssertionError((Object)"this statement should be unreachable");
        }

        @Override
        Cut<Comparable<?>> withUpperBoundType(BoundType boundType, DiscreteDomain<Comparable<?>> discreteDomain) {
            throw new IllegalStateException();
        }

        @Override
        void describeAsLowerBound(StringBuilder stringBuilder) {
            throw new AssertionError();
        }

        @Override
        void describeAsUpperBound(StringBuilder stringBuilder) {
            stringBuilder.append("+\u221e)");
        }

        @Override
        Comparable<?> leastValueAbove(DiscreteDomain<Comparable<?>> discreteDomain) {
            throw new AssertionError();
        }

        @Override
        Comparable<?> greatestValueBelow(DiscreteDomain<Comparable<?>> discreteDomain) {
            return discreteDomain.maxValue();
        }

        @Override
        public int compareTo(Cut<Comparable<?>> cut) {
            return cut == this ? 0 : 1;
        }

        public String toString() {
            return "+\u221e";
        }

        private Object readResolve() {
            return INSTANCE;
        }

        @Override
        public int compareTo(Object object) {
            return this.compareTo((Cut)object);
        }

        static AboveAll access$100() {
            return INSTANCE;
        }
    }

    private static final class BelowAll
    extends Cut<Comparable<?>> {
        private static final BelowAll INSTANCE = new BelowAll();
        private static final long serialVersionUID = 0L;

        private BelowAll() {
            super(null);
        }

        @Override
        Comparable<?> endpoint() {
            throw new IllegalStateException("range unbounded on this side");
        }

        @Override
        boolean isLessThan(Comparable<?> comparable) {
            return false;
        }

        @Override
        BoundType typeAsLowerBound() {
            throw new IllegalStateException();
        }

        @Override
        BoundType typeAsUpperBound() {
            throw new AssertionError((Object)"this statement should be unreachable");
        }

        @Override
        Cut<Comparable<?>> withLowerBoundType(BoundType boundType, DiscreteDomain<Comparable<?>> discreteDomain) {
            throw new IllegalStateException();
        }

        @Override
        Cut<Comparable<?>> withUpperBoundType(BoundType boundType, DiscreteDomain<Comparable<?>> discreteDomain) {
            throw new AssertionError((Object)"this statement should be unreachable");
        }

        @Override
        void describeAsLowerBound(StringBuilder stringBuilder) {
            stringBuilder.append("(-\u221e");
        }

        @Override
        void describeAsUpperBound(StringBuilder stringBuilder) {
            throw new AssertionError();
        }

        @Override
        Comparable<?> leastValueAbove(DiscreteDomain<Comparable<?>> discreteDomain) {
            return discreteDomain.minValue();
        }

        @Override
        Comparable<?> greatestValueBelow(DiscreteDomain<Comparable<?>> discreteDomain) {
            throw new AssertionError();
        }

        @Override
        Cut<Comparable<?>> canonical(DiscreteDomain<Comparable<?>> discreteDomain) {
            try {
                return Cut.belowValue(discreteDomain.minValue());
            } catch (NoSuchElementException noSuchElementException) {
                return this;
            }
        }

        @Override
        public int compareTo(Cut<Comparable<?>> cut) {
            return cut == this ? 0 : -1;
        }

        public String toString() {
            return "-\u221e";
        }

        private Object readResolve() {
            return INSTANCE;
        }

        @Override
        public int compareTo(Object object) {
            return this.compareTo((Cut)object);
        }

        static BelowAll access$000() {
            return INSTANCE;
        }
    }
}

