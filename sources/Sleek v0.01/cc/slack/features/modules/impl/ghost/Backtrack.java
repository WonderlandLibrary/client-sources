package cc.slack.features.modules.impl.ghost;

import cc.slack.events.impl.network.PacketEvent;
import cc.slack.events.impl.player.UpdateEvent;
import cc.slack.features.modules.api.Category;
import cc.slack.features.modules.api.Module;
import cc.slack.features.modules.api.ModuleInfo;
import cc.slack.features.modules.api.settings.Value;
import cc.slack.features.modules.api.settings.impl.NumberValue;
import cc.slack.utils.client.mc;
import io.github.nevalackin.radbus.Listen;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketDirection;
import net.minecraft.network.login.server.S00PacketDisconnect;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.network.play.server.S19PacketEntityStatus;
import net.minecraft.network.play.server.S3BPacketScoreboardObjective;
import net.minecraft.network.play.server.S3EPacketTeams;
import net.minecraft.network.status.server.S00PacketServerInfo;

@ModuleInfo(
   name = "Backtrack",
   category = Category.GHOST
)
public class Backtrack extends Module {
   private final NumberValue<Integer> maxDelay = new NumberValue("Max Delay", 4, 1, 40, 1);
   private final NumberValue<Integer> backtrackTime = new NumberValue("Backtrack ticks", 20, 10, 60, 1);
   private int ticksSinceAttack = 0;
   private int backtrackTicks = 0;
   private boolean enabled = false;
   public EntityPlayer player;
   List<List<Packet>> packetCache = new ArrayList();
   List<Packet> currentTick = new ArrayList();

   public Backtrack() {
      this.addSettings(new Value[]{this.maxDelay, this.backtrackTime});
   }

   public void onEnable() {
      this.ticksSinceAttack = 0;
      this.backtrackTicks = 0;
      this.enabled = false;
      this.packetCache.clear();
      this.currentTick.clear();
   }

   @Listen
   public void onUpdate(UpdateEvent event) {
      if (!this.currentTick.isEmpty()) {
         this.packetCache.add(this.currentTick);
         this.currentTick.clear();
      }

      if (this.enabled) {
         if (this.ticksSinceAttack < (Integer)this.maxDelay.getValue()) {
            ++this.ticksSinceAttack;
         } else {
            this.releaseFirst();
         }
      }

      if (this.backtrackTicks > 0) {
         --this.backtrackTicks;
      } else if (this.enabled) {
         int cacheSize = this.packetCache.size();

         for(int i = 0; i < cacheSize; ++i) {
            this.releaseFirst();
         }

         this.enabled = false;
         this.currentTick.clear();
         this.packetCache.clear();
      }

   }

   @Listen
   public void onPacket(PacketEvent event) {
      Packet packet = event.getPacket();
      if (event.getDirection() == PacketDirection.OUTGOING) {
         if (event.getPacket() instanceof C02PacketUseEntity) {
            C02PacketUseEntity wrapper = (C02PacketUseEntity)packet;
            if (wrapper.getEntityFromWorld(mc.getWorld()) instanceof EntityPlayer && wrapper.getAction() == C02PacketUseEntity.Action.ATTACK) {
               if (this.backtrackTicks == 0) {
                  this.ticksSinceAttack = 0;
               }

               this.backtrackTicks = (Integer)this.backtrackTime.getValue();
               this.enabled = true;
               this.player = (EntityPlayer)wrapper.getEntityFromWorld(mc.getWorld());
            }
         }
      } else if (!(packet instanceof S00PacketDisconnect) && !(packet instanceof S00PacketServerInfo) && !(packet instanceof S3EPacketTeams) && !(packet instanceof S19PacketEntityStatus) && !(packet instanceof S02PacketChat) && !(packet instanceof S3BPacketScoreboardObjective) && mc.getPlayer().ticksExisted > 4 && this.enabled) {
         this.currentTick.add(packet);
         event.cancel();
      }

   }

   private void releaseFirst() {
      if (!this.packetCache.isEmpty()) {
         Iterator var1 = ((List)this.packetCache.get(0)).iterator();

         while(var1.hasNext()) {
            Packet packet = (Packet)var1.next();

            try {
               packet.processPacket(mc.getMinecraft().getNetHandler().getNetworkManager().getNetHandler());
            } catch (Exception var4) {
            }
         }

         this.packetCache.remove(0);
      }
   }
}
