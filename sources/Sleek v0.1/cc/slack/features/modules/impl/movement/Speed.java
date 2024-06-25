package cc.slack.features.modules.impl.movement;

import cc.slack.events.impl.network.PacketEvent;
import cc.slack.events.impl.player.MoveEvent;
import cc.slack.events.impl.player.UpdateEvent;
import cc.slack.features.modules.api.Category;
import cc.slack.features.modules.api.Module;
import cc.slack.features.modules.api.ModuleInfo;
import cc.slack.features.modules.api.settings.Value;
import cc.slack.features.modules.api.settings.impl.BooleanValue;
import cc.slack.features.modules.api.settings.impl.ModeValue;
import cc.slack.features.modules.api.settings.impl.NumberValue;
import cc.slack.features.modules.impl.movement.speeds.ISpeed;
import cc.slack.features.modules.impl.movement.speeds.hop.HypixelHopSpeed;
import cc.slack.features.modules.impl.movement.speeds.hop.NCPHopSpeed;
import cc.slack.features.modules.impl.movement.speeds.hop.VerusHopSpeed;
import cc.slack.features.modules.impl.movement.speeds.hop.VulcanHopSpeed;
import cc.slack.features.modules.impl.movement.speeds.lowhops.VulcanLowSpeed;
import cc.slack.features.modules.impl.movement.speeds.vanilla.GroundStrafeSpeed;
import cc.slack.features.modules.impl.movement.speeds.vanilla.LegitSpeed;
import cc.slack.features.modules.impl.movement.speeds.vanilla.StrafeSpeed;
import cc.slack.features.modules.impl.movement.speeds.vanilla.VanillaSpeed;
import cc.slack.utils.client.mc;
import io.github.nevalackin.radbus.Listen;
import net.minecraft.client.settings.GameSettings;

@ModuleInfo(
   name = "Speed",
   category = Category.MOVEMENT,
   key = 48
)
public class Speed extends Module {
   private final ModeValue<ISpeed> mode = new ModeValue(new ISpeed[]{new VanillaSpeed(), new StrafeSpeed(), new GroundStrafeSpeed(), new LegitSpeed(), new VerusHopSpeed(), new HypixelHopSpeed(), new NCPHopSpeed(), new VulcanLowSpeed(), new VulcanHopSpeed()});
   public final NumberValue<Float> vanillaspeed = new NumberValue("Vanilla Speed", 1.0F, 0.0F, 3.0F, 0.01F);
   public final BooleanValue vanillaGround = new BooleanValue("Vanilla Only Ground", false);
   public final BooleanValue jumpFix = new BooleanValue("Jump Fix", true);

   public Speed() {
      this.addSettings(new Value[]{this.mode, this.vanillaspeed, this.vanillaGround, this.jumpFix});
   }

   public void onEnable() {
      ((ISpeed)this.mode.getValue()).onEnable();
   }

   public void onDisable() {
      if ((Boolean)this.jumpFix.getValue()) {
         mc.getGameSettings().keyBindJump.pressed = GameSettings.isKeyDown(mc.getGameSettings().keyBindJump);
      }

      ((ISpeed)this.mode.getValue()).onDisable();
   }

   @Listen
   public void onMove(MoveEvent event) {
      ((ISpeed)this.mode.getValue()).onMove(event);
   }

   @Listen
   public void onUpdate(UpdateEvent event) {
      if ((Boolean)this.jumpFix.getValue()) {
         mc.getGameSettings().keyBindJump.pressed = false;
      }

      ((ISpeed)this.mode.getValue()).onUpdate(event);
   }

   @Listen
   public void onPacket(PacketEvent event) {
      ((ISpeed)this.mode.getValue()).onPacket(event);
   }
}
