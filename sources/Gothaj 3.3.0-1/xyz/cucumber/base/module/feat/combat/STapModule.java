package xyz.cucumber.base.module.feat.combat;

import god.buddy.aot.BCompiler;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.C02PacketUseEntity;
import xyz.cucumber.base.events.EventListener;
import xyz.cucumber.base.events.ext.EventMoveButton;
import xyz.cucumber.base.events.ext.EventSendPacket;
import xyz.cucumber.base.events.ext.EventTick;
import xyz.cucumber.base.module.ArrayPriority;
import xyz.cucumber.base.module.Category;
import xyz.cucumber.base.module.Mod;
import xyz.cucumber.base.module.ModuleInfo;
import xyz.cucumber.base.module.settings.ModuleSettings;
import xyz.cucumber.base.module.settings.NumberSettings;
import xyz.cucumber.base.utils.Timer;

@ModuleInfo(
   category = Category.COMBAT,
   description = "Automatically goes backwards to dodge hits",
   name = "STap",
   key = 0,
   priority = ArrayPriority.HIGH
)
public class STapModule extends Mod {
   public Timer timer = new Timer();
   public Timer timer1 = new Timer();
   public NumberSettings holdTime = new NumberSettings("Hold Time", 100.0, 25.0, 350.0, 25.0);

   public STapModule() {
      this.addSettings(new ModuleSettings[]{this.holdTime});
   }

   @EventListener
   public void onSendPacket(EventSendPacket e) {
      if (e.getPacket() instanceof C02PacketUseEntity) {
         C02PacketUseEntity packet = (C02PacketUseEntity)e.getPacket();
         if (packet.getAction() == C02PacketUseEntity.Action.ATTACK) {
            Entity entity = packet.getEntityFromWorld(this.mc.theWorld);
            this.timer1.reset();
         }
      }
   }

   @EventListener
   @BCompiler(
      aot = BCompiler.AOT.AGGRESSIVE
   )
   public void onTick(EventTick e) {
      if (this.mc.thePlayer.hurtTime == 1) {
         this.timer.reset();
      }
   }

   @EventListener
   @BCompiler(
      aot = BCompiler.AOT.AGGRESSIVE
   )
   public void onMoveButton(EventMoveButton e) {
      if (this.mc.thePlayer.hurtTime == 0
         && this.mc.thePlayer.onGround
         && !this.mc.gameSettings.keyBindJump.pressed
         && !this.timer1.hasTimeElapsed(this.holdTime.getValue(), false)
         && this.timer.hasTimeElapsed(500.0, false)
         && this.mc.objectMouseOver.entityHit != null
         && this.mc.objectMouseOver.entityHit.hurtResistantTime > 0) {
         e.forward = false;
         e.backward = true;
      }
   }
}
