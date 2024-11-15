package dev.lvstrng.argon.modules.impl;

import dev.lvstrng.argon.event.events.CameraEvent;
import dev.lvstrng.argon.event.listeners.CameraListener;
import dev.lvstrng.argon.event.listeners.TickListener;
import dev.lvstrng.argon.mixin.KeyBindingAccessor;
import dev.lvstrng.argon.modules.Category;
import dev.lvstrng.argon.modules.Module;
import dev.lvstrng.argon.modules.setting.Setting;
import dev.lvstrng.argon.modules.setting.settings.IntSetting;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.glfw.GLFW;

public final class Freecam extends Module implements TickListener, CameraListener {
    private final IntSetting speedSetting;
    public Vec3d previousPosition;
    public Vec3d currentPosition;

    public Freecam() {
        super("Freecam", "Lets you move freely around the world without actually moving", 0, Category.MISC);
        this.speedSetting = new IntSetting("Speed", 1.0, 10.0, 1.0, 1.0);
        this.addSettings(new Setting[]{this.speedSetting});
        this.previousPosition = Vec3d.ZERO;
        this.currentPosition = Vec3d.ZERO;
    }

    @Override
    public void onEnable() {
        this.eventBus.registerPriorityListener(TickListener.class, this);
        this.eventBus.registerPriorityListener(CameraListener.class, this);
        if (this.mc.world != null) {
            Vec3d playerPosition = this.mc.player.getEyePos();
            this.currentPosition = playerPosition;
            this.previousPosition = playerPosition;
        }
        super.onEnable();
    }

    @Override
    public void onDisable() {
        this.eventBus.unregister(TickListener.class, this);
        this.eventBus.unregister(CameraListener.class, this);
        if (this.mc.world != null) {
            this.mc.player.setVelocity(Vec3d.ZERO);
            this.mc.worldRenderer.reload();
        }
        super.onDisable();
    }

    @Override
    public void onTick() {
        if (this.mc.currentScreen != null) {
            return;
        }
        resetMovementKeys();
        Vec3d forward = new Vec3d(
                -MathHelper.sin(-this.mc.player.getYaw() * 0.017453292f - 3.1415927f),
                0.0,
                -MathHelper.cos(-this.mc.player.getYaw() * 0.017453292f - 3.1415927f)
        );
        Vec3d up = new Vec3d(0.0, 1.0, 0.0);
        Vec3d right = up.crossProduct(forward);
        Vec3d movement = Vec3d.ZERO;

        if (isKeyPressed(this.mc.options.forwardKey)) {
            movement = movement.add(forward);
        }
        if (isKeyPressed(this.mc.options.backKey)) {
            movement = movement.subtract(forward);
        }
        if (isKeyPressed(this.mc.options.leftKey)) {
            movement = movement.add(right);
        }
        if (isKeyPressed(this.mc.options.rightKey)) {
            movement = movement.subtract(right);
        }
        if (isKeyPressed(this.mc.options.jumpKey)) {
            movement = movement.add(0.0, speedSetting.getValue(), 0.0);
        }
        if (isKeyPressed(this.mc.options.sneakKey)) {
            movement = movement.add(0.0, -speedSetting.getValue(), 0.0);
        }

        Vec3d adjustedMovement = movement.normalize().multiply(speedSetting.getValue() * (isKeyPressed(this.mc.options.sprintKey) ? 2 : 1));
        this.previousPosition = this.currentPosition;
        this.currentPosition = this.currentPosition.add(adjustedMovement);
    }

    @Override
    public void onCamera(final CameraEvent event) {
        if (this.mc.currentScreen == null) {
            double tickDelta = this.mc.getTickDelta();
            event.setX(MathHelper.lerp(tickDelta, this.previousPosition.x, this.currentPosition.x));
            event.setY(MathHelper.lerp(tickDelta, this.previousPosition.y, this.currentPosition.y));
            event.setZ(MathHelper.lerp(tickDelta, this.previousPosition.z, this.currentPosition.z));
        }
    }

    private boolean isKeyPressed(KeyBinding key) {
        return GLFW.glfwGetKey(this.mc.getWindow().getHandle(), ((KeyBindingAccessor) key).getBoundKey().getCode()) == GLFW.GLFW_PRESS;
    }

    private void resetMovementKeys() {
        this.mc.options.useKey.setPressed(false);
        this.mc.options.attackKey.setPressed(false);
        this.mc.options.forwardKey.setPressed(false);
        this.mc.options.backKey.setPressed(false);
        this.mc.options.leftKey.setPressed(false);
        this.mc.options.rightKey.setPressed(false);
        this.mc.options.jumpKey.setPressed(false);
        this.mc.options.sneakKey.setPressed(false);
    }
}