package cc.novoline.modules.move;

import cc.novoline.events.EventTarget;
import cc.novoline.events.events.EventState;
import cc.novoline.events.events.MotionUpdateEvent;
import cc.novoline.modules.AbstractModule;
import cc.novoline.modules.EnumModuleType;
import cc.novoline.modules.ModuleManager;
import cc.novoline.modules.move.Scaffold;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.network.play.client.C0BPacketEntityAction$Action;

public class FastSneak extends AbstractModule {
   public FastSneak(ModuleManager var1) {
      super(var1, EnumModuleType.MOVEMENT, "FastSneak", "Fast Sneak");
   }

   @EventTarget
   public void onPre(MotionUpdateEvent var1) {
      String var2 = Scaffold.r();
      if(this.mc.player.movementInput().sneak() && this.mc.player.isMoving() && !this.isEnabled(Scaffold.class)) {
         if(var1.getState().equals(EventState.PRE)) {
            this.sendPacketNoEvent(new C0BPacketEntityAction(this.mc.player, C0BPacketEntityAction$Action.STOP_SNEAKING));
         }

         this.sendPacketNoEvent(new C0BPacketEntityAction(this.mc.player, C0BPacketEntityAction$Action.START_SNEAKING));
      }

   }
}
