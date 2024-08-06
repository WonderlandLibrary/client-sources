package com.shroomclient.shroomclientnextgen.listeners;

import com.shroomclient.shroomclientnextgen.annotations.RegisterListeners;
import com.shroomclient.shroomclientnextgen.events.Bus;
import com.shroomclient.shroomclientnextgen.events.EventBus.Priority;
import com.shroomclient.shroomclientnextgen.events.SubscribeEvent;
import com.shroomclient.shroomclientnextgen.events.impl.JumpEvent;
import com.shroomclient.shroomclientnextgen.events.impl.MotionEvent;
import com.shroomclient.shroomclientnextgen.events.impl.PacketEvent;
import com.shroomclient.shroomclientnextgen.events.impl.StepEvent;
import com.shroomclient.shroomclientnextgen.mixin.EntityVelocityUpdateS2CPacketAccessor;
import com.shroomclient.shroomclientnextgen.mixin.ExplosionS2CPacketAccessor;
import com.shroomclient.shroomclientnextgen.modules.ModuleManager;
import com.shroomclient.shroomclientnextgen.modules.impl.client.Notifications;
import com.shroomclient.shroomclientnextgen.modules.impl.movement.LongJump;
import com.shroomclient.shroomclientnextgen.modules.impl.movement.Scaffold;
import com.shroomclient.shroomclientnextgen.modules.impl.player.NoFall;
import com.shroomclient.shroomclientnextgen.protection.JnicInclude;
import com.shroomclient.shroomclientnextgen.util.C;
import com.shroomclient.shroomclientnextgen.util.MovementUtil;
import java.awt.*;
import java.util.stream.StreamSupport;
import net.minecraft.block.AirBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.network.packet.s2c.play.EntityVelocityUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.ExplosionS2CPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;

@RegisterListeners
public class MoveListener {

    public static Vec3d prevPos = new Vec3d(0.0, 0.0, 0.0);
    public static Vec3d epicPrevPos = new Vec3d(0.0, 0.0, 0.0);
    public static boolean recieved = false;
    public static boolean fireballed = false;
    ExplosionS2CPacket ExplosionPacket = null;
    EntityVelocityUpdateS2CPacket VelocityPacket = null;

    @JnicInclude
    @SubscribeEvent(Priority.HIGHEST)
    public void onPreMotion(MotionEvent.Pre e) {
        if (C.p() == null) return;

        MovementUtil.ticks++;
        if (!e.isOnGround()) {
            if (MovementUtil.airTicks == 0) {
                MovementUtil.jumps++;
                MovementUtil.lastLeaveGroundPos = prevPos;
                if (
                    !ModuleManager.isEnabled(Scaffold.class)
                ) MovementUtil.lastLeaveGroundPosNoScaffold = prevPos;
            }

            MovementUtil.airTicks++;
            MovementUtil.groundTicks = 0;
        } else {
            MovementUtil.groundTicks++;
            MovementUtil.airTicks = 0;
        }

        if (C.p().hurtTime >= 9) MovementUtil.ticksSinceLastHurt = 0;
        MovementUtil.ticksSinceLastHurt++;

        if (!NoFall.isOverVoid()) {
            MovementUtil.lastNotOverVoidPos = C.p().getPos();
            MovementUtil.lastNotOverVoidVelo = C.p().getVelocity();
        }

        MovementUtil.lastOnGround = MovementUtil.onGroundClient;
        MovementUtil.onGroundClient = C.p().isOnGround();

        MovementUtil.lastUsingItem = MovementUtil.isUsingItem;
        MovementUtil.isUsingItem = C.p().isUsingItem();

        // C.p.getlastpos
        prevPos = C.p().getPos();

        // ------------------------------------------------------------------------------------
        // extra shit done outside of their file because i hate @alwayspost or smth

        // lj
        if (LongJump.hypixelBallin) {
            int ticksBalling = MovementUtil.ticks - LongJump.fireballTicks;
            if (fireballed) {
                if (
                    ticksBalling > 10 &&
                    C.p().isOnGround() &&
                    LongJump.fireballed
                ) {
                    if (
                        this.ExplosionPacket == null &&
                        this.VelocityPacket == null
                    ) {
                        Notifications.notify(
                            "No Velocity Received",
                            new Color(162, 3, 75),
                            2
                        );
                    }

                    fireballed = false;
                    LongJump.fireballed = false;
                }

                // does keep y
                //float height = 0.01F;
                //int flightTicksFirstHalf = 30;
                //int flightTicksSecondHalf = 30;
                //int flightTicksMax = 60;
                //int hopTicks = 1;

                // highest possible
                //float height = 0.42F;
                //int flightTicksFirstHalf = 10;
                //int flightTicksSecondHalf = 30;
                //int flightTicksMax = 30;
                //int hopTicks = 1;

                // most stable
                float height = 0.3f;
                int flightTicksFirstHalf = 10;
                int flightTicksSecondHalf = 30;
                int flightTicksMax = 30;

                // messing with motion in any way (lower or higher) always causes lagback
                //if (ticksBalling < 5)
                //    MovementUtil.setMotion(1f);

                if (C.p().hurtTime == 9) {
                    // insta flags on any other tick or any higher number
                    MovementUtil.setMotion(1.1f);
                    recieved = true;
                }

                if (recieved) {
                    if (ticksBalling <= flightTicksFirstHalf) {
                        C.p()
                            .setVelocity(
                                C.p().getVelocity().x,
                                height,
                                C.p().getVelocity().z
                            );
                    } else if (
                        ticksBalling <= flightTicksSecondHalf &&
                        ticksBalling % 2 == 0
                    ) {
                        C.p()
                            .setVelocity(
                                C.p().getVelocity().x,
                                height,
                                C.p().getVelocity().z
                            );
                    }

                    if (
                        MovementUtil.airTicks == 0 ||
                        ticksBalling > flightTicksMax + 10
                    ) {
                        LongJump.fireballed = false;
                        fireballed = false;
                        recieved = false;
                    }
                }
            }
        } else if (
            MovementUtil.airTicks == 0 ||
            MovementUtil.ticks - LongJump.fireballTicks > 10
        ) {
            LongJump.fireballed = false;
            fireballed = false;
        }

        // custom step event because mixins hard
        if (C.p().horizontalCollision) {
            if (
                C.p().isOnGround() &&
                !C.p().isClimbing() &&
                !C.p().isTouchingWater() &&
                !C.p().isInLava()
            ) {
                if (
                    C.p().input.movementForward != 0 ||
                    C.p().input.movementSideways != 0
                ) {
                    if (!C.mc.options.jumpKey.isPressed()) {
                        Box box = C.p()
                            .getBoundingBox()
                            .offset(0, 0.05, 0)
                            .expand(0.05);

                        Iterable<VoxelShape> blockCollisions = C.w()
                            .getBlockCollisions(C.p(), box);

                        double stepHeight = StreamSupport.stream(
                            blockCollisions.spliterator(),
                            false
                        )
                            .flatMap(shape -> shape.getBoundingBoxes().stream())
                            .filter(shapeBox -> shapeBox.intersects(box))
                            .mapToDouble(bb -> bb.maxY)
                            .max()
                            .orElse(0);

                        stepHeight -= C.p().getY();

                        if (
                            C.w()
                                .getBlockState(
                                    new BlockPos(
                                        C.p().getBlockX(),
                                        (int) (C.p().getEyeY() + 1),
                                        C.p().getBlockZ()
                                    )
                                )
                                .getBlock() instanceof
                            AirBlock
                        ) if (
                            !(C.w()
                                    .getBlockState(
                                        new BlockPos(
                                            C.p().getBlockX(),
                                            (C.p().getBlockY()),
                                            C.p().getBlockZ()
                                        )
                                    )
                                    .getBlock() instanceof
                                StairsBlock)
                        ) if (
                            !(C.w()
                                    .getBlockState(
                                        new BlockPos(
                                            C.p().getBlockX(),
                                            (C.p().getBlockY() - 1),
                                            C.p().getBlockZ()
                                        )
                                    )
                                    .getBlock() instanceof
                                StairsBlock)
                        ) if (
                            !(C.w()
                                    .getBlockState(
                                        new BlockPos(
                                            C.p().getBlockX(),
                                            (C.p().getBlockY()),
                                            C.p().getBlockZ()
                                        )
                                    )
                                    .getBlock() instanceof
                                SlabBlock)
                        ) if (
                            !(C.w()
                                    .getBlockState(
                                        new BlockPos(
                                            C.p().getBlockX(),
                                            (C.p().getBlockY() - 1),
                                            C.p().getBlockZ()
                                        )
                                    )
                                    .getBlock() instanceof
                                SlabBlock)
                        ) Bus.post(new StepEvent(stepHeight));
                    }
                }
            }
        }
    }

    @SubscribeEvent(Priority.HIGHEST)
    public void PacketEvent(PacketEvent.Send.Pre event) {}

    @SubscribeEvent
    public void PacketEvent(PacketEvent.Receive.Pre event) {
        if (LongJump.fireballed) {
            if (event.getPacket() instanceof EntityVelocityUpdateS2CPacket p) {
                if (p.getId() == C.p().getId() && fireballed) {
                    this.VelocityPacket = p;

                    ((EntityVelocityUpdateS2CPacketAccessor) p).setVelocityY(
                            100
                        );
                }
            } else {
                if (event.getPacket() instanceof ExplosionS2CPacket p) {
                    epicPrevPos = C.p().getPos();
                    this.ExplosionPacket = p;
                    fireballed = true;
                    LongJump.fireballTicks = MovementUtil.ticks;

                    ((ExplosionS2CPacketAccessor) p).setPlayerVelocityX(0);
                    ((ExplosionS2CPacketAccessor) p).setPlayerVelocityZ(0);
                    ((ExplosionS2CPacketAccessor) p).setPlayerVelocityY(0.1f);
                }
            }
        }
    }

    @SubscribeEvent(Priority.HIGHEST)
    public void jumpEvent(JumpEvent e) {
        MovementUtil.lastJumpPos = C.p().getPos();
        MovementUtil.lastJumpVelocity = C.p().getVelocity();
    }
}
