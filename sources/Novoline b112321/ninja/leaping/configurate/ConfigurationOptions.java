package ninja.leaping.configurate;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.UnmodifiableIterator;
import java.util.Objects;
import java.util.Set;
import net.Ea;
import net.aIp;
import net.acE;
import ninja.leaping.configurate.ValueType;
import ninja.leaping.configurate.objectmapping.DefaultObjectMapperFactory;
import ninja.leaping.configurate.objectmapping.ObjectMapperFactory;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializers;
import ninja.leaping.configurate.util.MapFactory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ConfigurationOptions {
   private final MapFactory mapFactory;
   private final String header;
   private final Ea f;
   private final ImmutableSet acceptedTypes;
   private final ObjectMapperFactory objectMapperFactory;
   private final boolean shouldCopyDefaults;

   private ConfigurationOptions(@NotNull MapFactory var1, @Nullable String var2, @NotNull Ea var3, @Nullable Set var4, @NotNull ObjectMapperFactory var5, boolean var6) {
      this.mapFactory = var1;
      this.header = var2;
      ValueType.b();
      this.f = var3;
      this.acceptedTypes = var4 == null?null:ImmutableSet.copyOf(var4);
      this.objectMapperFactory = var5;
      this.shouldCopyDefaults = var6;
   }

   @NotNull
   public static ConfigurationOptions defaults() {
      return new ConfigurationOptions(aIp.b(), (String)null, TypeSerializers.a(), (Set)null, DefaultObjectMapperFactory.getInstance(), false);
   }

   @NotNull
   public MapFactory getMapFactory() {
      return this.mapFactory;
   }

   @NotNull
   public ConfigurationOptions setMapFactory(@NotNull MapFactory var1) {
      ValueType.b();
      Objects.requireNonNull(var1, "mapFactory");
      return this.mapFactory == var1?this:new ConfigurationOptions(var1, this.header, this.f, this.acceptedTypes, this.objectMapperFactory, this.shouldCopyDefaults);
   }

   @Nullable
   public String getHeader() {
      return this.header;
   }

   @NotNull
   public ConfigurationOptions setHeader(@Nullable String var1) {
      acE[] var2 = ValueType.b();
      return Objects.equals(this.header, var1)?this:new ConfigurationOptions(this.mapFactory, var1, this.f, this.acceptedTypes, this.objectMapperFactory, this.shouldCopyDefaults);
   }

   @NotNull
   public Ea f() {
      return this.f;
   }

   @NotNull
   public ConfigurationOptions a(@NotNull Ea var1) {
      ValueType.b();
      Objects.requireNonNull(var1, "serializers");
      return this.f == var1?this:new ConfigurationOptions(this.mapFactory, this.header, var1, this.acceptedTypes, this.objectMapperFactory, this.shouldCopyDefaults);
   }

   @NotNull
   public ObjectMapperFactory getObjectMapperFactory() {
      return this.objectMapperFactory;
   }

   @NotNull
   public ConfigurationOptions setObjectMapperFactory(@NotNull ObjectMapperFactory var1) {
      ValueType.b();
      Objects.requireNonNull(var1, "factory");
      return this.objectMapperFactory == var1?this:new ConfigurationOptions(this.mapFactory, this.header, this.f, this.acceptedTypes, var1, this.shouldCopyDefaults);
   }

   public boolean acceptsType(@NotNull Class var1) {
      ValueType.b();
      Objects.requireNonNull(var1, "type");
      if(this.acceptedTypes == null) {
         return true;
      } else if(this.acceptedTypes.contains(var1)) {
         return true;
      } else {
         UnmodifiableIterator var3 = this.acceptedTypes.iterator();
         if(var3.hasNext()) {
            Class var4 = (Class)var3.next();
            if(var4.isAssignableFrom(var1)) {
               return true;
            }
         }

         return false;
      }
   }

   @NotNull
   public ConfigurationOptions setAcceptedTypes(@Nullable Set var1) {
      acE[] var2 = ValueType.b();
      return Objects.equals(this.acceptedTypes, var1)?this:new ConfigurationOptions(this.mapFactory, this.header, this.f, var1, this.objectMapperFactory, this.shouldCopyDefaults);
   }

   public boolean shouldCopyDefaults() {
      return this.shouldCopyDefaults;
   }

   @NotNull
   public ConfigurationOptions setShouldCopyDefaults(boolean var1) {
      acE[] var2 = ValueType.b();
      return this.shouldCopyDefaults == var1?this:new ConfigurationOptions(this.mapFactory, this.header, this.f, this.acceptedTypes, this.objectMapperFactory, var1);
   }

   @Nullable
   public ImmutableSet getAcceptedTypes() {
      return this.acceptedTypes;
   }

   public boolean equals(Object var1) {
      acE[] var2 = ValueType.b();
      if(this == var1) {
         return true;
      } else if(!(var1 instanceof ConfigurationOptions)) {
         return false;
      } else {
         ConfigurationOptions var3 = (ConfigurationOptions)var1;
         return Objects.equals(Boolean.valueOf(this.shouldCopyDefaults), Boolean.valueOf(var3.shouldCopyDefaults)) && Objects.equals(this.mapFactory, var3.mapFactory) && Objects.equals(this.header, var3.header) && Objects.equals(this.f, var3.f) && Objects.equals(this.acceptedTypes, var3.acceptedTypes) && Objects.equals(this.objectMapperFactory, var3.objectMapperFactory);
      }
   }

   public int hashCode() {
      return Objects.hash(new Object[]{this.mapFactory, this.header, this.f, this.acceptedTypes, this.objectMapperFactory, Boolean.valueOf(this.shouldCopyDefaults)});
   }

   public String toString() {
      return "ConfigurationOptions{mapFactory=" + this.mapFactory + ", header=\'" + this.header + '\'' + ", serializers=" + this.f + ", acceptedTypes=" + this.acceptedTypes + ", objectMapperFactory=" + this.objectMapperFactory + ", shouldCopyDefaults=" + this.shouldCopyDefaults + '}';
   }
}
