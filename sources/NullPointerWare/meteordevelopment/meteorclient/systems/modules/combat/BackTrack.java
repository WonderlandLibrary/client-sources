/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package meteordevelopment.meteorclient.systems.modules.combat;

import meteordevelopment.meteorclient.events.entity.player.AttackEntityEvent;
import meteordevelopment.meteorclient.events.packets.PacketEvent;
import meteordevelopment.meteorclient.events.render.Render3DEvent;
import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.renderer.ShapeMode;
import meteordevelopment.meteorclient.settings.*;
import meteordevelopment.meteorclient.systems.modules.Categories;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.utils.network.LongPacketClass;
import meteordevelopment.meteorclient.utils.player.PlayerUtils;
import meteordevelopment.meteorclient.utils.render.color.SettingColor;
import meteordevelopment.orbit.EventHandler;
import meteordevelopment.orbit.EventPriority;
import net.minecraft.entity.Entity;
import net.minecraft.entity.TrackedPosition;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.c2s.play.CommandExecutionC2SPacket;
import net.minecraft.network.packet.s2c.common.DisconnectS2CPacket;
import net.minecraft.network.packet.s2c.play.*;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public final class BackTrack extends Module {

    // TODO: Add box or render of player.
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<Mode> mode = sgGeneral.add(new EnumSetting.Builder<Mode>()
        .name("Mode")
        .description("What mode to use.")
        .defaultValue(Mode.Lag)
        .build()
    );

    private final Setting<Integer> ticks = sgGeneral.add(new IntSetting.Builder()
        .name("ticks")
        .description("How many ticks to delay")
        .defaultValue(20)
        .sliderRange(1, 60)
        .visible(() -> mode.get() == Mode.Dump)
        .build()
    );

    private final Setting<Integer> delay = sgGeneral.add(new IntSetting.Builder()
        .name("delay")
        .description("How many milliseconds to delay")
        .defaultValue(500)
        .sliderRange(1, 10000)
        .visible(() -> mode.get() == Mode.Lag)
        .build()
    );

    private final Setting<Double> range = sgGeneral.add(new DoubleSetting.Builder()
        .name("range")
        .description("The maximum range the entity can be to backtrack it.")
        .defaultValue(4.5)
        .min(0)
        .sliderMax(6)
        .build()
    );

    private final Setting<Boolean> smart = sgGeneral.add(new BoolSetting.Builder()
        .name("smart")
        .description("Stop backtracking if entity would be closer if we didnt backtrack.")
        .defaultValue(true)
        .build()
    );

    private final Setting<Boolean> stoprange = sgGeneral.add(new BoolSetting.Builder()
        .name("stop-range")
        .description("Stop backtracking if entity is getting out of range.")
        .defaultValue(true)
        .build()
    );

    private final Setting<Boolean> render = sgGeneral.add(new BoolSetting.Builder()
        .name("render-box")
        .description("Render a box.")
        .defaultValue(false)
        .build()
    );

    public final Setting<ShapeMode> shapeMode = sgGeneral.add(new EnumSetting.Builder<ShapeMode>()
        .name("shape-mode")
        .description("How the shapes are rendered.")
        .defaultValue(ShapeMode.Both)
        .visible(render::get)
        .build()
    );

    private final Setting<SettingColor> sidecolor = sgGeneral.add(new ColorSetting.Builder()
        .name("players-color")
        .description("The other player's color.")
        .defaultValue(new SettingColor(255, 255, 255))
        .visible(render::get)
        .build()
    );

    private final Setting<SettingColor> linecolor = sgGeneral.add(new ColorSetting.Builder()
        .name("players-color")
        .description("The other player's color.")
        .defaultValue(new SettingColor(255, 255, 255))
        .visible(render::get)
        .build()
    );



    final CopyOnWriteArrayList<LongPacketClass> packetList = new CopyOnWriteArrayList<>();
    int tickcounter = 0;


    Entity target;
    Entity fakeplayer;
    boolean dumpNextTick;
    Vec3d pos;

    public BackTrack() {
        super(Categories.Combat, "backtrack", "Abuses \"lag\" to hit enemies from far away.");
    }


    @Override
    public void onActivate() {
        tickcounter = 0;
        dumpNextTick = false;
        dump(false);
    }


    @Override
    public void onDeactivate() {
        dump(true);
    }

    private void dump(boolean handle) {
        if (handle)
            for (LongPacketClass packet : packetList) { packetList.remove(packet); packet.getPacket().apply(mc.getNetworkHandler()); }
        else
            packetList.clear();

        fakeplayer = null;
        target = null;
        pos = null;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    private void onPacketRecieve(final PacketEvent.Receive e) {
        if (!cancelPackets(target)) return;

        if (e.packet instanceof PlayerPositionLookS2CPacket || e.packet instanceof DisconnectS2CPacket) {
            dumpNextTick = true;
        } else if (e.packet instanceof HealthUpdateS2CPacket packet) {
            // Catch this here if the health is more than 0, so that we can see health with backtrack.
            if (packet.getHealth() <= 0)
                dumpNextTick = true;
        } else if (!(e.packet instanceof ChatMessageS2CPacket) && !(e.packet instanceof ProfilelessChatMessageS2CPacket) && !(e.packet instanceof GameMessageS2CPacket) && !(e.packet instanceof CommandExecutionC2SPacket) && !(e.packet instanceof WorldTimeUpdateS2CPacket)) {
            // Dont add packet to list if its a chat packet, or if its a worldtime packet (otherwise would trigger lag hud)
            if (e.packet instanceof EntityS2CPacket &&
                ((EntityS2CPacket) e.packet).getEntity(mc.world) == target ||
                e.packet instanceof EntityPositionS2CPacket &&
                    ((EntityPositionS2CPacket) e.packet).getId() == target.getId()) {

                if (e.packet instanceof EntityS2CPacket) {
                    pos = new Vec3d(pos.x + ((EntityS2CPacket) e.packet).getDeltaX() / 4096.0, pos.y + ((EntityS2CPacket) e.packet).getDeltaY() / 4096.0, pos.z + ((EntityS2CPacket) e.packet).getDeltaZ() / 4096.0);
                } else {
                    pos = new Vec3d(((EntityPositionS2CPacket) e.packet).getX(), ((EntityPositionS2CPacket) e.packet).getY(), ((EntityPositionS2CPacket) e.packet).getZ());
                }
                // Squared, forgot it was meant to be sqrt
                if (MathHelper.sqrt((float) mc.player.squaredDistanceTo(pos)) > range.get() && stoprange.get()) {
                    dumpNextTick = true;
                    return;
                }

                if (MathHelper.sqrt((float) mc.player.squaredDistanceTo(pos)) < MathHelper.sqrt((float) mc.player.squaredDistanceTo(target.getPos())) && smart.get()) {
                    dumpNextTick = true;
                    return;
                }


            }

            e.cancel();
            packetList.add(new LongPacketClass(e.packet, System.currentTimeMillis()));
        }
    }

    @EventHandler
    private void onRender3D(final Render3DEvent event) {
        if (render.get() && target != null) {
            double x = MathHelper.lerp(event.tickDelta, fakeplayer.lastRenderX, fakeplayer.getX()) - fakeplayer.getX();
            double y = MathHelper.lerp(event.tickDelta, fakeplayer.lastRenderY, fakeplayer.getY()) - fakeplayer.getY();
            double z = MathHelper.lerp(event.tickDelta, fakeplayer.lastRenderZ, fakeplayer.getZ()) - fakeplayer.getZ();

            Box box = fakeplayer.getBoundingBox();
            event.renderer.box(x + box.minX, y + box.minY, z + box.minZ, x + box.maxX, y + box.maxY, z + box.maxZ, sidecolor.get(), linecolor.get(), shapeMode.get(), 0);
            fakeplayer.lastRenderX = fakeplayer.getX(); fakeplayer.lastRenderY = fakeplayer.getY(); fakeplayer.lastRenderZ = fakeplayer.getZ();
        }
    }

    @EventHandler
    private void onTick(final TickEvent.Pre event) {
        if (mc.world == null) dump(false);
        else if (cancelPackets(target)) {
            if (dumpNextTick) {
                dumpNextTick = false;
                dump(true);
                return;
            }
            switch (mode.get()) {
                case Lag -> {
                    for (LongPacketClass packet : packetList) {
                        if (packet.getId() + delay.get() < System.currentTimeMillis() && packet.getPacket() != null) {
                            packetList.remove(packet);
                            packet.getPacket().apply((mc.getNetworkHandler()));
                        }
                    }
                }

                case Dump -> {
                    if (tickcounter >= ticks.get()) {
                        dump(true);
                        tickcounter = 0;
                    } else
                        tickcounter++;
                }
            }
        } else {
            dump(true);
        }
    }

    @EventHandler
    private void onAttack(final AttackEntityEvent event) {
        if (!cancelPackets(event.entity)) {
            return;
        }


        if (event.entity != target) {
            dump(true);
            pos = event.entity.getPos();
            fakeplayer = new PlayerEntity(mc.world, event.entity.getBlockPos(), 90, mc.getGameProfile()) {
                @Override
                public boolean isSpectator() {
                    return false;
                }

                @Override
                public boolean isCreative() {
                    return false;
                }
            };
            fakeplayer.setPosition(event.entity.getPos());
        }


        target = event.entity;


    }

    private boolean cancelPackets(Entity entity) {
        return entity != null && entity.isAlive();
    }


    public enum Mode {
        Lag,
        Dump
    }
}
