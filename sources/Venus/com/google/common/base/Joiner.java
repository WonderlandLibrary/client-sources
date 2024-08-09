/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.base;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.IOException;
import java.util.AbstractList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import javax.annotation.Nullable;

@GwtCompatible
public class Joiner {
    private final String separator;

    public static Joiner on(String string) {
        return new Joiner(string);
    }

    public static Joiner on(char c) {
        return new Joiner(String.valueOf(c));
    }

    private Joiner(String string) {
        this.separator = Preconditions.checkNotNull(string);
    }

    private Joiner(Joiner joiner) {
        this.separator = joiner.separator;
    }

    @CanIgnoreReturnValue
    public <A extends Appendable> A appendTo(A a, Iterable<?> iterable) throws IOException {
        return this.appendTo(a, iterable.iterator());
    }

    @CanIgnoreReturnValue
    public <A extends Appendable> A appendTo(A a, Iterator<?> iterator2) throws IOException {
        Preconditions.checkNotNull(a);
        if (iterator2.hasNext()) {
            a.append(this.toString(iterator2.next()));
            while (iterator2.hasNext()) {
                a.append(this.separator);
                a.append(this.toString(iterator2.next()));
            }
        }
        return a;
    }

    @CanIgnoreReturnValue
    public final <A extends Appendable> A appendTo(A a, Object[] objectArray) throws IOException {
        return this.appendTo(a, Arrays.asList(objectArray));
    }

    @CanIgnoreReturnValue
    public final <A extends Appendable> A appendTo(A a, @Nullable Object object, @Nullable Object object2, Object ... objectArray) throws IOException {
        return this.appendTo(a, Joiner.iterable(object, object2, objectArray));
    }

    @CanIgnoreReturnValue
    public final StringBuilder appendTo(StringBuilder stringBuilder, Iterable<?> iterable) {
        return this.appendTo(stringBuilder, iterable.iterator());
    }

    @CanIgnoreReturnValue
    public final StringBuilder appendTo(StringBuilder stringBuilder, Iterator<?> iterator2) {
        try {
            this.appendTo((Appendable)stringBuilder, iterator2);
        } catch (IOException iOException) {
            throw new AssertionError((Object)iOException);
        }
        return stringBuilder;
    }

    @CanIgnoreReturnValue
    public final StringBuilder appendTo(StringBuilder stringBuilder, Object[] objectArray) {
        return this.appendTo(stringBuilder, (Iterable<?>)Arrays.asList(objectArray));
    }

    @CanIgnoreReturnValue
    public final StringBuilder appendTo(StringBuilder stringBuilder, @Nullable Object object, @Nullable Object object2, Object ... objectArray) {
        return this.appendTo(stringBuilder, Joiner.iterable(object, object2, objectArray));
    }

    public final String join(Iterable<?> iterable) {
        return this.join(iterable.iterator());
    }

    public final String join(Iterator<?> iterator2) {
        return this.appendTo(new StringBuilder(), iterator2).toString();
    }

    public final String join(Object[] objectArray) {
        return this.join(Arrays.asList(objectArray));
    }

    public final String join(@Nullable Object object, @Nullable Object object2, Object ... objectArray) {
        return this.join(Joiner.iterable(object, object2, objectArray));
    }

    public Joiner useForNull(String string) {
        Preconditions.checkNotNull(string);
        return new Joiner(this, this, string){
            final String val$nullText;
            final Joiner this$0;
            {
                this.this$0 = joiner;
                this.val$nullText = string;
                super(joiner2, null);
            }

            @Override
            CharSequence toString(@Nullable Object object) {
                return object == null ? this.val$nullText : this.this$0.toString(object);
            }

            @Override
            public Joiner useForNull(String string) {
                throw new UnsupportedOperationException("already specified useForNull");
            }

            @Override
            public Joiner skipNulls() {
                throw new UnsupportedOperationException("already specified useForNull");
            }
        };
    }

    public Joiner skipNulls() {
        return new Joiner(this, this){
            final Joiner this$0;
            {
                this.this$0 = joiner;
                super(joiner2, null);
            }

            @Override
            public <A extends Appendable> A appendTo(A a, Iterator<?> iterator2) throws IOException {
                Object obj;
                Preconditions.checkNotNull(a, "appendable");
                Preconditions.checkNotNull(iterator2, "parts");
                while (iterator2.hasNext()) {
                    obj = iterator2.next();
                    if (obj == null) continue;
                    a.append(this.this$0.toString(obj));
                    break;
                }
                while (iterator2.hasNext()) {
                    obj = iterator2.next();
                    if (obj == null) continue;
                    a.append(Joiner.access$100(this.this$0));
                    a.append(this.this$0.toString(obj));
                }
                return a;
            }

            @Override
            public Joiner useForNull(String string) {
                throw new UnsupportedOperationException("already specified skipNulls");
            }

            @Override
            public MapJoiner withKeyValueSeparator(String string) {
                throw new UnsupportedOperationException("can't use .skipNulls() with maps");
            }
        };
    }

    public MapJoiner withKeyValueSeparator(char c) {
        return this.withKeyValueSeparator(String.valueOf(c));
    }

    public MapJoiner withKeyValueSeparator(String string) {
        return new MapJoiner(this, string, null);
    }

    CharSequence toString(Object object) {
        Preconditions.checkNotNull(object);
        return object instanceof CharSequence ? (CharSequence)object : object.toString();
    }

    private static Iterable<Object> iterable(Object object, Object object2, Object[] objectArray) {
        Preconditions.checkNotNull(objectArray);
        return new AbstractList<Object>(objectArray, object, object2){
            final Object[] val$rest;
            final Object val$first;
            final Object val$second;
            {
                this.val$rest = objectArray;
                this.val$first = object;
                this.val$second = object2;
            }

            @Override
            public int size() {
                return this.val$rest.length + 2;
            }

            @Override
            public Object get(int n) {
                switch (n) {
                    case 0: {
                        return this.val$first;
                    }
                    case 1: {
                        return this.val$second;
                    }
                }
                return this.val$rest[n - 2];
            }
        };
    }

    Joiner(Joiner joiner, 1 var2_2) {
        this(joiner);
    }

    static String access$100(Joiner joiner) {
        return joiner.separator;
    }

    public static final class MapJoiner {
        private final Joiner joiner;
        private final String keyValueSeparator;

        private MapJoiner(Joiner joiner, String string) {
            this.joiner = joiner;
            this.keyValueSeparator = Preconditions.checkNotNull(string);
        }

        @CanIgnoreReturnValue
        public <A extends Appendable> A appendTo(A a, Map<?, ?> map) throws IOException {
            return this.appendTo(a, map.entrySet());
        }

        @CanIgnoreReturnValue
        public StringBuilder appendTo(StringBuilder stringBuilder, Map<?, ?> map) {
            return this.appendTo(stringBuilder, (Iterable<? extends Map.Entry<?, ?>>)map.entrySet());
        }

        public String join(Map<?, ?> map) {
            return this.join(map.entrySet());
        }

        @Beta
        @CanIgnoreReturnValue
        public <A extends Appendable> A appendTo(A a, Iterable<? extends Map.Entry<?, ?>> iterable) throws IOException {
            return this.appendTo(a, iterable.iterator());
        }

        @Beta
        @CanIgnoreReturnValue
        public <A extends Appendable> A appendTo(A a, Iterator<? extends Map.Entry<?, ?>> iterator2) throws IOException {
            Preconditions.checkNotNull(a);
            if (iterator2.hasNext()) {
                Map.Entry<?, ?> entry = iterator2.next();
                a.append(this.joiner.toString(entry.getKey()));
                a.append(this.keyValueSeparator);
                a.append(this.joiner.toString(entry.getValue()));
                while (iterator2.hasNext()) {
                    a.append(Joiner.access$100(this.joiner));
                    Map.Entry<?, ?> entry2 = iterator2.next();
                    a.append(this.joiner.toString(entry2.getKey()));
                    a.append(this.keyValueSeparator);
                    a.append(this.joiner.toString(entry2.getValue()));
                }
            }
            return a;
        }

        @Beta
        @CanIgnoreReturnValue
        public StringBuilder appendTo(StringBuilder stringBuilder, Iterable<? extends Map.Entry<?, ?>> iterable) {
            return this.appendTo(stringBuilder, iterable.iterator());
        }

        @Beta
        @CanIgnoreReturnValue
        public StringBuilder appendTo(StringBuilder stringBuilder, Iterator<? extends Map.Entry<?, ?>> iterator2) {
            try {
                this.appendTo((Appendable)stringBuilder, iterator2);
            } catch (IOException iOException) {
                throw new AssertionError((Object)iOException);
            }
            return stringBuilder;
        }

        @Beta
        public String join(Iterable<? extends Map.Entry<?, ?>> iterable) {
            return this.join(iterable.iterator());
        }

        @Beta
        public String join(Iterator<? extends Map.Entry<?, ?>> iterator2) {
            return this.appendTo(new StringBuilder(), iterator2).toString();
        }

        public MapJoiner useForNull(String string) {
            return new MapJoiner(this.joiner.useForNull(string), this.keyValueSeparator);
        }

        MapJoiner(Joiner joiner, String string, 1 var3_3) {
            this(joiner, string);
        }
    }
}

