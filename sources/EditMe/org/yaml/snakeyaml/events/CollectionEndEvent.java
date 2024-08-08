package org.yaml.snakeyaml.events;

import org.yaml.snakeyaml.error.Mark;

public abstract class CollectionEndEvent extends Event {
   public CollectionEndEvent(Mark var1, Mark var2) {
      super(var1, var2);
   }
}
