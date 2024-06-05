package cc.novoline.modules.visual;

import cc.novoline.events.EventTarget;
import cc.novoline.events.events.PacketDirection;
import cc.novoline.events.events.PacketEvent;
import cc.novoline.modules.AbstractModule;
import cc.novoline.modules.EnumModuleType;
import cc.novoline.modules.ModuleManager;
import cc.novoline.modules.visual.HUD;
import net.minecraft.network.play.server.S45PacketTitle;
import org.jetbrains.annotations.NotNull;

public class NoTitle extends AbstractModule {
   public NoTitle(@NotNull ModuleManager var1) {
      super(var1, EnumModuleType.VISUALS, "NoTitle", "No Title");
   }

   @EventTarget
   public void b(PacketEvent var1) {
      int var2 = HUD.h();
      if(var1.getDirection().equals(PacketDirection.INCOMING) && var1.getPacket() instanceof S45PacketTitle) {
         var1.setCancelled(true);
      }

   }
}
