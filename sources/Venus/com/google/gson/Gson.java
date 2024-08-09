/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.gson;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.FieldNamingStrategy;
import com.google.gson.GsonBuilder;
import com.google.gson.InstanceCreator;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonNull;
import com.google.gson.JsonSyntaxException;
import com.google.gson.LongSerializationPolicy;
import com.google.gson.ReflectionAccessFilter;
import com.google.gson.ToNumberPolicy;
import com.google.gson.ToNumberStrategy;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.internal.ConstructorConstructor;
import com.google.gson.internal.Excluder;
import com.google.gson.internal.LazilyParsedNumber;
import com.google.gson.internal.Primitives;
import com.google.gson.internal.Streams;
import com.google.gson.internal.bind.ArrayTypeAdapter;
import com.google.gson.internal.bind.CollectionTypeAdapterFactory;
import com.google.gson.internal.bind.DateTypeAdapter;
import com.google.gson.internal.bind.JsonAdapterAnnotationTypeAdapterFactory;
import com.google.gson.internal.bind.JsonTreeReader;
import com.google.gson.internal.bind.JsonTreeWriter;
import com.google.gson.internal.bind.MapTypeAdapterFactory;
import com.google.gson.internal.bind.NumberTypeAdapter;
import com.google.gson.internal.bind.ObjectTypeAdapter;
import com.google.gson.internal.bind.ReflectiveTypeAdapterFactory;
import com.google.gson.internal.bind.SerializationDelegatingTypeAdapter;
import com.google.gson.internal.bind.TypeAdapters;
import com.google.gson.internal.sql.SqlTypesSupport;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.google.gson.stream.MalformedJsonException;
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

    Gson(Excluder excluder, FieldNamingStrategy fieldNamingStrategy, Map<Type, InstanceCreator<?>> map, boolean bl, boolean bl2, boolean bl3, boolean bl4, boolean bl5, boolean bl6, boolean bl7, boolean bl8, LongSerializationPolicy longSerializationPolicy, String string, int n, int n2, List<TypeAdapterFactory> list, List<TypeAdapterFactory> list2, List<TypeAdapterFactory> list3, ToNumberStrategy toNumberStrategy, ToNumberStrategy toNumberStrategy2, List<ReflectionAccessFilter> list4) {
        this.excluder = excluder;
        this.fieldNamingStrategy = fieldNamingStrategy;
        this.instanceCreators = map;
        this.constructorConstructor = new ConstructorConstructor(map, bl8, list4);
        this.serializeNulls = bl;
        this.complexMapKeySerialization = bl2;
        this.generateNonExecutableJson = bl3;
        this.htmlSafe = bl4;
        this.prettyPrinting = bl5;
        this.lenient = bl6;
        this.serializeSpecialFloatingPointValues = bl7;
        this.useJdkUnsafe = bl8;
        this.longSerializationPolicy = longSerializationPolicy;
        this.datePattern = string;
        this.dateStyle = n;
        this.timeStyle = n2;
        this.builderFactories = list;
        this.builderHierarchyFactories = list2;
        this.objectToNumberStrategy = toNumberStrategy;
        this.numberToNumberStrategy = toNumberStrategy2;
        this.reflectionFilters = list4;
        ArrayList<TypeAdapterFactory> arrayList = new ArrayList<TypeAdapterFactory>();
        arrayList.add(TypeAdapters.JSON_ELEMENT_FACTORY);
        arrayList.add(ObjectTypeAdapter.getFactory(toNumberStrategy));
        arrayList.add(excluder);
        arrayList.addAll(list3);
        arrayList.add(TypeAdapters.STRING_FACTORY);
        arrayList.add(TypeAdapters.INTEGER_FACTORY);
        arrayList.add(TypeAdapters.BOOLEAN_FACTORY);
        arrayList.add(TypeAdapters.BYTE_FACTORY);
        arrayList.add(TypeAdapters.SHORT_FACTORY);
        TypeAdapter<Number> typeAdapter = Gson.longAdapter(longSerializationPolicy);
        arrayList.add(TypeAdapters.newFactory(Long.TYPE, Long.class, typeAdapter));
        arrayList.add(TypeAdapters.newFactory(Double.TYPE, Double.class, this.doubleAdapter(bl7)));
        arrayList.add(TypeAdapters.newFactory(Float.TYPE, Float.class, this.floatAdapter(bl7)));
        arrayList.add(NumberTypeAdapter.getFactory(toNumberStrategy2));
        arrayList.add(TypeAdapters.ATOMIC_INTEGER_FACTORY);
        arrayList.add(TypeAdapters.ATOMIC_BOOLEAN_FACTORY);
        arrayList.add(TypeAdapters.newFactory(AtomicLong.class, Gson.atomicLongAdapter(typeAdapter)));
        arrayList.add(TypeAdapters.newFactory(AtomicLongArray.class, Gson.atomicLongArrayAdapter(typeAdapter)));
        arrayList.add(TypeAdapters.ATOMIC_INTEGER_ARRAY_FACTORY);
        arrayList.add(TypeAdapters.CHARACTER_FACTORY);
        arrayList.add(TypeAdapters.STRING_BUILDER_FACTORY);
        arrayList.add(TypeAdapters.STRING_BUFFER_FACTORY);
        arrayList.add(TypeAdapters.newFactory(BigDecimal.class, TypeAdapters.BIG_DECIMAL));
        arrayList.add(TypeAdapters.newFactory(BigInteger.class, TypeAdapters.BIG_INTEGER));
        arrayList.add(TypeAdapters.newFactory(LazilyParsedNumber.class, TypeAdapters.LAZILY_PARSED_NUMBER));
        arrayList.add(TypeAdapters.URL_FACTORY);
        arrayList.add(TypeAdapters.URI_FACTORY);
        arrayList.add(TypeAdapters.UUID_FACTORY);
        arrayList.add(TypeAdapters.CURRENCY_FACTORY);
        arrayList.add(TypeAdapters.LOCALE_FACTORY);
        arrayList.add(TypeAdapters.INET_ADDRESS_FACTORY);
        arrayList.add(TypeAdapters.BIT_SET_FACTORY);
        arrayList.add(DateTypeAdapter.FACTORY);
        arrayList.add(TypeAdapters.CALENDAR_FACTORY);
        if (SqlTypesSupport.SUPPORTS_SQL_TYPES) {
            arrayList.add(SqlTypesSupport.TIME_FACTORY);
            arrayList.add(SqlTypesSupport.DATE_FACTORY);
            arrayList.add(SqlTypesSupport.TIMESTAMP_FACTORY);
        }
        arrayList.add(ArrayTypeAdapter.FACTORY);
        arrayList.add(TypeAdapters.CLASS_FACTORY);
        arrayList.add(new CollectionTypeAdapterFactory(this.constructorConstructor));
        arrayList.add(new MapTypeAdapterFactory(this.constructorConstructor, bl2));
        this.jsonAdapterFactory = new JsonAdapterAnnotationTypeAdapterFactory(this.constructorConstructor);
        arrayList.add(this.jsonAdapterFactory);
        arrayList.add(TypeAdapters.ENUM_FACTORY);
        arrayList.add(new ReflectiveTypeAdapterFactory(this.constructorConstructor, fieldNamingStrategy, excluder, this.jsonAdapterFactory, list4));
        this.factories = Collections.unmodifiableList(arrayList);
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

    private TypeAdapter<Number> doubleAdapter(boolean bl) {
        if (bl) {
            return TypeAdapters.DOUBLE;
        }
        return new TypeAdapter<Number>(this){
            final Gson this$0;
            {
                this.this$0 = gson;
            }

            @Override
            public Double read(JsonReader jsonReader) throws IOException {
                if (jsonReader.peek() == JsonToken.NULL) {
                    jsonReader.nextNull();
                    return null;
                }
                return jsonReader.nextDouble();
            }

            @Override
            public void write(JsonWriter jsonWriter, Number number) throws IOException {
                if (number == null) {
                    jsonWriter.nullValue();
                    return;
                }
                double d = number.doubleValue();
                Gson.checkValidFloatingPoint(d);
                jsonWriter.value(d);
            }

            @Override
            public Object read(JsonReader jsonReader) throws IOException {
                return this.read(jsonReader);
            }

            @Override
            public void write(JsonWriter jsonWriter, Object object) throws IOException {
                this.write(jsonWriter, (Number)object);
            }
        };
    }

    private TypeAdapter<Number> floatAdapter(boolean bl) {
        if (bl) {
            return TypeAdapters.FLOAT;
        }
        return new TypeAdapter<Number>(this){
            final Gson this$0;
            {
                this.this$0 = gson;
            }

            @Override
            public Float read(JsonReader jsonReader) throws IOException {
                if (jsonReader.peek() == JsonToken.NULL) {
                    jsonReader.nextNull();
                    return null;
                }
                return Float.valueOf((float)jsonReader.nextDouble());
            }

            @Override
            public void write(JsonWriter jsonWriter, Number number) throws IOException {
                if (number == null) {
                    jsonWriter.nullValue();
                    return;
                }
                float f = number.floatValue();
                Gson.checkValidFloatingPoint(f);
                Number number2 = number instanceof Float ? (Number)number : (Number)Float.valueOf(f);
                jsonWriter.value(number2);
            }

            @Override
            public Object read(JsonReader jsonReader) throws IOException {
                return this.read(jsonReader);
            }

            @Override
            public void write(JsonWriter jsonWriter, Object object) throws IOException {
                this.write(jsonWriter, (Number)object);
            }
        };
    }

    static void checkValidFloatingPoint(double d) {
        if (Double.isNaN(d) || Double.isInfinite(d)) {
            throw new IllegalArgumentException(d + " is not a valid double value as per JSON specification. To override this behavior, use GsonBuilder.serializeSpecialFloatingPointValues() method.");
        }
    }

    private static TypeAdapter<Number> longAdapter(LongSerializationPolicy longSerializationPolicy) {
        if (longSerializationPolicy == LongSerializationPolicy.DEFAULT) {
            return TypeAdapters.LONG;
        }
        return new TypeAdapter<Number>(){

            @Override
            public Number read(JsonReader jsonReader) throws IOException {
                if (jsonReader.peek() == JsonToken.NULL) {
                    jsonReader.nextNull();
                    return null;
                }
                return jsonReader.nextLong();
            }

            @Override
            public void write(JsonWriter jsonWriter, Number number) throws IOException {
                if (number == null) {
                    jsonWriter.nullValue();
                    return;
                }
                jsonWriter.value(number.toString());
            }

            @Override
            public Object read(JsonReader jsonReader) throws IOException {
                return this.read(jsonReader);
            }

            @Override
            public void write(JsonWriter jsonWriter, Object object) throws IOException {
                this.write(jsonWriter, (Number)object);
            }
        };
    }

    private static TypeAdapter<AtomicLong> atomicLongAdapter(TypeAdapter<Number> typeAdapter) {
        return new TypeAdapter<AtomicLong>(typeAdapter){
            final TypeAdapter val$longAdapter;
            {
                this.val$longAdapter = typeAdapter;
            }

            @Override
            public void write(JsonWriter jsonWriter, AtomicLong atomicLong) throws IOException {
                this.val$longAdapter.write(jsonWriter, atomicLong.get());
            }

            @Override
            public AtomicLong read(JsonReader jsonReader) throws IOException {
                Number number = (Number)this.val$longAdapter.read(jsonReader);
                return new AtomicLong(number.longValue());
            }

            @Override
            public Object read(JsonReader jsonReader) throws IOException {
                return this.read(jsonReader);
            }

            @Override
            public void write(JsonWriter jsonWriter, Object object) throws IOException {
                this.write(jsonWriter, (AtomicLong)object);
            }
        }.nullSafe();
    }

    private static TypeAdapter<AtomicLongArray> atomicLongArrayAdapter(TypeAdapter<Number> typeAdapter) {
        return new TypeAdapter<AtomicLongArray>(typeAdapter){
            final TypeAdapter val$longAdapter;
            {
                this.val$longAdapter = typeAdapter;
            }

            @Override
            public void write(JsonWriter jsonWriter, AtomicLongArray atomicLongArray) throws IOException {
                jsonWriter.beginArray();
                int n = atomicLongArray.length();
                for (int i = 0; i < n; ++i) {
                    this.val$longAdapter.write(jsonWriter, atomicLongArray.get(i));
                }
                jsonWriter.endArray();
            }

            @Override
            public AtomicLongArray read(JsonReader jsonReader) throws IOException {
                ArrayList<Long> arrayList = new ArrayList<Long>();
                jsonReader.beginArray();
                while (jsonReader.hasNext()) {
                    long l = ((Number)this.val$longAdapter.read(jsonReader)).longValue();
                    arrayList.add(l);
                }
                jsonReader.endArray();
                int n = arrayList.size();
                AtomicLongArray atomicLongArray = new AtomicLongArray(n);
                for (int i = 0; i < n; ++i) {
                    atomicLongArray.set(i, (Long)arrayList.get(i));
                }
                return atomicLongArray;
            }

            @Override
            public Object read(JsonReader jsonReader) throws IOException {
                return this.read(jsonReader);
            }

            @Override
            public void write(JsonWriter jsonWriter, Object object) throws IOException {
                this.write(jsonWriter, (AtomicLongArray)object);
            }
        }.nullSafe();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public <T> TypeAdapter<T> getAdapter(TypeToken<T> typeToken) {
        TypeAdapter<Object> typeAdapter;
        Objects.requireNonNull(typeToken, "type must not be null");
        TypeAdapter typeAdapter2 = (TypeAdapter)this.typeTokenCache.get(typeToken);
        if (typeAdapter2 != null) {
            TypeAdapter typeAdapter3 = typeAdapter2;
            return typeAdapter3;
        }
        Map<TypeToken<?>, TypeAdapter<?>> map = this.threadLocalAdapterResults.get();
        boolean bl = false;
        if (map == null) {
            map = new HashMap();
            this.threadLocalAdapterResults.set(map);
            bl = true;
        } else {
            typeAdapter = map.get(typeToken);
            if (typeAdapter != null) {
                return typeAdapter;
            }
        }
        typeAdapter = null;
        try {
            FutureTypeAdapter futureTypeAdapter = new FutureTypeAdapter();
            map.put(typeToken, futureTypeAdapter);
            for (TypeAdapterFactory typeAdapterFactory : this.factories) {
                typeAdapter = typeAdapterFactory.create(this, typeToken);
                if (typeAdapter == null) continue;
                futureTypeAdapter.setDelegate(typeAdapter);
                map.put(typeToken, typeAdapter);
                break;
            }
        } finally {
            if (bl) {
                this.threadLocalAdapterResults.remove();
            }
        }
        if (typeAdapter == null) {
            throw new IllegalArgumentException("GSON (2.10.1) cannot handle " + typeToken);
        }
        if (bl) {
            this.typeTokenCache.putAll(map);
        }
        return typeAdapter;
    }

    public <T> TypeAdapter<T> getDelegateAdapter(TypeAdapterFactory typeAdapterFactory, TypeToken<T> typeToken) {
        if (!this.factories.contains(typeAdapterFactory)) {
            typeAdapterFactory = this.jsonAdapterFactory;
        }
        boolean bl = false;
        for (TypeAdapterFactory typeAdapterFactory2 : this.factories) {
            if (!bl) {
                if (typeAdapterFactory2 != typeAdapterFactory) continue;
                bl = true;
                continue;
            }
            TypeAdapter<T> typeAdapter = typeAdapterFactory2.create(this, typeToken);
            if (typeAdapter == null) continue;
            return typeAdapter;
        }
        throw new IllegalArgumentException("GSON cannot serialize " + typeToken);
    }

    public <T> TypeAdapter<T> getAdapter(Class<T> clazz) {
        return this.getAdapter(TypeToken.get(clazz));
    }

    public JsonElement toJsonTree(Object object) {
        if (object == null) {
            return JsonNull.INSTANCE;
        }
        return this.toJsonTree(object, object.getClass());
    }

    public JsonElement toJsonTree(Object object, Type type) {
        JsonTreeWriter jsonTreeWriter = new JsonTreeWriter();
        this.toJson(object, type, jsonTreeWriter);
        return jsonTreeWriter.get();
    }

    public String toJson(Object object) {
        if (object == null) {
            return this.toJson(JsonNull.INSTANCE);
        }
        return this.toJson(object, object.getClass());
    }

    public String toJson(Object object, Type type) {
        StringWriter stringWriter = new StringWriter();
        this.toJson(object, type, stringWriter);
        return stringWriter.toString();
    }

    public void toJson(Object object, Appendable appendable) throws JsonIOException {
        if (object != null) {
            this.toJson(object, object.getClass(), appendable);
        } else {
            this.toJson((JsonElement)JsonNull.INSTANCE, appendable);
        }
    }

    public void toJson(Object object, Type type, Appendable appendable) throws JsonIOException {
        try {
            JsonWriter jsonWriter = this.newJsonWriter(Streams.writerForAppendable(appendable));
            this.toJson(object, type, jsonWriter);
        } catch (IOException iOException) {
            throw new JsonIOException(iOException);
        }
    }

    public void toJson(Object object, Type type, JsonWriter jsonWriter) throws JsonIOException {
        TypeAdapter<?> typeAdapter = this.getAdapter(TypeToken.get(type));
        boolean bl = jsonWriter.isLenient();
        jsonWriter.setLenient(false);
        boolean bl2 = jsonWriter.isHtmlSafe();
        jsonWriter.setHtmlSafe(this.htmlSafe);
        boolean bl3 = jsonWriter.getSerializeNulls();
        jsonWriter.setSerializeNulls(this.serializeNulls);
        try {
            typeAdapter.write(jsonWriter, object);
        } catch (IOException iOException) {
            throw new JsonIOException(iOException);
        } catch (AssertionError assertionError) {
            throw new AssertionError("AssertionError (GSON 2.10.1): " + ((Throwable)((Object)assertionError)).getMessage(), (Throwable)((Object)assertionError));
        } finally {
            jsonWriter.setLenient(bl);
            jsonWriter.setHtmlSafe(bl2);
            jsonWriter.setSerializeNulls(bl3);
        }
    }

    public String toJson(JsonElement jsonElement) {
        StringWriter stringWriter = new StringWriter();
        this.toJson(jsonElement, (Appendable)stringWriter);
        return stringWriter.toString();
    }

    public void toJson(JsonElement jsonElement, Appendable appendable) throws JsonIOException {
        try {
            JsonWriter jsonWriter = this.newJsonWriter(Streams.writerForAppendable(appendable));
            this.toJson(jsonElement, jsonWriter);
        } catch (IOException iOException) {
            throw new JsonIOException(iOException);
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

    public void toJson(JsonElement jsonElement, JsonWriter jsonWriter) throws JsonIOException {
        boolean bl = jsonWriter.isLenient();
        jsonWriter.setLenient(false);
        boolean bl2 = jsonWriter.isHtmlSafe();
        jsonWriter.setHtmlSafe(this.htmlSafe);
        boolean bl3 = jsonWriter.getSerializeNulls();
        jsonWriter.setSerializeNulls(this.serializeNulls);
        try {
            Streams.write(jsonElement, jsonWriter);
        } catch (IOException iOException) {
            throw new JsonIOException(iOException);
        } catch (AssertionError assertionError) {
            throw new AssertionError("AssertionError (GSON 2.10.1): " + ((Throwable)((Object)assertionError)).getMessage(), (Throwable)((Object)assertionError));
        } finally {
            jsonWriter.setLenient(bl);
            jsonWriter.setHtmlSafe(bl2);
            jsonWriter.setSerializeNulls(bl3);
        }
    }

    public <T> T fromJson(String string, Class<T> clazz) throws JsonSyntaxException {
        T t = this.fromJson(string, TypeToken.get(clazz));
        return Primitives.wrap(clazz).cast(t);
    }

    public <T> T fromJson(String string, Type type) throws JsonSyntaxException {
        return (T)this.fromJson(string, TypeToken.get(type));
    }

    public <T> T fromJson(String string, TypeToken<T> typeToken) throws JsonSyntaxException {
        if (string == null) {
            return null;
        }
        StringReader stringReader = new StringReader(string);
        return this.fromJson((Reader)stringReader, typeToken);
    }

    public <T> T fromJson(Reader reader, Class<T> clazz) throws JsonSyntaxException, JsonIOException {
        T t = this.fromJson(reader, TypeToken.get(clazz));
        return Primitives.wrap(clazz).cast(t);
    }

    public <T> T fromJson(Reader reader, Type type) throws JsonIOException, JsonSyntaxException {
        return (T)this.fromJson(reader, TypeToken.get(type));
    }

    public <T> T fromJson(Reader reader, TypeToken<T> typeToken) throws JsonIOException, JsonSyntaxException {
        JsonReader jsonReader = this.newJsonReader(reader);
        T t = this.fromJson(jsonReader, typeToken);
        Gson.assertFullConsumption(t, jsonReader);
        return t;
    }

    private static void assertFullConsumption(Object object, JsonReader jsonReader) {
        try {
            if (object != null && jsonReader.peek() != JsonToken.END_DOCUMENT) {
                throw new JsonSyntaxException("JSON document was not fully consumed.");
            }
        } catch (MalformedJsonException malformedJsonException) {
            throw new JsonSyntaxException(malformedJsonException);
        } catch (IOException iOException) {
            throw new JsonIOException(iOException);
        }
    }

    public <T> T fromJson(JsonReader jsonReader, Type type) throws JsonIOException, JsonSyntaxException {
        return (T)this.fromJson(jsonReader, TypeToken.get(type));
    }

    public <T> T fromJson(JsonReader jsonReader, TypeToken<T> typeToken) throws JsonIOException, JsonSyntaxException {
        boolean bl = true;
        boolean bl2 = jsonReader.isLenient();
        jsonReader.setLenient(false);
        try {
            jsonReader.peek();
            bl = false;
            TypeAdapter<T> typeAdapter = this.getAdapter(typeToken);
            T t = typeAdapter.read(jsonReader);
            return t;
        } catch (EOFException eOFException) {
            if (bl) {
                T t = null;
                return t;
            }
            throw new JsonSyntaxException(eOFException);
        } catch (IllegalStateException illegalStateException) {
            throw new JsonSyntaxException(illegalStateException);
        } catch (IOException iOException) {
            throw new JsonSyntaxException(iOException);
        } catch (AssertionError assertionError) {
            throw new AssertionError("AssertionError (GSON 2.10.1): " + ((Throwable)((Object)assertionError)).getMessage(), (Throwable)((Object)assertionError));
        } finally {
            jsonReader.setLenient(bl2);
        }
    }

    public <T> T fromJson(JsonElement jsonElement, Class<T> clazz) throws JsonSyntaxException {
        T t = this.fromJson(jsonElement, TypeToken.get(clazz));
        return Primitives.wrap(clazz).cast(t);
    }

    public <T> T fromJson(JsonElement jsonElement, Type type) throws JsonSyntaxException {
        return (T)this.fromJson(jsonElement, TypeToken.get(type));
    }

    public <T> T fromJson(JsonElement jsonElement, TypeToken<T> typeToken) throws JsonSyntaxException {
        if (jsonElement == null) {
            return null;
        }
        return this.fromJson((JsonReader)new JsonTreeReader(jsonElement), typeToken);
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
            TypeAdapter<T> typeAdapter = this.delegate;
            if (typeAdapter == null) {
                throw new IllegalStateException("Adapter for type with cyclic dependency has been used before dependency has been resolved");
            }
            return typeAdapter;
        }

        @Override
        public TypeAdapter<T> getSerializationDelegate() {
            return this.delegate();
        }

        @Override
        public T read(JsonReader jsonReader) throws IOException {
            return this.delegate().read(jsonReader);
        }

        @Override
        public void write(JsonWriter jsonWriter, T t) throws IOException {
            this.delegate().write(jsonWriter, t);
        }
    }
}

