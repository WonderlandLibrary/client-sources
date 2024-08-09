/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package meteordevelopment.meteorclient.systems.modules.combat;

import meteordevelopment.meteorclient.events.packets.PacketEvent;
import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.mixin.EntityVelocityUpdateS2CPacketAccessor;
import meteordevelopment.meteorclient.mixininterface.IVec3d;
import meteordevelopment.meteorclient.settings.*;
import meteordevelopment.meteorclient.systems.modules.Categories;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.systems.modules.world.Timer;
import meteordevelopment.meteorclient.utils.math.MathUtils;
import meteordevelopment.meteorclient.utils.other.TimerMS;
import meteordevelopment.meteorclient.utils.player.DamageBoostUtil;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.EntityVelocityUpdateS2CPacket;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.tick.Tick;

public final class VulcanVelocity extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    public final Setting<Double> knockbackHorizontal = sgGeneral.add(new DoubleSetting.Builder()
        .name("knockback-horizontal")
        .description("How much horizontal knockback you will take.")
        .defaultValue(0)
        .sliderMax(1)
        .build()
    );

    public final Setting<Double> knockbackVertical = sgGeneral.add(new DoubleSetting.Builder()
        .name("knockback-vertical")
        .description("How much vertical knockback you will take.")
        .defaultValue(0.4)
        .sliderMax(1)
        .build()
    );

    boolean resetNextTick = false;

    private int ticks = 0;
    private TimerMS timer = new TimerMS();
    private int ticksToWait;


    public VulcanVelocity() {
        super(Categories.Movement, "Vulcan velocity", "A vulcan velocity");
    }

    @EventHandler
    private void onTick(final TickEvent.Post event){
        ++ticks;
        ticksToWait = ticks*50;
    }

    @EventHandler
    private void onTick(final TickEvent.Pre event) {
        if (resetNextTick) {
            resetNextTick = false;
            mc.player.setVelocity(0, 0.4, 0);
            mc.player.jump();
        }
    }

    @EventHandler
    private void onPacketReceive(final PacketEvent.Receive event) {

        Packet<?> p = event.packet;

        if (p instanceof EntityVelocityUpdateS2CPacket packet && packet.getId() == mc.player.getId() && timer.hasTimePassed(ticksToWait)) {

            timer.reset();
            double velX = (packet.getVelocityX() / 8000d - mc.player.getVelocity().x) * knockbackHorizontal.get();
            double velY = (packet.getVelocityY() / 8000d - mc.player.getVelocity().y) * knockbackVertical.get();
            double velZ = (packet.getVelocityZ() / 8000d - mc.player.getVelocity().z) * knockbackHorizontal.get();
            ((EntityVelocityUpdateS2CPacketAccessor) packet).setX((int) (velX * 8000 + mc.player.getVelocity().x * 8000));
            ((EntityVelocityUpdateS2CPacketAccessor) packet).setY((int) (velY * 8000 + mc.player.getVelocity().y * 8000));
            ((EntityVelocityUpdateS2CPacketAccessor) packet).setZ((int) (velZ * 8000 + mc.player.getVelocity().z * 8000));
        }

        if (p instanceof EntityVelocityUpdateS2CPacket packet && packet.getId() == mc.player.getId()) {
            resetNextTick = true;
        }
    }

}
