package ru.FecuritySQ.module.игрок;

import net.minecraft.advancements.criterion.EntityEquipmentPredicate;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.item.*;
import org.lwjgl.glfw.GLFW;
import ru.FecuritySQ.event.Event;
import ru.FecuritySQ.event.imp.EventUpdate;
import ru.FecuritySQ.module.Module;
import ru.FecuritySQ.option.imp.OptionBoolean;

public class ElytraSwap extends Module {

    public ElytraSwap() {
        super(Category.Игрок, GLFW.GLFW_KEY_Z);
    }

    @Override
    public void enable() {
        for (ItemStack stack : mc.player.inventory.armorInventory) {
            if(stack.getItem() instanceof ArmorItem armorItem){
                if(armorItem.getEquipmentSlot() == EquipmentSlotType.CHEST){
                    enquipElytra();
                }
            }
            if(stack.getItem() instanceof ElytraItem) enquipChest();
        }
        toggle();
    }

    private void enquipElytra(){
        for (int inventoryIndex = 0; inventoryIndex < 36; inventoryIndex++) {
            ItemStack stack = mc.player.inventory.getStackInSlot(inventoryIndex);
            if(stack.getItem() instanceof ElytraItem){
                swapSlot(inventoryIndex, 6);
            }
        }
    }

    private void enquipChest(){
        for (int inventoryIndex = 0; inventoryIndex < 36; inventoryIndex++) {
            ItemStack stack = mc.player.inventory.getStackInSlot(inventoryIndex);
            if (!(stack.getItem() instanceof ArmorItem))
                continue;
            ArmorItem armor = (ArmorItem) stack.getItem();
            if(armor.getEquipmentSlot() == EquipmentSlotType.CHEST){
                swapSlot(inventoryIndex, 6);
            }
        }
    }

    private void swapSlot(int from, int to) {
        mc.playerController.windowClick(0, from < 9 ? 36 + from : from, 0, ClickType.PICKUP, mc.player);
        mc.playerController.windowClick(0, to, 0, ClickType.PICKUP, mc.player);
        mc.playerController.windowClick(0, from < 9 ? 36 + from : from, 0, ClickType.PICKUP, mc.player);
    }
}
