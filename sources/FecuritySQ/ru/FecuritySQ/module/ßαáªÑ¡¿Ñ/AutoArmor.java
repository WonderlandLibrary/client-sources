package ru.FecuritySQ.module.сражение;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.play.server.SEntityVelocityPacket;
import org.lwjgl.glfw.GLFW;
import ru.FecuritySQ.event.Event;
import ru.FecuritySQ.event.imp.EventPacket;
import ru.FecuritySQ.event.imp.EventUpdate;
import ru.FecuritySQ.module.Module;
import ru.FecuritySQ.option.imp.OptionNumric;
import ru.FecuritySQ.utils.Counter;

public class AutoArmor extends Module {

    public Counter counter = new Counter();
    public OptionNumric delay = new OptionNumric("Задержка", 25, 1, 2000, 0.5F);

    public AutoArmor() {
        super(Category.Сражение, GLFW.GLFW_KEY_0);
        addOption(delay);
    }

    @Override
    public void event(Event e) {
        if(!isEnabled()) return;
        if(e instanceof EventUpdate){
            if(shouldEquip()) equip();
        }
    }

    private void equip() {
        int[] armorSlots = new int[]{-1, -1, -1, -1};
        int[] armorValues = new int[]{-1, -1, -1, -1};

        for (int type = 0; type < 4; type++) {
            ItemStack current = mc.player.inventory.armorItemInSlot(type);
            if (!current.isEmpty() && current.getItem() instanceof ArmorItem) {
                armorValues[type] = getArmorValue(current);
            }
        }

        for (int slot = 9; slot < 45; slot++) {
            ItemStack itemStack = mc.player.inventory.getStackInSlot(slot);
            if (itemStack.isEmpty() || !(itemStack.getItem() instanceof ArmorItem) || itemStack.getCount() > 1)
                continue;

            ArmorItem armor = (ArmorItem) itemStack.getItem();
            int type = armor.getEquipmentSlot().ordinal() - 2;

            if (type == 2 && mc.player.inventory.armorItemInSlot(type).getItem().equals(Items.ELYTRA))
                continue;

            int value = getArmorValue(itemStack);
            if (value > armorValues[type]) {
                armorSlots[type] = slot;
                armorValues[type] = value;
            }
        }

        for (int type = 0; type < 4; type++) {
            int slot = armorSlots[type];
            if (slot == -1)
                continue;

            ItemStack current = mc.player.inventory.armorItemInSlot(type);
            if (!current.isEmpty() || mc.player.inventory.getFirstEmptyStack() != -1) {
                mc.playerController.windowClick(0, 8 - type, 0, ClickType.QUICK_MOVE, mc.player);
                mc.playerController.windowClick(0, slot, 0, ClickType.QUICK_MOVE, mc.player);
                break;
            }
        }
    }

    private boolean shouldEquip() {
        boolean hasDelayFinished = counter.hasTimeElapsed((long) delay.get());
        boolean isCreative = mc.player.isCreative();
        return !isCreative && hasDelayFinished;
    }

    private int getArmorValue(ItemStack itemStack) {
        if (!itemStack.isEmpty() && itemStack.getItem() instanceof ArmorItem armorItem) {
            int reductionAmount = (int) ((int) (armorItem.getDamageReduceAmount() + armorItem.getArmorMaterial().getToughness()) + armorItem.getArmorMaterial().getKnockbackResistance());
            int enchantmentLevel = EnchantmentHelper.getEnchantmentLevel(Enchantments.PROTECTION, itemStack);
            return reductionAmount + enchantmentLevel;
        }
        return -1;
    }
}
