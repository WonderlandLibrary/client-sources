package dev.lvstrng.argon.modules.impl;

import dev.lvstrng.argon.event.listeners.TickListener;
import dev.lvstrng.argon.mixin.MinecraftClientAccessor;
import dev.lvstrng.argon.modules.Category;
import dev.lvstrng.argon.modules.Module;
import dev.lvstrng.argon.modules.setting.Setting;
import dev.lvstrng.argon.modules.setting.settings.BooleanSetting;
import dev.lvstrng.argon.modules.setting.settings.EnumSetting;
import dev.lvstrng.argon.modules.setting.settings.IntSetting;
import dev.lvstrng.argon.utils.ActionType;
import dev.lvstrng.argon.utils.Mouse;
import dev.lvstrng.argon.utils.RandomUtil;
import dev.lvstrng.argon.utils.Timer;
import net.minecraft.item.AxeItem;
import net.minecraft.item.Item;
import net.minecraft.item.RangedWeaponItem;
import net.minecraft.item.SwordItem;
import net.minecraft.util.hit.HitResult;
import org.lwjgl.glfw.GLFW;

public final class AutoClicker extends Module implements TickListener {
    private final BooleanSetting onlyWeapon;
    private final BooleanSetting onlyBlocks;
    private final BooleanSetting onClick;
    private final IntSetting delay;
    private final IntSetting chance;
    private final EnumSetting actions;
    private final Timer timer;

    public AutoClicker() {
        super("Auto Clicker", "Automatically clicks for you", 0, Category.MISC);
        this.onlyWeapon = new BooleanSetting("Only Weapon", true).setDescription("Only left clicks with weapon in hand");
        this.onlyBlocks = new BooleanSetting("Only Blocks", true).setDescription("Only right clicks blocks");
        this.onClick = new BooleanSetting("On Click", true);
        this.delay = new IntSetting("Delay", 0, 1000, 0, 1);
        this.chance = new IntSetting("Chance", 0, 100, 100, 1);
        this.actions = new EnumSetting("Actions", ActionType.ALL, ActionType.class);
        this.timer = new Timer();
        this.addSettings(new Setting[]{onlyWeapon, onClick, delay, chance, actions});
    }

    @Override
    public void onEnable() {
        this.eventBus.registerPriorityListener(TickListener.class, this);
        this.timer.reset();
        super.onEnable();
    }

    @Override
    public void onDisable() {
        this.eventBus.registerPriorityListener(TickListener.class, this);
        super.onDisable();
    }

    @Override
    public void onTick() {
        if (this.mc.player == null) return;
        if (this.mc.currentScreen != null) return;
        if (this.mc.crosshairTarget == null) return;

        if (this.timer.hasElapsed(this.delay.getValueInt()) && this.chance.getValueInt() >= RandomUtil.getRandom(1, 100)) {
            if (this.actions.is(ActionType.RIGHT_CLICK)) {
                this.handleRightClick();
            }
            if (this.actions.is(ActionType.LEFT_CLICK)) {
                this.handleLeftClick();
            }
            if (this.actions.is(ActionType.ALL)) {
                this.handleRightClick();
                this.handleLeftClick();
            }
        }
    }

    private void handleRightClick() {
        Item mainItem = this.mc.player.getMainHandStack().getItem();
        Item offItem = this.mc.player.getOffHandStack().getItem();
        if (mainItem.getFoodComponent() != null || offItem.getFoodComponent() != null) return;
        if (mainItem instanceof RangedWeaponItem || offItem instanceof RangedWeaponItem) return;
        if (this.onClick.getValue() && GLFW.glfwGetMouseButton(this.mc.getWindow().getHandle(), GLFW.GLFW_MOUSE_BUTTON_RIGHT) != 1)
            return;

        Mouse.simulateClick(GLFW.GLFW_MOUSE_BUTTON_RIGHT);
        ((MinecraftClientAccessor) this.mc).invokeDoItemUse();
        this.timer.reset();
    }

    private void handleLeftClick() {
        Item mainItem = this.mc.player.getMainHandStack().getItem();
        if (this.mc.crosshairTarget.getType() == HitResult.Type.BLOCK) return;
        if (!this.mc.player.isUsingItem()) return;
        if (this.onlyWeapon.getValue() && !(mainItem instanceof SwordItem) && !(mainItem instanceof AxeItem)) return;
        if (this.onClick.getValue() && GLFW.glfwGetMouseButton(this.mc.getWindow().getHandle(), GLFW.GLFW_MOUSE_BUTTON_LEFT) != 1)
            return;

        Mouse.simulateClick(GLFW.GLFW_MOUSE_BUTTON_LEFT);
        ((MinecraftClientAccessor) this.mc).invokeDoAttack();
        this.timer.reset();
    }
}
