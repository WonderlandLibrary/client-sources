package dev.lvstrng.argon.modules.impl;

import dev.lvstrng.argon.event.listeners.TickListener;
import dev.lvstrng.argon.modules.Category;
import dev.lvstrng.argon.modules.Module;
import dev.lvstrng.argon.modules.setting.Setting;
import dev.lvstrng.argon.modules.setting.settings.BooleanSetting;
import dev.lvstrng.argon.modules.setting.settings.IntSetting;
import dev.lvstrng.argon.utils.InventoryUtil;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.util.Hand;

public final class AutoPot extends Module implements TickListener {
    private final IntSetting minHealth;
    private final IntSetting switchDelay;
    private final IntSetting throwDelay;
    private final BooleanSetting switchBack;
    private final BooleanSetting lookDown;
    private int actionCounter;
    private int throwCounter;
    private int selectedSlot;
    private float lookAngle;
    private boolean isHealing;

    public AutoPot() {
        super("Auto Pot", "Automatically throws health potions when low on health", 0, Category.COMBAT);
        this.minHealth = new IntSetting("Min Health", 1.0, 20.0, 10.0, 1.0);
        this.switchDelay = new IntSetting("Switch Delay", 0.0, 10.0, 0.0, 1.0);
        this.throwDelay = new IntSetting("Throw Delay", 0.0, 10.0, 0.0, 1.0);
        this.switchBack = new BooleanSetting("Switch Back", true);
        this.lookDown = new BooleanSetting("Look Down", true);
        this.addSettings(new Setting[]{this.minHealth, this.switchDelay, this.throwDelay, this.switchBack, this.lookDown});
    }

    private void resetCounters() {
        this.actionCounter = 0;
        this.throwCounter = 0;
        this.selectedSlot = -1;
        this.lookAngle = -1.0f;
    }

    @Override
    public void onEnable() {
        this.eventBus.registerPriorityListener(TickListener.class, this);
        this.resetCounters();
        super.onEnable();
    }

    @Override
    public void onDisable() {
        this.eventBus.unregister(TickListener.class, this);
        super.onDisable();
    }

    @Override
    public void onTick() {
        if (this.mc.currentScreen != null) {
            return;
        }
        if (this.mc.player.getHealth() <= this.minHealth.getValueFloat() || isHealing) {
            if (isHealing && this.mc.player.getHealth() >= this.mc.player.getMaxHealth()) {
                isHealing = false;
                return;
            }
            if (!InventoryUtil.hasPotion(StatusEffects.INSTANT_HEALTH, 1, 1, this.mc.player.getMainHandStack())) {
                if (actionCounter < this.switchDelay.getValue()) {
                    actionCounter++;
                    return;
                }
                if (switchBack.getValue() && selectedSlot == -1) {
                    selectedSlot = this.mc.player.getInventory().selectedSlot;
                }
                if (lookDown.getValue() && lookAngle == -1.0f) {
                    lookAngle = this.mc.player.getPitch();
                }
                int potionSlot = InventoryUtil.method313(StatusEffects.INSTANT_HEALTH, 1, 1);
                if (potionSlot != -1) {
                    InventoryUtil.setSlot(potionSlot);
                    actionCounter = 0;
                }
            }
            if (InventoryUtil.hasPotion(StatusEffects.INSTANT_HEALTH, 1, 1, this.mc.player.getMainHandStack())) {
                if (throwCounter < this.throwDelay.getValue()) {
                    throwCounter++;
                    return;
                }
                if (lookDown.getValue()) {
                    this.mc.player.setPitch(90.0f);
                }
                if (this.mc.interactionManager.interactItem(this.mc.player, Hand.MAIN_HAND).shouldSwingHand()) {
                    this.mc.player.swingHand(Hand.MAIN_HAND);
                }
                throwCounter = 0;
            }
        } else if (selectedSlot != -1 || lookAngle != -1.0f) {
            InventoryUtil.setSlot(selectedSlot);
            selectedSlot = -1;
            this.mc.player.setPitch(lookAngle);
            lookAngle = -1.0f;
        }
    }
}