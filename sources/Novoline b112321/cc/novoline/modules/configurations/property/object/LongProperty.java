package cc.novoline.modules.configurations.property.object;

import cc.novoline.modules.configurations.property.AbstractNumberProperty;
import cc.novoline.modules.configurations.property.object.IntProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class LongProperty extends AbstractNumberProperty {
   public LongProperty(@Nullable Long var1) {
      super(var1);
   }

   public LongProperty() {
      this(Long.valueOf(0L));
   }

   @NotNull
   public static LongProperty of(@Nullable Long var0) {
      return new LongProperty(var0);
   }

   @NotNull
   public static LongProperty create() {
      return new LongProperty();
   }

   protected void c(@Nullable Long var1) {
      this.value = Long.valueOf(((Long)this.value).longValue() + var1.longValue());
   }

   protected void b(@Nullable Long var1) {
      this.value = Long.valueOf(((Long)this.value).longValue() - var1.longValue());
   }

   protected boolean greaterThan(@Nullable Long var1) {
      int[] var2 = IntProperty.a();
      return var1 == null?this.value != null:(this.value == null?true:((Long)this.value).longValue() > var1.longValue());
   }

   protected boolean lessThan(@Nullable Long var1) {
      int[] var2 = IntProperty.a();
      return var1 == null?false:(this.value == null?true:((Long)this.value).longValue() < var1.longValue());
   }

   protected boolean inLimits(@NotNull Long var1) {
      int[] var2 = IntProperty.a();
      return (this.minimum == null || var1.longValue() >= ((Long)this.minimum).longValue()) && (this.maximum == null || var1.longValue() <= ((Long)this.maximum).longValue());
   }

   protected LongProperty self() {
      return this;
   }
}
