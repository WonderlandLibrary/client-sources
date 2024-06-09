 package us.dev.direkt.module.internal.combat;

import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketUseEntity;
import us.dev.api.timing.Timer;
import us.dev.direkt.Wrapper;
import us.dev.direkt.event.internal.events.game.network.EventSendPacket;
import us.dev.direkt.event.internal.filters.PacketFilter;
import us.dev.direkt.module.ModCategory;
import us.dev.direkt.module.annotations.ModData;
import us.dev.direkt.module.ToggleableModule;
import us.dev.dvent.Listener;
import us.dev.dvent.Link;

@ModData(label = "Criticals", aliases = "crits", category = ModCategory.COMBAT)
public class Criticals extends ToggleableModule {
	private Timer timer = new Timer();
	private boolean cancelSomePackets;

    @Listener
    protected Link<EventSendPacket> onSendPacket = new Link<>((event) -> {
        if (Wrapper.getPlayer().onGround) {
            if (event.getPacket() instanceof CPacketUseEntity) {
                if (((CPacketUseEntity) event.getPacket()).getAction() == CPacketUseEntity.Action.ATTACK) {
                    if (Wrapper.getPlayer().isCollidedVertically && this.timer.hasReach(500)) {
                        Wrapper.sendPacketDirect(new CPacketPlayer.Position(Wrapper.getPlayer().posX, Wrapper.getPlayer().posY + 0.0627, Wrapper.getPlayer().posZ, false));
                        Wrapper.sendPacketDirect(new CPacketPlayer.Position(Wrapper.getPlayer().posX, Wrapper.getPlayer().posY, Wrapper.getPlayer().posZ, false));
                        this.timer.reset();
                        this.cancelSomePackets = true;
                    } //else if(your on water using hacks) tp down some
                }
            } else if (event.getPacket() instanceof CPacketPlayer) {
                if (cancelSomePackets) {
                    event.setCancelled(true);
                    cancelSomePackets = false;
                }
            }
        }
    }, new PacketFilter<>(CPacketUseEntity.class, CPacketPlayer.class));

}
