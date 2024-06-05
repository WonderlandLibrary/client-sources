package cc.novoline.modules.configurations.property;

import cc.novoline.modules.configurations.property.AbstractProperty;
import cc.novoline.modules.configurations.property.exception.NumberLimitsException;
import java.util.Objects;
import net.acE;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractNumberProperty extends AbstractProperty {
   protected Number minimum;
   protected Number maximum;
   private static int[] e;

   protected AbstractNumberProperty(Number var1) {
      super(var1);
   }

   protected AbstractNumberProperty() {
   }

   public void set(@Nullable Number var1) {
      int var2 = AbstractProperty.b();
      if(this.inLimits(var1)) {
         super.set(var1);
      }

      throw new NumberLimitsException("Unable to set " + var1 + " (min:" + this.minimum + ", max:" + this.maximum + ")", this);
   }

   protected abstract void a(Number var1);

   protected abstract void f(Number var1);

   protected abstract boolean greaterThan(Number var1);

   protected abstract boolean lessThan(Number var1);

   protected abstract boolean inLimits(@NotNull Number var1);

   protected boolean greaterOrEquals(Number var1) {
      int var2 = AbstractProperty.b();
      return this.greaterThan(var1)?true:(var1 == null && this.value == null?false:Objects.equals(var1, this.value));
   }

   protected boolean lessOrEquals(Number var1) {
      int var2 = AbstractProperty.b();
      return this.lessThan(var1)?true:(var1 == null?false:Objects.equals(var1, this.value));
   }

   public AbstractNumberProperty minimum(@Nullable Number var1) {
      int var2 = AbstractProperty.a();
      if(this.value != null && this.lessThan(var1)) {
         throw new IllegalArgumentException("minimum is greater than current value: " + this.value + ", min: " + var1);
      } else {
         this.minimum = var1;
         AbstractNumberProperty var10000 = this.self();
         if(acE.b() == null) {
            ++var2;
            AbstractProperty.b(var2);
         }

         return var10000;
      }
   }

   public Number getMinimum() {
      return this.minimum;
   }

   public AbstractNumberProperty maximum(@Nullable Number var1) {
      int var2 = AbstractProperty.b();
      if(this.value != null && this.greaterThan(var1)) {
         throw new IllegalArgumentException("current value is greater than maximum: " + this.value + ", max: " + var1);
      } else {
         this.maximum = var1;
         return this.self();
      }
   }

   public Number getMaximum() {
      return this.maximum;
   }

   protected abstract AbstractNumberProperty self();

   public static void a(int[] var0) {
      e = var0;
   }

   public static int[] b() {
      return e;
   }

   private static NumberLimitsException a(NumberLimitsException var0) {
      return var0;
   }

   static {
      a(new int[3]);
   }
}
