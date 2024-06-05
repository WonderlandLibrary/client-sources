package cc.novoline.events;

import cc.novoline.events.EventManager$MethodData;
import java.util.concurrent.CopyOnWriteArrayList;

final class EventManager$1 extends CopyOnWriteArrayList {
   private static final long serialVersionUID = 666L;
   final EventManager$MethodData val$data;

   EventManager$1(EventManager$MethodData var1) {
      this.val$data = var1;
      this.add(this.val$data);
   }
}
