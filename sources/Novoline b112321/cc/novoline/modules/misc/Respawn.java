package cc.novoline.modules.misc;

import cc.novoline.events.EventTarget;
import cc.novoline.events.events.EventState;
import cc.novoline.events.events.MotionUpdateEvent;
import cc.novoline.modules.AbstractModule;
import cc.novoline.modules.EnumModuleType;
import cc.novoline.modules.ModuleManager;
import cc.novoline.modules.misc.WindowedFullscreen;
import net.minecraft.client.gui.GuiGameOver;

public final class Respawn extends AbstractModule {
   public Respawn(ModuleManager var1) {
      super(var1, "Respawn", EnumModuleType.MISC, "Respawns you");
   }

   @EventTarget
   public void onUpdate(MotionUpdateEvent var1) {
      String[] var2 = WindowedFullscreen.a();
      if(var1.getState().equals(EventState.PRE) && this.mc.currentScreen instanceof GuiGameOver) {
         this.mc.player.respawnPlayer();
      }

   }
}
