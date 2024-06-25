package cc.slack.features.modules.impl.player;

import cc.slack.events.impl.network.PacketEvent;
import cc.slack.events.impl.player.MotionEvent;
import cc.slack.events.impl.player.MoveEvent;
import cc.slack.events.impl.player.UpdateEvent;
import cc.slack.features.modules.api.Category;
import cc.slack.features.modules.api.Module;
import cc.slack.features.modules.api.ModuleInfo;
import cc.slack.features.modules.api.settings.Value;
import cc.slack.features.modules.api.settings.impl.ModeValue;
import cc.slack.features.modules.impl.player.nofalls.INoFall;
import cc.slack.features.modules.impl.player.nofalls.basics.NoGroundNofall;
import cc.slack.features.modules.impl.player.nofalls.basics.SpoofGroundNofall;
import cc.slack.features.modules.impl.player.nofalls.basics.VanillaNofall;
import cc.slack.features.modules.impl.player.nofalls.specials.HypixelBlinkNofall;
import cc.slack.features.modules.impl.player.nofalls.specials.VerusNofall;
import cc.slack.features.modules.impl.player.nofalls.specials.VulcanNofall;
import cc.slack.utils.client.mc;
import io.github.nevalackin.radbus.Listen;

@ModuleInfo(
   name = "NoFall",
   category = Category.PLAYER
)
public class NoFall extends Module {
   private final ModeValue<INoFall> mode = new ModeValue(new INoFall[]{new VanillaNofall(), new SpoofGroundNofall(), new NoGroundNofall(), new HypixelBlinkNofall(), new VulcanNofall(), new VerusNofall()});

   public NoFall() {
      this.addSettings(new Value[]{this.mode});
   }

   public void onEnable() {
      ((INoFall)this.mode.getValue()).onEnable();
   }

   public void onDisable() {
      ((INoFall)this.mode.getValue()).onDisable();
   }

   @Listen
   public void onMove(MoveEvent event) {
      ((INoFall)this.mode.getValue()).onMove(event);
   }

   @Listen
   public void onUpdate(UpdateEvent event) {
      if (!mc.getPlayer().isSpectator() && !mc.getPlayer().capabilities.allowFlying && !mc.getPlayer().capabilities.disableDamage) {
         ((INoFall)this.mode.getValue()).onUpdate(event);
      }
   }

   @Listen
   public void onMotion(MotionEvent event) {
      ((INoFall)this.mode.getValue()).onMotion(event);
   }

   @Listen
   public void onPacket(PacketEvent event) {
      ((INoFall)this.mode.getValue()).onPacket(event);
   }
}
