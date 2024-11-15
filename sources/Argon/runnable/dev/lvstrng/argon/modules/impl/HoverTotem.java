package dev.lvstrng.argon.modules.impl;

import dev.lvstrng.argon.event.listeners.TickListener;
import dev.lvstrng.argon.mixin.HandledScreenMixin;
import dev.lvstrng.argon.modules.Category;
import dev.lvstrng.argon.modules.Module;
import dev.lvstrng.argon.modules.setting.Setting;
import dev.lvstrng.argon.modules.setting.settings.IntSetting;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;

public final class HoverTotem extends Module implements TickListener {
    private final IntSetting delaySetting;
    private final IntSetting totemSlotSetting;
    private int delayCounter;

    public HoverTotem() {
        super("Hover Totem", "Equips a totem in your totem and offhand slots if a totem is hovered", 0, Category.COMBAT);
        this.delaySetting = new IntSetting("Delay", 0.0, 20.0, 0.0, 1.0);
        this.totemSlotSetting = new IntSetting("Totem Slot", 1.0, 9.0, 1.0, 1.0).setDescription("Your preferred totem slot");
        this.addSettings(new Setting[]{this.delaySetting, this.totemSlotSetting});
    }

    @Override
    public void onEnable() {
        this.eventBus.registerPriorityListener(TickListener.class, this);
        this.delayCounter = 0;
        super.onEnable();
    }

    @Override
    public void onDisable() {
        this.eventBus.unregister(TickListener.class, this);
        super.onDisable();
    }

    @Override
    public void onTick() {
        Screen currentScreen = this.mc.currentScreen;
        InventoryScreen inventoryScreen = (InventoryScreen) currentScreen;

        if (!(inventoryScreen instanceof InventoryScreen)) this.delayCounter = this.delaySetting.getValueInt();
        else {
            Slot focusedSlot = ((HandledScreenMixin) inventoryScreen).getFocusedSlot();
            if (focusedSlot != null) {
                int index = focusedSlot.getIndex();
                int totemStartIndex = 35;

                if (index > totemStartIndex) return;

                index = totemSlotSetting.getValueInt();
                int totemIndex = index - totemStartIndex;
                Item item = focusedSlot.getStack().getItem();
                ItemStack totemStack = this.mc.player.getInventory().getStack(totemIndex);

                if (item == Items.TOTEM_OF_UNDYING) {
                    if (this.delayCounter > 0) {
                        --this.delayCounter;
                        return;
                    }
                    this.mc.interactionManager.clickSlot(inventoryScreen.getScreenHandler().syncId, index, totemIndex, SlotActionType.SWAP, this.mc.player);
                    this.delayCounter = this.delaySetting.getValueInt();
                } else {
                    this.mc.player.getOffHandStack();
                    if (totemStack.isOf(Items.TOTEM_OF_UNDYING)) return;
                    if (this.delayCounter > 0) {
                        --this.delayCounter;
                        return;
                    }
                    this.mc.interactionManager.clickSlot(inventoryScreen.getScreenHandler().syncId, index, 40, SlotActionType.SWAP, this.mc.player);
                    this.delayCounter = this.delaySetting.getValueInt();
                }
            }
        }
    }
}