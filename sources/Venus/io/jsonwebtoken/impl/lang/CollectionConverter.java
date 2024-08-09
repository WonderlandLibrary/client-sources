/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.lang;

import io.jsonwebtoken.impl.lang.Converter;
import io.jsonwebtoken.impl.lang.Function;
import io.jsonwebtoken.lang.Assert;
import io.jsonwebtoken.lang.Collections;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

class CollectionConverter<T, C extends Collection<T>>
implements Converter<C, Object> {
    private final Converter<T, Object> elementConverter;
    private final Function<Integer, C> fn;

    public static <T> CollectionConverter<T, List<T>> forList(Converter<T, Object> converter) {
        return new CollectionConverter(converter, new CreateListFunction(null));
    }

    public static <T> CollectionConverter<T, Set<T>> forSet(Converter<T, Object> converter) {
        return new CollectionConverter(converter, new CreateSetFunction(null));
    }

    public CollectionConverter(Converter<T, Object> converter, Function<Integer, C> function) {
        this.elementConverter = Assert.notNull(converter, "Element converter cannot be null.");
        this.fn = Assert.notNull(function, "Collection function cannot be null.");
    }

    @Override
    public Object applyTo(C c) {
        if (Collections.isEmpty(c)) {
            return c;
        }
        Collection collection = (Collection)this.fn.apply(c.size());
        for (Object e : c) {
            Object object = this.elementConverter.applyTo(e);
            collection.add(object);
        }
        return collection;
    }

    private C toElementList(Collection<?> collection) {
        Assert.notEmpty(collection, "Collection cannot be null or empty.");
        Collection collection2 = (Collection)this.fn.apply(collection.size());
        for (Object obj : collection) {
            T t = this.elementConverter.applyFrom(obj);
            collection2.add(t);
        }
        return (C)collection2;
    }

    @Override
    public C applyFrom(Object object) {
        if (object == null) {
            return null;
        }
        List list = object.getClass().isArray() && !object.getClass().getComponentType().isPrimitive() ? Collections.arrayToList(object) : (object instanceof Collection ? (List)object : java.util.Collections.singletonList(object));
        Object object2 = Collections.isEmpty(list) ? (Collection)this.fn.apply(0) : this.toElementList(list);
        return object2;
    }

    @Override
    public Object applyFrom(Object object) {
        return this.applyFrom(object);
    }

    @Override
    public Object applyTo(Object object) {
        return this.applyTo((C)((Collection)object));
    }

    static class 1 {
    }

    private static class CreateSetFunction<T>
    implements Function<Integer, Set<T>> {
        private CreateSetFunction() {
        }

        @Override
        public Set<T> apply(Integer n) {
            return n > 0 ? new LinkedHashSet(n) : new LinkedHashSet();
        }

        @Override
        public Object apply(Object object) {
            return this.apply((Integer)object);
        }

        CreateSetFunction(1 var1_1) {
            this();
        }
    }

    private static class CreateListFunction<A>
    implements Function<Integer, List<A>> {
        private CreateListFunction() {
        }

        @Override
        public List<A> apply(Integer n) {
            return n > 0 ? new ArrayList(n) : new ArrayList();
        }

        @Override
        public Object apply(Object object) {
            return this.apply((Integer)object);
        }

        CreateListFunction(1 var1_1) {
            this();
        }
    }
}

