package com.example.editme.modules.movement;

import com.example.editme.modules.Module;
import java.util.function.Predicate;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.util.MovementInput;
import net.minecraftforge.client.event.InputUpdateEvent;

@Module.Info(
   name = "NoSlowDown",
   category = Module.Category.MOVEMENT
)
public class NoSlowDown extends Module {
   @EventHandler
   private Listener eventListener = new Listener(NoSlowDown::lambda$new$0, new Predicate[0]);

   private static void lambda$new$0(InputUpdateEvent var0) {
      if (mc.field_71439_g.func_184587_cr() && !mc.field_71439_g.func_184218_aH()) {
         MovementInput var10000 = var0.getMovementInput();
         var10000.field_78902_a *= 5.0F;
         var10000 = var0.getMovementInput();
         var10000.field_192832_b *= 5.0F;
      }

   }
}
