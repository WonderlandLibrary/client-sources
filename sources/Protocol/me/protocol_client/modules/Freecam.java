package me.protocol_client.modules;

import me.protocol_client.Protocol;
import me.protocol_client.Wrapper;
import me.protocol_client.module.Category;
import me.protocol_client.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.MovementInput;

import org.lwjgl.input.Keyboard;

import darkmagician6.EventManager;
import darkmagician6.EventTarget;
import events.EventMove;
import events.EventPacketSent;
import events.EventPreMotionUpdates;
import events.EventPrePlayerUpdate;

public class Freecam extends Module {

	public Freecam() {
		super("Freecam", "freecam", Keyboard.KEY_U, Category.PLAYER, new String[] { "dsdfsdfsdfsdghgh" });
	}

	private EntityOtherPlayerMP	entity	= null;
	private double				oldX;
	private double				oldY;
	private double				oldZ;

	@EventTarget
	public void onPacketSent(EventPacketSent event) {
		Packet packet = event.getPacket();
		if (packet instanceof C03PacketPlayer) {
			event.setCancelled(true);
		}
	}

	public void onEnable() {
		EventManager.register(this);
		oldX = Minecraft.getMinecraft().thePlayer.posX;
		oldY = Minecraft.getMinecraft().thePlayer.posY;
		oldZ = Minecraft.getMinecraft().thePlayer.posZ;
		mc.thePlayer.noClip = true;
		entity = new EntityOtherPlayerMP(Minecraft.getMinecraft().theWorld, Minecraft.getMinecraft().thePlayer.getGameProfile());
		entity.clonePlayer(Minecraft.getMinecraft().thePlayer, true);
		entity.copyLocationAndAnglesFrom(Minecraft.getMinecraft().thePlayer);
		entity.rotationYawHead = Minecraft.getMinecraft().thePlayer.rotationYawHead;
		entity.swingProgress = mc.thePlayer.swingProgress;
		entity.swingProgressInt = mc.thePlayer.swingProgressInt;
		entity.isSwingInProgress = mc.thePlayer.isSwingInProgress;
		entity.setSneaking(Protocol.sneakModule.isToggled() || Wrapper.getPlayer().isSneaking());
		if (mc.thePlayer.getItemInUse() != null) {
			entity.setItemInUse(mc.thePlayer.getItemInUse(), mc.thePlayer.getItemInUseCount());
		}
		Minecraft.getMinecraft().theWorld.addEntityToWorld(-69, entity);
	}

	@EventTarget
	public void onUpdate(EventPrePlayerUpdate event) {
		mc.thePlayer.noClip = true;
	}
	@EventTarget
	public void onMove(EventMove event){
		if (Wrapper.getPlayer().moveForward == 0 && Wrapper.getPlayer().moveStrafing == 0) {
			event.setCancelled(true);
		}
		event.x *= 3f;
		event.z *= 3f;
	}
	@EventTarget
	public void onEvent(EventPreMotionUpdates event) {
		mc.thePlayer.noClip = true;
		mc.thePlayer.renderArmPitch += 400.0F;
		mc.thePlayer.capabilities.isFlying = false;
		mc.thePlayer.motionY = 0;

		if (mc.gameSettings.keyBindJump.pressed)
			mc.thePlayer.motionY += Protocol.flyModule.speed;
		if (mc.gameSettings.keyBindSneak.pressed)
			mc.thePlayer.motionY -= Protocol.flyModule.speed;
	}

	public void onDisable() {
		EventManager.unregister(this);
		Wrapper.getPlayer().capabilities.isCreativeMode = false;
		mc.thePlayer.capabilities.isFlying = false;
		Minecraft.getMinecraft().thePlayer.setPositionAndRotation(oldX, oldY, oldZ, Minecraft.getMinecraft().thePlayer.rotationYaw, Minecraft.getMinecraft().thePlayer.rotationPitch);
		Minecraft.getMinecraft().theWorld.removeEntityFromWorld(-69);
		entity = null;
		Minecraft.getMinecraft().renderGlobal.loadRenderers();
	}
}
