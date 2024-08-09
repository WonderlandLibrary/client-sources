package dev.excellent.client.module.impl.combat;

import dev.excellent.api.event.impl.player.UpdateEvent;
import dev.excellent.api.interfaces.event.Listener;
import dev.excellent.client.module.api.Category;
import dev.excellent.client.module.api.Module;
import dev.excellent.client.module.api.ModuleInfo;
import dev.excellent.impl.util.player.MoveUtil;
import dev.excellent.impl.util.time.TimerUtil;
import dev.excellent.impl.value.impl.NumberValue;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

@ModuleInfo(name = "Auto Armor", description = "Автоматически надевает на вас броню", category = Category.COMBAT)
public class AutoArmor extends Module {
    final NumberValue delay = new NumberValue("Задержка", this, 100.0f, 0.0f, 1000.0f, 1.0f);
    final TimerUtil stopWatch = TimerUtil.create();


    private final Listener<UpdateEvent> onUpdate = event -> {
        if (MoveUtil.isMoving()) {
            return;
        }
        PlayerInventory inventoryPlayer = AutoArmor.mc.player.inventory;
        int[] bestIndexes = new int[4];
        int[] bestValues = new int[4];

        for (int i = 0; i < 4; ++i) {
            bestIndexes[i] = -1;
            ItemStack stack = inventoryPlayer.armorItemInSlot(i);

            if (!isItemValid(stack) || !(stack.getItem() instanceof ArmorItem armorItem)) {
                continue;
            }

            bestValues[i] = calculateArmorValue(armorItem, stack);
        }

        for (int i = 0; i < 36; ++i) {
            Item item;
            ItemStack stack = inventoryPlayer.getStackInSlot(i);

            if (!isItemValid(stack) || !((item = stack.getItem()) instanceof ArmorItem)) continue;

            ArmorItem armorItem = (ArmorItem) item;
            int armorTypeIndex = armorItem.getSlot().getIndex();
            int value = calculateArmorValue(armorItem, stack);

            if (value <= bestValues[armorTypeIndex]) continue;

            bestIndexes[armorTypeIndex] = i;
            bestValues[armorTypeIndex] = value;
        }

        ArrayList<Integer> randomIndexes = new ArrayList<>(Arrays.asList(0, 1, 2, 3));
        Collections.shuffle(randomIndexes);

        for (int index : randomIndexes) {
            int bestIndex = bestIndexes[index];

            if (bestIndex == -1 || (isItemValid(inventoryPlayer.armorItemInSlot(index)) && inventoryPlayer.getFirstEmptyStack() == -1))
                continue;

            if (bestIndex < 9) {
                bestIndex += 36;
            }

            if (!this.stopWatch.hasReached(this.delay.getValue().longValue())) break;

            ItemStack armorItemStack = inventoryPlayer.armorItemInSlot(index);

            if (isItemValid(armorItemStack)) {
                AutoArmor.mc.playerController.windowClick(0, 8 - index, 0, ClickType.QUICK_MOVE, AutoArmor.mc.player);
            }

            AutoArmor.mc.playerController.windowClick(0, bestIndex, 0, ClickType.QUICK_MOVE, AutoArmor.mc.player);
            this.stopWatch.reset();
            break;
        }
    };


    private boolean isItemValid(ItemStack stack) {
        return stack != null && !stack.isEmpty();
    }

    private int calculateArmorValue(final ArmorItem armor, final ItemStack stack) {
        final int protectionLevel = EnchantmentHelper.getEnchantmentLevel(Enchantments.PROTECTION, stack);
        final IArmorMaterial armorMaterial = armor.getArmorMaterial();
        final int damageReductionAmount = armorMaterial.getDamageReductionAmount(armor.getEquipmentSlot());
        return ((armor.getDamageReduceAmount() * 20 + protectionLevel * 12 + (int) (armor.getToughness() * 2) + damageReductionAmount * 5) >> 3);
    }

}