package rina.turok.bope.bopemod.hacks.movement;

import java.util.function.Predicate;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.util.MovementInput;
import net.minecraftforge.client.event.InputUpdateEvent;
import rina.turok.bope.bopemod.BopeModule;
import rina.turok.bope.bopemod.hacks.BopeCategory;

public class BopeNoSlowDown extends BopeModule {
   @EventHandler
   private Listener<InputUpdateEvent> listener = new Listener<>(event -> {
      if (this.mc.player.isHandActive() && !this.mc.player.isRiding()) {
         MovementInput var10000 = event.getMovementInput();
         var10000.moveStrafe *= 5.0F;
         var10000 = event.getMovementInput();
         var10000.moveForward *= 5.0F;
      }

   });

   public BopeNoSlowDown() {
      super(BopeCategory.BOPE_MOVEMENT);
      this.name = "No Slow Down";
      this.tag = "NoSlowDown";
      this.description = "Is able to modify the event eat.";
      this.release("B.O.P.E - Module - B.O.P.E");
   }
}
