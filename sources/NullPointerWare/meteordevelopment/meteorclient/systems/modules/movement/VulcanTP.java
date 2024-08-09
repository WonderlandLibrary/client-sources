/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package meteordevelopment.meteorclient.systems.modules.movement;

import meteordevelopment.meteorclient.events.entity.player.PlayerMoveEvent;
import meteordevelopment.meteorclient.events.packets.PacketEvent;
import meteordevelopment.meteorclient.events.world.CollisionShapeEvent;
import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.settings.BoolSetting;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.systems.modules.Categories;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.systems.modules.Modules;
import meteordevelopment.meteorclient.systems.modules.world.Timer;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.network.packet.c2s.play.TeleportConfirmC2SPacket;
import net.minecraft.network.packet.s2c.play.PlayerPositionLookS2CPacket;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShapes;

import java.util.Objects;

public final class VulcanTP extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<Boolean> newver = sgGeneral.add(new BoolSetting.Builder()
        .name("new")
        .description("For newer versions (needs special usage), all credits to your_doom")
        .defaultValue(false)
        .build()
    );


    private final Setting<Boolean> illegal = sgGeneral.add(new BoolSetting.Builder()
        .name("illegal")
        .description("Sends illegal move packet to possibly bypass something.")
        .defaultValue(false)
        .visible(() -> !newver.get())
        .build()
    );

    private final Setting<Boolean> fakeconfirm = sgGeneral.add(new BoolSetting.Builder()
        .name("fake-confirm")
        .description("Confirms cancelled flags.")
        .defaultValue(true)
        .visible(() -> !newver.get())
        .build()
    );

    int flags;
    Vec3d flagvec = null;

    Vec3d tpPos;

    Stage stage = Stage.Start;

    int ticks;



    public VulcanTP() {
        super(Categories.World, "vulcan-tp", "ClickTP for Vulcan.");
    }


    @Override
    public void onActivate() {
        if (!newver.get()) {
            flags = 0;

            stage = Stage.Start;


            BlockPos pos;
            BlockHitResult hit = (BlockHitResult) mc.player.raycast(300, mc.getTickDelta(), false);
            boolean miss = hit.getType() == HitResult.Type.MISS;
            pos = miss ? null : hit.getBlockPos();
            Direction dir = Direction.UP;
            if (pos != null) {
                tpPos = Vec3d.ofBottomCenter(pos.offset(dir, 1));
            } else {
                info("Please look at a block, that is atleast 300 blocks in range.");
                this.toggle();
                return;
            }
            if (!mc.world.getBlockState(pos.up(2)).isAir() || !mc.world.getBlockState(pos.up(1)).isAir() || !mc.world.getBlockState(pos.up(3)).isAir()) {
                info("Please look at a block, that has air over it.");
                this.toggle();
                return;
            }
            sendNoEvent(new PlayerMoveC2SPacket.PositionAndOnGround(mc.player.getX(), mc.player.getY() - 0.074, mc.player.getZ(), false));
        } else {
            BlockPos pos;
            BlockHitResult hit = (BlockHitResult) mc.player.raycast(300, mc.getTickDelta(), false);
            boolean miss = hit.getType() == HitResult.Type.MISS;
            pos = miss ? null : hit.getBlockPos();
            Direction dir = Direction.UP;
            if (pos != null) {
                tpPos = Vec3d.ofBottomCenter(pos.offset(dir, 1));
            } else {
                info("Please look at a block, that is atleast 300 blocks in range.");
                this.toggle();
                return;
            }
            if (!mc.world.getBlockState(pos.up(2)).isAir() || !mc.world.getBlockState(pos.up(1)).isAir() || !mc.world.getBlockState(pos.up(3)).isAir()) {
                info("Please look at a block, that has air over it.");
                this.toggle();
                return;
            }

            ticks = (int) (Math.ceil(Math.sqrt(mc.player.squaredDistanceTo(tpPos)) / 10) * 5);
        }
    }


    @Override
    public void onDeactivate() {
        if (!newver.get())
            Modules.get().get(Timer.class).setOverride(Timer.OFF);
    }

    @EventHandler
    private void onMove(final PlayerMoveEvent event) {
        if (ticks > 0)
            event.cancel();
    }

    @EventHandler
    private void onPacketSend(final PacketEvent.Send event) {
        if (event.packet instanceof PlayerMoveC2SPacket.PositionAndOnGround && ticks > 0) {
            event.cancel();
        }
    }

    @EventHandler
    private void onTick(final TickEvent.Pre e){
        if (!newver.get()) {
            if (stage == Stage.Teleport) {
                if (illegal.get())
                    sendNoEvent(new PlayerMoveC2SPacket.PositionAndOnGround(mc.player.getX() + 5000, mc.player.getY(), mc.player.getZ() + 5000, false));
                mc.player.setPosition(tpPos.getX(), tpPos.getY(), tpPos.getZ());
                stage = Stage.Wait;
            }
        } else {
            if (ticks > 0) ticks--;
            else {
                for (int i = 0; i < Math.ceil(Math.sqrt(mc.player.squaredDistanceTo(tpPos)) / 10); i++) {
                    emptyFull();
                }

                sendNoEvent(new PlayerMoveC2SPacket.Full(tpPos.x, tpPos.y, tpPos.z, mc.player.getYaw(), mc.player.getPitch(), true));
                mc.player.setPosition(tpPos);
                this.toggle();
            }
        }
    }


    @EventHandler
    private void onPacketRecieve(final PacketEvent.Receive e) {
        if (!newver.get()) {
            if (e.packet instanceof PlayerPositionLookS2CPacket p) {
                if (!Objects.equals(flagvec, new Vec3d(p.getX(), p.getY(), p.getZ())) && flags >= 3) {
                    this.toggle();
                }
                if (fakeconfirm.get()) sendNoEvent(new TeleportConfirmC2SPacket(p.getTeleportId()));
                e.setCancelled(true);
                flags++;
                if (flags == 1) {
                    flagvec = new Vec3d(p.getX(), p.getY(), p.getZ());
                    stage = Stage.Teleport;
                }
            }
        }
    }

    @EventHandler
    private void onCollsion(final CollisionShapeEvent e) {
        if (!newver.get())
            if (Objects.equals(e.pos, new BlockPos(mc.player.getBlockX(), mc.player.getBlockY() - 1, mc.player.getBlockZ()))) e.shape = VoxelShapes.fullCube();
    }

    public enum Stage {
        Start,
        Teleport,
        Flag,
        Wait
    }
}
