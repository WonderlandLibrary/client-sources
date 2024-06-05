package cc.novoline.modules.configurations.property.object;

import cc.novoline.modules.configurations.property.AbstractProperty;
import cc.novoline.modules.configurations.property.exception.UnacceptableValueException;
import cc.novoline.modules.configurations.property.object.IntProperty;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.Collection;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class StringProperty extends AbstractProperty {
   private List acceptableValues;

   public StringProperty(@Nullable String var1) {
      super(var1);
   }

   public StringProperty() {
      this("");
   }

   @NotNull
   public static StringProperty of(@Nullable String var0) {
      return new StringProperty(var0);
   }

   @NotNull
   public static StringProperty create() {
      return new StringProperty();
   }

   public void set(@Nullable String var1) {
      int[] var2 = IntProperty.a();
      if(this.inLimits(var1)) {
         super.set(var1);
      }

      throw new UnacceptableValueException("Unable to set " + var1 + " as it\'s unacceptable value: " + var1, this);
   }

   private boolean inLimits(@NotNull String var1) {
      int[] var2 = IntProperty.a();
      return this.acceptableValues != null && !this.acceptableValues.isEmpty()?this.acceptableValues.contains(var1):true;
   }

   public boolean equals(@Nullable String var1) {
      int[] var2 = IntProperty.a();
      return var1 == null?this.value == null:var1.equals(this.value);
   }

   public boolean equalsIgnoreCase(@Nullable String var1) {
      int[] var2 = IntProperty.a();
      return var1 == null?this.value == null:var1.equalsIgnoreCase((String)this.value);
   }

   public StringProperty append(@Nullable Object var1) {
      this.set((String)this.get() + var1);
      return this;
   }

   public StringProperty acceptableValues(@NotNull Collection var1) {
      this.acceptableValues = new ObjectArrayList(var1);
      return this;
   }

   public final StringProperty acceptableValues(@NotNull String... var1) {
      this.acceptableValues = new ObjectArrayList(var1);
      return this;
   }

   public List getAcceptableValues() {
      return this.acceptableValues;
   }

   private static UnacceptableValueException a(UnacceptableValueException var0) {
      return var0;
   }
}
