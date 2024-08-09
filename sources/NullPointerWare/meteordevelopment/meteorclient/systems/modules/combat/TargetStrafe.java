/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package meteordevelopment.meteorclient.systems.modules.combat;

import meteordevelopment.meteorclient.events.entity.player.PlayerMoveEvent;
import meteordevelopment.meteorclient.events.entity.player.SendMovementPacketsEvent;
import meteordevelopment.meteorclient.events.render.Render3DEvent;
import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.settings.*;
import meteordevelopment.meteorclient.systems.modules.Categories;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.systems.modules.Modules;
import meteordevelopment.meteorclient.systems.modules.misc.Disabler;
import meteordevelopment.meteorclient.utils.entity.SortPriority;
import meteordevelopment.meteorclient.utils.entity.TargetUtils;
import meteordevelopment.meteorclient.utils.player.MoveUtils;
import meteordevelopment.meteorclient.utils.player.Rotations;
import meteordevelopment.meteorclient.utils.render.RenderUtils;
import meteordevelopment.meteorclient.utils.render.color.Color;
import meteordevelopment.meteorclient.utils.world.BlockUtils;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;

import static meteordevelopment.meteorclient.utils.player.MoveUtils.setMoveSpeed2;

public final class TargetStrafe extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    public final Setting<MoveMode> mode = sgGeneral.add(new EnumSetting.Builder<MoveMode>()
        .name("Mode")
        .description("Select the movement mode.")
        .defaultValue(MoveMode.Basic)
        .build()
    );

    private final Setting<SortPriority> priority = sgGeneral.add(new EnumSetting.Builder<SortPriority>()
        .name("priority")
        .description("How to filter targets within range.")
        .defaultValue(SortPriority.ClosestAngle)
        .build()
    );

    public final Setting<ExecuteMode> executeMode = sgGeneral.add(new EnumSetting.Builder<ExecuteMode>()
        .name("Execute Mode")
        .description("Select when the movement should be executed.")
        .defaultValue(ExecuteMode.Tick)
        .build()
    );

    public final Setting<Double> targetRange = sgGeneral.add(new DoubleSetting.Builder()
        .name("Target Range")
        .description("Set the range for target detection.")
        .defaultValue(7)
        .sliderRange(1, 30)
        .build()
    );

    public final Setting<Double> radius = sgGeneral.add(new DoubleSetting.Builder()
        .name("Radius")
        .description("Set the radius for movement.")
        .defaultValue(1.9)
        .sliderRange(1, 30)
        .build()
    );

    public final Setting<Double> speed = sgGeneral.add(new DoubleSetting.Builder()
        .name("Speed")
        .description("Set the movement speed.")
        .defaultValue(0.24)
        .sliderRange(1, 30)
        .build()
    );

    public final Setting<Double> scrollSpeed = sgGeneral.add(new DoubleSetting.Builder()
        .name("Scroll Speed")
        .description("Set the scroll speed.")
        .defaultValue(0.26)
        .sliderRange(1, 30)
        .visible(() -> mode.get() == MoveMode.Scroll)
        .build()
    );

    public final Setting<Boolean> autoJump = sgGeneral.add(new BoolSetting.Builder()
        .name("Auto Jump")
        .description("Auto Jump while module is on...")
        .defaultValue(false)
        .build()
    );

    public final Setting<Boolean> ogSpeedCalc = sgGeneral.add(new BoolSetting.Builder()
        .name("Original speed calculations")
        .description("Preserves the original (unreadable) speed calcs...")
        .defaultValue(false)
        .build()
    );

    public final Setting<Boolean> jumpOnly = sgGeneral.add(new BoolSetting.Builder()
        .name("Jump Only")
        .description("Only do shit while jumping")
        .defaultValue(false)
        .build()
    );

    public final Setting<Boolean> voidCheck = sgGeneral.add(new BoolSetting.Builder()
        .name("Void Check")
        .description("Checks if the next jump pos is in void...")
        .defaultValue(false)
        .build()
    );

    public final Setting<Boolean> isCaveUpdated = sgGeneral.add(new BoolSetting.Builder()
        .name("Cave Update")
        .description("Changes the Y value from 0 to -60 because of the cave update...")
        .defaultValue(false)
        .visible(voidCheck::get)
        .build()
    );

    public final Setting<Boolean> circel = sgGeneral.add(new BoolSetting.Builder()
        .name("Circel")
        .description("Renders a circel on the target!.")
        .defaultValue(false)
        .build()
    );

    public final Setting<Boolean> disablerBoost = sgGeneral.add(new BoolSetting.Builder()
        .name("Disabler Boost")
        .description("Enable damage boost.")
        .defaultValue(false)
        .build()
    );

    public final Setting<Boolean> disablerBoostSetMode = sgGeneral.add(new BoolSetting.Builder()
        .name("Set Mode")
        .description("Sets the mode to scroll if disabler is on.")
        .defaultValue(false)
        .build()
    );

    public final Setting<Double> disablerBoostSetting = sgGeneral.add(new DoubleSetting.Builder()
        .name("Boost")
        .description("Set the disablerboost value.")
        .defaultValue(0.09)
        .sliderRange(1, 30)
        .visible(disablerBoost::get)
        .build()
    );

    public final Setting<Boolean> damageBoost = sgGeneral.add(new BoolSetting.Builder()
        .name("Damage Boost")
        .description("Enable damage boost.")
        .defaultValue(false)
        .build()
    );

    public final Setting<Double> boost = sgGeneral.add(new DoubleSetting.Builder()
        .name("Boost")
        .description("Set the boost value.")
        .defaultValue(0.09)
        .sliderRange(1, 30)
        .visible(damageBoost::get)
        .build()
    );

    public TargetStrafe() {
        super(Categories.Combat, "target-strafe", "Circel!!!");
    }

    private PlayerEntity target;
    private int direction = 1;
    MoveMode defaultMode;

    @Override
    public void onActivate() {
        super.onActivate();
        defaultMode = mode.get();
    }

    @EventHandler
    private void onTick(final TickEvent.Pre event){
        if (executeMode.get() == ExecuteMode.Tick) run();
    }

    @EventHandler
    private void onMove(final PlayerMoveEvent event) {
        if (executeMode.get() == ExecuteMode.Move) run();
        if (executeMode.get() == ExecuteMode.Move && mode.get().equals(MoveMode.Basic2)) {
            target = TargetUtils.getPlayerTarget(targetRange.get(), priority.get()); // find target
            if (TargetUtils.isBadTarget(target, targetRange.get())) return;

            if (mc.player.isOnGround() && autoJump.get()) mc.player.jump(); // jump if needed, set direction
            if ((mc.player.isOnGround() || !mc.options.jumpKey.isPressed()) && jumpOnly.get()) return;

            if (disablerBoostSetMode.get() && Modules.get().isActive(Disabler.class))
                setModeOnDisabler();

            if (BlockUtils.isVoidBelow2(mc.player.getBlockPos(), isCaveUpdated.get()) && voidCheck.get()) // check void
                direction = -direction;

            if (mc.options.leftKey.isPressed()) {
                direction = 1;
            } else if (mc.options.rightKey.isPressed()) {
                direction = -1;
            }
            if (mc.player.horizontalCollision) direction = direction == 1 ? -1 : 1; // check collision

            // double speed = damageBoost.get() && mc.player.hurtTime != 0 ? this.speed.get() + boost.get() : this.speed.get(); // set speed + movement factor
            double speed = getSpeed();
            double forward = mc.player.distanceTo(target) > radius.get() ? 1 : 0;

            float yaw = Rotations.lookAtEntity(target)[0]; // calculate rotation
            mc.player.bodyYaw = yaw;
            mc.player.headYaw = yaw;
            SecondBasic(event, speed, yaw, direction, forward);
        }
    }

    @EventHandler
    private void onUpdate(final SendMovementPacketsEvent event) {
        if (executeMode.get() == ExecuteMode.Update) run();
    }

    @EventHandler
    private void onRender(final Render3DEvent e) {
        if (mc.player == null) return;
        if (circel.get() && target != null)
            RenderUtils.renderCircle(radius.get(), 1, Color.WHITE, target, e);
    }

    private void run() {
        target = TargetUtils.getPlayerTarget(targetRange.get(), priority.get()); // find target
        if (TargetUtils.isBadTarget(target, targetRange.get())) return;

        if (mc.player.isOnGround() && autoJump.get()) mc.player.jump(); // jump if needed, set direction
        if ((mc.player.isOnGround() || !mc.options.jumpKey.isPressed()) && jumpOnly.get()) return;

        if (disablerBoostSetMode.get() && Modules.get().isActive(Disabler.class))
            setModeOnDisabler();

        if (BlockUtils.isVoidBelow2(mc.player.getBlockPos(), isCaveUpdated.get()) && voidCheck.get()) // check void
            direction = -direction;

        if (mc.options.leftKey.isPressed()) {
            direction = 1;
        } else if (mc.options.rightKey.isPressed()) {
            direction = -1;
        }
        if (mc.player.horizontalCollision) direction = direction == 1 ? -1 : 1; // check collision

        // double speed = damageBoost.get() && mc.player.hurtTime != 0 ? this.speed.get() + boost.get() : this.speed.get(); // set speed + movement factor
        double speed = getSpeed();
        double forward = mc.player.distanceTo(target) > radius.get() ? 1 : 0;

        float yaw = Rotations.lookAtEntity(target)[0]; // calculate rotation
        mc.player.bodyYaw = yaw;
        mc.player.headYaw = yaw;

        switch (mode.get()) {
            case Basic -> getBasic(yaw, speed, forward, direction);
            case Scroll -> getScroll(target, speed);
            case ScrollTest -> getScrollTest(target, speed, forward);
        }
    }

    private void getScroll(Entity target, double speed) {
        double c1 = (mc.player.getX() - target.getX()) / (Math.sqrt(Math.pow(mc.player.getX() - target.getX(), 2) + Math.pow(mc.player.getZ() - target.getZ(), 2)));
        double s1 = (mc.player.getZ() - target.getZ()) / (Math.sqrt(Math.pow(mc.player.getX() - target.getX(), 2) + Math.pow(mc.player.getZ() - target.getZ(), 2)));
        double x = speed * s1 * direction - scrollSpeed.get() * speed * c1;
        double z = -speed * c1 * direction - scrollSpeed.get() * speed * s1;
        MoveUtils.setHVelocity(x, z);
    }

    private void getScrollTest(Entity target, double speed, double forward) {
        double deltaX = mc.player.getX() - target.getX();
        double deltaZ = mc.player.getZ() - target.getZ();

        double distance = Math.sqrt(deltaX * deltaX + deltaZ * deltaZ);

        double c1 = deltaX / distance;
        double s1 = deltaZ / distance;

        double x = speed * s1 * direction - scrollSpeed.get() * speed * c1;
        double z = -speed * c1 * direction - scrollSpeed.get() * speed * s1;

        MoveUtils.setHVelocity(x, z);
    }

    private void setModeOnDisabler() {
        if (!disablerBoostSetMode.get() || !disablerBoost.get())
            return;

        if (disablerBoost.get() && !Modules.get().isActive(Disabler.class))
            mode.set(defaultMode);

        if (disablerBoost.get() && disablerBoostSetMode.get() && mode.get().equals(MoveMode.Basic) && Modules.get().isActive(Disabler.class))
            mode.set(MoveMode.ScrollTest);
    }


    private void getBasic(float yaw, double speed, double forward, double direction) {
        if (forward != 0.0D) {
            if (direction > 0.0D) {
                yaw += (float) (forward > 0.0D ? -45 : 45);
            } else if (direction < 0.0D) {
                yaw += (float) (forward > 0.0D ? 45 : -45);
            }
            direction = 0.0D;
            if (forward > 0.0D) {
                forward = 1.0D;
            } else if (forward < 0.0D) {
                forward = -1.0D;
            }
        }

        double cos = Math.cos(Math.toRadians((yaw + 90.0F)));
        double sin = Math.sin(Math.toRadians((yaw + 90.0F)));

        double x = forward * speed * cos + direction * speed * sin;
        double z = forward * speed * sin - direction * speed * cos;
        MoveUtils.setHVelocity(x, z);
    }

    private void SecondBasic(final PlayerMoveEvent e, double moveSpeed, float yaw, double strafe, double forward) {
        if (forward != 0.0D) {
            if (strafe > 0.0D) {
                yaw += ((forward > 0.0D) ? -45 : 45);
            } else if (strafe < 0.0D) {
                yaw += ((forward > 0.0D) ? 45 : -45);
            }
            strafe = 0.0D;
            if (forward > 0.0D) {
                forward = 1.0D;
            } else if (forward < 0.0D) {
                forward = -1.0D;
            }
        }
        if (strafe > 0.0D) {
            strafe = 1.0D;
        } else if (strafe < 0.0D) {
            strafe = -1.0D;
        }
        double mx = Math.cos(Math.toRadians((yaw + 90.0F)));
        double mz = Math.sin(Math.toRadians((yaw + 90.0F)));
        setMoveSpeed2(e, moveSpeed, strafe);
    }


    private double getSpeed() {
        if (ogSpeedCalc.get())
            return damageBoost.get() && mc.player.hurtTime != 0 ? this.speed.get() + boost.get() : this.speed.get(); // set speed + movement factor

        double speed;

        if (damageBoost.get() && mc.player.hurtTime != 0) {
            speed = this.speed.get() + boost.get();
        } else {
            speed = this.speed.get();
        }

        if (disablerBoost.get() && Modules.get().isActive(Disabler.class) && Modules.get().get(Disabler.class).mode.get().equals(Disabler.DisablerMode.VulcanFullPing)) {
            speed = speed + disablerBoostSetting.get();
        }
        return speed;
    }


public enum MoveMode{
        Basic,
        Basic2,
        Scroll,
        ScrollTest
    }

public enum ExecuteMode{
        Tick,
        Move,
        Update
    }
}
