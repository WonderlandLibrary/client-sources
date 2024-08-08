package org.yaml.snakeyaml.events;

import org.yaml.snakeyaml.error.Mark;

public final class DocumentEndEvent extends Event {
   private final boolean explicit;

   public DocumentEndEvent(Mark var1, Mark var2, boolean var3) {
      super(var1, var2);
      this.explicit = var3;
   }

   public boolean getExplicit() {
      return this.explicit;
   }

   public boolean is(Event.ID var1) {
      return Event.ID.DocumentEnd == var1;
   }
}
