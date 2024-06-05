package zamorozka.modules.COMBAT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import org.lwjgl.input.Keyboard;

import de.Hero.settings.Setting;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.InventoryEffectRenderer;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import zamorozka.event.EventTarget;
import zamorozka.event.events.EventPreMotionUpdates;
import zamorozka.main.Zamorozka;
import zamorozka.module.Category;
import zamorozka.module.Module;
import zamorozka.ui.MathUtil;
import zamorozka.ui.Timer2;
import zamorozka.ui.TimerHelper;

public class AutoArmor extends Module {

	TimerHelper timer;
	private Timer2 time = new Timer2();

	public AutoArmor() {
		super("AutoArmor", Keyboard.KEY_NONE, Category.COMBAT);
	}

	@Override
	public void setup() {
		Zamorozka.instance.settingsManager.rSetting(new Setting("ArmorDelay", this, 250, 1, 1000, true));
		Zamorozka.instance.settingsManager.rSetting(new Setting("EnchantmentsCheck", this, true));
		Zamorozka.instance.settingsManager.rSetting(new Setting("MovingSwap", this, false));
	}

	@EventTarget
	public void onUpdate(EventPreMotionUpdates event) {
		double dl = Zamorozka.settingsManager.getSettingByName("ArmorDelay").getValDouble() + MathUtil.getRandomInRange(0, 30);
		if (time.check((float) dl)) {
			if (mc.currentScreen instanceof GuiChest)
				return;
			if (mc.currentScreen instanceof GuiContainer && !(mc.currentScreen instanceof InventoryEffectRenderer))
				return;

			InventoryPlayer inventory = mc.player.inventory;

			if (!Zamorozka.instance.settingsManager.getSettingByName("MovingSwap").getValBoolean() && (mc.player.movementInput.moveForward != 0 || mc.player.movementInput.moveStrafe != 0))
				return;

			int[] bestArmorSlots = new int[4];
			int[] bestArmorValues = new int[4];

			for (int type = 0; type < 4; type++) {
				bestArmorSlots[type] = -1;

				ItemStack stack = inventory.armorItemInSlot(type);
				if (this.isNullOrEmpty(stack) || !(stack.getItem() instanceof ItemArmor))
					continue;

				ItemArmor item = (ItemArmor) stack.getItem();
				bestArmorValues[type] = getArmorValue(item, stack);
			}

			for (int slot = 0; slot < 36; slot++) {
				ItemStack stack = inventory.getStackInSlot(slot);

				if (this.isNullOrEmpty(stack) || !(stack.getItem() instanceof ItemArmor))
					continue;

				ItemArmor item = (ItemArmor) stack.getItem();
				int armorType = item.armorType.getIndex();
				int armorValue = getArmorValue(item, stack);

				if (armorValue > bestArmorValues[armorType]) {
					bestArmorSlots[armorType] = slot;
					bestArmorValues[armorType] = armorValue;
				}
			}

			ArrayList<Integer> types = new ArrayList<Integer>(Arrays.asList(0, 1, 2, 3));
			Collections.shuffle(types);
			for (int type : types) {

				int slot = bestArmorSlots[type];
				if (slot == -1)
					continue;

				ItemStack oldArmor = inventory.armorItemInSlot(type);
				if (!this.isNullOrEmpty(oldArmor) && inventory.getFirstEmptyStack() == -1)
					continue;

				if (slot < 9)
					slot += 36;

				if (!this.isNullOrEmpty(oldArmor))
					mc.playerController.windowClick(0, 8 - type, 0, ClickType.QUICK_MOVE, mc.player);
				mc.playerController.windowClick(0, slot, 0, ClickType.QUICK_MOVE, mc.player);
				time.reset();
				break;

			}
		}
	}

	int getArmorValue(ItemArmor item, ItemStack stack) {
		int armorPoints = item.damageReduceAmount;
		int prtPoints = 0;
		int armorToughness = (int) item.toughness;
		int armorType = item.getArmorMaterial().getDamageReductionAmount(EntityEquipmentSlot.LEGS);

		if (Zamorozka.instance.settingsManager.getSettingByName("EnchantmentsCheck").getValBoolean()) {
			Enchantment protection = Enchantments.PROTECTION;
			int prtLvl = EnchantmentHelper.getEnchantmentLevel(protection, stack);

			EntityPlayerSP player = mc.player;
			DamageSource dmgSource = DamageSource.causePlayerDamage(player);
			prtPoints = protection.calcModifierDamage(prtLvl, dmgSource);
		}

		return armorPoints * 5 + prtPoints * 3 + armorToughness + armorType;
	}

	public static boolean isNullOrEmpty(ItemStack stack) {

		// func_190926_b es el isEmpty en otras versiones
		return stack == null || stack.func_190926_b();
	}

}
