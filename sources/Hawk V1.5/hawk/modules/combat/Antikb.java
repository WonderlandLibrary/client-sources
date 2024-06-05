package hawk.modules.combat;

import hawk.events.Event;
import hawk.events.listeners.EventPacket;
import hawk.modules.Module;
import hawk.settings.BooleanSetting;
import hawk.settings.NumberSetting;
import hawk.settings.Setting;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S27PacketExplosion;

public class Antikb extends Module {
   public NumberSetting verticalKnockback = new NumberSetting("Vertical Knockback", 0.0D, -100.0D, 100.0D, 1.0D, this);
   public BooleanSetting hypixel = new BooleanSetting("Hypixel", false, this);
   public NumberSetting horizontalKnockback = new NumberSetting("Horizontal Knockback", 0.0D, -100.0D, 100.0D, 1.0D, this);

   public void onEvent(Event var1) {
      if (var1 instanceof EventPacket && var1.isIncoming()) {
         EventPacket var2 = (EventPacket)var1;
         if (EventPacket.packet instanceof S12PacketEntityVelocity) {
            S12PacketEntityVelocity var3 = (S12PacketEntityVelocity)EventPacket.packet;
            if (this.hypixel.isEnabled()) {
               var3.setMotionX((int)((double)(var3.func_149411_d() / 100) * this.horizontalKnockback.getValue()));
               var3.setMotionY((int)((double)(var3.func_149410_e() / 100) * this.verticalKnockback.getValue()));
               var3.setMotionZ((int)((double)(var3.func_149409_f() / 100) * this.horizontalKnockback.getValue()));
            } else {
               var1.setCancelled(true);
            }
         } else if (EventPacket.packet instanceof S27PacketExplosion) {
            var1.setCancelled(true);
         }
      }

   }

   public void onDisable() {
   }

   public Antikb() {
      super("Velocity", 0, Module.Category.COMBAT);
      this.addSettings(new Setting[]{this.hypixel});
   }

   public void onEnable() {
   }
}
