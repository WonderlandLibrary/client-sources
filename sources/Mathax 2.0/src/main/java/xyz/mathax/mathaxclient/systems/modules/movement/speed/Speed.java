package xyz.mathax.mathaxclient.systems.modules.movement.speed;

import net.minecraft.entity.MovementType;
import net.minecraft.network.packet.s2c.play.PlayerPositionLookS2CPacket;
import xyz.mathax.mathaxclient.eventbus.EventHandler;
import xyz.mathax.mathaxclient.events.entity.player.PlayerMoveEvent;
import xyz.mathax.mathaxclient.events.packets.PacketEvent;
import xyz.mathax.mathaxclient.events.world.TickEvent;
import xyz.mathax.mathaxclient.settings.*;
import xyz.mathax.mathaxclient.systems.modules.Category;
import xyz.mathax.mathaxclient.systems.modules.Module;
import xyz.mathax.mathaxclient.systems.modules.Modules;
import xyz.mathax.mathaxclient.systems.modules.movement.speed.modes.Strafe;
import xyz.mathax.mathaxclient.systems.modules.movement.speed.modes.Vanilla;
import xyz.mathax.mathaxclient.systems.modules.world.Timer;
import xyz.mathax.mathaxclient.utils.player.PlayerUtils;

public class Speed extends Module {
    private SpeedMode currentMode;

    private final SettingGroup generalSettings = settings.createGroup("General");

    // General

    public final Setting<SpeedModes> speedModeSetting = generalSettings.add(new EnumSetting.Builder<SpeedModes>()
            .name("Mode")
            .description("The method of applying speed.")
            .defaultValue(SpeedModes.Vanilla)
            .onModuleEnabled(value -> onSpeedModeChanged(value.get()))
            .onChanged(this::onSpeedModeChanged)
            .build()
    );

    public final Setting<Double> vanillaSpeedSetting = generalSettings.add(new DoubleSetting.Builder()
            .name("Vanilla speed")
            .description("The speed in blocks per second.")
            .defaultValue(5.6)
            .min(0)
            .sliderRange(0, 20)
            .visible(() -> speedModeSetting.get() == SpeedModes.Vanilla)
            .build()
    );

    public final Setting<Double> ncpSpeedSetting = generalSettings.add(new DoubleSetting.Builder()
            .name("Strafe speed")
            .description("The speed.")
            .visible(() -> speedModeSetting.get() == SpeedModes.Strafe)
            .defaultValue(1.6)
            .min(0)
            .sliderRange(0, 3)
            .build()
    );

    public final Setting<Boolean> ncpSpeedLimitSetting = generalSettings.add(new BoolSetting.Builder()
            .name("Speed limit")
            .description("Limit your speed on servers with very strict anti-cheats.")
            .visible(() -> speedModeSetting.get() == SpeedModes.Strafe)
            .defaultValue(false)
            .build()
    );

    public final Setting<Double> timerSetting = generalSettings.add(new DoubleSetting.Builder()
            .name("Timer")
            .description("Timer override.")
            .defaultValue(1)
            .min(0.01)
            .sliderRange(0.01, 10)
            .build()
    );

    public final Setting<Boolean> inLiquidsSetting = generalSettings.add(new BoolSetting.Builder()
            .name("In liquids")
            .description("Use speed when in lava or water.")
            .defaultValue(false)
            .build()
    );

    public final Setting<Boolean> whenSneakingSetting = generalSettings.add(new BoolSetting.Builder()
            .name("When sneaking")
            .description("Use speed when sneaking.")
            .defaultValue(false)
            .build()
    );

    public final Setting<Boolean> vanillaOnGroundSetting = generalSettings.add(new BoolSetting.Builder()
            .name("Only on ground")
            .description("Use speed only when standing on a block.")
            .visible(() -> speedModeSetting.get() == SpeedModes.Vanilla)
            .defaultValue(false)
            .build()
    );

    public Speed(Category category) {
        super(category, "Speed", "Modifies your movement speed when moving on the ground.");

        onSpeedModeChanged(speedModeSetting.get());
    }

    @Override
    public void onEnable() {
        currentMode.onEnable();
    }

    @Override
    public void onDisable() {
        Modules.get().get(Timer.class).setOverride(Timer.OFF);

        currentMode.onDisable();
    }

    @EventHandler
    private void onPlayerMove(PlayerMoveEvent event) {
        if (event.type != MovementType.SELF || mc.player.isFallFlying() || mc.player.isClimbing() || mc.player.getVehicle() != null) {
            return;
        }

        if (!whenSneakingSetting.get() && mc.player.isSneaking()) {
            return;
        }

        if (vanillaOnGroundSetting.get() && !mc.player.isOnGround() && speedModeSetting.get() == SpeedModes.Vanilla) {
            return;
        }

        if (!inLiquidsSetting.get() && (mc.player.isTouchingWater() || mc.player.isInLava())) {
            return;
        }

        Modules.get().get(Timer.class).setOverride(PlayerUtils.isMoving() ? timerSetting.get() : Timer.OFF);

        currentMode.onMove(event);
    }

    @EventHandler
    private void onPreTick(TickEvent.Pre event) {
        if (mc.player.isFallFlying() || mc.player.isClimbing() || mc.player.getVehicle() != null) {
            return;
        }

        if (!whenSneakingSetting.get() && mc.player.isSneaking()) {
            return;
        }

        if (vanillaOnGroundSetting.get() && !mc.player.isOnGround() && speedModeSetting.get() == SpeedModes.Vanilla) {
            return;
        }

        if (!inLiquidsSetting.get() && (mc.player.isTouchingWater() || mc.player.isInLava())) {
            return;
        }

        currentMode.onTick();
    }

    @EventHandler
    private void onPacketReceive(PacketEvent.Receive event) {
        if (event.packet instanceof PlayerPositionLookS2CPacket) {
            currentMode.onRubberband();
        }
    }

    private void onSpeedModeChanged(SpeedModes mode) {
        switch (mode) {
            case Vanilla -> currentMode = new Vanilla();
            case Strafe -> currentMode = new Strafe();
        }
    }

    @Override
    public String getInfoString() {
        return currentMode.getHudString();
    }
}