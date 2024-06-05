package cc.novoline.modules.configurations.property.object;

import cc.novoline.modules.binds.ModuleKeybind;
import cc.novoline.modules.configurations.property.object.BooleanProperty;
import cc.novoline.modules.configurations.property.object.ColorProperty;
import cc.novoline.modules.configurations.property.object.DoubleProperty;
import cc.novoline.modules.configurations.property.object.FloatProperty;
import cc.novoline.modules.configurations.property.object.IntProperty;
import cc.novoline.modules.configurations.property.object.KeyBindProperty;
import cc.novoline.modules.configurations.property.object.ListProperty;
import cc.novoline.modules.configurations.property.object.LongProperty;
import cc.novoline.modules.configurations.property.object.StringProperty;
import cc.novoline.utils.java.Checks;
import java.util.Collection;
import java.util.List;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class PropertyFactory {
   @Contract(
      value = "_ -> new",
      pure = true
   )
   @NotNull
   public static KeyBindProperty keyBinding(@NotNull ModuleKeybind var0) {
      Checks.notNull(var0, "Key binding");
      return new KeyBindProperty(var0);
   }

   @NotNull
   public static ListProperty createList(@NotNull List var0) {
      return ListProperty.of(var0);
   }

   @NotNull
   public static ListProperty createList(@NotNull Collection var0) {
      return ListProperty.of(var0);
   }

   @SafeVarargs
   @NotNull
   public static ListProperty createList(@NotNull Object... var0) {
      return ListProperty.of(var0);
   }

   @NotNull
   public static ListProperty createList(@Nullable Object var0) {
      return ListProperty.of(var0);
   }

   @NotNull
   public static ListProperty createList(@Nullable Object var0, @Nullable Object var1) {
      return ListProperty.of(var0, var1);
   }

   @NotNull
   public static ListProperty createList(@Nullable Object var0, @Nullable Object var1, @Nullable Object var2) {
      return ListProperty.of(var0, var1, var2);
   }

   @NotNull
   public static BooleanProperty createBoolean(@Nullable Boolean var0) {
      return BooleanProperty.of(var0);
   }

   @NotNull
   public static BooleanProperty booleanTrue() {
      return BooleanProperty.of(Boolean.valueOf(true));
   }

   @NotNull
   public static BooleanProperty booleanFalse() {
      return BooleanProperty.of(Boolean.valueOf(false));
   }

   @NotNull
   public static BooleanProperty immutableBooleanFalse() {
      return BooleanProperty.alwaysFalse();
   }

   @NotNull
   public static BooleanProperty immutableBooleanTrue() {
      return BooleanProperty.alwaysTrue();
   }

   @NotNull
   public static DoubleProperty createDouble(@Nullable Double var0) {
      return DoubleProperty.of(var0);
   }

   @NotNull
   public static DoubleProperty createDouble() {
      return DoubleProperty.create();
   }

   @NotNull
   public static FloatProperty createFloat(@Nullable Float var0) {
      return FloatProperty.of(var0);
   }

   @NotNull
   public static FloatProperty createFloat() {
      return FloatProperty.create();
   }

   @NotNull
   public static IntProperty createInt(@Nullable Integer var0) {
      return IntProperty.of(var0);
   }

   @NotNull
   public static IntProperty createInt() {
      return IntProperty.create();
   }

   @NotNull
   public static LongProperty createLong(@Nullable Long var0) {
      return LongProperty.of(var0);
   }

   @NotNull
   public static LongProperty createLong() {
      return LongProperty.create();
   }

   @NotNull
   public static StringProperty createString(@Nullable String var0) {
      return StringProperty.of(var0);
   }

   @NotNull
   public static StringProperty createString() {
      return StringProperty.create();
   }

   @NotNull
   public static ColorProperty createColor(@Nullable Integer var0) {
      return ColorProperty.of(var0);
   }
}
