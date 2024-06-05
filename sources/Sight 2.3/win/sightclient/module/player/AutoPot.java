package win.sightclient.module.player;

import java.util.List;

import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import win.sightclient.Sight;
import win.sightclient.event.Event;
import win.sightclient.event.events.client.EventUpdate;
import win.sightclient.module.Category;
import win.sightclient.module.Module;
import win.sightclient.module.settings.BooleanSetting;
import win.sightclient.module.settings.NumberSetting;
import win.sightclient.module.settings.Setting;
import win.sightclient.utils.PlayerUtils;
import win.sightclient.utils.TimerUtils;

public class AutoPot extends Module {

	private NumberSetting delay = new NumberSetting("Delay", this, 600, 150, 1500, true);
	private BooleanSetting heal = new BooleanSetting("AutoHeal", this, true);
	private NumberSetting healHealth = new NumberSetting("HealPercent", this, 70, 5, 100, true);
	
	private BooleanSetting speed = new BooleanSetting("Speed", this, true);
	private BooleanSetting jumpboost = new BooleanSetting("JumpBoost", this, false);
	private BooleanSetting other = new BooleanSetting("Other", this, true);
	
	private TimerUtils timer = new TimerUtils();
	
	public AutoPot() {
		super("AutoPot", Category.PLAYER);
	}

	@Override
	public void updateSettings() {
		this.healHealth.setVisible(this.heal.getValue());
	}
	
	@Override
	public void onEvent(Event e) {
		if (e instanceof EventUpdate) {
			EventUpdate eu = (EventUpdate)e;
			if (eu.isPre() && timer.hasReached(this.delay.getValue())) {
				int toThrow = this.getStackToThrow();
				if (toThrow == -1) {
					activated = false;
					timer.reset();
					return;
				} else {
					activated = true;
					if (eu.getLastPitch() == 85F) {
						mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(toThrow));
						mc.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(getStackToThrow2()));
						timer.reset();
						activated = false;
					} else {
						eu.setPitch(85F);
					}
				}
			}
		}
	}
	
	private static boolean activated;
	
	public static boolean isActivated() {
		return activated;
	}
	
	@Override
	public void onDisable()  {
		activated = false;
	}
	
	private ItemStack getStackToThrow2() {
	    for (int k = 0; k < 9; k++) {
	    	ItemStack item = mc.thePlayer.inventory.mainInventory[k];
            if (item != null && this.isValid(item)) {
            	return item;
            }
		}
		return null;
	}
	
	private int getStackToThrow() {
	    for (int k = 0; k < 9; k++) {
	    	ItemStack item = mc.thePlayer.inventory.mainInventory[k];
            if (item != null && this.isValid(item)) {
            	return k;
            }
		}
		return -1;
	}
	
	private boolean isValid(ItemStack stack) {
		if (!(stack.getItem() instanceof ItemPotion)) {
			return false;
		}
		if (!ItemPotion.isSplash(stack.getMetadata())) {
			return false;
		}
		if (stack.getDisplayName().equalsIgnoreCase("�aFrog's Potion (20s)") && !this.jumpboost.getValue()) {
			return false;
		}
		if (PlayerUtils.isBadPotion(stack)) {
			return false;
		}
		ItemPotion ip = (ItemPotion)stack.getItem();
		List<PotionEffect> effects = ip.getEffects(stack);
		boolean containsBad = false;
		for (PotionEffect pe : effects) {
			if (mc.thePlayer.isPotionActive(pe.getPotionID())) {
				containsBad = true;
			} else if (pe.getPotionID() == Potion.regeneration.id || pe.getPotionID() == Potion.heal.id) {
				if (!this.heal.getValue()) {
					containsBad = true;
				} else if ((mc.thePlayer.getHealth() / mc.thePlayer.getMaxHealth()) > (this.healHealth.getValueFloat() / 100F)) {
					containsBad = true;
				}
			} else if (pe.getPotionID() == Potion.moveSpeed.id && !this.speed.getValue()) {
				containsBad = true;
			} else if (!this.other.getValue()) {
				containsBad = true;
			}
		}
		
		if (!containsBad) {
			return true;
		}
		return false;
	}
}
