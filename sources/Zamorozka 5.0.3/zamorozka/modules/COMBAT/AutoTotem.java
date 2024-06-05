package zamorozka.modules.COMBAT;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import com.mojang.realmsclient.gui.ChatFormatting;

import de.Hero.settings.Setting;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.potion.Potion;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import zamorozka.event.EventTarget;
import zamorozka.event.events.EventRender2D;
import zamorozka.event.events.EventUpdate;
import zamorozka.main.Zamorozka;
import zamorozka.module.Category;
import zamorozka.module.Module;
import zamorozka.ui.RenderUtil;
import zamorozka.ui.Timer2;
import zamorozka.ui.TimerHelper;
import zamorozka.ui.font.CFontRenderer;
import zamorozka.ui.font.Fonts;

public class AutoTotem extends Module {

	private Timer2 timer = new Timer2();

	@Override
	public void setup() {
		Zamorozka.settingsManager.rSetting(new Setting("OnlyInvOpen", this, false));
		Zamorozka.settingsManager.rSetting(new Setting("TotemDelay", this, 0, 0, 5000, true));
		Zamorozka.settingsManager.rSetting(new Setting("TotemHealth", this, 20, 0, 20, true));
	}

	public AutoTotem() {
		super("AutoTotem", 0, Category.COMBAT);
	}

	@EventTarget
	public void onUpdate(EventUpdate event) {
		float tote = (float) Zamorozka.settingsManager.getSettingByName("TotemDelay").getValDouble();
		float ggg = (float) Zamorozka.settingsManager.getSettingByName("TotemHealth").getValDouble();
		this.setDisplayName("AutoTotem §f§ " + totem2());
		if (timer.check(tote)) {
			if (mc.player.getHealth() <= (float) Zamorozka.settingsManager.getSettingByName("TotemHealth").getValDouble() && this.mc.player.getHeldItemOffhand().getItem() != Items.field_190929_cY && this.totem() != -1
					&& (this.mc.currentScreen instanceof GuiInventory || this.mc.currentScreen == null)) {
				if (!(mc.currentScreen instanceof GuiInventory) && Zamorozka.settingsManager.getSettingByName("OnlyInvOpen").getValBoolean())
					return;
				mc.playerController.windowClick(0, this.totem(), 1, ClickType.PICKUP, this.mc.player);
				mc.playerController.windowClick(0, 45, 1, ClickType.PICKUP, this.mc.player);
				timer.reset();
			}
		}
	}

	public int totem() {
		for (int i = 0; i < 45; ++i) {
			final ItemStack itemStack = this.mc.player.inventoryContainer.getSlot(i).getStack();
			if (itemStack.getItem() == Items.field_190929_cY) {
				return i;
			}
		}
		return -1;
	}

	public int totem2() {
		int count = 0;
		for (int i = 0; i < 45; ++i) {
			if (!mc.player.inventory.getStackInSlot(i).func_190926_b() && mc.player.inventory.getStackInSlot(i).getItem() == Items.field_190929_cY) {
				count++;
			}
		}
		return count;
	}
}