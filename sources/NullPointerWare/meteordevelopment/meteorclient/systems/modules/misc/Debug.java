/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package meteordevelopment.meteorclient.systems.modules.misc;

import meteordevelopment.meteorclient.events.entity.player.PlayerMoveEvent;
import meteordevelopment.meteorclient.events.packets.PacketEvent;
import meteordevelopment.meteorclient.settings.BoolSetting;
import meteordevelopment.meteorclient.settings.PacketListSetting;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.systems.modules.Categories;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.utils.network.PacketUtils;
import meteordevelopment.meteorclient.utils.player.ChatUtils;
import meteordevelopment.meteorclient.utils.player.PlayerUtils;
import meteordevelopment.orbit.EventHandler;
import meteordevelopment.orbit.EventPriority;
import net.minecraft.network.packet.Packet;
import net.minecraft.text.Text;

import java.util.Set;

public final class Debug extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<Set<Class<? extends Packet<?>>>> s2cPackets = sgGeneral.add(new PacketListSetting.Builder()
        .name("S2C-packets")
        .description("Server-to-client packets to cancel.")
        .filter(aClass -> PacketUtils.getS2CPackets().contains(aClass))
        .build()
    );

    private final Setting<Set<Class<? extends Packet<?>>>> c2sPackets = sgGeneral.add(new PacketListSetting.Builder()
        .name("C2S-packets")
        .description("Client-to-server packets to cancel.")
        .filter(aClass -> PacketUtils.getC2SPackets().contains(aClass))
        .build()
    );

    private final Setting<Boolean> velocity = sgGeneral.add(new BoolSetting.Builder()
        .name("Velocity")
        .description("Prints velocity to chat.")
        .defaultValue(true)
        .build()
    );

    public final Setting<Boolean> bhop = sgGeneral.add(new BoolSetting.Builder()
        .name("BHop")
        .description("debug bhop")
        .defaultValue(false)
        .build()
    );

    public final Setting<Boolean> bhopTicks = sgGeneral.add(new BoolSetting.Builder()
        .name("Ticks")
        .description("debug bhop")
        .defaultValue(false)
        .visible(bhop::get)
        .build()
    );


    public Debug() {
        super(Categories.Misc, "Debug", "Fucking Debug Shit.");
    }

    @EventHandler(priority = EventPriority.MEDIUM)
    private void onMove(final PlayerMoveEvent e) {
        if (isNull()) return;;

        if (velocity.get() && PlayerUtils.isMoving()) {
            assert mc.player != null;
            ChatUtils.addMessage(Text.literal("Velocity is " + mc.player.getVelocity()));
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST + 1)
    private void onReceivePacket(PacketEvent.Receive event) {
        if (isNull()) return;;
        if (s2cPackets.get().contains(event.packet.getClass())) ChatUtils.addMessage(Text.literal("Received Packet " + event.packet.getClass().getSimpleName()));
    }

    @EventHandler(priority = EventPriority.HIGHEST + 1)
    private void onSendPacket(PacketEvent.Send event) {
        if (isNull()) return;
        if (c2sPackets.get().contains(event.packet.getClass())) ChatUtils.addMessage(Text.literal("Sent Packet " + event.packet.getClass().getSimpleName()));
    }
}

