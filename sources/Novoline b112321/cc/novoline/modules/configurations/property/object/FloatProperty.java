package cc.novoline.modules.configurations.property.object;

import cc.novoline.modules.configurations.property.AbstractNumberProperty;
import cc.novoline.modules.configurations.property.object.IntProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class FloatProperty extends AbstractNumberProperty {
   public FloatProperty(@Nullable Float var1) {
      super(var1);
   }

   public FloatProperty() {
      this(Float.valueOf(0.0F));
   }

   @NotNull
   public static FloatProperty of(@Nullable Float var0) {
      return new FloatProperty(var0);
   }

   @NotNull
   public static FloatProperty create() {
      return new FloatProperty();
   }

   protected void e(@Nullable Float var1) {
      this.value = Float.valueOf(((Float)this.value).floatValue() + var1.floatValue());
   }

   protected void a(@Nullable Float var1) {
      this.value = Float.valueOf(((Float)this.value).floatValue() - var1.floatValue());
   }

   protected boolean greaterThan(@Nullable Float var1) {
      int[] var2 = IntProperty.a();
      return var1 == null?this.value != null:(this.value == null?true:((Float)this.value).floatValue() > var1.floatValue());
   }

   protected boolean lessThan(@Nullable Float var1) {
      int[] var2 = IntProperty.a();
      return var1 == null?false:(this.value == null?true:((Float)this.value).floatValue() < var1.floatValue());
   }

   protected boolean inLimits(@NotNull Float var1) {
      int[] var2 = IntProperty.a();
      return (this.minimum == null || var1.floatValue() >= ((Float)this.minimum).floatValue()) && (this.maximum == null || var1.floatValue() <= ((Float)this.maximum).floatValue());
   }

   protected FloatProperty self() {
      return this;
   }
}
