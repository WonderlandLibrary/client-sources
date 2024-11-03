package xyz.cucumber.base.module.feat.combat;

import god.buddy.aot.BCompiler;
import java.util.LinkedList;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import xyz.cucumber.base.Client;
import xyz.cucumber.base.events.EventListener;
import xyz.cucumber.base.events.EventType;
import xyz.cucumber.base.events.ext.EventHit;
import xyz.cucumber.base.events.ext.EventMotion;
import xyz.cucumber.base.events.ext.EventSendPacket;
import xyz.cucumber.base.events.ext.EventWorldChange;
import xyz.cucumber.base.module.Category;
import xyz.cucumber.base.module.Mod;
import xyz.cucumber.base.module.ModuleInfo;
import xyz.cucumber.base.module.feat.player.ScaffoldModule;
import xyz.cucumber.base.module.settings.ModuleSettings;
import xyz.cucumber.base.module.settings.NumberSettings;
import xyz.cucumber.base.utils.Timer;
import xyz.cucumber.base.utils.game.EntityUtils;
import xyz.cucumber.base.utils.math.RotationUtils;

@ModuleInfo(
   category = Category.COMBAT,
   description = "Uses blink to give you reach",
   name = "Lag Range",
   key = 0
)
public class LagRangeModule extends Mod {
   public LinkedList<Packet> outPackets = new LinkedList<>();
   public Timer timer = new Timer();
   public KillAuraModule killAura;
   public NumberSettings pulseDelay = new NumberSettings("pulse Delay", 200.0, 10.0, 500.0, 10.0);

   public LagRangeModule() {
      this.addSettings(new ModuleSettings[]{this.pulseDelay});
   }

   @Override
   public void onEnable() {
      this.killAura = (KillAuraModule)Client.INSTANCE.getModuleManager().getModule(KillAuraModule.class);
   }

   @Override
   public void onDisable() {
      this.fullRelease();
   }

   @EventListener
   public void onWorldChange(EventWorldChange e) {
   }

   @EventListener
   @BCompiler(
      aot = BCompiler.AOT.AGGRESSIVE
   )
   public void onMotion(EventMotion e) {
      this.setInfo(String.valueOf(this.pulseDelay.getValue()));
      if (e.getType() == EventType.PRE) {
         if (this.timer.hasTimeElapsed(this.pulseDelay.getValue(), false)) {
            this.fullRelease();
         }

         if (this.mc.thePlayer.hurtTime > 0) {
            this.fullRelease();
         }
      }
   }

   @EventListener
   @BCompiler(
      aot = BCompiler.AOT.AGGRESSIVE
   )
   public void onHit(EventHit e) {
      if (this.isHurtTime()) {
         e.setForced(true);
         this.fullRelease();
      }
   }

   @EventListener
   public void onSendPacket(EventSendPacket e) {
      if (this.shouldCancel() && !e.isCancelled()) {
         e.setCancelled(true);
         this.outPackets.add(e.getPacket());
      }
   }

   public void smartRelease() {
      if (!this.mc.isSingleplayer()) {
         try {
            while (this.outPackets.size() > 0) {
               Packet packet = this.outPackets.poll();
               this.mc.getNetHandler().getNetworkManager().sendPacketNoEvent(packet);
               if (packet instanceof C03PacketPlayer) {
                  C03PacketPlayer c03 = (C03PacketPlayer)packet;
                  if (this.isTargetCloseOrVisible() && EntityUtils.getDistanceToEntityBoxFromPosition(c03.x, c03.y, c03.z, this.killAura.target) <= 3.0) {
                     this.mc.thePlayer.setPosition(c03.x, c03.y, c03.z);
                     this.outPackets.clear();
                     this.timer.reset();
                  }
               }
            }
         } catch (Exception var3) {
         }

         this.outPackets.clear();
         this.timer.reset();
      }
   }

   public void fullRelease() {
      if (!this.mc.isSingleplayer()) {
         try {
            while (this.outPackets.size() > 0) {
               this.mc.getNetHandler().getNetworkManager().sendPacketNoEvent(this.outPackets.poll());
            }
         } catch (Exception var2) {
         }

         this.outPackets.clear();
         this.timer.reset();
      }
   }

   public boolean shouldCancel() {
      if (this.killAura.isEnabled() && !Client.INSTANCE.getModuleManager().getModule(ScaffoldModule.class).isEnabled()) {
         return true;
      } else {
         this.fullRelease();
         return false;
      }
   }

   public boolean isTargetCloseOrVisible() {
      Entity rayTracedEntity = RotationUtils.rayTrace(3.0, new float[]{RotationUtils.serverYaw, RotationUtils.serverPitch});
      return this.killAura.target == null
         ? false
         : rayTracedEntity == this.killAura.target
            || this.mc.objectMouseOver.entityHit == this.killAura.target
            || EntityUtils.getDistanceToEntityBox(this.killAura.target) <= 3.0;
   }

   public boolean isHurtTime() {
      return this.killAura.target.hurtTime <= 2;
   }
}
