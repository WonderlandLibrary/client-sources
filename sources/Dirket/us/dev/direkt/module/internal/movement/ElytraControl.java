package us.dev.direkt.module.internal.movement;


import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemElytra;
import net.minecraft.item.ItemStack;
import us.dev.direkt.Wrapper;
import us.dev.direkt.event.internal.events.game.player.update.EventPreMotionUpdate;
import us.dev.direkt.module.ModCategory;
import us.dev.direkt.module.annotations.ModData;
import us.dev.direkt.module.ToggleableModule;
import us.dev.dvent.Listener;
import us.dev.dvent.Link;

@ModData(label = "Elytra Control", aliases = "elytra", category = ModCategory.MOVEMENT)
public class ElytraControl extends ToggleableModule {

	@Listener
	protected Link<EventPreMotionUpdate> onPreMotionUpdate = new Link<>(event -> {
		ItemStack itemstack = Wrapper.getPlayer().getItemStackFromSlot(EntityEquipmentSlot.CHEST);

		if (itemstack != null && itemstack.getItem() == Items.ELYTRA && ItemElytra.isBroken(itemstack) ) {
			if(Wrapper.getPlayer().isElytraFlying()) {
				double[] direction = Wrapper.moveLooking(0);
				Double xDir = direction[0],
						zDir = direction[1];
				if(Math.abs(Wrapper.getPlayer().motionX + Wrapper.getPlayer().motionZ) > 2 && Wrapper.getPlayer().rotationPitch < 0) {
					Wrapper.getPlayer().motionX /= 1.7F;
					Wrapper.getPlayer().motionZ /= 1.7F;
					Wrapper.getPlayer().motionY *= 1.0001F;
				} else if(Wrapper.getPlayer().rotationPitch > 0) {
					Wrapper.getPlayer().motionY = 0;
					Wrapper.getPlayer().motionX = xDir * 2.5F;
					Wrapper.getPlayer().motionZ = zDir * 2.5F;
				}
			}
		}
	});

}
