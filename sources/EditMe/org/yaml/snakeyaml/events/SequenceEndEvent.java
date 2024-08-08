package org.yaml.snakeyaml.events;

import org.yaml.snakeyaml.error.Mark;

public final class SequenceEndEvent extends CollectionEndEvent {
   public SequenceEndEvent(Mark var1, Mark var2) {
      super(var1, var2);
   }

   public boolean is(Event.ID var1) {
      return Event.ID.SequenceEnd == var1;
   }
}
