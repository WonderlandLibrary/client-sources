package us.dev.direkt.module.internal.player;

import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.network.Packet;
import net.minecraft.network.play.INetHandlerPlayServer;
import net.minecraft.network.play.client.*;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import us.dev.direkt.Direkt;
import us.dev.direkt.Wrapper;
import us.dev.direkt.event.internal.events.game.network.EventPreReceivePacket;
import us.dev.direkt.event.internal.events.game.network.EventSendPacket;
import us.dev.direkt.event.internal.events.game.player.update.EventPreMotionUpdate;
import us.dev.direkt.event.internal.events.game.server.EventServerDisconnect;
import us.dev.direkt.event.internal.filters.PacketFilter;
import us.dev.direkt.module.ModCategory;
import us.dev.direkt.module.annotations.ModData;
import us.dev.direkt.module.ToggleableModule;
import us.dev.direkt.module.annotations.traits.TransientStatus;
import us.dev.direkt.util.client.MovementUtils;
import us.dev.dvent.Listener;
import us.dev.dvent.Link;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ModData(label = "Blink", category = ModCategory.PLAYER)
@TransientStatus
public class Blink extends ToggleableModule {

	private List<Packet<INetHandlerPlayServer>> packets = new ArrayList<>();
	public static EntityOtherPlayerMP fakeEntity;	
	
	@Listener
	protected Link<EventSendPacket> onSendPacket = new Link<>(event -> {
		if (MovementUtils.isMoving(Wrapper.getPlayer())) {
			if (event.getPacket() instanceof CPacketPlayer) {
				event.setCancelled(true);
				packets.add(event.getPacket());
			}
		}
		if (event.getPacket() instanceof CPacketPlayerDigging || event.getPacket() instanceof CPacketPlayerTryUseItemOnBlock || event.getPacket() instanceof CPacketEntityAction || event.getPacket() instanceof CPacketUseEntity || event.getPacket() instanceof CPacketAnimation || event.getPacket() instanceof CPacketPlayerTryUseItem) {
			event.setCancelled(true);
			packets.add(event.getPacket());
		}
		if (!(event.getPacket() instanceof CPacketChatMessage) & !(event.getPacket() instanceof CPacketClientStatus))
			event.setCancelled(true);
	});

	@Listener
	protected Link<EventPreReceivePacket> onPreReceivePacket = new Link<>(event -> {
		if (Wrapper.getWorld().getEntityByID(-1337) != null) {
			SPacketPlayerPosLook packet = (SPacketPlayerPosLook) event.getPacket();
			Wrapper.getWorld().getEntityByID(-1337).setPositionAndRotation(packet.getX(), packet.getY(), packet.getZ(), packet.getYaw(), packet.getPitch());
			event.setCancelled(true);
		}
	}, new PacketFilter<>(SPacketPlayerPosLook.class));

	@Listener
	protected Link<EventServerDisconnect> onServerDisconnect = new Link<>(event -> {
		this.setRunning(false);
	});

	@Override
	public void onEnable() {
		if (Wrapper.getWorld() != null) {
			fakeEntity = new EntityOtherPlayerMP(Wrapper.getWorld(), Wrapper.getPlayer().getGameProfile());
			fakeEntity.inventory = Wrapper.getPlayer().inventory;
			fakeEntity.copyLocationAndAnglesFrom(Wrapper.getPlayer());
			fakeEntity.posY = Wrapper.getPlayer().getEntityBoundingBox().minY;
			fakeEntity.rotationPitch = Wrapper.getPlayer().rotationPitch;
			fakeEntity.rotationYaw = Wrapper.getPlayer().rotationYaw;
			fakeEntity.rotationYawHead = Wrapper.getPlayer().rotationYawHead;
			fakeEntity.renderYawOffset = Wrapper.getPlayer().renderYawOffset;
			Wrapper.getWorld().addEntityToWorld(-1337, fakeEntity);
			fakeEntity = null;
		}
		else
			this.setRunning(false);
	}
	
	@Override
	public void onDisable() {
		if (Wrapper.getWorld() != null) {
			if (Wrapper.getWorld().getEntityByID(-1337) != null)
				Wrapper.getWorld().removeEntityFromWorld(-1337);
			packets.forEach(Wrapper::sendPacketDirect);
		}
		packets.clear();
	}

}
