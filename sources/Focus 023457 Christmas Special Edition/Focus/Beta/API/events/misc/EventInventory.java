
package Focus.Beta.API.events.misc;

import Focus.Beta.API.Event;
import net.minecraft.entity.player.EntityPlayer;

public class EventInventory extends Event {
	private final EntityPlayer player;

	public EventInventory(EntityPlayer player) {
		this.player = player;
	}

	public EntityPlayer getPlayer() {
		return this.player;
	}
}
