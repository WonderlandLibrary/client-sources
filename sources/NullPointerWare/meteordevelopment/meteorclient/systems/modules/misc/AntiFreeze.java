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
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Direction;

import java.util.ArrayList;
import java.util.List;

public final class AntiFreeze extends Module {

    private final SettingGroup sgGeneral = settings.getDefaultGroup();


    List<String> words = new ArrayList<>();

     public AntiFreeze() {
        super(Categories.Misc, "anti-freeze", "Tries to stop suffocating.");
    }

    @Override
    public void onActivate() {
        words.add("freeze");
        words.add("screenshare");
        words.add("frozen");
        words.add("teamspeak");
        words.add("you are frozen");
        words.add("froze");
        words.add("join ts");
        words.add("caught");
        super.onActivate();
    }

    @EventHandler
    private void onTick(final TickEvent.Pre event) {
        HandledScreen<?> chest = mc.currentScreen instanceof HandledScreen<?> ? (HandledScreen<?>) mc.currentScreen : null;

        if (chest != null && words.contains(chest.getTitle().toString().toLowerCase()))
            mc.currentScreen = null;
    }


}
