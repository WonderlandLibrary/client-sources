package com.viaversion.viaversion.libs.gson;

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
   private final ThreadLocal<Map<TypeToken<?>, TypeAdapter<?>>> threadLocalAdapterResults = new ThreadLocal<>();
   private final ConcurrentMap<TypeToken<?>, TypeAdapter<?>> typeTokenCache = new ConcurrentHashMap<>();
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
      this(
         Excluder.DEFAULT,
         DEFAULT_FIELD_NAMING_STRATEGY,
         Collections.emptyMap(),
         false,
         false,
         false,
         true,
         false,
         false,
         false,
         true,
         LongSerializationPolicy.DEFAULT,
         DEFAULT_DATE_PATTERN,
         2,
         2,
         Collections.emptyList(),
         Collections.emptyList(),
         Collections.emptyList(),
         DEFAULT_OBJECT_TO_NUMBER_STRATEGY,
         DEFAULT_NUMBER_TO_NUMBER_STRATEGY,
         Collections.emptyList()
      );
   }

   Gson(
      Excluder excluder,
      FieldNamingStrategy fieldNamingStrategy,
      Map<Type, InstanceCreator<?>> instanceCreators,
      boolean serializeNulls,
      boolean complexMapKeySerialization,
      boolean generateNonExecutableGson,
      boolean htmlSafe,
      boolean prettyPrinting,
      boolean lenient,
      boolean serializeSpecialFloatingPointValues,
      boolean useJdkUnsafe,
      LongSerializationPolicy longSerializationPolicy,
      String datePattern,
      int dateStyle,
      int timeStyle,
      List<TypeAdapterFactory> builderFactories,
      List<TypeAdapterFactory> builderHierarchyFactories,
      List<TypeAdapterFactory> factoriesToBeAdded,
      ToNumberStrategy objectToNumberStrategy,
      ToNumberStrategy numberToNumberStrategy,
      List<ReflectionAccessFilter> reflectionFilters
   ) {
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
      List<TypeAdapterFactory> factories = new ArrayList<>();
      factories.add(TypeAdapters.JSON_ELEMENT_FACTORY);
      factories.add(ObjectTypeAdapter.getFactory(objectToNumberStrategy));
      factories.add(excluder);
      factories.addAll(factoriesToBeAdded);
      factories.add(TypeAdapters.STRING_FACTORY);
      factories.add(TypeAdapters.INTEGER_FACTORY);
      factories.add(TypeAdapters.BOOLEAN_FACTORY);
      factories.add(TypeAdapters.BYTE_FACTORY);
      factories.add(TypeAdapters.SHORT_FACTORY);
      TypeAdapter<Number> longAdapter = longAdapter(longSerializationPolicy);
      factories.add(TypeAdapters.newFactory(long.class, Long.class, longAdapter));
      factories.add(TypeAdapters.newFactory(double.class, Double.class, this.doubleAdapter(serializeSpecialFloatingPointValues)));
      factories.add(TypeAdapters.newFactory(float.class, Float.class, this.floatAdapter(serializeSpecialFloatingPointValues)));
      factories.add(NumberTypeAdapter.getFactory(numberToNumberStrategy));
      factories.add(TypeAdapters.ATOMIC_INTEGER_FACTORY);
      factories.add(TypeAdapters.ATOMIC_BOOLEAN_FACTORY);
      factories.add(TypeAdapters.newFactory(AtomicLong.class, atomicLongAdapter(longAdapter)));
      factories.add(TypeAdapters.newFactory(AtomicLongArray.class, atomicLongArrayAdapter(longAdapter)));
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
      return serializeSpecialFloatingPointValues ? TypeAdapters.DOUBLE : new TypeAdapter<Number>() {
         public Double read(JsonReader in) throws IOException {
            if (in.peek() == JsonToken.NULL) {
               in.nextNull();
               return null;
            } else {
               return in.nextDouble();
            }
         }

         public void write(JsonWriter out, Number value) throws IOException {
            if (value == null) {
               out.nullValue();
            } else {
               double doubleValue = value.doubleValue();
               Gson.checkValidFloatingPoint(doubleValue);
               out.value(doubleValue);
            }
         }
      };
   }

   private TypeAdapter<Number> floatAdapter(boolean serializeSpecialFloatingPointValues) {
      return serializeSpecialFloatingPointValues ? TypeAdapters.FLOAT : new TypeAdapter<Number>() {
         public Float read(JsonReader in) throws IOException {
            if (in.peek() == JsonToken.NULL) {
               in.nextNull();
               return null;
            } else {
               return (float)in.nextDouble();
            }
         }

         public void write(JsonWriter out, Number value) throws IOException {
            if (value == null) {
               out.nullValue();
            } else {
               float floatValue = value.floatValue();
               Gson.checkValidFloatingPoint((double)floatValue);
               Number floatNumber = (Number)(value instanceof Float ? value : floatValue);
               out.value(floatNumber);
            }
         }
      };
   }

   static void checkValidFloatingPoint(double value) {
      if (Double.isNaN(value) || Double.isInfinite(value)) {
         throw new IllegalArgumentException(
            value
               + " is not a valid double value as per JSON specification. To override this behavior, use GsonBuilder.serializeSpecialFloatingPointValues() method."
         );
      }
   }

   private static TypeAdapter<Number> longAdapter(LongSerializationPolicy longSerializationPolicy) {
      return longSerializationPolicy == LongSerializationPolicy.DEFAULT ? TypeAdapters.LONG : new TypeAdapter<Number>() {
         public Number read(JsonReader in) throws IOException {
            if (in.peek() == JsonToken.NULL) {
               in.nextNull();
               return null;
            } else {
               return in.nextLong();
            }
         }

         public void write(JsonWriter out, Number value) throws IOException {
            if (value == null) {
               out.nullValue();
            } else {
               out.value(value.toString());
            }
         }
      };
   }

   private static TypeAdapter<AtomicLong> atomicLongAdapter(final TypeAdapter<Number> longAdapter) {
      return (new TypeAdapter<AtomicLong>() {
         public void write(JsonWriter out, AtomicLong value) throws IOException {
            longAdapter.write(out, value.get());
         }

         public AtomicLong read(JsonReader in) throws IOException {
            Number value = longAdapter.read(in);
            return new AtomicLong(value.longValue());
         }
      }).nullSafe();
   }

   private static TypeAdapter<AtomicLongArray> atomicLongArrayAdapter(final TypeAdapter<Number> longAdapter) {
      return (new TypeAdapter<AtomicLongArray>() {
         public void write(JsonWriter out, AtomicLongArray value) throws IOException {
            out.beginArray();
            int i = 0;

            for (int length = value.length(); i < length; i++) {
               longAdapter.write(out, value.get(i));
            }

            out.endArray();
         }

         public AtomicLongArray read(JsonReader in) throws IOException {
            List<Long> list = new ArrayList<>();
            in.beginArray();

            while (in.hasNext()) {
               long value = longAdapter.read(in).longValue();
               list.add(value);
            }

            in.endArray();
            int length = list.size();
            AtomicLongArray array = new AtomicLongArray(length);

            for (int i = 0; i < length; i++) {
               array.set(i, list.get(i));
            }

            return array;
         }
      }).nullSafe();
   }

   public <T> TypeAdapter<T> getAdapter(TypeToken<T> type) {
      Objects.requireNonNull(type, "type must not be null");
      TypeAdapter<?> cached = this.typeTokenCache.get(type);
      if (cached != null) {
         return (TypeAdapter<T>)cached;
      } else {
         Map<TypeToken<?>, TypeAdapter<?>> threadCalls = this.threadLocalAdapterResults.get();
         boolean isInitialAdapterRequest = false;
         if (threadCalls == null) {
            threadCalls = new HashMap<>();
            this.threadLocalAdapterResults.set(threadCalls);
            isInitialAdapterRequest = true;
         } else {
            TypeAdapter<T> ongoingCall = (TypeAdapter<T>)threadCalls.get(type);
            if (ongoingCall != null) {
               return ongoingCall;
            }
         }

         TypeAdapter<T> candidate = null;

         try {
            Gson.FutureTypeAdapter<T> call = new Gson.FutureTypeAdapter<>();
            threadCalls.put(type, call);

            for (TypeAdapterFactory factory : this.factories) {
               candidate = factory.create(this, type);
               if (candidate != null) {
                  call.setDelegate(candidate);
                  threadCalls.put(type, candidate);
                  break;
               }
            }
         } finally {
            if (isInitialAdapterRequest) {
               this.threadLocalAdapterResults.remove();
            }
         }

         if (candidate == null) {
            throw new IllegalArgumentException("GSON (2.10.1) cannot handle " + type);
         } else {
            if (isInitialAdapterRequest) {
               this.typeTokenCache.putAll(threadCalls);
            }

            return candidate;
         }
      }
   }

   public <T> TypeAdapter<T> getDelegateAdapter(TypeAdapterFactory skipPast, TypeToken<T> type) {
      if (!this.factories.contains(skipPast)) {
         skipPast = this.jsonAdapterFactory;
      }

      boolean skipPastFound = false;

      for (TypeAdapterFactory factory : this.factories) {
         if (!skipPastFound) {
            if (factory == skipPast) {
               skipPastFound = true;
            }
         } else {
            TypeAdapter<T> candidate = factory.create(this, type);
            if (candidate != null) {
               return candidate;
            }
         }
      }

      throw new IllegalArgumentException("GSON cannot serialize " + type);
   }

   public <T> TypeAdapter<T> getAdapter(Class<T> type) {
      return this.getAdapter(TypeToken.get(type));
   }

   public JsonElement toJsonTree(Object src) {
      return (JsonElement)(src == null ? JsonNull.INSTANCE : this.toJsonTree(src, src.getClass()));
   }

   public JsonElement toJsonTree(Object src, Type typeOfSrc) {
      JsonTreeWriter writer = new JsonTreeWriter();
      this.toJson(src, typeOfSrc, writer);
      return writer.get();
   }

   public String toJson(Object src) {
      return src == null ? this.toJson((JsonElement)JsonNull.INSTANCE) : this.toJson(src, src.getClass());
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
      } catch (IOException var5) {
         throw new JsonIOException(var5);
      }
   }

   public void toJson(Object src, Type typeOfSrc, JsonWriter writer) throws JsonIOException {
      TypeAdapter<Object> adapter = this.getAdapter((TypeToken<Object>)TypeToken.get(typeOfSrc));
      boolean oldLenient = writer.isLenient();
      writer.setLenient(true);
      boolean oldHtmlSafe = writer.isHtmlSafe();
      writer.setHtmlSafe(this.htmlSafe);
      boolean oldSerializeNulls = writer.getSerializeNulls();
      writer.setSerializeNulls(this.serializeNulls);

      try {
         adapter.write(writer, src);
      } catch (IOException var13) {
         throw new JsonIOException(var13);
      } catch (AssertionError var14) {
         throw new AssertionError("AssertionError (GSON 2.10.1): " + var14.getMessage(), var14);
      } finally {
         writer.setLenient(oldLenient);
         writer.setHtmlSafe(oldHtmlSafe);
         writer.setSerializeNulls(oldSerializeNulls);
      }
   }

   public String toJson(JsonElement jsonElement) {
      StringWriter writer = new StringWriter();
      this.toJson(jsonElement, writer);
      return writer.toString();
   }

   public void toJson(JsonElement jsonElement, Appendable writer) throws JsonIOException {
      try {
         JsonWriter jsonWriter = this.newJsonWriter(Streams.writerForAppendable(writer));
         this.toJson(jsonElement, jsonWriter);
      } catch (IOException var4) {
         throw new JsonIOException(var4);
      }
   }

   public JsonWriter newJsonWriter(Writer writer) throws IOException {
      if (this.generateNonExecutableJson) {
         writer.write(")]}'\n");
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
      } catch (IOException var11) {
         throw new JsonIOException(var11);
      } catch (AssertionError var12) {
         throw new AssertionError("AssertionError (GSON 2.10.1): " + var12.getMessage(), var12);
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
      return this.fromJson(json, (TypeToken<T>)TypeToken.get(typeOfT));
   }

   public <T> T fromJson(String json, TypeToken<T> typeOfT) throws JsonSyntaxException {
      if (json == null) {
         return null;
      } else {
         StringReader reader = new StringReader(json);
         return this.fromJson(reader, typeOfT);
      }
   }

   public <T> T fromJson(Reader json, Class<T> classOfT) throws JsonSyntaxException, JsonIOException {
      T object = this.fromJson(json, TypeToken.get(classOfT));
      return Primitives.wrap(classOfT).cast(object);
   }

   public <T> T fromJson(Reader json, Type typeOfT) throws JsonIOException, JsonSyntaxException {
      return this.fromJson(json, (TypeToken<T>)TypeToken.get(typeOfT));
   }

   public <T> T fromJson(Reader json, TypeToken<T> typeOfT) throws JsonIOException, JsonSyntaxException {
      JsonReader jsonReader = this.newJsonReader(json);
      T object = this.fromJson(jsonReader, typeOfT);
      assertFullConsumption(object, jsonReader);
      return object;
   }

   private static void assertFullConsumption(Object obj, JsonReader reader) {
      try {
         if (obj != null && reader.peek() != JsonToken.END_DOCUMENT) {
            throw new JsonSyntaxException("JSON document was not fully consumed.");
         }
      } catch (MalformedJsonException var3) {
         throw new JsonSyntaxException(var3);
      } catch (IOException var4) {
         throw new JsonIOException(var4);
      }
   }

   public <T> T fromJson(JsonReader reader, Type typeOfT) throws JsonIOException, JsonSyntaxException {
      return this.fromJson(reader, (TypeToken<T>)TypeToken.get(typeOfT));
   }

   public <T> T fromJson(JsonReader reader, TypeToken<T> typeOfT) throws JsonIOException, JsonSyntaxException {
      boolean isEmpty = true;
      boolean oldLenient = reader.isLenient();
      reader.setLenient(true);

      Object var6;
      try {
         reader.peek();
         isEmpty = false;
         TypeAdapter<T> typeAdapter = this.getAdapter(typeOfT);
         return typeAdapter.read(reader);
      } catch (EOFException var13) {
         if (!isEmpty) {
            throw new JsonSyntaxException(var13);
         }

         var6 = null;
      } catch (IllegalStateException var14) {
         throw new JsonSyntaxException(var14);
      } catch (IOException var15) {
         throw new JsonSyntaxException(var15);
      } catch (AssertionError var16) {
         throw new AssertionError("AssertionError (GSON 2.10.1): " + var16.getMessage(), var16);
      } finally {
         reader.setLenient(oldLenient);
      }

      return (T)var6;
   }

   public <T> T fromJson(JsonElement json, Class<T> classOfT) throws JsonSyntaxException {
      T object = this.fromJson(json, TypeToken.get(classOfT));
      return Primitives.wrap(classOfT).cast(object);
   }

   public <T> T fromJson(JsonElement json, Type typeOfT) throws JsonSyntaxException {
      return this.fromJson(json, (TypeToken<T>)TypeToken.get(typeOfT));
   }

   public <T> T fromJson(JsonElement json, TypeToken<T> typeOfT) throws JsonSyntaxException {
      return json == null ? null : this.fromJson(new JsonTreeReader(json), typeOfT);
   }

   @Override
   public String toString() {
      return "{serializeNulls:" + this.serializeNulls + ",factories:" + this.factories + ",instanceCreators:" + this.constructorConstructor + "}";
   }

   static class FutureTypeAdapter<T> extends SerializationDelegatingTypeAdapter<T> {
      private TypeAdapter<T> delegate = null;

      public void setDelegate(TypeAdapter<T> typeAdapter) {
         if (this.delegate != null) {
            throw new AssertionError("Delegate is already set");
         } else {
            this.delegate = typeAdapter;
         }
      }

      private TypeAdapter<T> delegate() {
         TypeAdapter<T> delegate = this.delegate;
         if (delegate == null) {
            throw new IllegalStateException("Adapter for type with cyclic dependency has been used before dependency has been resolved");
         } else {
            return delegate;
         }
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
