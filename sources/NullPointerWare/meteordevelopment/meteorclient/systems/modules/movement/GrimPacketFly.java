/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package meteordevelopment.meteorclient.systems.modules.movement;

import meteordevelopment.meteorclient.events.entity.player.PlayerMoveEvent;
import meteordevelopment.meteorclient.events.packets.PacketEvent;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.systems.modules.Categories;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.utils.player.MoveUtils;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;

public final class GrimPacketFly extends Module {

    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    public GrimPacketFly() {
        super(Categories.Movement, "grim-packetfly", "Flies on oldgrim");
    }

    @EventHandler
    private void onPlayerMove(final PlayerMoveEvent event) {
        MoveUtils.fullFlightMove(event, 0.1, false);
        sendNoEvent(new PlayerMoveC2SPacket.Full(mc.player.getX(), mc.player.getY(), mc.player.getZ(), mc.player.getYaw(), mc.player.getPitch(), mc.player.isOnGround()));
    }

    @EventHandler
    private void onPacketSend(final PacketEvent.Send event) {
        if (event.packet instanceof PlayerMoveC2SPacket packet && packet.changesPosition()) {
            event.cancel();
            sendNoEvent(new PlayerMoveC2SPacket.Full(packet.getX(0), packet.getY(0), packet.getZ(0), mc.player.getYaw(), mc.player.getPitch(), packet.isOnGround()));
        }
    }
}
