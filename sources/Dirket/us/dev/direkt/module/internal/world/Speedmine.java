package us.dev.direkt.module.internal.world;

import net.minecraft.network.play.server.SPacketRemoveEntityEffect;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import us.dev.api.property.BoundedProperty;
import us.dev.direkt.Wrapper;
import us.dev.direkt.event.internal.events.game.player.update.EventPreMotionUpdate;
import us.dev.direkt.module.ModCategory;
import us.dev.direkt.module.annotations.ModData;
import us.dev.direkt.module.ToggleableModule;
import us.dev.direkt.module.property.annotations.Exposed;
import us.dev.dvent.Listener;
import us.dev.dvent.Link;

@ModData(label = "Speed Mine", aliases = "fastmine", category = ModCategory.WORLD)
public class Speedmine extends ToggleableModule {

    @Exposed(description = "The haste modifier to use for your mining speed")
    private BoundedProperty<Integer> hasteAmount = new BoundedProperty<>("Haste", 1, 1, 5);

	@Listener
	protected Link<EventPreMotionUpdate> onPreMotionUpdate = new Link<>(event -> {
		Wrapper.getPlayer().addPotionEffect(new PotionEffect(Potion.getPotionById(3), 50, hasteAmount.getValue() - 1));
		Wrapper.getPlayerController().setBlockHitDelay(0);
	});
	
	@Override
	public void onDisable() {
		Wrapper.getPlayerController().setBlockHitDelay(5);
		Wrapper.receivePacket(new SPacketRemoveEntityEffect(Wrapper.getPlayer().getEntityId(), Potion.getPotionById(3)));
	}
}