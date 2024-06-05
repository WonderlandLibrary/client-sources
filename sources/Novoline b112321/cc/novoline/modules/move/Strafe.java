package cc.novoline.modules.move;

import cc.novoline.events.EventTarget;
import cc.novoline.events.events.MoveEvent;
import cc.novoline.modules.AbstractModule;
import cc.novoline.modules.EnumModuleType;
import cc.novoline.modules.ModuleManager;
import cc.novoline.modules.move.Scaffold;
import org.jetbrains.annotations.NotNull;

public class Strafe extends AbstractModule {
   public Strafe(@NotNull ModuleManager var1) {
      super(var1, EnumModuleType.MOVEMENT, "Strafe");
   }

   @EventTarget
   public void a(MoveEvent var1) {
      String var2 = Scaffold.r();
      if(this.mc.player.movementInput().jump() || !this.mc.player.onGround) {
         var1.setMoveSpeed(this.mc.player.getBaseMoveSpeed() * 0.98D);
      }

   }
}
