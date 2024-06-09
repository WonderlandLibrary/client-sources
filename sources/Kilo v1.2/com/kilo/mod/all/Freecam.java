package com.kilo.mod.all;

import javax.xml.crypto.dsig.spec.C14NMethodParameterSpec;

import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C01PacketChatMessage;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C03PacketPlayer.C04PacketPlayerPosition;
import net.minecraft.network.play.client.C03PacketPlayer.C05PacketPlayerLook;
import net.minecraft.network.play.client.C03PacketPlayer.C06PacketPlayerPosLook;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.network.play.client.C0CPacketInput;
import net.minecraft.network.play.client.C0DPacketCloseWindow;
import net.minecraft.network.play.client.C0EPacketClickWindow;
import net.minecraft.network.play.client.C0FPacketConfirmTransaction;
import net.minecraft.network.play.client.C10PacketCreativeInventoryAction;
import net.minecraft.network.play.client.C11PacketEnchantItem;
import net.minecraft.network.play.client.C12PacketUpdateSign;
import net.minecraft.network.play.client.C13PacketPlayerAbilities;
import net.minecraft.network.play.client.C14PacketTabComplete;
import net.minecraft.network.play.client.C15PacketClientSettings;
import net.minecraft.network.play.client.C16PacketClientStatus;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import net.minecraft.network.play.client.C18PacketSpectate;
import net.minecraft.network.play.client.C19PacketResourcePackStatus;

import org.lwjgl.input.Keyboard;

import com.kilo.mod.Category;
import com.kilo.mod.Module;
import com.kilo.mod.toolbar.dropdown.Interactable;

public class Freecam extends Module {
	
	public EntityOtherPlayerMP ghost;
	private boolean allowFly, isFlying, onGround;

	public Freecam(String finder, Category category, String name, String description, String warning) {
		super(finder, category, name, description, warning);
	}
	
	public void onEnable() {
		super.onEnable();
		this.allowFly = mc.thePlayer.capabilities.allowFlying;
		this.isFlying = mc.thePlayer.capabilities.isFlying;
		this.onGround = mc.thePlayer.onGround;
		
		ghost = new EntityOtherPlayerMP(mc.theWorld, mc.getSession().getProfile());
		ghost.copyLocationAndAnglesFrom(mc.thePlayer);
		ghost.setRotationYawHead(mc.thePlayer.rotationYawHead);
		ghost.inventory.copyInventory(mc.thePlayer.inventory);
		mc.theWorld.addEntityToWorld(-2, ghost);
		mc.thePlayer.motionY = 0.05447;
	}
	
	public void onDisable() {
		super.onDisable();
		mc.thePlayer.capabilities.allowFlying = allowFly;
		mc.thePlayer.capabilities.isFlying = isFlying;
		mc.thePlayer.onGround = onGround;

		mc.thePlayer.copyLocationAndAnglesFrom(ghost);
		mc.theWorld.removeEntityFromWorld(-2);
	}
	
	public Packet onPacketSend(Packet packet) {
		if (!(packet instanceof C03PacketPlayer) &&
			!(packet instanceof C04PacketPlayerPosition) &&
			!(packet instanceof C05PacketPlayerLook) && 
			!(packet instanceof C06PacketPlayerPosLook) &&
			!(packet instanceof C0APacketAnimation) &&
			!(packet instanceof C0CPacketInput)) {
			return packet;
		}
		return null;
	}
	
	public void onPlayerUpdate() {
		mc.thePlayer.capabilities.isFlying = true;
		mc.thePlayer.capabilities.allowFlying = true;
		mc.thePlayer.onGround = false;
	}
}
