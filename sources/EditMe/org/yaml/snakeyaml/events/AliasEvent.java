package org.yaml.snakeyaml.events;

import org.yaml.snakeyaml.error.Mark;

public final class AliasEvent extends NodeEvent {
   public AliasEvent(String var1, Mark var2, Mark var3) {
      super(var1, var2, var3);
   }

   public boolean is(Event.ID var1) {
      return Event.ID.Alias == var1;
   }
}
