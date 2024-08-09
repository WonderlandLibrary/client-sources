/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package meteordevelopment.meteorclient.systems.modules.movement;

import meteordevelopment.meteorclient.events.packets.PacketEvent;
import meteordevelopment.meteorclient.events.world.CollisionShapeEvent;
import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.settings.BoolSetting;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.systems.modules.Categories;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.network.packet.s2c.play.PlayerPositionLookS2CPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShapes;

import java.util.Objects;

public final class DownClip extends Module {

    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<Boolean> fakeconfirm = sgGeneral.add(new BoolSetting.Builder()
        .name("fake-confirm")
        .description("Confirms cancelled flags.")
        .defaultValue(true)
        .build()
    );


    public DownClip() {
        super(Categories.Movement, "down-clip", "Clips downwards on some anticheats.");
    }

    int flags = 0;
    Vec3d flagVec;
    int ticks = 0;


    @Override
    public void onActivate() {
        flags = 0;
        ticks = 0;
        flagVec = null;
        if (!mc.player.isOnGround()) {
            error("You need to be on ground!");
            this.toggle();
        }
    }

    @EventHandler
    private void onTick(final TickEvent.Post event) {
        switch (ticks) {
            case 0 -> {
                clip(-0.25);
            }
            case 2 -> {
                clip(1.55);
            }
        }

        ticks++;
    }

    private void clip(Double dist) {
        mc.player.setPosition(mc.player.getX(), mc.player.getY() + dist, mc.player.getZ());
        sendNoEvent(new PlayerMoveC2SPacket.PositionAndOnGround(mc.player.getX(), mc.player.getY(), mc.player.getZ(), mc.player.isOnGround()));
    }

    @EventHandler
    private void onPacketRecieve(final PacketEvent.Receive event) {
        if (event.packet instanceof PlayerPositionLookS2CPacket packet) {
            flags++;
            if (packet.getY() % 1 != 0)
                this.toggle();
            else
                event.cancel();

        }
    }

    @EventHandler
    private void onCollisionShape(final CollisionShapeEvent event) {
        if (Objects.equals(event.pos, new BlockPos(mc.player.getBlockX(), mc.player.getBlockY() - 1, mc.player.getBlockZ()))) event.shape = VoxelShapes.fullCube();
    }

}
