package dev.lvstrng.argon.modules.impl;

import dev.lvstrng.argon.event.listeners.TickListener;
import dev.lvstrng.argon.mixin.HandledScreenMixin;
import dev.lvstrng.argon.modules.Category;
import dev.lvstrng.argon.modules.Module;
import dev.lvstrng.argon.modules.setting.Setting;
import dev.lvstrng.argon.modules.setting.settings.EnumSetting;
import dev.lvstrng.argon.modules.setting.settings.IntSetting;
import dev.lvstrng.argon.utils.AnimationType;
import dev.lvstrng.argon.utils.InventoryUtil;
import dev.lvstrng.argon.utils.Mode;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;

public final class AutoPotRefill extends Module implements TickListener {
    private final EnumSetting refillMode;
    private final IntSetting refillDelay;
    private int delayCounter;

    public AutoPotRefill() {
        super("Auto Pot Refill", "Refills your hotbar with potions", 0, Category.COMBAT);
        this.refillMode = new EnumSetting("Mode", Mode.NORMAL, Mode.class);
        this.refillDelay = new IntSetting("Delay", 0.0, 10.0, 0.0, 1.0);
        this.addSettings(new Setting[]{this.refillMode, this.refillDelay});
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
        if (currentScreen instanceof InventoryScreen inventoryScreen) {
            int isAnimationType = refillMode.current().equals(AnimationType.POSITIVE) ? 1 : 0;

            if (isAnimationType != 0) {
                Slot focusedSlot = ((HandledScreenMixin) inventoryScreen).getFocusedSlot();
                if (focusedSlot == null) {
                    return;
                }
                PlayerInventory playerInventory = this.mc.player.getInventory();
                int emptySlotIndex = -1;

                for (int i = 0; i <= 8; i++) {
                    if (playerInventory.getStack(i).isEmpty()) {
                        emptySlotIndex = i;
                        break;
                    }
                }

                if (emptySlotIndex != -1) {
                    if (delayCounter < refillDelay.getValueInt()) {
                        delayCounter++;
                        return;
                    }
                    this.mc.interactionManager.clickSlot(inventoryScreen.getScreenHandler().syncId, focusedSlot.getIndex(), emptySlotIndex, SlotActionType.SWAP, this.mc.player);
                    delayCounter = 0;
                }
            } else {
                int potionSlot = InventoryUtil.method319(StatusEffects.INSTANT_HEALTH, 1, 1);
                if (potionSlot != -1) {
                    PlayerInventory playerInventory = this.mc.player.getInventory();
                    int emptySlotIndex = -1;

                    for (int j = 0; j <= 8; j++) {
                        if (playerInventory.getStack(j).isEmpty()) {
                            emptySlotIndex = j;
                            break;
                        }
                    }

                    if (emptySlotIndex != -1) {
                        if (delayCounter < refillDelay.getValueInt()) {
                            delayCounter++;
                            return;
                        }
                        this.mc.interactionManager.clickSlot(inventoryScreen.getScreenHandler().syncId, potionSlot, emptySlotIndex, SlotActionType.SWAP, this.mc.player);
                        delayCounter = 0;
                    }
                }
            }
        }
    }
}