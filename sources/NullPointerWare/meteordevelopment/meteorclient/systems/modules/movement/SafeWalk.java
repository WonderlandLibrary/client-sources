/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package meteordevelopment.meteorclient.systems.modules.movement;

import meteordevelopment.meteorclient.events.entity.player.ClipAtLedgeEvent;
import meteordevelopment.meteorclient.events.entity.player.SendMovementPacketsEvent;
import meteordevelopment.meteorclient.settings.BoolSetting;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.systems.modules.Categories;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

public final class SafeWalk extends Module {
    public SafeWalk() {
        super(Categories.Movement, "safe-walk", "Prevents you from walking off blocks.");
    }

    private final SettingGroup sgGeneral = settings.getDefaultGroup();
    private final Setting<Boolean> onSneak = sgGeneral.add(new BoolSetting.Builder()
        .name("onSneak")
        .description("Makes you sneak.")
        .defaultValue(true)
        .build()
    );

    private boolean sneakTicks = false;

    @Override
    public void onDeactivate() {
        super.onDeactivate();

        mc.player.setSneaking(false);

    }

    @EventHandler
    private void onUpdate(final SendMovementPacketsEvent.Pre e) {
        if (mc.player == null || mc.world == null) return;
        if (onSneak.get()) {
            if (mc.world.getBlockState(new BlockPos(mc.player.getBlockPos().offset(Direction.Axis.Y, -1))).getBlock() == Blocks.AIR) {
                mc.options.sneakKey.setPressed(true);
                sneakTicks = true;
                return;
            }

            if (sneakTicks) {
                mc.options.sneakKey.setPressed(false);
                sneakTicks = false;
            }
        }
    }


    @EventHandler
    private void onClipAtLedge(final ClipAtLedgeEvent event) {
        assert mc.player != null;
        if (!mc.player.isSneaking()) event.clip = true;
    }
}
