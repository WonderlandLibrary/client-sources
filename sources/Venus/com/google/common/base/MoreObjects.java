/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.base;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.Arrays;
import javax.annotation.Nullable;

@GwtCompatible
public final class MoreObjects {
    public static <T> T firstNonNull(@Nullable T t, @Nullable T t2) {
        return t != null ? t : Preconditions.checkNotNull(t2);
    }

    public static ToStringHelper toStringHelper(Object object) {
        return new ToStringHelper(object.getClass().getSimpleName(), null);
    }

    public static ToStringHelper toStringHelper(Class<?> clazz) {
        return new ToStringHelper(clazz.getSimpleName(), null);
    }

    public static ToStringHelper toStringHelper(String string) {
        return new ToStringHelper(string, null);
    }

    private MoreObjects() {
    }

    public static final class ToStringHelper {
        private final String className;
        private final ValueHolder holderHead;
        private ValueHolder holderTail;
        private boolean omitNullValues;

        private ToStringHelper(String string) {
            this.holderTail = this.holderHead = new ValueHolder(null);
            this.omitNullValues = false;
            this.className = Preconditions.checkNotNull(string);
        }

        @CanIgnoreReturnValue
        public ToStringHelper omitNullValues() {
            this.omitNullValues = true;
            return this;
        }

        @CanIgnoreReturnValue
        public ToStringHelper add(String string, @Nullable Object object) {
            return this.addHolder(string, object);
        }

        @CanIgnoreReturnValue
        public ToStringHelper add(String string, boolean bl) {
            return this.addHolder(string, String.valueOf(bl));
        }

        @CanIgnoreReturnValue
        public ToStringHelper add(String string, char c) {
            return this.addHolder(string, String.valueOf(c));
        }

        @CanIgnoreReturnValue
        public ToStringHelper add(String string, double d) {
            return this.addHolder(string, String.valueOf(d));
        }

        @CanIgnoreReturnValue
        public ToStringHelper add(String string, float f) {
            return this.addHolder(string, String.valueOf(f));
        }

        @CanIgnoreReturnValue
        public ToStringHelper add(String string, int n) {
            return this.addHolder(string, String.valueOf(n));
        }

        @CanIgnoreReturnValue
        public ToStringHelper add(String string, long l) {
            return this.addHolder(string, String.valueOf(l));
        }

        @CanIgnoreReturnValue
        public ToStringHelper addValue(@Nullable Object object) {
            return this.addHolder(object);
        }

        @CanIgnoreReturnValue
        public ToStringHelper addValue(boolean bl) {
            return this.addHolder(String.valueOf(bl));
        }

        @CanIgnoreReturnValue
        public ToStringHelper addValue(char c) {
            return this.addHolder(String.valueOf(c));
        }

        @CanIgnoreReturnValue
        public ToStringHelper addValue(double d) {
            return this.addHolder(String.valueOf(d));
        }

        @CanIgnoreReturnValue
        public ToStringHelper addValue(float f) {
            return this.addHolder(String.valueOf(f));
        }

        @CanIgnoreReturnValue
        public ToStringHelper addValue(int n) {
            return this.addHolder(String.valueOf(n));
        }

        @CanIgnoreReturnValue
        public ToStringHelper addValue(long l) {
            return this.addHolder(String.valueOf(l));
        }

        public String toString() {
            boolean bl = this.omitNullValues;
            String string = "";
            StringBuilder stringBuilder = new StringBuilder(32).append(this.className).append('{');
            ValueHolder valueHolder = this.holderHead.next;
            while (valueHolder != null) {
                Object object = valueHolder.value;
                if (!bl || object != null) {
                    stringBuilder.append(string);
                    string = ", ";
                    if (valueHolder.name != null) {
                        stringBuilder.append(valueHolder.name).append('=');
                    }
                    if (object != null && object.getClass().isArray()) {
                        Object[] objectArray = new Object[]{object};
                        String string2 = Arrays.deepToString(objectArray);
                        stringBuilder.append(string2, 1, string2.length() - 1);
                    } else {
                        stringBuilder.append(object);
                    }
                }
                valueHolder = valueHolder.next;
            }
            return stringBuilder.append('}').toString();
        }

        private ValueHolder addHolder() {
            ValueHolder valueHolder;
            this.holderTail = this.holderTail.next = (valueHolder = new ValueHolder(null));
            return valueHolder;
        }

        private ToStringHelper addHolder(@Nullable Object object) {
            ValueHolder valueHolder = this.addHolder();
            valueHolder.value = object;
            return this;
        }

        private ToStringHelper addHolder(String string, @Nullable Object object) {
            ValueHolder valueHolder = this.addHolder();
            valueHolder.value = object;
            valueHolder.name = Preconditions.checkNotNull(string);
            return this;
        }

        ToStringHelper(String string, 1 var2_2) {
            this(string);
        }

        private static final class ValueHolder {
            String name;
            Object value;
            ValueHolder next;

            private ValueHolder() {
            }

            ValueHolder(1 var1_1) {
                this();
            }
        }
    }
}

