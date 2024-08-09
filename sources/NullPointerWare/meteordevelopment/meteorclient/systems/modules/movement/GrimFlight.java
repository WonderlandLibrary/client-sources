/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package meteordevelopment.meteorclient.systems.modules.movement;

import meteordevelopment.meteorclient.events.packets.PacketEvent;
import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.mixin.PlayerMoveC2SPacketAccessor;
import meteordevelopment.meteorclient.settings.*;
import meteordevelopment.meteorclient.systems.modules.Categories;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.network.packet.c2s.play.PlayerInputC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.math.Vec3d;

public final class GrimFlight extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    public final Setting<Mode> mode = sgGeneral.add(new EnumSetting.Builder<Mode>()
        .name("Mode")
        .description("Mode to use")
        .defaultValue(Mode.SetPacket)
        .build()
    );


    private final Setting<Integer> position = sgGeneral.add(new IntSetting.Builder()
        .name("position")
        .description("What position should flight offset you to.")
        .defaultValue(500)
        .min(1)
        .max(1000)
        .sliderMax(1000)
        .sliderMin(1)
        .visible(() -> mode.get() == Mode.SetPacket)
        .build()
    );

    public final Setting<PacketMode> packetmode = sgGeneral.add(new EnumSetting.Builder<PacketMode>()
        .name("packet-mode")
        .description("Mode to use with SetPacket mode.")
        .defaultValue(PacketMode.Explosion)
        .visible(() -> mode.get() == Mode.SetPacket)
        .build()
    );

    private final Setting<Boolean> boatup = sgGeneral.add(new BoolSetting.Builder()
        .name("upwards")
        .description("If to only go upwards after exiting boat.")
        .defaultValue(true)
        .visible(() -> mode.get() == Mode.BoatExit)
        .build()
    );

    private final Setting<Boolean> autoexit = sgGeneral.add(new BoolSetting.Builder()
        .name("autoexit")
        .description("If to autoexit the boat.")
        .defaultValue(true)
        .visible(() -> mode.get() == Mode.BoatExit && boatup.get())
        .build()
    );

    public final Setting<Double> motion = sgGeneral.add(new DoubleSetting.Builder()
        .name("motion")
        .description("What should be the motion after exiting boat")
        .defaultValue(1)
        .min(1)
        .max(15)
        .sliderMax(15)
        .sliderMin(1)
        .visible(() -> mode.get() == Mode.BoatExit)
        .build()
    );

    private final Setting<Boolean> disable = sgGeneral.add(new BoolSetting.Builder()
        .name("autodisable")
        .description("If to autodisable the module.")
        .defaultValue(true)
        .visible(() -> mode.get() == Mode.BoatExit)
        .build()
    );

    private final Setting<Boolean> blink = sgGeneral.add(new BoolSetting.Builder()
        .name("blink")
        .description("If flight should abuse lag (WIP)")
        .defaultValue(false)
        .visible(() -> mode.get() == Mode.BoatExit)
        .build()
    );

    boolean wasInBoat = false;
    int tickcounter = 0;


    public GrimFlight() {
        super(Categories.Movement, "grim-flight", "Flight but for grim");
    }

    @Override
    public void onActivate() {
        wasInBoat = false;
        tickcounter = 0;
    }

    @EventHandler
    public void onTick(TickEvent.Pre e) {
        switch (mode.get()) {
            case BoatExit -> {
                if (!wasInBoat && mc.player.getVehicle() != null) {
                    wasInBoat = true;
                } else if (wasInBoat) {
                    if (mc.player.getVehicle() == null && !boatup.get()) {
                        boost();
                        if (disable.get()) this.toggle();
                        wasInBoat = false;
                    }
                    if (boatup.get()) {
                        mc.getNetworkHandler().sendPacket(new PlayerInputC2SPacket(0.0f, 0.0f, false, true));
                        if (mc.player.getVehicle() == null) {
                            mc.getNetworkHandler().sendPacket(new PlayerInputC2SPacket(0.0f, 0.0f, false, false));
                            mc.player.addVelocity(0, motion.get(), 0);
                            if (disable.get()) this.toggle();
                            wasInBoat = false;
                        }
                    }
                }
            }
        }

    }

    @EventHandler
    public void onPacketSend(PacketEvent.Send e) {
        switch (mode.get()) {
            case SetPacket -> {
                if (e.packet instanceof PlayerMoveC2SPacket packet) {
                    ((PlayerMoveC2SPacketAccessor) packet).setX(packet.getX(mc.player.getX()) + position.get());
                    ((PlayerMoveC2SPacketAccessor) packet).setZ(packet.getZ(mc.player.getZ()) + position.get());
                }
            }
        }
    }

    public void boost() {
        assert mc.player != null;
        Vec3d v = mc.player.getRotationVecClient().multiply(motion.get());
        mc.player.addVelocity(v.getX(), v.getY(), v.getZ());
    }



    public enum Mode {
        SetPacket,
        BoatExit
    }

    public enum PacketMode {
        Jump,
        Explosion,
        Always
    }
}
