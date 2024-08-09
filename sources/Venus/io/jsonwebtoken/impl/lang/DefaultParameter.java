/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.lang;

import io.jsonwebtoken.impl.lang.Converter;
import io.jsonwebtoken.impl.lang.Parameter;
import io.jsonwebtoken.lang.Assert;
import io.jsonwebtoken.lang.Strings;
import java.util.Collection;

public class DefaultParameter<T>
implements Parameter<T> {
    private final String ID;
    private final String NAME;
    private final boolean SECRET;
    private final Class<T> IDIOMATIC_TYPE;
    private final Class<? extends Collection<T>> COLLECTION_TYPE;
    private final Converter<T, Object> CONVERTER;

    public DefaultParameter(String string, String string2, boolean bl, Class<T> clazz, Class<? extends Collection<T>> clazz2, Converter<T, Object> converter) {
        this.ID = Strings.clean(Assert.hasText(string, "ID argument cannot be null or empty."));
        this.NAME = Strings.clean(Assert.hasText(string2, "Name argument cannot be null or empty."));
        this.IDIOMATIC_TYPE = Assert.notNull(clazz, "idiomaticType argument cannot be null.");
        this.CONVERTER = Assert.notNull(converter, "Converter argument cannot be null.");
        this.SECRET = bl;
        this.COLLECTION_TYPE = clazz2;
    }

    @Override
    public String getId() {
        return this.ID;
    }

    @Override
    public String getName() {
        return this.NAME;
    }

    @Override
    public boolean supports(Object object) {
        if (object == null) {
            return false;
        }
        if (this.COLLECTION_TYPE != null && this.COLLECTION_TYPE.isInstance(object)) {
            Collection<T> collection = this.COLLECTION_TYPE.cast(object);
            return collection.isEmpty() || this.IDIOMATIC_TYPE.isInstance(collection.iterator().next());
        }
        return this.IDIOMATIC_TYPE.isInstance(object);
    }

    @Override
    public T cast(Object object) {
        if (object != null) {
            if (this.COLLECTION_TYPE != null) {
                T t;
                if (!this.COLLECTION_TYPE.isInstance(object)) {
                    String string = "Cannot cast " + object.getClass().getName() + " to " + this.COLLECTION_TYPE.getName() + "<" + this.IDIOMATIC_TYPE.getName() + ">";
                    throw new ClassCastException(string);
                }
                Collection<T> collection = this.COLLECTION_TYPE.cast(object);
                if (!collection.isEmpty() && !this.IDIOMATIC_TYPE.isInstance(t = collection.iterator().next())) {
                    String string = "Cannot cast " + object.getClass().getName() + " to " + this.COLLECTION_TYPE.getName() + "<" + this.IDIOMATIC_TYPE.getName() + ">: At least one " + "element is not an instance of " + this.IDIOMATIC_TYPE.getName();
                    throw new ClassCastException(string);
                }
            } else if (!this.IDIOMATIC_TYPE.isInstance(object)) {
                String string = "Cannot cast " + object.getClass().getName() + " to " + this.IDIOMATIC_TYPE.getName();
                throw new ClassCastException(string);
            }
        }
        return (T)object;
    }

    @Override
    public boolean isSecret() {
        return this.SECRET;
    }

    public int hashCode() {
        return this.ID.hashCode();
    }

    public boolean equals(Object object) {
        if (object instanceof Parameter) {
            return this.ID.equals(((Parameter)object).getId());
        }
        return true;
    }

    public String toString() {
        return "'" + this.ID + "' (" + this.NAME + ")";
    }

    @Override
    public Object applyTo(T t) {
        return this.CONVERTER.applyTo(t);
    }

    @Override
    public T applyFrom(Object object) {
        return this.CONVERTER.applyFrom(object);
    }
}

