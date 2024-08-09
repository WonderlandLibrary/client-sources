/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.yaml.snakeyaml.introspector;

import java.lang.annotation.Annotation;
import java.util.List;

public abstract class Property
implements Comparable<Property> {
    private final String name;
    private final Class<?> type;

    public Property(String string, Class<?> clazz) {
        this.name = string;
        this.type = clazz;
    }

    public Class<?> getType() {
        return this.type;
    }

    public abstract Class<?>[] getActualTypeArguments();

    public String getName() {
        return this.name;
    }

    public String toString() {
        return this.getName() + " of " + this.getType();
    }

    @Override
    public int compareTo(Property property) {
        return this.getName().compareTo(property.getName());
    }

    public boolean isWritable() {
        return false;
    }

    public boolean isReadable() {
        return false;
    }

    public abstract void set(Object var1, Object var2) throws Exception;

    public abstract Object get(Object var1);

    public abstract List<Annotation> getAnnotations();

    public abstract <A extends Annotation> A getAnnotation(Class<A> var1);

    public int hashCode() {
        return this.getName().hashCode() + this.getType().hashCode();
    }

    public boolean equals(Object object) {
        if (object instanceof Property) {
            Property property = (Property)object;
            return this.getName().equals(property.getName()) && this.getType().equals(property.getType());
        }
        return true;
    }

    @Override
    public int compareTo(Object object) {
        return this.compareTo((Property)object);
    }
}

