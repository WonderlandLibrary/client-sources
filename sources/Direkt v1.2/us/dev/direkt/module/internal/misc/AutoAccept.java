package us.dev.direkt.module.internal.misc;

import net.minecraft.network.play.server.SPacketChat;
import net.minecraft.util.StringUtils;
import us.dev.direkt.Direkt;
import us.dev.direkt.Wrapper;
import us.dev.direkt.event.internal.events.game.network.EventPostReceivePacket;
import us.dev.direkt.event.internal.filters.PacketFilter;
import us.dev.direkt.module.ModCategory;
import us.dev.direkt.module.annotations.ModData;
import us.dev.direkt.module.ToggleableModule;
import us.dev.dvent.Link;
import us.dev.dvent.Listener;

@ModData(label = "Auto Accept", category = ModCategory.MISC)
public class AutoAccept extends ToggleableModule {

	@Listener
	protected Link<EventPostReceivePacket> onRender2D = new Link<>(event -> {
        final SPacketChat packetChat = (SPacketChat)event.getPacket();
        Direkt.getInstance().getFriendManager().getFriendsList().stream()
        .filter(friend -> StringUtils.stripControlCodes(packetChat.getChatComponent().getFormattedText() + " ").toLowerCase().startsWith(friend.getName().toLowerCase()) && packetChat.getChatComponent().getFormattedText().toLowerCase().contains("has requested to teleport to you."))
        .forEach(friend -> Wrapper.sendChatMessage("/etpyes " + friend.getName()));
	}, new PacketFilter<>(SPacketChat.class));
	
}
