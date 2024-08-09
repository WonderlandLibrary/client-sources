/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.yaml.snakeyaml.introspector;

import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.List;
import org.yaml.snakeyaml.error.YAMLException;
import org.yaml.snakeyaml.introspector.GenericProperty;
import org.yaml.snakeyaml.util.ArrayUtils;

public class MethodProperty
extends GenericProperty {
    private final PropertyDescriptor property;
    private final boolean readable;
    private final boolean writable;

    private static Type discoverGenericType(PropertyDescriptor propertyDescriptor) {
        Type[] typeArray;
        Method method = propertyDescriptor.getReadMethod();
        if (method != null) {
            return method.getGenericReturnType();
        }
        Method method2 = propertyDescriptor.getWriteMethod();
        if (method2 != null && (typeArray = method2.getGenericParameterTypes()).length > 0) {
            return typeArray[0];
        }
        return null;
    }

    public MethodProperty(PropertyDescriptor propertyDescriptor) {
        super(propertyDescriptor.getName(), propertyDescriptor.getPropertyType(), MethodProperty.discoverGenericType(propertyDescriptor));
        this.property = propertyDescriptor;
        this.readable = propertyDescriptor.getReadMethod() != null;
        this.writable = propertyDescriptor.getWriteMethod() != null;
    }

    @Override
    public void set(Object object, Object object2) throws Exception {
        if (!this.writable) {
            throw new YAMLException("No writable property '" + this.getName() + "' on class: " + object.getClass().getName());
        }
        this.property.getWriteMethod().invoke(object, object2);
    }

    @Override
    public Object get(Object object) {
        try {
            this.property.getReadMethod().setAccessible(false);
            return this.property.getReadMethod().invoke(object, new Object[0]);
        } catch (Exception exception) {
            throw new YAMLException("Unable to find getter for property '" + this.property.getName() + "' on object " + object + ":" + exception);
        }
    }

    @Override
    public List<Annotation> getAnnotations() {
        List<Annotation> list = this.isReadable() && this.isWritable() ? ArrayUtils.toUnmodifiableCompositeList(this.property.getReadMethod().getAnnotations(), this.property.getWriteMethod().getAnnotations()) : (this.isReadable() ? ArrayUtils.toUnmodifiableList(this.property.getReadMethod().getAnnotations()) : ArrayUtils.toUnmodifiableList(this.property.getWriteMethod().getAnnotations()));
        return list;
    }

    @Override
    public <A extends Annotation> A getAnnotation(Class<A> clazz) {
        A a = null;
        if (this.isReadable()) {
            a = this.property.getReadMethod().getAnnotation(clazz);
        }
        if (a == null && this.isWritable()) {
            a = this.property.getWriteMethod().getAnnotation(clazz);
        }
        return a;
    }

    @Override
    public boolean isWritable() {
        return this.writable;
    }

    @Override
    public boolean isReadable() {
        return this.readable;
    }
}

