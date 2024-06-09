package exhibition.module.impl.render;

import exhibition.event.Event;
import exhibition.event.EventListener;
import exhibition.event.RegisterEvent;
import exhibition.event.impl.EventRenderGui;
import exhibition.module.Module;
import exhibition.module.data.ModuleData;
import exhibition.module.impl.combat.Killaura;
import java.util.Iterator;
import net.minecraft.entity.player.EntityPlayer;

public class TargetESP extends Module {
   public TargetESP(ModuleData data) {
      super(data);
   }

   public EventListener.Priority getPriority() {
      return EventListener.Priority.HIGHEST;
   }

   @RegisterEvent(
      events = {EventRenderGui.class}
   )
   public void onEvent(Event event) {
      EntityPlayer var3;
      for(Iterator var2 = mc.theWorld.playerEntities.iterator(); var2.hasNext(); var3 = (EntityPlayer)var2.next()) {
         ;
      }

   }

   public static boolean isPriority(EntityPlayer player) {
      if (player.equals(Killaura.vip)) {
         return true;
      } else {
         return player.getDisplayName().getFormattedText().contains(" §6§l");
      }
   }
}
