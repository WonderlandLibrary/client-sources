/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package meteordevelopment.meteorclient.systems.modules.movement;

import meteordevelopment.meteorclient.events.entity.player.PlayerMoveEvent;
import meteordevelopment.meteorclient.events.packets.PacketEvent;
import meteordevelopment.meteorclient.events.world.CollisionShapeEvent;
import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.settings.*;
import meteordevelopment.meteorclient.systems.modules.Categories;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.systems.modules.Modules;
import meteordevelopment.meteorclient.systems.modules.world.GrimTimer;
import meteordevelopment.meteorclient.systems.modules.world.Timer;
import meteordevelopment.meteorclient.utils.misc.Keybind;
import meteordevelopment.meteorclient.utils.world.BlockUtils;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.block.Blocks;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.network.packet.c2s.play.TeleportConfirmC2SPacket;
import net.minecraft.network.packet.s2c.play.PlayerPositionLookS2CPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShapes;

import java.util.Objects;

public final class VulcanFly extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();


    PlayerPositionLookS2CPacket flag;

    private final Setting<Boolean> newver = sgGeneral.add(new BoolSetting.Builder()
        .name("new")
        .description("For newer versions (needs special usage)")
        .defaultValue(false)
        .build()
    );

    private final Setting<Boolean> fastland = sgGeneral.add(new BoolSetting.Builder()
        .name("fast-land")
        .description("Lands easier (shift on top of block while not being inside of a block)")
        .defaultValue(true)
        .visible(newver::get)
        .build()
    );

    private final Setting<Keybind> disablekeybind = sgGeneral.add(new KeybindSetting.Builder()
        .name("disable-bind")
        .description("The keybind to land.")
        .defaultValue(Keybind.none())
            .action(() -> {
                if (flag != null && !fastland.get() && newver.get()) {
                    flag.apply(mc.getNetworkHandler());
                    flag = null;
                    wait = true;
                }
            })
        .visible(() -> !fastland.get() && newver.get())
        .build()
    );



    private final Setting<Boolean> autoflag = sgGeneral.add(new BoolSetting.Builder()
        .name("auto-flag")
        .description("Autoflags.")
        .defaultValue(true)
        .visible(() -> !newver.get())
        .build()
    );

    private final Setting<Boolean> cancelflags = sgGeneral.add(new BoolSetting.Builder()
        .name("cancel-flags")
        .description("Cancels flags.")
        .defaultValue(true)
        .visible(() -> !newver.get())
        .build()
    );

    private final Setting<Boolean> fakeconfirm = sgGeneral.add(new BoolSetting.Builder()
        .name("fake-confirm")
        .description("Confirms cancelled flags.")
        .defaultValue(true)
        .visible(() -> !newver.get() && cancelflags.get())
        .build()
    );

    private final Setting<Double> timerBoost = sgGeneral.add(new DoubleSetting.Builder()
        .name("timer-boost")
        .description("How much to boost movement.")
        .sliderRange(1.0d, 10.0d)
        .defaultValue(2.5d)
        .build()
    );

    int flags;
    boolean wait;



    public VulcanFly() {
        super(Categories.Movement, "vulcan-fly", "Fly for vulcan.");
    }

    @Override
    public void onActivate() {
        wait = false;
        flag = null;
        flags = 0;
        if (autoflag.get() || newver.get()) {
            assert mc.player != null;
            sendNoEvent(new PlayerMoveC2SPacket.PositionAndOnGround(mc.player.getX(), mc.player.getY() - 0.1, mc.player.getZ(), false));
        }
    }

    @Override
    public void onDeactivate() {
        if (flag != null)
            flag.apply(mc.getNetworkHandler());
        flag = null;
        Modules.get().get(Timer.class).setOverride(Timer.OFF);
    }

    @EventHandler
    private void onTick(final TickEvent.Pre e){
        if (flags >= 1) Modules.get().get(Timer.class).setOverride(timerBoost.get());
        if (newver.get() && fastland.get() && BlockUtils.collidable(mc.player.getBlockPos().down()) && mc.options.sneakKey.isPressed() && !wait && mc.player.isOnGround() && !BlockUtils.inside(mc.player, mc.player.getBoundingBox())) {
            if (flag != null) {
                flag.apply(mc.getNetworkHandler());
                flag = null;
                wait = true;
            }
        }
    }

    @EventHandler
    private void onPacketRecieve(final PacketEvent.Receive e) {
        if (e.packet instanceof PlayerPositionLookS2CPacket p && (cancelflags.get() || newver.get())) {
            if (!newver.get()) {
                if (fakeconfirm.get()) sendNoEvent(new TeleportConfirmC2SPacket(p.getTeleportId()));
                e.setCancelled(true);
                flags++;
            } else {
                flags++;
                if (flags == 1) {
                    e.setCancelled(true);
                    flag = p;
                }
                if (flags >= 2) {
                    this.toggle();
                }
            }

        }
    }

    @EventHandler
    private void onCollision(final CollisionShapeEvent e) {
        if (Objects.equals(e.pos, new BlockPos(mc.player.getBlockX(), mc.player.getBlockY() - 1, mc.player.getBlockZ())) && !mc.player.isSneaking()) e.shape = VoxelShapes.fullCube();
        else if (!wait && !Objects.equals(e.pos, new BlockPos(mc.player.getBlockX(), mc.player.getBlockY() - 1, mc.player.getBlockZ()))) e.shape = VoxelShapes.empty();
    }

    @EventHandler
    private void onMove(final PlayerMoveEvent event) {
        if (mc.world.getBlockState(mc.player.getBlockPos().down()).getBlock() != Blocks.AIR && wait) {
            event.setX(0);
            event.setZ(0);
        }
    }

    @Override
    public String getInfoString() {
        return Integer.toString(flags);
    }
}
