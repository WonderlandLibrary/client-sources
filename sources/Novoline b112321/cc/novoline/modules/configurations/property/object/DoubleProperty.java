package cc.novoline.modules.configurations.property.object;

import cc.novoline.modules.configurations.property.AbstractNumberProperty;
import cc.novoline.modules.configurations.property.object.IntProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class DoubleProperty extends AbstractNumberProperty {
   public DoubleProperty(@Nullable Double var1) {
      super(var1);
   }

   public DoubleProperty() {
      super(Double.valueOf(0.0D));
   }

   @NotNull
   public static DoubleProperty of(@Nullable Double var0) {
      return new DoubleProperty(var0);
   }

   @NotNull
   public static DoubleProperty create() {
      return new DoubleProperty();
   }

   protected void e(@Nullable Double var1) {
      this.value = Double.valueOf(((Double)this.value).doubleValue() + var1.doubleValue());
   }

   protected void a(@Nullable Double var1) {
      this.value = Double.valueOf(((Double)this.value).doubleValue() - var1.doubleValue());
   }

   protected boolean greaterThan(@Nullable Double var1) {
      int[] var2 = IntProperty.a();
      return var1 == null?this.value != null:(this.value == null?true:((Double)this.value).doubleValue() > var1.doubleValue());
   }

   protected boolean lessThan(@Nullable Double var1) {
      int[] var2 = IntProperty.a();
      return var1 == null?false:(this.value == null?true:((Double)this.value).doubleValue() < var1.doubleValue());
   }

   protected boolean inLimits(@NotNull Double var1) {
      int[] var2 = IntProperty.a();
      return (this.minimum == null || var1.doubleValue() >= ((Double)this.minimum).doubleValue()) && (this.maximum == null || var1.doubleValue() <= ((Double)this.maximum).doubleValue());
   }

   protected DoubleProperty self() {
      return this;
   }
}
