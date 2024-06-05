package me.rich.module.combat;

import clickgui.setting.Setting;
import me.rich.Main;
import me.rich.event.EventTarget;
import me.rich.event.events.EventUpdate;
import me.rich.module.Category;
import me.rich.module.Feature;
import me.rich.notifications.NotificationPublisher;
import me.rich.notifications.NotificationType;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.item.ItemAppleGold;
import net.minecraft.item.ItemStack;

public class AutoGApple extends Feature {
	private boolean eating = false;

	public AutoGApple() {
		super("AutoGApple", 0, Category.COMBAT);
		Main.settingsManager.rSetting(new Setting("Health", this, 15.0, 1.0, 20.0, true));
	}

	@EventTarget
	public void onUpdate(EventUpdate e) {
		this.setModuleName("AutoGapple §7[" + Main.settingsManager.getSettingByName(Main.moduleManager.getModule(AutoGApple.class), "Health").getValFloat() + "]");
		if ((double) (mc.player.getHealth() + mc.player.getAbsorptionAmount()) > Main.settingsManager.getSettingByName(Main.moduleManager.getModule(AutoGApple.class), "Health").getValFloat() && this.eating) {
			this.eating = false;
			this.stop();
			return;
		}
		if (!this.canEat()) {
			return;
		}
		if (this.isFood(mc.player.getHeldItemOffhand())) {
			if ((double) mc.player.getHealth() <= Main.settingsManager.getSettingByName(Main.moduleManager.getModule(AutoGApple.class), "Health").getValFloat() && this.canEat()) {
				KeyBinding.setKeyBindState(mc.gameSettings.keyBindUseItem.getKeyCode(), true);
				this.eating = true;
			}
		}
		if (!this.canEat()) {
			this.stop();
		}
	}

	public static boolean isNullOrEmptyStack(ItemStack itemStack) {
		return itemStack == null || itemStack.func_190926_b();
	}

	boolean isFood(ItemStack itemStack) {
		return !isNullOrEmptyStack(itemStack) && itemStack.getItem() instanceof ItemAppleGold;
	}

	public boolean canEat() {
		return mc.objectMouseOver == null || !(mc.objectMouseOver.entityHit instanceof EntityVillager);
	}

	void stop() {
		KeyBinding.setKeyBindState(mc.gameSettings.keyBindUseItem.getKeyCode(), false);
	}

	@Override
	public void onEnable() {
		super.onEnable();
		NotificationPublisher.queue(this.getName(), "was enabled.", NotificationType.INFO);
	}

	public void onDisable() {
		NotificationPublisher.queue(this.getName(), "was disabled.", NotificationType.INFO);
		super.onDisable();
	}
}
