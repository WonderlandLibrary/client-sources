/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collector;
import javax.annotation.Nullable;

@Beta
@GwtCompatible
public final class MoreCollectors {
    private static final Collector<Object, ?, Optional<Object>> TO_OPTIONAL = Collector.of(ToOptionalState::new, ToOptionalState::add, ToOptionalState::combine, ToOptionalState::getOptional, Collector.Characteristics.UNORDERED);
    private static final Object NULL_PLACEHOLDER = new Object();
    private static final Collector<Object, ?, Object> ONLY_ELEMENT = Collector.of(ToOptionalState::new, MoreCollectors::lambda$static$0, ToOptionalState::combine, MoreCollectors::lambda$static$1, Collector.Characteristics.UNORDERED);

    public static <T> Collector<T, ?, Optional<T>> toOptional() {
        return TO_OPTIONAL;
    }

    public static <T> Collector<T, ?, T> onlyElement() {
        return ONLY_ELEMENT;
    }

    private MoreCollectors() {
    }

    private static Object lambda$static$1(ToOptionalState toOptionalState) {
        Object object = toOptionalState.getElement();
        return object == NULL_PLACEHOLDER ? null : object;
    }

    private static void lambda$static$0(ToOptionalState toOptionalState, Object object) {
        toOptionalState.add(object == null ? NULL_PLACEHOLDER : object);
    }

    private static final class ToOptionalState {
        static final int MAX_EXTRAS = 4;
        @Nullable
        Object element = null;
        @Nullable
        List<Object> extras = null;

        ToOptionalState() {
        }

        IllegalArgumentException multiples(boolean bl) {
            StringBuilder stringBuilder = new StringBuilder().append("expected one element but was: <").append(this.element);
            for (Object object : this.extras) {
                stringBuilder.append(", ").append(object);
            }
            if (bl) {
                stringBuilder.append(", ...");
            }
            stringBuilder.append('>');
            throw new IllegalArgumentException(stringBuilder.toString());
        }

        void add(Object object) {
            Preconditions.checkNotNull(object);
            if (this.element == null) {
                this.element = object;
            } else if (this.extras == null) {
                this.extras = new ArrayList<Object>(4);
                this.extras.add(object);
            } else if (this.extras.size() < 4) {
                this.extras.add(object);
            } else {
                throw this.multiples(false);
            }
        }

        ToOptionalState combine(ToOptionalState toOptionalState) {
            if (this.element == null) {
                return toOptionalState;
            }
            if (toOptionalState.element == null) {
                return this;
            }
            if (this.extras == null) {
                this.extras = new ArrayList<Object>();
            }
            this.extras.add(toOptionalState.element);
            if (toOptionalState.extras != null) {
                this.extras.addAll(toOptionalState.extras);
            }
            if (this.extras.size() > 4) {
                this.extras.subList(4, this.extras.size()).clear();
                throw this.multiples(false);
            }
            return this;
        }

        Optional<Object> getOptional() {
            if (this.extras == null) {
                return Optional.ofNullable(this.element);
            }
            throw this.multiples(true);
        }

        Object getElement() {
            if (this.element == null) {
                throw new NoSuchElementException();
            }
            if (this.extras == null) {
                return this.element;
            }
            throw this.multiples(true);
        }
    }
}

