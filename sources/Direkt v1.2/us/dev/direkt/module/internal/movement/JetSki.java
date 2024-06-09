package us.dev.direkt.module.internal.movement;


import net.minecraft.world.gen.layer.GenLayerRemoveTooMuchOcean;
import us.dev.direkt.Direkt;
import us.dev.direkt.Wrapper;
import us.dev.direkt.event.internal.events.game.player.update.EventPreMotionUpdate;
import us.dev.direkt.module.ModCategory;
import us.dev.direkt.module.annotations.ModData;
import us.dev.direkt.module.ToggleableModule;
import us.dev.dvent.Listener;
import us.dev.dvent.Link;

@ModData(label = "Jet Ski", category = ModCategory.MOVEMENT)
public class JetSki extends ToggleableModule {

	private int fuckbitchesgetmoney;

	@Listener
	protected Link<EventPreMotionUpdate> onPreMotionUpdate = new Link<>(event -> {
		if(Wrapper.getPlayer().isInWater()) {
			fuckbitchesgetmoney++;
			double xD = Wrapper.moveLooking(0)[0];
			double zD = Wrapper.moveLooking(0)[1];
			if(fuckbitchesgetmoney >= 3) {
				if(Direkt.getInstance().getModuleManager().getModule(Timer.class).isRunning()) 
					Wrapper.getTimer().timerSpeed = 1.55F;
				Wrapper.getPlayer().motionX = xD * 0.06F;
				Wrapper.getPlayer().motionZ = zD * 0.06F;
				this.fuckbitchesgetmoney = 0;
			} else {
				if(Direkt.getInstance().getModuleManager().getModule(Timer.class).isRunning()) 
					Wrapper.getTimer().timerSpeed = 0.9F;
				Wrapper.getPlayer().motionX = xD * 0.27F;
				Wrapper.getPlayer().motionZ = zD * 0.27F;
			}
		}
	});

}
