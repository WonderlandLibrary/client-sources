package com.kilo.mod.all;

import io.netty.util.concurrent.GenericFutureListener;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

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
import org.lwjgl.util.Timer;

import com.kilo.mod.Category;
import com.kilo.mod.Module;
import com.kilo.mod.ModuleManager;
import com.kilo.mod.toolbar.dropdown.Interactable;
import com.kilo.util.Util;

public class Blink extends Module {
	
	private Timer timer = new Timer();
	private List<Packet> packets = new CopyOnWriteArrayList<Packet>();
	public EntityOtherPlayerMP ghost;

	public Blink(String finder, Category category, String name, String description, String warning) {
		super(finder, category, name, description, warning);

		addOption("Time", "Time between each blink", Interactable.TYPE.SLIDER, 0, new float[] {0, 20}, true);

		addOption("Intermittent", "Blink intermittently", Interactable.TYPE.CHECKBOX, false, null, false);
	}
	
	public void onEnable() {
		super.onEnable();
		ghost = new EntityOtherPlayerMP(mc.theWorld, mc.getSession().getProfile());
		ghost.copyLocationAndAnglesFrom(mc.thePlayer);
		ghost.setRotationYawHead(mc.thePlayer.rotationYawHead);
		ghost.inventory.copyInventory(mc.thePlayer.inventory);
		mc.theWorld.addEntityToWorld(-1, ghost);
		packets.clear();
	}
	
	public void onDisable() {
		super.onDisable();
		mc.theWorld.removeEntityFromWorld(-1);
		if (ModuleManager.get("freecam").active) {
			packets.clear();
		}
		for(Packet packet : packets) {
			mc.getNetHandler().getNetworkManager().sendPacket(packet, (GenericFutureListener)null);
		}
		packets.clear();
	}
	
	public void update() {
		if (ModuleManager.get("freecam").active) {
			return;
		}
		timer.tick();
		if (Util.makeBoolean(getOptionValue("intermittent"))) {
			if (timer.getTime() > Util.makeFloat(getOptionValue("time"))) {
				ghost.copyLocationAndAnglesFrom(mc.thePlayer);
				ghost.setRotationYawHead(mc.thePlayer.rotationYawHead);
				ghost.inventory.copyInventory(mc.thePlayer.inventory);
				for(Packet packet : packets) {
					mc.getNetHandler().getNetworkManager().sendPacket(packet, (GenericFutureListener)null);
				}
				packets.clear();
				timer.reset();
			}
		}
	}
	
	public Packet onPacketSend(Packet packet) {
		if (ModuleManager.get("freecam").active) {
			return packet;
		}
		if (!(packet instanceof C01PacketChatMessage) &&
				!(packet instanceof C02PacketUseEntity) &&
				!(packet instanceof C03PacketPlayer) &&
				!(packet instanceof C04PacketPlayerPosition) &&
				!(packet instanceof C05PacketPlayerLook) && 
				!(packet instanceof C06PacketPlayerPosLook) &&
				!(packet instanceof C07PacketPlayerDigging) &&
				!(packet instanceof C08PacketPlayerBlockPlacement) &&
				!(packet instanceof C09PacketHeldItemChange) &&
				!(packet instanceof C10PacketCreativeInventoryAction) &&
				!(packet instanceof C11PacketEnchantItem) &&
				!(packet instanceof C12PacketUpdateSign) &&
				!(packet instanceof C13PacketPlayerAbilities) &&
				!(packet instanceof C14NMethodParameterSpec) &&
				!(packet instanceof C14PacketTabComplete) &&
				!(packet instanceof C15PacketClientSettings) &&
				!(packet instanceof C16PacketClientStatus) &&
				!(packet instanceof C17PacketCustomPayload) &&
				!(packet instanceof C18PacketSpectate) &&
				!(packet instanceof C19PacketResourcePackStatus) &&
				!(packet instanceof C0APacketAnimation) &&
				!(packet instanceof C0BPacketEntityAction) &&
				!(packet instanceof C0CPacketInput) &&
				!(packet instanceof C0DPacketCloseWindow) &&
				!(packet instanceof C0EPacketClickWindow) &&
				!(packet instanceof C0FPacketConfirmTransaction)) {
			return packet;
		}
		packets.add(packet);
		return null;
	}
}
