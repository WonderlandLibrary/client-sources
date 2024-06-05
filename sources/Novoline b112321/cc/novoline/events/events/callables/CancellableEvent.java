package cc.novoline.events.events.callables;

import cc.novoline.events.events.Cancellable;
import cc.novoline.events.events.Event;
import net.aSv;
import net.acE;

public abstract class CancellableEvent implements Event, Cancellable {
   private boolean cancelled;
   private static acE[] b;

   @aSv
   public boolean isCancelled() {
      return this.cancelled;
   }

   @aSv
   public void setCancelled(boolean var1) {
      this.cancelled = var1;
   }

   public static void b(acE[] var0) {
      b = var0;
   }

   public static acE[] b() {
      return b;
   }

   static {
      if(b() == null) {
         b(new acE[1]);
      }

   }
}
