/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package meteordevelopment.meteorclient.systems.modules.movement;

import kroppeb.stareval.function.Type;
import meteordevelopment.meteorclient.events.entity.player.PlayerMoveEvent;
import meteordevelopment.meteorclient.events.meteor.KeyEvent;
import meteordevelopment.meteorclient.events.world.CollisionShapeEvent;
import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.settings.*;
import meteordevelopment.meteorclient.systems.modules.Categories;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.systems.modules.Modules;
import meteordevelopment.meteorclient.systems.modules.player.Rotation;
import meteordevelopment.meteorclient.utils.Utils;
import meteordevelopment.meteorclient.utils.player.MoveUtils;
import meteordevelopment.meteorclient.utils.world.BlockUtils;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShapes;
import org.lwjgl.glfw.GLFW;

import java.util.Objects;

public final class AntiVoid extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private int tick;
    private boolean keyPressed = false;

    private final Setting<Mode> mode = sgGeneral.add(new EnumSetting.Builder<Mode>()
        .name("mode")
        .description("The method to prevent you from falling into the void.")
        .defaultValue(Mode.Vulcanvoidcheck)
        .onChanged(a -> onActivate())
        .build()
    );

    private final Setting<Double> minimumY = sgGeneral.add(new DoubleSetting.Builder()
        .name("trigger height")
        .description("The height for the antivoid to get triggered.")
        .defaultValue(35)
        .sliderMax(360)
        .min(0)
        .max(360)
        .build()
    );

    private final Setting<Double> falldistance = sgGeneral.add(new DoubleSetting.Builder()
        .name("fall distance")
        .description("The fall distance before it triggers")
        .defaultValue(5)
        .sliderMax(100)
        .min(0)
        .max(360)
        .build()
    );

    public AntiVoid() {
        super(Categories.Movement, "anti-void", "Attempts to prevent you from falling into the void.");
    }

    @Override
    public void onDeactivate() {
        if (Modules.get().get(MoveExploit.class).isActive()){
            Modules.get().get(MoveExploit.class).toggle();
        }
        keyPressed = false;
    }

    @EventHandler
    private void onPreTick(final TickEvent.Pre e) {
        final int minY = mc.world.getBottomY();
        tick++;

        switch (mode.get()) {
            case Fallcheck:
                if (mc.player.fallDistance > falldistance.get()) {
                    tick = 0;
                    if (tick < 8) {
                        if (!Modules.get().get(MoveExploit.class).isActive()) {
                            Modules.get().get(MoveExploit.class).toggle();
                            mc.player.setPosition(mc.player.getPos().getX() + 0.01, mc.player.getPos().getY(), mc.player.getPos().getZ());
                        }
                    } else {
                        if (Modules.get().get(MoveExploit.class).isActive()) {
                            Modules.get().get(MoveExploit.class).toggle();
                        }
                    }
                }
                break;

            case Vulcanvoidcheck:
                if (BlockUtils.isVoidBelow2(mc.player.getBlockPos(), false)){
                    tick = 0;

                    if (tick < 8){
                        if (!Modules.get().get(MoveExploit.class).isActive()){
                            Modules.get().get(MoveExploit.class).toggle();
                            mc.player.setPosition(mc.player.getPos().getX() + 0.01, mc.player.getPos().getY(), mc.player.getPos().getZ());
                        }
                    }else {
                        if (Modules.get().get(MoveExploit.class).isActive()){
                            Modules.get().get(MoveExploit.class).toggle();
                        }
                    }
                }
                break;

            case Vulcanheight:
                if (mc.player.getY() < minY + minimumY.get()){
                    tick = 0;

                    if (tick < 8){
                        if (!Modules.get().get(MoveExploit.class).isActive()){
                            Modules.get().get(MoveExploit.class).toggle();
                        }
                        mc.player.setPosition(mc.player.getPos().getX() + 0.01, mc.player.getPos().getY(), mc.player.getPos().getZ());
                    }else {
                        if (Modules.get().get(MoveExploit.class).isActive()){
                            Modules.get().get(MoveExploit.class).toggle();
                        }
                    }
                }

                if (mc.player.getY() > minY + minimumY.get()){
                    if (Modules.get().get(MoveExploit.class).isActive()){
                        Modules.get().get(MoveExploit.class).toggle();
                    }
                }
                break;

            case Skid:
                if (mc.player.fallDistance > falldistance.get() && BlockUtils.getDistanceToVoid() == mc.player.getY() - 1.0D) {
                    Vec3d pos = mc.player.getPos();
                    double newY = pos.y + 0.3D;
                    PlayerMoveC2SPacket.PositionAndOnGround packet = new PlayerMoveC2SPacket.PositionAndOnGround(pos.x, newY, pos.z, false);
                    sendNoEvent(packet);
                }
                break;

            case Freeze:
                if (mc.player.fallDistance > falldistance.get() && BlockUtils.isVoidBelow2(mc.player.getBlockPos(), false) && !Modules.get().isActive(StopMotion.class)) {
                    Modules.get().get(StopMotion.class).toggle();
                }
                break;
        }
    }

    @EventHandler
    private void onMove(final PlayerMoveEvent e) {
        if (mode.get().equals(Mode.NoVelo)) {
            if (isNull())
                return;
            if (BlockUtils.isVoidBelow2(mc.player.getBlockPos(), false) && mc.player.fallDistance > falldistance.get()) {
                e.setX(0);
                e.setY(0);
                e.setZ(0);
            }
        }
    }

    public enum Mode {
        Vulcanheight,
        Vulcanvoidcheck,
        Fallcheck,
        NoVelo,
        Skid,
        Freeze
    }
}
