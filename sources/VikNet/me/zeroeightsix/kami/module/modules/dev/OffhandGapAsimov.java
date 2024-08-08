package me.zeroeightsix.kami.module.modules.dev;

import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.module.ModuleManager;
import me.zeroeightsix.kami.module.modules.combat.AutoTotem;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketClickWindow;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created 25 November 2019 by hub
 */
@Module.Info(name = "OffhandGapAsimov", category = Module.Category.HIDDEN, description = "Auto Offhand Gapple")
public class OffhandGapAsimov extends Module {


    private int numOfGaps;
    private int preferredGapSlot;

    private Setting<Boolean> totemOnDisable = register(Settings.b("TotemOnDisable", true));
    private Setting<TotemMode> totemMode = register(Settings.enumBuilder(TotemMode.class).withName("TotemMode").withValue(TotemMode.KAMI).withVisibility(v -> totemOnDisable.getValue()).build());

    @Override
    public void onEnable() {
        if (ModuleManager.getModuleByName("AutoTotem").isEnabled()) {
            ModuleManager.getModuleByName("AutoTotem").disable();
        }
        if (ModuleManager.getModuleByName("AutoTotemDev").isEnabled()) {
            ModuleManager.getModuleByName("AutoTotemDev").disable();
        }
    }

    @Override
    public void onDisable() {
        if (!totemOnDisable.getValue()) {
            return;
        }
        if (totemMode.getValue().equals(TotemMode.KAMI)) {
            AutoTotem autoTotem = (AutoTotem) ModuleManager.getModuleByName("AutoTotem");
            autoTotem.disableSoft();
            if (autoTotem.isDisabled()) {
                autoTotem.enable();
            }
        }
        if (totemMode.getValue().equals(TotemMode.ASIMOV)) {
            AutoTotemDev autoTotemDev = (AutoTotemDev) ModuleManager.getModuleByName("AutoTotemDev");
            autoTotemDev.disableSoft();
            if (autoTotemDev.isDisabled()) {
                autoTotemDev.enable();
            }
        }
    }

    @Override
    public void onUpdate() {

        if (mc.player == null) {
            // still in menu or smthg
            return;
        }

        // dont activate in containers
        if (mc.currentScreen instanceof GuiContainer) {
            return;
        }

        // calc number of gaps and find best gap slot
        if (!findGaps()) {
            // no gaps left
            this.disable();
            return;
        }

        // when offhand is not a gap
        if (!mc.player.getHeldItemOffhand().getItem().equals(Items.GOLDEN_APPLE)) {

            boolean offhandEmptyPreSwitch = false;

            if (mc.player.getHeldItemOffhand().getItem().equals(Items.AIR)) {
                offhandEmptyPreSwitch = true;
            }

            // pick up gap
            //mc.player.connection.sendPacket(new CPacketClickWindow(0, preferredGapSlot, 0, ClickType.PICKUP, mc.player.openContainer.slotClick(preferredGapSlot, 0, ClickType.PICKUP, mc.player), mc.player.openContainer.getNextTransactionID(mc.player.inventory)));
            mc.playerController.windowClick(0, preferredGapSlot, 0, ClickType.PICKUP, mc.player);

            // place gap in offhand slot
            //mc.player.connection.sendPacket(new CPacketClickWindow(0, 45, 0, ClickType.PICKUP, mc.player.openContainer.slotClick(45, 0, ClickType.PICKUP, mc.player), mc.player.openContainer.getNextTransactionID(mc.player.inventory)));
            mc.playerController.windowClick(0, 45, 0, ClickType.PICKUP, mc.player);

            if (!offhandEmptyPreSwitch) {
                // place offhand item in now empty gap slot
                //mc.player.connection.sendPacket(new CPacketClickWindow(0, preferredGapSlot, 0, ClickType.PICKUP, mc.player.openContainer.slotClick(45, 0, ClickType.PICKUP, mc.player), mc.player.openContainer.getNextTransactionID(mc.player.inventory)));
                mc.playerController.windowClick(0, preferredGapSlot, 0, ClickType.PICKUP, mc.player);
            }

            // TODO: try to detect "item on mouse" and place in empty slot

            mc.playerController.updateController();

        }

    }

    private boolean findGaps() {

        this.numOfGaps = 0;

        AtomicInteger preferredGapSlotStackSize = new AtomicInteger();

        preferredGapSlotStackSize.set(Integer.MIN_VALUE);

        getInventoryAndHotbarSlots().forEach((slotKey, slotValue) -> {

            int numOfGapsInStack = 0;

            if (slotValue.getItem().equals(Items.GOLDEN_APPLE)) {
                numOfGapsInStack = slotValue.getCount();

                // always use the largest stack of gaps first
                if (preferredGapSlotStackSize.get() < numOfGapsInStack) {
                    preferredGapSlotStackSize.set(numOfGapsInStack);
                    preferredGapSlot = slotKey;
                }

            }

            this.numOfGaps = this.numOfGaps + numOfGapsInStack;

        });

        // if we hold gaps in offhand, add the stacksize to the total sum
        if (mc.player.getHeldItemOffhand().getItem().equals(Items.GOLDEN_APPLE)) {
            this.numOfGaps = this.numOfGaps + mc.player.getHeldItemOffhand().getCount();
        }

        return this.numOfGaps != 0;

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

    private enum TotemMode {
        KAMI, ASIMOV
    }

}
