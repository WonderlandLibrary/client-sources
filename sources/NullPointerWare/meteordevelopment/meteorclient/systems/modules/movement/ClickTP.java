/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package meteordevelopment.meteorclient.systems.modules.movement;

import meteordevelopment.meteorclient.events.meteor.KeyEvent;
import meteordevelopment.meteorclient.events.render.Render3DEvent;
import meteordevelopment.meteorclient.renderer.Renderer3D;
import meteordevelopment.meteorclient.renderer.ShapeMode;
import meteordevelopment.meteorclient.settings.*;
import meteordevelopment.meteorclient.systems.modules.Categories;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.systems.modules.combat.Aura;
import meteordevelopment.meteorclient.utils.render.RenderUtils;
import meteordevelopment.meteorclient.utils.render.color.Color;
import meteordevelopment.meteorclient.utils.render.color.SettingColor;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.glfw.GLFW;

public final class ClickTP extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();
    private final SettingGroup sgRender = settings.createGroup("Render");

    private final Setting<Mode> mode = sgGeneral.add(new EnumSetting.Builder<Mode>()
        .name("weapon")
        .description("Only attacks an entity when a specified weapon is in your hand.")
        .defaultValue(Mode.onEnable)
        .build()
    );

    private final Setting<Double> maxDistance = sgGeneral.add(new DoubleSetting.Builder()
        .name("max-distance")
        .description("The maximum distance you can teleport.")
        .defaultValue(5)
        .build()
    );

    private final Setting<Boolean> render = sgRender.add(new BoolSetting.Builder()
        .name("render")
        .description("renders shit")
        .defaultValue(true)
        .build()
    );

    private final Setting<Integer> duration = sgGeneral.add(new IntSetting.Builder()
        .name("duration")
        .description("the duration of the render")
        .defaultValue(20)
        .build()
    );

    private final Setting<SettingColor> sideColor = sgRender.add(new ColorSetting.Builder()
        .name("side-color")
        .description("The side color of the target block rendering.")
        .defaultValue(new SettingColor(197, 137, 232, 10))
        .visible(render::get)
        .build()
    );

    private final Setting<SettingColor> lineColor = sgRender.add(new ColorSetting.Builder()
        .name("line-color")
        .description("The line color of the target block rendering.")
        .defaultValue(new SettingColor(197, 137, 232))
        .visible(render::get)
        .build()
    );

    Vec3d tpPos;

    public ClickTP() {
        super(Categories.Movement, "click-tp", "Teleports you to the block you look at.");
    }

    @Override
    public void onActivate() {
        if (!mode.get().equals(Mode.onEnable)) {
            this.toggle();
            return;
        }

        teleport();
        this.toggle();
    }

    @EventHandler
    private void onKey(final KeyEvent e) {
        if (e.key == GLFW.GLFW_MOUSE_BUTTON_LEFT) {
            if (mode.get().equals(Mode.mouse)) {
              teleport();
            }
        }
    }

    @EventHandler
    private void render3D(final Render3DEvent e) {
        if (isNull()) return;

        BlockPos pos;
        BlockHitResult hit = (BlockHitResult) mc.player.raycast(maxDistance.get(), mc.getTickDelta(), false);
        boolean miss = hit.getType() == HitResult.Type.MISS;
        pos = miss ? null : hit.getBlockPos();

        if (pos != null)
            RenderUtils.renderTickingBlock(pos, sideColor.get(), lineColor.get(), ShapeMode.Both, 0, duration.get(), false, false);
    }

    private void teleport() {
        BlockPos pos;
        BlockHitResult hit = (BlockHitResult) mc.player.raycast(maxDistance.get(), mc.getTickDelta(), false);
        boolean miss = hit.getType() == HitResult.Type.MISS;
        pos = miss ? null : hit.getBlockPos();
        Direction dir = Direction.UP;
        if (pos != null) {
            tpPos = Vec3d.ofBottomCenter(pos.offset(dir, 1));
        } else {
            info("Please look at a block, that is in range.");
            this.toggle();
            return;
        }
        if (!mc.world.getBlockState(pos.up(2)).isAir() || !mc.world.getBlockState(pos.up(1)).isAir() || !mc.world.getBlockState(pos.up(3)).isAir()) {
            info("Please look at a block, that has air over it.");
            this.toggle();
            return;
        }

        mc.player.setPosition(tpPos.getX(), tpPos.getY(), tpPos.getZ());
    }

    public enum Mode {
     onEnable,
     mouse
    }
}
