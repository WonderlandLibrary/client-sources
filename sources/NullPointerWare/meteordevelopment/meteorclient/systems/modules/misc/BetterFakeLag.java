/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package meteordevelopment.meteorclient.systems.modules.misc;

import meteordevelopment.meteorclient.events.entity.player.SendMovementPacketsEvent;
import meteordevelopment.meteorclient.events.packets.PacketEvent;
import meteordevelopment.meteorclient.events.render.Render3DEvent;
import meteordevelopment.meteorclient.renderer.ShapeMode;
import meteordevelopment.meteorclient.settings.*;
import meteordevelopment.meteorclient.systems.modules.Categories;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.utils.other.TimerMS;
import meteordevelopment.meteorclient.utils.render.WireframeEntityRenderer;
import meteordevelopment.meteorclient.utils.render.color.SettingColor;
import meteordevelopment.orbit.EventHandler;
import meteordevelopment.orbit.EventPriority;
import net.minecraft.client.network.OtherClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.network.packet.Packet;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class BetterFakeLag extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<Integer> delay = sgGeneral.add(new IntSetting.Builder()
        .name("delay")
        .description("How many milliseconds to delay")
        .defaultValue(500)
        .sliderRange(1, 10000)
        .max(15000)
        .build()
    );

    //private final Setting<Set<Class<? extends Packet<?>>>> packets = sgGeneral.add(new PacketListSetting.Builder()
    //    .name("packets")
    //    .description("What packets to delay.")
    //    .filter(aClass -> PacketUtils.getC2SPackets().contains(aClass))
    //    .build()
    //);

    private final Setting<Boolean> renderserver = sgGeneral.add(new BoolSetting.Builder()
        .name("render-serverside")
        .description("Renders serverside position.")
        .defaultValue(true)
        .build()
    );

    private final Setting<SettingColor> color  = sgGeneral.add(new ColorSetting.Builder()
        .name("Color")
        .description("The color of the position entity thing")
        .defaultValue(new SettingColor(205, 205, 205, 127))
        .visible(renderserver::get)
        .build()
    );

    private final Setting<SettingColor> color2  = sgGeneral.add(new ColorSetting.Builder()
        .name("Color-side")
        .description("The color of the position entity thing sides")
        .defaultValue(new SettingColor(205, 205, 205, 127))
        .visible(renderserver::get)
        .build()
    );

    private Vec3d pos = null;

    OtherClientPlayerEntity entity;
    private final List<Packet<?>> packetList = new ArrayList<>();

    private boolean sending;
    // DONE: Render fake lag
    // done?
    private Vec3d lastPos;
    TimerMS timer = new TimerMS();

    //long names funi
//    CopyOnWriteArrayList<LongPacketClass> longPacketClassCopyOnWriteArrayList = new CopyOnWriteArrayList<>();

    public BetterFakeLag() {
        super(Categories.Misc, "betterfakelag", "Fakes lag better.");
    }

    @Override
    public void onActivate() {
        assert mc.player != null;
        assert mc.world != null;
        super.onActivate();
        packetList.clear();
        entity = new OtherClientPlayerEntity(mc.world, mc.getGameProfile());
        lastPos = mc.player.getPos();
        entity.setYaw(0);
        entity.setPitch(0);
        entity.prevYaw = 0;
        entity.prevPitch = 0;
        entity.prevBodyYaw = 0;
        entity.headYaw = 0;
        entity.bodyYaw = 0;
        entity.prevHeadYaw = 0;

        entity.lastRenderX = entity.getX();
        entity.lastRenderY = entity.getY();
        entity.lastRenderZ = entity.getZ();

        entity.setPosition(pos);
    }

    @EventHandler
    private void onRender3D(final Render3DEvent event) {
        if (renderserver.get() && pos != null && entity != null && !Objects.equals(entity.getPos(), mc.player.getPos())) {
            drawBoundingBox(event, entity);
        }
    }

    private void drawBoundingBox(final Render3DEvent event, Entity entity) {
        WireframeEntityRenderer.render(event, entity, 1, color.get(), color2.get(), ShapeMode.Lines);
    }


    @EventHandler
    private void onTick(final SendMovementPacketsEvent.Pre e) {
        if (mc.world == null || mc.player == null) return;;

        if (timer.hasTimePassed(delay.get())) {
            sending = true;
            timer.reset();
            if (packetList.isEmpty()) {
                sending = false;
                return;
            }

            for (Packet<?> packet : packetList) {
                sendNoEvent(packet);
            }

            sending = false;
            packetList.clear();
            if (renderserver.get()) {
                lastPos = mc.player.getPos();
                entity.setPos(lastPos.getX(), lastPos.getY(), lastPos.getZ());
            }
        }
    }

    @Override
    public void onDeactivate() {
        for (Packet<?> packet : packetList) {
            sendNoEvent(packet);
        }

        entity = null;
    }


    @EventHandler(priority = EventPriority.HIGHEST)
    public void onSendPacket(final PacketEvent.Send e) {
        if(!sending) {
            packetList.add(e.packet);
            e.cancel();
        }
    }
}
