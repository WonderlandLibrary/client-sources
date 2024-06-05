package hawk.modules.combat;

import hawk.events.Event;
import hawk.events.listeners.EventUpdate;
import hawk.modules.Module;
import java.util.Iterator;
import net.minecraft.entity.Entity;

public class Antibot extends Module {
   public void onEvent(Event var1) {
      if (var1 instanceof EventUpdate) {
         Iterator var3 = this.mc.theWorld.loadedEntityList.iterator();

         while(var3.hasNext()) {
            Object var2 = var3.next();
            if (((Entity)var2).isInvisible() && var2 != this.mc.thePlayer) {
               this.mc.theWorld.removeEntity((Entity)var2);
            }
         }
      }

   }

   public void onEnable() {
   }

   public Antibot() {
      super("AntiBot", 0, Module.Category.COMBAT);
   }

   public void onDisable() {
   }
}
