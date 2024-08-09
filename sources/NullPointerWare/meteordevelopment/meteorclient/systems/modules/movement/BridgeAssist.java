/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package meteordevelopment.meteorclient.systems.modules.movement;

import meteordevelopment.meteorclient.events.entity.player.ClipAtLedgeEvent;
import meteordevelopment.meteorclient.events.entity.player.SendMovementPacketsEvent;
import meteordevelopment.meteorclient.events.meteor.KeyEvent;
import meteordevelopment.meteorclient.settings.BoolSetting;
import meteordevelopment.meteorclient.settings.DoubleSetting;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.systems.modules.Categories;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.glfw.GLFW;

import static net.minecraft.block.Blocks.AIR;

public final class BridgeAssist extends Module {
    public BridgeAssist() {
        super(Categories.Movement, "BridgeAssist", "Helps you with the bridging.");
    }

    private final SettingGroup sgGeneral = settings.getDefaultGroup();
    private final Setting<Boolean> onSneak = sgGeneral.add(new BoolSetting.Builder()
        .name("onSneak")
        .description("Makes you sneak.")
        .defaultValue(true)
        .build()
    );

    private final Setting<Boolean> sneakPressed = sgGeneral.add(new BoolSetting.Builder()
        .name("sneakPressed")
        .description("Only if sneak is pressed.")
        .defaultValue(true)
        .visible(onSneak::get)
        .build()
    );

    private final Setting<Boolean> onLookDown = sgGeneral.add(new BoolSetting.Builder()
        .name("onLookDown")
        .description("Makes you sneak when looking down.")
        .defaultValue(true)
        .build()
    );

    private final Setting<Double> pitch = sgGeneral.add(new DoubleSetting.Builder()
        .name("pitch")
        .description("The speed in blocks per second.")
        .defaultValue(90)
        .min(70)
        .sliderMax(90)
        .visible(onLookDown::get)
        .build()
    );

    private boolean sneakTicks = false;
    private boolean holdingShift = false;

    // DONE: figure out if the clip on ledge works
    // it works

    @EventHandler
    private void onUpdate(final SendMovementPacketsEvent.Pre e) {
        if (mc.player == null || mc.world == null) return;

        if (onLookDown.get() && !(mc.player.getPitch() >= pitch.get()))
            return;

        if (!holdingShift && sneakPressed.get())
            return;

        if(onSneak.get()) {
            if(mc.world.getBlockState(new BlockPos((int) mc.player.getX(), (int) (mc.player.getBoundingBox().minY - 1), (int) mc.player.getZ())).getBlock() == AIR) {
                mc.options.sneakKey.setPressed(true);
                sneakTicks = true;
                return;
            }

            if(sneakTicks) {
                mc.options.sneakKey.setPressed(false);
                sneakTicks = false;
            }
        }
    }

    @EventHandler
    private void onClipAtLedge(final ClipAtLedgeEvent event) {

        if (this.isNull()) return;

        // Check if player is looking down
        if (onLookDown.get() && !(mc.player.getPitch() >= pitch.get()))
            return;

        if (!holdingShift && sneakPressed.get())
            return;

        if (!mc.player.isSneaking()) event.clip = true;
    }

    @EventHandler
    private void onKey(final KeyEvent e) {
        holdingShift = e.key == GLFW.GLFW_KEY_LEFT_SHIFT;
    }
}
