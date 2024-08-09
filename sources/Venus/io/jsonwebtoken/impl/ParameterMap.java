/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl;

import io.jsonwebtoken.impl.lang.Nameable;
import io.jsonwebtoken.impl.lang.Parameter;
import io.jsonwebtoken.impl.lang.ParameterReadable;
import io.jsonwebtoken.impl.lang.Parameters;
import io.jsonwebtoken.lang.Assert;
import io.jsonwebtoken.lang.Collections;
import io.jsonwebtoken.lang.Objects;
import io.jsonwebtoken.lang.Registry;
import io.jsonwebtoken.lang.Strings;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class ParameterMap
implements Map<String, Object>,
ParameterReadable,
Nameable {
    protected final Registry<String, ? extends Parameter<?>> PARAMS;
    protected final Map<String, Object> values;
    protected final Map<String, Object> idiomaticValues;
    private final boolean initialized;
    private final boolean mutable;

    public ParameterMap(Set<Parameter<?>> set) {
        this(Parameters.registry(set));
    }

    public ParameterMap(Registry<String, ? extends Parameter<?>> registry) {
        this(registry, null, true);
    }

    public ParameterMap(Registry<String, ? extends Parameter<?>> registry, Map<String, ?> map) {
        this(registry, Assert.notNull(map, "Map argument cannot be null."), false);
    }

    public ParameterMap(Registry<String, ? extends Parameter<?>> registry, Map<String, ?> map, boolean bl) {
        Assert.notNull(registry, "Parameter registry cannot be null.");
        Assert.notEmpty(registry.values(), "Parameter registry cannot be empty.");
        this.PARAMS = registry;
        this.values = new LinkedHashMap<String, Object>();
        this.idiomaticValues = new LinkedHashMap<String, Object>();
        if (!Collections.isEmpty(map)) {
            this.putAll((Map<? extends String, ?>)map);
        }
        this.mutable = bl;
        this.initialized = true;
    }

    private void assertMutable() {
        if (this.initialized && !this.mutable) {
            String string = this.getName() + " instance is immutable and may not be modified.";
            throw new UnsupportedOperationException(string);
        }
    }

    protected ParameterMap replace(Parameter<?> parameter) {
        Registry<String, ? extends Parameter<?>> registry = Parameters.replace(this.PARAMS, parameter);
        return new ParameterMap(registry, this, this.mutable);
    }

    @Override
    public String getName() {
        return "Map";
    }

    @Override
    public <T> T get(Parameter<T> parameter) {
        Assert.notNull(parameter, "Parameter cannot be null.");
        String string = Assert.hasText(parameter.getId(), "Parameter id cannot be null or empty.");
        Object object = this.idiomaticValues.get(string);
        return parameter.cast(object);
    }

    @Override
    public int size() {
        return this.values.size();
    }

    @Override
    public boolean isEmpty() {
        return this.values.isEmpty();
    }

    @Override
    public boolean containsKey(Object object) {
        return this.values.containsKey(object);
    }

    @Override
    public boolean containsValue(Object object) {
        return this.values.containsValue(object);
    }

    @Override
    public Object get(Object object) {
        return this.values.get(object);
    }

    private static Object clean(Object object) {
        if (object instanceof String) {
            object = Strings.clean((String)object);
        }
        return object;
    }

    @Override
    protected final <T> Object put(Parameter<T> parameter, Object object) {
        this.assertMutable();
        Assert.notNull(parameter, "Parameter cannot be null.");
        Assert.hasText(parameter.getId(), "Parameter id cannot be null or empty.");
        return this.apply(parameter, ParameterMap.clean(object));
    }

    @Override
    public final Object put(String string, Object object) {
        this.assertMutable();
        string = Assert.notNull(Strings.clean(string), "Member name cannot be null or empty.");
        Parameter parameter = (Parameter)this.PARAMS.get(string);
        if (parameter != null) {
            return this.put(parameter, object);
        }
        return this.nullSafePut(string, ParameterMap.clean(object));
    }

    private Object nullSafePut(String string, Object object) {
        if (Objects.isEmpty(object)) {
            return this.remove(string);
        }
        this.idiomaticValues.put(string, object);
        return this.values.put(string, object);
    }

    private <T> Object apply(Parameter<T> parameter, Object object) {
        Object b;
        Object a;
        String string = parameter.getId();
        if (Objects.isEmpty(object)) {
            return this.remove(string);
        }
        try {
            a = parameter.applyFrom(object);
            Assert.notNull(a, "Parameter's resulting idiomaticValue cannot be null.");
            b = parameter.applyTo(a);
            Assert.notNull(b, "Parameter's resulting canonicalValue cannot be null.");
        } catch (Exception exception) {
            StringBuilder stringBuilder = new StringBuilder(100);
            stringBuilder.append("Invalid ").append(this.getName()).append(" ").append(parameter).append(" value");
            if (parameter.isSecret()) {
                stringBuilder.append(": ").append("<redacted>");
            } else if (!(object instanceof byte[])) {
                stringBuilder.append(": ").append(Objects.nullSafeToString(object));
            }
            stringBuilder.append(". ").append(exception.getMessage());
            String string2 = stringBuilder.toString();
            throw new IllegalArgumentException(string2, exception);
        }
        Object object2 = this.nullSafePut(string, b);
        this.idiomaticValues.put(string, a);
        return object2;
    }

    @Override
    public Object remove(Object object) {
        this.assertMutable();
        this.idiomaticValues.remove(object);
        return this.values.remove(object);
    }

    @Override
    public void putAll(Map<? extends String, ?> map) {
        if (map == null) {
            return;
        }
        for (Map.Entry<String, ?> entry : map.entrySet()) {
            String string = entry.getKey();
            this.put(string, entry.getValue());
        }
    }

    @Override
    public void clear() {
        this.assertMutable();
        this.values.clear();
        this.idiomaticValues.clear();
    }

    @Override
    public Set<String> keySet() {
        return new KeySet(this, null);
    }

    @Override
    public Collection<Object> values() {
        return new ValueSet(this, null);
    }

    @Override
    public Set<Map.Entry<String, Object>> entrySet() {
        return new EntrySet(this, null);
    }

    public String toString() {
        return this.values.toString();
    }

    @Override
    public int hashCode() {
        return this.values.hashCode();
    }

    @Override
    public boolean equals(Object object) {
        return this.values.equals(object);
    }

    @Override
    public Object put(Object object, Object object2) {
        return this.put((String)object, object2);
    }

    static class 1 {
    }

    private class EntryIterator
    extends ParameterMapIterator<Map.Entry<String, Object>> {
        final ParameterMap this$0;

        private EntryIterator(ParameterMap parameterMap) {
            this.this$0 = parameterMap;
            super(parameterMap);
        }

        @Override
        public Map.Entry<String, Object> next() {
            return this.nextEntry();
        }

        @Override
        public Object next() {
            return this.next();
        }

        EntryIterator(ParameterMap parameterMap, 1 var2_2) {
            this(parameterMap);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private class KeyIterator
    extends ParameterMapIterator<String> {
        final ParameterMap this$0;

        private KeyIterator(ParameterMap parameterMap) {
            this.this$0 = parameterMap;
            super(parameterMap);
        }

        @Override
        public String next() {
            return this.nextEntry().getKey();
        }

        @Override
        public Object next() {
            return this.next();
        }

        KeyIterator(ParameterMap parameterMap, 1 var2_2) {
            this(parameterMap);
        }
    }

    private class ValueIterator
    extends ParameterMapIterator<Object> {
        final ParameterMap this$0;

        private ValueIterator(ParameterMap parameterMap) {
            this.this$0 = parameterMap;
            super(parameterMap);
        }

        @Override
        public Object next() {
            return this.nextEntry().getValue();
        }

        ValueIterator(ParameterMap parameterMap, 1 var2_2) {
            this(parameterMap);
        }
    }

    private abstract class ParameterMapIterator<T>
    implements Iterator<T> {
        final Iterator<Map.Entry<String, Object>> i;
        transient Map.Entry<String, Object> current;
        final ParameterMap this$0;

        ParameterMapIterator(ParameterMap parameterMap) {
            this.this$0 = parameterMap;
            this.i = parameterMap.values.entrySet().iterator();
            this.current = null;
        }

        @Override
        public boolean hasNext() {
            return this.i.hasNext();
        }

        protected Map.Entry<String, Object> nextEntry() {
            this.current = this.i.next();
            return this.current;
        }

        @Override
        public void remove() {
            if (this.current == null) {
                throw new IllegalStateException();
            }
            String string = this.current.getKey();
            this.this$0.remove(string);
        }
    }

    private class EntrySet
    extends ParameterMapSet<Map.Entry<String, Object>> {
        final ParameterMap this$0;

        private EntrySet(ParameterMap parameterMap) {
            this.this$0 = parameterMap;
            super(parameterMap, null);
        }

        @Override
        public Iterator<Map.Entry<String, Object>> iterator() {
            return new EntryIterator(this.this$0, null);
        }

        EntrySet(ParameterMap parameterMap, 1 var2_2) {
            this(parameterMap);
        }
    }

    private class ValueSet
    extends ParameterMapSet<Object> {
        final ParameterMap this$0;

        private ValueSet(ParameterMap parameterMap) {
            this.this$0 = parameterMap;
            super(parameterMap, null);
        }

        @Override
        public Iterator<Object> iterator() {
            return new ValueIterator(this.this$0, null);
        }

        ValueSet(ParameterMap parameterMap, 1 var2_2) {
            this(parameterMap);
        }
    }

    private class KeySet
    extends ParameterMapSet<String> {
        final ParameterMap this$0;

        private KeySet(ParameterMap parameterMap) {
            this.this$0 = parameterMap;
            super(parameterMap, null);
        }

        @Override
        public Iterator<String> iterator() {
            return new KeyIterator(this.this$0, null);
        }

        KeySet(ParameterMap parameterMap, 1 var2_2) {
            this(parameterMap);
        }
    }

    private abstract class ParameterMapSet<T>
    extends AbstractSet<T> {
        final ParameterMap this$0;

        private ParameterMapSet(ParameterMap parameterMap) {
            this.this$0 = parameterMap;
        }

        @Override
        public int size() {
            return this.this$0.size();
        }

        ParameterMapSet(ParameterMap parameterMap, 1 var2_2) {
            this(parameterMap);
        }
    }
}

