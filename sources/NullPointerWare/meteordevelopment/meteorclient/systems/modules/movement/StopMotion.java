/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package meteordevelopment.meteorclient.systems.modules.movement;

import meteordevelopment.meteorclient.events.entity.player.PlayerMoveEvent;
import meteordevelopment.meteorclient.events.entity.player.SendMovementPacketsEvent;
import meteordevelopment.meteorclient.events.packets.PacketEvent;
import meteordevelopment.meteorclient.settings.BoolSetting;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.systems.modules.Categories;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.network.packet.c2s.play.*;
import net.minecraft.network.packet.s2c.play.EntityVelocityUpdateS2CPacket;

public final class StopMotion extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<Boolean> minemalia = sgGeneral.add(new BoolSetting.Builder()
        .name("Minemalia")
        .description("Cancels fall damage on Vulcan servers...")
        .defaultValue(true)
        .build()
    );

    private int ticks;
    private float yaw, pitch;

//    private boolean missed = false;

    public StopMotion() {
        super(Categories.Movement, "StopMotion", "Stops and cancels all movement.");
    }

    @Override
    public void onActivate() {
        if(mc.player != null) {
            yaw = mc.player.getYaw();
            pitch = mc.player.getPitch();
        }
        super.onActivate();
    }

    @Override
    public void onDeactivate() {
        if (mc.player != null)
            mc.player.setVelocity(0, 0, 0);

        if (minemalia.get())
            mc.player.updatePosition(mc.player.getX(), mc.player.getY() + 0.01, mc.player.getZ());
        ticks = 0;
        super.onDeactivate();
    }

    @EventHandler
    private void onMove(final PlayerMoveEvent e) {
      if (isNull())
          return;

        e.setX(0);
        e.setY(0);
        e.setZ(0);
    }

    @EventHandler
    private void onUpdate(final SendMovementPacketsEvent.Pre event) {
        if (isNull())
            return;

        mc.player.setYaw(yaw);
        mc.player.setPitch(pitch);
        mc.player.setVelocity(0, 0, 0);
        ticks++;
    }

    @EventHandler
    private void onPacketSend(final PacketEvent.Send e) {
        if(e.packet instanceof PlayerMoveC2SPacket.Full || e.packet instanceof PlayerMoveC2SPacket.LookAndOnGround || e.packet instanceof PlayerMoveC2SPacket.PositionAndOnGround ||  e.packet instanceof PlayerMoveC2SPacket.OnGroundOnly|| e.packet instanceof PlayerInteractEntityC2SPacket || e.packet instanceof PlayerInteractItemC2SPacket || e.packet instanceof PlayerInteractBlockC2SPacket || e.packet instanceof ClientCommandC2SPacket) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    private void onPacketReceive(final PacketEvent.Receive e) {
        if(e.packet instanceof EntityVelocityUpdateS2CPacket) {
            e.setCancelled(true);
        }
    }
}
