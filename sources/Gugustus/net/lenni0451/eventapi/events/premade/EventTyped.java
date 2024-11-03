package net.lenni0451.eventapi.events.premade;

import net.lenni0451.eventapi.events.IEvent;
import net.lenni0451.eventapi.events.types.ITyped;

public class EventTyped implements IEvent, ITyped {
   private final byte type;

   protected EventTyped(byte type) {
      this.type = type;
   }

   @Override
   public byte getType() {
      return this.type;
   }
}
