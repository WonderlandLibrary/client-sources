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
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.math.Vec3d;

public final class Spider extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<Double> speed = sgGeneral.add(new DoubleSetting.Builder()
        .name("climb-speed")
        .description("The speed you go up blocks.")
        .defaultValue(0.2)
        .min(0.0)
        .build()
    );

    private final Setting<Boolean> spoof = sgGeneral.add(new BoolSetting.Builder()
        .name("spoof")
        .description("Spoofs position.")
        .defaultValue(false)
        .build()
    );

    private final Setting<Double> position = sgGeneral.add(new DoubleSetting.Builder()
        .name("spoof-position")
        .description("The position spoof uses.")
        .defaultValue(0.061)
        .sliderMin(0)
        .sliderMax(1)
        .visible(spoof::get)
        .build()
    );

    private final Setting<Integer> delay = sgGeneral.add(new IntSetting.Builder()
        .name("delay")
        .description("The delay between movements in ticks.")
        .defaultValue(6)
        .min(0)
        .sliderMax(200)
        .visible(spoof::get)
        .build()
    );

    private final Setting<Boolean> limit = sgGeneral.add(new BoolSetting.Builder()
        .name("limit")
        .description("Wont set motion if limit is passed.")
        .defaultValue(false)
        .build()
    );

    private final Setting<Double> limitvalue = sgGeneral.add(new DoubleSetting.Builder()
        .name("limit-motion")
        .description("The motion limit uses.")
        .defaultValue(0.05)
        .defaultValue(0.2)
        .min(0.0)
        .visible(limit::get)
        .build()
    );

    int ticks;
    boolean back;


    public Spider() {
        super(Categories.Movement, "spider", "Allows you to climb walls like a spider.");
    }

    @Override
    public void onActivate() {
        ticks = 0;
        back = false;
    }

    @EventHandler
    private void onTick(final TickEvent.Post event){
        if (!mc.player.horizontalCollision) return;

        Vec3d velocity = mc.player.getVelocity();
        if (velocity.y >= limitvalue.get() && limit.get()) return;
        if (spoof.get()) {
            ticks++;
            if (ticks >= delay.get()) {
                back = !back;
                ticks = 0;
                mc.player.setVelocity(velocity.x, speed.get(), velocity.z);
            }
        } else {
            mc.player.setVelocity(velocity.x, speed.get(), velocity.z);
        }
    }

    @EventHandler
    public void onPacketSend(PacketEvent.SendBypass event) {
        if (!spoof.get() || !mc.player.horizontalCollision || !back) return;
        if (event.packet instanceof PlayerMoveC2SPacket.Full) {
            Vec3d forward = Vec3d.fromPolar(0, mc.player.getYaw()).normalize();
            ((PlayerMoveC2SPacketAccessor) event.packet).setX(mc.player.getX() + forward.x * position.get());
            ((PlayerMoveC2SPacketAccessor) event.packet).setZ(mc.player.getZ() + forward.z * position.get());
        }
        if (event.packet instanceof PlayerMoveC2SPacket.PositionAndOnGround) {
            Vec3d forward = Vec3d.fromPolar(0, mc.player.getYaw()).normalize();
            ((PlayerMoveC2SPacketAccessor) event.packet).setX(mc.player.getX() + forward.x * position.get());
            ((PlayerMoveC2SPacketAccessor) event.packet).setZ(mc.player.getZ() + forward.z * position.get());
        }
        if (event.packet instanceof PlayerMoveC2SPacket p && p.changesPosition()) {
            Vec3d forward = Vec3d.fromPolar(0, mc.player.getYaw()).normalize();
            ((PlayerMoveC2SPacketAccessor) event.packet).setX(mc.player.getX() + forward.x * position.get());
            ((PlayerMoveC2SPacketAccessor) event.packet).setZ(mc.player.getZ() + forward.z * position.get());
        }
    }
}
