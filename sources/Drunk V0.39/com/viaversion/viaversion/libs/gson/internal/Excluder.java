/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.libs.gson.internal;

import com.viaversion.viaversion.libs.gson.ExclusionStrategy;
import com.viaversion.viaversion.libs.gson.FieldAttributes;
import com.viaversion.viaversion.libs.gson.Gson;
import com.viaversion.viaversion.libs.gson.TypeAdapter;
import com.viaversion.viaversion.libs.gson.TypeAdapterFactory;
import com.viaversion.viaversion.libs.gson.annotations.Expose;
import com.viaversion.viaversion.libs.gson.annotations.Since;
import com.viaversion.viaversion.libs.gson.annotations.Until;
import com.viaversion.viaversion.libs.gson.reflect.TypeToken;
import com.viaversion.viaversion.libs.gson.stream.JsonReader;
import com.viaversion.viaversion.libs.gson.stream.JsonWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public final class Excluder
implements TypeAdapterFactory,
Cloneable {
    private static final double IGNORE_VERSIONS = -1.0;
    public static final Excluder DEFAULT = new Excluder();
    private double version = -1.0;
    private int modifiers = 136;
    private boolean serializeInnerClasses = true;
    private boolean requireExpose;
    private List<ExclusionStrategy> serializationStrategies = Collections.emptyList();
    private List<ExclusionStrategy> deserializationStrategies = Collections.emptyList();

    protected Excluder clone() {
        try {
            return (Excluder)super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new AssertionError((Object)e);
        }
    }

    public Excluder withVersion(double ignoreVersionsAfter) {
        Excluder result = this.clone();
        result.version = ignoreVersionsAfter;
        return result;
    }

    public Excluder withModifiers(int ... modifiers) {
        Excluder result = this.clone();
        result.modifiers = 0;
        int[] nArray = modifiers;
        int n = nArray.length;
        int n2 = 0;
        while (n2 < n) {
            int modifier = nArray[n2];
            result.modifiers |= modifier;
            ++n2;
        }
        return result;
    }

    public Excluder disableInnerClassSerialization() {
        Excluder result = this.clone();
        result.serializeInnerClasses = false;
        return result;
    }

    public Excluder excludeFieldsWithoutExposeAnnotation() {
        Excluder result = this.clone();
        result.requireExpose = true;
        return result;
    }

    public Excluder withExclusionStrategy(ExclusionStrategy exclusionStrategy, boolean serialization, boolean deserialization) {
        Excluder result = this.clone();
        if (serialization) {
            result.serializationStrategies = new ArrayList<ExclusionStrategy>(this.serializationStrategies);
            result.serializationStrategies.add(exclusionStrategy);
        }
        if (!deserialization) return result;
        result.deserializationStrategies = new ArrayList<ExclusionStrategy>(this.deserializationStrategies);
        result.deserializationStrategies.add(exclusionStrategy);
        return result;
    }

    @Override
    public <T> TypeAdapter<T> create(final Gson gson, final TypeToken<T> type) {
        Class<T> rawType = type.getRawType();
        boolean excludeClass = this.excludeClassChecks(rawType);
        final boolean skipSerialize = excludeClass || this.excludeClassInStrategy(rawType, true);
        final boolean skipDeserialize = excludeClass || this.excludeClassInStrategy(rawType, false);
        if (skipSerialize) return new TypeAdapter<T>(){
            private TypeAdapter<T> delegate;

            @Override
            public T read(JsonReader in) throws IOException {
                if (!skipDeserialize) return this.delegate().read(in);
                in.skipValue();
                return null;
            }

            @Override
            public void write(JsonWriter out, T value) throws IOException {
                if (skipSerialize) {
                    out.nullValue();
                    return;
                }
                this.delegate().write(out, value);
            }

            private TypeAdapter<T> delegate() {
                TypeAdapter typeAdapter;
                TypeAdapter d = this.delegate;
                if (d != null) {
                    typeAdapter = d;
                    return typeAdapter;
                }
                typeAdapter = this.delegate = gson.getDelegateAdapter(Excluder.this, type);
                return typeAdapter;
            }
        };
        if (skipDeserialize) return new /* invalid duplicate definition of identical inner class */;
        return null;
    }

    public boolean excludeField(Field field, boolean serialize) {
        ExclusionStrategy exclusionStrategy;
        if ((this.modifiers & field.getModifiers()) != 0) {
            return true;
        }
        if (this.version != -1.0 && !this.isValidVersion(field.getAnnotation(Since.class), field.getAnnotation(Until.class))) {
            return true;
        }
        if (field.isSynthetic()) {
            return true;
        }
        if (this.requireExpose) {
            Expose annotation = field.getAnnotation(Expose.class);
            if (annotation == null) return true;
            if (serialize ? !annotation.serialize() : !annotation.deserialize()) {
                return true;
            }
        }
        if (!this.serializeInnerClasses && this.isInnerClass(field.getType())) {
            return true;
        }
        if (this.isAnonymousOrLocal(field.getType())) {
            return true;
        }
        List<ExclusionStrategy> list = serialize ? this.serializationStrategies : this.deserializationStrategies;
        if (list.isEmpty()) return false;
        FieldAttributes fieldAttributes = new FieldAttributes(field);
        Iterator<ExclusionStrategy> iterator = list.iterator();
        do {
            if (!iterator.hasNext()) return false;
        } while (!(exclusionStrategy = iterator.next()).shouldSkipField(fieldAttributes));
        return true;
    }

    private boolean excludeClassChecks(Class<?> clazz) {
        if (this.version != -1.0 && !this.isValidVersion(clazz.getAnnotation(Since.class), clazz.getAnnotation(Until.class))) {
            return true;
        }
        if (!this.serializeInnerClasses && this.isInnerClass(clazz)) {
            return true;
        }
        if (!this.isAnonymousOrLocal(clazz)) return false;
        return true;
    }

    public boolean excludeClass(Class<?> clazz, boolean serialize) {
        if (this.excludeClassChecks(clazz)) return true;
        if (this.excludeClassInStrategy(clazz, serialize)) return true;
        return false;
    }

    private boolean excludeClassInStrategy(Class<?> clazz, boolean serialize) {
        ExclusionStrategy exclusionStrategy;
        List<ExclusionStrategy> list = serialize ? this.serializationStrategies : this.deserializationStrategies;
        Iterator<ExclusionStrategy> iterator = list.iterator();
        do {
            if (!iterator.hasNext()) return false;
        } while (!(exclusionStrategy = iterator.next()).shouldSkipClass(clazz));
        return true;
    }

    private boolean isAnonymousOrLocal(Class<?> clazz) {
        if (Enum.class.isAssignableFrom(clazz)) return false;
        if (clazz.isAnonymousClass()) return true;
        if (!clazz.isLocalClass()) return false;
        return true;
    }

    private boolean isInnerClass(Class<?> clazz) {
        if (!clazz.isMemberClass()) return false;
        if (this.isStatic(clazz)) return false;
        return true;
    }

    private boolean isStatic(Class<?> clazz) {
        if ((clazz.getModifiers() & 8) == 0) return false;
        return true;
    }

    private boolean isValidVersion(Since since, Until until) {
        if (!this.isValidSince(since)) return false;
        if (!this.isValidUntil(until)) return false;
        return true;
    }

    private boolean isValidSince(Since annotation) {
        if (annotation == null) return true;
        double annotationVersion = annotation.value();
        if (!(annotationVersion > this.version)) return true;
        return false;
    }

    private boolean isValidUntil(Until annotation) {
        if (annotation == null) return true;
        double annotationVersion = annotation.value();
        if (!(annotationVersion <= this.version)) return true;
        return false;
    }
}

