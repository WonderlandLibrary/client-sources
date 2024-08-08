package org.yaml.snakeyaml.events;

import org.yaml.snakeyaml.error.Mark;

public final class StreamStartEvent extends Event {
   public StreamStartEvent(Mark var1, Mark var2) {
      super(var1, var2);
   }

   public boolean is(Event.ID var1) {
      return Event.ID.StreamStart == var1;
   }
}
