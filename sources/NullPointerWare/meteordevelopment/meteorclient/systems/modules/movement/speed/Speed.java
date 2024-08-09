/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package meteordevelopment.meteorclient.systems.modules.movement.speed;

import meteordevelopment.meteorclient.events.entity.player.PlayerMoveEvent;
import meteordevelopment.meteorclient.events.entity.player.SendMovementPacketsEvent;
import meteordevelopment.meteorclient.events.packets.PacketEvent;
import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.settings.*;
import meteordevelopment.meteorclient.systems.modules.Categories;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.systems.modules.Modules;
import meteordevelopment.meteorclient.systems.modules.movement.speed.modes.*;
import meteordevelopment.meteorclient.systems.modules.world.Timer;
import meteordevelopment.meteorclient.utils.player.PlayerUtils;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.entity.MovementType;
import net.minecraft.network.packet.s2c.play.PlayerPositionLookS2CPacket;

public final class Speed extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    public final Setting<SpeedModes> speedMode = sgGeneral.add(new EnumSetting.Builder<SpeedModes>()
        .name("mode")
        .description("The method of applying speed.")
        .defaultValue(SpeedModes.Vanilla)
        .onModuleActivated(speedModesSetting -> onSpeedModeChanged(speedModesSetting.get()))
        .onChanged(this::onSpeedModeChanged)
        .build()
    );

    public final Setting<Integer> friction = sgGeneral.add(new IntSetting.Builder()
        .name("friction")
        .description("The air friction.")
        .defaultValue(160)
        .sliderRange(1, 200)
        .visible(() -> speedMode.get() == SpeedModes.RealStrafe)
        .build()
    );

    public final Setting<Integer> turnrate = sgGeneral.add(new IntSetting.Builder()
        .name("turn-rate")
        .description("The turn rate.")
        .defaultValue(180)
        .sliderRange(0, 180)
        .visible(() -> speedMode.get() == SpeedModes.RealStrafe)
        .build()
    );

    public final Setting<Boolean> grimGround = sgGeneral.add(new BoolSetting.Builder()
        .name("grim-ground")
        .description("If to speed on ground.")
        .defaultValue(false)
        .visible(() -> speedMode.get() == SpeedModes.Grim)
        .build()
    );

    public final Setting<Double> grimGroundAmount = sgGeneral.add(new DoubleSetting.Builder()
        .name("grim-ground-amount")
        .description("The ground amount.")
        .defaultValue(1)
        .min(0)
        .sliderMax(2)
        .visible(() -> speedMode.get() == SpeedModes.Grim && grimGround.get())
        .build()
    );


    public final Setting<Double> grimBoost = sgGeneral.add(new DoubleSetting.Builder()
        .name("grim-boost")
        .description("The boost amount.")
        .defaultValue(1)
        .min(0)
        .sliderMax(2)
        .visible(() -> speedMode.get() == SpeedModes.Grim)
        .build()
    );

    public final Setting<Double> grimDist = sgGeneral.add(new DoubleSetting.Builder()
        .name("grim-distance")
        .description("The distance.")
        .defaultValue(1.5)
        .min(0)
        .sliderMax(3)
        .visible(() -> speedMode.get() == SpeedModes.Grim)
        .build()
    );

    public final Setting<Double> vanillaSpeed = sgGeneral.add(new DoubleSetting.Builder()
        .name("vanilla-speed")
        .description("The speed in blocks per second.")
        .defaultValue(5.6)
        .min(0)
        .sliderMax(20)
        .visible(() -> speedMode.get() == SpeedModes.Vanilla || speedMode.get() == SpeedModes.Disabler)
        .build()
    );

    public final Setting<Boolean>  boostSpeedValue = sgGeneral.add(new BoolSetting.Builder()
        .name("boostSpeedValue")
        .description("")
        .defaultValue(false)
        .visible(() -> speedMode.get() == SpeedModes.VulcantFDP)
        .build()
    );

    public final Setting<Double> boostDelayValue = sgGeneral.add(new DoubleSetting.Builder()
        .name("disabler-range")
        .description("The range that can be added by the disabler being on")
        .defaultValue(3.0)
        .min(0)
        .sliderMax(3)
        .visible(() -> speedMode.get() == SpeedModes.VulcantFDP)
        .build()
    );

    public final Setting<Double> ncpSpeed = sgGeneral.add(new DoubleSetting.Builder()
        .name("strafe-speed")
        .description("The speed.")
        .visible(() -> speedMode.get() == SpeedModes.Strafe)
        .defaultValue(1.6)
        .min(0)
        .sliderMax(3)
        .build()
    );

    public final Setting<Boolean> ncpSpeedLimit = sgGeneral.add(new BoolSetting.Builder()
        .name("speed-limit")
        .description("Limits your speed on servers with very strict anticheats.")
        .visible(() -> speedMode.get() == SpeedModes.Strafe)
        .defaultValue(false)
        .build()
    );


    public final Setting<Double> timer = sgGeneral.add(new DoubleSetting.Builder()
        .name("timer")
        .description("Timer override.")
        .defaultValue(1)
        .min(0.01)
        .sliderMin(0.01)
        .sliderMax(10)
        .build()
    );

    public final Setting<Boolean> inLiquids = sgGeneral.add(new BoolSetting.Builder()
        .name("in-liquids")
        .description("Uses speed when in lava or water.")
        .defaultValue(false)
        .build()
    );

    public final Setting<Boolean> whenSneaking = sgGeneral.add(new BoolSetting.Builder()
        .name("when-sneaking")
        .description("Uses speed when sneaking.")
        .defaultValue(false)
        .build()
    );

    public final Setting<Boolean> vanillaOnGround = sgGeneral.add(new BoolSetting.Builder()
        .name("only-on-ground")
        .description("Uses speed only when standing on a block.")
        .visible(() -> speedMode.get() == SpeedModes.Vanilla || speedMode.get() == SpeedModes.Disabler)
        .defaultValue(false)
        .build()
    );

    //Custom
    public final Setting<Boolean> autojump = sgGeneral.add(new BoolSetting.Builder()
        .name("AutoJump")
        .description("Jumps when your on the ground")
        .defaultValue(true)
        .visible(() -> speedMode.get() == SpeedModes.Custom)
        .build()
    );

    public final Setting<Boolean> MoveOnly = sgGeneral.add(new BoolSetting.Builder()
        .name("MoveOnly")
        .description("Only moves when you are moving")
        .defaultValue(true)
        .visible(() -> speedMode.get() == SpeedModes.Custom && autojump.get())
        .build()
    );

    public final Setting<Boolean> ymotiontoggle = sgGeneral.add(new BoolSetting.Builder()
        .name("YMotion")
        .description("Should Use Y Motion")
        .defaultValue(true)
        .visible(() -> speedMode.get() == SpeedModes.Custom)
        .build()
    );

    public final Setting<Double> ymotion = sgGeneral.add(new DoubleSetting.Builder()
        .name("YMotion Value")
        .description("The Y motion for Custom Speed.")
        .defaultValue(0.0)
        .min(0)
        .sliderMax(1)
        .visible(() -> speedMode.get() == SpeedModes.Custom && ymotiontoggle.get())
        .build()
    );


    private SpeedMode currentMode;

    public Speed() {
        super(Categories.Movement, "speed", "Modifies your movement speed when moving.");

        onSpeedModeChanged(speedMode.get());
    }

    @Override
    public void onActivate() {
        currentMode.onActivate();
    }

    @Override
    public void onDeactivate() {
        Modules.get().get(Timer.class).setOverride(Timer.OFF);
        currentMode.onDeactivate();
    }

    @EventHandler
    private void onPlayerMove(final PlayerMoveEvent event) {
        if (event.type != MovementType.SELF || mc.player.isFallFlying() || mc.player.isClimbing() || mc.player.getVehicle() != null) return;
        if (!whenSneaking.get() && mc.player.isSneaking()) return;
        if (vanillaOnGround.get() && !mc.player.isOnGround() && speedMode.get() == SpeedModes.Vanilla) return;
        if (!inLiquids.get() && (mc.player.isTouchingWater() || mc.player.isInLava())) return;

        if (timer.get() != 1.0) Modules.get().get(Timer.class).setOverride(PlayerUtils.isMoving() ? timer.get() : Timer.OFF);

        currentMode.onMove(event);
    }

    @EventHandler
    private void onPreTick(final TickEvent.Pre event) {
        if (mc.player.isFallFlying() || mc.player.isClimbing() || mc.player.getVehicle() != null) return;
        if (!whenSneaking.get() && mc.player.isSneaking()) return;
        if (vanillaOnGround.get() && !mc.player.isOnGround() && speedMode.get() == SpeedModes.Vanilla) return;
        if (!inLiquids.get() && (mc.player.isTouchingWater() || mc.player.isInLava())) return;

        currentMode.onTick();
    }

    private void onUpdate(final SendMovementPacketsEvent.Pre event) {

    }

    @EventHandler
    private void onPacketReceive(final PacketEvent.Receive event) {
        if (event.packet instanceof PlayerPositionLookS2CPacket) currentMode.onRubberband();
    }

    private void onSpeedModeChanged(SpeedModes mode) {
        switch (mode) {
            case Vanilla -> currentMode = new Vanilla();
            case Disabler -> currentMode = new Disabler();
            case Strafe -> currentMode = new Strafe();
            case RealStrafe -> currentMode = new RealStrafe();
            case Grim -> currentMode = new Grim();
            case Vulcant -> currentMode = new Vulcant();
/*            case VulcantTest -> currentMode = new VulcantTest(); // I will commit this after done*/
            case VulcantFDP -> currentMode = new VulcantFDP();
            case MineMenClub -> currentMode = new MineMenClub();
            case VulcantGround -> currentMode = new VulcantGround();
            // case VerusPacket -> currentMode = new VerusPacket();
            case Custom ->  currentMode = new Custom();
        }
    }

    @Override
    public String getInfoString() {
        return currentMode.getHudString();
    }
}
