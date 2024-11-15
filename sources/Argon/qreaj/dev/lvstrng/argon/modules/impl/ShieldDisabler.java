package dev.lvstrng.argon.modules.impl;

import dev.lvstrng.argon.event.events.AttackEvent;
import dev.lvstrng.argon.event.listeners.AttackListener;
import dev.lvstrng.argon.event.listeners.TickListener;
import dev.lvstrng.argon.modules.Category;
import dev.lvstrng.argon.modules.Module;
import dev.lvstrng.argon.modules.setting.Setting;
import dev.lvstrng.argon.modules.setting.settings.BooleanSetting;
import dev.lvstrng.argon.modules.setting.settings.IntSetting;
import dev.lvstrng.argon.utils.InventoryUtil;
import dev.lvstrng.argon.utils.Mouse;
import dev.lvstrng.argon.utils.TargetUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.util.hit.EntityHitResult;
import org.lwjgl.glfw.GLFW;

public final class ShieldDisabler extends Module implements TickListener, AttackListener {
    private final IntSetting hitDelaySetting;
    private final IntSetting switchDelaySetting;
    private final BooleanSetting switchBackSetting;
    private final BooleanSetting stunSetting;
    private final BooleanSetting clickSimulationSetting;

    private int hitDelayCounter;
    private int switchDelayCounter;
    private int selectedSlotIndex;

    public ShieldDisabler() {
        super("Shield Disabler", "Automatically disables your opponents shield", 0, Category.COMBAT);
        this.hitDelaySetting = new IntSetting("Hit Delay", 0.0, 20.0, 0.0, 1.0);
        this.switchDelaySetting = new IntSetting("Switch Delay", 0.0, 20.0, 0.0, 1.0);
        this.switchBackSetting = new BooleanSetting("Switch Back", true);
        this.stunSetting = new BooleanSetting("Stun", false);
        this.clickSimulationSetting = new BooleanSetting("Click Simulation", false);
        this.addSettings(new Setting[]{switchDelaySetting, hitDelaySetting, switchBackSetting, stunSetting, clickSimulationSetting});
    }

    @Override
    public void onEnable() {
        this.eventBus.registerPriorityListener(TickListener.class, this);
        this.eventBus.registerPriorityListener(AttackListener.class, this);
        this.hitDelayCounter = hitDelaySetting.getValueInt();
        this.switchDelayCounter = switchDelaySetting.getValueInt();
        this.selectedSlotIndex = -1;
        super.onEnable();
    }

    @Override
    public void onDisable() {
        this.eventBus.unregister(TickListener.class, this);
        this.eventBus.unregister(AttackListener.class, this);
        super.onDisable();
    }

    @Override
    public void onTick() {
        if (this.mc.currentScreen != null || mc.player.isUsingItem()) return;
        if (mc.crosshairTarget instanceof EntityHitResult target) {
            if (target.getEntity() instanceof PlayerEntity player) {
                if (TargetUtil.canHit(player)) return;
                handleShieldDisabling(player);
            }
        }
    }

    private void handleShieldDisabling(PlayerEntity player) {
        if (player.isHolding(Items.SHIELD) && player.isUsingItem()) {
            if (switchDelayCounter > 0) {
                if (selectedSlotIndex == -1) selectedSlotIndex = this.mc.player.getInventory().selectedSlot;
                --switchDelayCounter;
                return;
            }
            if (InventoryUtil.holdAxe()) {
                if (hitDelayCounter > 0) --hitDelayCounter;
                else performShieldDisable(player);
            }
        } else if (selectedSlotIndex != -1) {
            if (switchBackSetting.getValue()) InventoryUtil.setSlot(selectedSlotIndex);
            selectedSlotIndex = -1;
        }
    }

    private void performShieldDisable(PlayerEntity player) {
        if (clickSimulationSetting.getValue()) Mouse.simulateClick(GLFW.GLFW_MOUSE_BUTTON_1);
        TargetUtil.attack(player, true);
        if (stunSetting.getValue()) {
            if (clickSimulationSetting.getValue()) Mouse.simulateClick(GLFW.GLFW_MOUSE_BUTTON_1);
            TargetUtil.attack(player, true);
        }
        hitDelayCounter = hitDelaySetting.getValueInt();
        switchDelayCounter = switchDelaySetting.getValueInt();
    }

    @Override
    public void onAttack(AttackEvent event) {
        if (GLFW.glfwGetMouseButton(this.mc.getWindow().getHandle(), GLFW.GLFW_MOUSE_BUTTON_1) != 1)
            event.cancelEvent();
    }
}