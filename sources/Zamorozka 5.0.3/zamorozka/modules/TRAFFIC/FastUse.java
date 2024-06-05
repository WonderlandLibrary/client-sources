package zamorozka.modules.TRAFFIC;

import org.lwjgl.input.Mouse;

import de.Hero.settings.Setting;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemEgg;
import net.minecraft.item.ItemEndCrystal;
import net.minecraft.item.ItemEnderPearl;
import net.minecraft.item.ItemExpBottle;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemSnow;
import net.minecraft.item.ItemSnowball;
import net.minecraft.item.ItemSplashPotion;
import zamorozka.event.EventTarget;
import zamorozka.event.events.EventPreMotionUpdates;
import zamorozka.main.Zamorozka;
import zamorozka.module.Category;
import zamorozka.module.Module;
import zamorozka.ui.ClientUtils;

public class FastUse extends Module {

	@Override
	public void setup() {
		Zamorozka.settingsManager.rSetting(new Setting("FastEXP", this, true));
		Zamorozka.settingsManager.rSetting(new Setting("FastCrystals", this, true));
		Zamorozka.settingsManager.rSetting(new Setting("FastPearls", this, true));
		Zamorozka.settingsManager.rSetting(new Setting("FastSnowballs", this, true));
		Zamorozka.settingsManager.rSetting(new Setting("FastPotion", this, true));
		Zamorozka.settingsManager.rSetting(new Setting("FastEgg", this, true));
		Zamorozka.settingsManager.rSetting(new Setting("FastHoe", this, true));
	}

	public FastUse() {
		super("FastUse", 0, Category.TRAFFIC);
	}

	@EventTarget
	public void onUpdate(EventPreMotionUpdates event) {
		if (Zamorozka.settingsManager.getSettingByName("FastPotion").getValBoolean()) {
			this.setDisplayName("FastUse §f§ " + "FastPotion");
		} else {
			this.setDisplayName("FastUse");
		}
		if (mc.player.inventory.getCurrentItem().getItem() instanceof ItemExpBottle
				&& Zamorozka.settingsManager.getSettingByName("FastEXP").getValBoolean()) {
			mc.rightClickDelayTimer = 0;
		}
		if (mc.player.inventory.getCurrentItem().getItem() instanceof ItemEndCrystal
				&& Zamorozka.settingsManager.getSettingByName("FastCrystals").getValBoolean()) {
			mc.rightClickDelayTimer = 0;
		}
		if (mc.player.inventory.getCurrentItem().getItem() instanceof ItemEnderPearl
				&& Zamorozka.settingsManager.getSettingByName("FastPearls").getValBoolean()) {
			mc.rightClickDelayTimer = 0;
		}
		if (mc.player.inventory.getCurrentItem().getItem() instanceof ItemEgg
				&& Zamorozka.settingsManager.getSettingByName("FastEgg").getValBoolean()) {
			mc.rightClickDelayTimer = 0;
		}
		if (mc.player.inventory.getCurrentItem().getItem() instanceof ItemSnowball
				&& Zamorozka.settingsManager.getSettingByName("FastSnowballs").getValBoolean()) {
			mc.rightClickDelayTimer = 0;
		}
		if (mc.player.inventory.getCurrentItem().getItem() instanceof ItemHoe
				&& Zamorozka.settingsManager.getSettingByName("FastHoe").getValBoolean()) {
			mc.rightClickDelayTimer = 0;
		}
		if (mc.player.inventory.getCurrentItem().getItem() instanceof ItemSplashPotion
				&& Zamorozka.settingsManager.getSettingByName("FastPotion").getValBoolean()) {
			mc.rightClickDelayTimer = 0;
		}
	}
}