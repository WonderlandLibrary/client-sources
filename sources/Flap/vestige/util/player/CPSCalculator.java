package vestige.util.player;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import org.jetbrains.annotations.NotNull;
import vestige.event.Listener;

public class CPSCalculator {
   private static final Minecraft mc = Minecraft.getMinecraft();
   private static final List<Long> a = new ArrayList();
   private static final List<Long> b = new ArrayList();
   public static long LL = 0L;
   public static long LR = 0L;

   @Listener
   public void onMouseUpdate(@NotNull MouseEvent d) {
      if (d == null) {
         $$$reportNull$$$0(0);
      }

      if (d.getButton() == 0) {
         aL();
         if (mc.objectMouseOver != null) {
            Entity en = mc.objectMouseOver.entityHit;
            if (en == null) {
               return;
            }
         }
      } else if (d.getButton() == 1) {
         aR();
      }

   }

   public static void aL() {
      a.add(LL = System.currentTimeMillis());
   }

   public static void aR() {
      b.add(LR = System.currentTimeMillis());
   }

   public static int f() {
      a.removeIf((o) -> {
         return o < System.currentTimeMillis() - 1000L;
      });
      return a.size();
   }

   public static int i() {
      b.removeIf((o) -> {
         return o < System.currentTimeMillis() - 1000L;
      });
      return b.size();
   }

   // $FF: synthetic method
   private static void $$$reportNull$$$0(int var0) {
      throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "d", "vestige/util/player/CPSCalculator", "onMouseUpdate"));
   }
}
