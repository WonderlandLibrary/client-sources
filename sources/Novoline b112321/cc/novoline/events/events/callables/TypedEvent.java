package cc.novoline.events.events.callables;

import cc.novoline.events.events.Event;
import cc.novoline.events.events.Typed;
import cc.novoline.events.events.callables.CancellableEvent;

public abstract class TypedEvent implements Event, Typed {
   private final byte type;

   protected TypedEvent(byte var1) {
      CancellableEvent.b();
      super();
      this.type = var1;
   }

   public byte getType() {
      return this.type;
   }
}
