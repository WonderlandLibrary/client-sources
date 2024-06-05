package cc.novoline.events;

import cc.novoline.events.EventManager;
import java.lang.reflect.Method;

final class EventManager$MethodData {
   private final Object source;
   private final Method target;
   private final byte priority;

   public EventManager$MethodData(Object var1, Method var2, byte var3) {
      EventManager.b();
      super();
      this.source = var1;
      this.target = var2;
      this.priority = var3;
   }

   public Object getSource() {
      return this.source;
   }

   public Method getTarget() {
      return this.target;
   }

   public byte getPriority() {
      return this.priority;
   }
}
