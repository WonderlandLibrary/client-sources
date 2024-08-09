/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package meteordevelopment.meteorclient.systems.modules.combat;

import meteordevelopment.meteorclient.events.packets.PacketEvent;
import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.settings.BoolSetting;
import meteordevelopment.meteorclient.settings.IntSetting;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.systems.modules.Categories;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.network.packet.s2c.play.EntityVelocityUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.ExplosionS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerPositionLookS2CPacket;
import net.minecraft.util.math.Direction;

public final class GrimVelocity extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<Boolean> funny = sgGeneral.add(new BoolSetting.Builder()
        .name("funny")
        .description("Send funny.")
        .defaultValue(true)
        .build()
    );

    private final Setting<Boolean> always = sgGeneral.add(new BoolSetting.Builder()
        .name("always")
        .description("Send packet always.")
        .defaultValue(true)
        .build()
    );

    private final Setting<Integer> delay = sgGeneral.add(new IntSetting.Builder()
        .name("delay")
        .description("Delay between flags. Set to 0 to disable.")
        .defaultValue(20)
        .min(0)
        .build()
    );


    int timeout;
    boolean velo = false;



    public GrimVelocity() {
        super(Categories.Combat, "grim-velocity", "Tries to stop velocity on anticheats like grim.");
    }

    @Override
    public void onActivate() {
        velo = false;
    }

    @EventHandler
    public void onTick(TickEvent.Pre e) {
        if (always.get() || velo)
            doFunny();
    }

    @EventHandler
    public void onPacket(PacketEvent.Receive e) {
        if (e.packet instanceof PlayerPositionLookS2CPacket) {
            timeout = delay.get();
        } else if (((e.packet instanceof EntityVelocityUpdateS2CPacket packet && timeout <= 0 && packet.getId() == mc.player.getId()) || e.packet instanceof ExplosionS2CPacket)) {
            e.cancel();
            velo = true;
        } else timeout--;
    }

    private void doFunny() {
        velo = false;
        if (funny.get()) // 1.17 funny
            sendNoEvent(new PlayerMoveC2SPacket.Full(mc.player.getX(), mc.player.getY(), mc.player.getZ(), mc.player.getYaw(), mc.player.getPitch(), mc.player.isOnGround()));
        sendNoEvent(new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.STOP_DESTROY_BLOCK, mc.player.getBlockPos().up(), Direction.DOWN));
    }
}
