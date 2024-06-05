package cc.novoline.events.events;

import cc.novoline.events.events.Event;
import cc.novoline.modules.AbstractModule;

public class BindEvent implements Event {
   private final int keyCode;
   private final String keyName;
   private final AbstractModule module;

   public BindEvent(AbstractModule var1, int var2, String var3) {
      this.module = var1;
      this.keyCode = var2;
      this.keyName = var3;
   }

   public String getKeyName() {
      return this.keyName;
   }

   public int getKeyCode() {
      return this.keyCode;
   }

   public AbstractModule getModule() {
      return this.module;
   }
}
