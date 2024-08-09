package dev.darkmoon.client.module.impl.player;

import com.darkmagician6.eventapi.EventTarget;
import dev.darkmoon.client.module.setting.impl.BooleanSetting;
import dev.darkmoon.client.module.setting.impl.NumberSetting;
import dev.darkmoon.client.event.player.EventUpdate;
import dev.darkmoon.client.module.Category;
import dev.darkmoon.client.module.Module;
import dev.darkmoon.client.module.ModuleAnnotation;
import dev.darkmoon.client.utility.misc.TimerHelper;
import dev.darkmoon.client.utility.move.MovementUtility;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

@ModuleAnnotation(name = "AutoArmor", category = Category.PLAYER)
public class AutoArmor extends Module {
    public BooleanSetting onlyNoMoving = new BooleanSetting("Only No Moving", true);
    public BooleanSetting openInventory = new BooleanSetting("Only Inventory", true);
    public NumberSetting delay = new NumberSetting("Delay", 1, 0.1F, 10, 0.1F);
    private final TimerHelper timerHelper = new TimerHelper();

    @EventTarget
    public void onUpdate(EventUpdate event) {
        if (!(mc.currentScreen instanceof GuiInventory) && openInventory.get()) {
            return;
        }

        if (MovementUtility.isMoving() && onlyNoMoving.get()) {
            return;
        }

        InventoryPlayer inventory = mc.player.inventory;
        int[] armorSlots = new int[4];
        int[] armorValues = new int[4];

        for (int type = 0; type < 4; ++type) {
            armorSlots[type] = -1;
            ItemStack stack = inventory.armorItemInSlot(type);
            if (!isNullOrEmpty(stack) && stack.getItem() instanceof ItemArmor) {
                ItemArmor item = (ItemArmor) stack.getItem();
                armorValues[type] = getArmorValue(item, stack);
            }
        }

        for (int slot = 0; slot < 36; ++slot) {
            ItemStack stack = inventory.getStackInSlot(slot);
            if (!isNullOrEmpty(stack) && stack.getItem() instanceof ItemArmor) {
                ItemArmor item = (ItemArmor) stack.getItem();
                int armorType = item.armorType.getIndex();
                int armorValue = getArmorValue(item, stack);
                if (armorValue > armorValues[armorType]) {
                    armorSlots[armorType] = slot;
                    armorValues[armorType] = armorValue;
                }
            }
        }

        ArrayList<Integer> types = new ArrayList<>(Arrays.asList(0, 1, 2, 3));
        Collections.shuffle(types);

        for (final int i : types) {
            int j = armorSlots[i];
            if (j == -1) {
                continue;
            }
            final ItemStack oldArmor = inventory.armorItemInSlot(i);
            if (!isNullOrEmpty(oldArmor) && inventory.getFirstEmptyStack() == -1) {
                continue;
            }
            if (j < 9) {
                j += 36;
            }
            if (timerHelper.hasReached(delay.get() * 100.0f)) {
                if (!isNullOrEmpty(oldArmor)) {
                    mc.playerController.windowClick(0, 8 - i, 0, ClickType.QUICK_MOVE, mc.player);
                }
                mc.playerController.windowClick(0, j, 0, ClickType.QUICK_MOVE, mc.player);
                timerHelper.reset();
                break;
            }
            break;
        }
    }

    private int getArmorValue(ItemArmor item, ItemStack stack) {
        final int armorPoints = item.damageReduceAmount;
        final int armorToughness = (int) item.toughness;
        final int armorType = item.getArmorMaterial().getDamageReductionAmount(EntityEquipmentSlot.LEGS);
        final Enchantment protection = Enchantments.PROTECTION;
        final int prtLvl = EnchantmentHelper.getEnchantmentLevel(protection, stack);
        final DamageSource dmgSource = DamageSource.causePlayerDamage(mc.player);
        int prtPoints = protection.calcModifierDamage(prtLvl, dmgSource);
        return armorPoints * 5 + prtPoints * 3 + armorToughness + armorType;
    }

    public boolean isNullOrEmpty(ItemStack stack) {
        return stack == null || stack.isEmpty();
    }
}
