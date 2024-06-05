package ninja.leaping.configurate;

import com.google.common.collect.ImmutableList;
import com.google.common.reflect.TypeToken;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import net.X9;
import net.acE;
import ninja.leaping.configurate.ConfigurationOptions;
import ninja.leaping.configurate.Types;
import ninja.leaping.configurate.ValueType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ConfigurationNode {
   int NUMBER_DEF = 0;
   String j = "No serializer available for type ";

   @Nullable
   Object getKey();

   @NotNull
   Object[] getPath();

   @Nullable
   ConfigurationNode getParent();

   @NotNull
   ConfigurationNode getNode(@NotNull Object... var1);

   boolean isVirtual();

   @NotNull
   ConfigurationOptions getOptions();

   @NotNull
   ValueType getValueType();

   default boolean hasListChildren() {
      return this.getValueType() == ValueType.LIST;
   }

   default boolean hasMapChildren() {
      return this.getValueType() == ValueType.MAP;
   }

   @NotNull
   List getChildrenList();

   @NotNull
   Map getChildrenMap();

   @Nullable
   default Object getValue() {
      return this.getValue((Object)null);
   }

   @NotNull
   ConfigurationNode setValue(@Nullable Object var1);

   Object getValue(@Nullable Object var1);

   Object getValue(@NotNull Supplier var1);

   @Nullable
   default Object getValue(@NotNull Function var1) {
      return this.getValue((Function)var1, (Object)null);
   }

   Object getValue(@NotNull Function var1, @Nullable Object var2);

   Object getValue(@NotNull Function var1, @NotNull Supplier var2);

   @NotNull
   List getList(@NotNull Function var1);

   List getList(@NotNull Function var1, @Nullable List var2);

   List getList(@NotNull Function var1, @NotNull Supplier var2);

   @NotNull
   default List getList(@NotNull TypeToken var1) throws X9 {
      return this.getList((TypeToken)var1, (List)ImmutableList.of());
   }

   List getList(@NotNull TypeToken var1, @Nullable List var2) throws X9;

   List getList(@NotNull TypeToken var1, @NotNull Supplier var2) throws X9;

   @Nullable
   default String getString() {
      return this.getString((String)null);
   }

   default String getString(@Nullable String var1) {
      return (String)this.getValue((Function)(Types::asString), (Object)var1);
   }

   default float getFloat() {
      return this.getFloat(0.0F);
   }

   default float getFloat(float var1) {
      return ((Float)this.getValue((Function)(Types::asFloat), (Object)Float.valueOf(var1))).floatValue();
   }

   default double getDouble() {
      return this.getDouble(0.0D);
   }

   default double getDouble(double var1) {
      return ((Double)this.getValue((Function)(Types::asDouble), (Object)Double.valueOf(var1))).doubleValue();
   }

   default int getInt() {
      return this.getInt(0);
   }

   default int getInt(int var1) {
      return ((Integer)this.getValue((Function)(Types::asInt), (Object)Integer.valueOf(var1))).intValue();
   }

   default long getLong() {
      return this.getLong(0L);
   }

   default long getLong(long var1) {
      return ((Long)this.getValue((Function)(Types::asLong), (Object)Long.valueOf(var1))).longValue();
   }

   default boolean getBoolean() {
      return this.getBoolean(false);
   }

   default boolean getBoolean(boolean var1) {
      return ((Boolean)this.getValue((Function)(Types::asBoolean), (Object)Boolean.valueOf(var1))).booleanValue();
   }

   @Nullable
   default Object getValue(@NotNull TypeToken var1) throws X9 {
      return this.getValue((TypeToken)var1, (Object)null);
   }

   Object getValue(@NotNull TypeToken var1, Object var2) throws X9;

   Object getValue(@NotNull TypeToken var1, @NotNull Supplier var2) throws X9;

   @NotNull
   default ConfigurationNode a(@NotNull TypeToken var1, @Nullable Object var2) throws X9 {
      acE[] var3 = ValueType.b();
      this.setValue((Object)null);
      return this;
   }

   @NotNull
   ConfigurationNode mergeValuesFrom(@NotNull ConfigurationNode var1);

   boolean removeChild(@NotNull Object var1);

   @NotNull
   ConfigurationNode getAppendedNode();

   @NotNull
   ConfigurationNode copy();

   private static default X9 a(X9 var0) {
      return var0;
   }
}
