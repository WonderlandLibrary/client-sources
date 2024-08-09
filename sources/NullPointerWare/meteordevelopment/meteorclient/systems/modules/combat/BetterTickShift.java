/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package meteordevelopment.meteorclient.systems.modules.combat;

import meteordevelopment.meteorclient.events.entity.player.PlayerMoveEvent;
import meteordevelopment.meteorclient.events.meteor.KeyEvent;
import meteordevelopment.meteorclient.events.packets.PacketEvent;
import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.mixininterface.IVec3d;
import meteordevelopment.meteorclient.settings.*;
import meteordevelopment.meteorclient.systems.modules.Categories;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.systems.modules.Modules;
import meteordevelopment.meteorclient.systems.modules.render.Freecam;
import meteordevelopment.meteorclient.systems.modules.world.Timer;
import meteordevelopment.meteorclient.utils.misc.input.Input;
import meteordevelopment.meteorclient.utils.player.PlayerUtils;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.s2c.play.PlayerPositionLookS2CPacket;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.glfw.GLFW;

public final class BetterTickShift extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();
    private final SettingGroup sgCharge = settings.createGroup("Charge");
    private final SettingGroup sgAC = settings.createGroup("Anti Cheat");
    // Charge
    private final Setting<Boolean> charge = sgCharge.add(new BoolSetting.Builder()
        .name("charge")
        .description("Whether or not to charge up your movements.")
        .defaultValue(true)
        .build()
    );

    private final Setting<Integer> chargeTicks = sgCharge.add(new IntSetting.Builder()
        .name("charge-ticks")
        .description("How many ticks to charge up your movement.")
        .defaultValue(30)
        .min(1)
        .sliderRange(1, 50)
        .visible(charge::get)
        .build()
    );

    private final Setting<Boolean> lockMovement = sgCharge.add(new BoolSetting.Builder()
        .name("lock-movement")
        .description("Disables your movement when you are charging.")
        .defaultValue(false)
        .visible(charge::get)
        .build()
    );

    private final Setting<Boolean> chatInfo = sgCharge.add(new BoolSetting.Builder()
        .name("chat-info")
        .description("Tells you when you have finished charging.")
        .defaultValue(false)
        .visible(charge::get)
        .build()
    );


    // General
    private final Setting<Integer> durationTicks = sgGeneral.add(new IntSetting.Builder()
        .name("duration-ticks")
        .description("How many ticks you are allowed to tick shift for.")
        .defaultValue(60)
        .min(1)
        .sliderRange(1, 100)
        .build()
    );

    private final Setting<Double> factor = sgGeneral.add(new DoubleSetting.Builder()
        .name("factor")
        .description("How fast to perform the tick shift.")
        .defaultValue(3.5)
        .min(1)
        .sliderRange(1, 10)
        .build()
    );

    private final Setting<Boolean> onJump = sgGeneral.add(new BoolSetting.Builder()
        .name("on-jump")
        .description("Whether the player needs to jump first or not.")
        .defaultValue(false)
        .build()
    );

    private final Setting<Double> distance = sgGeneral.add(new DoubleSetting.Builder()
        .name("distance")
        .description("How far are you allowed to tick shift for.")
        .defaultValue(12)
        .min(1)
        .sliderRange(1, 20)
        .build()
    );

    private final Setting<Boolean> onRubberband = sgGeneral.add(new BoolSetting.Builder()
        .name("disable-on-rubberband")
        .description("Whether or not to turn off on rubberband.")
        .defaultValue(true)
        .build()
    );


    // Anti Cheat
    private final Setting<Boolean> inWater = sgAC.add(new BoolSetting.Builder()
        .name("in-water")
        .description("Whether or not to allow you to tick shift in water.")
        .defaultValue(false)
        .build()
    );

    private final Setting<Boolean> inLava = sgAC.add(new BoolSetting.Builder()
        .name("in-lava")
        .description("Whether or not to allow you to tick shift in lava.")
        .defaultValue(false)
        .build()
    );

    private final Setting<Boolean> whenSneaking = sgAC.add(new BoolSetting.Builder()
        .name("when-sneaking")
        .description("Allow tick shift when sneaking.")
        .defaultValue(false)
        .build()
    );

    private final Setting<Boolean> hungerCheck = sgAC.add(new BoolSetting.Builder()
        .name("hunger-check")
        .description("Pauses when hunger reaches 3 or less drumsticks")
        .defaultValue(true)
        .build()
    );

    private final Setting<Boolean> step = sgAC.add(new BoolSetting.Builder()
        .name("step")
        .description("Whether or not to allow you to step up on to blocks.")
        .defaultValue(false)
        .build()
    );

    private final Setting<Double> stepHeight = sgAC.add(new DoubleSetting.Builder()
        .name("height")
        .description("How high are you allowed to step.")
        .defaultValue(1)
        .min(0.5)
        .sliderRange(0.5, 10)
        .visible(step::get)
        .build()
    );

    private PlayerEntity target = null;

    public BetterTickShift() {
        super(Categories.Combat, "tick-shift-better", "Timer but bypasses shit");
    }


    private int chargeTicked;
    private int durationTicked;

    private boolean messaged;

    private boolean charged;
    private boolean moved;

    private Vec3d startPos;

//    Timer timerClass = Modules.get().get(Timer.class);
    public Freecam freecam() {return Modules.get().get(Freecam.class);}


    @Override
    public void onActivate() {
        reset();
        startPos = Vec3d.ofCenter(mc.player.getBlockPos());
        messaged = false;
    }

    @Override
    public void onDeactivate() {
        reset();
        startPos = null;
    }

    private void reset() {
        Modules.get().get(Timer.class).setOverride(Timer.OFF);
        // timerClass.setOverride(Timer.OFF);
        chargeTicked = 0;
        durationTicked = 0;

        charged = false;
        moved = false;

        if (step.get()) {
            assert mc.player != null;
            mc.player.setStepHeight(0.5f);
        }
    }

    @EventHandler
    private void onPacketReceive(final PacketEvent.Receive event) {
        if (event.packet instanceof PlayerPositionLookS2CPacket) {
            if (onRubberband.get()) toggle();
        }
    }

    @EventHandler
    private void onKey(final KeyEvent event) {
        if (!charged && lockMovement.get() && !freecam().isActive()) {
            if (Input.isKeyPressed(GLFW.GLFW_KEY_W) || Input.isKeyPressed(GLFW.GLFW_KEY_A) || Input.isKeyPressed(GLFW.GLFW_KEY_S) || Input.isKeyPressed(GLFW.GLFW_KEY_D) || Input.isKeyPressed(GLFW.GLFW_KEY_SPACE))
                event.cancel();
        }
    }

    @EventHandler
    private void onTick(final TickEvent.Pre event) {
        // Check pause settings
        if (mc.player.isFallFlying() || mc.player.isClimbing() || mc.player.getVehicle() != null) return;
        if (!whenSneaking.get() && mc.player.isSneaking()) return;
        if (!inWater.get() && mc.player.isTouchingWater()) return;
        if (!inLava.get() && mc.player.isInLava()) return;
        if (hungerCheck.get() && (mc.player.getHungerManager().getFoodLevel() <= 6)) return;

        // Make sure timer is off firslidet
        Modules.get().get(Timer.class).setOverride(Timer.OFF);

        if (charge.get()) {
            // Charge up meter
            chargeTicked++;
        }

        if (chargeTicked >= chargeTicks.get() || !charge.get()) charged = true;

        if (charged) {
            if (charge.get() && chatInfo.get() && !messaged) {
                warning("Charged!");
                messaged = true;
            }

            if (PlayerUtils.isMoving() && (!onJump.get() || (mc.options.jumpKey.isPressed() && onJump.get()))) moved = true;

            if (moved) {
                // Increment duration ticks
                durationTicked++;

                if (step.get()) {
                    mc.player.setStepHeight(stepHeight.get().floatValue());
                }
            }
        }

        if (startPos != null && (Math.sqrt(mc.player.squaredDistanceTo(startPos)) >= distance.get() || (durationTicked * factor.get()) > durationTicks.get())) {
            toggle();
        }
    }

    @EventHandler
    private void onMove(final PlayerMoveEvent event) {
        if (charged && moved) {
            // Now we override the timer
            Modules.get().get(Timer.class).setOverride(factor.get());

            double moveForward = mc.player.input.movementForward;
            double moveStrafe = mc.player.input.movementSideways;
            double rotationYaw = mc.player.getYaw();
            if (moveForward == 0.0 && moveStrafe == 0.0) {
                ((IVec3d) event.movement).setXZ(0, 0);
            } else {
                if (moveForward != 0.0) {
                    if (moveStrafe > 0.0) {
                        rotationYaw += (moveForward > 0.0 ? -45 : 45);
                    } else if (moveStrafe < 0.0) {
                        rotationYaw += (moveForward > 0.0 ? 45 : -45);
                    }
                    moveStrafe = 0.0;
                }

                moveStrafe = moveStrafe == 0.0 ? moveStrafe : (moveStrafe > 0.0 ? 1.0 : -1.0);
                ((IVec3d) event.movement).setXZ(moveForward * getMaxSpeed() * Math.cos(Math.toRadians(rotationYaw + 90.0) + moveStrafe * getMaxSpeed() * Math.sin(Math.toRadians(rotationYaw + 90.0))), moveForward * getMaxSpeed() * Math.sin(Math.toRadians(rotationYaw + 90.0)) - moveStrafe * getMaxSpeed() * Math.cos(Math.toRadians(rotationYaw + 90.0)));
            }
        }

    }

    private double getMaxSpeed() {
        double defaultSpeed = 0.2873;
        if (mc.player.hasStatusEffect(StatusEffects.SPEED)) {
            int amplifier = mc.player.getStatusEffect(StatusEffects.SPEED).getAmplifier();
            defaultSpeed *= 1.0 + 0.2 * (amplifier + 1);
        }
        if (mc.player.hasStatusEffect(StatusEffects.SLOWNESS)) {
            int amplifier = mc.player.getStatusEffect(StatusEffects.SLOWNESS).getAmplifier();
            defaultSpeed /= 1.0 + 0.2 * (amplifier + 1);
        }
        return defaultSpeed;
    }

    @Override
    public String getInfoString() {
        if (charge.get()) {
            if (chargeTicked < chargeTicks.get()) {
                return chargeTicked + "";
            } else {
                return "Charged";
            }
        } else {
            return durationTicked + "";
        }
    }

}
