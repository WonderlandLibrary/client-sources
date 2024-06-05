package cc.novoline.modules.configurations.property.object;

import cc.novoline.modules.configurations.property.AbstractProperty;
import cc.novoline.modules.configurations.property.object.BooleanProperty$ImmutableBooleanProperty;
import cc.novoline.modules.configurations.property.object.IntProperty;
import cc.novoline.utils.java.Lazy;
import java.util.function.Supplier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BooleanProperty extends AbstractProperty {
   private static final Lazy TRUE = Lazy.createThreadSafe(BooleanProperty::lambda$static$0);
   private static final Lazy FALSE = Lazy.createThreadSafe(BooleanProperty::lambda$static$1);

   public BooleanProperty(@Nullable Boolean var1) {
      super(Boolean.valueOf(var1.booleanValue()));
   }

   public BooleanProperty() {
      this(Boolean.valueOf(false));
   }

   @NotNull
   public static BooleanProperty of(@Nullable Boolean var0) {
      return new BooleanProperty(var0);
   }

   @NotNull
   public static BooleanProperty create() {
      return new BooleanProperty();
   }

   public static BooleanProperty alwaysTrue() {
      return (BooleanProperty)TRUE.get();
   }

   public static BooleanProperty alwaysFalse() {
      return (BooleanProperty)FALSE.get();
   }

   public void setTrue() {
      this.set(Boolean.valueOf(true));
   }

   public void setFalse() {
      this.set(Boolean.valueOf(false));
   }

   public void invert() {
      int[] var1 = IntProperty.a();
      this.value = Boolean.valueOf(this.value == null || !((Boolean)this.value).booleanValue());
   }

   private static BooleanProperty lambda$static$1() {
      return new BooleanProperty$ImmutableBooleanProperty(Boolean.valueOf(false));
   }

   private static BooleanProperty lambda$static$0() {
      return new BooleanProperty$ImmutableBooleanProperty(Boolean.valueOf(true));
   }
}
