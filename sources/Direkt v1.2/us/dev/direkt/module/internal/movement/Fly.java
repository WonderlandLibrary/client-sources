package us.dev.direkt.module.internal.movement;

import net.minecraft.network.play.client.CPacketPlayer;
import us.dev.api.property.BoundedProperty;
import us.dev.api.property.Property;
import us.dev.direkt.Wrapper;
import us.dev.direkt.event.internal.events.game.player.update.EventPreMotionUpdate;
import us.dev.direkt.module.ModCategory;
import us.dev.direkt.module.ToggleableModule;
import us.dev.direkt.module.annotations.ModData;
import us.dev.direkt.module.property.annotations.Exposed;
import us.dev.dvent.Link;
import us.dev.dvent.Listener;

@ModData(label = "Fly", category = ModCategory.MOVEMENT)
public class Fly extends ToggleableModule {
    @Exposed(description = "The speed modifier for your flight")
    private BoundedProperty<Integer> flightSpeed = new BoundedProperty<>("Speed", 0, 0, 20);

	@Listener
	protected Link<EventPreMotionUpdate> onPreMotionUpdate = new Link<>(event -> {
		Wrapper.getPlayer().onGround = true;
		Wrapper.getPlayer().moveEntity(Wrapper.getPlayer().motionX * flightSpeed.getValue(), 0, Wrapper.getPlayer().motionZ * flightSpeed.getValue());
		if (Wrapper.getGameSettings().keyBindJump.isKeyDown())
			Wrapper.getPlayer().setVelocity(Wrapper.getPlayer().motionX, 0.1 + (flightSpeed.getValue() / 10), Wrapper.getPlayer().motionZ);
		else if (Wrapper.getGameSettings().keyBindSneak.isKeyDown())
			Wrapper.getPlayer().setVelocity(Wrapper.getPlayer().motionX, -0.1 + -(flightSpeed.getValue() / 10), Wrapper.getPlayer().motionZ);
		else
			Wrapper.getPlayer().setVelocity(Wrapper.getPlayer().motionX, 0, Wrapper.getPlayer().motionZ);
	});

}