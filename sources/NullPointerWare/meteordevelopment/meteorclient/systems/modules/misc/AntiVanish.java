/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package meteordevelopment.meteorclient.systems.modules.misc;

import meteordevelopment.meteorclient.events.packets.PacketEvent;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.systems.modules.Categories;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.entity.Entity;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.EntityS2CPacket;
import net.minecraft.network.packet.s2c.play.EntityStatusEffectS2CPacket;

public final class AntiVanish extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    public AntiVanish() {
        super(Categories.Misc, "AntiVanish", "Detects if someone vanished in the game :)");
    }

    @EventHandler
    private void onPacket(final PacketEvent.Receive e) {
        if (mc.player == null || mc.world == null) return;

        Packet p = e.packet;

        if (p instanceof EntityStatusEffectS2CPacket statusPacket) {
            Entity entity = mc.world.getEntityById((statusPacket.getEntityId()));

            if (entity == null) {
                error("Someone did a vanish >:|.");
            }
        }

        if (p instanceof EntityS2CPacket entityPacket) {
            Entity entity = (entityPacket.getEntity(mc.world));

            if (entity == null) {
                error("Someone did a vanish. The name is ");
            }
        }
    }


}
