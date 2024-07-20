/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.gson;

import com.viaversion.viaversion.libs.gson.FieldNamingPolicy;
import com.viaversion.viaversion.libs.gson.FieldNamingStrategy;
import com.viaversion.viaversion.libs.gson.GsonBuilder;
import com.viaversion.viaversion.libs.gson.InstanceCreator;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonIOException;
import com.viaversion.viaversion.libs.gson.JsonNull;
import com.viaversion.viaversion.libs.gson.JsonSyntaxException;
import com.viaversion.viaversion.libs.gson.LongSerializationPolicy;
import com.viaversion.viaversion.libs.gson.ReflectionAccessFilter;
import com.viaversion.viaversion.libs.gson.ToNumberPolicy;
import com.viaversion.viaversion.libs.gson.ToNumberStrategy;
import com.viaversion.viaversion.libs.gson.TypeAdapter;
import com.viaversion.viaversion.libs.gson.TypeAdapterFactory;
import com.viaversion.viaversion.libs.gson.internal.ConstructorConstructor;
import com.viaversion.viaversion.libs.gson.internal.Excluder;
import com.viaversion.viaversion.libs.gson.internal.LazilyParsedNumber;
import com.viaversion.viaversion.libs.gson.internal.Primitives;
import com.viaversion.viaversion.libs.gson.internal.Streams;
import com.viaversion.viaversion.libs.gson.internal.bind.ArrayTypeAdapter;
import com.viaversion.viaversion.libs.gson.internal.bind.CollectionTypeAdapterFactory;
import com.viaversion.viaversion.libs.gson.internal.bind.DateTypeAdapter;
import com.viaversion.viaversion.libs.gson.internal.bind.JsonAdapterAnnotationTypeAdapterFactory;
import com.viaversion.viaversion.libs.gson.internal.bind.JsonTreeReader;
import com.viaversion.viaversion.libs.gson.internal.bind.JsonTreeWriter;
import com.viaversion.viaversion.libs.gson.internal.bind.MapTypeAdapterFactory;
import com.viaversion.viaversion.libs.gson.internal.bind.NumberTypeAdapter;
import com.viaversion.viaversion.libs.gson.internal.bind.ObjectTypeAdapter;
import com.viaversion.viaversion.libs.gson.internal.bind.ReflectiveTypeAdapterFactory;
import com.viaversion.viaversion.libs.gson.internal.bind.SerializationDelegatingTypeAdapter;
import com.viaversion.viaversion.libs.gson.internal.bind.TypeAdapters;
import com.viaversion.viaversion.libs.gson.internal.sql.SqlTypesSupport;
import com.viaversion.viaversion.libs.gson.reflect.TypeToken;
import com.viaversion.viaversion.libs.gson.stream.JsonReader;
import com.viaversion.viaversion.libs.gson.stream.JsonToken;
import com.viaversion.viaversion.libs.gson.stream.JsonWriter;
import com.viaversion.viaversion.libs.gson.stream.MalformedJsonException;
import java.io.EOFException;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicLongArray;

public final class Gson {
    static final boolean DEFAULT_JSON_NON_EXECUTABLE = false;
    static final boolean DEFAULT_LENIENT = false;
    static final boolean DEFAULT_PRETTY_PRINT = false;
    static final boolean DEFAULT_ESCAPE_HTML = true;
    static final boolean DEFAULT_SERIALIZE_NULLS = false;
    static final boolean DEFAULT_COMPLEX_MAP_KEYS = false;
    static final boolean DEFAULT_SPECIALIZE_FLOAT_VALUES = false;
    static final boolean DEFAULT_USE_JDK_UNSAFE = true;
    static final String DEFAULT_DATE_PATTERN = null;
    static final FieldNamingStrategy DEFAULT_FIELD_NAMING_STRATEGY = FieldNamingPolicy.IDENTITY;
    static final ToNumberStrategy DEFAULT_OBJECT_TO_NUMBER_STRATEGY = ToNumberPolicy.DOUBLE;
    static final ToNumberStrategy DEFAULT_NUMBER_TO_NUMBER_STRATEGY = ToNumberPolicy.LAZILY_PARSED_NUMBER;
    private static final String JSON_NON_EXECUTABLE_PREFIX = ")]}'\n";
    private final ThreadLocal<Map<TypeToken<?>, TypeAdapter<?>>> threadLocalAdapterResults = new ThreadLocal();
    private final ConcurrentMap<TypeToken<?>, TypeAdapter<?>> typeTokenCache = new ConcurrentHashMap();
    private final ConstructorConstructor constructorConstructor;
    private final JsonAdapterAnnotationTypeAdapterFactory jsonAdapterFactory;
    final List<TypeAdapterFactory> factories;
    final Excluder excluder;
    final FieldNamingStrategy fieldNamingStrategy;
    final Map<Type, InstanceCreator<?>> instanceCreators;
    final boolean serializeNulls;
    final boolean complexMapKeySerialization;
    final boolean generateNonExecutableJson;
    final boolean htmlSafe;
    final boolean prettyPrinting;
    final boolean lenient;
    final boolean serializeSpecialFloatingPointValues;
    final boolean useJdkUnsafe;
    final String datePattern;
    final int dateStyle;
    final int timeStyle;
    final LongSerializationPolicy longSerializationPolicy;
    final List<TypeAdapterFactory> builderFactories;
    final List<TypeAdapterFactory> builderHierarchyFactories;
    final ToNumberStrategy objectToNumberStrategy;
    final ToNumberStrategy numberToNumberStrategy;
    final List<ReflectionAccessFilter> reflectionFilters;

    public Gson() {
        this(Excluder.DEFAULT, DEFAULT_FIELD_NAMING_STRATEGY, Collections.emptyMap(), false, false, false, true, false, false, false, true, LongSerializationPolicy.DEFAULT, DEFAULT_DATE_PATTERN, 2, 2, Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), DEFAULT_OBJECT_TO_NUMBER_STRATEGY, DEFAULT_NUMBER_TO_NUMBER_STRATEGY, Collections.emptyList());
    }

    Gson(Excluder excluder, FieldNamingStrategy fieldNamingStrategy, Map<Type, InstanceCreator<?>> instanceCreators, boolean serializeNulls, boolean complexMapKeySerialization, boolean generateNonExecutableGson, boolean htmlSafe, boolean prettyPrinting, boolean lenient, boolean serializeSpecialFloatingPointValues, boolean useJdkUnsafe, LongSerializationPolicy longSerializationPolicy, String datePattern, int dateStyle, int timeStyle, List<TypeAdapterFactory> builderFactories, List<TypeAdapterFactory> builderHierarchyFactories, List<TypeAdapterFactory> factoriesToBeAdded, ToNumberStrategy objectToNumberStrategy, ToNumberStrategy numberToNumberStrategy, List<ReflectionAccessFilter> reflectionFilters) {
        this.excluder = excluder;
        this.fieldNamingStrategy = fieldNamingStrategy;
        this.instanceCreators = instanceCreators;
        this.constructorConstructor = new ConstructorConstructor(instanceCreators, useJdkUnsafe, reflectionFilters);
        this.serializeNulls = serializeNulls;
        this.complexMapKeySerialization = complexMapKeySerialization;
        this.generateNonExecutableJson = generateNonExecutableGson;
        this.htmlSafe = htmlSafe;
        this.prettyPrinting = prettyPrinting;
        this.lenient = lenient;
        this.serializeSpecialFloatingPointValues = serializeSpecialFloatingPointValues;
        this.useJdkUnsafe = useJdkUnsafe;
        this.longSerializationPolicy = longSerializationPolicy;
        this.datePattern = datePattern;
        this.dateStyle = dateStyle;
        this.timeStyle = timeStyle;
        this.builderFactories = builderFactories;
        this.builderHierarchyFactories = builderHierarchyFactories;
        this.objectToNumberStrategy = objectToNumberStrategy;
        this.numberToNumberStrategy = numberToNumberStrategy;
        this.reflectionFilters = reflectionFilters;
        ArrayList<TypeAdapterFactory> factories = new ArrayList<TypeAdapterFactory>();
        factories.add(TypeAdapters.JSON_ELEMENT_FACTORY);
        factories.add(ObjectTypeAdapter.getFactory(objectToNumberStrategy));
        factories.add(excluder);
        factories.addAll(factoriesToBeAdded);
        factories.add(TypeAdapters.STRING_FACTORY);
        factories.add(TypeAdapters.INTEGER_FACTORY);
        factories.add(TypeAdapters.BOOLEAN_FACTORY);
        factories.add(TypeAdapters.BYTE_FACTORY);
        factories.add(TypeAdapters.SHORT_FACTORY);
        TypeAdapter<Number> longAdapter = Gson.longAdapter(longSerializationPolicy);
        factories.add(TypeAdapters.newFactory(Long.TYPE, Long.class, longAdapter));
        factories.add(TypeAdapters.newFactory(Double.TYPE, Double.class, this.doubleAdapter(serializeSpecialFloatingPointValues)));
        factories.add(TypeAdapters.newFactory(Float.TYPE, Float.class, this.floatAdapter(serializeSpecialFloatingPointValues)));
        factories.add(NumberTypeAdapter.getFactory(numberToNumberStrategy));
        factories.add(TypeAdapters.ATOMIC_INTEGER_FACTORY);
        factories.add(TypeAdapters.ATOMIC_BOOLEAN_FACTORY);
        factories.add(TypeAdapters.newFactory(AtomicLong.class, Gson.atomicLongAdapter(longAdapter)));
        factories.add(TypeAdapters.newFactory(AtomicLongArray.class, Gson.atomicLongArrayAdapter(longAdapter)));
        factories.add(TypeAdapters.ATOMIC_INTEGER_ARRAY_FACTORY);
        factories.add(TypeAdapters.CHARACTER_FACTORY);
        factories.add(TypeAdapters.STRING_BUILDER_FACTORY);
        factories.add(TypeAdapters.STRING_BUFFER_FACTORY);
        factories.add(TypeAdapters.newFactory(BigDecimal.class, TypeAdapters.BIG_DECIMAL));
        factories.add(TypeAdapters.newFactory(BigInteger.class, TypeAdapters.BIG_INTEGER));
        factories.add(TypeAdapters.newFactory(LazilyParsedNumber.class, TypeAdapters.LAZILY_PARSED_NUMBER));
        factories.add(TypeAdapters.URL_FACTORY);
        factories.add(TypeAdapters.URI_FACTORY);
        factories.add(TypeAdapters.UUID_FACTORY);
        factories.add(TypeAdapters.CURRENCY_FACTORY);
        factories.add(TypeAdapters.LOCALE_FACTORY);
        factories.add(TypeAdapters.INET_ADDRESS_FACTORY);
        factories.add(TypeAdapters.BIT_SET_FACTORY);
        factories.add(DateTypeAdapter.FACTORY);
        factories.add(TypeAdapters.CALENDAR_FACTORY);
        if (SqlTypesSupport.SUPPORTS_SQL_TYPES) {
            factories.add(SqlTypesSupport.TIME_FACTORY);
            factories.add(SqlTypesSupport.DATE_FACTORY);
            factories.add(SqlTypesSupport.TIMESTAMP_FACTORY);
        }
        factories.add(ArrayTypeAdapter.FACTORY);
        factories.add(TypeAdapters.CLASS_FACTORY);
        factories.add(new CollectionTypeAdapterFactory(this.constructorConstructor));
        factories.add(new MapTypeAdapterFactory(this.constructorConstructor, complexMapKeySerialization));
        this.jsonAdapterFactory = new JsonAdapterAnnotationTypeAdapterFactory(this.constructorConstructor);
        factories.add(this.jsonAdapterFactory);
        factories.add(TypeAdapters.ENUM_FACTORY);
        factories.add(new ReflectiveTypeAdapterFactory(this.constructorConstructor, fieldNamingStrategy, excluder, this.jsonAdapterFactory, reflectionFilters));
        this.factories = Collections.unmodifiableList(factories);
    }

    public GsonBuilder newBuilder() {
        return new GsonBuilder(this);
    }

    @Deprecated
    public Excluder excluder() {
        return this.excluder;
    }

    public FieldNamingStrategy fieldNamingStrategy() {
        return this.fieldNamingStrategy;
    }

    public boolean serializeNulls() {
        return this.serializeNulls;
    }

    public boolean htmlSafe() {
        return this.htmlSafe;
    }

    private TypeAdapter<Number> doubleAdapter(boolean serializeSpecialFloatingPointValues) {
        if (serializeSpecialFloatingPointValues) {
            return TypeAdapters.DOUBLE;
        }
        return new TypeAdapter<Number>(){

            @Override
            public Double read(JsonReader in) throws IOException {
                if (in.peek() == JsonToken.NULL) {
                    in.nextNull();
                    return null;
                }
                return in.nextDouble();
            }

            @Override
            public void write(JsonWriter out, Number value) throws IOException {
                if (value == null) {
                    out.nullValue();
                    return;
                }
                double doubleValue = value.doubleValue();
                Gson.checkValidFloatingPoint(doubleValue);
                out.value(doubleValue);
            }
        };
    }

    private TypeAdapter<Number> floatAdapter(boolean serializeSpecialFloatingPointValues) {
        if (serializeSpecialFloatingPointValues) {
            return TypeAdapters.FLOAT;
        }
        return new TypeAdapter<Number>(){

            @Override
            public Float read(JsonReader in) throws IOException {
                if (in.peek() == JsonToken.NULL) {
                    in.nextNull();
                    return null;
                }
                return Float.valueOf((float)in.nextDouble());
            }

            @Override
            public void write(JsonWriter out, Number value) throws IOException {
                if (value == null) {
                    out.nullValue();
                    return;
                }
                float floatValue = value.floatValue();
                Gson.checkValidFloatingPoint(floatValue);
                Number floatNumber = value instanceof Float ? (Number)value : (Number)Float.valueOf(floatValue);
                out.value(floatNumber);
            }
        };
    }

    static void checkValidFloatingPoint(double value) {
        if (Double.isNaN(value) || Double.isInfinite(value)) {
            throw new IllegalArgumentException(value + " is not a valid double value as per JSON specification. To override this behavior, use GsonBuilder.serializeSpecialFloatingPointValues() method.");
        }
    }

    private static TypeAdapter<Number> longAdapter(LongSerializationPolicy longSerializationPolicy) {
        if (longSerializationPolicy == LongSerializationPolicy.DEFAULT) {
            return TypeAdapters.LONG;
        }
        return new TypeAdapter<Number>(){

            @Override
            public Number read(JsonReader in) throws IOException {
                if (in.peek() == JsonToken.NULL) {
                    in.nextNull();
                    return null;
                }
                return in.nextLong();
            }

            @Override
            public void write(JsonWriter out, Number value) throws IOException {
                if (value == null) {
                    out.nullValue();
                    return;
                }
                out.value(value.toString());
            }
        };
    }

    private static TypeAdapter<AtomicLong> atomicLongAdapter(final TypeAdapter<Number> longAdapter) {
        return new TypeAdapter<AtomicLong>(){

            @Override
            public void write(JsonWriter out, AtomicLong value) throws IOException {
                longAdapter.write(out, value.get());
            }

            @Override
            public AtomicLong read(JsonReader in) throws IOException {
                Number value = (Number)longAdapter.read(in);
                return new AtomicLong(value.longValue());
            }
        }.nullSafe();
    }

    private static TypeAdapter<AtomicLongArray> atomicLongArrayAdapter(final TypeAdapter<Number> longAdapter) {
        return new TypeAdapter<AtomicLongArray>(){

            @Override
            public void write(JsonWriter out, AtomicLongArray value) throws IOException {
                out.beginArray();
                int length = value.length();
                for (int i = 0; i < length; ++i) {
                    longAdapter.write(out, value.get(i));
                }
                out.endArray();
            }

            @Override
            public AtomicLongArray read(JsonReader in) throws IOException {
                ArrayList<Long> list = new ArrayList<Long>();
                in.beginArray();
                while (in.hasNext()) {
                    long value = ((Number)longAdapter.read(in)).longValue();
                    list.add(value);
                }
                in.endArray();
                int length = list.size();
                AtomicLongArray array = new AtomicLongArray(length);
                for (int i = 0; i < length; ++i) {
                    array.set(i, (Long)list.get(i));
                }
                return array;
            }
        }.nullSafe();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public <T> TypeAdapter<T> getAdapter(TypeToken<T> type2) {
        Objects.requireNonNull(type2, "type must not be null");
        TypeAdapter cached = (TypeAdapter)this.typeTokenCache.get(type2);
        if (cached != null) {
            TypeAdapter adapter = cached;
            return adapter;
        }
        Map<TypeToken<?>, TypeAdapter<?>> threadCalls = this.threadLocalAdapterResults.get();
        boolean isInitialAdapterRequest = false;
        if (threadCalls == null) {
            threadCalls = new HashMap();
            this.threadLocalAdapterResults.set(threadCalls);
            isInitialAdapterRequest = true;
        } else {
            TypeAdapter<?> ongoingCall = threadCalls.get(type2);
            if (ongoingCall != null) {
                return ongoingCall;
            }
        }
        TypeAdapter<T> candidate = null;
        try {
            FutureTypeAdapter<T> call = new FutureTypeAdapter<T>();
            threadCalls.put(type2, call);
            for (TypeAdapterFactory factory : this.factories) {
                candidate = factory.create(this, type2);
                if (candidate == null) continue;
                call.setDelegate(candidate);
                threadCalls.put(type2, candidate);
                break;
            }
        } finally {
            if (isInitialAdapterRequest) {
                this.threadLocalAdapterResults.remove();
            }
        }
        if (candidate == null) {
            throw new IllegalArgumentException("GSON (2.10.1) cannot handle " + type2);
        }
        if (isInitialAdapterRequest) {
            this.typeTokenCache.putAll(threadCalls);
        }
        return candidate;
    }

    public <T> TypeAdapter<T> getDelegateAdapter(TypeAdapterFactory skipPast, TypeToken<T> type2) {
        if (!this.factories.contains(skipPast)) {
            skipPast = this.jsonAdapterFactory;
        }
        boolean skipPastFound = false;
        for (TypeAdapterFactory factory : this.factories) {
            if (!skipPastFound) {
                if (factory != skipPast) continue;
                skipPastFound = true;
                continue;
            }
            TypeAdapter<T> candidate = factory.create(this, type2);
            if (candidate == null) continue;
            return candidate;
        }
        throw new IllegalArgumentException("GSON cannot serialize " + type2);
    }

    public <T> TypeAdapter<T> getAdapter(Class<T> type2) {
        return this.getAdapter(TypeToken.get(type2));
    }

    public JsonElement toJsonTree(Object src) {
        if (src == null) {
            return JsonNull.INSTANCE;
        }
        return this.toJsonTree(src, src.getClass());
    }

    public JsonElement toJsonTree(Object src, Type typeOfSrc) {
        JsonTreeWriter writer = new JsonTreeWriter();
        this.toJson(src, typeOfSrc, writer);
        return writer.get();
    }

    public String toJson(Object src) {
        if (src == null) {
            return this.toJson(JsonNull.INSTANCE);
        }
        return this.toJson(src, src.getClass());
    }

    public String toJson(Object src, Type typeOfSrc) {
        StringWriter writer = new StringWriter();
        this.toJson(src, typeOfSrc, writer);
        return writer.toString();
    }

    public void toJson(Object src, Appendable writer) throws JsonIOException {
        if (src != null) {
            this.toJson(src, src.getClass(), writer);
        } else {
            this.toJson((JsonElement)JsonNull.INSTANCE, writer);
        }
    }

    public void toJson(Object src, Type typeOfSrc, Appendable writer) throws JsonIOException {
        try {
            JsonWriter jsonWriter = this.newJsonWriter(Streams.writerForAppendable(writer));
            this.toJson(src, typeOfSrc, jsonWriter);
        } catch (IOException e) {
            throw new JsonIOException(e);
        }
    }

    public void toJson(Object src, Type typeOfSrc, JsonWriter writer) throws JsonIOException {
        TypeAdapter<?> adapter = this.getAdapter(TypeToken.get(typeOfSrc));
        boolean oldLenient = writer.isLenient();
        writer.setLenient(true);
        boolean oldHtmlSafe = writer.isHtmlSafe();
        writer.setHtmlSafe(this.htmlSafe);
        boolean oldSerializeNulls = writer.getSerializeNulls();
        writer.setSerializeNulls(this.serializeNulls);
        try {
            adapter.write(writer, src);
        } catch (IOException e) {
            throw new JsonIOException(e);
        } catch (AssertionError e) {
            throw new AssertionError("AssertionError (GSON 2.10.1): " + ((Throwable)((Object)e)).getMessage(), (Throwable)((Object)e));
        } finally {
            writer.setLenient(oldLenient);
            writer.setHtmlSafe(oldHtmlSafe);
            writer.setSerializeNulls(oldSerializeNulls);
        }
    }

    public String toJson(JsonElement jsonElement) {
        StringWriter writer = new StringWriter();
        this.toJson(jsonElement, (Appendable)writer);
        return writer.toString();
    }

    public void toJson(JsonElement jsonElement, Appendable writer) throws JsonIOException {
        try {
            JsonWriter jsonWriter = this.newJsonWriter(Streams.writerForAppendable(writer));
            this.toJson(jsonElement, jsonWriter);
        } catch (IOException e) {
            throw new JsonIOException(e);
        }
    }

    public JsonWriter newJsonWriter(Writer writer) throws IOException {
        if (this.generateNonExecutableJson) {
            writer.write(JSON_NON_EXECUTABLE_PREFIX);
        }
        JsonWriter jsonWriter = new JsonWriter(writer);
        if (this.prettyPrinting) {
            jsonWriter.setIndent("  ");
        }
        jsonWriter.setHtmlSafe(this.htmlSafe);
        jsonWriter.setLenient(this.lenient);
        jsonWriter.setSerializeNulls(this.serializeNulls);
        return jsonWriter;
    }

    public JsonReader newJsonReader(Reader reader) {
        JsonReader jsonReader = new JsonReader(reader);
        jsonReader.setLenient(this.lenient);
        return jsonReader;
    }

    public void toJson(JsonElement jsonElement, JsonWriter writer) throws JsonIOException {
        boolean oldLenient = writer.isLenient();
        writer.setLenient(true);
        boolean oldHtmlSafe = writer.isHtmlSafe();
        writer.setHtmlSafe(this.htmlSafe);
        boolean oldSerializeNulls = writer.getSerializeNulls();
        writer.setSerializeNulls(this.serializeNulls);
        try {
            Streams.write(jsonElement, writer);
        } catch (IOException e) {
            throw new JsonIOException(e);
        } catch (AssertionError e) {
            throw new AssertionError("AssertionError (GSON 2.10.1): " + ((Throwable)((Object)e)).getMessage(), (Throwable)((Object)e));
        } finally {
            writer.setLenient(oldLenient);
            writer.setHtmlSafe(oldHtmlSafe);
            writer.setSerializeNulls(oldSerializeNulls);
        }
    }

    public <T> T fromJson(String json, Class<T> classOfT) throws JsonSyntaxException {
        T object = this.fromJson(json, TypeToken.get(classOfT));
        return Primitives.wrap(classOfT).cast(object);
    }

    public <T> T fromJson(String json, Type typeOfT) throws JsonSyntaxException {
        return (T)this.fromJson(json, TypeToken.get(typeOfT));
    }

    public <T> T fromJson(String json, TypeToken<T> typeOfT) throws JsonSyntaxException {
        if (json == null) {
            return null;
        }
        StringReader reader = new StringReader(json);
        return this.fromJson((Reader)reader, typeOfT);
    }

    public <T> T fromJson(Reader json, Class<T> classOfT) throws JsonSyntaxException, JsonIOException {
        T object = this.fromJson(json, TypeToken.get(classOfT));
        return Primitives.wrap(classOfT).cast(object);
    }

    public <T> T fromJson(Reader json, Type typeOfT) throws JsonIOException, JsonSyntaxException {
        return (T)this.fromJson(json, TypeToken.get(typeOfT));
    }

    public <T> T fromJson(Reader json, TypeToken<T> typeOfT) throws JsonIOException, JsonSyntaxException {
        JsonReader jsonReader = this.newJsonReader(json);
        T object = this.fromJson(jsonReader, typeOfT);
        Gson.assertFullConsumption(object, jsonReader);
        return object;
    }

    private static void assertFullConsumption(Object obj, JsonReader reader) {
        try {
            if (obj != null && reader.peek() != JsonToken.END_DOCUMENT) {
                throw new JsonSyntaxException("JSON document was not fully consumed.");
            }
        } catch (MalformedJsonException e) {
            throw new JsonSyntaxException(e);
        } catch (IOException e) {
            throw new JsonIOException(e);
        }
    }

    public <T> T fromJson(JsonReader reader, Type typeOfT) throws JsonIOException, JsonSyntaxException {
        return (T)this.fromJson(reader, TypeToken.get(typeOfT));
    }

    public <T> T fromJson(JsonReader reader, TypeToken<T> typeOfT) throws JsonIOException, JsonSyntaxException {
        boolean isEmpty = true;
        boolean oldLenient = reader.isLenient();
        reader.setLenient(true);
        try {
            reader.peek();
            isEmpty = false;
            TypeAdapter<T> typeAdapter = this.getAdapter(typeOfT);
            T t = typeAdapter.read(reader);
            return t;
        } catch (EOFException e) {
            if (isEmpty) {
                T t = null;
                return t;
            }
            throw new JsonSyntaxException(e);
        } catch (IllegalStateException e) {
            throw new JsonSyntaxException(e);
        } catch (IOException e) {
            throw new JsonSyntaxException(e);
        } catch (AssertionError e) {
            throw new AssertionError("AssertionError (GSON 2.10.1): " + ((Throwable)((Object)e)).getMessage(), (Throwable)((Object)e));
        } finally {
            reader.setLenient(oldLenient);
        }
    }

    public <T> T fromJson(JsonElement json, Class<T> classOfT) throws JsonSyntaxException {
        T object = this.fromJson(json, TypeToken.get(classOfT));
        return Primitives.wrap(classOfT).cast(object);
    }

    public <T> T fromJson(JsonElement json, Type typeOfT) throws JsonSyntaxException {
        return (T)this.fromJson(json, TypeToken.get(typeOfT));
    }

    public <T> T fromJson(JsonElement json, TypeToken<T> typeOfT) throws JsonSyntaxException {
        if (json == null) {
            return null;
        }
        return this.fromJson((JsonReader)new JsonTreeReader(json), typeOfT);
    }

    public String toString() {
        return "{serializeNulls:" + this.serializeNulls + ",factories:" + this.factories + ",instanceCreators:" + this.constructorConstructor + "}";
    }

    static class FutureTypeAdapter<T>
    extends SerializationDelegatingTypeAdapter<T> {
        private TypeAdapter<T> delegate = null;

        FutureTypeAdapter() {
        }

        public void setDelegate(TypeAdapter<T> typeAdapter) {
            if (this.delegate != null) {
                throw new AssertionError((Object)"Delegate is already set");
            }
            this.delegate = typeAdapter;
        }

        private TypeAdapter<T> delegate() {
            TypeAdapter<T> delegate = this.delegate;
            if (delegate == null) {
                throw new IllegalStateException("Adapter for type with cyclic dependency has been used before dependency has been resolved");
            }
            return delegate;
        }

        @Override
        public TypeAdapter<T> getSerializationDelegate() {
            return this.delegate();
        }

        @Override
        public T read(JsonReader in) throws IOException {
            return this.delegate().read(in);
        }

        @Override
        public void write(JsonWriter out, T value) throws IOException {
            this.delegate().write(out, value);
        }
    }
}

