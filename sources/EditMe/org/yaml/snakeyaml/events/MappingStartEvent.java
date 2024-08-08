package org.yaml.snakeyaml.events;

import org.yaml.snakeyaml.error.Mark;

public final class MappingStartEvent extends CollectionStartEvent {
   public MappingStartEvent(String var1, String var2, boolean var3, Mark var4, Mark var5, Boolean var6) {
      super(var1, var2, var3, var4, var5, var6);
   }

   public boolean is(Event.ID var1) {
      return Event.ID.MappingStart == var1;
   }
}
