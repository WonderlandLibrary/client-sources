package us.dev.direkt.module.internal.combat;

import net.minecraft.inventory.ClickType;
import net.minecraft.network.play.client.CPacketAnimation;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.util.EnumHand;
import us.dev.api.timing.Timer;
import us.dev.direkt.Wrapper;
import us.dev.direkt.event.internal.events.game.network.EventSendPacket;
import us.dev.direkt.event.internal.filters.PacketFilter;
import us.dev.direkt.module.ModCategory;
import us.dev.direkt.module.annotations.ModData;
import us.dev.direkt.module.ToggleableModule;
import us.dev.dvent.Listener;
import us.dev.dvent.Link;

@ModData(label = "Armor Breaker", aliases = "breaker", category = ModCategory.COMBAT)
public class ArmorBreaker extends ToggleableModule {
    private Timer timer = new Timer();
    private boolean cancelSomePackets;

    @Listener
    protected Link<EventSendPacket> onSendPacket = new Link<>((event) -> {
        CPacketUseEntity packet = (CPacketUseEntity) event.getPacket();
        if (((CPacketUseEntity) event.getPacket()).getAction() == CPacketUseEntity.Action.ATTACK) {
            if (timer.hasReach(200)) {
                event.setCancelled(true);
                Wrapper.getPlayerController().windowClick(Wrapper.getPlayer().inventoryContainer.windowId, 9, Wrapper.getPlayer().inventory.currentItem, ClickType.SWAP, Wrapper.getPlayer());
                Wrapper.sendPacket(new CPacketAnimation(EnumHand.MAIN_HAND));
                Wrapper.sendPacketDirect(new CPacketUseEntity(packet.getEntityFromWorld(Wrapper.getWorld())));
                Wrapper.getPlayerController().windowClick(Wrapper.getPlayer().inventoryContainer.windowId, 9, Wrapper.getPlayer().inventory.currentItem, ClickType.SWAP, Wrapper.getPlayer());
                timer.reset();
            }
        }
    }, new PacketFilter<>(CPacketUseEntity.class));

}
