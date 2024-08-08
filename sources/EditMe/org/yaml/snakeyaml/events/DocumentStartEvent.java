package org.yaml.snakeyaml.events;

import java.util.Map;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.error.Mark;

public final class DocumentStartEvent extends Event {
   private final boolean explicit;
   private final DumperOptions.Version version;
   private final Map tags;

   public DocumentStartEvent(Mark var1, Mark var2, boolean var3, DumperOptions.Version var4, Map var5) {
      super(var1, var2);
      this.explicit = var3;
      this.version = var4;
      this.tags = var5;
   }

   public boolean getExplicit() {
      return this.explicit;
   }

   public DumperOptions.Version getVersion() {
      return this.version;
   }

   public Map getTags() {
      return this.tags;
   }

   public boolean is(Event.ID var1) {
      return Event.ID.DocumentStart == var1;
   }
}
