package dev.lvstrng.argon.modules.impl;

import dev.lvstrng.argon.event.listeners.TickListener;
import dev.lvstrng.argon.modules.Category;
import dev.lvstrng.argon.modules.Module;
import org.lwjgl.glfw.GLFW;

public final class NoJumpDelay extends Module implements TickListener {
    public NoJumpDelay() {
        super("No Jump Delay", "Lets you jump faster, removing the delay", 0, Category.MISC);
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
        if (this.mc.currentScreen != null || !this.mc.player.isOnGround()) return;
        if (GLFW.glfwGetKey(this.mc.getWindow().getHandle(), this.getKeybind()) != 1) return;
        this.mc.options.jumpKey.setPressed(false);
        this.mc.player.jump();
    }
}