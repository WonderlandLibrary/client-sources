package net.skidunion;

import java.util.function.Consumer;
import kotlin.Metadata;
import net.skidunion.W;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 3
)
public final class aq {
   public static void a(W var0) {
      var0.a((Consumer)null, (Consumer)null);
   }

   public static void a(W var0, @Nullable Consumer var1) {
      var0.a(var1, (Consumer)null);
   }
}
