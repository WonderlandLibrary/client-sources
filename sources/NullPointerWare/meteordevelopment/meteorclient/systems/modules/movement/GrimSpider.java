/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package meteordevelopment.meteorclient.systems.modules.movement;

import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.systems.modules.Categories;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.utils.player.MoveUtils;
import meteordevelopment.meteorclient.utils.player.PlayerUtils;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.util.math.Direction;

public final class GrimSpider extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    public GrimSpider() {
        super(Categories.Movement, "grim-spider", "A spider for grim...");
    }

    @Override
    public void onActivate() {

    }

    @EventHandler
    private void onTick(final TickEvent.Pre event) {
        sendNoEvent(new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.STOP_DESTROY_BLOCK, mc.player.getBlockPos(), Direction.DOWN));
        sendNoEvent(new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.STOP_DESTROY_BLOCK, mc.player.getBlockPos().up(), Direction.DOWN));
        sendNoEvent(new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.STOP_DESTROY_BLOCK, mc.player.getBlockPos().up().up(), Direction.DOWN));
        sendNoEvent(new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.STOP_DESTROY_BLOCK, mc.player.getBlockPos().offset(mc.player.getHorizontalFacing()), Direction.DOWN));
        sendNoEvent(new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.STOP_DESTROY_BLOCK, mc.player.getBlockPos().offset(mc.player.getHorizontalFacing()).up(), Direction.DOWN));
        sendNoEvent(new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.STOP_DESTROY_BLOCK, mc.player.getBlockPos().offset(mc.player.getHorizontalFacing()).up().up(), Direction.DOWN));
    }
}
