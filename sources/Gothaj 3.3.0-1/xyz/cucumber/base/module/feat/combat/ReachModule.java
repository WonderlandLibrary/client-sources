package xyz.cucumber.base.module.feat.combat;

import god.buddy.aot.BCompiler;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S27PacketExplosion;
import net.minecraft.network.play.server.S32PacketConfirmTransaction;
import xyz.cucumber.base.events.EventListener;
import xyz.cucumber.base.events.ext.EventReach;
import xyz.cucumber.base.module.ArrayPriority;
import xyz.cucumber.base.module.Category;
import xyz.cucumber.base.module.Mod;
import xyz.cucumber.base.module.ModuleInfo;
import xyz.cucumber.base.module.settings.ModuleSettings;
import xyz.cucumber.base.module.settings.NumberSettings;

@ModuleInfo(
   category = Category.COMBAT,
   description = "Allows you hit entity from longer distance",
   name = "Reach",
   key = 0,
   priority = ArrayPriority.HIGH
)
@BCompiler(
   aot = BCompiler.AOT.AGGRESSIVE
)
public class ReachModule extends Mod {
   public NumberSettings expand = new NumberSettings("Expand", 0.5, 0.01, 3.0, 0.01);
   private ConcurrentHashMap<Packet<?>, Long> packets = new ConcurrentHashMap<>();

   public ReachModule() {
      this.addSettings(new ModuleSettings[]{this.expand});
   }

   @EventListener
   public void onReach(EventReach e) {
      e.setRange(e.getRange() + this.expand.getValue());
   }

   private boolean blockPacket(Packet packet) {
      if (packet instanceof S12PacketEntityVelocity) {
         return true;
      } else if (packet instanceof S27PacketExplosion) {
         return true;
      } else {
         return packet instanceof S32PacketConfirmTransaction ? true : packet instanceof S08PacketPlayerPosLook;
      }
   }
}
