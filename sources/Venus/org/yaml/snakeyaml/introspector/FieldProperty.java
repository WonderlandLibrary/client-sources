/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.yaml.snakeyaml.introspector;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;
import org.yaml.snakeyaml.error.YAMLException;
import org.yaml.snakeyaml.introspector.GenericProperty;
import org.yaml.snakeyaml.util.ArrayUtils;

public class FieldProperty
extends GenericProperty {
    private final Field field;

    public FieldProperty(Field field) {
        super(field.getName(), field.getType(), field.getGenericType());
        this.field = field;
        field.setAccessible(false);
    }

    @Override
    public void set(Object object, Object object2) throws Exception {
        this.field.set(object, object2);
    }

    @Override
    public Object get(Object object) {
        try {
            return this.field.get(object);
        } catch (Exception exception) {
            throw new YAMLException("Unable to access field " + this.field.getName() + " on object " + object + " : " + exception);
        }
    }

    @Override
    public List<Annotation> getAnnotations() {
        return ArrayUtils.toUnmodifiableList(this.field.getAnnotations());
    }

    @Override
    public <A extends Annotation> A getAnnotation(Class<A> clazz) {
        return this.field.getAnnotation(clazz);
    }
}

