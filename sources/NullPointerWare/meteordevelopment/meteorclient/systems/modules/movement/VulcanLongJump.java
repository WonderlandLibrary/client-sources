/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package meteordevelopment.meteorclient.systems.modules.movement;

import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.settings.BoolSetting;
import meteordevelopment.meteorclient.settings.DoubleSetting;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.systems.modules.Categories;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.network.packet.c2s.play.PlayerInputC2SPacket;
import net.minecraft.util.math.Vec3d;

public final class VulcanLongJump extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<Boolean> boatup = sgGeneral.add(new BoolSetting.Builder()
        .name("upwards")
        .description("If to only go upwards after exiting boat.")
        .defaultValue(true)
        .build()
    );

    private final Setting<Boolean> autoexit = sgGeneral.add(new BoolSetting.Builder()
        .name("autoexit")
        .description("If to autoexit the boat.")
        .defaultValue(false)
        .build()
    );

    public final Setting<Double> motionY = sgGeneral.add(new DoubleSetting.Builder()
        .name("motionY")
        .description("motion stuff...")
        .defaultValue(1)
        .min(1)
        .max(15)
        .sliderMax(15)
        .sliderMin(1)
        .build()
    );

    public final Setting<Double> motionX = sgGeneral.add(new DoubleSetting.Builder()
        .name("motionX")
        .description("motion stuff...")
        .defaultValue(1)
        .min(1)
        .max(15)
        .sliderMax(15)
        .sliderMin(1)
        .build()
    );

    private final Setting<Boolean> disable = sgGeneral.add(new BoolSetting.Builder()
        .name("disable")
        .description("Disables the module after use...")
        .defaultValue(true)
        .build()
    );

    boolean wasInBoat = false;


    int tick;

    public VulcanLongJump() {
        super(Categories.Movement, "VulcantLongJump", "Is Vulcan worth 20$...");
    }

    @Override
    public void onActivate() {
        tick = 0;
        wasInBoat = false;
    }

    @EventHandler
    public void onTick(TickEvent.Pre e) {
        tick++;
        if (!wasInBoat && mc.player.getVehicle() != null) {
            wasInBoat = true;
        } else if (wasInBoat) {
            if (mc.player.getVehicle() == null && !boatup.get()) {
                boost();
                if (disable.get()) this.toggle();
                wasInBoat = false;
            }
            if (boatup.get()) {
                if(tick < 15) {
                    mc.getNetworkHandler().sendPacket(new PlayerInputC2SPacket(0.0f, 0.0f, false, true));
                    if (mc.player.getVehicle() == null) {
                        mc.getNetworkHandler().sendPacket(new PlayerInputC2SPacket(0.0f, 0.0f, false, false));
                        mc.player.addVelocity(motionX.get(), motionY.get(), 0);
                    }
                }
                if(tick > 15) {
                    info("tick: " + tick);
                    if (disable.get()) this.toggle();
                    wasInBoat = false;
                    tick = 0;
                }
            }
        }
    }

    public void boost() {
        assert mc.player != null;
        Vec3d v = mc.player.getRotationVecClient().multiply(motionY.get());
        mc.player.addVelocity(v.getX(), v.getY(), v.getZ());
    }
}
