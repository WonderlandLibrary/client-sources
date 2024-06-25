package cc.slack.features.modules.impl.movement;

import cc.slack.events.impl.network.PacketEvent;
import cc.slack.events.impl.player.CollideEvent;
import cc.slack.events.impl.player.MotionEvent;
import cc.slack.events.impl.player.MoveEvent;
import cc.slack.events.impl.player.UpdateEvent;
import cc.slack.features.modules.api.Category;
import cc.slack.features.modules.api.Module;
import cc.slack.features.modules.api.ModuleInfo;
import cc.slack.features.modules.api.settings.Value;
import cc.slack.features.modules.api.settings.impl.ModeValue;
import cc.slack.features.modules.impl.movement.flights.IFlight;
import cc.slack.features.modules.impl.movement.flights.impl.AirJumpFlight;
import cc.slack.features.modules.impl.movement.flights.impl.ChunkFlight;
import cc.slack.features.modules.impl.movement.flights.impl.CollideFlight;
import cc.slack.features.modules.impl.movement.flights.impl.VanillaFlight;
import cc.slack.features.modules.impl.movement.flights.impl.VerusFlight;
import cc.slack.features.modules.impl.movement.flights.impl.VerusJumpFlight;
import io.github.nevalackin.radbus.Listen;

@ModuleInfo(
   name = "Flight",
   category = Category.MOVEMENT,
   key = 33
)
public class Flight extends Module {
   private final ModeValue<IFlight> mode = new ModeValue(new IFlight[]{new VanillaFlight(), new VerusJumpFlight(), new VerusFlight(), new ChunkFlight(), new CollideFlight(), new AirJumpFlight()});

   public Flight() {
      this.addSettings(new Value[]{this.mode});
   }

   public void onEnable() {
      ((IFlight)this.mode.getValue()).onEnable();
   }

   public void onDisable() {
      ((IFlight)this.mode.getValue()).onDisable();
   }

   @Listen
   public void onMove(MoveEvent event) {
      ((IFlight)this.mode.getValue()).onMove(event);
   }

   @Listen
   public void onUpdate(UpdateEvent event) {
      ((IFlight)this.mode.getValue()).onUpdate(event);
   }

   @Listen
   public void onPacket(PacketEvent event) {
      ((IFlight)this.mode.getValue()).onPacket(event);
   }

   @Listen
   public void onCollide(CollideEvent event) {
      ((IFlight)this.mode.getValue()).onCollide(event);
   }

   @Listen
   public void onMotion(MotionEvent event) {
      ((IFlight)this.mode.getValue()).onMotion(event);
   }
}
