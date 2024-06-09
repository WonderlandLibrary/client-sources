package lunadevs.luna.module.player;

import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.EventTarget;
import com.darkmagician6.eventapi.events.Event;
import com.mojang.authlib.GameProfile;

import lunadevs.luna.category.Category;
import lunadevs.luna.events.BoundingBoxEvent;
import lunadevs.luna.events.MoveEvent;
import lunadevs.luna.events.PacketSendEvent;
import lunadevs.luna.events.PushOutOfBlocksEvent;
import lunadevs.luna.events.TickEvent;
import lunadevs.luna.events.TickPreEvent;
import lunadevs.luna.events.UpdateEvent;
import lunadevs.luna.module.Module;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.util.BlockPos;

//Coded by faith.

public class Freecam extends Module {

	private EntityOtherPlayerMP fakePlayer;
	private float posX;
	private float posY;
	private float posZ;
	public static boolean enabled;

	public Freecam() {
		super("Freecam", Keyboard.KEY_NONE, Category.PLAYER, false);
	}

	@Override
	public void onEnable() {
		super.onEnable();
		this.posX = (float) Freecam.mc.thePlayer.posX;
		this.posY = (float) Freecam.mc.thePlayer.posY;
		this.posZ = (float) Freecam.mc.thePlayer.posZ;
		(this.fakePlayer = new EntityOtherPlayerMP(Freecam.mc.theWorld,
				new GameProfile(Freecam.mc.session.getProfile().getId(), Freecam.mc.thePlayer.getName())))
						.setPositionAndRotation(Freecam.mc.thePlayer.posX, Freecam.mc.thePlayer.posY,
								Freecam.mc.thePlayer.posZ, Freecam.mc.thePlayer.rotationYaw,
								Freecam.mc.thePlayer.rotationPitch);
		this.fakePlayer.rotationYawHead = Freecam.mc.thePlayer.rotationYawHead;
		this.fakePlayer.inventory = Freecam.mc.thePlayer.inventory;
		this.fakePlayer.setSneaking(Freecam.mc.thePlayer.isSneaking());
		Freecam.mc.theWorld.addEntityToWorld(1337, this.fakePlayer);
		Freecam.enabled = true;
	}

	@Override
	public void onDisable() {
		super.onDisable();
		try {
			if (this.fakePlayer != null) {
				Freecam.mc.theWorld.removeEntity(this.fakePlayer);
				Freecam.mc.thePlayer.setPosition(this.posX, this.posY, this.posZ);
				Freecam.mc.thePlayer.sendQueue.addToSendQueue(
						new C0BPacketEntityAction(Freecam.mc.thePlayer, C0BPacketEntityAction.Action.STOP_SNEAKING));
			}
		} catch (Exception ex) {
		}
		Freecam.enabled = false;
		Freecam.mc.thePlayer.capabilities.isFlying = false;
	}

	@EventTarget
	public void pushOutOfBlocks(final PushOutOfBlocksEvent e) {
		if (this.isEnabled) {
			e.setCancelled(true);
		}
	}

	@EventTarget
	public void onBB(BoundingBoxEvent event) {
		if (this.isEnabled) {
			event.setCancelled(true);
		}
	}

	public void onMove(final MoveEvent e) {
		if (this.isEnabled && e.x == 0.0) {
			final double motionZ = e.z;
		}
	}

	@EventTarget(4)
	public void preTick(final TickPreEvent event) {
		if (this.isEnabled) {

			Freecam.mc.thePlayer.capabilities.isFlying = true;
			final EntityPlayerSP thePlayer = Freecam.mc.thePlayer;
			thePlayer.renderArmPitch += 700.0f;
			final EntityPlayerSP thePlayer2 = Freecam.mc.thePlayer;
			thePlayer2.renderArmYaw += 180.0f;
			Freecam.mc.thePlayer.sendQueue.addToSendQueue(
					new C0BPacketEntityAction(Freecam.mc.thePlayer, C0BPacketEntityAction.Action.START_SNEAKING));
			if (Freecam.mc.thePlayer.isSprinting()) {
				Freecam.mc.thePlayer.sendQueue.addToSendQueue(
						new C0BPacketEntityAction(Freecam.mc.thePlayer, C0BPacketEntityAction.Action.STOP_SPRINTING));
			}
			event.setCancelled(true);
		}
	}

	public String getValue() {
		return null;
	}

}
