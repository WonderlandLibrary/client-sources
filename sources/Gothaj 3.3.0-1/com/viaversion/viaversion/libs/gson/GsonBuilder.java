package com.viaversion.viaversion.libs.gson;

import com.viaversion.viaversion.libs.gson.internal.$Gson$Preconditions;
import com.viaversion.viaversion.libs.gson.internal.Excluder;
import com.viaversion.viaversion.libs.gson.internal.bind.DefaultDateTypeAdapter;
import com.viaversion.viaversion.libs.gson.internal.bind.TreeTypeAdapter;
import com.viaversion.viaversion.libs.gson.internal.bind.TypeAdapters;
import com.viaversion.viaversion.libs.gson.internal.sql.SqlTypesSupport;
import com.viaversion.viaversion.libs.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public final class GsonBuilder {
   private Excluder excluder = Excluder.DEFAULT;
   private LongSerializationPolicy longSerializationPolicy = LongSerializationPolicy.DEFAULT;
   private FieldNamingStrategy fieldNamingPolicy = FieldNamingPolicy.IDENTITY;
   private final Map<Type, InstanceCreator<?>> instanceCreators = new HashMap<>();
   private final List<TypeAdapterFactory> factories = new ArrayList<>();
   private final List<TypeAdapterFactory> hierarchyFactories = new ArrayList<>();
   private boolean serializeNulls = false;
   private String datePattern = Gson.DEFAULT_DATE_PATTERN;
   private int dateStyle = 2;
   private int timeStyle = 2;
   private boolean complexMapKeySerialization = false;
   private boolean serializeSpecialFloatingPointValues = false;
   private boolean escapeHtmlChars = true;
   private boolean prettyPrinting = false;
   private boolean generateNonExecutableJson = false;
   private boolean lenient = false;
   private boolean useJdkUnsafe = true;
   private ToNumberStrategy objectToNumberStrategy = Gson.DEFAULT_OBJECT_TO_NUMBER_STRATEGY;
   private ToNumberStrategy numberToNumberStrategy = Gson.DEFAULT_NUMBER_TO_NUMBER_STRATEGY;
   private final LinkedList<ReflectionAccessFilter> reflectionFilters = new LinkedList<>();

   public GsonBuilder() {
   }

   GsonBuilder(Gson gson) {
      this.excluder = gson.excluder;
      this.fieldNamingPolicy = gson.fieldNamingStrategy;
      this.instanceCreators.putAll(gson.instanceCreators);
      this.serializeNulls = gson.serializeNulls;
      this.complexMapKeySerialization = gson.complexMapKeySerialization;
      this.generateNonExecutableJson = gson.generateNonExecutableJson;
      this.escapeHtmlChars = gson.htmlSafe;
      this.prettyPrinting = gson.prettyPrinting;
      this.lenient = gson.lenient;
      this.serializeSpecialFloatingPointValues = gson.serializeSpecialFloatingPointValues;
      this.longSerializationPolicy = gson.longSerializationPolicy;
      this.datePattern = gson.datePattern;
      this.dateStyle = gson.dateStyle;
      this.timeStyle = gson.timeStyle;
      this.factories.addAll(gson.builderFactories);
      this.hierarchyFactories.addAll(gson.builderHierarchyFactories);
      this.useJdkUnsafe = gson.useJdkUnsafe;
      this.objectToNumberStrategy = gson.objectToNumberStrategy;
      this.numberToNumberStrategy = gson.numberToNumberStrategy;
      this.reflectionFilters.addAll(gson.reflectionFilters);
   }

   public GsonBuilder setVersion(double version) {
      if (!Double.isNaN(version) && !(version < 0.0)) {
         this.excluder = this.excluder.withVersion(version);
         return this;
      } else {
         throw new IllegalArgumentException("Invalid version: " + version);
      }
   }

   public GsonBuilder excludeFieldsWithModifiers(int... modifiers) {
      Objects.requireNonNull(modifiers);
      this.excluder = this.excluder.withModifiers(modifiers);
      return this;
   }

   public GsonBuilder generateNonExecutableJson() {
      this.generateNonExecutableJson = true;
      return this;
   }

   public GsonBuilder excludeFieldsWithoutExposeAnnotation() {
      this.excluder = this.excluder.excludeFieldsWithoutExposeAnnotation();
      return this;
   }

   public GsonBuilder serializeNulls() {
      this.serializeNulls = true;
      return this;
   }

   public GsonBuilder enableComplexMapKeySerialization() {
      this.complexMapKeySerialization = true;
      return this;
   }

   public GsonBuilder disableInnerClassSerialization() {
      this.excluder = this.excluder.disableInnerClassSerialization();
      return this;
   }

   public GsonBuilder setLongSerializationPolicy(LongSerializationPolicy serializationPolicy) {
      this.longSerializationPolicy = Objects.requireNonNull(serializationPolicy);
      return this;
   }

   public GsonBuilder setFieldNamingPolicy(FieldNamingPolicy namingConvention) {
      return this.setFieldNamingStrategy(namingConvention);
   }

   public GsonBuilder setFieldNamingStrategy(FieldNamingStrategy fieldNamingStrategy) {
      this.fieldNamingPolicy = Objects.requireNonNull(fieldNamingStrategy);
      return this;
   }

   public GsonBuilder setObjectToNumberStrategy(ToNumberStrategy objectToNumberStrategy) {
      this.objectToNumberStrategy = Objects.requireNonNull(objectToNumberStrategy);
      return this;
   }

   public GsonBuilder setNumberToNumberStrategy(ToNumberStrategy numberToNumberStrategy) {
      this.numberToNumberStrategy = Objects.requireNonNull(numberToNumberStrategy);
      return this;
   }

   public GsonBuilder setExclusionStrategies(ExclusionStrategy... strategies) {
      Objects.requireNonNull(strategies);

      for (ExclusionStrategy strategy : strategies) {
         this.excluder = this.excluder.withExclusionStrategy(strategy, true, true);
      }

      return this;
   }

   public GsonBuilder addSerializationExclusionStrategy(ExclusionStrategy strategy) {
      Objects.requireNonNull(strategy);
      this.excluder = this.excluder.withExclusionStrategy(strategy, true, false);
      return this;
   }

   public GsonBuilder addDeserializationExclusionStrategy(ExclusionStrategy strategy) {
      Objects.requireNonNull(strategy);
      this.excluder = this.excluder.withExclusionStrategy(strategy, false, true);
      return this;
   }

   public GsonBuilder setPrettyPrinting() {
      this.prettyPrinting = true;
      return this;
   }

   public GsonBuilder setLenient() {
      this.lenient = true;
      return this;
   }

   public GsonBuilder disableHtmlEscaping() {
      this.escapeHtmlChars = false;
      return this;
   }

   public GsonBuilder setDateFormat(String pattern) {
      this.datePattern = pattern;
      return this;
   }

   public GsonBuilder setDateFormat(int style) {
      this.dateStyle = style;
      this.datePattern = null;
      return this;
   }

   public GsonBuilder setDateFormat(int dateStyle, int timeStyle) {
      this.dateStyle = dateStyle;
      this.timeStyle = timeStyle;
      this.datePattern = null;
      return this;
   }

   public GsonBuilder registerTypeAdapter(Type type, Object typeAdapter) {
      Objects.requireNonNull(type);
      $Gson$Preconditions.checkArgument(
         typeAdapter instanceof JsonSerializer
            || typeAdapter instanceof JsonDeserializer
            || typeAdapter instanceof InstanceCreator
            || typeAdapter instanceof TypeAdapter
      );
      if (typeAdapter instanceof InstanceCreator) {
         this.instanceCreators.put(type, (InstanceCreator<?>)typeAdapter);
      }

      if (typeAdapter instanceof JsonSerializer || typeAdapter instanceof JsonDeserializer) {
         TypeToken<?> typeToken = TypeToken.get(type);
         this.factories.add(TreeTypeAdapter.newFactoryWithMatchRawType(typeToken, typeAdapter));
      }

      if (typeAdapter instanceof TypeAdapter) {
         TypeAdapterFactory factory = TypeAdapters.newFactory(TypeToken.get(type), (TypeAdapter<?>)typeAdapter);
         this.factories.add(factory);
      }

      return this;
   }

   public GsonBuilder registerTypeAdapterFactory(TypeAdapterFactory factory) {
      Objects.requireNonNull(factory);
      this.factories.add(factory);
      return this;
   }

   public GsonBuilder registerTypeHierarchyAdapter(Class<?> baseType, Object typeAdapter) {
      Objects.requireNonNull(baseType);
      $Gson$Preconditions.checkArgument(typeAdapter instanceof JsonSerializer || typeAdapter instanceof JsonDeserializer || typeAdapter instanceof TypeAdapter);
      if (typeAdapter instanceof JsonDeserializer || typeAdapter instanceof JsonSerializer) {
         this.hierarchyFactories.add(TreeTypeAdapter.newTypeHierarchyFactory(baseType, typeAdapter));
      }

      if (typeAdapter instanceof TypeAdapter) {
         TypeAdapterFactory factory = TypeAdapters.newTypeHierarchyFactory(baseType, (TypeAdapter<?>)typeAdapter);
         this.factories.add(factory);
      }

      return this;
   }

   public GsonBuilder serializeSpecialFloatingPointValues() {
      this.serializeSpecialFloatingPointValues = true;
      return this;
   }

   public GsonBuilder disableJdkUnsafe() {
      this.useJdkUnsafe = false;
      return this;
   }

   public GsonBuilder addReflectionAccessFilter(ReflectionAccessFilter filter) {
      Objects.requireNonNull(filter);
      this.reflectionFilters.addFirst(filter);
      return this;
   }

   public Gson create() {
      List<TypeAdapterFactory> factories = new ArrayList<>(this.factories.size() + this.hierarchyFactories.size() + 3);
      factories.addAll(this.factories);
      Collections.reverse(factories);
      List<TypeAdapterFactory> hierarchyFactories = new ArrayList<>(this.hierarchyFactories);
      Collections.reverse(hierarchyFactories);
      factories.addAll(hierarchyFactories);
      this.addTypeAdaptersForDate(this.datePattern, this.dateStyle, this.timeStyle, factories);
      return new Gson(
         this.excluder,
         this.fieldNamingPolicy,
         new HashMap<>(this.instanceCreators),
         this.serializeNulls,
         this.complexMapKeySerialization,
         this.generateNonExecutableJson,
         this.escapeHtmlChars,
         this.prettyPrinting,
         this.lenient,
         this.serializeSpecialFloatingPointValues,
         this.useJdkUnsafe,
         this.longSerializationPolicy,
         this.datePattern,
         this.dateStyle,
         this.timeStyle,
         new ArrayList<>(this.factories),
         new ArrayList<>(this.hierarchyFactories),
         factories,
         this.objectToNumberStrategy,
         this.numberToNumberStrategy,
         new ArrayList<>(this.reflectionFilters)
      );
   }

   private void addTypeAdaptersForDate(String datePattern, int dateStyle, int timeStyle, List<TypeAdapterFactory> factories) {
      boolean sqlTypesSupported = SqlTypesSupport.SUPPORTS_SQL_TYPES;
      TypeAdapterFactory sqlTimestampAdapterFactory = null;
      TypeAdapterFactory sqlDateAdapterFactory = null;
      TypeAdapterFactory dateAdapterFactory;
      if (datePattern != null && !datePattern.trim().isEmpty()) {
         dateAdapterFactory = DefaultDateTypeAdapter.DateType.DATE.createAdapterFactory(datePattern);
         if (sqlTypesSupported) {
            sqlTimestampAdapterFactory = SqlTypesSupport.TIMESTAMP_DATE_TYPE.createAdapterFactory(datePattern);
            sqlDateAdapterFactory = SqlTypesSupport.DATE_DATE_TYPE.createAdapterFactory(datePattern);
         }
      } else {
         if (dateStyle == 2 || timeStyle == 2) {
            return;
         }

         dateAdapterFactory = DefaultDateTypeAdapter.DateType.DATE.createAdapterFactory(dateStyle, timeStyle);
         if (sqlTypesSupported) {
            sqlTimestampAdapterFactory = SqlTypesSupport.TIMESTAMP_DATE_TYPE.createAdapterFactory(dateStyle, timeStyle);
            sqlDateAdapterFactory = SqlTypesSupport.DATE_DATE_TYPE.createAdapterFactory(dateStyle, timeStyle);
         }
      }

      factories.add(dateAdapterFactory);
      if (sqlTypesSupported) {
         factories.add(sqlTimestampAdapterFactory);
         factories.add(sqlDateAdapterFactory);
      }
   }
}
