package xyz.cucumber.base.module.feat.movement;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.network.Packet;
import xyz.cucumber.base.Client;
import xyz.cucumber.base.events.EventListener;
import xyz.cucumber.base.events.ext.EventGameLoop;
import xyz.cucumber.base.events.ext.EventReceivePacket;
import xyz.cucumber.base.events.ext.EventSendPacket;
import xyz.cucumber.base.events.ext.EventWorldChange;
import xyz.cucumber.base.module.Category;
import xyz.cucumber.base.module.Mod;
import xyz.cucumber.base.module.ModuleInfo;
import xyz.cucumber.base.module.feat.player.ScaffoldModule;
import xyz.cucumber.base.utils.Timer;

@ModuleInfo(
   category = Category.MOVEMENT,
   description = "Lags your connection",
   name = "Blink"
)
public class BlinkModule extends Mod {
   private Timer timer = new Timer();
   private List<Packet> inPackets = new ArrayList<>();
   private List<Packet> outPackets = new ArrayList<>();

   @Override
   public void onDisable() {
      for (Packet p : this.inPackets) {
         p.processPacket(this.mc.getNetHandler().getNetworkManager().getNetHandler());
      }

      this.inPackets.clear();

      for (Packet p : this.outPackets) {
         this.mc.getNetHandler().getNetworkManager().sendPacketNoEvent(p);
      }

      this.inPackets.clear();
      this.outPackets.clear();
   }

   @EventListener
   public void onWorldChange(EventWorldChange e) {
      this.inPackets.clear();
      this.outPackets.clear();
      if (Client.INSTANCE.getModuleManager().getModule(ScaffoldModule.class).isEnabled()) {
         Client.INSTANCE.getModuleManager().getModule(ScaffoldModule.class).toggle();
      }

      this.toggle();
   }

   @EventListener
   public void onSendPacket(EventSendPacket e) {
      e.setCancelled(true);
      this.outPackets.add(e.getPacket());
   }

   @EventListener
   public void onReceivePacket(EventReceivePacket e) {
      e.setCancelled(true);
      this.inPackets.add(e.getPacket());
   }

   @EventListener
   public void onGameLoop(EventGameLoop e) {
      if (this.timer.hasTimeElapsed(500.0, true)) {
         for (Packet p : this.inPackets) {
            p.processPacket(this.mc.getNetHandler().getNetworkManager().getNetHandler());
         }

         this.inPackets.clear();

         for (Packet p : this.outPackets) {
            this.mc.getNetHandler().getNetworkManager().sendPacketNoEvent(p);
         }

         this.outPackets.clear();
      }
   }
}
