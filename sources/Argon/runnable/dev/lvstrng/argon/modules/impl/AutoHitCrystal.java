// Decompiled by the rizzer xd
//

package dev.lvstrng.argon.modules.impl;

import dev.lvstrng.argon.event.events.AttackEvent;
import dev.lvstrng.argon.event.events.ItemUseEvent;
import dev.lvstrng.argon.event.listeners.AttackListener;
import dev.lvstrng.argon.event.listeners.ItemUseListener;
import dev.lvstrng.argon.event.listeners.TickListener;
import dev.lvstrng.argon.modules.Category;
import dev.lvstrng.argon.modules.Module;
import dev.lvstrng.argon.modules.setting.Setting;
import dev.lvstrng.argon.modules.setting.settings.BooleanSetting;
import dev.lvstrng.argon.modules.setting.settings.IntSetting;
import dev.lvstrng.argon.utils.InventoryUtil;
import dev.lvstrng.argon.utils.Mouse;
import dev.lvstrng.argon.utils.RandomUtil;
import dev.lvstrng.argon.utils.TargetUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.SwordItem;
import net.minecraft.util.hit.BlockHitResult;
import org.lwjgl.glfw.GLFW;

public final class AutoHitCrystal extends Module implements TickListener, ItemUseListener, AttackListener {
    private final IntSetting switchDelay;
    private final IntSetting switchChance;
    private final IntSetting placeDelay;
    private final IntSetting placeChance;
    private final BooleanSetting workWithTotem;
    private final BooleanSetting workWithCrystal;
    private final BooleanSetting clickSimulation;
    private int currentPlaceDelay;
    private int currentSwitchDelay;
    private boolean isUsingItem;
    private boolean isPlacingCrystal;
    private boolean isTotemActive;

    public AutoHitCrystal() {
        super("Auto Hit Crystal", "Automatically hit crystals for you", 0, Category.COMBAT);
        this.switchDelay = new IntSetting("Switch Delay", 0.0, 20.0, 0.0, 1.0);
        this.switchChance = new IntSetting("Switch Chance", 0.0, 100.0, 100.0, 1.0);
        this.placeDelay = new IntSetting("Place Delay", 0.0, 20.0, 0.0, 1.0);
        this.placeChance = new IntSetting("Place Chance", 0.0, 100.0, 100.0, 1.0).setDescription("Randomization");
        this.workWithTotem = new BooleanSetting("Work With Totem", false);
        this.workWithCrystal = new BooleanSetting("Work With Crystal", false);
        this.clickSimulation = new BooleanSetting("Click Simulation", false).setDescription("Makes the CPS HUD think you're legit");
        this.currentPlaceDelay = 0;
        this.currentSwitchDelay = 0;
        this.addSettings(new Setting[]{this.switchDelay, this.switchChance, this.placeDelay, this.placeChance, this.workWithTotem, this.workWithCrystal, this.clickSimulation});
    }

    @Override
    public void onEnable() {
        this.eventBus.registerPriorityListener(TickListener.class, this);
        this.eventBus.registerPriorityListener(ItemUseListener.class, this);
        this.eventBus.registerPriorityListener(AttackListener.class, this);
        this.currentPlaceDelay = this.placeDelay.getValueInt();
        this.currentSwitchDelay = this.switchDelay.getValueInt();
        this.isUsingItem = false;
        this.isPlacingCrystal = false;
        this.isTotemActive = false;
        super.onEnable();
    }

    @Override
    public void onDisable() {
        this.eventBus.unregister(TickListener.class, this);
        this.eventBus.unregister(ItemUseListener.class, this);
        this.eventBus.unregister(AttackListener.class, this);
        super.onDisable();
    }

    @Override
    public void onTick() {
        final int randomChance = RandomUtil.getRandom(1, 100);
        final MinecraftClient mcClient = this.mc;

        if (mcClient.currentScreen != null) {
            return;
        }

        if (GLFW.glfwGetMouseButton(mcClient.getWindow().getHandle(), 1) == 1) {
            final ItemStack heldItem = this.mc.player.getMainHandStack();
            boolean isSword = heldItem.getItem() instanceof SwordItem;
            boolean isTotem = this.workWithTotem.getValue() && heldItem.isOf(Items.TOTEM_OF_UNDYING);
            boolean isCrystal = this.workWithCrystal.getValue() && heldItem.isOf(Items.END_CRYSTAL);

            if (!isSword && !isTotem && !isCrystal) {
                return;
            }

            this.isUsingItem = true;

            if (!isSword && !isTotem) {
                this.isUsingItem = false;
                return;
            }

            if (isSword) {
                this.isUsingItem = true;
            }

            if (isTotem) {
                if (this.currentSwitchDelay > 0) {
                    --this.currentSwitchDelay;
                    return;
                }
                this.currentSwitchDelay = this.switchDelay.getValueInt();
                this.isTotemActive = true;
                InventoryUtil.method309(Items.OBSIDIAN);
            }

            if (isCrystal) {
                if (this.currentPlaceDelay > 0) {
                    --this.currentPlaceDelay;
                    return;
                }
                this.currentPlaceDelay = this.placeDelay.getValueInt();
                TargetUtil.placeBlock((BlockHitResult) mcClient.crosshairTarget, true);
                this.isPlacingCrystal = true;
            }

            if (this.isUsingItem)
                if (this.clickSimulation.getValue())
                    Mouse.pressKeyDefaultDelay(1);
        } else {
            this.currentPlaceDelay = this.placeDelay.getValueInt();
            this.currentSwitchDelay = this.switchDelay.getValueInt();
            this.isUsingItem = false;
            this.isPlacingCrystal = false;
        }

        this.isTotemActive = false;
    }

    @Override
    public void onItemUse(final ItemUseEvent event) {
        final ItemStack heldItem = this.mc.player.getMainHandStack();
        if (!heldItem.isOf(Items.END_CRYSTAL) && !heldItem.isOf(Items.OBSIDIAN) && GLFW.glfwGetMouseButton(this.mc.getWindow().getHandle(), 1) != 1)
            event.cancelEvent();
    }

    @Override
    public void onAttack(final AttackEvent event) {
        if (this.mc.player.getMainHandStack().isOf(Items.END_CRYSTAL) && GLFW.glfwGetMouseButton(this.mc.getWindow().getHandle(), 0) != 1)
            event.cancelEvent();
    }
}