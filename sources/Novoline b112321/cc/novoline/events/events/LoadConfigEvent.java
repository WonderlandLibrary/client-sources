package cc.novoline.events.events;

import cc.novoline.events.events.Event;
import cc.novoline.modules.configurations.property.AbstractProperty;

public class LoadConfigEvent implements Event {
   private final AbstractProperty property;
   private final String value;

   public LoadConfigEvent(AbstractProperty var1, String var2) {
      this.property = var1;
      this.value = var2;
   }

   public String getValue() {
      return this.value;
   }

   public AbstractProperty getProperty() {
      return this.property;
   }
}
