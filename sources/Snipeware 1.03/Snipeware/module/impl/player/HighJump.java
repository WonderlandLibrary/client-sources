package Snipeware.module.impl.player;

import java.awt.Color;

import Snipeware.api.annotations.Handler;
import Snipeware.events.player.EventMotionUpdate;
import Snipeware.module.Module;
import Snipeware.util.other.Logger;
import Snipeware.util.player.MovementUtils;
import Snipeware.value.impl.EnumValue;
import net.minecraft.item.ItemFireball;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class HighJump extends Module {
	private boolean execute;
	private EnumValue<Mode> mode = new EnumValue<>("Mode", Mode.Vanilla);
	public HighJump() {
		super("HighJump", 0, ModuleCategory.PLAYER);
		super.addValues(mode);
	}
	
	 public enum Mode {
	        Vanilla,
	        Watchdog,
	    }
	
	@Handler
	public void onMotionUpdate(final EventMotionUpdate event) {
		if(mode.getValueAsString() == "Watchdog") {
			for(int i = 0;i < 9;i++) {
				ItemStack item = mc.thePlayer.inventory.getStackInSlot(i);
				if(item != null && item.getItem() instanceof ItemFireball) {
					mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(i));
					mc.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(mc.thePlayer.getPosition().down(), 1, mc.thePlayer.inventory.getStackInSlot(i), 1, 1,1));
					execute = true;
					break;
					
				}
			}
			
			if(execute) {
				if(mc.thePlayer.hurtTime > 0) {
					mc.thePlayer.motionY = 3;
				}
			}
			
		}else if(mode.getValueAsString() == "Vanilla") {
			if(mc.gameSettings.keyBindJump.pressed == true && MovementUtils.isOnGround (0.8)) {
				mc.thePlayer.motionY = 1;
			}
		}
	}
	
	public void onEnable() {
		super.onEnable();
	}
	
	public void onDisable() {
		super.onDisable();
	}
}
