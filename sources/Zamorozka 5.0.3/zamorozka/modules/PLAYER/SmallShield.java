package zamorozka.modules.PLAYER;

import de.Hero.settings.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemShield;
import net.minecraft.util.EnumHand;
import zamorozka.event.EventTarget;
import zamorozka.event.events.EventPostMotionUpdates;
import zamorozka.event.events.EventUpdate;
import zamorozka.main.Zamorozka;
import zamorozka.module.Category;
import zamorozka.module.Module;

public class SmallShield extends Module {

	@Override
	public void setup() {
		Zamorozka.settingsManager.rSetting(new Setting("HandAmount", this, 0.8, 0, 1, true));
	}

	ItemRenderer itemRenderer = mc.entityRenderer.itemRenderer;

	public SmallShield() {
		super("SmallShield", 0, Category.VISUALLY);
	}

	@Override
	public void onRender() {
		if (!getState())
			return;
		itemRenderer.equippedProgressOffHand = (float) Zamorozka.settingsManager.getSettingByName("HandAmount")
				.getValDouble();
		super.onRender();
	}
}