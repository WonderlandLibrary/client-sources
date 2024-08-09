/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package meteordevelopment.meteorclient.systems.modules.movement;

import meteordevelopment.meteorclient.events.entity.player.PlayerMoveEvent;
import meteordevelopment.meteorclient.events.packets.PacketEvent;
import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.systems.modules.Categories;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.utils.player.MoveUtils;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.network.packet.s2c.play.PlayerPositionLookS2CPacket;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public final class Phase extends Module {

    public Phase() {
        super(Categories.Movement, "phase", "Phases through blocks.");
    }

    int ticks;

    boolean doclip = false;
    boolean flagged = false;
    boolean waitflag = false;



    @Override
    public void onActivate() {
        ticks = 0;
        doclip = false;
        flagged = false;
        waitflag = false;
    }

    @EventHandler
    private void onTick(final TickEvent.Pre event) {
        if (mc.player.horizontalCollision) doclip = true;
        if (!doclip) return;

        switch (ticks) {
            case 0 -> {
                mc.player.updatePosition(mc.player.getX() - sin(MoveUtils.getDirection()) * 0.06, mc.player.getY(), mc.player.getZ() + cos(MoveUtils.getDirection()) * 0.06);
            }
            case 1 -> {
                // flag packet
                sendNoEvent(new PlayerMoveC2SPacket.Full(mc.player.getX() - sin(MoveUtils.getDirection()) * 0.06, mc.player.getY(), mc.player.getZ() + cos(MoveUtils.getDirection()) * 0.06, mc.player.getYaw(), mc.player.getPitch(), mc.player.isOnGround()));
                waitflag = true;
            }
            case 5 -> {
                //idfk
                doclip = false;
                ticks = 0;
                flagged = false;
                return;
            }
        }


        ticks++;


        if (flagged) {
            mc.player.updatePosition(mc.player.getX() - sin(MoveUtils.getDirection()) * 1.5, mc.player.getY(), mc.player.getZ() + cos(MoveUtils.getDirection()) * 1.5);
            doclip = false;
            ticks = 0;
            flagged = false;
        }
    }

    @EventHandler
    private void onPacketRecieve(final PacketEvent.Receive event) {
        if (event.packet instanceof PlayerPositionLookS2CPacket && waitflag) {
            waitflag = false;
            flagged = true;
        }
    }

    @EventHandler
    private void onMove(final PlayerMoveEvent event) {
        if (doclip)
            event.cancel();
    }
}
