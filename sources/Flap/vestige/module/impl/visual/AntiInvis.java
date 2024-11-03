package vestige.module.impl.visual;

import net.minecraft.potion.Potion;
import vestige.event.Listener;
import vestige.event.impl.MotionEvent;
import vestige.module.Category;
import vestige.module.Module;

public class AntiInvis extends Module {
   public AntiInvis() {
      super("AntiInvis", Category.VISUAL);
   }

   @Listener
   public void onMotion(MotionEvent e) {
      mc.theWorld.playerEntities.stream().forEach((player) -> {
         player.removePotionEffect(Potion.invisibility.getId());
         player.setInvisible(false);
      });
   }
}
