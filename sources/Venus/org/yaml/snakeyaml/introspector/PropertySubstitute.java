/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.yaml.snakeyaml.introspector;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.yaml.snakeyaml.error.YAMLException;
import org.yaml.snakeyaml.internal.Logger;
import org.yaml.snakeyaml.introspector.Property;

public class PropertySubstitute
extends Property {
    private static final Logger log = Logger.getLogger(PropertySubstitute.class.getPackage().getName());
    protected Class<?> targetType;
    private final String readMethod;
    private final String writeMethod;
    private transient Method read;
    private transient Method write;
    private Field field;
    protected Class<?>[] parameters;
    private Property delegate;
    private boolean filler;

    public PropertySubstitute(String string, Class<?> clazz, String string2, String string3, Class<?> ... classArray) {
        super(string, clazz);
        this.readMethod = string2;
        this.writeMethod = string3;
        this.setActualTypeArguments(classArray);
        this.filler = false;
    }

    public PropertySubstitute(String string, Class<?> clazz, Class<?> ... classArray) {
        this(string, clazz, (String)null, (String)null, classArray);
    }

    @Override
    public Class<?>[] getActualTypeArguments() {
        if (this.parameters == null && this.delegate != null) {
            return this.delegate.getActualTypeArguments();
        }
        return this.parameters;
    }

    public void setActualTypeArguments(Class<?> ... classArray) {
        this.parameters = classArray != null && classArray.length > 0 ? classArray : null;
    }

    @Override
    public void set(Object object, Object object2) throws Exception {
        if (this.write != null) {
            if (!this.filler) {
                this.write.invoke(object, object2);
            } else if (object2 != null) {
                if (object2 instanceof Collection) {
                    Collection collection = (Collection)object2;
                    for (Object e : collection) {
                        this.write.invoke(object, e);
                    }
                } else if (object2 instanceof Map) {
                    Map map = (Map)object2;
                    for (Map.Entry entry : map.entrySet()) {
                        this.write.invoke(object, entry.getKey(), entry.getValue());
                    }
                } else if (object2.getClass().isArray()) {
                    int n = Array.getLength(object2);
                    for (int i = 0; i < n; ++i) {
                        this.write.invoke(object, Array.get(object2, i));
                    }
                }
            }
        } else if (this.field != null) {
            this.field.set(object, object2);
        } else if (this.delegate != null) {
            this.delegate.set(object, object2);
        } else {
            log.warn("No setter/delegate for '" + this.getName() + "' on object " + object);
        }
    }

    @Override
    public Object get(Object object) {
        try {
            if (this.read != null) {
                return this.read.invoke(object, new Object[0]);
            }
            if (this.field != null) {
                return this.field.get(object);
            }
        } catch (Exception exception) {
            throw new YAMLException("Unable to find getter for property '" + this.getName() + "' on object " + object + ":" + exception);
        }
        if (this.delegate != null) {
            return this.delegate.get(object);
        }
        throw new YAMLException("No getter or delegate for property '" + this.getName() + "' on object " + object);
    }

    @Override
    public List<Annotation> getAnnotations() {
        Annotation[] annotationArray = null;
        if (this.read != null) {
            annotationArray = this.read.getAnnotations();
        } else if (this.field != null) {
            annotationArray = this.field.getAnnotations();
        }
        return annotationArray != null ? Arrays.asList(annotationArray) : this.delegate.getAnnotations();
    }

    @Override
    public <A extends Annotation> A getAnnotation(Class<A> clazz) {
        A a = this.read != null ? this.read.getAnnotation(clazz) : (this.field != null ? this.field.getAnnotation(clazz) : this.delegate.getAnnotation(clazz));
        return a;
    }

    public void setTargetType(Class<?> clazz) {
        if (this.targetType != clazz) {
            this.targetType = clazz;
            String string = this.getName();
            block0: for (Class<?> clazz2 = clazz; clazz2 != null; clazz2 = clazz2.getSuperclass()) {
                for (Field field : clazz2.getDeclaredFields()) {
                    if (!field.getName().equals(string)) continue;
                    int n = field.getModifiers();
                    if (Modifier.isStatic(n) || Modifier.isTransient(n)) continue block0;
                    field.setAccessible(false);
                    this.field = field;
                    continue block0;
                }
            }
            if (this.field == null && log.isLoggable(Logger.Level.WARNING)) {
                log.warn(String.format("Failed to find field for %s.%s", clazz.getName(), this.getName()));
            }
            if (this.readMethod != null) {
                this.read = this.discoverMethod(clazz, this.readMethod, new Class[0]);
            }
            if (this.writeMethod != null) {
                this.filler = false;
                this.write = this.discoverMethod(clazz, this.writeMethod, this.getType());
                if (this.write == null && this.parameters != null) {
                    this.filler = true;
                    this.write = this.discoverMethod(clazz, this.writeMethod, this.parameters);
                }
            }
        }
    }

    private Method discoverMethod(Class<?> clazz, String string, Class<?> ... classArray) {
        for (Class<?> clazz2 = clazz; clazz2 != null; clazz2 = clazz2.getSuperclass()) {
            for (Method method : clazz2.getDeclaredMethods()) {
                Class<?>[] classArray2;
                if (!string.equals(method.getName()) || (classArray2 = method.getParameterTypes()).length != classArray.length) continue;
                boolean bl = true;
                for (int i = 0; i < classArray2.length; ++i) {
                    if (classArray2[i].isAssignableFrom(classArray[i])) continue;
                    bl = false;
                }
                if (!bl) continue;
                method.setAccessible(false);
                return method;
            }
        }
        if (log.isLoggable(Logger.Level.WARNING)) {
            log.warn(String.format("Failed to find [%s(%d args)] for %s.%s", string, classArray.length, this.targetType.getName(), this.getName()));
        }
        return null;
    }

    @Override
    public String getName() {
        String string = super.getName();
        if (string != null) {
            return string;
        }
        return this.delegate != null ? this.delegate.getName() : null;
    }

    @Override
    public Class<?> getType() {
        Class<?> clazz = super.getType();
        if (clazz != null) {
            return clazz;
        }
        return this.delegate != null ? this.delegate.getType() : null;
    }

    @Override
    public boolean isReadable() {
        return this.read != null || this.field != null || this.delegate != null && this.delegate.isReadable();
    }

    @Override
    public boolean isWritable() {
        return this.write != null || this.field != null || this.delegate != null && this.delegate.isWritable();
    }

    public void setDelegate(Property property) {
        this.delegate = property;
        if (this.writeMethod != null && this.write == null && !this.filler) {
            this.filler = true;
            this.write = this.discoverMethod(this.targetType, this.writeMethod, this.getActualTypeArguments());
        }
    }
}

