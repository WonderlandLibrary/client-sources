/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.lang3.builder;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringExclude;
import org.apache.commons.lang3.builder.ToStringStyle;

public class ReflectionToStringBuilder
extends ToStringBuilder {
    private boolean appendStatics = false;
    private boolean appendTransients = false;
    protected String[] excludeFieldNames;
    private Class<?> upToClass = null;

    public static String toString(Object object) {
        return ReflectionToStringBuilder.toString(object, null, false, false, null);
    }

    public static String toString(Object object, ToStringStyle toStringStyle) {
        return ReflectionToStringBuilder.toString(object, toStringStyle, false, false, null);
    }

    public static String toString(Object object, ToStringStyle toStringStyle, boolean bl) {
        return ReflectionToStringBuilder.toString(object, toStringStyle, bl, false, null);
    }

    public static String toString(Object object, ToStringStyle toStringStyle, boolean bl, boolean bl2) {
        return ReflectionToStringBuilder.toString(object, toStringStyle, bl, bl2, null);
    }

    public static <T> String toString(T t, ToStringStyle toStringStyle, boolean bl, boolean bl2, Class<? super T> clazz) {
        return new ReflectionToStringBuilder(t, toStringStyle, null, clazz, bl, bl2).toString();
    }

    public static String toStringExclude(Object object, Collection<String> collection) {
        return ReflectionToStringBuilder.toStringExclude(object, ReflectionToStringBuilder.toNoNullStringArray(collection));
    }

    static String[] toNoNullStringArray(Collection<String> collection) {
        if (collection == null) {
            return ArrayUtils.EMPTY_STRING_ARRAY;
        }
        return ReflectionToStringBuilder.toNoNullStringArray(collection.toArray());
    }

    static String[] toNoNullStringArray(Object[] objectArray) {
        ArrayList<String> arrayList = new ArrayList<String>(objectArray.length);
        for (Object object : objectArray) {
            if (object == null) continue;
            arrayList.add(object.toString());
        }
        return arrayList.toArray(ArrayUtils.EMPTY_STRING_ARRAY);
    }

    public static String toStringExclude(Object object, String ... stringArray) {
        return new ReflectionToStringBuilder(object).setExcludeFieldNames(stringArray).toString();
    }

    private static Object checkNotNull(Object object) {
        if (object == null) {
            throw new IllegalArgumentException("The Object passed in should not be null.");
        }
        return object;
    }

    public ReflectionToStringBuilder(Object object) {
        super(ReflectionToStringBuilder.checkNotNull(object));
    }

    public ReflectionToStringBuilder(Object object, ToStringStyle toStringStyle) {
        super(ReflectionToStringBuilder.checkNotNull(object), toStringStyle);
    }

    public ReflectionToStringBuilder(Object object, ToStringStyle toStringStyle, StringBuffer stringBuffer) {
        super(ReflectionToStringBuilder.checkNotNull(object), toStringStyle, stringBuffer);
    }

    public <T> ReflectionToStringBuilder(T t, ToStringStyle toStringStyle, StringBuffer stringBuffer, Class<? super T> clazz, boolean bl, boolean bl2) {
        super(ReflectionToStringBuilder.checkNotNull(t), toStringStyle, stringBuffer);
        this.setUpToClass(clazz);
        this.setAppendTransients(bl);
        this.setAppendStatics(bl2);
    }

    protected boolean accept(Field field) {
        if (field.getName().indexOf(36) != -1) {
            return true;
        }
        if (Modifier.isTransient(field.getModifiers()) && !this.isAppendTransients()) {
            return true;
        }
        if (Modifier.isStatic(field.getModifiers()) && !this.isAppendStatics()) {
            return true;
        }
        if (this.excludeFieldNames != null && Arrays.binarySearch(this.excludeFieldNames, field.getName()) >= 0) {
            return true;
        }
        return field.isAnnotationPresent(ToStringExclude.class);
    }

    protected void appendFieldsIn(Class<?> clazz) {
        if (clazz.isArray()) {
            this.reflectionAppendArray(this.getObject());
            return;
        }
        AccessibleObject[] accessibleObjectArray = clazz.getDeclaredFields();
        AccessibleObject.setAccessible(accessibleObjectArray, true);
        for (AccessibleObject accessibleObject : accessibleObjectArray) {
            String string = ((Field)accessibleObject).getName();
            if (!this.accept((Field)accessibleObject)) continue;
            try {
                Object object = this.getValue((Field)accessibleObject);
                this.append(string, object);
            } catch (IllegalAccessException illegalAccessException) {
                throw new InternalError("Unexpected IllegalAccessException: " + illegalAccessException.getMessage());
            }
        }
    }

    public String[] getExcludeFieldNames() {
        return (String[])this.excludeFieldNames.clone();
    }

    public Class<?> getUpToClass() {
        return this.upToClass;
    }

    protected Object getValue(Field field) throws IllegalArgumentException, IllegalAccessException {
        return field.get(this.getObject());
    }

    public boolean isAppendStatics() {
        return this.appendStatics;
    }

    public boolean isAppendTransients() {
        return this.appendTransients;
    }

    public ReflectionToStringBuilder reflectionAppendArray(Object object) {
        this.getStyle().reflectionAppendArrayDetail(this.getStringBuffer(), null, object);
        return this;
    }

    public void setAppendStatics(boolean bl) {
        this.appendStatics = bl;
    }

    public void setAppendTransients(boolean bl) {
        this.appendTransients = bl;
    }

    public ReflectionToStringBuilder setExcludeFieldNames(String ... stringArray) {
        if (stringArray == null) {
            this.excludeFieldNames = null;
        } else {
            this.excludeFieldNames = ReflectionToStringBuilder.toNoNullStringArray(stringArray);
            Arrays.sort(this.excludeFieldNames);
        }
        return this;
    }

    public void setUpToClass(Class<?> clazz) {
        Object object;
        if (clazz != null && (object = this.getObject()) != null && !clazz.isInstance(object)) {
            throw new IllegalArgumentException("Specified class is not a superclass of the object");
        }
        this.upToClass = clazz;
    }

    @Override
    public String toString() {
        Class<?> clazz;
        if (this.getObject() == null) {
            return this.getStyle().getNullText();
        }
        this.appendFieldsIn(clazz);
        for (clazz = this.getObject().getClass(); clazz.getSuperclass() != null && clazz != this.getUpToClass(); clazz = clazz.getSuperclass()) {
            this.appendFieldsIn(clazz);
        }
        return super.toString();
    }
}

