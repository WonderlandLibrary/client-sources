/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package meteordevelopment.meteorclient.systems.modules.movement;

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
import net.minecraft.network.packet.Packet;
import org.joml.Vector3d;

import java.util.ArrayList;
import java.util.List;

public final class Blink extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

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

    private final List<Packet<?>> packets = new ArrayList<>();
    private FakePlayerEntity model;
    private final Vector3d start = new Vector3d();

    private boolean cancelled = false;
    private int timer = 0;
    private boolean checking;

    public Blink() {
        super(Categories.Movement, "blink", "Allows you to essentially teleport while suspending motion updates.");
    }

    @Override
    public void onActivate() {
        checking = false;
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
        dumpPackets(!cancelled);
        if (cancelled) mc.player.setPos(start.x, start.y, start.z);
        cancelled = false;
    }

    @EventHandler
    private void onTick(final TickEvent.Post event){
        timer++;
        if (packets.size() >= pulsePackets.get() && pulse.get()) {
            timer = 0;

            dumpPackets(true);




            if (model != null) {
                model.despawn();
                model = null;
            }


            if (renderOriginal.get()) {
                model = new FakePlayerEntity(mc.player, mc.player.getGameProfile().getName(), 20, true);
                model.doNotPush = true;
                model.hideWhenInsideCamera = true;
                model.spawn();
            }
        }
    }

    @EventHandler
    private void onSendPacket(final PacketEvent.Send event) {
        if (checking) return;
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

    private void dumpPackets(boolean send) {
        synchronized (packets) {
            if (send) {
                checking = true;
                packets.forEach(mc.player.networkHandler::sendPacket);
            }
            packets.clear();
            checking = false;
        }


        if (model != null) {
            model.despawn();
            model = null;
        }

        timer = 0;
    }
}
