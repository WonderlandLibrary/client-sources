package in.momin5.cookieclient.client.modules.misc;

import in.momin5.cookieclient.CookieClient;
import in.momin5.cookieclient.api.event.events.PacketEvent;
import in.momin5.cookieclient.api.module.Category;
import in.momin5.cookieclient.api.module.Module;
import in.momin5.cookieclient.api.setting.settings.SettingBoolean;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.*;
import net.minecraft.network.play.server.*;
import net.minecraftforge.common.MinecraftForge;

public class PacketCanceller extends Module {

	public PacketCanceller() {
		super("PacketCanceller", "Cancels specific packets", Category.MISC);
	}

	private SettingBoolean incoming = new SettingBoolean("Incoming", this, true);
	private SettingBoolean outgoing = new SettingBoolean("Outgoing", this, true);

	private SettingBoolean cancelCPacketPlayer 					= register(new SettingBoolean("CPacketPlayer", this, false));
	private SettingBoolean cancelCPacketPlayerAbilities 		= register(new SettingBoolean("CPacketPlayerAbilities", this, false));
	private SettingBoolean cancelCPacketPlayerDigging 			= register(new SettingBoolean("CPacketPlayerDigging", this, false));
	private SettingBoolean cancelCPacketPlayerTryUseItem 		= register(new SettingBoolean("CPacketPlayerTryUseItem", this, false));
	private SettingBoolean cancelCPacketPlayerTryUseItemOnBlock = register(new SettingBoolean("CPacketPlayerTryUseItemOnBlock", this, false));
	private SettingBoolean cancelCPacketAnimation 				= register(new SettingBoolean("CPacketAnimation", this, false));
	private SettingBoolean cancelCPacketEntityAction 			= register(new SettingBoolean("CPacketEntityAction", this, false));
	private SettingBoolean cancelCPacketUseEntity 				= register(new SettingBoolean("CPacketUseEntity", this, false));

	private SettingBoolean cancelSPacketPlayerAbilities 		= register(new SettingBoolean("SPacketPlayerAbilities", this, false));
	private SettingBoolean cancelSPacketPlayerPosLook 			= register(new SettingBoolean("SPacketPlayerPosLook", this, false));
	private SettingBoolean cancelSPacketMoveVehicle 			= register(new SettingBoolean("SPacketMoveVehicle", this, false));
	private SettingBoolean cancelSPacketBlockAction 			= register(new SettingBoolean("SPacketBlockAction", this, false));
	private SettingBoolean cancelSPacketBlockBreakAnim 			= register(new SettingBoolean("SPacketBlockBreakAnim", this, false));
	private SettingBoolean cancelSPacketBlockChange 			= register(new SettingBoolean("SPacketBlockChange", this, false));
	private SettingBoolean cancelSPacketMultiBlockChange 		= register(new SettingBoolean("SPacketMultiBlockChange", this, false));
	private SettingBoolean cancelSPacketEntity 					= register(new SettingBoolean("SPacketEntity", this, false));
	private SettingBoolean cancelSPacketEntityAttach 			= register(new SettingBoolean("SPacketEntityAttach", this, false));
	private SettingBoolean cancelSPacketEntityEffect 			= register(new SettingBoolean("SPacketEntityEffect", this, false));
	private SettingBoolean cancelSPacketEntityEquipment 		= register(new SettingBoolean("SPacketEntityEquipment", this, false));
	private SettingBoolean cancelSPacketEntityHeadLook 			= register(new SettingBoolean("SPacketEntityHeadLook", this, false));
	private SettingBoolean cancelSPacketEntityStatus 			= register(new SettingBoolean("SPacketEntityStatus", this, false));
	private SettingBoolean cancelSPacketEntityTeleport 			= register(new SettingBoolean("SPacketEntityTeleport", this, false));
	private SettingBoolean cancelSPacketEntityVelocity 			= register(new SettingBoolean("SPacketEntityVelocity", this, false));
	private SettingBoolean cancelSPacketEntityProperties 		= register(new SettingBoolean("SPacketEntityProperties", this, false));

	@EventHandler
	public Listener<PacketEvent.Send> onSend = new Listener<>(event -> {
		if (outgoing.enabled) {
			Packet<?> packet = event.getPacket();
			if (packet instanceof CPacketPlayer 				 && cancelCPacketPlayer.enabled) event.cancel();
			if (packet instanceof CPacketPlayerAbilities 		 && cancelCPacketPlayerAbilities.enabled) event.cancel();
			if (packet instanceof CPacketPlayerDigging 			 && cancelCPacketPlayerDigging.enabled) event.cancel();
			if (packet instanceof CPacketPlayerTryUseItem 		 && cancelCPacketPlayerTryUseItem.enabled) event.cancel();
			if (packet instanceof CPacketPlayerTryUseItemOnBlock && cancelCPacketPlayerTryUseItemOnBlock.enabled) event.cancel();
			if (packet instanceof CPacketAnimation 				 && cancelCPacketAnimation.enabled) event.cancel();
			if (packet instanceof CPacketEntityAction 			 && cancelCPacketEntityAction.enabled) event.cancel();
			if (packet instanceof CPacketUseEntity 				 && cancelCPacketUseEntity.enabled) event.cancel();
		}
	});

	@EventHandler
	public Listener<PacketEvent.Receive> onRead = new Listener<>(event -> {
		if (incoming.enabled) {
			Packet<?> packet = event.getPacket();
			if (packet instanceof SPacketPlayerAbilities 	&& cancelSPacketPlayerAbilities.enabled) event.cancel();
			if (packet instanceof SPacketPlayerPosLook  	&& cancelSPacketPlayerPosLook.enabled) event.cancel();
			if (packet instanceof SPacketMoveVehicle  		&& cancelSPacketMoveVehicle.enabled) event.cancel();
			if (packet instanceof SPacketBlockAction  		&& cancelSPacketBlockAction.enabled) event.cancel();
			if (packet instanceof SPacketBlockBreakAnim  	&& cancelSPacketBlockBreakAnim.enabled) event.cancel();
			if (packet instanceof SPacketBlockChange  		&& cancelSPacketBlockChange.enabled) event.cancel();
			if (packet instanceof SPacketMultiBlockChange 	&& cancelSPacketMultiBlockChange.enabled) event.cancel();
			if (packet instanceof SPacketEntity 		 	&& cancelSPacketEntity.enabled) event.cancel();
			if (packet instanceof SPacketEntityAttach  		&& cancelSPacketEntityAttach.enabled) event.cancel();
			if (packet instanceof SPacketEntityEffect  		&& cancelSPacketEntityEffect.enabled) event.cancel();
			if (packet instanceof SPacketEntityEquipment 	&& cancelSPacketEntityEquipment.enabled) event.cancel();
			if (packet instanceof SPacketEntityHeadLook  	&& cancelSPacketEntityHeadLook.enabled) event.cancel();
			if (packet instanceof SPacketEntityStatus  		&& cancelSPacketEntityStatus.enabled) event.cancel();
			if (packet instanceof SPacketEntityTeleport  	&& cancelSPacketEntityTeleport.enabled) event.cancel();
			if (packet instanceof SPacketEntityVelocity  	&& cancelSPacketEntityVelocity.enabled) event.cancel();
			if (packet instanceof SPacketEntityProperties 	&& cancelSPacketEntityProperties.enabled) event.cancel();
		}
	});

	@Override
	public void onDisable(){
		MinecraftForge.EVENT_BUS.unregister(this);
		CookieClient.EVENT_BUS.unsubscribe(this);
	}
}
