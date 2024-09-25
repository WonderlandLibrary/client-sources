package none.module.modules.player;

import org.lwjgl.input.Keyboard;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemBucketMilk;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C03PacketPlayer;
import none.event.Event;
import none.event.RegisterEvent;
import none.event.events.EventPreMotionUpdate;
import none.module.Category;
import none.module.Module;
import none.valuesystem.ModeValue;
import none.valuesystem.NumberValue;

public class FastUse extends Module{

	public FastUse() {
		super("FastUse", "FastUse", Category.PLAYER, Keyboard.KEY_NONE);
	}
	
	private static final String[] modes = {"Packet", "Timer", "NCP"};
	public static ModeValue MODE = new ModeValue("Modes", "Packet", modes);
	private NumberValue<Integer> ticks = new NumberValue<>("TICKS", 12, 1, 32);
	
	@Override
	protected void onEnable() {
		super.onEnable();
		mc.timer.timerSpeed = 1;
	}
	
	@Override
	protected void onDisable() {
		super.onDisable();
		mc.timer.timerSpeed = 1;
	}
	
	@Override
	@RegisterEvent(events = {EventPreMotionUpdate.class})
	public void onEvent(Event event) {
		if (!isEnabled()) return;
		
		setDisplayName(getName() + ChatFormatting.WHITE + " " + MODE.getSelected());
		
		String str = MODE.getSelected();
		
		if (event instanceof EventPreMotionUpdate) {
			EventPreMotionUpdate e = (EventPreMotionUpdate) event;
			
			if (e.isPre()) {
				if (mc.thePlayer.inventory.getCurrentItem() != null && canUseItem(mc.thePlayer.inventory.getCurrentItem().getItem())) {
					switch (str) {
		                case "Timer": {
		                	if (mc.thePlayer.isUsingItem()) {
			                    mc.timer.timerSpeed = 1.3555f;
		                	}else {
		                        mc.timer.timerSpeed = 1F;
		                    }
		                    break;
		                }
		                case "Packet": {
		                	if (mc.thePlayer.isUsingItem()) {
	                			int count = 35;
		                    	for (int i = 0; i < count; i++) {
		                            mc.thePlayer.connection.sendPacket(new C03PacketPlayer());
		                        }
		                        mc.playerController.onStoppedUsingItem(mc.thePlayer);
			                }
		                    break;
		                }
		                case "NCP" : {
		                	if (mc.thePlayer.isUsingItem()) {
		                		if (mc.thePlayer.getItemInUseCount() < 14) {
		                			for (int i = 0; i < 20; i++) {
			                            mc.thePlayer.connection.sendPacket(new C03PacketPlayer());
			                        }
			                        mc.playerController.onStoppedUsingItem(mc.thePlayer);
		                		}
		                	}
		                }
					}
				}else {
					mc.timer.timerSpeed = 1F;
				}
			}
			return;
		}
	}
	
	private boolean canUseItem(Item item) {
    	if (item instanceof ItemSword) {
    		return false;
    	}
    	
    	if (item instanceof ItemBow) {
    		return false;
    	}
    	
    	if (!(item instanceof ItemFood || item instanceof ItemBucketMilk || item instanceof ItemPotion)) {
    		return false;
    	}
    	if (item instanceof ItemFood || item instanceof ItemBucketMilk || item instanceof ItemPotion) {
    		return true;
    	}
    	return false;
    }
}
