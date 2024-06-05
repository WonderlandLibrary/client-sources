/*
 * Decompiled with CFR 0.152.
 */
package org.yaml.snakeyaml.introspector;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.yaml.snakeyaml.error.YAMLException;
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

    public PropertySubstitute(String name, Class<?> type, String readMethod, String writeMethod, Class<?> ... params) {
        super(name, type);
        this.readMethod = readMethod;
        this.writeMethod = writeMethod;
        this.setActualTypeArguments(params);
        this.filler = false;
    }

    public PropertySubstitute(String name, Class<?> type, Class<?> ... params) {
        this(name, type, (String)null, (String)null, params);
    }

    @Override
    public Class<?>[] getActualTypeArguments() {
        if (this.parameters != null) return this.parameters;
        if (this.delegate == null) return this.parameters;
        return this.delegate.getActualTypeArguments();
    }

    public void setActualTypeArguments(Class<?> ... args) {
        if (args != null && args.length > 0) {
            this.parameters = args;
            return;
        }
        this.parameters = null;
    }

    @Override
    public void set(Object object, Object value) throws Exception {
        if (this.write != null) {
            if (!this.filler) {
                this.write.invoke(object, value);
                return;
            }
            if (value == null) return;
            if (value instanceof Collection) {
                Collection collection = (Collection)value;
                Iterator i$ = collection.iterator();
                while (i$.hasNext()) {
                    Object val = i$.next();
                    this.write.invoke(object, val);
                }
                return;
            }
            if (value instanceof Map) {
                Map map = (Map)value;
                Iterator i$ = map.entrySet().iterator();
                while (i$.hasNext()) {
                    Map.Entry entry = i$.next();
                    this.write.invoke(object, entry.getKey(), entry.getValue());
                }
                return;
            }
            if (!value.getClass().isArray()) return;
            int len = Array.getLength(value);
            int i = 0;
            while (i < len) {
                this.write.invoke(object, Array.get(value, i));
                ++i;
            }
            return;
        }
        if (this.field != null) {
            this.field.set(object, value);
            return;
        }
        if (this.delegate != null) {
            this.delegate.set(object, value);
            return;
        }
        log.warning("No setter/delegate for '" + this.getName() + "' on object " + object);
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
        }
        catch (Exception e) {
            throw new YAMLException("Unable to find getter for property '" + this.getName() + "' on object " + object + ":" + e);
        }
        if (this.delegate == null) throw new YAMLException("No getter or delegate for property '" + this.getName() + "' on object " + object);
        return this.delegate.get(object);
    }

    @Override
    public List<Annotation> getAnnotations() {
        List<Annotation> list;
        Annotation[] annotations = null;
        if (this.read != null) {
            annotations = this.read.getAnnotations();
        } else if (this.field != null) {
            annotations = this.field.getAnnotations();
        }
        if (annotations != null) {
            list = Arrays.asList(annotations);
            return list;
        }
        list = this.delegate.getAnnotations();
        return list;
    }

    @Override
    public <A extends Annotation> A getAnnotation(Class<A> annotationType) {
        A annotation;
        if (this.read != null) {
            annotation = this.read.getAnnotation(annotationType);
            return annotation;
        }
        if (this.field != null) {
            annotation = this.field.getAnnotation(annotationType);
            return annotation;
        }
        annotation = this.delegate.getAnnotation(annotationType);
        return annotation;
    }

    public void setTargetType(Class<?> targetType) {
        if (this.targetType == targetType) return;
        this.targetType = targetType;
        String name = this.getName();
        block0: for (Class<?> c = targetType; c != null; c = c.getSuperclass()) {
            for (Field f : c.getDeclaredFields()) {
                if (!f.getName().equals(name)) continue;
                int modifiers = f.getModifiers();
                if (Modifier.isStatic(modifiers) || Modifier.isTransient(modifiers)) continue block0;
                f.setAccessible(true);
                this.field = f;
                continue block0;
            }
        }
        if (this.field == null && log.isLoggable(Level.FINE)) {
            log.fine(String.format("Failed to find field for %s.%s", targetType.getName(), this.getName()));
        }
        if (this.readMethod != null) {
            this.read = this.discoverMethod(targetType, this.readMethod, new Class[0]);
        }
        if (this.writeMethod == null) return;
        this.filler = false;
        this.write = this.discoverMethod(targetType, this.writeMethod, this.getType());
        if (this.write != null) return;
        if (this.parameters == null) return;
        this.filler = true;
        this.write = this.discoverMethod(targetType, this.writeMethod, this.parameters);
    }

    private Method discoverMethod(Class<?> type, String name, Class<?> ... params) {
        Class<?> c = type;
        while (true) {
            if (c == null) {
                if (!log.isLoggable(Level.FINE)) return null;
                log.fine(String.format("Failed to find [%s(%d args)] for %s.%s", name, params.length, this.targetType.getName(), this.getName()));
                return null;
            }
            for (Method method : c.getDeclaredMethods()) {
                Class<?>[] parameterTypes;
                if (!name.equals(method.getName()) || (parameterTypes = method.getParameterTypes()).length != params.length) continue;
                boolean found = true;
                for (int i = 0; i < parameterTypes.length; ++i) {
                    if (parameterTypes[i].isAssignableFrom(params[i])) continue;
                    found = false;
                }
                if (!found) continue;
                method.setAccessible(true);
                return method;
            }
            c = c.getSuperclass();
        }
    }

    @Override
    public String getName() {
        String n = super.getName();
        if (n != null) {
            return n;
        }
        if (this.delegate == null) return null;
        String string = this.delegate.getName();
        return string;
    }

    @Override
    public Class<?> getType() {
        Class<?> t = super.getType();
        if (t != null) {
            return t;
        }
        if (this.delegate == null) return null;
        Class<?> clazz = this.delegate.getType();
        return clazz;
    }

    @Override
    public boolean isReadable() {
        if (this.read != null) return true;
        if (this.field != null) return true;
        if (this.delegate == null) return false;
        if (!this.delegate.isReadable()) return false;
        return true;
    }

    @Override
    public boolean isWritable() {
        if (this.write != null) return true;
        if (this.field != null) return true;
        if (this.delegate == null) return false;
        if (!this.delegate.isWritable()) return false;
        return true;
    }

    public void setDelegate(Property delegate) {
        this.delegate = delegate;
        if (this.writeMethod == null) return;
        if (this.write != null) return;
        if (this.filler) return;
        this.filler = true;
        this.write = this.discoverMethod(this.targetType, this.writeMethod, this.getActualTypeArguments());
    }
}

