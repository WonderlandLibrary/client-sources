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
import meteordevelopment.meteorclient.systems.modules.Modules;
import meteordevelopment.meteorclient.systems.modules.combat.Aura;
import meteordevelopment.meteorclient.systems.modules.world.GrimTimer;
import meteordevelopment.meteorclient.utils.Utils;
import meteordevelopment.meteorclient.utils.entity.SortPriority;
import meteordevelopment.meteorclient.utils.entity.TargetUtils;
import meteordevelopment.meteorclient.utils.entity.fakeplayer.FakePlayerEntity;
import meteordevelopment.meteorclient.utils.misc.Keybind;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.network.packet.Packet;
import org.joml.Vector3d;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

public final class BestFakeLag extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();
    private final SettingGroup sgFlush  = settings.createGroup("Flush");
    private final SettingGroup sgRender  = settings.createGroup("Render");

    // General


//    private final Setting<Keybind> cancelBlink = sgGeneral.add(new KeybindSetting.Builder()
//        .name("cancel-blink")
//        .description("Cancels sending packets and sends you back to your original position.")
//        .defaultValue(Keybind.none())
//        .action(() -> {
//            cancelled = true;
//            if (isActive()) toggle();
//        })
//        .build()
//    );

    private final Setting<Boolean> weaponsOnly = sgGeneral.add(new BoolSetting.Builder()
        .name("weapns-only")
        .description("Toggles blink off and on every ? ticks.")
        .defaultValue(false)
        .build()
    );

    private final Setting<List<Item>> weapons = sgGeneral.add(new ItemListSetting.Builder()
        .name("Weapons")
        .description("What counts as a weapon anyway?")
            .visible(weaponsOnly::get)
        .build()
    );


    // Flush

    private final Setting<Keybind> flushKeyBind = sgGeneral.add(new KeybindSetting.Builder()
        .name("flush-keybind")
        .description("the key you can press to flush")
        .defaultValue(Keybind.none())
        .build()
    );

    private final Setting<Boolean> pulse = sgFlush.add(new BoolSetting.Builder()
        .name("pulse")
        .description("Toggles blink off and on every ? ticks.")
        .defaultValue(false)
        .build()
    );

    private final Setting<Integer> pulsePackets = sgFlush.add(new IntSetting.Builder()
        .name("pulse")
        .description("How many packets to wait before pulsing.")
        .defaultValue(1)
        .min(1)
        .sliderMax(100)
        .visible(pulse::get)
        .build()
    );

    private final Setting<Boolean> proximity = sgFlush.add(new BoolSetting.Builder()
        .name("proximity")
        .description("Flushes if you are a certain distance away from the player")
        .defaultValue(false)
        .build()
    );

    private final Setting<Boolean> proximityKa = sgFlush.add(new BoolSetting.Builder()
        .name("proximity-ka")
        .description("Uses the killaura reach as proximity...")
        .defaultValue(false)
        .visible(proximity::get)
        .build()
    );

    private final Setting<Double> proximityDistance = sgFlush.add(new DoubleSetting.Builder()
        .name("proximity")
        .description("How far to be to yk, flush")
        .defaultValue(3)
        .min(0)
        .sliderMax(6)
        .visible(() -> proximity.get() && !proximityKa.get())
        .build()
    );


    private final Setting<Boolean> flushOnHit = sgFlush.add(new BoolSetting.Builder()
        .name("flush-on-git")
        .description("Flushes on hit")
        .defaultValue(true)
        .build()
    );

    private final Setting<Boolean> flushOnDamage = sgFlush.add(new BoolSetting.Builder()
        .name("flush-on-damage")
        .description("Flushes on hit")
        .defaultValue(true)
        .build()
    );

    // Render

    private final Setting<Boolean> renderOriginal = sgRender.add(new BoolSetting.Builder()
        .name("render-original")
        .description("Renders your player model at the original position.")
        .defaultValue(true)
        .build()
    );

    private final List<Packet<?>> packets = new ArrayList<>();
    private final List<Packet<?>> packets2 = new ArrayList<>(packets);
    private Entity closestEntity;
    private FakePlayerEntity model;
    private final Vector3d start = new Vector3d();
    private boolean cancelled = false;
    private int timer = 0;
    private boolean shouldFlush, flushed, flushing, pressed;
    public double x, y, z;

    public BestFakeLag() {
        super(Categories.Movement, "best-fake-lag", "Allows you to essentially teleport while suspending motion updates.");
    }

    @Override
    public void onActivate() {
        flushed = false;
        shouldFlush = false;

        packets.clear();
        packets2.clear();

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
        flushed = false;
        shouldFlush = false;
        cancelled = false;
        closestEntity = null;
    }

    @EventHandler
    private void attackEvent(final AttackEntityEvent e) {
        if (flushOnHit.get())
            shouldFlush = true;
    }

    @EventHandler
    private void onTickPre(final TickEvent.Pre e) {
       closestEntity =  TargetUtils.getPlayerTarget(6, SortPriority.LowestDistance);

       flushed = false;

      if ((proximity.get() && closestEntity.distanceTo(mc.player) <= proximityDistance.get() && !proximityKa.get()) || (proximity.get() && closestEntity.distanceTo(mc.player) <= Modules.get().get(Aura.class).calculateRange() && proximityKa.get()))
         shouldFlush = true;
    }

    @EventHandler
    private void onTick(final TickEvent.Post event){
        if (isNull()) return;

        timer++;

        if (flushKeyBind.get().isPressed()) {
            pressed = true;
        } else if (!flushKeyBind.get().isPressed()) {
            pressed = false;
        }

        if(pressed && mc.currentScreen == null) {
            shouldFlush = true;
            pressed = false;
            return;
        }


        if (weaponsOnly.get() && weapons.get().contains(mc.player.getMainHandStack().getItem())) {
            if (!packets.isEmpty())
                shouldFlush = true;
            return;
        }

        if (mc.player.hurtTime >= 1 && flushOnDamage.get()) {
            shouldFlush = true;
            return;
        }

        if ((packets.size() >= pulsePackets.get() && pulse.get()) ) {
            timer = 0;
            shouldFlush = true;
            x = mc.player.getX();
            y = mc.player.getY();
            z = mc.player.getZ();
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
        try {
            List<Packet<?>> packetsCopy;

            synchronized (packets) {
                packetsCopy = new ArrayList<>(packets);
                flushed = true;
                shouldFlush = false;
                packets.clear();
            }

            if (send) {
                packetsCopy.forEach(mc.player.networkHandler::sendPacket);
            }

            if (model != null) {
                model.despawn();
                model = null;
            }

            packetsCopy.clear();

            timer = 0;
        } catch (Exception e) {
            throw new ConcurrentModificationException(e);
        }
    }
}
