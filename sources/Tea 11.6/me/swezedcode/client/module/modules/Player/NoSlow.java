package me.swezedcode.client.module.modules.Player;

import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventListener;
import com.darkmagician6.eventapi.Listener;

import me.swezedcode.client.module.ModCategory;
import me.swezedcode.client.module.Module;
import me.swezedcode.client.module.modules.Options.MemeNames;
import me.swezedcode.client.utils.events.EventPreMotionUpdates;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class NoSlow extends Module {

	public static boolean enabled;

	public NoSlow() {
		super("NoSlow", Keyboard.KEY_NONE, 0xFF0FFFEF, ModCategory.Player);
	}
	
	@EventListener
	public void onEvent(EventPreMotionUpdates event) {
		if (mc.thePlayer.isBlocking()) {
			mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(
					C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, new BlockPos(0, 0, 0), EnumFacing.DOWN));
		}
	}

	@Override
	public void onEnable() {
		enabled = true;
		if(!MemeNames.enabled) {
			setDisplayName("NoSlow");
		}else{
			setDisplayName("MakesYouLessFat");
		}
		if (this.mc.thePlayer.isBlocking()) {
			this.mc.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(new BlockPos(0, 0, 0), 1,
					this.mc.thePlayer.getCurrentEquippedItem(), 0.0F, 0.0F, 0.0F));
		}
	}

	@Override
	public void onDisable() {
		if(!MemeNames.enabled) {
			setDisplayName("NoSlow");
		}else{
			setDisplayName("MakesYouLessFat");
		}
		enabled = false;
	}

}
