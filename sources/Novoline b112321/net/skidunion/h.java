package net.skidunion;

import kotlin.Metadata;
import kotlin.jvm.internal.MutablePropertyReference0;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import net.skidunion.ac;
import org.jetbrains.annotations.Nullable;

// $FF: synthetic class
@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 3
)
final class h extends MutablePropertyReference0 {
   h(ac var1) {
      super(var1);
   }

   public String getName() {
      return "error";
   }

   public String getSignature() {
      return "e()Ljava/lang/Throwable;";
   }

   public KDeclarationContainer getOwner() {
      return Reflection.getOrCreateKotlinClass(ac.class);
   }

   @Nullable
   public Object get() {
      return ((ac)this.receiver).e();
   }

   public void set(@Nullable Object var1) {
      ((ac)this.receiver).a((Throwable)var1);
   }
}
