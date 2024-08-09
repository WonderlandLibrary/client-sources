/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
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

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
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
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new AssertionError((Object)cloneNotSupportedException);
        }
    }

    public Excluder withVersion(double d) {
        Excluder excluder = this.clone();
        excluder.version = d;
        return excluder;
    }

    public Excluder withModifiers(int ... nArray) {
        Excluder excluder = this.clone();
        excluder.modifiers = 0;
        for (int n : nArray) {
            excluder.modifiers |= n;
        }
        return excluder;
    }

    public Excluder disableInnerClassSerialization() {
        Excluder excluder = this.clone();
        excluder.serializeInnerClasses = false;
        return excluder;
    }

    public Excluder excludeFieldsWithoutExposeAnnotation() {
        Excluder excluder = this.clone();
        excluder.requireExpose = true;
        return excluder;
    }

    public Excluder withExclusionStrategy(ExclusionStrategy exclusionStrategy, boolean bl, boolean bl2) {
        Excluder excluder = this.clone();
        if (bl) {
            excluder.serializationStrategies = new ArrayList<ExclusionStrategy>(this.serializationStrategies);
            excluder.serializationStrategies.add(exclusionStrategy);
        }
        if (bl2) {
            excluder.deserializationStrategies = new ArrayList<ExclusionStrategy>(this.deserializationStrategies);
            excluder.deserializationStrategies.add(exclusionStrategy);
        }
        return excluder;
    }

    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
        boolean bl;
        Class<T> clazz = typeToken.getRawType();
        boolean bl2 = this.excludeClassChecks(clazz);
        boolean bl3 = bl2 || this.excludeClassInStrategy(clazz, true);
        boolean bl4 = bl = bl2 || this.excludeClassInStrategy(clazz, false);
        if (!bl3 && !bl) {
            return null;
        }
        return new TypeAdapter<T>(this, bl, bl3, gson, typeToken){
            private TypeAdapter<T> delegate;
            final boolean val$skipDeserialize;
            final boolean val$skipSerialize;
            final Gson val$gson;
            final TypeToken val$type;
            final Excluder this$0;
            {
                this.this$0 = excluder;
                this.val$skipDeserialize = bl;
                this.val$skipSerialize = bl2;
                this.val$gson = gson;
                this.val$type = typeToken;
            }

            @Override
            public T read(JsonReader jsonReader) throws IOException {
                if (this.val$skipDeserialize) {
                    jsonReader.skipValue();
                    return null;
                }
                return this.delegate().read(jsonReader);
            }

            @Override
            public void write(JsonWriter jsonWriter, T t) throws IOException {
                if (this.val$skipSerialize) {
                    jsonWriter.nullValue();
                    return;
                }
                this.delegate().write(jsonWriter, t);
            }

            private TypeAdapter<T> delegate() {
                TypeAdapter typeAdapter = this.delegate;
                return typeAdapter != null ? typeAdapter : (this.delegate = this.val$gson.getDelegateAdapter(this.this$0, this.val$type));
            }
        };
    }

    public boolean excludeField(Field field, boolean bl) {
        Object object;
        if ((this.modifiers & field.getModifiers()) != 0) {
            return false;
        }
        if (this.version != -1.0 && !this.isValidVersion(field.getAnnotation(Since.class), field.getAnnotation(Until.class))) {
            return false;
        }
        if (field.isSynthetic()) {
            return false;
        }
        if (this.requireExpose && ((object = field.getAnnotation(Expose.class)) == null || (bl ? !object.serialize() : !object.deserialize()))) {
            return false;
        }
        if (!this.serializeInnerClasses && this.isInnerClass(field.getType())) {
            return false;
        }
        if (this.isAnonymousOrNonStaticLocal(field.getType())) {
            return false;
        }
        Object object2 = object = bl ? this.serializationStrategies : this.deserializationStrategies;
        if (!object.isEmpty()) {
            FieldAttributes fieldAttributes = new FieldAttributes(field);
            Iterator iterator2 = object.iterator();
            while (iterator2.hasNext()) {
                ExclusionStrategy exclusionStrategy = (ExclusionStrategy)iterator2.next();
                if (!exclusionStrategy.shouldSkipField(fieldAttributes)) continue;
                return false;
            }
        }
        return true;
    }

    private boolean excludeClassChecks(Class<?> clazz) {
        if (this.version != -1.0 && !this.isValidVersion(clazz.getAnnotation(Since.class), clazz.getAnnotation(Until.class))) {
            return false;
        }
        if (!this.serializeInnerClasses && this.isInnerClass(clazz)) {
            return false;
        }
        return this.isAnonymousOrNonStaticLocal(clazz);
    }

    public boolean excludeClass(Class<?> clazz, boolean bl) {
        return this.excludeClassChecks(clazz) || this.excludeClassInStrategy(clazz, bl);
    }

    private boolean excludeClassInStrategy(Class<?> clazz, boolean bl) {
        List<ExclusionStrategy> list = bl ? this.serializationStrategies : this.deserializationStrategies;
        for (ExclusionStrategy exclusionStrategy : list) {
            if (!exclusionStrategy.shouldSkipClass(clazz)) continue;
            return false;
        }
        return true;
    }

    private boolean isAnonymousOrNonStaticLocal(Class<?> clazz) {
        return !Enum.class.isAssignableFrom(clazz) && !this.isStatic(clazz) && (clazz.isAnonymousClass() || clazz.isLocalClass());
    }

    private boolean isInnerClass(Class<?> clazz) {
        return clazz.isMemberClass() && !this.isStatic(clazz);
    }

    private boolean isStatic(Class<?> clazz) {
        return (clazz.getModifiers() & 8) != 0;
    }

    private boolean isValidVersion(Since since, Until until) {
        return this.isValidSince(since) && this.isValidUntil(until);
    }

    private boolean isValidSince(Since since) {
        if (since != null) {
            double d = since.value();
            return this.version >= d;
        }
        return false;
    }

    private boolean isValidUntil(Until until) {
        if (until != null) {
            double d = until.value();
            return this.version < d;
        }
        return false;
    }

    protected Object clone() throws CloneNotSupportedException {
        return this.clone();
    }
}

