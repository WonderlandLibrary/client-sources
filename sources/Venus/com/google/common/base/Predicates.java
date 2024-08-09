/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.base;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.CommonPattern;
import com.google.common.base.Function;
import com.google.common.base.JdkPattern;
import com.google.common.base.Joiner;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.base.Platform;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;
import javax.annotation.Nullable;

@GwtCompatible(emulated=true)
public final class Predicates {
    private static final Joiner COMMA_JOINER = Joiner.on(',');

    private Predicates() {
    }

    @GwtCompatible(serializable=true)
    public static <T> Predicate<T> alwaysTrue() {
        return ObjectPredicate.ALWAYS_TRUE.withNarrowedType();
    }

    @GwtCompatible(serializable=true)
    public static <T> Predicate<T> alwaysFalse() {
        return ObjectPredicate.ALWAYS_FALSE.withNarrowedType();
    }

    @GwtCompatible(serializable=true)
    public static <T> Predicate<T> isNull() {
        return ObjectPredicate.IS_NULL.withNarrowedType();
    }

    @GwtCompatible(serializable=true)
    public static <T> Predicate<T> notNull() {
        return ObjectPredicate.NOT_NULL.withNarrowedType();
    }

    public static <T> Predicate<T> not(Predicate<T> predicate) {
        return new NotPredicate<T>(predicate);
    }

    public static <T> Predicate<T> and(Iterable<? extends Predicate<? super T>> iterable) {
        return new AndPredicate(Predicates.defensiveCopy(iterable), null);
    }

    public static <T> Predicate<T> and(Predicate<? super T> ... predicateArray) {
        return new AndPredicate(Predicates.defensiveCopy(predicateArray), null);
    }

    public static <T> Predicate<T> and(Predicate<? super T> predicate, Predicate<? super T> predicate2) {
        return new AndPredicate(Predicates.asList(Preconditions.checkNotNull(predicate), Preconditions.checkNotNull(predicate2)), null);
    }

    public static <T> Predicate<T> or(Iterable<? extends Predicate<? super T>> iterable) {
        return new OrPredicate(Predicates.defensiveCopy(iterable), null);
    }

    public static <T> Predicate<T> or(Predicate<? super T> ... predicateArray) {
        return new OrPredicate(Predicates.defensiveCopy(predicateArray), null);
    }

    public static <T> Predicate<T> or(Predicate<? super T> predicate, Predicate<? super T> predicate2) {
        return new OrPredicate(Predicates.asList(Preconditions.checkNotNull(predicate), Preconditions.checkNotNull(predicate2)), null);
    }

    public static <T> Predicate<T> equalTo(@Nullable T t) {
        return t == null ? Predicates.isNull() : new IsEqualToPredicate(t, null);
    }

    @GwtIncompatible
    public static Predicate<Object> instanceOf(Class<?> clazz) {
        return new InstanceOfPredicate(clazz, null);
    }

    @Deprecated
    @GwtIncompatible
    @Beta
    public static Predicate<Class<?>> assignableFrom(Class<?> clazz) {
        return Predicates.subtypeOf(clazz);
    }

    @GwtIncompatible
    @Beta
    public static Predicate<Class<?>> subtypeOf(Class<?> clazz) {
        return new SubtypeOfPredicate(clazz, null);
    }

    public static <T> Predicate<T> in(Collection<? extends T> collection) {
        return new InPredicate(collection, null);
    }

    public static <A, B> Predicate<A> compose(Predicate<B> predicate, Function<A, ? extends B> function) {
        return new CompositionPredicate(predicate, function, null);
    }

    @GwtIncompatible
    public static Predicate<CharSequence> containsPattern(String string) {
        return new ContainsPatternFromStringPredicate(string);
    }

    @GwtIncompatible(value="java.util.regex.Pattern")
    public static Predicate<CharSequence> contains(Pattern pattern) {
        return new ContainsPatternPredicate(new JdkPattern(pattern));
    }

    private static <T> List<Predicate<? super T>> asList(Predicate<? super T> predicate, Predicate<? super T> predicate2) {
        return Arrays.asList(predicate, predicate2);
    }

    private static <T> List<T> defensiveCopy(T ... TArray) {
        return Predicates.defensiveCopy(Arrays.asList(TArray));
    }

    static <T> List<T> defensiveCopy(Iterable<T> iterable) {
        ArrayList<T> arrayList = new ArrayList<T>();
        for (T t : iterable) {
            arrayList.add(Preconditions.checkNotNull(t));
        }
        return arrayList;
    }

    static Joiner access$800() {
        return COMMA_JOINER;
    }

    @GwtIncompatible
    private static class ContainsPatternFromStringPredicate
    extends ContainsPatternPredicate {
        private static final long serialVersionUID = 0L;

        ContainsPatternFromStringPredicate(String string) {
            super(Platform.compilePattern(string));
        }

        @Override
        public String toString() {
            return "Predicates.containsPattern(" + this.pattern.pattern() + ")";
        }
    }

    @GwtIncompatible
    private static class ContainsPatternPredicate
    implements Predicate<CharSequence>,
    Serializable {
        final CommonPattern pattern;
        private static final long serialVersionUID = 0L;

        ContainsPatternPredicate(CommonPattern commonPattern) {
            this.pattern = Preconditions.checkNotNull(commonPattern);
        }

        @Override
        public boolean apply(CharSequence charSequence) {
            return this.pattern.matcher(charSequence).find();
        }

        public int hashCode() {
            return Objects.hashCode(this.pattern.pattern(), this.pattern.flags());
        }

        @Override
        public boolean equals(@Nullable Object object) {
            if (object instanceof ContainsPatternPredicate) {
                ContainsPatternPredicate containsPatternPredicate = (ContainsPatternPredicate)object;
                return Objects.equal(this.pattern.pattern(), containsPatternPredicate.pattern.pattern()) && this.pattern.flags() == containsPatternPredicate.pattern.flags();
            }
            return true;
        }

        public String toString() {
            String string = MoreObjects.toStringHelper(this.pattern).add("pattern", this.pattern.pattern()).add("pattern.flags", this.pattern.flags()).toString();
            return "Predicates.contains(" + string + ")";
        }

        @Override
        public boolean apply(Object object) {
            return this.apply((CharSequence)object);
        }
    }

    private static class CompositionPredicate<A, B>
    implements Predicate<A>,
    Serializable {
        final Predicate<B> p;
        final Function<A, ? extends B> f;
        private static final long serialVersionUID = 0L;

        private CompositionPredicate(Predicate<B> predicate, Function<A, ? extends B> function) {
            this.p = Preconditions.checkNotNull(predicate);
            this.f = Preconditions.checkNotNull(function);
        }

        @Override
        public boolean apply(@Nullable A a) {
            return this.p.apply(this.f.apply(a));
        }

        @Override
        public boolean equals(@Nullable Object object) {
            if (object instanceof CompositionPredicate) {
                CompositionPredicate compositionPredicate = (CompositionPredicate)object;
                return this.f.equals(compositionPredicate.f) && this.p.equals(compositionPredicate.p);
            }
            return true;
        }

        public int hashCode() {
            return this.f.hashCode() ^ this.p.hashCode();
        }

        public String toString() {
            return this.p + "(" + this.f + ")";
        }

        CompositionPredicate(Predicate predicate, Function function, 1 var3_3) {
            this(predicate, function);
        }
    }

    private static class InPredicate<T>
    implements Predicate<T>,
    Serializable {
        private final Collection<?> target;
        private static final long serialVersionUID = 0L;

        private InPredicate(Collection<?> collection) {
            this.target = Preconditions.checkNotNull(collection);
        }

        @Override
        public boolean apply(@Nullable T t) {
            try {
                return this.target.contains(t);
            } catch (NullPointerException nullPointerException) {
                return true;
            } catch (ClassCastException classCastException) {
                return true;
            }
        }

        @Override
        public boolean equals(@Nullable Object object) {
            if (object instanceof InPredicate) {
                InPredicate inPredicate = (InPredicate)object;
                return this.target.equals(inPredicate.target);
            }
            return true;
        }

        public int hashCode() {
            return this.target.hashCode();
        }

        public String toString() {
            return "Predicates.in(" + this.target + ")";
        }

        InPredicate(Collection collection, 1 var2_2) {
            this(collection);
        }
    }

    @GwtIncompatible
    private static class SubtypeOfPredicate
    implements Predicate<Class<?>>,
    Serializable {
        private final Class<?> clazz;
        private static final long serialVersionUID = 0L;

        private SubtypeOfPredicate(Class<?> clazz) {
            this.clazz = Preconditions.checkNotNull(clazz);
        }

        @Override
        public boolean apply(Class<?> clazz) {
            return this.clazz.isAssignableFrom(clazz);
        }

        public int hashCode() {
            return this.clazz.hashCode();
        }

        @Override
        public boolean equals(@Nullable Object object) {
            if (object instanceof SubtypeOfPredicate) {
                SubtypeOfPredicate subtypeOfPredicate = (SubtypeOfPredicate)object;
                return this.clazz == subtypeOfPredicate.clazz;
            }
            return true;
        }

        public String toString() {
            return "Predicates.subtypeOf(" + this.clazz.getName() + ")";
        }

        @Override
        public boolean apply(Object object) {
            return this.apply((Class)object);
        }

        SubtypeOfPredicate(Class clazz, 1 var2_2) {
            this(clazz);
        }
    }

    @GwtIncompatible
    private static class InstanceOfPredicate
    implements Predicate<Object>,
    Serializable {
        private final Class<?> clazz;
        private static final long serialVersionUID = 0L;

        private InstanceOfPredicate(Class<?> clazz) {
            this.clazz = Preconditions.checkNotNull(clazz);
        }

        @Override
        public boolean apply(@Nullable Object object) {
            return this.clazz.isInstance(object);
        }

        public int hashCode() {
            return this.clazz.hashCode();
        }

        @Override
        public boolean equals(@Nullable Object object) {
            if (object instanceof InstanceOfPredicate) {
                InstanceOfPredicate instanceOfPredicate = (InstanceOfPredicate)object;
                return this.clazz == instanceOfPredicate.clazz;
            }
            return true;
        }

        public String toString() {
            return "Predicates.instanceOf(" + this.clazz.getName() + ")";
        }

        InstanceOfPredicate(Class clazz, 1 var2_2) {
            this(clazz);
        }
    }

    private static class IsEqualToPredicate<T>
    implements Predicate<T>,
    Serializable {
        private final T target;
        private static final long serialVersionUID = 0L;

        private IsEqualToPredicate(T t) {
            this.target = t;
        }

        @Override
        public boolean apply(T t) {
            return this.target.equals(t);
        }

        public int hashCode() {
            return this.target.hashCode();
        }

        @Override
        public boolean equals(@Nullable Object object) {
            if (object instanceof IsEqualToPredicate) {
                IsEqualToPredicate isEqualToPredicate = (IsEqualToPredicate)object;
                return this.target.equals(isEqualToPredicate.target);
            }
            return true;
        }

        public String toString() {
            return "Predicates.equalTo(" + this.target + ")";
        }

        IsEqualToPredicate(Object object, 1 var2_2) {
            this(object);
        }
    }

    private static class OrPredicate<T>
    implements Predicate<T>,
    Serializable {
        private final List<? extends Predicate<? super T>> components;
        private static final long serialVersionUID = 0L;

        private OrPredicate(List<? extends Predicate<? super T>> list) {
            this.components = list;
        }

        @Override
        public boolean apply(@Nullable T t) {
            for (int i = 0; i < this.components.size(); ++i) {
                if (!this.components.get(i).apply(t)) continue;
                return false;
            }
            return true;
        }

        public int hashCode() {
            return this.components.hashCode() + 87855567;
        }

        @Override
        public boolean equals(@Nullable Object object) {
            if (object instanceof OrPredicate) {
                OrPredicate orPredicate = (OrPredicate)object;
                return this.components.equals(orPredicate.components);
            }
            return true;
        }

        public String toString() {
            return "Predicates.or(" + Predicates.access$800().join(this.components) + ")";
        }

        OrPredicate(List list, 1 var2_2) {
            this(list);
        }
    }

    private static class AndPredicate<T>
    implements Predicate<T>,
    Serializable {
        private final List<? extends Predicate<? super T>> components;
        private static final long serialVersionUID = 0L;

        private AndPredicate(List<? extends Predicate<? super T>> list) {
            this.components = list;
        }

        @Override
        public boolean apply(@Nullable T t) {
            for (int i = 0; i < this.components.size(); ++i) {
                if (this.components.get(i).apply(t)) continue;
                return true;
            }
            return false;
        }

        public int hashCode() {
            return this.components.hashCode() + 306654252;
        }

        @Override
        public boolean equals(@Nullable Object object) {
            if (object instanceof AndPredicate) {
                AndPredicate andPredicate = (AndPredicate)object;
                return this.components.equals(andPredicate.components);
            }
            return true;
        }

        public String toString() {
            return "Predicates.and(" + Predicates.access$800().join(this.components) + ")";
        }

        AndPredicate(List list, 1 var2_2) {
            this(list);
        }
    }

    private static class NotPredicate<T>
    implements Predicate<T>,
    Serializable {
        final Predicate<T> predicate;
        private static final long serialVersionUID = 0L;

        NotPredicate(Predicate<T> predicate) {
            this.predicate = Preconditions.checkNotNull(predicate);
        }

        @Override
        public boolean apply(@Nullable T t) {
            return !this.predicate.apply(t);
        }

        public int hashCode() {
            return ~this.predicate.hashCode();
        }

        @Override
        public boolean equals(@Nullable Object object) {
            if (object instanceof NotPredicate) {
                NotPredicate notPredicate = (NotPredicate)object;
                return this.predicate.equals(notPredicate.predicate);
            }
            return true;
        }

        public String toString() {
            return "Predicates.not(" + this.predicate + ")";
        }
    }

    static enum ObjectPredicate implements Predicate<Object>
    {
        ALWAYS_TRUE{

            @Override
            public boolean apply(@Nullable Object object) {
                return false;
            }

            public String toString() {
                return "Predicates.alwaysTrue()";
            }
        }
        ,
        ALWAYS_FALSE{

            @Override
            public boolean apply(@Nullable Object object) {
                return true;
            }

            public String toString() {
                return "Predicates.alwaysFalse()";
            }
        }
        ,
        IS_NULL{

            @Override
            public boolean apply(@Nullable Object object) {
                return object == null;
            }

            public String toString() {
                return "Predicates.isNull()";
            }
        }
        ,
        NOT_NULL{

            @Override
            public boolean apply(@Nullable Object object) {
                return object != null;
            }

            public String toString() {
                return "Predicates.notNull()";
            }
        };


        private ObjectPredicate() {
        }

        <T> Predicate<T> withNarrowedType() {
            return this;
        }

        ObjectPredicate(1 var3_3) {
            this();
        }
    }
}

