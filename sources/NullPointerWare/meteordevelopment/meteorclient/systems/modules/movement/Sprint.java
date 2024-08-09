/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package meteordevelopment.meteorclient.systems.modules.movement;

import meteordevelopment.meteorclient.events.entity.player.PlayerMoveEvent;
import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.settings.EnumSetting;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.systems.modules.Categories;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.utils.player.PlayerUtils;
import meteordevelopment.orbit.EventHandler;

public final class Sprint extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<Mode> mode = sgGeneral.add(new EnumSetting.Builder<Mode>()
        .name("speed-mode")
        .description("What mode of sprinting.")
        .defaultValue(Mode.Strict)
        .build()
    );

    // Removed whenStationary as it was just Rage sprint

    public Sprint() {
        super(Categories.Movement, "sprint", "Automatically sprints.");
    }

    private boolean override;

    private boolean isOverride;

    public void setOverride(boolean override) {
        this.override = override;
        isOverride = true;
    }
    public void resetOverride() {
        this.override = false;
        isOverride = false;
    }

    @Override
    public void onDeactivate() {
        override = false;
        if (mc.player != null)
            mc.player.setSprinting(false);
    }

    private void sprint() {
        if (mc.player.getHungerManager().getFoodLevel() <= 6) return;
        // if isOverride is true, get value of override bool, else use "true" as value
        mc.player.setSprinting(isOverride ? override : true);
    }

    @EventHandler
    private void onTick(final TickEvent.Post event) {
        switch (mode.get()) {
            case Strict -> {
                if (PlayerUtils.isMoving())
                    mc.options.sprintKey.setPressed(true);
            }
            case Rage -> sprint();
        }
    }

    @EventHandler
    private void onMove(final PlayerMoveEvent e) {
        if (mode.get().equals(Mode.RageMove))
            mc.player.setSprinting(true);
    }

    public enum Mode {
        Strict,
        Rage,
        RageMove
    }
}
