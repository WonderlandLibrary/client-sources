/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package meteordevelopment.meteorclient.systems.modules.combat;

import meteordevelopment.meteorclient.events.packets.PacketEvent;
import meteordevelopment.meteorclient.mixininterface.IPlayerInteractEntityC2SPacket;
import meteordevelopment.meteorclient.settings.BoolSetting;
import meteordevelopment.meteorclient.settings.EnumSetting;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.systems.modules.Categories;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.systems.modules.Modules;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;

public final class WTap<PreMotionEvent, AttackEvent> extends Module {
     private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<Mode> mode = sgGeneral.add(new EnumSetting.Builder<Mode>()
        .name("Mode")
        .description("The way you WTap.")
        .defaultValue(Mode.Packet)
        .build()
    );

    private final Setting<Boolean> ka = sgGeneral.add(new BoolSetting.Builder()
        .name("Killaura-Only")
        .description("Only performs kb shit with killaura (useless)")
        .defaultValue(true)
        .build()
    );

    public WTap() {
        super(Categories.Combat, "WTap", "Perdorms more KB on hit! doesn't bipass Grim!");
    }

    @EventHandler
    private void onSendPacket(final PacketEvent.Send event) {

        if (mode.get().equals(Mode.Packet) && event.packet instanceof IPlayerInteractEntityC2SPacket packet && packet.getType() == PlayerInteractEntityC2SPacket.InteractType.ATTACK && mc.player.isOnGround()) {
            Entity entity = packet.getEntity();

            if (!(entity instanceof LivingEntity) || (entity != Modules.get().get(Aura.class).getTarget() && ka.get()))
                return;

            assert mc.player != null;
            mc.player.networkHandler.sendPacket(new ClientCommandC2SPacket(mc.player, ClientCommandC2SPacket.Mode.START_SPRINTING));
        }
    }

    public static int ticks;


    public void onAttackEvent(final AttackEvent event) {
        ticks = 0;
    }

    public void onPreMotion(final PreMotionEvent event) {
        if (mode.get().equals(Mode.Set) && mc.player.isOnGround()) {
            ++ticks;
            if (mc.player.isSprinting()) {
                if (ticks == 2) {
                    mc.player.setSprinting(false);
                }
                if (ticks == 3) {
                    mc.player.setSprinting(true);
                }
            }
        }
    }

    public enum Mode {
        Packet,
        Set
    }

}
