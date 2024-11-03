package xyz.cucumber.base.module.feat.player;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C00PacketKeepAlive;
import net.minecraft.network.play.server.S00PacketKeepAlive;
import net.minecraft.network.play.server.S03PacketTimeUpdate;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S27PacketExplosion;
import net.minecraft.network.play.server.S32PacketConfirmTransaction;
import xyz.cucumber.base.events.EventListener;
import xyz.cucumber.base.events.ext.EventGameLoop;
import xyz.cucumber.base.events.ext.EventReceivePacket;
import xyz.cucumber.base.events.ext.EventSendPacket;
import xyz.cucumber.base.events.ext.EventWorldChange;
import xyz.cucumber.base.module.Category;
import xyz.cucumber.base.module.Mod;
import xyz.cucumber.base.module.ModuleInfo;
import xyz.cucumber.base.module.settings.BooleanSettings;
import xyz.cucumber.base.module.settings.ModuleSettings;
import xyz.cucumber.base.module.settings.NumberSettings;

@ModuleInfo(
   category = Category.PLAYER,
   description = "Automatically spoofs your ping",
   name = "Ping Spoof"
)
public class PingSpoofModule extends Mod {
   private ConcurrentHashMap<Packet<?>, Long> outgoing = new ConcurrentHashMap<>();
   private ConcurrentHashMap<Packet<?>, Long> incoming = new ConcurrentHashMap<>();
   public NumberSettings delay = new NumberSettings("Delay", 90.0, 0.0, 1000.0, 10.0);
   public BooleanSettings keepAlive = new BooleanSettings("Keep alive packet", true);
   public BooleanSettings c0fPacket = new BooleanSettings("C0F packet", true);
   public BooleanSettings allPackets = new BooleanSettings("All packets", true);

   public PingSpoofModule() {
      this.addSettings(new ModuleSettings[]{this.delay, this.keepAlive, this.c0fPacket, this.allPackets});
   }

   @EventListener
   public void onSendPacket(EventSendPacket e) {
      if (e.getPacket() instanceof C00PacketKeepAlive && this.keepAlive.isEnabled()) {
         this.outgoing.put(e.getPacket(), (long)((double)System.currentTimeMillis() + this.delay.getValue()));
         e.setCancelled(true);
      }
   }

   @EventListener
   public void onWorldChange(EventWorldChange e) {
      this.incoming.clear();
      this.outgoing.clear();
   }

   @EventListener
   public void onReceivePacket(EventReceivePacket e) {
   }

   @EventListener
   public void onGameLoop(EventGameLoop e) {
      if (!this.mc.isSingleplayer()) {
         Iterator<Entry<Packet<?>, Long>> iterator = this.incoming.entrySet().iterator();

         while (iterator.hasNext()) {
            Entry<Packet<?>, Long> entry = iterator.next();
            if (entry.getValue() < System.currentTimeMillis()) {
               ((Packet<INetHandler>)entry.getKey()).processPacket(this.mc.getNetHandler().getNetworkManager().getNetHandler());
               iterator.remove();
            }
         }

         iterator = this.outgoing.entrySet().iterator();

         while (iterator.hasNext()) {
            Entry<Packet<?>, Long> entry = iterator.next();
            if (entry.getValue() < System.currentTimeMillis()) {
               this.mc.getNetHandler().getNetworkManager().sendPacketNoEvent(entry.getKey());
               iterator.remove();
            }
         }
      }
   }

   private boolean blockPacket(Packet packet) {
      if (packet instanceof S03PacketTimeUpdate) {
         return true;
      } else if (packet instanceof S00PacketKeepAlive) {
         return true;
      } else if (packet instanceof S12PacketEntityVelocity) {
         return true;
      } else {
         return packet instanceof S27PacketExplosion ? true : packet instanceof S32PacketConfirmTransaction;
      }
   }
}
