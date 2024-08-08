package me.zeroeightsix.kami.module.modules.dev;

import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created 21 June 2019 by hub
 * Updated 25 November 2019 by hub
 */
@Module.Info(name = "AutoTotemDev", category = Module.Category.HIDDEN, description = "Auto Totem")
public class AutoTotemDev extends Module {

    // TODO: support different inventory views (hotbar slots and inventory slots)
    // https://wiki.vg/Inventory

    // TODO:
    // step 1: if player has no totem in offhandslot, select hotbarslot x and use item swap to offhand
    // step 2: if player has no totem in hotbarslot x, if no inventory is open, refill from player inventory

    private int numOfTotems;
    private int preferredTotemSlot;

    private Setting<Boolean> soft = register(Settings.b("Soft", false));
    private Setting<Boolean> pauseInContainers = register(Settings.b("PauseInContainers", true));
    private Setting<Boolean> pauseInInventory = register(Settings.b("PauseInInventory", true));

    @Override
    public void onUpdate() {

        if (mc.player == null) {
            // still in menu or smthg
            return;
        }

        // calc number of totems and find best totem slot
        if (!findTotems()) {
            // no totems left
            return;
        }

        // pause in containers but not in inventory
        if (pauseInContainers.getValue() && (mc.currentScreen instanceof GuiContainer) && !(mc.currentScreen instanceof GuiInventory)) {
            return;
        }

        // pause in inventory
        if (pauseInInventory.getValue() && (mc.currentScreen instanceof GuiInventory) && (mc.currentScreen instanceof GuiInventory)) {
            return;
        }

        if (soft.getValue()) {

            // when offhand is empty
            if (mc.player.getHeldItemOffhand().getItem().equals(Items.AIR)) {

                // pick up totem
                //mc.player.connection.sendPacket(new CPacketClickWindow(0, preferredTotemSlot, 0, ClickType.PICKUP, mc.player.openContainer.slotClick(preferredTotemSlot, 0, ClickType.PICKUP, mc.player), mc.player.openContainer.getNextTransactionID(mc.player.inventory)));
                mc.playerController.windowClick(0, preferredTotemSlot, 0, ClickType.PICKUP, mc.player);

                // place totem in offhand slot
                //mc.player.connection.sendPacket(new CPacketClickWindow(0, 45, 0, ClickType.PICKUP, mc.player.openContainer.slotClick(45, 0, ClickType.PICKUP, mc.player), mc.player.openContainer.getNextTransactionID(mc.player.inventory)));
                mc.playerController.windowClick(0, 45, 0, ClickType.PICKUP, mc.player);

                // TODO: try to detect "item on mouse" and place in empty slot

                mc.playerController.updateController();

            }

        } else {

            // when offhand is not a totem
            if (!mc.player.getHeldItemOffhand().getItem().equals(Items.TOTEM_OF_UNDYING)) {

                boolean offhandEmptyPreSwitch = false;

                if (mc.player.getHeldItemOffhand().getItem().equals(Items.AIR)) {
                    offhandEmptyPreSwitch = true;
                }

                // pick up totem
                //mc.player.connection.sendPacket(new CPacketClickWindow(0, preferredTotemSlot, 0, ClickType.PICKUP, mc.player.openContainer.slotClick(preferredTotemSlot, 0, ClickType.PICKUP, mc.player), mc.player.openContainer.getNextTransactionID(mc.player.inventory)));
                mc.playerController.windowClick(0, preferredTotemSlot, 0, ClickType.PICKUP, mc.player);

                // place totem in offhand slot
                //mc.player.connection.sendPacket(new CPacketClickWindow(0, 45, 0, ClickType.PICKUP, mc.player.openContainer.slotClick(45, 0, ClickType.PICKUP, mc.player), mc.player.openContainer.getNextTransactionID(mc.player.inventory)));
                mc.playerController.windowClick(0, 45, 0, ClickType.PICKUP, mc.player);

                if (!offhandEmptyPreSwitch) {
                    // place offhand item in now empty totem slot
                    //mc.player.connection.sendPacket(new CPacketClickWindow(0, preferredTotemSlot, 0, ClickType.PICKUP, mc.player.openContainer.slotClick(45, 0, ClickType.PICKUP, mc.player), mc.player.openContainer.getNextTransactionID(mc.player.inventory)));
                    mc.playerController.windowClick(0, preferredTotemSlot, 0, ClickType.PICKUP, mc.player);
                }

                // TODO: try to detect "item on mouse" and place in empty slot

                mc.playerController.updateController();

            }

        }

    }

    private boolean findTotems() {

        this.numOfTotems = 0;

        AtomicInteger preferredTotemSlotStackSize = new AtomicInteger();


        preferredTotemSlotStackSize.set(Integer.MIN_VALUE);


        getInventoryAndHotbarSlots().forEach((slotKey, slotValue) -> {

            int numOfTotemsInStack = 0;
            if (slotValue.getItem().equals(Items.TOTEM_OF_UNDYING)) {
                numOfTotemsInStack = slotValue.getCount();


                // always use the largest stack of totems first
                if (preferredTotemSlotStackSize.get() < numOfTotemsInStack) {
                    preferredTotemSlotStackSize.set(numOfTotemsInStack);
                    preferredTotemSlot = slotKey;
                }


            }

            this.numOfTotems = this.numOfTotems + numOfTotemsInStack;

        });

        // if we hold totems in offhand, add the stacksize to the total sum
        if (mc.player.getHeldItemOffhand().getItem().equals(Items.TOTEM_OF_UNDYING)) {
            this.numOfTotems = this.numOfTotems + mc.player.getHeldItemOffhand().getCount();
        }

        return this.numOfTotems != 0;

    }

    /**
     * @return Map(Key = Slot Id, Value = Slot ItemStack) Player Inventory (3 Rows + Hotbar Row)
     */
    private static Map<Integer, ItemStack> getInventoryAndHotbarSlots() {
        return getInventorySlots(9, 44);
    }

    private static Map<Integer, ItemStack> getInventorySlots(int current, int last) {
        Map<Integer, ItemStack> fullInventorySlots = new HashMap<>();
        while (current <= last) {
            fullInventorySlots.put(current, mc.player.inventoryContainer.getInventory().get(current));
            current++;
        }
        return fullInventorySlots;
    }

    public void disableSoft() {
        soft.setValue(false);
    }

}
