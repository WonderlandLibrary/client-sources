package dev.lvstrng.argon.modules.impl;

import dev.lvstrng.argon.event.events.AttackEvent;
import dev.lvstrng.argon.event.listeners.AttackListener;
import dev.lvstrng.argon.event.listeners.TickListener;
import dev.lvstrng.argon.modules.Category;
import dev.lvstrng.argon.modules.Module;
import dev.lvstrng.argon.modules.setting.Setting;
import dev.lvstrng.argon.modules.setting.settings.BooleanSetting;
import dev.lvstrng.argon.modules.setting.settings.IntSetting;
import dev.lvstrng.argon.utils.Mouse;
import dev.lvstrng.argon.utils.TargetUtil;
import dev.lvstrng.argon.utils.Timer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.util.hit.EntityHitResult;
import org.lwjgl.glfw.GLFW;

public final class TriggerBot extends Module implements TickListener, AttackListener {
    private final BooleanSetting workInScreen;
    private final BooleanSetting whileUsing;
    private final BooleanSetting onLeftClick;
    private final BooleanSetting allItems;
    private final IntSetting swordDelay;
    private final IntSetting axeDelay;
    private final BooleanSetting checkShield;
    private final BooleanSetting onlyCritSword;
    private final BooleanSetting onlyCritAxe;
    private final BooleanSetting swingHand;
    private final BooleanSetting whileAscending;
    private final BooleanSetting clickSimulation;
    private final BooleanSetting strayBypass;
    private final BooleanSetting allEntities;
    private final BooleanSetting useShield;
    private final IntSetting shieldTime;
    private final BooleanSetting samePlayer;
    private final Timer timer;

    public TriggerBot() {
        super("Trigger Bot", "Automatically hits players for you", 0, Category.COMBAT);

        workInScreen = new BooleanSetting("Work In Screen", false).setDescription("Will trigger even if you're inside a screen");
        whileUsing = new BooleanSetting("While Use", false).setDescription("Will hit the player no matter if you're eating or blocking with a shield");
        onLeftClick = new BooleanSetting("On Left Click", false).setDescription("Only gets triggered if holding down left click");
        allItems = new BooleanSetting("All Items", false).setDescription("Works with all Items / THIS USES SWORD DELAY AS THE DELAY");
        swordDelay = new IntSetting("Sword Delay", 0.0, 1000.0, 550.0, 1.0).setDescription("Delay for swords");
        axeDelay = new IntSetting("Axe Delay", 0.0, 1000.0, 800.0, 1.0).setDescription("Delay for axes");
        checkShield = new BooleanSetting("Check Shield", false).setDescription("Checks if the player is blocking your hits with a shield (Recommended with Shield Disabler)");
        onlyCritSword = new BooleanSetting("Only Crit Sword", false).setDescription("Only does critical hits with a sword");
        onlyCritAxe = new BooleanSetting("Only Crit Axe", false).setDescription("Only does critical hits with an axe");
        swingHand = new BooleanSetting("Swing Hand", true).setDescription("Whether to swing the hand or not");
        whileAscending = new BooleanSetting("While Ascending", false).setDescription("Won't hit if you're ascending from a jump, only if on ground or falling");
        clickSimulation = new BooleanSetting("Click Simulation", false).setDescription("Makes the CPS hud think you're legit");
        strayBypass = new BooleanSetting("Stray Bypass", false).setDescription("Bypasses stray's Anti-TriggerBot");
        allEntities = new BooleanSetting("All Entities", false).setDescription("Will attack all entities");
        useShield = new BooleanSetting("Use Shield", false).setDescription("Uses shield if it's in your offhand");
        shieldTime = new IntSetting("Shield Time", 100.0, 1000.0, 350.0, 1.0);
        samePlayer = new BooleanSetting("Same Player", false).setDescription("Hits the player that was recently attacked, good for FFA");

        timer = new Timer();

        addSettings(new Setting[]{workInScreen, whileUsing, onLeftClick, allItems, swordDelay, axeDelay, checkShield, whileAscending, samePlayer, onlyCritSword, onlyCritAxe, swingHand, clickSimulation, strayBypass, allEntities, useShield, shieldTime});
    }

    @Override
    public void onEnable() {
        this.eventBus.registerPriorityListener(TickListener.class, this);
        this.eventBus.registerPriorityListener(AttackListener.class, this);
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
        if (!isTriggerEnabled()) return;
        final Item item = this.mc.player.getMainHandStack().getItem();
        if (onLeftClick.getValue() && !isLeftMouseButtonPressed()) return;
        if (isInUse() && !whileUsing.getValue()) return;
        if (!whileAscending.getValue() && isAscending()) return;

        if (!allItems.getValue() && item instanceof SwordItem) handleSwordItem();
        else if (item instanceof AxeItem) handleAxe();
    }

    private boolean isTriggerEnabled() {
        return workInScreen.getValue() || this.mc.currentScreen == null;
    }

    private boolean isLeftMouseButtonPressed() {
        return GLFW.glfwGetMouseButton(this.mc.getWindow().getHandle(), 0) == 1;
    }

    private boolean isInUse() {
        return this.mc.player.getOffHandStack().getItem().isFood() || this.mc.player.getOffHandStack().getItem() instanceof ShieldItem;
    }

    private boolean isAscending() {
        return !this.mc.player.isOnGround() && (this.mc.player.getVelocity().y > 0.0 || this.mc.player.fallDistance <= 0.0f);
    }

    private void handleSwordItem() {
        if (this.mc.crosshairTarget instanceof EntityHitResult entityHitResult) {
            Entity targetEntity = entityHitResult.getEntity();
            if (isEligibleForAttack(targetEntity)) {
                if (timer.hasPassedDelay(swordDelay.getValueFloat())) {
                    if (useShield.getValue() && isUsingShield()) Mouse.pressKeyDefaultDelay(GLFW.GLFW_MOUSE_BUTTON_2);
                    TargetUtil.attack(targetEntity, swingHand.getValue());
                    if (clickSimulation.getValue()) Mouse.pressKeyDefaultDelay(GLFW.GLFW_MOUSE_BUTTON_1);
                    timer.reset();
                } else if (useShield.getValue() && isUsingShield())
                    Mouse.pressKeyWithDelay(GLFW.GLFW_MOUSE_BUTTON_2, shieldTime.getValueInt());
            }
        }
    }

    private void handleAxe() {
        if (this.mc.crosshairTarget instanceof EntityHitResult entityHitResult) {
            Entity targetEntity = entityHitResult.getEntity();
            if (isEligibleForAttack(targetEntity)) {
                if (timer.hasPassedDelay(axeDelay.getValueFloat())) {
                    TargetUtil.attack(targetEntity, swingHand.getValue());
                    if (clickSimulation.getValue()) Mouse.pressKeyDefaultDelay(GLFW.GLFW_MOUSE_BUTTON_1);
                    timer.reset();
                } else if (useShield.getValue() && isUsingShield())
                    Mouse.pressKeyWithDelay(GLFW.GLFW_MOUSE_BUTTON_2, shieldTime.getValueInt());
            }
        }
    }

    private boolean isEligibleForAttack(Entity entity) {
        if (entity == null) return false;
        if (samePlayer.getValue() && entity != this.mc.player.getAttacking()) return false;
        if (entity instanceof PlayerEntity player) {
            if (checkShield.getValue() && player.isBlocking() && !TargetUtil.canHit(player)) return false;
            if ((onlyCritSword.getValue() || onlyCritAxe.getValue()) && this.mc.player.fallDistance <= 0.0f)
                return false;
        }
        if (strayBypass.getValue() && entity instanceof ZombieEntity) return true;
        return allEntities.getValue();
    }

    private boolean isUsingShield() {
        return this.mc.player.getOffHandStack().getItem() == Items.SHIELD && this.mc.player.isBlocking();
    }

    @Override
    public void onAttack(AttackEvent event) {
        if (!isLeftMouseButtonPressed())
            event.cancelEvent();
    }
}