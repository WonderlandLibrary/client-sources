/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package meteordevelopment.meteorclient.systems.modules.movement;

import meteordevelopment.meteorclient.events.entity.player.AttackEntityEvent;
import meteordevelopment.meteorclient.events.game.GameLeftEvent;
import meteordevelopment.meteorclient.events.packets.PacketEvent;
import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.settings.*;
import meteordevelopment.meteorclient.systems.modules.Categories;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.utils.Utils;
import meteordevelopment.meteorclient.utils.entity.fakeplayer.FakePlayerEntity;
import meteordevelopment.meteorclient.utils.misc.Keybind;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.entity.Entity;
import net.minecraft.network.packet.Packet;
import org.joml.Vector3d;

import java.util.ArrayList;
import java.util.List;

public final class Blink2 extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();
    private final SettingGroup sgFlush  = settings.createGroup("Flush");

    private final Setting<Boolean> renderOriginal = sgGeneral.add(new BoolSetting.Builder()
        .name("render-original")
        .description("Renders your player model at the original position.")
        .defaultValue(true)
        .build()
    );

    private final Setting<Keybind> cancelBlink = sgGeneral.add(new KeybindSetting.Builder()
        .name("cancel-blink")
        .description("Cancels sending packets and sends you back to your original position.")
        .defaultValue(Keybind.none())
        .action(() -> {
            cancelled = true;
            if (isActive()) toggle();
        })
        .build()
    );

    private final Setting<Boolean> pulse = sgGeneral.add(new BoolSetting.Builder()
        .name("pulse")
        .description("Toggles blink off and on every ? ticks.")
        .defaultValue(false)
        .build()
    );
    private final Setting<Integer> pulseMaxPackets = sgGeneral.add(new IntSetting.Builder()
        .name("Packets")
        .description("Max amount of packets...")
        .defaultValue(1)
        .min(1)
        .sliderMax(100)
        .visible(pulse::get)
        .build()
    );

    private final Setting<Integer> pulsePackets = sgGeneral.add(new IntSetting.Builder()
        .name("pulse")
        .description("How many packets to wait before pulsing.")
        .defaultValue(1)
        .min(1)
        .sliderMax(100)
        .visible(pulse::get)
        .build()
    );

    private final Setting<Boolean> flushOnDamage = sgFlush.add(new BoolSetting.Builder()
        .name("flush-on-damage")
        .description("Flushes on hit")
        .defaultValue(true)
        .build()
    );

    private final List<Packet<?>> packets = new ArrayList<>();
    private Entity closestEntity;
    private FakePlayerEntity model;
    private final Vector3d start = new Vector3d();
    private boolean cancelled = false;
    private int timer = 0;
    private boolean shouldFlush, flushed, flushing, pressed;
    public double x, y, z;

    public Blink2() {
        super(Categories.Movement, "blink2", "Allows you to essentially teleport while suspending motion updates.");
    }

    @Override
    public void onActivate() {
        if (renderOriginal.get()) {
            model = new FakePlayerEntity(mc.player, mc.player.getGameProfile().getName(), 20, true);
            model.doNotPush = true;
            model.hideWhenInsideCamera = true;
            model.spawn();
        }

        Utils.set(start, mc.player.getPos());
    }

    @Override
    public void onDeactivate() {
        flushPackets(!cancelled);
        if (cancelled) mc.player.setPos(start.x, start.y, start.z);
        cancelled = false;
    }


    @EventHandler
    private void onAttack(final AttackEntityEvent e) {
        shouldFlush = true;
    }

    @EventHandler
    private void onTick(final TickEvent.Post event){
        timer++;

        if (isNull()) return;


        if (mc.player.hurtTime >= 1 && flushOnDamage.get()) {
            shouldFlush = true;
            return;
        }

        if (packets.size() >= pulsePackets.get() && pulse.get()) {
            shouldFlush = true;
        }

        if (shouldFlush)
            flushPackets(true);

        if (renderOriginal.get() && flushed) {
            model = new FakePlayerEntity(mc.player, mc.player.getGameProfile().getName(), 20, true);
            model.doNotPush = true;
            model.hideWhenInsideCamera = true;
            model.spawn();
            flushed = false;
        }
    }

    @EventHandler
    private void onSendPacket(final PacketEvent.Send event) {
        event.cancel();

        synchronized (packets) {
            packets.add(event.packet);
        }
    }

    @Override
    public String getInfoString() {
        return String.format("%.1f", timer / 20f);
    }

    @EventHandler
    private void onGameLeft(final GameLeftEvent e) {
        this.toggle();
    }

    private void flushPackets(boolean send) {
        synchronized (packets) {
            if (send) {
                packets.forEach(mc.player.networkHandler::sendPacket);
            }
            packets.clear();
            flushed = true;
            timer = 0;
        }


        if (model != null) {
            model.despawn();
            model = null;
        }

        shouldFlush = false;

        timer = 0;
    }
}
