package us.dev.direkt.module.internal.player;


import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.network.play.client.CPacketPlayer;
import us.dev.direkt.Wrapper;
import us.dev.direkt.event.internal.events.game.network.EventSendPacket;
import us.dev.direkt.event.internal.events.game.player.update.EventPreMotionUpdate;
import us.dev.direkt.event.internal.events.game.server.EventServerDisconnect;
import us.dev.direkt.event.internal.filters.PacketFilter;
import us.dev.direkt.module.ModCategory;
import us.dev.direkt.module.annotations.ModData;
import us.dev.direkt.module.ToggleableModule;
import us.dev.direkt.module.annotations.traits.TransientStatus;
import us.dev.dvent.Listener;
import us.dev.dvent.Link;

@ModData(label = "Freecam", category = ModCategory.PLAYER)
@TransientStatus
public class Freecam extends ToggleableModule {

	private EntityOtherPlayerMP fakeEntity;
	private boolean savedAllowFlying, savedisFlying;

	@Listener
	protected Link<EventPreMotionUpdate> onPreMotionUpdate = new Link<>(event -> {
		Wrapper.getPlayer().capabilities.isFlying = true;
		Wrapper.getPlayer().moveEntity(Wrapper.getPlayer().motionX, Wrapper.getPlayer().motionY, Wrapper.getPlayer().motionZ);
		Wrapper.getPlayer().moveEntity(Wrapper.getPlayer().motionX, Wrapper.getPlayer().motionY, Wrapper.getPlayer().motionZ);
		Wrapper.getPlayer().noClip = true;
	});

	@Listener
	protected Link<EventSendPacket> onSendPacket = new Link<>(event -> {
		event.setCancelled(true);
	}, new PacketFilter<>(CPacketPlayer.class));

	@Listener
	protected Link<EventServerDisconnect> onServerDisconnect = new Link<>(event -> {
		this.setRunning(false);
	});

	@Override
	public void onEnable() {
		if (Wrapper.getWorld() != null) {
			this.savedAllowFlying = Wrapper.getPlayer().capabilities.allowFlying;
			this.savedisFlying = Wrapper.getPlayer().capabilities.isFlying;
			Wrapper.getPlayer().capabilities.allowFlying = true;
			Wrapper.getPlayer().capabilities.isFlying = true;
			this.fakeEntity = new EntityOtherPlayerMP(Wrapper.getWorld(), Wrapper.getPlayer().getGameProfile());
			this.fakeEntity.inventory = Wrapper.getPlayer().inventory;
			this.fakeEntity.copyLocationAndAnglesFrom(Wrapper.getPlayer());
			this.fakeEntity.posY = Wrapper.getPlayer().getEntityBoundingBox().minY;
			this.fakeEntity.rotationPitch = Wrapper.getPlayer().rotationPitch;
			this.fakeEntity.rotationYaw = Wrapper.getPlayer().rotationYaw;
			this.fakeEntity.rotationYawHead = Wrapper.getPlayer().rotationYawHead;
			this.fakeEntity.renderYawOffset = Wrapper.getPlayer().renderYawOffset;
			this.fakeEntity.setEntityId(-1337);
			Wrapper.getWorld().addEntityToWorld(-1337, this.fakeEntity);
		}
	}

	@Override
	public void onDisable() {
		if (Wrapper.getWorld().getEntityByID(-1337) != null){
			Wrapper.getPlayer().noClip = false;
			Wrapper.getPlayer().motionX = 0;
			Wrapper.getPlayer().motionY = 0;
			Wrapper.getPlayer().motionZ = 0;
			Wrapper.getPlayer().setPosition(this.fakeEntity.posX, this.fakeEntity.posY, this.fakeEntity.posZ);
			Wrapper.getPlayer().capabilities.allowFlying = savedAllowFlying;
			Wrapper.getPlayer().capabilities.isFlying = savedisFlying;
			Wrapper.getWorld().removeEntity(this.fakeEntity);
		}
	}
	
}
