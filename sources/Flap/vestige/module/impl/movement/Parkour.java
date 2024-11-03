package vestige.module.impl.movement;

import vestige.event.Listener;
import vestige.event.impl.PreMotionEvent;
import vestige.module.Category;
import vestige.module.Module;

public class Parkour extends Module {
   public boolean prevOffGround;

   public Parkour() {
      super("Parkour", Category.MOVEMENT);
   }

   @Listener
   public void onPremotion(PreMotionEvent event) {


   }
}
