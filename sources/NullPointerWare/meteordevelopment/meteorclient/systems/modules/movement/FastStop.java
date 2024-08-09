/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package meteordevelopment.meteorclient.systems.modules.movement;

import meteordevelopment.meteorclient.events.entity.player.PlayerMoveEvent;
import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.settings.BoolSetting;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.systems.modules.Categories;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.systems.modules.misc.Disabler;
import meteordevelopment.meteorclient.utils.player.MoveUtils;
import meteordevelopment.orbit.EventHandler;

import java.util.Objects;

public final class FastStop extends Module {

    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    public final Setting<Boolean> onlyOnGround = sgGeneral.add(new BoolSetting.Builder()
        .name("only-onground")
        .description("Only onground.")
        .defaultValue(false)
        .build()
    );

    public FastStop() {
        super(Categories.Movement, "fast-stop", "Stops movement faster.");
    }

    @EventHandler
    private void onPreTick(final TickEvent.Pre event) {
        assert mc.player != null;
        if (!MoveUtils.isMoving() && (!onlyOnGround.get() || mc.player.isOnGround())) {
            mc.player.setVelocity(0, mc.player.getVelocity().y, 0);
        }
    }
}
