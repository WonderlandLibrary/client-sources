package us.dev.direkt.module.internal.player;


import net.minecraft.network.play.client.CPacketCloseWindow;
import us.dev.direkt.Wrapper;
import us.dev.direkt.event.internal.events.game.network.EventSendPacket;
import us.dev.direkt.event.internal.filters.PacketFilter;
import us.dev.direkt.module.ModCategory;
import us.dev.direkt.module.annotations.ModData;
import us.dev.direkt.module.ToggleableModule;
import us.dev.dvent.Listener;
import us.dev.dvent.Link;

@ModData(label = "More Inventory", category = ModCategory.PLAYER)
public class MoreInventory extends ToggleableModule {

	@Listener
	protected Link<EventSendPacket> onSendPacket = new Link<>(event -> {
		event.setCancelled(true);
	}, new PacketFilter<>(CPacketCloseWindow.class));
	
	@Override
	public void onDisable() {
		Wrapper.sendPacket(new CPacketCloseWindow(Wrapper.getPlayer().openContainer.windowId));
	}
	
}
