package cc.novoline.modules.configurations.property.object;

import cc.novoline.modules.configurations.property.AbstractNumberProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class IntProperty extends AbstractNumberProperty {
   private static int[] f;

   public IntProperty(@Nullable Integer var1) {
      super(var1);
   }

   public IntProperty() {
      this(Integer.valueOf(0));
   }

   @NotNull
   public static IntProperty of(@Nullable Integer var0) {
      return new IntProperty(var0);
   }

   @NotNull
   public static IntProperty create() {
      return new IntProperty();
   }

   protected boolean inLimits(@NotNull Integer var1) {
      int[] var2 = a();
      return (this.minimum == null || var1.intValue() >= ((Integer)this.minimum).intValue()) && (this.maximum == null || var1.intValue() <= ((Integer)this.maximum).intValue());
   }

   public void c(@Nullable Integer var1) {
      int[] var2 = a();
      if(this.value == null) {
         this.value = var1;
      }

      this.set(Integer.valueOf(((Integer)this.value).intValue() + var1.intValue()));
   }

   public void b(@Nullable Integer var1) {
      int[] var2 = a();
      if(this.value == null) {
         this.value = var1;
      }

      this.set(Integer.valueOf(((Integer)this.value).intValue() - var1.intValue()));
   }

   public boolean greaterThan(@Nullable Integer var1) {
      int[] var2 = a();
      return var1 == null?this.value != null:(this.value == null?true:((Integer)this.value).intValue() > var1.intValue());
   }

   public boolean lessThan(@Nullable Integer var1) {
      int[] var2 = a();
      return var1 == null?false:(this.value == null?true:((Integer)this.value).intValue() < var1.intValue());
   }

   protected IntProperty self() {
      return this;
   }

   public static void b(int[] var0) {
      f = var0;
   }

   public static int[] a() {
      return f;
   }

   static {
      if(a() == null) {
         b(new int[5]);
      }

   }
}
