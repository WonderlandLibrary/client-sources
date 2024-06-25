package cc.slack.features.modules.impl.player;

import cc.slack.events.impl.network.PacketEvent;
import cc.slack.events.impl.player.UpdateEvent;
import cc.slack.events.impl.render.RenderEvent;
import cc.slack.features.modules.api.Category;
import cc.slack.features.modules.api.Module;
import cc.slack.features.modules.api.ModuleInfo;
import cc.slack.features.modules.api.settings.Value;
import cc.slack.features.modules.api.settings.impl.ModeValue;
import cc.slack.features.modules.api.settings.impl.NumberValue;
import cc.slack.utils.client.mc;
import cc.slack.utils.other.PrintUtil;
import cc.slack.utils.other.TimeUtil;
import cc.slack.utils.player.TimerUtil;
import io.github.nevalackin.radbus.Listen;
import net.minecraft.network.PacketDirection;
import net.minecraft.network.play.client.C03PacketPlayer;

@ModuleInfo(
   name = "Timer",
   category = Category.PLAYER
)
public class TimerModule extends Module {
   private final ModeValue<String> mode = new ModeValue(new String[]{"Normal", "Balance"});
   private final NumberValue<Float> speed = new NumberValue("Speed", 1.5F, 0.1F, 10.0F, 0.25F);
   private int balance;
   public boolean balancing;
   private final TimeUtil timer = new TimeUtil();

   public TimerModule() {
      this.addSettings(new Value[]{this.mode, this.speed});
   }

   public void onEnable() {
      this.balance = 0;
      this.balancing = false;
      this.timer.reset();
   }

   public void onDisable() {
      TimerUtil.reset();
   }

   @Listen
   public void onUpdate(UpdateEvent event) {
      String var2 = ((String)this.mode.getValue()).toLowerCase();
      byte var3 = -1;
      switch(var2.hashCode()) {
      case -1039745817:
         if (var2.equals("normal")) {
            var3 = 1;
         }
         break;
      case -339185956:
         if (var2.equals("balance")) {
            var3 = 0;
         }
      }

      switch(var3) {
      case 0:
         if (mc.getPlayer() != null && mc.getPlayer().ticksExisted % 20 == 0) {
            PrintUtil.debugMessage("Balance: " + this.balance);
         }
         break;
      case 1:
         mc.getTimer().timerSpeed = (Float)this.speed.getValue();
      }

   }

   @Listen
   public void onPacket(PacketEvent event) {
      if (event.getDirection() == PacketDirection.INCOMING) {
         if (((String)this.mode.getValue()).equalsIgnoreCase("balance") && event.getPacket() instanceof C03PacketPlayer) {
            if (!(event.getPacket() instanceof C03PacketPlayer.C04PacketPlayerPosition) && !(event.getPacket() instanceof C03PacketPlayer.C05PacketPlayerLook) && !(event.getPacket() instanceof C03PacketPlayer.C06PacketPlayerPosLook)) {
               this.balancing = true;
               --this.balance;
               event.cancel();
            } else {
               this.balancing = false;
               ++this.balance;
            }
         }

      }
   }

   @Listen
   public void onRender(RenderEvent event) {
      if (event.state == RenderEvent.State.RENDER_2D) {
         if (((String)this.mode.getValue()).equalsIgnoreCase("balance") && this.timer.hasReached(50)) {
            --this.balance;
            this.timer.reset();
         }

      }
   }
}
