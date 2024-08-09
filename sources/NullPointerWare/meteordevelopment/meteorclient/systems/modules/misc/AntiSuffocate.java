/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package meteordevelopment.meteorclient.systems.modules.misc;

import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.settings.BoolSetting;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.systems.modules.Categories;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Direction;

public final class AntiSuffocate extends Module {

    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    public final Setting<Boolean> swing = sgGeneral.add(new BoolSetting.Builder()
        .name("swing")
        .description("Swings players hand.")
        .defaultValue(false)
        .build()
    );

    public AntiSuffocate() {
        super(Categories.Misc, "anti-suffocate", "Tries to stop suffocating.");
    }

    @EventHandler
    private void onTick(final TickEvent.Pre event) {
        if (mc.player.getBlockStateAtPos().isOpaque()) {
            sendNoEvent(new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.START_DESTROY_BLOCK, mc.player.getBlockPos().down(), Direction.UP));
            //sendNoEvent(new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.STOP_DESTROY_BLOCK, mc.player.getBlockPos().down(), Direction.NORTH));
            if (swing.get())
                mc.player.swingHand(Hand.MAIN_HAND);
        }
    }

}
