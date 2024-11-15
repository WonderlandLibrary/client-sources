package dev.lvstrng.argon.modules.impl;

import dev.lvstrng.argon.event.listeners.TickListener;
import dev.lvstrng.argon.modules.Category;
import dev.lvstrng.argon.modules.Module;

public final class AutoJumpReset extends Module implements TickListener {
    public AutoJumpReset() {
        super("Auto Jump Reset", "Automatically jumps for you when you get hit so you take less knockback (not good for crystal pvp)", 0, Category.COMBAT);
    }

    @Override
    public void onEnable() {
        this.eventBus.registerPriorityListener(TickListener.class, this);
        super.onEnable();
    }

    @Override
    public void onDisable() {
        this.eventBus.unregister(TickListener.class, this);
        super.onDisable();
    }

    @Override
    public void onTick() {
        if (isPlayerInScreen() || isPlayerJumping() || isPlayerOnGround() || isPlayerInAir() || isPlayerHitCooldown()) {
            return;
        }

        if (isPlayerJumpResetConditionMet()) {
            this.mc.player.jump();
        }
    }

    private boolean isPlayerInScreen() {
        return this.mc.currentScreen != null;
    }

    private boolean isPlayerJumping() {
        return this.mc.player.isUsingItem();
    }

    private boolean isPlayerOnGround() {
        return this.mc.player.hurtTime == 0;
    }

    private boolean isPlayerInAir() {
        return this.mc.player.hurtTime != this.mc.player.maxHurtTime;
    }

    private boolean isPlayerHitCooldown() {
        return !this.mc.player.isOnGround();
    }

    private boolean isPlayerJumpResetConditionMet() {
        return this.mc.player.hurtTime == 1;
    }
}